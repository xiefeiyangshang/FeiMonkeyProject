package com.whaty.imooc.utile;

import com.whaty.imooc.logic.model.ClassInfoModel;

public class SharedClassInfo {

	public static void saveClassInfo(ClassInfoModel classInfo) {
		SharedPrefsUtil.putVlaue(GPContants.USER_BANJIID_1, classInfo.getBanjiId1());
		SharedPrefsUtil.putVlaue(GPContants.USER_BANJINAME_1, classInfo.getBanjiName1());
		SharedPrefsUtil.putVlaue(GPContants.USER_HEADTEACHERNAME_1, classInfo.getHeadTeacherName1());
		SharedPrefsUtil.putVlaue(GPContants.USER_HEADTEACHERPHONE_1, classInfo.getHeadTeacherPhone1());
		SharedPrefsUtil.putVlaue(GPContants.USER_PROJECTID_1, classInfo.getProjectId1());
		SharedPrefsUtil.putVlaue(GPContants.USER_ORGANID_1, classInfo.getOrganId1());
		SharedPrefsUtil.putVlaue(GPContants.USER_HOMECOURSEID_1, classInfo.getHomeCourseId1());
		SharedPrefsUtil.putVlaue(GPContants.USER_STARTTIME_1, classInfo.getStartTime1());
		SharedPrefsUtil.putVlaue(GPContants.USER_ENDTIME_1, classInfo.getEndTime1());
		SharedPrefsUtil.putVlaue(GPContants.USER_STATUSNAME_1, classInfo.isStatusName1());

		SharedPrefsUtil.putVlaue(GPContants.USER_BANJIID_2, classInfo.getBanjiId2());
		SharedPrefsUtil.putVlaue(GPContants.USER_BANJINAME_2, classInfo.getBanjiName2());
		SharedPrefsUtil.putVlaue(GPContants.USER_HEADTEACHERNAME_2, classInfo.getHeadTeacherName2());
		SharedPrefsUtil.putVlaue(GPContants.USER_HEADTEACHERPHONE_2, classInfo.getHeadTeacherPhone2());
		SharedPrefsUtil.putVlaue(GPContants.USER_PROJECTID_2, classInfo.getProjectId2());
		SharedPrefsUtil.putVlaue(GPContants.USER_ORGANID_2, classInfo.getOrganId2());
		SharedPrefsUtil.putVlaue(GPContants.USER_HOMECOURSEID_2, classInfo.getHomeCourseId2());
		SharedPrefsUtil.putVlaue(GPContants.USER_STARTTIME_2, classInfo.getStartTime2());
		SharedPrefsUtil.putVlaue(GPContants.USER_ENDTIME_2, classInfo.getEndTime2());
		SharedPrefsUtil.putVlaue(GPContants.USER_STATUSNAME_2, classInfo.isStatusName2());

		SharedPrefsUtil.putVlaue(GPContants.USER_BANJIID, classInfo.getBanjiId());
		SharedPrefsUtil.putVlaue(GPContants.USER_BANJINAME, classInfo.getBanjiName());
		SharedPrefsUtil.putVlaue(GPContants.USER_HEADTEACHERNAME, classInfo.getHeadTeacherName());
		SharedPrefsUtil.putVlaue(GPContants.USER_HEADTEACHERPHONE, classInfo.getHeadTeacherPhone());
		SharedPrefsUtil.putVlaue(GPContants.USER_PROJECTID, classInfo.getProjectId());
		SharedPrefsUtil.putVlaue(GPContants.USER_ORGANID, classInfo.getOrganId());
		SharedPrefsUtil.putVlaue(GPContants.USER_HOMECOURSEID, classInfo.getHomeCourseId());
		SharedPrefsUtil.putVlaue(GPContants.USER_ONLYHAVEONECLASS, String.valueOf(classInfo.isOnlyHaveOneClass()));
		SharedPrefsUtil.putVlaue(GPContants.USER_STATE_FLAG, classInfo.getStateFlag());
	}

	public static String getKeyValue(String key) {
		return SharedPrefsUtil.getValue(key);

	}

	public static void clear() {
		SharedPrefsUtil.clearData();
	}

	public static void saveUserHeadTeacherName(String headTeacherName) {
		SharedPrefsUtil.putVlaue(GPContants.USER_HEADTEACHERNAME, headTeacherName);
	}

	public static void saveUserOrganId(String organId) {
		SharedPrefsUtil.putVlaue(GPContants.USER_ORGANID, organId);
	}

	public static void saveUserBanjiName(String banjiName) {
		SharedPrefsUtil.putVlaue(GPContants.USER_BANJINAME, banjiName);
	}

	public static void saveUserProjectId(String projectId) {
		SharedPrefsUtil.putVlaue(GPContants.USER_PROJECTID, projectId);
	}

	public static void saveUserHeadTeacherPhone(String headTeacherPhone) {
		SharedPrefsUtil.putVlaue(GPContants.USER_HEADTEACHERPHONE, headTeacherPhone);
	}

	public static void saveUserBanjiId(String banjiId) {
		SharedPrefsUtil.putVlaue(GPContants.USER_BANJIID, banjiId);
	}

	public static void saveUserHomeWordCourseId(String homeWordCourseId) {
		SharedPrefsUtil.putVlaue(GPContants.USER_HOMECOURSEID, homeWordCourseId);
	}
	public static void saveUserOnlyOneClass(String flag) {
		SharedPrefsUtil.putVlaue(GPContants.USER_ONLYHAVEONECLASS, flag);
	}

	public static void saveUserStatusName(String status) {
		SharedPrefsUtil.putVlaue(GPContants.USER_STATUSNAME, status);
	}
	


}
