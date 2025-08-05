package com.suven.framework.util.createcode.swagger;


import java.util.LinkedHashMap;


/**
 * "200":{
*      "description":"OK",
*       "schema":{
*           "$ref":"#/definitions/ResponseResultVo«ClassSimpleName»"
*         }
*     },
 */
public class SwaggerResponseResultMap extends LinkedHashMap {


    public static SwaggerResponseResultMap build(){
        return new SwaggerResponseResultMap();
    }

    public SwaggerResponseResultMap put(String key , String value ){
        super.put(key, value);
        return this;
    }

//"description":"OK",
//        "schema":{
//        "$ref":"#/definitions/Result«类»"
//    }

    public SwaggerResponseResultMap setResponse(String responseUrl) {
        SwaggerResponseShipBean bean = new SwaggerResponseShipBean();
//        bean.setType("Object");
        bean.put("Result",responseUrl);
        this.put("200",bean);
        return this;
    }
    public SwaggerResponseResultMap setCode() {
        if(this.get("200") == null){
            this.put("200","OK");
        }
//        this.put("201","Created");
//        this.put("401","Unauthorized");
//        this.put("403","Forbidden");
//        this.put("404","Not Found");
        return this;
    }



}
