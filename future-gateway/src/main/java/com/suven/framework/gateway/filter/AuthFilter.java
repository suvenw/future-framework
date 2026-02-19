package com.suven.framework.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suven.framework.gateway.common.GatewayConstant;
import com.suven.framework.gateway.common.GatewayProperties;
import com.suven.framework.gateway.result.Result;
import com.suven.framework.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * JWT 鉴权过滤器
 * 验证请求的 JWT Token，并将用户信息传递到下游服务
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private GatewayProperties gatewayProperties;

    @Autowired
    private JwtUtil jwtUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 如果未开启鉴权，直接放行
        if (!gatewayProperties.isAuthEnabled()) {
            return chain.filter(exchange);
        }

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getRawPath();

        // 检查是否是白名单路径
        Boolean isWhiteList = exchange.getAttribute("isWhiteList");
        if (Boolean.TRUE.equals(isWhiteList)) {
            return chain.filter(exchange);
        }

        // 获取 Token
        String token = extractToken(request);
        
        if (!StringUtils.hasText(token)) {
            log.warn("[AuthFilter] 请求 {} 未携带 Token", path);
            return unauthorized(exchange.getResponse(), "未授权，请先登录");
        }

        try {
            // 验证并解析 Token
            Claims claims = jwtUtil.parseToken(token);
            String userId = claims.get("userId", String.class);
            String username = claims.get("username", String.class);

            // 将用户信息添加到请求头，传递给下游服务
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header(GatewayConstant.HEADER_USER_ID, userId)
                    .header(GatewayConstant.HEADER_USER_NAME, username)
                    .header(GatewayConstant.HEADER_GATEWAY_FROM, "future-gateway")
                    .build();

            log.debug("[AuthFilter] 用户 {}({}) 访问 {}", username, userId, path);

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (ExpiredJwtException e) {
            log.warn("[AuthFilter] Token 已过期: {}", e.getMessage());
            return unauthorized(exchange.getResponse(), "登录已过期，请重新登录");
        } catch (Exception e) {
            log.warn("[AuthFilter] Token 验证失败: {}", e.getMessage());
            return unauthorized(exchange.getResponse(), "Token 无效");
        }
    }

    /**
     * 从请求中提取 Token
     */
    private String extractToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(GatewayConstant.HEADER_AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(GatewayConstant.TOKEN_PREFIX)) {
            return bearerToken.substring(GatewayConstant.TOKEN_PREFIX.length());
        }
        return null;
    }

    /**
     * 返回未授权响应
     */
    private Mono<Void> unauthorized(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        
        Result<?> result = Result.unauthorized(message);
        
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(result);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            byte[] bytes = ("{\"code\":401,\"message\":\"" + message + "\"}").getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        }
    }

    @Override
    public int getOrder() {
        // 在白名单过滤器之后执行
        return Ordered.HIGHEST_PRECEDENCE + 20;
    }
}
