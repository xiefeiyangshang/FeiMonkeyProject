package com.whatyplugin.imooc.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.imooc.logic.whatydb.MCDBOpenHelper;
import com.whatyplugin.imooc.logic.whatydb.annotation.Column;
import com.whatyplugin.imooc.logic.whatydb.annotation.TableName;
@TableName(MCDBOpenHelper.TABLE_OFFLINE_NAME)
public class MCLearnOfflineRecord extends MCDataModel implements Parcelable ,Comparable<MCLearnOfflineRecord>{

	/*
	 * 视频对应id
	 */
	@Column(MCDBOpenHelper.TABLE_OFFLINE_ID)
	private String id;
	
	/*
	 * 课程id
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_COURSEID)
	private String courseId;
	/**
	 * 发布者id
	 */
	@Column(MCDBOpenHelper.TABLE_OFFLINE_USERID)
	private String userId;
	
	/*
	 * 该视频上次播放的时间
	 */
	@Column(MCDBOpenHelper.TABLE_OFFLINE_RECORD_TIME)
	private String recordTime;
	/*
	 * 学习的时长
	 */
	@Column(MCDBOpenHelper.TABLE_OFFLINE_STUDY_TIME)
	private String studyTime;
	
	@Column(MCDBOpenHelper.TABLE_OFFLINE_TOTAL_TIME)
	private String totalTime;
	
	@Column(MCDBOpenHelper.TABLE_OFFLINE_TYPE)
	private int type;
	
	
	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getStudyTime() {
		return studyTime;
	}

	public void setStudyTime(String studyTime) {
		this.studyTime = studyTime;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public void setId(String id) {
		this.id = id;
	}
	public MCLearnOfflineRecord(){
		
	}
	public MCLearnOfflineRecord(Parcel parcel){
		id = parcel.readString();		
		courseId = parcel.readString();	
		userId = parcel.readString();	
		recordTime = parcel.readString();	
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(courseId);
		dest.writeString(userId);
		dest.writeString(recordTime);
	}

	@Override
	public MCDataModel modelWithData(Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(MCLearnOfflineRecord another) {
		// TODO Auto-generated method stub
		return 0;
	}

}
