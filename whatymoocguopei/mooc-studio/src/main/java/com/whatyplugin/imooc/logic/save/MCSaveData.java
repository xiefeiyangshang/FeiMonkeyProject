package com.whatyplugin.imooc.logic.save;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.tencent.bugly.crashreport.CrashReport;
import com.whatyplugin.base.define.MCBaseDefine.MCUserType;
import com.whatyplugin.base.storage.MCUserDefaults;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCFullUserModel;
import com.whatyplugin.imooc.logic.utils.FileUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCSaveData {
	private static MCSaveDataListener saveListener;

	public static void EditUserInfo(String key, String value, Context context) {
		MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(key, value);

	}

	public static void clearUser(Context context) {
		CrashReport.setUserId("");
		MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE);
		MCUserDefaults.clearDate(context);
	}

	public static String getChannelId(Context context) {
		return MCUserDefaults.getUserDefaults(context, "profiles").getString("chanid");
	}

	public static Map<String, String> getCourseTypeJson(Context context) {
		String key = MCUserDefaults.getUserDefaults(context, Contants.COURSE_TYPE_FILE).getString("day");
		String value = FileUtils.getStringFromFile(Contants.FILE_PATH, Contants.COURSE_TYPE_FILE_NAME);
		Map<String, String> map = new HashMap<String, String>();
		map.put(key, value);
		return map;
	}

	public static boolean getDownloadPausedStatus(String mid, Context context) {
		return MCUserDefaults.getUserDefaults(context, Contants.DOWNLOAD_PAUSED_STATUS).getBoolean(new StringBuilder(String.valueOf(mid)).toString());
	}

	public static String getDownloadSDcardPath(Context context) {
		String downloadPath = MCUserDefaults.getUserDefaults(context, "profiles").getString("sdcard_path");
		if (TextUtils.isEmpty(downloadPath))
			downloadPath = Environment.getExternalStorageDirectory().getPath();

		return downloadPath;
	}

	public static String getFriendId(Context context) {
		return MCUserDefaults.getUserDefaults(context, "friend").getString("friendid");
	}

	public static Boolean getIsFirstIn(Context context) {
		return Boolean.valueOf(MCUserDefaults.getUserDefaults(context, Contants.INAPPISFIRST_FILE).getBoolean(Contants.ISFIRST));
	}

	public static boolean getIsOptioinFocusStatus(Context context) {
		return MCUserDefaults.getUserDefaults(context, "profiles").getBoolean("option_focus");
	}

	public static boolean getIsUpdated(Context context) {
		return MCUserDefaults.getUserDefaults(context, "profiles").getBoolean("isupdated");
	}

	public static String getLastPlayTime(Context context) {
		return MCUserDefaults.getUserDefaults(context, "lastplaytime").getString("play_time", "");
	}

	public static Object getLoadingMessage(String key, Context context) {
		return MCUserDefaults.getUserDefaults(context, "loadingmessage").getString(key);
	}

	public static int getMessageFriendId(Context context) {
		return MCUserDefaults.getUserDefaults(context, "messagefriend").getInt("messagefriendid");
	}

	public static String getMyNoteTypeState(String key, Context context) {
		return MCUserDefaults.getUserDefaults(context, Contants.MYNOTE_TYPE_FILE).getString(key);
	}

	public static String getMyTalkTypeState(String key, Context context) {
		return MCUserDefaults.getUserDefaults(context, Contants.MYTALK_TYPE_FILE).getString(key);
	}

	public static boolean getNewFriendUnRead(Context context) {
		return MCUserDefaults.getUserDefaults(context, "newfriend").getBoolean("newFriendUnread", false);
	}

	public static boolean getPushSetting(Context context) {
		return MCUserDefaults.getUserDefaults(context, "push").getBoolean("ispush", true);
	}

	public static String getStartTime(Context context) {
		return MCUserDefaults.getUserDefaults(context, "push").getString("starttime", "00:00");
	}

	public static float getStatTime(Context context) {
		return MCUserDefaults.getUserDefaults(context, "stattime").getLong("stay_time");
	}

	public static String getStopTime(Context context) {
		return MCUserDefaults.getUserDefaults(context, "push").getString("stoptime", "24:00");
	}

	public static boolean getUnReadNewFriend(String fid, Context context) {
		return MCUserDefaults.getUserDefaults(context, "newfriends").containsKey(fid);
	}

	public static Object getUserInfo(String key, Context context) {
		return MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).getString(key);
	}

	public static String getVersionName(Context context) {
		return MCUserDefaults.getUserDefaults(context, "version").getString("versionname");
	}

	public static boolean isSameToday(Context context) {
		String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		boolean result = MCUserDefaults.getUserDefaults(context, "profiles").getString("day").equals(formatStr) ? true : false;
		MCUserDefaults.getUserDefaults(context, "profiles").putString("day", formatStr);
		return result;
	}

	public static void saveChannelId(String value, Context context) {
		MCUserDefaults.getUserDefaults(context, "profiles").putString("chanid", value);
	}

	public static void saveCourseTypeJSon(String json, Context context) {
		String str = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		FileUtils.saveStringToFile(Contants.FILE_PATH, Contants.COURSE_TYPE_FILE_NAME, json);
		MCUserDefaults.getUserDefaults(context, Contants.COURSE_TYPE_FILE).putString("day", str);
	}

	public static void saveDownloadPausedStatus(String mid, boolean isPaused, Context context) {
		MCUserDefaults.getUserDefaults(context, Contants.DOWNLOAD_PAUSED_STATUS).putBoolean(new StringBuilder(String.valueOf(mid)).toString(), isPaused);
	}

	public static void saveDownloadSDcardPath(String path, Context context) {
		MCUserDefaults.getUserDefaults(context, "profiles").putString("sdcard_path", path);
	}

	public static void saveIsFirstIn(Boolean key, Context context) {
		MCUserDefaults.getUserDefaults(context, Contants.INAPPISFIRST_FILE).putBoolean(Contants.ISFIRST, key.booleanValue());
	}

	public static void saveIsOptioinFocusStatus(boolean isOptioned, Context context) {
		MCUserDefaults.getUserDefaults(context, "profiles").putBoolean("option_focus", isOptioned);
	}

	public static void saveIsUpdated(Boolean isupdated, Context context) {
		MCUserDefaults.getUserDefaults(context, "profiles").putBoolean("isupdated", isupdated.booleanValue());
	}

	public static void saveLastPlayTime(String playTime, Context context) {
		MCUserDefaults.getUserDefaults(context, "lastplaytime").putString("play_time", playTime);
	}

	public static void saveLoadingMessage(String pic, String url, String end_time, Context context) {
		MCUserDefaults.getUserDefaults(context, "loadingmessage").putString("loading_pic", pic);
		MCUserDefaults.getUserDefaults(context, "loadingmessage").putString("loading_url", url);
		MCUserDefaults.getUserDefaults(context, "loadingmessage").putString("loading_date", end_time);
	}

	public static void saveMessageFriendId(String fid, Context context) {
		MCUserDefaults.getUserDefaults(context, "messagefriend").putString("messagefriendid", fid);
	}

	public static void saveMyNoteTypeState(String key, Context context) {
		MCUserDefaults.getUserDefaults(context, Contants.MYNOTE_TYPE_FILE).putString(Contants.MYNOTE_STATE, key);
	}

	public static void saveMyTalkTypeState(String key, Context context) {
		MCUserDefaults.getUserDefaults(context, Contants.MYTALK_TYPE_FILE).putString(Contants.MYTALK_STATE, key);
	}

	public static void saveNewFriendUnRead(boolean unread, Context context) {
		MCUserDefaults.getUserDefaults(context, "newfriend").putBoolean("newFriendUnread", unread);
	}

	public static void saveNewFriends(List arg5, Context context) {
	}

	public static void savePushSetting(Boolean ispush, Context context) {
		MCUserDefaults.getUserDefaults(context, "push").putBoolean("ispush", ispush.booleanValue());
	}

	public static void savePushTime(String uid, String starttime, String stoptime, Context context) {
		MCUserDefaults.getUserDefaults(context, "push").putString("starttime", starttime);
		MCUserDefaults.getUserDefaults(context, "push").putString("stoptime", stoptime);
	}

	public static void saveStatTime(long stay_time, Context context) {
		MCUserDefaults.getUserDefaults(context, "stattime").putLong("stay_time", stay_time);
	}

	public static void saveUserInfo(MCFullUserModel user, Context context) {
		CrashReport.setUserId(user.getId());
		MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.USERID, user.getId());
		MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.NICKNAME, user.getNickname());
		MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.PIC, user.getImageUrl());
		MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.EMAIL, user.getEmail());

		MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.LOGINTYPE, user.getLoginType());
		MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.UID, user.getLoginToken());

		MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.TYPE, "0");

		MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.MARK, user.getNickname());
		if (user.getSiteModel() != null) {
			MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.webDoMain, user.getSiteModel().getWebDoMain());
			MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.siteCode, user.getSiteModel().getCode());
		}
		// 记录上次登录的用户
		MCUserDefaults.getUserDefaults(context, Contants.NETWORK).putString(Contants.LAST_LOGIN_ID, user.getId());

		MCUserDefaults mcUserDefaults = MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE);
		String teacherFlag = Contants.TEACHER_FLAG;
		String type = user.getType() == MCUserType.MC_USER_TYPE_TEACHER ? "1" : "0";
		mcUserDefaults.putString(teacherFlag, type);
		if (user.getUpdateFlag() != Contants.DEFAULT_UPDATE && user.getUpdateFlag() != Contants.NO_UPDATE) {
			if (user.getUpdateFlag() == Contants.BASE_INFO_UPDATE) {
				MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.INFO_LAST_UPDATE_TIME, user.getBaseInfoLastUpdateTime());
			} else if (user.getUpdateFlag() == Contants.PWD_UPDATE) {
				MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.PWD_LAST_UPDATE_TIME, user.getPwdLastUpdateTime());
			} else if (user.getUpdateFlag() == Contants.INFO_PWD_UPDATE) {
				MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.INFO_LAST_UPDATE_TIME, user.getBaseInfoLastUpdateTime());
				MCUserDefaults.getUserDefaults(context, Contants.USERINFO_FILE).putString(Contants.PWD_LAST_UPDATE_TIME, user.getPwdLastUpdateTime());
			}
		}

		if (saveListener != null) {
			saveListener.onSaveUserData(user);
		}
	}

	public static void saveVersionName(String key, Context context) {
		MCUserDefaults.getUserDefaults(context, "version").putString("versionname", key);
	}

	public static void savefriendId(String fid, Context context) {
		MCUserDefaults.getUserDefaults(context, "friend").putString("friendid", fid);
	}

	public static void saveUserAvatar(String add, Context context) {
		MCUserDefaults.getUserDefaults(context, "userAvatar").putString("userAvatar", add);
	}

	public static String getUserAvatar(Context context) {
		return MCUserDefaults.getUserDefaults(context, "userAvatar").getString("userAvatar");
	}

	public static void saveUserSign(String add, Context context) {
		MCUserDefaults.getUserDefaults(context, "sign").putString("sign", add);
	}

	public static String getUserSign(Context context) {
		return MCUserDefaults.getUserDefaults(context, "sign").getString("sign");
	}

	public static void saveUserGender(String add, Context context) {
		MCUserDefaults.getUserDefaults(context, "gneder").putString("gneder", add);
	}

	public static String getUserGender(Context context) {
		return MCUserDefaults.getUserDefaults(context, "gneder").getString("gneder");
	}

	public static void saveUserLocalAvatar(String add, Context context) {
		MCUserDefaults.getUserDefaults(context, "userLocalAvatar").putString("userLocalAvatar", add);
	}

	public static String getUserLocalAvatar(Context context) {
		return MCUserDefaults.getUserDefaults(context, "userLocalAvatar").getString("userLocalAvatar");
	}

	public static void saveUserSiteCode(String siteCode, Context context) {
		MCUserDefaults.getUserDefaults(context, "userSiteCode").putString("userSiteCode", siteCode);
	}

	public static String getUserSiteCode(Context context) {
		return MCUserDefaults.getUserDefaults(context, "userSiteCode").getString("userSiteCode");
	}

	public static void saveUserLoginId(String loginId, Context context) {
		MCUserDefaults.getUserDefaults(context, "loginId").putString("loginId", loginId);
	}

	public static String getUserLoginId(Context context) {
		return MCUserDefaults.getUserDefaults(context, "loginId").getString("loginId");
	}

	/**
	 * 监听saveUserInfo， 做一些自己的特殊处理
	 * 
	 * @param listener
	 */
	public static void setOnSaveUserDataListener(MCSaveDataListener listener) {
		saveListener = listener;
	}

	public interface MCSaveDataListener {
		public void onSaveUserData(MCFullUserModel model);
	}

	public static void saveSiteDoMain(Context context,String domain){
		MCUserDefaults.getUserDefaults(context, "siteDomain").putString("siteDomain", domain);
	}

	public static String getSiteDoMain(Context context) {
		return MCUserDefaults.getUserDefaults(context, "siteDomain").getString("siteDomain");
	}
}
