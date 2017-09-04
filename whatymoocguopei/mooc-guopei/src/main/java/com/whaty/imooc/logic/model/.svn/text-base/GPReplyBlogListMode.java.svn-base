package com.whaty.imooc.logic.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.utils.DateUtil;

/**
 * { "id": "ff8080814e9b42a1014ece8f3fae0260", "parentId":
 * "ff8080814e900e76014eba2ebc9700ca", "title": "", "detail": " asdgasdg",
 * "topicType": "0", "nickName": "小培7", "mainId":
 * "ff8080814e900e76014eba2ebc9700ca", "userName": "xiaopei7", "picFileName":
 * "", "postTime": "2015-07-27 16:09:31" }
 * 
 * 
 * @author whaty
 * 
 */

public class GPReplyBlogListMode extends MCDataModel {

	private String id;
	private String parentId;
	private String detail;
	private String nickName;
	private String picFileName;
	private String postTime;
	private String mainId;
	private String replyUsername;
	private String replyNickname;
	private List<GPReplyBlogListMode> listReply;
	private boolean isReply;
	
	

	@Override
	public MCDataModel modelWithData(Object arg1) {
		GPReplyBlogListMode blogListMode = new GPReplyBlogListMode();
		JSONObject object = (JSONObject) arg1;
		blogListMode.setId(object.optString("id"));
		blogListMode.setDetail(object.optString("detail"));
		blogListMode.setPostTime(object.optString("postTime"));
		blogListMode.setPicFileName(object.optString("picFileName"));
		blogListMode.setNickName(object.optString("nickName"));
		blogListMode.setReply(false);
		blogListMode.setPostTime(DateUtil.getFormatfromTimeStr(object.optString("postTime"), DateUtil.FORMAT_LONG, DateUtil.FORMAT_YY_MM_DD).replaceAll("\\.0", "."));
		List<GPReplyBlogListMode> listReply = new ArrayList<GPReplyBlogListMode>();
		if (object.has("replies")) {
			JSONArray array = object.optJSONArray("replies");
			for (int i = 0; i < array.length(); i++) {
				object = array.optJSONObject(i);
				GPReplyBlogListMode replyListMode = new GPReplyBlogListMode();
				replyListMode.setId(object.optString("id"));
				replyListMode.setDetail(object.optString("detail"));
				replyListMode.setReplyNickname(object.optString("replyNickname"));
				replyListMode.setNickName(object.optString("nickName"));
				replyListMode.setPostTime(DateUtil.getFormatfromTimeStr(object.optString("postTime"), DateUtil.FORMAT_LONG, DateUtil.FORMAT_YY_MM_DD).replaceAll("\\.0", "."));
				replyListMode.setPicFileName(object.optString("picFileName"));
				replyListMode.setParentId(object.optString("parentId"));
				replyListMode.setReply(true);
				listReply.add(replyListMode);

			}
			blogListMode.setListReply(listReply);
		}

		return blogListMode;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPicFileName() {
		return picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReplyUsername() {
		return replyUsername;
	}

	public void setReplyUsername(String replyUsername) {
		this.replyUsername = replyUsername;
	}

	public String getReplyNickname() {
		return replyNickname;
	}

	public void setReplyNickname(String replyNickname) {
		this.replyNickname = replyNickname;
	}

	public List<GPReplyBlogListMode> getListReply() {
		return listReply;
	}

	public void setListReply(List<GPReplyBlogListMode> listReply) {
		this.listReply = listReply;
	}

	public String getNikeReplyName() {
		return getNickName() + " 回复 " + getReplyNickname();
	}

	public boolean isReply() {
		return isReply;
	}

	public void setReply(boolean isReply) {
		this.isReply = isReply;
	}

}
