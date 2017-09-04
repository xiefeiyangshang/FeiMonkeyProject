package com.whaty.imooc.logic.model;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.utils.DateUtil;

import org.json.JSONArray;
import org.json.JSONObject;

public class ClassInfoModel extends MCDataModel {

	// 存储的2
	private String headTeacherName2;
	private String organId2;
	private String banjiName2;
	private String projectId2;
	private String headTeacherPhone2;
	private String banjiId2;
	private String homeCourseId2;
	private String startTime2;
	private String endTime2;
	private Boolean NoTime2;
	private boolean expiratonTime2;
	private String isStatusName2;
	
	// 存储的1
	private String headTeacherName1;
	private String organId1;
	private String banjiName1;
	private String projectId1;
	private String headTeacherPhone1;
	private String banjiId1;
	private String homeCourseId1;
	private String startTime1;
	private String endTime1;
	private Boolean NoTime1;
	private boolean expiratonTime1;
	private String isStatusName1;

	// 为了兼容以前已经使用的 程序中真正使用的，选择的时候进行复制
	private String headTeacherName = "";
	private String organId = "";
	private String banjiName = "";
	private String projectId = "";
	private String headTeacherPhone = "";
	private String banjiId = "";
	private String homeCourseId = "";
	private String startTime;
	private String endTime;
	private String isStatusName;  // 0是能力提升  1是有效学习

	private boolean onlyHaveOneClass = false; // 只有一个班级
	private boolean expiratonTime; // 班级过期 只判断当前的 true 表示过期 false表示没有过期
	private String haveOneExpTime; // 期中一个过期 "one" 代表一班 “two代表二班”
	private Boolean NoTime;
	private String  stateFlag;

	public String getStateFlag() {
		return stateFlag;
	}

	public void setStateFlag(String stateFlag) {
		this.stateFlag = stateFlag;
	}

