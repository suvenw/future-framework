package com.suven.framework.http.interceptor;//package com.suven.framework.http.interceptor;
//
//
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
////@Component
//public class InterServerConfigSetting {
//    private String exclude;
//    private String path = "/**";
//    private String excludePath;
//
//    public String getExclude() {
//        return exclude;
//    }
//
//    public InterServerConfigSetting setExclude(String exclude) {
//        this.exclude = exclude;
//        return this;
//    }
//
//    public List<String> getPaths() {
//        return converterPath(path);
//    }
//
//    public String getPath() {
//        return path;
//    }
//    public void setPath(String path) {
//        if(null != path){
//        this.path = path;
//        }
//    }
//
//    public String getExcludePath() {
//        return excludePath;
//    }
//
//    public void setExcludePath(String excludePath) {
//        if(null != excludePath){
//        this.excludePath = excludePath;
//        }
//    }
//
//    public List<String> getExcludePaths() {
//        return converterPath(excludePath);
//    }
//
//    public boolean isExclude(int order){
//
//        List<String> list = converterPath(exclude);
//        if(list.isEmpty()){
//            return false;
//        }
//        return list.contains(String.valueOf(order));
//    }
//
//    private List<String> converterPath(String path){
//        if(path == null || "".equals(path)){
//            return new ArrayList<>();
//        }
//        String[] inter = path.split(";|,");
//        if(inter == null || inter.length == 0){
//            return new ArrayList<>();
//        }
//        return new ArrayList<>(Arrays.asList(inter));
//    }
//}
