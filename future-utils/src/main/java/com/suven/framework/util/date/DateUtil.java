package com.suven.framework.util.date;

import com.suven.framework.util.constants.TimeConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.*;



/**
 * Title: UrlParamSign.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 日期工具类；
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public class DateUtil implements DatePattern {

		private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

		/**
		 * 检查当前时间和指定时间是否同一周
		 *
		 * @param year           年
		 * @param week           周
		 * @param firstDayOfWeek 周的第一天设置值，{@link Calendar#DAY_OF_WEEK}
		 * @return {@link Boolean}	true-同一周, false-不同周
		 */
		public static boolean isSameWeek(int year, int week, int firstDayOfWeek) {
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(firstDayOfWeek);
			return year == cal.get(YEAR) && week == cal.get(WEEK_OF_YEAR);
		}

		/**
		 * 检查当前时间和指定时间是否同一周
		 *
		 * @param time           被检查的时间
		 * @param firstDayOfWeek 周的第一天设置值，{@link Calendar#DAY_OF_WEEK}
		 * @return {@link Boolean}	true-同一周, false-不同周
		 */
		public static boolean isSameWeek(Date time, int firstDayOfWeek) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			cal.setFirstDayOfWeek(firstDayOfWeek);
			return isSameWeek(cal.get(YEAR), cal.get(WEEK_OF_YEAR), firstDayOfWeek);
		}

		/**
		 * 获取周的第一天
		 *
		 * @param firstDayOfWeek 周的第一天设置值，{@link Calendar#DAY_OF_WEEK}
		 * @param time           指定时间，为 null 代表当前时间
		 * @return {@link Date}		周的第一天
		 */
		public static Date firstTimeOfWeek(int firstDayOfWeek, Date time) {
			Calendar cal = Calendar.getInstance();
			if (time != null) {
				cal.setTime(time);
			}
			cal.setFirstDayOfWeek(firstDayOfWeek);
			int day = cal.get(DAY_OF_WEEK);

			if (day == firstDayOfWeek) {
				day = 0;
			} else if (day < firstDayOfWeek) {
				day = day + (7 - firstDayOfWeek);
			} else if (day > firstDayOfWeek) {
				day = day - firstDayOfWeek;
			}

			cal.set(HOUR_OF_DAY, 0);
			cal.set(MINUTE, 0);
			cal.set(SECOND, 0);
			cal.set(MILLISECOND, 0);

			cal.add(DATE, -day);
			return cal.getTime();
		}

		/**
		 * 检查指定日期是否今天(使用系统时间)
		 *
		 * @param date 被检查的日期
		 * @return
		 */
		public static boolean isToday(Date date) {
			Calendar cal = Calendar.getInstance();
			cal.add(DATE, 1);
			cal.set(HOUR_OF_DAY, 0);
			cal.set(MINUTE, 0);
			cal.set(SECOND, 0);
			cal.set(MILLISECOND, 0);
			Date end = cal.getTime(); // 明天的开始
			cal.add(MILLISECOND, -1);
			cal.add(DATE, -1);
			Date start = cal.getTime(); // 昨天的结束
			return date.after(start) && date.before(end);
		}

		/**
		 * 日期转换成字符串格式
		 *
		 * @param theDate     待转换的日期
		 * @param datePattern 日期格式
		 * @return 日期字符串
		 */
		public static String date2Str(Date theDate, String datePattern) {
			if (theDate == null) {
				return "";
			}

			DateFormat format = new SimpleDateFormat(datePattern);
			try {
				return format.format(theDate);
			} catch (Exception e) {
				return "";
			}
		}

		/**
		 * 字符串转换成日期格式
		 *
		 * @param dateString  待转换的日期字符串
		 * @param datePattern 日期格式
		 * @return {@link Date}		转换后的日期
		 */
		public static Date str2Date(String dateString, String datePattern) {
			if (dateString == null || dateString.trim().isEmpty()) {
				return null;
			}

			DateFormat format = new SimpleDateFormat(datePattern);
			try {
				return format.parse(dateString);
			} catch (ParseException e) {
				log.error("ParseException in Converting String to date: " + e.getMessage());
			}

			return null;

		}

		/**
		 * 字符串转换成日期格式
		 *
		 * @param dateString 待转换的日期字符串
		 * @return {@link Date}		转换后的日期
		 */
		public static Date str2Date(String dateString) {
			if (dateString == null || dateString.trim().isEmpty()) {
				return null;
			}
			DateFormat format = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
			try {
				return format.parse(dateString);
			} catch (ParseException e) {
				log.error("ParseException in Converting String to date: " + e.getMessage());
			}

			return null;

		}

		/**
		 * 把秒数转换成把毫秒数
		 *
		 * @param seconds 秒数的数组
		 * @return {@link Long} 毫秒数
		 */
		public static long toMillisSecond(long... seconds) {
			long millis = 0L;
			if (seconds != null && seconds.length > 0) {
				for (long time : seconds) {
					millis += (time * TimeConstant.ONE_SECOND_MILLISECOND);
				}
			}
			return millis;
		}

		/**
		 * 把毫秒数转换成把秒数
		 *
		 * @param millis 毫秒数的数组
		 * @return {@link Long} 毫秒数
		 */
		public static long toSecond(long... millis) {
			long second = 0L;
			if (millis != null && millis.length > 0) {
				for (long time : millis) {
					second += (time / TimeConstant.ONE_SECOND_MILLISECOND);
				}
			}
			return second;
		}

		/**
		 * 修改日期
		 *
		 * @param theDate 待修改的日期
		 * @param addYeas 加减的年数
		 * @return 修改后的日期
		 */

		public static Date changeDateOfYear(Date theDate, int addYeas) {
			return changeDateOfMonth(theDate, addYeas * 12);
		}

		/**
		 * 修改日期
		 *
		 * @param theDate  待修改的日期
		 * @param addMonth 加减的月数
		 * @return 修改后的日期
		 */
		public static Date changeDateOfMonth(Date theDate, int addMonth) {
			if (theDate == null) {
				return null;
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(theDate);

			cal.add(MONTH, addMonth);
			return cal.getTime();
		}

		/**
		 * 把当前时间格式化成yyyy-MM-dd HH:mm:ss
		 *
		 * @return String
		 */
		public static final String date() {
			return date(FORMAT_YYYYMMDDHHMMSS);
		}

		/**
		 * 把当前时间格式化
		 *
		 * @param format
		 * @return String
		 */
		public static final String date(String format) {
			return date(format, System.currentTimeMillis());
		}

		/**
		 * 把时间戳（秒）格式化成yyyy-MM-dd HH:mm:ss
		 *
		 * @param timestamp
		 * @return String
		 */
		public static final String date(int timestamp) {
			return date(FORMAT_YYYYMMDDHHMMSS, timestamp);
		}

		/**
		 * 把时间戳（毫秒）格式化成yyyy-MM-dd HH:mm:ss
		 *
		 * @param timestamp
		 * @return String
		 */
		public static final String date(long timestamp) {
			return date(FORMAT_YYYYMMDDHHMMSS, timestamp);
		}

		/**
		 * 把时间戳格式化
		 *
		 * @param format
		 * @param timestamp 秒
		 * @return String
		 */
		public static final String date(String format, int timestamp) {
			return date(format, timestamp * TimeConstant.ONE_SECOND_MILLISECOND);
		}

		/**
		 * 把时间戳格式化
		 *
		 * @param format
		 * @param timestamp 毫秒
		 * @return String
		 */
		public static final String date(String format, long timestamp) {
			return new SimpleDateFormat(format).format(timestamp);
		}

		/**
		 * 修改日期
		 *
		 * @param theDate 待修改的日期
		 * @param addDays 加减的天数
		 * @param hour    设置的小时
		 * @param minute  设置的分
		 * @param second  设置的秒
		 * @return 修改后的日期
		 */
		public static Date changeDateTime(Date theDate, int addDays, int hour, int minute, int second) {
			if (theDate == null) {
				return null;
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(theDate);

			cal.add(DAY_OF_MONTH, addDays);

			if (hour >= 0 && hour <= 24) {
				cal.set(HOUR_OF_DAY, hour);
			}
			if (minute >= 0 && minute <= 60) {
				cal.set(MINUTE, minute);
			}
			if (second >= 0 && second <= 60) {
				cal.set(SECOND, second);
			}

			return cal.getTime();
		}

		public static Date add(Date theDate, int addHours, int addMinutes, int addSecond) {
			if (theDate == null) {
				return null;
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(theDate);

			cal.add(HOUR_OF_DAY, addHours);
			cal.add(MINUTE, addMinutes);
			cal.add(SECOND, addSecond);

			return cal.getTime();
		}

		public static Date addMin(Date theDate, int addMinutes) {
			if (theDate == null) {
				return null;
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(theDate);
			cal.add(MINUTE, addMinutes);
			return cal.getTime();
		}

		/**
		 * 取得星期几
		 *
		 * @param theDate
		 * @return
		 */
		public static int dayOfWeek(Date theDate) {
			if (theDate == null) {
				return -1;
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(theDate);

			return cal.get(DAY_OF_WEEK);
		}

		/**
		 * 获得某一时间的0点
		 *
		 * @param theDate 需要计算的时间
		 */
		public static Date getDate0AM(Date theDate) {
			if (theDate == null) {
				return null;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(theDate);
			calendar.set(HOUR_OF_DAY, 0);
			calendar.set(MINUTE, 0);
			calendar.set(SECOND, 0);
			calendar.set(MILLISECOND, 0);
			return calendar.getTime();
		}

		/**
		 * 获得某一时间的下一个0点
		 *
		 * @param theDate 需要计算的时间
		 */
		public static Date getNextDay0AM(Date theDate) {
			if (theDate == null) {
				return null;
			}
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(theDate.getTime() + TimeConstant.ONE_DAY_MILLISECOND);
			return new GregorianCalendar(cal.get(YEAR), cal.get(MONTH), cal.get(DAY_OF_MONTH)).getTime();
		}

		/**
		 * 获得指定日期的23点59分59秒的时间
		 *
		 * @param theDate 需要计算的时间
		 */
		public static Date getThisDay2359PM(Date theDate) {
			if (theDate == null) {
				return null;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(theDate);
			calendar.set(HOUR_OF_DAY, 23);
			calendar.set(MINUTE, 59);
			calendar.set(SECOND, 59);
			calendar.set(MILLISECOND, 0);
			return calendar.getTime();
		}

		/**
		 * 获得当天的指定时间点
		 *
		 * @param theDate 需要计算的时间
		 */
		public static Date getSelectTime(Date theDate, int hour, int minute, int second) {
			if (theDate == null) {
				return null;
			}

			if (hour < 0 || minute < 0 || second < 0) {
				return null;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(theDate);
			calendar.set(HOUR_OF_DAY, hour);
			calendar.set(MINUTE, minute);
			calendar.set(SECOND, second);
			calendar.set(MILLISECOND, 0);
			return calendar.getTime();
		}


		/**
		 * 获得指定时间的下个周一的00:00:00的时间
		 *
		 * @param date 指定的时间
		 * @return {@link Date} 周一的00:00:00的时间
		 */
		public static Date getNextMonday(Date date) {
			if (date == null) {
				return null;
			}

			// 本周周一
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateUtil.getDate0AM(date));
			cal.set(DAY_OF_WEEK, MONDAY);

			Calendar nextMondayCal = Calendar.getInstance();
			nextMondayCal.setTimeInMillis(cal.getTimeInMillis() + TimeConstant.ONE_DAY_MILLISECOND * 7);
			return nextMondayCal.getTime();
		}

		/**
		 * 获得获得改变后的时间
		 *
		 * @param addDay 增加的天数(减少天数, 则传负数)
		 * @param to0AM  是否取0点时间
		 * @return
		 */
		public static Date add(int addDay, boolean to0AM) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(DATE, addDay);
			Date time = calendar.getTime();
			return to0AM ? getDate0AM(time) : time;
		}

		/**
		 * 获得指定时间，改变多少天后的时间；
		 *
		 * @param addDay 增加的天数(减少天数, 则传负数)
		 * @param to0AM  是否取0点时间
		 * @return
		 */
		public static Date add(Date date, int addDay, boolean to0AM) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(DATE, addDay);
			Date time = calendar.getTime();
			return to0AM ? getDate0AM(time) : time;
		}

		/**
		 * 获得当前时间的秒
		 *
		 * @return {@link Long} 	当前时间的秒
		 */
		public static long getCurrentSecond() {
			return toSecond(System.currentTimeMillis());
		}

		/**
		 * 获得当前时间的毫秒数
		 *
		 * @return {@link Long}
		 */
		public static long getCurrentMillis() {
			return System.currentTimeMillis();
		}

		/**
		 * 获取该日期在当前年中的第几周
		 *
		 * @param date
		 * @return int
		 */
		public static int getWeekOfYear(Date date) {
			Calendar c = Calendar.getInstance();
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.setMinimalDaysInFirstWeek(7);
			c.setTime(date);
			return c.get(Calendar.WEEK_OF_YEAR);
		}

		/**
		 * 获取该日期所在年份的最后一周
		 *
		 * @param date
		 * @return int
		 */
		public static int getLastWeekOfYear(Date date) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = c.get(Calendar.YEAR);
			c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
			return getWeekOfYear(c.getTime());
		}

		/**
		 * 寻找最合适的单位来显示时间
		 *
		 * @param time
		 * @return
		 * @author ruan 2013-7-21
		 */
		public static final String showTime(long time) {
			String str = "";
			if (time > 0 && time <= 1000) {
				str = time + " ns";
			} else if (time > 1000 && time <= 1000000) {
				str = new DecimalFormat("0.00").format(time / 1000.0) + " μs";
			} else if (time > 1000000 && time <= 1000000000) {
				str = new DecimalFormat("0.00").format(time / 1000000.0) + " ms";
			} else {
				str = new DecimalFormat("0.00").format(time / 1000000000.0) + " s";
			}
			return str;
		}

		/**
		 * 获取当月最后一天是多少号
		 *
		 * @author ruan
		 */
		public static int getLastDayOfMonth() {
			Calendar cDay1 = Calendar.getInstance();
			cDay1.setTimeInMillis(System.currentTimeMillis());
			return cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		}

		/**
		 * 获取下个月
		 *
		 * @return
		 * @author ruan
		 */
		public final static String getNextMonth() {
			Calendar c = Calendar.getInstance();
			Date date = new Date();
			c.setTime(date);
			c.add(Calendar.MONTH, 1);
			return date2Str(c.getTime(), PATTERN_YYYYMM);
		}

		/**
		 * 获取上个月
		 *
		 * @return
		 * @author ruan
		 */
		public final static String getLastMonth() {
			Calendar c = Calendar.getInstance();
			Date date = new Date();
			c.setTime(date);
			c.add(Calendar.MONTH, -1);
			return date2Str(c.getTime(), PATTERN_YYYYMM);
		}

		/**
		 * 判断是否是本月最后一天
		 *
		 * @return
		 */
		public static boolean isLastDayOfMonth() {
			Calendar c = Calendar.getInstance();
			Date date = new Date();
			c.setTime(date);
			Calendar c1 = (Calendar) c.clone();
			c.add(Calendar.DAY_OF_MONTH, 1);
			return c.get(Calendar.MONTH) != c1.get(Calendar.MONTH);
		}

		/**
		 * 判断是否是本月第一天
		 *
		 * @return
		 */
		public static boolean isFirstDayOfMonth() {
			Calendar c = Calendar.getInstance();
			Date date = new Date();
			c.setTime(date);
			Calendar c1 = (Calendar) c.clone();
			c.add(Calendar.DAY_OF_MONTH, -1);
			return c.get(Calendar.MONTH) != c1.get(Calendar.MONTH);
		}

		public static int getCurrDate() {
			Calendar c = Calendar.getInstance();
			Date date = new Date();
			c.setTime(date);
			return c.get(Calendar.DAY_OF_MONTH);
		}

		/**
		 * 日期格式 01
		 *
		 * @return
		 */
		public static String getCurrentDate() {
			Calendar c = Calendar.getInstance();
			Date date = new Date();
			c.setTime(date);
			return date2Str(date, PATTERN_DD);
		}

		/**
		 * 获取当前年份
		 *
		 * @return
		 */
		public static int getCurrYear() {
			Calendar c = Calendar.getInstance();
			Date date = new Date();
			c.setTime(date);
			return c.get(Calendar.YEAR);
		}

		/**
		 * 获取当前月份
		 *
		 * @return
		 */
		public static int getCurrMonth() {
			Calendar c = Calendar.getInstance();
			Date date = new Date();
			c.setTime(date);
			return c.get(Calendar.MONTH) + 1;
		}

		/**
		 * 字符串转时间戳
		 *
		 * @param str         字符串
		 * @param format      时间格式
		 * @param isMicrotime 是否输出毫秒
		 */
		public static final long str2time(String str, String format, boolean isMicrotime) {
			if (str == null || "".equals(str)) {
				return -1;
			}
			try {
				if (isMicrotime) {
					return new SimpleDateFormat(format).parse(str).getTime();
				}
				return new SimpleDateFormat(format).parse(str).getTime() / 1000;
			} catch (Exception e) {
				return -1;
			}
		}

		/**
		 * 字符串转时间戳
		 *
		 * @param str    字符串
		 */
		public static final int str2time(String str) {
			return (int) str2time(str, FORMAT_YYYYMMDDHHMMSS, false);
		}

		/**
		 * 字符串转时间戳
		 *
		 * @param str         字符串
		 * @param isMicrotime 是否输出毫秒
		 */
		public static final long str2time(String str, boolean isMicrotime) {
			return str2time(str, FORMAT_YYYYMMDDHHMMSS, isMicrotime);
		}

		/**
		 * 字符串转时间戳
		 *
		 * @param str    字符串
		 * @param format 时间格式
		 */
		public static final int str2time(String str, String format) {
			return (int) str2time(str, format, false);
		}

		/**
		 * 得到本周的第一天
		 *
		 * @return
		 */
		public static String getCurrWeekBegin() {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_WEEK, calendar
					.getActualMinimum(Calendar.DAY_OF_WEEK));

			return date2Str(calendar.getTime(), FORMAT_YYYYMMDD);
		}

		/**
		 * 得到本周的最后一天
		 *
		 * @return
		 */
		public static String getCurrWeekEnd() {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_WEEK, calendar
					.getActualMaximum(Calendar.DAY_OF_WEEK) + 7);
			return date2Str(calendar.getTime(), FORMAT_YYYYMMDD);
		}

		/**
		 * 获取当前小时
		 *
		 * @return
		 */
		public static int getCurrHour() {
			Calendar c = Calendar.getInstance();
			Date date = new Date();
			c.setTime(date);
			return c.get(Calendar.HOUR_OF_DAY);
		}

		/**
		 * 获取三月前的时间
		 *
		 * @return
		 */
		public static long getThreeMonthAgo() {
			Calendar c = Calendar.getInstance();
			Date date = new Date();
			c.setTime(date);
			c.add(Calendar.MONTH, -3);
			return c.getTimeInMillis();
		}

		/**
		 * 将时间转化成 "yyyy-MM-dd"格式的字符串
		 *
		 * @param date
		 * @return
		 */
		public static String YMDToString(Date date) {
			SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_YYYYMMDD);
			return dateformat.format(date);
		}

		/**
		 * 将时间转换成‘yyyy-MM-dd HH24:mm ’格式的字符串
		 *
		 * @param date
		 * @return
		 */
		public static String YMDHMToString(Date date) {
			if (date == null) {
				return "";
			}
			DateFormat dateformat = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
			return dateformat.format(date);
		}

		public static Date getTomorrowStartDate(Date theDate) {//获得指定日期的明天开始时间0点0分0秒的时间
			if (theDate == null) {
				return null;
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(theDate);
			cal.add(DATE, 1);
			cal.set(HOUR_OF_DAY, 0);
			cal.set(MINUTE, 0);
			cal.set(SECOND, 0);
			cal.set(MILLISECOND, 0);
			Date tomorrowStartDate = cal.getTime(); //明天的开始时间
			return tomorrowStartDate;
		}

		/**
		 * 获取日期, 以参考日期date为标准进行日期增减。
		 *
		 * @param offset 日期偏移量，昨天是－1
		 * @return yyyy-MM-dd
		 */
		public static String yyyy_MM_dd(final int offset) {
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, offset);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.format(cal.getTime());
		}

		/**
		 * 获取当前日期的无分隔符全数字格式
		 *
		 * @return yyyyMMddHHmmss
		 */
		public static String getNowToNumFormat() {
			return date2Str(new Date(), PATTERN_YYYYMMDDHHMMSS);
		}


//	public static boolean between() {
//		SimpleDateFormat df = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
//		try {
//			Date dateAfter = df.parse("2015-09-05 23:59:59");
//			Date dateBefor = df.parse("2015-09-03 00:00:00");
//			Date currDate = new Date();
//			if (currDate.before(dateAfter) && currDate.after(dateBefor)) {
//				return true;
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
//		return false;
//
//	}





		/**
		 * 得到指定月的天数
		 */
		public static int getMonthLastDay(int year, int month) {
			Calendar a = Calendar.getInstance();
			a.set(Calendar.YEAR, year);
			a.set(Calendar.MONTH, month - 1);
			a.set(Calendar.DATE, 1);//把日期设置为当月第一天
			a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
			int maxDate = a.get(Calendar.DATE);
			return maxDate;
		}

		/**
		 * 时间格式转换
		 *
		 * @param date
		 * @return
		 * @throws ParseException
		 */
		public static Date getDate(String date) {
			try {
				return new SimpleDateFormat(PATTERN_YYYYMMDDHHMMSS).parse(date);
			} catch (ParseException e) {
				log.error("ParseException in Converting String to date: " + e.getMessage(), e);
			}
			return null;
		}

		/**
		 * 获取当月第一天时间
		 *
		 * @return
		 * @throws ParseException
		 */
		public static Date getMounthFirstDay() {
			SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_YYYYMMDDHHMMSS);
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_MONTH, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			String dateStr = sdf.format(cal.getTime());
			try {
				return dateFormat.parse(dateStr);
			} catch (ParseException e) {
			}
			return null;
		}

		/**
		 * 获取下个月第一天时间
		 *
		 * @return
		 * @throws ParseException
		 */
		public static Date getNextMounthFirstDay() {
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
			String dateStr = sdf.format(cal.getTime());
			try {
				return dateFormat.parse(dateStr);
			} catch (ParseException e) {
			}
			return null;
		}

		/**
		 * 获取当月最后一天时间
		 *
		 * @return
		 * @throws ParseException
		 */
		public static Date getMounthLatDay() {
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.roll(Calendar.DAY_OF_MONTH, -1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
			String dateStr = sdf.format(cal.getTime());
			Date parse = null;
			try {
				parse = dateFormat.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return parse;
		}

		/**
		 * 获取两个时间的相差秒
		 *
		 * @param beforDate
		 * @param afterDate
		 * @return
		 */
		public static int getTimeSub(Date beforDate, Date afterDate) {
			if (null == beforDate || null == afterDate) {
				return 0;
			}

			long beforDateTime = beforDate.getTime();
			long afterDateTime = afterDate.getTime();

			if (beforDateTime > afterDateTime) {
				return 0;
			}

			long sub = afterDateTime - beforDateTime;
			if (sub >= 1000) {//不足一秒，直接设置为0
				int i = (int) (sub / 1000);
				return i;
			}
			return 0;
		}



		public enum DateEnum {

			YEARS(1),
			MONTHS(2),
			WEEKS(3),
			DAYS(5),
			HOURS(11),
			MINUTES(12),
			SECONDS(13),
			MILLISECONDS(14),
			;
			private int index;
			DateEnum(int index){
				this.index = index;
			}

			private static Map<Integer, DateEnum> enumMap = new HashMap<>();
			static {
				for(DateEnum enums : values()) {
					enumMap.put(enums.index, enums);
				}
			}

			public int getIndex() {
				return index;
			}

			public static DateEnum getDate(int index){
				return enumMap.get(index);
			}

		}

		/**
		 * 按时间类型增加时间数；
		 * @param date
		 * @param dateEnum
		 * @param amount
		 * @return
		 */
		public static Date addField(Date date, DateEnum dateEnum, int amount) {
			if (date == null) {
				throw new IllegalArgumentException("The date must not be null");
			} else {
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(dateEnum.index, amount);
				return c.getTime();
			}

		}

		public static String getDate(long millis) {
			if (millis < 0) {
				return null;
			}
			return DateFormatUtils.format(millis, PATTERN_YYYYMMDDHHMM);
		}

		public static String getDateString(Date date, String format){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}


		public static Date parseDate(String str) {
			if (StringUtils.isBlank(str)){
				return null;
			}
			try {
				return  DateUtils.parseDate(str, parsePatterns);
			} catch (Exception e) {
				return null;
			}
		}
		/**
		 * 获取两个日期之间的天数
		 *
		 * @param before
		 * @param after
		 * @return
		 */
		public static double getDistanceOfTwoDate(Date before, Date after) {
			long beforeTime = before.getTime();
			long afterTime = after.getTime();
			return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
		}

		/**
		 * 获取两个时间中的具体多少天，小时，分钟
		 * @param fDate
		 * @param oDate
		 * @return
		 */
		public static String getIntervalTimes(Date fDate, Date oDate){
			if (null == fDate || null == oDate) {
				return null;
			}
			long intervalMilli = oDate.getTime() - fDate.getTime();
			if(intervalMilli < 0){
				return null;
			}

			long d=intervalMilli /  (24 * 60 * 60 * 1000);//天
			long h=(intervalMilli-d*(24 * 60 * 60 * 1000)) / (60 * 60 * 1000);//小时
			long m=(intervalMilli-d*(24 * 60 * 60 * 1000)-h*(60 * 60 * 1000)) / 60000;//分钟
			long s=(intervalMilli-d*(24 * 60 * 60 * 1000)-h*(60 * 60 * 1000) - m*(60 * 1000)) / 1000;//秒
			String expireTime = getExpireTime(d, h, m, s);
			return expireTime;
		}

	public static String getExpireTime(long d, long h, long m, long s) {
		// 使用 StringBuilder 拼接字符串
		StringBuilder expireTimeBuilder = new StringBuilder();

		if (d > 0) {
			expireTimeBuilder.append(d).append("天");
			if (h > 0) {
				expireTimeBuilder.append(h).append("小时");
			}
		} else if (h > 0) {
			expireTimeBuilder.append(h).append("小时");
			if (m > 0) {
				expireTimeBuilder.append(m).append("分钟");
			}
		} else if (m > 0) {
			expireTimeBuilder.append(m).append("分钟");
		} else if (s > 0) {
			expireTimeBuilder.append(s).append("秒");
		}

		// 返回拼接结果
		return expireTimeBuilder.toString();
	}



		/**
		 * 获取两个日期之间的小时
		 *
		 * @param before
		 * @param after
		 * @return
		 */
		public static double getDistanceOfTwoDateH(Date before, Date after) {
			long beforeTime = before.getTime();
			long afterTime = after.getTime();
			return (afterTime - beforeTime) / (1000 * 60 * 60);
		}

		/**
		 * 获取两个日期之间的分钟
		 *
		 * @param before
		 * @param after
		 * @return
		 */
		public static double getDistanceOfTwoDateM(Date before, Date after) {
			long beforeTime = before.getTime();
			long afterTime = after.getTime();
			return (afterTime - beforeTime) / (1000 * 60 );
		}

		/**
		 * 获取当天周几(1至7分别对应星期一至星期天）
		 * @return
		 */
		public static int getWeekDay(){
			Date today = new Date();
			Calendar c=Calendar.getInstance();
			c.setTime(today);
			int weekday = c.get(Calendar.DAY_OF_WEEK);
			if(weekday == 1){
				weekday = weekday + 6;
			}else{
				weekday--;
			}
			return weekday;
		}


	}