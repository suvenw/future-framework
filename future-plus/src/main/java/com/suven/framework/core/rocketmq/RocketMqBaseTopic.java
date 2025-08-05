package com.suven.framework.core.rocketmq;

import com.suven.framework.util.ids.GlobalIdEnum;
import com.suven.framework.util.ids.IGlobalId;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ROCKET_MQ队列配置
 * Description:
 * @Author lixiangling
 * date 2018/5/23 9:36
 * Copyright: (c) 2018 gc by https://www.suven.top
 * @version : 1.0.0
 * --------------------------------------------------------
 * modifyer    modifyTime                 comment
 *
 * --------------------------------------------------------
 */


/**
 * Title: RocketMQMessage.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明)RocketMQ MQ队列配置状态枚举类,包括(topicName,tag,producerGroupName,consumerGroupName,全局唯一id)
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public enum RocketMqBaseTopic implements IRocketMqTopic, IRocketMqTopicParam {

    //用户登录
    USER_LOGIN(topicName,topicTag,productGroup,consumerGroup, GlobalIdEnum.OAUTH,3),

     ;

    String topic;
    String tag;
    String producerGroupName;
    String consumerGroupName;
    int failedRetryCount;
    GlobalIdEnum module;

    private static Map<String, RocketMqBaseTopic> topicMap = new HashMap<>();

    static {

        for(RocketMqBaseTopic topic : values()) {
            topicMap.put(topic.getTopic(), topic);
        }
    }



    RocketMqBaseTopic(String topic, String tag, String producerGroupName, String consumerGroupName, GlobalIdEnum module, int retryCount){
        this.topic = topic;
        this.tag = tag;
        this.producerGroupName = producerGroupName;
        this.consumerGroupName = consumerGroupName;
        this.failedRetryCount = retryCount;
        this.module = module;
    }



    public static RocketMqBaseTopic getTopicName(String topicName){
        return topicMap.get(topicName);
    }

    public String getTopic() {
        return topic;
    }

    public String getTag() {
        return tag;
    }

    public String getProducerGroupName() {
        return producerGroupName;
    }

    public String getConsumerGroupName() {
        return consumerGroupName;
    }

    public int getFailedRetryCount() {
        return failedRetryCount;
    }

    public IGlobalId getModule() {
        return module;
    }
}
