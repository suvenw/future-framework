package com.suven.framework.util.tool;

import com.suven.framework.http.api.IBaseApi;
import org.apache.commons.collections.ObjectTrues;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IoUtilConverter {

    /**
     * List 以ID分组 Map<Long,List<T>>
     * Map<Integer, List<T>> groupBy = appleList.stream().collect(Collectors.groupingBy(T::getId));
     * @param list
     * @return
     */
    public static  <T extends IBaseApi> Map<Long,T> listConverterMap(List<T> list){
        if(ObjectTrues.isNotEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.toMap(T::getId, entity -> entity));
    }

    /**
     * List 以ID分组 Map<Long,List<T>>
     * Map<Integer, List<T>> groupBy = appleList.stream().collect(Collectors.groupingBy(T::getId));
     * @param list
     * @return
     */
    public  static <T extends IBaseApi> Map<Long,List<T>> listConverterMapGroup(List<T> list){
        if(ObjectTrues.isNotEmpty(list)){
            return Collections.emptyMap();
        }
        return list.stream().collect(Collectors.groupingBy(T::getId));
    }

    /**
     * Map<Long,T> map --> list<T>
     * @param map
     * @return
     */
    public static <T extends IBaseApi> List<T> MapEntityConvertList(Map<Long,T> map){
        List<T> result = map.values().stream().collect(Collectors.toList());
        return result;
    }





}
