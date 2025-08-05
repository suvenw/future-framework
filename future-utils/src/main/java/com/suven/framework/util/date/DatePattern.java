package com.suven.framework.util.date;

import com.suven.framework.util.constants.TimeConstant;


/**
 * Title: UrlParamSign.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 时间格式定义参数接口；
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public interface DatePattern extends TimeConstant {

	/** HH:mm 格式 */
	String PATTERN_HH_MM = "HH:mm";

	/**日期**/
	String PATTERN_DD="dd";

	/** yyyy 格式 */
	String FORMAT_YYYY = "yyyy";
	/** yyyy-MM 格式 */
	String FORMAT_YYYYMM = "yyyy-MM";
	/** yyyy-MM-dd 格式 */
	String FORMAT_YYYYMMDD = "yyyy-MM-dd";
	/** yyyy-MM-dd HH 格式 */
	String FORMAT_YYYYMMDDHH = "yyyy-MM-dd HH";
	/** yyyy-MM-dd HH:mm 格式 */
	String FORMAT_YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
	/** yyyy-MM-dd HH:mm:ss 格式 */
	String FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-dd HH:mm:ss.SSS 格式 */
	String FORMAT_YYYYMMDDHHMMSSSSS ="yyyy-MM-dd HH:mm:ss.SSS";

	/** yyyyMM 格式 */
	String PATTERN_YYYYMM = "yyyyMM";
	/** yyyyMMdd 格式 */
	String PATTERN_YYYYMMDD = "yyyyMMdd";
	/** yyyyMMddHHmm 格式 */
	String PATTERN_YYYYMMDDHH = "yyyyMMddHH";
	/** yyyyMMddHHmm 格式 */
	String PATTERN_YYYYMMDDHHMM = "yyyyMMddHHmm";
	/** yyyyMMddHHmmss 格式 */
	String PATTERN_YYYYMMDDHHMMSS ="yyyyMMddHHmmss";
	/** yyyyMMddHHmmssSSS 格式 */
	String PATTERN_YYYYMMDDHHMMSSSSS ="yyyyMMddHHmmssSSS";





	String[] parsePatterns = {
			FORMAT_YYYY, FORMAT_YYYYMM,   FORMAT_YYYYMMDD, FORMAT_YYYYMMDDHH, FORMAT_YYYYMMDDHHMM,FORMAT_YYYYMMDDHHMMSS, FORMAT_YYYYMMDDHHMMSSSSS,
			PATTERN_DD,  PATTERN_YYYYMM, PATTERN_YYYYMMDD, PATTERN_YYYYMMDDHH,PATTERN_YYYYMMDDHHMM,PATTERN_YYYYMMDDHHMMSS, PATTERN_YYYYMMDDHHMMSSSSS,
			PATTERN_HH_MM
	};
}
