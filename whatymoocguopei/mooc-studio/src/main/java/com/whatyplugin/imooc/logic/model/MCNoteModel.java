package com.whatyplugin.imooc.logic.model;


import java.io.Serializable;

import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.type.MCDate;
import com.whatyplugin.base.type.MCTime;

public class MCNoteModel extends MCDataModel implements Serializable {
    private MCFromModel beFrom;
    private int chapter;
    private int collectedNum;
    private String content;
    private String courseName;
    private MCDate createOn;
    private String id;
    private String imageUrl;
    private boolean isCollected;
    private boolean isPraised;
    private MCTime mediaProgress;
    private int praisedNum;
    private int seq;
    private MCUserModel user;
    private static final String TAG = MCNoteModel.class.getSimpleName();
	
    public MCNoteModel() {
        super();
    }

    public MCFromModel getBeFrom() {
        return this.beFrom;
    }

    public int getChapter() {
        return this.chapter;
    }

    public int getCollectedNum() {
        return this.collectedNum;
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

    public MCTime getMediaProgress() {
        return this.mediaProgress;
    }

    public int getPraisedNum() {
        return this.praisedNum;
    }

    public int getSeq() {
        return this.seq;
    }

    public MCUserModel getUser() {
        return this.user;
    }

    public boolean isCollected() {
        return this.isCollected;
    }

    public boolean isPraised() {
        return this.isPraised;
    }

    public MCNoteModel modelWithData(Object data) {
        if(data != null && data.toString().length() > 0) {
            MCNoteModel model = new MCNoteModel();
            try {
                JSONObject v8 = new JSONObject(data.toString());
                if(v8.has("id")){
                	model.setId(v8.getString("id"));
                }
              
                return model;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setBeFrom(MCFromModel beFrom) {
        this.beFrom = beFrom;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public void setCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    public void setCollectedNum(int collectedNum) {
        this.collectedNum = collectedNum;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMediaProgress(MCTime mediaProgress) {
        this.mediaProgress = mediaProgress;
    }

    public void setPraised(boolean isPraised) {
        this.isPraised = isPraised;
    }

    public void setPraisedNum(int praisedNum) {
        this.praisedNum = praisedNum;
    }

    public void setSeq(int seq) {
        this.seq = seq;
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

