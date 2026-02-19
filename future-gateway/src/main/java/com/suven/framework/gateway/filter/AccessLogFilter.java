package com.suven.framework.gateway.filter;

import com.suven.framework.gateway.common.GatewayConstant;
import com.suven.framework.gateway.common.GatewayProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 访问日志过滤器
 * 记录请求的基本信息和响应耗时
 */
@Component
public class AccessLogFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(AccessLogFilter.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Autowired
    private GatewayProperties gatewayProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!gatewayProperties.isLogEnabled()) {
            return chain.filter(exchange);
        }

        // 记录请求开始时间
        long startTime = System.currentTimeMillis();
        exchange.getAttributes().put(GatewayConstant.REQUEST_START_TIME, startTime);

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getRawPath();
        String method = request.getMethod() != null ? request.getMethod().name() : "UNKNOWN";
        String clientIp = getClientIp(request);
        String userAgent = request.getHeaders().getFirst("User-Agent");

        // 记录请求日志
        log.info("[Gateway Request] {} | {} | {} | {} | {}",
                LocalDateTime.now().format(formatter),
                clientIp,
                method,
                path,
                userAgent != null ? userAgent.substring(0, Math.min(userAgent.length(), 50)) : "-"
        );

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // 计算耗时
            Long start = exchange.getAttribute(GatewayConstant.REQUEST_START_TIME);
            long duration = start != null ? System.currentTimeMillis() - start : 0;
            int statusCode = exchange.getResponse().getStatusCode() != null 
                    ? exchange.getResponse().getStatusCode().value() : 0;

            // 记录响应日志
            if (gatewayProperties.isResponseLogEnabled()) {
                log.info("[Gateway Response] {} | {} | {} | {}ms | status:{}",
                        clientIp,
                        method,
                        path,
                        duration,
                        statusCode
                );
            } else {
                // 只记录慢请求（超过1秒）
                if (duration > 1000) {
                    log.warn("[Gateway Slow Request] {} | {} | {} | {}ms | status:{}",
                            clientIp,
                            method,
                            path,
                            duration,
                            statusCode
                    );
                }
            }
        }));
    }

    /**
     * 获取客户端真实IP
     */
    private String getClientIp(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            InetSocketAddress remoteAddress = request.getRemoteAddress();
            if (remoteAddress != null) {
                ip = remoteAddress.getAddress().getHostAddress();
            }
        }
        // 多个代理情况，取第一个IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip != null ? ip : "unknown";
    }

    @Override
    public int getOrder() {
        // 最高优先级，最先执行
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
