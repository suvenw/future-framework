package com.suven.framework.http.proxy;




import java.util.Map;

public interface HttpRequestProxy <T extends HttpRequestBuilder>{



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
    HttpClientResponse execute(T httpRequestBuilder) ;

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
     */
    HttpClientResponse executeAsync(T httpRequestBuilder,FutureCallbackProxy future);


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
    T getRequest(String url, Map<String, ?> params, HttpProxyHeader header, HttpProxyRequest httpProxyRequest );



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
    T postFormRequest(String url, Map<String, ?> params, HttpProxyHeader header, HttpProxyRequest httpProxyRequest );

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
    T  postJsonRequest(String url, String jsonData, HttpProxyHeader header,  HttpProxyRequest httpProxyRequest);
}
