package com.suven.framework.core.redis;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.TypeSerializer;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.util.crypt.SignParam;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.util.json.StringFormat;
import com.suven.framework.util.tool.Splitable;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Title: Basethis.getRedisClientByKey(key).java
 * @author Joven.wang
 * @date 2015年2月10日
 * @version V1.0
 * Description: TODO(说明) 对象缓存统一模板实现类; 方法名,命名规范: 保存,更新:addCacheXxx ;
 *               查找:findCacheXxx; 删除:delCacheXxx
 * 
 */
@Service
@AllArgsConstructor
public abstract class RedisClientConvertEntityCache<T extends IBaseApi> implements RedisClientConvertEntity<T> {

	public Logger logger = LoggerFactory.getLogger(getClass());
	private final RedisClientServer redisClientServer;
	public RedisClientConvertEntityCache(RedisClientServer redisClient) {
		this.redisClientServer = redisClient;
	}

	/**
	 * @return RedisClientServer 代码接口实现
	 */
	@Override
	public RedisClientServer redisClient() {
		return redisClientServer;
	}



	public void setDateCache(String key, int seconds) {
		if (key == null)
			return;
		this.redisClient().expire(key, seconds);
	}

	/** 将查询结果缓存到redis 中，缓存时间为2秒**/
	@Override
	public boolean addResultToCache(String key, Object value) {
		if (key == null)
			return false;
		String data = JsonUtils.toJson(value);
		boolean  result = this.redisClient().setEx(key, data,RedisConstants.THREE_SECOND);
		return result;

	}

	/**直接根据key值获取String类型value*/
	@Override
	public String findResultByKey(String key){
		if (key == null){
			return null;
		}
		String value = this.redisClient().get(key);
		return value;
	}

	/**
	 * @Title:
	 * Description: redis中value为int值加1
	 * @param
	 * @return
	 * @author liulu
	 * @date 2018/5/31 11:17
	 */
	@Override
	public long increment(String key){
		if(StringUtils.isEmpty(key)){
			return 0;
		}
		return this.redisClient().increment(key);
	}

	/**
	 * redis中value为int值减1
	 * @param  key
	 */
	@Override
	public long decrement(String key){
		if(StringUtils.isEmpty(key)){
			return 0;
		}
		return this.redisClient().decrement(key);
	}

	/**
	 * @Title:
	 * Description: redis中value为int值加1,有效时间
	 * @param
	 * @return
	 * @author liulu
	 * @date 2018/5/31 11:17
	 */
	@Override
	public long increment(String key, int time, boolean refreshTime){
		if(StringUtils.isEmpty(key)){
			return 0;
		}
		return this.redisClient().increment(key,time,refreshTime);
	}

	/**
	 * @Title:
	 * Description: redis中value为int值减1
	 * @param
	 * @return
	 * @author liulu
	 * @date 2018/5/31 11:17
	 */
	public long decrement(String key,int time,boolean refreshTime){
		if(StringUtils.isEmpty(key)){
			return 0;
		}
		return this.redisClient().decrement(key,time,refreshTime);
	}


	@Override
	public  List<T> findResultByCache(String key, Class<T> clazz) {
		if (key == null)
			return new ArrayList<>();
		try {
			String result = this.redisClient().get(key);
			List<T> list = JsonUtils.toList(result,clazz);
			if(null == list){
				return new ArrayList<>();
			}
			return list;
		}catch (Exception e){
			logger.warn("Cached model Exception from findResultByCache key[{}] ,clazz[{}] ", key, clazz);
		}
		return new ArrayList<>();

	}

	/**
	 * 初始化缓存类对象的缓存KEY
	 *
	 * @param clazz
	 * @return
	 */
	@Override
	public String getPrefixKey(Class<?> clazz) {
		if (null == clazz) {
			logger.warn("Cached model entity clazz is null ");
			return null;
		}
		String key = StringFormat.underscoreName(clazz.getSimpleName()) + Splitable.DELIMITER_ARGS;
		return key;
	}

	/**
	 * 缓存key的参数值默认为对象的ID值;
	 *
	 * @param entity
	 * @return
	 */
	protected String getKeyVal(IBaseApi entity) {
		if (null == entity)
			return null;
		return String.valueOf(entity.getId());
	}

	/**
	 * 初始化缓存类对象的缓存KEY
	 *
	 * @param entity
	 * @return
	 */
	private String getEntityKey(IBaseApi entity) {
		if (null == entity) {
			logger.warn("Cached model entity clazz is null ");
			return null;
		}
		String key = getPrefixKey(entity.getClass());
		return key += this.getKeyVal(entity);
	}

