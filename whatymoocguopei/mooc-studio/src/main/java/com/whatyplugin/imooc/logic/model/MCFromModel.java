package com.whatyplugin.imooc.logic.model;


import com.whatyplugin.base.define.MCBaseDefine.MCFromType;
import com.whatyplugin.base.model.MCDataModel;

public class MCFromModel extends MCDataModel {
    private String name;
    private MCFromType type;
    private String id;
    public MCFromModel() {
        super();
    }

    public MCFromModel modelWithData(Object data) {
        return null;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}

