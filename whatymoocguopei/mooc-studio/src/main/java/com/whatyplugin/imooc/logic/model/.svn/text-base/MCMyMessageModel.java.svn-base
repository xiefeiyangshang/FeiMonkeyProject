package com.whatyplugin.imooc.logic.model;


import org.json.JSONObject;

import com.whatyplugin.base.define.MCBaseDefine.MCSendStatus;
import com.whatyplugin.base.type.MCDate;

public class MCMyMessageModel extends MCMessageModel {
    private int unread;

    public MCMyMessageModel() {
        super();
    }

    public int getUnread() {
        return this.unread;
    }

    public MCMyMessageModel modelWithData(Object data) {
    	MCMyMessageModel v2_1 = null;
        MCUserModel userModel;
        JSONObject v8;
        String v1 = data.toString();
        if(v1 != null && v1.length() > 0) {
            MCMyMessageModel v2 = new MCMyMessageModel();
            try {
                JSONObject v5 = new JSONObject(v1);
                if(v5.has("unread")) {
                    v2.setUnread(v5.getInt("unread"));
                }

                if(v5.has("sender")) {
                    v8 = new JSONObject(v5.getString("sender"));
                    userModel = new MCUserModel();
                    if(v8.has("uid")) {
                        userModel.setId(v8.getString("uid"));
                    }

                    if(v8.has("nickname")) {
                        userModel.setNickname(v8.getString("nickname"));
                    }

                    if(v8.has("pic")) {
                        userModel.setImageUrl(v8.getString("pic"));
                    }

                    v2.setSender(userModel);
                }

                if(v5.has("receiver")) {
                    v8 = new JSONObject(v5.getString("receiver"));
                    userModel = new MCUserModel();
                    if(v8.has("uid")) {
                        userModel.setId(v8.getString("uid"));
                    }

                    if(v8.has("nickname")) {
                        userModel.setNickname(v8.getString("nickname"));
                    }

                    if(v8.has("pic")) {
                        userModel.setImageUrl(v8.getString("pic"));
                    }

                    v2.setReceiver(userModel);
                }

                if(!v5.has("message")) {
                    return v2_1;
                }

                JSONObject v4 = new JSONObject(v5.getString("message"));
                if(v4.has("id")) {
                    v2.setId(v4.getString("id"));
                }

                if(v4.has("type")) {
                    v2.setType(this.convertIntToType(v4.getInt("type")));
                }

                if(v4.has("content")) {
                    v2.setContent(v4.getString("content"));
                }

                if(v4.has("sendtime")) {
                    v2.setCreatedOn(MCDate.dateWithMilliseconds(v4.getLong("sendtime")));
                }

                if(v4.has("servertime")) {
                    v2.setServerTime(MCDate.dateWithSeconds(v4.getLong("servertime")));
                }

                v2.setSendStatue(MCSendStatus.MC_SEND_STATUS_SUCCESS);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            v2_1 = null;
        }

        return v2_1;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }
}

