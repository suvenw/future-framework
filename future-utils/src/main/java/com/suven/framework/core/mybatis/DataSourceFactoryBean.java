//package com.suven.framework.core.mybatis;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.springframework.beans.factory.FactoryBean;
//
//import javax.sql.DataSource;
//
//
///**
// * @Title: DataSourceFactoryBean.java
// * @author Joven.wang
// * @date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) MyBatis DataSource Factory Bean 管理工厂实现类
// * Copyright: (c) 2018 gc by https://www.suven.top
// *
// */
//public class DataSourceFactoryBean implements FactoryBean<DataSource> {
//
//    private DruidDataSource dataSource;
//
//    private DataSourceConfig configuration;
//
//    public DataSourceFactoryBean(DataSourceConfig configuration) {
//        this.configuration = configuration;
//    }
//
//    @Override
//    public DataSource getObject() throws Exception {
//        dataSource = new DruidDataSource();
//        dataSource.setUrl(configuration.getUrl());
//        dataSource.setDriverClassName(configuration.getDriverClassName());
//        dataSource.setUsername(configuration.getUsername());
//        dataSource.setPassword(configuration.getPassword());
//        dataSource.setMaxActive(configuration.getMaxActive());
//        dataSource.setMinIdle(configuration.getMinIdle());
//        dataSource.setMaxWait(configuration.getMaxWait());
//        dataSource.setMinEvictableIdleTimeMillis(configuration.getMinEvictableIdleTimeMillis());
//        dataSource.setLoginTimeout(configuration.getLoginTimeout());
//        dataSource.setValidationQueryTimeout(configuration.getValidationTimeoutMs());
//        dataSource.setValidationQuery(configuration.getValidationQuery());
//        dataSource.setTestWhileIdle(configuration.isTestWhileIdle());
//        dataSource.setTestOnBorrow(configuration.isTestOnBorrow());
//        dataSource.setTestOnReturn(configuration.isTestOnReturn());
//        dataSource.setPoolPreparedStatements(configuration.isPoolPreparedStatements());
//        dataSource.setMaxPoolPreparedStatementPerConnectionSize(configuration.getMaxPoolPreparedStatementPerConnectionSize());
//        dataSource.setFilters(configuration.getFilters());
//        return dataSource;
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return DataSource.class;
//    }
//
//    @Override
//    public boolean isSingleton() {
//        return true;
//    }
//
//    void close() {
//        if (dataSource != null && !dataSource.isClosed()) {
//            dataSource.close();
//        }
//    }
//}
