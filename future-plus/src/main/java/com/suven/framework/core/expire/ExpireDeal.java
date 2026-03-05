package com.suven.framework.core.expire;

import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 针对ssdbkey类解析Expire注解
 * @author summerao
 *
 */
@Component
public class ExpireDeal {
	private static Map<String, Integer> map = new ConcurrentHashMap<>();
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final static Class KeyClass = ExpireDeal.class;

	@PostConstruct
	public void init() {
		Map<String, Object> annoMap = annoValue(Expire.class);
		tranValue(annoMap);
	}
	private void tranValue(Map<String, Object> annoMap){
		if(map ==null){
			return;
		}
		for(Entry<String, Object> entry : annoMap.entrySet()){
			String key = entry.getKey();
			Expire expire = (Expire) entry.getValue();
			int num = expire.value();
	        int expireTime = (int) expire.unit().toSeconds(num);
			map.put(key, expireTime);
		}
	}
	public static int get(String key) {
		Integer expireTime = map.get(key);
		if (expireTime == null) {
			return 0;
		}
		return expireTime;
	}
	private Map<String, Object> annoValue(Class<? extends Annotation> annoType) {
        Map<String, Object> expireToAnno = new HashMap<>();
        Annotation head = AnnotationUtils.findAnnotation(KeyClass, annoType);
        if (head == null) {
            return Collections.emptyMap();
        }
        Field[] fields = KeyClass.getFields();
        for (Field field : fields) {
            Annotation anno = AnnotationUtils.getAnnotation(field, annoType);
            if (anno == null) {
                anno = head;
            }
            String expireStr = null;
            try {
            	expireStr = FieldUtils.readStaticField(field).toString();
            	expireToAnno.put(expireStr, anno);
            } catch (Exception e) {
                logger.warn("", e);
            }
        }
        return expireToAnno;
    }

}
