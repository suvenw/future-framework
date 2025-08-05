package com.suven.framework.util.createcode.swagger;

import java.util.Map;

public  class SwaggerResponseResult {
        private String type;
        private  String title;
        private Map<String, SwaggerResponseProperty> properties;

        public static SwaggerResponseResult build(){
            return new SwaggerResponseResult();
        }

        public String getType() {
            return type;
        }

        public SwaggerResponseResult setType(String type) {
            this.type = type;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public SwaggerResponseResult setTitle(String title) {
            this.title = title;
            return this;
        }

        public Map<String, SwaggerResponseProperty> getProperties() {
            return properties;
        }

        public SwaggerResponseResult setProperties(Map<String, SwaggerResponseProperty> properties) {
            this.properties = properties;
            return this;
        }
    }