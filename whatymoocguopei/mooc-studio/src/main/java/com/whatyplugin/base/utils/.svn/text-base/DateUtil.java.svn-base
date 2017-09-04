package com.whatyplugin.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.impl.cookie.DateUtils;

import android.text.TextUtils;

public class DateUtil {

	public static String FORMAT_SHORT = "yyyy-MM-dd";
	public static String FORMAT_NEW_SHORT = "yyyy.MM.dd";
	public static String FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
	public static String FORMAT_NEW_MINUTE = "yyyy.MM.dd HH:mm";
	public static String FORMAT_HOUR = "yyyy-MM-dd HH";
	public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
	public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
	public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
	public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";
	public static String FORMAT_NOS = "yyyy-MM-dd HH:mm";
	public static String FORMAT_EASY = "MM-dd HH:mm";
	public static String FORMAT_YEAR = "yyyy.MM.dd";
	public static String FORMAT_MONTH = "MM.dd";
	public static String FORMAT_YY_MM_DD = "yy.MM.dd";
	public static String FORMAT_HOUR_SECOND = "HH:mm:ss";
	public static String FORMAT_MM_DD_CN = "MM月dd日 HH:mm";
	public static String FORMAT_HH_MM_CN = "HH:mm";
	public static String FORMAT_yyyy_MM_DD_CN = "yyyy年MM月dd日 HH:mm";

	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * 默认日期格式
	 */
	public static String getDatePattern() {
		return FORMAT_LONG;
	}

	/**
	 * 根据预设格式返回当前日期
	 *
	 * @return
	 */
	public static String getNow() {
		return format(new Date());
	}

	/**
	 * 获取当前日期,根据用户指定格式
	 */
	public static String getNow(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * 使用预设格式格式化日期
	 *
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * 使用预设格式提取字符串日期
	 *
	 * @param strDate
	 *            日期字符串
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, getDatePattern());
	}
	/**
	 *
	 * 使用时分秒的
	 *
	 */
	public static Date YYYYMMDDparse(String strDate){
		return parse(strDate, FORMAT_SHORT);
	}



	/**
	 * 使用用户格式提取字符串日期
	 *
	 * @param strDate
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用用户格式提取字符串日期
	 *
	 * @param strDate
	 *            日期字符串
	 *
	 *            日期格式
	 * @return
	 */
	public static Date parseAll(String strDate) {
		String formats[] = new String[] { FORMAT_SHORT, FORMAT_LONG, FORMAT_FULL, FORMAT_MINUTE, FORMAT_HOUR };
		try {
			Date parseDate = DateUtils.parseDate(strDate, formats);
			return parseDate;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 得到毫秒值
	 *
	 * @param strDate
	 * @return
	 */
	public static long getTime(String strDate) {
		return parse(strDate).getTime();
	}

	/**
	 * 获取日期年份
	 *
	 * @param date
	 *            日期
	 * @return
	 */
	public static String getYear(Date date) {
		return format(date).substring(0, 4);
	}

	/**
	 * 判断两个时间是否是10分钟之内
	 *
	 * @param date
	 * @param lastDate
	 * @return
	 */
	public static boolean isInTenMinute(Date date, Date lastDate) {
		long delta = date.getTime() - lastDate.getTime();
		boolean is = false;
		if (-6000L * 10 < delta && delta < 60000L * 10) {
			is = true;
		}
		return is;
	}

	/**
	 *
	 * @param timeStr  格式化的字符串
	 * @param patternStr  格式化的字符串的表现形式
	 * @param topatternStr  需要格式化到此风格
	 * @return
	 */
	public static String getFormatfromTimeStr(String timeStr, String patternStr, String topatternStr) {
		String format = "";
		if(TextUtils.isEmpty(timeStr)){
			return format;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(patternStr);
		try {
			Date date = sdf.parse(timeStr);
			sdf = new SimpleDateFormat(topatternStr);
			format = sdf.format(date);
			return format;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return format;
	}

	/**
	 * 给定字符串时间在 当前时间之前
	 *
	 * @param beforeTime
	 * @return
	 */

	public static boolean toBeforeNow(String beforeTime) {
		Date date = new Date();
		return date.before(DateUtil.parse(beforeTime));
	}

	public static boolean YYYYMMDDBeforeNow(String beforeTime) {
		Date date = new Date();
		return date.before(YYYYMMDDparse(beforeTime));
	}



}
