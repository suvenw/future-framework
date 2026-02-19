package com.suven.framework.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 简单白名单过滤器：
 *  - 对一些无需鉴权的路径直接放行（仅示例逻辑，可按需替换为真实白名单配置）
 */
@Component
public class CustomWhiteFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(CustomWhiteFilter.class);

    /**
     * 示例白名单路径前缀，可以根据实际业务改为从配置中心读取
     */
    private static final List<String> WHITE_LIST_PATH_PREFIXES = Arrays.asList(
            "/test/hello",
            "/actuator",
            "/health"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getURI().getRawPath();

        logger.info("[Gateway][WhiteFilter] 请求路径: {}", requestPath);

        // 简单白名单判断：命中则直接透传，不做额外处理
        if (isWhitelisted(requestPath)) {
            logger.debug("[Gateway][WhiteFilter] 命中白名单路径, 直接放行: {}", requestPath);
            return chain.filter(exchange);
        }

        // 非白名单路径，目前仅透传，后续可以在此处添加统一鉴权/验签逻辑
        return chain.filter(exchange);
    }

    private boolean isWhitelisted(String path) {
        for (String prefix : WHITE_LIST_PATH_PREFIXES) {
            if (path.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        // 比全局日志过滤器稍后执行
        return 10;
    }
}