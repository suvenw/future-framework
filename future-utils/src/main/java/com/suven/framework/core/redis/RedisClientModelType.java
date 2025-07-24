package com.suven.framework.core.redis;

import com.suven.framework.core.redis.factory.RedisConfigurationSetting;
import com.suven.framework.core.redis.factory.RedisConnectionEnum;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *d ate 创建时间: 2023-12-29
 * <pre>
 * description (说明): 实现支持model 为 ,standalone,sentinel,masterSlave，cluster，group 默认为 cluster;
 *  *         model: cluster
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 *
 * # model 为 ,standalone,sentinel,masterSlave，cluster，group 默认为 cluster;
 *         model: cluster
 **/

public class RedisClientModelType implements RedisModelType{
    private RedisConfigurationSetting redisConfig;
   public RedisClientModelType(RedisConfigurationSetting redisConfig){
        this.redisConfig = redisConfig;
    }

    public static RedisClientModelType build(RedisConfigurationSetting redisConfig){
       return new RedisClientModelType(redisConfig);
    }

    public RedisConfigurationSetting getRedisConfig(){
       return redisConfig;
    }
    /**
     * 单结点模式
     ***/
    @Override
    public boolean isStandalone() {
        String configModel = redisConfig.getClient().getModel();
        boolean standalone =  RedisConnectionEnum.REDIS_STANDALONE_STRATEGY.equalsModelType(configModel);
        return standalone;
    }

    /**
     * 主从节点模式
     ***/
    @Override
    public boolean isMasterSlave() {
        String configModel = getRedisConfig().getClient().getModel();
        boolean masterSlave =  RedisConnectionEnum.REDIS_MASTER_SLAVE_STRATEGY.equalsModelType(configModel);
        return masterSlave;
    }

    /**
     * 目前只支持两种模式,默认是集群模式,
     * 不是聚群模式,就是单结点模式;
     * 暂时不做哨兵模式逻辑,
     * 哨兵模式使用单结点模式逻辑
     ***/
    @Override
    public boolean isSentinel() {
        String configModel = getRedisConfig().getClient().getModel();
        boolean sentinel =  RedisConnectionEnum.REDIS_SENTINEL_STRATEGY.equalsModelType(configModel);
        return sentinel;
    }

    /**
     * 目前只支持两种模式,默认是集群模式,
     * 不是聚群模式,就是单结点模式;
     * 暂时不做哨兵模式逻辑,
     * 哨兵模式使用单结点模式逻辑
     ***/
    @Override
    public boolean isCluster() {
        String configModel = getRedisConfig().getClient().getModel();
        boolean cluster =  RedisConnectionEnum.REDIS_CLUSTER_STRATEGY.equalsModelType(configModel);
        return cluster;
    }

    /**
     * 目前只支持两种模式,默认是集群模式,
     * 不是聚群模式,就是单结点模式;
     * 暂时不做哨兵模式逻辑,
     * 哨兵模式使用单结点模式逻辑
     ***/
    @Override
    public boolean isClusterGroup() {
        String configModel = getRedisConfig().getClient().getModel();
        boolean cluster =  RedisConnectionEnum.REDIS_CLUSTER_GROUP_STRATEGY.equalsModelType(configModel);
        return cluster;
    }
}
