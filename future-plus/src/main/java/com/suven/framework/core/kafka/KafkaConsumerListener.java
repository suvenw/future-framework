package com.suven.framework.core.kafka;

import com.suven.framework.util.json.JsonUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Title: KafkaProducerService.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) kafka 这是kafka 接入spring boot 管理的新实现方式用例;旧模式已去用了;
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

//@Component
public class KafkaConsumerListener {

	private static Logger logger = LoggerFactory.getLogger(KafkaConsumerListener.class);

//	@Autowired
    KafkaConsumerThreadPool kafkaThreadPool;

//    @KafkaListener(topics = {"top-test"})
    public void testListener(ConsumerRecord record) {
        logger.warn(" ConsumerRecord messager this is the top-test send message = : " + JsonUtils.toJson(record));
        kafkaThreadPool.run(new ConsumerWorkerThread(record,new KafkaConsumerHandler()));
    }
//
//    @KafkaListener(topics = {"top-test-2"})
    public void testListener2(ConsumerRecord<String,String> record) {
        logger.warn(" ConsumerRecord testListener2 this is the top-test-2 send message =-------- : " + record.value().toString());
        kafkaThreadPool.run(new ConsumerWorkerThread(record,new KafkaConsumerHandler()));
    }

//    @Component
    public class KafkaConsumerHandler extends KafkaConsumerAbstractHandler{

        public void executeConsumer(Object value ){
            logger.warn(" abstract  executeConsumer value:[{}]" , JsonUtils.toJson(value));
        }

    }


}

