package com.suven.framework.http.proxy.httpclient;

import cn.hutool.core.codec.Base64Encoder;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.BodyMediaTypeEnum;
import com.suven.framework.http.proxy.HttpResponseBodyHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ApacheResponseBodyHandler implements HttpResponseBodyHandler {

    private int mediaType;

    public ApacheResponseBodyHandler(int mediaType) {
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
     * 6.结果是否成功标识,各个不同的网络架构返回的结果,和验证功能的依据不一样,通过proxy统一转换成统一的返回结果实现类
     *
     * @param httpResponse     框架的返回网络对象 httpResponse ，各业务自己强转
     * @param contentType 转换的结果类型，请求网络请求数据类型
     * @return 返回 contentType 对应的结果内容信息
     */
    @Override
    public String handleResponseBody(Object httpResponse, int contentType) throws IOException {
        int mediaType = contentType(contentType);
        CloseableHttpResponse response = (CloseableHttpResponse)httpResponse;
        BodyMediaTypeEnum bodyMediaTypeEnum = BodyMediaTypeEnum.code(mediaType);
        String body = "";
        switch (bodyMediaTypeEnum) {
            case BODY_JSON:
            case BODY_JSON_STRING:
                body = EntityUtils.toString(response.getEntity(), HttpClientConstants.DEFAULT_ENCODING);
                break;
            case BODY_BYTES:
                String content = EntityUtils.toString(response.getEntity(), HttpClientConstants.DEFAULT_ENCODING);
                body = Base64Encoder.encode(content);
                break;
            case BODY_FILE:
                body = IOUtils.toString(response.getEntity().getContent(), HttpClientConstants.DEFAULT_ENCODING);
            default:
        }
        return body;
    }

}
