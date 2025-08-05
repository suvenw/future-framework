package com.suven.framework.core.kafka;


/**
 * Title: KafkaMessage.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) kafka 消费端消息对象封装类,请求类型封装实现参数类;
 */

public class KafkaMessage {
    /** 功能码topic*/
    private  String topic;
    /** 功能码类型*/
    private  String key;
    /** 功能结果字符串**/
    private  String value;
    /** 功能请求参数对象类**/


    public KafkaMessage(){

    }
    public KafkaMessage(String messager){

    }

    public KafkaMessage(String topic, String key,String value) {
        this.value = value;
        this.topic = topic;
        this.key = key;
    }

    public static KafkaMessage sendKafkaMessage(String topic, String key, String value)  {
        KafkaMessage message =  new KafkaMessage(topic,key,value);
        return  message;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KafkaMessage{" +
                "topic='" + topic + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}