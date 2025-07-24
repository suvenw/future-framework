package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.HttpJsonRequest;

import java.io.Serializable;

public class HttpJsonRequestVo implements HttpJsonRequest,Serializable {


    public static HttpJsonRequestVo build(){
        return new HttpJsonRequestVo();
    }



}

