package com.suven.framework.core.redis.factory;


import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.redis.RedisStrategyEnum;

import java.util.Map;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *
 * <pre>
 * @description (说明):
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @date 创建时间: 2023-12-29
 * # model 为 ,standalone,sentinel,masterSlave，cluster，group 默认为 cluster;
 *         model: cluster
 **/
public enum RedisConnectionEnum implements RedisStrategyEnum {
    REDIS_STANDALONE_STRATEGY(1,"standalone",1,RedisConnectionInStandalone.class,"单机模式"),
    REDIS_SENTINEL_STRATEGY(2,"sentinel",1,RedisConnectionInSentinel.class,"哨兵模式"),
    REDIS_MASTER_SLAVE_STRATEGY(3,"masterSlave",1, RedisConnectionInMasterSlave.class,"主从模式"),
    REDIS_CLUSTER_STRATEGY(4,"cluster",1,RedisConnectionInCluster.class,"集群模式"),
    REDIS_CLUSTER_GROUP_STRATEGY(5,"clusterGroup",1,RedisConnectionInCluster.class,"集群组模式"),

    ;
    int code;
    String modelName;
   int apiType;
    Class<?> modelClass;

    String desc;
    private static Map<Object, RedisConnectionEnum> enumsMap;

    static {
        enumsMap = IterableConvert.convertMap(values(), RedisConnectionEnum::getCode);
        enumsMap.putAll(IterableConvert.convertMap(values(), RedisConnectionEnum::getModelName));
    }
    public static RedisConnectionEnum getEnum(int index){
        return enumsMap.get(index);
    }
    public static RedisConnectionEnum getEnum(String index){
        return enumsMap.get(index);
    }

    RedisConnectionEnum(int code, String modelName,int apiType, Class<?> modelClass,String desc) {
        this.code = code;
        this.apiType = apiType;
        this.modelName = modelName;
        this.modelClass = modelClass;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }
    public int getApiType() {
        return apiType;
    }
    public String getIndex(){
        return this.getModelName() + "_" + getApiType();
    }

    public String getModelName() {
        return modelName;
    }


    public String getDesc() {
        return desc;
    }
    public Class<?> getModelClass(){
        return this.modelClass;
    }


}
