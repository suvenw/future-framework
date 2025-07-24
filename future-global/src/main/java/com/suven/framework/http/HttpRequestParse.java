package com.suven.framework.http;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2022-03-29
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  HttpServletRequestParamter请求解释实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
public class HttpRequestParse implements  Serializable {


    public static  <T> T parseFrom(Map<String, Object>  map, Class<T> clazz) throws Exception{
        T iRequestVo =  JsonParse.parseFrom(map,clazz);
        return iRequestVo;
    }



    public static <T> T parseJson(String json, Class<T> clazz) {
        T iRequestVo =   JsonParse.parseJson(json,clazz);
        return iRequestVo;
    }

    public static <T>T requestFrom(HttpServletRequest request, Class<T> clazz) {
        try {
            Map map =  request.getParameterMap();
            T iRequestVo = JsonParse.parseFrom(map,clazz);
//            HttpParameterValidator.checkValidatorParameter(iRequestVo);
            return iRequestVo;
        }catch (RuntimeException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("参数转换类型");
        }
    }
    public static <T>T  requestJson(HttpServletRequest request,Class<T> clazz) {
        try {
            String json = postJsonRequestBody(request);
            T iRequestVo =   JsonParse.parseJson(json,clazz);
//            HttpParameterValidator.checkValidatorParameter(iRequestVo);
            return iRequestVo;
        }catch (RuntimeException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("参数转换类型");
        }


    }

    /**
     * 在请求头添加参数:
     * 1. Content-Type=application/json
     * 2. Content-Json=true
     * 获取json请求参数,返回 map 对象 **/
    public static String postJsonRequestBody(HttpServletRequest servletRequest) {

        try {
            String json = IOUtils.toString(servletRequest.getInputStream(), "UTF-8");
            if(null == json || "" .equals(json )){
                return null;
            }
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}