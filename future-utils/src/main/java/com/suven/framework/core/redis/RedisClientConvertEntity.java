package com.suven.framework.core.redis;


import com.suven.framework.http.api.IBaseApi;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RedisClientConvertEntity<T extends IBaseApi>{


    RedisClientServer redisClient();

    /**
     * 将查询结果缓存到redis 中，缓存时间为2秒
     **/
    boolean addResultToCache(String key, Object result);

    /**
     * 直接根据key值获取String类型value
     */
    String findResultByKey(String key);

    /**

     * Description: redis中value为int值加1
     */
    long increment(String key);

    /**
     * Description: redis中value为int值减1
     */
    long decrement(String key);

    /**

     * Description: redis中value为int值加1, 有效时间
     */
    long increment(String key, int time, boolean refreshTime);

    List<T> findResultByCache(String key, Class<T> clazz);

    /**
     * 初始化缓存类对象的缓存KEY
     *
     * @param clazz
     * @return
     */
    String getPrefixKey(Class<?> clazz);

    /**
     * 添加对象到redis缓存中;
     *
     * @param entity
     * @return
     */
    boolean addCache(IBaseApi entity);

    /**
     * @param entity 在自定义key:增加的参数值;
     * @param list   批量插入的对象的信息;
     * @return
     */
    boolean addCacheList(IBaseApi entity, List<T> list);

    /**
     * @param claxx 在自定义key:增加的参数值;
     * @param list  用于排序的字段值;
     * @return
     */
    boolean addCacheList(Class<?> claxx, List<T> list);

    boolean addCacheByKey(String cacheKey, IBaseApi entity);

    /**
     * 添加对象到redis缓存中;
     *
     * @param entity
     * @return
     */
    boolean addCacheByKey(String cacheKey, IBaseApi entity, int time);

    /**
     * 添加对象到redis缓存中;
     *
     * @param cacheKey
     * @return
     */
    boolean addCacheByKeyBase(String cacheKey, Object o);

    boolean addCacheByKeyBase(String cacheKey, Object o, int time);

    /**
     * 通过类对像标签索引字段查找缓存对象信息;
     *
     * @param entity
     * @return
     */
    @SuppressWarnings("unchecked")
    T findCache(IBaseApi entity);

    /**
     * 通过索引字段查找缓存对象信息;
     *
     * @param claxx
     * @return
     */
    T findCacheById(Class<T> claxx, long entityId);

    /**
     * 通过索引字段查找缓存对象信息;
     *
     * @param claxx
     * @return
     */
    T findCacheById(Class<T> claxx, String entityId);

    /**
     * 通过自己完成定义好的缓存key,查找相关类对象信息;
     *
     * @param cacheKey 自定义好的完整的缓存对应key(eg: key==player:123)
     * @param clazz    (eg:ModelEntity.class)
     * @return
     */
    T findCacheByKey(String cacheKey, Class<T> clazz);

    /**
     * 通过自己完成定义好的缓存key,查找相关基本类;
     *
     * @param <V>
     * @param cacheKey 自定义好的完整的缓存对应key(eg: key==player:123)
     * @param clazz    (eg:ModelEntity.class)
     * @return
     * @return
     */
    <V> V findCacheByKeyBase(String cacheKey, Class<V> clazz);

    /**
     * 类名:小写: 批量查找KV的对象的批量获取缓存列表集合信息;
     *
     * @param colle
     * @return
     */
    <K> Map<K, T> findMapCache(Class<T> clazz, Collection<K> colle);

    /**
     * 类名:小写: 批量查找KV的对象的批量获取缓存列表集合信息;
     *
     * @param clazz     反映的类对象
     * @param colle     要搜索或查找的扩展id的集合;
     * @param prefixKey T对象的完整key的前缀; 实例: userKey:123 其中prefixKey== userKey
     *                  ,userId==123; 其中":" 是自动实现
     * @return
     */
    <K> Map<K, T> findMapCache(Class<T> clazz, String prefixKey, Collection<K> colle);

    /**
     * Description: 设备控制并发锁的的过期时间
     */

    boolean expire(String cacheKey, int unixTime);

    /**
     * 删除指定的key的值;
     *
     * @param entity IBaseApi
     * @return
     */
    boolean delCache(IBaseApi entity);

    /**
     * 删除指定的key的值;
     *
     * @param cacheKey IBaseApi
     * @return
     */
    boolean delCacheBase(String cacheKey);

    /**
     * 通过类名称和id删除缓存对象
     *
     * @param claxx
     * @param entityId
     * @return
     */
    boolean delCache(Class<T> claxx, long entityId);

    /**
     * 通过类名称和id删除缓存对象
     *
     * @param claxx
     * @param entityId
     * @return
     */
    boolean delCache(Class<T> claxx, String entityId);

    /**
     * 通过类名称和ids批量删除缓存对象
     *
     * @param claxx
     * @param entityIds
     * @return
     */
    boolean delCache(Class<T> claxx, List<Long> entityIds);

    /**
     * 批量删除;
     *
     * @param set Collection
     * @return
     */
    void delCache(Collection<String> set);

//    /**
//     * @param cacheSet 在自定义key:增加的参数值;
//     * @return
//     */
//    boolean addTCacheSet(CacheSetParam cacheSet);
//
//    /**
//     * @param cacheSet 在自定义key:增加的参数值; 参加排序的集合;S
//     * @return
//     */
//    boolean addMapCacheSet(CacheSetParam cacheSet);
//
//    /**
//     * 将一个或多个 collections 元素加入到集合 key 当中，已经存在于集合的 collections 元素将被忽略。
//     * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
//     *
//     * @param key
//     * @param collections
//     * @return
//     */
//    boolean addCacheSet(String key, Object... collections);
//
//    /**
//     * 删除Set集合的排序信息;
//     *
//     * @param cacheSet
//     * @return
//     */
//    boolean delTCacheSet(CacheSetParam cacheSet);
//
//    /**
//     * 删除Set集合的排序信息;
//     *
//     * @param cacheSet
//     * @return
//     */
//    boolean delCollectionCacheSet(CacheSetParam cacheSet);
//
//    /**
//     * 删除Set集合的排序信息;
//     *
//     * @param cacheSet
//     * @return
//     */
//    boolean delMapCacheSet(CacheSetParam cacheSet);
//
//    /**
//     * 从指定set的大集合数组中,分页获取主指页的数据的id数组,再通过id数据批量对应的对象的实现方法;
//     * 获取最大数据集合;(set,从set集合中获取0到500条数的集合)
//     *
//     * @return
//     */
//    Set<String> findMapCacheSet(String prefixSetKey, PagePlus page);
//
//    /**
//     * 从获取指定Set 通常是其他属性到主键的映射Set
//     *
//     * @return
//     */
//    Set<String> findCacheSet(String setKey);
//
//    /**
//     * 从指定set的大集合数组中,分页获取主指页的数据的id数组,再通过id数据批量对应的对象的实现方法;
//     * 获取最大数据集合;(set,从set集合中获取0到500条数的集合)
//     *
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    Map<String, T> findMapCacheSet(IBaseApi entity, String prefixSetKey, BasePage page);

    boolean addMapCacheByEntity(IBaseApi entity);

    /**
     * 缓存IBaseApi Object 类对象的下画线小写文称_map:+prefixMapKey为 map的查询key, mapKey
     * 为map的缓存key
     *
     * @param entity 缓存IBaseApi Object 类对象
     * @param mapKey prefixMapKey 缓存到redis中的map的最外面的key的后缀;
     * @param mapKey 为缓存到map的key;
     * @return
     */
    boolean addMapCacheByEntity(IBaseApi entity, Object mapKey);

    /**
     * 缓存IBaseApi Object 类对象的下画线小写文称_map:+prefixMapKey为 map的查询key, mapKey
     * 为map的缓存key
     *
     * @param values       缓存IBaseApi Object 类对象
     * @param mapPrefixKey mapPrefixKey 缓存到redis中的map的最外面的key的后缀;
     * @param mapKey       为缓存到map的key;
     * @return
     */
    boolean addMapCacheByValuse(String mapPrefixKey, Object mapKey, Object values);

    /**
     * 缓存IBaseApi Object 类对象的下画线小写文称_map:+prefixMapKey为 map的查询key, mapKey
     * 为map的缓存key
     *
     * @param map prefixMapKey 缓存到redis中的map的最外面的key的后缀;
     *            规范为:类对象的下画线小写文称_map:+自定义key
     * @param map mapKey 为缓存到map的key;
     * @return
     */
    boolean addMapCacheByMap(Class<T> clazz, Map<?, T> map);

    /**
     * 缓存IBaseApi Object 类对象的下画线小写文称_map:+prefixMapKey为 map的查询key, mapKey
     * 为map的缓存key
     *
     * @param map prefixMapKey 缓存到redis中的map的最外面的key的后缀;
     *            规范为:类对象的下画线小写文称_map:+自定义key
     * @param map mapKey 为缓存到map的key;
     * @return
     */
    boolean addMapCacheByMap(String mapPrefix, Map<?, T> map);

    /**
     * 通过类对象,
     *
     * @param entity
     * @return
     */
    T findMapCacheByEntity(IBaseApi entity);

    /**
     * 通过类对象,
     *
     * @param clazz
     * @param mapPrefixKey
     * @param mapKey
     * @return
     */
    T findMapCacheByKey(Class<T> clazz, String mapPrefixKey, Object mapKey);

    /**
     * 通过类对象,
     *
     * @param clazz
     * @param mapValue
     * @param mapKey
     * @return
     */
    <K, V> Map<K, V> findMapCacheByKV(Class<?> clazz, Class<K> mapKey, Class<V> mapValue);

    /**
     * 按map的前缀查找对象map集合;
     *
     * @param mapPrefixKey
     * @param mapKey
     * @param mapValue
     * @return
     */
    <K, V> Map<K, V> findMapCacheByKV(Object mapPrefixKey, Class<K> mapKey, Class<V> mapValue);

    /**
     * 通过批量的缓存map key
     * 的集合,到redis缓存中查找返回已存的值缓存map<k,v>中,并从请求mapKeyList集合中,删除已由到的key;
     * 如mapKeyList.size == 0 说明传过的k全部从缓存中查到对象的value值;
     *
     * @param clazz      返回map value中的对象类型;
     * @param mapKeyList map中的key的值的集合;
     * @return
     */
    <K, V> Map<K, V> findMapCacheByKList(Class<V> clazz, Collection<K> mapKeyList);

    /**
     * 通过批量的缓存map key
     * 的集合,到redis缓存中查找返回已存的值缓存map<k,v>中,并从请求mapKeyList集合中,删除已由到的key;
     * 如mapKeyList.size == 0 说明传过的k全部从缓存中查到对象的value值;
     *
     * @param prefixKey  map的缓存key
     * @param clazz      返回map value中的对象类型;
     * @param mapKeyList map中的key的值的集合;
     * @return
     */
    <K, V> Map<K, V> findMapCacheByKList(String prefixKey, Class<V> clazz, Collection<K> mapKeyList);

    /**
     * 删除map指定的key
     *
     * @param entity
     * @return
     */
    boolean delMapCache(IBaseApi entity);

    /**
     * 删除map指定的key
     *
     * @param entityClass
     * @param mapKey
     * @return
     */
    boolean delMapCache(Class<?> entityClass, Object mapKey);

    /**
     * 删除map指定的key
     *
     * @param mapPrefixpKey
     * @param mapKey
     * @return
     */
    boolean delMapCache(String mapPrefixpKey, Object mapKey);

    /**
     * <pre>
     * <b>@Title: 根据属性列表生成对象的Key</b>
     * <b>payment_bank:passport_id_678848_account_type_1</b>
     * <b>   类名        属性名     属性值      属性名   属性值</b>
     * <b>Description: TODO 获取对象o的fieldName属性值，不存在则抛出异常</b>
     * <pre>
     */
    String getKeyByFieldList(List<String> fieldList, Object o);

    /**
     * 通过自定义key,表对象，入参生成rediskey字符串，
     * 再通过md5生成字符串，并返回加密后的前16位字符串
     * eg: table:rediskey:100:200
     *
     * @param prefixKey 自定义rediskey 字符串,允许为空，但注意防止key相同
     * @param table     数据表对象的class类,,允许为空，但注意防止key相同
     * @param params    接口需求参数
     * @return
     */

    String getListForRedisKey(String prefixKey, Class<?> table, Object... params);
}
