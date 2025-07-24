package com.suven.framework.util.bean;

import com.suven.framework.common.constants.ReflectionsScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


/**
 * @Title: XmlSerializer.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 枚举工具类
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class EnumUtils {
	
	private static Logger LOGGER = LoggerFactory.getLogger(EnumUtils.class);
	
	/**
	 * 根据枚举类名和Key的名字获得枚举对象
	 * 
	 * @param <T>
	 * @param  enumClass		枚举类对象
	 * @param  fieldName		类型名	
	 * @return T
	 */
	public static <T extends Enum<T>> T valueOf(Class<T> enumClass, String fieldName) {
		return Enum.valueOf(enumClass, fieldName);
	}

	/**
	 * 根据枚举的类名和一个常量值构建枚举对象
	 * 
	 * @param <T>
	 * @param enumClass			枚举类对象
	 * @param value				需要查询的值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T getEnum(Class<T> enumClass, int value) {
		try {
			if (value < 0) {
				return null;
			}
			Method method = enumClass.getMethod("values");
			T[] values = (T[]) method.invoke(enumClass);
			if (values != null && values.length > value) {
				return values[value];
			}
			return null;
		} catch (Exception e) {
			LOGGER.error("构建枚举 [Class: {} - Value: {} ] 出现异常", enumClass, value, e);
			return null;
		}
	}

	public static <E extends Enum<E>> List<E> getEnumListByInterfaceClass(Class interfaceClass) {
		Set<Class<E >> classList = ReflectionsScan.reflections.getSubTypesOf(interfaceClass);
		List<E> list = new ArrayList<>();
		if(null == classList || classList.isEmpty())
			return list;
		for (Class<?> clazz : classList){
			try {
				if (!clazz.isEnum() || !interfaceClass.isAssignableFrom(clazz)) {
					continue;
				}
				Class<E> enumClass = (Class<E>)clazz;
				list.addAll(Arrays.asList(enumClass.getEnumConstants()));

			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return list;
	}

}
