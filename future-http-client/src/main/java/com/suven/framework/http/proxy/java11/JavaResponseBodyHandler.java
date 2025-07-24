package com.suven.framework.http.proxy.java11;

import cn.hutool.core.codec.Base64Encoder;
import com.suven.framework.http.proxy.BodyMediaTypeEnum;
import com.suven.framework.http.proxy.HttpResponseBodyHandler;

import java.io.IOException;
import java.net.http.HttpResponse;

public class JavaResponseBodyHandler implements HttpResponseBodyHandler {

    private int mediaType;

    public JavaResponseBodyHandler(int mediaType) {
        this.mediaType = mediaType;
    }


    /**
     * 来源于 HttpProxyRequest 请求参数的MediaType getMediaType();
     *
     * @param mediaType
     * @return
     */
    @Override
    public int contentType(int mediaType) {
        if ( 0 != mediaType){
            this.mediaType = mediaType;
        }
        return this.mediaType;
    }

    /**
     * @return
     */
    @Override
    public int getContentType() {
        return mediaType;
    }

    /**
     * 各类框架根据bodyMediaType要求返回类型，做具体实现
     *
     * @param httpResponse 框架的返回网络对象 httpResponse ，各业务自己强转
     * @param contentType  转换的结果类型
     * @return 返回字符串
     */
    @Override
    public String handleResponseBody(Object httpResponse, int contentType) throws IOException {
        HttpResponse<String> response = ( HttpResponse<String>)httpResponse;

        BodyMediaTypeEnum bodyMediaTypeEnum = BodyMediaTypeEnum.code(contentType);
        String body = "";
        switch (bodyMediaTypeEnum) {
            case BODY_JSON:
            case BODY_JSON_STRING:
                body = response.body();
                break;
            case BODY_BYTES:
            case BODY_FILE:
                byte[] bytes = response.body().getBytes();
                body = Base64Encoder.encode(bytes);
                break;
        }
        return body;
    }
}
