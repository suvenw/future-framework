package com.suven.framework.core.mybatis;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;

import java.util.Set;

public interface IgnoreTenantLineHandler  extends TenantLineHandler {

    /**
     *  根据表名判断是否忽略租户
     */
    public boolean isIgnoreByTableName(String tableName) ;
    /**
     * 根据实体类名判断是否忽略租户
     */
    public boolean isIgnoreByEntityName(String entityName);

    /**
     * 根据实体类判断是否忽略租户
     */
    public boolean isIgnoreByEntityClass(Class<?> entityClass) ;

    /**
     * 获取所有忽略租户的表名
     */
    public Set<String> getAllIgnoreTables() ;

    /**
     * 获取忽略配置信息
     */
    public TenantIgnoreConfig getIgnoreConfigByTable(String tableName) ;

    /**
     * 获取忽略配置信息
     */
    public TenantIgnoreConfig getIgnoreConfigByEntity(String entityName) ;

}
