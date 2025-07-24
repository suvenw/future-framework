package com.suven.framework.http;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.exception.HttpClientRuntimeException;
import com.suven.framework.http.proxy.HttpProxy;
import com.suven.framework.http.proxy.httpclient.ApacheHttpClientProxy;
import com.suven.framework.http.proxy.hutool.HutoolHttpClientProxy;
import com.suven.framework.http.proxy.java11.JavaHttpClientProxy;
import com.suven.framework.http.proxy.okhttp3.OkHttp3HttpClientProxy;
import com.suven.framework.http.util.ClassUtil;

import java.net.Proxy;

/**
 * @author 作者 : suven
 * CreateDate 创建时间: 2023-02-03
 * @version 版本: v1.0.0
 * <pre>
 *
 *  description (说明):  HttpClientUtil请求工具代理实现逻辑类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by <a href="https://www.66os.com">...</a>
 **/


public class HttpClientUtilProxy {
    protected static HttpProxy proxy;

    /** 使用单例模式初始化实例 **/
    private static class HttpProxyInstance {
        private static final HttpClientUtilProxy INSTANCE = new HttpClientUtilProxy();
        private static final HttpClientConfig HTTP_CONFIG = HttpClientConfig.builder().toTimeout(HttpClientConstants.DEFAULT_TIMEOUT);
    }

    public static HttpClientUtilProxy getInstance() {
        return getInstance(HttpProxyInstance.HTTP_CONFIG);
    }
    public static HttpClientUtilProxy getInstance(HttpClientConfig httpConfig) {
        if(proxy == null){
            selectHttpProxy();
        }if(httpConfig != null) {
            proxy.setHttpConfig(httpConfig);
        }
        return HttpProxyInstance.INSTANCE;
    }

    /**
     * 初始化设置代理与超时时长，单位毫秒
     */
    public static HttpClientUtilProxy getInstance(Proxy toProxy, int timeout) {
        if(proxy == null){
            selectHttpProxy();
        }
        if(toProxy != null && timeout > 0){
            proxy.setHttpConfig(HttpProxyInstance.HTTP_CONFIG.toProxy(toProxy).toTimeout(timeout));
        }
        return HttpProxyInstance.INSTANCE;
    }

    /**
     * 选择网络请求架构实现代理逻辑
     */
    protected static void selectHttpProxy() {
        HttpProxy defaultProxy = null;
        ClassLoader classLoader = HttpClientUtil.class.getClassLoader();

        // 基于 java 11 HttpClient
        if (ClassUtil.isPresent("java.net.http.HttpClient", classLoader)) {
            defaultProxy = createHttpProxy(JavaHttpClientProxy.class);
        }
        // 基于 okhttp3
        if (null == defaultProxy && ClassUtil.isPresent("okhttp3.OkHttpClient", classLoader)) {
            defaultProxy = createHttpProxy(OkHttp3HttpClientProxy.class);
        }
        // 基于 apache httpclient
        if (null == defaultProxy && ClassUtil.isPresent("org.apache.http.impl.client.HttpClients", classLoader)) {
            defaultProxy = createHttpProxy(ApacheHttpClientProxy.class);
        }
        // 基于 hutool
        if (null == defaultProxy && ClassUtil.isPresent("cn.hutool.http.HttpRequest", classLoader)) {
            defaultProxy = createHttpProxy(HutoolHttpClientProxy.class);
        }
        if (defaultProxy == null) {
            throw new HttpClientRuntimeException("Has no HttpImpl defined in environment!");
        }
        setHttp(defaultProxy);

    }

    /**
     * 创建网络实现的代码类
     * @param clazz 实现类
     * @param <T> 实现类对象
     * @return 实现类对象
     */
    protected static <T extends HttpProxy> HttpProxy createHttpProxy(Class<T> clazz) {
        try {
            return  clazz.getDeclaredConstructor().newInstance();
        } catch (Throwable e) {
            return null;
        }
    }

    protected static void setHttp(HttpProxy httpProxy) {
        proxy = httpProxy;
    }

    protected static void checkHttpNotNull(HttpProxy proxy) {
        if (null == proxy) {
            getInstance();
        }
    }

}
