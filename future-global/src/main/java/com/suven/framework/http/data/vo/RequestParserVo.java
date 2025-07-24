package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.IRequestVo;
import com.suven.framework.http.JsonParse;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public class RequestParserVo  implements IRequestVo, Serializable {


    public static RequestParserVo build(){
        return new RequestParserVo();
    }


    @Override
    public <T> T parseFrom(Map<String, Object>  map, Class<T> clazz) throws Exception{
        T iRequestVo =  JsonParse.parseFrom(map,clazz);
        return iRequestVo;

    }

    @Override
    public <T> T parseJson(String json, Class<T> clazz) {
        T iRequestVo =   JsonParse.parseJson(json,clazz);
        return iRequestVo;
    }



    /**
     * 将map 转换成类型对象
     * @param headersMap
     * @param clazz
     * @return
     */
    @Override
    public <T>T parseHeader(Map<String,String> headersMap, Class<T> clazz)  {
        if (headersMap == null){
            return null;
        }
        T header = null;
        try{
            header = clazz.newInstance();
            Field[] fields = FieldUtils.getAllFields(clazz);
            for (Field field : fields) {
                try {
                    int mod = field.getModifiers();
                    if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                        continue;
                    }
                    field.setAccessible(true);
                    String value = headersMap.get(field.getName());
                    field.set(header, value);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
        return header;
    }



}

