package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.IHeaderRequestVo;
import com.suven.framework.http.JsonParse;

import java.io.Serializable;
import java.util.Map;

/**
 * Title: RequestHeaderParserVo 表单类型 表单对象转换 Object 对象实现 http 表单请求,参数继承对象
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http请求头参数VO类
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
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

