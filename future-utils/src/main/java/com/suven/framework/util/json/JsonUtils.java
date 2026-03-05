package com.suven.framework.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.ImmutableSet;
import com.suven.framework.core.ObjectTrue;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @ Title: JsonUtils.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) JSON 转换相关的工具类
 *  Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public abstract class JsonUtils{

    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    private static ImmutableSet<? extends Class<? extends Serializable>> primitiveSet;

    static {
        // 还是直接的HashSet比较快..
        primitiveSet =
                ImmutableSet.of(int.class, long.class, double.class, float.class,
                        byte.class, short.class, String.class, boolean.class);
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        if(ObjectTrue.isEmpty(json)){
            return null;
        }
        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            log.warn("字符串转" + clazz.getSimpleName() + "失败", e);
            return null;
        }
    }

    public static <T> T parse(byte[] json, Class<T> clazz) {
        if(ObjectTrue.isEmpty(json)){
            return null;
        }
        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            log.warn("字符串转" + clazz.getSimpleName() + "失败", e);
            return null;
        }
    }

    public static <T> T parseJson(JSONObject json, Class<T> clazz) {
        if(ObjectTrue.isEmpty(json)){
            return null;
        }
        try {
            return JSON.parseObject(json.toJSONString(), clazz);
        } catch (Exception e) {
            log.warn("字符串转" + clazz.getSimpleName() + "失败", e);
            return null;
        }
    }

    private static String toJSONStringConvertNull(Object object) {
        return JSON.toJSONString(object,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty);
    }
    public static String toJson(Object object) {
        try {
            if(ObjectTrue.isEmpty(object)){
                return null;
            }
            return toJSONStringConvertNull(object);
        } catch (Exception e) {
            log.error("对象转json字符串失败", e);
            return null;
        }
    }
    public static String toJSONEnumNoUsingName(Object bean){
        int features = SerializerFeature.config(JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.WriteEnumUsingName, false);
        return JSON.toJSONString(bean,features);
    }




    public static String map2String(Map<?, ?> map) {
        return toJson(map);
    }

    public static Map<Object, Object> toMap(String string) {
        HashMap map = parseObject(string, HashMap.class);
        return map;
    }

    public static <K,V>Map<K, V> toTreeMap(String string) {
        return parseObject(string, TreeMap.class);
    }

    public static List<?> toList(String string) {
        return parseObject(string, List.class);
    }

    public static <T>List<T> toList(String string,Class<T> clazz) {
        return JSON.parseArray(string, clazz);
    }



    public static String listToString(Collection<Map<String, Object>> map) {
        return toJson(map);
    }


    /**
     * 解析json变成Map
     * @param json
     */
    public static Map<String,String> toTreeMap2(String json) {


        JSONObject myJsonObject = JSON.parseObject(json);
        Map<String,String> treeMap = new TreeMap<>();
        Iterator<String> keys = myJsonObject.keySet().iterator();
        String key;
        Object value;
        while( keys.hasNext())
        {
            key = (String)keys.next();
            value = myJsonObject.get(key);
            treeMap.put(key,String.valueOf(value));
        }

        return treeMap;
    }
    /*
     * 将json对象转换为java对象的工具类
     *
     * @param entityName
     *
     * @param object 需要转换的json对象(只可以为JSONObject对象不能使JSONArry对象)
     *
     * @return 返回对应的java实体
     */
    public static <T extends Object> T toPojo(JSONObject jsonObject, Class<T> klass) {
        String className = klass.getName();
        Class cl = null;
        T obj = null;
        try {
            cl = Class.forName(className);
            obj = (T) cl.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            log.info("获取类名失败 ");
        } catch (InstantiationException | IllegalAccessException e) {
            log.info("获取newInstance失败 ");
        } catch (InvocationTargetException | NoSuchMethodException e) {
            log.info("获取newInstance失败 ");
            throw new RuntimeException(e);
        }

        Field[] fds = cl.getDeclaredFields();
        for (Field fd : fds) {
            if (!fd.canAccess(obj)) {
                fd.setAccessible(true);
            }
            Iterator<String> keys = jsonObject.keySet().iterator();
            String key = "";
            String fieldName = fd.getName();
            String capName = StringUtils.capitalize(fieldName);
            try {
                while (keys.hasNext()) {
                    key =  keys.next();
                    if (fieldName.equals(key)) {
                        String value = jsonObject.get(key).toString();
                        boolean valueStatus = (!"".equals(value) && !"0".equals(value));
                        if (fd.getType() == long.class) {
                            if (valueStatus) {
                                fd.set(obj, Long.parseLong(value));
                            }
                        } else if (fd.getType() == String.class) {
                            fd.set(obj, value);
                        } else if (fd.getType() == int.class) {
                            if (valueStatus) {
                                fd.set(obj, Integer.parseInt(value));
                            }
                        } else if (fd.getType() == double.class) {
                            if (valueStatus) {
                                fd.set(obj, Double.parseDouble(value));
                            }
                        } else if (fd.getType() == float.class) {
                            if (valueStatus) {
                                fd.set(obj, Float.parseFloat(value));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("json格式和pojo类型不一致 pojo字段: "+ fd.getName()+ " json: "+ JsonUtils
                        .toJson(jsonObject.getString(fd.getName())).replaceAll("\"", "'").replaceAll("\\\\'", "\""));
            }
        }
        return obj;
    }


    /**
     * json字符转换类型到clazz
     * @param map json字符串
     * @param clazz 类型
     * @param <T> 转换后的类型
     * @return t  转换后的类型
     */
    public static <T> T parseByMap(Map<String,String> map, Class<T> clazz) {
        return parseObject(toJson(map),clazz);
    }

    /**
     * 将map 转换成类型对象
     * @param map 参数对象
     * @param beanClass 转换成结果对象
     * @return Object  转换后的类型
     */
    public static <T>T mapToObject(Map<String, String> map, Class<T> beanClass)  {
        if (map == null) {
            return null;
        }
        T obj = null;
        try{
            obj = beanClass.getDeclaredConstructor().newInstance();
            Field[] fields = FieldUtils.getAllFields(beanClass.getClass());
            for (Field field : fields) {
                try {
                    int mod = field.getModifiers();
                    if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                        continue;
                    }
                    field.setAccessible(true);
                    field.set(obj, map.get(field.getName()));
                } catch (Exception e) {
                    log.warn("objectToMap field Exception:[{}]",e);
                }
            }
        } catch (Exception e) {
            log.warn("objectToMap  beanClass.newInstance() Exception:[{}]",e);
        }
        return obj;
    }


    /**
     * 将类型转换成map
     * @param object
     * @return
     */
    public static Map<String, Object> objectToMap(Object object) {
        List<Field> fields = FieldUtils.getAllFieldsList(object.getClass());// klass.getFields();
        Map<String, Object> map = new HashMap<>();
        if(ObjectTrue.isEmpty(fields)){
            return map;
        }
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                map.put(field.getName(), field.get(object));
            } catch (Exception e) {
                log.warn("objectToMap Exception:[{}]",e);
            }
        }
        return  map;
    }
}
