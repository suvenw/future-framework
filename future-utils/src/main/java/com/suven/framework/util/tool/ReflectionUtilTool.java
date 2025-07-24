package com.suven.framework.util.tool;

import com.suven.framework.core.spring.SpringUtil;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 * 
 * @see {@link ReflectionUtils}
 */
public abstract class ReflectionUtilTool extends ReflectionUtils {

	/**
	 * 查找唯一被指定注释声明的域
	 * 
	 * @param <A> 注释类型
	 * 
	 * @param  clazz 			被查找的类
	 * @param  type 			指定的注释
	 * @return {@link Field}	不存在会返回 null
	 */
	public static <A extends Annotation> Field findUniqueFieldWithAnnotation(Class<?> clazz, final Class<A> type) {
		final List<Field> fields = new ArrayList<Field>();
		FieldFilter fieldFilter = new FieldFilter() {
			@Override
			public boolean matches(Field field) {
				return field.isAnnotationPresent(type);
			}
		};
		
		FieldCallback fieldCallback = new FieldCallback() {
			@Override
			public void doWith(Field field) throws IllegalArgumentException {
				fields.add(field);
			}
		};
		
		doWithFields(clazz, fieldCallback, fieldFilter);
		if (fields.size() > 1) {
			throw new IllegalStateException("被注释" + type.getSimpleName() + "声明的域不唯一");
		} else if (fields.size() == 1) {
			return fields.get(0);
		}
		
		return null;
	}

	/**
	 * 类似{@link org.springframework.util.ReflectionUtils#doWithFields(Class, FieldCallback, FieldFilter)}
	 * 的方法，只是该方法不会递归检查父类上的域
	 * 
	 * @see org.springframework.util.ReflectionUtils#doWithFields(Class, FieldCallback, FieldFilter)
	 * @param clazz
	 * @param fc
	 * @param ff
	 * @throws IllegalArgumentException
	 */
	public static void doWithDeclaredFields(Class<?> clazz, FieldCallback fc, FieldFilter ff) throws IllegalArgumentException {
		if (clazz == null || clazz == Object.class) {
			return;
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (ff != null && !ff.matches(field)) {
				continue;
			}
			try {
				fc.doWith(field);
			} catch (IllegalAccessException ex) {
				throw new IllegalStateException("非法访问属性 '" + field.getName() + "': " + ex);
			}
		}
	}
	
	/**
	 * 获得第一个使用指定注释声明的属性
	 * @param clz 属性所在类
	 * @param annotationClass 注释类型
	 * @return 不存在则返回 null
	 */
	public static Field getFirstDeclaredFieldWith(Class<?> clz, Class<? extends Annotation> annotationClass) {
		for (Field field : clz.getDeclaredFields()) {
			if (field.isAnnotationPresent(annotationClass)) {
				return field;
			}
		}
		return null;
	}
	

	/**
	 * 获得第一个使用指定注释声明的属性. 该方法会递归所有父类的属性
	 * 
	 * @param clazz 			属性所在类
	 * @param annotationClass 	注释类型
	 * @return {@link Field}	不存在则返回 null
	 */
	public static Field getRecursiveFirstDeclaredFieldWith(Class<?> clazz, Class<? extends Annotation> annotationClass) {
		Field currField = null;
		if(clazz != null && clazz != Object.class) {
			currField = getFirstDeclaredFieldWith(clazz, annotationClass);
			if(currField == null) {
				return getRecursiveFirstDeclaredFieldWith(clazz.getSuperclass(), annotationClass);
			}
		}
		return currField;
	}
	
	/**
	 * 获得全部使用指定注释声明的属性
	 * @param clz 属性所在类
	 * @param annotationClass 注释类型
	 * @return 不会返回 null
	 */
	public static Field[] getDeclaredFieldsWith(Class<?> clz, Class<? extends Annotation> annotationClass) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : clz.getDeclaredFields()) {
			if (field.isAnnotationPresent(annotationClass)) {
				fields.add(field);
			}
		}
		return fields.toArray(new Field[0]);
	}

	/**
	 * 获得第一个使用指定注释声明的方法
	 * @param clz 属性所在类
	 * @param annotationClass 注释类型
	 * @return 不存在则返回 null
	 */
	public static Method getFirstDeclaredMethodWith(Class<?> clz, Class<? extends Annotation> annotationClass) {
		for (Method method : clz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(annotationClass)) {
				return method;
			}
		}
		return null;
	}

	/**
	 * 获得全部使用指定注释声明的方法
	 * @param clz 属性所在类
	 * @param annotationClass 注释类型
	 * @return 不会返回 null
	 */
	public static Method[] getDeclaredMethodsWith(Class<?> clz, Class<? extends Annotation> annotationClass) {
		List<Method> methods = new ArrayList<Method>();
		for (Method method : clz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(annotationClass)) {
				methods.add(method);
			}
		}
		return methods.toArray(new Method[0]);
	}

	/**
	 * 获得全部使用指定注释声明的 get 方法
	 * @param clz 属性所在类
	 * @param annotationClass 注释类型
	 * @return 不会返回 null
	 */
	public static Method[] getDeclaredGetMethodsWith(Class<?> clz, Class<? extends Annotation> annotationClass) {
		List<Method> methods = new ArrayList<Method>();
		for (Method method : clz.getDeclaredMethods()) {
			if (method.getAnnotation(annotationClass) == null) {
				continue;
			}
			if (method.getReturnType().equals(void.class)) {
				continue;
			}
			if (method.getParameterTypes().length > 0) {
				continue;
			}
			methods.add(method);
		}
		return methods.toArray(new Method[0]);
	}


	public static Object invokeClassMethod(String beanName, String methodName) {
		return invokeClassMethod(beanName,methodName,null);
	}
		/**
         * 通过 spring content 的bean字符串获取对应的类,并通过参数运行指定的方法;返回对应对象;
         * @param beanName 从spring获取指定的类的名称的字符串
         * @param methodName beanName对象的对应的方法名称的字符串
         * @param args  执行beanName对象的方法时传弟的参数的数组;可以为null
         * @param paramTypes beanName对象的对应的方法名称的字符串,指定的参数类型,可以为null
         * @return
         */
	public static Object invokeClassMethod(String beanName, String methodName,Object[] args,Class<?>... paramTypes){

		Object beanClass = SpringUtil.getBean(beanName);

		Method method = ReflectionUtils.findMethod(beanClass.getClass(), methodName,paramTypes);

		Object result = ReflectionUtils.invokeMethod(method, beanClass,args);
		return result;
	}

    /**
     * 通过 spring content 的bean字符串获取对应的类,并通过参数运行指定的方法;返回对应对象;
     * @param methodName beanName对象的对应的方法名称的字符串
     * @param args  执行beanName对象的方法时传弟的参数的数组;可以为null
     * @param paramTypes beanName对象的对应的方法名称的字符串,指定的参数类型,可以为null
     * @return
     */
    public static Object invokeBeanMethod(Object beanClass, String methodName,Object[] args,Class<?>... paramTypes){


        Method method = ReflectionUtils.findMethod(beanClass.getClass(), methodName,paramTypes);

        Object result = ReflectionUtils.invokeMethod(method, beanClass,args);
        return result;
    }

}
