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


import java.util.Map;


/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明): http 网络请求接口类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

public interface HttpSyncProxy {


	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	HttpClientResponse get(String url);

	/**
	 * GET 请求
	 * @param url URL
	 * @param httpProxyRequest HttpProxyParameter
	 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *  encode 是否需要encode转码值为true 或 false, 默认为false
	 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	HttpClientResponse get(String url, HttpProxyRequest httpProxyRequest);



	/**
	 * GET 请求,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *  encode 是否需要encode转码值为true 或 false, 默认为false
	 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	HttpClientResponse get(String url, Map<String, String> params, int timeout);
	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param encode 是否需要 url encode
	 *  httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *  encode 是否需要encode转码值为true 或 false, 默认为false
	 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	HttpClientResponse get(String url, Map<String, String> params, boolean encode);


	/**
	 * GET 表单 请求,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params 参数
	 @param header 请求头
	 *  httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *  encode 是否需要encode转码值为true 或 false, 默认为false
	 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	HttpClientResponse get(String url, Map<String, String> params,HttpProxyHeader header );


	/**
	 * GET 表单 请求,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *  encode 是否需要encode转码值为true 或 false, 默认为false
	 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	HttpClientResponse get(String url, Map<String, String> params, HttpProxyRequest httpProxyRequest);


	/**
	 * GET  表单 请求,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param header 请求头
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *  encode 是否需要encode转码值为true 或 false, 默认为false
	 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	HttpClientResponse get(String url, Map<String, String> params, HttpProxyHeader header , HttpProxyRequest httpProxyRequest);


	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	HttpClientResponse post(String url);

	/**
	 * POST JSON 请求
	 *
	 * @param url  URL
	 * @param data JSON 参数
	 * @return 结果
	 */
	HttpClientResponse post(String url, String data);

	/**
	 * POST JSON 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url  URL
	 * @param data JSON 参数
	 * @param timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 * @return 结果
	 */
	HttpClientResponse post(String url, String data, int timeout);

	/**
	 * POST JSON 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param data JSON 参数
	 *  httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *  encode 是否需要encode转码值为true 或 false, 默认为false
	 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 *  https 是否使用证书  https 的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	HttpClientResponse post(String url, String data, HttpProxyHeader header);

	/**
	 * POST JSON 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param data JSON 参数
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *  encode 是否需要encode转码值为true 或 false, 默认为false
	 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	HttpClientResponse post(String url, String data,  HttpProxyRequest httpProxyRequest);



	/**
	 * POST JSON 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param data JSON 参数
	 * @param header 请求头
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *  timeout() 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *  encode 是否需要encode转码值为true 或 false, 默认为false
	 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	HttpClientResponse post(String url, String data, HttpProxyHeader header, HttpProxyRequest httpProxyRequest);



	/**
	 *  POST 表单 请求, 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 * @param timeout 是否需要 url encode
	 * @return 结果
	 */
	HttpClientResponse post(String url, Map<String, String> params, int timeout);

	/**
	 *  POST 表单 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	HttpClientResponse post(String url, Map<String, String> params,  boolean encode);



	/**
	 *  POST 表单 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param header 请求头
	 *  httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类 ,具体默认值 HttpProxyDefaultParameter
	 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *  encode 是否需要encode转码值为true 或 false, 默认为false
	 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	HttpClientResponse post(String url, Map<String, String> params, HttpProxyHeader header);

	/**
	 *  POST 表单 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类 ,具体默认值 HttpProxyDefaultParameter
	 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *  encode 是否需要encode转码值为true 或 false, 默认为false
	 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	HttpClientResponse post(String url, Map<String, String> params, HttpProxyRequest httpProxyRequest);

	/**
	 *  POST 表单 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param header 请求头
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *  timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *  bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *  futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *  encode 是否需要encode转码值为true 或 false, 默认为false
	 *  proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	HttpClientResponse post(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest httpProxyRequest);
}
