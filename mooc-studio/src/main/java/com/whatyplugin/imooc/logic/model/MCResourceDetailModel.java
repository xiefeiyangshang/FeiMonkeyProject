package com.whatyplugin.imooc.logic.model;

import java.io.Serializable;

import org.json.JSONObject;

import android.text.TextUtils;

import com.whatyplugin.base.define.MCBaseDefine.MCResourcesType;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.imooc.logic.utils.Const;

public class MCResourceDetailModel extends MCDataModel implements Serializable {

	private String itemId;
	private String name;
	private String resourceId;
	private String resourceLink;
	private int size;
	private MCResourcesType resourcesType;
	private String id;
	
	//这里的数字和背景图的level数值保持一致
	private void convertResTypeFromUrl(MCResourceDetailModel detailModel) {
		String url = detailModel.getResourceLink();
		if(TextUtils.isEmpty(url)){
			detailModel.setResourcesType(MCResourcesType.MC_OTHER_TYPE);
			return;
		}
		url = url.toLowerCase();
		if (url.endsWith(".ppt") || url.endsWith(".pptx")) {
			detailModel.setResourcesType(MCResourcesType.MC_PPT_TYPE);
		} else if (url.endsWith(".txt")) {
			detailModel.setResourcesType(MCResourcesType.MC_TXT_TYPE);
		} else if (url.endsWith(".pdf")) {
			detailModel.setResourcesType(MCResourcesType.MC_PDF_TYPE);
		} else if (url.endsWith(".doc") || url.endsWith(".docx")) {
			detailModel.setResourcesType(MCResourcesType.MC_DOC_TYPE);
		} else if (url.endsWith(".mp3") || url.endsWith(".wav")) {
			detailModel.setResourcesType(MCResourcesType.MC_MUSIC_TYPE);
		} else if (url.endsWith(".mp4") || url.endsWith(".avi") || url.endsWith(".wmv")) {
			detailModel.setResourcesType(MCResourcesType.MC_VIDEO_TYPE);
		} else if (url.endsWith(".exe")) {
			detailModel.setResourcesType(MCResourcesType.MC_EXE_TYPE);
		} else if (url.endsWith(".xls")) {
			detailModel.setResourcesType(MCResourcesType.MC_XLS_TYPE);
		} else if (url.endsWith(".jpg") || url.endsWith(".png")) {
			detailModel.setResourcesType(MCResourcesType.MC_IMG_TYPE);
	    //文件夹类型的暂时不考虑
		//} else if (url.endsWith(".file")) {
		//	detailModel.setResourcesType(MCResourcesType.MC_VIDEO_TYPE);
		}else {
			detailModel.setResourcesType(MCResourcesType.MC_OTHER_TYPE);
		} 
	}

	public MCResourceDetailModel modelWithData(Object data) {
		MCResourceDetailModel sectionModel = null;
		JSONObject jsonObject = null;
		if (data != null) {
			try {
				jsonObject = new JSONObject(data.toString());
				sectionModel = new MCResourceDetailModel();
				if (jsonObject.has("description")) {
					sectionModel.setName(jsonObject.getString("description"));
				}
				if (jsonObject.has("metadata")) {
					jsonObject = new JSONObject(jsonObject.getString("metadata"));
					if (!jsonObject.isNull("resourceId")) {
						sectionModel.setResourceId(jsonObject.getString("resourceId"));
					}
					if (!jsonObject.isNull("resourceLink")) {
						sectionModel.setResourceLink(Const.SITE_LOCAL_URL + Const.BASE_PATH + "/" + jsonObject.getString("resourceLink"));
					}
					if (!jsonObject.isNull("itemId")) {
						sectionModel.setItemId(jsonObject.getString("itemId"));
					}
					if (!jsonObject.isNull("size")) {
						sectionModel.setSize(jsonObject.getInt("size") * 1024);
					}
					convertResTypeFromUrl(sectionModel);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sectionModel;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceLink() {
		return resourceLink;
	}

	public void setResourceLink(String resourceLink) {
		this.resourceLink = resourceLink;
	}

	public MCResourcesType getResourcesType() {
		return resourcesType;
	}

	public void setResourcesType(MCResourcesType resourcesType) {
		this.resourcesType = resourcesType;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
