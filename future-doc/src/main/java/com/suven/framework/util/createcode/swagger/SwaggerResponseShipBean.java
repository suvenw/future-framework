package com.suven.framework.util.createcode.swagger;

import java.util.HashMap;
import java.util.Map;

public class SwaggerResponseShipBean {
//    "description":"OK",
//            "schema":{
//        "$ref":"#/definitions/Result«类»"
//    }

    private String description = "OK";
//    private String type ;
    private Map<String,String> schema = new HashMap<>();

    public String getDescription() {
        return description;
    }

    public SwaggerResponseShipBean setDescription(String description) {
        this.description = description;
        return this;
    }

    public Map<String, String> getSchema() {
        return schema;
    }

//    public String getType() {
//        return type;
//    }
//
//    public SwaggerResponseShipBean setType(String type) {
//        this.type = type;
//        return this;
//    }

    public SwaggerResponseShipBean setSchema(Map<String, String> schema) {
        this.schema = schema;
        return this;
    }

    public SwaggerResponseShipBean put(String  result, String responseClass) {
        String value = "#/definitions/"+result+"«"+responseClass+"»";
        this.schema.put("$ref",value);
        return this;
    }

}
