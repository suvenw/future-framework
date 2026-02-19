package com.suven.framework.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Gateway 网关服务启动类
 * 
 * 功能说明：
 * 1. 统一入口 - 所有微服务请求经过网关转发
 * 2. 路由转发 - 基于 Nacos 服务发现自动路由
 * 3. JWT 鉴权 - 可选的 Token 验证（可通过配置开启）
 * 4. 访问日志 - 记录请求信息和响应时间
 * 5. 限流控制 - 基于令牌桶算法的限流（可通过配置开启）
 * 6. 跨域支持 - 全局 CORS 配置
 * 7. 白名单 - 支持配置免鉴权路径
 * 
 * 过滤器执行顺序：
 * 1. AccessLogFilter - 记录访问日志
 * 2. WhiteListFilter - 白名单检查
 * 3. AuthFilter - JWT 鉴权（如果开启）
 * 4. RateLimitFilter - 限流控制（如果开启）
 * 5. ResponseHeaderFilter - 添加响应头
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class GatewayMain {

    public static void main(String[] args) {
        SpringApplication.run(GatewayMain.class, args);
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");
        System.out.println("║              Future Gateway Service Started              ║");
        System.out.println("║                                                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
}
