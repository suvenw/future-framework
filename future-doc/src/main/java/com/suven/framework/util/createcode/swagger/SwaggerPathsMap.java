package com.suven.framework.util.createcode.swagger;

import java.util.TreeMap;

//数据为;key-value; object  SwaggerRequestMethodBean
//SwaggerPathsMap<url, SwaggerRequestMethodMap<RequestMethod.POST,SwaggerPathsBean>>
public class SwaggerPathsMap<T> extends TreeMap {
    //map 存储的数据结构为
     // url -> SwaggerRequestMethodMap->SwaggerPathsBean
//    RequestMethod.POST
//    RequestMethod.GET

    public static SwaggerPathsMap build(){
        return new SwaggerPathsMap();
    }

    public SwaggerPathsMap put(String key, SwaggerRequestMethodMap value){
        super.put(key, value);
        return this;
    }
    public T getTo(String key){
       Object object =  super.get(key);
        return (T)object;
    }
}
