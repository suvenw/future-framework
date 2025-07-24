package com.suven.framework.core.aliyun.sms;

/**
 * 阿里云发送短信返回错误提示信息类；
 */
public class AliyunSmsResult {

    private String code;
    private String message;
    private String requestId;
    private String bizId;

    private final String SUCCESS = "OK";

    public AliyunSmsResult(){

    }
    public static AliyunSmsResult build(){
        return new AliyunSmsResult();
    }

    /**
     * 成功信息
     * @return
     */
    public boolean isSuccess(){
        return SUCCESS.equals(code);
    }

    /**
     * 错误异常信息；
     * @return
     */
    public AliyunSmsErrorEnum getErrorEnum(){
        if(code == null){
           return AliyunSmsErrorEnum.SYSTEM_ERROR;
        }
        if(!isSuccess()){
           return AliyunSmsErrorEnum.getSmsError(code);
        }return AliyunSmsErrorEnum.SMS_SUCCESS;
    }

    public String getCode() {
        return code;
    }

    public AliyunSmsResult setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AliyunSmsResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getRequestId() {
        return requestId;
    }

    public AliyunSmsResult setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getBizId() {
        return bizId;
    }

    public AliyunSmsResult setBizId(String bizId) {
        this.bizId = bizId;
        return this;
    }
}
