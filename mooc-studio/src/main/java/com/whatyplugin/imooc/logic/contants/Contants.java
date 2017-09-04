package com.whatyplugin.imooc.logic.contants;

import android.os.Environment;

import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;

public class Contants {
	public static final String SEARCH_KEY_MY_NOTE_COURSE = "SEARCH_KEY_MY_NOTE_COURSE";
	public static final String SEARCH_KEY_MY_NOTE = "SEARCH_KEY_MY_NOTE";
	public static final String SEARCH_KEY_ALL_NOTE = "SEARCH_KEY_ALL_NOTE";

	public static final String SEARCH_KEY_MY_QUESTION_COURSE = "SEARCH_KEY_MY_QUESTION_COURSE";
	public static final String SEARCH_KEY_MY_QUESTION = "SEARCH_KEY_MY_QUESTION";
	public static final String SEARCH_KEY_ALL_QUESTION = "SEARCH_KEY_ALL_QUESTION";

	public static final String SEARCH_KEY_ALL_COURSE = "SEARCH_KEY_ALL_COURSE";
	public static String SPACENOP = "     ";
	public static final String SFP_SUFFIX = "SFP";
	public static String SITE_CODE = "SITE_CODE";
	public static String webDoMain = "webDoMain";
	public static String siteCode = "siteCode";
	public static String ABOUT_ME = null;
	public static String ADDRESS = null;
	public static String LOGINTYPE = null;
	public static String APK_NAME = null;
	public static String APK_PATH = null;
	public static String BASE_FOLDER_PATH = null;
	public static int BASE_INFO_UPDATE = 0;
	public static String BASE_PATH = null;
	public static String BASE_VIDEO_PATH = null;
	public static String CHAT_CONTENT = null;
	public static int CHAT_DETAIL_IMAGE_MAX = 0;
	public static int CHAT_EXPRESSION_WIDTH = 0;
	public static int CHAT_LISTVIEW_EXPRESSION_WIDTH = 0;
	public static int CHAT_SENDED_IMAGE_MAX = 0;
	public static String CHAT_SEND_FAILED_MSG = null;
	public static String COMPANYID = null;
	public static int COURSE_IMAGE_HEIGHT = 0;
	public static int COURSE_IMAGE_WIDTH = 0;
	public static String COURSE_TYPE_FILE = null;
	public static String COURSE_TYPE_FILE_NAME = null;
	public static int COURSE_TYPE_IMAGE_WIDTH = 0;
	public static int CROP_VIEW_WIDTH = 0;
	public static String DEFAULT_UID = "";
	public static int DEFAULT_UPDATE = 0;
	public static String DOWNLOAD_PAUSED_STATUS = null;
	public static String EMAIL = null;
	public static String EXIST = null;
	public static String LAST_LOGIN_ID = null;
	public static String FILE_PATH = null;
	public static String FIRST_ENTER = null;
	public static String HISTORY = null;
	public static String IMAGE_PATH = null;
	public static String INAPPISFIRST_FILE = null;
	public static String INFO_LAST_UPDATE_TIME = null;
	public static int INFO_PWD_UPDATE = 0;
	public static String ISFIRST = null;
	public static String JOBS = null;
	public static String LOGIN_CHANNEL = null;
	public static String MARK = null;
	public static String MULTI_URL = null;
	public static String MYNOTE_STATE = null;
	public static String MYNOTE_TYPE_FILE = null;
	public static String MYTALK_STATE = null;
	public static String MYTALK_TYPE_FILE = null;
	public static String NETWORK = null;
	public static String NETWORK_CHANGED_ACTION = null;
	public static String NETWORK_SETTING = null;
	public static String NICKNAME = null;
	public static int NOTE_HEADIMAGE_HEIGHT = 0;
	public static int NOTE_HEADIMAGE_WIDTH = 0;
	public static int NO_UPDATE = 0;
	public static int PERSONAL_COURSE_IMAGE_HEIGHT = 0;
	public static int PERSONAL_COURSE_IMAGE_WIDTH = 0;
	public static String PIC = null;
	public static String PWD_LAST_UPDATE_TIME = null;
	public static int PWD_UPDATE = 0;
	public static String QUERY_MESSAGE_FINISH_ACTION = null;
	public static String RELOAD_CHANGED_ACTION = null;
	public static String SDCARD_STATUS_CHANGED = null;
	public static String SEX = null;
	public static int SHARE_EDIT_HEIGHT = 0;
	public static int SHARE_HEIGHT = 0;
	public static String SINGLE_URL = null;
	public static String TEACHER_FLAG = null;
	public static String TYPE = null;
	public static String UID = null;
	public static String USERID = null;
	

	
	
