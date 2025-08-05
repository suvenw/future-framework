package com.suven.framework.file.util;

import java.util.HashMap;
import java.util.Map;

public enum ContentTypeEnum {
    DEFAULT(0,"image/jpeg"),
    BMP(1,"image/bmp"),
    GIF(2,"image/gif"),
    JPEG(3,"image/jpeg"),
    JPG(4,"image/jpeg"),
    PNG(5,"image/jpeg"),
    HTML(6,"text/html"),
    TEXT(8,"text/plain"),
    XML(9,"text/xml"),
    PPT(10,"application/vnd.ms-powerpoint"),
    PPTX(11,"application/vnd.ms-powerpoint"),
    DOC(12,"application/msword"),
    DOCX(13,"application/msword"),
    VSD(14,"application/vnd.visio"),




        ;

    private  static Map<Integer, ContentTypeEnum> typeMap = new HashMap<>();
    static {
        for(ContentTypeEnum msgType : values()) {
            typeMap.put(msgType.id, msgType);
        }
    }

    public static ContentTypeEnum getEnum(int index){
        return typeMap.get(index);
    }



    private int id;
    private String value;
    private ContentTypeEnum(int id,String value){
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
