# Future Gateway 网关服务

基于 Spring Cloud Gateway 构建的统一 API 网关服务。

## 功能特性

### 1. 路由转发
- 基于 Nacos 服务发现的自动路由
- 支持配置自定义路由规则
- 支持路径重写、负载均衡

### 2. 安全认证
- JWT Token 鉴权（可选，通过配置开启）
- 白名单路径支持
- 用户信息透传到下游服务

### 3. 访问日志
- 请求日志记录（URL、方法、IP、User-Agent）
- 响应耗时统计
- 慢请求告警（超过1秒）

### 4. 限流控制
- 基于令牌桶算法的限流
- 按用户ID或IP限流
- 支持配置开启/关闭

### 5. 异常处理
- 全局异常捕获
- 统一的错误响应格式
- 详细的错误日志

### 6. 响应处理
- 统一的响应头添加
- 安全响应头设置

## 过滤器执行顺序

| 顺序 | 过滤器 | 说明 |
|------|--------|------|
| 1 | AccessLogFilter | 记录访问日志，设置请求开始时间 |
| 2 | WhiteListFilter | 检查白名单，标记免鉴权请求 |
| 3 | AuthFilter | JWT 鉴权（可选） |
| 4 | RateLimitFilter | 限流控制（可选） |
| 5 | ResponseHeaderFilter | 添加响应头 |

## 配置说明

### application.yml

```yaml
# Gateway 自定义配置
gateway:
  config:
    # 是否开启 JWT 鉴权
    auth-enabled: false
    
    # JWT 密钥（生产环境请使用复杂密钥，长度不小于32位）
    jwt-secret: future-gateway-secret-key-2024
    
    # JWT 过期时间（秒），默认7天
    jwt-expiration: 604800
    
    # 白名单路径（支持 Ant 路径模式）
    white-list-paths:
      - /api/sys/user/login
      - /api/sys/user/register
      - /api/upload/public/**
    
    # 是否开启请求日志
    log-enabled: true
    
    # 是否开启响应日志
    response-log-enabled: false
    
    # 是否开启限流
    rate-limit-enabled: false
    
    # 限流桶容量
    rate-limit-capacity: 100
    
    # 限流速率（每秒令牌数）
    rate-limit-tokens: 50
```

## 使用示例

### 1. 启动服务

```bash
./gradlew :future-gateway:bootRun
```

### 2. 配置路由

在 `application.yml` 中添加路由配置：

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: my-service
          uri: lb://my-service-name
          predicates:
            - Path=/api/my/**
          filters:
            - StripPrefix=0
```

### 3. JWT 鉴权使用

开启鉴权后，请求需要在 Header 中携带 Token：

```
Authorization: Bearer <your-jwt-token>
```

Token 解析后，用户信息会通过 Header 透传到下游服务：
- `X-User-Id`: 用户ID
- `X-User-Name`: 用户名

## 目录结构

```
future-gateway/
├── src/main/java/com/suven/framework/gateway/
│   ├── GatewayApplication.java          # 启动类
│   ├── common/                          # 公共常量、配置
│   │   ├── GatewayConstant.java         # 常量定义
│   │   └── GatewayProperties.java       # 配置属性
│   ├── config/                          # 配置类
│   │   └── GatewayConfig.java           # 网关配置
│   ├── filter/                          # 过滤器
│   │   ├── AccessLogFilter.java         # 访问日志过滤器
│   │   ├── AuthFilter.java              # 鉴权过滤器
│   │   ├── RateLimitFilter.java         # 限流过滤器
│   │   ├── ResponseHeaderFilter.java    # 响应头过滤器
│   │   └── WhiteListFilter.java         # 白名单过滤器
│   ├── exception/                       # 异常处理
│   │   ├── GatewayException.java        # 业务异常
│   │   └── GlobalExceptionHandler.java  # 全局异常处理器
│   ├── result/                          # 响应结果
│   │   ├── Result.java                  # 统一响应封装
│   │   └── ResultCode.java              # 状态码枚举
│   └── util/                            # 工具类
│       └── JwtUtil.java                 # JWT 工具类
└── src/main/resources/
    ├── application.yml                  # 应用配置
    └── bootstrap.yml                    # 启动配置
```

## 扩展开发

### 自定义过滤器

实现 `GlobalFilter` 和 `Ordered` 接口：

```java
@Component
public class MyCustomFilter implements GlobalFilter, Ordered {
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 前置处理
        
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // 后置处理
        }));
    }
    
    @Override
    public int getOrder() {
        // 定义执行顺序
        return Ordered.HIGHEST_PRECEDENCE + 50;
    }
}
```

## 注意事项

1. **密钥安全**：生产环境请使用复杂的 JWT 密钥，并妥善保管
2. **限流配置**：根据实际业务量调整限流参数
3. **白名单**：合理配置白名单，避免影响正常业务流程
4. **日志级别**：生产环境建议将日志级别调整为 WARN 或 ERROR
