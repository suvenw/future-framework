package com.suven.framework.http.proxy;

import com.alibaba.fastjson.JSON;
import com.suven.framework.http.exception.HttpClientRuntimeException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 作者 : suven.wang
 * CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明): http 网络请求,响应封装
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

public class HttpClientResponse {
	private boolean success = true;
	private int code = 200;
	private Map<String, List<String>> headers;
	private String body;
	private String error;

	public HttpClientResponse() {
	}
	public static HttpClientResponse build() {
		HttpClientResponse http = new HttpClientResponse();
		return http;
	}
	public static HttpClientResponse build(boolean success, int code) {
		HttpClientResponse http = new HttpClientResponse();
		http.success = success;
		http.code = code;
		return http;
	}
	public static HttpClientResponse build( String body) {
		HttpClientResponse http = new HttpClientResponse();
		http.body = body;
		return http;
	}
	public  static HttpClientResponse build(boolean success, int code, String error) {
		HttpClientResponse http = new HttpClientResponse();
		http.success = success;
		http.code = code;
		http.error = error;
		return http;
	}

	public  static HttpClientResponse build(boolean success, int code, Map<String, List<String>> headers, String body, String error) {
		HttpClientResponse http = new HttpClientResponse();
		http.success = success;
		http.code = code;
		http.headers = headers;
		http.body = body;
		http.error = error;
		return http;
	}

	private HttpClientResponse check() {
		if (Objects.isNull(this)) {
			throw new HttpClientRuntimeException("Invalid SimpleHttpResponse.");
		}
		if (!this.isSuccess()) {
			throw new HttpClientRuntimeException(this.getError());
		}
		return this;
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public <T>T parseBody(Class<T> clazz ){
		if (Objects.isNull( body) || "".equals(body)){
			return null;
		}
		return JSON.parseObject(body, clazz);
	}
}
