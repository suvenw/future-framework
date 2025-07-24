package com.suven.framework.core.aliyun.sms;

public class AliyunSmsRequestDto {


    private String phoneNumbers;
    private String signName  ;
    private String templateCode;
    private String templateParam;
    private String outId;

    private AliyunSmsRequestDto() {
    }


    public static AliyunSmsRequestDto build(){
            return new AliyunSmsRequestDto().setSignName("爱看app");
    }

    public static AliyunSmsRequestDto build(SmsContentEnum smsContentEnum,long phoneNumber,String code){
        String phoneNumbers = String.valueOf(phoneNumber);
//        switch (smsContentEnum){
//            case SMS_REGISTER_CONTENT:
//              return   buildRegister(phoneNumbers,code);
//            case SMS_LOGIN_CONTENT:
//                return buildLogin(phoneNumbers,code);
//            case SMS_PASSWORD_RESET_CONTENT:
//               return buildPasswordReset(phoneNumbers,code);
//            case SMS_PHONE_BIND_CONTENT:
//                return buildPhoneBind(phoneNumbers,code);
//
//        }
        return  build().setSignName().setParam(phoneNumbers,code);
    }

    /**
     * 手机绑定
     * @return
     */
    private static AliyunSmsRequestDto buildPhoneBind(String phoneNumbers, String code) {
        return  build().setSignName().setTemplateCode("SMS_169100715").setParam(phoneNumbers,code);
    }


    /**
     * 注册验证
     * @return
     */
    public static AliyunSmsRequestDto buildRegister(String phoneNumbers,String code){

        return  build().setSignName().setTemplateCode("SMS_162521183").setParam(phoneNumbers,code);
    }
    /**
     * 动态密码登录
     * @return
     */
    public static AliyunSmsRequestDto buildLogin(String phoneNumbers,String code){
        return  build().setSignName().setTemplateCode("SMS_162198892").setParam(phoneNumbers,code);
    }
    /**
     * 密码重置
     * @return
     */
    public static AliyunSmsRequestDto buildPasswordReset(String phoneNumbers,String code){
        return  build().setSignName().setTemplateCode("SMS_162198891").setParam(phoneNumbers,code);
    }



    private AliyunSmsRequestDto setParam(String phoneNumbers,String code) {
        String templateParam = "{ \"code\":\""+code+"\"}";
        this.templateParam = templateParam;
        this.phoneNumbers = phoneNumbers;
        return this;
    }
    private AliyunSmsRequestDto setSignName() {
        this.signName = "爱看app";
        return this;
    }



    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public AliyunSmsRequestDto setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
        return this;
    }

    public String getSignName() {
        return this.signName;
    }


    public AliyunSmsRequestDto setSignName(String signName) {
        this.signName = signName;
        return this;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public AliyunSmsRequestDto setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
        return this;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public AliyunSmsRequestDto setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
        return this;
    }

    public String getOutId() {
        return outId;
    }

    public AliyunSmsRequestDto setOutId(String outId) {
        this.outId = outId;
        return this;
    }
}
