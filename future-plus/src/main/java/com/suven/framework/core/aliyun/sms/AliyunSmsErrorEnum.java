package com.suven.framework.core.aliyun.sms;

import java.util.HashMap;
import java.util.Map;

public enum AliyunSmsErrorEnum {


    SMS_SUCCESS("OK","OK","0"),
    RAM_PERMISSION_DENY("isp.RAM_PERMISSION_DENY","RAM权限DENY","-1"),
    OUT_OF_SERVICE("isp.OUT_OF_SERVICE","业务停机","-1"),
    PRODUCT_UN_SUBSCRIPT("isv.PRODUCT_UN_SUBSCRIPT","未开通云通信产品的阿里云客户","-1"),
    PRODUCT_UNSUBSCRIBE("isv.PRODUCT_UNSUBSCRIBE","产品未开通","-1"),
    ACCOUNT_NOT_EXISTS("isv.ACCOUNT_NOT_EXISTS","账户不存在","-1"),
    ACCOUNT_ABNORMAL("isv.ACCOUNT_ABNORMAL","账户异常","-1"),
    INVALID_PARAMETERS("isv.INVALID_PARAMETERS","参数异常","-1"),
    SYSTEM_ERROR("isv.SYSTEM_ERROR","系统错误","-1"),

    SMS_TEMPLATE_ILLEGAL("isv.SMS_TEMPLATE_ILLEGAL","模板不合法(不存在或被拉黑)","-1"),

    SMS_SIGNATURE_ILLEGAL("isv.SMS_SIGNATURE_ILLEGAL","签名不合法(不存在或被拉黑)","-1"),
    MOBILE_NUMBER_ILLEGAL("isv.MOBILE_NUMBER_ILLEGAL","无效号码","-1"),
    TEMPLATE_MISSING_PARAMETERS("isv.TEMPLATE_MISSING_PARAMETERS","模板变量缺少对应参数值","-1"),


    EXTEND_CODE_ERROR("isv.EXTEND_CODE_ERROR","扩展码使用错","-1"),
    DOMESTIC_NUMBER_NOT_SUPPORTED("isv.DOMESTIC_NUMBER_NOT_SUPPORTED","国际/港澳台消息模板不支持发送境内号码","-1"),
    DAY_LIMIT_CONTROL("isv.DAY_LIMIT_CONTROL","触发日发送限额","-1"),
    SMS_CONTENT_ILLEGAL("isv.SMS_CONTENT_ILLEGAL","短信内容包含禁止发送内容","-1"),
    SMS_SIGN_ILLEGAL("isv.SMS_SIGN_ILLEGAL","签名禁止使用","-1"),


    ;

    private String code;
    private String message;
    private String requestId;

    AliyunSmsErrorEnum(String code, String message, String requestId){
        this.code = code;
        this.message = message;
        this.requestId = requestId;

    }
    private static Map<String, AliyunSmsErrorEnum> enumMap = new HashMap<>();
    static {

        for(AliyunSmsErrorEnum type : values()) {
            enumMap.put(type.code, type);
        }
    }


    public static AliyunSmsErrorEnum getSmsError(String code){
        return enumMap.get(code);
    }

    public String getCode() {
        return code;
    }

    public AliyunSmsErrorEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AliyunSmsErrorEnum setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getRequestId() {
        return requestId;
    }

    public AliyunSmsErrorEnum setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }
}
