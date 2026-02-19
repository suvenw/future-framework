# Future Spring Cloud Alibaba Nacos Starter

微服务注册与配置中心 Starter 模块，统一封装 Spring Cloud Alibaba Nacos 相关依赖。

## 功能特性

- 统一 Spring Cloud Alibaba Nacos 版本管理
- 自动解决 protobuf 版本冲突（强制使用 3.25.5 兼容 Nacos 3.0.2）
- 集成 Nacos 服务注册与配置中心
- 集成 Dubbo RPC（可选）

## 使用方法

### 1. 添加依赖

在服务的 `build.gradle` 中添加：

```groovy
dependencies {
    // 其他依赖...
    implementation project(':future-spring-cloud')
}
```

### 2. 启用服务发现

在主类上添加 `@EnableDiscoveryClient` 注解：

```java
@EnableDiscoveryClient
@SpringBootApplication
public class MyApplication extends AbstractJettyAppServer {
    public static void main(String[] args) {
        jettyStart(MyApplication.class, args);
    }
}
```

### 3. 配置 Nacos

创建 `application-nacos.yml`：

```yaml
spring:
  cloud:
    nacos:
      discovery:
        enabled: true
        server-addr: ${NACOS_SERVER_ADDR:192.168.3.100:8848}
        namespace: ${NACOS_NAMESPACE:FUTURE-GATEWAY}
        group: ${NACOS_GROUP:DEFAULT_GROUP}
        prefer-ip-address: true
        heart-beat-interval: 5000
        heart-beat-timeout: 15000
        metadata:
          version: 1.0
          region: default
      config:
        enabled: true
        server-addr: ${NACOS_SERVER_ADDR:192.168.3.100:8848}
        namespace: ${NACOS_NAMESPACE:FUTURE-GATEWAY}
        group: ${NACOS_GROUP:DEFAULT_GROUP}
        file-extension: yml
        import-check:
          enabled: false
        shared-configs:
          - data-id: common.yml
            group: DEFAULT_GROUP
            refresh: true
```

### 4. 在主配置中引入

在 `application.yml` 中：

```yaml
spring:
  profiles:
    include: base,nacos,db,dubbo,redis,sms
```

## 版本说明

- Spring Boot: 3.1.5
- Spring Cloud: 2023.0.x
- Spring Cloud Alibaba: 2023.0.3.3
- Nacos Client: 3.0.2

## 注意事项

1. 由于 Nacos 3.0.2 使用 protobuf 3.x，本模块强制指定 protobuf-java 版本为 3.25.5
2. 使用 Jetty 作为 Web 容器时，已自动包含 jetty-jndi 依赖消除警告
3. 如需禁用 Nacos 注册，设置 `spring.cloud.nacos.discovery.enabled=false`
