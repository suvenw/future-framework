package com.suven.framework.http.interceptor;

import com.suven.framework.http.message.HttpRequestPostMessage;
import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

/**
 * @author 作者 : suven
 * CreateDate创建时间: 2023-02-06
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description (说明):  参数请求拦截器,对接参数转换扩张实现
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.66os.com
 **/

public class JsonHandlerInterceptorRequestExt implements Serializable {




    public static JsonHandlerInterceptorRequestExt build(){
        return new JsonHandlerInterceptorRequestExt();
    }
    /**
     * 从请求头获取token
     * @param message
     * @param request
     */
    public void  getTokenByHeader(HttpRequestPostMessage message, HttpServletRequest request) throws Exception {
        if(null != message.getAccessToken()){
            return;
        }
        String accessToken = "accesstoken";
        String token  = request.getHeader(accessToken);
        if(token != null && "".equals(token)){
            message.setAccessToken(token);
        }
    }

}
