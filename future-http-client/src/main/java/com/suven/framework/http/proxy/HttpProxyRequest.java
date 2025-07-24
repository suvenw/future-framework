package com.suven.framework.http.proxy;


import okhttp3.MediaType;

import java.util.Objects;

/**
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2023-03-21
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  http 网络请求,个性业务参数扩张请求接口类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.66os.com
 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
 *  encode 是否需要encode转码值为true 或 false, 默认为false
 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
 **/


public interface HttpProxyRequest {
    /**  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行 **/
    int getTimeout() ;
    /**  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行 **/
    int getTimeout(int timeout) ;

    /**  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流**/
    int getBodyMediaType();
    /**  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null **/
    boolean isFutureResult();

    /** encode 是否需要encode转码值为true 或 false, 默认为false **/
    boolean isEncode();

    /** proxy 是否使用代理 proxy的值为 true 或 false, 默认为false **/
    boolean isProxy();

    /** https 是否使用 https 证书请求 https 的值为 true 或 false, 默认为false **/
    boolean isHttps();
    /** application/json; charset=utf-8
     *  An rfc_2045 Media Type, appropriate to describe the content type of an HTTP request or response body**/
    MediaType getMediaType();

    public static void initHttpProxyRequest(HttpProxyRequest httpProxyRequest){
        if(Objects.isNull(httpProxyRequest)){
            httpProxyRequest = HttpProxyDefaultRequest.builder();
        }
    }

}
