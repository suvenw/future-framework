package com.suven.framework.core.db;



/**
 * @Title: DruidDataConfig.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) DruidDataSource 基础属性配置bean类；
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public class DruidDataConfig {


        private int initialSize;
        private int minIdle;
        private int maxActive;
        private int maxWait;
        private long timeBetweenEvictionRunsMillis;
        private long minEvictableIdleTimeMillis;
        private String validationQuery;
        private boolean testWhileIdle;
        private boolean testOnBorrow;
        private boolean testOnReturn;
        private boolean poolPreparedStatements;
        private String filters;
        private boolean useGlobalDataSourceStat;
        private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500";






        public DruidDataConfig(){
        }

        public static DruidDataConfig build(){
            return new DruidDataConfig();
        }

        public int getInitialSize() {
            return initialSize;
        }

        public DruidDataConfig setInitialSize(int initialSize) {
            this.initialSize = initialSize;
            return this;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public DruidDataConfig setMinIdle(int minIdle) {
            this.minIdle = minIdle;
            return this;
        }

        public int getMaxActive() {
            return maxActive;
        }

        public DruidDataConfig setMaxActive(int maxActive) {
            this.maxActive = maxActive;
            return this;
        }

        public int getMaxWait() {
            return maxWait;
        }

        public DruidDataConfig setMaxWait(int maxWait) {
            this.maxWait = maxWait;
            return this;
        }

        public long getTimeBetweenEvictionRunsMillis() {
            return timeBetweenEvictionRunsMillis;
        }

        public DruidDataConfig setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
            this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
            return this;
        }

        public long getMinEvictableIdleTimeMillis() {
            return minEvictableIdleTimeMillis;
        }

        public DruidDataConfig setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
            this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
            return this;
        }

        public String getValidationQuery() {
            return validationQuery;
        }

        public DruidDataConfig setValidationQuery(String validationQuery) {
            this.validationQuery = validationQuery;
            return this;
        }

        public boolean isTestWhileIdle() {
            return testWhileIdle;
        }

        public DruidDataConfig setTestWhileIdle(boolean testWhileIdle) {
            this.testWhileIdle = testWhileIdle;
            return this;
        }

        public boolean isTestOnBorrow() {
            return testOnBorrow;
        }

        public DruidDataConfig setTestOnBorrow(boolean testOnBorrow) {
            this.testOnBorrow = testOnBorrow;
            return this;
        }

        public boolean isTestOnReturn() {
            return testOnReturn;
        }

        public DruidDataConfig setTestOnReturn(boolean testOnReturn) {
            this.testOnReturn = testOnReturn;
            return this;
        }

        public boolean isPoolPreparedStatements() {
            return poolPreparedStatements;
        }

        public DruidDataConfig setPoolPreparedStatements(boolean poolPreparedStatements) {
            this.poolPreparedStatements = poolPreparedStatements;
            return this;
        }

        public String getFilters() {
            return filters;
        }

        public DruidDataConfig setFilters(String filters) {
            this.filters = filters;
            return this;
        }

        public boolean isUseGlobalDataSourceStat() {
            return useGlobalDataSourceStat;
        }

        public DruidDataConfig setUseGlobalDataSourceStat(boolean useGlobalDataSourceStat) {
            this.useGlobalDataSourceStat = useGlobalDataSourceStat;
            return this;
        }

        public String getConnectionProperties() {
            return connectionProperties;
        }

        public DruidDataConfig setConnectionProperties(String connectionProperties) {
            this.connectionProperties = connectionProperties;
            return this;
        }

        @Override
        public String toString() {
            return "DruidDataConfig{" +
                    "initialSize=" + initialSize +
                    ", minIdle=" + minIdle +
                    ", maxActive=" + maxActive +
                    ", maxWait=" + maxWait +
                    ", timeBetweenEvictionRunsMillis=" + timeBetweenEvictionRunsMillis +
                    ", minEvictableIdleTimeMillis=" + minEvictableIdleTimeMillis +
                    ", validationQuery='" + validationQuery + '\'' +
                    ", testWhileIdle=" + testWhileIdle +
                    ", testOnBorrow=" + testOnBorrow +
                    ", testOnReturn=" + testOnReturn +
                    ", poolPreparedStatements=" + poolPreparedStatements +
                    ", filters='" + filters + '\'' +
                    '}';
        }
    }