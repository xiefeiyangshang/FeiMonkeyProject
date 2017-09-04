package com.whaty.imooc.logic.model;

import java.io.Serializable;

import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;

/**
 * 自己定义的bean的demo
 * 
 * @author 马彦君
 * 
 */
public class MCDemoModel extends MCDataModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;

	/**
	 * 将json数据封装成bean
	 */
	@Override
	public MCDemoModel modelWithData(Object data) {
		if (data != null && data.toString().length() > 0) {
			MCDemoModel model = new MCDemoModel();
			try {
				JSONObject courseInfo = new JSONObject(data.toString());
				model.setId(courseInfo.optString("id"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return model;
		}
		return null;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