	/**
	 * 初始化缓存类对象的缓存KEY
	 *
	 * @param clazz
	 * @return
	 */
	protected String getMapEntityKey(Class<?> clazz) {
		if (null == clazz) {
			logger.warn("Cached model getMapEntityKey clazz is null  or mapKey is null");
			return null;
		}
		String key = this.getPrefixKey(clazz) + "_map:";
		return key;
	}

	/**
	 * 添加对象到redis缓存中;
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean addCache(IBaseApi entity) {
		try {
			if (null == entity) {
				return false;
			}
			String key = getEntityKey(entity);
			if (null == key) {
				logger.warn("Cached model entity is key is null ");
				return false;
			}
			String bytes = TypeSerializer.parseString(entity);
			if (null == bytes) {
				logger.warn("add Cached model to pb bytes is null ");
				return false;
			}
			this.redisClient().setEx(key, bytes, RedisConstants.HALF_MONTH);
			return true;
		} catch (Exception e) {
			logger.warn("add Cached model to Exception := ", e);
		}
		return false;
	}

	/**
	 *
	 * @param entity
	 *            在自定义key:增加的参数值;
	 * @param list
	 *            批量插入的对象的信息;
	 * @return
	 */
	@Override
	public boolean addCacheList(IBaseApi entity, List<T> list) {
		return this.addCacheList(entity.getClass(), list);
	}

	/**
	 *
	 * @param claxx
	 *            在自定义key:增加的参数值;
	 * @param list
	 *            用于排序的字段值;
	 * @return
	 */
	@Override
	public boolean addCacheList(Class<?> claxx, List<T> list) {
		if (claxx == null || null == list || list.isEmpty()) {
			logger.warn("Cached model colle is null or colle isEmpty ");
			return false;
		}
		try {//
			Map<String, String > kvMap = new HashMap<>();
			for (T t : list) {
				String data = JsonUtils.toJson(t);
				kvMap.put(getKeyVal(t), data);
			}
			String prefix = this.getPrefixKey(claxx);
			this.redisClient().hmset(prefix, kvMap);
			return true;
		} catch (Exception e) {
			logger.error("Cached model colle Exception [{}] ",e);
		}
		return false;
	}

	@Override
	public boolean addCacheByKey(String cacheKey, IBaseApi entity) {
		 
		if (null == cacheKey) {
			logger.warn("Cached model entity is key is null ");
			return false;
		}
		return  addCacheByKey(cacheKey,entity,RedisConstants.HALF_MONTH);
	}
	/**
	 * 添加对象到redis缓存中;
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean addCacheByKey(String cacheKey, IBaseApi entity, int time) {
		 
		try {
			if (null == cacheKey) {
				logger.warn("Cached model entity is key is null ");
				return false;
			}
			
			String bytes = TypeSerializer.parseString(entity);
			if (null == bytes) {
				logger.warn("add Cached model to pb bytes is null ");
				return false;
			}
			this.redisClient().setEx(cacheKey, bytes, time);
			return true;
		} catch (Exception e) {
			logger.warn("add Cached model to Exception := ", e);
		}
		return false;
	}

	/**
	 * 添加对象到redis缓存中;
	 *
	 * @param cacheKey
	 * @return
	 */
	@Override
	public boolean addCacheByKeyBase(String cacheKey, Object o) {
		if (null == o) {
			logger.warn("Cached model entity is key is null ");
			return false;
		}
		if (null == o) {
			logger.warn("get by Cached  model entity is null ");
			return false;
		}
		return addCacheByKeyBase(cacheKey,o,RedisConstants.HALF_MONTH);
	}

	@Override
	public boolean addCacheByKeyBase(String cacheKey, Object o, int time){
		try {
			if (null == cacheKey) {
				logger.warn("Cached model entity is key is null ");
				return false;
			}
			if (null == o) {
				logger.warn("get by Cached  model entity is null ");
				return false;
			}
			String bytes = TypeSerializer.parseString(o);
			if (null == bytes) {
				logger.warn("add Cached model to pb bytes is null ");
				return false;
			}
			this.redisClient().setEx(cacheKey, bytes, time);
			return true;
		} catch (Exception e) {
			logger.warn("add Cached model to Exception := ", e);
		}
		return false;
	}

	// add,update,delete,get,findList,findPage

