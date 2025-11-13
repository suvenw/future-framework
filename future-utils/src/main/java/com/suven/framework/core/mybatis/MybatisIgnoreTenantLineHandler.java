package com.suven.framework.core.mybatis;

import com.baomidou.mybatisplus.annotation.TableName;
import com.suven.framework.core.db.ext.TenantIgnore;
import com.suven.framework.util.json.StringFormat;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 租户忽略管理器 - 手动创建和加载
 */
@Slf4j
public class MybatisIgnoreTenantLineHandler implements IgnoreTenantLineHandler {
    
    /**
     * 按表名存储的忽略配置
     * Key: 表名, Value: 忽略配置
     */
    private final Map<String, TenantIgnoreConfig> tableIgnoreMap = new ConcurrentHashMap<>();
    
    /**
     * 按实体类名存储的忽略配置
     * Key: 实体类名, Value: 忽略配置
     */
    private final Map<String, TenantIgnoreConfig> entityIgnoreMap = new ConcurrentHashMap<>();
    
    /**
     * 是否已初始化
     */
    private boolean initialized = false;
    
    /**
     * 实体类扫描包路径
     */
    private List<String> entityPackages = new ArrayList<>();
    
    /**
     * 默认构造函数
     */
    public MybatisIgnoreTenantLineHandler() {
    }
    
    /**
     * 带包路径的构造函数
     */
    public MybatisIgnoreTenantLineHandler(String... entityPackages) {
        this.entityPackages.addAll(Arrays.asList(entityPackages));
    }
    
    /**
     * 手动初始化方法
     */
    public void init() {
        if (initialized) {
            log.warn("租户忽略管理器已初始化，跳过重复初始化");
            return;
        }
        
        log.info("开始手动初始化租户忽略配置...");
        long startTime = System.currentTimeMillis();
        
        int loadedCount = 0;
        for (String packageName : entityPackages) {
            loadedCount += scanAndLoadEntities(packageName);
        }
        
        this.initialized = true;
        long costTime = System.currentTimeMillis() - startTime;
        log.info("租户忽略配置手动初始化完成，共加载 {} 个配置，耗时 {} ms", loadedCount, costTime);
        log.info("已忽略租户的表: {}", tableIgnoreMap.keySet());
    }
    
    /**
     * 带包路径的初始化方法
     */
    public void init(String... packages) {
        if (packages != null && packages.length > 0) {
            this.entityPackages.addAll(Arrays.asList(packages));
        }
        init();
    }
    
    /**
     * 扫描指定包下的实体类并加载配置
     */
    private int scanAndLoadEntities(String packageName) {
        int count = 0;
        try {
            ClassPathScanningCandidateComponentProvider scanner = 
                new ClassPathScanningCandidateComponentProvider(false);
            
            // 添加扫描过滤器：有 @TenantIgnore 注解的类
            scanner.addIncludeFilter(new AnnotationTypeFilter(TenantIgnore.class));
            
            Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(packageName);
            
            for (BeanDefinition beanDefinition : beanDefinitions) {
                String className = beanDefinition.getBeanClassName();
                Class<?> clazz = ClassUtils.forName(className, null);
                String simpleName = clazz.getSimpleName();
                String tableName = StringFormat.underscoreName(simpleName);
                // 检查是否有 @TableName 注解
                TableName tableAnnotation = clazz.getAnnotation(TableName.class);
                if (tableAnnotation != null) {
                    tableName = tableAnnotation.value();
                }
                // 检查是否有 @TableName 注解
                TenantIgnore tenantIgnore = clazz.getAnnotation(TenantIgnore.class);
                if (tenantIgnore == null){
                    continue;
                }
                // 创建配置对象
                TenantIgnoreConfig config = new TenantIgnoreConfig(
                    tableName,
                        simpleName,
                    tenantIgnore.reason(),
                    tenantIgnore.isIgnore()
                );

                // 存储到缓存
                tableIgnoreMap.put(tableName, config);
                entityIgnoreMap.put(simpleName, config);

                count++;
                log.debug("加载租户忽略配置: 表名={}, 实体类={}, 原因={}",
                         tableName, simpleName, tenantIgnore.reason());
            }
        } catch (Exception e) {
            log.error("扫描包 {} 时发生异常", packageName, e);
        }
        return count;
    }
    
