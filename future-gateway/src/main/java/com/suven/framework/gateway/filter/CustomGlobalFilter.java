package com.suven.framework.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器示例：
 *  - 打印简单的访问日志
 *  - 在请求头中追加一个标记，方便下游服务识别来自网关的请求
 */
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(CustomGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getRawPath();
        String method = request.getMethodValue();

        log.info("[Gateway][GlobalFilter] {} {}", method, path);

        HttpHeaders headers = request.getHeaders();
        // 给下游服务一个统一的标记头
        ServerHttpRequest mutatedRequest = request.mutate()
                .headers(h -> h.addIfAbsent("X-Gateway-From", "future-gateway"))
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    /**
     * 可以通过调整顺序控制多个 GlobalFilter 的执行先后
     */
    @Override
    public int getOrder() {
        return 0;
    }
}