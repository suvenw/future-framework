package com.suven.framework.core.rocketmq;


import com.suven.framework.util.ids.IGlobalId;

public interface IRocketMqTopic {

    String getTopic();

    String getTag();

    String getProducerGroupName();

    String getConsumerGroupName();

    int getFailedRetryCount();

    IGlobalId getModule();

    /** formats: `topicName:tags` **/
    default String formatsTopicNameTags(){
        String topicTag = new  StringBuilder(getTopic()).append(":").append(getTag()).toString();
        return topicTag;
    }
}
