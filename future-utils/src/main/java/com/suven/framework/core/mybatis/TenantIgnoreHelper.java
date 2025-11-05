package com.suven.framework.core.mybatis;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 租户忽略工具类
 */
@Component
public class TenantIgnoreHelper {
    
    @Autowired
    private IgnoreTenantLineHandler ignoreTenantLineHandler;
    
    /**
     * 检查实体类是否忽略租户
     */
    public boolean isIgnoreTenant(Class<?> entityClass) {
        return ignoreTenantLineHandler.isIgnoreByEntityClass(entityClass);
    }
    
    /**
     * 检查表是否忽略租户
     */
    public boolean isIgnoreTenant(String tableName) {
        return ignoreTenantLineHandler.isIgnoreByTableName(tableName);
    }
    
    /**
     * 获取所有忽略租户的表
     */
    public Set<String> getIgnoreTenantTables() {
        return ignoreTenantLineHandler.getAllIgnoreTables();
    }
}