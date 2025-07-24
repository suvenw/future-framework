package com.suven.framework.http.api;


/**
 * 框架http请求接口接收对象统一实现类
 */
public interface HttpHeaderRequest extends HttpRequestType {

    @Override
    default int parseType() {
        return HttpRequestTypeEnum.TYPE_HEADER.getCode();
    }

//    /**
//     * http 提交参数头部实现java反射类对象;
//     * @param headersMap http头的聚合信息
//     * @param headerClass  反射接收类对象
//     * @return
//     */
//
//    <T> T parseHeader(Map<String, String> headersMap, Class<T> headerClass) ;
}
