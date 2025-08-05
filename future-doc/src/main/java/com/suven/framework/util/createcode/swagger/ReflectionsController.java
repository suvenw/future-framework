package com.suven.framework.util.createcode.swagger;

import com.suven.framework.http.api.ApiCmd;
import com.suven.framework.http.api.ApiDoc;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2022-01-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  扫描控制类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
@Component
public class ReflectionsController {

    public TreeMap<String,Class>  getControllerClass(SwaggerReflection swaggerReflection){
        //初始化扫描包;
        Reflections reflections = swaggerReflection.getReflections();
        Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(Controller.class);
        Set<Class<?>> restList = reflections.getTypesAnnotatedWith(RestController.class);
        Set<Class<?>> apiDocList = reflections.getTypesAnnotatedWith(ApiDoc.class);
        TreeMap<String,Class> controllerTreeClass = new TreeMap<>();
        controllerTreeClass.putAll( classesList.stream().collect(Collectors.toMap(Class::getSimpleName, clazz -> clazz)));
        controllerTreeClass.putAll(restList.stream().collect(Collectors.toMap(Class::getSimpleName, clazz -> clazz)));
        controllerTreeClass.putAll(apiDocList.stream().collect(Collectors.toMap(Class::getSimpleName, clazz -> clazz)));

        return controllerTreeClass;
    }

    /** 构造api 返回接口参数实现类 **/
    public Class getApiDocResponseClass(ApiDoc apiDoc){
        Class apiDocResponseClass = null;
        if(null ==  apiDoc.response()){
            return apiDocResponseClass;
        }
        apiDocResponseClass = apiDoc.response()[0];
        if(apiDoc.response().length == 1){
            return apiDocResponseClass;
        }
        Class constructorClass = getConstructorClass(apiDoc.response());
        if(constructorClass != null){
            apiDocResponseClass = constructorClass;
        }
        return apiDocResponseClass;
    }

    private Class<?> getConstructorClass(Class<?>[] response){
        try{
            Class[] classes = new Class[response.length - 1];
            System.arraycopy(response, 1, classes, 0, classes.length);
            Constructor constructor = response[0].getDeclaredConstructor(classes);
            return constructor.getClass();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /** 获取请求方法的url,请求方式类型,post/get **/
    public   void getMappingMethodTypeUrl(Method method, SwaggerMethodBean methodBean, ApiDoc apiDoc){


        RequestMapping mapping =  method.getAnnotation(RequestMapping.class);
        if (notNull(mapping) && notOne(mapping.value())) {
            String url = mapping.value()[0];
            String methodType = "GET";
            if (null != mapping.method() && mapping.method().length >= 1){
                methodType = mapping.method()[0].name();
            }
            methodBean.setRequestUrl(url);
            methodBean.setRequestMethodType(methodType);
            return;
        }
        PostMapping post =  method.getAnnotation(PostMapping.class);
        if (notNull(post) && notOne(post.value())) {
            methodBean.setRequestUrl(post.value()[0]);
            methodBean.setRequestMethodType("POST");
            return;
        }
        GetMapping get =  method.getAnnotation(GetMapping.class);
        if (notNull(get) && notOne(get.value())) {
            methodBean.setRequestUrl(get.value()[0]);
            methodBean.setRequestMethodType("GET");
            return;
        }
        DeleteMapping delete =  method.getAnnotation(DeleteMapping.class);
        if (notNull(delete) && notOne(delete.value())) {
            methodBean.setRequestUrl(delete.value()[0]);
            methodBean.setRequestMethodType("DELETE");
            return;
        }
        PatchMapping patch =  method.getAnnotation(PatchMapping.class);
        if (notNull(patch) && notOne(patch.value())) {
            methodBean.setRequestUrl(patch.value()[0]);
            methodBean.setRequestMethodType("PATCH");
            return;
        }
        ApiCmd apiCmd =  method.getAnnotation(ApiCmd.class);
        if (notNull(apiCmd) && notOne(apiCmd.cmd())) {
            String cmd = apiDoc.cmd();
            methodBean.setRequestUrl(cmd);
            methodBean.setRequestMethodType(apiCmd.methodType());
            return;
        }
        if(notNull(apiDoc) ){
            String apiMethodType  = apiDoc.methodType();
            String cmd = apiDoc.cmd();
            methodBean.setRequestUrl(cmd);
            methodBean.setRequestMethodType(apiMethodType);
            return;
        }

    }

    private boolean notEquals(Object patch){
        return patch != null && !"".equals(patch) ;
    }
    private boolean notNull(Object patch){
        return patch != null;
    }
    private boolean notOne(String... path){
        if (null != path && path.length >= 1 )  {
            return true;
        }
        return false;

    }
}