	/**
	 * 通过类对像标签索引字段查找缓存对象信息;
	 *
	 * @param entity
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T findCache(IBaseApi entity) {
		  
		try {

			if (null == entity) {
				logger.warn("get by Cached  model entity is null ");
				return null;
			}
			String key = this.getEntityKey(entity);
			if (null == key) {
				logger.warn("Cached model entity is key is null ");
				return null;
			}
			byte[] value = this.redisClient().getByte(key);
			if (null == value) {
				logger.warn("get by Cached bytes info is null ");
				return null;
			}
			T obj = (T)   TypeSerializer.parseObject(entity.getClass(), value);
			return obj;
		} catch (Exception e) {
			logger.warn("add Cached model to Exception := ", e);
		}
		return null;
	}

	/**
	 * 通过索引字段查找缓存对象信息;
	 *
	 * @param claxx
	 * @return
	 */
	@Override
	public T findCacheById(Class<T> claxx, long entityId) {
		return this.findCacheById(claxx, String.valueOf(entityId));
	}

	/**
	 * 通过索引字段查找缓存对象信息;
	 *
	 * @param claxx
	 * @return
	 */
	@Override
	public T findCacheById(Class<T> claxx, String entityId) {
		
		if (null == claxx) {
			logger.warn("get by Cached  model entity is null ");
			return null;
		}
		// String key = StringFormat.underscoreName(claxx.getSimpleName());
		String key = this.getPrefixKey(claxx);
		if (null == key) {
			logger.warn("Cached model entity is key is null ");
			return null;
		}
		key += entityId;
		byte[] value = this.redisClient().getByte(key);
		if (null == value) {
			logger.warn("get by Cached bytes info is null ");
			return null;
		}
		T obj =   TypeSerializer.parseObject(claxx, value);
		return obj;
	}

	/**
	 * 通过自己完成定义好的缓存key,查找相关类对象信息;
	 *
	 * @param cacheKey
	 *            自定义好的完整的缓存对应key(eg: key==player:123)
	 * @param clazz
	 *            (eg:ModelEntity.class)
	 * @return
	 */
	@Override
	public T findCacheByKey(String cacheKey, Class<T> clazz) {
		
		if (null == cacheKey || null == clazz) {
			logger.warn("get by Cached param in CachedKey is null OR clazz is null ");
			return null;
		}
		byte[] value = this.redisClient().getByte(cacheKey);
		if (null == value) {
			logger.warn("get by Cached bytes info is null ");
			return null;
		}
		T obj =   TypeSerializer.parseObject(clazz, value);
		return obj;
	}

	/**
	 * 通过自己完成定义好的缓存key,查找相关基本类;
	 *
	 * @param <V>
	 *
	 * @param cacheKey
	 *            自定义好的完整的缓存对应key(eg: key==player:123)
	 * @param clazz
	 *            (eg:ModelEntity.class)
	 * @return
	 * @return
	 */
	@Override
	public <V> V findCacheByKeyBase(String cacheKey, Class<V> clazz) {
		
		if (null == cacheKey || null == clazz) {
			logger.warn("get by Cached param in CachedKey is null OR clazz is null ");
			return null;
		}
		byte[] value = this.redisClient().getByte(cacheKey);
		if (null == value) {
			logger.warn("get by Cached bytes info is null ");
			return null;
		}
		V obj =   TypeSerializer.parseObject(clazz, value);
		return obj;
	}

	/**
	 * 类名:小写: 批量查找KV的对象的批量获取缓存列表集合信息;
	 *
	 * @param colle
	 * @return
	 */
	@Override
	public <K> Map<K, T> findMapCache(Class<T> clazz, Collection<K> colle) {
		
		String prefix = this.getPrefixKey(clazz);
		if (null == colle || null == prefix || null == clazz) {
			logger.warn("findMap by Cached param in CachedKey is null "
					+ "OR Collection Param is null OR return Class is null");
		}
		Collection<K> set = colle;
		if (!(colle instanceof Set)) {
			set = new HashSet<>(colle);
		}
		Map<K, T> map = this.redisClient().batchGet(clazz, prefix, set, false);
		return map;
	}

	/**
	 * 类名:小写: 批量查找KV的对象的批量获取缓存列表集合信息;
	 *
	 * @param clazz
	 *            反映的类对象
	 * @param colle
	 *            要搜索或查找的扩展id的集合;
	 * @param prefixKey
	 *            T对象的完整key的前缀; 实例: userKey:123 其中prefixKey== userKey
	 *            ,userId==123; 其中":" 是自动实现
	 * @return
	 */
	@Override
	public <K> Map<K, T> findMapCache(Class<T> clazz, String prefixKey, Collection<K> colle) {
		
		String prefix = this.getPrefixKey(clazz);
		if (null == colle || null == prefix || null == clazz) {
			logger.warn("findMap by Cached param in CachedKey is null "
					+ "OR Collection Param is null OR return Class is null");
		}
		Collection<K> set = colle;
		if (!(colle instanceof Set)) {
			set = new HashSet<>(colle);
		}
		Map<K, T> map = this.redisClient().batchGet(clazz, prefix, set, false);
		return map;
	}


