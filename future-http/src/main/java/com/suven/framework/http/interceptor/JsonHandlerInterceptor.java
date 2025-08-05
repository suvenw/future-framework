package com.suven.framework.http.interceptor;//package com.suven.framework.http.interceptor;
//
//import com.suven.framework.common.GlobalLogbackThread;
//import com.suven.framework.common.enums.SysResultCodeEnum;
//import com.suven.framework.core.db.DataSourceHolder;
//import com.suven.framework.http.NetworkUtil;
//import com.suven.framework.http.exception.SystemRuntimeException;
//import com.suven.framework.http.message.HttpRequestPostMessage;
//import com.suven.framework.http.message.HttpRequestRemote;
//import com.suven.framework.http.message.ParameterMessage;
//import com.suven.framework.util.crypt.SignParam;
//import com.suven.framework.util.json.JsonUtils;
//import org.apache.commons.io.IOUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.*;
//
//
///**
// * Title: JsonHandlerInterceptorAdapter.java
// * @author Joven.wang
// * date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) http 接口拦截器,主要是实现数据反映成对象Bean方面业务类,包括头部数据封装,排序值为3;
// */
//
//@Component
//public class JsonHandlerInterceptor extends AbstractHandlerInterceptorAdapter  implements IHandlerInterceptor {
//    private static Logger logger = LoggerFactory.getLogger(BlackHandlerInterceptor.class);
//    /**
//     * Get the order value of this object.
//     * <p>Higher values are interpreted as lower priority. As a consequence,
//     * the object with the lowest value has the highest priority (somewhat
//     * analogous to Servlet {@code load-on-startup} values).
//     * <p>Same order values will result in arbitrary sort positions for the
//     * affected objects.
//     *
//     * @return the order value
//     * @see #HIGHEST_PRECEDENCE
//     * @see #LOWEST_PRECEDENCE
//     */
//    @Override
//    public int getOrder() {
//        return HandlerInterceptorEnum.HANDLER_ORDER_HEADERS.getIndex();
//    }
//
//    /**
//     * 通过supportsRunStatus 和 interceptor 提供架构的 preHandle方法,实现开关控制逻辑实现,其实等于preHandle增加开关配置逻辑
//     *
//     * @param request
//     * @param response
//     **/
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        logger.info(" url request JsonHandlerInterceptorAdapter  preHandle ===================");
//        //判断是get还是post
//        boolean isPostReq = this.isPostRequestMethod(request);
//        boolean isJson = false;
//
//        Map param = new HashMap(request.getParameterMap());
//        /** 如果为post类型请求, json 只支持post请求,且通过ParameterMap获取的内容信息为空时,再通过获取json方式读取文件流; **/
//        if(isPostReq && param.size() < 1){
//            param = postJsonRequestBody(request);
//            if(null == param ||  param.size() < 1){
//                logger.warn("[BAD PARAM] size < 1 or is null. url:{}", request.getRequestURI());
//                throw new Exception("post request not param, request url:"+request.getRequestURI());
//            }
//            isJson = true;
//        }
//        //返回签名
//        HttpRequestPostMessage message ;
//        if( isHeaderToken()){
//            Map<String,Object> headerInfoMap =  getHeadersInfo(request);
//            message =  this.parseFrom(headerInfoMap, HttpRequestPostMessage.class);
//            this.convertBodyByHeaders(headerInfoMap,param);
//        }else {
//            message = this.parseFrom(param, HttpRequestPostMessage.class);
//        }
//
//        if(message == null) {
//            throw new Exception("request data error, request url : " + request.getRequestURI());
//        }
//
//        String url = request.getServletPath();
//        long netTime = message.getTimes();
//        long sysTime = System.currentTimeMillis();
//
//        message.setIp(NetworkUtil.getIpAddress(request));
//        message.setUri(request.getRequestURI());
//        message.setTimes(sysTime);
//        //兼容token验证
//        JsonHandlerInterceptorRequestExt.build().getTokenByHeader(message,request);
//        //初始请求数据到当前线程安全中,实现业务解耦,
//        ParameterMessage.setRequestParamMessage(message,url,param);
//        //收集来自请求接口的代理属性信息
//        HttpRequestRemote remote = new HttpRequestRemote();
//        String serverMd5Sign = SignParam.getServerSign(param);
//
//        remote.setJsonRequest(isJson);
//        remote.setUrl(url);
//        remote.setClientIp(message.getIp());
//        remote.setDataType(message.getDataType());
//        remote.setSrvMd5Sign(serverMd5Sign);
//        remote.setCliMd5Sign(message.getSign());
//        remote.setPostRequest(isPostReq);
//        remote.setNetTime(sysTime - netTime);
//
//        ParameterMessage.setRequestRemote(remote);
//
//        logger.info("receive client request url=[{}],mode=[{}] ,param=[{}] ", url, request.getMethod(), JsonUtils.map2String(param));
//        return true;
//    }
//
//    /**
//     * 在请求头添加参数:
//     * 1. Content-Type=application/json
//     * 2. Content-Json=true
//     * 获取json请求参数,返回 map 对象 **/
//    private Map postJsonRequestBody(HttpServletRequest servletRequest) {
//        /** 暂时不通过请求头参数验证是否json请求 **/
//        boolean isJson = this.isPostRequestMethod(servletRequest);
//        if (!isJson) {
//            return null;
//         }
//        try {
//            String json = IOUtils.toString(servletRequest.getInputStream(),CHARSET_CONTENT_UTF8);
//            if(null == json || "" .equals(json )){
//                return null;
//            }
//            Map map = JsonUtils.toMap(json) ;
//            return map;
//        } catch (Exception e) {
//            logger.error("getJsonRequestBody exception ", e);
//            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_JSON_FAIL);
//        }
//    }
//
//
//
//
//    private Map<String, Object> getHeadersInfo(HttpServletRequest request) {
//        Map<String, Object> map = new LinkedHashMap<>();
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            map.put(key, value);
//        }
//        return map;
//    }
//
//    /** 通过配置 http Headers 的那些参数字段参与body加密实现**/
//    private  boolean convertBodyByHeaders(Map<String, Object> headMap, Map<String, Object> bodyMap){
//        List<String> convertKeyList =  this.getHandlerSetting().getService().getHeaderFieldList();
//        if(headMap == null || headMap.isEmpty()) {
//            return false;
//        }
//        if(null == bodyMap || bodyMap.isEmpty()){
//            return false;
//        }
//        if(convertKeyList == null){
//            return false;
//        }
//        convertKeyList.forEach(key -> {
//            Object value =  headMap.get( key);
//            bodyMap.put(key,value);
//        });
//        return true;
//    }
//
//
//    /**
//     * This implementation is empty.
//     */
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
//            throws Exception {
//        logger.debug("JsonHandlerInterceptorAdapter preHandle method ModelAndView 2 ...." );
//    }
//
//    /**
//     * This implementation is empty.
//     */
//    @Override
//    public void afterCompletion(
//            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//            throws Exception {
//        logger.debug("JsonHandlerInterceptorAdapter afterCompletion method 3 ...." );
//    }
//
//    /**
//     * This implementation is empty.
//     */
//    @Override
//    public void afterConcurrentHandlingStarted(
//            HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        GlobalLogbackThread.removeTraceId();
//        ParameterMessage.clear();
//        DataSourceHolder.remove();
//        logger.debug("JsonHandlerInterceptorAdapter afterConcurrentHandlingStarted method 4 ...." );
//    }
//
//
//
//}
