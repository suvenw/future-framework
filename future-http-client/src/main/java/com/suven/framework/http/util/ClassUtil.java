/*
 * Copyright (c) 2019-2029, xkcoding & Yangkai.Shen & 沈扬凯 (237497819@qq.com & xkcoding.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.suven.framework.http.util;


import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author 作者 : suven.wang
 * CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明): http 类判断实现逻辑类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

public class ClassUtil {

	private static ImmutableSet primitiveSet =
			ImmutableSet.of( byte.class,Byte.class,char.class,short.class, Short.class,
					int.class, Integer.class, long.class,Long.class,
					float.class,Float.class,double.class, Double.class,
				boolean.class,Boolean.class,String.class,Number.class);

	private static Charset charset = Charset.forName("UTF-8");

	/**
	 * 确定class是否可以被加载
	 *
	 * @param className   完整类名
	 * @param classLoader 类加载
	 * @return {boolean}
	 */
	public static boolean isPresent(String className, ClassLoader classLoader) {
		try {
			Class.forName(className, true, classLoader);
			return true;
		} catch (Throwable ex) {
			return false;
		}
	}

	/**
	 * 排除 静态,或final类型
	 * @param field
	 * @return
	 */
	public static boolean isTypeClass(Field field) {
		boolean isStatic = Modifier.isStatic(field.getModifiers());
		boolean isFinal = Modifier.isFinal(field.getModifiers());
//			boolean isTyep = RedisSetEnum.isContains(property.getType().getSimpleName());
		return isStatic || isFinal;
	}
	/**属性类型**/
	public static boolean isPrimitiveType(Class<?> fieldType) {
		return ClassUtils.isPrimitiveOrWrapper(fieldType)
				|| fieldType == String.class || fieldType == Date.class || fieldType == byte[].class ;
	}

	//排除map类
	public static  boolean isMapOrJson(Class<?> fieldType){
		boolean isLog = isMapOrJsonClass(fieldType);
		if(isLog){
		}
		return isLog;
	}
	public static  boolean isMapOrJsonClass(Class<?> fieldType){
		if(fieldType.isAssignableFrom(Map.class)){
			return true;
		}

		Class<?> interfaces[] = fieldType.getInterfaces();
		if(null != interfaces && Arrays.asList(interfaces).contains(Map.class)){
			return true;
		}
		if("JSONOBJECT".equals(fieldType.getSimpleName().toUpperCase())){
			return true;
		}
		return false;
	}


	/**
	 * 排除 过滤 静态,或final类型,属性是类自己本身的
	 * **/
	public static boolean excludeFieldOrClass(Class entityClazz, Field field) {
		// 如果是类或集合,从新回调;
		Class<?> fieldType = field.getType();
		if (isTypeClass(field)) { //过滤 排除 静态,或final类型
			return true;
		} //过滤
		if(isMapOrJson(fieldType)){
			return true;
		} //过滤 类自己本身，如果出现迭归类，会出现死循环
		if(isOneselfClass(fieldType, entityClazz)){
			return true;
		}
		return false;
	}

	/** 排队类本身，解决迭归类死循环问题 **/
	public static  boolean isOneselfClass(Class<?> fieldType,Class<?> entityClazz){
		if(entityClazz == null){
			return true;
		}
		boolean isOwnerClazz = fieldType.equals(entityClazz) || entityClazz.getSimpleName().equals(fieldType.getSimpleName());
		if(isOwnerClazz){
		}
		return isOwnerClazz;
	}

	public static  Class getGenericTypeToCompound(Class entityClazz ,Field field){
		Type genericType = field.getGenericType();
		if(genericType == null){
			return null;
		}
		if ( genericType instanceof ParameterizedType) {
			//得到泛型里的class类型对象。
			Class<?> genericClazz = getRawType(genericType);
			if(genericClazz == null){
				return null;
			}
			//过滤 类自己本身，如果出现迭归类，会出现死循环
			if(isOneselfClass(genericClazz,entityClazz)){
				return null;
			}
			return genericClazz;
		}
		return null;
	}

	/**
	 * 数组类型,且不是字节数组类型
	 * @param clazz
	 * @return
	 */
	public static boolean isIterable(Class<?> clazz) {
		return (clazz.isArray() || Collection.class.isAssignableFrom(clazz))
				&& !byte[].class.isAssignableFrom(clazz);
	}


	// type不能直接实例化对象，通过type获取class的类型，然后实例化对象
	public static Class<?> getRawType(Type type) {
		if (type instanceof Class) {
			return (Class) type;
		} else if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type[] rawType = parameterizedType.getActualTypeArguments();
//            Type rawType = parameterizedType.getRawType();
			if(rawType[0] instanceof TypeVariable){
				return null;
			}
			return (Class) rawType[0];
		} else if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type).getGenericComponentType();
			return Array.newInstance(getRawType(componentType), 0).getClass();
		} else if (type instanceof TypeVariable) {
			return Object.class;
		} else if (type instanceof WildcardType) {
			return getRawType(((WildcardType) type).getUpperBounds()[0]);
		} else {
			String className = type == null ? "null" : type.getClass().getName();
			throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
		}
	}

	/**
	 * 将数据类型转换成相对应的对像,或字符类型;
	 * @param data 要转换成 String字符串;
	 * @return
	 */
	public static String  parseValueString( Object data) { //boolean.class, pojo.class ,String.class
		if (data == null) {
			return "";
		}
		Class clazzType = data.getClass();
		if (byte[].class.equals(data.getClass())) {
			String str = new String((byte[])data,charset);
			return str;
		}else if(primitiveSet.contains(clazzType)){
			return String.valueOf(data);
		}
		return "";
	}

}
