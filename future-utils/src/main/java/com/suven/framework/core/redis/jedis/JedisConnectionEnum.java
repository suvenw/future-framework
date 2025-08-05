package com.suven.framework.core.redis.jedis;


import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.redis.RedisStrategyEnum;
import com.suven.framework.core.redis.factory.RedisConnectionInMasterSlave;
import com.suven.framework.core.redis.factory.RedisConnectionInSentinel;
import com.suven.framework.core.redis.factory.RedisConnectionInStandalone;

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
 * date 创建时间: 2023-12-29
 * # model 为 ,standalone,sentinel,masterSlave，cluster，group 默认为 cluster;
 *         model: cluster
 **/
public enum JedisConnectionEnum implements RedisStrategyEnum {
    REDIS_STANDALONE_JEDIS_STRATEGY(11,"standalone",2, RedisConnectionInStandalone.class,"单机模式"),
    REDIS_SENTINEL_JEDIS_STRATEGY(12,"sentinel",2, RedisConnectionInSentinel.class,"哨兵模式"),
    REDIS_MASTER_SLAVE_JEDIS_STRATEGY(13,"masterSlave", 2, RedisConnectionInMasterSlave.class,"主从模式"),
//    REDIS_CLUSTER_JEDIS_STRATEGY(14,"cluster",2,RedisConnectionInCluster.class,"集群模式"),
//    REDIS_CLUSTER_GROUP_S_JEDISTRATEGY(15,"clusterGroup",2,RedisConnectionInCluster.class,"集群组模式"),

    ;
    int code;
    String modelName;
   int apiType;
    Class<?> modelClass;
    String desc;
    private static Map<Object, JedisConnectionEnum> enumsMap;

    static {
        enumsMap = IterableConvert.convertMap(values(), JedisConnectionEnum::getCode);
        enumsMap.putAll(IterableConvert.convertMap(values(), JedisConnectionEnum::getModelName));
    }
    public static JedisConnectionEnum getEnum(int index){
        return enumsMap.get(index);
    }
    public static JedisConnectionEnum getEnum(String index){
        return enumsMap.get(index);
    }

    JedisConnectionEnum(int code, String modelName, int apiType, Class<?> modelClass, String desc) {
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
