package com.whatyplugin.imooc.logic.model;


import java.io.Serializable;

import org.json.JSONObject;

import com.whatyplugin.base.define.MCBaseDefine.MCUserType;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.type.MCDate;

public class MCDiscussionReplyModel extends MCDataModel implements Serializable {
    private String content;
    private MCDate createdOn;
    private int isBestAnswered;
    private boolean isSupported;
    private int supportedNum;
    private MCUserModel user;
    private String id;
    
    public MCDiscussionReplyModel() {
        super();
    }

    public String getContent() {
        return this.content;
    }

    public MCDate getCreatedOn() {
        return this.createdOn;
    }

    public boolean getIsSupported() {
        return this.isSupported;
    }

    public int getSupportedNum() {
        return this.supportedNum;
    }

    public MCUserModel getUser() {
        return this.user;
    }

    public int isBestAnswered() {
        return this.isBestAnswered;
    }

    public MCDiscussionReplyModel modelWithData(Object data) {
    	MCDiscussionReplyModel v4_1 = null;
        String v2 = data.toString();
        if(v2 != null && v2.length() > 0) {
            MCDiscussionReplyModel v4 = new MCDiscussionReplyModel();
            MCUserModel userModel = new MCUserModel();
            try {
                JSONObject v3 = new JSONObject(v2);
                JSONObject v7 = v3.getJSONObject("user");
                userModel.setId(v7.getString("uid"));
                userModel.setImageUrl(v7.getString("pic"));
                userModel.setNickname(v7.getString("nickname"));
                MCUserType v8 = v7.getInt("is_v") == 1 ? MCUserType.MC_USER_TYPE_TEACHER : MCUserType.MC_USER_TYPE_NORMAL;
                userModel.setType(v8);
                v4.setUser(userModel);
                JSONObject v5 = v3.getJSONObject("reply");
                v4.setId(v5.getString("id"));
                v4.setContent(v5.getString("desc"));
                v4.setCreatedOn(MCDate.dateWithMilliseconds(v5.getLong("time")));
                v4.setSupportedNum(v5.getInt("support_num"));
                int v1 = v5.getInt("is_support");
                if(v1 == 1) {
                    v4.setIsSupported(true);
                    return v4_1;
                }

                if(v1 != 0) {
                    return v4_1;
                }

                v4.setIsSupported(false);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        return v4_1;
    }

    public void setBestAnswered(int isBestAnswered) {
        this.isBestAnswered = isBestAnswered;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedOn(MCDate createdOn) {
        this.createdOn = createdOn;
    }

    public void setIsSupported(boolean isSupported) {
        this.isSupported = isSupported;
    }

    public void setSupportedNum(int supportedNum) {
        this.supportedNum = supportedNum;
    }

    public void setUser(MCUserModel user) {
        this.user = user;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}

