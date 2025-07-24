package com.suven.framework.core.redis.ext;

import com.suven.framework.common.constants.ReflectionsScan;
import com.suven.framework.core.redis.RedisConstants;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author suven.wang
 * @version 1.00.00
 * 
 * <pre>
 * 
 * </pre>
 */
@Component
public class ExpansionDeal {
	private  Map<String, RedisEx> map = new ConcurrentHashMap<>();// 第一个key为项目分类key,第二个key为url对应值;
	private  Map<String, Integer> nullMap = new ConcurrentHashMap<>();// 第一个key为项目分类key,第二个key为url对应值;
	private Logger logger = LoggerFactory.getLogger(getClass());


	@PostConstruct
	public void init() {
		Set<Class<?>> classList =  ReflectionsScan.reflections.getTypesAnnotatedWith(RedisEx.class);
		for (Class<?> cls : classList) {
			annoValue(RedisEx.class, cls);
			// tranValue(annoMap);
		}
	}


	/**
	 * 组装每个项目key
	 * 
	 * @param annoType
	 * @param cls
	 * @return
	 */
	private void annoValue(Class<? extends Annotation> annoType, Class<?> cls) {

		RedisEx head = getExansion(annoType, cls);
//		if (head != null && head.group()  != RedisConstants.REDIS_NONE) {
//		if (head != null) {
//			String tableName = StringFormat.underscoreName(cls.getSimpleName())+":";
//			map.put(tableName, head);
//			return ;
//		}
		
		Field[] fields = cls.getFields();
		for (Field field : fields) {
			RedisEx eanno = (RedisEx) AnnotationUtils.getAnnotation(field,annoType);
			if (!(field.getType() == String.class)) {
				continue;
			}
			appKeyToMap(eanno, field);
		}
	}

	/**
	 * @param annoType
	 * @param cls
	 */
	public RedisEx getExansion(Class<? extends Annotation> annoType,
			Class<?> cls) {
		Annotation head = AnnotationUtils.findAnnotation(cls, annoType);

		if (head == null) {
			return null;
		}
		RedisEx ex = (RedisEx) head;
		return ex;
	}

	private void appKeyToMap(RedisEx eanno, Field field) {
		try {
			String expireStr = FieldUtils.readStaticField(field).toString();
			if (eanno == null) {
				nullMap.put(expireStr, RedisConstants.REDIS_ZERO);
			}else if (!map.containsKey(expireStr)) {
				map.put(expireStr, eanno);
			} else {
				throw new RuntimeException(
						" Redis key Class RedisEx project field key["+expireStr+"]is exit ,please to init param ");
			}
		} catch (Exception e) {
			logger.warn("RedisEx FieldUtils.readStaticField", e);
		}
	}



	public  int getRedisInstance(String cacheKey){
		String mapkey = prefixCacheKey(cacheKey);
		RedisEx ex = map.get(mapkey);
		if (null == ex ) {
			Integer dex = nullMap.get(mapkey);
			if(null != dex && dex == 0){
				return dex;
			}
			return RedisConstants.REDIS_NONE;
		}
		return ex.redisKey();
	}
	/**
	 * 过滤增加的类型;获取redis key 前缀的方法
	 * @param cacheKey
	 * @return
	 */
	private String prefixCacheKey(String cacheKey){
		Pattern pattern = Pattern.compile("\\d+$");
		Matcher matcher = pattern.matcher(cacheKey);
		if(matcher.find()){
			int index = cacheKey.indexOf(":");
			if(index > 0){
				return cacheKey.substring(0, index+1);
			}else {
				return cacheKey.substring(0, cacheKey.lastIndexOf("_")+1);
			}

		}
		return cacheKey;
	}

}
