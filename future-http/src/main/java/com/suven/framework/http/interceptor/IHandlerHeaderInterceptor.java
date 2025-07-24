//package com.suven.framework.http.interceptor;
//
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import java.nio.charset.Charset;
//
//public interface IHandlerHeaderInterceptor {
//
//
//    final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;
//    final String CONTENT_TYPE_HEADER = "Content-Type";
//    final String CONTENT_JSON_HEADER = "Content-Json";
//    final String CONTENT_PARAMETER_HEADER = "Content-Parameter";
//
//    final String CHARSET_CONTENT_UTF8 = "UTF-8";
//
//
//
//    /**
//     *  在请求头添加参数:
//     *  1. Content-Type=application/json
//     *  2. Content-Json=true
//     * 通过获取请求头的Content-Type类型,值是包含 "application/json" 字符串信息
//     * 用于判断请求过来的参数格式,如果包含则认为是json格式,
//     * 如果是json返回ture;
//     * @param request HttpServletRequest 网络请求对象
//     * @return  boolean   如果是json返回ture;否则为false;
//     * **/
//    default boolean isJsonRequestFromContentType(HttpServletRequest request){
//        if(null == request){
//            return false;
//        }
//        String contentType =  request.getHeader(CONTENT_TYPE_HEADER);
//        String contentJsonHeader =  request.getHeader(CONTENT_JSON_HEADER);
//        if(contentType != null && contentType.contains(CONTENT_TYPE) && null != contentJsonHeader&& "true".equals(contentJsonHeader)){
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * @param request HttpServletRequest 网络请求对象
//     * @return  boolean   如果是json返回ture;否则为false;
//     * request Method 如果为post 返回true, 否则返回false
//     * @return
//     */
//    default boolean isPostRequestMethod(HttpServletRequest request){
//        if(null == request){
//            return false;
//        }
//        boolean isPost = request.getMethod().equals(RequestMethod.POST.name());
//        return isPost;
//    }
//
//    /**
//     * @param request HttpServletRequest 网络请求对象
//     * @return  boolean   如果是json返回ture;否则为false;
//     * request Method 如果为post 返回true, 否则返回false
//     * @return
//     */
//    default boolean requestParamIsNull(HttpServletRequest request){
//        if(null == request){
//            return false;
//        }
//        String param =  request.getHeader(CONTENT_PARAMETER_HEADER);
//        boolean isParam =  null != param && "true".equals(param);
//        return isParam;
//    }
//
//}
