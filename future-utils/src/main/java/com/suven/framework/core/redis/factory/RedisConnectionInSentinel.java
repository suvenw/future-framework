package com.suven.framework.core.redis.factory;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * 然后，实现具体的连接策略类，包括单机模式、哨兵模式、集群模式和主从模式。每个策略类都实现了 RedisConnectionStrategy 接口，并根据对应的连接方式创建 RedisConnectionFactory 实例：
 *
 * 2.哨兵模式策略类 SentinelRedisConnectionStrategy：
 */
public class RedisConnectionInSentinel  extends AbstractRedisConnection implements RedisConnectionStrategy {

    private final String master;
    private final Set<String> sentinels;

    public RedisConnectionInSentinel(RedisConfigurationSetting redisConfig) {
        super(redisConfig);
        master = redisConfig.getSentinel().getMaster();
        sentinels = new HashSet<>( redisConfig.getSentinel().getNodes());
    }

    @Override
    public RedisConnectionFactory createConnectionFactory() {
        RedisSentinelConfiguration config = new RedisSentinelConfiguration(
                master,
                sentinels);
        config.setPassword(RedisPassword.of(redisConfig.getSentinel().getPassword()));
        return new LettuceConnectionFactory(config);
    }
}