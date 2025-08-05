//package com.suven.framework.util.json;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.ImmutableSet;
//import com.google.protobuf.Descriptors;
//import com.google.protobuf.GeneratedMessage;
//import io.protostuff.LinkedBuffer;
//import io.protostuff.ProtobufIOUtil;
//import io.protostuff.Schema;
//import io.protostuff.runtime.RuntimeSchema;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.reflect.MethodUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.Serializable;
//import java.lang.reflect.Method;
//import java.nio.charset.Charset;
//import java.util.*;
//
//import static com.suven.framework.util.json.JsonUtils.parseObject;
//import static com.suven.framework.util.json.JsonUtils.toJson;
//
//
///**
// * Title: SerializableUtil.java
// * @author Joven.wang
// * date   2019-10-18 12:35:25
// * @version V1.0
// *  <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) Java 序列化工具类, 目前使用的序列化使用的Kryo
// *  * why kryo:<br>
// *  * 1. 性能
// *  * 2. 生成体积小
// * Copyright: (c) 2018 gc by https://www.suven.top
// *
// */
//public class SerializableUtil {
//
//    private static Logger log = LoggerFactory.getLogger(SerializableUtil.class);
//
//    private static Charset charset = Charset.forName("UTF-8");
//	private static ImmutableSet<? extends Class<? extends Serializable>> primitiveSet;
//
//	static {
//		// 还是直接的HashSet比较快..
//		primitiveSet = ImmutableSet.of(int.class, Integer.class, long.class,Long.class,
//				double.class, Double.class, float.class,Float.class, byte.class,Byte.class,
//				short.class, Short.class, boolean.class,Boolean.class,String.class,Number.class);
//	}
//
//    /**
//     * 对象序列化成字节
//     *
//     * @param object java对象
//     * @return 序列化后的数组
//     */
//    @SuppressWarnings("unchecked")
//	public static byte[] toBytes(Object object) {
//    	if (null == object){
//    		return null;
//		}
//    	if(primitiveSet.contains(object.getClass())){
//    		return String.valueOf(object).getBytes();
//    	}
//        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
//        try {
//            return ProtobufIOUtil.toByteArray(object, (Schema<Object>) RuntimeSchema.getSchema(object.getClass()), buffer);
//        } finally {
//            buffer.clear();
//        }
//    }
//
//
//
//	/**
//     * 反序列化
//     *
//     * @param bytes 已经被序列化的数组
//     * @param klass 需要反序列化成的类型
//     * @param <T>   不解释
//     * @return 反序列化后的实例
//     */
//    @SuppressWarnings("unchecked")
//	public static <T> T fromBytes(byte[] bytes, Class<T> klass) {
//		if(bytes == null || klass == null){
//			return null;
//		}
//        try {
//        	//如果klass是pb对象直接使用反映实现
//        	if(GeneratedMessage.class.isAssignableFrom(klass)){
//        		T obj = (T) MethodUtils.invokeStaticMethod(klass, "parseFrom", bytes);
//        		return obj;
//        	}
//        	//将pb-byte[] 转换为java object
//            T object = klass.newInstance();
//			Schema schema = RuntimeSchema.getSchema(klass);
//            ProtobufIOUtil.mergeFrom(bytes, object, schema);
//            return object;
//        } catch (Exception e) {
//            log.error("byteStr:{}", new String(bytes));
//            log.error("反序列化失败:{}", e);
//        }
//        return null;
//    }
//
//
//    /**
//     * 反序列化byte数组
//     * @param byteList
//     * @param klass
//     * @return
//     */
//    public static <T> List<T> fromByteList(List<byte[]> byteList, Class<T> klass) {
//		if(byteList == null || klass == null){
//			return null;
//		}
//    	List<T> list = new ArrayList<>(byteList.size());
//    	for (byte[] bs : byteList) {
//    		list.add(parseValue(klass, bs));
//		}
//        return list;
//    }
//
//    /**
//     * 反序列化byte Map集合;
//     * @param claxx
//     * @param clazz
//     * @param datas
//     * @return
//     */
//    public static <K,V> Map<K, V> fromByteMap(Class<K> claxx ,Class<V> clazz,Map<byte[] ,byte[]> datas) {
//		if(datas == null || clazz == null || claxx == null){
//			return null;
//		}
//    	Map<K, V> map = new LinkedHashMap<>();
//    	for(Map.Entry<byte[], byte[]> it: datas.entrySet()) {
//    		map.put(parseValue(claxx, it.getKey()), parseValue(clazz ,it.getValue()));
//    	}
//    	return map;
//    }
//
//
//    /**
//	 * 将数据类型转换成相对应的对像,或字符类型;
//	 * @param str 要转换成byte[] 数据组;
//	 * @param clazzType 被转成的数据类型;
//	 * @return
//	 */
//	public static <T> T parseValue( Class<T> clazzType, String str) { //boolean.class, pojo.class ,String.class
//		Object value = null;
//		if (clazzType == null ) {
//			return null;
//		}
//		if(str == null || "".equals(str)){
//			str = "0";
//		}
//	    if (short.class == clazzType || Short.class == clazzType) {
//			value = Short.parseShort(str);
//		} else if (int.class == clazzType || Integer.class == clazzType) {
//			value = Integer.parseInt(str);
//		} else if (long.class == clazzType || Long.class == clazzType) {
//			value = Long.parseLong(str);
//		} else if (float.class == clazzType || Float.class == clazzType) {
//			value = Float.parseFloat(str);
//		} else if (double.class == clazzType || Double.class == clazzType
//				|| Number.class == clazzType) {
//			value = Double.parseDouble(str);
//		}
//		return (T)value;
//	}
//
//
//	/**
//	 * 将数据类型转换成相对应的对像,或字符类型;
//	 * @param data 要转换成byte[] 数据组;
//	 * @param clazzType 被转成的数据类型;
//	 * @return
//	 */
//	public static <T> T parseValue( Class<T> clazzType, byte[] data) { //boolean.class, pojo.class ,String.class
//
//		boolean wasNullCheck = false;
//		Object value = null;
//		if (clazzType == null) {
//			return null;
//		}
//		if (data == null) {
//			return null;
//		}
//		if (byte[].class.equals(clazzType)) {
//			return (T) data;
//		}else if(!primitiveSet.contains(clazzType)){
//			return SerializableUtil.fromBytes(data, clazzType);
//		} else if (byte.class == clazzType || Byte.class == clazzType) {
//			value = data[0];
//			wasNullCheck = true;
//		} else if (boolean.class == clazzType || Boolean.class == clazzType) {
//			value = data[0] == 1;
//			wasNullCheck = true;
//		}else if (String.class == clazzType) {
//			value = new String(data,charset);
//			wasNullCheck = true;
//		}else{
//			String str = new String(data,charset);
//			value = parseValue(clazzType, str);
//			wasNullCheck = true;
//		}
//		if (wasNullCheck && value != null) {
//			return (T) value;
//		}
//		return null;
//	}
//	/**
//	 * 将list转化为数组
//	 * @param set
//	 * @return
//	 */
//	public static <V> byte[][] tranformStrTobyte(String prefix,Collection<V> set){
//		if(set == null ){
//			return null;
//		}
//		byte[][] arr = new byte[set.size()][];
//		int i = 0 ;
//		for (V v : set) {
//			arr[i]=(prefix + v).getBytes();
//			i++;
//		}
//		return arr;
//	}
//
//
//	public static <T extends GeneratedMessage> T jsonToPB(String json, Class<T> clazz) {
//		JSONObject jsonObject = parseObject(json, JSONObject.class);
//		return jsonToPB(jsonObject, clazz);
//	}
//
//	public static <T extends GeneratedMessage> T jsonToPB(JSONObject jsonObject, Class<T> clazz) {
//		GeneratedMessage.Builder builder;
//		try {
//			builder = (GeneratedMessage.Builder) MethodUtils.invokeStaticMethod(clazz, "newBuilder");
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//
//		builder = jsonToPBBuilder(jsonObject, builder.getClass());
//		return (T) builder.build();
//	}
//
//	public static <T extends GeneratedMessage.Builder> T jsonToPBBuilder(JSONObject jsonObject, Class<T> clazz) {
//
//		GeneratedMessage.Builder builder = genBuilder(clazz);
//		Set<String> keySet = collectKey(jsonObject);
//
//		List<Descriptors.FieldDescriptor> fields =
//				builder.getDescriptorForType().getFields();
//		for (Descriptors.FieldDescriptor field : fields) {
//			if (!keySet.contains(field.getName())) continue;
//
//			String fieldName = field.getName();
//			String capName = StringUtils.capitalize(fieldName);
//			try {
//				if (field.isRepeated()) {
//
//					String methodName = "add" + capName;
//					JSONArray array = jsonObject.getJSONArray(fieldName);
//					Class c = findFieldType(builder, methodName);
//
//					if (field.getType() == Descriptors.FieldDescriptor.Type.MESSAGE) {
//						signPBArray(builder, methodName, array, c);
//					} else {
//						signPrimitiveArray(builder, methodName, array, c);
//					}
//				} else {
//					String methodName = "set" + capName;
//					if (field.getType() == Descriptors.FieldDescriptor.Type.MESSAGE) {
//						singPB(jsonObject, builder, fieldName, methodName);
//					} else {
//						signPrimitive(jsonObject, builder, fieldName, methodName);
//					}
//				}
//			} catch (Exception e) {
//				log.info("PB格式和json类型不一致 pb字段: " + field.getContainingType().getName() + "." + fieldName
//						+ " json: "
//						+ toJson(jsonObject.getString(fieldName)).replaceAll("\"", "'").replaceAll("\\\\'", "\""));
//			}
//		}
//		return (T) builder;
//	}
//
//	public static Object invokeMethod(Object o, String methodName, Object... args) {
//		try {
//			if (o instanceof Class) {
//				return MethodUtils.invokeStaticMethod((Class)o, methodName, args);
//			}
//			return MethodUtils.invokeMethod(o, methodName, args);
//		} catch (Exception e) {
//			log.warn("反射调用(一般是类型转换)出错, 一般可以忽略 detail: target:{} method:{} arg:{} argType:{}",
//					o, methodName, args[0], args[0].getClass());
//			return null;
//		}
//	}
//
//	private static void signPrimitive(JSONObject jsonObject,
//									  GeneratedMessage.Builder builder,
//									  String fieldName,
//									  String methodName) {
//		Class type = findFieldType(builder, methodName);
//		Object retValue;
//		if (type == int.class) {
//			retValue = jsonObject.getInteger(fieldName);
//		} else if (type == long.class) {
//			retValue = jsonObject.getLong(fieldName);
//		} else if (type == String.class) {
//			retValue = jsonObject.getString(fieldName);
//		} else if (type == boolean.class) {
//			try {
//				retValue = jsonObject.getBoolean(fieldName);
//			} catch (JSONException e) {
//				int intValue = jsonObject.getInteger(fieldName);
//				if (intValue == 1) {
//					retValue = true;
//				} else if (intValue == 0) {
//					retValue = false;
//				} else throw new JSONException(jsonObject.getString(fieldName) + " 不能转为boolean类型");
//			}
//		} else if (type == double.class) {
//			retValue = jsonObject.getDouble(fieldName);
//		} else if (type == float.class) {
//			retValue = Float.valueOf(jsonObject.getString(fieldName));
//		} else {
//			return;
//		}
//		invokeMethod(builder, methodName, retValue);
//	}
//
//	private static void singPB(JSONObject jsonObject,
//							   GeneratedMessage.Builder builder,
//							   String fieldName, String methodName) {
//		Class c = findFieldType(builder, methodName);
//		Object value = jsonToPB(jsonObject.getJSONObject(fieldName), c);
//		invokeMethod(builder, methodName, value);
//	}
//
//	private static void signPrimitiveArray(GeneratedMessage.Builder builder,
//										   String methodName,
//										   JSONArray array, Class c) {
//		for (int i = 0; i < array.size(); i++) {
//			Object value = array.get(i);
//			value = invokeMethod(c, "valueOf", value);
//			invokeMethod(builder, methodName, value);
//		}
//	}
//
//	private static void signPBArray(GeneratedMessage.Builder builder,
//									String methodName,
//									JSONArray array, Class c) {
//		for (int i = 0; i < array.size(); i++) {
//			Object pb = jsonToPB(array.getJSONObject(i), c);
//			invokeMethod(builder, methodName, pb);
////            MethodUtils.invokeMethod(builder, methodName, pb);
//		}
//	}
//
//	public static Class findFieldType(GeneratedMessage.Builder builder, String methodName) {
//		Method[] methods = builder.getClass().getMethods();
//		for (Method method : methods) {
//			if (method.getName().equals(methodName)
//					&& method.getParameterTypes().length == 1) {
//				Class c = method.getParameterTypes()[0];
//				if (GeneratedMessage.class.isAssignableFrom(c)) {
//					return c;
//				}
//				if (primitiveSet.contains(c)) {
//					return c;
//				}
//			}
//		}
//		return null;
//	}
//
//	private static Set<String> collectKey(JSONObject jsonObject) {
//		Set<String> set = new HashSet<>();
//		Iterator<String> iter = jsonObject.keySet().iterator();
//		int count = 0;
//		while (iter.hasNext()) {
//			set.add(iter.next());
//			if(count++ > 1000){
//				count = -9000;
//				log.warn("[JSON TOLONG], large than {}, json:{}", count, jsonObject.toString());
//			}
//		}
//		return set;
//	}
//
//	private static <T extends GeneratedMessage.Builder> GeneratedMessage.Builder genBuilder(Class<T> clazz) {
//		GeneratedMessage.Builder builder;
//		try {
//			String className = clazz.getName();
//			String messageClassName = className.substring(0, className.length() - "$Builder".length());
//			Class klass = Class.forName(messageClassName);
//			builder = (GeneratedMessage.Builder) MethodUtils.invokeStaticMethod(klass, "newBuilder");
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		return builder;
//	}
//
//
//	/**
//	 * 反序列化
//	 *
//	 * @param bytes 已经被序列化的数组
//	 * @param klass 需要反序列化成的类型
//	 * @param <T>   不解释
//	 * @return 反序列化后的实例
//	 */
//	public static <T> T fromBytesToPb(byte[] bytes, Class<T> klass) {
//		try {
//			T obj = (T) MethodUtils.invokeStaticMethod(klass, "parseFrom", bytes);
//			return obj;
//		} catch (Exception e) {
//			log.warn("反序列化失败:{}, byteStr:{}", e.getMessage(), new String(bytes));
//		}
//		return null;
//	}
//
//	public static void main(String[] args) {
//		byte[] by = SerializableUtil.toBytes(39089073);
//		System.out.println(by);
//		System.out.println( new String(by));
//	}
//}
