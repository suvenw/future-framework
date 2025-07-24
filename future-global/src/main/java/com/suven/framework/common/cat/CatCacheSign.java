package com.suven.framework.common.cat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <pre>
 *
 * </pre>
 * @author suvenwang  
 * @version 1.00.00
 * <pre>
 * @desc:  cat 监控 获取 Redis api 执行方法上的 监控注解标签,用于统计redis 方法执行时长统计
 * </pre>
 */
@Target(value={ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CatCacheSign {
    String service() default "";
}

