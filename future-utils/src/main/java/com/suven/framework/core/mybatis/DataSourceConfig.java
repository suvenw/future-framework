//package com.suven.framework.core.mybatis;
//
//import com.suven.framework.core.db.DataDruidConfig;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
///**
// * @Title: DataSourceConfig.java
// * @author Joven.wang
// * @date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) MyBatis DataSource config MyBatis加载数据库配置文件实现类
// * Copyright: (c) 2018 gc by https://www.suven.top
// *
// */
//
//@ConfigurationProperties(prefix = DataDruidConfig.DATASOURCE_DRUID_CONFIG)
//public class DataSourceConfig {
//
//    private String url;
//
//    private String username;
//
//    private String password;
//
//    private String driverClassName;
//
//    private int maxActive = 20;
//
//    private int minIdle;
//
//    private long maxWait;
//
//    private long minEvictableIdleTimeMillis;
//
//    private int loginTimeout;
//
//    private int validationTimeoutMs = 300;
//
//    private String validationQuery = "SELECT 'x'";
//
//    private boolean testWhileIdle = true;
//
//    private boolean testOnBorrow = false;
//
//    private boolean testOnReturn = false;
//
//    private boolean poolPreparedStatements = true;
//
//    private int maxPoolPreparedStatementPerConnectionSize = 20;
//
//    private String filters = "stat";
//
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getDriverClassName() {
//        return driverClassName;
//    }
//
//    public void setDriverClassName(String driverClassName) {
//        this.driverClassName = driverClassName;
//    }
//
//    public int getMaxActive() {
//        return maxActive;
//    }
//
//    public void setMaxActive(int maxActive) {
//        this.maxActive = maxActive;
//    }
//
//    public int getMinIdle() {
//        return minIdle;
//    }
//
//    public void setMinIdle(int minIdle) {
//        this.minIdle = minIdle;
//    }
//
//    public long getMaxWait() {
//        return maxWait;
//    }
//
//    public void setMaxWait(long maxWait) {
//        this.maxWait = maxWait;
//    }
//
//    public long getMinEvictableIdleTimeMillis() {
//        return minEvictableIdleTimeMillis;
//    }
//
//    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
//        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
//    }
//
//    public int getLoginTimeout() {
//        return loginTimeout;
//    }
//
//    public void setLoginTimeout(int loginTimeout) {
//        this.loginTimeout = loginTimeout;
//    }
//
//    public int getValidationTimeoutMs() {
//        return validationTimeoutMs;
//    }
//
//    public void setValidationTimeoutMs(int validationTimeoutMs) {
//        this.validationTimeoutMs = validationTimeoutMs;
//    }
//
//    public String getValidationQuery() {
//        return validationQuery;
//    }
//
//    public void setValidationQuery(String validationQuery) {
//        this.validationQuery = validationQuery;
//    }
//
//    public boolean isTestWhileIdle() {
//        return testWhileIdle;
//    }
//
//    public void setTestWhileIdle(boolean testWhileIdle) {
//        this.testWhileIdle = testWhileIdle;
//    }
//
//    public boolean isTestOnBorrow() {
//        return testOnBorrow;
//    }
//
//    public void setTestOnBorrow(boolean testOnBorrow) {
//        this.testOnBorrow = testOnBorrow;
//    }
//
//    public boolean isTestOnReturn() {
//        return testOnReturn;
//    }
//
//    public void setTestOnReturn(boolean testOnReturn) {
//        this.testOnReturn = testOnReturn;
//    }
//
//    public boolean isPoolPreparedStatements() {
//        return poolPreparedStatements;
//    }
//
//    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
//        this.poolPreparedStatements = poolPreparedStatements;
//    }
//
//    public int getMaxPoolPreparedStatementPerConnectionSize() {
//        return maxPoolPreparedStatementPerConnectionSize;
//    }
//
//    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
//        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
//    }
//
//    public String getFilters() {
//        return filters;
//    }
//
//    public void setFilters(String filters) {
//        this.filters = filters;
//    }
//}
