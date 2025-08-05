package com.suven.framework.util.bean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Title: XmlSerializer.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 泛型工具类
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class GenericsUtils {
	
	/**
	 * 获得指定类的父类的泛型参数的实际类型
	 * 
	 * @param  clazz 			Class
	 * @param  index  			泛型参数所在索引,从0开始
	 * @return  {@link Class}	
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz, int index) {
		if (clazz == null) {
			return null;
		}
		
		Type genericType = clazz.getGenericSuperclass();
		if (!(genericType instanceof ParameterizedType)) {
			return Object.class;
		}
		
		Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();
		if (params != null && index >= 0 && index < params.length ) {
			if (params[index] instanceof Class) {
				return (Class) params[index];
			}
		}
		return Object.class;
	}
	/**
	 * Http 替换https
	 * @param url
	 * @return
	 */
	private static String replaceHttps(String url) {
		String HTTPS = "https://";
		String HTTP = "http://";
		// 是否含有端口号
		boolean isHttp = url.startsWith(HTTP);
		url = url.replaceFirst(HTTP,HTTPS);
		return url;
	}


}
