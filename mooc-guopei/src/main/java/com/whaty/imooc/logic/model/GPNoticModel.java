package com.whaty.imooc.logic.model;
import android.util.Base64;

import com.whaty.imooc.ui.index.GPRequestUrl;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.utils.DateUtil;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 *
 *
 *
 * @author whaty
 *
 */

public class GPNoticModel extends MCDataModel implements Serializable {
	/**
	 * bulletin 通知公告 subjectBulletin 学科公告 areaBulletin 班级公告
	 */
	private static final long serialVersionUID = 1L;
	private String id; // 通知ID
	private String name; // 通知标题
	private int viewCount; // 查看次数
	private String time; // 发布时间
	private boolean isRead; // 1代表已读 0代表未读 ,true 未读 ，false 已读
	private String content; // 内容详情
	private boolean isTop; // 是否置顶 true 代表置顶 false代表普通的
	private String author; // 作者
	private String type; // 公告类型 通知：bulletin 区域： areaBulletin 学科：subjectBulletin
	private String title; // title上显示的文字

	@Override
	public MCDataModel modelWithData(Object arg1) {
		GPNoticModel model = new GPNoticModel();

		JSONObject object = (JSONObject) arg1;
		model.setId(object.optString("id"));
		model.setTime(DateUtil.getFormatfromTimeStr(object.optString("publishdate"), DateUtil.FORMAT_LONG, DateUtil.FORMAT_NEW_MINUTE));
		model.setName(object.optString("title"));
		model.setViewCount(Integer.valueOf(object.optString("viewCount").equals("") ? "0" : object.optString("viewCount")));
		model.setRead(object.optString("isRead").equals("0"));
		model.setTop(object.optString("FlagIstop").equals("置顶"));
		model.setAuthor(object.optString("NAME"));
		model.setContent(object.optString("note"));
		model.setType(object.optString("type"));
		// 有加密的数据类型
		if (!object.optString("type").equals("bulletin")) {
			String webViewContent = 	decodeBase64(object.optString("note").getBytes());
			if(webViewContent.contains("src")  ){
				String regex = "src=\"/learning";
				String regex1 = "src=\"";
				String str = "";
				Pattern pat = Pattern.compile(regex);
				Matcher matcher = pat.matcher(webViewContent);
				while (matcher.find()) {
					String temp = webViewContent.substring(matcher.start(),matcher.end());
					str = webViewContent.replaceAll(temp, temp.substring(0,temp.lastIndexOf(regex))+regex1 + GPRequestUrl.getInstance().CODEURL +"/learning");
				}
				model.setContent(str);
			}
			else{

				model.setContent(webViewContent);
			}
		}

		return model;
	}
	public String decodeBase64(byte[] input) {

		return new String(Base64.decode(input, Base64.DEFAULT));

	}

	@Override
	public String getId() {

		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getStrViewCount() {
		return "浏览:" + getViewCount() + "次";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNoRead() {
		return isRead ? "<未读>" : "";
	}

	public String getTitle() {
		return type.equals("bulletin") ? "通知公告" : type.equals("subjectBulletin") ? "学科公告" : type.equals("areaBulletin") ? "班级公告" : "公告";
	}

}
