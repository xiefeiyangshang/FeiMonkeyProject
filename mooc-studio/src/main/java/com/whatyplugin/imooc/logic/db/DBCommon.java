package com.whatyplugin.imooc.logic.db;

import android.net.Uri;

import com.whatyplugin.imooc.ui.mymooc.MoocApplication;

public class DBCommon {
	public static String getPreUri() {
		return "content://" + MoocApplication.getMainPackageName() + "/";
	}

	public interface AddressColumns extends BaseColumns {
		public static final String AREANO = "areano";
		public static final Uri CONTENT_URI_ADDRESS = Uri.parse(DBCommon.getPreUri() + MoocContentProvider.ADDRESS_TABLE_NAME);;
		public static final String CREATE_TABLE_ADDRESS = " CREATE TABLE IF NOT EXISTS " + MoocContentProvider.ADDRESS_TABLE_NAME + " ( " + "_id"
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + "id" + " INTEGER , " + "name" + " VARCHAR , " + "subname" + " VARCHAR , " + "pid"
				+ " INTEGER , " + "type" + " VARCHAR , " + "pinyin" + " VARCHAR , " + "areano" + " INTEGER , " + "od" + " INTEGER)";;
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String OD = "od";
		public static final String PID = "pid";
		public static final String PINYIN = "pinyin";
		public static final String SUBNAME = "subname";
		public static final String TYPE = "type";
		public static final String _ID = "_id";
		public static final String[] projects = new String[] { "_id", "id", "name", "subname", "pid", "type", "pinyin", "areano", "od" };

	}

	public interface BaseColumns {
		public static final String AUTHORITY = "cn.com.open.mooc";
		public static final String _ID = "_id";

	}

	public interface CacheColumns extends BaseColumns {
		public static final Uri CONTENT_URI_CACHE = Uri.parse(DBCommon.getPreUri() + MoocContentProvider.CACHE_TABLE_NAME);;
		public static final String CREATE_TABLE_CACHE = " CREATE TABLE IF NOT EXISTS " + MoocContentProvider.CACHE_TABLE_NAME + " ( " + "_id"
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + "id" + " INTEGER , " + "json" + " TEXT," + "method" + " VARCHAR," + "type" + " INTEGER)";
		public static final String ID = "id";
		public static final String JSON = "json";
		public static final String METHOD = "method";
		public static final String TYPE = "type";
		public static final String[] projects = new String[] { "_id", "id", "type", "json", "method" };

	}

	public interface ChapterColumns extends BaseColumns {
		public static final Uri CONTENT_URI_CHAPTER = Uri.parse(DBCommon.getPreUri() + MoocContentProvider.CHAPTER_TABLE_NAME);;
		public static final String COURSE_ID = "course_id";
		public static final String CREATE_TABLE_CHAPTER = " CREATE TABLE IF NOT EXISTS " + MoocContentProvider.CHAPTER_TABLE_NAME + " ( " + "_id"
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + "id" + " INTEGER," + "course_id" + " INTEGER," + "seqid" + " INTEGER," + "name"
				+ " VARCHAR)";;
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String SEQID = "seqid";
		public static final String[] projects = new String[] { "id", "course_id", "seqid", "name" };

	}

	public interface CourseColumns extends BaseColumns {
		public static final String CHAPTER = "chapter";
		public static final String COMPANYID = "companyid";
		public static final String ID = "id";
		public static final String ISLEARNED = "islearned";
		public static final String MEDIA = "media";
		public static final String NAME = "name";
		public static final String PIC = "pic";
		public static final String PURPOSE = "purpose";

		public static final Uri CONTENT_URI_COURSE = Uri.parse(DBCommon.getPreUri() + MoocContentProvider.COURSE_TABLE_NAME);
		public static final String[] projects = new String[] { "id", "name", "pic", "purpose", "chapter", "media", "islearned", "companyid" };
		public static final String CREATE_TABLE_COURSE = " CREATE TABLE IF NOT EXISTS " + MoocContentProvider.COURSE_TABLE_NAME + " ( " + "_id"
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + "id" + " INTEGER," + "name" + " VARCHAR , " + "pic" + " VARCHAR , " + "purpose"
				+ " VARCHAR , " + "chapter" + " INTEGER , " + "companyid" + " INTEGER , " + "media" + " INTEGER , " + "islearned" + " INTEGER)";
	}