	/**
	 * @Title:
	 * Description: 设备控制并发锁的的过期时间
	 * @param
	 * @return
	 */

	@Override
	public boolean expire(String cacheKey, int unixTime){
		boolean value = this.redisClient().expire(cacheKey,unixTime);
		return value ;
	}

	/**
	 * 删除指定的key的值;
	 *
	 * @param entity
	 *            IBaseApi
	 * @return
	 */
	@Override
	public boolean delCache(IBaseApi entity) {
		String prefix = getEntityKey(entity);
		if (null == prefix) {
			logger.warn("Cached model initCollPrefixKey is null ");
			return false;
		}
		this.redisClient().delete(prefix);
		return true;
	}

	/**
	 * 删除指定的key的值;
	 *
	 * @param cacheKey
	 *            IBaseApi
	 * @return
	 */
	@Override
	public boolean delCacheBase(String cacheKey) {
		if (null == cacheKey) {
			logger.warn("Cached model cacheKey is null ");
			return false;
		}
		this.redisClient().delete(cacheKey);
		return true;
	}

	/**
	 * 通过类名称和id删除缓存对象
	 *
	 * @param claxx
	 * @param entityId
	 * @return
	 */
	@Override
	public boolean delCache(Class<T> claxx, long entityId) {
		return this.delCache(claxx, String.valueOf(entityId));
	}

	/**
	 * 通过类名称和id删除缓存对象
	 *
	 * @param claxx
	 * @param entityId
	 * @return
	 */
	@Override
	public boolean delCache(Class<T> claxx, String entityId) {
		if (null == claxx) {
			logger.warn("Cached model Class<T> claxx is null ");
			return false;
		}
		String prefix = this.getPrefixKey(claxx);
		if (null == prefix) {
			logger.warn("Cached model sClass<T> claxx prefix is null ");
			return false;
		}
		prefix += entityId;
		this.redisClient().delete(prefix);
		return true;
	}

	/**
	 * 通过类名称和ids批量删除缓存对象
	 *
	 * @param claxx
	 * @param entityIds
	 * @return
	 */
	@Override
	public boolean delCache(Class<T> claxx, List<Long> entityIds) {
		if (null == claxx || entityIds == null) {
			logger.warn("Cached model Class<T> claxx is null ");
			return false;
		}
		String prefix = this.getPrefixKey(claxx);
		if (null == prefix) {
			logger.warn("Cached model sClass<T> claxx prefix is null ");
			return false;
		}
		String[] keys = new String[entityIds.size()];

		for (int i = 0; i < entityIds.size(); i++) {
			keys[i] = prefix + entityIds.get(i);
		}
		this.redisClient().delete(keys);
		return true;
	}

	/**
	 * 批量删除;
	 *
	 * @param set
	 *            Collection
	 * @return
	 */
	@Override
	public void delCache(Collection<String> set) {
		if (null == set || set.isEmpty()) {
			return;
		}
		this.redisClient().delete(set.toArray(new String[set.size()]));

	}

