package com.suven.framework.core.redis.factory;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * Redis 集群模式连接策略实现类
 * 使用 LettuceConnectionFactory 创建 Redis 集群连接
 * 
 * 支持的功能：
 * 1. Redis 集群节点配置
 * 2. 密码认证
 * 3. 最大重定向次数配置
 * 4. 命令超时时间配置
 * 
 * 注意：Lettuce 连接池配置通过 spring.data.redis.lettuce.pool 在配置文件中配置，
 * Spring Boot 会自动应用这些配置。
 * 
 * @author suven
 * @date 2024-01-01
 */
public class RedisConnectionInCluster extends AbstractRedisConnection {

    private final List<String> nodes;
    private final Integer maxRedirects;

    public RedisConnectionInCluster(RedisConfigurationSetting redisConfig) {
        super(redisConfig);
        nodes = redisConfig.getCluster().getNodes();
        Integer max = redisConfig.getCluster().getMaxRedirects();
        maxRedirects = max == null ? 3 : max; // 默认最大重定向次数为 3
    }

    /**
     * 创建 Redis 集群连接工厂
     * 
     * @return RedisConnectionFactory Redis 连接工厂实例
     */
    @Override
    public RedisConnectionFactory createConnectionFactory() {
        // 1. 创建集群配置
        RedisClusterConfiguration clusterConfig = createClusterConfiguration();
        
        // 2. 创建 Lettuce 客户端配置
        LettuceClientConfiguration clientConfig = createLettuceClientConfiguration();
        
        // 3. 创建并返回 LettuceConnectionFactory
        LettuceConnectionFactory factory = new LettuceConnectionFactory(clusterConfig, clientConfig);
        factory.afterPropertiesSet();
        return factory;
    }

    /**
     * 创建 Redis 集群配置
     * 
     * @return RedisClusterConfiguration 集群配置对象
     */
    private RedisClusterConfiguration createClusterConfiguration() {
        RedisClusterConfiguration config = new RedisClusterConfiguration(nodes);
        
        // 设置密码（如果配置了）
        if (Objects.nonNull(redisConfig.getPassword())) {
            config.setPassword(RedisPassword.of(redisConfig.getPassword()));
        }
        
        // 设置最大重定向次数（集群模式下，当 key 不在当前节点时，需要重定向到正确的节点）
        config.setMaxRedirects(maxRedirects);
        
        return config;
    }

    /**
     * 创建 Lettuce 客户端配置
     * Spring Boot 会自动处理连接池配置（如果配置了 spring.data.redis.lettuce.pool）
     * 
     * @return LettuceClientConfiguration Lettuce 客户端配置
     */
    private LettuceClientConfiguration createLettuceClientConfiguration() {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = 
            LettuceClientConfiguration.builder();
        
        // 设置命令超时时间
        Duration timeout = getCommandTimeout();
        builder.commandTimeout(timeout);
        
        return builder.build();
    }

    /**
     * 获取命令超时时间
     * 
     * @return Duration 超时时间
     */
    private Duration getCommandTimeout() {
        if (redisConfig.getTimeout() != null) {
            return Duration.ofMillis(redisConfig.getTimeout().toMillis());
        }
        return Duration.ofSeconds(5); // 默认 5 秒
    }
}