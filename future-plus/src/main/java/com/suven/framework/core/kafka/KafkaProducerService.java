package com.suven.framework.core.kafka;



import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.util.ids.GUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @Title: KafkaProducerService.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) kafka 生产端业务功能流程实现类与测试例子;
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */


@Service
@ConditionalOnClass({KafkaAutoConfigSetting.class})
@EnableConfigurationProperties({KafkaAutoConfigSetting.class})
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);



    @Autowired(required=false)
    @Qualifier("kafkaTemplate")
    private KafkaTemplate kafkaTemplate;


//    @PostConstruct
//    //然后每隔1分钟执行一次
//    @Scheduled(fixedRate =  1000 * 60)
    public void initTest(){
        String value = GUID.getUUID()+"生产者向指定TOPIC 批量发送 异步消息";
        String value2 = "收到 批量发送 异步消息";
        KafkaMessage message = new KafkaMessage("top-test","top-test-key",value);
        KafkaMessage message2 = new KafkaMessage("top-test-2","top-test-2-key",value2);
        List list = new ArrayList();
        for (int i = 0; i < 100; i++) {
            message.setValue(value + " " +i);
            message2.setValue(value2 + "-------" +i);
            this.send(message);
            list.add(message2)
            if(i%10==0){
                this.send(message2,list.toArray());
                list.clear();
            }

            logger.warn("" + JsonUtils.toJson(message));
        }
    }

    /**
     *
     * 生产者向指定TOPIC 批量发送 异步消息,
     * @param objects KafkaMessage对象的集合;
     */
    public boolean send(KafkaMessage message,Object... objects ){
        if(kafkaTemplate == null ){
            logger.info("Kafka kafkaTemplate is null ," +
                    " please init kafkaTemplate by KafkaAutoConfig in application-mq.properties add [gc.kafka.producer.enabled=true]");
            throw new RuntimeException(" Kafka kafkaTemplate is null , please  in application-mq.properties add [gc.kafka.producer.enabled=true]");
        }
        if(message == null){
            return false;
        }
        try {
            if(objects == null || objects.length == 0){
                if(message.getValue() == null){
                    return false;
                }
                kafkaTemplate.send(message.getTopic(), message.getKey(), message.getValue());
                logger.info("kafka send info topic={},key={},message={}",message.getTopic(), message.getKey(),message.getValue());
                logger.info("Producer端一共产生了 1 条消息！");
            }else {
                for(Object value : objects){
                    value = JsonUtils.toJson(value);
                    kafkaTemplate.send(message.getTopic(), message.getKey(), value);
                    logger.info("kafka send info topic={},key={},message={}",message.getTopic(), message.getKey(),value);
                }
                logger.info("Producer端一共产生了" + objects.length + "条消息！");
            }
        } catch (Exception e) {
            logger.error(String.format("kafka 自动提交偏移量消费实现异常 ,参数: topic=%s,key=%s,message=%s 实现方法 sendProducer异常:",message.getTopic(), message.getKey(),objects), e );
        }finally {
        }
        return true;
    }






} 