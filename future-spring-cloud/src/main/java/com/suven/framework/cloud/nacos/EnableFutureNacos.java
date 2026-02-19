package com.suven.framework.cloud.nacos;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.lang.annotation.*;

/**
 * 启用 Future Cloud Nacos 服务注册与发现
 * 
 * 使用示例：
 * <pre>
 * @EnableFutureNacos
 * @SpringBootApplication
 * public class MyApplication {
 *     public static void main(String[] args) {
 *         SpringApplication.run(MyApplication.class, args);
 *     }
 * }
 * </pre>
 * 
 * 注意：此注解等同于 @EnableDiscoveryClient，用于启用 Spring Cloud 服务发现
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableDiscoveryClient
public @interface EnableFutureNacos {
}
