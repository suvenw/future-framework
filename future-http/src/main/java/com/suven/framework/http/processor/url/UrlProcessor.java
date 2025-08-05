package com.suven.framework.http.processor.url;

import com.suven.framework.common.constants.ReflectionsScan;
import com.suven.framework.http.processor.url.annotations.AnnotationListener;
import com.suven.framework.http.processor.url.annotations.Observer;
import com.google.common.collect.Lists;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Title: UrlProcessor.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 基于AnnotationListener 实现Observer抽象实现类
 */


@Component
public class UrlProcessor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UrlRegistry registry;

    @Component
    public static class UrlRegistry {
        @Autowired
        private List<AnnotationListener> observers; // 存放所有需要处理特定Annotation的对象
    }


    @PostConstruct
    public void init() {
        for (Object observer : registry.observers) {
            List<Method> methods = findObserverMethod(observer.getClass());
            for (Method method : methods) {
                Class<?>[] types = method.getParameterTypes();
                if (checkParameterType(types)) { continue; }

                Class annoType = types[1];
                Map<String, Object> urlToAnno = findFieldAnnotationByAnnoType(annoType);
                for (Map.Entry<String, Object> entry : urlToAnno.entrySet()) {
                    String url = entry.getKey();
                    Object annoValue = entry.getValue();
                    invoke(method, observer, url, annoValue);
                }
            }
        }

    }

    private Object invoke(Method method, Object target, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(target, args);
        } catch (Exception e) {
            logger.warn("", e);
        }
        return null;
    }


    /**
     * 根据给定的注解查找类中每个字段的值
     * @param annoType 给定的注解
     * @return 一个map, key是字段值, value是该字段上面注解的值
     */
    private Map<String, Object> findFieldAnnotationByAnnoType(Class<? extends Annotation> annoType) {
        Map<String, Object> urlToAnno = new HashMap<>();
        Set<Class<?>> clazzSet = ReflectionsScan.reflections.getTypesAnnotatedWith(annoType);
        if (clazzSet.isEmpty()) {
            logger.error("注解:{}必须存在于类上", annoType);
        }
        for (Class<?> klass : clazzSet) {
            Annotation head = AnnotationUtils.findAnnotation(klass, annoType);
            Field[] fields = klass.getFields();
            for (Field field : fields) {
                Annotation anno = AnnotationUtils.getAnnotation(field, annoType);
                if (anno == null) {
                    continue;
                }
                String urlString;
                try {
                    urlString = FieldUtils.readStaticField(field).toString();
                    urlToAnno.put(urlString, anno);
                } catch (Exception e) {
                    logger.warn("", e);
                }
            }
        }
        return urlToAnno;
    }


    private boolean checkParameterType(Class<?>[] types) {
        // 参数个数为2, 第一个参数为String类型, 第二个参数为注解类型
        return types.length != 2
                && types[0].isAssignableFrom(String.class)
                && Annotation.class.isAssignableFrom(types[1]);
    }

    private List<Method> findObserverMethod(Class<?> klass) {
        Method[] methods = klass.getDeclaredMethods();
        List<Method> methodList = Lists.newArrayList();
        for (Method method : methods) {
            Observer observer = AnnotationUtils.findAnnotation(method, Observer.class);
            if (observer != null) {
                methodList.add(method);
            }
        }
        return methodList;
    }

}
