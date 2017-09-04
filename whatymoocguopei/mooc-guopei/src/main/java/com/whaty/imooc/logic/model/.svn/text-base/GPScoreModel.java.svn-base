package com.whaty.imooc.logic.model;

import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;

public class GPScoreModel extends MCDataModel {
	private String Score;

	@Override
	public MCDataModel modelWithData(Object arg1) {
		JSONObject jsonObject = (JSONObject) arg1;

		GPScoreModel scoreModel = new GPScoreModel();
		scoreModel.setScore(jsonObject.optString("score"));
		return scoreModel;
	}

	@Override
	public String getId() {

		return null;
	}

	// -1 代表没有成绩
	public Integer getScore() {
//		return -1;
		return "-1".equals(Score) ? -1 : Integer.valueOf(Score);
	}

	public void setScore(String score) {
		Score = score;
	}

}
