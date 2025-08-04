package com.suven.framework.dubbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 全局日志跟踪类型
 *
 * @author vincentdeng
 */
public class GlobalLogbackThread {

   private static Logger logger = LoggerFactory.getLogger(GlobalLogbackThread.class);

    public static final String LOGBACK_TRACE_ID = "logback_trace_id";
    public static final String LOGBACK_LOCAL_IP = "logback_local_ip";

    public interface GlobalConfigConstants{
        /**  ================ 7. LOGBACK start param   ================ **/
        public static final String LOGBACK_TRACE_ID = "logback_trace_id";
        public static final String LOGBACK_LOCAL_IP = "logback_local_ip";
        /**  ================ 7. LOGBACK end param   ================ **/
    }
    public static void setLogbackTraceId(String traceId){
        traceId = traceId == null ? "" : traceId;
        MDC.put(LOGBACK_TRACE_ID,traceId);
    }

    public static String getLogbackTraceIdInMDC(String module){
        try{
            String traceId =  MDC.get(LOGBACK_TRACE_ID);
            if(traceId == null){
                traceId = initGlobalLogbackTraceId(module);
            }
            return traceId;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Exception by setGlobalLogbackTraceId error[{}]",e);
        }
        return "";
    }

    public static String initGlobalLogbackTraceId(String module){
        if(module == null){
            module =  "";
        }
        String traceId = new StringBuilder("[")
                .append(module)
                .append("_")
                .append(data())
                .append("_")
                .append(uuid())
                .append("]").toString();
        MDC.put(LOGBACK_TRACE_ID,traceId);
        logger.info("MDC server global logback info logback_trace_id ");
        return traceId;
    }

    public static void removeTraceId(){
        MDC.remove(LOGBACK_TRACE_ID);
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

}