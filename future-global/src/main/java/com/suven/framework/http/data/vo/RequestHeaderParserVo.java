package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.IHeaderRequestVo;
import com.suven.framework.http.JsonParse;

import java.io.Serializable;
import java.util.Map;

public class RequestHeaderParserVo extends JsonParse implements  IHeaderRequestVo,Serializable {


    public static RequestHeaderParserVo build(){
        return new RequestHeaderParserVo();
    }

    @Override
    public <T>T parseHeader(Map<String, Object> headersMap, Class<T> clazz)  {
        if (headersMap == null){
            return null;
        }
        T obj = null;
        try{
            obj = JsonParse.parseFrom(headersMap,clazz);
        } catch (Exception e) {
        }
        return obj;
    }

//    /**
//     * 将map 转换成类型对象
//     * @param headersMap
//     * @param clazz
//     * @return
//     */
//    @Override
//    public <T>T parseHeader(Map<String, Object> headersMap, Class<T> clazz)  {
//        if (headersMap == null){
//            return null;
//        }
//        T obj = null;
//        try{
//            obj = clazz.newInstance();
//            Field[] fields = FieldUtils.getAllFields(clazz.getClass());
//            for (Field field : fields) {
//                try {
//                    int mod = field.getModifiers();
//                    if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
//                        continue;
//                    }
//                    field.setAccessible(true);
//                    Object value = headersMap.get(field.getName());
//                    field.set(obj, value);
//                } catch (Exception e) {
//                }
//            }
//        } catch (Exception e) {
//        }
//        return obj;
//    }




}

