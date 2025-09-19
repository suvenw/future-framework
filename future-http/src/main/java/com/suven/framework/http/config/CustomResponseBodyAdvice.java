package com.suven.framework.http.config;

import com.google.common.reflect.TypeToken;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.api.SkipWrap;
import com.suven.framework.http.data.vo.ResponseCovertResultVo;
import com.suven.framework.http.inters.IResultCodeEnum;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    List<Class<?>> interFaceList = Arrays.asList(IResponseResult.class,IBaseApi.class,IResultCodeEnum.class);
    final TypeToken<IBaseApi> type = new TypeToken<IBaseApi>(getClass()) {};
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 在此处可以根据需要判断是否对特定的Controller方法进行处理
//        return type.getRawType().isAssignableFrom(returnType.getParameterType());
        boolean isSup = checkMethodParameter(returnType);
        return isSup;
    }
    private boolean checkMethodParameter(MethodParameter returnType){
        // 检查 returnType 是否是 RestController 注解的方法
        boolean isRestController = isRestController(returnType);
        boolean isSkip = this.isSkipWrap(returnType);
        // 检查 returnType 是否实现了多个接口
        boolean isInterface = this.checkInterfaceImplementations(returnType);
        boolean isEnum = this.checkEnum(returnType);
        // 返回多接口判断结果
        return isRestController || isSkip || isInterface || isEnum;
    }

    private boolean isRestController(MethodParameter returnType){
        return returnType.getDeclaringClass().isAnnotationPresent(RestController.class);
    }
    private boolean isSkipWrap(MethodParameter returnType){
        Class<?> clazz = returnType.getDeclaringClass();
        boolean skip = clazz.isAnnotationPresent(SkipWrap.class)
                || returnType.hasMethodAnnotation(SkipWrap.class);
        return  !skip;
    }

    private boolean checkInterfaceImplementations(MethodParameter returnType) {
        Class<?>[] interfaces = returnType.getDeclaringClass().getInterfaces();
        // 判断 returnType 是否实现了指定的接口
        for (Class<?> interfaceClass : interfaces) {
            if (interFaceList.contains(interfaceClass)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkEnum(MethodParameter returnType){
        // 检查 returnType 是否为枚举类型
        boolean isEnumType = returnType.getParameterType().isEnum();
        // 检查 returnType 是否实现了指定的接口
        boolean isInterface = returnType.getParameterType().isAssignableFrom(IResultCodeEnum.class);
        return isEnumType && isInterface;
    }

    /**
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     *
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request               the current request
     * @param response              the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                    Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                    ServerHttpRequest request, ServerHttpResponse response) {
        // 在此处对响应体进行处理
        // 可以修改、包装、添加额外的数据等
        // 返回最终的响应体
        // 示例：在响应体中添加额外的数据
        //异常类型
        // 已是标准返回，跳过
        if (null != body && interFaceList.contains(body.getClass())){
            return body;
        }
        // ResponseEntity 里可能已经是标准体或特殊响应，跳过
        if (body instanceof ResponseEntity) {
            return body;
        }
        // 特殊类型跳过（下载/流式等）
        if (body instanceof Resource
                || body instanceof byte[]
                || body instanceof StreamingResponseBody
                || body instanceof SseEmitter) {
            return body;
        }
        Object result =  ResponseCovertResultVo.convertData(body,false);
        return result;
    }
}