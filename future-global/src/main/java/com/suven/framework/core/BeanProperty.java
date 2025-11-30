package com.suven.framework.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Java Bean属性操作工具类
 * 用于动态判断和调用getter/setter方法
 */
public class BeanProperty {
    
    /**
     * 判断类是否有指定字段的getter方法
     * @param clazz 目标类
     * @param fieldName 字段名
     * @return 是否存在getter方法
     */
    public static boolean hasGetter(Class<?> clazz, String fieldName) {
        return getGetterMethod(clazz, fieldName) != null;
    }
    
    /**
     * 判断类是否有指定字段的setter方法
     * @param clazz 目标类
     * @param fieldName 字段名
     * @param paramType 参数类型
     * @return 是否存在setter方法
     */
    public static boolean hasSetter(Class<?> clazz, String fieldName, Class<?> paramType) {
        return getSetterMethod(clazz, fieldName, paramType) != null;
    }
    
    /**
     * 获取getter方法
     * @param clazz 目标类
     * @param fieldName 字段名
     * @return Method对象，如果不存在返回null
     */
    public static Method getGetterMethod(Class<?> clazz, String fieldName) {
        if (clazz == null || fieldName == null || fieldName.trim().isEmpty()) {
            return null;
        }
        
        String getterName = "get" + capitalize(fieldName);
        String booleanGetterName = "is" + capitalize(fieldName);
        
        try {
            // 尝试获取标准的getter方法
            return clazz.getMethod(getterName);
        } catch (NoSuchMethodException e1) {
            try {
                // 对于boolean类型，尝试is开头的方法
                return clazz.getMethod(booleanGetterName);
            } catch (NoSuchMethodException e2) {
                return null;
            }
        }
    }
    
