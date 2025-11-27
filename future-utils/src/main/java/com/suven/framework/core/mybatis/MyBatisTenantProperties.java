package com.suven.framework.core.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户配置属性
 */
@ConfigurationProperties(prefix = "saas.server.mybatis")
public class MyBatisTenantProperties {

    /**
     * 是否启用多租户
     */
    private boolean enabled = false;

    /**
     * 扫描带有 @TenantIgnore 的实体包路径
     */
    private List<String> scanPackages = new ArrayList<>();

    /**
     * 需要忽略多租户的 Mapper Statement ID 列表
     */
    private List<String> ignoreStatements = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(List<String> scanPackages) {
        this.scanPackages = scanPackages;
    }

    public List<String> getIgnoreStatements() {
        return ignoreStatements;
    }

    public void setIgnoreStatements(List<String> ignoreStatements) {
        this.ignoreStatements = ignoreStatements;
    }
}

