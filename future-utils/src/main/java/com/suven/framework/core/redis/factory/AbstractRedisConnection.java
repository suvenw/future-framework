package com.suven.framework.core.redis.factory;

import com.suven.framework.core.redis.RedisClientModelType;
import com.suven.framework.core.redis.RedisModelType;
import lombok.Getter;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *
 * <pre>
 * @ description (说明): redis 多模式实现策略实现抽像类实现,通过构造器注册配置文件,提供获取配置文件的方法
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @date 创建时间: 2023-12-29
 **/
@Getter
public  abstract class AbstractRedisConnection  implements RedisConnectionStrategy {

    protected RedisConfigurationSetting redisConfig;
    protected RedisModelType redisModelType;

    public AbstractRedisConnection(RedisConfigurationSetting redisConfig) {
       this.redisConfig = redisConfig;
       this.redisModelType = RedisClientModelType.build(redisConfig);
    }

    @Override
    public RedisModelType getRedisModelType() {
        return redisModelType;
    }
}
