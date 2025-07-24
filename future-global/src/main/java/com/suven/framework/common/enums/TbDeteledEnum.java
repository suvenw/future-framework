package com.suven.framework.common.enums;

import com.suven.framework.core.IterableConvert;

import java.util.Arrays;
import java.util.Map;

/**
 * @ClassName TbStatusEnum
 * @Author suven.wang
 * @Description 表状态属性值枚举类,0.下架,1.上架
 * @CreateDate 2018-11-15  12:45
 * @WeeK 星期四
 * @Version v2.0
 **/

public enum TbDeteledEnum {
    DELETED(1,"已删除,启用删除"), //下载
    NORMAL(0,"正常"), //上载

    ;


    private int index;
    private String value;

    private static final Map<Integer, TbDeteledEnum> enumMap =
            IterableConvert.convertMap(Arrays.asList(values()),TbDeteledEnum::index);

    TbDeteledEnum(int index , String value){
        this.index = index;
        this.value = value;
    }
    public static TbDeteledEnum getEnum(int index){
        return enumMap.get(index);
    }

    public int index() {
        return index;
    }

    public String getValue() {
        return value;
    }
}
