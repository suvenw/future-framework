package com.suven.framework.core.redis;

import com.suven.framework.core.redis.factory.RedisConfigurationSetting;
import org.springframework.data.redis.connection.RedisConnectionFactory;

public interface RedisFactoryRouter {


    /**
     * 获取 redis Client 端的相关配置文件的方法
     * @return RedisConfigurationSetting 配置文件
     */
    RedisConfigurationSetting getRedisConfig();


    RedisModelType modelType();

    RedisSwitchControl switcher();

    /**
     * 1.如果是单个聚群模式,按RedisSentinelInterface返回
     * 2.如果是多组聚群模式,即返回通过 redisCacheKey 做路由查找对应的分组;
     * 3.如果是单结点或哨兵模式,按RedisSentinelInterface返回
     *
     * @param redisCacheKey
     * @return
     */
    RedisConnectionFactory getRedisFactoryByKey(Object redisCacheKey);


    /** 执行种模式代理**/
    boolean execStrategy(RedisStrategyEnum redisStrategyEnum);


}
