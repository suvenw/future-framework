package com.suven.framework.core.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

import java.util.Arrays;

/**
 * 租户忽略管理器工厂
 */
@Slf4j
public class TenantIgnoreManagerFactory implements DisposableBean {
    
    private IgnoreTenantLineHandler tenantIgnoreManager;
    
    /**
     * 创建默认的租户忽略管理器
     */
    public IgnoreTenantLineHandler createTenantIgnoreManager() {
        return createTenantIgnoreManager("com");
    }
    
    /**
     * 创建指定包路径的租户忽略管理器
     */
    public IgnoreTenantLineHandler createTenantIgnoreManager(String... entityPackages) {
        if (tenantIgnoreManager != null) {
            log.warn("租户忽略管理器已存在，返回现有实例");
            return tenantIgnoreManager;
        }
        
        tenantIgnoreManager = new MybatisIgnoreTenantLineHandler(entityPackages);
        log.info("创建租户忽略管理器，扫描包路径: {}", Arrays.toString(entityPackages));
        return tenantIgnoreManager;
    }
    
    /**
     * 获取租户忽略管理器（如果不存在则创建）
     */
    public IgnoreTenantLineHandler getOrCreateTenantIgnoreManager(String... entityPackages) {
        if (tenantIgnoreManager == null) {
            return createTenantIgnoreManager(entityPackages);
        }
        return tenantIgnoreManager;
    }
    
    /**
     * Spring Bean 销毁时清理资源
     */
    @Override
    public void destroy() throws Exception {
        if (tenantIgnoreManager != null) {
            tenantIgnoreManager.destroy();
            tenantIgnoreManager = null;
        }
    }
}