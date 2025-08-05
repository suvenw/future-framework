package com.suven.framework.util.createcode.swagger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 "definitions":{
         "Car":{
         "type":"object",
         "properties":{
         "carInfo":{
         "type":"string",
         "description":"车信息"
         },
         "carName":{
         "type":"string",
         "description":"车名称"
         },
         "id":{
         "type":"integer",
         "format":"int32",
         "description":"车标识"
         }
         },
         "title":"Car"
         },
         "Result«类»":{
         "type":"object",
         "properties":{
         "code":{
         "type":"integer",
         "format":"int32"
         },
         "data":{
         "$ref":"#/definitions/类"
         },
         "msg":{
         "type":"string"
         }
         },
         "title":"Result«类»"
         },
         "类":{
         "type":"object",
         "properties":{
         "age":{
         "type":"integer",
         "format":"int32",
         "description":"年龄"
         },
         "car":{
         "description":"用户车信息",
         "$ref":"#/definitions/Car"
         },
         "comm":{
         "type":"integer",
         "format":"int32",
         "description":"公司"
         },
         "name":{
         "type":"string",
         "description":"姓名"
         },
         "sex":{
         "type":"integer",
         "format":"int32",
         "description":"性别"
         }
         },
         "title":"类"
         }
         }
**/
public class SwaggerResponseParameterMap extends LinkedHashMap<String, SwaggerResponseResult> {


    public static SwaggerResponseParameterMap build(){
        return new SwaggerResponseParameterMap();
    }

    /**
     * 返回通用的 ResponseResultVo对象;
     * @param className
     * @return
     */
    public SwaggerResponseParameterMap responseResultVo(String className){

        String title = "Result«"+className+"»";
        Map<String, SwaggerResponseProperty> resultData =   this.getResultData(className);
        SwaggerResponseResult result = SwaggerResponseResult.build().setType("object").setTitle(title).setProperties(resultData);
        super.put(title,result);
        return this;
    }

    /**
     * 结果对象类
     * @param className
     * @return
     */
    public SwaggerResponseParameterMap responseDataVo(String className, Map<String, SwaggerResponseProperty> responseData){
        String title =  className;
        SwaggerResponseResult result = SwaggerResponseResult.build().setType("object").setTitle(title).setProperties(responseData);
        super.put(title,result);
        return this;
    }

    public Map<String, SwaggerResponseProperty> getResultData(String className){
        Map<String, SwaggerResponseProperty> data = new HashMap<>();
        data.put("code", SwaggerResponseProperty.build("integer","错误码: 0.为成功,其它为错误"));
        data.put("msg", SwaggerResponseProperty.build("String","错误信息说明"));
        data.put("data", (SwaggerResponseProperty.build().setType(className).setRef(className)));
        return data;
    }









}
