package com.whatyplugin.imooc.logic.model;


import com.whatyplugin.base.model.MCDataModel;

public class MCOfflineReplayModel extends MCDataModel {
	private String id;
	
    public MCOfflineReplayModel() {
        super();
    }

    public MCOfflineReplayModel modelWithData(Object data) {
        return null;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}

