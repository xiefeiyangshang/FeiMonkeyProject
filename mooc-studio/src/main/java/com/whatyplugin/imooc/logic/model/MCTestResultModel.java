package com.whatyplugin.imooc.logic.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.whatyplugin.base.model.MCDataModel;

public class MCTestResultModel extends MCDataModel implements Parcelable {
	private String id;
	/**
	 * 正确选项
	 */
	private String correctOption;
	/**
	 * 是否正确：0不正确、1正确
	 */
	private int correctness;
	/**
	 * 题目序号
	 */
	private int questionIndex;
	/**
	 * 答案解析
	 */
	private String solution;
	/**
	 * 用户选项
	 */
	private String userOption;
	
	public String getCorrectOption() {
		return correctOption;
	}

	public void setCorrectOption(String correctOption) {
		this.correctOption = correctOption;
	}

	public int getCorrectness() {
		return correctness;
	}

	public void setCorrectness(int i) {
		this.correctness = i;
	}

	public int getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(int i) {
		this.questionIndex = i;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getUserOption() {
		return userOption;
	}

	public void setUserOption(String userOption) {
		this.userOption = userOption;
	}

	public static Parcelable.Creator<MCTestResultModel> getCreator() {
		return CREATOR;
	}

	public MCTestResultModel(Parcel parcel) {
		correctOption = parcel.readString();
		correctness = parcel.readInt();
		questionIndex = parcel.readInt();
		solution = parcel.readString();
		userOption = parcel.readString();
	}

	public MCTestResultModel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(correctOption);
		dest.writeInt(correctness);
		dest.writeInt(questionIndex);
		dest.writeString(solution);
		dest.writeString(userOption);
	}
	
	public static final Parcelable.Creator<MCTestResultModel> CREATOR = new Creator<MCTestResultModel>() {
		
		@Override
		public MCTestResultModel[] newArray(int size) {
			return new MCTestResultModel[size];
		}
		
		@Override
		public MCTestResultModel createFromParcel(Parcel parcel) {
			return new MCTestResultModel(parcel);
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public MCDataModel modelWithData(Object obj) {
		MCTestResultModel resultModel = null;
		if (!TextUtils.isEmpty(obj.toString())) {
			 resultModel = new MCTestResultModel();
			try {
				JSONObject	jsonObject = new JSONObject(obj.toString());
				resultModel.setCorrectness(jsonObject.optInt("correctness"));
				resultModel.setCorrectOption(jsonObject.optString("correctOption"));
				resultModel.setSolution(jsonObject.optString("solution"));
				resultModel.setUserOption(jsonObject.optString("userOption"));
				resultModel.setQuestionIndex(jsonObject.optInt("questionIndex"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}		
		return resultModel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
