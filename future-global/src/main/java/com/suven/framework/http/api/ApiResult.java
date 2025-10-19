package com.suven.framework.http.api;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author joven
 * @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
 * @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
 * ---------------------

 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Documented//说明该注解将被包含在javadoc中  
public @interface ApiResult {
    boolean skip() default false; //； 是否跳转统一数据包装,设置成true,则跳过统一数据包装

}





