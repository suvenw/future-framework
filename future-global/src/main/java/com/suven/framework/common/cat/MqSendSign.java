package com.suven.framework.common.cat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <pre>
 *
 * </pre>
 * @author suvenwang  
 * @version 1.00.00
 * <pre>
 * @desc:  RocketMQ MQ队列配置状态枚举类, api 执行方法上注解
 *  USER_LOGIN(topicName,topicTag,productGroup,consumerGroup, GlobalIdEnum.OAUTH,3),
 * </pre>
 */
@Target(value={ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MqSendSign {

    /** 主题名称,默认为TEST_TOPIC **/
   String topicName() default "TOPIC_TEST";
   String methodName() default "执行方法名称,syncSend";
//    /** 主题名称,默认为TEST_TOPIC **/
//    String topicTag() default "TOPIC_TAG_TEST";
//    /** 主题名称,使用发送名称(syncSend:异步,sendOneway:同步) **/
//
//    /** 主题名称,超时时间,单位为秒 **/
//    long timeOut() default 0L;
//    /** 主题名称,重试次数据,默认为0,不重试 **/
//    int delayLevel() default  0;
//    /** 主题名称,自定义前缀,默认为""**/
//    String prefixKeys() default "";

}

