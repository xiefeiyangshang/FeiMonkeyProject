package com.whatyplugin.imooc.logic.model;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.whatyplugin.base.enums.TimeStyle;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.utils.DateUtil;

/**
 * 自测列表的条目
 * 
 * @author bzy
 */
public class MCTestModel extends MCDataModel implements Parcelable {

	public static interface TextConstant {
		public static String TEST_NOT_START = "尚未开始";
		public static String TEST_START = "进行中";
		public static String TEST_END = "已过期";
		public static String TEXT_NOT_HAVE = "暂无";
		public static String TEXT_TIME_NOTIN_SETTIME ="题目已经过期!";
		public static String TEXT_TIME_MORETHEN_NOWTIME ="题目尚未开始!";
	}

	/**
	 * 测试倒计时(long)
	 */
	private long txtLimitTime;
	/**
	 * 用户保存的答案
	 */
	private String answer;
	/**
	 * 自测对应的ID
	 */
	private String openCourseID;

	/**
	 * 处理好的开始时间加上结束时间 {11.02-11.29}
	 */
	private String descTime;
	/**
	 * 最终得分
	 */
	private String finalScore = "0";
	/**
	 * 自测开始时间
	 */
	private String startDate;
	/**
	 * 自测结束时间
	 */
	private String endDate;
	/**
	 * 自测标题
	 */
	private String title;
	/**
	 * 试卷类型：0指定 1随机
	 */
	private String type;
	/**
	 * 自测题id
	 */
	private String id;
	/**
	 * 题目数
	 */
	private int questionCount;
	/**
	 * 已重做次数
	 */
	private int hasRedoNum;
	/**
	 * 当前允许做的次数： -1 无限制， 0 1 2 3代表重做次数
	 */
	private int redo_num;
	/**
	 * 重做方式：1重做(不同的题目)，0修改(同一套题目)，2无(针对指定类型试卷)
	 */
	private int redo_type;
	/**
	 * 题目构成和关联知识点
	 */
	private MCTestInfo testInfo;
   private boolean unStart;
	public boolean isUnStart() {
	return unStart;
}

public void setUnStart(boolean unStart) {
	this.unStart = unStart;
}

	/**
	 * 来自内存的构造器
	 * 
	 * @param parcel
	 */
	public MCTestModel(Parcel parcel) {
		txtLimitTime = parcel.readLong();
		answer = parcel.readString();
		openCourseID = parcel.readString();
		descTime = parcel.readString();
		finalScore = parcel.readString();
		startDate = parcel.readString();
		endDate = parcel.readString();
		title = parcel.readString();
		type = parcel.readString();
		id = parcel.readString();
		questionCount = parcel.readInt();
		hasRedoNum = parcel.readInt();
		redo_num = parcel.readInt();
		redo_type = parcel.readInt();
		testInfo = parcel.readParcelable(MCTestInfo.class.getClassLoader());
	}

	/**
	 * 用户创建
	 */
	public MCTestModel() {
	}

	public long getTxtLimitTime() {
		return txtLimitTime;
	}

	public void setTxtLimitTime(long txtLimitTime) {
		this.txtLimitTime = txtLimitTime;
	}

	public String getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	public int getHasRedoNum() {
		return hasRedoNum;
	}

	public void setHasRedoNum(int hasRedoNum) {
		this.hasRedoNum = hasRedoNum;
	}

	public int getRedo_num() {
		return redo_num;
	}

	public void setRedo_num(int redo_num) {
		this.redo_num = redo_num;
	}

	public int getRedo_type() {
		return redo_type;
	}

	public void setRedo_type(int redo_type) {
		this.redo_type = redo_type;
	}

	public String getOpenCourseID() {
		return openCourseID;
	}

