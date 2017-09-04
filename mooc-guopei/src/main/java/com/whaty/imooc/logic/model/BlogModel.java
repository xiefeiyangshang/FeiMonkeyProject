package com.whaty.imooc.logic.model;

import com.whaty.imooc.utile.GPStringUtile;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.utils.DateUtil;

import org.json.JSONObject;

import java.io.Serializable;

public class BlogModel extends MCDataModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String id;
	private String title;
	private String likeNum;
	private String viewNum;
	private String updateDate;
	private String userImg;
	private String tags;
	private String content;// 内容、
	private boolean isMyBlog; // 是不是我的日志 1是我的 0 是别人的 true是我的 false 是别人的
	private String isPublished; // 是否发布
	private String myBlogZanNum;
	private String abstractContent;

	@Override
	public MCDataModel modelWithData(Object arg1) {
		BlogModel blogModel = new BlogModel();
		JSONObject jsonObject = (JSONObject) arg1;
		try {
			blogModel.setId(jsonObject.getString("id"));
			blogModel.setUserName(jsonObject.optString("NAME"));
			blogModel.setLikeNum(jsonObject.has("zanNum") ? jsonObject.optString("zanNum") : jsonObject.optString("numzan"));
			blogModel.setMyBlogZanNum(jsonObject.optString("numzan"));
			blogModel.setViewNum(jsonObject.optString("viewCount"));
			blogModel.setUpdateDate(DateUtil.getFormatfromTimeStr(jsonObject.optString("updateDate"), DateUtil.FORMAT_FULL, DateUtil.FORMAT_NEW_MINUTE).replaceAll("\\.0", "\\."));
			String uir = GPStringUtile.addNameSpaseLearn(jsonObject.optString("userImg"));
			blogModel.setUserImg(GPStringUtile.addNameSpaseLearn(jsonObject.optString("userImg")));
			blogModel.setTitle(jsonObject.optString("title"));
			blogModel.setTags(jsonObject.optString("tags"));
			String conment = jsonObject.optString("content");
			blogModel.setContent(conment);
//			blogModel.setContent(conment.equals("") ? "" : conment.replaceAll("^(<p>)", "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")); // 添加空格
			blogModel.setMyBlog(jsonObject.optString("myblog","0").equals("1"));
			blogModel.setIsPublished(jsonObject.optString("isPublished"));
			blogModel.setAbstractContent(jsonObject.optString("abstractContent"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return blogModel;
	}

	@Override
	public String getId() {

		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(String likeNum) {
		this.likeNum = likeNum;
	}

	public String getViewNum() {
		return viewNum;
	}

	public void setViewNum(String viewNum) {
		this.viewNum = viewNum;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isMyBlog() {
		return isMyBlog;
	}

	public void setMyBlog(boolean isMyBlog) {
		this.isMyBlog = isMyBlog;
	}

	// 显示我的信息
	public String getNameAndTime() {
		return getUserName() + "   " + getUpdateDate();
	}

	public String getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}

	public String getAbstractContent() {
		return abstractContent;
	}

	public void setAbstractContent(String abstractContent) {
		this.abstractContent = abstractContent;
	}

	/**
	 * 
	 * 直接在这里完成+1 操作
	 * 
	 * @return
	 */
	public String getAndOneViewCount() {
		return String.valueOf((Integer.valueOf(getViewNum()) + 1));
	}

	public String getMyBlogZanNum() {
		return myBlogZanNum;
	}

	public void setMyBlogZanNum(String myBlogZanNum) {
		this.myBlogZanNum = myBlogZanNum;
	}

}