	// sectionId改为varchar类型，同时添加了2个字段type、resourceSection
	public interface DownloadColumns extends BaseColumns {
		public static final String CHAPTERSEQ = "chapter_seq";
		public static final Uri CONTENT_URI_DOWNLOADINFO = Uri.parse(DBCommon.getPreUri() + MoocContentProvider.DOWNLOADINFO_TABLE_NAME);;
		public static final String COURSEID = "courseId";
		public static final String COURSEIMAGEURL = "courseImageUrl";
		public static final String COURSENAME = "courseName";
		public static final String CPSTEP = "cpstep";
		public static final String CREATE_TABLE_DOWNLOADINFO = " CREATE TABLE IF NOT EXISTS " + MoocContentProvider.DOWNLOADINFO_TABLE_NAME + " ( "
				+ "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT ," + "courseId" + " VARCHAR , " + "courseName" + " VARCHAR , "

				+ "courseImageUrl" + " VARCHAR , " + "chapter_seq" + " INTEGER DEFAULT 0, " + "section_seq" + " INTEGER , " + "sectionId"
				+ " VARCHAR , " + "sectionName" + " VARCHAR , " + "filename" + " VARCHAR , " + "fileSize" + " LONG , " + "downloadSize" + " LONG , "
				+ "downloadUrl" + " VARCHAR, " + "type" + " VARCHAR," + "resourceSection VARCHAR)";
		public static final String DOWLOADSIZE = "downloadSize";
		public static final String DOWNLOADURL = "downloadUrl";
		public static final String FILENAME = "filename";
		public static final String FILESIZE = "fileSize";
		public static final String MSTEP = "mstep";
		public static final String SECTIOINNAME = "sectionName";
		public static final String SECTIONID = "sectionId";
		public static final String SECTIONSEQ = "section_seq";
		public static final String TYPE = "type";
		public static final String[] projects = new String[] { "_id", "courseId", "courseName", "courseImageUrl", "chapter_seq", "section_seq",
				"sectionId", "sectionName", "filename", "fileSize", "downloadSize", "downloadUrl", "type", "resourceSection" };
	}

	// 作业数据库属性
	public interface HomeworkColumns extends BaseColumns {
		public static final Uri CONTENT_URI = Uri.parse(DBCommon.getPreUri() + MoocContentProvider.HOMEWORK_TABLE_NAME);;
		public static final String COURSEID = "courseId"; // 课程id
		public static final String HOMEWORKID = "homeworkId"; // 作业id
		public static final String NOTE = "note"; // 答案
		public static final String LASTDATE = "lastDate"; // 草稿的最新更改日期
		public static final String PICPATHS = "picPaths"; // 图片地址
		public static final String LOGINID = "loginid"; // 存储的用户名 //9版本更新上去
		public static final String CREATE_TABLE_SQL = " CREATE TABLE IF NOT EXISTS " + MoocContentProvider.HOMEWORK_TABLE_NAME + " ( " + "_id"
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + COURSEID + " VARCHAR , " + HOMEWORKID + " VARCHAR , " + LOGINID + " VARCHAR , " + NOTE
				+ " VARCHAR , " + LASTDATE + " VARCHAR , " + PICPATHS + " VARCHAR)";
		public static final String[] projects = new String[] { "_id", COURSEID, HOMEWORKID, NOTE, LASTDATE, PICPATHS };
	}

	public interface MediaDurationColumns extends BaseColumns {
		public static final Uri CONTENT_URI_MEDIADURATION = Uri.parse(DBCommon.getPreUri() + MoocContentProvider.MEDIADURATION_TABLE_NAME);
		public static final String COURSEID = "courseid";
		public static final String CREATE_TABLE_MEDIADURATION = " CREATE TABLE IF NOT EXISTS " + MoocContentProvider.MEDIADURATION_TABLE_NAME + " ( "
				+ "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT ," + "id" + " INTEGER , " + "mid" + " VARCHAR," + "courseid" + " VARCHAR," + "uid"
				+ " VARCHAR," + "date" + " VARCHAR," + "time" + " INTEGER)";;
		public static final String DATE = "date";
		public static final String ID = "id";
		public static final String MID = "mid";
		public static final String TIME = "time";
		public static final String UID = "uid";
		public static final String[] projects = new String[] { "_id", "id", "mid", "courseid", "uid", "time", "date" };;
	}

