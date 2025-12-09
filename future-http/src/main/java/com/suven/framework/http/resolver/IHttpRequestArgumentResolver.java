package com.suven.framework.http.resolver;

import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public interface IHttpRequestArgumentResolver {

    /**
     * 字符节类型
     */
    final Charset CHARSET_CONTENT_UTF8 = StandardCharsets.UTF_8;
    /**
     * 判断请求类型,是不是post请求类型,方法是否读取json实现逻辑
     * @param request http 请求信息
     * @return boolean true/false
     */
    boolean isPostRequestMethod(HttpServletRequest request);

    /**
     *  获取 http  获取全部请求头参数,并做好兼容处理,全量将"_""-"替换成"",并且全部转小写,方便面兼容对象反射付值
     * @param request HttpServletRequest
     * true/false 是否需要兼容,并做好兼容处理,true: 全量将"_""-"替换成""
     * @return  Map<String, String>
     */
    default Map<String, String> getRequestHeadersBody(HttpServletRequest request) {
        return getRequestHeadersBody( request, true);
    }
    /**
     * 获取 http  获取全部请求头参数,并做好兼容处理,全量将"_""-"替换成"",并且全部转小写,方便面兼容对象反射付值
     *
     * @param request      HttpServletRequest
     * @param isCompatible true/false 是否需要兼容,并做好兼容处理,true: 全量将"_""-"替换成""
     * @return Map<String, String>
     */
    Map<String, String> getRequestHeadersBody(HttpServletRequest request, boolean isCompatible);

    /**
     * 获取 http json 请求的数据字符串
     *
     * @param request HttpServletRequest
     * @return String JSONString
     */
    String getRequestJsonBody(HttpServletRequest request);

    /**
     * 获取 http json 请求的数据字符串
     *
     * @param request HttpServletRequest
     * @return Map<String, Object> 表单请求对象
     */
    Map<String, Object> getRequestFormBody(HttpServletRequest request);
}
