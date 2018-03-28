package com.poreader.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author Yang
 *
 */
public class DateUtils {

	/**
	 * 空白日期，定义为1900-1-1
	 */
	public final static Date Date_EMPTY = new Date() {

		private static final long serialVersionUID = 8729434191329587401L;

		{
			Calendar c = Calendar.getInstance();
			c.clear();
			c.set(1900, 1, 1);
			super.setTime(c.getTime().getTime());
		}
	};

	/**
	 * 字符串转Date类型
	 * @param str
	 * @param defaultVal
	 * @param pattern
	 * @return
	 */
	public static Date formString(String str, Date defaultVal, String pattern) {
		Date result = defaultVal;
		if (pattern == null || pattern.isEmpty())
			pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			result = simpleDateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 按指定日期类型转换成字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toString(Date date, String pattern) {
		if (pattern == null || pattern.isEmpty())
			pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	public static Calendar toCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	public static Date toDate(Calendar calendar) {
		return calendar.getTime();
	}

	public static Long getTotalMilliseconds(Date min, Date max) {
		long lMin = min.getTime();
		long lMax = max.getTime();
		return lMax - lMin;
	}

	public static long getTotalSeconds(Date min, Date max) {
		long milliseconds = getTotalMilliseconds(min, max);
		return milliseconds / 1000;
	}

	public static long getMinutes(Date min, Date max) {
		long milliseconds = getTotalMilliseconds(min, max);
		return milliseconds / (60 * 1000);
	}

	public static long getHours(Date min, Date max) {
		long milliseconds = getTotalMilliseconds(min, max);
		return milliseconds / (60 * 60 * 1000);
	}

	public static long getDays(Date min, Date max) {
		long milliseconds = getTotalMilliseconds(min, max);
		return milliseconds / (24 * 60 * 60 * 1000);
	}
}
