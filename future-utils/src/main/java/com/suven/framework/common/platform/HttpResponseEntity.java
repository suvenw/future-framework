package com.suven.framework.common.platform;
/**
 * 响应实体
 */
public class HttpResponseEntity {
	/** 定义响应状态码 */
	private int statusCode;
	/** 定义响应内容 */
	private String content;
	/** 无参构造器 */
	public HttpResponseEntity(){}
	public HttpResponseEntity(int statusCode, String content) {
		this.statusCode = statusCode;
		this.content = content;
	}
	/** setter and getter method */
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "HttpResponseEntity [statusCode=" + statusCode + ", content=" + content + "]";
	}
	
	
}