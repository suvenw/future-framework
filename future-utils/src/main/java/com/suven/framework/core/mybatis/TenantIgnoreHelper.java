package com.suven.framework.core.mybatis;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 租户忽略工具类
 */
@Component
public class TenantIgnoreHelper {
    
    @Autowired(required = false)
    private IgnoreTenantLineHandler ignoreTenantLineHandler;

    public boolean isIgnoreNull() {
        return this.ignoreTenantLineHandler == null;
    }

    /**
     * 检查实体类是否忽略租户
     */
    public boolean isIgnoreTenant(Class<?> entityClass) {
        if (isIgnoreNull()){
            return false;
        }
        return ignoreTenantLineHandler.isIgnoreByEntityClass(entityClass);
    }
    
    /**
     * 检查表是否忽略租户
     */
    public boolean isIgnoreTenant(String tableName) {
        if (isIgnoreNull()){
            return false;
        }
        return ignoreTenantLineHandler.isIgnoreByTableName(tableName);
    }
    
    /**
     * 获取所有忽略租户的表
     */
    public Set<String> getIgnoreTenantTables() {
        if (isIgnoreNull()){
            return new HashSet<>();
        }
        return ignoreTenantLineHandler.getAllIgnoreTables();
    }
}