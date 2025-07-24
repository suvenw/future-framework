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
public @interface ApiDesc {

    String value()  default "字段说明描述"; //属性描述；
	int required() default 0;//"是否必须,0,1";
    String type() default "";// "参数据类型"
    int isHide() default 0;//"是否必须,0.不隐藏,1.隐藏";
	
}





