package com.whatyplugin.imooc.logic.model;

import android.text.TextUtils;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.type.MCTime;
import com.whatyplugin.imooc.logic.model.MCSectionModel.MCCourseFocusStatus;
import com.whatyplugin.imooc.logic.utils.RequestUrl;
import com.whatyplugin.imooc.logic.utils.StringUtils;

import org.json.JSONObject;

import java.io.Serializable;

public class MCCourseModel extends MCDataModel implements Serializable {
	private int companyId;
	private MCTime courseDuration;
	private String description;
	private String id;
	private String imageUrl;
	private MCCourseFocusStatus isFocused;// 用此参数来作为是否已选课程的判断
	private int learnedCount;
	private String name;
	private static final long serialVersionUID = 1;
	private String shareUrl;
	private int studiedChapterSeq;
	private int studiedMediaSeq;
	private MCCourseTypeModel type;
	private String credit;
	private int nCount; // 笔记数量
	private String className; // 班级名称
	private String percent;//课程学习百分比

	/**
	 * 学习时长
	 * @return
     */
	private String courseTime;
	private String effectTime;
	private String pageTime;

	public String getPageTime() {
		return pageTime;
	}

	public void setPageTime(String pageTime) {
		this.pageTime = pageTime;
	}

	public String getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(String effectTime) {
		this.effectTime = effectTime;
	}

	public String getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getnCount() {
		return nCount;
	}

	public void setnCount(int nCount) {
		this.nCount = nCount;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public MCCourseModel() {
		super();
	}

	public boolean equals(MCCourseModel o) {
		boolean v1;
		if (!(o instanceof MCCourseModel)) {
			v1 = false;
		} else if (this.getId() == o.getId()) {
			v1 = true;
		} else {
			v1 = super.equals(o);
		}

		return v1;
	}

	public int getCompanyId() {
		return this.companyId;
	}

	public MCTime getCourseDuration() {
		return this.courseDuration;
	}

	public String getDescription() {
		return this.description;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public MCCourseFocusStatus getIsFocused() {
		return this.isFocused;
	}

	public int getLearnedCount() {
		return this.learnedCount;
	}

	public String getName() {
		return this.name;
	}

	public String getShareUrl() {
		return this.shareUrl;
	}

	public int getStudiedChapterSeq() {
		return this.studiedChapterSeq;
	}

	public int getStudiedMediaSeq() {
		return this.studiedMediaSeq;
	}

	public MCCourseTypeModel getType() {
		return this.type;
	}

	public MCCourseModel modelWithData(Object data) {
		if (data != null && data.toString().length() > 0) {
			String info = data.toString();
			if (info != null && info.length() > 0) {
				MCCourseModel model = new MCCourseModel();

				try {
					JSONObject jsonObject = new JSONObject(info);

					// 名称
					if (jsonObject.has("cName")) {
						model.setName(jsonObject.getString("cName"));
					}

					// 图片
					if (!jsonObject.isNull("cPhoto")) {
						String cPicture = jsonObject.getString("cPhoto");
						if (!cPicture.startsWith("http:") && !TextUtils.isEmpty(cPicture)) {
							cPicture = RequestUrl.getInstance().MODEL_BASE + cPicture;
						}
						model.setImageUrl(cPicture);
					} else if (!jsonObject.isNull("cPicture")) {
						String cPicture = jsonObject.getString("cPicture");
						if (!cPicture.startsWith("http:") && !TextUtils.isEmpty(cPicture)) {
							cPicture = RequestUrl.getInstance().MODEL_BASE + cPicture;
						}
						model.setImageUrl(cPicture);
					}

					// 学分
					if (jsonObject.has("cCredit")) {
						model.setCredit(jsonObject.getString("cCredit"));
					}

					if (jsonObject.has("cId")) {
						model.setId(jsonObject.getString("cId"));
					}

					if (jsonObject.has("duration")) {
						model.setCourseDuration(MCTime.timeWithMilliseconds(((long) jsonObject.getInt("duration"))));
					}

					if (jsonObject.has("chapter_seq")) {
						model.setStudiedChapterSeq(jsonObject.getInt("chapter_seq"));
					}

					if (jsonObject.has("media_seq")) {
						model.setStudiedMediaSeq(jsonObject.getInt("media_seq"));
					}

					if (jsonObject.has("share_url")) {
						model.setShareUrl(jsonObject.getString("share_url"));
					}
					if (jsonObject.has("className"))
						model.setClassName(jsonObject.getString("className"));

					// 是否是已选课程
					if (jsonObject.has("cStatus")) {
						int status = (jsonObject.getInt("cStatus"));
						if (status == 1) {
							model.setIsFocused(MCCourseFocusStatus.MC_COURSE_FOCUSED);
						} else {
							model.setIsFocused(MCCourseFocusStatus.MC_COURSE_UNFOCUSED);
						}
					}

					if (jsonObject.has("nCount") && !jsonObject.isNull("nCount")) {
						model.setnCount(jsonObject.getInt("nCount"));
					}

					// 选课人数
					if (jsonObject.has("eCount")) {
						model.setLearnedCount(jsonObject.getInt("eCount"));
					}
					
					// 课程学习百分比
					if (jsonObject.has("percent")) {
						model.setPercent(jsonObject.getString("percent"));
					}

					String courseInfo = null;
					// 课程介绍
					if (!jsonObject.isNull("cIntro")) {
						courseInfo = jsonObject.getString("cIntro");
					} else if (!jsonObject.isNull("cInfo")) {
						courseInfo = jsonObject.getString("cInfo");
					}
					model.setDescription(StringUtils.trimSpace(courseInfo));

					return model;
				} catch (Exception v1) {
					v1.printStackTrace();
				}
			}
		}
		return null;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public void setCourseDuration(MCTime courseDuration) {
		this.courseDuration = courseDuration;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setIsFocused(MCCourseFocusStatus isFocused) {
		this.isFocused = isFocused;
	}

	public void setLearnedCount(int learnedCount) {
		this.learnedCount = learnedCount;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public void setStudiedChapterSeq(int studiedChapterSeq) {
		this.studiedChapterSeq = studiedChapterSeq;
	}

	public void setStudiedMediaSeq(int studiedMediaSeq) {
		this.studiedMediaSeq = studiedMediaSeq;
	}

	public void setType(MCCourseTypeModel type) {
		this.type = type;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}
	
}
