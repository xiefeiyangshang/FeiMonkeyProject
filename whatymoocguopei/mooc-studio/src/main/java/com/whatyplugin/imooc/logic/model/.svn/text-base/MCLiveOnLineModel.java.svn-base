package com.whatyplugin.imooc.logic.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.utils.DateUtil;

public class MCLiveOnLineModel extends MCDataModel {
	private String startTime; // 开始时间
	private String endTime; // 结束时间
	private String itemId; // itemId
	private int status; // 0:未开始　1:进行中　2:已结束，进行中和已结束才会有地址
	private String title; // 标题
	private String note; // 备注
	private String liveLink;

	@Override
	public MCDataModel modelWithData(Object arg1) {

		MCLiveOnLineModel lineModel = new MCLiveOnLineModel();
		try {
			MCLog.e("arg1=== ", arg1.toString());
			JSONObject jsonObject = new JSONObject(arg1.toString());
			lineModel.setStartTime(jsonObject.optString("startTime"));
			lineModel.setEndTime(jsonObject.optString("endTime"));
			lineModel.setStatus(jsonObject.optInt("status"));
			lineModel.setLiveLink(jsonObject.optString("liveLink"));
			lineModel.setTitle(jsonObject.optString("title"));
			lineModel.setNote(jsonObject.optString("note"));
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return lineModel;
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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLiveLink() {
		return liveLink;
	}

	public void setLiveLink(String liveLink) {
		this.liveLink = liveLink;
	}

	// Button 显示的文字
	public String getButtonText() {
		return getStatus() == 0 ? "直播未开始哦~" : getStatus() == 1 ? "进入直播室" : getStatus() == 2 ? "查看回放回放" : "暂无的状态";
	}

	// 显示的日期 2015-12-10 09:33:00.0 给定的日期
	public String showTime() {
		return showTime(oneY() ? DateUtil.FORMAT_MM_DD_CN : DateUtil.FORMAT_yyyy_MM_DD_CN);

	}

	// 判断 是不是同一天
	public boolean oneDate() {
		return startTime.replaceAll("\\s([\\d]{2}:){2}\\d{2}\\.\\d$", "").equals(endTime.replaceAll("\\s([\\d]{2}:){2}\\d{2}\\.\\d$", ""));
	}

	public String showTime(String dataFrat) {
		return DateUtil.getFormatfromTimeStr(startTime, DateUtil.FORMAT_FULL, dataFrat)
				+ (oneDate() ? ("-" + DateUtil.getFormatfromTimeStr(endTime, DateUtil.FORMAT_FULL, DateUtil.FORMAT_HH_MM_CN)) : "");

	}

	// 判断是都是同一年
	public boolean oneY() {
		return startTime.contains(DateUtil.getYear(new Date()));
	}

	@Override
	public String getId() {

		return null;
	}

}
