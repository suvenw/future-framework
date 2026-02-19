package com.suven.framework.gateway.filter;

import com.suven.framework.gateway.common.GatewayProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 白名单过滤器
 * 对白名单中的路径直接放行，跳过鉴权
 */
@Component
public class WhiteListFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(WhiteListFilter.class);

    @Autowired
    private GatewayProperties gatewayProperties;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /** 默认白名单路径 */
    private static final List<String> DEFAULT_WHITE_LIST = Arrays.asList(
            "/actuator/**",
            "/health",
            "/health/**",
            "/info",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/webjars/**",
            "/favicon.ico",
            "/error"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getRawPath();

        // 检查是否在白名单中
        boolean isWhiteList = isInWhiteList(path);
        
        // 将白名单标记放入 exchange 属性中，供后续过滤器使用
        exchange.getAttributes().put("isWhiteList", isWhiteList);

        if (isWhiteList) {
            log.debug("[WhiteListFilter] 路径 {} 命中白名单，直接放行", path);
        }

        return chain.filter(exchange);
    }

    /**
     * 检查路径是否在白名单中
     */
    public boolean isInWhiteList(String path) {
        List<String> allWhiteList = new ArrayList<>();
        allWhiteList.addAll(DEFAULT_WHITE_LIST);
        allWhiteList.addAll(gatewayProperties.getWhiteListPaths());

        for (String pattern : allWhiteList) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        // 在日志过滤器之后，鉴权过滤器之前执行
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}
