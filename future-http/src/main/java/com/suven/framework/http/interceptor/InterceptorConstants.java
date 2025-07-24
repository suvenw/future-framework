package com.suven.framework.http.interceptor;

public interface InterceptorConstants {

    final String  ENABLED = ".enabled";
    /** link 日志采摘 配置开关规范 **/
    final String SAAS_CLOUD_CONFIG = "saas.cloud.config.log";
    final String SAAS_CLOUD_CONFIG_ENABLED = SAAS_CLOUD_CONFIG+ENABLED;

    /** HandlerInterceptorConfigSetting 服务拦截启动器 配置开关规范 **/
    final String SAAS_CLOUD_HANDLER_CONFIG = "saas.cloud.config.handler";
    final String SAAS_CLOUD_HANDLER_CONFIG_ENABLED = SAAS_CLOUD_HANDLER_CONFIG+ENABLED;


}
