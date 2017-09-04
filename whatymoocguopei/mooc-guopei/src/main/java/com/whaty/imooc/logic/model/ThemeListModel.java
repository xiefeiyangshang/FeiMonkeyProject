package com.whaty.imooc.logic.model;
import com.whaty.imooc.ui.index.GPRequestUrl;
import com.whaty.imooc.utile.GPStringUtile;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.utils.DateUtil;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * { "id": "532a304d-4ad4-4dbe-a8eb-2d99b153043d", "startDate":
 * "2015-07-07 00:00:00.0", "resourceNum": "0", "jinghuaNum": "0", "endDate":
 * "2015-07-20 23:59:59.0", "tieziNum": "0", "type": "20", "`name`": "aa" }
 * 
 * @author whaty
 * 
 */

public class ThemeListModel extends MCDataModel {
	private String id;
	private String name;
	private String resourceNum;
	private String tieziNum;
	private String type; // 21 表示论坛， 20表示资源
	private String startDate;
	private String endDate;
	private String jinghuaNum;
	private Boolean isType;
	private String activityDetail;
	private int State;
	private String[] allHint = new String[] { "评论已过期", "评论暂未开始，开始日期：", "亲，赶紧写个评论吧" };
	private String hint;
	private String ids;
	private String themeId;
	private boolean overdue; // true 表示过期 false 表示不过期
	private String ThemeNum;

	@Override
	public MCDataModel modelWithData(Object arg1) {
		JSONObject object = (JSONObject) arg1;
		ThemeListModel listModel = new ThemeListModel();
		listModel.setId(object.optString("id"));
		listModel.setEndDate(object.optString("endDate"));
		listModel.setStartDate(object.optString("startDate"));
		listModel.setType(object.optString("type"));
		listModel.setIsType((object.optString("type")).equals("21"));
		listModel.setTieziNum(object.optString("tieziNum", "0"));
		listModel.setResourceNum(object.optString("resourceNum", "0"));
		listModel.setName(object.optString("NAME"));
		listModel.setJinghuaNum(object.optString("jinghuaNum", "0"));

		String webViewContent =     object.optString("activityDetail");
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
			listModel.setActivityDetail(str);
		}
		else{

			listModel.setActivityDetail(webViewContent);
		}

//		listModel.setActivityDetail(object.optString("activityDetail"));


		listModel.setThemeNum(String.format("主题%s",GPStringUtile.enNum2CnNum(GPStringUtile.ThemeListNum + "")));
		GPStringUtile.ThemeListNum++;
		// 返回数量跟这个使用的一个model 为了防止报错 他俩JSON 的区别就是 themeId
		if (!object.has("themeId")) {
			listModel.setOverdue(!DateUtil.toBeforeNow(listModel.getEndDate()));
			listModel.setState(!DateUtil.toBeforeNow(listModel.getStartDate()) ? (!DateUtil.toBeforeNow(listModel.getEndDate()) ? 4 : 3) : 1);
			listModel.setHint(!DateUtil.toBeforeNow(listModel.getStartDate()) ? (!DateUtil.toBeforeNow(listModel.getEndDate()) ? allHint[0] : allHint[2])
					: (allHint[1] + getDateFTime(listModel.getStartDate())));
		} else {
			listModel.setThemeId(object.optString("themeId"));
		}
		return listModel;
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

	public String getResourceNum() {
		return resourceNum;
	}

	public void setResourceNum(String resourceNum) {
		this.resourceNum = resourceNum;
	}

	public String getTieziNum() {
		return tieziNum;
	}

	public void setTieziNum(String tieziNum) {
		this.tieziNum = tieziNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJinghuaNum() {
		return jinghuaNum;
	}

	public void setJinghuaNum(String jinghuaNum) {
		this.jinghuaNum = jinghuaNum;
	}

	public Boolean getIsType() {
		return isType;
	}

	public void setIsType(Boolean isType) {
		this.isType = isType;
	}

	public String getActivityDetail() {
		return activityDetail;
	}

	public void setActivityDetail(String activityDetail) {
		this.activityDetail = activityDetail;
	}

	public String getStartAndEndTime() {
		return GPStringUtile.StartAndEndTime(getStartDate(), getEndDate());
	}

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getDateFTime(String date) {
		return DateUtil.getFormatfromTimeStr(date, DateUtil.FORMAT_FULL, DateUtil.FORMAT_SHORT);
	}

	public String gettieziNumAndOne() {
		return String.valueOf(Integer.valueOf(getTieziNum()) + 1);
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public boolean isOverdue() {
		return overdue;
	}

	public void setOverdue(boolean overdue) {
		this.overdue = overdue;
	}

	public String getThemeNum() {
		return ThemeNum;
	}

	public void setThemeNum(String themeNum) {
		ThemeNum = themeNum;
	}

}
