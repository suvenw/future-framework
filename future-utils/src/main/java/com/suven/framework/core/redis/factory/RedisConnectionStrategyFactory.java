package com.suven.framework.core.redis.factory;


import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionStrategyFactory extends AbstractRedisConnection {



    public RedisConnectionStrategyFactory(RedisConfigurationSetting redisConfig) {
       super(redisConfig);
    }

    /**
     * 根据配置的 Redis 连接模式创建对应的连接工厂
     * 通过枚举 {@link RedisConnectionEnum} 来确定使用哪种连接策略
     * 
     * @return RedisConnectionFactory Redis 连接工厂实例
     */
    @Override
    public RedisConnectionFactory createConnectionFactory() {
        // 从配置中获取 Redis 连接模式（standalone/sentinel/masterSlave/cluster/clusterGroup）
        String model = getRedisConfig().getClient().getModel();
        RedisConnectionEnum redisEnum = RedisConnectionEnum.getEnum(model);
        
        // 根据枚举类型创建对应的连接策略实例
        RedisConnectionStrategy strategy = createStrategyByEnum(redisEnum);
        
        // 创建并返回连接工厂
        return strategy.createConnectionFactory();
    }

    /**
     * 根据 Redis 连接枚举创建对应的连接策略实例
     * 
     * @param redisEnum Redis 连接枚举类型，如果为 null 则使用默认的单机模式
     * @return RedisConnectionStrategy Redis 连接策略实例
     */
    private RedisConnectionStrategy createStrategyByEnum(RedisConnectionEnum redisEnum) {
        if (redisEnum == null) {
            // 如果枚举为 null，使用默认的单机模式
            return new RedisConnectionInStandalone(redisConfig);
        }

        // 使用 switch 语句根据枚举类型创建对应的策略实例
        switch (redisEnum) {
            case REDIS_STANDALONE_STRATEGY:
                return new RedisConnectionInStandalone(redisConfig);
                
            case REDIS_SENTINEL_STRATEGY:
                return new RedisConnectionInSentinel(redisConfig);
                
            case REDIS_MASTER_SLAVE_STRATEGY:
                return new RedisConnectionInMasterSlave(redisConfig);
                
            case REDIS_CLUSTER_STRATEGY:
            case REDIS_CLUSTER_GROUP_STRATEGY:
                // 集群模式和集群组模式都使用相同的集群连接策略
                return new RedisConnectionInCluster(redisConfig);
                
            default:
                // 未知模式，默认使用单机模式
                return new RedisConnectionInStandalone(redisConfig);
        }
    }


}