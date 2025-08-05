package com.suven.framework.util.ids;


import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Title: GUID.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) GUID实现全局唯一ID生成器
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public class GUID {

	private static class GUIDHolder {
		private static final GUID instance = new GUID();
	}
	private long maxSeq = 16382L;
	private long rand = random();
	private long seq = 0L;
	private long time = System.nanoTime();

	public static GUID instance = null;
	private static final long ONE_STEP = 10;
	private static long lastTime = System.currentTimeMillis();
	private static short count = 0;

	/**
	 * 获取id生成器类的单例对象
	 * @return
	 */
	public static GUID instance(){
		return GUIDHolder.instance;
	}

	public long next() {
		long value = (random() << 64L);
		synchronized (this) {
			if (seq < maxSeq) {
				seq++;
			} else {
				seq = 0L;
				rand = random();
				time = System.nanoTime();
			}
			value += (seq << 32L);
			value += rand << 16L;
			value += time;
		}
		return value;
	}







	/**
	 * 获取全局唯一的id ,id = 系统时间 +计数
	 * @return
	 */
	public synchronized String getUID(){
		if (count == ONE_STEP) {
			boolean done = false;
			while (!done) {
				long now = System.currentTimeMillis();
				if (now == lastTime) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
					}
					continue;
				} else {
					lastTime = now;
					count = 0;
					done = true;
				}
			}
		}
		String result = lastTime + "" + (count++);
		return result;
	}

	private long random() {
		return ThreadLocalRandom.current().nextLong(128, 2048);
	}


	/**
	 * 去除"-"间隔符
	 * @return String
	 */
	public static String getUUIDoffSpace() {

		String s = UUID.randomUUID().toString().toUpperCase();
		return s.replace("-", "");
	}
	public static String halfUUID(){
		String s = UUID.randomUUID().toString();
		s = s.replace("-","").substring(0,16);
		return s;
	}

	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID(){
		String s = UUID.randomUUID().toString();
		return s;
	}

	/**
	 * 根据参数number 获取number个UUID
	 * @param number
	 * @return
	 */
	public static String[] getUUID(int number) {
		if (number < 1){
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < ss.length; i++) {
			ss[i] = getUUIDoffSpace();
		}
		return ss;
	}
	
}
