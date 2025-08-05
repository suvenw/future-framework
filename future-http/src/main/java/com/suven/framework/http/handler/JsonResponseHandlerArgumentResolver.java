//package com.suven.framework.http.handler;
//
//import com.suven.framework.common.enums.SysResultCodeEnum;
//import com.suven.framework.http.exception.SystemRuntimeException;
//import com.suven.framework.http.message.ParamMessage;
//import org.springframework.core.MethodParameter;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import javax.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//
///**
// * @Title: JsonReturnHandlerArgumentResolver.java
// * @author Joven.wang
// * @date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) http 接口统一请求返回结果,返回结果实现写到redis 缓存中,逻辑实现业务类;
// */
//
//public class JsonResponseHandlerArgumentResolver extends AbstractHandlerArgumentResolver<IResponseHandler> {
//
//
//
//    @Override
//    protected Object onResolve(MethodParameter parameter,
//                               NativeWebRequest webRequest,
//                               ModelAndViewContainer mavContainer,
//                               WebDataBinderFactory binderFactory) throws Exception{
//
//        mavContainer.setRequestHandled(true);
//        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
//        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
//        /** **/
//        Class responseClass = parameter.getParameterType();
//        return parserResponse(request, response,responseClass);
//    }
//
//    private IResponseHandler parserResponse(HttpServletRequest request, HttpServletResponse response, Class responseClass){
//        boolean isExtendsIRequestVo =  IResponseHandler.class.isAssignableFrom(responseClass);
//        if(!isExtendsIRequestVo){
//            String REQUEST_OBJECT_ERROR_MSG = "receive client IResponseVo Object class :["+responseClass+"] not extends BaseHttpResponseWriteHandlerConverter or implements IResponseVo ";
//            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR).format(REQUEST_OBJECT_ERROR_MSG);
//        }
//        try {
//             IResponseHandler responseVo = (IResponseHandler) responseClass.newInstance();
//            responseVo.initResponse(response);
//
//            /** 用于异常处理,返回统一规范对象 **/
//            ParamMessage.setResponseResult(responseVo.getResultVo());
//
//            return responseVo;
//        }catch (Exception e){
//            e.printStackTrace();
//            if(e instanceof SystemRuntimeException){
//                throw  (SystemRuntimeException)e;
//            }else {
//                throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR).format(e.getCause().getMessage());
//            }
//        }
//
//    }
//
//}