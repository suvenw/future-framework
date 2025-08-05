package com.suven.framework.util.createcode.swagger;

import java.util.*;

public class SwaggerResultBean {
//        "swagger":"2.0",
//         "info":{
//        "description":"简单优雅的restfun风格",
//                "version":"1.0.0",
//                "title":"鼎盛下载站利用swagger构建api文档",
//                "termsOfService":"http://192.168.25.200/soft"
//    },
//            "host":"192.168.2.1:19030",
//            "basePath":"/soft",
//            "tags":Array[27],
//            "paths":Object{...},
//             "definitions":Object{...}


    private String swagger; //"2.0"
    private SwaggerInfo info;
    private String host;
    private String basePath;
    private List<SwaggerTagBean> tags;
    private SwaggerPathsMap paths;
    private LinkedHashMap definitions;

    public String getSwagger() {
        return swagger;
    }

    public SwaggerResultBean setSwagger(String swagger) {
        this.swagger = swagger;
        return this;
    }

    public SwaggerInfo getInfo() {
        return info;
    }

    public SwaggerResultBean setInfo(SwaggerInfo info) {
        this.info = info;
        return this;
    }

    public String getHost() {
        return host;
    }

    public SwaggerResultBean setHost(String host) {
        this.host = host;
        return this;
    }

    public String getBasePath() {
        return basePath;
    }

    public SwaggerResultBean setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public List<SwaggerTagBean> getTags() {
        return tags;
    }

    public SwaggerResultBean setTags(List<SwaggerTagBean> tags) {
        this.tags = tags;
        return this;
    }

    public SwaggerResultBean setTags(SwaggerTagBean tag) {
        if(tags == null){
            tags = new ArrayList<>();
        }tags.add(tag);
        return this;
    }


    public SwaggerPathsMap getPaths() {
        return paths;
    }

    public SwaggerResultBean setPaths(SwaggerPathsMap paths) {
        this.paths = paths;
        return this;
    }

    public LinkedHashMap getDefinitions() {
        return definitions;
    }

    public SwaggerResultBean setDefinitions(LinkedHashMap definitions) {
        this.definitions = definitions;
        return this;
    }
    public SwaggerResultBean setDefinitionsAll(Collection<SwaggerResponseParameterMap> collection) {
        this.definitions = SwaggerResponseParameterMap.build();
        for (SwaggerResponseParameterMap e : collection)
            definitions.putAll(e);
        return this;
    }

    @Override
    public String toString() {
        return "SwaggerResultBean{" +
                "swagger='" + swagger + '\'' +
                ", info=" + info +
                ", host='" + host + '\'' +
                ", basePath='" + basePath + '\'' +
                ", tags=" + tags +
                ", paths=" + paths +
                ", definitions=" + definitions +
                '}';
    }
}
