package com.suven.framework.core.redis;

import com.suven.framework.common.cat.CatCacheKeySign;
import com.suven.framework.common.cat.CatCacheSign;
import com.suven.framework.common.constants.CatTopConstants;
import redis.clients.jedis.resps.ScanResult;
import redis.clients.jedis.resps.Tuple;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisClientServer  {


    default  public boolean isWrite() {/** 是否写*/
        return true;
    }

    default public boolean isRead() {//是否可读read
        return true;
    }

    default public boolean isDaoCache() {//dao是否开启缓存
        return true;
    }
    /**
     * 获取前缀key 实现
     *
     * @param clazz
     * @return
     */
    String getPrefixKey(Class<?> clazz);



    /*================== write ================================*/

    /**
     * 添加到缓存列表
     *
     * @param key
     * @param value
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    String set(@CatCacheKeySign String key, byte[] value) ;

    /**
     * 添加到缓存列表
     *
     * @param key
     * @param value
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    String set(@CatCacheKeySign String key, String value) ;


//    /**
//     * 返回给定 key 的旧值。 当 key 没有旧值时，即 key 不存在时，返回 nil 。
//     * 当 key 存在但不是字符串类型时，返回一个错误。
//     * @param key
//     * @param value
//     * @return
//     */
//    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
//    String getSet(@CatCacheKeySign String key, String value,long second) ;
    /**
     * （SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值。
     *  设置成功，返回 1 。 设置失败，返回 0 。
     * @param key
     * @param value 类型：NX | XX,NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。
     * @param second
     * @return 返回是否设置成功
     * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1
     * GB).
     * @param key
     * @param value
     * param nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key
     *          if it already exist.
     * param expx EX|PX, expire time units: EX = seconds; PX = milliseconds
     * param time expire time in the units of <code>expx</code>
     * @return Status code reply
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean setNx(@CatCacheKeySign String key, String value, int second);



    /**
     * 按过期时间添加到缓存列表,默认是3天
     *
     * @param key
     * @param value
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean setEx(@CatCacheKeySign String key, String value) ;

    /**
     * 按过期时间添加到缓存列表
     *
     * @param key
     * @param value
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean setEx(@CatCacheKeySign String key, String value, int secondsTime) ;


    /**
     * 删除指定key 的缓存值;
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean delete(@CatCacheKeySign String key);

    /**
     * 批量删除redis key,已使用重写3.0分析实现key,实现功能
     * @param keys
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean delete(@CatCacheKeySign String... keys) ;

    /**
     *  当 key 不存在时，返回 -2 。
     *  当 key 存在但没有设置剩余生存时间时，返回 -1 。
     *  否则，以秒为单位，返回 key 的剩余生存时间。
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long ttl(@CatCacheKeySign String key) ;

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     * @param data key -value 类型的 map 集合
     * @param seconds  过期时间为多少秒
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    void batchSetEx(@CatCacheKeySign Map<String[], String[]> data, int seconds);

    /**
     *
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     * @param prefix  自定义的前缀key
     * @param kvMap   原始数据map集合
     * @param seconds 设置过期秒数;
     * @param <V>     泛型,map 可以传任意类型
     */
    <V> void  batchSetExMap(@CatCacheKeySign String prefix, Map<String, V> kvMap, int seconds) ;

    /**
     * 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，
     * 如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。
     * @param prefix  自定义的前缀key
     * @param kvMap   原始数据map集合
     * @param seconds 设置过期秒数;
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    <K, V> void batchSet(@CatCacheKeySign String prefix, Map<K, V> kvMap, int seconds) ;

    /**
     * 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，
     * 如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。
     * @param kvMap   组装后的数据map集合
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    void batchSet(@CatCacheKeySign Map<String, byte[]> kvMap, int seconds);

    /**
     * 设置key多少秒后失效
     * @param key 如果生存时间设置成功，返回 1 。当 key 不存在或没办法设置生存时间，返回 0 。
     * @param secondsTime 设置失效秒量 RedisConstants.属性为值多少秒
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean expireAt(@CatCacheKeySign String key, long secondsTime) ;


    /**
     * 设置key多少秒后失效
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * @param key 设置key多少秒后失效
     * @param unixTime 设置失效秒量 RedisConstants.属性为值多少秒
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean expire(@CatCacheKeySign String key, int unixTime) ;




    /**
     * 批量设置key多少秒后失效
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * @param expireMap  组装好的数据map集合
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    void batchExpire(@CatCacheKeySign Map<String, Integer> expireMap) ;

    /**
     * 批量设置key多少秒后失效
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * @param prefix  自定义的前缀key
     * @param expireMap  原始数据map集合
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    void batchExpire(@CatCacheKeySign String prefix, Map<String, Integer> expireMap);


    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     * @param key 设置到缓存key值
     * @param value
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long setnx(@CatCacheKeySign String key, String value);


    /**
     * 将 key 中储存的数字值增一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * @param key 设置到缓存key值
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long increment(@CatCacheKeySign String key);

    /**
     * 将 key 中储存的数字值减一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * @param key 设置到缓存key值
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long decrement(@CatCacheKeySign String key);


    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long increment(@CatCacheKeySign String key, int time, boolean refreshTime);

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long decrement(@CatCacheKeySign String key, int time, boolean refreshTime) ;



    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾
     * @param key 缓存key
     * @param value  增加到缓存中的字符串集合数组
     * @return 成功的数量
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long rightPush(@CatCacheKeySign String key, String... value) ;

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾
     * @param key 缓存key
     * @param value  增加到缓存中的字符串集合数组
     * @return 成功的数量
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long rightPushAll(@CatCacheKeySign String key, String... value) ;

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾
     * @param key 缓存key
     * @param expireSeconds  缓存key的有效时间,单位是秒;使用 RedisConstants 属性参数
     * @param value  增加到缓存中的字节数组
     * @return 成功的数量
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long rightPushWithExpire(@CatCacheKeySign String key, int expireSeconds, Object value) ;

    /**
     * 将一个或多个值 value 插入到列表 key 的表头，最左边，头部插入数据
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头：
     **/
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long leftPush(@CatCacheKeySign String key, String... value);

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最左边，头部插入数据)。
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾
     * @param key 缓存key
     * @param expireSeconds  缓存key的有效时间,单位是秒;使用 RedisConstants 属性参数
     * @param value  增加到缓存中的字节数组
     * @return 成功的数量
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long leftPushWithExpire(@CatCacheKeySign String key, int expireSeconds, Object value) ;

    /**
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
     *  count 的值可以是以下几种：
     *  count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     *  count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     *  count = 0 : 移除表中所有与 value 相等的值。
     * @param key 缓存key
     * @param count 移除列表中与参数个数
     * @param value remove方法接受一个键（key）、一个计数（count）和一个值（value），并返回被移除的元素数量。
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long leftRemoveFromList(@CatCacheKeySign String key, long count, String value) ;

    /**
     * @param key  将列表 key 下标为 index 的元素的值设置为 value 。
     * @param index 当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。
     * @param value
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    String leftSet(@CatCacheKeySign String key, int index, String value);


    // ================= redis SortedSet（有序集合） ===================


    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     * @param key
     * @param
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long removeFromSortedSet(@CatCacheKeySign String key, String... members) ;

    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     * @param key
     * @param start
     * @param end
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long removeFromSortedSetByScore(@CatCacheKeySign String key, double start, double end) ;


    /**
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
     * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
     * @param key
     * @param member
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Long addSet(@CatCacheKeySign String key, String... member) ;

    /**
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
     * 当 key 不是集合类型，返回一个错误。
     * @param key
     * @param member
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Long removeFromSet(@CatCacheKeySign String key, String... member);

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     score 值可以是整数值或双精度浮点数。
     如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * @param key 索引 key
     * @param score 排序
     * @param member 元素
     * @return long
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long addSortedSet(@CatCacheKeySign String key, long score, String member) ;
    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     *
     * @param key 索引 key
     * @param scoreMembers 由member 和score 组成的集合map
     * @return 长度 long
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long addSortedSet(@CatCacheKeySign String key, Map<String, Double> scoreMembers);

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     score 值可以是整数值或双精度浮点数。
     如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * @param key 索引 key
     * @param scoreMembers 由member 和score 组成的集合map
     * @return 长度 long
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long addSortedSet(@CatCacheKeySign byte[] key, Map<byte[], Double> scoreMembers) ;



    /**
     * 批量将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     * @param sortSetMap
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    void batchAddSortedSet(@CatCacheKeySign Map<String, Map<String, Double>> sortSetMap);
    /*================== read ================================*/

    /**
     * 获取返回 key 所关联的字符串值。
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    String get(@CatCacheKeySign String key) ;

    /**
     * 获取返回 key 所关联的字符串值。
     * @param key 索引值
     * @return bytes
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    byte[] getByte(@CatCacheKeySign String key);
    /**
     * 通过key获取返回指定的对象
     *
     * @param fieldKey 类对象的字段 field 值
     * @param clazz 对象实体类
     * @return 对象实体类
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    <T> T getT(@CatCacheKeySign String fieldKey, Class<T> clazz);

    /**
     * 批量获取kv数据，传入多个key
     * list参数为所需查询的kv结构的key后接参数集合，最终key为api内部循环拼接prefix+list.get(i); clazz为返回的类型
     * eg: String.class ,list return : Map<String,String> map为可以查询到的数据map对应KV集合,
     * 外部调用者可关注list余留数据是否为0，等于0则全部匹配到，否则list中数据即为未匹配到的.
     *
     * @param clazz 对象实现类
     * @param  prefix 集体 id 对象的前缀 key
     * @param  set 要查找的集体
     * @param  isRemove  是否从集体删除已获取到的 key
     * @return 指定类型的 map 对象
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    <K, V> Map<K, V> batchGet(Class<V> clazz, @CatCacheKeySign String prefix, Collection<K> set, boolean isRemove);

    /**
     * 批量获取kv数据，传入多个key
     * list参数为所需查询的kv结构的key后接参数集合，最终key为api内部循环拼接prefix+list.get(i); clazz为返回的类型
     * eg: String.class ,list return : Map<String,String> map为可以查询到的数据map对应KV集合,
     * 外部调用者可关注list余留数据是否为0，等于0则全部匹配到，否则list中数据即为未匹配到的.
     *
     * @param  prefix 集体 id 对象的前缀 key
     * @return 指定类型的 map 对象
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Map<Long, Long> batchGetLong(@CatCacheKeySign String prefix, Collection<Long> keys);

    /**
     * 检查给定 key 是否存在。若 key 存在，返回 1 ，否则返回 0 。
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean exists(@CatCacheKeySign String key) ;

    /**
     * 返回列表 key 中，下标为 index 的元素。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * @param key 索引位置从0开始，其中0表示列表的第一个元素，1表示列表的第二个元素，依此类推。
     * @param index
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    String indexList(@CatCacheKeySign String key, long index) ;


    /**
     * 返回指定key的list的长度
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long lenList(@CatCacheKeySign String key) ;


    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 我们首先获取了ListOperations实例，然后使用range方法从Redis列表中获取指定范围内的元素。range方法接受一个键（key）、一个起始索引（start）和一个结束索引（end），并返回在指定范围内的元素列表。
     * 请注意，索引位置从0开始，其中0表示列表的第一个元素，1表示列表的第二个元素，依此类推。
     * @param key key 索引
     * @param start 开始值 0.开始
     * @param end 结束值
     * @param clazzType 返回结果类型
     * @param <T> 返回结果对象
     * @return 返回指定对象的集合列表
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    <T> List<T> listRange(@CatCacheKeySign byte[] key, long start, long end, Class<T> clazzType) ;


    /**
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递减(从大到小)来排列。
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。
     * 在上述示例中，我们首先获取了ZSetOperations实例，然后使用reverseRange方法从Redis有序集合中按分数从高到低获取指定范围内的成员。reverseRange方法接受一个键（key）、一个起始索引（start）和一个结束索引（end），并返回在指定范围内的成员集合。
     * 请注意，范围是根据成员的分数从高到低来定义的，而不是根据成员的索引位置。
     * 上述示例假设您已经配置了适当的RedisTemplate实例，并在Spring中进行了注入。通过RedisTemplate，您可以执行各种与Redis交互的操作。
     * @param key key 索引
     * @param start 开始值 0.开始
     * @param end 结束值
     * @return 返回指定对象的集合列表
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    List<String> reverseRangeSortedSet(@CatCacheKeySign String key, long start, long end) ;

    /***
     * 返回有序集合中member的得分
     * @param key  key 索引
     * @param member 值
     * @return double值
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Double getSortedSet(@CatCacheKeySign String key, String member);

    /***
     * 返回有序集合的长度
     * @param key 使用size方法从Redis有序集合中获取成员数量。size方法接受一个键（key），并返回有序集合的基数（成员数量）。
     * @return 长度数量
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    int getSortedSetSize(@CatCacheKeySign byte[] key);

    /**
     * 使用reverseRangeWithScores方法从Redis有序集合中按分数从高到低获取指定范围内的成员及其分数。
     * reverseRangeWithScores方法接受一个键（key）、一个起始索引（start）和一个结束索引（end），
     * 并返回在指定范围内的成员及其分数的集合。
     * @param key 一个键（key）
     * @param start 一个起始索引（start）为 0
     * @param end 一个结束索引（end）
     * @return map 对象
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Map<String, Double> reverseRangeWithScores(@CatCacheKeySign String key, long start, long end);


    /***
     * 按得分返回返回元素及元素的得分
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @param clazzKey
     * @param <K>
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    <K> Map<K, Double> reverseRangeWithScores(@CatCacheKeySign String key, double min, double max, int offset, int count,
                                              Class<K> clazzKey) ;

    /**
     *
     * @param key 一个键（key）
     * @param start 一个起始索引（start）为 0
     * @param end 一个结束索引（end）
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    List<String[]> getRangeSortSet(String[] key, long start, long end) ;

    /***
     * 按位置范围获取有序集合中的元素
     * @param key 一个键（key）
     * @param start 一个起始索引（start）为 0
     * @param end 一个结束索引（end）
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    List<String> getRangeSortSet(@CatCacheKeySign String key, long start, long end) ;


    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Map<byte[], Double> getSortSetWithScore(@CatCacheKeySign String key, long start, long end);

    /***
     * 为有序集合中的member的得分增加score
     * @param key
     * @param score
     * @param member
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Double zincrby(@CatCacheKeySign String key, long score, String member) ;

    /***
     * 按得分范围获取有序集合中的元素
     * @param key
     * @param min
     * @param max
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    List<String> zrangeByScore(@CatCacheKeySign String key, double min, double max) ;


    /***
     * 按得分范围倒序获取有序集合元素
     * @param key
     * @param min
     * @param max
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    List<String> zrevrangeByScore(@CatCacheKeySign String key, double min, double max);


    /***
     * 返回有序集合中得分在min-max之间的成员数量
     * @param key
     * @param min
     * @param max
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Long zcount(@CatCacheKeySign String key, double min, double max);



    /***
     * 返回set的长度
     * @param key
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Long scard(@CatCacheKeySign String key);


    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    ScanResult<String> sscan(@CatCacheKeySign String key, String cursor);

    /***
     * 判断member是否存在set中
     * @param key
     * @param member
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean sismember(@CatCacheKeySign String key, String member);


    /***
     * 随机返回集合中的几个元素
     * @param key
     * @param count
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    List<String> srandmember(@CatCacheKeySign String key, int count);

    /***
     * 返回set中的所有成员
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Set<String> smembers(@CatCacheKeySign String key);


    /***
     * 按倒序返回有序集合中的元素
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    List<String> zrevrange(@CatCacheKeySign String[] key, long start, long end) ;


    /***
     * 按倒序返回有序集合中的元素
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    List<byte[]> zrevrangeByScore(@CatCacheKeySign byte[] key, double max, double min, int pos, int count);

    /***
     * 在哈希中放置一个字段
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long hset(@CatCacheKeySign String key, byte[] field, byte[] value);


    /***
     * 在哈希中放置多个字段
     * @param key redis 对应的key -> map
     * @param fieldKey   redis 缓存中 map->key
     * @param fieldValue redis 缓存中 map->Value
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean hmset(@CatCacheKeySign String key, String fieldKey, String fieldValue);


    /***
     * 在哈希中放置多个字段
     * @param key
     * @param hash
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    void hmset(@CatCacheKeySign String key, Map<String, String> hash) ;


    /***
     * 在哈希中放置多个字段
     * @param key
     * @param hash
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    void hmset(@CatCacheKeySign byte[] key, Map<byte[], byte[]> hash);


    /***
     * 返回哈希的键值对个数
     * @param key
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long hlen(@CatCacheKeySign String key);



    /***
     * 返回哈希中的某个字段值
     * @param key
     * @param field
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    byte[] hget(@CatCacheKeySign String key, String field);



    /***
     * 返回哈希中的某个字段值
     * @param key
     * @param field
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    <T>T hgetT(@CatCacheKeySign String key, String field, Class<T> clazz);



    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Map<String, String> hmget(@CatCacheKeySign String key, String... fields);


    /***
     * 返回哈希中的多个字段值
     * @param key
     * @param fields
     * @param clazz
     * @param <K>
     * @param <V>
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    <K, V> Map<K,V> hmget(@CatCacheKeySign String key, Collection<K> fields, Class<V> clazz);


    /***
     * 返回哈希所有键值对
     * @param key
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Map<byte[],byte[]> hgetAll(@CatCacheKeySign byte[] key);


    /***
     * 返回哈希所有键值对
     * @param key
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Map<String,String> hgetAll(@CatCacheKeySign String key);


    /***
     * 删除hasn中的某些字段
     * @param key
     * @param field
     * @return
     */

    long hdel(@CatCacheKeySign String key, String... field);


    /***
     * 判断哈希中是否存在某个字段
     * @param key
     * @param field
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean hexists(@CatCacheKeySign String key, String field) ;

    /***
     * 将hash中的某个字段原子递增
     * @param key
     * @param field
     * @param value
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long hincrBy(String key, String field, int value) ;

    /***
     * 获取key的所有哈希字段
     * @param key
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    Set<String> hkeys(String key) ;


    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    <T> Map<T, Long> multiZrank(@CatCacheKeySign String key, Collection<T> collection) ;

    /**
     * 按mapPrefixKey和指定时间delMapCacheByExpireMsTime, 删除过期的k,v
     * @param mapPrefixKey redis map 的前缀key
     * @param expireMsTime 过期时间
     * @return
     */

    Map<String, String> getMapCacheAndDelExpire(String mapPrefixKey, long expireMsTime) ;
    /***
     * 按位置范围获取有序列表中的元素并返回元素的分数
     * @param key
     * @param start 开始位置
     * @param end 结束位置
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    List<Tuple> getMembersWithScoresInRange(@CatCacheKeySign String key, long start, long end) ;


    /**
     * 根据Score值范围 倒序获取值   取指定数量个值
     *
     * @param key 索引 key
     * @param max	score 范围
     * @param min 最小值
     * @author xiaogenliu
     * @return
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    List<byte[]> reverseRangeByScore(@CatCacheKeySign byte[] key, double max, double min);


    /**
     * 发送消息到指定的通道
     * @param channel - 要发送的消息通道
     * @param message - 消息内容
     * @return 返回该channel的订阅数量
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    long publish(@CatCacheKeySign String key, String channel, String message);

    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param uuid 请求标识uuid
     * @param expireTime expireTime 超期时间（秒）
     * @return 是否获取成功
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean getSet(@CatCacheKeySign String lockKey, String uuid, long expireTime);


    /**
     *  释放分布式锁
     * @param lockKey 锁
     * @param uuid 请求标识
     * @return 是否释放成功
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    boolean evalLock(@CatCacheKeySign String lockKey, String uuid) ;

//
//    <T extends IBaseApi> void addCacheList(Class<T> entityClass, List<T> list);
//
//    <T extends IBaseApi> Object findCacheByKey(String key, Class<T> entityClass);
//
//   <T> void addCacheByKey(String key, T result);
//
//    <T>void addCache(T entity);
//
//    <T extends IBaseApi> IBaseApi findCacheById(Class<T> entityClass, long id);
//
//    <T extends IBaseApi> Map<Long,T> findMapCache(Class<T> entityClass, Collection<Long> idList);
}
