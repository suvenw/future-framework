package com.suven.framework.gateway.common;

/**
 * Gateway 常量定义
 */
public class GatewayConstant {

    /** 请求开始时间属性 key */
    public static final String REQUEST_START_TIME = "requestStartTime";

    /** 网关请求标识头 */
    public static final String HEADER_GATEWAY_FROM = "X-Gateway-From";

    /** 用户ID请求头 */
    public static final String HEADER_USER_ID = "X-User-Id";

    /** 用户名请求头 */
    public static final String HEADER_USER_NAME = "X-User-Name";

    /** JWT Token 请求头 */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    /** JWT Token 前缀 */
    public static final String TOKEN_PREFIX = "Bearer ";

    /** 响应内容类型 */
    public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

    /** 限流key前缀 */
    public static final String RATE_LIMITER_KEY_PREFIX = "gateway:rate_limiter:";

    /** 默认限流容量 */
    public static final int DEFAULT_RATE_CAPACITY = 100;

    /** 默认限流速率 */
    public static final int DEFAULT_RATE_TOKENS = 10;

    private GatewayConstant() {
        // 禁止实例化
    }
}
