package com.suven.framework.core.kafka;

import com.suven.framework.common.constants.GlobalConfigConstants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Title: KafkaAutoConfig.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) kafka 生产端和消费端Bean 实现启动配置类,包括kafka 配置参数实现
 */



@Configuration
@ConfigurationProperties(prefix = GlobalConfigConstants.KAFKA_CONFIG_PREFIX)
@ConditionalOnProperty(name = GlobalConfigConstants.KAFKA_CONFIG_ENABLED,  matchIfMissing = false)
@EnableAutoConfiguration(exclude = {KafkaAutoConfiguration.class})
@EnableKafka
//@NacosConfigurationProperties( groupId= GlobalConfigConstants.SERVICE_NAME , dataId = GlobalConfigConstants.SERVICE_MQ_CONFIG_PREFIX,prefix= GlobalConfigConstants.KAFKA_CONFIG_PREFIX, autoRefreshed = true)
public class KafkaAutoConfigSetting {

    private KafkaProducerSettings producer = new KafkaProducerSettings();
    private KafkaConsumerSettings consumer = new KafkaConsumerSettings();



    /** 创建 Kafka Listener topic 监听工厂**/
    @ConditionalOnProperty(name= GlobalConfigConstants.KAFKA_CONSUMER_ENABLED,matchIfMissing = false)
    @ConditionalOnMissingBean({ConsumerFactory.class})
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        /** 创建kafka 消费工厂**/
        ConsumerFactory consumerFactory = new DefaultKafkaConsumerFactory(consumer.valueOf());
        consumerFactory.createConsumer();
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(consumer.getConcurrency());
        factory.getContainerProperties().setPollTimeout(consumer.getPollTimeout());
        return factory;
    }


    //创建kaka 生产者到spring bean
    @ConditionalOnProperty(name = GlobalConfigConstants.KAFKA_PRODUCER_ENABLED,matchIfMissing = false)
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory(producer.valueOf());
        return new KafkaTemplate<>(producerFactory);
    }

//    @ConditionalOnProperty(prefix = GlobalConstants.KAFKA_CONFIG_PREFIX,
//            value =GlobalConstants.KAFKA_PRODUCER_ENABLED,matchIfMissing = true)
//    @Bean("kafkaProducer")
//    public KafkaProducer createKafkaProducer(){
//        KafkaProducer kafkaProducer = new KafkaProducer(producer.valueOf());
//        return kafkaProducer;
//    }

//    @ConditionalOnProperty(prefix = GlobalConstants.KAFKA_CONFIG_PREFIX,
//            value =GlobalConstants.KAFKA_CONSUMER_ENABLED,matchIfMissing = true)
//    public KafkaConsumer createConsumer(String topic){
//        if(null != topic){
//            consumer.setGroupId(topic);
//        }
//        Properties properties = consumer.valueOf();
//        KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);
//        return kafkaConsumer;
//    }
//    //监听器必须实现MessageListener这个接口中onMessage方法
//    @Component
//    public class MyMessageListener implements MessageListener<String, String> {
//        public final Logger logger = LoggerFactory.getLogger(MyMessageListener.class);
//
//        @Override//此方法处理消息
//        public void onMessage(ConsumerRecord<String, String> data) {
//            String topic = data.topic();//消费的topic
//            logger.info("-------------recieve message from {} topic-------------", topic);
//            logger.info("partition:{}", String.valueOf(data.partition()));//消费的topic的分区
//            logger.info("offset:{}", String.valueOf(data.offset()));//消费者的位置
//            logger.info("get message from {} topic : {}", topic, data.value());//接收到的消息
//        }
//    }

