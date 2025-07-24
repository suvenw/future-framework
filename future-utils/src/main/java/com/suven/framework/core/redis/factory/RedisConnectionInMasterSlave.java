package com.suven.framework.core.redis.factory;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * 然后，实现具体的连接策略类，包括单机模式、哨兵模式、集群模式和主从模式。每个策略类都实现了 RedisConnectionStrategy 接口，并根据对应的连接方式创建 RedisConnectionFactory 实例：
 *
 * 4.主从模式策略类 MasterSlaveRedisConnectionStrategy：
 */
public class RedisConnectionInMasterSlave extends AbstractRedisConnection implements RedisConnectionStrategy {

    public RedisConnectionInMasterSlave(RedisConfigurationSetting redisConfig) {
        super(redisConfig);
    }

    @Override
    public RedisConnectionFactory createConnectionFactory() {
        RedisStaticMasterReplicaConfiguration config = new RedisStaticMasterReplicaConfiguration(redisConfig.getHost(),redisConfig.getPort());
        config.setPassword(RedisPassword.of(redisConfig.getPassword()));
        return new LettuceConnectionFactory(config);
    }
}