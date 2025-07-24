package com.suven.framework.core.advice;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class  BaseAspectAdvice {






    /** ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
     ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
    **/
     public static ImmutableSet<? extends Class<? >> primitiveSet
            = ImmutableSet.of(
            BindingResult.class,
            ServletRequest.class,
            ServletResponse.class,
            MultipartFile.class);



    /**
     * 返回标签注解的方法的返回结果进行扩写的结果,用于自定义或扩写实现
     * @param joinPoint
     * @return
     */
    protected   Object getMethodReturnValueExt(Object joinPoint) {
        return joinPoint;
    }

    /**
     * 返回标签注解的方法的返回结果
     * @param joinPoint
     * @return
     */
    protected   Object getMethodReturnValue(ProceedingJoinPoint joinPoint) {
        try {
            Object	result = joinPoint.proceed();
            Object	resultExt = this.getMethodReturnValueExt(result);
            return resultExt;
        }catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取请求参数的值的聚合
     * @param joinPoint
     */
    protected List<Object> getMethodParameterValueList(ProceedingJoinPoint joinPoint ) {
        return Arrays.asList( joinPoint.getArgs());
    }

    /**
     * 获取切面的方法的参数例型的实现方法
     * @param joinPoint
     */
    protected List<Class> getMethodParameterClass(ProceedingJoinPoint joinPoint ) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?>[] parameters = method.getParameterTypes();
        return Arrays.asList(parameters);


    }
    /**
     * 获取方法上所有参数的注解标签,不存在注解时,返回null,否则返回参数类型和对应的指定的注解标签
     * @param joinPoint
     */
    protected Multimap<String, Annotation> getMethodParameterAnnotation(ProceedingJoinPoint joinPoint, Annotation mqSendSign ) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?>[] parameters = method.getParameterTypes();
        Multimap<String,Annotation > map =   ArrayListMultimap.create();
        for (int i = 0, j = parameters.length; i < j; i++) {
            String parameterName =  parameters[i].getName();
            Annotation annotation = parameters[i].getAnnotation(mqSendSign.getClass());
            if(null != annotation ){
                map.put(parameterName,annotation);
            }

        }
        return map;
    }

    protected LinkedHashMap<String,Object> getMethodParameterNameAndValue(ProceedingJoinPoint joinPoint  ){
        return this.getMethodParameterNameAndValue(joinPoint,true);
    }
    /**
     * @param joinPoint
     */
    protected LinkedHashMap<String,Object> getMethodParameterNameAndValue(ProceedingJoinPoint joinPoint ,boolean exclude ) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[]  parameterValues = joinPoint.getArgs();
//         请求的方法参数名称
        ParameterNameDiscoverer parameterNameDiscoverer = new StandardReflectionParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        LinkedHashMap<String,Object > map =   new LinkedHashMap<>();
        for (int i = 0, j = parameterValues.length; i < j; i++) {
            String paramName =  parameterNames[i];
            Object paramValue = parameterValues[i];
            if (exclude && isExclude(parameterTypes[i]) ){
                continue;
            }
            map.put(paramName,paramValue);
        }
        return map;
    }
    /**
     * 获取切面类的方法相关属性: 0.请求参数类型,1.请求参数字段名称,2.请求参数的值,依次组成的集合的列表
     * @param joinPoint
     */
    protected List<Object> getMethodParameterClassNameValueList(ProceedingJoinPoint joinPoint ,boolean exclude ) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //请求的方法参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        //请求的方法参数名称
        ParameterNameDiscoverer parameterNameDiscoverer = new StandardReflectionParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        //请求的方法参数值
        Object[]  parameterValues = joinPoint.getArgs();

        List<Object> list = new ArrayList<>();
        for (int i = 0, j = parameterNames.length; i < j; i++) {
            Class<?> classes =  parameterTypes[i];
            String parameterName =  parameterNames[i];
            Object parameterValue = parameterValues[i];
            if (exclude && isExclude(parameterTypes[i]) ){
                continue;
            }
            list.add(classes);
            list.add(parameterName);
            list.add(parameterValue);

        }
        return list;
    }
    /**
     * 返回请求类名和方法名称,0.对应的是类型,1.对应的是方法名
     */
    protected String getTargetClassSimpleName(ProceedingJoinPoint joinPoint ) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = signature.getName();
        return className;
    }
    /**
     * 返回请求类名和方法名称,0.对应的是类型,1.对应的是方法名
     */
    protected String getMethodName(ProceedingJoinPoint joinPoint ) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //请求的方法名
        String methodName = signature.getName();
        return methodName;
    }

    protected List<String> getMethodTargetClassName(ProceedingJoinPoint joinPoint ) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        return Arrays.asList(className,methodName);
    }
    public List<Class> excludeClass(){
        return null;
    }

    protected boolean isExclude(Class<?> parameter){
        List<Class> excludeClassList =  excludeClass();
        if(excludeClassList == null ){
            excludeClassList = new ArrayList<>(primitiveSet);
        }else {
            excludeClassList.addAll(primitiveSet);
        }
       for (Class e: excludeClassList ) {
           if (e.isAssignableFrom(parameter)) {
               return  true;
           }
       }
       return false;
    }


}
