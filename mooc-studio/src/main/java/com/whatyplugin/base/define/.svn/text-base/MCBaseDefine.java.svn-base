package com.whatyplugin.base.define;


public class MCBaseDefine {
	public enum MCAnswerRule {
		MC_ANSWER_RULE_ALLOW_SKIP("MC_ANSWER_RULE_ALLOW_SKIP", 0), MC_ANSWER_RULE_MUST_ANSWER_ONCE(
				"MC_ANSWER_RULE_MUST_ANSWER_ONCE", 1), MC_ANSWER_RULE_MUST_ANSWER_ALL(
				"MC_ANSWER_RULE_MUST_ANSWER_ALL", 2);

		private MCAnswerRule(String arg1, int arg2) {
		}

	}

	public enum MCAnswerStatus {
	}

	public enum MCCourseType {

	}

	public enum MCFromType {

	}

	public enum MCNetworkRequestType {

		MC_NETWORK_REQUEST_GET("MC_NETWORK_REQUEST_GET", 0), MC_NETWORK_REQUEST_POST(
				"MC_NETWORK_REQUEST_POST", 1);

		private MCNetworkRequestType(String arg1, int arg2) {
		}

	}

	public enum MCUserType {
		MC_USER_TYPE_NORMAL("MC_USER_TYPE_NORMAL", 0), MC_USER_TYPE_TEACHER(
				"MC_USER_TYPE_TEACHER", 1);
		private MCUserType(String arg1, int arg2) {
		}
	}

	//下载类型
	public enum MCDownloadNodeType {
		MC_VIDEO_TYPE("MC_VIDEO_TYPE", 0),
		MC_RESOURCE_TYPE("MC_RESOURCE_TYPE", 1),
		MC_SFP_TYPE("MC_SFP_TYPE", 2),
		MC_SFP_AND_VIDEO_TYPE("MC_SFP_AND_VIDEO_TYPE", 3),
		MC_OTHER_TYPE("MC_OTHER_TYPE", 4),
		MC_SCORM_TYPE("MC_SCORM_TYPE", 5);//scorm类型课件
		private int value;
		private String info;
		private MCDownloadNodeType(String info, int value) {
			this.info = info;
			this.value = value;
		}
		public int value(){
			return this.value;
		}
		public String info(){
			return this.info;
		}

		public static MCDownloadNodeType initWithString(String type) {
			if("0".equals(type)){
				return MC_VIDEO_TYPE;
			}else if("1".equals(type)){
				return MC_RESOURCE_TYPE;
			}else if("2".equals(type)){
				return MC_SFP_TYPE;
			}else if("3".equals(type)){
				return MC_SFP_AND_VIDEO_TYPE;
			}else if ("5".equals(type)) {
				return MC_SCORM_TYPE;
			} else{
				return MC_OTHER_TYPE;
			}
		}
	}

	public enum MCGender {
		MC_GENDER_UNKNOWN("MC_GENDER_UNKNOWN", 0), MC_GENDER_MALE(
				"MC_GENDER_MALE", 1), MC_GENDER_FEMALE("MC_GENDER_FEMALE", 2);

		private MCGender(String arg1, int arg2) {
		}

	}

	public enum MCDownloadStatus {
		MC_DOWNLOADED("MC_DOWNLOADED", 0),
		MC_DOWNLOADING("MC_DOWNLOADING", 1),
		MC_DOWNLOADPRE("MC_DOWNLOADPRE", 2),
		MC_DOWNLOADNONE("MC_DOWNLOADNONE", 4);

		private MCDownloadStatus(String arg1, int arg2) {
		}

	}

	public enum MCQuestionType {
		MC_QUESTION_TYPE_SINGLE("MC_QUESTION_TYPE_SINGLE", 0), MC_QUESTION_TYPE_MULTI(
				"MC_QUESTION_TYPE_MULTI", 1);

		private MCQuestionType(String arg1, int arg2) {
		}

	}

	public enum MCPushType {

		MC_PUSH_TYPE_SYSTEM("MC_PUSH_TYPE_SYSTEM", 0, 0), MC_PUSH_TYPE_FRIENDMSG(
				"MC_PUSH_TYPE_FRIENDMSG", 1, 1), MC_PUSH_TYPE_NEWUSERREGIST(
				"MC_PUSH_TYPE_NEWUSERREGIST", 2, 2), MC_PUSH_TYPE_FRIENDREQUEST(
				"MC_PUSH_TYPE_FRIENDREQUEST", 3, 3), MC_PUSH_TYPE_COURSEUPDATE(
				"MC_PUSH_TYPE_COURSEUPDATE", 4, 4);
		int _value;

		private MCPushType(String arg1, int arg2, int value) {
			this._value = value;
		}

	}

	public enum MCUpgradeType {

		MC_UPGRADE_TYPE_NO_UPGRADE("MC_UPGRADE_TYPE_NO_UPGRADE", 0), MC_UPGRADE_TYPE_NEED_UPGRADE(
				"MC_UPGRADE_TYPE_NEED_UPGRADE", 1), MC_UPGRADE_TYPE_MUST_UPGRADE(
				"MC_UPGRADE_TYPE_MUST_UPGRADE", 2);

		private MCUpgradeType(String arg1, int arg2) {
		}
	}

