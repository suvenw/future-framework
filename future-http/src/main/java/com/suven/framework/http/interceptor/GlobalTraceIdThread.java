package com.suven.framework.http.interceptor;

import com.alibaba.fastjson.JSON;
import com.suven.framework.http.inters.IResultCodeEnum;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * 全局日志跟踪类型
 *
 * @author vincentdeng
 */
public class GlobalTraceIdThread {

    public static Logger log = LoggerFactory.getLogger(GlobalTraceIdThread.class);

    /**  ================ 7. LOGBACK start param   ================ **/
    public static final String LOGBACK_TRACE_ID = "trace_id";
    /**  ================ 7. LOGBACK end param   ================ **/
    /**请求参数**/
    private static ThreadLocal<HttpRequestHeaders> param = new ThreadLocal<>();
    private static ThreadLocal<IResultCodeEnum> codeEnum  = new ThreadLocal<>();

    /**请求参数**/
    public static HttpRequestHeaders getHttpRequestHeaders() {
        HttpRequestHeaders result = param.get();
        return result == null ? new HttpRequestHeaders() : result;
    }

    /**请求参数**/
    public static void setHttpRequestHeaders(HttpRequestHeaders headers) {
        if(headers == null){
            headers = new HttpRequestHeaders();
        }
        param.set(headers);
    }

    public static IResultCodeEnum getCodeEnum() {
        IResultCodeEnum code =  codeEnum.get();
        return code;
    }

    public static void setCodeEnum(IResultCodeEnum codeEnum) {
        GlobalTraceIdThread.codeEnum.set(codeEnum);
    }

    public static void setLogbackTraceId(String traceId){
        traceId = traceId == null ? "" : traceId;

    }

    public static String getLogbackTraceIdInMDC(String module){
        try{
            String traceId =  MDC.get(LOGBACK_TRACE_ID);
            if(Objects.nonNull(traceId)){
                return traceId;
            }
               traceId = initTraceId(module);
            return traceId;
        }catch (Exception e){
            log.error("Exception by setGlobalLogbackTraceId error[{}]",e);
        }
        return "";
    }

    public static String initTraceId(String module){
        if(module == null){
            module =  "";
        }
        String traceId = new StringBuilder()
                .append(module)
                .append("_")
                .append(data())
                .append("_")
                .append(uuid())
                .append("").toString();
        MDC.put(LOGBACK_TRACE_ID,traceId);
        TraceContext.putCorrelation("traceId",traceId);
        log.info("MDC server global logback info logback_trace_id ");
        return traceId;
    }

    /**
     *  业务传递当前线程线路id,如何为空时,初始化uuid链路id
     */
    private static String traceIdMDC(){
        String traceId = MDC.get(LOGBACK_TRACE_ID);
        if (Objects.isNull(traceId)){
            traceId = initTraceId("");
        }
        return traceId;
    }
    private static String traceIdSkywalking(){
        String traceId = TraceContext.traceId();
        if (Objects.isNull(traceId)){
            traceId = initTraceId("");

        }
        return traceId;
    }
    public static void removeTraceId(){
        MDC.remove(LOGBACK_TRACE_ID);
        param.remove();
        codeEnum.remove();
    }



    public static String data() {
        try {
            DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            return format.format(new Date());
        } catch (Exception e) {
            return "";
        }
    }

    public static String uuid(){
        String s = UUID.randomUUID().toString();
        s = s.replace("-","").substring(0,16);
        return s;
    }



    public static void printLogForRequestMessage(int code , String msg, HttpRequestHeaders headers,
                                                 Object requestFromParameter,String jsonData) {
        String startDate =  dateToString(new Date(headers.getTimes()));
        String endDate =  dateToString(new Date());
        long runTime = System.currentTimeMillis() - headers.getTimes();
        String from = JSON.toJSONString(requestFromParameter) ;
        String header = JSON.toJSONString(headers) ;
        log.warn("type=Exception,  code=[{}], msg=[{}],ip=[{}];" +
                        " Request-Headers=[{}] requestBeginTime=[{}],  requestEndTime=[{}], responseRunTime =[{}]",
                 code, msg, headers.getIp(),
                header,startDate,endDate,runTime);
        log.warn("type=Exception,  URL=[{}],FROM-Request-AttributeParam=[{}],JSON-Request-AttributeParam=[{}]," ,
                headers.getUri(), from,jsonData);
    }
    public static void printSuccessLog(long beginTime, HttpRequestHeaders message) {
        long exeTime = System.currentTimeMillis() - message.getTimes();
        log.warn("type=Success ,ip= [{}], RequestMessage= [{}], requestBeginTime=[{}], responseRunTime =[{}] ",
                message.getIp(),
                message,beginTime, exeTime);
    }

    private static String dateToString(Date date) {
        try {
            return NORM_DATETIME_MS_FORMAT.format(date);
        } catch (Exception ex) {
        }
        return "";
    }
    /**
     * 标准日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS
     */
    private static final String PATTERN_YYYYMMDDHHMMSSSSS ="yyyyMMddHHmmssSSS";
    /**
     * 标准日期时间格式，精确到毫秒 {@link DateFormat}：yyyy-MM-dd HH:mm:ss.SSS
     */
    private  static final  DateFormat NORM_DATETIME_MS_FORMAT = new SimpleDateFormat(PATTERN_YYYYMMDDHHMMSSSSS);


}