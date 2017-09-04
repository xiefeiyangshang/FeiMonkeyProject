package com.whatyplugin.base.weblog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.Const;

/**
 * 提交到web端把使用info作为key传递json内容。批量为info=&info=&info=
 * 
 * @author banzhenyu
 */
public class WhatyLog {

	private static JSONArray arr;
	private static JSONObject obj;
	private static JSONObject event;
	private static SimpleDateFormat format;
	private static WhatyLog manager = new WhatyLog();
	public static final String fileName = "mooc.txt";
	private static final String TAG = "WhatyLog";
	
	public static boolean flag;   //定位成功后的标记
	public static double latitude = 0.0;
	public static double longitude = 0.0;
	
	private WhatyLog() {
	}

	public static WhatyLog getInstance() {
		return manager;
	}

	public static void StartAnalyze(Context context) {
		manager.startLog(context);
	}
	/**
	 * 开始记录日志时候调用一次
	 * @param context
	 */
	private void startLog(Context context) {
		InfoTerminal.getLocatin(context);
	}

	public static void LoadAnalyze(Context context) {
		LoadAnalyze(context, null);
	}

	public static void EndAnalyze(Context context) {
		EndAnalyze(context, null);
	}

	public static void LoadAnalyze(Context context, String str) {
		serializeStart(context, str);
	}

	public static void EndAnalyze(Context context, String str) {
		serializeEnd(context, str);
	}

	@SuppressLint("SimpleDateFormat")
	private static void serializeStart(Context context, String str) {
		MCLog.d(TAG, "开始统计" + ( str == null ? context.getClass().getSimpleName() : str));
		format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			BeanApp appInfo = InfoApp.getInfo(context);
			BeanScreen screenInfo = InfoScreen.getScreen(context);
			BeanTerminal terminalInfo = InfoTerminal.getTerminal(context);

			arr = new JSONArray();
			obj = new JSONObject();

			/************************** User ******************************/
			JSONObject user = new JSONObject();

			String usrName = MCSaveData.getUserInfo(Contants.USERID, context).toString();
			user.put("loginId", usrName);
			user.put("ip", InfoUser.getIpAddress(context));
			user.put("uid", UUID.randomUUID().toString());
			user.put("siteCode", Const.SITECODE);
			user.put("domain", Const.SITE_LOCAL_URL.replace("http://", ""));
			user.put("language", Locale.getDefault().getLanguage());
			user.put("charset", "UTF-8");
			obj.put("user", user);

			event = new JSONObject();
			event.put("pageTitle", str == null ? context.getClass().getSimpleName() : str);
			event.put("url", Const.SITE_LOCAL_URL);
			event.put("eventid", System.currentTimeMillis());
			event.put("eventName", "LoadView");
			event.put("eventType", "LoadView");
			event.put("eventCode", "fff");
			event.put("eventDesc", "LoadView");
			event.put("referrer", System.currentTimeMillis());
			event.put("previousCode", System.currentTimeMillis());
			event.put("loadTime", System.currentTimeMillis());
			event.put("beginTime", format.format(new Date()));
			event.put("duration", null);
			event.put("errorCode", 1);
			event.put("errorMsg", "no error");

			/************************** Detail ******************************/
			JSONObject detail = new JSONObject();
			detail.put("browser", "10");

			JSONObject screen = new JSONObject();
			screen.put("height", screenInfo.height);
			screen.put("width", screenInfo.width);
			screen.put("colorDepth", screenInfo.colorDepth);
			detail.put("screen", screen);

			JSONObject terminal = new JSONObject();
			terminal.put("os", terminalInfo.os);
			terminal.put("deviceID", terminalInfo.deviceID);
			terminal.put("deviceName", terminalInfo.deviceName);
			terminal.put("deviceMac", terminalInfo.deviceMac);
			terminal.put("telecommunicasOperator", terminalInfo.telecommunicasOperator);
			terminal.put("position", latitude + ","+ longitude);
			detail.put("terminal", terminal);
			JSONObject app = new JSONObject();
			app.put("code", appInfo.code);
			app.put("name", appInfo.name);
			app.put("version ", appInfo.version);
			app.put("description", appInfo.description);
			detail.put("app", app);
			detail.put("platform", "terminal");
			obj.put("detail", detail);
			manager.mikDirFile(context);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private static void serializeEnd(Context context, String str) {
		MCLog.d(TAG, "结束统计" + ( str == null ? context.getClass().getSimpleName() : str));
		try {
			String endTime = format.format(new Date());
			long time1 = format.parse((String) event.get("beginTime")).getTime();
			long time2 = format.parse(endTime).getTime();
			long duration = time2 - time1;

			event.put("duration", duration + "ms");
			event.put("endTime", endTime);
			obj.put("event", event);
			arr.put(obj);

			FileInputStream inputStream = context.openFileInput(fileName);
			byte[] bytes = new byte[1024];
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			while (inputStream.read(bytes) != -1) {
				arrayOutputStream.write(bytes, 0, bytes.length);
			}
			inputStream.close();
			arrayOutputStream.close();
			String content = new String(arrayOutputStream.toByteArray());
			String[] split = content.split("@@");
			if (split.length < 100) {
				FileOutputStream out = context.openFileOutput(fileName, Context.MODE_APPEND);
				out.write(("@@" + obj.toString()).getBytes());
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void mikDirFile(Context context) {
		String path = context.getFilesDir().getAbsolutePath();
		File dir = new File(path + "/" + fileName);
		if (!dir.exists()) {
			try {
				dir.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static String is2Str(InputStream is) {
		String all_content = null;
		try {
			all_content = new String();
			ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
			byte[] str_b = new byte[1024];
			int i = -1;
			while ((i = is.read(str_b)) > 0) {
				outputstream.write(str_b, 0, i);
			}
			return all_content = outputstream.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all_content;
	}

}
