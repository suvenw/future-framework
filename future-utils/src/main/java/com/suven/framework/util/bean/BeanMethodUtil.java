package com.suven.framework.util.bean;

import com.suven.framework.http.api.ApiDoc;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeanMethodUtil {

    private IBeanMethod beanMethod;
    private List<IBeanMethod> beanMethodList;


    public IBeanMethod getBeanMethod() {
        return beanMethod;
    }

    public void setBeanMethod(IBeanMethod beanMethod) {
        this.beanMethod = beanMethod;
    }
    @ApiDoc
    public List<IBeanMethod> getBeanMethodList() {
        return beanMethodList;
    }

    @ApiDoc
    public void setBeanMethodList(List<IBeanMethod> beanMethodList,BeanMethod beanMethod) {
        this.beanMethodList = beanMethodList;
        this.beanMethod = beanMethod;
    }

    private static interface IBeanMethod extends java.io.Serializable{
        String getBeanMethod();
        void setBeanMethod(String beanMethod);
    }


    private static class BeanMethod implements IBeanMethod{
        private String beanMethod;
        public String getBeanMethod() {
            return beanMethod;
        }

        public void setBeanMethod(String beanMethod) {
            this.beanMethod = beanMethod;
        }
    }

    public static void methodInvoke(){
            Set<Class<? >> classList = new HashSet<>();
            classList.add(BeanMethodUtil.class);
            if(null != classList && !classList.isEmpty()){
                for (Class<?> clazz : classList){
                    Method[] methodSet = ReflectionUtils.getAllDeclaredMethods(clazz);
                    printFormat(fmt, "class info ", clazz.getPackage());

                    for (Method declaredMethod : methodSet) {
                        printFormat(fmt, "Method name start", "===========================");
                        printFormat(fmt, "Method name", declaredMethod.getName());  //获得单独的方法名
                        //获得完整的方法信息（包括修饰符、返回值、路径、名称、参数、抛出值）
                        printFormat(fmt, "toGenericString", declaredMethod.toGenericString());

                        int modifiers = declaredMethod.getModifiers();      //获得修饰符
                        printFormat(fmt, "Modifiers", Modifier.toString(modifiers));

                        printFormat(fmt, "ReturnType", declaredMethod.getReturnType());   //获得返回值
                        printFormat(fmt, "getGenericReturnType", declaredMethod.getGenericReturnType());//获得完整信息的返回值

                        Class<?>[] parameterTypes = declaredMethod.getParameterTypes(); //获得参数类型
                        Type[] genericParameterTypes = declaredMethod.getGenericParameterTypes();
                        for (int i = 0; i < parameterTypes.length; i++) {
                            printFormat(fmt, "ParameterType", parameterTypes[i]);
                            printFormat(fmt, "GenericParameterType", genericParameterTypes[i]);
                        }

                        Class<?>[] exceptionTypes = declaredMethod.getExceptionTypes();     //获得异常名称
                        Type[] genericExceptionTypes = declaredMethod.getGenericExceptionTypes();
                        for (int i = 0; i < exceptionTypes.length; i++) {
                            printFormat(fmt, "ExceptionTypes", exceptionTypes[i]);
                            printFormat(fmt, "GenericExceptionTypes", genericExceptionTypes[i]);
                        }

                        Annotation[] annotations = declaredMethod.getAnnotations(); //获得注解
                        for (Annotation annotation : annotations) {
                            printFormat(fmt, "Annotation", annotation);
                            printFormat(fmt, "AnnotationType", annotation.annotationType());
                        }

                        printFormat(fmt, "Method name end", "=========================== \n\n\n\n");
                    }
                }
            }
        }
    private static final String fmt = "%24s:   %s\n";
    static void printFormat(String format,String desc , Object info){
        String data = String.format(fmt,desc,info);
        System.out.println(data);
    }



}
