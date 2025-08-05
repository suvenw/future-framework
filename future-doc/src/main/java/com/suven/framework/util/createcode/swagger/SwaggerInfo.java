package com.suven.framework.util.createcode.swagger;


import com.suven.framework.http.api.ApiDesc;


public class SwaggerInfo {
//"info":{
//        "description":"简单优雅的restfun风格",
//                "version":"1.0.0",
//                "title":"鼎盛下载站利用swagger构建api文档",
//                "termsOfService":"http://192.168.25.200/soft"
//    }

    public static SwaggerInfo init(){
       return new SwaggerInfo().setDescription("简单优雅的restfun风格")
               .setVersion("1.0.0")
               .setTermsOfService("http://127.0.0.1/top/")
               .setTitle("利用swagger构建项目接口api文档")
               ;
    }

    @ApiDesc(value = "字段介绍说明, 简单优雅的restfun风格")
    private String description;//简单优雅的restfun风格
    @ApiDesc(value = "字段介绍说明, 版本信息")
    private String version; //"1.0.0"
    @ApiDesc(value = "字段介绍说明, 简单优雅的restfun风格")
    private String title;//简单优雅的restfun风格
    @ApiDesc(value = "字段说明,服务版本")
    private String termsOfService; //"1.0.0"


    public static SwaggerInfo build(String title ,String desc , String version,String url){
        return new SwaggerInfo().setTitle(title).setDescription(desc)
                .setVersion(version)
                .setTermsOfService(url)

                ;
    }

    public String getDescription() {
        return description;
    }

    public SwaggerInfo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public SwaggerInfo setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SwaggerInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTermsOfService() {
        return termsOfService;
    }

    public SwaggerInfo setTermsOfService(String termsOfService) {
        this.termsOfService = termsOfService;
        return this;
    }


}
