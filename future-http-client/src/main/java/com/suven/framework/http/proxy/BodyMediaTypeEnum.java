package com.suven.framework.http.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 作者 : suven
 * CreateDate 创建时间: 2023-04-23
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  返回文件类型枚举
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.66os.com
 * bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
 **/


public enum BodyMediaTypeEnum {

    BODY_JSON(0, "JSON字符串"),
    BODY_JSON_STRING(1, "JSON字符串"),
    BODY_BYTES(2, "为文件流byte[]数组"),
    BODY_FILE(3, "为文件流"),
    ;


    private Integer code;
    private String desc;

    BodyMediaTypeEnum(Integer code,String desc) {
        this.code = code;
        this.desc=desc;
    }

    public Integer getCode() {
        return code;
    }


    public String getDesc() {
        return desc;
    }

    private static Map<Integer, BodyMediaTypeEnum> enumMap = new HashMap<>();
    static {
        for(BodyMediaTypeEnum enums : values()) {
            enumMap.put(enums.code, enums);
        }

    }


    public static BodyMediaTypeEnum code(Integer code){
        BodyMediaTypeEnum enums =  enumMap.get(code);
        if(null == enums){
            return null;
        }return enums;
    }

}
