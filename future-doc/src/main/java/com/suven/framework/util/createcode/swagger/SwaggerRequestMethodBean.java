package com.suven.framework.util.createcode.swagger;

import java.util.ArrayList;
import java.util.List;

//数据为;key-value; object  SwaggerRequestMethodBean
public class SwaggerRequestMethodBean {
//            "tags":Array[1],
//            "summary":"add",
//            "operationId":"addUsingPOST",
//            "consumes":Array[1],
//            "produces":Array[1],
//            "parameters":Array[18],
//            "responses":Object{...},
//            "deprecated":false,
 //           "definitions":Object{...}

    public static SwaggerRequestMethodBean build(){
        return new SwaggerRequestMethodBean();
    }
    public static SwaggerRequestMethodBean init(){
        return build().setConsumes("application/json").setProduces("*/*").setDeprecated(true);
    }

    public static SwaggerRequestMethodBean init2(){
        return build().setTags("banner-info-web-controller").setSummary("add").setOperationId("addUsingPOST")
                .setConsumes("application/json").setProduces("*/*").setParameters(SwaggerParameterBean.init())
                .setResponses(SwaggerResponseResultMap.build()).setDeprecated(true);
    }

    private List<String> tags;
    private String summary;
    private String operationId;
    private List<String> consumes;
    private List<String>  produces;
    private List<SwaggerParameterBean>  parameters;
    private boolean deprecated;
    private SwaggerResponseResultMap responses;
    private String description;
    private String author;

    public List<String> getTags() {
        return tags;
    }

    public SwaggerRequestMethodBean setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public SwaggerRequestMethodBean setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getOperationId() {
        return operationId;
    }

    public SwaggerRequestMethodBean setOperationId(String operationId) {
        this.operationId = operationId;
        return this;
    }

    public List<String> getConsumes() {
        return consumes;
    }

    public SwaggerRequestMethodBean setConsumes(List<String> consumes) {
        this.consumes = consumes;
        return this;
    }

    public List<String> getProduces() {
        return produces;
    }

    public List<SwaggerParameterBean> getParameters() {
        return parameters;
    }

    public SwaggerRequestMethodBean setParameters(List<SwaggerParameterBean> parameters) {
        this.parameters = parameters;
        return this;
    }

    public SwaggerRequestMethodBean setProduces(List<String> produces) {
        this.produces = produces;
        return this;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public SwaggerRequestMethodBean setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
        return this;
    }

    public SwaggerResponseResultMap getResponses() {
        return responses;
    }

    public SwaggerRequestMethodBean setResponses(SwaggerResponseResultMap responses) {
        this.responses = responses;
        return this;
    }

    public SwaggerRequestMethodBean setConsumes(String consume) {
        if(this.consumes == null){
            this.consumes = new ArrayList<String>();
        }this.consumes.add( consume);
        return this;
    }


    public SwaggerRequestMethodBean setProduces(String produce) {
        if(this.produces == null){
            this.produces = new ArrayList<>();
        }this.produces.add( produce);
        return this;
    }

    public SwaggerRequestMethodBean setTags(String tag) {
        if(this.tags == null){
            this.tags = new ArrayList<>();
        }this.tags.add( tag);
        return this;
    }

    public SwaggerRequestMethodBean setParameters(SwaggerParameterBean parameter) {
        if(this.parameters == null){
            this.parameters = new ArrayList<>();
        }this.parameters.add( parameter);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SwaggerRequestMethodBean setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public SwaggerRequestMethodBean setAuthor(String author) {
        this.author = author;
        return this;
    }
}
