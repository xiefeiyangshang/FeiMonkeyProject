package com.whaty.imooc.utile;

import com.whaty.imooc.ui.index.GPRequestUrl;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.utils.DateUtil;
import com.whatyplugin.imooc.logic.utils.StringUtils;

public class GPStringUtile extends StringUtils {

	public static String WORKSHOP = null;
	public static int WORKSHOPNUM = 0;
	public static int ThemeListNum = 1;
	public static int KAO_HE_SHULIANG = 1; // 考核数量
	// 国培专用添加图片
	public static String addNameSpaseLearn(String noNameSpaseUrl) {
		String ss= null;
		ss = GPRequestUrl.getInstance().getUserInfo(GPContants.CODEURL);
		MCLog.e("addNameSpaseLearnhdsf", "addNameSpaseLearnhdsf getUserInfo = " + ss);

		return GPRequestUrl.getInstance().getUserInfo(GPContants.CODEURL) + "/learning" + noNameSpaseUrl.replaceAll("^http://[^/]*/", "");
	}

	// 开始时间 结束时间 不带0
	public static String StartAndEndTime(String startTime, String endTime) {
		return DateUtil.getFormatfromTimeStr(startTime, DateUtil.FORMAT_FULL, DateUtil.FORMAT_NEW_SHORT).replaceAll("\\.0", "\\.") + "-"
				+ DateUtil.getFormatfromTimeStr(endTime, DateUtil.FORMAT_FULL, DateUtil.FORMAT_NEW_SHORT).replaceAll("\\.0", "\\.");
	}

	// 开始时间结束时间带0
	public static String StartTimeAndZore(String startTime, String endTime) {
		return String.format("%s-%s", DateUtil.getFormatfromTimeStr(startTime, DateUtil.FORMAT_FULL, DateUtil.FORMAT_NEW_SHORT),
				DateUtil.getFormatfromTimeStr(endTime, DateUtil.FORMAT_FULL, DateUtil.FORMAT_NEW_SHORT));
	}

	public static String MMdd_MMdd(String start, String end) {
		return String.format("%s-%s", MMdd(start), MMdd(end));
	}

	public static String MMdd(String time) {
		return DateUtil.getFormatfromTimeStr(time, DateUtil.FORMAT_FULL, DateUtil.FORMAT_MONTH).replaceAll("\\.0", "\\.");
	}

	public static String YYYYMMdd(String time) {
		return DateUtil.getFormatfromTimeStr(time, DateUtil.FORMAT_FULL, DateUtil.FORMAT_NEW_SHORT).replaceAll("\\.0", "\\.");
	}

	public static String StartTimeAndEndTime(String startTime, String endTime) {
		return String.format("(%s~%s)", startTime, endTime);
	}

	public static String enNum2CnNum(String num) {
		String[] CnNum = new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String[] Cn10Num = new String[] { "十", "百", "千" };
		String CnStr = "";

		try {
			char[] strArray = num.toCharArray();
			int length = strArray.length;
			for (int i = 0; i < strArray.length; i++) {
				if (length > 1 && ((char2Int(strArray[i])) != 0)) {
					CnStr += (CnNum[char2Int(strArray[i])] + Cn10Num[length - 2]);
				} else {
					CnStr += CnNum[char2Int(strArray[i])];
				}
				length--;
			}
			// 处理多个零字段
			CnStr = CnStr.replaceAll("零零*", "零");
			// 处理最后一个是零
			if (CnStr.lastIndexOf("零") + 1 == CnStr.length()) {
				CnStr = CnStr.replaceAll("零$", "");
			}
			// 处理十几
			if (CnStr.contains("十") && (CnStr.length() == 3 || CnStr.length() == 2)) {
				CnStr = CnStr.replaceAll("^一", "");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return CnStr;

	}

	private static Integer char2Int(char cha) {
		return Integer.valueOf(cha + "");
	}

}
