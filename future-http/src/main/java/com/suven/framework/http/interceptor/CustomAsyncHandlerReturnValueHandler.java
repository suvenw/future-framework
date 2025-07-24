package com.suven.framework.http.interceptor;//package com.bonade.hrm.salary.cloud.Interceptor;
//
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import java.util.concurrent.CompletableFuture;
//
//public class CustomAsyncHandlerReturnValueHandler implements AsyncHandlerMethodReturnValueHandler {
//
//    @Override
//    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
//        // 检查返回值是否为 CompletableFuture 类型
//        return returnValue instanceof CompletableFuture;
//    }
//
//    @Override
//    public boolean supportsReturnType(MethodParameter returnType) {
//        // 支持 CompletableFuture 类型的返回值
//        return CompletableFuture.class.isAssignableFrom(returnType.getParameterType());
//    }
//
//    @Override
//    public void handleReturnValue(Object returnValue, MethodParameter returnType,
//                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
//        // 将 CompletableFuture 的结果设置给 ModelAndViewContainer
//        CompletableFuture<?> completableFuture = (CompletableFuture<?>) returnValue;
//        completableFuture.thenAccept(result -> {
//            mavContainer.setRequestHandled(true);
//            mavContainer.addAttribute(result);
//        });
//    }
//
//    public void handleReturnValue(Object returnValue, MethodParameter returnType,
//                                  MediaType mediaType, ModelAndViewContainer mavContainer,
//                                  NativeWebRequest webRequest) throws Exception {
//        // 在这个示例中我们不需要处理不同的 MediaType
//        handleReturnValue(returnValue, returnType, mavContainer, webRequest);
//    }
//
//}