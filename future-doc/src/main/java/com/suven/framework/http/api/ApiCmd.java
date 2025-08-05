package com.suven.framework.http.api;



import java.lang.annotation.*;

/**
 * @author joven
 * @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
 *     @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
 * ---------------------

 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Documented//说明该注解将被包含在javadoc中  
public @interface ApiCmd {

    String cmd() default "";
    String methodType()  default "ws";//接口请求类型;默认是ws

}





