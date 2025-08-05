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

package com.suven.framework.http.util;

import com.alibaba.fastjson.JSON;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.exception.HttpClientRuntimeException;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;
import java.util.function.BiConsumer;


/**
 * @author 作者 : suven.wang
 * CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明): http 网络请求参数转换实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

public class HttpParamsUtil {

	private final static String MD5_CRYPT = "MD5";
	private static final String URF_8 = "UTF-8";
	/**
	 * 判断 map 不为空
	 *
	 * @param map map
	 * @return {@code true} - 不为空，{@code false} - 空
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return map != null && !map.isEmpty();
	}

	/**
	 * 判断 map 为空
	 *
	 * @param map map
	 * @return {@code true} - 空，{@code false} - 不为空
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return !isNotEmpty(map);
	}


	/**
	 * 判断 str 字符串是否 为空
	 *
	 * @param str String
	 * @return {@code true} - 空，{@code false} - 不为空
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.trim().isEmpty();
	}

	/**
	 * 判断 str 字符串是否 为空
	 *
	 * @param str String
	 * @return {@code true} - 空，{@code false} - 不为空
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 如果给定字符串{@code str}中不包含{@code appendStr}，则在{@code str}后追加{@code appendStr}；
	 * 如果已包含{@code appendStr}，则在{@code str}后追加{@code otherwise}
	 *
	 * @param str       给定的字符串
	 * @param appendStr 需要追加的内容
	 * @param otherwise 当{@code appendStr}不满足时追加到{@code str}后的内容
	 * @return 追加后的字符串
	 */
	public static String appendIfNotContain(String str, String appendStr, String otherwise) {
		if (isEmpty(str) || isEmpty(appendStr)) {
			return str;
		}
		if (str.contains(appendStr)) {
			return str.concat(otherwise);
		}
		return str.concat(appendStr);
	}

	/**
	 * 编码
	 *
	 * @param value str
	 * @return encode str
	 */
	public static String urlEncode(String value) {
		if (value == null) {
			return "";
		}
		try {
			String encoded = URLEncoder.encode(value, HttpClientConstants.DEFAULT_ENCODING.displayName());
			return encoded.replace("+", "%20")
					.replace("*", "%2A")
					.replace("~", "%7E")
					.replace("/", "%2F");
		} catch (UnsupportedEncodingException e) {
			throw new HttpClientRuntimeException("Failed To Encode Uri", e);
		}
	}

	/**
	 * 解码URL<br>
	 * 将%开头的16进制表示的内容解码。
	 *
	 * @param url URL
	 * @return 解码后的URL
	 * @throws HttpClientRuntimeException SimplestHttpException
	 */
	public static String urlDecode(String url)  {
		if (isEmpty(url)) {
			return url;
		}
		try {
			return URLDecoder.decode(url, HttpClientConstants.DEFAULT_ENCODING.displayName());
		} catch (UnsupportedEncodingException e) {
			throw new HttpClientRuntimeException("Unsupported encoding", e);
		}
	}

	/**
	 * 遍历
	 *
	 * @param map    待遍历的 map
	 * @param action 操作
	 * @param <K>    map键泛型
	 * @param <V>    map值泛型
	 */
	public static <K, V> void  forFunction(Map<K, V> map, BiConsumer<String,String> action) {
		if (isEmpty(map) || action == null) {
			return;
		}
		for (Map.Entry<K, V> entry : map.entrySet()) {
			K k;
			V v;
			try {
				k = entry.getKey();
				v = entry.getValue();
			} catch (IllegalStateException ise) {
				// this usually means the entry is no longer in the map.
				throw new ConcurrentModificationException(ise);
			}
			String key = k instanceof byte[] ? new String((byte[])k): String.valueOf(k);
			String value = v instanceof byte[] ? new String((byte[])v): String.valueOf(v);
			action.accept(key, value);
		}
	}
	/**
	 * 遍历
	 *
	 * @param map    待遍历的 map
	 * @param action 操作
	 * @param <K>    map键泛型
	 * @param <V>    map值泛型
	 */
	public static <K, V> void  forEach(Map<K, V> map, BiConsumer<? super K, ? super V> action) {
		if (isEmpty(map) || action == null) {
			return;
		}
		for (Map.Entry<K, V> entry : map.entrySet()) {
			K k;
			V v;
			try {
				k = entry.getKey();
				v = entry.getValue();
			} catch (IllegalStateException ise) {
				// this usually means the entry is no longer in the map.
				throw new ConcurrentModificationException(ise);
			}
			action.accept(k, v);
		}
	}

	/**
	 * map转字符串，转换后的字符串格式为 {@code xxx=xxx&xxx=xxx}
	 *
	 * @param params 待转换的map
	 * @param encode 是否转码
	 * @return str
	 */
	public static String parseMapToString(Map<String, String> params, boolean encode) {
		if(isEmpty(params)){
			return "";
		}
		List<String> paramList = new ArrayList<>();
		forFunction(params, (k, v) -> {
			if (v == null) {
				paramList.add(k + "=");
			} else {
				paramList.add(k + "=" + (encode ? urlEncode(v) : v));
			}
		});
		return String.join("&", paramList);
	}






	/**
	 * 字符串转map，字符串格式为 {@code xxx=xxx&xxx=xxx}
	 *
	 * @param str    待转换的字符串
	 * @param decode 是否解码
	 * @return map
	 */
	public static Map<String, String> parseStringToTreeMap(String str, boolean decode) {
		str = preProcess(str);

		Map<String, String> params = new TreeMap<>();
		if (isEmpty(str)) {
			return params;
		}

		if (!str.contains("&")) {
			params.put(decode(str, decode), HttpClientConstants.EMPTY);
			return params;
		}

		final int len = str.length();
		String name = null;
		// 未处理字符开始位置
		int pos = 0;
		// 未处理字符结束位置
		int i;
		// 当前字符
		char c;
		for (i = 0; i < len; i++) {
			c = str.charAt(i);
			// 键值对的分界点
			if (c == '=') {
				if (null == name) {
					// name可以是""
					name = str.substring(pos, i);
				}
				pos = i + 1;
			}
			// 参数对的分界点
			else if (c == '&') {
				if (null == name && pos != i) {
					// 对于像&a&这类无参数值的字符串，我们将name为a的值设为""
					addParam(params, str.substring(pos, i), HttpClientConstants.EMPTY, decode);
				} else if (name != null) {
					addParam(params, name, str.substring(pos, i), decode);
					name = null;
				}
				pos = i + 1;
			}
		}

		// 处理结尾
		if (pos != i) {
			if (name == null) {
				addParam(params, str.substring(pos, i), HttpClientConstants.EMPTY, decode);
			} else {
				addParam(params, name, str.substring(pos, i), decode);
			}
		} else if (name != null) {
			addParam(params, name, HttpClientConstants.EMPTY, decode);
		}

		return params;
	}

	/**
	 *  将参数按K-V方式增加到参数对象Map中;
	 * @param params 参数对象Map
	 * @param key 参数对象Map中的key
	 * @param value 参数对象Map中的key对应的value值
	 * @param decode 参数对象是否需要转码
	 */
	private static void addParam(Map<String, String> params, String key, String value, boolean decode) {
		key = decode(key, decode);
		value = decode(value, decode);
		if (params.containsKey(key)) {
			params.put(key, params.get(key) + "," + value);
		} else {
			params.put(key, value);
		}
	}

	/**
	 * 字符串进行转码实现
	 * @param str
	 * @param decode
	 * @return
	 */
	private static String decode(String str, boolean decode) {
		return decode ? urlDecode(str) : str;
	}


	/**
	 * 解释请求url,是否有问号(?)或井号(#),对应去除
	 * @param str
	 * @return
	 */
	private static String preProcess(String str) {
		if (isEmpty(str)) {
			return str;
		}
		// 去除 URL 路径信息
		int beginPos = str.indexOf("?");
		if (beginPos > -1) {
			str = str.substring(beginPos + 1);
		}

		// 去除 # 后面的内容
		int endPos = str.indexOf("#");
		if (endPos > -1) {
			str = str.substring(0, endPos);
		}

		return str;
	}

	/***
	 * 请求的url转换Map请求对象,其中urlParam支持两种格式转换,url格式或对象json字符串格式
	 * @param urlParam 字符串类型ulr请求实现
	 * @param decode 是否需要转码
	 * @param isUrlNotJson 默认值为false,使用的是json格式转换内容
	 * @return
	 */
	public static Map<String, String> toMapByString(String urlParam,boolean decode, boolean isUrlNotJson) {
		if(isUrlNotJson){
			Map<String, String>  treeMap = parseStringToTreeMap(urlParam,decode);
			return treeMap;
		}else {
			Object object = JSON.parse(urlParam);
			Map<String, String>  treeMap = toMap(object,decode);
			return treeMap;

		}
	}
	/***
	 * 请求的内容体对象转换成请求Map<kv>格式对象,
	 * @param object 内容体对象,或头部体对象
	 * @param decode 是否需要转码,默认值为false
	 * @return  map	结果
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> toMap(Object object,boolean decode) {
		if(object == null || isMapOrJsonClass(object.getClass()) ){
			return (Map<String, String>)object;
		}
		List<Field> fields = FieldUtils.getAllFieldsList(object.getClass());// klass.getFields();
		Map<String, String> map = new TreeMap<>();
        for (Field field : fields) {
			try {
				field.setAccessible(true);
				Object obj = field.get(object);
				String value = ClassUtil.parseValueString(obj);
				value = decode(value,decode);
				map.put(field.getName(),value);
			} catch (Exception e) {
			}
		}
		return  map;
	}

//	public static HttpRequestParams getClientSign(Object head,Object body,String md5Key,boolean decode)  {
//		//1.请求参数对象转换排序的map树
//		Map<String, String> dataMap = new TreeMap<>();
//		Map<String, String> headerMap = null;
//		Map<String, String> bodyMap = null;
//		if(null != head){
//			headerMap = toMap(head,decode);
//			if(headerMap != null && !headerMap.isEmpty()){
//				dataMap.putAll(headerMap);
//			}
//		}
//		if(null != body){
//			bodyMap = toMap(body,decode);
//			if(bodyMap != null && !bodyMap.isEmpty()){
//				dataMap.putAll(bodyMap);
//			}
//		}
//		//2.请求的map 树转换url get请求规范的字符串
//		String urlParam = getSortedMapSign(dataMap,Arrays.asList("cliSign"));
//		String signParam = paramMd5length(urlParam,md5Key,8,24);
//		HttpRequestParams httpRequestParams = HttpRequestParams.build(bodyMap,headerMap,decode,urlParam,signParam);
//		return httpRequestParams;
//	}




	/**
	 * 获取签名 服务端,用于传输公共参数,作用是参数改篡改,用于前端的方法,notContains集合,为排除参与加密字段
	 * 获取签名的参数 格式a=1&b=1
	 * @param param 为所有请求参数
	 * @param notContains 排除参与加密字段
	 * @return
	 */
	public static String getSortedMapSign(Map<String, String> param,List<String> notContains){
		StringBuffer sb = new StringBuffer();
		String strBody="";
		if(null != param && param.size() > 0) {
			if(!(param instanceof  TreeMap)){
				param = new TreeMap<>(param);
			}
			for (Iterator<Map.Entry<String, String>> iterator = param.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, String> obj = iterator.next();
				if(null != notContains && notContains.contains(obj.getKey())){
					continue;
				}
				Object value = obj.getValue();
				sb.append(obj.getKey()).append("=").append(value).append("&");
			}
			if(sb.length()>1 ){
				strBody = sb.substring(0, sb.length() - 1);
			}
		}
		return strBody;
	}

	/**
	 * 将头部对像或公共参数对像 和请求体对象进行汇总合并成一个map<K-v> bodyMap对像,内容是否需要decode
	 * @param head 头部对像或公共参数对像
	 * @param body 请求体对象
	 * @param decode ,内容是否需要decode
	 * @return
	 */
	public static Map<String, String> getClientSignMap(Object head,Object body,boolean decode ){
		Map<String, String> dataMap = new TreeMap<>();
		if(null != head){
			Map<String, String> headMap = toMap(head,decode);
			if(headMap != null && !headMap.isEmpty()){
				dataMap.putAll(headMap);
			}
		}
		if(null != body){
			Map<String, String> bodyMap = toMap(body,decode);
			if(bodyMap != null && !bodyMap.isEmpty()){
				dataMap.putAll(bodyMap);
			}
		}

		return dataMap;
	}

	/**
	 * 加密
	 * @param md5Content 参与加密码的内容信息;
	 *  @param md5Content 每个系统自定议的干扰的内容信息
	 * @return
	 */
	public   static String paramMd5length(String md5Content, String md5Key,int indexStart, int indexEnd) {
		String pass = paramMd5LowerCase(md5Content,md5Key);
		pass = pass.substring(indexStart, indexEnd);
		return pass;
	}


	/**
	 * 加密
	 * @param md5Content 参与加密码的内容信息;
	 *  @param md5Content 每个系统自定议的干扰的内容信息
	 * @return
	 */
	private  static String paramMd5LowerCase(String md5Content, String md5Key) {
		String pass = md5String(md5Content + md5Key).toLowerCase();
		return pass;
	}

	/**
	 * 将二进制转换成16进制字符串
	 * @param bytes
	 * @return
	 */
	private static String parseByte2Hex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}


	/**
	 * 将字符串内容进行md5加密实现
	 * @param src
	 * @return
	 */
	private static String md5String(String src){
		try {
			MessageDigest alg = MessageDigest.getInstance(MD5_CRYPT);
			byte[] md5Byte =  alg.digest(src.getBytes(URF_8));
			return parseByte2Hex(md5Byte);
		} catch (Exception e) {}
		return "";
	}

	/**
	 * 判断字段或类型是map或JSONOBJECT, 返回true, 否则返回false
	 * @param fieldType
	 * @return
	 */
	public static  boolean isMapOrJsonClass(Class<?> fieldType){
		if(fieldType.isAssignableFrom(Map.class)){
			return true;
		}

		Class<?> interfaces[] = fieldType.getInterfaces();
		if(null != interfaces && Arrays.asList(interfaces).contains(Map.class)){
			return true;
		}
		if("JSONOBJECT".equals(fieldType.getSimpleName().toUpperCase())){
			return true;
		}
		return false;
	}
}
