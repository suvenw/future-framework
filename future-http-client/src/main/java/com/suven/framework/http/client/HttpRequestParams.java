package com.suven.framework.http.client;



import java.io.Serializable;
import java.util.Map;

/**
 * @author 作者 : suven
 * CreateDate 创建时间: 2023-02-03
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  api请求参数转换后的请求参数对象
 *  String url, Map<String, String> params, HttpProxyHeader header, boolean encode
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.66os.com
 **/


public class HttpRequestParams implements Serializable {

    private Map<String, String> bodyMap;
    private Map<String, String> headerMap;
    private boolean encode;
    private  String urlParam;
    private String signParam;

    public HttpRequestParams() {
    }
    public static HttpRequestParams build(Map<String, String> bodyMap, String urlParam) {
        return build(bodyMap,null,true,urlParam,null);
    }
    public static HttpRequestParams build(Map<String, String> bodyMap, Map<String, String> headers) {
        return build(bodyMap,headers,true,null,null);
    }
    public static HttpRequestParams build(Map<String, String> bodyMap, Map<String, String> headerMap, boolean encode,
                                          String urlParam, String signParam ) {
        HttpRequestParams request = new HttpRequestParams();
        request.urlParam = urlParam;
        request.bodyMap = bodyMap;
        request.headerMap = headerMap;
        request.encode = encode;
        request.signParam = signParam;
        return request;
    }


    public Map<String, String> getSignToBodyMap() {
        bodyMap.put("cliSign",this.getSignParam());
        return bodyMap;
    }
    public Map<String, String> getSignToHeaderMap() {
        headerMap.put("cliSign",this.getSignParam());
        return headerMap;
    }



    public Map<String, String> getBodyMap() {
        return bodyMap;
    }

    public void setBodyMap(Map<String, String> bodyMap) {
        this.bodyMap = bodyMap;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public boolean isEncode() {
        return encode;
    }

    public void setEncode(boolean encode) {
        this.encode = encode;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    public String getSignParam() {
        return signParam;
    }

    public void setSignParam(String signParam) {
        this.signParam = signParam;
    }
}
