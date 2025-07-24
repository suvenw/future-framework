package com.suven.framework.core.redis.client;

import com.suven.framework.common.cat.CatCacheKeySign;

import java.util.Map;

/**
 * @author suven.wang
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @description : (说明) 基于RedisTemplate api方法做增加自定义方法,提供业务方使用
 *
 */
public interface RedisPlusOperations {

    Long incrementBy(String key, long delta);

    <T>T getT(String key, Class<T> clazz);

    /*** 分布式锁实现
     *
     * @param lockKey 自定义锁参数,
     * @param uuid 唯一JVM标识
     * @param expireTime 过期时间,jvm 异常保护处理
     * @return
     */

    boolean getSet( String lockKey, String uuid, long expireTime);

    boolean getSet(String lockKey, String uuid, int seconds);

    /**
     *  释放分布式锁
     * @param lockKey 锁
     * @param uuid 请求标识
     * @return 是否释放成功
     */
     boolean evalLock( String lockKey, String uuid);


    boolean setex(String key, String value, int second );

    /**
     * 批量expire
     *
     * @param keysValues {key1:time1,key2:time2...}
     */
    Map<String, Object> refinedExpire(Map<String, Integer> keysValues);

    Map<String, String> getMapCacheAndDelExpire(String codeKey, long expire);

    <K, V> void multiSet(@CatCacheKeySign String prefix, Map<K, V> kvMap, int seconds);
}
