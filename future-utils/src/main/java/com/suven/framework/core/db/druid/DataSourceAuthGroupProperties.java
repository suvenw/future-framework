package com.suven.framework.core.db.druid;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;

/**
 * @Title: DataSourceAutoConfig.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) DruidDataSource 数据库聚群，启动和管理的配置实现类；；
 *  数据库连接池的相结合配置文件类型定义参数；
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */


/**
 * 应用服务起动类，加载数据源实现模块组实现类
 * 数据库聚群，启动和管理的配置实现类；
 * top.datasource.druid.config.enabled =true.表示启动该实现类来初始化DataSourceGroupNameEnum对象管理的数据源，
 * 默认值为false,
 * 数据库的初始参数都是统一的，实现类为DruidDataConfig，统一从Environment environment 初始化获取；
 * // ImportBeanDefinitionRegistrar
 * // BeanDefinitionRegistryPostProcessor
 * setEnvironment()-->postProcessBeanDefinitionRegistry() --> postProcessBeanFactory()
 */
@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class, JdbcTemplateAutoConfiguration.class})
@ConditionalOnProperty(name =  DatasourceConfiguration.SPRING_DATA_SOURCE_ENABLED,  matchIfMissing = true)
@ConfigurationProperties(value = DatasourceConfiguration.SPRING_DATA_SOURCE)
@AutoConfigureBefore({DruidDataSourceAutoConfig.class})
public class DataSourceAuthGroupProperties implements IDataSourceGroupProperties {

    private final Logger logger = LoggerFactory.getLogger(DataSourceAuthGroupProperties.class);


    private DruidDatasourceGroup oauth;



    @Override
    public DruidDatasourceGroup getDatasourceGroup() {
        return oauth;
    }

    @Override
    public boolean getDefaultTargetDataSource() {
        return oauth.isTarget();
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return this.getClass();
    }

    public DruidDatasourceGroup getOauth() {
        return oauth;
    }

    public void setOauth(DruidDatasourceGroup oauth) {
        this.oauth = oauth;
    }
}
