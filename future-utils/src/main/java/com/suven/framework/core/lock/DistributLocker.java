package com.suven.framework.core.lock;

import com.suven.framework.core.redis.RedisClientServer;
import com.suven.framework.core.redis.RedisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DistributLocker implements Locker<Object>{
	private final static Logger logger = LoggerFactory.getLogger(DistributLocker.class);
	@Autowired
	private RedisClientServer redisClient;

	@Override
	public boolean obtainLock(String lockKey){
		String threadName =  Thread.currentThread().getName();
		String nXkey = RedisConstants.REDIS_LOCK_PREFIX+lockKey;
		for( int i = 0 ; i < 3; i++ ){
			boolean value = redisClient.setNx(nXkey, threadName, RedisConstants.THREE_SECOND);
			if(value){
				return true;
			}
			logger.warn("lock wait and retry: {}, {}", nXkey, i);
			try {
				Thread.sleep(10);
			} catch (Exception ignore) {
			}
		}
		return false;
	}


	/**
	 * 释放锁,请确保此方法最终被调用.
	 * @param lockKey
	 * @return
	 */
	@Override
	public boolean releaseLock(String lockKey){
		String threadId =  Thread.currentThread().getName();
		logger.info("Thread：" + threadId + " start unlock");

		String value = redisClient.get(lockKey);
		if(value != null && value.equals(threadId)) {//如果是本线程加锁
			redisClient.delete(lockKey);
			logger.info("Thread：" + Thread.currentThread().getName() + " unlock success");
		}
		return true ;
	}

	@Override
	public void lock(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlock(Object data) {
		// TODO Auto-generated method stub
		
	}
}
