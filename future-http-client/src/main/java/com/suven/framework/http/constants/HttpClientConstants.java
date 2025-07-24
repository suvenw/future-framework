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

package com.suven.framework.http.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  常量池
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
public interface HttpClientConstants {
	/**
	 * 超时时长，单位毫秒
	 */
	int DEFAULT_TIMEOUT = 3000;



	/**
	 * 网络请求码,401
	 * {@code 400 Bad Request} (HTTP/1.1 - RFC 2616) */
	int SC_BAD_REQUEST = 400;
	/**
	 * 网络请求码,401
	 * {@code 401 Unauthorized} (HTTP/1.0 - RFC 1945) */
	int SC_UNAUTHORIZED = 401;


	/**
	 * 编码格式
	 */
	Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

	/**
	 * JSON
	 */
	String CONTENT_TYPE_JSON = "application/json; charset=utf-8";

	/**
	 * Content-Type
	 */
	String CONTENT_TYPE = "Content-Type";

	/**
	 * JSON
	 */
	String CONTENT_JSON_KEY ="Content-Json";
	/**
	 * JSON
	 */
	String CONTENT_JSON_VALUE ="true";

	/**
	 * Content-Encoding
	 */
	String CONTENT_ENCODING = "Content-Encoding";

	/**
	 * User-Agent
	 */
	String USER_AGENT = "User-Agent";



	/**
	 * 模拟 User-Agent
	 */
	String USER_AGENT_DATA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36 future-http-client";

	/**
	 * 空字符串
	 */
	String EMPTY = "";

	/** HTTPS 请求**/
	public static final String HTTPS = "HTTPS";
	/** GMTLS 请求**/
	public static final String GM_PROTOCOL = "GMTLS";
	/** https 协议 版本号**/
	public static final String INTERNATIONAL_PROTOCOL = "TLSv1.2";
	/** sdk 加密实现**/
	public static final String SIGNATURE_ALGORITHM_SDK_HMAC_SHA256 = "SDK-HMAC-SHA256";
	/** sdk 加密实现**/
	public static final String SIGNATURE_ALGORITHM_SDK_HMAC_SM3 = "SDK-HMAC-SM3";

}
