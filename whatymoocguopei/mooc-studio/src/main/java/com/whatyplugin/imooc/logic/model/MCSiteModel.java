package com.whatyplugin.imooc.logic.model;
public class MCSiteModel extends MCUserModel {
	
	private String url;
	private String name;
	private String webDoMain;
	private String code;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWebDoMain() {
		return webDoMain;
	}
	public void setWebDoMain(String webDoMain) {
		this.webDoMain = webDoMain;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
