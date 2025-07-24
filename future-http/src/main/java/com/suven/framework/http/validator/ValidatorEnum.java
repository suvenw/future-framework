package com.suven.framework.http.validator;


import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author suven.wang@ XXX.net
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) Gaven LoadingCache 缓存实现内存缓存实现抽象类
 */


public enum ValidatorEnum {
    TOKEN,
    VERSION,
    MAINTAIN,

    ;

    private static Map<String, ValidatorEnum> enumMap = new HashMap<>();
    static {

        for(ValidatorEnum type : values()) {
            enumMap.put(type.name(), type);
        }
    }

    /**
     * 验证是否包括这类型的属性类型
     * @param type
     * @return
     */
    public static boolean isContains(String type){
        type = type.toUpperCase();
        return enumMap.keySet().contains(type);
    }
}
