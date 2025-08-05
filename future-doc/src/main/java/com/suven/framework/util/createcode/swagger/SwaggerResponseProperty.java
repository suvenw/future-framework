package com.suven.framework.util.createcode.swagger;

import java.util.TreeMap;

public class SwaggerResponseProperty extends TreeMap {


    public static SwaggerResponseProperty build(){
        return new SwaggerResponseProperty();
    }

    public   static SwaggerResponseProperty build(String type, String description ){
       return build().setType(type).setDescription(description);
    }


        public SwaggerResponseProperty setType(String type) {
            this.put("type", type);
//            this.put("format", type);
            return this;
        }


        public SwaggerResponseProperty setDescription(String description) {
            this.put("description", description);
            return this;
        }

        public SwaggerResponseProperty setRef(String className) {
            this.put("$ref", "#/definitions/"+ className);
            return this;
        }

    }