package com.whatyplugin.imooc.logic.utils;


import java.util.List;

import android.content.Context;
import android.view.View;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.model.MCUserModel;
import com.whatyplugin.imooc.ui.view.ExitDailogView;
import com.whatyplugin.imooc.ui.view.ExitDailogView.IExitListener;

public class Utils {
	private static long lastClickTime;

	public static int stringToInt(String paramString) {
		try {
			return Integer.parseInt(paramString);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void showAlertDialog(Context context) {
		ExitDailogView exitView = new ExitDailogView(context,
				R.layout.normal_exit_dialog);
		exitView.setIExitListener(new IExitListener() {
			public void cancel() {
			}

			public void exit() {
			}

			public void runback() {
			}
		});
		exitView.setTitle(context.getResources().getString(
				R.string.alert_title_label));
		exitView.setContent(context.getResources().getString(
				R.string.wifi_not_use_alert));
		exitView.setSubmmitButtonName(context.getResources().getString(
				R.string.yes_label));
		exitView.showCancelButton(false);
		exitView.showDivieLine(false);
	}

	public static boolean isSameToday(String string) {
		return false;
	}

	public static boolean checkUserExist(Context mContext, String id) {
		return true;
	}

	public static List getAllUserUid(Context context) {
		return null;
	}

	public static long stringToLong(String numberStr) {
		try {
			return Long.valueOf(numberStr).longValue();
		} catch (NumberFormatException e) {
		}

		return 0;
	}

	public static float readPictureDegree(String filepath) {
		return 0;
	}

	public static boolean isFastDoubleClick() {
		boolean isFastDouble;
		long millis = System.currentTimeMillis();
		long betweenNumber = millis - Utils.lastClickTime;
		if (0 >= betweenNumber || betweenNumber >= 1000) {
			Utils.lastClickTime = millis;
			isFastDouble = false;
		} else {
			isFastDouble = true;
		}

		return isFastDouble;
	}

	public static Object getExpressionString(Context mContext, String content,
			String string, int scaleWidth) {
		return null;
	}

	public static int dip2px(Context context, float dpValue) {
		return ((int) (dpValue
				* context.getResources().getDisplayMetrics().density + 0.5f));
	}

	public static void showOrHideImage(Context mContext, View view,
			MCUserModel user) {

	}
}
