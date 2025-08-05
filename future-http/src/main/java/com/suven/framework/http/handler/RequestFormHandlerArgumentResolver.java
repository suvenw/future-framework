package com.suven.framework.http.handler;


import com.suven.framework.http.api.HttpFromRequest;
import com.suven.framework.http.api.HttpRequestTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;



/**
 * Title: RequestFormHandlerArgumentResolver.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口统一表单请求方法参数实现转换逻辑实现业务类;
 */

public class RequestFormHandlerArgumentResolver extends AbstractHandlerArgumentResolver<HttpFromRequest> {

    @Override
    protected Object onResolve(MethodParameter parameter,
                               NativeWebRequest webRequest,
                               ModelAndViewContainer mavContainer,
                               WebDataBinderFactory binderFactory)  throws Exception {

        mavContainer.setRequestHandled(true);
        if(parameter.getParameterType() == HttpServletRequest.class){
            return null;
        }
        /**
         *  获取http请求信息的实现方法记录
         * HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
         * HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        **/

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        HttpRequestTypeEnum httpRequestTypeEnum = HttpRequestTypeEnum.TYPE_FORM;
        Class<?> requestHeaderClass = parameter.getParameterType();
        Object  result = this.parserDate(request,response, requestHeaderClass,httpRequestTypeEnum);
        return result;

    }

    @Override
    public Object parserDate(HttpServletRequest webRequest, HttpServletResponse response, Class<?> clazz, HttpRequestTypeEnum httpRequestTypeEnum) {
        return this.parseFromData(webRequest,response,clazz,httpRequestTypeEnum);
    }
}