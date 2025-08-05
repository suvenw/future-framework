package com.suven.framework.http.proxy;

import com.alibaba.fastjson.JSON;
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
 *  @Description (说明): http 网络请求抽像类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

public abstract class AbstractHttpProxy implements HttpProxy {
	protected  HttpClientConfig httpClientConfig;

	public AbstractHttpProxy(HttpClientConfig httpClientConfig) {
		this.httpClientConfig = httpClientConfig;
	}

	@Override
	public void setHttpConfig(HttpClientConfig httpClientConfig) {
		this.httpClientConfig = httpClientConfig;
	}

	public Proxy getProxy(){
		if (null == httpClientConfig){
			return null;
		}
		return httpClientConfig.getProxy();
	}


	public void  initHttpProxyRequest(HttpProxyRequest httpProxyRequest){
		if( null ==  httpProxyRequest){
			httpProxyRequest = HttpProxyDefaultRequest.builder();
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
	public abstract HttpClientResponse getHttpClientResponse( Object httpResponse, HttpResponseBodyHandler responseBodyHandler) throws IOException;


	/**
	 * 通过JSON 字符串转换成对象类对象的实现方法
	 * @param body
	 * @param parseJson
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> T getData(String body, Class<T> parseJson) {
		return JSON.parseObject(body,parseJson);
	}

	/**
	 * 添加request header
	 *
	 */
	protected Headers initJsonHeaders(){
		Headers headers = Headers.of(
				HttpClientConstants.USER_AGENT, HttpClientConstants.USER_AGENT_DATA,
				HttpClientConstants.CONTENT_TYPE, HttpClientConstants.CONTENT_TYPE_JSON,
				HttpClientConstants.CONTENT_JSON_KEY, HttpClientConstants.CONTENT_JSON_VALUE);
		return headers;
	}

	/**
	 * 添加request header
	 *
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
	 * 添加request header
	 *
	 */
	protected Headers initFormHeaders(){
		Headers headers = Headers.of(
				HttpClientConstants.USER_AGENT, HttpClientConstants.USER_AGENT_DATA);
		return headers;
	}
}
