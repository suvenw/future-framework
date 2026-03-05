package com.suven.framework.util.http;

import com.alibaba.fastjson2.JSON;
import com.suven.framework.util.constants.Env;
import com.suven.framework.util.crypt.UrlParamSign;
import com.suven.framework.util.json.JsonUtils;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by joven on 2017/9/8.
 */
public class OkHttpClients extends UrlParamSign {

    private static Logger logger = LoggerFactory.getLogger(OkHttpClients.class);
    private static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient instance;
    private static String urlAppPrefix;
    private static final int HTTP_CONNECT_MAX_REQUESTS_PERHOST = 3;//http连接3秒超时
    private static final int HTTP_CONNECT_TIME_OUT = 3;//http连接3秒超时
    private static final int HTTP_CONNECT_TIMEOUT_TEST = 30;//测试http连接超时为60秒



    private OkHttpClients(){}

    private  static OkHttpClient instance(){
        if(null != instance){
            return instance;
        }
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(HTTP_CONNECT_MAX_REQUESTS_PERHOST);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        int http_time_out = HTTP_CONNECT_TIME_OUT;
        if(Env.isDev()|| Env.isTest()){
            http_time_out = HTTP_CONNECT_TIMEOUT_TEST;
        }
        builder.connectTimeout(http_time_out, TimeUnit.SECONDS)
                .readTimeout(http_time_out, TimeUnit.SECONDS)
                .writeTimeout(http_time_out, TimeUnit.SECONDS).
                dispatcher(dispatcher);
        instance = builder.build();
        return  instance;
    }

    protected static String getUserAgent() {
        return  "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)";
    }


    private static Request.Builder requestBuilder(){
        Request.Builder request = new Request
                .Builder()
                .removeHeader("User-Agent").addHeader("User-Agent", getUserAgent());
        return request;
    }

    /**
     * get请求
     *
     * @param url      请求url
     * @param params   需要附加的参数
     */
    public static <T>T getHttp(String url, Object params,Class<T> clazz) {
        byte[] result = getHttp(url,params);
        if(null != result){
            try {
                return JSON.parseObject(result, clazz);
            } catch (Exception e) {
                logger.warn("error http request  url:{},params:{} , response result：{} , Exception message:{} ", url, JsonUtils.toJson(params),result, e);
                return null;
            }
        }return null;
    }
    /**
     * get请求
     *
     * @param url      请求url
     * @param params   需要附加的参数
     */
    public static byte[] getHttp(String url, Object params) {
        String getParam = null;
        try {
            long start = System.currentTimeMillis();
            if (!url.startsWith("http")){
                url = urlAppPrefix + url ;
            }
            OkHttpClient client = instance();
            getParam = getValue(params);
            getParam = getParam == null ? "": getParam;
            String getUrl = toUTF8UriString(url + getParam);
            logger.info("【getHttp】 request urlParam------>{}{}  ", url,getParam);
            Request.Builder requestBuilder = requestBuilder().url(getUrl);
            //可以省略，默认是GET请求
            requestBuilder.method("GET",null);
            Request request = requestBuilder.build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                byte[] ret = response.body().bytes();
                logger.info("【getHttp({}ms)】 request urlParam------>{}{}  result------>{}", System.currentTimeMillis() - start, url,getParam, ret);
                return ret;
            }else {
                logger.info("【getHttp({}ms)】 request urlParam------>{}{}  response.code------>{}", System.currentTimeMillis() - start, url,getParam, response.code());
            }
        } catch (IOException e) {
            logger.warn("error http request  request urlParam------>{}{} ,  Exception message:{}", url, getParam==null?JsonUtils.toJson(params):getParam, e);
        }
        return null;
    }

    /**
     * get请求
     *
     * @param url      请求url
     * @param params   需要附加的参数
     */
    public static InputStream getHttpInputStream(String url,Object params) {
        String getParam = null;
        try {
            if(null == url || "".equals(url)){
                return null;
            }
            long start = System.currentTimeMillis();

            OkHttpClient client = instance();
            getParam = getValue(params);
            getParam = getParam == null ? "": getParam;
            String getUrl = toUTF8UriString(url + getParam);
            logger.info("【getHttp】 request urlParam------>url:[{}], getParam[{}]  ", url,getParam);
            Request.Builder requestBuilder = requestBuilder().url(getUrl);
            //可以省略，默认是GET请求
            requestBuilder.method("GET",null);
            Request request = requestBuilder.build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                InputStream ret = response.body().byteStream();
                logger.info("【getHttp({}ms)】 request urlParam------>{}{}  result------>{}", System.currentTimeMillis() - start, url,getParam,"Successful");
                return ret;
            }else {
                logger.info("【getHttp({}ms)】 request urlParam------>{}{}  response.code------>{}", System.currentTimeMillis() - start, url,getParam, response.code());
            }
        } catch (IOException e) {
            logger.warn("error http request  request urlParam------>{}{} ,  Exception message:{}", url, getParam==null?JsonUtils.toJson(params):getParam, e);
        }
        return null;
    }
    /**
     * get请求
     *
     * @param url      请求url
     * @param params   需要附加的参数
     */
    public static <T>T postHttp(String url, Object params,Class<T> clazz) {
        String result = postHttp(url,params);
        if(null != result){
            try {
                return JsonUtils.parseObject(result, clazz);
            } catch (Exception e) {
                logger.warn("error http request  url:{},params:{} , response result：{} , Exception message:{}  ", url, JsonUtils.toJson(params),result, e);
                return null;
            }
        }return null;
    }
    public static String postHttp(String url, Object params) {
        try {
            long start = System.currentTimeMillis();
            if (!url.startsWith("http")){
                url = urlAppPrefix + url ;
            }
            OkHttpClient client = instance();
            RequestBody body = postParam(params);

            Request request = requestBuilder().url(url).post(body).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String ret = response.body().string();
                logger.info("【post({}ms)】  url------>{}  params------>{}  result------> {}", System.currentTimeMillis() - start, url, params, ret);
                return ret;
            }

        } catch (Exception e) {
            logger.warn("error http request  url:{},params:{} ,  Exception message:{}", url, JsonUtils.toJson(params), e);
            return null;
        }
        return null;
    }

    /**
     * 异步get请求
     */
    public  static <T>T getAsynHttp(String url, Object params,Class<T> clazz) {
        String result = "";
        if (!url.startsWith("http")){
            url = urlAppPrefix + url ;
        }
        OkHttpClient httpClient = instance();
        String getParam = getValue(params);
        String getUrl = toUTF8UriString( url + getParam);

        Request.Builder requestBuilder = requestBuilder().url(getUrl);
        //可以省略，默认是GET请求
        requestBuilder.method("GET",null);
        Request request = requestBuilder.build();
        Call call = httpClient.newCall(request);
        OkHttpCallback<T> back = new OkHttpCallback<>(clazz);
        call.enqueue(back);
        return  back.get();
    }




    public <T>T  postAsynHttp(String url ,Object params,Class<T> clazz) {
       OkHttpClient client =  instance();
        if (!url.startsWith("http")){
            url = urlAppPrefix + url ;
        }
        RequestBody body = postParam(params);
        Request request = requestBuilder().url(url).post(body).build();
        Call call = client.newCall(request);
        OkHttpCallback<T> back = new OkHttpCallback<>(clazz);
        call.enqueue(back);
        return  back.get();
    }





}
