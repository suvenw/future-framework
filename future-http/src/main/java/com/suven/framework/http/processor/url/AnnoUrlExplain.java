package com.suven.framework.http.processor.url;

import com.suven.framework.common.constants.ReflectionsScan;
import jakarta.annotation.PostConstruct;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;


/**
 * @Title: AnnoUrlExplain.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http get/post 接口 url 采摘缓存实现类,用于接口验证等业务;
 */


@Component
public final class AnnoUrlExplain {

    public static Set<String> urlSet = new HashSet<>();

    /**
     * 获取指定文件下面的RequestMapping方法保存在mapp中
     * T extends Annotation
     * @return
     */
    @PostConstruct
    public void init() {
        Reflections reflections = ReflectionsScan.reflections;
        Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(Controller.class);
        Set<Class<?>> restControllerList = reflections.getTypesAnnotatedWith(RestController.class);
        classesList.addAll(restControllerList);
        // 存放url和   RequestMapping 的对应关系
        for (Class classes : classesList) {
            //得到该类下面的所有方法
            Method[] methods = classes.getDeclaredMethods();
            if (null == methods || methods.length == 0){
                continue;
            }
            for (Method method : methods) {
                try {
                    //得到该类下面的RequestMapping注解
                    String path  = methodMapping(method);
                    if (null == path) {
                        continue;
                    }
                    urlSet.add(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private  String methodMapping(Method method){

        RequestMapping mapping =  method.getAnnotation(RequestMapping.class);
        if (notNull(mapping) && notOne(mapping.value())) {
             return mapping.value()[0];
        }
        PostMapping post =  method.getAnnotation(PostMapping.class);
        if (notNull(post) && notOne(post.value())) {
            return post.value()[0];
        }
        GetMapping get =  method.getAnnotation(GetMapping.class);
        if (notNull(get) && notOne(get.value())) {
            return get.value()[0];
        }
        DeleteMapping delete =  method.getAnnotation(DeleteMapping.class);
        if (notNull(delete) && notOne(delete.value())) {
            return delete.value()[0];
        }
        PatchMapping patch =  method.getAnnotation(PatchMapping.class);
        if (notNull(patch) && notOne(patch.value())) {
            return patch.value()[0];
        }
        RequestPart part =  method.getAnnotation(RequestPart.class);
        if (notNull(part) && notOne(part.value())) {
            return part.value();
        }
        return null;

    }

    private boolean notNull(Object patch){
         return patch != null;
    }
    private boolean notOne(String... path){
        if (null != path && path.length == 1 )  {
            return true;
        }
        return false;

    }




}
