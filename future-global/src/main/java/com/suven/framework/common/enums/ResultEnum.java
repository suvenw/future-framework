package com.suven.framework.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName StatusEnum
 * @Author suven.wang
 * @Description 说明: 请求结果成功,失败值枚举类, 包括:0.失败, 1.成功
 * @CreateDate 2018-12-19  20:10
 * @WeeK 星期三
 * @Version v2.0
 *
 **/
public enum ResultEnum {
    FAIL(0), //"失败"
    SUCCESS(1), //"成功"


    ;


    private int id;

    private  static Map<Integer, ResultEnum> typeMap = new HashMap<>();
    static {
        for(ResultEnum msgType : values()) {
            typeMap.put(msgType.id, msgType);
        }
    }

    ResultEnum(int id){
        this.id = id;
    }
    public static ResultEnum getEnum(int index){
        return typeMap.get(index);
    }

    public int id() {
        return id;
    }


    }
