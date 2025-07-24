package com.suven.framework.core.expire;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;


/**
 * cache expire注解
 * @author summerao
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface Expire {
	int value() default 0;
	TimeUnit unit() default TimeUnit.HOURS;
}
