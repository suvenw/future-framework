package com.suven.framework.util.createcode.swagger;


import com.suven.framework.http.api.ApiDesc;

public class SwaggerParameterBean {
//     "name":"area",
//             "in":"query",
//             "description":"广告地区",
//             "required":false,
//             "type":"string"

//    SwaggerParameterBean

    @ApiDesc(value = "字段介绍说明,name ")
    private String name;
    @ApiDesc(value = "字段介绍说明, 查询")
    private String in = "query";
    private String description;
    @ApiDesc(value = "字段介绍说明, 是否必须字段")
    private boolean required;
    @ApiDesc(value = "字段介绍说明, 请求参数类型")
    private String type;
    @ApiDesc(value = "字段介绍说明, 请求参数类型")
    private String defaultValue;


    public static SwaggerParameterBean build(){
        return  new SwaggerParameterBean();
    }

    public static SwaggerParameterBean init(){
        return init("area","广告地区","string");
    }
    public static SwaggerParameterBean init(String name, String desc,String type){
        return build().setName(name).setDescription(desc).setType(type).setIn("query").setRequired(false);
    }

    public String getName() {
        return name;
    }

    public SwaggerParameterBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getIn() {
        return in;
    }

    public SwaggerParameterBean setIn(String in) {
        this.in = in;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SwaggerParameterBean setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public SwaggerParameterBean setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public String getType() {
        return type;
    }

    public SwaggerParameterBean setType(String type) {
        this.type = type;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