	@Override
	public MCDataModel modelWithData(Object arg1) {

		ClassInfoModel classInfoModel = new ClassInfoModel();
		try {
			JSONArray array = (JSONArray) arg1;
			System.out.println("=====  "+array);
			int length = array.length();
			if (length == 1) {
				JSONObject jsonObj = array.getJSONObject(0);
				classInfoModel.setNoTime(true);
				saveClassInfo(classInfoModel, jsonObj);
				if (classInfoModel.getNoTime()) {
					classInfoModel.setExpiratonTime(false);
				} else {
					classInfoModel.setExpiratonTime(false);
				}

				
				return classInfoModel;
			}

			for (int i = 0; i < array.length() && !classInfoModel.isOnlyHaveOneClass(); i++) {
				JSONObject jsonObj = array.getJSONObject(i);

				if (i == 0) {
					classInfoModel.setNoTime1(true);
					if (!classInfoModel.getNoTime1()) {
						if (inTime(jsonObj)) {
							classInfoModel.setExpiratonTime1(true);
//							if (outTime(jsonObj)) {
//								continue;
//							}
						}
					}
					classInfoModel.setBanjiId1(jsonObj.optString("ext0"));
					classInfoModel.setHeadTeacherName1(jsonObj.optString("ext7"));
					classInfoModel.setBanjiName1(jsonObj.optString("ext1"));
					classInfoModel.setProjectId1(jsonObj.optString("ext3"));
					classInfoModel.setOrganId1(jsonObj.optString("ext4"));
					classInfoModel.setHeadTeacherPhone1(jsonObj.optString("ext8"));
					classInfoModel.setHomeCourseId1(jsonObj.optString("ext2"));
					classInfoModel.setStartTime1(jsonObj.optString("ext5"));
					classInfoModel.setEndTime1(jsonObj.optString("ext6"));
					classInfoModel.setStatusName1(jsonObj.optString("ext9"));
					classInfoModel.setStateFlag(jsonObj.optString("ext10"));


				}
				if (i == 1) {
					classInfoModel.setNoTime2(true);
					if (!classInfoModel.getNoTime2()) {
						if (inTime(jsonObj)) {
							classInfoModel.setExpiratonTime2(true);
//							if (outTime(jsonObj)) {
//								continue;
//							}
						}
					}
					classInfoModel.setBanjiId2(jsonObj.optString("ext0"));
					classInfoModel.setHeadTeacherName2(jsonObj.optString("ext7"));
					classInfoModel.setBanjiName2(jsonObj.optString("ext1"));
					classInfoModel.setProjectId2(jsonObj.optString("ext3"));
					classInfoModel.setOrganId2(jsonObj.optString("ext4"));
					classInfoModel.setHeadTeacherPhone2(jsonObj.optString("ext8"));
					classInfoModel.setHomeCourseId2(jsonObj.optString("ext2"));
					classInfoModel.setStartTime2(jsonObj.optString("ext5"));
					classInfoModel.setEndTime2(jsonObj.optString("ext6"));
					classInfoModel.setStatusName2(jsonObj.optString("ext9"));
					classInfoModel.setStateFlag(jsonObj.optString("ext10"));

					classInfoModel.setNoTime2(true);
				}

			}
			if (classInfoModel.isExpiratonTime1() && classInfoModel.isExpiratonTime2()) {
				classInfoModel.setExpiratonTime(true);// 全部都过期
			} else if (classInfoModel.isExpiratonTime1() || classInfoModel.isExpiratonTime2()) { // 如果
				if (!classInfoModel.isExpiratonTime1()) {
					saveClassInfo(classInfoModel, array.getJSONObject(0));
				}
				if (!classInfoModel.isExpiratonTime2()) {
					saveClassInfo(classInfoModel, array.getJSONObject(1));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return classInfoModel;
	}

	private void saveClassInfo(ClassInfoModel classInfoModel, JSONObject jsonObj) {
		classInfoModel.setOnlyHaveOneClass(true);
		classInfoModel.setBanjiId(jsonObj.optString("ext0"));
		classInfoModel.setHeadTeacherName(jsonObj.optString("ext7"));
		classInfoModel.setBanjiName(jsonObj.optString("ext1"));
		classInfoModel.setProjectId(jsonObj.optString("ext3"));
		classInfoModel.setOrganId(jsonObj.optString("ext4"));
		classInfoModel.setHeadTeacherPhone(jsonObj.optString("ext8"));
		classInfoModel.setHomeCourseId(jsonObj.optString("ext2"));
		classInfoModel.setStartTime(jsonObj.optString("ext5"));
		classInfoModel.setEndTime(jsonObj.optString("ext6"));
		classInfoModel.setStatusName(jsonObj.optString("ext9"));
		classInfoModel.setStateFlag(jsonObj.optString("ext10"));

		classInfoModel.setExpiratonTime(false);
		

	}

	private boolean inTime(JSONObject jsonObj) {
		return unStartTime(jsonObj);
	}

//	private boolean outTime(JSONObject jsonObj) {
//		return !DateUtil.YYYYMMDDBeforeNow(jsonObj.optString("ext6"));
//	}

	private boolean unStartTime(JSONObject jsonObj) {
		return DateUtil.YYYYMMDDBeforeNow(jsonObj.optString("ext5"));
	}

	@Override
	public String getId() {

		return null;
	}



	public String getOrganId2() {
		return organId2;
	}

	public void setOrganId2(String organId2) {
		this.organId2 = organId2;
	}

	public String getBanjiName2() {
		return banjiName2;
	}

	public void setBanjiName2(String banjiName2) {
		this.banjiName2 = banjiName2;
	}

	public String getProjectId2() {
		return projectId2;
	}

	public void setProjectId2(String projectId2) {
		this.projectId2 = projectId2;
	}

	public String getHeadTeacherPhone2() {
		return headTeacherPhone2;
	}

	public void setHeadTeacherPhone2(String headTeacherPhone2) {
		this.headTeacherPhone2 = headTeacherPhone2;
	}

	public String getBanjiId2() {
		return banjiId2;
	}

	public void setBanjiId2(String banjiId2) {
		this.banjiId2 = banjiId2;
	}



	public String getOrganId1() {
		return organId1;
	}

	public void setOrganId1(String organId1) {
		this.organId1 = organId1;
	}

	public String getBanjiName1() {
		return banjiName1;
	}

	public void setBanjiName1(String banjiName1) {
		this.banjiName1 = banjiName1;
	}

	public String getProjectId1() {
		return projectId1;
	}

	public void setProjectId1(String projectId1) {
		this.projectId1 = projectId1;
	}

	public String getHeadTeacherPhone1() {
		return headTeacherPhone1;
	}

	public void setHeadTeacherPhone1(String headTeacherPhone1) {
		this.headTeacherPhone1 = headTeacherPhone1;
	}

	public String getBanjiId1() {
		return banjiId1;
	}

	public void setBanjiId1(String banjiId1) {
		this.banjiId1 = banjiId1;
	}



	public String getBanjiName() {
		return banjiName;
	}

	public void setBanjiName(String banjiName) {
		this.banjiName = banjiName;
	}




	public String getBanjiId() {
		return banjiId;
	}

	public void setBanjiId(String banjiId) {
		this.banjiId = banjiId;
	}



	public String getHomeCourseId2() {
		return homeCourseId2;
	}

	public void setHomeCourseId2(String homeCourseId2) {
		this.homeCourseId2 = homeCourseId2;
	}

	public String getHomeCourseId1() {
		return homeCourseId1;
	}

	public void setHomeCourseId1(String homeCourseId1) {
		this.homeCourseId1 = homeCourseId1;
	}

	public String getStartTime2() {
		return startTime2;
	}

	public void setStartTime2(String startTime2) {
		this.startTime2 = startTime2;
	}

	public String getEndTime2() {
		return endTime2;
	}

	public void setEndTime2(String endTime2) {
		this.endTime2 = endTime2;
	}

	public String getStartTime1() {
		return startTime1;
	}

	public void setStartTime1(String startTime1) {
		this.startTime1 = startTime1;
	}

	public String getEndTime1() {
		return endTime1;
	}

	public void setEndTime1(String endTime1) {
		this.endTime1 = endTime1;
	}

	public boolean isOnlyHaveOneClass() {
		return onlyHaveOneClass;
	}

	public void setOnlyHaveOneClass(boolean onlyHaveOneClass) {
		this.onlyHaveOneClass = onlyHaveOneClass;
	}

	public boolean isExpiratonTime() {
		return expiratonTime;
	}

	public void setExpiratonTime(boolean expiratonTime) {
		this.expiratonTime = expiratonTime;
	}

	public String getHaveOneExpTime() {
		return haveOneExpTime;
	}

	public void setHaveOneExpTime(String haveOneExpTime) {
		this.haveOneExpTime = haveOneExpTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Boolean getNoTime2() {
		return NoTime2;
	}

	public void setNoTime2(Boolean noTime2) {
		NoTime2 = noTime2;
	}

	public Boolean getNoTime1() {
		return NoTime1;
	}

	public void setNoTime1(Boolean noTime1) {
		NoTime1 = noTime1;
	}

	public Boolean getNoTime() {
		return NoTime;
	}

	public void setNoTime(Boolean noTime) {
		NoTime = noTime;
	}

	public boolean isExpiratonTime2() {
		return expiratonTime2;
	}

	public void setExpiratonTime2(boolean expiratonTime2) {
		this.expiratonTime2 = expiratonTime2;
	}

	public boolean isExpiratonTime1() {
		return expiratonTime1;
	}

	public void setExpiratonTime1(boolean expiratonTime1) {
		this.expiratonTime1 = expiratonTime1;
	}

	public String getHeadTeacherName2() {
		return headTeacherName2;
	}

	public void setHeadTeacherName2(String headTeacherName2) {
		this.headTeacherName2 = headTeacherName2;
	}

	public String getHeadTeacherName1() {
		return headTeacherName1;
	}

	public void setHeadTeacherName1(String headTeacherName1) {
		this.headTeacherName1 = headTeacherName1;
	}

	public String getHeadTeacherName() {
		return headTeacherName;
	}

	public void setHeadTeacherName(String headTeacherName) {
		this.headTeacherName = headTeacherName;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getHeadTeacherPhone() {
		return headTeacherPhone;
	}

	public void setHeadTeacherPhone(String headTeacherPhone) {
		this.headTeacherPhone = headTeacherPhone;
	}

	public String getHomeCourseId() {
		return homeCourseId;
	}

	public void setHomeCourseId(String homeCourseId) {
		this.homeCourseId = homeCourseId;
	}

	public String isStatusName2() {
		return isStatusName2;
	}

	public void setStatusName2(String isStatusName2) {
		this.isStatusName2 = isStatusName2;
	}

	public String isStatusName1() {
		return isStatusName1;
	}

	public void setStatusName1(String isStatusName1) {
		this.isStatusName1 = isStatusName1;
	}

	public String isStatusName() {
		return isStatusName;
	}

	public void setStatusName(String isStatusName) {
		this.isStatusName = isStatusName;
	}

	

}