	/* ---------- redisSet 相关信息 --------------- */

//
//
//	/**
//	 *
//	 * @param cacheSet
//	 *            在自定义key:增加的参数值; 参加排序的集合;S
//	 * @return
//	 */
//	@Override
//	public boolean addMapCacheSet(CacheSetParam cacheSet) {
//		if (null == cacheSet || null == cacheSet.getMap() || cacheSet.getMap().isEmpty()) {
//			logger.warn("Cached model cacheSet is null or  cacheSet.getMap().isEmpty() ");
//			return false;
//		}
//		int ints = this.redisClient().hmset(cacheSet.getRedisPrefixKey(), cacheSet.getMap());
//		return ints > 0;
//	}
//
//	/**
//	 * 将一个或多个 collections 元素加入到集合 key 当中，已经存在于集合的 collections 元素将被忽略。
//	 * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
//	 * @param key
//	 * @param collections
//	 * @return
//	 */
//	@Override
//	public boolean addCacheSet(String key, Object... collections) {
//		if (null == collections || collections.length <= 0) {
//			logger.warn("Cached model cacheSet is null or  cacheSet.getMap().isEmpty() ");
//			return false;
//		}
//		String[] values = new String[collections.length];
//		int i = 0;
//		for (Object o : collections) {
//			values[i] = String.valueOf(o);
//		}
//		Long ints = this.redisClient().addSortedSet(key, values);
//		return ints > 0;
//	}
//
//
//	/**
//	 * 删除Set集合的排序信息;
//	 *
//	 * @param cacheSet
//	 * @return
//	 */
//	@Override
//	public boolean delTCacheSet(CacheSetParam cacheSet) {
//		if (null == cacheSet) {
//			logger.warn("CacheSetParam model cacheSet is null ");
//			return false;
//		}
//		if (null != cacheSet.getSetMemberKey()) {
//			long log = this.redisClient().zrem(cacheSet.getRedisPrefixKey(), cacheSet.getSetMemberKey());
//			return log > 0;
//		}
//		return false;
//	}
//
//	/**
//	 * 删除Set集合的排序信息;
//	 *
//	 * @param cacheSet
//	 * @return
//	 */
//	@Override
//	public boolean delCollectionCacheSet(CacheSetParam cacheSet) {
//		if (null == cacheSet) {
//			logger.warn("CacheSetParam model cacheSet is null ");
//			return false;
//		}
//		long log = this.redisClient().zrem(cacheSet.getRedisPrefixKey(), cacheSet.getColle());
//		return log > 0;
//	}
//
//	/**
//	 * 删除Set集合的排序信息;
//	 *
//	 * @param cacheSet
//	 * @return
//	 */
//	@Override
//	public boolean delMapCacheSet(CacheSetParam cacheSet) {
//		if (null == cacheSet || null == cacheSet.getMap() || cacheSet.getMap().isEmpty()) {
//			logger.warn("CacheSetParam model cacheSet is null or cacheSet.getMap() isEmpty() ");
//			return false;
//		}
//		String[] arr = StringFormat.toArray(cacheSet.getMap().keySet());
//		long log = this.redisClient().zrem(cacheSet.getRedisPrefixKey(), arr);
//		return log > 0;
//	}
//
//	/**
//	 * 从指定set的大集合数组中,分页获取主指页的数据的id数组,再通过id数据批量对应的对象的实现方法;
//	 * 获取最大数据集合;(set,从set集合中获取0到500条数的集合)
//	 *
//	 * @return
//	 */
//	@Override
//	public Set<String> findMapCacheSet(String prefixSetKey, BasePage page) {
//		if (null == prefixSetKey || null == page) {
//			logger.warn("Cached model prefixSetKey is null or page is null ");
//			return null;
//		}
//		Set<String> set = this.redisClient().zrange(prefixSetKey, page.getStart(), page.getZeroEndSize());
//		return set;
//	}
//
//	/**
//	 * 从获取指定Set 通常是其他属性到主键的映射Set
//	 * @return
//	 */
//	@Override
//	public Set<String> findCacheSet(String setKey) {
//		if(null == setKey){
//			return null;
//		}
//		Set<String> set = this.redisClient().smembers(setKey);
//		return set;
//	}
//
//	/**
//	 * 从指定set的大集合数组中,分页获取主指页的数据的id数组,再通过id数据批量对应的对象的实现方法;
//	 * 获取最大数据集合;(set,从set集合中获取0到500条数的集合)
//	 *
//	 * @return
//	 */
//	@Override
//	@SuppressWarnings("unchecked")
//	public Map<String, T> findMapCacheSet(IBaseApi entity, String prefixSetKey, BasePage page) {
//
//		if (null == page) {
//			logger.warn("Cached model  page is null ");
//			return null;
//		}
//		if (null == entity || prefixSetKey == null) {
//			logger.warn("Cached model entity is null");
//			return null;
//		}
//
//		Set<String> set = this.redisClient().zrange(prefixSetKey, page.getStart(), page.getZeroEndSize());
//		if (null == set || set.isEmpty()) {
//			logger.warn("find Cached by prefixSetKey and page to result is null  ");
//			return null;
//		}
//		String prefixTKey = this.getEntityKey(entity);
//		if (null == prefixSetKey) {
//			logger.warn("Cached model entity to prefixSetKey result is null ");
//			return null;
//		}
//		Map<String, T> map = (Map<String, T>) this.redisClient().mget(entity.getClass(), prefixTKey, set, false);
//		return map;
//	}

	// ----------------hash map---------------

	@Override
	public boolean addMapCacheByEntity(IBaseApi entity) {
		 
		if (null == entity) {
			logger.warn("Cached model IBaseApi entity  is null ");
			return false;
		}
		return addMapCacheByEntity(entity, entity.getId());
	}

