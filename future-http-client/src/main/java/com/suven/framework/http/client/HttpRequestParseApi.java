package com.suven.framework.http.client;

import com.suven.framework.http.util.HttpParamsUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 作者 : suven
 * CreateDate 创建时间: 2023-02-03
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  提供给业务方便使用的api
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.66os.com
 **/


public class HttpRequestParseApi implements  Serializable {

   private static List<String> convertKeyList = Arrays.asList("userId","accessToken");
    /**
     * body 类型请求实现,headers头部请求参数对象为空,
     * 且头部请求参数不参与加密,convertKeyList 通过请求头信息,
     * 转换到body参加进行加密的参数信息,默认为userId,和accessToken
     * 且内容不否需要decode转码
     * @param body 请求内容信息对象
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    public static HttpRequestParams getHttpRequestParams(Object body, String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestApiParam(null,false,body,convertKeyList,false,md5Key);
        return httpRequestParams;
    }
    /**
     * body 类型请求实现,headers头部请求参数对象为空,且头部请求参数不参与加密
     * @param body 请求内容信息对象
     * @param decode 内容是否需要decode转码
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    public static HttpRequestParams getHttpRequestParams(Object body, boolean decode, String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestApiParam(null,false,body,convertKeyList,decode,md5Key);
        return httpRequestParams;
    }
    /**
     * headers, body 表单对象提交类型请求实现 ,头部请求参数不参与加密
     * @param headers 头部请求参数对象
     * @param body 请求内容信息对象
     * @param decode 内容是否需要decode转码
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    public static HttpRequestParams getHttpRequestParams(Object headers, Object body, boolean decode, String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestApiParam(headers,false,body,convertKeyList,decode,md5Key);
        return httpRequestParams;
    }
    /**
     * headers, body 表单对象提交类型请求实现 ,头部请求参数不参与加密
     * @param headers 头部请求参数对象
     * @param body 请求内容信息对象
     *  @param convertKeyList 通过请求头信息,转换到body参加进行加密的参数信息,默认为userId,和accessToken
     * @param decode 内容是否需要decode转码
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    public static HttpRequestParams getHttpRequestParams(Object headers, Object body, List<String> convertKeyList, boolean decode, String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestApiParam(headers,false,body,convertKeyList,decode,md5Key);
        return httpRequestParams;
    }
    /**
     * headers, body 表单对象提交类型请求实现 ,头部请求参数不参与加密
     * @param headers 头部请求参数对象
     * @param body 请求内容信息对象
     *  @param convertKeyList 通过请求头信息,转换到body参加进行加密的参数信息,默认为userId,和accessToken
     * @param decode 内容是否需要decode转码
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    public static HttpRequestParams getHttpRequestParams(Object headers, boolean headersIsSignParam
            , Object body, List<String> convertKeyList, boolean decode, String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestApiParam(headers,headersIsSignParam,body,convertKeyList,decode,md5Key);
        return httpRequestParams;
    }

    //json or url
    /**
     * data 为json 类型请求实现,body内容的请求数据格式,头部请求参数对象且头部请求参数不参与加密,true时为url格式,默认为url请求,内容不需要decode转码
     * @param body 请求内容信息,可以为对像json格式内容,也是url格式(a=123&b=ABC)转换成对应加密码
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    public static HttpRequestParams getHttpRequestString(String  body, String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestStringParam(null,false,body,true,convertKeyList,false,md5Key);
        return httpRequestParams;
    }
    /**
     * data 为json 类型请求实现,body内容的请求数据格式,头部请求参数对象且头部请求参数不参与加密,内容不需要decode转码
     * @param body 请求内容信息,可以为对像json格式内容,也是url格式(a=123&b=ABC)转换成对应加密码
     * @param isUrlNotJson = true 时为url格式,否则为json 类型
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    public static HttpRequestParams getHttpRequestString(String  body, boolean isUrlNotJson, String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestStringParam(null,false,body,isUrlNotJson,convertKeyList,false,md5Key);
        return httpRequestParams;
    }
    /**
     * data 为json 类型请求实现,body内容的请求数据格式,头部请求参数对象且头部请求参数不参与加密,true时为url格式,默认为url请求
     * @param body 请求内容信息,可以为对像json格式内容,也是url格式(a=123&b=ABC)转换成对应加密码
     * @param decode 内容是否需要decode转码
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    public static HttpRequestParams getHttpRequestString(String  body, boolean isUrlNotJson, boolean decode, String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestStringParam(null,false,body,true,convertKeyList,decode,md5Key);
        return httpRequestParams;
    }
    /**
     * data 为json 类型请求实现,body内容的请求数据格式,头部请求参数不参与加密,true时为url格式,默认为url请求
     * @param headers 头部请求参数对象
     * @param body 请求内容信息,可以为对像json格式内容,也是url格式(a=123&b=ABC)转换成对应加密码
     * @param decode 内容是否需要decode转码
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    public static HttpRequestParams getHttpRequestString(Object headers, String  body, boolean decode, String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestStringParam(headers,false,body,true,convertKeyList,decode,md5Key);
        return httpRequestParams;
    }

    /**
     * data 为json 类型请求实现,body内容的请求数据格式,true时为url格式,默认为url请求
     * @param headers 头部请求参数对象
     * @param headersIsSignParam 头部请求参数是否参与加密
     * @param body 请求内容信息,可以为对像json格式内容,也是url格式(a=123&b=ABC)转换成对应加密码
     * @param decode 内容是否需要decode转码
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    public static HttpRequestParams getHttpRequestString(Object headers, boolean headersIsSignParam, String  body, boolean decode, String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestStringParam(headers,headersIsSignParam,body,true, convertKeyList,decode,md5Key);
        return httpRequestParams;
    }

    /**
     * data 为json 类型请求实现,body内容的请求数据格式,true时为url格式,默认为url请求
     * @param headers 头部请求参数对象
     * @param headersIsSignParam 头部请求参数是否参与加密
     * @param body 请求内容信息,可以为对像json格式内容,也是url格式(a=123&b=ABC)转换成对应加密码
     * @param convertKeyList 通过请求头信息,转换到body参加进行加密的参数信息,默认为userId,和accessToken
     * @param decode 内容是否需要decode转码
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    public static HttpRequestParams getHttpRequestString(Object headers, boolean headersIsSignParam, String  body, List<String> convertKeyList, boolean decode, String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestStringParam(headers,headersIsSignParam,body,true, convertKeyList,decode,md5Key);
        return httpRequestParams;
    }
    /**
     * data 为json 类型请求实现
     * @param headers 头部请求参数对象
     * @param headersIsSignParam 头部请求参数是否参与加密
     * @param body 请求内容信息对象
     * @param convertKeyList 通过请求头信息,转换到body参加进行加密的参数信息,默认为userId,和accessToken
     * @param decode 内容是否需要decode转码
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    private static HttpRequestParams getHttpRequestApiParam(Object headers, boolean headersIsSignParam, Object body, List<String> convertKeyList, boolean decode, String md5Key)  {
        //1.请求参数对象转换排序的map树
        Map<String, String> dataMap = new TreeMap<>();
        Map<String, String> headMap = null;
        Map<String, String> bodyMap = null;
        if(null != headers){
            headMap = HttpParamsUtil.toMap(headers,decode);
            if(headersIsSignParam && headMap != null && !headMap.isEmpty() ){
                dataMap.putAll(headMap);
            }
        }
        if(null != body){
            bodyMap = HttpParamsUtil.toMap(body,decode);
            if(bodyMap != null && !bodyMap.isEmpty()){
                dataMap.putAll(bodyMap);
            }
        }
        //如果头像信息不参与加密,获取头部的用户信息,存放到body中,进行加密码,防止用户信息被篡改；保证数据安全
        if( null != convertKeyList && !convertKeyList.isEmpty() ){
            convertUserInfoToBodyByHeaders(headMap,bodyMap,convertKeyList);
        }
        //2.请求的map 树转换url get请求规范的字符串
        String urlParam = HttpParamsUtil.getSortedMapSign(dataMap, Arrays.asList("cliSign"));
        String signParam = HttpParamsUtil.paramMd5length(urlParam,md5Key,8,24);

        HttpRequestParams httpRequestParams = HttpRequestParams.build(bodyMap,headMap,decode,urlParam,signParam);
        return httpRequestParams;
    }


    /**
     * data 为json 类型请求实现
     * @param headers 头部请求参数对象
     * @param headersIsSignParam 头部请求参数是否参与加密
     * @param body 请求内容信息,可以为对像json格式内容,也是url格式(a=123&b=ABC)转换成对应加密码
     * @param convertKeyList 通过请求头信息,转换到body参加进行加密的参数信息,默认为userId,和accessToken
     * @param isUrlNotJson body内容的请求数据格式,true时为url格式,默认为
     * @param decode 内容是否需要decode转码
     * @param md5Key 自定义的加密码字符串内容
     * @return
     */
    private static HttpRequestParams getHttpRequestStringParam(Object headers, boolean headersIsSignParam, String body, boolean isUrlNotJson, List<String> convertKeyList, boolean decode, String md5Key)  {
        //1.请求参数对象转换排序的map树
        Map<String, String> dataMap = new TreeMap<>();
        Map<String, String> headMap = null;
        Map<String, String> bodyMap = null;

        if(null != headers){
            headMap = HttpParamsUtil.toMap(headers,decode);
            if(headersIsSignParam && headMap != null && !headMap.isEmpty()){
                dataMap.putAll(headMap);
            }
        }
        if(null != body){
            bodyMap = HttpParamsUtil.toMapByString(body,decode,isUrlNotJson);
            if(bodyMap != null && !bodyMap.isEmpty()){
                dataMap.putAll(bodyMap);
            }
        }
        //如果头像信息不参与加密,获取头部的用户信息,存放到body中,进行加密码,防止用户信息被篡改；保证数据安全
        if( null != convertKeyList && !convertKeyList.isEmpty() ){
            convertUserInfoToBodyByHeaders(headMap,bodyMap,convertKeyList);
        }
        //2.请求的map 树转换url get请求规范的字符串
        String urlParam = HttpParamsUtil.getSortedMapSign(dataMap, Arrays.asList("cliSign"));
        String signParam = HttpParamsUtil.paramMd5length(urlParam,md5Key,8,24);
        HttpRequestParams httpRequestParams = HttpRequestParams.build(bodyMap,headMap,decode,urlParam,signParam);
        return httpRequestParams;
    }






    private static boolean convertUserInfoToBodyByHeaders(Map<String, String> headMap, Map<String, String> bodyMap ,List<String> convertKeyList){
        if(null == headMap || headMap.isEmpty()) {
            return false;
        }
        if(null == bodyMap  || bodyMap.isEmpty()){
            return false;
        }
        if(convertKeyList == null){
            return false;
        }
        convertKeyList.forEach(key -> {
            String value =  headMap.get( key);
            bodyMap.put(key,value);
        });
        return true;
    }

}
