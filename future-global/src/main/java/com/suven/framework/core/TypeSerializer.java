package com.suven.framework.core;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableSet;
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
public class TypeSerializer {

	private static Logger log = LoggerFactory.getLogger(TypeSerializer.class);
    private static Charset charset = Charset.forName("UTF-8");
	private static ImmutableSet<? extends Class<? extends Serializable>> primitiveSet;

	static {
		// 还是直接的HashSet比较快..
		primitiveSet = ImmutableSet.of(int.class, Integer.class, long.class,Long.class,
				double.class, Double.class, float.class,Float.class, byte.class,Byte.class,
				short.class, Short.class, boolean.class,Boolean.class,String.class,Number.class);
	}

        /**
	 * 将数据类型转换成相对应的对像,或字符类型;
	 * @param str 要转换成byte[] 数据组;
	 * @param clazzType 被转成的数据类型;
	 * @return
	 */
	public static <T> T parseObject( Class<T> clazzType, String str) { //boolean.class, pojo.class ,String.class
		Object value = null;
		if (clazzType == null ) {
			return null;
		}
		if (String.class == clazzType) {
			value = str;
			return (T)value;
		} else if(!primitiveSet.contains(clazzType)){
			return JSON.parseObject(str, clazzType);
		}
		if(str == null || "".equals(str)){
			str = "0";
		} else if (boolean.class == clazzType || Boolean.class == clazzType) {
            value = Boolean.valueOf(str);
        }else if (short.class == clazzType || Short.class == clazzType) {
			value = Short.parseShort(str);
		} else if (int.class == clazzType || Integer.class == clazzType) {
			value = Integer.parseInt(str);
		} else if (long.class == clazzType || Long.class == clazzType) {
			value = Long.parseLong(str);
		} else if (float.class == clazzType || Float.class == clazzType) {
			value = Float.parseFloat(str);
		} else if (double.class == clazzType || Double.class == clazzType
				|| Number.class == clazzType) {
			value = Double.parseDouble(str);
		}
			return (T)value;

	}

	/**
	 * 将数据类型转换成相对应的对像,或字符类型;
	 * @param data 要转换成byte[] 数据组;
	 * @param clazzType 被转成的数据类型;
	 * @return
	 */
	public static <T> T parseObject( Class<T> clazzType, byte[] data) { //boolean.class, pojo.class ,String.class

		boolean wasNullCheck = false;
		Object value = null;
		if (clazzType == null) {
			return null;
		}
		if (data == null) {
			return null;
		}
		if (byte[].class.equals(clazzType)) {
			return (T) data;
		} else if (byte.class == clazzType || Byte.class == clazzType) {
			value = data[0];
			return (T)value;
		} else if (String.class == clazzType) {
			value = new String(data,charset);
            return (T)value;
        }else {
			return  parseObject(clazzType,new String(data));
		}
	}

	/**
	 * 对象序列化成字节
	 *
	 * @param object java对象
	 * @return 序列化后的数组
	 */
	@SuppressWarnings("unchecked")
	public static byte[] parseBytes(Object object) {
		if (null == object){
			return null;
		}
		if(primitiveSet.contains(object.getClass())){
			return String.valueOf(object).getBytes();
		}
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			return ProtobufIOUtil.toByteArray(object, (Schema<Object>) RuntimeSchema.getSchema(object.getClass()), buffer);
		} finally {
			buffer.clear();
		}
	}

	@SuppressWarnings("unchecked")
	public static String parseString(Object object) {
		if (null == object){
			return null;
		}
		if(primitiveSet.contains(object.getClass())){
			return String.valueOf(object);
		}
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			byte[] data =  ProtobufIOUtil.toByteArray(object, (Schema<Object>)
					RuntimeSchema.getSchema(object.getClass()), buffer);
			return new String(data);
		} finally {
			buffer.clear();
		}
	}


	/**
	 * 反序列化
	 *
	 * @param bytes 已经被序列化的数组
	 * @param klass 需要反序列化成的类型
	 * @param <T>   不解释
	 * @return 反序列化后的实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromBytes(byte[] bytes, Class<T> klass) {
		if(bytes == null || klass == null){
			return null;
		}
		try {
			//如果klass是pb对象直接使用反映实现
			if(GeneratedMessage.class.isAssignableFrom(klass)){
				T obj = (T) MethodUtils.invokeStaticMethod(klass, "parseFrom", bytes);
				return obj;
			}
			//将pb-byte[] 转换为java object
			T object = klass.newInstance();
			Schema schema = RuntimeSchema.getSchema(klass);
			ProtobufIOUtil.mergeFrom(bytes, object, schema);
			return object;
		} catch (Exception e) {
			log.error("byteStr:{}", new String(bytes));
			log.error("反序列化失败:{}", e);
		}
		return null;
	}


	/**
	 * 反序列化byte数组
	 * @param byteList
	 * @param klass
	 * @return
	 */
	public static <T> List<T> fromByteList(List<byte[]> byteList, Class<T> klass) {
		if(byteList == null || klass == null){
			return null;
		}
		List<T> list = new ArrayList<>(byteList.size());
		for (byte[] bs : byteList) {
			list.add(parseObject(klass, bs));
		}
		return list;
	}

	/**
	 * 反序列化byte Map集合;
	 * @param claxx
	 * @param clazz
	 * @param datas
	 * @return
	 */
	public static <K,V> Map<K, V> fromByteMap(Class<K> claxx , Class<V> clazz, Map<byte[] ,byte[]> datas) {
		if(datas == null || clazz == null || claxx == null){
			return null;
		}
		Map<K, V> map = new LinkedHashMap<>();
		for(Map.Entry<byte[], byte[]> it: datas.entrySet()) {
			map.put(parseObject(claxx, it.getKey()), parseObject(clazz ,it.getValue()));
		}
		return map;
	}

	/**
	 * 将list转化为数组
	 * @param set
	 * @return
	 */
	public static <V> byte[][] tranformStrTobyte(String prefix, Collection<V> set){
		if(set == null ){
			return null;
		}
		byte[][] arr = new byte[set.size()][];
		int i = 0 ;
		for (V v : set) {
			arr[i]=(prefix + v).getBytes();
			i++;
		}
		return arr;
	}

}