	/**
	 * 缓存IBaseApi Object 类对象的下画线小写文称_map:+prefixMapKey为 map的查询key, mapKey
	 * 为map的缓存key
	 *
	 * @param entity
	 *            缓存IBaseApi Object 类对象
	 * @param mapKey
	 *            prefixMapKey 缓存到redis中的map的最外面的key的后缀;
	 * @param mapKey
	 *            为缓存到map的key;
	 * @return
	 */
	@Override
	public boolean addMapCacheByEntity(IBaseApi entity, Object mapKey) {
		 
		if (null == entity) {
			logger.warn("Cached model IBaseApi entity  is null ");
			return false;
		}
		String prefix = this.getMapEntityKey(entity.getClass());// 获取map的缓存key
		return addMapCacheByValuse(prefix, mapKey, entity);
	}

	/**
	 * 缓存IBaseApi Object 类对象的下画线小写文称_map:+prefixMapKey为 map的查询key, mapKey
	 * 为map的缓存key
	 *
	 * @param values
	 *            缓存IBaseApi Object 类对象
	 * @param mapPrefixKey
	 *            mapPrefixKey 缓存到redis中的map的最外面的key的后缀;
	 * @param mapKey
	 *            为缓存到map的key;
	 * @return
	 */
	@Override
	public boolean addMapCacheByValuse(String mapPrefixKey, Object mapKey, Object values) {
		if (null == mapPrefixKey) {
			logger.warn("Cached model IBaseApi entity  is null ");
			return false;
		}
		if (null == mapKey) {
			logger.warn("Cached model Object mapKey is null ");
			return false;
		}
		if (null == values) {
			logger.warn("Cached model Object values is null ");
			return false;
		}
		byte[] mapKeys = TypeSerializer.parseBytes(mapKey);
		byte[] value = TypeSerializer.parseBytes(values);
		long t = this.redisClient().hset(mapPrefixKey, mapKeys, value);
		return t > 0;
	}

	/**
	 * 缓存IBaseApi Object 类对象的下画线小写文称_map:+prefixMapKey为 map的查询key, mapKey
	 * 为map的缓存key
	 *
	 * @param map
	 *            prefixMapKey 缓存到redis中的map的最外面的key的后缀;
	 *            规范为:类对象的下画线小写文称_map:+自定义key
	 * @param map
	 *            mapKey 为缓存到map的key;
	 * @return
	 */
	@Override
	public boolean addMapCacheByMap(Class<T> clazz, Map<?, T> map) {
		 
		if (null == clazz) {
			logger.warn("Cached model Class<T> clazz is null ");
			return false;
		}
		if (null == map) {
			logger.warn("Cached model Map<Object,Object> map  is null ");
			return false;
		}
		String prefix = this.getMapEntityKey(clazz);
		return this.addMapCacheByMap(prefix, map);
	}

	/**
	 * 缓存IBaseApi Object 类对象的下画线小写文称_map:+prefixMapKey为 map的查询key, mapKey
	 * 为map的缓存key
	 *
	 * @param map
	 *            prefixMapKey 缓存到redis中的map的最外面的key的后缀;
	 *            规范为:类对象的下画线小写文称_map:+自定义key
	 * @param map
	 *            mapKey 为缓存到map的key;
	 * @return
	 */
	@Override
	public boolean addMapCacheByMap(String mapPrefix, Map<?, T> map) {
		 
		if (null == mapPrefix) {
			logger.warn("Cached model Class<T> clazz prefix is null ");
			return false;
		}
		if (null == map || map.isEmpty()) {
			logger.warn("Cached key  Map<Object,Object> map  is null ");
			return false;
		}
		final Map<byte[], byte[]> bhash = new HashMap<byte[], byte[]>(map.size());
		for (final Map.Entry<?, T> entry : map.entrySet()) {
			bhash.put(TypeSerializer.parseBytes(entry.getKey()), TypeSerializer.parseBytes(entry.getValue()));
		}
		byte[] mapKey = TypeSerializer.parseBytes(mapPrefix);
		this.redisClient().hmset(mapKey, bhash);
		return true;
	}

