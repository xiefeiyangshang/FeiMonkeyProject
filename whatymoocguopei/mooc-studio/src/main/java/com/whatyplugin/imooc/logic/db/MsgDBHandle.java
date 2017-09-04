package com.whatyplugin.imooc.logic.db;


import android.content.ContentValues;
import android.content.Context;

import com.whatyplugin.base.define.MCBaseDefine.MCSendStatus;
import com.whatyplugin.imooc.logic.db.DBCommon.MsgColumns;
import com.whatyplugin.imooc.logic.db.DBCommon.UserColumns;
import com.whatyplugin.imooc.logic.model.MCMessageModel;
import com.whatyplugin.imooc.logic.model.MCUserModel;
import com.whatyplugin.imooc.logic.utils.Utils;

public class MsgDBHandle extends DBHandle {
    public MsgDBHandle(Context context) {
        super(context);
    }

    public void deleteMessage(int friendUid) {
        this.delete(MsgColumns.CONTENT_URI_MSG, "(receiveruid = " + friendUid + " or " + "senderuid" + " = " + friendUid + ")" + " and " + "sendstatus" + " = " + MCSendStatus.MC_SEND_STATUS_SUCCESS.value());
    }

    public void deleteMessage(String receiverUid, String senderUid, long serverTime, long createOn) {
        this.delete(MsgColumns.CONTENT_URI_MSG, "receiveruid = " + receiverUid + " and " + "senderuid" + " = " + senderUid + " and (" + "sendtime" + " = " + serverTime + " or " + "sendtime" + " = " + createOn + ")");
    }

    private void insertMessage(MCMessageModel message) {
        ContentValues content = new ContentValues();
        content.put("id", Integer.valueOf(message.getId()));
        content.put("receiveruid", Integer.valueOf(message.getReceiver().getId()));
        content.put("type", Integer.valueOf(message.getType().value()));
        content.put("sendtime", Long.valueOf(message.getServerTime().millisecondsSince1970()));
        content.put("content", message.getContent());
        content.put("senderuid", Integer.valueOf(message.getSender().getId()));
        if(message.getReadStatue() != null) {
            content.put("statue", Integer.valueOf(message.getReadStatue().value()));
        }

        if(message.getSendStatue() != null) {
            content.put("sendstatus", Integer.valueOf(message.getSendStatue().value()));
        }

        this.insert(MsgColumns.CONTENT_URI_MSG, content);
    }

    public void saveMessageModel(MCMessageModel message, String uid, boolean saveUser) {
        try {
            String receiverId = message.getReceiver().getId();
            String senderId = message.getSender().getId();
            MCUserModel userModel = message.getReceiver().getId().equals(uid) ? message.getSender() : message.getReceiver();
            this.deleteMessage(receiverId, senderId, message.getServerTime().millisecondsSince1970(), message.getCreatedOn().millisecondsSince1970());
            this.insertMessage(message);
            if(!saveUser) {
                return;
            }

            ContentValues content = new ContentValues();
            content.put("uid", Integer.valueOf(userModel.getId()));
            content.put("nickname", userModel.getNickname());
            content.put("pic", userModel.getImageUrl());
            if(Utils.checkUserExist(this.mContext, userModel.getId())) {
                this.update(UserColumns.CONTENT_URI_USER, "uid = " + userModel.getId(), content);
                return;
            }

            this.insert(UserColumns.CONTENT_URI_USER, content);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }
}

