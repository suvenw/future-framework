package com.suven.framework.http.exception;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.ParameterMessage;
import com.suven.framework.util.constants.Env;
import com.suven.framework.util.json.JsonFormatTool;
import com.suven.framework.util.json.JsonUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;



/**
 * Title: GlobalExceptionErrorResponse.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http 接口异常返回 错误信息的统一封装对象类;
 */

public class GlobalExceptionErrorResponse {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    protected IResponseResult write(IResponseResult result, Object message, HttpServletResponse response) {

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setHeader("Accept",MediaType.APPLICATION_JSON_VALUE);
        if(message instanceof  SystemRuntimeException){
            SystemRuntimeException exception  = (SystemRuntimeException)message;
            if(null == exception.getError() ){
                logger.error("please definition SystemRuntimeException information to SysMsgEnumType class ");
                exception = new SystemRuntimeException(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            }
            result.of(exception.getErrorCode(),exception.getErrorMessage());
            if(exception.getResponse() != null) {
                result.of(false,exception.getErrorCode(),exception.getErrorMessage(),exception.getResponse());
            }
        }
        if(message instanceof BusinessLogicException){
            BusinessLogicException exception  = (BusinessLogicException)message;
            if(null == exception.getError() ){
                logger.error("please definition BusinessLogicException information to SysMsgEnumType class ");
                exception = new BusinessLogicException(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            }
            result.of(exception.getErrorCode(),exception.getErrorMessage());
        }
        if(message instanceof IResultCodeEnum){
            IResultCodeEnum msg  = (IResultCodeEnum)message;
            result.of(msg.getCode(),msg.getMsg());
        }
        printErrorLogForRequestMessage(logger,result.code(), result.message());
        if(!Env.isProd()){
            logger.warn(JsonFormatTool.formatJson(JsonUtils.toJson(result)));
        }
        return result;
    }
    private void printErrorLogForRequestMessage(Logger logger,int code ,String msg) {
        HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
        String  json = ParameterMessage.getJsonParseString();
        long netTime = ParameterMessage.getRequestRemote().getNetTime();
        long exeTime = System.currentTimeMillis() - message.getTimes();
        logger.warn("type=Exception, request-url=[{}], code=[{}], msg=[{}],ip=[{}]; FROM-Request-BaseParam = [{}],Request-AttributeParam=[{}], responseEndTime =[{}],netEndTime =[{}]",
                message.getUri(), code, msg, message.getIp(),
                message.toString(),json,exeTime,netTime);
    }

    public  void printExceptionErrorLogger(Logger logger,Exception e){
        String errorMessage = e.getMessage();
        StringBuffer msg = new StringBuffer().append(e.getClass().getSimpleName()).append(":");
        if(e instanceof SystemRuntimeException){
            errorMessage =((SystemRuntimeException) e).getErrorMessage();
            msg.append(errorMessage);
            if(Env.isProd()){//线上版本不打错误堆栈;
                logger.warn(msg.toString());
                return;
            }

        }else {
            msg.append(errorMessage);
        }
        StringBuffer error = new StringBuffer();
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            error.append("\n\t at " + stackTraceElement.toString());
        }
        logger.error(msg.append(error).toString());
    }
}
