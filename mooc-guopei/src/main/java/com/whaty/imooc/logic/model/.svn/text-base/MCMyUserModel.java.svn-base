package com.whaty.imooc.logic.model;
import android.util.Log;

import com.whaty.imooc.ui.index.GPRequestUrl;
import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.SharedPrefsUtil;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCFullUserModel;
import com.whatyplugin.imooc.logic.model.MCJobModel;
import com.whatyplugin.imooc.logic.model.MCSiteModel;
import com.whatyplugin.imooc.logic.utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MCMyUserModel extends MCFullUserModel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String about;
    private String baseInfoLastUpdateTime;
    private String city;
    private String email;
    private int exist;
    private boolean is_friend;
    private String job;
    private List<MCJobModel> jobs;
    private String loginChannel;
    private String mark;
    private String pwdLastUpdateTime;
    private int updateFlag;
    private String learSpaceAddress;
    private String status;
    private String success;
    private String LearLoginType;
    private List<MCSiteModel> sites;

    public MCMyUserModel() {
        super();
        this.updateFlag = Contants.DEFAULT_UPDATE;
    }

    public MCMyUserModel modelWithData(Object data) {
        JSONObject jsonObj;
        MCMyUserModel mcMyUserModel = null;
        String dataString = data.toString();
        if (dataString != null && dataString.length() > 0) {
            mcMyUserModel = new MCMyUserModel();
            try {
                jsonObj = new JSONObject(dataString);
                if ((!jsonObj.has("has_update") || jsonObj.getInt("has_update") == 0) && (jsonObj.has("has_update"))) {
                    mcMyUserModel.setUpdateFlag(Contants.NO_UPDATE);
                }

                mcMyUserModel.setId(jsonObj.getString("userID"));
                if (!jsonObj.isNull("nickName"))
                    mcMyUserModel.setNickname(jsonObj.getString("nickName"));
                mcMyUserModel.setLoginType(jsonObj.getString("loginType"));
                mcMyUserModel.setLoginToken(jsonObj.getString("loginToken"));
                if (!jsonObj.isNull("headphotoURL"))
                    mcMyUserModel.setImageUrl(jsonObj.getString("headphotoURL"));

                if (!jsonObj.isNull("learspaceAddress")) {
                    String learspaceTemp = jsonObj.getString("learspaceAddress");
                    if (learspaceTemp != null && !"".equals(learspaceTemp)) {
                        //去掉站点返回的URL 最后的/
                        mcMyUserModel.setLearSpaceAddress(learspaceTemp.replaceFirst("/$", ""));
                    }

                }
                if (!jsonObj.isNull("success")) {
                    String successTemp = jsonObj.getString("success");
                    if (successTemp != null && !"".equals(successTemp)) {
                        mcMyUserModel.setSuccess(successTemp);
                    }
                }
                if (!jsonObj.isNull("status")) {
                    String statusTemp = jsonObj.getString("status");
                    if (statusTemp != null && !"".equals(statusTemp)) {
                        mcMyUserModel.setStatus(statusTemp);
                    }
                }
                if (!jsonObj.isNull("LearLoginType")) {
                    String LearLoginTypeTemp = jsonObj.getString("LearLoginType");
                    if (LearLoginTypeTemp != null && !"".equals(LearLoginTypeTemp)) {
                        mcMyUserModel.setLearLoginType(LearLoginTypeTemp);
                    }
                }
                if (!jsonObj.isNull("JGInfo")) {
                    sites = new ArrayList<MCSiteModel>();
                    JSONArray sites = jsonObj.getJSONArray("JGInfo");
                    for (int i = 0; i < sites.length(); i++) {
                        MCSiteModel siteModel = new MCSiteModel();
                        JSONObject site = sites.getJSONObject(i);

                        String siteWebDomain = site.getString("JGURL");
                        String siteCode = site.getString("JGID");
                        String siteName = site.getString("JGTitle");
                        Log.e("=======", "==================  " + siteWebDomain);
                        String host = String.format("http://%s", siteWebDomain);
//                        MCLog.e("=====   ",GPRequestUrl.getInstance().COURSE_LIST);

//                        GPInitInformation.WEBTRN_TEST_LOCAL_URL=GPRequestUrl.CODEURL;

                        SharedPrefsUtil.putVlaue(GPContants.CODEURL, host);
                        SharedPrefsUtil.putVlaue(GPContants.CODESITE, siteCode);
                        Const.SITECODE = siteCode;
                        GPContants.CODEURL = host;
                        GPRequestUrl.getInstance().updateUrl(host);

                        siteModel.setCode(siteCode);
                        siteModel.setWebDoMain(siteWebDomain);
                        siteModel.setName(siteName);
                        this.sites.add(siteModel);
                        mcMyUserModel.setSites(this.sites);


                    }
                }
            } catch (Exception v0) {
                return mcMyUserModel;
            }

        }

        return mcMyUserModel;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getBaseInfoLastUpdateTime() {
        return baseInfoLastUpdateTime;
    }

    public void setBaseInfoLastUpdateTime(String baseInfoLastUpdateTime) {
        this.baseInfoLastUpdateTime = baseInfoLastUpdateTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getExist() {
        return exist;
    }

    public void setExist(int exist) {
        this.exist = exist;
    }

    public boolean isIs_friend() {
        return is_friend;
    }

    public void setIs_friend(boolean is_friend) {
        this.is_friend = is_friend;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<MCJobModel> getJobs() {
        return jobs;
    }

    public String getLoginChannel() {
        return loginChannel;
    }

    public void setLoginChannel(String loginChannel) {
        this.loginChannel = loginChannel;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPwdLastUpdateTime() {
        return pwdLastUpdateTime;
    }

    public void setPwdLastUpdateTime(String pwdLastUpdateTime) {
        this.pwdLastUpdateTime = pwdLastUpdateTime;
    }

    public int getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(int updateFlag) {
        this.updateFlag = updateFlag;
    }

    public String getLearSpaceAddress() {
        return learSpaceAddress;
    }

    public void setLearSpaceAddress(String learSpaceAddress) {
        this.learSpaceAddress = learSpaceAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getLearLoginType() {
        return LearLoginType;
    }

    public void setLearLoginType(String learLoginType) {
        LearLoginType = learLoginType;
    }

    public List<MCSiteModel> getSites() {
        return sites;
    }

    public void setSites(List<MCSiteModel> sites) {
        this.sites = sites;
    }


}
