package com.suven.framework.util.createcode.swagger;

import java.util.LinkedHashMap;

/**    SwaggerRequestMethodMap<RequestMethod.POST,SwaggerPathsBean>
 *    SwaggerRequestMethodMap<RequestMethod.GET,SwaggerPathsBean>
 *   SwaggerRequestMethodMap<RequestMethod.,SwaggerPathsBean>
 *   key 为RequestMethod.POST 对应的值 RequestMethod
 */
public class SwaggerRequestMethodMap extends LinkedHashMap  {

    public static SwaggerRequestMethodMap build(){
        return new SwaggerRequestMethodMap();
    }

    public SwaggerRequestMethodMap put(String requestMethodTypeName, SwaggerRequestMethodBean requestMethodBean){
        super.put(requestMethodTypeName, requestMethodBean);
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
