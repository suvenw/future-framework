package com.suven.framework.http.api;

import com.suven.framework.core.IterableConvert;
import org.springframework.http.MediaType;

import java.util.Map;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *
 * <pre>
 * @description (说明):
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * date 创建时间: 2024-01-02
 **/

public enum HttpRequestTypeEnum {

    TYPE_ALL(0, "ALL", MediaType.ALL_VALUE),
    TYPE_FORM(1, "FORM", MediaType.APPLICATION_FORM_URLENCODED_VALUE),
    TYPE_HEADER(2, "HEADER", MediaType.APPLICATION_JSON_VALUE),
    TYPE_JSON(3, "JSON", MediaType.APPLICATION_JSON_VALUE),
    TYPE_STREAM(4, "STREAM", MediaType.APPLICATION_OCTET_STREAM_VALUE),

    ;
    private static Map<Integer, HttpRequestTypeEnum> enumsMap;

    static {
        enumsMap = IterableConvert.convertMap(values(), HttpRequestTypeEnum::getCode);
    }

    public static HttpRequestTypeEnum getEnum(int index) {
        return enumsMap.get(index);
    }

    int code;
    String contentType;
    String mediaType;

    HttpRequestTypeEnum(int code, String contentType, String mediaType) {
        this.code = code;
        this.contentType = contentType;
        this.mediaType = mediaType;
    }

    boolean isJson(){
        return  this.code == TYPE_JSON.getCode();
    }
    boolean isForm(){
        return  this.code == TYPE_FORM.getCode();
    }
    boolean isStream(){
        return  this.code == TYPE_STREAM.code;
    }
    boolean isAll(){
        return  this.code == TYPE_ALL.code;
    }

    public int getCode() {
        return code;
    }

    public String getContentType() {
        return contentType;
    }

    public String getMediaType() {
        return mediaType;
    }
}