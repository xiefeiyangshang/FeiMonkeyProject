package com.whatyplugin.imooc.logic.model;


import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;

public class MCResultModel extends MCDataModel {
    private int result;
    private String id;
    public MCResultModel() {
        super();
    }

    public int getResult() {
        return this.result;
    }

    public MCResultModel modelWithData(Object data) {
        MCResultModel v3 = null;
        String v1 = data.toString();
        if(v1 != null && v1.length()>0) {
            v3 = new MCResultModel();
            try {
                v3.setResult(new JSONObject(v1).getInt("result"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Object v3_1 = null;
        }

        return v3;
    }

    public void setResult(int result) {
        this.result = result;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

