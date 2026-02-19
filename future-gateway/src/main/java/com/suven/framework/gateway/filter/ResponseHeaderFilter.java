package com.suven.framework.gateway.filter;

import com.suven.framework.gateway.common.GatewayConstant;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 响应头过滤器
 * 为所有响应添加统一的网关标识头
 */
@Component
public class ResponseHeaderFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();
            
            // 添加网关标识头
            headers.addIfAbsent(GatewayConstant.HEADER_GATEWAY_FROM, "future-gateway");
            
            // 添加安全相关头
            headers.addIfAbsent("X-Content-Type-Options", "nosniff");
            headers.addIfAbsent("X-Frame-Options", "DENY");
            headers.addIfAbsent("X-XSS-Protection", "1; mode=block");
        }));
    }

    @Override
    public int getOrder() {
        // 最后执行
        return Ordered.LOWEST_PRECEDENCE;
    }
}
