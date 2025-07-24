package com.suven.framework.http.proxy.java11;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.exception.HttpClientRuntimeException;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;


/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  java11 http 网络请求配置类
 *  默认代理选择器
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
public class DefaultProxySelector extends ProxySelector {
	private HttpClientConfig httpClientConfig;

	public DefaultProxySelector(HttpClientConfig httpClientConfig) {
		this.httpClientConfig = httpClientConfig;
	}

	@Override
	public List<Proxy> select(URI uri) {
		return Collections.singletonList(httpClientConfig.getProxy());
	}

	@Override
	public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
		throw new HttpClientRuntimeException("Proxy connect failed!");
	}
}
