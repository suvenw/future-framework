package com.suven.framework.core.advice;

import com.suven.framework.util.json.StringFormat;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("catAdviceConfigSetting")
@ConfigurationProperties(value = "com.sixeoc.cat")
public class AdviceConfigSetting {
   private boolean redis = true;
   private boolean db = true;
   private boolean filter = true;
   private boolean http = true;
   private boolean task = true;
   private boolean url = true;
    private boolean mq = true;
    private boolean logger = true;
    private String exclude="0";

    public boolean isRedis() {
        return redis;
    }

    public void setRedis(boolean redis) {
        this.redis = redis;
    }

    public boolean isDb() {
        return db;
    }

    public void setDb(boolean db) {
        this.db = db;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public boolean isHttp() {
        return http;
    }

    public void setHttp(boolean http) {
        this.http = http;
    }

    public boolean isTask() {
        return task;
    }

    public void setTask(boolean task) {
        this.task = task;
    }

    public boolean isUrl() {
        return url;
    }

    public void setUrl(boolean url) {
        this.url = url;
    }

    public boolean isMq() {
        return mq;
    }

    public void setMq(boolean mq) {
        this.mq = mq;
    }

    public boolean isLogger() {
        return logger;
    }

    public void setLogger(boolean logger) {
        this.logger = logger;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public boolean isValidator(String validator ){
        try {
            String methodName = StringFormat.toUpperCaseFirstOne(validator);
            methodName = "is"+methodName;
            Object result = MethodUtils.invokeMethod(this,methodName);
            if (result instanceof  Boolean){
                return (Boolean)result;
            }
            return false;
        }catch (Exception e){
            return false;
        }

    }
    /** 检查配置类型是否可执行 **/
    public  Object validator(String methodName){
        try {
            String method = StringFormat.toUpperCaseFirstOne(methodName);
            method = "get"+method;
            Object result = MethodUtils.invokeMethod(this,method);
            return result;
        }catch (Exception e){
            return null;
        }
    }

    public static void main(String[] args) {
        AdviceConfigSetting a = new AdviceConfigSetting();
       Object is =  a.validator("exclude");
       System.out.println(is);
    }
}
