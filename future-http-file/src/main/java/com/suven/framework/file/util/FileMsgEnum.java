package com.suven.framework.file.util;

import com.google.common.collect.Maps;
import com.suven.framework.http.inters.IResultCodeEnum;

import java.util.Map;

public enum FileMsgEnum  implements IResultCodeEnum {

    //RESOURCE_BANNER_SAVE_FAIL(1103001,"保存广告banner失败"),

    UPLOAD_FILE_IS_NULL_FAIL(1103002,"上传文件不能为空"),
    UPLOAD_FILE_SIZE_FAIL(1103003,"上传文件大小不一致"),
    UPLOAD_FILE_PATH_NULL(1103004,"下载文件URL不能为空"),
    UPLOAD_FILE_SIZE_ZERO(1103005,"文件大小不能为0"),

    DELETE_FILE_PATH_IS_NULL(1103006,"文件路径不能为空"),
    UPLOAD_FILE_EXCEPTION_FAIL(1103007,"文件上传未知异常"),


//    RESOURCE_BANNER_UPDATE_FAIL(1103006,"更新启动页广告失败"),
    //RESOURCE_BANNER_DELETE_FAIL(1103007,"删除启动页广告失败"),
    ;
    private int code;
    private String msg;
    private static Map<String, FileMsgEnum> msgTypeMap = Maps.newHashMap();
    static {
        for(FileMsgEnum msgType : values()) {
            msgTypeMap.put(msgType.name(), msgType);
        }
    }

    FileMsgEnum(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }
}
