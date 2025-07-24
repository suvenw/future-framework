package com.suven.framework.core.expire;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 标示注解.
 * 针对redis api write method 起标示作用.保证缓存后置通知捕获.
 * @author summerao
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExSign {
	
}
