package com.whatyplugin.imooc.logic.model;

import java.io.Serializable;

import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.imooc.logic.utils.StringUtils;

public class MCMyNoteModel extends MCDataModel implements Serializable {
	private static final String TAG = MCMyNoteModel.class.getSimpleName();

	private String id;
	private String title;
	private String content;
	private String lastPerateDate;

	/**
	 * 标记标签
	 */
	private String tag;

	public String getLastPerateDate() {
		return lastPerateDate;
	}

	public void setLastPerateDate(String lastPerateDate) {
		this.lastPerateDate = lastPerateDate;
	}

	private String courseId;
	/**
	 * 发布者Id
	 */
	private String userId;
	/**
	 * 发布者ssoUserId
	 */
	private String ssuUserId;
	/**
	 * 发布者姓名
	 */
	private String ssoUserTrueName;
	/**
	 * 是否公开
	 */
	private boolean isPublic;
	/**
	 * 是否教师推荐
	 */
	private boolean isTRecommend;
	/**
	 * 创建日期
	 */
	private String createDate;
	/**
	 * 更新日期
	 */
	private String updateDate;
	/**
	 * 笔记作者头像
	 */
	private String photo;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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

	public String getSsuUserId() {
		return ssuUserId;
	}

	public void setSsuUserId(String ssuUserId) {
		this.ssuUserId = ssuUserId;
	}

	public String getSsoUserTrueName() {
		return ssoUserTrueName;
	}

	public void setSsoUserTrueName(String ssoUserTrueName) {
		this.ssoUserTrueName = ssoUserTrueName;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public boolean isTRecommend() {
		return isTRecommend;
	}

	public void setTRecommend(boolean isTRecommend) {
		this.isTRecommend = isTRecommend;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public static String getTag() {
		return TAG;
	}

	public MCMyNoteModel() {
		super();
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public MCMyNoteModel modelWithData(Object data) {
		if (data != null && data.toString().length() > 0) {
			MCMyNoteModel model = new MCMyNoteModel();
			try {
				JSONObject obj = new JSONObject(data.toString());
				model.setId(obj.optString("id"));
				model.setTitle(StringUtils.htmlStrToTextStr(obj.optString("title")));
				model.setContent(StringUtils.htmlStrToTextStr(obj.optString("note")));
				model.setTag(obj.optString("tag"));
				model.setCourseId(obj.optString("courseId"));
				model.setUserId(obj.optString("loginId"));
				model.setSsuUserId(obj.optString("ssuUserId"));
				model.setSsoUserTrueName(obj.optString("ssoUserTrueName"));

				model.setLastPerateDate(obj.optString("updateDate").equals("null") ? obj.optString("createDate") : obj.optString("updateDate"));
				model.setCreateDate(obj.optString("createDate"));
				model.setUpdateDate(obj.optString("updateDate"));
				if (!obj.isNull("photo")) {
					model.setPhoto(obj.optString("photo"));
				}

				if (obj.has("isPublic")) {
					String isP = obj.getString("isPublic");
					if ("1".equals(isP))
						model.setPublic(true);
				}
				if (obj.has("isTRecommend")) {
					String isR = obj.getString("isTRecommend");
					if ("1".equals(isR))
						model.setTRecommend(true);
				}

				return model;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
