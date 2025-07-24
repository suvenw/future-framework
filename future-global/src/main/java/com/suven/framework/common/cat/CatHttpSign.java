package com.suven.framework.common.cat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * http請求 监控注解。
 * @author summerao
 *
 */

/**
 *
 * <pre>
 *
 * </pre>
 * @author suven.wang
 * @version 1.00.000
 * <pre>
 * @desc:    cat 监控获取 http请求 对应执行方法上的 监控注解,用于统计http接口方法执行时长统计
 * </pre>
 */
@Target(value={ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CatHttpSign {
}