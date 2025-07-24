package com.suven.framework.util;

import com.suven.framework.common.constants.ReflectionsScan;
import com.suven.framework.util.constants.ConfLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public abstract class PropertiesHelper {
	private static Logger logger = LoggerFactory.getLogger(PropertiesHelper.class);
	private static Properties props;
	static {
		props = new Properties();


		Set<Class<? extends ConfLoader>> clazzSet = ReflectionsScan.reflections.getSubTypesOf(ConfLoader.class);
		try {
			for (Class<? extends ConfLoader> clazz : clazzSet) {
				List<String> list = clazz.newInstance().getConfList();
				for (String str : list) {
					props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(str));
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public static String getProp(String key) {
		return props.getProperty(key);
	}

}
