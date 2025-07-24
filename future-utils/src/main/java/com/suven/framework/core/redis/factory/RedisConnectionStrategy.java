package com.suven.framework.core.redis.factory;


import com.suven.framework.core.redis.RedisModelType;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 使用策略模式可以实现统一全量配置，并支持Redis的单机模式、哨兵模式、集群模式和主从模式。下面是一个示例代码，展示了如何使用策略模式实现统一配置，并根据需要选择合适的Redis连接方式。
 *
 * 首先，定义一个接口 RedisConnectionStrategy，表示Redis连接策略，并声明一个方法 createConnectionFactory 用于创建 RedisConnectionFactory 实例：
 */
public interface RedisConnectionStrategy {
    RedisConnectionFactory createConnectionFactory();

    RedisModelType getRedisModelType();
}