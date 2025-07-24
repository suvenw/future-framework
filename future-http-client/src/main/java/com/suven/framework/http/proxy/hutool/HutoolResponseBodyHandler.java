package com.suven.framework.http.proxy.hutool;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.http.HttpResponse;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.BodyMediaTypeEnum;
import com.suven.framework.http.proxy.HttpResponseBodyHandler;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class HutoolResponseBodyHandler  implements HttpResponseBodyHandler {

    private int mediaType;

    public HutoolResponseBodyHandler(int mediaType) {
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
        HttpResponse response = (HttpResponse) httpResponse;
        String body = "";
        int mediaType = contentType(contentType);
        if (null == response.body()) {
            return body;
        }
        BodyMediaTypeEnum bodyMediaTypeEnum = BodyMediaTypeEnum.code(mediaType);
        switch (bodyMediaTypeEnum) {
            case BODY_JSON:
            case BODY_JSON_STRING:
                body = response.body();
                break;
            case BODY_BYTES:
                body = Base64Encoder.encode(response.bodyBytes());
                break;
            case BODY_FILE:
                body = IOUtils.toString(response.bodyStream(), HttpClientConstants.DEFAULT_ENCODING);
                break;
        }
        return body;
    }
}
