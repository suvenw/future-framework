package com.suven.framework.core.rocketmq;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.core.TypeSerializer;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByRandom;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.suven.framework.core.redis.RedisClientServer;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.util.ids.GlobalId;
import com.suven.framework.util.json.JsonUtils;



/**
 * @Setter@Getter 2020 06-01   19:53
 * @author leien
 * @desc mq自定义模版类
 */
@Component
@ConditionalOnMissingBean(DefaultMQProducer.class)
@ConditionalOnProperty(name = GlobalConfigConstants.ROCKET_MQ_PRODUCT_CONFIG_ENABLED,  matchIfMissing = false)
public class RocketMQApi {
    private Logger logger = LoggerFactory.getLogger(RocketMQApi.class);

    @Autowired
    private RedisClientServer redisClusterServer;

    public static final int CACHE_TIME = 60*60*24;

    @Autowired(required = false)
    private DefaultMQProducer producer;

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    private MessageQueueSelector messageQueueSelector = new SelectMessageQueueByHash();

    public MessageQueueSelector getMessageQueueSelector() {
        return messageQueueSelector;
    }

    public void setMessageQueueSelector(MessageQueueSelector messageQueueSelector) {
        this.messageQueueSelector = messageQueueSelector;
    }

    /**
     *
     * @param rocketMqTopic  也可以加tag，topic:tag
     * @param payload
     * mq  同步发送的时候默认会2次 重试，异步发送的时候，只重试一次(且无论重试次数设置多少)
     * @return
     */
    public <T>SendResult syncSend(IRocketMqTopic rocketMqTopic, T payload) {
        return syncSend(rocketMqTopic,payload,null,0,0);

    }
    /**
     *
     * @param rocketMqTopic  也可以加tag，topic:tag
     * @param payload
     * mq  同步发送的时候默认会2次 重试，异步发送的时候，只重试一次(且无论重试次数设置多少)
     * @return
     */
    public <T>SendResult syncSend(IRocketMqTopic rocketMqTopic, T payload,long timeout) {
        return syncSend(rocketMqTopic,payload,null,timeout,0);

    }
    /**
     *
     * @param rocketMqTopic  也可以加tag，topic:tag
     * @param payload
     * mq  同步发送的时候默认会2次 重试，异步发送的时候，只重试一次(且无论重试次数设置多少)
     * @return
     */
    public <T>SendResult syncSend(IRocketMqTopic rocketMqTopic, T payload, String prefixKeys,long timeout,int delayLevel) {

        SendRocketMqMessage rocketMq = new SendRocketMqMessage<>(rocketMqTopic, payload, prefixKeys,delayLevel).build();
        if(null == rocketMq || !rocketMq.isSuccess){
            throw new SystemRuntimeException("SendRocketMqMessage build Exception");
        }
        String rocketMqJsonMessage = rocketMq.getRocketMqJsonMessage();

        setRedisCache(rocketMq.getGlobalId(),rocketMqTopic.getFailedRetryCount());
        logger.info("send rocketMq nameSrv=【{}】 message= 【{}】", producer.getNamesrvAddr(),rocketMqJsonMessage);
        SendResult sendResult = null;
        try{
            if(timeout == 0){
                sendResult =   producer.send( rocketMq.getMessage());
            }else {
                sendResult = producer.send( rocketMq.getMessage(),timeout);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error( "syncSend rocketMq Exception : [{}]" ,e);
        }
        return  sendResult;
    }

    /**
     *
     * @param rocketMqTopic  也可以加tag，topic:tag
     * @param payload
     * mq  同步发送的时候默认会2次 重试，异步发送的时候，只重试一次(且无论重试次数设置多少)
     * @return
     */
    public <T>void sendOneway(IRocketMqTopic rocketMqTopic, T payload) {
        sendOneway(rocketMqTopic,payload,null,0);
    }
    /**
     *
     * @param rocketMqTopic  也可以加tag，topic:tag
     * @param payload
     * mq  同步发送的时候默认会2次 重试，异步发送的时候，只重试一次(且无论重试次数设置多少)
     * @return
     */
    public <T>void sendOneway(IRocketMqTopic rocketMqTopic, T payload, String prefixKeys,int delayLevel) {

        SendRocketMqMessage rocketMq = new SendRocketMqMessage<>(rocketMqTopic, payload, prefixKeys,delayLevel).build();
        if(null == rocketMq || !rocketMq.isSuccess){
            throw new SystemRuntimeException("SendRocketMqMessage build Exception");
        }
        String rocketMqJsonMessage = rocketMq.getRocketMqJsonMessage();

        setRedisCache(rocketMq.getGlobalId(),rocketMqTopic.getFailedRetryCount());
        logger.info("send rocketMq nameSrv=【{}】 message= 【{}】", producer.getNamesrvAddr(),rocketMqJsonMessage);
        SendResult sendResult = null;
        try{
            producer.sendOneway( rocketMq.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            logger.error( "sendOneway rocketMq Exception : [{}]" ,e);
        }
    }

    public <T>SendResult sendOrder(IRocketMqTopic rocketMqTopic, T payload,String messageQueueHashKey) {
        return sendOrder(rocketMqTopic,payload,null,0,0,messageQueueHashKey);
    }
    public <T>SendResult sendOrder(IRocketMqTopic rocketMqTopic, T payload,long timeout,String messageQueueHashKey) {
        return sendOrder(rocketMqTopic,payload,null,timeout,0,messageQueueHashKey);

    }
    public <T>SendResult sendOrder(IRocketMqTopic rocketMqTopic, T payload, String prefixKeys,long timeout,int delayLevel,String messageQueueHashKey)  {
        try {
            SendRocketMqMessage rocketMq = new SendRocketMqMessage<>(rocketMqTopic, payload, prefixKeys,delayLevel).build();
            if(null == rocketMq || !rocketMq.isSuccess){
                throw new SystemRuntimeException("SendRocketMqMessage build Exception");
            }
            String rocketMqJsonMessage = rocketMq.getRocketMqJsonMessage();
            //将 全局id放入redis缓存
            setRedisCache(rocketMq.getGlobalId(),rocketMqTopic.getFailedRetryCount());
            logger.info("send rocketMq nameSrv=【{}】 message= 【{}】", producer.getNamesrvAddr(),rocketMqJsonMessage);
            SendResult sendResult = producer.send(rocketMq.getMessage(),new SelectMessageQueueByRandom(),messageQueueHashKey,timeout);
            return  sendResult;
        }catch (Exception e){
           logger.error( "sendOrder rocketMq Exception : [{}]" ,(Object)e.getStackTrace());
           e.printStackTrace();
        }
        return null;

    }

    public <T>void asyncSend(IRocketMqTopic rocketMqTopic, T payload, SendCallback sendCallback)  {
        asyncSend(rocketMqTopic,payload,null,0,0,sendCallback);
    }

    /**
     * 异步发送
     * @param rocketMqTopic
     * @param payload
     * @param sendCallback
     * @param prefixKeys
     * @param timeout
     * @param delayLevel
     */
    public <T>void asyncSend(IRocketMqTopic rocketMqTopic, T payload,  String prefixKeys, long timeout,int delayLevel,SendCallback sendCallback)  {

        try {
            SendRocketMqMessage rocketMq = new SendRocketMqMessage<>(rocketMqTopic, payload, prefixKeys,delayLevel).build();
            if(null == rocketMq || !rocketMq.isSuccess){
                throw new SystemRuntimeException("SendRocketMqMessage build Exception");
            }
            String rocketMqJsonMessage = rocketMq.getRocketMqJsonMessage();
            //将 全局id放入redis缓存
            setRedisCache(rocketMq.getGlobalId(),rocketMqTopic.getFailedRetryCount());
            logger.info("send rocketMq nameSrv=【{}】 message= 【{}】", producer.getNamesrvAddr(),rocketMqJsonMessage);
            if(timeout  == 0 ){
                producer.send(rocketMq.getMessage(),sendCallback);
            }else if(timeout > 0 ){
                producer.send(rocketMq.getMessage(),sendCallback,timeout);
            }

        }catch (Exception e){
            logger.error( "sendOrder rocketMq Exception : [{}]" ,new  Object[]{e.getStackTrace()});
        }

    }


    public <T>void asyncSendOrder(IRocketMqTopic rocketMqTopic, T payload, SendCallback sendCallback,String messageQueueHashKey)
    {
        asyncSendOrder(rocketMqTopic,payload,sendCallback,messageQueueHashKey,null,0,0);
    }
    public <T>void asyncSendOrder(IRocketMqTopic rocketMqTopic, T payload, SendCallback sendCallback,String messageQueueHashKey, String prefixKeys,long timeout,int delayLevel)
    {

         try {
             SendRocketMqMessage rocketMq = new SendRocketMqMessage<>(rocketMqTopic, payload, prefixKeys,delayLevel).build();
             if(null == rocketMq || !rocketMq.isSuccess){
                 throw new SystemRuntimeException("SendRocketMqMessage build Exception");
             }
             String rocketMqJsonMessage = rocketMq.getRocketMqJsonMessage();
             //将 全局id放入redis缓存
             setRedisCache(rocketMq.getGlobalId(),rocketMqTopic.getFailedRetryCount());
             logger.info("send rocketMq nameSrv=【{}】 message= 【{}】", producer.getNamesrvAddr(),rocketMqJsonMessage);
             if(timeout  == 0 ){
                 producer.send(rocketMq.getMessage(),this.getMessageQueueSelector(),messageQueueHashKey,sendCallback);

             }else if(timeout > 0 ){
                 producer.send(rocketMq.getMessage(),this.getMessageQueueSelector(),messageQueueHashKey,sendCallback,timeout);
             }

         }catch (Exception e){
             logger.error( "sendOrder rocketMq Exception : [{}]" ,e);
         }
    }





    public static void main(String[] args) {
        long globalId = GlobalId.get().nextId();
        System.out.println(globalId);
    }

    protected void setRedisCache(long globalId, int failedRetryCount){
//        redisClusterServer.setex(RedisKeys.MQ_CONSUMER + globalId,
//                String.valueOf(failedRetryCount), CACHE_TIME);
    }

    /** 发送QM消息对象信息 **/
    private class SendRocketMqMessage<T>  {
        private IRocketMqTopic rocketMqTopic;
        private T payload;
        private String prefixKeys;
        private long globalId;
        private String rocketMqJsonMessage;
        private int delayLevel;
        private int serializable = 0;
        private Message message;


        private boolean isSuccess = false;

        public SendRocketMqMessage(IRocketMqTopic rocketMqTopic, T payload, String prefixKeys, int delayLevel) {
            this.rocketMqTopic = rocketMqTopic;
            this.payload = payload;
            this.prefixKeys = prefixKeys;
            this.delayLevel = delayLevel;
        }

        public long getGlobalId() {
            return globalId;
        }

        public String getRocketMqJsonMessage() {
            return rocketMqJsonMessage;
        }

        public Message getMessage() {
            return message;
        }

        public SendRocketMqMessage setSerializable(int  serializable){
            this.serializable = serializable;
            return this;
        }
        public boolean isSuccess(){
            return isSuccess;
        }

        public SendRocketMqMessage build() {
            try {
                if(StringUtils.isBlank(prefixKeys)){
                    prefixKeys =" default_";
                }
                globalId = GlobalId.get(rocketMqTopic.getModule()).nextId();
                String messageKeys = new  StringBuilder(prefixKeys).append(rocketMqTopic.getTopic()).append(globalId).toString();

                RocketMQProductWithPayload rocketMQMessage =  RocketMQProductWithPayload.build(payload , globalId, rocketMqTopic.getFailedRetryCount());
                rocketMQMessage.setTopic(rocketMqTopic.formatsTopicNameTags());
                byte[] prefixByte = null;
                if(serializable == 0){
                    rocketMqJsonMessage = JsonUtils.toJson(rocketMQMessage);
                }if(serializable == 1){
                    rocketMqJsonMessage = TypeSerializer.parseString(rocketMQMessage);
                }
                String globalIds = String.valueOf(globalId);
//                sendResult = rocketMQTemplate.syncSend(userTopic, );
//                message =  MessageBuilder.withPayload(payload).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build();
                message = new Message(rocketMqTopic.getTopic(),rocketMqTopic.getTag(),messageKeys,prefixByte);
                message.setTransactionId(globalIds);
                message.putUserProperty("globalId",globalIds);
                if(delayLevel >0){
                    message.setDelayTimeLevel(delayLevel);
                }
                isSuccess = true;
            }catch (Exception e){
                logger.error("SendRocketMqMessage build Exception : [{}]", e);
            }

            return this;
        }

    }
}
