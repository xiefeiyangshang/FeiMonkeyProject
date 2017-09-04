package com.whatyplugin.imooc.logic.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.text.Html;
import android.text.TextUtils;

import com.whatyplugin.base.define.MCBaseDefine.MCHomeworkStatus;
import com.whatyplugin.base.model.MCDataModel;

public class MCHomeworkModel extends MCDataModel implements Serializable {

	private String id;
	private String courseId;
	private String homeworkStuId;
	private String title;
	private String detail;
	private String startDate;
	private String endDate;
	private String startDateWithYear;
	private String endDateWithYear;
	private String commentStartDate;
	private String commentEndDate;
	private String totalScore;
	private MCHomeworkStatus status;// 学生作业状态：-1未做、0未提交(有草稿)、1已提交、2已批改、3已退回
	private boolean isComment;
	private boolean isBetweenCommitTime;// 是否提交时间范围内
	private boolean isOverTime;// 是否已过期
	private boolean isBetweenCommentTime;// 是否互评时间范围内
	private String homeworkScore;// 学生作业卷面分，例90.0
	private String note;
	private List<String> picPathList;
	private int localStatus;
	private String lastDate;
	private String comments;
	private Boolean isClick;

	public MCHomeworkModel() {
		super();
	}

	// 0 去做作业
	// 1 查看作业 - 有草稿（可以查看，选择重做）
	// 2 没有标记 ,已提交，未批改
	// 3 成绩已出
	// 4 互评
	// 5作业已过时的
	// 6不应该出现的状态
	// else if(model.status == MCHomeworkStatus.MC_HOMEWORK_UNDONE){
	// model.setLocalStatus(model.isBetweenCommitTime()?0:5);//去做作业
	// }
	private void converToLocalStatus(MCHomeworkModel model) {
		model.setIsClick(model.isBetweenCommitTime());
		if (model.status == MCHomeworkStatus.MC_HOMEWORK_UNDONE) {
			model.setLocalStatus(model.isBetweenCommitTime() ? 0 : 5);// 去做作业
		} else if (model.isComment() && model.isBetweenCommentTime()
				&& (model.status == MCHomeworkStatus.MC_HOMEWORK_COMMIT || model.status == MCHomeworkStatus.MC_HOMEWORK_PIGAI)) {

			model.setLocalStatus(4);// 互评
		} else if (model.status == MCHomeworkStatus.MC_HOMEWORK_UNCOMMIT) {
			model.setLocalStatus(model.isBetweenCommitTime() ? 1 : 5);// 查看作业 -
																		// 有草稿（可以查看，选择重做）
		} else if (model.status == MCHomeworkStatus.MC_HOMEWORK_COMMIT) {
			model.setLocalStatus(2);// 没有标记 ,已提交，未批改
		} else {
			model.setLocalStatus(5);// 已过期的状态
		}

		// 过期，驳回，过期，互评作业单独处理
//		if (model.isComment()) {
//			model.setLocalStatus(4); // 互评作业 显示时间
//		}
		// 不管在没在时间， 成绩出了就显示成绩
		if (model.status == MCHomeworkStatus.MC_HOMEWORK_PIGAI) {
			model.setLocalStatus(3);// 成绩已出
		}

	}

	private String converDateToLocal(String str, int start) {
		String returnStr = "";
		try {
			str = str.replace("-", ".");
			returnStr = str.substring(start, 10);
		} catch (Exception e) {
		}
		return returnStr;
	}

