package com.suven.framework.util.bean;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import javax.annotation.Nullable;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;




/**
 * 对象拷贝工具类
 * <p>
 * 基于Spring Bean拷贝和Apache Bean拷贝结合，实现业务增加和通过。
 * 支持对象属性拷贝、克隆、Map转换等操作，并处理枚举、日期等特殊类型。
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

@SuppressWarnings({"unchecked", "rawtypes"})
public class BeanUtil {
	private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);

	private static BeanUtilsBean beanUtilsBean;
	private BeanUtil(){}
	static{
		if(beanUtilsBean == null){
			beanUtilsBean = BeanUtilsBean.getInstance();
		}
	}


	/**
	 * bean属性拷贝
	 * <p>
	 * 支持枚举、数字、字符串、日期、布尔等类型之间的自动转换。
	 * 可以忽略指定字段的拷贝。
	 * </p>
	 *
	 * @param source 源对象，可为null
	 * @param target 目标对象，可为null
	 * @param ignoreFields 需要忽略的字段名，可选参数
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void copyProperties(@Nullable Object source, @Nullable Object target, String... ignoreFields){
		if(source == null || target == null){
			return;
		}
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreFields != null) ? Arrays.asList(ignoreFields) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && (ignoreFields == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = org.springframework.beans.BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);

						Class sourceType = sourcePd.getPropertyType();
						PropertyDescriptor pd = org.springframework.beans.BeanUtils.getPropertyDescriptor(target.getClass(), targetPd.getName());
						Class targetType = pd.getPropertyType();

						if(sourceType.isEnum() && (Integer.class.equals(targetType) || int.class.equals(targetType))){//源对象属性是枚举
							if(value == null){
								value = 0;
							} else {
								value = Enum.valueOf(sourceType, String.valueOf(value)).ordinal();
							}
						} else if(targetType.isEnum() && (Integer.class.equals(sourceType) || int.class.equals(sourceType))){//目标对象属性是枚举
							if(value == null){
								value = 0;
							}
							int intValue = (Integer)value;
							Method method = targetType.getMethod("values");
							Object[] enumValues = (Object[])method.invoke(targetType);
							if(intValue >= 0 &&  intValue <  enumValues.length){
								value = enumValues[intValue];
							} else {
								continue;
							}

						}

						if(String.class.equals(sourceType) && Number.class.isAssignableFrom(targetType)){
							Constructor constructor = targetType.getConstructor(String.class);
							value = constructor.newInstance(String.valueOf(value));
						} else if(String.class.equals(targetType) && Number.class.isAssignableFrom(sourceType)){
							value = String.valueOf(value);
						}else if(value!=null && Date.class.equals(sourceType) && String.class.equals(targetType)){
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
							value = format.format(value);
						}

						if((Boolean.class.equals(sourceType) || boolean.class.equals(sourceType))
								&& (Integer.class.equals(targetType) || int.class.equals(targetType)))
						{
							value = (Boolean)value ? 1 : 0;
						} else if((Boolean.class.equals(targetType) || boolean.class.equals(targetType))
								&& (Integer.class.equals(sourceType) || int.class.equals(sourceType)))
						{
							value = (Integer) value > 0;
						}


						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}

						writeMethod.invoke(target, value);
					}
					catch (Throwable e) {
						log.error("BeanUtil 对象复制出错:", e);
						throw new RuntimeException(e);
					}
				}
			}
		}
	}


	/**
	 * 克隆对象
	 * <p>
	 * 使用Apache Commons BeanUtils实现对象的深度克隆。
	 * </p>
	 *
	 * @param bean 要克隆的对象
	 * @return 克隆后的新对象
	 */
	public static Object cloneBean(Object bean){
		try{
			return  beanUtilsBean.cloneBean(bean);
		} catch (Throwable e) {
			log.error("BeanUtil 对象克隆出错:", e);
			throw new RuntimeException(e);
		}
	}


	/**
	 * 拷贝属性给对象(类型宽松)
	 * <p>
	 * 支持枚举类型与整数类型的自动转换。
	 * </p>
	 *
	 * @param bean 目标对象
	 * @param name 属性名
	 * @param value 属性值，可为null
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void copyProperty(Object bean, String name, @Nullable Object value){
		try{
			Class propertyClazz = beanUtilsBean.getPropertyUtils().getPropertyType(bean, name);

			if(propertyClazz.isEnum() && value instanceof Integer){//属性枚举型 目标值是整型
				int intValue = (Integer)value;
				Method method = propertyClazz.getMethod("values");
				Object[] enumValues = (Object[])method.invoke(propertyClazz);
				if(intValue >= 0 &&  intValue <  enumValues.length){
					value = enumValues[intValue];
				} else {//不合理的int值范围就不修改
					return;
				}
			}

			beanUtilsBean.copyProperty(bean, name, value);

		} catch (Throwable e) {
			log.error("BeanUtil 对象属性赋值出错:", e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * 将bean转换为Map(不能将枚举转换为int)
	 * <p>
	 * 使用Apache Commons BeanUtils实现对象到Map的转换。
	 * </p>
	 *
	 * @param bean 要转换的bean对象
	 * @return 属性Map，key为属性名，value为属性值
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Map describe(Object bean){
		try{
			return beanUtilsBean.describe(bean);
		} catch (Throwable e) {
			log.error("BeanUtil 对象克隆出错:", e);
			throw new RuntimeException(e);
		}
	}


	/**
	 * 将bean转换为Map(能将枚举转换为int)
	 * <p>
	 * 支持枚举类型自动转换为整数序号，日期类型保持不变。
	 * </p>
	 *
	 * @param bean 要转换的bean对象，可为null
	 * @return 属性Map，枚举转换为int，可为null
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map buildMap(Object bean){
		if(bean == null){
			return null;
		}
		try{
			Map map = describe(bean);
			PropertyDescriptor[] pds = beanUtilsBean.getPropertyUtils().getPropertyDescriptors(bean);
			for(PropertyDescriptor pd : pds){
				Class type = pd.getPropertyType();
				if(type.isEnum()){
					Object value = beanUtilsBean.getPropertyUtils().getSimpleProperty(bean, pd.getName());
					int intValue = 0;
					if(value != null){
						intValue = Enum.valueOf(type, String.valueOf(value)).ordinal();
					}
					map.put(pd.getName(), intValue);

				} else if(type == java.util.Date.class){//防止是Timestamp
					Object value = beanUtilsBean.getPropertyUtils().getSimpleProperty(bean, pd.getName());
					if(value != null){
						Calendar cal = Calendar.getInstance();
						cal.setTime((java.util.Date)value);
						map.put(pd.getName(),  cal.getTime());
					}
				}
			}
			return map;
		} catch (Throwable e) {
			log.error("BeanUtil 创建Map失败:", e);
			throw new RuntimeException(e);
		}


	}

	/**
	 * 将bean列表转换成map的列表
	 *
	 * @param beanList bean对象列表
	 * @return Map列表，可为null
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> buildMapList(List beanList){
		if(beanList != null && !beanList.isEmpty()){
			List<Map> mapList = new ArrayList<Map>();
			for(Object bean : beanList){
				mapList.add(buildMap(bean));
			}
			return mapList;
		}
		return null;
	}


	/**
	 * 将map转为Bean
	 * <p>
	 * 支持枚举类型、日期类型等特殊类型的自动转换。
	 * </p>
	 *
	 * @param map 属性Map，可为null
	 * @param clazz 目标类类型
	 * @return 转换后的bean对象，可为null
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object buildBean(Map map, Class clazz){
		if(map == null){
			return null;
		}
		Object bean = null;
		try{
			bean = clazz.getDeclaredConstructor().newInstance();
			PropertyDescriptor[] pds = beanUtilsBean.getPropertyUtils().getPropertyDescriptors(clazz);
			for(PropertyDescriptor pd : pds){
				String fieldName = pd.getName();
				if(map.containsKey(fieldName)){
					Object mapValue = map.get(fieldName);
					Class beanType = pd.getPropertyType();
					Object beanValue = mapValue;


					if(beanType.isEnum()){
						if(mapValue != null){
							if(mapValue instanceof String){
								if(String.valueOf(mapValue).matches("\\d+")){//数字型
									mapValue = Integer.parseInt(String.valueOf(mapValue));
									int intValue = (Integer)mapValue;

									Method method = beanType.getMethod("values");
									Object[] enumValues = (Object[])method.invoke(beanType);
									if(intValue >= 0 &&  intValue <  enumValues.length){
										beanValue = enumValues[intValue];
									} else {
										continue;
									}
								} else {//字符串标识的枚举值
									try{
										beanValue = Enum.valueOf(beanType, String.valueOf(mapValue));
									} catch (IllegalArgumentException e) {//是一个错误的值
										continue;
									}
								}

							} else if(mapValue instanceof Integer){//整型
								int intValue = (Integer)mapValue;
								Method method = beanType.getMethod("values");
								Object[] enumValues = (Object[])method.invoke(beanType);
								if(intValue >= 0 &&  intValue <  enumValues.length){
									beanValue = enumValues[intValue];
								} else {//超过了枚举的int值范围
									continue;
								}
							}
						}
					} else if(beanType.equals(java.util.Date.class)){
						if(mapValue != null){
							if(mapValue instanceof String){
								try{
									DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									beanValue = format.parse(String.valueOf(mapValue));
								} catch (ParseException e) {
									log.error("BeanUtil buildBean string 转 Date 出错!");
									continue;
								}

							}
						}
					}

					beanUtilsBean.copyProperty(bean, fieldName, beanValue);

				}

			}
			return bean;
		}catch (Throwable e) {
			log.error("BeanUtil 根据map创建bean出错:", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 拷贝属性给对象(类型严格)
	 * <p>
	 * 使用严格的类型匹配，不进行类型转换。
	 * </p>
	 *
	 * @param bean 目标对象
	 * @param name 属性名
	 * @param value 属性值，可为null
	 */
	public static void setProperty(Object bean, String name, @Nullable Object value){
		try{
			beanUtilsBean.setProperty(bean, name, value);
		} catch (Throwable e) {
			log.error("BeanUtil 给对象属性赋值出错:", e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * 获取对象属性值
	 *
	 * @param bean bean对象
	 * @param name 属性名
	 * @return 属性值
	 */
	public static Object getProperty(Object bean, String name){
		try{
			return beanUtilsBean.getPropertyUtils().getSimpleProperty(bean, name);
		} catch (Throwable e) {
			log.error("BeanUtil 获取对象属性值出错:", e);
			throw new RuntimeException(e);
		}

	}


	/**
	 * thrift集合转List
	 * <p>
	 * 将Thrift对象列表转换为普通Java对象列表。
	 * </p>
	 *
	 * @param source 源对象列表
	 * @param clazz 目标类类型
	 * @param <T> 泛型类型
	 * @return 转换后的对象列表
	 */
	public static <T> List<?> thriftListToBean(List<? extends T> source, Class clazz){
		//clone后的集合
		try{
			List<T> temp=Lists.newArrayList();
			for(T t:source){
				T temporary=(T) clazz.getDeclaredConstructor().newInstance();
				BeanUtils.copyProperties(temporary,t);
				temp.add(temporary);
			}
			return temp;
		}catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			log.error("BeanUtil thriftListToBean出错:", e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * thrift Bean转普通Bean
	 *
	 * @param source 源类
	 * @param dest 目标类
	 * @param <T> 泛型类型
	 * @return 转换后的对象
	 */
	public static <T> T thriftBeanToBean(Class source, Class dest){
		try{
			T temporary=(T) dest.getDeclaredConstructor().newInstance();
			BeanUtils.copyProperties(temporary,source);
			return temporary;
		}catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			log.error("BeanUtil thriftBeanToBean出错:", e);
			throw new RuntimeException(e);
		}
	}


	/**
	 * AOP代理工具类
	 * <p>
	 * 用于获取AOP代理对象的原始目标对象。
	 * 支持JDK动态代理和CGLIB代理。
	 * </p>
	 *
	 * @author lixiangling
	 * @date 2018/7/26 9:17
	 * @version 1.0.0
	 * Copyright: (c) 2018 gc by https://www.suven.top
	 * --------------------------------------------------------
	 * modifyer    modifyTime                 comment
	 * <p>
	 * --------------------------------------------------------
	 */
	public static class AopTargetUtils {

		private static final Logger logger = LoggerFactory.getLogger(AopTargetUtils.class);
		/**
		 * 获取目标对象
		 * <p>
		 * 从AOP代理对象中获取原始的目标对象。
		 * </p>
		 *
		 * @param proxy 代理对象
		 * @return 目标对象，如果不是代理对象则返回自身
		 */
		public static Object getTarget(Object proxy)  {

			if(!AopUtils.isAopProxy(proxy)) {
				return proxy;//不是代理对象
			}
			try {
				if (AopUtils.isJdkDynamicProxy(proxy)) {
					return getJdkDynamicProxyTargetObject(proxy);
				} else { //cglib
					return getCglibProxyTargetObject(proxy);
				}
			}catch (Exception e){
				logger.warn("",e);
			}
			return proxy;


		}


		private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
			Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
			h.setAccessible(true);
			Object dynamicAdvisedInterceptor = h.get(proxy);

			Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
			advised.setAccessible(true);

			Object target = ((AdvisedSupport)advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

			return target;
		}


		private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
			Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
			h.setAccessible(true);
			AopProxy aopProxy = (AopProxy) h.get(proxy);

			Field advised = aopProxy.getClass().getDeclaredField("advised");
			advised.setAccessible(true);

			Object target = ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();

			return target;
		}

	}
	/**
	 * 替换SQL中的IN查询占位符
	 *
	 * @param sql SQL语句
	 * @param replacement 替换字符串
	 * @return 替换后的SQL语句
	 */
	@SuppressWarnings("unused")
	private String replaceByInToIds(String sql, String replacement){
		final String searchString = "?";
		final String searchIn = " IN ";
		int beginIndex = sql.toUpperCase().indexOf(searchIn);
		if( beginIndex > 0 && (sql.indexOf(searchString) > 0)){
			String s = sql.substring(0,beginIndex) + StringUtils.replaceOnce(sql.substring(beginIndex), searchString, replacement);
			return s;
		}
		return sql;
	}

}
