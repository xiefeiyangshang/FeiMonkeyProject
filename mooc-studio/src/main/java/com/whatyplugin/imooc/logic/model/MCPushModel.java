package com.whatyplugin.imooc.logic.model;


import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONObject;

import com.whatyplugin.base.define.MCBaseDefine.MCPushType;
import com.whatyplugin.base.model.MCDataModel;

public class MCPushModel extends MCDataModel implements Serializable {
    private String content;
    private MCUserModel sender;
    private MCPushType type;
    private String id;
    
    public MCPushModel() {
        super();
    }

    protected MCPushType convertIntToType(int type) {
        MCPushType pushType;
        if(type == 0) {
            pushType = MCPushType.MC_PUSH_TYPE_SYSTEM;
        }
        else if(type == 1) {
            pushType = MCPushType.MC_PUSH_TYPE_FRIENDMSG;
        }
        else if(type == 2) {
            pushType = MCPushType.MC_PUSH_TYPE_NEWUSERREGIST;
        }
        else if(type == 3) {
            pushType = MCPushType.MC_PUSH_TYPE_FRIENDREQUEST;
        }
        else if(type == 4) {
            pushType = MCPushType.MC_PUSH_TYPE_COURSEUPDATE;
        }
        else {
            pushType = MCPushType.MC_PUSH_TYPE_SYSTEM;
        }

        return pushType;
    }

    public String getContent() {
        return this.content;
    }

    public MCUserModel getSender() {
        return this.sender;
    }

    public MCPushType getType() {
        return this.type;
    }

    public MCPushModel modelWithData(Object data) {
        MCPushModel v4 = null;
        String v3 = data.toString();
        if(v3 != null && v3.length() > 0) {
            v4 = new MCPushModel();
            try {
                JSONObject v5 = new JSONArray(v3).getJSONObject(0);
                if(v5.has("kind")) {
                    v5.getString("kind");
                }

                if(!v5.has("data")) {
                    return v4;
                }

                JSONObject v1 = v5.getJSONObject("data");
                v1.getString("content");
                v1.getString("type");
                MCUserModel v6 = new MCUserModel();
                v6.setId(v1.getString("uid"));
                v4.setSender(v6);
            }
            catch(Exception v2) {
                v2.printStackTrace();
            }
        }
        else {
            Object v4_1 = null;
        }

        return v4;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(MCUserModel sender) {
        this.sender = sender;
    }

    public void setType(MCPushType type) {
        this.type = type;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}

