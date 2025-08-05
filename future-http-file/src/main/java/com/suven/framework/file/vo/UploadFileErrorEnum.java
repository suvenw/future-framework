package com.suven.framework.file.vo;


import java.util.HashMap;
import java.util.Map;

public enum  UploadFileErrorEnum {

    UPLOAD_FILE_SUCCESS(0,"文件上传成功"),
    UPLOAD_FILE_NOT_FINISH(100,"文件上传尚未完成,请继续上传"),
    UPLOAD_FILE_ERROR_DEFAULT(101,"上传文件未知错误"),
    UPLOAD_FILE_ERROR_EXT_NAME(102,"上传文件类型不一致"),
    UPLOAD_FILE_ERROR_OVER_SIZE(103,"超过文件大小"),
    UPLOAD_FILE_ERROR_OVER_BLOCK_SIZE(104,"上传文件超过块大小"),
    UPLOAD_FILE_ERROR_OFFSET(105,"续传位置错误，请用新位置"),


    UPLOAD_FILE_ERROR_TO_FST(110,"上传文件到fastDFS失败，请重试"),
    UPLOAD_FILE_ERROR_IS_NULL(111,"上传文件不能为空，请重试"),
    UPLOAD_FILE_ERROR_SIZE_NOT_UNANIMOUS(112,"文件大小不一致"),

    UPLOAD_FILE_ERROR_TO_OSS(120,"上传文件到阿里OSS失败，请重试"),
    ;

    private int index;
    private String name;




    UploadFileErrorEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public UploadFileErrorEnum setIndex(int index) {
        this.index = index;
        return this;
    }

    public UploadFileErrorEnum setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    private static Map<Integer, UploadFileErrorEnum> typeMap = new HashMap<>();
    static {

        for(UploadFileErrorEnum type : values()) {
            typeMap.put(type.index, type);
        }
    }
    public static UploadFileErrorEnum getGoodsTypeEnum(int index){
        UploadFileErrorEnum enums =  typeMap.get(index);
        enums =  enums == null ? UploadFileErrorEnum.UPLOAD_FILE_ERROR_DEFAULT : enums;
        return enums;
    }
}
