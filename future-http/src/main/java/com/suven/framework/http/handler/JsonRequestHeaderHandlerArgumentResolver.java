//package com.suven.framework.http.handler;
//
//
//import com.suven.framework.http.api.IHeaderRequestVo;
//import com.suven.framework.common.enums.SysResultCodeEnum;
//import com.suven.framework.http.exception.SystemRuntimeException;
//import org.springframework.core.MethodParameter;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * @Title: JsonRequestHandlerArgumentResolver.java
// * @author Joven.wang
// * @date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) http 接口统一请求实现转换逻辑实现业务类;
// */
//
//public class JsonRequestHeaderHandlerArgumentResolver extends AbstractHandlerArgumentResolver<IHeaderRequestVo> {
//
//    @Override
//    protected Object onResolve(MethodParameter parameter,
//                               NativeWebRequest webRequest,
//                               ModelAndViewContainer mavContainer,
//                               WebDataBinderFactory binderFactory)  throws Exception {
//
//        mavContainer.setRequestHandled(true);
//        if(parameter.getParameterType() == HttpServletRequest.class){
//            return null;
//        }
//        /**
//         *  获取http请求信息的实现方法记录
//         * HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
//         * HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
//        **/
//        boolean isJsonRequest = this.requestContentTypeIsJson(webRequest);
////        boolean isJsonRequest =  ParamMessage.getRequestRemote().isJsonRequest();
//        Class requestHeaderClass = parameter.getParameterType();
//        Object  result = this.parserDate(webRequest, requestHeaderClass,isJsonRequest);
//        return result;
//    }
//
//
//
//
//    private <T> T parserDate(NativeWebRequest webRequest, Class<T> clazz,boolean isJsonRequest){
//        if(clazz == null){
//            return null;
//        }
//        boolean isExtendsIRequestVo = IHeaderRequestVo.class.isAssignableFrom(clazz);
//        if(!isExtendsIRequestVo){
//            String REQUEST_OBJECT_ERROR_MSG = "receive client request Object class :["+clazz+"] not extends RequestParserVo or implements IRequestVo ";
//            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR).format(REQUEST_OBJECT_ERROR_MSG);
//
//        }
//        T t  = null;
//        try {
//            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
//            IHeaderRequestVo requestVo = (IHeaderRequestVo) clazz.newInstance();
//            Map<String, Object> headersMap = this.getHeadersInfo(request);
//             t  = requestVo.parseHeader(headersMap,clazz);
//
//        }catch (Exception e){
//            e.printStackTrace();
//            if(e instanceof SystemRuntimeException){
//                throw  (SystemRuntimeException)e;
//            }else {
//                throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR).format(e.getCause().getMessage());
//            }
//        }
//
//        return t;
//    }
//
//    private Map<String, Object> getHeadersInfo(HttpServletRequest request) {
//        Map<String, Object> map = new HashMap<>();
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            map.put(key, value);
//        }
//        return map;
//    }
//
//}