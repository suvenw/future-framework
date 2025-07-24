package com.suven.framework.core.redis.ext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * <pre>
 * 
 * </pre>
 * @author suven  pf_qq@163.com
 * @version 1.00.00
 * <pre>
 * 
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface RedisEx {
	int redisKey()  default -1; //写指类key 标识RedisClusterEnum.indexKey
	int group()  default -1; //写到model类标识
	
}
