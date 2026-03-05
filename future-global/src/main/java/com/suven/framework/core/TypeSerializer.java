package com.suven.framework.core;

import com.alibaba.fastjson2.JSON;
import com.google.protobuf.GeneratedMessage;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author suven.wang
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @description : (说明) 数据类型统一转换实现类,提供业务方使用
 *
 */
/**
 * 类型序列化和反序列化工具类
 * <p>
 * 提供数据类型统一转换功能，支持基本类型、字符串、对象之间的相互转换
 * 支持使用 Protobuf 和 JSON 进行序列化/反序列化操作
 * </p>
 * <p>
 * 主要功能：
 * <ul>
 *   <li>字符串转对象：parseObject(Class, String)</li>
 *   <li>字节数组转对象：parseObject(Class, byte[])</li>
 *   <li>对象序列化成字节：parseBytes(Object)</li>
 *   <li>对象转字符串：parseString(Object)</li>
 *   <li>字节反序列化：fromBytes(byte[], Class)</li>
 *   <li>字节集合反序列化：fromByteList, fromByteMap</li>
 * </ul>
 * </p>
 *
 * @author suven.wang
 * @version V1.0
 */
public class TypeSerializer {

	/**
	 * 日志记录器，用于记录序列化/反序列化过程中的异常和调试信息
	 */
	private static final Logger log = LoggerFactory.getLogger(TypeSerializer.class);

	/**
	 * 字符编码，默认使用 UTF-8 编码
	 * 用于字符串和字节数组之间的转换
	 */
	private static final Charset charset = StandardCharsets.UTF_8;

	/**
	 * 基本类型集合
	 * <p>
	 * 包含 Java 所有的基本类型及其包装类，用于快速判断类型是否为基本类型
	 * 使用不可变集合 Set.of() 提高访问性能
	 * </p>
	 */
	private static final Set<? extends Class<? extends Serializable>> primitiveSet;

	/**
	 * 静态初始化块
	 * <p>
	 * 初始化基本类型集合，包括：
	 * <ul>
	 *   <li>整数类型：int, Integer, long, Long, short, Short, byte, Byte</li>
	 *   <li>浮点类型：float, Float, double, Double</li>
	 *   <li>布尔类型：boolean, Boolean</li>
	 *   <li>其他类型：String, Number</li>
	 * </ul>
	 * </p>
	 * <p>
	 * 注意：使用 Set.of() 创建的集合是不可变的，比 HashSet 有更好的性能
	 * </p>
	 */
	static {
		// 使用 Set.of() 创建不可变集合，比 HashSet 性能更好
		primitiveSet = Set.of(int.class, Integer.class, long.class, Long.class,
				double.class, Double.class, float.class, Float.class, byte.class, Byte.class,
				short.class, Short.class, boolean.class, Boolean.class, String.class, Number.class);
	}


