package com.suven.framework.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TbStatusEnum
 * @Author suven.wang
 * @Description 表状态属性值枚举类,0.下架,1.上架
 * @CreateDate 2018-11-15  12:45
 * @WeeK 星期四
 * @Version v2.0
 **/

public enum  TbStatusEnum {
    DISABLE(0,"下架"), //下载
    ENABLE(1,"上架"), //上载
    FROZEN(2,"冻结"), //冻结
    MUTED(1,"禁言"),//禁言
    NOT_MUTED(0,"解除禁言"),//解除禁言



    ;


    private int id;
    private String value;

    private  static Map<Integer, TbStatusEnum> typeMap = new HashMap<>();
    static {
        for(TbStatusEnum msgType : values()) {
            typeMap.put(msgType.id, msgType);
        }
    }

    TbStatusEnum(int id , String value){
        this.id = id;
        this.value = value;
    }
    public static TbStatusEnum getEnum(int index){
        return typeMap.get(index);
    }

    public int index() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
