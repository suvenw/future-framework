package com.suven.framework.http.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author by suven on 18-8-7.
 */
public enum RegexEnum {

    EMPTY(0, "","为空"),
    NOTNULL(1, "NotNull","非空"),
    UNIQUE(2, "unique","唯一校验"),
    PHONE(3, "Phone","电话号码"),
    MOBILE(4, "Mobile","手机号码"),
    EMAIL(5, "Email","电子邮件"),
    URL(6, "URL","网址"),
    DIGIT(7, "Digit","数字"),
    INTEGER(8, "Int","整数"),
    POSTCODE(9, "PostCode","邮政编码"),
    PASSWD(9, "Passwd","密码"),
    IDCARD(11, "IdCard","身份证"),
    CHINESE(12,"Chinese","中文"),
    DATE(13,"Date","日期"),
    DATETIME(14,"DateTime","日期时间"),
    MONTH(15,"Month","月份"),
    DAY(16,"Day","天数"),
    FLOAT(17,"Float","小数数字");

    private Integer code;
    private String key;
    private String desc;

    RegexEnum(Integer code, String key, String desc) {
        this.code = code;
        this.key = key;
        this.desc=desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    private static Map<String, RegexEnum> enumMap = new HashMap<>();
    static {
        for(RegexEnum enums : values()) {
            enumMap.put(enums.desc, enums);
        }

    }


    public static String getKey(String des){
        RegexEnum enums =  enumMap.get(des);
        if(null == enums){
            return null;
        }return enums.key;
    }

}
