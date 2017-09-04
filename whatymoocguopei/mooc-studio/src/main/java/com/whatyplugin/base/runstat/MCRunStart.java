package com.whatyplugin.base.runstat;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.base.queue.MCOperationQueue;
import com.whatyplugin.base.queue.MCRequestOperation;
import com.whatyplugin.base.storage.MCUserDefaults;

public class MCRunStart {
	public enum MCAPPType {

		MC_APP_TYPE_IMOOC("MC_APP_TYPE_IMOOC", 0, 1);
		private int _value;

		private MCAPPType(String arg1, int arg2, int value) {
			this._value = value;
		}

		public int value() {
			return this._value;
		}

	}

	public enum MCPlatType {
		MC_PLAT_TYPE_IPHONE("MC_PLAT_TYPE_IPHONE", 0, 1), MC_PLAT_TYPE_ANDROID(
				"MC_PLAT_TYPE_ANDROID", 1, 2), MC_PLAT_TYPE_IPAD(
				"MC_PLAT_TYPE_IPAD", 2, 3), MC_PLAT_TYPE_WINPHONE(
				"MC_PLAT_TYPE_WINPHONE", 3, 3);
		private int _value;

		private MCPlatType(String arg1, int arg2, int value) {
			this._value = value;
		}

		public int value() {
			return this._value;
		}
	}

	public enum MCRunType {
		MC_Run_TYPE_RUN("MC_Run_TYPE_RUN", 0, 1);
		private int _value;

		private MCRunType(String arg1, int arg2, int value) {
			this._value = value;
		}

		public int value() {
			return this._value;
		}

	}

	public enum MCServiceType {

		MC_SERVICE_TYPE_APP("MC_SERVICE_TYPE_APP", 0, 1), MC_SERVICE_TYPE_GAME(
				"MC_SERVICE_TYPE_GAME", 1, 2);
		private int _value;

		private MCServiceType(String arg1, int arg2, int value) {
			this._value = value;
		}

		public int value() {
			return this._value;
		}

	}

	private static String TAG;

	static {
		MCRunStart.TAG = MCRunStart.class.getSimpleName();
	}

	public MCRunStart() {
		super();
	}

	static String access$0() {
		return MCRunStart.TAG;
	}

	public static void apprun(MCServiceType service, String channelId,
			MCPlatType plat, MCAPPType app, int uid, MCRunType run,
			String screensize, Context context) {
		PackageInfo v4 = null;
		if (!MCRunStart.isSameToday(context)) {
			PackageManager v5 = context.getPackageManager();
			try {
				v4 = v5.getPackageInfo(context.getPackageName(), 0);
			} catch (Exception v1) {
				v1.printStackTrace();
			}

			MCBaseRequest v6 = new MCBaseRequest();
			v6.requestUrl = "apprun";
			HashMap v3 = new HashMap();
			((Map) v3).put("service_id",
					new StringBuilder(String.valueOf(service.value()))
							.toString());
			((Map) v3).put("chan_id",
					new StringBuilder(String.valueOf(channelId)).toString());
			((Map) v3).put("plat_id",
					new StringBuilder(String.valueOf(plat.value())).toString());
			((Map) v3).put("app_id",
					new StringBuilder(String.valueOf(app.value())).toString());
			String v8 = "v_id";
			String v7 = v4 == null ? "2.0.0" : v4.versionName;
			((Map) v3).put(v8, v7);
			((Map) v3).put("d_code", MCRunStart.getDeviceId(context));
			((Map) v3).put("uid",
					new StringBuilder(String.valueOf(uid)).toString());
			((Map) v3).put("brand", String.valueOf(Build.BRAND) + " "
					+ Build.MODEL);
			((Map) v3).put("os", "Android");
			((Map) v3).put("osv", Build.VERSION.RELEASE);
			((Map) v3).put("screen_size", screensize);
			((Map) v3).put("type",
					new StringBuilder(String.valueOf(run.value())).toString());
			v6.requestParams = ((Map) v3);
			v6.listener = new MCNetworkBackListener() {
				public void onNetworkBackListener(MCCommonResult result,
						String responeData) {
					MCLog.e(MCRunStart.TAG, "apprun responeData:" + responeData);
				}
			};
			MCOperationQueue.getInstance().addTask(
					MCRequestOperation.operationWithData(v6, context));
		}
	}

	public static String getDeviceId(Context context) {
		return "";
	}

	public static void installrun(MCServiceType service, String channelId,
			MCPlatType plat, MCAPPType app, int uid, MCRunType run,
			String screensize, Context context) {
		PackageInfo v4 = null;
		PackageManager v5 = context.getPackageManager();
		try {
			v4 = v5.getPackageInfo(context.getPackageName(), 0);
		} catch (Exception v1) {
			v1.printStackTrace();
		}

		MCBaseRequest v6 = new MCBaseRequest();
		v6.requestUrl = "appinstall";
		HashMap v3 = new HashMap();
		((Map) v3).put("service_id",
				new StringBuilder(String.valueOf(service.value())).toString());
		((Map) v3).put("chan_id",
				new StringBuilder(String.valueOf(channelId)).toString());
		((Map) v3).put("plat_id",
				new StringBuilder(String.valueOf(plat.value())).toString());
		((Map) v3).put("app_id",
				new StringBuilder(String.valueOf(app.value())).toString());
		String v8 = "v_id";
		String v7 = v4 == null ? "2.0.0" : v4.versionName;
		((Map) v3).put(v8, v7);
		((Map) v3).put("d_code", MCRunStart.getDeviceId(context));
		((Map) v3)
				.put("brand", String.valueOf(Build.BRAND) + " " + Build.MODEL);
		((Map) v3).put("os", "Android");
		((Map) v3)
				.put("uid", new StringBuilder(String.valueOf(uid)).toString());
		((Map) v3).put("osv", Build.VERSION.RELEASE);
		((Map) v3).put("screen_size", screensize);
		((Map) v3).put("type",
				new StringBuilder(String.valueOf(run.value())).toString());
		v6.requestParams = ((Map) v3);
		v6.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result,
					String responeData) {
				MCLog.e(TAG, "appinstall responeData:" + responeData);
			}
		};
		MCOperationQueue.getInstance().addTask(
				MCRequestOperation.operationWithData(v6, context));
	}

	private static boolean isSameToday(Context context) {
		String v3 = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System
				.currentTimeMillis()));
		boolean v2 = MCUserDefaults.getUserDefaults(context, "profiles")
				.getString("runapp_day").equals(v3) ? true : false;
		MCUserDefaults.getUserDefaults(context, "profiles").putString(
				"runapp_day", v3);
		return v2;
	}
}