	public enum MCMediaType {
		MC_VIDEO_TYPE("MC_VIDEO_TYPE", 0, "视频"), // 视频类型
		MC_RESOURCE_TYPE("MC_RESOURCE_TYPE", 1, "下载资料"), // 下载资料
		MC_PROGRAMME_TYPE("MC_PROGRAMME_TYPE", 2, "图文"), // 图文类型
		MC_LINK_TYPE("MC_LINK_TYPE", 3, "外部链接"), // 外部链接类型
		MC_DOC_TYPE("MC_DOC_TYPE", 4, "文档"), // 文档类型
		MC_COURSEWARE_TYPE("MC_COURSEWARE_TYPE", 5, "电子课件"), // 电子课件
		MC_HOMEWORK_TYPE("MC_HOMEWORK_TYPE", 6, "作业"), // 作业类型
		MC_TOPIC_TYPE("MC_TOPIC_TYPE", 7, "主题讨论"), // 主题讨论
		MC_EVALUATION_TYPE("MC_EVALUATION_TYPE", 8, "自测"), // 自测
		MC_LIVE_TYPE("MC_LIVE_TYPE", 9, "直播"), // 直播
		MC_SCORM_TYPE("MC_SCORM_TYPE", 10, "课件"); // scorm课件
		private int iNum;
		private String type;

		private MCMediaType(String arg1, int arg2, String info) {
			iNum = arg2;
			type = info;
		}

		public int toNumber() {
			return this.iNum;
		}

		public String typeInfo() {
			return type;
		}
	}

	//下载资源类型  跟res_file_type.xml里面的maxLevel对应
	public enum MCResourcesType {
		MC_PPT_TYPE("MC_PPT_TYPE", 0, "ppt"), 			// ppt
		MC_DOC_TYPE("MC_DOC_TYPE", 1, "doc"), 			// doc
		MC_MUSIC_TYPE("MC_MUSIC_TYPE", 2, "music"), 	// music
		MC_TXT_TYPE("MC_TXT_TYPE", 3, "txt"), 			// txt
		MC_XLS_TYPE("MC_XLS_TYPE", 4, "xls"), 			// xls
		MC_RAR_TYPE("MC_RAR_TYPE", 5, "rar"), 			// rar
		MC_VIDEO_TYPE("MC_VIDEO_TYPE", 6, "video"), 	// video
		MC_PDF_TYPE("MC_PDF_TYPE", 7, "PDF"), 			// pdf
		MC_EXE_TYPE("MC_EXE_TYPE", 8, "EXE"), 			// exe
		MC_IMG_TYPE("MC_IMG_TYPE", 9, "IMG"), 			// img
		MC_FILE_TYPE("MC_FILE_TYPE", 10, "FILE"), 		// file
		MC_OTHER_TYPE("MC_OTHER_TYPE", 11, "other"); 	// other

		private int iNum;
		private String type;

		private MCResourcesType(String arg1, int num, String info) {
			iNum = num;
			type = info;
		}

		public int toNumber() {
			return this.iNum;
		}

		public String typeInfo() {
			return type;
		}
	}

	//作业状态的枚举
	public enum MCHomeworkStatus {
		MC_HOMEWORK_UNDONE("MC_HOMEWORK_UNDONE", -1, "UNDONE"), 			// 未做
		MC_HOMEWORK_UNCOMMIT("MC_HOMEWORK_UNCOMMIT", 0, "UNCOMMIT"), 	// 未提交
		MC_HOMEWORK_COMMIT("MC_HOMEWORK_COMMIT", 1, "COMMIT"), 			// 已提交
		MC_HOMEWORK_PIGAI("MC_HOMEWORK_PIGAI", 2, "PIGAI"), 			// 已批改
		MC_HOMEWORK_TURNBACK("MC_HOMEWORK_TURNBACK", 3, "TURNBACK"),// 退回
		MC_HOMEWORK_UNKNOW("MC_HOMEWORK_UNKNOW", 4, "UNKNOW");

		private int iNum;
		private String type;

		private MCHomeworkStatus(String name, int num, String info) {
			iNum = num;
			type = info;
		}

		public int toNumber() {
			return this.iNum;
		}

		public String typeInfo() {
			return type;
		}
	}

	public enum MCSendStatus {

		MC_SEND_STATUS_SENDING("MC_SEND_STATUS_SENDING", 0, 0), MC_SEND_STATUS_SUCCESS(
				"MC_SEND_STATUS_SUCCESS", 1, 1), MC_SEND_STATUS_FAILED(
				"MC_SEND_STATUS_FAILED", 2, -1);
		int value;

		private MCSendStatus(String arg1, int arg2, int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}

	}

	public enum MCMessageType {

		MC_MSG_TYPE_TEXT("MC_MSG_TYPE_TEXT", 0, 1), MC_MSG_TYPE_IMG(
				"MC_MSG_TYPE_IMG", 1, 2), MC_MSG_TYPE_FRIENDS(
				"MC_MSG_TYPE_FRIENDS", 2, 5), MC_MSG_TYPE_COURSEUPDATE(
				"MC_MSG_TYPE_COURSEUPDATE", 3, 8);
		int value;

		private MCMessageType(String arg1, int arg2, int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}

	}

	public enum MCReadStatus {

		MC_READ_STATUS_READ("MC_READ_STATUS_READ", 0, 1), MC_READ_STATUS_UNREAD(
				"MC_READ_STATUS_UNREAD", 1, 0);
		public int value;

		private MCReadStatus(String arg1, int arg2, int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}
	}

	public enum MCMessageQueryType {
		MC_MSG_FROM_SERVER("MC_MSG_FROM_SERVER", 0), MC_MSG_FROM_CACHE(
				"MC_MSG_FROM_CACHE", 1);

		int value;

		private MCMessageQueryType(String arg1, int value) {
			this.value = value;
		}
	}

	public enum MCMessageFrom {

		MC_MSG_USER("MC_MSG_USER", 0, 0), MC_MSG_SYSTEM("MC_MSG_SYSTEM", 1, 1);
		int value;

		private MCMessageFrom(String arg1, int arg2, int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}
}