    /**
     * 获取setter方法
     * @param clazz 目标类
     * @param fieldName 字段名
     * @param paramType 参数类型
     * @return Method对象，如果不存在返回null
     */
    public static Method getSetterMethod(Class<?> clazz, String fieldName, Class<?> paramType) {
        if (clazz == null || fieldName == null || fieldName.trim().isEmpty()) {
            return null;
        }
        
        String setterName = "set" + capitalize(fieldName);
        
        try {
            return clazz.getMethod(setterName, paramType);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
    
    /**
     * 设置对象属性值
     * @param target 目标对象
     * @param fieldName 字段名
     * @param value 要设置的值
     * @return 是否设置成功
     */
    public static boolean setProperty(Object target, String fieldName, Object value) {
        if (target == null || fieldName == null || fieldName.trim().isEmpty()) {
            return false;
        }
        
        try {
            Class<?> clazz = target.getClass();
            Field field = getField(clazz, fieldName);
            if (field == null) {
                return false;
            }
            
            Class<?> fieldType = field.getType();
            Method setter = getSetterMethod(clazz, fieldName, fieldType);
            
            if (setter != null) {
                // 类型转换
                Object convertedValue = convertType(value, fieldType);
                setter.invoke(target, convertedValue);
                return true;
            }
            
            return false;
        } catch (Exception e) {
            System.err.println("设置属性失败: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 获取对象属性值
     * @param target 目标对象
     * @param fieldName 字段名
     * @return 属性值，如果获取失败返回null
     */
    public static Object getProperty(Object target, String fieldName) {
        if (target == null || fieldName == null || fieldName.trim().isEmpty()) {
            return null;
        }
        
        try {
            Method getter = getGetterMethod(target.getClass(), fieldName);
            if (getter != null) {
                return getter.invoke(target);
            }
            return null;
        } catch (Exception e) {
            System.err.println("获取属性失败: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 批量设置属性值
     * @param target 目标对象
     * @param properties 属性名-值映射
     * @return 成功设置的属性数量
     */
    public static int setProperties(Object target, Map<String, Object> properties) {
        if (target == null || properties == null || properties.isEmpty()) {
            return 0;
        }
        
        int successCount = 0;
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            if (setProperty(target, entry.getKey(), entry.getValue())) {
                successCount++;
            }
        }
        return successCount;
    }
    
    /**
     * 获取对象的所有属性值
     * @param target 目标对象
     * @return 属性名-值映射
     */
    public static Map<String, Object> getAllProperties(Object target) {
        Map<String, Object> result = new HashMap<>();
        if (target == null) {
            return result;
        }
        
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Object value = getProperty(target, fieldName);
            if (value != null) {
                result.put(fieldName, value);
            }
        }
        return result;
    }
    
    /**
     * 从源对象复制属性到目标对象
     * 只复制目标对象中存在且源对象中也有相同名称的属性
     * @param target 目标对象
     * @param source 源对象
     * @return 成功复制的属性数量
     */
    public static int copyPropertiesFrom(Object target, Object source) {
        if (target == null || source == null) {
            return 0;
        }
        
        int successCount = 0;
        Class<?> targetClass = target.getClass();
        Class<?> sourceClass = source.getClass();
        
        // 获取目标对象的所有字段
        Field[] targetFields = getAllFields(targetClass);
        
        for (Field targetField : targetFields) {
            String fieldName = targetField.getName();
            
            // 检查目标对象是否有该字段的setter方法
            Class<?> targetFieldType = targetField.getType();
            if (!hasSetter(targetClass, fieldName, targetFieldType)) {
                continue;
            }
            
            // 检查源对象是否有该字段的getter方法
            if (!hasGetter(sourceClass, fieldName)) {
                continue;
            }
            
            // 获取源对象的属性值
            Object sourceValue = getProperty(source, fieldName);
            if (sourceValue == null) {
                continue;
            }
            
            // 设置到目标对象
            if (setProperty(target, fieldName, sourceValue)) {
                successCount++;
            }
        }
        
        return successCount;
    }
    
    /**
     * 从源对象复制属性到目标对象（指定源对象类型）
     * 只复制目标对象中存在且源对象中也有相同名称的属性
     * @param target 目标对象
     * @param source 源对象
     * @param sourceClass 源对象类型（用于明确指定源类型，如HttpRequestArgumentUserHeader.class）
     * @return 成功复制的属性数量
     */
    public static int copyPropertiesFrom(Object target, Object source, Class<?> sourceClass) {
        if (target == null || source == null || sourceClass == null) {
            return 0;
        }
        
        int successCount = 0;
        Class<?> targetClass = target.getClass();
        
        // 获取源对象的所有字段
        Field[] sourceFields = getAllFields(sourceClass);
        
        for (Field sourceField : sourceFields) {
            String fieldName = sourceField.getName();
            
            // 检查目标对象是否有该字段的setter方法
            Field targetField = getField(targetClass, fieldName);
            if (targetField == null) {
                continue;
            }
            
            Class<?> targetFieldType = targetField.getType();
            if (!hasSetter(targetClass, fieldName, targetFieldType)) {
                continue;
            }
            
            // 检查源对象是否有该字段的getter方法
            if (!hasGetter(sourceClass, fieldName)) {
                continue;
            }
            
            // 获取源对象的属性值
            Object sourceValue = getProperty(source, fieldName);
            if (sourceValue == null) {
                continue;
            }
            
            // 设置到目标对象
            if (setProperty(target, fieldName, sourceValue)) {
                successCount++;
            }
        }
        
        return successCount;
    }
    
    // 私有工具方法
    private static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    private static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // 尝试从父类查找
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && superClass != Object.class) {
                return getField(superClass, fieldName);
            }
            return null;
        }
    }
    
    /**
     * 获取类及其所有父类的字段（不包括Object类）
     * @param clazz 目标类
     * @return 字段数组
     */
    private static Field[] getAllFields(Class<?> clazz) {
        java.util.List<Field> fieldList = new java.util.ArrayList<>();
        Class<?> currentClass = clazz;
        
        while (currentClass != null && currentClass != Object.class) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                fieldList.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }
        
        return fieldList.toArray(new Field[0]);
    }
    
    /**
     * 类型转换
     * @param value 原始值
     * @param targetType 目标类型
     * @return 转换后的值
     */
    private static Object convertType(Object value, Class<?> targetType) {
        if (value == null || targetType == null) {
            return null;
        }
        
        // 如果类型已经匹配，直接返回
        if (targetType.isAssignableFrom(value.getClass())) {
            return value;
        }
        
        // 尝试使用TypeSerializer进行类型转换
        try {
            String valueStr = value.toString();
            return TypeSerializer.parseObject(targetType, valueStr);
        } catch (Exception e) {
            // 转换失败时返回原值
            return value;
        }
    }
}