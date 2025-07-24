package com.suven.framework.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PlatformEnum
 * @Author suven.wang
 * @Description 账号平台型属性值枚举类,
 * 包括:0."缺省平台", 1.苹果手机 ,2.安卓手机, 3.window手机, 4.官方网站, 5.小程序, 6.H5移动端, 7.后台管理
 * 11.苹果PAD ,12.安卓PAD, 13.windowPAD, 15.小程序PAD, 16.H5移动端PAD
 * @CreateDate 2018-11-15  12:45
 * @WeeK 星期四
 * @Version v2.0
 **/
public enum PlatformEnum {
    DEFAULT(0,"缺省平台"),
    IOS(1,"苹果手机"),
    ANDROID(2,"安卓手机"),
    WINDOW (3,"window 手机"),
    WEB (4,"官方网站"),
    PROGRAM (5,"小程序"),
    H5 (6,"H5移动端"),
    SYS (7,"后台管理"),



    IOS_PAD(11,"苹果PAD"),
    ANDROID_PAD(12,"安卓PAD"),
    WINDOW_PAD (13,"windowPAD"),
    PROGRAM_PAD (15,"小程序PAD"),
    H5_PAD (16,"H5移动端PAD"),
    ;


    private int index;
    private String value;

    private  static Map<Integer, PlatformEnum> typeMap = new HashMap<>();
    static {
        for(PlatformEnum msgType : values()) {
            typeMap.put(msgType.index, msgType);
        }
    }

    PlatformEnum(int index ,String value){
        this.index = index;
        this.value = value;
    }

    public int getId() {
        return index;
    }

    public String getValue() {
        return value;
    }

    public static PlatformEnum getType(int id) {
        return  typeMap.get(id);
    }
}
