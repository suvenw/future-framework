package com.suven.framework.util.createcode.swagger;



public class SwaggerMethodBean {

    private String  requestInfo = "";  //请求接口说明
    private String  requestMethodName  = ""; //请求方法名
    private String  requestUrl  = "";  //请求url
    private String  requestMethodType = ""; //请求方式,post,get

    private String  requestName = "";//接口请求类名
    private String  responseName = "";//接口返回类名 request

    private String requestAuthor = "";

    public static SwaggerMethodBean build(){
        return new SwaggerMethodBean();
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public SwaggerMethodBean setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
        return this;
    }

    public String getRequestName() {
        return requestName;
    }

    public SwaggerMethodBean setRequestName(String requestName) {
        this.requestName = requestName;
        return this;
    }

    public String getResponseName() {
        return responseName  == null ? "" : responseName;
    }

    public SwaggerMethodBean setResponseName(String responseName) {
        this.responseName = responseName;
        return this;
    }

    public String getRequestMethodName() {
        return requestMethodName == null ? "" : requestMethodName;
    }

    public SwaggerMethodBean setRequestMethodName(String requestMethodName) {
        this.requestMethodName = requestMethodName;
        return this;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public SwaggerMethodBean setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    public String getRequestMethodType() {
        return requestMethodType == null ? "" : requestMethodType.toLowerCase();
    }

    public SwaggerMethodBean setRequestMethodType(String requestMethodType) {
        if(requestMethodType != null)
        this.requestMethodType = requestMethodType.toLowerCase();
        return this;
    }

    public String getRequestMethod() {
        return requestMethodType.toUpperCase();
    }

    public String getRequestAuthor() {
        return requestAuthor;
    }

    public SwaggerMethodBean setRequestAuthor(String requestAuthor) {
        this.requestAuthor = requestAuthor;
        return this;
    }
}
