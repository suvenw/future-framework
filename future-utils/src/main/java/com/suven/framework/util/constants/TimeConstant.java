package com.suven.framework.util.constants;


/**
 * Title: XmlSerializer.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 时间单位静态定义参数接口
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public interface TimeConstant {

	/** 
	 * 一秒钟的毫秒数
	 */
	int ONE_SECOND_MILLISECOND = 1000;
	
	/** 
	 * 一分钟的毫秒数
	 */
	int ONE_MINUTE_MILLISECOND = 60 * ONE_SECOND_MILLISECOND;
	
	/** 
	 * 一小时的毫秒数
	 */
	int ONE_HOUR_MILLISECOND = 60 * ONE_MINUTE_MILLISECOND;
	
	/** 
	 * 一天的毫秒数
	 */
	int ONE_DAY_MILLISECOND = 24 * ONE_HOUR_MILLISECOND;
	
	/** 
	 * 一分钟的秒数
	 */
	int ONE_MINUTE_SECOND = ONE_MINUTE_MILLISECOND / ONE_SECOND_MILLISECOND;
	
	/** 
	 * 1小时的秒数
	 */
	int ONE_HOUR_SECOND = ONE_HOUR_MILLISECOND / ONE_SECOND_MILLISECOND;
	
	/** 
	 * 1天的秒数
	 */
	int ONE_DAY_SECOND = ONE_DAY_MILLISECOND / ONE_SECOND_MILLISECOND;
	
	/** 
	 * 7天的秒数
	 */
	int SEVEN_DAY_SECOND = 7 * ONE_DAY_SECOND;
}