//    @Bean
//    KafkaConsumerHandler myMessageListener(){
//        return  new KafkaConsumerHandler();
//    }




    public KafkaProducerSettings getProducer() {
        return producer;
    }

    public void setProducer(KafkaProducerSettings producer) {
        this.producer = producer;
    }

    public KafkaConsumerSettings getConsumer() {
        return consumer;
    }

    public void setConsumer(KafkaConsumerSettings consumer) {
        this.consumer = consumer;
    }

    public static  class KafkaProducerSettings {
        private String bootstrapServers="h153:9092";
        private int acks = 0;
        private int retries = 0 ;
        private int lingerMs = 1 ;
        private int batchSize = 16384;
        private int bufferMemory = 33554432;
        private int maxBlockMs = 1000;
        private String keySerializer ="org.apache.kafka.common.serialization.StringSerializer";
        private String valueSerializer = "org.apache.kafka.common.serialization.StringDeserializer";


        public Properties buildProducerProperties(){
            Properties props = new Properties();
            initProps(props);
            return props;
        }
        public Map<String, Object> valueOf(){
            Map<String, Object> props = new HashMap<>();
            initProps(props);
            return props;
        }
        private void initProps(Map props){
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.getBootstrapServers());
            props.put(ProducerConfig.ACKS_CONFIG, String.valueOf(this.getAcks()));
            props.put(ProducerConfig.RETRIES_CONFIG, this.getRetries());
            props.put(ProducerConfig.BATCH_SIZE_CONFIG, this.getBatchSize());
            props.put(ProducerConfig.LINGER_MS_CONFIG, this.getLingerMs());
            props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, this.getBufferMemory());
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, this.getKeySerializer());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, this.getValueSerializer());
            props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, this.getMaxBlockMs());
        }

        public int getAcks() {
            return acks;
        }

        public void setAcks(int acks) {
            this.acks = acks;
        }

        public int getRetries() {
            return retries;
        }

        public void setRetries(int retries) {
            this.retries = retries;
        }

        public int getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(int batchSize) {
            this.batchSize = batchSize;
        }

        public int getBufferMemory() {
            return bufferMemory;
        }

        public void setBufferMemory(int bufferMemory) {
            this.bufferMemory = bufferMemory;
        }

        public String getKeySerializer() {
            return keySerializer;
        }

        public void setKeySerializer(String keySerializer) {
            this.keySerializer = keySerializer;
        }

        public String getBootstrapServers() {
            return bootstrapServers;
        }

        public void setBootstrapServers(String bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
        }

        public int getLingerMs() {
            return lingerMs;
        }

        public void setLingerMs(int lingerMs) {
            this.lingerMs = lingerMs;
        }

        public String getValueSerializer() {
            return valueSerializer;
        }

        public void setValueSerializer(String valueSerializer) {
            this.valueSerializer = valueSerializer;
        }

        public int getMaxBlockMs() {
            return maxBlockMs;
        }

        public void setMaxBlockMs(int maxBlockMs) {
            this.maxBlockMs = maxBlockMs;
        }
    }


    public static class  KafkaConsumerSettings{

        private String bootstrapServers = "localhost:9092";
        private String groupId;
        private boolean enableAutoCommit = false;
        private int autoCommitIntervalms = 1000;
        private int sessionTimeoutMs = 3000;
        private int concurrency = 2;
        private int threadPoolSize = 2;
        private long pollTimeout = 1000;

        private String autoOffsetReset = "earliest";
        private String keySerializer ="org.apache.kafka.common.serialization.StringDeserializer";
        private String valueSerializer = "org.apache.kafka.common.serialization.StringDeserializer";


        public Properties buildConsumerProperties(){
            Properties props = new Properties();
            initProps(props);
            return props;
        }
        public Map<String, Object> valueOf(){
            Map<String, Object> props = new HashMap<>();
            initProps(props);
            return props;
        }
        private void initProps(Map props){
//            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.getBootstrapServers());
            props.put(ConsumerConfig.GROUP_ID_CONFIG, this.getGroupId());
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, this.isEnableAutoCommit());
            props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, this.getAutoCommitIntervalms());
            props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, this.getSessionTimeoutMs());
//            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,this.getAutoOffsetReset());
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, this.getKeySerializer());
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, this.getValueSerializer());
        }


        public String getBootstrapServers() {
            return bootstrapServers;
        }

        public void setBootstrapServers(String bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
        }

        public String getGroupId() {
            if(groupId == null){
                groupId = "group01";
            }
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public boolean isEnableAutoCommit() {
            return enableAutoCommit;
        }

        public void setEnableAutoCommit(boolean enableAutoCommit) {
            this.enableAutoCommit = enableAutoCommit;
        }

        public int getAutoCommitIntervalms() {
            return autoCommitIntervalms;
        }

        public void setAutoCommitIntervalms(int autoCommitIntervalms) {
            this.autoCommitIntervalms = autoCommitIntervalms;
        }

        public int getSessionTimeoutMs() {
            return sessionTimeoutMs;
        }

        public void setSessionTimeoutMs(int sessionTimeoutMs) {
            this.sessionTimeoutMs = sessionTimeoutMs;
        }

        public String getKeySerializer() {
            return keySerializer;
        }

        public void setKeySerializer(String keySerializer) {
            this.keySerializer = keySerializer;
        }

        public String getValueSerializer() {
            return valueSerializer;
        }

        public void setValueSerializer(String valueSerializer) {
            this.valueSerializer = valueSerializer;
        }

        public String getAutoOffsetReset() {
            return autoOffsetReset;
        }

        public void setAutoOffsetReset(String autoOffsetReset) {
            if(autoOffsetReset == null || "".equals(autoOffsetReset)){
                return;
            }
            this.autoOffsetReset = autoOffsetReset;
        }

        public int getConcurrency() {
            return concurrency;
        }

        public void setConcurrency(int concurrency) {
            this.concurrency = concurrency;
        }

        public long getPollTimeout() {
            return pollTimeout;
        }

        public void setPollTimeout(long pollTimeout) {
            this.pollTimeout = pollTimeout;
        }

        public int getThreadPoolSize() {
            return threadPoolSize;
        }

        public void setThreadPoolSize(int threadPoolSize) {
            if(threadPoolSize > 0 ){
                this.threadPoolSize = threadPoolSize;
            }
        }
    }

}

