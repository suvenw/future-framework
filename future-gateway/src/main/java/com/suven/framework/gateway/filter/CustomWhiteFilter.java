package com.suven.framework.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomWhiteFilter implements GlobalFilter {

    private Logger logger = LoggerFactory.getLogger(CustomFilterFactory.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        logger.info("=========================请求进入filter=========================");

        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getPath().toString();

        boolean allowedPath = true;
        if(allowedPath) {
            try {
                return chain.filter(exchange);
            }catch (Exception e){
                logger.info("=========================请求进入filter Exception=========================",e);
            }

        }
        return null;
    }
}