	public static String UNREAD_MSG = null;
	public static String UPDATE_NEWFRIEND_UNREAD_ACTION = null;
	public static String UPDATE_UNREAD_NUM_ACTION = null;
	public static String USERINFO_FILE = null;
	public static String USER_LOGIN_ACTION = null;
	public static String USER_UPDATE_HANDIMG_ACTION = null;
	public static String USER_LOGOUT_ACTION = null;
	public static int USER_PIC_HEIGHT_COURSEINFO = 0;
	public static int USER_PIC_HEIGHT_PERSONAL = 0;
	public static int USER_PIC_HEIGHT_SLIDING = 0;
	public static int USER_PIC_WIDTH_COURSEINFO = 0;
	public static int USER_PIC_WIDTH_PERSONAL = 0;
	public static int USER_PIC_WIDTH_SLIDING = 0;
	public static final String VERSIONNAME = "versionname";
	public static final String VERSION_FILE = "version";
	public static final String VERSION_NAME = "versionname";
	public static final String LEARLOGINTYPE = "learloginType";
	public static String VIDEO_PATH;

	static {
		Contants.BASE_FOLDER_PATH = "/Android/data/" + MoocApplication.getMainPackageName() + "/Mooc";
		Contants.BASE_PATH = Environment.getExternalStorageDirectory() + Contants.BASE_FOLDER_PATH;
		Contants.BASE_VIDEO_PATH = Environment.getExternalStorageDirectory() + Contants.BASE_FOLDER_PATH;
		Contants.VIDEO_PATH = Contants.BASE_VIDEO_PATH + "/video";
		Contants.IMAGE_PATH = Contants.BASE_PATH + "/image";
		Contants.APK_PATH = Contants.BASE_PATH + "/apk/";
		Contants.APK_NAME = "MOOC.apk";
		Contants.FILE_PATH = Contants.BASE_PATH + "/file";
		Contants.COURSE_TYPE_FILE_NAME = "course_type.json";
		Contants.HISTORY = "history";
		Contants.USERINFO_FILE = "userinfo";
		Contants.UID = "uid";
		Contants.USERID = "userid";
		Contants.NICKNAME = "nickname";
		Contants.PIC = "pic";
		Contants.TYPE = "type";
		Contants.EMAIL = "email";
		Contants.LAST_LOGIN_ID = "last_login_id";
		Contants.SEX = "sex";
		Contants.JOBS = "job";
		Contants.MARK = "mark";
		Contants.ABOUT_ME = "about_me";
		Contants.LOGIN_CHANNEL = "loginchannel";
		Contants.EXIST = "exist";
		Contants.ADDRESS = "address";
		Contants.LOGINTYPE = "logintype";
		Contants.COMPANYID = "companyid";
		Contants.INFO_LAST_UPDATE_TIME = "i_lastime";
		Contants.PWD_LAST_UPDATE_TIME = "p_lastime";
		Contants.TEACHER_FLAG = "is_v";
		Contants.MYTALK_TYPE_FILE = "mytalk_type";
		Contants.MYTALK_STATE = "mytalkstate";
		Contants.MYNOTE_TYPE_FILE = "mynote_type";
		Contants.MYNOTE_STATE = "mynotestate";
		Contants.INAPPISFIRST_FILE = "isfirst_inapp";
		Contants.ISFIRST = "isfirstin";
		Contants.COURSE_TYPE_FILE = "course_type";
		Contants.DOWNLOAD_PAUSED_STATUS = "paused_status";
		Contants.USER_PIC_WIDTH_SLIDING = 250;
		Contants.USER_PIC_HEIGHT_SLIDING = 250;
		Contants.USER_PIC_WIDTH_COURSEINFO = 150;
		Contants.USER_PIC_HEIGHT_COURSEINFO = 150;
		Contants.USER_PIC_WIDTH_PERSONAL = 370;
		Contants.USER_PIC_HEIGHT_PERSONAL = 370;
		Contants.COURSE_IMAGE_WIDTH = 533;
		Contants.COURSE_IMAGE_HEIGHT = 300;
		Contants.PERSONAL_COURSE_IMAGE_WIDTH = 358;
		Contants.PERSONAL_COURSE_IMAGE_HEIGHT = 201;
		Contants.CHAT_DETAIL_IMAGE_MAX = 375;
		Contants.CHAT_SENDED_IMAGE_MAX = 960;
		Contants.DEFAULT_UID = "";
		Contants.SHARE_HEIGHT = 700;
		Contants.SHARE_EDIT_HEIGHT = 710;
		Contants.NOTE_HEADIMAGE_WIDTH = 150;
		Contants.NOTE_HEADIMAGE_HEIGHT = 150;
		Contants.COURSE_TYPE_IMAGE_WIDTH = 98;
		Contants.CROP_VIEW_WIDTH = 840;
		Contants.CHAT_EXPRESSION_WIDTH = 100;
		Contants.CHAT_LISTVIEW_EXPRESSION_WIDTH = 70;
		Contants.NETWORK_CHANGED_ACTION = "cn.com.whatyplugin.mooc.NETWORK_CHANGED";
		Contants.RELOAD_CHANGED_ACTION = "cn.com.whatyplugin.mooc.RELOAD_CHANGED";
		Contants.USER_LOGIN_ACTION = "cn.com.whatyplugin.mooc.USER_LOGIN";
		Contants.USER_LOGOUT_ACTION = "cn.com.whatyplugin.mooc.USER_LOGOUT";
		Contants.USER_UPDATE_HANDIMG_ACTION = "cn.com.whatyplugin.mooc.USER_UPDATE_HANDIMG";
		Contants.SDCARD_STATUS_CHANGED = "cn.com.whatyplugin.mooc.SDCARD_STATUS_CHANGED";
		Contants.UPDATE_UNREAD_NUM_ACTION = "cn.com.whatyplugin.mooc.UPDATE_UNREAD_NUM";
		Contants.UPDATE_NEWFRIEND_UNREAD_ACTION = "cn.com.whatyplugin.mooc.UPDATE_NEWFRIEND_UNREAD";
		Contants.QUERY_MESSAGE_FINISH_ACTION = "cn.com.whatyplugin.mooc.QUERY_MESSAGE_FINISH";
		Contants.SINGLE_URL = "single_url";
		Contants.MULTI_URL = "multi_url";
		Contants.DEFAULT_UPDATE = -1;
		Contants.NO_UPDATE = 0;
		Contants.BASE_INFO_UPDATE = 1;
		Contants.PWD_UPDATE = 2;
		Contants.INFO_PWD_UPDATE = 3;
		Contants.UNREAD_MSG = "unread_msg";
		Contants.CHAT_CONTENT = "chat_content";
		Contants.FIRST_ENTER = "first_enter";
		Contants.CHAT_SEND_FAILED_MSG = "chat_send_failed_msg";
		Contants.NETWORK = "network";
		Contants.NETWORK_SETTING = "network_setting";
		
		
	}

	public Contants() {
		super();
	}

	public static void updateVideoPath() {
		String externalPath = MCSaveData.getDownloadSDcardPath(MoocApplication.getInstance());
		Contants.BASE_VIDEO_PATH = externalPath + Contants.BASE_FOLDER_PATH;
		Contants.VIDEO_PATH = Contants.BASE_VIDEO_PATH + "/video";
		System.out.println("BASE_VIDEO_PATH更新为： " + Contants.VIDEO_PATH);
	}
}
