package com.suven.framework.gateway.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suven.framework.gateway.common.GatewayConstant;
import com.suven.framework.gateway.result.Result;
import com.suven.framework.gateway.result.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 全局异常处理器
 */
@Component
@Order(-1) // 确保优先级高于默认处理器
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        
        // 设置响应内容类型
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        
        Result<?> result;
        HttpStatus httpStatus;

        if (ex instanceof GatewayException) {
            GatewayException gatewayEx = (GatewayException) ex;
            result = Result.error(gatewayEx.getCode(), gatewayEx.getMessage());
            httpStatus = HttpStatus.valueOf(gatewayEx.getCode());
            log.warn("[GatewayException] {} - {}", gatewayEx.getCode(), gatewayEx.getMessage());
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException statusEx = (ResponseStatusException) ex;
            result = Result.error(statusEx.getStatusCode().value(), statusEx.getReason());
            httpStatus = HttpStatus.valueOf(statusEx.getStatusCode().value());
            log.warn("[ResponseStatusException] {} - {}", statusEx.getStatusCode(), statusEx.getReason());
        } else {
            result = Result.error(ResultCode.INTERNAL_ERROR.getCode(), 
                    "服务器内部错误: " + ex.getMessage());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("[系统异常]", ex);
        }

        // 设置状态码
        response.setStatusCode(httpStatus);

        // 写入响应体
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(result);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            log.error("响应序列化失败", e);
            byte[] bytes = ("{\"code\":500,\"message\":\"系统错误\"}").getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        }
    }
}
