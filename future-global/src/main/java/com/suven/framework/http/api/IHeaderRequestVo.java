package com.suven.framework.http.api;


import java.util.Map;

/**
 * 框架http请求接口接收对象统一实现类
 */
public interface IHeaderRequestVo {




    /**
     * http json提交参数头部实现java反射类对象;
     * @param headersMap http头的聚合信息
     * @param clazz  反射接收类对象
     * @return
     */

    <T> T parseHeader(Map<String, Object> headersMap, Class<T> clazz) ;
}
