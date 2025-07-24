//package com.suven.framework.core;
//
//import com.google.common.base.Predicate;
//import com.suven.framework.common.constants.GlobalConfigConstants;
//import org.reflections.Reflections;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.lang.reflect.Member;
//import java.lang.reflect.Method;
//import java.util.List;
//import java.util.Set;
//
//public abstract class Scaner {
//    static Reflections reflections   = new Reflections(GlobalConfigConstants.COMPONENT_SCAN_BASE_PACKAGES);
//
//    private Scaner(){}
//    private static Reflections getScan(){
//        return reflections;
//    }
//
//
//
//    public static  <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type) {
//        Set<Class<? extends T>> classList = getScan().getSubTypesOf(type);
//        return classList;
//    }
//    public static Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
//        return getScan().getTypesAnnotatedWith(annotation, false);
//    }
//
//
//
//    public static Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation, boolean honorInherited) {
//        return getScan().getTypesAnnotatedWith(annotation,honorInherited);
//    }
//
//    public static Set<Class<?>> getTypesAnnotatedWith(Annotation annotation) {
//        return getScan().getTypesAnnotatedWith(annotation, false);
//    }
//
//    public static Set<Class<?>> getTypesAnnotatedWith(Annotation annotation, boolean honorInherited) {
//       return getScan().getTypesAnnotatedWith(annotation,honorInherited);
//    }
//
//
//
//    public static Set<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
//        return getScan().getMethodsAnnotatedWith(annotation);
//    }
//
//    public static Set<Method> getMethodsAnnotatedWith(Annotation annotation) {
//        return getScan().getMethodsAnnotatedWith(annotation);
//    }
//
//    public static Set<Method> getMethodsMatchParams(Class<?>... types) {
//        return getScan().getMethodsMatchParams(types);
//    }
//
//    public static Set<Method> getMethodsReturn(Class returnType) {
//        return getScan().getMethodsReturn(returnType);
//    }
//
//    public static Set<Method> getMethodsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
//        return getScan().getMethodsWithAnyParamAnnotated(annotation);
//    }
//
//    public static Set<Method> getMethodsWithAnyParamAnnotated(Annotation annotation) {
//        return getScan().getMethodsWithAnyParamAnnotated(annotation);
//    }
//
//    public static Set<Constructor> getConstructorsAnnotatedWith(Class<? extends Annotation> annotation) {
//        return getScan().getConstructorsAnnotatedWith(annotation);
//    }
//
//    public static Set<Constructor> getConstructorsAnnotatedWith(Annotation annotation) {
//        return getScan().getConstructorsAnnotatedWith(annotation);
//    }
//
//    public static Set<Constructor> getConstructorsMatchParams(Class<?>... types) {
//        return getScan().getConstructorsMatchParams(types);
//    }
//
//    public static Set<Constructor> getConstructorsWithAnyParamAnnotated(Class<? extends Annotation> annotation) {
//        return getScan().getConstructorsWithAnyParamAnnotated(annotation);
//    }
//
//    public static Set<Constructor> getConstructorsWithAnyParamAnnotated(Annotation annotation) {
//        return getScan().getConstructorsWithAnyParamAnnotated(annotation);
//    }
//
//
//    public static Set<Field> getFieldsAnnotatedWith(Annotation annotation) {
//        return getScan().getFieldsAnnotatedWith(annotation);
//    }
//
//    public static Set<String> getResources(Predicate<String> namePredicate) {
//        return getScan().getResources(namePredicate);
//    }
//
//
//
//    public List<String> getMethodParamNames(Method method) {
//        return getScan().getMethodParamNames(method);
//    }
//
//    public List<String> getConstructorParamNames(Constructor constructor) {
//        return getScan().getConstructorParamNames(constructor);
//    }
//
//    public static Set<Member> getFieldUsage(Field field) {
//        return getScan().getFieldUsage(field);
//    }
//
//    public static Set<Member> getMethodUsage(Method method) {
//        return getScan().getMethodUsage(method);
//    }
//
//    public static Set<Member> getConstructorUsage(Constructor constructor) {
//        return getScan().getConstructorUsage(constructor);
//    }
//
//
//}
