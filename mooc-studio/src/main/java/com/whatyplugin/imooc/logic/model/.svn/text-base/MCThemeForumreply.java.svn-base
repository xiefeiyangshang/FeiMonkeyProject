package com.whatyplugin.imooc.logic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.utils.DateUtil;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.logic.utils.StringUtils;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;

/**
 * 接口现在有问题先不删除调试信息
 * 
 * @author whaty
 * 
 */

public class MCThemeForumreply extends MCDataModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String parentId;
	private String body;
	private String nickName;
	private String handIamgeUrl;
	private String reply;
	private int state; // 1为加精，2为不加精
	private String time; // 发布时间
	private boolean zambia; // 自己赞
	private boolean otherUserZambia; // 其他人赞
	private String otherZambiaNames; // 其他人的赞
	private String newComent; // 自己评论的内容
	private boolean isComment; // 是否有评论
	private boolean ismyComent; // 是否来自自己的评论
	private List<MCforumreplyMode> list;
	private String mainId;
	private String tempDraft; // 临时草稿

	@Override
	public MCDataModel modelWithData(Object arg1) {

		if (arg1 == null || "{}".equals(arg1.toString()))
			return null;
		try {
			JSONObject object = (JSONObject) arg1;
			MCThemeForumreply forum = new MCThemeForumreply();
			forum.setBody(StringUtils.rmWebViewLabelP(object.optString("detail")));
			forum.setNickName(object.optString("nickName"));

			String orgTime = DateUtil.getFormatfromTimeStr(object.optString("postTime"), DateUtil.FORMAT_LONG, DateUtil.FORMAT_YEAR);
			forum.setTime(StringUtils.dateRelpaceZero(orgTime));

			forum.setId(object.optString("id")); // 回帖ID
			forum.setParentId(object.optString("parentId")); // 帖子ID
			forum.setMainId(object.optString("mainId"));
			if (object.has("picFileName")) {
				String picture = object.optString("picFileName");
				if (!TextUtils.isEmpty(picture) && picture.startsWith("http:")) {
					forum.setHandIamgeUrl(picture);
				} else {
					forum.setHandIamgeUrl(Const.SITE_LOCAL_URL + Const.BASE_PATH + picture);
				}
			}
			String zanNames = object.optString("favorNames").replace("[", "").replaceAll("]", "").replaceAll("\"", "");
			// 获取自己的昵称
			String myNickName = MCSaveData.getUserInfo("nickname", MoocApplication.getInstance()).toString();

			if (zanNames == null || "".equals(zanNames)) {
				forum.setZambia(false);
				forum.setOtherUserZambia(false);
			} else if (zanNames.contains("," + myNickName + ",") || zanNames.startsWith(myNickName) || zanNames.endsWith(myNickName)) {
				forum.setZambia(true);
				forum.setOtherUserZambia(true);
				forum.setOtherZambiaNames(zanNames);
			} else {
				forum.setZambia(false);
				forum.setOtherUserZambia(true);
				forum.setOtherZambiaNames(zanNames);
			}
			forum.setState(object.optString("topicType").equals("1") ? 1 : 2);
			List<MCforumreplyMode> listReply = new ArrayList<MCforumreplyMode>();
			// 处理评论
			if (object.has("replies")) {
				forum.setComment(true);

				try {

					JSONArray jsonArray = object.getJSONArray("replies");

					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						MCforumreplyMode mCforumreplyMode = new MCforumreplyMode();

						mCforumreplyMode.setId(jsonObject.optString("id"));
						mCforumreplyMode.setParentId(jsonObject.optString("parentId"));
						mCforumreplyMode.setDetail(jsonObject.optString("detail"));

						mCforumreplyMode.setNickName(jsonObject.optString("nickName"));
						mCforumreplyMode.setReplyNickname(jsonObject.optString("replyNickname"));
						mCforumreplyMode.setPicFileUrl(jsonObject.optString("picFileName"));
						// 替换URL命名空间

						mCforumreplyMode.setPostTime(DateUtil.getFormatfromTimeStr(jsonObject.optString("postTime"), DateUtil.FORMAT_LONG,
								DateUtil.FORMAT_YY_MM_DD).replaceAll("\\.0", "."));
						mCforumreplyMode.setMainId(jsonObject.optString("mianId"));
						mCforumreplyMode.setOnlyReply(false);// 现在都改成 某人回复某某人
						listReply.add(mCforumreplyMode);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				forum.setComment(false);
			}
			forum.setList(listReply);
			forum.setIsmyComent(false);// 初始值都是false

			return forum;

		} catch (Exception e) {
			return null;
		}
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<MCforumreplyMode> getList() {
		return list;
	}

	public void setList(List<MCforumreplyMode> list) {
		this.list = list;
	}

	public boolean isIsmyComent() {
		return ismyComent;
	}

	public void setIsmyComent(boolean ismyComent) {
		this.ismyComent = ismyComent;
	}

	private String CommentCentext; // 评论内容

	public String getNewComent() {
		return newComent;
	}

	public void setNewComent(String newComent) {
		this.newComent = newComent;
	}

	public boolean isComment() {
		return isComment;
	}

	public void setComment(boolean isComment) {
		this.isComment = isComment;
	}

	public String getCommentCentext() {
		return CommentCentext;
	}

	public void setCommentCentext(String commentCentext) {
		CommentCentext = commentCentext;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHandIamgeUrl() {
		return handIamgeUrl;
	}

	public void setHandIamgeUrl(String handIamgeUrl) {
		this.handIamgeUrl = handIamgeUrl;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public boolean getZambia() {
		return zambia;
	}

	public void setZambia(boolean zambia) {
		this.zambia = zambia;
	}

	public boolean isOtherUserZambia() {
		return otherUserZambia;
	}

	public void setOtherUserZambia(boolean otherUserZambia) {
		this.otherUserZambia = otherUserZambia;
	}

	public String getOtherZambiaNames() {
		return otherZambiaNames;
	}

	public void setOtherZambiaNames(String otherZambiaNames) {
		this.otherZambiaNames = otherZambiaNames;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getTempDraft() {
		return tempDraft;
	}

	public void setTempDraft(String tempDraft) {
		this.tempDraft = tempDraft;
	}

	public Boolean isDigest() {
		return getState() == 1;
	}

}
