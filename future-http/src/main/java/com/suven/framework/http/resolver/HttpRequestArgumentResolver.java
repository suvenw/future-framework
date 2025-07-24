package com.suven.framework.http.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *
 * <pre>
 * @ description (说明):
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @ date 创建时间: 2024-01-02
 **/

public class HttpRequestArgumentResolver implements IHttpRequestArgumentResolver {
    static final Logger log = LoggerFactory.getLogger(HttpRequestArgumentResolver.class);


    /**
     * 判断请求类型,是不是post请求类型,方法是否读取json实现逻辑
     * @param request http 请求信息
     * @return boolean true/false
     */
    @Override
    public boolean isPostRequestMethod(HttpServletRequest request){
        boolean isPost = request.getMethod().equals(RequestMethod.POST.name());
        return isPost;
    }



    /**
     *  获取 http  获取全部请求头参数,并做好兼容处理,全量将"_""-"替换成"",并且全部转小写,方便面兼容对象反射付值
     * @param request HttpServletRequest
     * @param isCompatible  true/false 是否需要兼容,并做好兼容处理,true: 全量将"_""-"替换成""
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> getRequestHeadersBody(HttpServletRequest request, boolean isCompatible) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key =  headerNames.nextElement();
            String value = request.getHeader(key);
            if(isCompatible) {
                key = key.replace("-", "").replace("_", "").toLowerCase();
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     *  获取 http json 请求的数据字符串
     * @param request HttpServletRequest
     * @return String JSONString
     */
    @Override
    public String getRequestJsonBody(HttpServletRequest request) {
        String json = "";
        boolean isPost = request.getMethod().equals(RequestMethod.POST.name());
        if(!isPost){
            return json;
        }
        try {
            boolean isClosed  = request.getInputStream().available() == 0;
            if (isClosed){
                return json;
            }
            json = IOUtils.toString(request.getInputStream(), CHARSET_CONTENT_UTF8);
            return json;
        }catch (Exception e){
            log.info("HttpServletRequestParser getRequestJsonBody parameter Exception ", e );
        }
        return json;

    }

    /**
     *  获取 http json 请求的数据字符串
     * @param request HttpServletRequest
     * @return  Map<String, Object> 表单请求对象
     */
    @Override
    public  Map<String, Object> getRequestFormBody(HttpServletRequest request) {
        try {
            Map<String, Object> map = new HashMap<>();
            Map<String, String[]>  parameterMap = request.getParameterMap();
            parameterMap.forEach(map::put);
            return map;
        }catch (Exception e){
            log.info("HttpServletRequestParser getRequestFormBody parameter Exception ", e );
        }
        return Collections.emptyMap();

    }
}
