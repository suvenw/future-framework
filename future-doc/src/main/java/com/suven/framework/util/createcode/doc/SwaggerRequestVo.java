package com.suven.framework.util.createcode.doc;

import com.suven.framework.http.api.ApiDesc;

import java.util.List;

public class SwaggerRequestVo  {


    @ApiDesc(value = "字段介绍说明,name ")
    private String name;
    @ApiDesc(value = "字段介绍说明, 查询")
    private String in = "query";
    @ApiDesc(value = "字段信息描述")
    private String description;
    @ApiDesc(value = "字段介绍说明, 是否必须字段")
    private boolean required;
    @ApiDesc(value = "字段介绍说明, 请求参数类型")
    private String type;




    @ApiDesc(value = "字段介绍说明, 请求参数类型",isHide = 1)
    private SwaggerResultVo swaggerResultVo;
    @ApiDesc(value = "聚合对象 请求参数类型",isHide = 1)
    private List<SwaggerBaseVo> swaggerBaseVo;

    @ApiDesc(value = "为SwaggerResultVo对象json 字符串")
    private String swaggerResultJson;

    @ApiDesc(value = "为 SwaggerBaseVo 对象json 字符串")
    private String swaggerBaseJson;


    public SwaggerRequestVo() {
    }

    public SwaggerRequestVo(SwaggerResultVo swaggerResultVo) {
        this.swaggerResultVo = swaggerResultVo;
    }

    public static SwaggerRequestVo build(){
        return  new SwaggerRequestVo();
    }

    public static SwaggerRequestVo init(){
        return init("area","广告地区","string");
    }
    public static SwaggerRequestVo init(String name, String desc, String type){
        return build().setName(name).setDescription(desc).setType(type).setIn("query").setRequired(false);
    }

    public String getName() {
        return name;
    }

    public SwaggerRequestVo setName(String name) {
        this.name = name;
        return this;
    }

    public String getIn() {
        return in;
    }

    public SwaggerRequestVo setIn(String in) {
        this.in = in;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SwaggerRequestVo setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public SwaggerRequestVo setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public String getType() {
        return type;
    }

    public SwaggerRequestVo setType(String type) {
        this.type = type;
        return this;
    }

    public SwaggerResultVo getSwaggerResultVo() {
        return swaggerResultVo;
    }

    public void setSwaggerResultVo(SwaggerResultVo swaggerResultVo) {
        this.swaggerResultVo = swaggerResultVo;
    }

    public String getSwaggerResultJson() {
        return swaggerResultJson;
    }

    public void setSwaggerResultJson(String swaggerResultJson) {
        this.swaggerResultJson = swaggerResultJson;
    }

    public List<SwaggerBaseVo> getSwaggerBaseVo() {
        return swaggerBaseVo;
    }

    public void setSwaggerBaseVo(List<SwaggerBaseVo> swaggerBaseVo) {
        this.swaggerBaseVo = swaggerBaseVo;
    }

    public String getSwaggerBaseJson() {
        return swaggerBaseJson;
    }

    public void setSwaggerBaseJson(String swaggerBaseJson) {
        this.swaggerBaseJson = swaggerBaseJson;
    }
}
