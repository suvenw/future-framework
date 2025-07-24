package com.suven.framework.http.proxy.okhttp3;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.*;
import com.suven.framework.http.util.HttpParamsUtil;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AbstractOkHttp3RequestProxy  extends AbstractHttpProxy implements HttpRequestProxy<Okhttp3RequestBuilder> {

    public static final MediaType CONTENT_TYPE_JSON = MediaType.get(HttpClientConstants.CONTENT_TYPE_JSON);
    protected final OkHttpClient.Builder httpClientBuilder ;

    public AbstractOkHttp3RequestProxy(HttpClientConfig httpClientConfig) {
       super(httpClientConfig);
        this.httpClientBuilder = new OkHttpClient().newBuilder();
    }
    public AbstractOkHttp3RequestProxy(HttpClientConfig httpClientConfig,OkHttpClient.Builder httpClientBuilder) {
        super(httpClientConfig);
        this.httpClientBuilder = httpClientBuilder;
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
     * @return
     */
    @Override
    public HttpClientResponse execute(Okhttp3RequestBuilder httpRequestBuilder) {
        HttpProxyRequest httpProxyRequest = httpRequestBuilder.getHttpProxyRequest();
        Request  request =  httpRequestBuilder.getRequest();
        OkHttpClient httpClient;
        // 设置代理
        if (httpProxyRequest.isProxy()) {
            httpClient = httpClientBuilder.connectTimeout(Duration
                            .ofMillis(httpProxyRequest.getTimeout()))
                    .writeTimeout(Duration.ofMillis(httpProxyRequest.getTimeout()))
                    .readTimeout(Duration.ofMillis(httpProxyRequest.getTimeout()))
                    .proxy(this.getProxy()).build();
        } else if (httpProxyRequest.isHttps()) {
            String protocol = HttpClientConstants.INTERNATIONAL_PROTOCOL;
            HttpSSLCipherSuiteUtil.createOkHttpClientBuilder(protocol, httpClientBuilder);
            httpClient = httpClientBuilder
                    .connectTimeout(Duration.ofMillis(httpProxyRequest.getTimeout()))
                    .writeTimeout(Duration.ofMillis(httpProxyRequest.getTimeout()))
                    .readTimeout(Duration.ofMillis(httpProxyRequest.getTimeout())).build();
        }else {
            httpClient = httpClientBuilder
                    .connectTimeout(Duration.ofMillis(httpProxyRequest.getTimeout()))
                    .writeTimeout(Duration.ofMillis(httpProxyRequest.getTimeout()))
                    .readTimeout(Duration.ofMillis(httpProxyRequest.getTimeout())).build();
        }

        try (Response response = httpClient.newCall(request).execute()) {
            HttpResponseBodyHandler handler = new Okhttp3ResponseBodyHandler(httpProxyRequest.getBodyMediaType());
            HttpClientResponse result =  this.getHttpClientResponse(response,handler);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }


    /**
     *  异步执行逻辑请求的方法实现,默认是读取返回结果
     * @param httpRequestBuilder 代码各业务的请求的Request
     * @param future FutureCallbackProxy 返回处理的异步线程
     *  httpProxyRequest HttpProxyRequest   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
     *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
     *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
     *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
     *  encode 是否需要encode转码值为true 或 false, 默认为false
     *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
     *  https 是否使用证书  https 的值为 true 或 false, 默认为false
     * @return HttpClientResponse 代码请求对象
     * @return
     */
    @Override
    public HttpClientResponse executeAsync(Okhttp3RequestBuilder httpRequestBuilder, FutureCallbackProxy future) {
        OkHttpClient httpClient;
        HttpProxyRequest httpProxyRequest = httpRequestBuilder.getHttpProxyRequest();
        Request  request =  httpRequestBuilder.getRequest();
        // 设置代理
        if ( httpClientConfig.isProxy()) {
            httpClient = httpClientBuilder.connectTimeout(Duration
                            .ofMillis(httpProxyRequest.getTimeout()))
                    .writeTimeout(Duration.ofMillis(httpProxyRequest.getTimeout()))
                    .readTimeout(Duration.ofMillis(httpProxyRequest.getTimeout()))
                    .proxy(this.getProxy()).build();
        } else {
            httpClient = httpClientBuilder
                    .connectTimeout(Duration.ofMillis(httpProxyRequest.getTimeout()))
                    .writeTimeout(Duration.ofMillis(httpProxyRequest.getTimeout()))
                    .readTimeout(Duration.ofMillis(httpProxyRequest.getTimeout())).build();
        }

        try  {

            OkHttp3FutureCallback futureCallback = (OkHttp3FutureCallback)future;
            Callback callback  = futureCallback.getFutureCallbackProxy();
            Call call = httpClient.newCall(request);
            call.enqueue(callback);
            if (!httpProxyRequest.isFutureResult()){
                return null;
            }
            Response response = futureCallback.getFuture().get(httpProxyRequest.getTimeout(), TimeUnit.MILLISECONDS);
            HttpResponseBodyHandler handler = new Okhttp3ResponseBodyHandler(httpProxyRequest.getBodyMediaType());
            HttpClientResponse  result = this.getHttpClientResponse(response,handler);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }

    /**
     * 根据请求参数类型,根据网络架构的返回数据结果,转换到统一规范对象HttpClientResponse
     *  bodyMediaType       ,根据请求参数类型, 0/1为 json 字符串,2.为文件流
     * @param httpResponse        网络架构的返回数据结果
     * @param responseBodyHandler 结果处理器
     * @return 返回网络请求结果对象
     * @throws IOException 返回网络请求异常信息
     */
    @Override
    public  HttpClientResponse getHttpClientResponse(Object httpResponse, HttpResponseBodyHandler responseBodyHandler ) throws IOException {
        Response response = (Response) httpResponse;
        int code = response.code();
        boolean successful = response.isSuccessful();
        Map<String, List<String>> headers = response.headers().toMultimap();
        try {
            if (null == response.body()) {
                return HttpClientResponse.build(successful, code, headers, "", null);
            }
            String body = responseBodyHandler.handleResponseBody(httpResponse,responseBodyHandler.getContentType());
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
     * @param httpProxyRequest HttpProxyRequest   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
     *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
     *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
     *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
     *  encode 是否需要encode转码值为true 或 false, 默认为false
     *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
     *  https 是否使用证书  https 的值为 true 或 false, 默认为false
     * @return HttpRequestBuilder 代码请求对象
     */
    @Override
    public Okhttp3RequestBuilder getRequest(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest  httpProxyRequest )  {
        if( null ==  httpProxyRequest){
            httpProxyRequest = HttpProxyDefaultRequest.builder();
        }
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (httpProxyRequest.isEncode()) {
            HttpParamsUtil.forFunction(params, urlBuilder::addEncodedQueryParameter);
        } else {
            HttpParamsUtil.forFunction(params, urlBuilder::addQueryParameter);
        }
        //url
        HttpUrl httpUrl = urlBuilder.build();
        Headers headers = initFormHeaders();
        if (header != null ){
            headers = Headers.of(header.getHeaders());
        }
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(httpUrl).headers(headers).get();
        Request request = requestBuilder.build();
        Okhttp3RequestBuilder requestProxy = new Okhttp3RequestBuilder(request,httpProxyRequest);
        return requestProxy;
    }


    /**
     * POST 表单 方式 提交表单实现的业务,转换请求执行的逻辑的实现方法,返回各业务的请求对像的代理对象HttpRequestBuilder
     * @param url 请求url
     * @param params 请求参数 Map的k-v集合
     * @param header 请求头部参数
     * @param httpProxyRequest HttpProxyRequest   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
     *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
     *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
     *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
     *  encode 是否需要encode转码值为true 或 false, 默认为false
     *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
     *  https 是否使用证书  https 的值为 true 或 false, 默认为false
     * @return HttpRequestBuilder 代码请求对象
     */
    @Override
    public Okhttp3RequestBuilder postFormRequest(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest  httpProxyRequest )  {
        if( null ==  httpProxyRequest){
            httpProxyRequest = HttpProxyDefaultRequest.builder();
        }
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (httpProxyRequest.isEncode()) {
            HttpParamsUtil.forFunction(params, formBuilder::addEncoded);
        } else {
            HttpParamsUtil.forFunction(params, formBuilder::add);
        }
        FormBody body = formBuilder.build();
        //Headers
        Headers headers = initFormHeaders();
        if (header != null ){
            headers = Headers.of(header.getHeaders());
        }

        //Request
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url).headers(headers).post(body);
        Request request = requestBuilder.build();

        Okhttp3RequestBuilder requestProxy = new Okhttp3RequestBuilder(request,httpProxyRequest);
        return requestProxy;
    }

    /**
     * POST JSON 方式 提交表单实现的业务,转换请求执行的逻辑的实现方法,返回各业务的请求对像的代理对象HttpRequestBuilder
     * @param url 请求url
     * @param jsonData 请求参数 JSON 格式数据
     * @param header 请求头部参数
     * @param httpProxyRequest HttpProxyRequest   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
     *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
     *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
     *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
     *  encode 是否需要encode转码值为true 或 false, 默认为false
     *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
     *  https 是否使用证书  https 的值为 true 或 false, 默认为false
     * @return HttpRequestBuilder 代码请求对象
     */
    @Override
    public Okhttp3RequestBuilder postJsonRequest(String url, String jsonData, HttpProxyHeader header, HttpProxyRequest  httpProxyRequest)  {
        if( null ==  httpProxyRequest){
            httpProxyRequest = HttpProxyDefaultRequest.builder();
        }
        if (HttpParamsUtil.isEmpty(jsonData)) {
            jsonData = HttpClientConstants.EMPTY;
        }

        if(httpProxyRequest.isEncode()){
            jsonData = HttpParamsUtil.urlEncode(jsonData);
        }
        RequestBody body = RequestBody.create(jsonData, httpProxyRequest.getMediaType());
        Headers headers = initJsonHeaders();
        if (header != null ){
            headers = Headers.of(header.getHeaders());
        }
        //Request
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url).headers(headers).post(body);
        Request request = requestBuilder.build();

        Okhttp3RequestBuilder requestProxy = new Okhttp3RequestBuilder(request,httpProxyRequest);
        return requestProxy;

    }


}
