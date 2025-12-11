package com.suven.framework.http.config;

import com.google.common.reflect.TypeToken;
import com.suven.framework.http.api.ApiResult;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.api.SkipWrap;
import com.suven.framework.http.data.vo.ResponseCovertResultVo;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@Order(1)
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private Logger log = LoggerFactory.getLogger(CustomResponseBodyAdvice.class);
    List<Class<?>> interFaceList = Arrays.asList(IResponseResult.class,IBaseApi.class,IResultCodeEnum.class);
    final TypeToken<IBaseApi> type = new TypeToken<IBaseApi>(getClass()) {};
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 跳过异常处理方法的返回
        if (isExceptionResponse(returnType)) {
            return false;
        }
        boolean bodyType= checkMethodParameter(returnType);
        return bodyType;
    }
        /**
     * 判断方法参数是否为异常响应处理
     *
     * @param returnType 方法参数对象，包含方法的返回类型和注解信息
     * @return 如果方法标记了ExceptionHandler注解则返回true，否则返回false
     */
    private boolean isExceptionResponse(MethodParameter returnType) {
        // 检查方法是否标记为异常处理（可选）
        return returnType.getMethodAnnotation(ExceptionHandler.class) != null;
    }

        /**
     * 检查方法参数是否满足特定条件
     *
     * @param returnType 方法参数对象，包含方法的返回类型信息
     * @return boolean 检查结果，当所有条件都满足时返回true，否则返回false
     */
    private boolean checkMethodParameter(MethodParameter returnType){
        // 检查 returnType 是否是 RestController 注解的方法
        boolean isRestController = isRestController(returnType);
        // 是否跳过注解
        boolean isSkipAnno  = isSkipAnnotation(returnType);
        boolean isSkipReturnType = this.isSkipReturnType(returnType);
        boolean isErrorEnum = this.checkNotEnum(returnType);
        boolean   isSkipWrap = this.isSkipWrap(returnType);
        //跳过的包装类型的情况
        boolean isSkip =  isSkipReturnType || isErrorEnum || isSkipWrap;
        return  isRestController && !( isSkipAnno || isSkip);

    }


        /**
     * 判断方法返回类型是否需要包装
     * 排除一些不需要处理的情况
     * @param returnType 方法返回类型参数
     * @return true表示不需要包装，false表示需要包装
     */
    private boolean isSkipReturnType(MethodParameter returnType){
        Class<?> rawType = returnType.getParameterType();
        // 判断返回类型是否为IResponseResult类型或其子类型
        boolean isResultType = rawType.isAssignableFrom(IResponseResult.class);
        // 排除一些不需要处理的情况
        // 1. 如果方法返回void
        boolean isVoid = returnType.getParameterType() == void.class;
        // 2. 如果已经是 ResponseEntity 类型（Spring内置响应实体）
        // 如果返回的是 ResponseEntity，通常也不需要我们包装，它可能有自己的逻辑
        boolean isResponseEntity = rawType.isAssignableFrom(ResponseEntity.class);
        return  (isResultType || isResponseEntity || isVoid);
    }



    /**
     * 判断是否应该跳过包装,不存在ApiResult 跳过包装,存在ApiResult 且skip为true也跳过包装,
     * 返回值类型为ResponseEntity 跳过包装,返回值类型为HttpEntity 跳过包装,返回值类型为Resource
     * 跳过包装,返回值类型为SseEmitter 跳过包装,返回值类型为StreamingResponseBody
     * 跳过包装,返回值类型为ResponseCovertResultVo 跳过包装,返回值类型为ResponseCovertResultVo 跳过包装,
     * 返回值类型为ResponseCovertResultVo 跳过包装,返回值类型为
     */
    private boolean isSkipAnnotation(MethodParameter returnType) {
        // 检查方法上的注解
        Class<?> clazz = returnType.getContainingClass();
        ApiResult methodAnnotation = returnType.getMethodAnnotation(ApiResult.class);
        ApiResult classAnnotation = clazz.getAnnotation(ApiResult.class);
        if ((methodAnnotation != null && methodAnnotation.skip()) || ( classAnnotation != null && classAnnotation.skip())) {
            return true;
        }
        // 检查类上的注解
        return false;
    }


        /**
     * 判断方法参数所在的类是否被RestController注解标记
     *
     * @param returnType 方法参数对象，用于获取声明该参数的类信息
     * @return 如果声明类被RestController注解标记则返回true，否则返回false
     */
    private boolean isRestController(MethodParameter returnType){
        return returnType.getDeclaringClass().isAnnotationPresent(RestController.class);
    }

        /**
     * 判断是否跳过包装处理
     *
     * @param returnType 方法参数对象，用于获取声明类和方法注解信息
     * @return boolean 返回true表示不跳过包装，false表示跳过包装
     */
    private boolean isSkipWrap(MethodParameter returnType){
        Class<?> clazz = returnType.getDeclaringClass();
        // 检查类或方法上是否有SkipWrap注解
        boolean skip = clazz.isAnnotationPresent(SkipWrap.class)
                || returnType.hasMethodAnnotation(SkipWrap.class);
        return  skip;
    }


        /**
     * 检查方法参数的返回类型是否实现了指定的接口
     *
     * @param returnType 方法参数对象，用于获取声明类及其接口信息
     * @return 如果返回类型的声明类实现了interFaceList中的任意一个接口则返回true，否则返回false
     */
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


    /**
     * 检查方法参数返回类型是否为枚举类型且实现了IResultCodeEnum接口
     * @param returnType 方法参数对象，包含返回类型信息
     * @return 如果返回类型既是枚举类型又实现了IResultCodeEnum接口则返回false，否则返回true
     */
    private boolean checkNotEnum(MethodParameter returnType){
        // 检查返回类型是否为枚举类型且实现了指定接口
        boolean isEnumType = returnType.getParameterType().isEnum();
        boolean isInterface = returnType.getParameterType().isAssignableFrom(IResultCodeEnum.class);
        return (isEnumType && isInterface);
    }


    /**
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     *         在此处对响应体进行处理
     *          可以修改、包装、添加额外的数据等
     *          返回最终的响应体
     *          示例：在响应体中添加额外的数据
     *          异常类型
     *          已是标准返回，跳过
     *          ResponseEntity 里可能已经是标准体或特殊响应，跳过
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

        if (body instanceof ResponseEntity || body instanceof IResponseResult) {
            log.info("ServerHttpRequest url:[{}],body class:[{}]",request.getURI(),body.getClass());
            log.info("ServerHttpRequest resultBody ======>>>:\n{}",JsonUtils.toJson(body));
            return body;
        }
        // 特殊类型跳过（下载/流式等）
        if (body instanceof Resource
                || body instanceof byte[]
                || body instanceof StreamingResponseBody
                || body instanceof ResponseBodyEmitter) {
            return body;
        }
        Object result =  ResponseCovertResultVo.convertData(body,false);
        log.info("ServerHttpRequest url:[{}],body class:[{}]",request.getURI(),body.getClass());
        log.info("ServerHttpRequest resultBody ======>>>:\n{}",JsonUtils.toJson(body));
        return result;
    }
}