	/**
	 * 通过类对象,
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public T findMapCacheByEntity(IBaseApi entity) {
		
		if (null == entity) {
			logger.warn("Cached model findMapCacheByEntity entity is null ");
			return null;
		}
		String prefix = this.getMapEntityKey(entity.getClass());
		Class<T> t = (Class<T>) entity.getClass();
		return findMapCacheByKey(t, prefix, entity.getId());
	}

	/**
	 * 通过类对象,
	 *
	 * @param clazz
	 * @param mapPrefixKey
	 * @param mapKey
	 * @return
	 */
	@Override
	public T findMapCacheByKey(Class<T> clazz, String mapPrefixKey, Object mapKey) {
		
		if (null == mapKey) {
			logger.warn("Cached model findMapCacheByKey Object mapKey is null ");
			return null;
		}
		if (null == clazz || null == mapPrefixKey) {
			logger.warn("Cached model clazz or mapPrefixKey  is null ");
			return null;
		}
		T t = this.redisClient().hgetT(mapPrefixKey, String.valueOf(mapKey), clazz);
		return t;
	}

	/**
	 * 通过类对象,
	 *
	 * @param clazz
	 * @param mapValue
	 * @param mapKey
	 * @return
	 */
	@Override
	public <K, V> Map<K, V> findMapCacheByKV(Class<?> clazz, Class<K> mapKey, Class<V> mapValue) {
		
		if (null == clazz) {
			logger.warn("Cached model findMapCacheByKey Object mapKey is null ");
			return null;
		}
		if (null == mapKey || null == mapValue) {
			logger.warn("Cached model mapKey or mapValue  is null ");
			return null;
		}
		String prefix = this.getMapEntityKey(clazz);
		if (null == prefix) {
			logger.warn("Cached model getMapEntityKey prefix is null ");
			return null;
		}
		return findMapCacheByKV(prefix, mapKey, mapValue);
	}

