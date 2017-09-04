package com.whatyplugin.imooc.logic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.imooc.logic.utils.RequestUrl;
import com.whatyplugin.imooc.logic.utils.StringUtils;

public class MCQuestionModel extends MCDataModel implements Serializable {
	private String id = "";
	private String title = ""; // 标题
	private String body = ""; // 内容
	private String keyword = ""; // 关键字
	private String publishDate = ""; // 发布日期
	private String courseId = ""; // 开课ID
	private String submituserId = ""; // 发布者LoginId
	private String submituserName = ""; // 发布者姓名
	private String submituserType = ""; // 发布者类型
	private String siteCode = ""; // 站点code
	private String lastReplyDate = ""; // 最后回复时间
	private String lastReplyUserId = ""; // 最后回复人
	private String lastReplyUserName = ""; // 最后回复姓名
	private String replyCount = ""; // 总回复次数
	private int aCount = 0; // 总回复次数
	private boolean isTeacherReplay;
	private String cPhoto;

	public String getcPhoto() {
		return cPhoto;
	}

	public void setcPhoto(String cPhoto) {
		this.cPhoto = cPhoto;
	}

	private String teacherReplyCount = ""; // 教师回复次数
	private String teacherRecommendCount = ""; // 教师推荐次数
	private String groupId = ""; // 课程章节ID
	private String userPic = ""; // 头像
	private String cName = ""; // 课程的名字
	private String type = ""; // 类型 老师或学生

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public int getaCount() {
		return aCount;
	}

	public void setaCount(int aCount) {
		this.aCount = aCount;
	}

	private String answers = "";
	private boolean isCollect = false;
	private String imgArray = "";
	private String[] originalUrls;
	private String[] smallSquareUrls;
	private String[] smallScaleUrls;
	private List<MCAnswerQuestionModel> questionModelList;
	private List<MCImgUrl> imgUrlList;

	public List<MCAnswerQuestionModel> getQuestionModelList() {
		return questionModelList;
	}

	public List<MCImgUrl> getImgUrlList() {
		return imgUrlList;
	}

	public void setImgUrlList(List<MCImgUrl> imgUrlList) {
		this.imgUrlList = imgUrlList;
	}

	public void setQuestionModelList(List<MCAnswerQuestionModel> questionModelList) {
		this.questionModelList = questionModelList;
	}

	public MCQuestionModel() {
		super();

	}

