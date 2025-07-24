package com.suven.framework.core.redis;

import com.suven.framework.common.cat.CatCacheKeySign;
import com.suven.framework.core.redis.client.SpringRedisClient;
import com.suven.framework.core.redis.ext.ExpansionDeal;
import com.suven.framework.core.redis.factory.RedisConfigurationSetting;
import com.suven.framework.core.redis.factory.RedisConnectionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.resps.ScanResult;
import redis.clients.jedis.resps.Tuple;

import java.time.Duration;
import java.time.Instant;
import java.util.*;


/**
 * @author Joven.wang
 * @version V1.0
 * @date 2016年7月27日
 * Description: TODO(说明)
 */
//@ConditionalOnMissingBean(name = "redisConnection")
//@ConditionalOnClass({RedisConnectionStrategy.class,RedisConfigurationSetting.class})
@Component
public class RedisClientServerProxy extends AbstractRedisFactoryRouter implements RedisClientServer {

    private Logger logger = LoggerFactory.getLogger(getClass());
    protected ExpansionDeal expansionDeal;
    public RedisClientServerProxy(RedisConnectionStrategy redisConnection, RedisConfigurationSetting redisConfig) {
        super(redisConnection, redisConfig);
    }


    /**
     * 获取前缀key 实现
     *
     * @param clazz
     * @return
     */
    public String getPrefixKey(Class<?> clazz) {
        return null;
    }
    /**
     * @return
     */
    @Override
    public ExpansionDeal getExpansionDeal() {
        return this.expansionDeal;
    }


	/*================== write ================================*/

