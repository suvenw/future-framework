package com.suven.framework.http.processor.url.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author vincentdeng
 * 此注解加在UrlCommand类里面
 * 用途：
 * 1.是否需要现网测试，请求方式
 * 2.是否需要加入白名单
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface UrlRemote {
//	String value() default "http://acshow. XXX.com/show7";
//    String value() default "127.0.0.1";
	boolean isWhite() default true; //true 代表加入白名单 默认加入白名单
	boolean mustCheck() default false; //true 一定需要登录验证 false 不需要验证
	boolean isParamSign() default true;//true 接口需要做参数加密
//	boolean isPrint() default true;//true 是否打印请求参数日志,默认不打印
	String  excludeParam() default ""; //排除请求参数字段,不参与加密,多个字段属性于,用","隔开;
}