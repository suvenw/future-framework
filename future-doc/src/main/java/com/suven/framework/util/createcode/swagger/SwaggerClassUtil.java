package com.suven.framework.util.createcode.swagger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author 作者 : suven.wang
 * CreateDate 创建时间: 2022-01-13
 * @WeeK 星期: 星期四
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  类型属性验证类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
public class SwaggerClassUtil {

    private static Logger logger =  LoggerFactory.getLogger(SwaggerClassUtil.class);
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
            logger.warn("entityClazz is map Object  pass return entityClazz[{}]..........,",fieldType);
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
        if (SwaggerClassUtil.isTypeClass(field)) { //过滤 排除 静态,或final类型
            return true;
        } //过滤
        if(SwaggerClassUtil.isMapOrJson(fieldType)){
            logger.warn("entityClazz is map Object  pass return fieldType[{}]..........,",fieldType);
            return true;
        } //过滤 类自己本身，如果出现迭归类，会出现死循环
        if(SwaggerClassUtil.isOneselfClass(fieldType, entityClazz)){
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
            logger.warn(" class isOneselfClass is contains    fieldType:[{}] and entityClazz:[{}]..........,",fieldType,entityClazz);
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
            Class<?> genericClazz = SwaggerClassUtil.getRawType(genericType);
            if(genericClazz == null){
                return null;
            }
            //过滤 类自己本身，如果出现迭归类，会出现死循环
            if(SwaggerClassUtil.isOneselfClass(genericClazz,entityClazz)){
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
     * 判断类属性 是objet 对象或聚合,如果是则添加到compoundList聚合中
     * @param entityClazz
     * @param field
     * @param compoundList
     * @return
     */
    public static boolean converterParameterizedType(Class entityClazz,Field field,Map<Class,String> compoundList,String paramType){
        Class fieldType = field.getType();
        if (isPrimitiveType(fieldType) || compoundList == null ) {
            return false;
        }
        if (!isIterable(fieldType)) {
            compoundList.put(fieldType,paramType);
            return true;
        }
        Type genericType = field.getGenericType();
        if (genericType != null && genericType instanceof ParameterizedType) {
            //得到泛型里的class类型对象。
            Class<?> genericClazz = getRawType(genericType);
            if(genericClazz == null){
                return false;
            }
            //过滤 类自己本身，如果出现迭归类，会出现死循环
            if(isOneselfClass(genericClazz,entityClazz)){
                return false;
            }
            compoundList.put(genericClazz,paramType);
            return true;
        }
        return false;
    }


//    /**
//     * 排除 静态,或final类型
//     * @param field
//     * @return
//     */
//    public static boolean isTypeClass(Field field) {
//        boolean isStatic = Modifier.isStatic(field.getModifiers());
//        boolean isFinal = Modifier.isFinal(field.getModifiers());
////			boolean isTyep = RedisSetEnum.isContains(property.getType().getSimpleName());
//        return isStatic || isFinal;
//    }
//    /**属性类型**/
//    public static boolean isPrimitiveType(Class<?> fieldType) {
//        return ClassUtils.isPrimitiveOrWrapper(fieldType)
//                || fieldType == String.class || fieldType == Date.class || fieldType == byte[].class ;
//    }
//
//    //排除map类
//    private static  boolean isMapOrJson(Class<?> fieldType){
//        boolean isLog = isMapOrJsonClass(fieldType);
//        if(isLog){
//            logger.warn("entityClazz is map Object  pass return entityClazz[{}]..........,",fieldType);
//        }
//        return isLog;
//    }
//    public static  boolean isMapOrJsonClass(Class<?> fieldType){
//        if(fieldType.isAssignableFrom(Map.class)){
//            return true;
//        }
//
//        Class<?> interfaces[] = fieldType.getInterfaces();
//        if(null != interfaces && Arrays.asList(interfaces).contains(Map.class)){
//            return true;
//        }
//        if("JSONOBJECT".equals(fieldType.getSimpleName().toUpperCase())){
//            return true;
//        }
//        return false;
//    }
//
//    /** 排队类本身，解决迭归类死循环问题 **/
//    public static  boolean isOneselfClass(Class<?> fieldType,Class<?> entityClazz){
//        boolean isOwnerClazz = fieldType.equals(entityClazz) || entityClazz.getSimpleName().equals(fieldType.getSimpleName());
//        if(isOwnerClazz){
//            logger.warn(" class isOneselfClass is contains    fieldType:[{}] and entityClazz:[{}]..........,",fieldType,entityClazz);
//        }
//        return isOwnerClazz;
//    }
//
//
//    /**
//     * 数组或集合类型
//     * @param clazz
//     * @return
//     */
//    public static boolean isIterable(Class<?> clazz) {
//        return (clazz.isArray() || Collection.class.isAssignableFrom(clazz))
//                && !byte[].class.isAssignableFrom(clazz);
//    }
}
