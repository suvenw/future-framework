package com.suven.framework.http.proxy.hutool;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.proxy.*;
import com.suven.framework.http.util.HttpParamsUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class AbstractHutoolRequestProxy extends AbstractHttpProxy implements HttpRequestProxy<HutoolRequestBuilder> {


    public AbstractHutoolRequestProxy(HttpClientConfig httpClientConfig) {
       super(httpClientConfig);
    }



    /**
     *  同步执行逻辑请求的方法实现
     * @param httpRequestBuilder
     *  httpProxyRequest HttpProxyRequest   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
     *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
     *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
     *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
     *  encode 是否需要encode转码值为true 或 false, 默认为false
     *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
     *  https 是否使用证书  https 的值为 true 或 false, 默认为false
     * @return 返回网络请求结果对象
     */
    private HttpClientResponse execute(HutoolRequestBuilder httpRequestBuilder, boolean isAsync) {
        // 设置超时时长
        HttpRequest request = httpRequestBuilder.getRequest();
        HttpProxyRequest proxyRequest = httpRequestBuilder.getHttpProxyRequest();
        request = request.timeout(proxyRequest.getTimeout());
        // 设置代理
        if (proxyRequest.isProxy()) {
            request = request.setProxy(this.getProxy());
        }

        try {
            HttpResponse response = null;
            if(isAsync){
                response = request.executeAsync();
                if(!proxyRequest.isFutureResult()){
                    return null;
                }

            }else {
                response = request.execute();
            };
            HttpResponseBodyHandler handler = new HutoolResponseBodyHandler(proxyRequest.getBodyMediaType());
            HttpClientResponse result = this.getHttpClientResponse(response,handler);
            return  result;
        } catch (Exception e) {
            e.printStackTrace();
            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }

    /**
     *  同步执行逻辑请求的方法实现
     * @param httpRequestBuilder
     *  httpProxyRequest HttpProxyRequest   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
     *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
     *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
     *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
     *  encode 是否需要encode转码值为true 或 false, 默认为false
     *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
     *  https 是否使用证书  https 的值为 true 或 false, 默认为false
     * @return 返回网络请求结果对象
     */
    @Override
    public HttpClientResponse execute(HutoolRequestBuilder httpRequestBuilder) {
        HttpClientResponse response =  this.execute(httpRequestBuilder,false);
        return response;
    }

    /**
     *  异步执行逻辑请求的方法实现,默认是读取返回结果
     * @param httpRequestBuilder 代码各业务的请求的Request
     *  future FutureCallbackProxy 返回处理的异步线程
     *  httpProxyRequest HttpProxyRequest   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
     *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
     *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
     *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
     *  encode 是否需要encode转码值为true 或 false, 默认为false
     *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
     *  https 是否使用证书  https 的值为 true 或 false, 默认为false
     * @return HttpClientResponse 代码请求对象
     */
    @Override
    public HttpClientResponse executeAsync(HutoolRequestBuilder httpRequestBuilder, FutureCallbackProxy futureProxy) {
        HttpClientResponse response =  this.execute(httpRequestBuilder,true);
        return  response;
    }

    /**
     *  异步执行逻辑请求的方法实现,默认是读取返回结果
     * @param httpRequestBuilder 代码各业务的请求的Request
     *  future FutureCallbackProxy 返回处理的异步线程
     *  httpProxyRequest HttpProxyRequest   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
     *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
     *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
     *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
     *  encode 是否需要encode转码值为true 或 false, 默认为false
     *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
     *  https 是否使用证书  https 的值为 true 或 false, 默认为false
     * @return HttpClientResponse 代码请求对象
     */
    public HttpClientResponse executeAsync(HutoolRequestBuilder httpRequestBuilder) {
        HttpClientResponse response =  this.executeAsync(httpRequestBuilder,null);
        return  response;
    }

    /**
     *  根据请求参数类型,根据网络架构的返回数据结果,转换到统一规范对象HttpClientResponse
     *  bodyMediaType ,根据请求参数类型, 0/1为 json 字符串,2.为文件流
     * @param httpResponse 网络架构的返回数据结果
     * @return 返回网络请求结果
     * @throws IOException 网格 io 异常
     */
    @Override
    public  HttpClientResponse getHttpClientResponse(Object httpResponse,HttpResponseBodyHandler handler) throws IOException {
        HttpResponse response = (HttpResponse) httpResponse;
        int code = response.getStatus();
        boolean successful = response.isOk();
        Map<String, List<String>> headers = response.headers();
        try {
            String body =  handler.handleResponseBody(httpResponse,handler.getContentType());
            return HttpClientResponse.build(successful, code, headers, body, null);
        }catch (Exception e){
            return HttpClientResponse.build(successful, code, headers, "", e.getMessage());
        }

    }
    /**
     * GET 方式 提交表单实现的业务,转换请求执行的逻辑的实现方法,返回各业务的请求对像的代理对象HttpRequestBuilder
     * @param url 请求url
     * @param params 请求参数 Map的k-v集合
     * @param header 请求头部参数
     * @param proxyRequest HttpProxyRequest   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
     *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
     *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
     *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
     *  encode 是否需要encode转码值为true 或 false, 默认为false
     *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
     *  https 是否使用证书  https 的值为 true 或 false, 默认为false
     * @return HttpRequestBuilder 代码请求对象
     */
    @Override
    public HutoolRequestBuilder getRequest(String url, Map<String, ?> params, HttpProxyHeader header, HttpProxyRequest proxyRequest )  {
        this.initHttpProxyRequest(proxyRequest);
        String requestUrl = url;
        if (HttpParamsUtil.isNotEmpty(params)) {
            String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
            requestUrl = baseUrl + HttpParamsUtil.parseMapToString(params,proxyRequest.isEncode());
        }
        HttpRequest request = HttpRequest.get(requestUrl);

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::header);
        }
        HutoolRequestBuilder requestBuilder = new HutoolRequestBuilder(request,proxyRequest);

        return requestBuilder;
    }



    /**
     * POST 表单 方式 提交表单实现的业务,转换请求执行的逻辑的实现方法,返回各业务的请求对像的代理对象HttpRequestBuilder
     * @param url 请求url
     * @param params 请求参数 Map的k-v集合
     * @param header 请求头部参数
     * @param proxyRequest HttpProxyRequest   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
     *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
     *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
     *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
     *  encode 是否需要encode转码值为true 或 false, 默认为false
     *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
     *  https 是否使用证书  https 的值为 true 或 false, 默认为false
     * @return HttpRequestBuilder 代码请求对象
     */
    @Override
    public HutoolRequestBuilder postFormRequest(String url, Map<String, ?> params, HttpProxyHeader header, HttpProxyRequest proxyRequest  )  {
        HttpRequest request = HttpRequest.post(url);
        this.initHttpProxyRequest(proxyRequest);
        if (proxyRequest.isEncode()) {
            HttpParamsUtil.forFunction(params, (k, v) -> request.form(k, HttpParamsUtil.urlEncode(v)));
        } else {
            HttpParamsUtil.forFunction(params, request::form);
        }

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::header);
        }
        HutoolRequestBuilder requestBuilder = new HutoolRequestBuilder(request,proxyRequest);
        return requestBuilder;

    }


    /**
     * POST 方式 提交表单实现的业务,转换请求执行的逻辑的实现方法,返回各业务的请求对像的代理对象HttpRequestBuilder
     * @param url 请求url
     * @param jsonData 请求参数 JSON 格式数据
     * @param header 请求头部参数
     * @param proxyRequest HttpProxyRequest   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
     *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
     *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
     *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
     *  encode 是否需要encode转码值为true 或 false, 默认为false
     *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
     *  https 是否使用证书  https 的值为 true 或 false, 默认为false
     * @return HttpRequestBuilder 代码请求对象
     */
    @Override
    public HutoolRequestBuilder postJsonRequest(String url, String jsonData, HttpProxyHeader header, HttpProxyRequest  proxyRequest )  {
        HttpRequest request = HttpRequest.post(url);

        if (HttpParamsUtil.isNotEmpty(jsonData)) {
            request.body(jsonData);
        }
        initJsonHeaders(header);
        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::header);
        }
        HutoolRequestBuilder requestBuilder = new HutoolRequestBuilder(request,proxyRequest);
        return requestBuilder;


    }


}
