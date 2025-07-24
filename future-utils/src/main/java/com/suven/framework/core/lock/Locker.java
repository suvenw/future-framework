/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.core.lock;

/**
 * <pre>
 * 自定义锁接口。
 * </pre>
 * @author suven  pf_qq@163.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface Locker<T> {
	/**
	 * 锁定数据。
	 * @param data 数据
	 */
    void lock(T data);
	
	/**
	 * 释放数据锁。
	 * @param data 数据
	 */
    void unlock(T data);
	
	/**
	 * 请确保lockKey是唯一值
	 * 同时为确保锁key最终被clear,设置了1分钟失效时间。
	 * @param lockKey
	 * @return true : 获取锁成功 false：失败
	 */
    boolean obtainLock(String lockKey);
	
	/**
	 * 释放锁,请确保此方法最终被调用.
	 * @param lockKey
	 * @return
	 */
    boolean releaseLock(String lockKey);
}
