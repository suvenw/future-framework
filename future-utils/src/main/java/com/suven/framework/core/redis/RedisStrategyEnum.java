package com.suven.framework.core.redis;

import java.util.Objects;

public interface RedisStrategyEnum {

     /**
      * 获取编号
      **/
     int getCode();


     /**
      * 获取api类型:1.spring 2.jedis
      **/
     int getApiType();



     /**
      * 获取model类型:model 为 ,standalone,sentinel,masterSlave，cluster，group 默认为 cluster;
      **/
     String getModelName();

     /**
      * 获取model类型对象的实现类:model 为 ,standalone,sentinel,masterSlave，cluster，group 默认为 cluster;
      **/
     Class<?> getModelClass();

     /**
      * 获取model类型的介绍信息:model 为 ,standalone,sentinel,masterSlave，cluster，group 默认为 cluster;
      **/
     String getDesc();

     default boolean equalsModelType( String configModelName){
         return Objects.equals(configModelName,this.getModelName());
     }

 }