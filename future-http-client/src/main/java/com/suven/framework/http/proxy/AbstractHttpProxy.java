package com.suven.framework.http.proxy;

import com.alibaba.fastjson2.JSON;
import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import okhttp3.Headers;

import java.io.IOException;
import java.net.Proxy;

/**
 * @author 作者 : suven.wang
 * CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明): HTTP 网络请求抽象类
 *  提供了HTTP客户端代理的基础实现，包括配置管理、代理设置、请求头初始化等功能
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
public abstract class AbstractHttpProxy implements HttpProxy {
	/**
	 * HTTP客户端配置对象，包含连接超时、读取超时等配置参数
	 */
	protected HttpClientConfig httpClientConfig;

	/**
	 * 构造函数
	 * @param httpClientConfig HTTP客户端配置对象，不可为null
	 */
	public AbstractHttpProxy(HttpClientConfig httpClientConfig) {
		this.httpClientConfig = httpClientConfig;
	}

	/**
	 * 设置HTTP客户端配置
	 * @param httpClientConfig HTTP客户端配置对象，不可为null
	 */
	@Override
	public void setHttpConfig(HttpClientConfig httpClientConfig) {
		this.httpClientConfig = httpClientConfig;
	}

	/**
	 * 获取HTTP代理配置
	 * @return 代理对象，如果未配置或httpClientConfig为null则返回null
	 */
	public Proxy getProxy(){
		if (null == httpClientConfig){
			return null;
		}
		return httpClientConfig.getProxy();
	}

	/**
	 * 初始化HTTP代理请求对象
	 * 如果传入的请求对象为null，则创建默认的请求对象
	 * @param httpProxyRequest HTTP代理请求对象，可以为null
	 */
	public void initHttpProxyRequest(HttpProxyRequest httpProxyRequest){
		if( null ==  httpProxyRequest){
			httpProxyRequest = HttpProxyDefaultRequest.builder();
		}
	}

	/**
	 * 根据请求参数类型,根据网络架构的返回数据结果,转换到统一规范对象HttpClientResponse
	 *
	 * @param httpResponse 网络架构的返回数据结果，不可为null
	 * @param responseBodyHandler 结果处理器，不可为null
	 * @return 返回网络请求结果对象
	 * @throws IOException 返回网络请求异常信息
	 */
	public abstract HttpClientResponse getHttpClientResponse( Object httpResponse, HttpResponseBodyHandler responseBodyHandler) throws IOException;

	/**
	 * 通过JSON字符串转换成对象
	 *
	 * @param body JSON字符串，可以为null
	 * @param parseJson 目标类型Class对象，不可为null
	 * @param <T> 泛型类型参数
	 * @return 解析后的对象，如果body为null则返回null
	 */
	@Override
	public <T> T getData(String body, Class<T> parseJson) {
		return JSON.parseObject(body,parseJson);
	}

	/**
	 * 初始化JSON格式的请求头
	 * 包含User-Agent、Content-Type等标准HTTP头信息
	 *
	 * @return OkHttp Headers对象，包含JSON格式的请求头
	 */
	protected Headers initJsonHeaders(){
		Headers headers = Headers.of(
				HttpClientConstants.USER_AGENT, HttpClientConstants.USER_AGENT_DATA,
				HttpClientConstants.CONTENT_TYPE, HttpClientConstants.CONTENT_TYPE_JSON,
				HttpClientConstants.CONTENT_JSON_KEY, HttpClientConstants.CONTENT_JSON_VALUE);
		return headers;
	}

	/**
	 * 初始化JSON格式的请求头并添加到header对象中
	 * 包含User-Agent、Content-Type等标准HTTP头信息
	 *
	 * @param header HTTP代理请求头对象，可以为null
	 * @return 总是返回true
	 */
	protected boolean initJsonHeaders(HttpProxyHeader header){
		if(header == null){
			header = new HttpProxyHeader();
		}
		header.add(HttpClientConstants.USER_AGENT, HttpClientConstants.USER_AGENT_DATA)
				.add(HttpClientConstants.CONTENT_TYPE, HttpClientConstants.CONTENT_TYPE_JSON)
				.add(HttpClientConstants.CONTENT_JSON_KEY, HttpClientConstants.CONTENT_JSON_VALUE);
		return true;
	}

	/**
	 * 初始化表单格式的请求头
	 * 仅包含User-Agent等基本的HTTP头信息
	 *
	 * @return OkHttp Headers对象，包含表单格式的请求头
	 */
	protected Headers initFormHeaders(){
		Headers headers = Headers.of(
				HttpClientConstants.USER_AGENT, HttpClientConstants.USER_AGENT_DATA);
		return headers;
	}
}
