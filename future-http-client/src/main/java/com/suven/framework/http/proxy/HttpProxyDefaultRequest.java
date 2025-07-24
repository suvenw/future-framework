/*
 * Copyright (c) 2019-2029, xkcoding & Yangkai.Shen & 沈扬凯 (237497819@qq.com & xkcoding.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.suven.framework.http.proxy;


import com.suven.framework.http.constants.HttpClientConstants;
import okhttp3.MediaType;

/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明): http 网络请求,个性参数参数实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 *  encode 是否需要encode转码,true 或 false, 默认为true
 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
 **/

public class HttpProxyDefaultRequest implements HttpProxyRequest {

   /**  timeout 获取超时间,单位为毫秒,默认值为3秒,方法传过来,按方法执行,否则按系统配置默认执行 **/
	private int timeout = 3000;
	/**  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流**/
	private int bodyMediaType = 0;
	/**  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null **/
	private boolean futureResult = false;
	/** encode 是否需要encode转码值为true 或 false, 默认为false **/
	private boolean encode = false;

	/** proxy 是否使用代理 proxy的值为 true 或 false, 默认为false **/
	private boolean proxy = false;

	/** proxy 是否使用代理 proxy的值为 true 或 false, 默认为false **/
	private boolean https = false;
	/** mediaType 请求参数数据类型, mediaType默认值为 "application/json; charset=utf-8" **/
	private MediaType mediaType = MediaType.get(HttpClientConstants.CONTENT_TYPE_JSON);

	public static HttpProxyDefaultRequest builder(){
		return new HttpProxyDefaultRequest();
	}

	/**  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行 **/
	@Override
	public int getTimeout() {
		return timeout;
	}
	/**  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行 **/
	@Override
	public int getTimeout(int timeout) {
		if(timeout > 0 ){
			return timeout;
		}
		return this.timeout;
	}

	/**  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流**/
	@Override
	public int getBodyMediaType() {
		return bodyMediaType;
	}

	/**  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null **/
	@Override
	public boolean isFutureResult() {
		return futureResult;
	}

	/** encode 是否需要encode转码值为true 或 false, 默认为false **/
	@Override
	public boolean isEncode() {
		return encode;
	}


	/**
	 * proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 **/
	@Override
	public boolean isProxy() {
		return proxy;
	}

	/**
	 * https 是否使用 https 证书请求 https 的值为 true 或 false, 默认为false
	 **/
	@Override
	public boolean isHttps() {
		return https;
	}

	/**
	 * application/json; charset=utf-8
	 * An rfc_2045 Media Type, appropriate to describe the content type of an HTTP request or response body
	 **/
	@Override
	public MediaType getMediaType() {
		return mediaType;
	}

	public HttpProxyRequest setEncode(boolean encode) {
		this.encode = encode;
		return this;
	}
	public HttpProxyRequest setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	public HttpProxyRequest setProxy(boolean proxy) {
		this.proxy = proxy;
		return this;
	}
	public HttpProxyRequest setFutureResult(boolean futureResult) {
		this.futureResult = futureResult;
		return this;
	}
	public HttpProxyRequest setBodyMediaType(int bodyMediaType) {
		this.bodyMediaType = bodyMediaType;
		return this;
	}

	public HttpProxyRequest setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
		return this;
	}
}
