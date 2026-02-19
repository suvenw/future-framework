package com.suven.framework.gateway.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 一个简单的示例 WebFlux 控制器，用于验证网关服务是否启动成功。
 * 注意：网关主要通过路由转发请求，此控制器仅作为健康检查/调试入口。
 *
 * URL: /test/hello
 */
@RestController
@RequestMapping("test")
public class HelloWordController {

    @GetMapping("hello")
    public Mono<String> hello() {
        // WebFlux 风格：使用 Mono/Flux 作为返回类型
        return Mono.just("hello word from future-gateway (webflux)");
    }
}
