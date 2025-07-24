package com.suven.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public  class PropertiesInstHelper {
	private  Logger logger = LoggerFactory.getLogger(PropertiesInstHelper.class);
	
//	@Value("${env}")
	private String env = PropertiesUtil.getString("env","test");
	
	/**
	 * @return true 外网生产环境
	 */
	public  boolean isProd() {
		//默认是生产环境，以免外网出错
		return env == null || "prod".equals(env);
	}
	
	/**
	 * @return true 开发人员自己的环境
	 */
	public  boolean isDev() {
		return "dev".equals(env);
	}
	
	/**
	 * @return true 内网服务器环境
	 */
	public  boolean isStage() {
		return "stage".equals(env);
	}
	
}
