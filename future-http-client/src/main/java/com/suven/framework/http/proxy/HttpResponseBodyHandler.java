package com.suven.framework.http.proxy;

import java.io.IOException;

public interface HttpResponseBodyHandler {

    /**
     * 来源于 HttpProxyRequest 请求参数的MediaType getMediaType();
     * @return
     */
    int contentType(int mediaType);

    int getContentType();

    /**
     * 各类框架根据bodyMediaType要求返回类型，做具体实现
     * @param httpResponse 框架的返回网络对象 httpResponse ，各业务自己强转
     * @param contentType 转换的结果类型
     * @return 返回字符串
     */
    String handleResponseBody(Object httpResponse, int contentType) throws IOException;

}