    /**
     * 添加到缓存列表
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public String set(String key, byte[] value) {
        if (null == key || !isWrite()) {
            return null;
        }
        try {
             this.getRedisClientByKey(key).set(key, new String(value));
            return isOK();
        }catch ( Exception e){
            logger.warn(" Redis  Server by set Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
       return null;
    }

    /**
     * 添加到缓存列表
     *
     * @param key key
     * @param value value
     * @return string
     */
    @Override
    public String set(String key, String value) {
        if (null == key || !isWrite()) {
            return null;
        }
        try {
            this.getRedisClientByKey(key).set(key, value);
            return isOK();
        }catch ( Exception e){
            logger.warn(" Redis  Server by set Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
        return null;
    }

//    /**
//     * 返回给定 key 的旧值。 当 key 没有旧值时，即 key 不存在时，返回 nil 。
//     * 当 key 存在但不是字符串类型时，返回一个错误。
//     *
//     * @param key
//     * @param value
//     * @return
//     */
//    @Override
//    public boolean getSet(String key, String value,long second) {
//        if (null == key || !isWrite()) {
//            return false;
//        }
//        try {
//           boolean result =  this.getRedisClientByKey(key).getSet(key, value,second);
//            return result;
//        }catch ( Exception e){
//            logger.warn(" Redis  Server by set Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
//        }
//        return false;
//    }

    /**
     * （SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值。
     * 设置成功，返回 1 。 设置失败，返回 0 。
     *
     * @param key
     * @param value  类型：NX | XX,NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。
     * @param second
     * @return 返回是否设置成功
     * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1
     * GB).
     * @return Status code reply
     */
    @Override
    public boolean setNx(String key, String value, int second) {
        if (null == key || !isWrite()) {
            return false;
        }
        try {
            boolean result = getRedisClientByKey(key).setex(key,value,second );
            return isOK(result);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by getSet Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
        return false;
    }

    /**
     * 按过期时间添加到缓存列表,默认是3天
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean setEx(String key, String value) {
        if (null == key || !isWrite()) {
            return false;
        }
        try {
            boolean result = getRedisClientByKey(key).setex(key,value,defaultTime() );
            return isOK(result);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by getSet Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
        return false;
    }

    /**
     * 按过期时间添加到缓存列表
     *
     * @param key
     * @param value
     * @param secondsTime
     * @return
     */
    @Override
    public boolean setEx(String key, String value, int secondsTime) {
        if (null == key || !isWrite()) {
            return false;
        }
        try {
            boolean result = getRedisClientByKey(key).setex(key,value,secondsTime );
            return isOK(result);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by getSet Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
        return false;
    }



    /**
     * 删除指定key 的缓存值;
     *
     * @param key
     * @return
     */
    @Override
    public boolean delete(String key) {
        if (null == key || !isWrite()) {
            return false;
        }
        try {
            Boolean result = getRedisClientByKey(key).delete(key);
            return isOK(result);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by getSet Modifier key:[{}]  , Exception: [{}] ",key, e);
        }
        return false;
    }

    /**
     * 批量删除redis key,已使用重写3.0分析实现key,实现功能
     *
     * @param keys
     */
    @Override
    public boolean delete(String... keys) {
        if (Objects.isNull(keys) || !isWrite()) {
            return false;
        }
        try {
            Long result = getRedisClientByKey(keys[0]).delete(Arrays.asList(keys));
            return isOK(result);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by getSet Modifier key:[{}]  ,  Exception: [{}] ",keys, e);
        }
        return false;
    }

    /**
     * 当 key 不存在时，返回 -2 。
     * 当 key 存在但没有设置剩余生存时间时，返回 -1 。
     * 否则，以秒为单位，返回 key 的剩余生存时间。
     *
     * @param key
     * @return
     */
    @Override
    public long ttl(String key) {
        return 0;
    }

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     *
     * @param data    key -value 类型的 map 集合
     * @param seconds 过期时间为多少秒
     */
    @Override
    public void batchSetEx(Map<String[], String[]> data, int seconds) {

    }

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     *
     * @param prefix  自定义的前缀key
     * @param kvMap   原始数据map集合
     * @param seconds 设置过期秒数;
     */
    @Override
    public <V>void batchSetExMap(@CatCacheKeySign String prefix ,Map<String, V> kvMap, int seconds) {
        if (kvMap == null || kvMap.isEmpty() || !isWrite()) {
            return;
        }
        //快速开发问题,暂时使用循环实现
        SpringRedisClient redisClient = getRedisClientByKey(prefix);
        redisClient.multiSet(prefix,kvMap,seconds);
    }



    /**
     * 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，
     * 如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。
     *
     * @param prefix  自定义的前缀key
     * @param kvMap   原始数据map集合
     * @param seconds 设置过期秒数;
     */
    @Override
    public <K, V> void batchSet(String prefix, Map<K, V> kvMap, int seconds) {
        if (kvMap == null || kvMap.isEmpty() || !isWrite()) {
            return;
        }
        SpringRedisClient redisClient = getRedisClientByKey(prefix);
        redisClient.multiSet(prefix,kvMap,seconds);
    }

    /**
     * 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，
     * 如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。
     *
     * @param kvMap   组装后的数据map集合
     * @param seconds 过期时间
     */
    @Override
    public void batchSet(Map<String, byte[]> kvMap, int seconds) {
        if (kvMap == null || kvMap.isEmpty() || !isWrite()) {
            return;
        }
        Optional<String> key = kvMap.keySet().stream().findFirst();
        if (Objects.isNull(key)){
            return;
        }
        String prefix = key.get();
        SpringRedisClient redisClient = getRedisClientByKey(prefix);
        redisClient.multiSet(prefix,kvMap,seconds);
    }

    /**
     * 设置key多少秒后失效
     *
     * @param key         如果生存时间设置成功，返回 1 。当 key 不存在或没办法设置生存时间，返回 0 。
     * @param secondsTime 设置失效秒量 RedisConstants.属性为值多少秒
     */
    @Override
    public boolean expireAt(String key, long secondsTime) {
        if (Objects.isNull(key) || !isWrite()) {
            return  false;
        }
        try {
            Boolean result = getRedisClientByKey(key).expireAt(key, Instant.ofEpochSecond(secondsTime));
            return isOK(result);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by getSet Modifier key:[{}]  , secondsTime:[{}] , Exception: [{}] ",key,secondsTime, e);
        }
        return false;
    }

    /**
     * 设置key多少秒后失效
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * @param key      设置key多少秒后失效
     * @param secondsTime 设置失效秒量 RedisConstants.属性为值多少秒
     */
    @Override
    public boolean expire(String key, int secondsTime) {
        if (Objects.isNull(key) || !isWrite()) {
            return  false;
        }
        try {
            Boolean result = getRedisClientByKey(key).expire(key, Duration.ofSeconds(secondsTime));
            return isOK(result);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by getSet Modifier key:[{}]  , , Exception: [{}] ",key, e);
        }
        return false;
    }

    /**
     * 批量设置key多少秒后失效
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     *
     * @param expireMap 组装好的数据map集合
     */
    @Override
    public void batchExpire(Map<String, Integer> expireMap) {

    }

    /**
     * 批量设置key多少秒后失效
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     *
     * @param prefix    自定义的前缀key
     * @param expireMap 原始数据map集合
     */
    @Override
    public void batchExpire(String prefix, Map<String, Integer> expireMap) {

    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     *
     * @param key   设置到缓存key值
     * @param value
     * @return
     */
    @Override
    public long setnx(String key, String value) {
        return 0;
    }

    /**
     * 将 key 中储存的数字值增一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     *
     * @param key 设置到缓存key值
     */
    @Override
    public long increment(String key) {
        return 0;
    }

    /**
     * 将 key 中储存的数字值减一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     *
     * @param key 设置到缓存key值
     */
    @Override
    public long decrement(String key) {
        return 0;
    }

    /**
     * @param key
     * @param time
     * @param refreshTime
     * @return
     */
    @Override
    public long increment(String key, int time, boolean refreshTime) {
        return 0;
    }

    /**
     * @param key
     * @param time
     * @param refreshTime
     * @return
     */
    @Override
    public long decrement(String key, int time, boolean refreshTime) {
        return 0;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾
     *
     * @param key   缓存key
     * @param value 增加到缓存中的字符串集合数组
     * @return 成功的数量
     */
    @Override
    public long rightPush(String key, String... value) {
        return 0;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾
     *
     * @param key   缓存key
     * @param value 增加到缓存中的字符串集合数组
     * @return 成功的数量
     */
    @Override
    public long rightPushAll(String key, String... value) {
        return 0;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾
     *
     * @param key           缓存key
     * @param expireSeconds 缓存key的有效时间,单位是秒;使用 RedisConstants 属性参数
     * @param value         增加到缓存中的字节数组
     * @return 成功的数量
     */
    @Override
    public long rightPushWithExpire(String key, int expireSeconds, Object value) {
        return 0;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头，最左边，头部插入数据
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头：
     *
     * @param key
     * @param value
     */
    @Override
    public long leftPush(String key, String... value) {
        return 0;
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最左边，头部插入数据)。
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾
     *
     * @param key           缓存key
     * @param expireSeconds 缓存key的有效时间,单位是秒;使用 RedisConstants 属性参数
     * @param value         增加到缓存中的字节数组
     * @return 成功的数量
     */
    @Override
    public long leftPushWithExpire(String key, int expireSeconds, Object value) {
        return 0;
    }

    /**
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
     * count 的值可以是以下几种：
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     * count = 0 : 移除表中所有与 value 相等的值。
     *
     * @param key   缓存key
     * @param count 移除列表中与参数个数
     * @param value remove方法接受一个键（key）、一个计数（count）和一个值（value），并返回被移除的元素数量。
     * @return
     */
    @Override
    public long leftRemoveFromList(String key, long count, String value) {
        return 0;
    }

    /**
     * @param key   将列表 key 下标为 index 的元素的值设置为 value 。
     * @param index 当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。
     * @param value
     * @return
     */
    @Override
    public String leftSet(String key, int index, String value) {
        return null;
    }

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @param key
     * @param members
     * @return
     */
    @Override
    public long removeFromSortedSet(String key, String... members) {
        return 0;
    }

    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public long removeFromSortedSetByScore(String key, double start, double end) {
        return 0;
    }

    /**
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
     * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
     *
     * @param key
     * @param member
     * @return
     */
    @Override
    public Long addSet(String key, String... member) {
        return null;
    }

    /**
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
     * 当 key 不是集合类型，返回一个错误。
     *
     * @param key
     * @param member
     * @return
     */
    @Override
    public Long removeFromSet(String key, String... member) {
        return null;
    }

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     *
     * @param key    索引 key
     * @param score  排序
     * @param member 元素
     * @return long
     */
    @Override
    public long addSortedSet(String key, long score, String member) {
        return 0;
    }

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     *
     * @param key          索引 key
     * @param scoreMembers 由member 和score 组成的集合map
     * @return 长度 long
     */
    @Override
    public long addSortedSet(String key, Map<String, Double> scoreMembers) {
        return 0;
    }

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     *
     * @param key          索引 key
     * @param scoreMembers 由member 和score 组成的集合map
     * @return 长度 long
     */
    @Override
    public long addSortedSet(byte[] key, Map<byte[], Double> scoreMembers) {
        return 0;
    }

    /**
     * 批量将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     *
     * @param sortSetMap
     */
    @Override
    public void batchAddSortedSet(Map<String, Map<String, Double>> sortSetMap) {

    }

    /**
     * 获取返回 key 所关联的字符串值。
     *
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        return null;
    }

    /**
     * 获取返回 key 所关联的字符串值。
     *
     * @param key 索引值
     * @return bytes
     */
    @Override
    public byte[] getByte(String key) {
        return new byte[0];
    }

    /**
     * 通过key获取返回指定的对象
     *
     * @param fieldKey 类对象的字段 field 值
     * @param clazz    对象实体类
     * @return 对象实体类
     */
    @Override
    public <T> T getT(String fieldKey, Class<T> clazz) {
        return null;
    }

    /**
     * 批量获取kv数据，传入多个key
     * list参数为所需查询的kv结构的key后接参数集合，最终key为api内部循环拼接prefix+list.get(i); clazz为返回的类型
     * eg: String.class ,list return : Map<String,String> map为可以查询到的数据map对应KV集合,
     * 外部调用者可关注list余留数据是否为0，等于0则全部匹配到，否则list中数据即为未匹配到的.
     *
     * @param clazz    对象实现类
     * @param prefix   集体 id 对象的前缀 key
     * @param set      要查找的集体
     * @param isRemove 是否从集体删除已获取到的 key
     * @return 指定类型的 map 对象
     */
    @Override
    public <K, V> Map<K, V> batchGet(Class<V> clazz, String prefix, Collection<K> set, boolean isRemove) {
        return null;
    }

    /**
     * 批量获取kv数据，传入多个key
     * list参数为所需查询的kv结构的key后接参数集合，最终key为api内部循环拼接prefix+list.get(i); clazz为返回的类型
     * eg: String.class ,list return : Map<String,String> map为可以查询到的数据map对应KV集合,
     * 外部调用者可关注list余留数据是否为0，等于0则全部匹配到，否则list中数据即为未匹配到的.
     *
     * @param prefix 集体 id 对象的前缀 key
     * @param keys
     * @return 指定类型的 map 对象
     */
    @Override
    public Map<Long, Long> batchGetLong(String prefix, Collection<Long> keys) {
        return null;
    }

    /**
     * 检查给定 key 是否存在。若 key 存在，返回 1 ，否则返回 0 。
     *
     * @param key
     * @return
     */
    @Override
    public boolean exists(String key) {
        return false;
    }

    /**
     * 返回列表 key 中，下标为 index 的元素。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key   索引位置从0开始，其中0表示列表的第一个元素，1表示列表的第二个元素，依此类推。
     * @param index
     * @return
     */
    @Override
    public String indexList(String key, long index) {
        return null;
    }

    /**
     * 返回指定key的list的长度
     *
     * @param key
     */
    @Override
    public long lenList(String key) {
        return 0;
    }

    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 我们首先获取了ListOperations实例，然后使用range方法从Redis列表中获取指定范围内的元素。range方法接受一个键（key）、一个起始索引（start）和一个结束索引（end），并返回在指定范围内的元素列表。
     * 请注意，索引位置从0开始，其中0表示列表的第一个元素，1表示列表的第二个元素，依此类推。
     *
     * @param key       key 索引
     * @param start     开始值 0.开始
     * @param end       结束值
     * @param clazzType 返回结果类型
     * @return 返回指定对象的集合列表
     */
    @Override
    public <T> List<T> listRange(byte[] key, long start, long end, Class<T> clazzType) {
        return null;
    }

    /**
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递减(从大到小)来排列。
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。
     * 在上述示例中，我们首先获取了ZSetOperations实例，然后使用reverseRange方法从Redis有序集合中按分数从高到低获取指定范围内的成员。reverseRange方法接受一个键（key）、一个起始索引（start）和一个结束索引（end），并返回在指定范围内的成员集合。
     * 请注意，范围是根据成员的分数从高到低来定义的，而不是根据成员的索引位置。
     * 上述示例假设您已经配置了适当的RedisTemplate实例，并在Spring中进行了注入。通过RedisTemplate，您可以执行各种与Redis交互的操作。
     *
     * @param key   key 索引
     * @param start 开始值 0.开始
     * @param end   结束值
     * @return 返回指定对象的集合列表
     */
    @Override
    public List<String> reverseRangeSortedSet(String key, long start, long end) {
        return null;
    }

    /***
     * 返回有序集合中member的得分
     * @param key  key 索引
     * @param member 值
     * @return double值
     */
    @Override
    public Double getSortedSet(String key, String member) {
        return null;
    }

    /***
     * 返回有序集合的长度
     * @param key 使用size方法从Redis有序集合中获取成员数量。size方法接受一个键（key），并返回有序集合的基数（成员数量）。
     * @return 长度数量
     */
    @Override
    public int getSortedSetSize(byte[] key) {
        return 0;
    }

    /**
     * 使用reverseRangeWithScores方法从Redis有序集合中按分数从高到低获取指定范围内的成员及其分数。
     * reverseRangeWithScores方法接受一个键（key）、一个起始索引（start）和一个结束索引（end），
     * 并返回在指定范围内的成员及其分数的集合。
     *
     * @param key   一个键（key）
     * @param start 一个起始索引（start）为 0
     * @param end   一个结束索引（end）
     * @return map 对象
     */
    @Override
    public Map<String, Double> reverseRangeWithScores(String key, long start, long end) {
        return null;
    }

    /***
     * 按得分返回返回元素及元素的得分
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @param clazzKey
     * @return
     */
    @Override
    public <K> Map<K, Double> reverseRangeWithScores(String key, double min, double max, int offset, int count, Class<K> clazzKey) {
        return null;
    }

    /**
     * @param key   一个键（key）
     * @param start 一个起始索引（start）为 0
     * @param end   一个结束索引（end）
     * @return
     */
    @Override
    public List<String[]> getRangeSortSet(String[] key, long start, long end) {
        return null;
    }

    /***
     * 按位置范围获取有序集合中的元素
     * @param key 一个键（key）
     * @param start 一个起始索引（start）为 0
     * @param end 一个结束索引（end）
     * @return
     */
    @Override
    public List<String> getRangeSortSet(String key, long start, long end) {
        return null;
    }

    /**
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public Map<byte[], Double> getSortSetWithScore(String key, long start, long end) {
        return null;
    }

    /***
     * 为有序集合中的member的得分增加score
     * @param key
     * @param score
     * @param member
     * @return
     */
    @Override
    public Double zincrby(String key, long score, String member) {
        return null;
    }

    /***
     * 按得分范围获取有序集合中的元素
     * @param key
     * @param min
     * @param max
     * @return
     */
    @Override
    public List<String> zrangeByScore(String key, double min, double max) {
        return null;
    }

    /***
     * 按得分范围倒序获取有序集合元素
     * @param key
     * @param min
     * @param max
     * @return
     */
    @Override
    public List<String> zrevrangeByScore(String key, double min, double max) {
        return null;
    }

    /***
     * 返回有序集合中得分在min-max之间的成员数量
     * @param key
     * @param min
     * @param max
     * @return
     */
    @Override
    public Long zcount(String key, double min, double max) {
        return null;
    }

    /***
     * 返回set的长度
     * @param key
     * @return
     */
    @Override
    public Long scard(String key) {
        return null;
    }

    /**
     * @param key
     * @param cursor
     * @return
     */
    @Override
    public ScanResult<String> sscan(String key, String cursor) {
        return null;
    }

    /***
     * 判断member是否存在set中
     * @param key
     * @param member
     * @return
     */
    @Override
    public boolean sismember(String key, String member) {
        return false;
    }

    /***
     * 随机返回集合中的几个元素
     * @param key
     * @param count
     * @return
     */
    @Override
    public List<String> srandmember(String key, int count) {
        return null;
    }

    /***
     * 返回set中的所有成员
     * @param key
     */
    @Override
    public Set<String> smembers(String key) {
        return null;
    }

    /***
     * 按倒序返回有序集合中的元素
     * @param key
     * @param start
     * @param end
     */
    @Override
    public List<String> zrevrange(String[] key, long start, long end) {
        return null;
    }

    /***
     * 按倒序返回有序集合中的元素
     * @param key
     * @param max
     * @param min
     * @param pos
     * @param count
     */
    @Override
    public List<byte[]> zrevrangeByScore(byte[] key, double max, double min, int pos, int count) {
        return null;
    }

    /***
     * 在哈希中放置一个字段
     * @param key
     * @param field
     * @param value
     */
    @Override
    public long hset(String key, byte[] field, byte[] value) {
        return 0;
    }

    /***
     * 在哈希中放置多个字段
     * @param key redis 对应的key -> map
     * @param fieldKey   redis 缓存中 map->key
     * @param fieldValue redis 缓存中 map->Value
     */
    @Override
    public boolean hmset(String key, String fieldKey, String fieldValue) {
        return false;
    }

    /***
     * 返回哈希的键值对个数
     * @param key
     * @return
     */
    @Override
    public long hlen(String key) {
        return 0;
    }

    /***
     * 返回哈希中的某个字段值
     * @param key
     * @param field
     * @return
     */
    @Override
    public byte[] hget(String key, String field) {
        return new byte[0];
    }

    /***
     * 返回哈希中的某个字段值
     * @param key
     * @param field
     * @param clazz
     * @return
     */
    @Override
    public <T> T hgetT(String key, String field, Class<T> clazz) {
        return null;
    }

    /**
     * @param key
     * @param fields
     * @return
     */
    @Override
    public Map<String, String> hmget(String key, String... fields) {
        return null;
    }

    /***
     * 返回哈希中的多个字段值
     * @param key
     * @param fields
     * @param clazz
     * @return
     */
    @Override
    public <K, V> Map<K, V> hmget(String key, Collection<K> fields, Class<V> clazz) {
        return null;
    }

    /***
     * 返回哈希所有键值对
     * @param key
     * @return
     */
    @Override
    public Map<byte[], byte[]> hgetAll(byte[] key) {
        return null;
    }

    /***
     * 返回哈希所有键值对
     * @param key
     * @return
     */
    @Override
    public Map<String, String> hgetAll(String key) {
        return null;
    }

    /***
     * 删除hasn中的某些字段
     * @param key
     * @param field
     * @return
     */
    @Override
    public long hdel(String key, String... field) {
        return 0;
    }

    /***
     * 判断哈希中是否存在某个字段
     * @param key
     * @param field
     * @return
     */
    @Override
    public boolean hexists(String key, String field) {
        return false;
    }

    /***
     * 在哈希中放置多个字段
     * @param key key
     * @param hash hash
     */
    @Override
    public void hmset(byte[] key, Map<byte[], byte[]> hash) {

    }

    /***
     * 在哈希中放置多个字段
     * @param key
     * @param hash
     */
    @Override
    public void hmset(String key, Map<String, String> hash) {

    }

    /***
     * 将hash中的某个字段原子递增
     * @param key
     * @param field
     * @param value
     * @return
     */
    @Override
    public long hincrBy(String key, String field, int value) {
        return 0;
    }

    /***
     * 获取key的所有哈希字段
     * @param key
     * @return
     */
    @Override
    public Set<String> hkeys(String key) {
        return null;
    }

    /**
     * @param key
     * @param collection
     * @param <T>
     * @return
     */
    @Override
    public <T> Map<T, Long> multiZrank(String key, Collection<T> collection) {
        return null;
    }

    /**
     * 按mapPrefixKey和指定时间delMapCacheByExpireMsTime, 删除过期的k,v
     *
     * @param mapPrefixKey redis map 的前缀key
     * @param expireMsTime 过期时间
     * @return
     */
    @Override
    public Map<String, String> getMapCacheAndDelExpire(String mapPrefixKey, long expireMsTime) {
        return null;
    }

    /***
     * 按位置范围获取有序列表中的元素并返回元素的分数
     * @param key
     * @param start 开始位置
     * @param end 结束位置
     * @return
     */
    @Override
    public List<Tuple> getMembersWithScoresInRange(String key, long start, long end) {
        return null;
    }

    /**
     * 根据Score值范围 倒序获取值   取指定数量个值
     *
     * @param key 索引 key
     * @param max score 范围
     * @param min 最小值
     * @return
     * @author xiaogenliu
     */
    @Override
    public List<byte[]> reverseRangeByScore(byte[] key, double max, double min) {
        return null;
    }

    /**
     * 发送消息到指定的通道
     *
     * @param key
     * @param channel - 要发送的消息通道
     * @param message - 消息内容
     * @return 返回该channel的订阅数量
     */
    @Override
    public long publish(String key, String channel, String message) {
        return 0;
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param uuid       请求标识uuid
     * @param expireTime expireTime 超期时间（秒）
     * @return 是否获取成功
     */
    @Override
    public boolean getSet(String lockKey, String uuid, long expireTime) {
        return false;
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey 锁
     * @param uuid    请求标识
     * @return 是否释放成功
     */
    @Override
    public boolean evalLock(String lockKey, String uuid) {
        return false;
    }


}
