package com.whaty.imooc.logic.model;

import com.whaty.imooc.utile.GPStringUtile;
import com.whatyplugin.base.model.MCDataModel;

import org.json.JSONObject;

import java.io.Serializable;

public class WorkShopModel extends MCDataModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String startTime;
	private String activityId;
	private String groupId;
	private String endTime;
	private String workTitle;
	private String workType; // 专题研讨
	private int workNum;
	private String Title;
	private Boolean isWorkShop; // 工作坊下的 活动，工作坊主题
	private String time;
	private Boolean isHaveWorkShop;

	@Override
	public MCDataModel modelWithData(Object arg1) {
		WorkShopModel model = new WorkShopModel();
		JSONObject object = (JSONObject) arg1;
		String modelGroupId = object.optString("groupId");
		if (object.optString("isdel").equals("1"))
			return null;

		String title = object.optString("groupName");
		model.setTitle(title);
		if (GPStringUtile.WORKSHOP != null && !GPStringUtile.WORKSHOP.equals(modelGroupId)) {
			model.setIsWorkShop(true);
			model.setWorkNum(GPStringUtile.WORKSHOPNUM);
			GPStringUtile.WORKSHOPNUM = 1;
		} else {
			GPStringUtile.WORKSHOPNUM = GPStringUtile.WORKSHOPNUM + 1;
			model.setIsWorkShop(false);
		}
		GPStringUtile.WORKSHOP = modelGroupId;

		model.setStartTime(object.optString("startDate"));
		model.setEndTime(object.optString("endDate"));
		model.setTime(GPStringUtile.StartTimeAndZore(model.getStartTime(), model.getEndTime()));
		String activityType = object.optString("activityType");
		model.setWorkType(activityType.equals("10") ? "专题研讨" : activityType.equals("11")?"观课磨课":activityType.equals("12")?"同课异构":"暂无");
		model.setWorkTitle(object.optString("activityName"));
		model.setGroupId(object.optString("groupId"));
		model.setActivityId(object.optString("activityId"));

		return model;
	}

	@Override
	public String getId() {

		return id;
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

	public String getWorkTitle() {
		return workTitle;
	}

	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public int getWorkNum() {
		return workNum;
	}

	public void setWorkNum(int workNum) {
		this.workNum = workNum;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Boolean getIsWorkShop() {
		return isWorkShop;
	}

	public void setIsWorkShop(Boolean isWorkShop) {
		this.isWorkShop = isWorkShop;
	}

	public String getTime() {
		return String.format("起止时间:%s", time);
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Boolean getIsHaveWorkShop() {
		return isHaveWorkShop;
	}

	public void setIsHaveWorkShop(Boolean isHaveWorkShop) {
		this.isHaveWorkShop = isHaveWorkShop;
	}

}
