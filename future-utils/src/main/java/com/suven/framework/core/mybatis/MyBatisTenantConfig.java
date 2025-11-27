//package com.suven.framework.core.mybatis;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.CollectionUtils;
//
///**
// * MyBatis-Plus 多租户配置类
// *
// * <p>独立的多租户配置，确保在禁用多租户功能时不会加载相关类</p>
// *
// * <p>配置说明：</p>
// * <ul>
// *   <li>通过配置项 <code>saas.server.mybatis.enabled=true</code> 启用多租户功能</li>
// *   <li>如果配置为 false 或不存在，此配置类不会加载，相关类也不会被解析</li>
// * </ul>
// *
// * <p>使用方式：</p>
// * <pre>
// * # application.yml
// * saas:
// *   server:
// *     mybatis:
// *       enabled: true  # 启用多租户功能
// * </pre>
// */
//@Configuration
//@EnableConfigurationProperties(MyBatisTenantProperties.class)
//@ConditionalOnProperty(prefix = "saas.server.mybatis", name = "enabled", havingValue = "true", matchIfMissing = false)
//public class MyBatisTenantConfig {
//
//    /**
//     * 租户处理器 Bean
//     *
//     * <p>用于处理租户相关的逻辑，如获取租户ID、判断是否忽略租户等</p>
//     */
//    @Bean
//    public IgnoreTenantLineHandler tenantLineHandler(MyBatisTenantProperties tenantProperties) {
//        MybatisIgnoreTenantLineHandler handler = new MybatisIgnoreTenantLineHandler();
//        if (!CollectionUtils.isEmpty(tenantProperties.getScanPackages())) {
//            handler.init(tenantProperties.getScanPackages().toArray(new String[0]));
//        } else {
//            handler.init();
//        }
//        return handler;
//    }
//
//    /**
//     * 租户拦截器配置
//     *
//     * <p>将多租户拦截器添加到 MyBatis-Plus 拦截器链中</p>
//     */
//    @Bean
//    public MyBatisTenantLineInnerInterceptor tenantLineInnerInterceptor(
//            IgnoreTenantLineHandler tenantLineHandler, MyBatisTenantProperties tenantProperties) {
//        MyBatisTenantLineInnerInterceptor interceptor = new MyBatisTenantLineInnerInterceptor(tenantLineHandler);
//        if (!CollectionUtils.isEmpty(tenantProperties.getIgnoreStatements())) {
//            interceptor.addIgnoreStatements(tenantProperties.getIgnoreStatements());
//        }
//        return interceptor;
//    }
//}
//
