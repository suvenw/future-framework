package com.suven.framework.http.proxy.java11;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.*;
import com.suven.framework.http.util.HttpParamsUtil;
import okhttp3.Headers;
import okhttp3.MediaType;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public abstract class AbstractJavaRequestProxy extends AbstractHttpProxy implements HttpRequestProxy<JavaRequestBuilder> {

    public static final MediaType CONTENT_TYPE_JSON = MediaType.get(HttpClientConstants.CONTENT_TYPE_JSON);
    protected final HttpClient.Builder httpClientBuilder;

    public AbstractJavaRequestProxy(HttpClientConfig httpClientConfig) {
       super(httpClientConfig);
        this.httpClientBuilder = HttpClient.newBuilder();
    }
    public AbstractJavaRequestProxy(HttpClientConfig httpClientConfig, HttpClient.Builder httpClientBuilder) {
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
    public HttpClientResponse execute(JavaRequestBuilder httpRequestBuilder) {
        HttpProxyRequest httpProxyRequest = httpRequestBuilder.getHttpProxyRequest();
        HttpRequest request =  httpRequestBuilder.getRequest();
        try {
            HttpClient client;
            if ( httpProxyRequest.isProxy()) {
                client = httpClientBuilder.connectTimeout(Duration.ofMillis(httpProxyRequest.getTimeout()))
                        .proxy(new DefaultProxySelector(httpClientConfig)).build();
            } else {
                client = httpClientBuilder.connectTimeout(Duration.ofMillis(httpProxyRequest.getTimeout())).build();
            }
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            HttpResponseBodyHandler handler = new JavaResponseBodyHandler(httpProxyRequest.getBodyMediaType());
            HttpClientResponse  response = this.getHttpClientResponse(httpResponse,handler);
            return  response;
        } catch (Exception e) {
            e.printStackTrace();
            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
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
    public HttpClientResponse executeAsync(JavaRequestBuilder httpRequestBuilder, FutureCallbackProxy future ) {
        HttpProxyRequest httpProxyRequest = httpRequestBuilder.getHttpProxyRequest();
        HttpRequest  request =  httpRequestBuilder.getRequest();
        try {
            HttpClient client;

            if (httpProxyRequest.isProxy()) {
                client = httpClientBuilder.connectTimeout(Duration.ofMillis(httpProxyRequest.getTimeout()))
                        .proxy(new DefaultProxySelector(httpClientConfig)).build();
            } else {
                client = httpClientBuilder.connectTimeout(Duration.ofMillis(httpProxyRequest.getTimeout())).build();
            }
            Future<HttpResponse<String>> futureProxy = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            if(!httpProxyRequest.isFutureResult()){
                return null;
            }

            HttpResponse<String> httpResponse = futureProxy.get(httpProxyRequest.getTimeout(), TimeUnit.MILLISECONDS);
            HttpResponseBodyHandler handler = new JavaResponseBodyHandler(httpProxyRequest.getBodyMediaType());
            HttpClientResponse  response = this.getHttpClientResponse(httpResponse,handler);
            return  response;

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
    @SuppressWarnings("unchecked")
    public HttpClientResponse getHttpClientResponse(Object httpResponse, HttpResponseBodyHandler responseBodyHandler) throws IOException {
        HttpResponse<String> response = ( HttpResponse<String>)httpResponse;
        int code = response.statusCode();
        boolean successful = isSuccess(response);
        Map<String, List<String>> headers = response.headers().map();
        String body = responseBodyHandler.handleResponseBody(httpResponse,responseBodyHandler.getContentType());
        return HttpClientResponse.build(successful, code, headers, body, null);

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
    public JavaRequestBuilder getRequest(String url,Map<String, ?> params, HttpProxyHeader header, HttpProxyRequest httpProxyRequest )  {
        if( null ==  httpProxyRequest){
            httpProxyRequest = HttpProxyDefaultRequest.builder();
        }
        String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
        String reqUrl = baseUrl + HttpParamsUtil.parseMapToString(params, httpProxyRequest.isEncode());

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(reqUrl)).GET()
                .timeout(Duration.ofMillis(httpProxyRequest.getTimeout()));

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), requestBuilder::header);
        }
        HttpRequest request = requestBuilder.build();
        JavaRequestBuilder requestBean = new JavaRequestBuilder(request,httpProxyRequest);

        return requestBean;
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
    public JavaRequestBuilder postFormRequest(String url,Map<String, ?> params, HttpProxyHeader header,  HttpProxyRequest httpProxyRequest )  {
        if( null ==  httpProxyRequest){
            httpProxyRequest = HttpProxyDefaultRequest.builder();
        }
         String requestUrl = url;
        if (HttpParamsUtil.isNotEmpty(params)) {
            String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
            requestUrl = baseUrl + HttpParamsUtil.parseMapToString(params, httpProxyRequest.isEncode());
        }
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl)).timeout(Duration.ofMillis(httpClientConfig.getTimeout()));

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), builder::header);
        }
        builder.POST(HttpRequest.BodyPublishers.noBody());
        HttpRequest request  = builder.build();
        JavaRequestBuilder requestProxy = new JavaRequestBuilder(request,httpProxyRequest);
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
    public JavaRequestBuilder postJsonRequest(String url, String jsonData, HttpProxyHeader header, HttpProxyRequest httpProxyRequest )  {
        if( null ==  httpProxyRequest){
            httpProxyRequest = HttpProxyDefaultRequest.builder();
        }
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url)).timeout(Duration.ofMillis(httpProxyRequest.getTimeout()));

        if (HttpParamsUtil.isNotEmpty(jsonData)) {
            if(httpProxyRequest.isEncode()){
                jsonData = HttpParamsUtil.urlEncode(jsonData);
            }
            builder.POST(HttpRequest.BodyPublishers.ofString(jsonData, HttpClientConstants.DEFAULT_ENCODING));
            builder.header(HttpClientConstants.CONTENT_ENCODING, HttpClientConstants.DEFAULT_ENCODING.displayName());
            builder.header(HttpClientConstants.CONTENT_TYPE, HttpClientConstants.CONTENT_TYPE_JSON);
            builder.header(HttpClientConstants.CONTENT_JSON_KEY, HttpClientConstants.CONTENT_JSON_VALUE);
        } else {
            builder.POST(HttpRequest.BodyPublishers.noBody());
        }

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), builder::header);
        }
        HttpRequest request  = builder.build();
        JavaRequestBuilder requestProxy = new JavaRequestBuilder(request,httpProxyRequest);
        return requestProxy;


    }

    /**
     * 添加request header
     *
     */
    protected Headers initHeaders(){
        Headers headers = Headers.of(HttpClientConstants.USER_AGENT, HttpClientConstants.USER_AGENT_DATA);
        return headers;
    }

    protected boolean isSuccess(HttpResponse<String> response) {
        if (response == null) {
            return false;
        }
        return response.statusCode() >= 200 && response.statusCode() < 300;
    }

}
