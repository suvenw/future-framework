package com.suven.framework.core.kafka;

import com.suven.framework.util.json.JsonUtils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

/**
 * @Title: KafkaConsumerAbstractHandler.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) kafka 消费端业务抽象功能流程实现类;
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */



public abstract class KafkaConsumerAbstractHandler<T>{

    private Logger logger = LoggerFactory.getLogger(KafkaConsumerAbstractHandler.class);



    public abstract void executeConsumer(T value );

    private void setLogger(Class clazz){
        logger.warn(" abstract  executeConsumer before setLogger clazz=:[{}]" , clazz);
    }

    public void  onMessage(ConsumerRecord<String, String> record,Class<T> clazz ) {
        try{
            Optional<?> messages = Optional.ofNullable(record.value());
            if (!messages.isPresent()) {
                return;
            }
            if(clazz == Object.class|| null == clazz){
                clazz = this.getTempalteType();
            }
            logger.info("  onMessage ConsumerRecord  value=:[{}]" , record.value());
            setLogger(clazz);
            if(null == clazz){
                clazz = (Class<T>) String.class;
            }
            if(String.class == clazz || String.class == clazz || clazz == Object.class ){
                executeConsumer((T)record.value());
            }else {
                T instance = JsonUtils.parseObject(record.value(),clazz);
                executeConsumer(instance);
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.warn(" abstract kafka onMessage  Exception clazz=:[{}] ,ConsumerRecord =:[{}] " , clazz,record.toString());
        }


    }

    private static Method getMethodByName(Class<?> clazz, String methodName,Class<?>... paramTypes) {
        try {
            Method method = ReflectionUtils.findMethod(clazz, methodName,paramTypes);
            return method;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private Class<T> getTempalteType() {
        Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }



}
