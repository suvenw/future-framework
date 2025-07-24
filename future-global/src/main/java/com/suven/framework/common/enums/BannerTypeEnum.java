package com.suven.framework.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName 广告类型
 * @Author xinye
 * @WeeK 星期四
 * @Version v2.0
 **/

public enum BannerTypeEnum {
    DISABLE("0","下架"), //下载
    GAME("1","游戏广告"), //上载
    GENERAL("2","普通广告"), //冻结
    START("3","开屏广告"),//禁言



    ;


    private String id;
    private String value;

    private  static Map<String, BannerTypeEnum> typeMap = new HashMap<>();
    static {
        for(BannerTypeEnum msgType : values()) {
            typeMap.put(msgType.id, msgType);
        }
    }

    BannerTypeEnum(String id , String value){
        this.id = id;
        this.value = value;
    }
    public static BannerTypeEnum getEnum(String index){
        return typeMap.get(index);
    }

    public String index() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
