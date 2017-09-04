package com.whatyplugin.imooc.logic.model;


import java.io.Serializable;

import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;

public class MCJobModel extends MCDataModel implements Serializable {
    private String name;
    private String id;
    
    public MCJobModel() {
        super();
    }

    public String getName() {
        return this.name;
    }

    public MCJobModel modelWithData(Object data) {
    	MCJobModel v1_1 = null;
        String v2 = data.toString();
        if(v2 != null && v2.length() > 0) {
            MCJobModel v1 = new MCJobModel();
            try {
                JSONObject v3 = new JSONObject(v2);
                v1.setId(v3.getString("id"));
                v1.setName(v3.getString("job"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            v1_1 = null;
        }

        return v1_1;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}

