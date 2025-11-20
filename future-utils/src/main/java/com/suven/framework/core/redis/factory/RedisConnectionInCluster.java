package com.suven.framework.core.redis.factory;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.List;
import java.util.Objects;

/**
 * 然后，实现具体的连接策略类，包括单机模式、哨兵模式、集群模式和主从模式。每个策略类都实现了 RedisConnectionStrategy 接口，并根据对应的连接方式创建 RedisConnectionFactory 实例：
 *
 * 3.集群模式策略类 RedisConnectionInCluster：
 */
public class RedisConnectionInCluster extends AbstractRedisConnection implements RedisConnectionStrategy {

    private final List<String> nodes;
    private final Integer maxRedirects;

    public RedisConnectionInCluster(RedisConfigurationSetting redisConfig) {
        super(redisConfig);
        nodes =  redisConfig.getCluster().getNodes();
        Integer max = redisConfig.getCluster().getMaxRedirects();
        maxRedirects =  max == null ? 0 : max;
    }

    @Override
    public RedisConnectionFactory createConnectionFactory() {
        RedisClusterConfiguration config = new RedisClusterConfiguration(nodes);
        if (Objects.nonNull(redisConfig.getPassword())){
            config.setPassword(RedisPassword.of(redisConfig.getPassword()));
        }
        config.setMaxRedirects(maxRedirects);
        return new LettuceConnectionFactory(config);
    }
}