	/**
	 * 按map的前缀查找对象map集合;
	 *
	 * @param mapPrefixKey
	 * @param mapKey
	 * @param mapValue
	 * @return
	 */
	@Override
	public <K, V> Map<K, V> findMapCacheByKV(Object mapPrefixKey, Class<K> mapKey, Class<V> mapValue) {
		
		if (null == mapPrefixKey) {
			logger.warn("Cached model findMapCacheByKV Object mapPrefixKey is null ");
			return null;
		}
		byte[] prefixByte = TypeSerializer.parseBytes(mapPrefixKey);
		Map<byte[], byte[]> map = this.redisClient().hgetAll(prefixByte);
		if (null != map && !map.isEmpty()) {
			Map<K, V> rmap = new LinkedHashMap<>();
			Iterator<Map.Entry<byte[], byte[]>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<byte[], byte[]> entry = it.next();
				if (null != entry.getKey() && entry.getKey().length > 0) {
					K k =   TypeSerializer.parseObject(mapKey, entry.getKey());
					V v =   TypeSerializer.parseObject(mapValue, entry.getValue());
					if (null != k) {
						rmap.put(k, v);
					}
				}
			}
			return rmap;
		}
		return null;
	}

	/**
	 * 通过批量的缓存map key
	 * 的集合,到redis缓存中查找返回已存的值缓存map<k,v>中,并从请求mapKeyList集合中,删除已由到的key;
	 * 如mapKeyList.size == 0 说明传过的k全部从缓存中查到对象的value值;
	 *
	 * @param clazz
	 *            返回map value中的对象类型;
	 * @param mapKeyList
	 *            map中的key的值的集合;
	 * @return
	 */
	@Override
	public <K, V> Map<K, V> findMapCacheByKList(Class<V> clazz, Collection<K> mapKeyList) {
		
		if (null == clazz) {
			logger.warn("Cached model findMapCacheByKList Class clazz is null ");
			return null;
		}
		if (null == mapKeyList) {
			logger.warn("Cached model findMapCacheByKList Collection<K> mapKeyList is null ");
			return null;
		}
		String prefixKey = this.getMapEntityKey(clazz);
		Map<K, V> map = this.redisClient().hmget(prefixKey, mapKeyList, clazz);
		return map;
	}

	/**
	 * 通过批量的缓存map key
	 * 的集合,到redis缓存中查找返回已存的值缓存map<k,v>中,并从请求mapKeyList集合中,删除已由到的key;
	 * 如mapKeyList.size == 0 说明传过的k全部从缓存中查到对象的value值;
	 *
	 * @param prefixKey
	 *            map的缓存key
	 * @param clazz
	 *            返回map value中的对象类型;
	 * @param mapKeyList
	 *            map中的key的值的集合;
	 * @return
	 */
	@Override
	public <K, V> Map<K, V> findMapCacheByKList(String prefixKey, Class<V> clazz, Collection<K> mapKeyList) {
		
		if (null == prefixKey) {
			logger.warn("Cached model findMapCacheByKList String prefixKey is null ");
			return null;
		}
		if (null == clazz) {
			logger.warn("Cached model findMapCacheByKList Class clazz is null ");
			return null;
		}
		if (null == mapKeyList) {
			logger.warn("Cached model findMapCacheByKList Collection<K> mapKeyList is null ");
			return null;
		}
		Map<K, V> map = this.redisClient().hmget(prefixKey, mapKeyList, clazz);
		return map;
	}

	/**
	 * 删除map指定的key
	 *
	 * @param entity
	 * @return
	 */
	@Override
	public boolean delMapCache(IBaseApi entity) {
		if (null == entity) {
			logger.warn("Cached model IBaseApi entity is null ");
			return false;
		}
		String prefix = this.getMapEntityKey(entity.getClass());
		if (null == prefix) {
			logger.warn("Cached model delMapCache prefix is null ");
			return false;
		}
		this.redisClient().hdel(prefix, String.valueOf(entity.getId()));
		return false;
	}

	/**
	 * 删除map指定的key
	 *
	 * @param entityClass
	 * @param mapKey
	 * @return
	 */
	@Override
	public boolean delMapCache(Class<?> entityClass, Object mapKey) {
		if (null == entityClass) {
			logger.warn("Cached model Class entityClass is null ");
			return false;
		}
		String prefix = this.getMapEntityKey(entityClass);
		if (null == prefix) {
			logger.warn("Cached model delMapCache mapKey is null ");
			return false;
		}
		this.redisClient().hdel(prefix, String.valueOf(mapKey));
		return false;
	}

	/**
	 * 删除map指定的key
	 *
	 * @param mapPrefixpKey
	 * @param mapKey
	 * @return
	 */
	@Override
	public boolean delMapCache(String mapPrefixpKey, Object mapKey) {
		if (null == mapPrefixpKey) {
			logger.warn("Cached model getMapEntityKey prefix is null ");
			return false;
		}
		this.redisClient().hdel(mapPrefixpKey, String.valueOf(mapKey));
		return false;
	}

	/**
	 * @Title: getFieldValueByName
	 * Description: TODO 获取对象o的fieldName属性值，不存在则抛出异常
	 */
	private Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter);
			Object value = method.invoke(o);
			return value;
		} catch (Exception e) {
			throw new SystemRuntimeException(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
		}
	}

	/**
	 * <pre>
	 * <b>@Title: 根据属性列表生成对象的Key</b>
	 * <b>payment_bank:passport_id_678848_account_type_1</b>
	 * <b>   类名        属性名     属性值      属性名   属性值</b>
	 * <b>Description: TODO 获取对象o的fieldName属性值，不存在则抛出异常</b>
	 * <pre>
	 */
	@Override
	public String getKeyByFieldList(List<String> fieldList, Object o) {
		if (null == fieldList) {
			logger.warn("Cached model fieldList List is null ");
			return null;
		}
		String prefixKey = getPrefixKey(Object.class);
		for (String fieldStr : fieldList) {
			try {
				Field field = Object.class.getDeclaredField(fieldStr);
				String value = String.valueOf(getFieldValueByName(field.getName(), o));
				prefixKey = prefixKey + StringFormat.underscoreName(field.getName()) + Splitable.SPLIT_UNDER_LINE
						+ value + Splitable.SPLIT_UNDER_LINE;
			} catch (NoSuchFieldException | SecurityException e) {
				throw new SystemRuntimeException(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
			}
		}
		prefixKey = prefixKey.substring(0, prefixKey.length() - 1);
		return prefixKey;
	}

	/**
	 * 通过自定义key,表对象，入参生成rediskey字符串，
	 * 再通过md5生成字符串，并返回加密后的前16位字符串
	 * eg: table:rediskey:100:200
	 * @param prefixKey 自定义rediskey 字符串,允许为空，但注意防止key相同
	 * @param table 数据表对象的class类,,允许为空，但注意防止key相同
	 * @param params 接口需求参数
	 * @return
	 */

	@Override
	public String getListForRedisKey(String prefixKey, Class<?> table, Object... params){
		boolean isPrefix = true;
		if( null == prefixKey){
			prefixKey = "";
			isPrefix = false;
		}
		if (null == table) {
			logger.warn("Cached model Class table is null ");
			return null;
		}
		String classsKey = this.getPrefixKey(table);
		String paramsKey = "";
		if(null != params){
			for (Object param : params) {
				if(!isPrefix){
					paramsKey +=  String.valueOf(param);
					isPrefix = true;
				}else {
					paramsKey += Splitable.DELIMITER_ARGS + String.valueOf(param);
				}
			}
		}
		String redisKey = SignParam.paramMd5By16(classsKey + paramsKey);
		return prefixKey + redisKey;
	}
}


