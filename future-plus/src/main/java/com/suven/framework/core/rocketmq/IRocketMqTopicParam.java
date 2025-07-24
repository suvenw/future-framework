package com.suven.framework.core.rocketmq;

public interface IRocketMqTopicParam {
//"OAUTH_LOGIN","OAUTH_LOGIN","OAUTH_LOGIN_PG_GROUP","OAUTH_LOGIN_CG_GROUP"
    String topicName = "OAUTH_LOGIN";
    String topicTag = "OAUTH_LOGIN";
    String consumerGroup = "OAUTH_LOGIN_CG_GROUP";
    String productGroup = "OAUTH_LOGIN_PG_GROUP";
}
