package com.suven.framework.test.rule.util;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.util.crypt.CryptUtil;
import com.suven.framework.util.json.JsonUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class SignParamTest {

	
	static Logger logger =LoggerFactory.getLogger(SignParamTest.class);
	
	/**
	 * 获取签名 客户端
	 * @param head
	 * @param body
	 * @return
	 */
	public static String getClientSign(Object head,Object body)  {
		String signParam=getClientSignParam(head,body);
		String pass = paramMd5(signParam);
		return pass.substring(8, 24);
	}
	
	public static String getClientSing(Map map){
		String signParam = getSignParam(map);
		String pass = paramMd5(signParam);
		return pass.substring(8, 24);
	}
	
	/**
	 * 获取签名 服务端
	 * @param param
	 * @return
	 */
	public static String getServerSign(Map param)  {
		String signParam="";
		param = getParameterMap(param);
		if(param.containsKey("sign")){
			param.remove("sign");//不参与服务端校验
		}
		signParam=getServerSignParam(param);
		String pass = paramMd5(signParam);
		return pass.substring(8, 24);
		
	}
	
	/**
	 * 获取签名的参数 格式a=1&b=1
	 * @param param
	 * @return
	 */
	public static String getServerSignParam(Map<String, Object> param){
		return getSignParam(param);
	}
	
	/**
	 * 获取签名的参数 格式a=1&b=1
	 * @param head
	 * @param body
	 * @return
	 */
	public static String getClientSignParam(Object head,Object body){
		return getSignParam(object2MapParam(head,body));
	}
	public static String getSignParam(Map param){
		StringBuffer sb = new StringBuffer();
		String strBody="";
		if(null != param && param.size() > 0) {
			for (Iterator<Entry<String, Object>> iterator = param.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Object> obj = iterator.next();
				sb.append(obj.getKey()).append("=").append(String.valueOf(obj.getValue())).append("&");
			}
			if(sb.length()>1){
				strBody = sb.substring(0, sb.length() - 1);
			}
		}
		return strBody;
	}
	
	
	public static Map<String, String> object2MapParam(Object head,Object body){
		Map bodyMap = null;
		Map headMap = null;
		try {
			if(null!=body){
			bodyMap= JsonUtils.toTreeMap(JSON.toJSONString(body));
			}
			if(null!=head){
				headMap=JsonUtils.toTreeMap(JSON.toJSONString(head));
			}
			} catch (Exception e) {
				logger.error("body or head content should be key-value");
			}
		    if(headMap!=null&&bodyMap!=null){
			headMap.putAll(bodyMap);
		    }
			return headMap;
	}
	

	
	
	/**
	 * 加密
	 * @param param
	 * @return
	 */
	private  static String paramMd5(String param) {
		String pass = "";
		try {
			pass = CryptUtil.md5(param + GlobalConfigConstants.TOP_SERVER_APPKEY).toLowerCase();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pass;
	}
	
	/**
	 * 将getParameterMap转成可读的map
	 * @param properties
	 * @return
	 */
	private static Map<String, Object> getParameterMap(Map<String, Object> properties) {
	    Map<String, Object> returnMap = new TreeMap<>();
	    Iterator<?> entries = properties.entrySet().iterator();
	    Map.Entry<String, Object> entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next();
	        name = entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            value = values[values.length-1];
//	            for(int i=0;i<values.length;i++){
//	                value = values[i] + ",";
//	            }
//	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        returnMap.put(name, value);
	    }
	    return returnMap;
}
}
