package com.whatyplugin.imooc.logic.model;

import java.util.List;

/**
 * 答案回复列表Bean,为了配合MCQuestion中设置的，属性设置到MCquestion中去设置
 * @author gaoli
 */

public class MCAnswerQuestionModel{
	private String id = "";
	private String body = "";			//内容
	private String publishDate = "";		//发布日期
	private String answerReplyCount = "";//本答案回复数
	private String isRecommend = "";//是否被推荐（1:推荐，0:否）
	private String reAnswerId = "";//回复答案ID
	private String reuserId = "";			//回复时间人
	private String reuserName = "";			//回复姓名
	private String reuserType = "";		//回复者类型（student/teacher）
	private String avatarUrl = "";	//头像
	private List<MCImgUrl> imgUrlList;
	
	public MCAnswerQuestionModel(){
		super();
	}


	public List<MCImgUrl> getImgUrlList() {
		return imgUrlList;
	}


	public void setImgUrlList(List<MCImgUrl> imgUrlList) {
		this.imgUrlList = imgUrlList;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getAnswerReplyCount() {
		return answerReplyCount;
	}

	public void setAnswerReplyCount(String answerReplyCount) {
		this.answerReplyCount = answerReplyCount;
	}

	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getReAnswerId() {
		return reAnswerId;
	}

	public void setReAnswerId(String reAnswerId) {
		this.reAnswerId = reAnswerId;
	}

	public String getReuserId() {
		return reuserId;
	}

	public void setReuserId(String reuserId) {
		this.reuserId = reuserId;
	}

	public String getReuserName() {
		return reuserName;
	}

	public void setReuserName(String reuserName) {
		this.reuserName = reuserName;
	}

	public String getReuserType() {
		return reuserType;
	}

	public void setReuserType(String reuserType) {
		this.reuserType = reuserType;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

}
