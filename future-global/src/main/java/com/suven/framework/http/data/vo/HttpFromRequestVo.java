package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.HttpFromRequest;
import com.suven.framework.http.api.IBeanClone;

import java.io.Serializable;

public  class HttpFromRequestVo implements HttpFromRequest, IBeanClone,Serializable {

    public static HttpFromRequestVo build(){
        return new HttpFromRequestVo();
    }

}

