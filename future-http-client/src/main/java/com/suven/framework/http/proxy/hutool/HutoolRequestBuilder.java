package com.suven.framework.http.proxy.hutool;


import cn.hutool.http.HttpRequest;
import com.suven.framework.http.proxy.HttpProxyDefaultRequest;
import com.suven.framework.http.proxy.HttpProxyRequest;
import com.suven.framework.http.proxy.HttpRequestBuilder;

public class HutoolRequestBuilder implements HttpRequestBuilder<HttpRequest> {

    private HttpRequest request;
    private HttpProxyRequest httpProxyRequest;
    public HutoolRequestBuilder(HttpRequest request, HttpProxyRequest proxyRequest){
        this.request = request;
        this.httpProxyRequest = proxyRequest;

    }

    /** 各代理各业务架构的自定义的请求对象**/
    @Override
    public HttpRequest getRequest() {
        return request;
    }

    /**
     * HttpProxyRequest  网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultRequest
     * timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
     * bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
     * futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
     * encode 是否需要encode转码值为true 或 false, 默认为false
     * proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
     *
     * @return
     */
    @Override
    public HttpProxyRequest getHttpProxyRequest() {
        if( null == httpProxyRequest){
            httpProxyRequest = HttpProxyDefaultRequest.builder();
        }
        return httpProxyRequest;
    }
}