	// 学生作业状态：-1未做、0未提交(有草稿)、1已提交、2已批改、3已退回
	private MCHomeworkStatus convertStatus(String str) {
		MCHomeworkStatus status = null;
		if ("-1".equals(str)) {
			status = MCHomeworkStatus.MC_HOMEWORK_UNDONE;
		} else if ("0".equals(str)) {
			status = MCHomeworkStatus.MC_HOMEWORK_UNCOMMIT;
		} else if ("1".equals(str)) {
			status = MCHomeworkStatus.MC_HOMEWORK_COMMIT;
		} else if ("2".equals(str)) {
			status = MCHomeworkStatus.MC_HOMEWORK_PIGAI;
		} else if ("3".equals(str)) {
			// 此处被驳回的作业 没有做处理 还是待批改的状态 直接改成了 状态1 待批改的情况 。。改回打开下面注释代码
			// 注释上面代码 即可
			status = MCHomeworkStatus.MC_HOMEWORK_COMMIT;
			// status = MCHomeworkStatus.MC_HOMEWORK_TURNBACK;
		} else {
			status = MCHomeworkStatus.MC_HOMEWORK_UNKNOW;
		}
		return status;
	}

	public boolean checkBetweenTime(String start, String end) {
		if (TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date()) + ".0";
		// 2015-04-01 23:59:59.0 服务端返回的时间样式
		if (start.compareTo(now) <= 0 && end.compareTo(now) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 不在时间范围内还要细分是不是过期的
	 * 
	 * @param model
	 * @param start
	 * @param end
	 */
	public void checkIsOverTime(MCHomeworkModel model, String start, String end) {
		if (TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
			model.setOverTime(true);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date()) + ".0";
		// 2015-04-01 23:59:59.0 服务端返回的时间样式
		if (end.compareTo(now) < 0) {
			model.setOverTime(true);
		}
	}

	@Override
	public MCDataModel modelWithData(Object data) {
		if (data != null && data.toString().length() > 0) {
			String info = data.toString();
			if (info != null && info.length() > 0) {
				MCHomeworkModel model = new MCHomeworkModel();
				String startDate = "";
				String endDate = "";
				String commentStartDate = "";
				String commentEndDate = "";
				try {
					JSONObject jsonObject = new JSONObject(info);

					// title
					if (!jsonObject.isNull("title")) {
						model.setTitle(Html.fromHtml(jsonObject.getString("title")).toString());
					}

					if (!jsonObject.isNull("homeworkNote")) {
						model.setDetail(jsonObject.getString("homeworkNote").trim());
					}

					if (!jsonObject.isNull("homeworkStuNote")) {
						model.setNote(jsonObject.getString("homeworkStuNote"));
					} else {
						model.setNote("");
					}

					if (!jsonObject.isNull("isComment")) {
						model.setComment("1".equals(jsonObject.getString("isComment")));
					}

					if (!jsonObject.isNull("status")) {
						model.setStatus(convertStatus(jsonObject.getString("status")));
					}

					if (!jsonObject.isNull("startDate")) {
						startDate = jsonObject.getString("startDate");
						model.setStartDate(converDateToLocal(startDate, 5));
						model.setStartDateWithYear(converDateToLocal(startDate, 0));
					}

					if (!jsonObject.isNull("endDate")) {
						endDate = jsonObject.getString("endDate");
						model.setEndDate(converDateToLocal(endDate, 5));
						model.setEndDateWithYear(converDateToLocal(endDate, 0));
					}

					if (!jsonObject.isNull("commentStartDate")) {
						commentStartDate = jsonObject.getString("commentStartDate");
						model.setCommentStartDate(converDateToLocal(commentStartDate, 5));
					}

					if (!jsonObject.isNull("commentEndDate")) {
						commentEndDate = jsonObject.getString("commentEndDate");
						model.setCommentEndDate(converDateToLocal(commentEndDate, 5));
					}

					if (!jsonObject.isNull("homeworkId")) {
						model.setId(jsonObject.getString("homeworkId"));
					}

					if (!jsonObject.isNull("homeworkStuId")) {
						model.setHomeworkStuId(jsonObject.getString("homeworkStuId"));
					}

					if (!jsonObject.isNull("totalScore")) {
						String score = jsonObject.getString("totalScore");
						model.setTotalScore(score.replaceFirst("\\.[0-9]*$", ""));// 去掉.0
					}
					model.setComments(jsonObject.optString("comments"));
					model.setBetweenCommitTime(checkBetweenTime(startDate, endDate));
					model.setBetweenCommentTime(checkBetweenTime(commentStartDate, commentEndDate));
					if (!model.isBetweenCommitTime()) {
						checkIsOverTime(model, startDate, endDate);
					}
					converToLocalStatus(model);
					return model;
				} catch (Exception e) {
					e.printStackTrace();
				}
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCommentStartDate() {
		return commentStartDate;
	}

	public void setCommentStartDate(String commentStartDate) {
		this.commentStartDate = commentStartDate;
	}

	public String getCommentEndDate() {
		return commentEndDate;
	}

	public void setCommentEndDate(String commentEndDate) {
		this.commentEndDate = commentEndDate;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public MCHomeworkStatus getStatus() {
		return status;
	}

	public void setStatus(MCHomeworkStatus status) {
		this.status = status;
	}

	public boolean isComment() {
		return isComment;
	}

	public void setComment(boolean isComment) {
		this.isComment = isComment;
	}

	public String getHomeworkScore() {
		return homeworkScore;
	}

	public void setHomeworkScore(String homeworkScore) {
		this.homeworkScore = homeworkScore;
	}

	public boolean isBetweenCommitTime() {
		return isBetweenCommitTime;
	}

	public void setBetweenCommitTime(boolean isBetweenCommitTime) {
		this.isBetweenCommitTime = isBetweenCommitTime;
	}

	public boolean isBetweenCommentTime() {
		return isBetweenCommentTime;
	}

	public void setBetweenCommentTime(boolean isBetweenCommentTime) {
		this.isBetweenCommentTime = isBetweenCommentTime;
	}

	public String getHomeworkStuId() {
		return homeworkStuId;
	}

	public void setHomeworkStuId(String homeworkStuId) {
		this.homeworkStuId = homeworkStuId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getLocalStatus() {
		return localStatus;
	}

	public void setLocalStatus(int localStatus) {
		this.localStatus = localStatus;
	}

	public List<String> getPicPathList() {
		return picPathList;
	}

	public void setPicPathList(List<String> picPathList) {
		this.picPathList = picPathList;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStartDateWithYear() {
		return startDateWithYear;
	}

	public void setStartDateWithYear(String startDateWithYear) {
		this.startDateWithYear = startDateWithYear;
	}

	public String getEndDateWithYear() {
		return endDateWithYear;
	}

	public void setEndDateWithYear(String endDateWithYear) {
		this.endDateWithYear = endDateWithYear;
	}

	public boolean isOverTime() {
		return isOverTime;
	}

	public void setOverTime(boolean isOverTime) {
		this.isOverTime = isOverTime;
	}

	public Boolean getIsClick() {
		return isClick;
	}

	public void setIsClick(Boolean isClick) {
		this.isClick = isClick;
	}

	@Override
	public String toString() {
		return "MCHomeworkModel [id=" + id + ", courseId=" + courseId + ", homeworkStuId=" + homeworkStuId + ", title=" + title + ", detail=" + detail
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", startDateWithYear=" + startDateWithYear + ", endDateWithYear=" + endDateWithYear
				+ ", commentStartDate=" + commentStartDate + ", commentEndDate=" + commentEndDate + ", totalScore=" + totalScore + ", status=" + status
				+ ", isComment=" + isComment + ", isBetweenCommitTime=" + isBetweenCommitTime + ", isOverTime=" + isOverTime + ", isBetweenCommentTime="
				+ isBetweenCommentTime + ", homeworkScore=" + homeworkScore + ", note=" + note + ", picPathList=" + picPathList + ", localStatus="
				+ localStatus + ", lastDate=" + lastDate + ", comments=" + comments + ", isClick=" + isClick + "]";
	}

}