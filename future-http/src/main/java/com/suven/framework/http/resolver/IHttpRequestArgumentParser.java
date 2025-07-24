package com.suven.framework.http.resolver;

import com.suven.framework.http.api.HttpRequestTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public interface IHttpRequestArgumentParser {
    /**
     * 通过 Http header http头部格式传输信息
     *
     * @param request             HttpServletRequest 请求信息
     * @param response            HttpServletResponse 请求信息
     * @param headerClass         接收请求头信息参数类
     * @param httpRequestTypeEnum 请求参数数据类型
     * @return 返回  接收请求头信息参数对象
     */
    <T> T parseHeaderData(HttpServletRequest request, HttpServletResponse response, Class<T> headerClass, HttpRequestTypeEnum httpRequestTypeEnum);

    /**
     * 通过 Http FORM 表单格式请求
     *
     * @param request             HttpServletRequest 请求信息
     * @param response            HttpServletResponse 请求信息
     * @param fromClass           接收表单请求信息参数类
     * @param httpRequestTypeEnum 请求参数数据类型
     * @return 返回  接收表单请求信息参数对象
     */
    <T> T parseFromData(HttpServletRequest request, HttpServletResponse response, Class<T> fromClass, HttpRequestTypeEnum httpRequestTypeEnum);

    /**
     * 通过 Http JSON 格式数据请求
     *
     * @param request             HttpServletRequest 请求信息
     * @param response            HttpServletResponse 请求信息
     * @param jsonClass           接收JSON请求信息参数类
     * @param httpRequestTypeEnum 请求参数数据类型
     * @return 返回  接收JSON请求信息参数对象
     */
    <T> T parseJsonData(HttpServletRequest request, HttpServletResponse response, Class<T> jsonClass, HttpRequestTypeEnum httpRequestTypeEnum);

    /**
     * http 表单提交实现java反射类对象;
     *
     * @param map       Map<String, Object> map 表单提交的数据
     * @param fromClass 表单提供参数 转换成对象类信息
     * @return T 返回转换对象
     */
    <T> T parseFrom(Map<String, Object> map, Class<T> fromClass) throws Exception;

    /**
     * http 提交参数头部实现java反射类对象;
     *
     * @param headersMap  http头的聚合信息
     * @param headerClass 反射接收类对象
     * @return T 返回转换对象
     */
    default <T> T parseHeader(Map<String, String> headersMap, Class<T> headerClass){
        return parseHeader(headersMap,headerClass,true);
    }


    /**
     * http 提交参数头部实现java反射类对象;
     *
     * @param headersMap  http头的聚合信息
     * @param headerClass 反射接收类对象
     * @return T 返回转换对象
     */
    <T> T parseHeader(Map<String, String> headersMap, Class<T> headerClass, boolean isCompatible);

    /**
     * http json提交参数实现java反射类对象;
     *
     * @param jsonString 反射字符串内容
     * @param jsonClass  反射接收类对象
     * @return T 返回转换对象
     */
    <T> T parseJson(String jsonString, Class<T> jsonClass);
}
