package com.suven.framework.core.db;


/**
 * @Title: DataSourceHolder.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 通过 ThreadLocal 当前线程安全类，实现线程内，参数切换实现业务解偶；
 *  * 以达到动态数据库连接池实现切换，实现多数据源原理
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class DataSourceHolder {
	// 数据源名称线程池
	private static final ThreadLocal<DataSourceGroup> holder = new ThreadLocal<>();
	



	public static void putDataSource(DataSourceGroup dataChooseParam) {
		
		holder.set(dataChooseParam);
	}

	public static DataSourceGroup getDataSource() {
		return holder.get();
	}

	public static void clear() {
//		holder.remove();
	}



	public static void remove(){
		holder.remove();
	}
}
