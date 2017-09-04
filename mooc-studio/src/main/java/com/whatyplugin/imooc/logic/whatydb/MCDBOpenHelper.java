package com.whatyplugin.imooc.logic.whatydb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MCDBOpenHelper extends SQLiteOpenHelper {

	private static final String TAG = "MCDBOpenHelper";
	private static final int START_VERSION = 1; // 开始的数据库版本号
	private static final int Version1_1 = 4; // 现在的数据库版本号
	// 以下是通知公告的字段
	public static final String TABLE_NOTIC_NAME = "notic";
	public static final String TABLE_NOTIC_ID = "id";
	public static final String TABLE_NOTIC_TITLE = "title";
	public static final String TABLE_NOTIC_NOTE = "note";
	public static final String TABLE_NOTIC_COURSEID = "courseId";
	public static final String TABLE_NOTIC_USERID = "userId";
	public static final String TABLE_NOTIC_USERNAME = "userName";
	public static final String TABLE_NOTIC_USER_LOGINID = "userLoginId";
	public static final String TABLE_NOTIC_TOP = "isTop";
	public static final String TABLE_NOTIC_VALID = "isValid";
	public static final String TABLE_NOTIC_PUBLISH_DATE = "publishDate";
	public static final String TABLE_NOTIC_UPDATE_DATE = "updateDate";
	public static final String TABLE_NOTIC_READCOUNT = "readCount";

	// 离线学习记录
	public static final String TABLE_OFFLINE_NAME = "offline";
	public static final String TABLE_OFFLINE_ID = "id";
	public static final String TABLE_OFFLINE_COURSEID = "courseId";
	public static final String TABLE_OFFLINE_USERID = "userID";
	public static final String TABLE_OFFLINE_RECORD_TIME = "recordTime";
	public static final String TABLE_OFFLINE_STUDY_TIME = "studyTime";
	public static final String TABLE_OFFLINE_TYPE = "type";
	public static final String TABLE_OFFLINE_TOTAL_TIME = "totalTime";
	private static final String CREATE_TABLE_OFFLINE = "CREATE TABLE " + TABLE_OFFLINE_NAME + "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + TABLE_OFFLINE_ID
			+ " VARCHAR(20)," + TABLE_OFFLINE_COURSEID + " VARCHAR(20)," + TABLE_OFFLINE_USERID + " VARCHAR(20)," + TABLE_OFFLINE_RECORD_TIME + " VARCHAR(30),"
			+ TABLE_OFFLINE_STUDY_TIME + " VARCHAR(30)," + TABLE_OFFLINE_TOTAL_TIME + " VARCHAR(30)," + TABLE_OFFLINE_TYPE + " integer)";

	public MCDBOpenHelper(Context context) {
		super(context, "mooc_study.db", null, Version1_1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// 创建通知公告表
		db.execSQL("CREATE TABLE " + TABLE_NOTIC_NAME + "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + TABLE_NOTIC_ID + " VARCHAR(20)," + TABLE_NOTIC_TITLE
				+ " VARCHAR(20)," + TABLE_NOTIC_NOTE + " VARCHAR(20)," + TABLE_NOTIC_COURSEID + " VARCHAR(20)," + TABLE_NOTIC_USERID + " VARCHAR(20),"
				+ TABLE_NOTIC_USERNAME + " VARCHAR(20)," + TABLE_NOTIC_USER_LOGINID + " VARCHAR(20)," + TABLE_NOTIC_TOP + " VARCHAR(20)," + TABLE_NOTIC_VALID
				+ " VARCHAR(20)," + TABLE_NOTIC_PUBLISH_DATE + " VARCHAR(20)," + TABLE_NOTIC_READCOUNT + " VARCHAR(20)," + TABLE_NOTIC_UPDATE_DATE
				+ " VARCHAR(20))");

		// 创建离线学习数据库
		createTable(db, CREATE_TABLE_OFFLINE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "数据库版本升级了    " + oldVersion + "    " + newVersion);
		// 更新数据库使用方法
		updateTable(db, TABLE_OFFLINE_NAME, CREATE_TABLE_OFFLINE);

	}

	private void createTable(SQLiteDatabase db, String createTable) {
		db.execSQL(createTable);
	}

	private boolean notExists(SQLiteDatabase db, String table) {
		String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + table.trim() + "' ";
		Cursor cursor = db.rawQuery(sql, null);
		boolean notExists =cursor.moveToFirst() && cursor.getInt(0) <= 0;
		return notExists;
	}

	private void updateTable(SQLiteDatabase db, String tableName, String CreateTable) {
		if (notExists(db, tableName)) {
			createTable(db, CreateTable);
		}
	}

}
