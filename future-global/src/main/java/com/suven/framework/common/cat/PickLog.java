package com.suven.framework.common.cat;


import java.lang.annotation.*;

/**
 *
 *
 */

/**
 * ClassName: PickLog.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-12-20 10:56:17
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 系统日志注解
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PickLog {

	/**
	 * 日志分类唯一标识
	 * 
	 * @return
	 */
	String topicName() default "";

	/**
	 * 日志类型
	 * 
	 * @return 0:操作日志;1:登录日志;2:定时任务;
	 */
	int logType() default 0;

	/**
	 * 操作日志类型
	 *
	 * @return （1.写文件,2.写db数据库,3.写Clickhouse, 4.写elk）
	 */
	int logWrite() default 1;
	
	/**
	 * 操作日志类型
	 * 
	 * @return （1查询，2添加，3修改，4删除）
	 */
	int operateType() default 1;


}
