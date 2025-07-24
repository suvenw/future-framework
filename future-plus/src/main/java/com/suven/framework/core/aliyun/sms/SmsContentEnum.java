package com.suven.framework.core.aliyun.sms;

import java.util.HashMap;
import java.util.Map;

/**
 * 1.注册,2登陆,3.重置密码
 */
public enum SmsContentEnum {
    SMS_REGISTER_CONTENT(1,"您正在申请手机注册，验证码为：${code}，5分钟内有效！"),
    SMS_LOGIN_CONTENT(2,"${code}，您正在登录，若非本人操作，请勿泄露。"),
    SMS_PASSWORD_RESET_CONTENT (3,"${code}，您正在进行密码重置操作，如非本人操作，请忽略本短信！"),
    SMS_PHONE_BIND_CONTENT (4,"${code}，您正在进行手机绑定操作，如非本人操作，请忽略本短信！"),
    ;
    private int index;
    private String content;
    SmsContentEnum(int index, String content){
        this.index = index;
        this.content = content;
    }

    private static Map<Integer, SmsContentEnum> enumMap = new HashMap<>();
    static {
        for(SmsContentEnum enums : values()) {
            enumMap.put(enums.index, enums);
        }
    }

    public static SmsContentEnum getSmsContentEnum(int index){
        return enumMap.get(index);
    }

    public int getIndex() {
        return index;
    }

    public String getContent() {
        return content;
    }


}
