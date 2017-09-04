package com.whatyplugin.imooc.logic.model;

import com.whatyplugin.imooc.logic.contants.Contants;

import org.json.JSONObject;

import java.util.List;

public class MCFullUserModel extends MCUserModel {
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

	
	private MCFullUserModel fullUserModel;
	private MCSiteModel siteModel;//登录用户所属站点

	public MCFullUserModel getInit() {
		return fullUserModel == null ? new MCFullUserModel() : fullUserModel;
	}

	public MCFullUserModel() {
		super();
		this.updateFlag = Contants.DEFAULT_UPDATE;
	}


	public String getAbout() {
		return this.about;
	}

	public String getBaseInfoLastUpdateTime() {
		return this.baseInfoLastUpdateTime;
	}

	public String getCity() {
		return this.city;
	}

	public String getEmail() {
		return this.email;
	}



	public void setJobs(List<MCJobModel> jobs) {
		this.jobs = jobs;
	}

	public int getExist() {
		return this.exist;
	}

	public boolean getIs_friend() {
		return this.is_friend;
	}

	public String getJob() {
		return this.job;
	}

	public List<MCJobModel> getJobs() {
		return this.jobs;
	}

	public String getLoginChannel() {
		return this.loginChannel;
	}

	public String getMark() {
		return this.mark;
	}

	public String getPwdLastUpdateTime() {
		return this.pwdLastUpdateTime;
	}

	public int getUpdateFlag() {
		return this.updateFlag;
	}

	public MCFullUserModel modelWithData(Object data) {

		MCFullUserModel fullUserModel = null;
		String dataString = data.toString();
		if (dataString != null && dataString.length() > 0) {
			fullUserModel = getInit();
			try {
				JSONObject jsonObj = new JSONObject(dataString);
				if ((!jsonObj.has("has_update") || jsonObj.getInt("has_update") == 0) && (jsonObj.has("has_update"))) {
					fullUserModel.setUpdateFlag(Contants.NO_UPDATE);
				}
				if (!jsonObj.isNull("userID"))
					fullUserModel.setId(jsonObj.getString("userID"));
				if (!jsonObj.isNull("nickName"))
					fullUserModel.setNickname(jsonObj.getString("nickName"));
				fullUserModel.setLoginType(jsonObj.optString("loginType"));
				fullUserModel.setLoginToken(jsonObj.optString("loginToken"));
				if (!jsonObj.isNull("headphotoURL")){//headphotoURL
					fullUserModel.setImageUrl(jsonObj.getString("headphotoURL"));}

			
			} catch (Exception e) {
				e.printStackTrace();
				return fullUserModel;
			}

		}

		return fullUserModel;
	}


	public void setAbout(String about) {
		this.about = about;
	}

	public void setBaseInfoLastUpdateTime(String baseInfoLastUpdateTime) {
		this.baseInfoLastUpdateTime = baseInfoLastUpdateTime;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setExist(int exist) {
		this.exist = exist;
	}

	public void setIs_friend(boolean is_friend) {
		this.is_friend = is_friend;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public void setLoginChannel(String loginChannel) {
		this.loginChannel = loginChannel;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public void setPwdLastUpdateTime(String pwdLastUpdateTime) {
		this.pwdLastUpdateTime = pwdLastUpdateTime;
	}

	public void setUpdateFlag(int updateFlag) {
		this.updateFlag = updateFlag;
	}

	

	public MCFullUserModel getFullUserModel() {
		return fullUserModel;
	}

	public void setFullUserModel(MCFullUserModel fullUserModel) {
		this.fullUserModel = fullUserModel;
	}

	public MCSiteModel getSiteModel() {
		return siteModel;
	}

	public void setSiteModel(MCSiteModel siteModel) {
		this.siteModel = siteModel;
	}


	
	
}