	public MCQuestionModel modelWithData(Object data) {
		String info = data == null ? "" : data.toString();
		if (info != null && info.length() > 0) {
			MCQuestionModel model = new MCQuestionModel();

			try {
				JSONObject jsonObject = new JSONObject(info);

				// 这里封装属性
				if (!jsonObject.isNull("title")) {
					model.setTitle(StringUtils.htmlStrToTextStr(jsonObject.getString("title")));
				}

				if (!jsonObject.isNull("cId")) {
					model.setCourseId(jsonObject.getString("cId"));
				}
				if (!jsonObject.isNull("cName")) {
					model.setcName(jsonObject.getString("cName"));
				}

				// 图片
				if (!jsonObject.isNull("cPhoto")) {
					String cPicture = jsonObject.getString("cPhoto");
					if (!cPicture.startsWith("http:") && !TextUtils.isEmpty(cPicture)) {
						cPicture = RequestUrl.getInstance().MODEL_BASE + cPicture;
					}
					model.setcPhoto(cPicture);
				} else if (!jsonObject.isNull("cPicture")) {
					String cPicture = jsonObject.getString("cPicture");
					if (!cPicture.startsWith("http:") && !TextUtils.isEmpty(cPicture)) {
						cPicture = RequestUrl.getInstance().MODEL_BASE + cPicture;
					}
					model.setcPhoto(cPicture);
				}

				if (!jsonObject.isNull("aCount")) {
					model.setaCount(jsonObject.getInt("aCount"));
				}

				if (!jsonObject.isNull("answerList")) {
					questionModelList = new ArrayList<MCAnswerQuestionModel>();
					JSONArray array = jsonObject.getJSONArray("answerList");// 获得答案的数组

					for (int i = 0; i < array.length(); i++) {
						MCAnswerQuestionModel mcAQModel = new MCAnswerQuestionModel();
						JSONObject innerJson = (JSONObject) array.get(i);

						if (!innerJson.isNull("id")) {
							mcAQModel.setId(innerJson.getString("id"));
						}
						if (!innerJson.isNull("body")) {
							mcAQModel.setBody(StringUtils.htmlStrToTextStr(innerJson.getString("body")));
						}
						if (!innerJson.isNull("publishDate")) {
							mcAQModel.setPublishDate(innerJson.getString("publishDate"));
						}
						if (!innerJson.isNull("answerReplyCount")) {
							mcAQModel.setAnswerReplyCount(innerJson.getString("answerReplyCount"));
						}
						if (!innerJson.isNull("isRecommend")) {
							mcAQModel.setIsRecommend(innerJson.getString("isRecommend"));
						}
						if (!innerJson.isNull("reAnswerId")) {
							mcAQModel.setReAnswerId(innerJson.getString("reAnswerId"));
						}
						// private String[] replyAnswerList;//(答案回复列表)
						if (!innerJson.isNull("reuserId")) {
							mcAQModel.setId(innerJson.getString("reuserId"));
						}
						if (!innerJson.isNull("reuserName")) {
							mcAQModel.setReuserName(innerJson.getString("reuserName"));
						}
						if (!innerJson.isNull("reuserType")) {
							mcAQModel.setReuserType(innerJson.getString("reuserType"));
						}

						mcAQModel.setAvatarUrl(innerJson.optString("avatarUrl"));

						if (!innerJson.isNull("imgUrlList")) {// 得到图片说明回复中有图片信息
							List<MCImgUrl> imgUrlList = new ArrayList<MCImgUrl>();
							JSONArray inarray = innerJson.getJSONArray("imgUrlList");// 获得答案的数组
							for (int j = 0; j < inarray.length(); j++) {
								MCImgUrl mcImgUrl = new MCImgUrl();
								JSONObject ininnerJson = (JSONObject) inarray.get(j);
								if (!ininnerJson.isNull("id")) {
									mcImgUrl.setId(ininnerJson.getString("id"));
								}
								if (!ininnerJson.isNull("originalUrl")) {
									mcImgUrl.setOriginalUrl(RequestUrl.getInstance().MODEL_BASE + ininnerJson.getString("originalUrl"));
								}
								if (!ininnerJson.isNull("squareUrl")) {
									mcImgUrl.setSquareUrl(RequestUrl.getInstance().MODEL_BASE + ininnerJson.getString("squareUrl"));
								}
								if (!ininnerJson.isNull("smallUrl")) {
									mcImgUrl.setSmallUrl(RequestUrl.getInstance().MODEL_BASE + ininnerJson.getString("smallUrl"));
								}
								if (!ininnerJson.isNull("publishDate")) {
									mcImgUrl.setPublishDate(ininnerJson.getString("publishDate"));
								}
								imgUrlList.add(mcImgUrl);
							}
							mcAQModel.setImgUrlList(imgUrlList);
						}
						questionModelList.add(mcAQModel);
					}
					model.setQuestionModelList(questionModelList);
				}
				if (!jsonObject.isNull("imgUrlList")) {
					// 添加图片的集合
					JSONArray array = jsonObject.getJSONArray("imgUrlList");// 获得答案的数组
					imgUrlList = new ArrayList<MCImgUrl>();
					for (int i = 0; i < array.length(); i++) {
						MCImgUrl mImgeUrl = new MCImgUrl();
						JSONObject innerJson = (JSONObject) array.get(i);

						if (!innerJson.isNull("id")) {
							mImgeUrl.setId(innerJson.getString("id"));
						}
						if (!innerJson.isNull("originalUrl")) {
							mImgeUrl.setOriginalUrl(RequestUrl.getInstance().MODEL_BASE + innerJson.getString("originalUrl"));
						}
						if (!innerJson.isNull("squareUrl")) {
							mImgeUrl.setSquareUrl(RequestUrl.getInstance().MODEL_BASE + innerJson.getString("squareUrl"));
						}
						if (!innerJson.isNull("smallUrl")) {
							mImgeUrl.setSmallUrl(RequestUrl.getInstance().MODEL_BASE + innerJson.getString("smallUrl"));
						}
						if (!innerJson.isNull("publishDate")) {
							mImgeUrl.setPublishDate(innerJson.getString("publishDate"));
						}
						imgUrlList.add(mImgeUrl);
					}
					model.setImgUrlList(imgUrlList);
				}

				if (!jsonObject.isNull("body")) {
					model.setBody(StringUtils.htmlStrToTextStr(jsonObject.getString("body")));
				}
				if (!jsonObject.isNull("replyCount")) {
					model.setReplyCount(jsonObject.getString("replyCount"));
				}
				if (!jsonObject.isNull("teacherRecommendCount")) {
					model.setTeacherRecommendCount(jsonObject.getString("replyCount"));
				} else {
					model.setTeacherRecommendCount("0");
				}

				model.setTeacherReplyCount(jsonObject.optString("teacherReplyCount"));

				try {
					String count = model.getTeacherReplyCount();
					if (!TextUtils.isEmpty(count) && Integer.parseInt(model.getTeacherReplyCount()) > 0) {
						model.setTeacherReplay(true);
					} else {
						model.setTeacherReplay(false);
					}
				} catch (Exception e) {
					model.setTeacherReplay(false);
				}

				if (!jsonObject.isNull("submituserName")) {
					model.setSubmituserName(jsonObject.getString("submituserName"));
				}
				if (!jsonObject.isNull("id")) {
					model.setId(jsonObject.getString("id"));
				}

				if (!jsonObject.isNull("replyCount")) {
					model.setReplyCount(jsonObject.getString("replyCount"));
				} else {
					model.setReplyCount("0");
				}
				if (!jsonObject.isNull("lastReplyUserName")) {
					model.setLastReplyUserName(jsonObject.getString("lastReplyUserName"));
				}

				if (!jsonObject.isNull("publishData")) {
					model.setPublishDate(jsonObject.getString("publishData"));
				}

				if (!jsonObject.isNull("avatarUrl")) {
					model.setUserPic(jsonObject.getString("avatarUrl"));
				}
				return model;
			} catch (Exception v1) {
				v1.printStackTrace();
			}
		}
		return null;
	}

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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getSubmituserId() {
		return submituserId;
	}

