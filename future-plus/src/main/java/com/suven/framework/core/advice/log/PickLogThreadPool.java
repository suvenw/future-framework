package com.suven.framework.core.advice.log;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.suven.framework.http.api.IBeanClone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;


/**
 * @Title: KafkaConsumerThreadPool.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) kafka 消费端线程池;
 *
 * 	线程池初始化方法
 *
 * 	corePoolSize 核心线程池大小----10
 * 	maximumPoolSize 最大线程池大小----1000
 * 	keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
 * 	TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
 * 	workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(10)====10容量的阻塞队列
 * 	threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
 * 	rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时,
 * 								即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
 * 							          任务会交给RejectedExecutionHandler来处理
 */

@Component
public class PickLogThreadPool {

	public static final int CORES = Runtime.getRuntime().availableProcessors();

	private final Logger logger = LoggerFactory.getLogger(getClass());


	/**
	 * 线程池初始化方法
	 *
	 * corePoolSize 核心线程池大小----10
	 * maximumPoolSize 最大线程池大小----1000
	 * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
	 * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
	 * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(10)====10容量的阻塞队列
	 * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
	 * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时,
	 * 							即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
	 * 						          任务会交给RejectedExecutionHandler来处理
	 */

	private final static ExecutorService executors = new ThreadPoolExecutor(
			2, 1000, 10L,
			TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue(10000),
			new ThreadFactoryBuilder().setNameFormat("pick-logger-worker-%d").build());



	
	public Future<IBeanClone>  submit(Callable<IBeanClone> callable) {
		try {
			Future<IBeanClone> futureResult = executors.submit(callable);
			return futureResult;
		} catch (Exception e) {
			logger.warn("ThreadPool - PickLoggerThreadPool Exception ", e);
		}
		return null;
	}
}
