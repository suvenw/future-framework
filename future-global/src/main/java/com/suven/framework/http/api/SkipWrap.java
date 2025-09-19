package com.suven.framework.http.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * title: SkipWrap.java
 * 用于@RestController 返回对象,是否需要 跳过数据包装
 * 加入:表示跳过数据包装,默认不跳过
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SkipWrap {}