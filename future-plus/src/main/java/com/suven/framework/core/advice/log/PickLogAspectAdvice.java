package com.suven.framework.core.advice.log;


import com.suven.framework.common.cat.PickLog;
import com.suven.framework.core.advice.AdviceConfigSetting;
import com.suven.framework.core.advice.BaseAspectAdvice;
import com.suven.framework.util.json.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 系统日志，切面处理类
 *
 */
@Aspect
@Component
public class PickLogAspectAdvice extends BaseAspectAdvice {




    private static Logger logger = LoggerFactory.getLogger(PickLogAspectAdvice.class);
    @Autowired(required = false)
    private PickLogThreadPool pickLogThreadPool;
    @Autowired(required = false)
    private PickLogListener pickLogListener;

    @Autowired(required = false)
    private AdviceConfigSetting adviceConfigSetting;



    @Around("@annotation(pickLog)")
    public Object aroundPickLog(@NotNull ProceedingJoinPoint joinPoint, @NotNull PickLog pickLog){
        if(!validator()){
            return this.getMethodReturnValue(joinPoint);
        }
        PickLogEven logBean = PickLogEven.build();

        //执行方法
        Object result = this.getMethodReturnValue(joinPoint);

        //执行时长(毫秒)
        logBean.setEngTime( System.currentTimeMillis() );
        logBean.toRunningTime();

        String targetClassName =  this.getTargetClassSimpleName(joinPoint);
        String runningMethodName =  this.getMethodName(joinPoint);
        Map<String,Object> parameterMap =  this.getMethodParameterNameAndValue(joinPoint);
        //获取请求头像信息
//        HttpRequestPostMessage requestHeader =  ParamMessage.getRequestMessage();
//        requestHeader.setAccessToken("");
//        logBean.setRequestHeader(requestHeader);

        logBean.setTopicName(pickLog.topicName());
        logBean.setRequestClassName(targetClassName);
        logBean.setRunningMethodName(runningMethodName);
        logBean.setRequestParameter(JsonUtils.toJson(parameterMap));
        logBean.setReturnResult(JsonUtils.toJson(result));

        logger.info("===================== aroundLog ===================== "+pickLog.topicName());
        if( null != pickLogListener && null != pickLogThreadPool){

            PickLogFuture pickLogFuture = new PickLogFuture(pickLogListener,pickLog,logBean);
            pickLogThreadPool.submit(pickLogFuture);
        }

        return result;
    }

    public boolean validator()  {
        if (null!= adviceConfigSetting && adviceConfigSetting.isValidator("logger")) {
            return true;
        }
        return false;
    }
}
