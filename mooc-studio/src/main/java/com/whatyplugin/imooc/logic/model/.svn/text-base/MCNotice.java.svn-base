package com.whatyplugin.imooc.logic.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.utils.DateUtil;
import com.whatyplugin.imooc.logic.whatydb.MCDBOpenHelper;
import com.whatyplugin.imooc.logic.whatydb.annotation.Column;
import com.whatyplugin.imooc.logic.whatydb.annotation.TableName;
@TableName(MCDBOpenHelper.TABLE_NOTIC_NAME)
public class MCNotice extends MCDataModel implements Parcelable,Comparable<MCNotice>{
	/**
	 * 通知Id
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_ID)
	private String id;
	/**
	 * 通知标题
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_TITLE)
	private String title;
	/**
	 * 通知内容
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_NOTE)
	private String note;
	/**
	 * 课程id
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_COURSEID)
	private String courseId;
	/**
	 * 发布者id
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_USERID)
	private String userId;
	/**
	 * 发布者姓名
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_USERNAME)
	private String userName;
	/**
	 * 发布者登录名
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_USER_LOGINID)
	private String userLoginId;
	/**
	 * 是否置顶：1是 0否
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_TOP)
	private String isTop;
	/**
	 * 是否有效：0无效，1有效
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_VALID)
	private String isValid;
	/**
	 * 发布日期
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_PUBLISH_DATE)
	private String publishDate;
	/**
	 * 更新日期
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_UPDATE_DATE)
	private String updateDate;
	/**
	 * 被阅读次数
	 */
	@Column(MCDBOpenHelper.TABLE_NOTIC_READCOUNT)
	private int readCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	@Override
	public MCDataModel modelWithData(Object obj) {
		MCNotice notice = new MCNotice();
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(obj.toString());
			notice.setId(jsonObj.getString("id"));
			notice.setTitle(jsonObj.getString("title"));
			int readCount = 0;
			try {
				String retCount = jsonObj.getString("readCount");
				readCount = Integer.valueOf(retCount);
			} catch (NumberFormatException e) {
				readCount = 0;
			}
			
			notice.setReadCount(readCount);
			notice.setUserId(jsonObj.getString("userId"));
			notice.setUserName(jsonObj.getString("userName"));
			notice.setUserLoginId(jsonObj.getString("userLoginId"));
			notice.setCourseId(jsonObj.getString("courseId"));
			notice.setIsTop(jsonObj.getString("isTop"));
			notice.setIsValid(jsonObj.getString("isValid"));
			notice.setNote(jsonObj.getString("note"));
			notice.setUpdateDate(DateUtil.getFormatfromTimeStr(jsonObj.getString("updateDate"), DateUtil.FORMAT_LONG, DateUtil.FORMAT_NEW_MINUTE));
			//服务器返回的时间是带秒的,效果图是不带秒的
			notice.setPublishDate(DateUtil.getFormatfromTimeStr(jsonObj.getString("publishDate"), DateUtil.FORMAT_LONG, DateUtil.FORMAT_NEW_MINUTE));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return notice;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(title);
		dest.writeString(note);
		dest.writeString(courseId);
		dest.writeString(userId);
		dest.writeString(userName);
		dest.writeString(userLoginId);
		dest.writeString(isTop);
		dest.writeString(isValid);
		dest.writeString(publishDate);
		dest.writeString(updateDate);
		dest.writeInt(readCount);

	}

	/**
	 * 从内存读取
	 */
	public static final Parcelable.Creator<MCDataModel> CREATOR = new Creator<MCDataModel>() {
		@Override
		public MCDataModel[] newArray(int size) {
			return new MCDataModel[size];
		}

		@Override
		public MCDataModel createFromParcel(Parcel parcel) {
			return new MCNotice(parcel);
		}
	};
	
	public MCNotice(Parcel parcel) {
		id = parcel.readString();		
		title = parcel.readString();	
		note = parcel.readString();	
		courseId = parcel.readString();	
		userId = parcel.readString();	
		userName = parcel.readString();	
		userLoginId = parcel.readString();	
		isTop = parcel.readString();	
		isValid = parcel.readString();	
		publishDate = parcel.readString();	
		updateDate = parcel.readString();	
		readCount = parcel.readInt();	
	}

	public MCNotice() {
	}
	
	/**
	 * 比较两个对象是否是同一个
	 */
	@Override
	public boolean equals(Object obj) {
		//检查自反性
		if(this == obj){
			 return true;
		}
		
		//是否为null
		if(obj == null){
			return false;
		}
		
		//类型检查
		if(obj instanceof MCNotice){
			MCNotice notice = (MCNotice) obj;
			//检查对象域
			return notice.updateDate.equals(this.getUpdateDate()) && notice.note.equals(this.getNote());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.updateDate.hashCode()+note.hashCode()*4;
	}

	/**
	 * 通过公告ID进行排序比较
	 */
	@Override
	public int compareTo(MCNotice another) {
		return this.getId().compareTo(another.getId());
	}
}
