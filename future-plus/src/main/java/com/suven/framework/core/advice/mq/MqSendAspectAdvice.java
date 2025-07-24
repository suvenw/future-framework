package com.suven.framework.core.advice.mq;


import com.suven.framework.common.cat.MqSendSign;
import com.suven.framework.core.advice.AdviceConfigSetting;
import com.suven.framework.core.advice.BaseAspectAdvice;
import com.suven.framework.core.rocketmq.IRocketMqTopic;
import com.suven.framework.core.rocketmq.RocketMQApi;
import com.suven.framework.util.json.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *
 * <pre>
 *
 * </pre>
 * @version 1.00.00
 * <pre>
 *
 * </pre>
 */
@Aspect
@Component
public class MqSendAspectAdvice extends BaseAspectAdvice {

	private static Logger logger = LoggerFactory.getLogger(MqSendAspectAdvice.class);

	@Autowired(required = false)
	private RocketMQApi rocketMQApi;
	@Autowired(required = false)
	private AdviceConfigSetting catAdviceConfigSetting;

	@Around("@annotation(mqSendSign)")
	public Object mqSendSignAdvice(ProceedingJoinPoint joinPoint, MqSendSign mqSendSign) throws Throwable {

		if (rocketMQApi == null) {
			logger.warn("RocketMQApi Bean is null start, please init RocketMQApi spring.rocketmq.producer.api.enabled=true");
			return null;
		}
		//查验配置mg是否生效
		if (!validator()) {
			return this.getMethodReturnValue(joinPoint);
		}
		Object value = null;
		try {
			String methodName = mqSendSign.methodName();
			IRocketMqTopic iRocketMqTopic = MqEnumTypeAdvice.getRocketMqTopic(mqSendSign.topicName());
			value = this.getMethodReturnValue(joinPoint);
			Method method = rocketMQApi.getClass().getMethod(methodName, IRocketMqTopic.class, Object.class);
			logger.debug("===================== mqSendSignAdvice ===================== "+ JsonUtils.toJson(value));
			method.invoke(rocketMQApi, iRocketMqTopic, value);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("mqSendSignAdvice Exception[{}]", e);

		}
		return value;

	}


	public boolean validator()  {
		if (null!= catAdviceConfigSetting && catAdviceConfigSetting.isValidator("mq")) {
			return true;
		}
		return false;
	}

}