	public void setOpenCourseID(String openCourseID) {
		this.openCourseID = openCourseID;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public MCTestInfo getTestInfo() {
		return testInfo;
	}

	public void setTestInfo(MCTestInfo testInfo) {
		this.testInfo = testInfo;
	}

	public String getDescTime(TimeStyle te) {
		if (te.equals(TimeStyle.M)) { // 转为仅显示月分不显示小时
			descTime = DateUtil.getFormatfromTimeStr(this.getStartDate(), DateUtil.FORMAT_LONG, DateUtil.FORMAT_YEAR) + " - "
					+ DateUtil.getFormatfromTimeStr(this.getEndDate(), DateUtil.FORMAT_LONG, DateUtil.FORMAT_YEAR);
		} else if (te.equals(TimeStyle.Y)) { // 转为仅显示月分不显示小时
			descTime = DateUtil.getFormatfromTimeStr(this.getStartDate(), DateUtil.FORMAT_LONG, DateUtil.FORMAT_YEAR) + " - "
					+ DateUtil.getFormatfromTimeStr(this.getEndDate(), DateUtil.FORMAT_LONG, DateUtil.FORMAT_YEAR);
		}
		return descTime;
	}

	public void setDescTime(String descTime) {
		this.descTime = descTime;
	}

	/**
	 * 填充数据
	 */
	@Override
	public MCDataModel modelWithData(Object obj) {
		MCTestModel testModel = null;
		JSONObject jsonObject = null;

		if (!TextUtils.isEmpty(obj.toString())) {
			testModel = new MCTestModel();
			try {
				jsonObject = new JSONObject(obj.toString());

				// 关联知识点和题目构成
				MCTestInfo info = new MCTestInfo();
				JSONArray jsonArray = jsonObject.optJSONArray("loreInfo");
				if (jsonArray != null) {
					info.desc = jsonArray.toString();
					info.statisticInfo = new HashMap<String, MCTestStatisticInfo>();
					if (!"null".equals(jsonObject.getString("statisticInfo"))) {
						JSONArray infoArray = jsonObject.getJSONArray("statisticInfo");

						for (int i = 0; i < infoArray.length(); i++) {
							JSONObject infoObject = infoArray.getJSONObject(i);
							MCTestStatisticInfo sinfo = new MCTestStatisticInfo();
							sinfo.count = infoObject.optInt("count");
							sinfo.score = infoObject.optInt("score");
							sinfo.type = infoObject.optString("type");
							if ("DANXUAN".equals(sinfo.type)) {
								info.statisticInfo.put("DANXUAN", sinfo);
							} else if ("DUOXUAN".equals(sinfo.type)) {
								info.statisticInfo.put("DUOXUAN", sinfo);
							} else if ("PANDUAN".equals(sinfo.type)) {
								info.statisticInfo.put("PANDUAN", sinfo);
							}
						}
					}
				}
				
				testModel.setTestInfo(info);
				// 自测属性
				testModel.setStartDate(jsonObject.optString("startDate"));
				testModel.setEndDate(jsonObject.optString("endDate"));
				testModel.setTitle(jsonObject.optString("title"));
				testModel.setType(jsonObject.optString("type"));
				testModel.setId(jsonObject.optString("id"));
				testModel.setQuestionCount(jsonObject.optInt("questionCount"));
				testModel.setHasRedoNum(jsonObject.optInt("hasRedoNum"));
				testModel.setRedo_num(jsonObject.optInt("redo_num"));
				testModel.setRedo_type(jsonObject.optInt("redo_type"));

				// ? 分数如何显示 1.已过期 (2015-02-28 23:59:59) 2.进行中 3.分数
				if (DateUtil.getTime(testModel.endDate) < System.currentTimeMillis()) {
					testModel.setFinalScore(TextConstant.TEST_END);
					testModel.setUnStart(false);
				} else if (DateUtil.getTime(testModel.startDate) > System.currentTimeMillis()) {
					testModel.setFinalScore(TextConstant.TEST_NOT_START);
					testModel.setUnStart(true);
				} else if (TextConstant.TEXT_NOT_HAVE.equals(jsonObject.optString("finalScore"))) {
					testModel.setFinalScore(TextConstant.TEST_START);
					testModel.setUnStart(false);
				} else {
					String retScore = jsonObject.optString("finalScore");
					if (!retScore.equals("")) {
						try {
							int score = (int) (retScore.contains(".") ? Double.valueOf(retScore) : Integer.valueOf(retScore));
							testModel.setFinalScore(String.valueOf(score));
						} catch (Exception e) {
							e.printStackTrace();
							testModel.setFinalScore("0");
						}
					}
				}

				String retLimitTime = jsonObject.optString("txtLimitTime");
				// 分钟 防止web返回带.的,不做判断直接崩
				if (!retLimitTime.equals("")) {
					try {
						int limitTime = (int) (retLimitTime.contains(".") ? Double.valueOf(retLimitTime) : Integer.valueOf(retLimitTime));
						if (limitTime > 0) {
							// 毫秒
							testModel.setTxtLimitTime(limitTime * 60 * 1000);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return testModel;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * 序列化到内存
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(txtLimitTime);
		dest.writeString(answer);
		dest.writeString(openCourseID);
		dest.writeString(descTime);
		dest.writeString(finalScore);
		dest.writeString(startDate);
		dest.writeString(endDate);
		dest.writeString(title);
		dest.writeString(type);
		dest.writeString(id);
		dest.writeInt(questionCount);
		dest.writeInt(hasRedoNum);
		dest.writeInt(redo_num);
		dest.writeInt(redo_type);
		dest.writeParcelable(testInfo, PARCELABLE_WRITE_RETURN_VALUE);
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
			return new MCTestModel(parcel);
		}
	};

}
