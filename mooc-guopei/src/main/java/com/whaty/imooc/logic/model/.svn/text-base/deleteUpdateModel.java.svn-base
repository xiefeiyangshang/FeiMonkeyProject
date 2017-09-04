package com.whaty.imooc.logic.model;

import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;

/**
 * 返回数据： {"msg":"删除成功","success":"1"}
 * 
 * @author whaty
 * 
 */

public class deleteUpdateModel extends MCDataModel {
	private String msg;
	private String success;
	private boolean isSuccess;

	@Override
	public MCDataModel modelWithData(Object arg1) {
		deleteUpdateModel model = new deleteUpdateModel();
		try {
			JSONObject jsonObject = new JSONObject(arg1.toString());
			model.setMsg(jsonObject.optString("msg"));
			model.setSuccess(jsonObject.optString("success"));
			model.setSuccess(jsonObject.optBoolean("success"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
