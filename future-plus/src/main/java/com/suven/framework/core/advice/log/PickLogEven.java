package com.suven.framework.core.advice.log;


import com.suven.framework.http.api.IBeanClone;
import com.suven.framework.http.api.IRequestVo;

public class PickLogEven implements IBeanClone {

    private long beginTime;
    private long engTime;
    private long runningTime;
    private String topicName;
    private String requestClassName;
    private String runningMethodName;
    private String requestParameter;
    private String returnResult;
    private IRequestVo requestHeader;

    public static PickLogEven build(){
        return new PickLogEven().toBeginTime(System.currentTimeMillis());
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }
    public PickLogEven toBeginTime(long beginTime) {
        this.beginTime = beginTime;
        return this;
    }
    public PickLogEven toRunningTime() {
        this.runningTime = this.engTime - this.beginTime;
        return this;
    }

    public long getEngTime() {
        return engTime;
    }

    public void setEngTime(long engTime) {
        this.engTime = engTime;
    }

    public long getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(long runningTime) {
        this.runningTime = runningTime;
    }

    public String getRequestClassName() {
        return requestClassName;
    }

    public void setRequestClassName(String requestClassName) {
        this.requestClassName = requestClassName;
    }

    public String getRunningMethodName() {
        return runningMethodName;
    }

    public void setRunningMethodName(String runningMethodName) {
        this.runningMethodName = runningMethodName;
    }

    public String getRequestParameter() {
        return requestParameter;
    }

    public void setRequestParameter(String requestParameter) {
        this.requestParameter = requestParameter;
    }

    public String getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(String returnResult) {
        this.returnResult = returnResult;
    }

    public IRequestVo getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(IRequestVo requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

}
