package com.suven.framework.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义响应头过滤器：
 *  - 通过在 application.yml 的 route 里引用 bean 名称，可为指定路由增加统一的响应头
 *
 *  使用方式示例：
 *  spring:
 *    cloud:
 *      gateway:
 *        routes:
 *          - id: future-http-system
 *            uri: lb://future-http-system
 *            predicates:
 *              - Path=/api/sys/**
 *            filters:
 *              - CustomHeaderFilter
 */
@Component
public class CustomHeaderFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.addIfAbsent("X-Gateway-App", "future-gateway");
        return chain.filter(exchange);
    }
}