package com.suven.framework.http.handler;


import com.google.common.reflect.TypeToken;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.BeanProperty;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.resolver.HttpRequestArgumentParser;
import com.suven.framework.http.resolver.HttpRequestArgumentUserHeader;
import jakarta.servlet.http.HttpServletRequest;
import org.checkerframework.checker.units.qual.K;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 对Controller中的入参进行转换
 * 具体的转换逻辑由子类实现 HandlerMethodArgumentResolver
 * Created by Alex on 2014/4/28
 */
public abstract class AbstractHandlerArgumentResolver<T> extends HttpRequestArgumentParser implements IHandlerMethodArgumentResolver {

    final static Logger log = LoggerFactory.getLogger(AbstractHandlerArgumentResolver.class);
    final TypeToken<T> type = new TypeToken<T>(getClass()) {
    };


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
//        System.err.println("AbstractHandlerArgumentResolver  supportsParameter method 5 ...." );
        return type.getRawType().isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
//        System.err.println("AbstractHandlerArgumentResolver  resolveArgument method 6 ...." );
        Object result = onResolve(parameter, webRequest, mavContainer, binderFactory);
        if (result == null) {
            log.error("转换参数失败");
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_CHECK);
        }

        return result;
    }


    /**
     * 通过BeanProperty工具类设置属性
     * 1. 先从propertiesMap设置属性值
     * 2. 再从HttpRequestArgumentUserHeader复制属性值（如果目标对象存在相同属性）
     * @param request HTTP请求对象
     * @param object 目标对象
     * @return object 加强后的对象
     */
    protected Object onResolveBeanProperty(HttpServletRequest request, Object object) {
        if (object == null) {
            return null;
        }
        // 2. 从HttpRequestArgumentUserHeader复制属性值到目标对象
        Map<String, String> headersMap = this.getRequestHeadersBody(request);
        if (headersMap != null && !headersMap.isEmpty()) {
            HttpRequestArgumentUserHeader userHeader = this.parseHeader(headersMap, HttpRequestArgumentUserHeader.class);
            if (userHeader != null) {
                // 判断目标对象是否存在与HttpRequestArgumentUserHeader一致的属性
                // 如果存在，将HttpRequestArgumentUserHeader对象的属性值赋给目标对象
                BeanProperty.copyPropertiesFrom(object, userHeader, HttpRequestArgumentUserHeader.class);
            }
        }
        
        return object;
    }




    protected abstract Object onResolve(MethodParameter parameter,
                                        NativeWebRequest webRequest,
                                        ModelAndViewContainer mavContainer,
                                        WebDataBinderFactory binderFactory) throws Exception;
}
