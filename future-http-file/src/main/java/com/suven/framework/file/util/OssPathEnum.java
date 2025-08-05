package com.suven.framework.file.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 文件业务类型实现枚举
 */
public enum OssPathEnum  implements IOssPath{
    DEFAULT(0,""),
    LOGO(1,"logo"),
    USER(2,"user"),
    GIF(3,"gif"),
    BANNER(4,"banner"),
    GATHER(5,"gather"),
    SOFTWARE(6,"software"),





    ;

    private  static Map<Integer, OssPathEnum> typeMap = new HashMap<>();
    static {
        for(OssPathEnum msgType : values()) {
            typeMap.put(msgType.id, msgType);
        }
    }

    public static IOssPath getEnum(int index){
        return typeMap.get(index);
    }



    private int id;
    private String value;
    private OssPathEnum(int id,String value){
        this.id = id;
        this.value = value;
    }

    public int id() {
        return id;
    }

    public String value(){
        return value;
    }
}
