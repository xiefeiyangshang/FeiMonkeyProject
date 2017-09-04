package com.whatyplugin.imooc.logic.model;


import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONObject;

import com.whatyplugin.base.define.MCBaseDefine.MCMessageType;
import com.whatyplugin.base.define.MCBaseDefine.MCReadStatus;
import com.whatyplugin.base.define.MCBaseDefine.MCSendStatus;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.type.MCDate;

public class MCMessageModel extends MCDataModel implements Serializable {
    private String content;
    private MCDate createdOn;
    private boolean isSystem;
    private MCReadStatus readStatue;
    private MCUserModel receiver;
    private MCSendStatus sendStatue;
    private MCUserModel sender;
    private MCDate serverTime;
    private MCMessageType type;
    private String id;
    
    public MCMessageModel() {
        super();
    }

    protected MCMessageType convertIntToType(int type) {
        MCMessageType msgType;
        if(type == 1) {
            msgType = MCMessageType.MC_MSG_TYPE_TEXT;
        }
        else if(type == 2) {
            msgType = MCMessageType.MC_MSG_TYPE_IMG;
        }
        else {
            if(type != 3 && type != 4) {
                if(type == 5) {
                    return MCMessageType.MC_MSG_TYPE_FRIENDS;
                }
                else if(type == 8) {
                    return MCMessageType.MC_MSG_TYPE_COURSEUPDATE;
                }
            }

            msgType = MCMessageType.MC_MSG_TYPE_TEXT;
        }

        return msgType;
    }

    public String getContent() {
        return this.content;
    }

    public MCDate getCreatedOn() {
        return this.createdOn;
    }

    public MCReadStatus getReadStatue() {
        return this.readStatue;
    }

    public MCUserModel getReceiver() {
        return this.receiver;
    }

    public MCSendStatus getSendStatue() {
        return this.sendStatue;
    }

    public MCUserModel getSender() {
        return this.sender;
    }

    public MCDate getServerTime() {
        return this.serverTime;
    }

    public MCMessageType getType() {
        return this.type;
    }

    public boolean isSystem() {
        return this.isSystem;
    }

    public MCMessageModel modelWithData(Object data) {
        MCMessageModel v3 = null;
        String v2 = data.toString();
        if(v2 != null && v2.length()>0) {
            v3 = new MCMessageModel();
            try {
                JSONObject v5 = new JSONArray(v2).getJSONObject(0);
                if(v5.has("message")) {
                    JSONObject v4 = v5.getJSONObject("message");
                    v3.setContent(v4.getString("content"));
                    v3.setCreatedOn(MCDate.dateWithMilliseconds(v4.getLong("sendtime")));
                    v3.setServerTime(MCDate.dateWithSeconds(v4.getLong("servertime")));
                    v3.setId(v4.getString("id"));
                    v3.setType(this.convertIntToType(v4.getInt("type")));
                    v3.setSendStatue(MCSendStatus.MC_SEND_STATUS_SUCCESS);
                }

                if(v5.has("receiver")) {
                    JSONObject v7 = v5.getJSONObject("receiver");
                    MCUserModel v6 = new MCUserModel();
                    v6.setId(v7.getString("uid"));
                    v3.setReceiver(v6);
                }

                if(!v5.has("sender")) {
                    return v3;
                }

                v3.setSender((MCUserModel) new MCUserModel().modelWithData(v5.getJSONObject("sender")));
            }
            catch(Exception v1) {
                v1.printStackTrace();
            }
        }
        else {
            Object v3_1 = null;
        }

        return v3;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedOn(MCDate createdOn) {
        this.createdOn = createdOn;
    }

    public void setReadStatue(MCReadStatus readStatue) {
        this.readStatue = readStatue;
    }

    public void setReceiver(MCUserModel receiver) {
        this.receiver = receiver;
    }

    public void setSendStatue(MCSendStatus sendStatue) {
        this.sendStatue = sendStatue;
    }

    public void setSender(MCUserModel sender) {
        this.sender = sender;
    }

    public void setServerTime(MCDate serverTime) {
        this.serverTime = serverTime;
    }

    public void setSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public void setType(MCMessageType type) {
        this.type = type;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}

