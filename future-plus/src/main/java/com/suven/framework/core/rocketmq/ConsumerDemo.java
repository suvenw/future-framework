package com.suven.framework.core.rocketmq;

import com.suven.framework.common.constants.GlobalConfigConstants;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import com.suven.framework.util.json.JsonUtils;

/**
 * RocketMQMessageListener
 */
@Service
@RocketMQMessageListener(nameServer = "${rocketmq.name-server}", topic = IRocketMqTopicParam.topicName, consumerGroup = IRocketMqTopicParam.consumerGroup)
@ConditionalOnProperty(name = GlobalConfigConstants.ROCKET_MQ_CONSUMER_CONFIG_ENABLED,  matchIfMissing = false)
@ConditionalOnClass(RocketMQAutoConfiguration.class)
public class ConsumerDemo extends RocketMQConsumerAbstractHandler<RocketMqTestBo> {


    @Override
    protected void onSuccessService(RocketMqTestBo dataMessage) {
        System.out.printf("######## user_consumer received: %s ; \n", JsonUtils.toJson(dataMessage));
    }

    @Override
    protected void onExceptionService(RocketMQConsumerWithPayload payload) {

    }
}
