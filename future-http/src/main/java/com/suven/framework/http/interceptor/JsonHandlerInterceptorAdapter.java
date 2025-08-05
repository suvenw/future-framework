//package com.suven.framework.http.interceptor;
//
//import com.suven.framework.common.GlobalLogbackThread;
//import com.suven.framework.common.enums.SysResultCodeEnum;
//import com.suven.framework.core.db.DataSourceHolder;
//import com.suven.framework.core.jetty.settings.SystemParamSettings;
//import com.suven.framework.http.NetworkUtil;
//import com.suven.framework.http.data.vo.RequestParserVo;
//import com.suven.framework.http.exception.SystemRuntimeException;
//import com.suven.framework.http.message.ParameterMessage;
//import com.suven.framework.http.message.HttpRequestPostMessage;
//import com.suven.framework.http.message.HttpRequestRemote;
//import com.suven.framework.util.json.JsonUtils;
//import com.suven.framework.util.crypt.SignParam;
//import org.apache.commons.io.IOUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
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
//@InterceptorOrder(order = InterceptorOrderValue.HANDEL_ORDER_JSON)
//public class JsonHandlerInterceptorAdapter extends BaseHandlerInterceptorAdapter
//        implements  IHandlerInterceptor,InterceptorConstants,
//        IHandlerHeaderInterceptor {
//    //AbstractHandlerMethodAdapter  HandlerInterceptorAdapter
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    private SystemParamSettings systemParamSettings;
//
//
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        systemParamSettings = applicationContext.getBean(SYSTEM_PARAM_SETTINGS, SystemParamSettings.class);
//    }
//
//    @SuppressWarnings("unchecked")
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
//        if(null != systemParamSettings && systemParamSettings.isHeaderToken()){
//            Map<String, String>  headerInfoMap =  getHeadersInfo(request);
//            message =  RequestParserVo.build().parseHeader(headerInfoMap, HttpRequestPostMessage.class);
//            this.convertBodyByHeaders(headerInfoMap,param);
//        }else {
//            message = RequestParserVo.build().parseFrom(param, HttpRequestPostMessage.class);
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
//        remote.setCliMd5Sign(message.getCliSign());
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
//        boolean isJson = this.isJsonRequestFromContentType(servletRequest);
//        if (!isJson) {
//            return null;
//         }
//        try {
//            String json = IOUtils.toString(servletRequest.getInputStream(), this.CHARSET_CONTENT_UTF8);
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
//    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
//        Map<String, String> map = new LinkedHashMap<>();
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
//    private  boolean convertBodyByHeaders(Map<String, String> headMap, Map<String, String> bodyMap){
//        List<String> convertKeyList =  systemParamSettings.getHeaderField();
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
//            String value =  headMap.get( key);
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
