package com.whaty.imooc.logic.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;

public class WebtrnInfoModel extends MCDataModel implements Serializable {

	private String id;
	private int success;
	private String info;

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public MCDataModel modelWithData(Object data) {
		String info = data.toString();
		if (info != null && info.length() > 0) {
			WebtrnInfoModel model = new WebtrnInfoModel();
			try {
				JSONObject jsonObject = new JSONObject(info);
				if (!jsonObject.isNull("success")) {
					model.setSuccess(jsonObject.getInt("success"));
				}
				if (!jsonObject.isNull("info")) {
					model.setInfo(jsonObject.getString("info"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return model;
		} else {
			return null;
		}

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
