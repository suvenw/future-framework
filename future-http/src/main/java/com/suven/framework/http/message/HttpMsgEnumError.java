package com.suven.framework.http.message;

import com.suven.framework.http.api.ApiDesc;

import java.io.Serializable;

/**
 * @author 作者 : suven.wang
 * CreateDate创建时间: 2021-12-15
 * @WeeK 星期: 星期四
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description (说明):  错误码实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
public class HttpMsgEnumError  implements Comparable<HttpMsgEnumError> ,Serializable {

    @ApiDesc(value = "错误编码code", required = 0)
    private  int code;
    @ApiDesc(value = "错误编码code描述信息", required = 0)
    private String msg;



    public HttpMsgEnumError() {
    }
    public HttpMsgEnumError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public  static HttpMsgEnumError build(){
        return new HttpMsgEnumError();
    }


    public HttpMsgEnumError init(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    @Override
    public int compareTo( HttpMsgEnumError o) {
        return this.code - o.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
