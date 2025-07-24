package com.suven.framework.http.proxy.httpclient;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.*;
import com.suven.framework.http.util.HttpParamsUtil;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class AbstractApacheRequestProxy extends AbstractHttpProxy implements HttpRequestProxy<ApacheRequestBuilder> {

    private CloseableHttpClient httpClient;
    private CloseableHttpAsyncClient asyncClient;

    public AbstractApacheRequestProxy() {
        super(new HttpClientConfig());
        this.httpClient = HttpClients.createDefault();
        this.asyncClient = HttpAsyncClients.createDefault();
        this.asyncClient.start();
    }

    public AbstractApacheRequestProxy(HttpClientConfig httpConfig) {
        super(httpConfig);
        this.httpClient = HttpClients.createDefault();
        this.asyncClient = HttpAsyncClients.createDefault();
        this.asyncClient.start();
    }

    public AbstractApacheRequestProxy(HttpClientConfig httpConfig, CloseableHttpClient httpClient,CloseableHttpAsyncClient asyncClient) {
        super(httpConfig);
        this.httpClient = httpClient;
        this.asyncClient = asyncClient;
        this.asyncClient.start();
    }

    private void initHttpClient(){
            this.httpClient = HttpSSLCipherSuiteUtil.createHttpClient();
            this.asyncClient =  HttpSSLCipherSuiteUtil.createHttpAsyncClient();
            asyncClient.start();
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
    public HttpClientResponse execute(ApacheRequestBuilder httpRequestBuilder) {
        HttpRequestBase request =  httpRequestBuilder.getRequest();
        HttpProxyRequest httpProxyRequest = httpRequestBuilder.getHttpProxyRequest();
        // 设置超时时长
        RequestConfig.Builder configBuilder = RequestConfig.custom()
                .setConnectTimeout(httpProxyRequest.getTimeout())
                .setSocketTimeout(httpProxyRequest.getTimeout())
                .setConnectionRequestTimeout(httpProxyRequest.getTimeout());
        // 设置代理
        if (httpProxyRequest.isProxy()) {
            Proxy proxy = this.getProxy();
            InetSocketAddress address = (InetSocketAddress) proxy.address();
            HttpHost host = new HttpHost(address.getHostName(), address.getPort(), proxy.type().name().toLowerCase());
            configBuilder.setProxy(host);
        }if (httpProxyRequest.isHttps()){
            initHttpClient();
        }

        request.setConfig(configBuilder.build());
        try (CloseableHttpResponse response = this.httpClient.execute(request)) {
            HttpResponseBodyHandler handler = new ApacheResponseBodyHandler(httpProxyRequest.getBodyMediaType());
            HttpClientResponse result =  this.getHttpClientResponse(response,handler);
            return  result;
        } catch (Exception e) {
            e.printStackTrace();
            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }


    /**
     *  异步执行逻辑请求的方法实现,默认是读取返回结果
     * @param httpRequestBuilder 代码各业务的请求的Request
     * @param futureProxy FutureCallbackProxy 返回处理的异步线程
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
    public HttpClientResponse executeAsync(ApacheRequestBuilder httpRequestBuilder, FutureCallbackProxy futureProxy) {
        HttpRequestBase  request =  httpRequestBuilder.getRequest();
        HttpProxyRequest httpProxyRequest = httpRequestBuilder.getHttpProxyRequest();
        try {
            // 设置超时时长
            RequestConfig.Builder configBuilder = RequestConfig.custom()
                    .setConnectTimeout(httpProxyRequest.getTimeout())
                    .setSocketTimeout(httpProxyRequest.getTimeout())
                    .setConnectionRequestTimeout(httpProxyRequest.getTimeout());



            // 设置代理
            if (httpProxyRequest.isProxy()) {
                Proxy proxy = this.getProxy();
                InetSocketAddress address = (InetSocketAddress) proxy.address();
                HttpHost host = new HttpHost(address.getHostName(), address.getPort(), proxy.type().name().toLowerCase());
                configBuilder.setProxy(host);
            }else if(httpProxyRequest.isHttps()){

            }

            request.setConfig(configBuilder.build());

            ApacheFutureCallback futureCallback = (ApacheFutureCallback)futureProxy.getFutureCallbackProxy();
            Future<HttpResponse> future = this.asyncClient.execute(request, futureCallback);
            if(!httpProxyRequest.isFutureResult()){
                return null;
            }
            //获取线程结果
            HttpResponse response = futureCallback.getFuture().get(httpProxyRequest.getTimeout(), TimeUnit.MILLISECONDS);
            futureProxy.isSuccess(response);
            HttpResponseBodyHandler  handler = new ApacheResponseBodyHandler(httpProxyRequest.getBodyMediaType());
            HttpClientResponse result = this.getHttpClientResponse(response,handler);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }

    /**
     *  根据请求参数类型,根据网络架构的返回数据结果,转换到统一规范对象HttpClientResponse
     *  bodyMediaType ,根据请求参数类型, 0/1为 json 字符串,2.为文件流
     * @param httpResponse 网络架构的返回数据结果
     * @return 返回网络请求结果
     * @throws IOException 网格 io 异常
     */
    @Override
    public HttpClientResponse getHttpClientResponse(Object httpResponse,HttpResponseBodyHandler bodyHandler) throws IOException {
        CloseableHttpResponse response = (CloseableHttpResponse)httpResponse;
        int code = response.getStatusLine().getStatusCode();
        boolean successful = isSuccess(response);
        Map<String, List<String>> headers = Arrays.stream(response.getAllHeaders())
                .collect(Collectors.toMap(Header::getName, (value) -> {
                    ArrayList<String> headerValue = new ArrayList<>();
                    headerValue.add(value.getValue());
                    return headerValue;
                }, (oldValue, newValue) -> newValue));
        if (null == response.getEntity()) {
            return HttpClientResponse.build(successful, code, headers, "", null);
        }
        try {
            String body =  bodyHandler.handleResponseBody(httpResponse,bodyHandler.getContentType());
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
    public ApacheRequestBuilder getRequest(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest httpProxyRequest)  {
        this.initHttpProxyRequest(httpProxyRequest);
        String requestUrl = url;
        if (HttpParamsUtil.isNotEmpty(params)) {
            String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
            requestUrl = baseUrl + HttpParamsUtil.parseMapToString(params,httpProxyRequest.isEncode());
        }

        HttpGet request = new HttpGet(requestUrl);
        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::addHeader);
        }else {
            addHeader(request);
        }
        ApacheRequestBuilder requestBean = new ApacheRequestBuilder(request,httpProxyRequest);

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
    public ApacheRequestBuilder postFormRequest(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest httpProxyRequest)  {
        HttpPost request = new HttpPost(url);
        this.initHttpProxyRequest(httpProxyRequest);
        if (HttpParamsUtil.isNotEmpty(params)) {
            List<NameValuePair> form = new ArrayList<>();
            HttpParamsUtil.forFunction(params, (k, v) -> form.add(new BasicNameValuePair(k, v)));
            request.setEntity(new UrlEncodedFormEntity(form, HttpClientConstants.DEFAULT_ENCODING));
        }

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::addHeader);
        }else {
            addHeader(request);
        }
        ApacheRequestBuilder requestProxy = new ApacheRequestBuilder(request,httpProxyRequest);
        return requestProxy;

    }
    /**
     * POST 方式 提交表单实现的业务,转换请求执行的逻辑的实现方法,返回各业务的请求对像的代理对象HttpRequestBuilder
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
    public ApacheRequestBuilder postJsonRequest(String url, String jsonData, HttpProxyHeader header,  HttpProxyRequest httpProxyRequest )  {
        HttpPost request = new HttpPost(url);
        this.initHttpProxyRequest(httpProxyRequest);

        if (HttpParamsUtil.isNotEmpty(jsonData)) {
            StringEntity entity = new StringEntity(jsonData, HttpClientConstants.DEFAULT_ENCODING);
            entity.setContentEncoding(HttpClientConstants.DEFAULT_ENCODING.displayName());
            entity.setContentType(HttpClientConstants.CONTENT_TYPE_JSON);
            request.setEntity(entity);
        }
        initJsonHeaders(header);
        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::addHeader);
        }else {
            addHeader(request);
        }

        ApacheRequestBuilder requestProxy = new ApacheRequestBuilder(request,httpProxyRequest);
        return requestProxy;


    }


    /**
     * 添加request header
     *
     * @param request HttpRequestBase
     */
    protected void addHeader(HttpRequestBase request) {
        String ua = HttpClientConstants.USER_AGENT;
        Header[] headers = request.getHeaders(ua);
        if (null == headers || headers.length == 0) {
            request.addHeader(ua, HttpClientConstants.USER_AGENT_DATA);
        }
    }


    protected boolean isSuccess(HttpResponse response) {
        if (response == null) {
            return false;
        }
        if (response.getStatusLine() == null) {
            return false;
        }
        return response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300;
    }



}
