package com.suven.framework.test.junit;

import com.suven.framework.util.crypt.SignParam;
import com.suven.framework.util.json.JsonFormatTool;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.test.rule.annotation.UserAnno;
import com.suven.framework.test.rule.util.TokenData;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
public class HttpClientTest extends BaseConfigTest {

    private static String http_env = "test";

    private static final String HTTP_POJECT = "/top";


    public static void setEnv(Class clazz){
        try {
            Method  m = clazz.getMethod("beforeTest");
            UserAnno anno = m.getAnnotation(UserAnno.class);
            if(anno != null){
                http_env = anno.env();
                userId = anno.userId();
                token = anno.token();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static final byte[] input2byte(File file )
            throws IOException {
        final   InputStream inStream = new FileInputStream(file);
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        if (inStream != null) {
            inStream.close();
        }
        return in2b;
    }

    public static HttpHost getHttpProxy(String url){
        HttpHost proxy = null;
        try {
            //http://bopaiapp.com/json/cdn/address/get_address_ship_list?appid=1005&pageNo=1&pageSize=100&platform=1&userId=27&version=7046
            String ip = InetAddress.getLocalHost().getHostAddress();
            String hostname = ip;
            int port = 80;
            if("prod".equals(http_env)){
                hostname = "online.com";//线上服务ip或域名
            }
            if("stage".equals(http_env)){
                hostname = "120.24.214.70";//灰度服务ip或域名
                port =9020;
            }
            if("test".equals(http_env)){
                hostname = "192.168.25.200";//测试服务ip或域名
                port =9020;
            }
            if("dev".equals(http_env)){
                String[] server = url.split("/");
                port = new JettyServerPortSettings().getPort(server[2]);
                port = 9900;
                hostname = "127.0.0.1";
            }
            if(url.contains("/upload")){
                hostname = "192.168.0.103";
                port =9030;
            }
            if(url.contains("/statistic")){
                hostname = "127.0.0.1";
                port =9120;
            }
            if(url.contains("/media")){
                hostname = "127.0.0.1";
                port =9110;
            }
            proxy = new HttpHost(hostname,port,"HTTP");
            if(proxy == null){
                proxy =  new HttpHost("127.0.0.1",8080,"HTTP");
            }

        } catch (Exception e) {
            e.printStackTrace();
            proxy =  new HttpHost("127.0.0.1",8080,"HTTP");
        }

//        System.out.println("HttpHost : " + proxy.toString());
        return proxy;
        // 创建代理服务器
    }



    private static Map<String,Object> map = new  TreeMap<>();
    /**
     * HttpClient连接SSL 
     */
    @SuppressWarnings("unchecked")
//    public static void sslGet(String requestParam ) {
//        CloseableHttpClient httpclient = null;
//        try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            FileInputStream instream = new FileInputStream(new File(sshKeyStorePath));
//            try {
//                // 加载keyStore d:\\tomcat.keystore
//                trustStore.load(instream,  sshKeystorePass.toCharArray());
//            } catch (CertificateException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    instream.close();
//                } catch (Exception ignore) {
//                }
//            }
//            // 相信自己的CA和所有自签名的证书
//            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
//            // 只允许使用TLSv1协议
//            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
//                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
//            // 创建http请求(get方式)
//            String httpgetparam =  requestURLBase +  requestURL + "?" + requestParam;
//            System.out.println("executing request  url " + httpgetparam);
//            HttpGet httpget = new HttpGet(httpgetparam);
//            System.out.println("executing request" + httpget.getRequestLine());
//            CloseableHttpResponse response = httpclient.execute(httpget);
//            try {
//                HttpEntity entity = response.getEntity();
//                System.out.println("----------------------------------------");
//                System.out.println(response.getStatusLine());
//                if (entity != null) {
//                    System.out.println("Response content length: " + entity.getContentLength());
//                    System.out.println(EntityUtils.toString(entity));
//                    EntityUtils.consume(entity);
//                }
//            } finally {
//                response.close();
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } finally {
//            if (httpclient != null) {
//                try {
//                    httpclient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    /**
     * post方式提交表单（模拟用户登录请求） 
     */
    public static  String postForm(String requestURL ,Map<Object,Object> map) {
        String result = null;
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost    
        HttpPost httppost = new HttpPost(requestURL);
        httppost.addHeader("Accept","application/json;charset=utf-8");
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for(Object key : map.keySet()){
            String pkey = key instanceof byte[] ? new String((byte[])key): String.valueOf(key);
            String pv = map.get(key) instanceof byte[] ? new String((byte[])map.get(key)): String.valueOf(map.get(key));
            formparams.add(new BasicNameValuePair(pkey, pv));
        }

//        formparams.add(new BasicNameValuePair("password", "123456"));
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            HttpHost proxy = getHttpProxy(requestURL);
            System.out.println("executing request " + proxy.toURI());
            CloseableHttpResponse response = httpclient.execute(proxy,httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("--------------------------------------");
                    result = EntityUtils.toString(entity, "UTF-8");
                    System.out.println("Response content: " + JsonFormatTool.formatJson( result));
                    System.out.println("--------------------------------------");
                    return result;
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源    
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果 
     */
    public static String post(String requestURL ,Map<String,Object> map) {
        String result = null;
        // 创建默认的httpClient实例.
        requestURL = appendUrl(requestURL);
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建httppost    
        HttpPost httppost = new HttpPost(requestURL);
        httppost.addHeader("Accept","application/json;charset=utf-8");
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        String postParam = "";
//        formparams.add(new BasicNameValuePair("type", "house"));
        for(Object key : map.keySet()){
            String pkey = key instanceof byte[] ? new String((byte[])key): String.valueOf(key);
            String pv = map.get(key) instanceof byte[] ? new String((byte[])map.get(key)): String.valueOf(map.get(key));
            formparams.add(new BasicNameValuePair(pkey, pv));
        }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request start post  " + "====================================" );

            HttpHost proxy = getHttpProxy(requestURL);

            if(!requestURL.contains("upload")) {
                System.out.println("executing request post url: " + proxy.toURI()+httppost.getURI());
                System.out.println("executing request post param:  " + JsonUtils.toJson(map));
            }
            System.out.println("executing request end post  " + "====================================" );
            CloseableHttpResponse response = httpclient.execute(proxy,httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    System.out.println("------------ executing request post start --------------------------");
                    result = EntityUtils.toString(entity, "UTF-8");
                    System.out.println("Response content: " + JsonFormatTool.formatJson( result));
                    System.out.println("------------ executing request post end --------------------------");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源    
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static Proxy getProxy(String requestUrl) throws Exception {
        HttpHost httpHost =  getHttpProxy(requestUrl);
        String host =  httpHost.getHostName();
//       host  = host.contains(Proxy.Type.HTTP.name().toLowerCase()) ? host : "http://"+host;
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, httpHost.getPort()));
        return proxy;
    }

    private static OkHttpClient getOkClient(){
        OkHttpClient client = new OkHttpClient().newBuilder().
                connectTimeout(1200, TimeUnit.MINUTES).readTimeout(12000, TimeUnit.SECONDS)
                // 解决内存溢出问题
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .build();
        return client;
    }

    public static String postOk(String requestUrl,Map<String,Object> requestParam){
        try {
            requestUrl = appendUrl(requestUrl);
            HttpHost httpHost =  getHttpProxy(requestUrl);
            FormBody.Builder builder = new FormBody.Builder();
            if (requestParam != null && requestParam.keySet().size() > 0) {
                for (String key : requestParam.keySet()) {
//                    String pkey = key instanceof byte[] ? new String((byte[])key): String.valueOf(key);
                    Object value = requestParam.get(key);
                    String pv = value instanceof byte[] ? new String((byte[])value): String.valueOf(value);
                    builder.add(key, pv);
                }
            }

            RequestBody requestBody = builder.build();

            String url = httpHost.toURI() + requestUrl;
            System.out.println("executing request start get  " + "====================================" );
            System.out.println("executing request start get url " + url );
            System.out.println("executing requestBody value: " + JsonUtils.toJson(requestBody ));
//           new HttpUrl.Builder().host().build();

            Request request = new Request.Builder()
                    .url(url )
                    .post(requestBody)
                    .build();


            Response response = getOkClient().newCall(request).execute();
            String result  = response.body().string();

            System.out.println("Response content:" + JsonFormatTool.formatJson(result));
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static String getOk(String requestUrl,String requestParam){
        try {
            requestUrl = appendUrl(requestUrl);
            HttpHost httpHost =  getHttpProxy(requestUrl);
//            Proxy proxy =  new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpHost.getHostName(), httpHost.getPort()));
//            OkHttpClient client = new OkHttpClient();

            String url = httpHost.toURI() + requestUrl + "?" + requestParam;
            System.out.println("executing request start get  " + "====================================" );
            System.out.println("executing request start get url " + url );
//           new HttpUrl.Builder().host().build();
//            OkHttpClient client = new OkHttpClient().newBuilder().
//                    connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS)
//                    // 解决内存溢出问题
//                    .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
//                    .build();
            Request request = new Request.Builder()
                    .url(url ).get()
                    .build();

            Response response = getOkClient().newCall(request).execute();
            String result  = response.body().string();

            System.out.println("Response content:" + JsonFormatTool.formatJson(result));
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }


    /**
     * 发送 get请求 
     */
    public static String get(String requestURL ,String requestParam) {
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            requestURL = appendUrl(requestURL);
            System.out.println("executing request start get  " + "====================================" );
            String httpgetparam = requestURL + "?" + requestParam;
            httpgetparam = URLDecoder.decode(httpgetparam,"UTF-8");
//            System.out.println("executing request url " + httpgetparam);
            HttpGet httpget = new HttpGet(httpgetparam);
            httpget.addHeader("Accept","application/json;charset=utf-8");
            HttpHost proxy = getHttpProxy(requestURL);



            String uri = proxy.toURI();
            System.err.println("executing request uri =: " + uri+httpgetparam);
            System.out.println("executing request end get  " + "====================================" );
            CloseableHttpResponse response = httpclient.execute(proxy,httpget);
            try {
                // 获取响应实体    
                HttpEntity entity = response.getEntity();
                System.out.println("-------- executing request get start ------------------------------");
                // 打印响应状态    
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    result = EntityUtils.toString(entity,"UTF-8");
//                    // 打印响应内容长度    
//                    System.out.println("Response content length: " + entity.getContentLength());  
                    // 打印响应内容    
                    System.out.println("Response content: " + JsonFormatTool.formatJson( result));
                }
                System.out.println("------------- executing request get end -----------------------");
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // 关闭连接,释放资源    
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 上传文件 
     */
    public static void upload() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceFile.action");

            FileBody bin = new FileBody(new File("F:\\image\\sendpix0.jpg"));
            StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("bin", bin).addPart("comment", comment).build();

            httppost.setEntity(reqEntity);


            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void coventBodyMap(Object o){
        if(null == o || "".equals(o)){
            return ;
        }
        String json = JsonUtils.toJson(o);
        TreeMap<String,Object> map1 = JSON.parseObject(json, TreeMap.class);
        if(null != map1 ){
            map.putAll(map1);
            HashMap mapData = new HashMap(map);
            mapData.remove("blockSize");
            String cliSign =  SignParam.getServerSign(mapData);
            map.put("cliSign",cliSign);
        }
    }

    private static TreeMap<String, Object> postHearTreeMap(){
        TokenData tokenData = new TokenData();
        tokenData.setUserid(userId);
        tokenData.setToken(token);


        TreeMap<String,Object> parametersMap =new TreeMap<>();
        parametersMap.putAll(getHeadTreeMap());
        parametersMap.put("sysVersion", "android");
        parametersMap.put("userId", tokenData.getUserid());
        parametersMap.put("accessToken", tokenData.getToken());
        parametersMap.put("device", "3f25d333755240e");
        parametersMap.put("times", System.currentTimeMillis());

//        parametersMap.put("cuid","fe15a067ab454bcd89ba6be830660614");

        map.putAll(parametersMap);
        return parametersMap;


    }
    private static String appendUrl(String url){
        if(url.contains(HTTP_POJECT)){
            return url;
        }
        return HTTP_POJECT+url;
    }

    private static TreeMap<String, Object> getHeadTreeMap(){
    	TreeMap<String,Object> parametersMap =new TreeMap<>();
    	parametersMap.put("platform", 1);
		parametersMap.put("version", 100003);
		parametersMap.put("appId", 1005);
		parametersMap.put("userId",1);
//        parametersMap.put("cliSign","efd3382beaf4b53c");
        map.putAll(parametersMap);
        return parametersMap;
    }

    private static String covert(){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> e : map.entrySet()) {
            sb.append(e.getKey());
            sb.append("=");
            sb.append(e.getValue());
            sb.append("&");
        }
        int len = sb.length();
        sb.delete(len - 1, len);

        return sb.toString();
    }

    public static String postURL(String url, Object o){
//    	String str = name.getMethodName();
//		System.out.println(str);
        postHearTreeMap();
        coventBodyMap(o);

        String content = covert();
    	System.out.println("post url =:" + content);
        return postOk(url, map);

    }

    public static String getURL(String url, Object o){
        getHeadTreeMap();
        coventBodyMap(o);
        String content = covert();

//    	 System.out.println("get url =:" + content);
//        return get(url, content);
        return getOk(url, content);
    }

    public static void uploadURL(String path,String requestUrl, Object o)throws Exception{
        postHearTreeMap();
        coventBodyMap(o);
        String content = covert();

//    	 System.out.println("get url =:" + content);
//        return get(url, content);
        requestUrl = appendUrl(requestUrl);
        HttpHost httpHost =  getHttpProxy(requestUrl);
        String url = httpHost.toURI() + requestUrl;
        FileUtil.oupload( path ,url, map);
    }
}