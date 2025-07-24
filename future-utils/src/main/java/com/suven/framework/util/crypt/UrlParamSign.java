package com.suven.framework.util.crypt;

import com.suven.framework.util.json.JsonUtils;
import okhttp3.FormBody;
import org.apache.commons.io.Charsets;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @Title: UrlParamSign.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 接口请求参数tree型排序后组装成原生字符串实现类；
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public class UrlParamSign{
    
    public static String getValue(Object object){
        if(null == object ){
            return null;
        }
        if(object instanceof  String){
            return "?"+object;
        }
        Map<String, String> map = objectToMap(object);
        String s = "";
        boolean is = true;
        for(String key : map.keySet()){
            String v = map.get(key);
            if(null != v && !"".equals(v)){
                if (is) {
                    s += "?" + key + "=" + v;
                    is = false;
                } else {
                    s += "&" + key + "=" + v;
                }
            }
        }
        return s;
    }
    
    public static List<NameValuePair> postValue(Object object){
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Map<String, String> map = objectToMap(object);
        for(String key : map.keySet()){
            String pv = map.get(key) ;
            formparams.add(new BasicNameValuePair(key, pv));
        }
        return formparams;
    }

    /**
     * 生成post body
     */
    public static FormBody postParam(Object params) {
        Map<String,Object> param = JsonUtils.objectToMap(params);
        FormBody.Builder fbuilder = new FormBody.Builder();
        if(null != param && !param.isEmpty()) {
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                fbuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        return fbuilder.build();
    }

    private static List<Field> getAllFieldsList(Class<?> cls) {
        final List<Field> allFields = new ArrayList<Field>();
        Class<?> currentClass = cls;
        while (currentClass != null) {
            final Field[] declaredFields = currentClass.getDeclaredFields();
            Collections.addAll(allFields, declaredFields);
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }
    
    private static Map<String,String> sortMap(Map map){
        Map<String, String> treeMap = new TreeMap<>();
        for(Object okey : map.keySet()){
            Object oval = map.get(okey);
            String key = okey instanceof byte[] ? new String((byte[])okey): String.valueOf(okey);
            String val = oval instanceof byte[] ? new String((byte[])oval): String.valueOf(oval);
            treeMap.put(key,val);
        }
        return treeMap;
    }
    
    private  static Map<String, String> objectToMap(Object obj){
        if(obj == null){
            return null;
        }
        if(obj instanceof  Map){
           return  sortMap((Map) obj);
        }
        Map<String, String> map = new TreeMap<>();
        if(isIterable(obj.getClass())){
            throw new RuntimeException("Object is not Collection or isArray");   
        }
        List<Field> declaredFields = getAllFieldsList(obj.getClass());
        for (Field field : declaredFields) {
           try {
               if (!isTypeClass(field)) {
                   field.setAccessible(true);
                   Object object = field.get(obj);
                   if(null != object){
                       String val = object instanceof byte[] ? new String((byte[])object): String.valueOf(object);
                       map.put(field.getName(),val);
                   }else {
                       map.put(field.getName(),null);
                   }
                   
               }
           }catch (Exception e){
               e.printStackTrace();
           }
            
            
        }
        return map;
    }

    private static boolean isIterable(Class<?> clazz) {
        return (clazz.isArray() || Collection.class.isAssignableFrom(clazz))
                && !byte[].class.isAssignableFrom(clazz);
    }
    /**
     * 排除 泛型,扩展性,或静态,或final类型
     * @return
     */
    private static boolean isTypeClass(Field property) {
        boolean isStatic = Modifier.isStatic(property.getModifiers());
        boolean isFinal = Modifier.isFinal(property.getModifiers());
        return isStatic || isFinal;
    }
    /**
     * 将字符串转为规范的uri字符串形式
     * @param url 字符串形式的url
     * @param charset 编码方式
     * @return 编码后的uri
     */
    public static String toUriString(String url, Charset charset) {
        String encodedString = url;
        try {
            encodedString = UriComponentsBuilder.fromUriString(url).build().encode().toUriString();
        } catch (Exception ignored) {
//            logger.error("编码异常", ignored);
        }
        return encodedString;
    }
    public static String toUTF8UriString(String url) {
        return toUriString(url, Charsets.toCharset(Charsets.UTF_8));
    }

}