	public void setSubmituserId(String submituserId) {
		this.submituserId = submituserId;
	}

	public String getSubmituserName() {
		return submituserName;
	}

	public void setSubmituserName(String submituserName) {
		this.submituserName = submituserName;
	}

	public String getSubmituserType() {
		return submituserType;
	}

	public void setSubmituserType(String submituserType) {
		this.submituserType = submituserType;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getLastReplyDate() {
		return lastReplyDate;
	}

	public void setLastReplyDate(String lastReplyDate) {
		this.lastReplyDate = lastReplyDate;
	}

	public String getLastReplyUserId() {
		return lastReplyUserId;
	}

	public void setLastReplyUserId(String lastReplyUserId) {
		this.lastReplyUserId = lastReplyUserId;
	}

	public String getLastReplyUserName() {
		return lastReplyUserName;
	}

	public void setLastReplyUserName(String lastReplyUserName) {
		this.lastReplyUserName = lastReplyUserName;
	}

	public String getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}

	public String getTeacherReplyCount() {
		return teacherReplyCount;
	}

	public void setTeacherReplyCount(String teacherReplyCount) {
		this.teacherReplyCount = teacherReplyCount;
	}

	public String getTeacherRecommendCount() {
		return teacherRecommendCount;
	}

	public void setTeacherRecommendCount(String teacherRecommendCount) {
		this.teacherRecommendCount = teacherRecommendCount;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserPic() {
		return userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public boolean isCollect() {
		return isCollect;
	}

	public void setCollect(boolean isCollect) {
		this.isCollect = isCollect;
	}

	public String getImgArray() {
		return imgArray;
	}

	public void setImgArray(String imgArray) {
		this.imgArray = imgArray;
	}

	public String[] getOriginalUrls() {
		return originalUrls;
	}

	public void setOriginalUrls(String[] originalUrls) {
		this.originalUrls = originalUrls;
	}

	public String[] getSmallSquareUrls() {
		return smallSquareUrls;
	}

	public void setSmallSquareUrls(String[] smallSquareUrls) {
		this.smallSquareUrls = smallSquareUrls;
	}

	public String[] getSmallScaleUrls() {
		return smallScaleUrls;
	}

	public void setSmallScaleUrls(String[] smallScaleUrls) {
		this.smallScaleUrls = smallScaleUrls;
	}

	public boolean isTeacherReplay() {
		return isTeacherReplay;
	}

	public void setTeacherReplay(boolean isTeacherReplay) {
		this.isTeacherReplay = isTeacherReplay;
	}

}