    /**
     * 根据表名判断是否忽略租户
     */
    public boolean isIgnoreByTableName(String tableName) {
        checkInitialized();
        TenantIgnoreConfig config = tableIgnoreMap.get(tableName);
        return config != null && config.isIgnore();
    }
    
    /**
     * 根据实体类名判断是否忽略租户
     */
    public boolean isIgnoreByEntityName(String entityName) {
        checkInitialized();
        TenantIgnoreConfig config = entityIgnoreMap.get(entityName);
        return config != null && config.isIgnore();
    }
    
    /**
     * 根据实体类判断是否忽略租户
     */
    public boolean isIgnoreByEntityClass(Class<?> entityClass) {
        return isIgnoreByEntityName(entityClass.getSimpleName());
    }
    
    /**
     * 获取所有忽略租户的表名
     */
    public Set<String> getAllIgnoreTables() {
        checkInitialized();
        return tableIgnoreMap.keySet();
    }
    
    /**
     * 获取忽略配置信息
     */
    public TenantIgnoreConfig getIgnoreConfigByTable(String tableName) {
        checkInitialized();
        return tableIgnoreMap.get(tableName);
    }
    
    /**
     * 获取忽略配置信息
     */
    public TenantIgnoreConfig getIgnoreConfigByEntity(String entityName) {
        checkInitialized();
        return entityIgnoreMap.get(entityName);
    }
    
    /**
     * 检查是否已初始化
     */
    private void checkInitialized() {
        if (!initialized) {
            throw new IllegalStateException("租户忽略管理器未初始化，请先调用 init() 方法");
        }
    }
    
    /**
     * 重新加载配置
     */
    public void reload() {
        tableIgnoreMap.clear();
        entityIgnoreMap.clear();
        initialized = false;
        init();
    }
    
    /**
     * 手动添加忽略配置（用于编程式添加）
     */
    public void addIgnoreConfig(String tableName, String entityClassName, String reason) {
        addIgnoreConfig(tableName, entityClassName, reason, true);
    }
    
    /**
     * 手动添加忽略配置
     */
    public void addIgnoreConfig(String tableName, String entityClassName, String reason, boolean enabled) {
        TenantIgnoreConfig config = new TenantIgnoreConfig(tableName, entityClassName, reason, enabled);
        tableIgnoreMap.put(tableName, config);
        entityIgnoreMap.put(entityClassName, config);
        
        if (!initialized) {
            initialized = true;
        }
    }
    
    /**
     * 获取初始化状态
     */
    public boolean isInitialized() {
        return initialized;
    }
    
    /**
     * 销毁方法
     */
    public void destroy() {
        tableIgnoreMap.clear();
        entityIgnoreMap.clear();
        initialized = false;
        log.info("租户忽略管理器已销毁");
    }



//    /**
//     * 获取租户字段名
//     * <p>
//     * 默认字段名叫: tenant_id
//     *
//     * @return 租户字段名
//     */
//    @Override
//    public String getTenantIdColumn() {
//        return TenantLineHandler.super.getTenantIdColumn();
//    }

    /**
     * 根据表名判断是否忽略拼接多租户条件
     * <p>
     * 默认都要进行解析并拼接多租户条件
     *
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
     */
    @Override
    public boolean ignoreTable(String tableName) {
        boolean tenantIgnore = false;
        // 1. 首先检查注解配置的忽略表
        tenantIgnore = this.isIgnoreByTableName(tableName);
        if (tenantIgnore) {
            log.debug("表 {} 通过注解配置忽略租户", tableName);
            return tenantIgnore;
        }
        return tenantIgnore;
    }




    @Override
    public Expression getTenantId() {
        // 从当前上下文中获取租户ID
        String currentTenantId = TenantContext.getCurrentTenantId();
        if (currentTenantId == null) {
            throw new RuntimeException("无法获取当前租户ID");
        }
        return new StringValue(currentTenantId);
    }


}