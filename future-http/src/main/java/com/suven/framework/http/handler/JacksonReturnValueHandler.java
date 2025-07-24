package com.suven.framework.http.handler;//package com.suven.framework.http.handler;
//
//import com.suven.framework.http.api.IResponseResult;
//import com.suven.framework.http.data.vo.ResponseResultVo;
//import com.suven.framework.http.inters.IResultCodeEnum;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.converter.HttpMessageNotWritableException;
//import org.springframework.web.HttpMediaTypeNotAcceptableException;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import java.io.IOException;
//
//public class JacksonReturnValueHandler implements HandlerMethodReturnValueHandler {
//
//
//
//    //
////
//
//    @Override
//    public void handleReturnValue(Object returnObject, MethodParameter returnType,
//                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
//            throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {
//        //默认的
//        if (returnObject instanceof IResponseResult) {
////            ((ResponseEntity<?>) body).getBody().put("extraData", "Some extra data");
//            super.handleReturnValue(returnObject,returnType,mavContainer,webRequest);
//            return ;
//        }
//        //自定义
//        if(returnObject instanceof IBaseApi){
//            IResponseResult vo =   ResponseResultVo.build().buildResultVo(returnObject);
//            super.handleReturnValue(vo,returnType,mavContainer,webRequest);
//            return ;
//        }//异常类型
//        if(returnObject instanceof IResultCodeEnum  ){
//            IResultCodeEnum codeEnum = (IResultCodeEnum) returnObject;
//            IResponseResult returnVo =   ResponseResultVo.build().buildResultVo(codeEnum.getCode(),codeEnum.getMsg());
//            super.handleReturnValue(returnVo,returnType,mavContainer,webRequest);
//            return ;
//        }
//        super.handleReturnValue(returnObject,returnType,mavContainer,webRequest);
//
//
//    }
//}