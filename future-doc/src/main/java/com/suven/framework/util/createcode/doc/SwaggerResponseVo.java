package com.suven.framework.util.createcode.doc;

import com.suven.framework.http.api.ApiDesc;

import java.util.ArrayList;
import java.util.List;

public class SwaggerResponseVo {
//"info":{
//        "description":"简单优雅的restfun风格",
//                "version":"1.0.0",
//                "title":"鼎盛下载站利用swagger构建api文档",
//                "termsOfService":"http://192.168.25.200/soft"
//    }

    public static SwaggerResponseVo init(){
       return new SwaggerResponseVo().setDescription("简单优雅的restfun风格")
               .setVersion("1.0.0")
               .setTermsOfService("http://192.168.25.200/soft")
               .setTitle("站利用swagger构建api文档")
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

    @ApiDesc(value = "请求参数说明")
    private SwaggerRequestVo requestVo; //"1.0.0"
    private List<SwaggerBaseVo> baseVoList;


    public SwaggerResponseVo(){

    }



    public String getDescription() {
        return description;
    }

    public SwaggerResponseVo setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public SwaggerResponseVo setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SwaggerResponseVo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTermsOfService() {
        return termsOfService;
    }

    public SwaggerResponseVo setTermsOfService(String termsOfService) {
        this.termsOfService = termsOfService;
        return this;
    }

    public SwaggerRequestVo getRequestVo() {
        return requestVo;
    }

    public SwaggerResponseVo setRequestVo(SwaggerRequestVo requestVo) {
        this.requestVo = requestVo;
        return this;
    }

    public List<SwaggerBaseVo> getBaseVoList() {
        return baseVoList;
    }

    public SwaggerResponseVo setBaseVoList(List<SwaggerBaseVo> baseVoList) {
        this.baseVoList = baseVoList;
        return this;
    }
    public SwaggerResponseVo setBaseVoList(SwaggerBaseVo baseVo) {
        if(this.baseVoList == null){
            baseVoList = new ArrayList<>();
        }baseVoList.add(baseVo);
        return this;
    }
}
