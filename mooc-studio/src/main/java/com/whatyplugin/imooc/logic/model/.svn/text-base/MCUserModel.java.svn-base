package com.whatyplugin.imooc.logic.model;


import java.io.Serializable;

import org.json.JSONObject;

import com.whatyplugin.base.define.MCBaseDefine.MCGender;
import com.whatyplugin.base.define.MCBaseDefine.MCUserType;
import com.whatyplugin.base.model.MCDataModel;

public class MCUserModel extends MCDataModel implements Serializable {
    private int companyId;
    private String desc;
    private MCGender gender;
    /**
     * 用户ID
     */
    private String id;
    
    private String imageUrl;	//用户头像
    private String nickname;	//用户昵称
    private String loginType;
    private String emile;
    private String sex;
    private String trueName;
    
    private String mobile;
    private String qq;
    
    public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmile() {
		return emile;
	}

	public void setEmile(String emile) {
		this.emile = emile;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	private String loginToken;
    private MCUserType type;

    public MCUserModel() {
        super();
    }

    MCGender convertIntToGender(int sex) {
        MCGender gender;
        if(sex == 0) {
            gender = MCGender.MC_GENDER_UNKNOWN;
        }
        else if(sex == 1) {
            gender = MCGender.MC_GENDER_MALE;
        }
        else if(sex == 2) {
            gender = MCGender.MC_GENDER_FEMALE;
        }
        else if(sex == 3) {
            gender = MCGender.MC_GENDER_UNKNOWN;
        }
        else {
            gender = MCGender.MC_GENDER_UNKNOWN;
        }

        return gender;
    }

    MCUserType convertIntToType(int is_v) {
        MCUserType userType;
        if(is_v == 0) {
            userType = MCUserType.MC_USER_TYPE_NORMAL;
        }
        else if(is_v == 1) {
            userType = MCUserType.MC_USER_TYPE_TEACHER;
        }
        else {
            userType = MCUserType.MC_USER_TYPE_NORMAL;
        }

        return userType;
    }

    public int getCompanyId() {
        return this.companyId;
    }

    public String getDesc() {
        return this.desc;
    }

    public MCGender getGender() {
        return this.gender;
    }

    public String getId() {
        return this.id;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getNickname() {
        return this.nickname;
    }

    public MCUserType getType() {
        return this.type;
    }

    public MCUserModel modelWithData(Object data) {
        MCUserModel model = null;
        String info = data.toString();
        if(info != null && info.length()>0) {
            model = new MCUserModel();
            try {
                JSONObject jsonObj = new JSONObject(info);
                if(jsonObj.has("uId")) {
                    model.setId(jsonObj.getString("uId"));
                }
                if(jsonObj.has("trueName")){
                	 model.setTrueName(jsonObj.getString("trueName"));
                }
                if(jsonObj.has("mobile")){
                	model.setMobile(jsonObj.getString("mobile").equals("null")?"":jsonObj.getString("mobile"));
                }
                if(jsonObj.has("qq")){
                	model.setQq(jsonObj.getString("qq"));
                }

                if(jsonObj.has("nickName")) {
                    model.setNickname(jsonObj.getString("nickName").trim().equals("null")?"":jsonObj.getString("nickName"));
                }
                if(jsonObj.has("email")){
                	model.setEmile(jsonObj.getString("email").trim().equals("null")?"未绑定邮箱":jsonObj.getString("email"));
                }
				model.setImageUrl(jsonObj.optString("photo"));
                if(jsonObj.has("company_id")) {
                    model.setCompanyId(jsonObj.getInt("company_id"));
                }

                if(jsonObj.has("gender")) {
                    model.setSex(jsonObj.getString("gender").trim().equals("null")?"男":jsonObj.getString("gender"));
                }

                if(jsonObj.has("is_v")) {
                    model.setType(this.convertIntToType(jsonObj.getInt("is_v")));
                    return model;
                }

                model.setType(MCUserType.MC_USER_TYPE_NORMAL);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
       
        return model;
    }

    @Override
	public String toString() {
		return "MCUserModel [companyId=" + companyId + ", desc=" + desc
				+ ", gender=" + gender + ", id=" + id + ", imageUrl="
				+ imageUrl + ", nickname=" + nickname + ", loginType="
				+ loginType + ", emile=" + emile + ", sex=" + sex
				+ ", loginToken=" + loginToken + ", type=" + type + "]";
	}

	public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setGender(MCGender gender) {
        this.gender = gender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setType(MCUserType type) {
        this.type = type;
    }
}

