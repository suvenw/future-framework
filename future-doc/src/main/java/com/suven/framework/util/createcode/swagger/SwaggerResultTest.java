package com.suven.framework.util.createcode.swagger;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMethod;

public class SwaggerResultTest {


    public static void main(String[] args) {

        SwaggerResultBean bean = new SwaggerResultBean();


        SwaggerRequestMethodBean methodBean = SwaggerRequestMethodBean.init();

        SwaggerRequestMethodMap methodMap = SwaggerRequestMethodMap.build().put(RequestMethod.POST.name(),methodBean);
        SwaggerPathsMap paths =  SwaggerPathsMap.build().put("url",methodMap);
        bean.setInfo(SwaggerInfo.init());

        bean.setBasePath("/soft").setHost("192.168.2.1:19030").setSwagger("2.0")
                .setTags( SwaggerTagBean.init("banner-info-web-controller","Banner Info Web Controller"))
                .setPaths(paths);

        System.out.println(JSON.toJSONString(bean));


    }
}
