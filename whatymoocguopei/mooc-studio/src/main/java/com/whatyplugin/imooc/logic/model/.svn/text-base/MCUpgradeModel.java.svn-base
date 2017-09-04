package com.whatyplugin.imooc.logic.model;


import org.json.JSONObject;

import com.whatyplugin.base.define.MCBaseDefine.MCUpgradeType;
import com.whatyplugin.base.model.MCDataModel;

public class MCUpgradeModel extends MCDataModel {
    private String content;
    private MCUpgradeType type;
    private String url;
    private String appVersion;
    private int enforeUpdate;
    private int isImportant;
    private String appSize;
    private String id;
    
    public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public int getEnforeUpdate() {
		return enforeUpdate;
	}

	public void setEnforeUpdate(int enforeUpdate) {
		this.enforeUpdate = enforeUpdate;
	}

	public int getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(int isImportant) {
		this.isImportant = isImportant;
	}

	public String getAppSize() {
		return appSize;
	}

	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}

	public MCUpgradeModel() {
        super();
    }

    MCUpgradeType convertIntToType(int type) {
        MCUpgradeType upgradeType;
        if(type == 0) {
            upgradeType = MCUpgradeType.MC_UPGRADE_TYPE_NO_UPGRADE;
        }
        else if(type == 1) {
            upgradeType = MCUpgradeType.MC_UPGRADE_TYPE_NEED_UPGRADE;
        }
        else if(type == 2) {
            upgradeType = MCUpgradeType.MC_UPGRADE_TYPE_MUST_UPGRADE;
        }
        else {
            upgradeType = MCUpgradeType.MC_UPGRADE_TYPE_NO_UPGRADE;
        }

        return upgradeType;
    }

    public String getContent() {
        return this.content;
    }

    public MCUpgradeType getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public MCUpgradeModel modelWithData(Object data) {
        MCUpgradeModel upgradeModel = null;
        String jsonData = data.toString();
        if(jsonData != null && jsonData.length() > 0) {
            upgradeModel = new MCUpgradeModel();
            try {
                JSONObject obj = new JSONObject(jsonData);
                upgradeModel.setContent(obj.optString("desc"));
                upgradeModel.setUrl(obj.optString("apkUrl"));
                upgradeModel.setAppVersion(obj.optString("curAppVersion"));
                upgradeModel.setEnforeUpdate(obj.optInt("enforeUpdate"));
                upgradeModel.setIsImportant(obj.optInt("isImportant"));
                upgradeModel.setAppSize(obj.optString("appSize"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        return upgradeModel;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(MCUpgradeType type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}

