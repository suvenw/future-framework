/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.core.lock;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

/**
 * <pre>
 * 对key-value中的key上锁。
 * </pre>
 * 
 * @author suvenw@163.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Component
public class KeyLocker<K> implements Locker<K> {
	/**
	 * 保存所有锁定的KEY及其信号量
	 */
	private final ConcurrentMap<K, Semaphore> map = new ConcurrentHashMap<K, Semaphore>();

	/**
	 * 保存每个线程锁定的KEY及其锁定计数
	 */
	private final ThreadLocal<Map<K, LockInfo>> local = new ThreadLocal<Map<K, LockInfo>>() {
		@Override
		protected Map<K, LockInfo> initialValue() {
			return new HashMap<K, LockInfo>();
		}
	};

	/**
	 * 
	 * <pre>
	 * 锁相关。
	 * </pre>
	 * 
	 * @author suvenw@163.com
	 * @version 1.00.00
	 * 
	 *          <pre>
	 * 修改记录
	 *    修改后版本:     修改人：  修改日期:     修改内容:
	 * </pre>
	 */
	private static class LockInfo {
		/**
		 * 当前线程锁Semaphore。
		 */
		private final Semaphore current;
		/**
		 * 当前线程锁次数。
		 */
		private int lockCount;

		private LockInfo(Semaphore current) {
			this.current = current;
			this.lockCount = 1;
		}
	}

	@Override
	public void lock(K key) {
		if (key == null)
			return;
		LockInfo info = local.get().get(key);
		if (info == null) {
			Semaphore current = new Semaphore(1);
			current.acquireUninterruptibly();
			Semaphore previous = map.put(key, current);

			if (previous != null)
				previous.acquireUninterruptibly();
			local.get().put(key, new LockInfo(current));
		} else {
			info.lockCount++;
		}
	}

	@Override
	public void unlock(K key) {
		if (key == null)
			return;
		LockInfo info = local.get().get(key);
		if (info != null && --info.lockCount == 0) {
			info.current.release();
			map.remove(key, info.current);
			local.get().remove(key);
		}
	}

	@Override
	public boolean obtainLock(String lockKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean releaseLock(String lockKey) {
		// TODO Auto-generated method stub
		return false;
	}
}
