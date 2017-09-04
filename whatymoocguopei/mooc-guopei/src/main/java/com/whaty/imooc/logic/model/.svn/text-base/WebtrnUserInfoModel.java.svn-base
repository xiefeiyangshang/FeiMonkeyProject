package com.whaty.imooc.logic.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;

public class WebtrnUserInfoModel extends MCDataModel implements Serializable {

	private String sign;
	private String trueName;
	private String nickName;
	private String gender;
	private String avatar;
	private String techProject=""; // 学科
	private String techSegment=""; // 学段
	private String techProfession; // 老师所教职业 学科+学段

	public String getSign() {
		return sign;
	}

	public String getTechProfession() {
		return techProfession;
	}

	public void setTechProfession(String techProfession) {
		this.techProfession = techProfession;
	}

	public String getTechSegment() {
		return techSegment;
	}

	public void setTechSegment(String techSegment) {
		this.techSegment = techSegment;
	}

	public String getTechproject() {
		return techProject;
	}

	public void setTechproject(String techproject) {
		this.techProject = techproject;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public MCDataModel modelWithData(Object data) {
		String info = data.toString();
		if (info != null && info.length() > 0) {
			WebtrnUserInfoModel model = new WebtrnUserInfoModel();
			try {
				JSONObject jsonObject = new JSONObject(info);
				if (!jsonObject.isNull("sign")) {
					model.setSign(jsonObject.getString("sign"));
				}
				if (!jsonObject.isNull("trueName")) {
					model.setTrueName(jsonObject.getString("trueName"));
				}
				if (!jsonObject.isNull("nickName")) {
					model.setNickName(jsonObject.getString("nickName"));
				}
				if (!jsonObject.isNull("male")) {
					model.setGender(jsonObject.getString("male").equals("0") ? "女" : "男");
				}
				if (!jsonObject.isNull("avatar")) {
					model.setAvatar(jsonObject.getString("avatar"));
				}
				if (jsonObject.has("techProject")) {
					model.setTechproject(jsonObject.optString("techProject").equals("null")?"":jsonObject.optString("techProject"));
				}
				if (jsonObject.has("techSegment")) {
					model.setTechSegment(jsonObject.optString("techSegment").equals("null")?"  ":jsonObject.optString("techSegment"));
				}
				model.setTechProfession(model.getTechSegment() + model.getTechproject());

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return model;
		} else {
			return null;
		}

	}

	@Override
	public String getId() {
		
		return null;
	}

}
