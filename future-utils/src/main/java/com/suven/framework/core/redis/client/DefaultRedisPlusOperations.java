package com.suven.framework.core.redis.client;

import com.suven.framework.common.cat.CatCacheKeySign;
import com.suven.framework.common.cat.CatCacheSign;
import com.suven.framework.common.constants.CatTopConstants;
import com.suven.framework.core.TypeSerializer;
import com.suven.framework.core.redis.RedisShortKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Joven.wang
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @description : (说明) 基于RedisTemplate api方法做增加自定义方法的具体功能实现 ,提供业务方使用
 *
 */
public class DefaultRedisPlusOperations implements RedisPlusOperations {

    Logger log = LoggerFactory.getLogger(getClass());

    protected RedisTemplate<String ,String> redis;
    public RedisTemplate<String ,String> getRedis(){
        return redis;
    }
    public DefaultRedisPlusOperations(RedisTemplate<String ,String> redisTemplate) {
        this.redis = redisTemplate;
    }

    public Long incrementBy(String key, long delta){
       return redis.opsForValue().increment(key,delta);
    }

    protected boolean isOK(String result){
        return "OK".equals(result);
    }
    protected boolean isOK(Long result){
        if (null == result || result == 0L){
            return false;
        }
        return true;
    }

    public <T>T getT(String key, Class<T> clazz) {
        String value = this.getRedis().opsForValue().get(key);
        if (value != null) {
           T data =  TypeSerializer.parseObject(clazz,value);
            return data;
        }
        return null;
    }

    /*** 分布式锁实现
     *
     * @param lockKey 自定义锁参数,
     * @param uuid 唯一JVM标识
     * @param expireTime 过期时间,jvm 异常保护处理
     * @return
     */
    @Override
    public boolean getSet(String lockKey, String uuid, long expireTime) {
        return false;
    }

    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param uuid 请求标识uuid
     * @param seconds expireTime 超期时间（秒）
     * @return 是否获取成功
     */
    @Override
    public boolean getSet(String lockKey, String uuid, int seconds) {
        Duration duration = Duration.ofSeconds(seconds);
        Boolean result =  this.redis.opsForValue().setIfPresent(lockKey, uuid, duration);
        return result;
    }


    /**
     *  释放分布式锁
     * @param lockKey 锁
     * @param uuid 请求标识
     * @return 是否释放成功
     */
    @Override
    public boolean evalLock( String lockKey, String uuid) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = redis.execute(redisScript,  Collections.singletonList(lockKey), Collections.singletonList(uuid));
        return isOK(result);

    }



    @Override
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public boolean setex(@CatCacheKeySign String key, String value, int second){
        if (null == key ) {
            return false;
        }
        try {
             this.redis.opsForValue().set(key,value,second,  TimeUnit.SECONDS);
            return true;
        }catch ( Exception e){
            log.warn(" Redis Cluster Server by getSet Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
        return false;
    }

    /**
     * 批量expire
     *
     * @param keysValues
     */
    @Override
    public Map<String, Object> refinedExpire(Map<String, Integer> keysValues) {
        return null;
    }

    /**
     * @param mapPrefixKey map 的前比缀 key
     * @param expireMsTime 过期时间
     * @return Map<String, String> 集合
     */
    @Override
    public Map<String, String> getMapCacheAndDelExpire(String mapPrefixKey, long expireMsTime) {
        if (null == mapPrefixKey) {
            log.warn("Cached model findMapCacheByKV Object mapPrefixKey is null ");
            return null;
        }
        try {
            Map<Object, Object> map = this.getRedis().opsForHash().entries(mapPrefixKey);
            if (Objects.isNull(map) || map.isEmpty()) {
                return null;
            }
            List<String> list = new ArrayList<>();
            Map<String , String> retutnMap  = new LinkedHashMap<>();
            map.forEach((k,v) ->{
                    String key = TypeSerializer.parseString(k);
                    String value = TypeSerializer.parseString(v);
                    Long number = TypeSerializer.parseObject(Long.class, value);
                    if (null != key && null != v && (number - expireMsTime) > 0) {
                        retutnMap.put(key,value);
                        return ;//未过期的
                    }
                    list.add(key);
            });
            if(list.size() > 0){
                this.getRedis().opsForHash().delete(mapPrefixKey,list.toArray());
            }
            return retutnMap;
        } catch (Exception e) {
            log.warn(" Redis Cluster Server by getMapCacheAndDelExpire Modifier key:[{}] , Exception: [{}] ",mapPrefixKey, e);
            return null;
        }
    }

    /**
     //     * 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，
     //     * 如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。
     //     * @param prefix  自定义的前缀key
     //     * @param kvMap   原始数据map集合
     //     * 备注:只支持集群模式
     //     */
    public <K, V> void multiSet(@CatCacheKeySign String prefix, Map<K, V> kvMap, int seconds) {
        if (null == prefix ) {
            return;
        }
        Map<String, String> map = new HashMap();
        kvMap.entrySet().stream().forEach(kv -> {
            String  key = RedisShortKeyUtil.formatKey(prefix,TypeSerializer.parseString(kv.getKey()));
            String value = TypeSerializer.parseString(kv.getValue());
            map.put(key, value);
        });
        this.redis.opsForValue().multiSet(map);

    }

}