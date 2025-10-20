package com.suven.framework.http.api;






import java.lang.annotation.*;

/**
 * @author joven
 * @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
 * @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
 * ---------------------

 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Documented//说明该注解将被包含在javadoc中  
public @interface ApiDoc {

    String group() default ""; //接口分组名称
    String groupDesc() default ""; //接口分组描述
    String  description()  default "";//接口描述
    String module() default "接口类说明"; //java类描述；
	String value() default "接口说明"; //属性描述；
    String  author() default "作者"; //作者名称;
    boolean isApp() default false; //； 是否是app前端业务接口
    String cmd() default "";//socket类型接口,自定义的功能码id
    String methodType()  default "";//接口请求类型;默认是ws
    Class<?>[] request() default Object.class;
    Class<?>[] response() default Object.class;//返回结果实现类
    RequestEnum requestBody() default RequestEnum.JSON;
    RequestMethod method() default RequestMethod.GET; // 修改为String类型，使用字符串常量


}





