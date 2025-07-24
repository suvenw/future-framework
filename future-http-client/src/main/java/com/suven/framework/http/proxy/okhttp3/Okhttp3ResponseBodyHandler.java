package com.suven.framework.http.proxy.okhttp3;

import com.suven.framework.http.proxy.BodyMediaTypeEnum;
import com.suven.framework.http.proxy.HttpResponseBodyHandler;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class Okhttp3ResponseBodyHandler implements HttpResponseBodyHandler {

    private int mediaType;

    public Okhttp3ResponseBodyHandler(int mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * 来源于 HttpProxyRequest 请求参数的MediaType getMediaType();
     *
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
     * @param httpResponse     框架的返回网络对象 httpResponse ，各业务自己强转
     * @param contentType 转换的结果类型
     * @return 返回字符串
     */
    @Override
    public String handleResponseBody(Object httpResponse, int contentType) throws IOException {
        int mediaType = contentType(contentType);
        Response response = (Response) httpResponse;
        String body = "";
        if (null == response.body()) {
            return body;
        }
        ResponseBody responseBody = response.body();
        BodyMediaTypeEnum bodyMediaTypeEnum = BodyMediaTypeEnum.code(mediaType);
        switch (bodyMediaTypeEnum) {
            case BODY_JSON:
            case BODY_JSON_STRING:
                body = responseBody.string();
                break;
            case BODY_BYTES:
            case BODY_FILE:
                body = responseBody.byteString().base64();
                break;

        }
        return body;
    }
}

