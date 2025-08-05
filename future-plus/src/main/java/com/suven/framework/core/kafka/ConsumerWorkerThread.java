package com.suven.framework.core.kafka;

import com.suven.framework.core.spring.SpringUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Title: ConsumerWorkerThread.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) kafka 消费端工作流线程实现类,通过消费consumerRecord队列信息,交给工作线程处理;
 */


public class ConsumerWorkerThread<T> implements Runnable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ConsumerRecord<String, String> consumerRecord;
    private final KafkaConsumerAbstractHandler<T> kafkaConsumerHandler;


    public ConsumerWorkerThread(ConsumerRecord<String, String> record, KafkaConsumerAbstractHandler<T> kafkaConsumerHandler) {
       this.consumerRecord = record;
       KafkaConsumerAbstractHandler<T> consumer = SpringUtil.getBean(kafkaConsumerHandler.getClass());
       this.kafkaConsumerHandler = consumer;

   }

    public void run() {
        try {
            if(null == consumerRecord ){
                return;
            }
            Optional<?> optional = Optional.ofNullable(consumerRecord.value());
            if (!optional.isPresent()) {
                return;
            }

            Class clazz = getParameterType();
            if(clazz == null){
                throw new RuntimeException("ReflectionUtils findMethod method to  getParameterTypes error" );
            }
            kafkaConsumerHandler.onMessage(consumerRecord,clazz);

        }catch (Exception e){
            e.printStackTrace();
        }


     }
//    private Class<T> getTempalteType() {
//        Class<T> clazz = (Class<T>) ((ParameterizedType) kafkaConsumerHandler.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        return clazz;
//    }

    private Class<T> getParameterType(){
        Method[] methods = kafkaConsumerHandler.getClass().getDeclaredMethods();
        for(Method method : methods){
            Class[] parameterClasses = method.getParameterTypes();
            if(!"executeConsumer".equals(method.getName())|| parameterClasses==null || parameterClasses.length < 1){
                continue;
            }
            Class<T> parameterType = parameterClasses[0];
            if(parameterType == Object.class){
                continue;
            }
            return parameterType;
        }
        return null;
    }
}