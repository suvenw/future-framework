//package com.suven.framework.core.mybatis;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
//import com.baomidou.mybatisplus.core.config.GlobalConfig;
//import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
//import com.baomidou.mybatisplus.core.injector.ISqlInjector;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import com.suven.framework.common.constants.GlobalConfigConstants;
//import com.suven.framework.core.db.druid.DruidDataSourceAutoConfig;
//import com.suven.framework.core.db.druid.DruidDataSourceConfigWrapper;
//import org.apache.ibatis.plugin.Interceptor;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Title: MyBatisAutoConfig.java
// * @author Joven.wang
// * date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) MyBatis DataSource Auto Config 配置启动加载实现类,默认是自动启动,可以在配置文件关闭:top.server.mybatis.enabled=false;
// * Copyright: (c) 2018 gc by https://www.suven.top
// *
// */
//
//@Configuration
//@ConditionalOnProperty(name = GlobalConfigConstants.TOP_SERVER_MYBATIS_ENABLED,  matchIfMissing = true)
//@ConfigurationProperties(GlobalConfigConstants.TOP_SERVER_MYBATIS)
//
////@EnableAutoConfiguration(exclude = {MybatisPlusAutoConfiguration.class})
////@AutoConfigureBefore({MybatisPlusAutoConfiguration.class})
//@AutoConfigureAfter({DruidDataSourceAutoConfig.class})
////@NacosConfigurationProperties( groupId= GlobalConfigConstants.SERVICE_NAME, dataId = GlobalConfigConstants.TOP_SERVER_MYBATIS_NAME, prefix= GlobalConfigConstants.TOP_SERVER_MYBATIS,  autoRefreshed = true)
//class MyBatisAutoConfigSetting implements ApplicationContextAware{
//
//
//    private ApplicationContext applicationContext;
//
//    private String resources = "classpath*:mapper/**/*.xml";
//    private String idType  = "AUTO";
//
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//
//    @Bean
//    public GlobalConfig globalConfig(){
//        GlobalConfig globalConfig =  new GlobalConfig();
//        GlobalConfig.DbConfig config = new GlobalConfig.DbConfig();
//        globalConfig.setDbConfig(config.setIdType(idTypeValueOf()));
//        globalConfig.setMetaObjectHandler(new MyBatisMetaHandler());
//
//        return globalConfig;
//    }
//
//
//    /** 使用mybatis plus 必须使用该实现类 **/
////    @ConditionalOnClass(DruidDataSourceConfigWrapper.class)
////    @ConditionalOnMissingBean(DruidDataSourceConfigWrapper.class)
////    @Bean
////    public FactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
//////        DataSource dataSource = applicationContext.getBean("dataSource",DataSource.class);
//////      SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
////        /** 使用mybatis plus 必须使用该实现类 **/
////        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
////
////        sqlSessionFactory.setDataSource(dataSource);
////        sqlSessionFactory.setGlobalConfig(globalConfig());
////        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(getResources()));
////        sqlSessionFactory.setPlugins(getPlugins());
////
////        return sqlSessionFactory;
////    }
//
//
//
//    /**
//     * 分页插件
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }
//
//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new DefaultSqlInjector();
//    }
//
//
//
//    public String getResources() {
//        return resources;
//    }
//
//    public Interceptor[] getPlugins() {
//        List<Interceptor> interceptors = new ArrayList<>();
//        interceptors.add(paginationInterceptor());
//        return interceptors.toArray(new Interceptor[interceptors.size()]);
//    }
//
//    public void setResources(String resources) {
//        if(null != resources && "".equals(resources))
//            this.resources = resources;
//    }
//
//    private IdType idTypeValueOf(){
//        String id = getIdType();
//        if(null ==  id){
//            return IdType.ID_WORKER;
//        }
//        return IdType.valueOf(id);
//    }
//
//    public String getIdType() {
//        return idType;
//    }
//
//
//    public void setIdType(String idType) {
//        this.idType = idType;
//    }
//
//
//}
