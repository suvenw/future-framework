package com.suven.framework.core.mybatis;

import lombok.Data;
import java.util.Set;

/**
 * 租户忽略配置信息
 */
public class TenantIgnoreConfig {
    
    /**
     * 表名
     */
    private String tableName;
    
    /**
     * 实体类名
     */
    private String entityClassName;
    
    /**
     * 忽略原因
     */
    private String reason;
    
    /**
     * 是否启用
     */
    private boolean ignore;
    
    public TenantIgnoreConfig(String tableName, String entityClassName, String reason, boolean ignore) {
        this.tableName = tableName;
        this.entityClassName = entityClassName;
        this.reason = reason;
        this.ignore = ignore;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEntityClassName() {
        return entityClassName;
    }

    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }
}