package com.suven.framework.util.crypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.suven.framework.util.json.JsonUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;


/**
 * 接口请求参数tree型排序后加载实现类
 * <p>
 * 提供客户端和服务端的签名生成功能，用于参数篡改检测。
 * 支持将请求参数按key进行排序后生成签名，确保传输数据的完整性。
 * </p>
 *
 * @author Joven.wang
 * @date 2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2018 gc by https://www.suven.top
 */
@SuppressWarnings({"rawtypes", "unchecked", "StringBufferReplaceableByString"})
public class SignParam {


	private static final Logger logger = LoggerFactory.getLogger(SignParam.class);

	public static final String TOP_SERVER_APPKEY = "H@s0zSix!fiNger8";
	/**
	 * 获取客户端签名，用于传输公共参数，防止参数篡改
	 * <p>
	 * 将head和body参数转换为Map，排序后生成MD5签名并截取中间16位。
	 * </p>
	 *
	 * @param head 公共参数对象属性，可为null
	 * @param body 接口个性参数对象属性，可为null
	 * @return 16位签名字符串
	 */
	public static String getClientSign(@Nullable Object head, @Nullable Object body) {
		Map dataMap = getClientSignMap(head,body);
        String signParam = getServerSignParam(dataMap,Arrays.asList("cliSign"));
		String pass = paramMd5(signParam);
		return pass.substring(8, 24);
	}
	
	/**
	 * 获取服务端签名，用于验证前端传输参数的完整性
	 * <p>
	 * 对服务端收到的参数进行排序后生成MD5签名并截取中间16位。
	 * </p>
	 *
	 * @param param 服务端收到前端所有参数的k-v值，可为null
	 * @return 16位签名字符串
	 */
	public static String getServerSign(@Nullable Map<String, Object> param) {
		String signParam = getServerSignParam(param,Arrays.asList("cliSign"));
		String pass = paramMd5(signParam);
		return pass.substring(8, 24);
		
	}
	

	/**
	 * 获取签名的参数字符串，格式为a=1&b=1
	 * <p>
	 * 对请求参数进行排序，排除指定字段后拼接成字符串。
	 * </p>
	 *
	 * @param param 所有请求参数，可为null
	 * @param notContains 排除参与加密的字段集合，可为null
	 * @return 拼接后的参数字符串
	 */
	public static String getServerSignParam(@Nullable Map<String, Object> param, @Nullable List<String> notContains){
		StringBuilder sb = new StringBuilder();
		String strBody="";
		if(null != param && param.size() > 0) {
			if(!(param instanceof  TreeMap)){
				param = new TreeMap<>(param);
			}
			for (Iterator<Entry<String, Object>> iterator = param.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Object> obj = iterator.next();
				if(null != notContains && notContains.contains(obj.getKey())){
					continue;
				}
                Object valueObj = obj.getValue();
                String value = "";
                if(null == valueObj){
                    value = "";
                }else if(valueObj instanceof String[]){
                    String[] values = (String[])valueObj;
                    value = values[values.length-1];
                }else{
                    value = valueObj.toString();
                }
				sb.append(obj.getKey()).append("=").append(value).append("&");
			}
			if(sb.length()>1 ){
				strBody = sb.substring(0, sb.length() - 1);
			}
		}
		return strBody;
	}


	/**
	 * 获取客户端签名参数Map
	 * <p>
	 * 将head和body对象转换为Map并合并。
	 * </p>
	 *
	 * @param head 公共参数对象，可为null
	 * @param body 个性参数对象，可为null
	 * @return 合并后的参数Map
	 */
	private static Map<String, Object> getClientSignMap(@Nullable Object head, @Nullable Object body){
		Map<String, Object> dataMap = new TreeMap<>();
		try {
            if(null != head){
                Map<String, Object> headMap = JsonUtils.objectToMap(head);
                if(headMap != null && !headMap.isEmpty()){
                    dataMap.putAll(headMap);
                }
            }
			if(null != body){
                Map<String, Object> bodyMap = JsonUtils.objectToMap(body);
                if(bodyMap != null && !bodyMap.isEmpty()){
                    dataMap.putAll(bodyMap);
                }
			}
		} catch (Exception e) {
				logger.error("body or head content should be key-value");
		}
		return dataMap;
	}

	/**
	 * 对参数进行MD5加密并返回前16位
	 * <p>
	 * 注意：此方法返回完整MD5值的长度，而非前16位。如需前16位请使用 substring。
	 * </p>
	 *
	 * @param param 要加密的字符串
	 * @return 32位MD5加密字符串的小写形式
	 */
	public  static String paramMd5By16(String param) {
		String pass = "";
		try {
			pass = CryptUtil.md5(param).toLowerCase();
			// 注意：此处未返回截取后的值，如需前16位应使用: return pass.substring(0, 16);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pass;
	}
	
	
	/**
	 * 对参数加上密钥后进行MD5加密
	 * <p>
	 * 使用固定的密钥TOP_SERVER_APPKEY对参数进行加密，增强安全性。
	 * </p>
	 *
	 * @param param 要加密的字符串
	 * @return 32位MD5加密字符串的小写形式
	 */
	private  static String paramMd5(String param) {
		String pass = "";
		try {
			pass = CryptUtil.md5(param + TOP_SERVER_APPKEY).toLowerCase();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pass;
	}
	

}