	public interface MsgColumns extends BaseColumns {
		public static final String CONTENT = "content";
		public static final Uri CONTENT_URI_MSG = Uri.parse(DBCommon.getPreUri() + MoocContentProvider.MSG_TABLE_NAME);;
		public static final String CREATE_TABLE_MSG = " CREATE TABLE IF NOT EXISTS " + MoocContentProvider.MSG_TABLE_NAME + " ( " + "_id"
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + "id" + " INTEGER," + "senderuid" + " VARCHAR," + "receiveruid" + " VARCHAR," + "type"
				+ " INTEGER," + "islocal" + " INTEGER," + "content" + " VARCHAR," + "sendtime" + " VARCHAR ," + "statue" + " INTEGER," + "sendstatus"
				+ " INTEGER)";;
		public static final String ID = "id";
		public static final String ISLOCAL = "islocal";
		public static final String RECEIVERUID = "receiveruid";
		public static final String SENDERUID = "senderuid";
		public static final String SENDSTATUS = "sendstatus";
		public static final String SENDTIME = "sendtime";
		public static final String STATUE = "statue";
		public static final String TYPE = "type";
		public static final String[] projects = new String[] { "senderuid", "id", "receiveruid", "content", "type", "sendtime", "statue", "islocal",
				"sendstatus" };;
	}

	public interface OfflineReportColums extends BaseColumns {
		public static final String CHAPTERID = "chapterid";
		public static final Uri CONTENT_URI_OFFLINEREPORT = Uri.parse(DBCommon.getPreUri() + MoocContentProvider.OFFLINEREPORT_TABLE_NAME);;
		public static final String COURSEID = "courseid";
		public static final String CREATE_TABLE_OFFLINEREPORT = "CREATE TABLE IF NOT EXISTS " + MoocContentProvider.OFFLINEREPORT_TABLE_NAME + " ( "
				+ "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT , " + "uid" + " INTEGER , " + "courseid" + " INTEGER , " + "chapterid" + " INTEGER , "
				+ "sectionid" + " INTEGER , " + "value1" + " VARCHAR , " + "value2" + " VARCHAR , " + "value3" + " VARCHAR , " + "inserttime"
				+ " LONG , " + "reporttype" + " INTEGER )";;
		public static final String INSERTTIME = "inserttime";
		public static final String REPORTTYPE = "reporttype";
		public static final String SECTIONID = "sectionid";
		public static final String UID = "uid";
		public static final String VALUE1 = "value1";
		public static final String VALUE2 = "value2";
		public static final String VALUE3 = "value3";
		public static final String _ID = "_id";
		public static final String[] projects = new String[] { "_id", "uid", "courseid", "chapterid", "sectionid", "value1", "value2", "value3",
				"inserttime", "reporttype" };

	}

	public interface SectionColumns extends BaseColumns {
		public static final String CHAPTER_ID = "chapter_id";
		public static final Uri CONTENT_URI_SECTION = Uri.parse(DBCommon.getPreUri() + MoocContentProvider.SECTION_TABLE_NAME);;
		public static final String CREATE_TABLE_SECTION = " CREATE TABLE IF NOT EXISTS " + MoocContentProvider.SECTION_TABLE_NAME + " ( " + "_id"
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + "mid" + " INTEGER," + "chapter_id" + " INTEGER," + "seqid" + " INTEGER," + "name"
				+ " VARCHAR , " + "pic" + " VARCHAR , " + "url" + " VARCHAR , " + "statue" + " INTEGER)";;
		public static final String ID = "mid";
		public static final String NAME = "name";
		public static final String PIC = "pic";
		public static final String SEQID = "seqid";
		public static final String STATUE = "statue";
		public static final String URL = "url";
		public static final String[] projects = new String[] { "mid", "chapter_id", "seqid", "pic", "statue", "name", "url" };;

	}

	public interface UserColumns extends BaseColumns {
		public static final String AGE = "age";
		public static final Uri CONTENT_URI_USER = Uri.parse(DBCommon.getPreUri() + MoocContentProvider.USER_TABLE_NAME);;
		public static final String CREATE_TABLE_USER = " CREATE TABLE IF NOT EXISTS " + MoocContentProvider.USER_TABLE_NAME + " ( " + "_id"
				+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + "uid" + " INTEGER," + "age" + " INTEGER," + "nickname" + " VARCHAR," + "teacher"
				+ " VARCHAR , " + "pic" + " VARCHAR , " + "sex" + " INTEGER)";;
		public static final String NICKNAE = "nickname";
		public static final String PIC = "pic";
		public static final String SEX = "sex";
		public static final String TEACHER = "teacher";
		public static final String UID = "uid";
		public static final String[] projects = new String[] { "uid", "nickname", "sex", "pic", "teacher" };
	}

	public DBCommon() {
		super();
	}
}
