package com.suven.framework.gateway.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.suven.framework.gateway.common.GatewayProperties;

/**
 * Gateway 核心配置类
 * 启用 GatewayProperties 配置属性绑定
 */
@Configuration
@EnableConfigurationProperties(GatewayProperties.class)
public class GatewayConfig {

    // 配置属性通过 @EnableConfigurationProperties 自动注入到 GatewayProperties

}
