package com.whatyplugin.imooc.logic.model;


import java.io.Serializable;

import org.json.JSONObject;

import com.whatyplugin.base.define.MCBaseDefine.MCFromType;
import com.whatyplugin.base.define.MCBaseDefine.MCUserType;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.type.MCDate;
import com.whatyplugin.base.type.MCTime;

public class MCDiscussionModel extends MCDataModel implements Serializable {
    private MCFromType beFrom;
    private int chapter;
    private String content;
    private String courseName;
    private MCDate createOn;
    private int hasBestAnswer;
    private String imageUrl;
    private boolean isTop;
    private MCDate lastRepliedOn;
    private MCTime mediaProgress;
    private int replyNum;
    private int seq;
    private MCUserModel user;
    private String id;
    
    public MCDiscussionModel() {
        super();
    }

    public MCFromType getBeFrom() {
        return this.beFrom;
    }

    public int getChapter() {
        return this.chapter;
    }

    public String getContent() {
        return this.content;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public MCDate getCreateOn() {
        return this.createOn;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public boolean getIsTop() {
        return this.isTop;
    }

    public MCDate getLastRepliedOn() {
        return this.lastRepliedOn;
    }

    public MCTime getMediaProgress() {
        return this.mediaProgress;
    }

    public int getReplyNum() {
        return this.replyNum;
    }

    public int getSeq() {
        return this.seq;
    }

    public MCUserModel getUser() {
        return this.user;
    }

    public int isHasBestAnswer() {
        return this.hasBestAnswer;
    }

    public MCDiscussionModel modelWithData(Object data) {
        MCDiscussionModel model = null;
        String dataStr = data.toString();
        if(dataStr != null && dataStr.length() > 0) {
            model = new MCDiscussionModel();
            MCUserModel userModel = new MCUserModel();
            try {
                JSONObject dataJson = new JSONObject(dataStr);
                JSONObject userInfo = dataJson.getJSONObject("user");
                userModel.setId(userInfo.getString("uid"));
                userModel.setImageUrl(userInfo.getString("pic"));
                userModel.setNickname(userInfo.getString("nickname"));
                MCUserType userType = userInfo.getInt("is_v") == 1 ? MCUserType.MC_USER_TYPE_TEACHER : MCUserType.MC_USER_TYPE_NORMAL;
                userModel.setType(userType);
                model.setUser(userModel);
                JSONObject quesJson = dataJson.getJSONObject("ques");
                model.setId(quesJson.getString("id"));
                model.setContent(quesJson.getString("desc"));
                model.setReplyNum(quesJson.getInt("number"));
                if(quesJson.has("is_top")) {
                    int isTop = quesJson.getInt("is_top");
                    if(isTop == 1) {
                        model.setTop(true);
                    }
                    else if(isTop == 0) {
                        model.setTop(false);
                    }
                }

                model.setHasBestAnswer(quesJson.getInt("is_finished"));
                model.setImageUrl(quesJson.getString("pic"));
                model.setLastRepliedOn(MCDate.dateWithMilliseconds(quesJson.getLong("last_date")));
                model.setCreateOn(MCDate.dateWithMilliseconds(quesJson.getLong("create_date")));
                model.setMediaProgress(MCTime.timeWithMilliseconds(quesJson.getLong("last_time")));
                model.setCourseName(quesJson.getString("course_name"));
                model.setChapter(quesJson.getInt("chapter_num"));
                model.setSeq(quesJson.getInt("section_num"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        return model;
    }

    public void setBeFrom(MCFromType beFrom) {
        this.beFrom = beFrom;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCreateOn(MCDate createOn) {
        this.createOn = createOn;
    }

    public void setHasBestAnswer(int hasBestAnswer) {
        this.hasBestAnswer = hasBestAnswer;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLastRepliedOn(MCDate lastRepliedOn) {
        this.lastRepliedOn = lastRepliedOn;
    }

    public void setMediaProgress(MCTime mediaProgress) {
        this.mediaProgress = mediaProgress;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setTop(boolean isTop) {
        this.isTop = isTop;
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

