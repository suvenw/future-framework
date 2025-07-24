package com.suven.framework.core.redis.factory;


import com.suven.framework.core.redis.RedisModelType;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionStrategyFactory extends AbstractRedisConnection implements RedisConnectionStrategy{



    public RedisConnectionStrategyFactory(RedisConfigurationSetting redisConfig) {
       super(redisConfig);
    }

    /**
     *
     * @return
     */
    @Override
    public RedisConnectionFactory createConnectionFactory() {
//        RedisConnectionEnum redisEnum = RedisConnectionEnum.getEnum(getRedisConfig().getClient().getModel());
        RedisConnectionStrategy strategy = null;
        RedisModelType redisModelType = getRedisModelType();
        if (redisModelType.isStandalone()){
            strategy = new RedisConnectionInStandalone(redisConfig);
        }if (redisModelType.isMasterSlave()){
            strategy = new RedisConnectionInMasterSlave(redisConfig);
        }if (redisModelType.isSentinel()){
            strategy = new RedisConnectionInSentinel(redisConfig);
        }if (redisModelType.isCluster()){
            strategy = new RedisConnectionInCluster(redisConfig);
        }if (redisModelType.isClusterGroup()){
            strategy = new RedisConnectionInCluster(redisConfig);
        }
        if (strategy == null){
            strategy = new RedisConnectionInStandalone(redisConfig);
        }
        RedisConnectionFactory factory = strategy.createConnectionFactory();
        return factory;

//        switch (redisEnum){
//            case REDIS_STANDALONE_STRATEGY:{
//                strategy = new RedisConnectionInStandalone(redisConfig);
//                break;
//            }
//            case REDIS_SENTINEL_STRATEGY:{
//                strategy = new RedisConnectionInSentinel(redisConfig);
//                break;
//            }
//            case REDIS_MASTER_SLAVE_STRATEGY:{
//                strategy = new RedisConnectionInMasterSlave(redisConfig);
//                break;
//            }
//            case REDIS_CLUSTER_STRATEGY:
//            case REDIS_CLUSTER_GROUP_STRATEGY:{
//                strategy = new RedisConnectionInCluster(redisConfig);
//                break;
//            } default:{
//                strategy = new RedisConnectionInStandalone(redisConfig);
//            }
//        }
//        RedisConnectionFactory factory = strategy.createConnectionFactory();
//        return factory;
    }


}