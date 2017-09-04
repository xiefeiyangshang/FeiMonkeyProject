package com.whatyplugin.imooc.logic.model;


import java.util.Date;

import com.whatyplugin.imooc.logic.offlinereport.OfflineReportType;
import com.whatyplugin.imooc.logic.utils.Utils;

public class MCLearnOfflineModel {
    private MCBaseOfflineModel baseModel;
    private int duration;
    private String sectionId;
    private int stayTime;
    private int uid;

    public MCLearnOfflineModel() {
        super();
    }

    public MCLearnOfflineModel(MCBaseOfflineModel baseModel) {
        super();
        this.baseModel = baseModel;
    }

    public MCBaseOfflineModel buildBaseModel() {
        MCBaseOfflineModel OModel = new MCBaseOfflineModel();
        OModel.setSectionId(this.sectionId);
        OModel.setUid(this.uid);
        OModel.setValue1(new StringBuilder(String.valueOf(this.duration)).toString());
        OModel.setValue2(new StringBuilder(String.valueOf(this.stayTime)).toString());
        OModel.setInsertTime(new Date().getTime());
        OModel.setReportType(OfflineReportType.MC_OFFLINE_REPORT_LEARN.value());
        return OModel;
    }

    public MCBaseOfflineModel getBaseModel() {
        return this.baseModel;
    }

    public int getDuration() {
        int duration = this.baseModel != null ? Utils.stringToInt(this.baseModel.getValue1()) : 0;
        return duration;
    }

    public long getInsertTime() {
        long insertTime = this.baseModel != null ? this.baseModel.getInsertTime() : 0;
        return insertTime;
    }

    public String getSectionId() {
        String sectionId = this.baseModel != null ? this.baseModel.getSectionId() : "";
        return sectionId;
    }

    public int getStayTime() {
        int stayTime = this.baseModel != null ? Utils.stringToInt(this.baseModel.getValue2()) : 0;
        return stayTime;
    }

    public int getUid() {
        int uid = this.baseModel != null ? this.baseModel.getUid() : 0;
        return uid;
    }

    public void setBaseModel(MCBaseOfflineModel baseModel) {
        this.baseModel = baseModel;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public void setStayTime(int stayTime) {
        this.stayTime = stayTime;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}

