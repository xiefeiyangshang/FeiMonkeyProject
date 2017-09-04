package com.whatyplugin.imooc.logic.utils;

import java.util.Date;

import com.whatyplugin.base.utils.DateUtil;

public class TimeFormatUtils {
	
	/**
	 * long 转成成 00:00:00格式的字符串 ,例如 30*60*1000(30分钟) 00:30:00
	 * @param mss
	 * @return
	 */
	public static String formatTime(long mss) {
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		String hour, min, sec;
		hour = hours >= 10 ? "" + hours : "0" + hours;
		min = minutes >= 10 ? "" + minutes : "0" + minutes;
		sec = seconds >= 10 ? "" + seconds : "0" + seconds;
		return hour + ":" + min + ":" + sec;
	}

	/**
	 * 两个纳米时间差转化成 获取当前秒数
	 * 
	 */
	public static String longToStringTime(long startTime,long stopTime){
	     long diff =stopTime-startTime;
	     return diff/1000+"";
	}
	
	
	
}
