package com.suven.framework.http.api;


import java.util.Map;

/**
 * 框架http请求接口接收对象统一实现类
 */
public interface IRequestVo {

    /**
     * http 表单提交实现java反射类对象;
     * @param map
     * @param clazz
     * @return
     */
    <T> T parseFrom(Map<String, Object>  map, Class<T> clazz) throws Exception;


    /**
     * http json提交参数实现java反射类对象;
     * @param jsonString 反射字符串内容
     * @param clazz  反射接收类对象
     * @return
     */

    <T> T parseJson(String jsonString, Class<T> clazz);


    /**
     * http json提交参数头部实现java反射类对象;
     * @param headersMap http头的聚合信息
     * @param clazz  反射接收类对象
     * @return
     */

    <T> T parseHeader(Map<String,String> headersMap, Class<T> clazz) ;

}
