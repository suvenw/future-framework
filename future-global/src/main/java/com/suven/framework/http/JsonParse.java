package com.suven.framework.http;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.SystemRuntimeException;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Alex on 2014/12/12
 */
public class JsonParse {

	private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static Multimap<Class<?>, String> fieldMap = HashMultimap.create();
	private static ImmutableSet<? extends Class<? >> primitiveSet
			= ImmutableSet.of();




	private final static char CONSTANTS_CHAR_KEY = 7;// Constants.CHARKEY;
	private final static String CONSTANTS_SPLITE = ",";
    private final static String CONSTANTS_KEY =  String.valueOf(CONSTANTS_CHAR_KEY);

    @Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * http 提交参数头部实现java反射类对象;
	 * @param headerMap http头的聚合信息
	 * @param headerClass  反射接收类对象
	 * @return
	 */

	public static <T> T parseHeaders(Map<String,String> headerMap, Class<T> headerClass, boolean isCompatible){
		if (Objects.isNull(headerMap) || Objects.isNull(headerClass) ){
			return  null;
		}
		//获取全部请求头参数,并做好兼容处理,全量将"_""-"替换成"",并且全部转小写,方便面兼容对象反射付值;
		Map<String, String>  headers = new HashMap<>();
		if(isCompatible){
			headerMap.forEach((k,v) -> {
				String key = k.replace("-", "").replace("_", "").toLowerCase();
				headers.put(key,v);
			});
		}else {
			headers.putAll(headerMap);
		}
		try {
			T header = headerClass.getDeclaredConstructor().newInstance();
			Field[] fields = FieldUtils.getAllFields(headerClass);
			for (Field field : fields) {
				try {
					int mod = field.getModifiers();
					if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
						continue;
					}
					String fieldName = field.getName();
					if (isCompatible){
						fieldName = fieldName.toLowerCase();
					}
					String value = headers.get(fieldName);
					field.setAccessible(true);
					field.set(header, value);
				} catch (Exception e) {
					logger.info("header class[{}] object obtains the value through the attribute name[{}]",headerClass, field.getName());
				}
			}
			return header;
		}catch (Exception e){
			logger.info("header class[{}] object obtains the value through the attribute Exception ",headerClass,e);
		}
		return null;
	}


	public static <T> T parseJson(String json, Class<T> clazz){
		if(null == json || "".equals(json)){
			return null;
		}
		try {
			return JSON.parseObject(json, clazz);
		} catch (Exception e) {
			logger.warn("字符串:[{}] , 转换成class: [{}] 类异常 :[{}]",json, clazz.getSimpleName() ,e);
			return null;
		}
	}



	public static <T> T parseFrom( Map map,  final Class<T> clazz) throws Exception {

		T instance= clazz.getDeclaredConstructor().newInstance();
		List<Field> fields = FieldUtils.getAllFieldsList(clazz);
		for (Field field : fields) {
			String fieldName = field.getName();
			if (!fieldMap.containsEntry(clazz, fieldName)) {
				fieldMap.put(clazz, fieldName);
			}
			String values = null;
			Object requestValue =  map.get(fieldName);
			if (Objects.isNull(requestValue)){
				continue;
			}
			if(requestValue instanceof String[] ){
				String[] rawValues = (String[])requestValue;
				if (rawValues.length <= 0){
					continue;
				}
				values = rawValues[0];
			}if( requestValue instanceof String){
				values = (String)requestValue;
			}

			Class<?> fieldType = field.getType();

			if (isTypeIsStaticOrFinal(field) || (fieldType != String.class && null == values)) {
				continue;
			}

			field.setAccessible(true);
			Object value = null;
			if (isSimpleType(fieldType)) {//基本类型
				value = signPrimitive(fieldType, values);
			}else if (isIterable(fieldType)) {//聚合类型
				String[] args = values.split(CONSTANTS_KEY);
				if(null == args || args.length == 1 ){
					args = values.split(CONSTANTS_SPLITE);
				}
				value = signIterable(field, args);
			}else if(isByte(fieldType)){
				value = Base64.getDecoder().decode(values);
			} else{
				logger.warn("{} {}此类型不能自动转换 ",fieldName,fieldType);
				throw new SystemRuntimeException(SysResultCodeEnum.SYS_INVALID_REQUEST);
			}
			setValue(field, instance, value);
		}
		return instance;
	}

	/**
	 * 排除 泛型,扩展性,或静态,或final类型
	 * @param property
	 * @return
	 */
	private static boolean isTypeIsStaticOrFinal(Field property) {
		boolean isStatic = Modifier.isStatic(property.getModifiers());
		boolean isFinal = Modifier.isFinal(property.getModifiers());
//			boolean isTyep = RedisSetEnum.isContains(property.getType().getSimpleName());
		return isStatic || isFinal;
	}


	private static void setValue(Field field, final Object instance,
								 Object value) throws IllegalAccessException {
		field.set(instance, value);
//		JsonParse json = (JsonParse) instance;
//		json.properties.add(field.getName());
	}

	private static boolean isSimpleType(Class<?> fieldType) {
		return ClassUtils.isPrimitiveOrWrapper(fieldType)
				|| fieldType == String.class || fieldType == Date.class;
	}

	private static Object signPrimitive(Class<?> fieldType, String rawValue) {
		Object value = null;
		if (StringUtils.isBlank(rawValue)) {
			if (fieldType == int.class || fieldType == long.class) {
				return 0;
			} else {
				return null;
			}
		}
		if (ClassUtils.isPrimitiveOrWrapper(fieldType)) {
			Class<?> wrapperClazz = ClassUtils.primitiveToWrapper(fieldType);
			try {
				value = MethodUtils.invokeStaticMethod(wrapperClazz, "valueOf", rawValue);
			} catch (Exception ex) {
				logger.warn("数组内容类型不对,转换失败! fieldType={},requestValue={}",fieldType.getSimpleName(),rawValue);
				throw new SystemRuntimeException(SysResultCodeEnum.SYS_INVALID_REQUEST);
			}
		} else if (fieldType == Date.class) {
            try {
                if (rawValue.matches("\\d+")) {
                    long time = Long.valueOf(rawValue);
                    value = new Date(time);
                } else {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    value = format.parse(rawValue);
                }
            } catch (Exception ex) {
				logger.warn("数组内容类型不对,转换失败! fieldType={},requestValue={}",fieldType.getSimpleName(),rawValue);
            }
		} else {
			value = rawValue;
		}
		return value;
	}

	private static Object signIterable(Field field, String[] strings) {
		Class<?> fieldType = field.getType();
		if (fieldType.isArray()) {
			Class<?> cpnType = fieldType.getComponentType();
			Object array = Array.newInstance(cpnType, strings.length);
			for (int i = 0; i < strings.length; i++) {
				Object primitive = signPrimitive(cpnType, strings[i]);
				if (null != primitive) {
					Array.set(array, i, primitive);
				}
			}
			return array;
		}
		Collection c = newCollection(fieldType);
		if (!(field.getGenericType() instanceof ParameterizedType)) {
			logger.warn("{} 泛型必须写上泛型的类型", field.getName());
			throw new SystemRuntimeException(SysResultCodeEnum.SYS_INVALID_REQUEST);
		}
		ParameterizedType ptType = (ParameterizedType) field.getGenericType();
		Type genericType = ptType.getActualTypeArguments()[0];
		for (String string : strings) {
			c.add(signPrimitive((Class) genericType, string.trim()));
		}
		return c;
	}


	private static <T> Collection<T> newCollection(Class<T> clazz) {
		if (clazz == List.class) {
			return new ArrayList<>();
		} else if (clazz == Set.class) {
			return new HashSet<>();
		} else {
			logger.warn("{} 此类型尚未实现自动转换", clazz.getSimpleName());
			throw new SystemRuntimeException(SysResultCodeEnum.SYS_INVALID_REQUEST);
		}
	}

	private static boolean isIterable(Class<?> clazz) {
		return (clazz.isArray() || Collection.class.isAssignableFrom(clazz))
				&& !byte[].class.isAssignableFrom(clazz);
	}

	private static boolean isByte(Class<?> clazz) {
		return (clazz.isArray() || Collection.class.isAssignableFrom(clazz))
				&& byte[].class.isAssignableFrom(clazz);
	}






}