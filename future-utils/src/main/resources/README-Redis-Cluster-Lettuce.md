# Redis 集群模式 LettuceConnectionFactory 配置指南

## 概述

本文档说明如何配置 Redis 集群模式，使用 `LettuceConnectionFactory` 作为连接工厂。

## 配置文件示例

### application.yml / application.properties

```yaml
spring:
  data:
    redis:
      # Redis 基础配置
      enabled: true
      password: 123456
      timeout: 1000ms
      
      # Lettuce 连接池配置（可选，如果不配置则使用共享连接模式）
      lettuce:
        pool:
          max-active: 100      # 最大连接数
          max-idle: 10         # 最大空闲连接数
          min-idle: 0          # 最小空闲连接数
          max-wait: -1ms        # 最大等待时间，-1 表示无限等待
      
      # Redis 集群配置
      cluster:
        enabled: true
        timeout: 10000         # 集群连接超时时间（毫秒）
        max-redirects: 3       # 最大重定向次数（默认 3）
        nodes:                 # 集群节点列表
          - 192.168.3.100:7001
          - 192.168.3.100:7002
          - 192.168.3.100:7003
          - 192.168.3.100:7004
          - 192.168.3.100:7005
          - 192.168.3.100:7006
```

### 使用分号分隔的节点列表（兼容旧配置）

```yaml
spring:
  data:
    redis:
      cluster:
        enabled: true
        timeout: 10000
        max-redirects: 3
        # 使用分号分隔的节点列表（会自动解析为 List）
        servers: 192.168.3.100:7001;192.168.3.100:7002;192.168.3.100:7003;192.168.3.100:7004;192.168.3.100:7005;192.168.3.100:7006
```

### 完整配置示例（包含客户端配置）

```yaml
spring:
  data:
    redis:
      enabled: true
      password: 123456
      timeout: 1000ms
      
      # Lettuce 连接池配置
      lettuce:
        pool:
          max-active: 100
          max-idle: 10
          min-idle: 0
          max-wait: -1ms
      
      # Redis 集群配置
      cluster:
        enabled: true
        timeout: 10000
        max-redirects: 3
        nodes:
          - 192.168.3.100:7001
          - 192.168.3.100:7002
          - 192.168.3.100:7003
          - 192.168.3.100:7004
          - 192.168.3.100:7005
          - 192.168.3.100:7006
      
      # 客户端配置（可选）
      client:
        model: cluster          # 连接模式：cluster/sentinel/standalone/masterSlave
        daoOpen: true           # 是否启用 DAO
        read: true              # 是否允许读操作
        write: true             # 是否允许写操作
```

## 配置说明

### 1. 集群节点配置

**方式一：使用 nodes 列表（推荐）**
```yaml
cluster:
  nodes:
    - 192.168.3.100:7001
    - 192.168.3.100:7002
    - 192.168.3.100:7003
```

**方式二：使用 servers 字符串（兼容旧配置）**
```yaml
cluster:
  servers: 192.168.3.100:7001;192.168.3.100:7002;192.168.3.100:7003
```

### 2. 连接池配置

Lettuce 支持两种连接模式：

**共享连接模式（默认）**
- 不配置 `lettuce.pool` 时使用
- 所有操作共享同一个连接
- 适合低并发场景

**连接池模式**
- 配置 `lettuce.pool` 后启用
- 使用连接池管理连接
- 适合高并发场景

```yaml
lettuce:
  pool:
    max-active: 100    # 最大连接数
    max-idle: 10       # 最大空闲连接数
    min-idle: 0        # 最小空闲连接数
    max-wait: -1ms      # 最大等待时间
```

### 3. 集群特定配置

```yaml
cluster:
  enabled: true         # 是否启用集群模式
  timeout: 10000       # 连接超时时间（毫秒）
  max-redirects: 3     # 最大重定向次数
```

**max-redirects 说明**：
- 在 Redis 集群模式下，当 key 不在当前节点时，需要重定向到正确的节点
- 默认值为 3，表示最多重定向 3 次
- 如果重定向次数超过此值，会抛出异常

### 4. 密码配置

```yaml
spring:
  data:
    redis:
      password: your_password_here
```

## 代码实现

集群连接由 `RedisConnectionInCluster` 类实现：

```java
public class RedisConnectionInCluster extends AbstractRedisConnection {
    
    @Override
    public RedisConnectionFactory createConnectionFactory() {
        // 1. 创建集群配置
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration(nodes);
        clusterConfig.setPassword(RedisPassword.of(password));
        clusterConfig.setMaxRedirects(maxRedirects);
        
        // 2. 创建 Lettuce 客户端配置
        LettuceClientConfiguration clientConfig = 
            LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(5))
                .build();
        
        // 3. 创建连接工厂
        return new LettuceConnectionFactory(clusterConfig, clientConfig);
    }
}
```

## 使用示例

### 1. 在代码中使用 RedisTemplate

```java
@Autowired
private RedisTemplate<String, Object> redisTemplate;

public void testCluster() {
    // 设置值
    redisTemplate.opsForValue().set("key1", "value1");
    
    // 获取值
    String value = (String) redisTemplate.opsForValue().get("key1");
    
    // 集群模式下，key 会自动路由到正确的节点
    redisTemplate.opsForHash().put("hash:key", "field", "value");
}
```

### 2. 验证集群连接

```java
@Autowired
private RedisConnectionFactory connectionFactory;

public void verifyCluster() {
    RedisConnection connection = connectionFactory.getConnection();
    try {
        Properties info = connection.info("cluster");
        System.out.println("Cluster info: " + info);
    } finally {
        connection.close();
    }
}
```

## 常见问题

### 1. 连接超时

**问题**：连接 Redis 集群时出现超时错误

**解决方案**：
- 检查防火墙设置
- 增加超时时间配置
- 检查网络连通性

```yaml
spring:
  data:
    redis:
      timeout: 5000ms
      cluster:
        timeout: 10000
```

### 2. 重定向异常

**问题**：`Too many redirections` 错误

**解决方案**：
- 增加 `max-redirects` 配置
- 检查集群节点是否全部在线
- 验证集群配置是否正确

```yaml
cluster:
  max-redirects: 5
```

### 3. 密码认证失败

**问题**：`NOAUTH Authentication required` 错误

**解决方案**：
- 确保配置了正确的密码
- 检查所有集群节点是否使用相同的密码

```yaml
spring:
  data:
    redis:
      password: your_password
```

## 性能优化建议

1. **连接池配置**
   - 根据并发量调整 `max-active`
   - 设置合适的 `min-idle` 保持连接预热
   - 避免 `max-wait` 过大导致线程阻塞

2. **超时配置**
   - 根据网络延迟设置合理的超时时间
   - 集群模式下建议超时时间 >= 5000ms

3. **节点配置**
   - 至少配置 3 个节点（Redis 集群最少需要 3 个主节点）
   - 建议配置所有主节点，提高可用性

## 参考资源

- [Spring Data Redis 文档](https://spring.io/projects/spring-data-redis)
- [Lettuce 文档](https://lettuce.io/)
- [Redis 集群模式文档](https://redis.io/docs/manual/scaling/)


