package com.whatyplugin.imooc.logic.model;

import java.io.Serializable;

import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.imooc.logic.utils.StringUtils;

public class MCUploadModel extends MCDataModel implements Serializable {

	private String fileSize;
	private String link;
	private boolean success;
	private String info;
	private String alt;
	private String title;
	private String shortPath; //项目的相对之后的路径
	private String id;
	
	/**
	 *  "fileSize": 1266681,
        "link": "http://192.168.22.130:8080/learning/incoming/localhost/default/20150408/1428458907562-6.jpg",
        "success": "1",
        "info": "上传成功！"
	 */
	@Override
	public MCDataModel modelWithData(Object data) {
		if (data != null && data.toString().length() > 0) {
			String info = data.toString();
			MCUploadModel model = new MCUploadModel();
			try {
				JSONObject jsonObject = new JSONObject(info);
				model.setFileSize(jsonObject.optString("fileSize"));
				model.setLink(jsonObject.optString("link"));
				if("1".equals(jsonObject.optString("success"))){
					model.setSuccess(true);
				}
				model.setInfo(jsonObject.optString("info"));
				model.setAlt(jsonObject.optString("alt"));
				model.setTitle(jsonObject.optString("title"));
				//改成StringUtils中的类  从incoming开始字符串
				model.setShortPath(StringUtils.rmSiteUrlAndPath(model.getLink()));
				return model;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortPath() {
		return shortPath;
	}

	public void setShortPath(String shortPath) {
		this.shortPath = shortPath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
