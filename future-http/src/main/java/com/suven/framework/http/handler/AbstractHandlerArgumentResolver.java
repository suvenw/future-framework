package com.suven.framework.http.handler;


import com.google.common.reflect.TypeToken;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.resolver.HttpRequestArgumentParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * 对Controller中的入参进行转换
 * 具体的转换逻辑由子类实现 HandlerMethodArgumentResolver
 * Created by Alex on 2014/4/28
 */
public abstract class AbstractHandlerArgumentResolver<T> extends HttpRequestArgumentParser implements IHandlerMethodArgumentResolver {

   final static Logger logger = LoggerFactory.getLogger(AbstractHandlerArgumentResolver.class);
    final TypeToken<T> type = new TypeToken<T>(getClass()) {};


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
            logger.error("转换参数失败");
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_CHECK);
        }
        return result;
    }






    protected abstract Object onResolve(MethodParameter parameter,
                                        NativeWebRequest webRequest,
                                        ModelAndViewContainer mavContainer,
                                        WebDataBinderFactory binderFactory) throws Exception;
}
