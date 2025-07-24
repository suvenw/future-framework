package com.suven.framework.http.handler;

import com.suven.framework.http.api.HttpRequestTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;


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
 * @date 创建时间: 2023-12-15
 **/
public interface IHandlerMethodArgumentResolver extends HandlerMethodArgumentResolver {
    /**
     * 通过获取请求头的Content-Type类型,值是包含 "application/json" 字符串信息
     *      * 用于判断请求过来的参数格式,如果包含则认为是json格式,
     * @param webRequest HttpServletRequest 网络请求对象
     * @param response HttpServletResponse 网络请求对象
     * @param clazz
     * @param contentTypeEnum
     * @return
     */
    Object parserDate(HttpServletRequest webRequest, HttpServletResponse response, Class<?> clazz, HttpRequestTypeEnum contentTypeEnum);
}
