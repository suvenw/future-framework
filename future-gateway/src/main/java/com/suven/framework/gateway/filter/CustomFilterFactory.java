package com.suven.framework.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;



@Component
public class CustomFilterFactory extends AbstractGatewayFilterFactory<CustomFilterFactory.Config> {

    public CustomFilterFactory() {
        super(Config.class);
    }
    private Logger logger = LoggerFactory.getLogger(CustomFilterFactory.class);

    @Override
    public GatewayFilter apply(Config config) {
        // 自定义 GatewayFilter 示例：
        //  - 记录请求路径
        //  - 为请求头添加一个标记，供下游服务使用
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getRawPath();
            logger.info("[Gateway][CustomFilterFactory] 进入自定义过滤器, path: {}", path);

            ServerHttpRequest mutated = request.mutate()
                    .headers(headers -> headers.addIfAbsent("X-Custom-Factory", "enabled"))
                    .build();

            return chain.filter(exchange.mutate().request(mutated).build());
        };
    }

    public static class Config {
        // define properties for the filter
    }
}