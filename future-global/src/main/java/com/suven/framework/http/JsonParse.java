package com.suven.framework.http;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.IterableConvert;
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
@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public class JsonParse {

	/**
	 * 日志记录器
	 * <p>
	 * 使用 MethodHandles.lookup().lookupClass() 获取当前类，确保日志记录的准确性
	 * </p>
	 */
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	/**
	 * 字段映射缓存
	 * <p>
	 * 用于缓存类的字段信息，避免重复反射获取字段，提高性能
	 * Key: 类的 Class 对象
	 * Value: 字段名称集合
	 * </p>
	 */
	private static final Multimap<Class<?>, String> fieldMap = HashMultimap.create();

	/**
	 * 基本类型集合
	 * <p>
	 * 预留字段，用于存储基本类型集合（当前未使用）
	 * </p>
	 */
	private static final ImmutableSet<? extends Class<?>> primitiveSet = ImmutableSet.of();


	/**
	 * 常量字符键
	 * <p>
	 * 用于分割聚合类型（数组、集合）的特殊字符
	 * 使用 ASCII 码 7（不可见字符）作为分隔符
	 * </p>
	 */
	private static final char CONSTANTS_CHAR_KEY = 7;

	/**
	 * 常量分隔符
	 * <p>
	 * 用于分割字符串的逗号分隔符
	 * </p>
	 */
	private static final String CONSTANTS_SPLITE = ",";

	/**
	 * 常量键字符串
	 * <p>
	 * 将常量字符键转换为字符串形式
	 * </p>
	 */
	private static final String CONSTANTS_KEY = String.valueOf(CONSTANTS_CHAR_KEY);

	/**
	 * 返回对象的字符串表示
	 * <p>
	 * 使用反射获取对象的所有字段并格式化为字符串
	 * </p>
	 *
	 * @return 对象的字符串表示
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * 解析 HTTP 请求头到指定类型的对象
	 * <p>
	 * 将 HTTP 请求头的 Map 数据通过反射机制映射到指定类型的对象中
	 * 支持字段名的大小写兼容处理
	 * </p>
	 *
	 * @param <T>          泛型类型，表示目标对象类型
	 * @param headerMap    HTTP 请求头的 Map 数据，键为请求头名称，值为请求头值
	 * @param headerClass  目标对象的 Class 类型，必须有无参构造函数
	 * @param isCompatible 是否启用兼容模式
	 *                     如果为 true，会自动将请求头名称中的 "-" 和 "_" 去除并转为小写
	 *                     例如："User-Agent" → "useragent"
	 * @return 转换后的目标对象，如果参数为 null 或转换失败则返回 null
	 *
	 * @apiNote 兼容模式说明：
	 * <ul>
	 *   <li>启用兼容模式时，字段名和请求头都会转为小写并去除特殊字符</li>
	 *   <li>这样可以匹配不同格式的请求头（如 User-Agent、user_agent、useragent）</li>
	 * </ul>
	 *
	 * @apiNote 示例用法：
	 * <pre>{@code
	 * // 定义请求头对象类
	 * class HeaderRequest {
	 *     private String userAgent;
	 *     private String contentType;
	 *     // getters and setters
	 * }
	 *
	 * // HTTP 请求头
	 * Map<String, String> headers = new HashMap<>();
	 * headers.put("User-Agent", "Mozilla/5.0");
	 * headers.put("Content-Type", "application/json");
	 *
	 * // 解析请求头（启用兼容模式）
	 * HeaderRequest request = JsonParse.parseHeaders(headers, HeaderRequest.class, true);
	 * // request.getUserAgent() = "Mozilla/5.0"
	 * // request.getContentType() = "application/json"
	 * }</pre>
	 */
	public static <T> T parseHeaders(Map<String, String> headerMap, Class<T> headerClass, boolean isCompatible) {
		// 检查参数是否为 null
		if (Objects.isNull(headerMap) || Objects.isNull(headerClass)) {
			return null;
		}

		// 获取全部请求头参数，并做好兼容处理
		// 全量将 "-" 和 "_" 替换成 ""，并且全部转小写，方便兼容对象反射赋值
		Map<String, String> headers = new HashMap<>();
		if (isCompatible) {
			headerMap.forEach((k, v) -> {
				// 去除连字符和下划线，转为小写
				String key = k.replace("-", "").replace("_", "").toLowerCase();
				headers.put(key, v);
			});
		} else {
			// 不启用兼容模式，直接复制原始请求头
			headers.putAll(headerMap);
		}

		try {
			// 通过反射创建目标对象实例
			T header = headerClass.getDeclaredConstructor().newInstance();

			// 获取目标类的所有字段（包括父类字段）
			Field[] fields = FieldUtils.getAllFields(headerClass);

			// 遍历所有字段进行赋值
			for (Field field : fields) {
				try {
					// 获取字段的修饰符
					int mod = field.getModifiers();

					// 跳过静态字段和 final 字段
					if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
						continue;
					}

					// 获取字段名称
					String fieldName = field.getName();

					// 如果启用兼容模式，将字段名转为小写
					if (isCompatible) {
						fieldName = fieldName.toLowerCase();
					}

					// 从请求头中获取对应的值
					String headersValue = headers.get(fieldName);

					// 设置字段可访问
					field.setAccessible(true);

					// 将字符串值转换为字段类型对应的值
					Object value = convertFieldNameToValue(field, headersValue);

					// 将值设置到字段中
					setValue(field, header, value);
				} catch (Exception e) {
					// 记录单个字段赋值失败的日志，继续处理其他字段
					logger.info("header class[{}] object obtains the value through the attribute name[{}]",
							headerClass, field.getName());
				}
			}
			return header;
		} catch (Exception e) {
			// 记录整个对象创建失败的日志
			logger.info("header class[{}] object obtains the value through the attribute Exception", headerClass, e);
		}
		return null;
	}
	/**
	 * 将 JSON 字符串解析为指定类型的对象
	 * <p>
	 * 使用 FastJSON 将 JSON 字符串转换为 Java 对象
	 * </p>
	 *
	 * @param <T>   泛型类型，表示目标对象类型
	 * @param json  要解析的 JSON 字符串，可以为 null 或空字符串
	 * @param clazz 目标对象的 Class 类型
	 * @return 解析后的对象，如果 JSON 为空或解析失败则返回 null
	 *
	 * @apiNote 支持的类型：
	 * <ul>
	 *   <li>基本类型和包装类</li>
	 *   <li>String 类型</li>
	 *   <li>自定义 POJO 类</li>
	 *   <li>集合和数组</li>
	 * </ul>
	 *
	 * @apiNote 示例用法：
	 * <pre>{@code
	 * // 解析为自定义对象
	 * String json = "{\"name\":\"张三\",\"age\":25}";
	 * User user = JsonParse.parseJson(json, User.class);
	 *
	 * // 解析为 Map
	 * Map map = JsonParse.parseJson(json, Map.class);
	 *
	 * // 解析为 List
	 * String arrayJson = "[{\"name\":\"张三\"},{\"name\":\"李四\"}]";
	 * List<User> users = JsonParse.parseJson(arrayJson, List.class);
	 * }</pre>
	 */
	public static <T> T parseJson(String json, Class<T> clazz) {
		// 检查 JSON 字符串是否为 null 或空
		if (null == json || "".equals(json)) {
			return null;
		}
		try {
			// 使用 FastJSON 解析 JSON 字符串为对象
			return JSON.parseObject(json, clazz);
		} catch (Exception e) {
			// 记录解析失败的日志
			logger.warn("字符串:[{}] , 转换成class: [{}] 类异常 :[{}]", json, clazz.getSimpleName(), e);
			return null;
		}
	}

	/**
	 * 将 Map 数据转换为指定类型的对象
	 * <p>
	 * 通过反射机制将 Map 中的数据映射到指定类型的对象中
	 * 支持基本类型、集合、数组等类型的自动转换
	 * </p>
	 *
	 * @param <T>   泛型类型，表示目标对象类型
	 * @param map   包含数据的 Map 对象，键为字段名，值为字段值
	 * @param clazz 目标对象的 Class 类型，必须有无参构造函数
	 * @return 转换后的目标对象
	 * @throws Exception 如果创建实例或设置字段值时发生异常
	 *
	 * @apiNote 支持的值类型：
	 * <ul>
	 *   <li>String：直接使用</li>
	 *   <li>String[]：取第一个元素</li>
	 * </ul>
	 *
	 * @apiNote 支持的字段类型：
	 * <ul>
	 *   <li>基本类型和包装类：自动转换</li>
	 *   <li>String：直接赋值</li>
	 *   <li>Date：支持时间戳和日期字符串</li>
	 *   <li>数组：逗号分隔的字符串</li>
	 *   <li>集合：逗号分隔的字符串，需要指定泛型</li>
	 * </ul>
	 *
	 * @apiNote 示例用法：
	 * <pre>{@code
	 * // 定义对象类
	 * class User {
	 *     private String name;
	 *     private Integer age;
	 *     private Date birthday;
	 *     // getters and setters
	 * }
	 *
	 * // Map 数据
	 * Map<String, Object> map = new HashMap<>();
	 * map.put("name", "张三");
	 * map.put("age", "25");
	 * map.put("birthday", "1998-01-01 00:00:00");
	 *
	 * // 转换为对象
	 * User user = JsonParse.parseFrom(map, User.class);
	 * }</pre>
	 */
	public static <T> T parseFrom(Map map, final Class<T> clazz) throws Exception {

		// 通过反射创建目标对象实例
		T instance = clazz.getDeclaredConstructor().newInstance();

		// 获取目标类的所有字段（包括父类字段）
		List<Field> fields = FieldUtils.getAllFieldsList(clazz);

		// 遍历所有字段进行赋值
		for (Field field : fields) {
			String fieldName = field.getName();

			// 缓存字段信息，避免重复反射
			if (!fieldMap.containsEntry(clazz, fieldName)) {
				fieldMap.put(clazz, fieldName);
			}

			String values = null;
			// 从 Map 中获取字段对应的值
			Object requestValue = map.get(fieldName);

			// 如果值为 null，跳过该字段
			if (Objects.isNull(requestValue)) {
				continue;
			}

			// 处理字符串数组类型，取第一个元素
			if (requestValue instanceof String[]) {
				String[] rawValues = (String[]) requestValue;
				if (rawValues.length <= 0) {
					continue;
				}
				values = rawValues[0];
			}
			// 处理字符串类型
			else if (requestValue instanceof String) {
				values = (String) requestValue;
			}

			Class<?> fieldType = field.getType();

			// 跳过静态字段、final 字段或值为 null 的非字符串字段
			if (isTypeIsStaticOrFinal(field) || (fieldType != String.class && null == values)) {
				continue;
			}

			// 设置字段可访问
			field.setAccessible(true);

			// 将字符串值转换为字段类型对应的值
			Object value = convertFieldNameToValue(field, values);

			// 将值设置到字段中
			setValue(field, instance, value);
		}
		return instance;
	}

	/**
	 * 根据 field 字段的类型，将字符串类型的值转换成对应的类型值
	 * <p>
	 * 支持的类型转换：
	 * <ul>
	 *   <li>基本类型和包装类：int, long, double, float, boolean 等</li>
	 *   <li>String：直接返回</li>
	 *   <li>Date：支持时间戳和日期字符串</li>
	 *   <li>数组：逗号或特殊字符分隔的字符串</li>
	 *   <li>集合：逗号或特殊字符分隔的字符串</li>
	 *   <li>字节数组：Base64 编码的字符串</li>
	 * </ul>
	 * </p>
	 *
	 * @param field  业务对象的字段，包含类型信息
	 * @param values 业务对象的字段对应的字符串值
	 * @return 根据 field 类型转换成对应类型的值
	 * @throws SystemRuntimeException 如果类型不支持自动转换
	 */
	private static Object convertFieldNameToValue(Field field, String values) {
		// 检查字段是否为 null
		if (field == null) {
			return null;
		}

		String fieldName = field.getName();
		Class<?> fieldType = field.getType();
		Object value;

		// 基本类型和字符串类型
		if (isSimpleType(fieldType)) {
			value = signPrimitive(fieldType, values);
		}
		// 聚合类型（数组、集合）
		else if (isIterable(fieldType)) {
			// 先尝试用特殊字符分隔符分割
			String[] args = values.split(CONSTANTS_KEY);
			// 如果分割结果无效，再尝试用逗号分隔符
			if (null == args || args.length == 1) {
				args = values.split(CONSTANTS_SPLITE);
			}
			value = signIterable(field, args);
		}
		// 字节数组类型
		else if (isByte(fieldType)) {
			value = Base64.getDecoder().decode(values);
		} else {
			// 不支持的类型
			logger.warn("{} {}此类型不能自动转换", fieldName, fieldType);
			throw new SystemRuntimeException(SysResultCodeEnum.SYS_INVALID_REQUEST);
		}
		return value;
	}

	/**
	 * 判断字段是否为静态或 final 类型
	 * <p>
	 * 静态字段和 final 字段不参与反射赋值操作
	 * </p>
	 *
	 * @param property 要检查的字段
	 * @return 如果字段是静态或 final 类型则返回 true，否则返回 false
	 */
	private static boolean isTypeIsStaticOrFinal(Field property) {
		// 检查是否为静态字段
		boolean isStatic = Modifier.isStatic(property.getModifiers());
		// 检查是否为 final 字段
		boolean isFinal = Modifier.isFinal(property.getModifiers());
		return isStatic || isFinal;
	}

	/**
	 * 设置字段的值
	 * <p>
	 * 通过反射将值设置到指定对象的字段中
	 * </p>
	 *
	 * @param field    要设置值的字段
	 * @param instance 目标对象实例
	 * @param value    要设置的值
	 * @throws IllegalAccessException 如果字段访问权限不足
	 */
	private static void setValue(Field field, final Object instance,
								 Object value) throws IllegalAccessException {
		field.set(instance, value);
	}

	/**
	 * 判断字段类型是否为简单类型
	 * <p>
	 * 简单类型包括：
	 * <ul>
	 *   <li>基本类型和包装类：int, Integer, long, Long 等</li>
	 *   <li>String 类型</li>
	 *   <li>Date 类型</li>
	 * </ul>
	 * </p>
	 *
	 * @param fieldType 要检查的字段类型
	 * @return 如果是简单类型则返回 true，否则返回 false
	 */
	private static boolean isSimpleType(Class<?> fieldType) {
		return ClassUtils.isPrimitiveOrWrapper(fieldType)
				|| fieldType == String.class || fieldType == Date.class;
	}

	/**
	 * 将字符串值转换为基本类型或包装类型的值
	 * <p>
	 * 支持的类型转换：
	 * <ul>
	 *   <li>基本类型和包装类：int, Integer, long, Long, double, Double 等</li>
	 *   <li>Date：支持时间戳和日期字符串（yyyy-MM-dd HH:mm:ss）</li>
	 *   <li>String：直接返回</li>
	 * </ul>
	 * </p>
	 *
	 * @param fieldType 目标字段类型
	 * @param rawValue  要转换的字符串值
	 * @return 转换后的值，如果转换失败则返回默认值（int 和 long 返回 0，其他返回 null）
	 * @throws SystemRuntimeException 如果类型转换失败
	 */
	private static Object signPrimitive(Class<?> fieldType, String rawValue) {
		Object value = null;

		// 处理空字符串
		if (StringUtils.isBlank(rawValue)) {
			// 基本类型 int 和 long 返回 0
			if (fieldType == int.class || fieldType == long.class) {
				return 0;
			} else {
				// 其他类型返回 null
				return null;
			}
		}

		// 基本类型和包装类转换
		if (ClassUtils.isPrimitiveOrWrapper(fieldType)) {
			// 将基本类型转换为包装类
			Class<?> wrapperClazz = ClassUtils.primitiveToWrapper(fieldType);
			try {
				// 调用包装类的 valueOf 方法进行转换
				value = MethodUtils.invokeStaticMethod(wrapperClazz, "valueOf", rawValue);
			} catch (Exception ex) {
				logger.warn("基本类型转换失败! fieldType={}, requestValue={}", fieldType.getSimpleName(), rawValue);
				throw new SystemRuntimeException(SysResultCodeEnum.SYS_INVALID_REQUEST);
			}
		}
		// Date 类型转换
		else if (fieldType == Date.class) {
			try {
				// 如果是纯数字，视为时间戳
				if (rawValue.matches("\\d+")) {
					long time = Long.parseLong(rawValue);
					value = new Date(time);
				} else {
					// 否则按照日期格式解析
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					value = format.parse(rawValue);
				}
			} catch (Exception ex) {
				logger.warn("日期类型转换失败! fieldType={}, requestValue={}", fieldType.getSimpleName(), rawValue);
			}
		} else {
			// String 类型直接返回
			value = rawValue;
		}
		return value;
	}

	/**
	 * 将字符串数组转换为集合或数组类型的值
	 * <p>
	 * 支持的类型：
	 * <ul>
	 *   <li>数组：int[], String[], Integer[] 等</li>
	 *   <li>集合：List, Set 等（必须指定泛型类型）</li>
	 * </ul>
	 * </p>
	 *
	 * @param field   目标字段，包含类型信息
	 * @param strings 字符串数组，包含要转换的值
	 * @return 转换后的数组或集合对象
	 * @throws SystemRuntimeException 如果泛型未指定或类型不支持
	 */
	private static Object signIterable(Field field, String[] strings) {
		Class<?> fieldType = field.getType();

		// 处理数组类型
		if (fieldType.isArray()) {
			// 获取数组元素类型
			Class<?> cpnType = fieldType.getComponentType();
			// 创建数组实例
			Object array = Array.newInstance(cpnType, strings.length);
			// 遍历字符串数组，逐个转换为元素类型并赋值
			for (int i = 0; i < strings.length; i++) {
				Object primitive = signPrimitive(cpnType, strings[i]);
				if (null != primitive) {
					Array.set(array, i, primitive);
				}
			}
			return array;
		}

		// 处理集合类型
		Collection c = newCollection(fieldType);

		// 检查是否指定了泛型类型
		if (!(field.getGenericType() instanceof ParameterizedType)) {
			logger.warn("{} 泛型必须写上泛型的类型", field.getName());
			throw new SystemRuntimeException(SysResultCodeEnum.SYS_INVALID_REQUEST);
		}

		// 获取泛型类型
		ParameterizedType ptType = (ParameterizedType) field.getGenericType();
		Type genericType = ptType.getActualTypeArguments()[0];

		// 遍历字符串数组，转换为泛型类型并添加到集合中
		for (String string : strings) {
			c.add(signPrimitive((Class) genericType, string.trim()));
		}
		return c;
	}

	/**
	 * 根据集合类型创建对应的集合实例
	 * <p>
	 * 支持的集合类型：
	 * <ul>
	 *   <li>List：创建 ArrayList</li>
	 *   <li>Set：创建 HashSet</li>
	 * </ul>
	 * </p>
	 *
	 * @param <T>   泛型类型
	 * @param clazz 集合类型的 Class 对象
	 * @return 创建的集合实例
	 * @throws SystemRuntimeException 如果集合类型不支持
	 */
	@SuppressWarnings("unchecked")
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

	/**
	 * 判断类是否为可迭代类型（数组或集合）
	 * <p>
	 * 排除字节数组类型（byte[]），因为字节数组有特殊的处理逻辑
	 * </p>
	 *
	 * @param clazz 要检查的类
	 * @return 如果是数组或集合（不包括 byte[]）则返回 true，否则返回 false
	 */
	private static boolean isIterable(Class<?> clazz) {
		return (clazz.isArray() || Collection.class.isAssignableFrom(clazz))
				&& !byte[].class.isAssignableFrom(clazz);
	}

	/**
	 * 判断类是否为字节数组类型
	 * <p>
	 * 用于标识需要 Base64 解码的字段类型
	 * </p>
	 *
	 * @param clazz 要检查的类
	 * @return 如果是 byte[] 类型则返回 true，否则返回 false
	 */
	private static boolean isByte(Class<?> clazz) {
		return (clazz.isArray() || Collection.class.isAssignableFrom(clazz))
				&& byte[].class.isAssignableFrom(clazz);
	}

}