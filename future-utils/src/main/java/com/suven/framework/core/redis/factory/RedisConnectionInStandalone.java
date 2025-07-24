package com.suven.framework.core.redis.factory;


import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.Objects;

/**
 * 然后，实现具体的连接策略类，包括单机模式、哨兵模式、集群模式和主从模式。每个策略类都实现了 RedisConnectionStrategy 接口，并根据对应的连接方式创建 RedisConnectionFactory 实例：
 *
 * 1.单机模式策略类 StandaloneRedisConnectionStrategy：
 */
public class RedisConnectionInStandalone extends AbstractRedisConnection implements RedisConnectionStrategy {



    public RedisConnectionInStandalone(RedisConfigurationSetting redisConfig) {
        super(redisConfig);
    }


    @Override
    public RedisConnectionFactory createConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisConfig.getHost(),redisConfig.getPort());
//        config.setHostName(redisConfig.getHost());
//        config.setPort(redisConfig.getPort());
        if (Objects.nonNull(redisConfig.getUsername())){
            config.setUsername(redisConfig.getUsername());
        }

        config.setDatabase(redisConfig.getDatabase());
        if (Objects.nonNull(redisConfig.getPassword())){
            config.setPassword(RedisPassword.of(redisConfig.getPassword()));
        }
        return new JedisConnectionFactory(config);
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName(redisConfig.getHost());
//        jedisConnectionFactory.setPort(redisConfig.getPort());
//        return jedisConnectionFactory;
//
    }
}