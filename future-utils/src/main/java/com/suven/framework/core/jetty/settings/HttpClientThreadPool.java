package com.suven.framework.core.jetty.settings;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @Title: HttpClientThreadPool.java
 * @author suven
 * @date   2019-10-18 12:35:25
 * @version V1.0
 * Description: (说明)    注意：这个线程池只给关注关系使用
 * Copyright: (c) 2018 gc by https://www.suven.top
 */


@Component
public class HttpClientThreadPool {

	public static final int CORES = Runtime.getRuntime().availableProcessors();
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final static ExecutorService pools = Executors.newFixedThreadPool((CORES > 2 ? CORES-2 : CORES), 
			new ThreadFactoryBuilder().setNameFormat("service-worker-%d").build());
	
	public void run(Runnable task) {
		try {
			pools.execute(task);
		} catch (Exception e) {
			logger.warn("ThreadPool - run error ", e);
		}
	}
}
