package com.suven.framework.http.interceptor;

import com.suven.framework.common.enums.IEnum;
import com.suven.framework.core.IterableConvert;

import java.util.Map;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *
 * <pre>
 * @description (说明):
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * date 创建时间: 2023-12-15
 **/
public enum HandlerInterceptorEnum  implements IEnum<Integer,String>,InterceptorOrderValue{

    HANDLER_ORDER_TRACE_ID(1,HANDLER_VALIDATOR_TRACE_ID,TraceIdHandlerInterceptor.class,"日志跟踪打印处理器"),	//普通用户（没房间号）
    HANDLER_ORDER_URL(2,HANDLER_VALIDATOR_URL,UrlHandlerInterceptor.class,"uri管理处理器"),
    HANDLER_ORDER_HEADERS(3,HANDLER_VALIDATOR_HEADERS,HeadersHandlerInterceptor.class,"公共参数处理器"),
    HANDLER_ORDER_WHITE(4,HANDLER_VALIDATOR_WHITE,UrlWhiteHandlerInterceptor.class,"url白名单处理器"),

    HANDLER_ORDER_BLACK(5,HANDLER_VALIDATOR_BLACK,BlackHandlerInterceptor.class,"黑名单管理处理器"),
    HANDLER_ORDER_PARAMETER(6,HANDLER_VALIDATOR_PARAMETER,ParameterHandlerInterceptor.class,"参数校验处理器"),
    HANDLER_ORDER_PARAMETER_SIGN(7,HANDLER_VALIDATOR_PARAMETER_SIGN,ParameterSignHandlerInterceptor.class,"参数防篡改处理器"),
    HANDLER_ORDER_TOKEN(5,HANDLER_VALIDATOR_TOKEN,TokenHandlerInterceptor.class,"用户token验证处理器"),
    HANDEL_ORDER_VERSION(12,HANDLER_VALIDATOR_VERSION, VersionHandlerInterceptor.class,"Redis结果缓存管理处理器"),
    HANDLER_ORDER_TENANT(10,HANDLER_VALIDATOR_TENANT,TenantHandlerInterceptor.class,"多租户管理处理器"),
    HANDEL_ORDER_REDIS_CACHE(11,HANDLER_VALIDATOR_REDIS,RedisCacheHandlerInterceptor.class,"Redis结果缓存管理处理器"),

    HANDEL_ORDER_JWT(100,HANDLER_VALIDATOR_JWT,JwtHandlerInterceptor.class,"后台管理系统处理器"),
    HANDLER_ORDER_ROLE(101,HANDLER_VALIDATOR_ROLE,RoleHandlerInterceptor.class,"角色校验处理器"),
    HANDLER_ORDER_DIRECTORY(102,HANDLER_VALIDATOR_DIRECTORY, DirectoryHandlerInterceptor.class,"目录处理器"),






            ;

    private int index;
    private String value;
    private Class<?>  handler;
    private String desc;



    private static Map<Integer, HandlerInterceptorEnum> enumsMap;

    static {
        enumsMap = IterableConvert.convertMap(values(), IEnum::getIndex);
    }

    private HandlerInterceptorEnum(int index,  String value,Class<?> handler, String desc) {
        this.index = index;
        this.value = value;
        this.handler = handler;
        this.desc = desc;
    }

    public static HandlerInterceptorEnum getEnum(int index){
        return enumsMap.get(index);
    }
    /**
     * 枚举索引编号,枚举的默认编号为int类型
     *
     * @return
     */
    @Override
    public Integer getIndex() {
        return index;
    }

    /**
     * 业务自定义的枚举字符串
     *
     * @return
     */
    @Override
    public String getValue() {
        return value;
    }

    public Class<?>  getHandler() {
        return handler;
    }

    /**
     * 业务自定义的枚举字符串的描述说明
     *
     * @return
     */
    @Override
    public String getDesc() {
        return desc;
    }
}
