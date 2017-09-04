package com.whaty.imooc.logic.model;

import java.io.Serializable;

import org.json.JSONObject;

import com.whaty.imooc.utile.GPStringUtile;
import com.whatyplugin.base.model.MCDataModel;

public class GPAssessmentModel extends MCDataModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String command;
	private String index;

	@Override
	public MCDataModel modelWithData(Object arg1) {
		GPAssessmentModel Model = new GPAssessmentModel();
		try {
			JSONObject jsonObject = new JSONObject(arg1.toString());
			Model.setName(jsonObject.optString("name"));
			Model.setCommand(jsonObject.optString("command").replaceAll("，", ","));
			GPStringUtile.KAO_HE_SHULIANG++;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Model;
	}

	@Override
	public String getId() {

		return null;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = String.format("%d. %s", GPStringUtile.KAO_HE_SHULIANG, name);
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setId(String id) {
		this.id = id;
	}

}
