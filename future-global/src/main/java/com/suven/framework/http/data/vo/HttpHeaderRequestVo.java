package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.HttpHeaderRequest;

import java.io.Serializable;

public class HttpHeaderRequestVo implements HttpHeaderRequest,Serializable {


    public static HttpHeaderRequestVo build(){
        return new HttpHeaderRequestVo();
    }



}

