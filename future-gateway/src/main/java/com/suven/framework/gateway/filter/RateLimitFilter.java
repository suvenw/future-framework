package com.suven.framework.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suven.framework.gateway.common.GatewayConstant;
import com.suven.framework.gateway.common.GatewayProperties;
import com.suven.framework.gateway.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流过滤器（基于令牌桶算法）
 * 对请求进行限流控制，防止流量过大压垮后端服务
 */
@Component
public class RateLimitFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(RateLimitFilter.class);

    @Autowired
    private GatewayProperties gatewayProperties;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /** 令牌桶缓存：key=IP或用户ID，value=令牌桶 */
    private final ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 如果未开启限流，直接放行
        if (!gatewayProperties.isRateLimitEnabled()) {
            return chain.filter(exchange);
        }

        String limitKey = getLimitKey(exchange);
        TokenBucket bucket = buckets.computeIfAbsent(limitKey, k -> 
            new TokenBucket(gatewayProperties.getRateLimitCapacity(), gatewayProperties.getRateLimitTokens())
        );

        if (!bucket.tryAcquire()) {
            log.warn("[RateLimitFilter] 请求被限流: {}", limitKey);
            return rateLimitResponse(exchange.getResponse());
        }

        return chain.filter(exchange);
    }

    /**
     * 获取限流key（优先使用用户ID，其次是IP）
     */
    private String getLimitKey(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        
        // 优先使用用户ID（如果已经鉴权）
        String userId = request.getHeaders().getFirst(GatewayConstant.HEADER_USER_ID);
        if (userId != null && !userId.isEmpty()) {
            return "user:" + userId;
        }

        // 其次使用客户端IP
        String clientIp = getClientIp(request);
        return "ip:" + clientIp;
    }

    /**
     * 获取客户端IP
     */
    private String getClientIp(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getHeaders().getFirst("X-Real-IP");
        }
        if (ip == null || ip.isEmpty()) {
            if (request.getRemoteAddress() != null) {
                ip = request.getRemoteAddress().getAddress().getHostAddress();
            }
        }
        return ip != null ? ip : "unknown";
    }

    /**
     * 返回限流响应
     */
    private Mono<Void> rateLimitResponse(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Result<?> result = Result.rateLimit("请求过于频繁，请稍后再试");

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(result);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            byte[] bytes = "{\"code\":429,\"message\":\"请求过于频繁\"}".getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        }
    }

    @Override
    public int getOrder() {
        // 在鉴权过滤器之后执行
        return Ordered.HIGHEST_PRECEDENCE + 30;
    }

    /**
     * 令牌桶实现
     */
    private static class TokenBucket {
        /** 桶容量 */
        private final int capacity;
        /** 每秒产生的令牌数 */
        private final int tokensPerSecond;
        /** 当前令牌数 */
        private final AtomicInteger tokens;
        /** 上次更新时间 */
        private volatile long lastUpdateTime;

        TokenBucket(int capacity, int tokensPerSecond) {
            this.capacity = capacity;
            this.tokensPerSecond = tokensPerSecond;
            this.tokens = new AtomicInteger(capacity);
            this.lastUpdateTime = System.currentTimeMillis();
        }

        /**
         * 尝试获取一个令牌
         */
        synchronized boolean tryAcquire() {
            refillTokens();
            int currentTokens = tokens.get();
            if (currentTokens > 0) {
                tokens.decrementAndGet();
                return true;
            }
            return false;
        }

        /**
         * 补充令牌
         */
        private void refillTokens() {
            long now = System.currentTimeMillis();
            long elapsed = now - lastUpdateTime;
            
            if (elapsed > 0) {
                // 计算需要补充的令牌数
                int tokensToAdd = (int) (elapsed * tokensPerSecond / 1000);
                if (tokensToAdd > 0) {
                    int newTokens = Math.min(tokens.get() + tokensToAdd, capacity);
                    tokens.set(newTokens);
                    lastUpdateTime = now;
                }
            }
        }
    }
}