	/**
	 * 将字符串转换成相对应的对象
	 * <p>
	 * 支持将字符串转换为各种数据类型，包括：
	 * <ul>
	 *   <li>基本类型：boolean, short, int, long, float, double</li>
	 *   <li>包装类：Boolean, Short, Integer, Long, Float, Double</li>
	 *   <li>字符串类型：String</li>
	 *   <li>自定义对象：通过 JSON 反序列化</li>
	 * </ul>
	 * </p>
	 *
	 * @param <T>       泛型类型，表示目标类型
	 * @param clazzType 目标类型的 Class 对象，不能为 null
	 *                  支持的类型包括基本类型、包装类、String 和自定义 POJO 类
	 * @param str       要转换成对象的字符串，可以为 null
	 *                  如果为 null 或空字符串，基本类型会转换为默认值（如 0）
	 * @return 转换后的对象，如果转换失败或参数无效则返回 null
	 *         对于基本类型，null 或空字符串会返回对应的零值
	 *
	 * @apiNote 示例用法：
	 * <pre>{@code
	 * // 字符串转整数
	 * Integer num = TypeSerializer.parseObject(Integer.class, "123");
	 *
	 * // 字符串转布尔值
	 * Boolean flag = TypeSerializer.parseObject(Boolean.class, "true");
	 *
	 * // 字符串转自定义对象
	 * User user = TypeSerializer.parseObject(User.class, "{\"name\":\"张三\"}");
	 * }</pre>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseObject(Class<T> clazzType, String str) {
		// 检查目标类型是否为 null
		if (clazzType == null) {
			return null;
		}

		// 如果目标类型是 String，直接返回原字符串
		if (String.class == clazzType) {
			return (T) str;
		}

		// 如果目标类型不是基本类型，使用 JSON 解析器进行转换
		// 支持自定义 POJO 类的转换
		if (!primitiveSet.contains(clazzType)) {
			return JSON.parseObject(str, clazzType);
		}

		// 处理基本类型的转换
		Object value = null;

		// 如果字符串为 null 或空，将其设置为 "0" 以避免解析异常
		if (str == null || "".equals(str)) {
			str = "0";
		}
		// 布尔类型转换：支持 "true"/"false" 字符串
		else if (boolean.class == clazzType || Boolean.class == clazzType) {
			value = Boolean.valueOf(str);
		}
		// 短整型转换
		else if (short.class == clazzType || Short.class == clazzType) {
			value = Short.parseShort(str);
		}
		// 整型转换
		else if (int.class == clazzType || Integer.class == clazzType) {
			value = Integer.parseInt(str);
		}
		// 长整型转换
		else if (long.class == clazzType || Long.class == clazzType) {
			value = Long.parseLong(str);
		}
		// 单精度浮点型转换
		else if (float.class == clazzType || Float.class == clazzType) {
			value = Float.parseFloat(str);
		}
		// 双精度浮点型转换或数字类型转换
		else if (double.class == clazzType || Double.class == clazzType
				|| Number.class == clazzType) {
			value = Double.parseDouble(str);
		}

		return (T) value;
	}

	/**
	 * 将字节数组转换成相对应的对象
	 * <p>
	 * 支持将字节数组转换为各种数据类型，包括：
	 * <ul>
	 *   <li>字节数组类型：byte[]</li>
	 *   <li>字节类型：byte, Byte（取数组的第一个字节）</li>
	 *   <li>字符串类型：String（使用 UTF-8 编码）</li>
	 *   <li>其他类型：先将字节数组转为字符串，再通过 parseObject(Class, String) 转换</li>
	 * </ul>
	 * </p>
	 *
	 * @param <T>       泛型类型，表示目标类型
	 * @param clazzType 目标类型的 Class 对象，不能为 null
	 *                  支持的类型包括 byte[], byte, Byte, String 以及可通过字符串转换的类型
	 * @param data      要转换成对象的字节数组，可以为 null
	 *                  如果为 null，方法返回 null
	 * @return 转换后的对象，如果转换失败或参数无效则返回 null
	 *
	 * @apiNote 示例用法：
	 * <pre>{@code
	 * // 字节数组转字符串
	 * String str = TypeSerializer.parseObject(String.class, "hello".getBytes());
	 *
	 * // 字节数组转整数（先转字符串，再转整数）
	 * Integer num = TypeSerializer.parseObject(Integer.class, "123".getBytes());
	 *
	 * // 字节数组转自定义对象
	 * User user = TypeSerializer.parseObject(User.class, "{\"name\":\"张三\"}".getBytes());
	 * }</pre>
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseObject(Class<T> clazzType, byte[] data) {

		// 检查目标类型是否为 null
		if (clazzType == null) {
			return null;
		}

		// 检查字节数组是否为 null
		if (data == null) {
			return null;
		}

		// 如果目标类型是字节数组，直接返回原数组
		if (byte[].class.equals(clazzType)) {
			return (T) data;
		}
		// 如果目标类型是字节，返回数组的第一个字节
		else if (byte.class == clazzType || Byte.class == clazzType) {
			Byte value = data[0];
			return (T) value;
		}
		// 如果目标类型是字符串，使用 UTF-8 编码转换
		else if (String.class == clazzType) {
			String value = new String(data, charset);
			return (T) value;
		}
		// 其他类型：先将字节数组转为字符串，再调用 parseObject(Class, String) 进行转换
		else {
			return parseObject(clazzType, new String(data));
		}
	}

	/**
	 * 将对象序列化成字节数组
	 * <p>
	 * 使用 Protobuf 协议进行序列化，支持：
	 * <ul>
	 *   <li>基本类型：先转为字符串，再转为字节数组</li>
	 *   <li>复杂对象：使用 Protobuf 协议序列化</li>
	 * </ul>
	 * </p>
	 *
	 * @param object 要序列化的 Java 对象，可以为 null
	 *               如果为 null，方法返回 null
	 * @return 序列化后的字节数组，如果序列化失败或对象为 null 则返回 null
	 *
	 * @apiNote 序列化规则：
	 * <ul>
	 *   <li>基本类型（int, String 等）：对象 → 字符串 → 字节数组</li>
	 *   <li>复杂对象：使用 Protobuf 协议序列化为字节数组</li>
	 * </ul>
	 *
	 * @apiNote 示例用法：
	 * <pre>{@code
	 * // 基本类型序列化
	 * byte[] bytes = TypeSerializer.parseBytes(123);
	 *
	 * // 字符串序列化
	 * byte[] bytes = TypeSerializer.parseBytes("hello");
	 *
	 * // 自定义对象序列化
	 * User user = new User("张三");
	 * byte[] bytes = TypeSerializer.parseBytes(user);
	 * }</pre>
	 */
	@SuppressWarnings("unchecked")
	public static byte[] parseBytes(Object object) {
		// 检查对象是否为 null
		if (null == object) {
			return null;
		}

		// 如果对象是基本类型，先转为字符串，再转为字节数组
		if (primitiveSet.contains(object.getClass())) {
			return String.valueOf(object).getBytes();
		}

		// 使用 Protobuf 协议序列化复杂对象
		// 分配缓冲区用于序列化
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			// 获取对象的运行时 Schema 并进行序列化
			Schema<Object> schema = (Schema<Object>) RuntimeSchema.getSchema(object.getClass());
			return ProtobufIOUtil.toByteArray(object, schema, buffer);
		} finally {
			// 无论成功或失败，都要清空缓冲区以释放资源
			buffer.clear();
		}
	}

	/**
	 * 将对象转换成字符串
	 * <p>
	 * 使用 Protobuf 协议进行序列化后再转为字符串，支持：
	 * <ul>
	 *   <li>基本类型：直接调用 String.valueOf() 转换</li>
	 *   <li>复杂对象：先序列化为字节数组，再转为字符串</li>
	 * </ul>
	 * </p>
	 *
	 * @param object 要转换的 Java 对象，可以为 null
	 *               如果为 null，方法返回 null
	 * @return 对象的字符串表示，如果转换失败或对象为 null 则返回 null
	 *
	 * @apiNote 转换规则：
	 * <ul>
	 *   <li>基本类型（int, String 等）：直接调用 String.valueOf()</li>
	 *   <li>复杂对象：对象 → Protobuf 字节数组 → 字符串</li>
	 * </ul>
	 *
	 * @apiNote 示例用法：
	 * <pre>{@code
	 * // 基本类型转换
	 * String str = TypeSerializer.parseString(123);  // "123"
	 *
	 * // 字符串转换
	 * String str = TypeSerializer.parseString("hello");  // "hello"
	 *
	 * // 自定义对象转换
	 * User user = new User("张三");
	 * String str = TypeSerializer.parseString(user);  // 序列化后的字符串
	 * }</pre>
	 */
	@SuppressWarnings("unchecked")
	public static String parseString(Object object) {
		// 检查对象是否为 null
		if (null == object) {
			return null;
		}

		// 如果对象是基本类型，直接调用 String.valueOf() 转换
		if (primitiveSet.contains(object.getClass())) {
			return String.valueOf(object);
		}

		// 使用 Protobuf 协议序列化复杂对象后再转为字符串
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			// 获取对象的运行时 Schema 并进行序列化
			Schema<Object> schema = (Schema<Object>) RuntimeSchema.getSchema(object.getClass());
			byte[] data = ProtobufIOUtil.toByteArray(object, schema, buffer);
			// 将字节数组转为字符串
			return new String(data);
		} finally {
			// 无论成功或失败，都要清空缓冲区以释放资源
			buffer.clear();
		}
	}


	/**
	 * 将字节数组反序列化为对象
	 * <p>
	 * 使用 Protobuf 协议进行反序列化，支持：
	 * <ul>
	 *   <li>Protobuf 生成的对象：使用反射调用 parseFrom() 方法</li>
	 *   <li>普通 Java 对象：使用 ProtobufIOUtil.mergeFrom() 方法</li>
	 * </ul>
	 * </p>
	 *
	 * @param <T>   泛型类型，表示反序列化后的目标类型
	 * @param bytes 已经被序列化的字节数组，可以为 null
	 *              如果为 null，方法返回 null
	 * @param klass 需要反序列化成的目标类型，不能为 null
	 *              必须有无参构造函数（对于普通 Java 对象）
	 * @return 反序列化后的实例，如果反序列化失败或参数无效则返回 null
	 *
	 * @apiNote 反序列化规则：
	 * <ul>
	 *   <li>Protobuf 对象（GeneratedMessage 的子类）：通过反射调用静态方法 parseFrom(byte[])</li>
	 *   <li>普通 Java 对象：先通过反射创建实例，再使用 ProtobufIOUtil.mergeFrom() 填充数据</li>
	 * </ul>
	 *
	 * @apiNote 示例用法：
	 * <pre>{@code
	 * // 反序列化 Protobuf 对象
	 * Person person = TypeSerializer.fromBytes(bytes, Person.class);
	 *
	 * // 反序列化普通 Java 对象
	 * User user = TypeSerializer.fromBytes(bytes, User.class);
	 * }</pre>
	 *
	 * @throws RuntimeException 如果反序列化过程中发生异常，会记录错误日志并返回 null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromBytes(byte[] bytes, Class<T> klass) {
		// 检查字节数组和目标类型是否为 null
		if (bytes == null || klass == null) {
			return null;
		}

		try {
			// 如果目标类型是 Protobuf 生成的对象，直接使用反射调用 parseFrom() 方法
			// GeneratedMessage 是 Protobuf 生成的所有消息类的基类
			if (GeneratedMessage.class.isAssignableFrom(klass)) {
				T obj = (T) MethodUtils.invokeStaticMethod(klass, "parseFrom", bytes);
				return obj;
			}

			// 对于普通 Java 对象，先通过反射创建实例，再使用 Protobuf 填充数据
			// 1. 通过无参构造函数创建实例
			T object = klass.getDeclaredConstructor().newInstance();
			// 2. 获取对象的运行时 Schema
			Schema<T> schema = RuntimeSchema.getSchema(klass);
			// 3. 使用 ProtobufIOUtil 将字节数组数据合并到对象中
			ProtobufIOUtil.mergeFrom(bytes, object, schema);
			return object;
		} catch (Exception e) {
			// 记录反序列化失败的详细信息
			log.error("反序列化失败，字节数组内容:{}", new String(bytes));
			log.error("反序列化异常类型:{}, 异常信息:{}", e.getClass().getName(), e.getMessage());
		}
		return null;
	}


	/**
	 * 批量反序列化字节数组列表
	 * <p>
	 * 将字节数组列表中的每个元素反序列化为指定类型的对象列表
	 * </p>
	 *
	 * @param <T>      泛型类型，表示反序列化后的目标类型
	 * @param byteList 字节数组列表，可以为 null
	 *                 如果为 null，方法返回 null
	 * @param klass    需要反序列化成的目标类型，不能为 null
	 *                 支持所有可由 parseObject(Class, byte[]) 转换的类型
	 * @return 反序列化后的对象列表，如果参数无效则返回 null
	 *
	 * @apiNote 转换规则：
	 * <ul>
	 *   <li>遍历字节数组列表，对每个字节数组调用 parseObject(Class, byte[]) 进行转换</li>
	 *   <li>转换结果存入 ArrayList 中返回</li>
	 * </ul>
	 *
	 * @apiNote 示例用法：
	 * <pre>{@code
	 * List<byte[]> byteList = Arrays.asList(
	 *     "张三".getBytes(),
	 *     "李四".getBytes()
	 * );
	 * List<String> names = TypeSerializer.fromByteList(byteList, String.class);
	 * // 结果: ["张三", "李四"]
	 *
	 * // 反序列化对象列表
	 * List<byte[]> userBytes = Arrays.asList(
	 *     user1Bytes,
	 *     user2Bytes
	 * );
	 * List<User> users = TypeSerializer.fromByteList(userBytes, User.class);
	 * }</pre>
	 */
	public static <T> List<T> fromByteList(List<byte[]> byteList, Class<T> klass) {
		// 检查字节数组列表和目标类型是否为 null
		if (byteList == null || klass == null) {
			return null;
		}

		// 创建 ArrayList 容器，初始容量为列表大小以提高性能
		List<T> list = new ArrayList<>(byteList.size());

		// 遍历字节数组列表，逐个反序列化
		for (byte[] bs : byteList) {
			// 调用 parseObject 方法进行反序列化
			T object = parseObject(klass, bs);
			list.add(object);
		}

		return list;
	}

	/**
	 * 反序列化字节数组 Map 集合
	 * <p>
	 * 将字节数组作为键值的 Map 转换为指定类型的键值对 Map
	 * </p>
	 *
	 * @param <K>   泛型类型，表示反序列化后的键类型
	 * @param <V>   泛型类型，表示反序列化后的值类型
	 * @param claxx Map 键的目标类型，不能为 null
	 *              支持所有可由 parseObject(Class, byte[]) 转换的类型
	 * @param clazz Map 值的目标类型，不能为 null
	 *              支持所有可由 parseObject(Class, byte[]) 转换的类型
	 * @param data 字节数组作为键值的 Map，可以为 null
	 *              如果为 null，方法返回 null
	 * @return 反序列化后的键值对 Map，如果参数无效则返回 null
	 *
	 * @apiNote 转换规则：
	 * <ul>
	 *   <li>遍历字节数组 Map 的每个键值对</li>
	 *   <li>对键和值分别调用 parseObject(Class, byte[]) 进行转换</li>
	 *   <li>转换结果存入 LinkedHashMap 中保持插入顺序</li>
	 * </ul>
	 *
	 * @apiNote 示例用法：
	 * <pre>{@code
	 * Map<byte[], byte[]> byteMap = new HashMap<>();
	 * byteMap.put("key1".getBytes(), "value1".getBytes());
	 * byteMap.put("key2".getBytes(), "value2".getBytes());
	 *
	 * // 反序列化为 String-String Map
	 * Map<String, String> stringMap = TypeSerializer.fromByteMap(String.class, String.class, byteMap);
	 * // 结果: {"key1": "value1", "key2": "value2"}
	 *
	 * // 反序列化为 Integer-User Map
	 * Map<Integer, User> userMap = TypeSerializer.fromByteMap(Integer.class, User.class, byteMap);
	 * }</pre>
	 */
	public static <K, V> Map<K, V> fromByteMap(Class<K> claxx, Class<V> clazz, Map<byte[], byte[]> data) {
		// 检查字节数组 Map 和目标类型是否为 null
		if (data == null || clazz == null || claxx == null) {
			return null;
		}

		// 使用 LinkedHashMap 保持插入顺序
		Map<K, V> map = new LinkedHashMap<>();

		// 遍历字节数组 Map 的每个键值对
		for (Map.Entry<byte[], byte[]> it : data.entrySet()) {
			// 反序列化键和值
			K key = parseObject(claxx, it.getKey());
			V value = parseObject(clazz, it.getValue());
			// 将转换后的键值对存入 Map
			map.put(key, value);
		}

		return map;
	}

	/**
	 * 将集合转换为二维字节数组
	 * <p>
	 * 将集合中的每个元素拼接指定前缀后，转换为字节数组，最终返回二维字节数组
	 * </p>
	 *
	 * @param <V>    泛型类型，表示集合中元素的类型
	 * @param prefix 每个元素转换时要拼接的前缀字符串，可以为 null 或空字符串
	 *               如果为 null，则不会拼接任何前缀
	 * @param set    要转换的集合，可以为 null
	 *               如果为 null，方法返回 null
	 * @return 二维字节数组，每个元素都是 (prefix + 元素).getBytes() 的结果
	 *         如果参数无效则返回 null
	 *
	 * @apiNote 转换规则：
	 * <ul>
	 *   <li>遍历集合中的每个元素</li>
	 *   <li>将元素与 prefix 拼接（prefix + element）</li>
	 *   <li>将拼接后的字符串转为字节数组（使用默认编码）</li>
	 *   <li>所有字节数组组成二维数组返回</li>
	 * </ul>
	 *
	 * @apiNote 示例用法：
	 * <pre>{@code
	 * // 转换 List
	 * List<String> names = Arrays.asList("张三", "李四", "王五");
	 * byte[][] bytes = TypeSerializer.tranformStrTobyte("user:", names);
	 * // 结果: [
	 * //   "user:张三".getBytes(),
	 * //   "user:李四".getBytes(),
	 * //   "user:王五".getBytes()
	 * // ]
	 *
	 * // 不拼接前缀
	 * byte[][] bytes = TypeSerializer.tranformStrTobyte(null, names);
	 * // 结果: ["张三".getBytes(), "李四".getBytes(), "王五".getBytes()]
	 *
	 * // 转换 Set
	 * Set<Integer> numbers = new HashSet<>(Arrays.asList(1, 2, 3));
	 * byte[][] bytes = TypeSerializer.tranformStrTobyte("num:", numbers);
	 * // 结果: ["num:1".getBytes(), "num:2".getBytes(), "num:3".getBytes()]
	 * }</pre>
	 */
	public static <V> byte[][] tranFormStrToByte(String prefix, Collection<V> set) {
		// 检查集合是否为 null
		if (set == null) {
			return null;
		}

		// 创建二维字节数组，大小与集合大小一致
		byte[][] arr = new byte[set.size()][];

		// 遍历集合，将每个元素转为字节数组
		int i = 0;
		for (V v : set) {
			// 拼接前缀和元素，然后转为字节数组
			// 如果 prefix 为 null，字符串拼接会自动处理
			String str = (prefix == null ? "" : prefix) + v;
			arr[i] = str.getBytes();
			i++;
		}

		return arr;
	}
}
