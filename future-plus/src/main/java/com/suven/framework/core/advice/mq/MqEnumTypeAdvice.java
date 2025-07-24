package com.suven.framework.core.advice.mq;

import com.suven.framework.common.constants.ReflectionsScan;
import com.suven.framework.core.rocketmq.IRocketMqTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public  class MqEnumTypeAdvice {

	private static Logger logger = LoggerFactory.getLogger(MqSendAspectAdvice.class);
	private static Map<String, IRocketMqTopic> msgTypeMap = new TreeMap<>();
	private static Set<String> checkCodeSet = new HashSet<>();

	public static Map<String, IRocketMqTopic> getRocketMqTopicMap() {
			return msgTypeMap;
	}

		/** 通过 topic 获取 IRocketMqTopic**/
	public static IRocketMqTopic getRocketMqTopic(String topic) {
			return msgTypeMap.get(topic);
	}


		static {
			List<Enum> list  =  getIMsgEnum();
			if(list != null && !list.isEmpty()){
				for(Enum msg : list){
					IRocketMqTopic imsg = (IRocketMqTopic)msg;
					msgTypeMap.put(imsg.getTopic(), imsg);
					if(checkCodeSet.contains(imsg.getTopic())){
						logger.error(" IMsgEnum is type double Topic :[{}] and Tag :[{}] is exist ",imsg.getTopic(),imsg.getTag());
						throw new RuntimeException(" IMsgEnum is type double :" +imsg.getTopic()+" and "+ imsg.getTag() +"is exist ");
					}
					checkCodeSet.add(imsg.getTopic());
				}
				checkCodeSet = null;
			}

		}

		private static <E extends Enum<E>> List<E> getIMsgEnum() {
			Set<Class<? extends IRocketMqTopic>> classList = ReflectionsScan.reflections.getSubTypesOf(IRocketMqTopic.class);
			List<E> list = new ArrayList<>();
			if(null == classList || classList.isEmpty()){
				return list;
			}
			for (Class<?> clazz : classList){
				try {
					if (!clazz.isEnum()) {
						continue;
					}
					Class<E> enumClass = (Class<E>)clazz;
					list.addAll(Arrays.asList(enumClass.getEnumConstants()));

				}catch (Exception e){
					e.printStackTrace();
				}
			}
			return list;
		}


	}