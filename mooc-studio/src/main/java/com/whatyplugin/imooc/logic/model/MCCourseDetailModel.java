package com.whatyplugin.imooc.logic.model;
import android.text.TextUtils;

import com.whatyplugin.base.model.MCDataModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MCCourseDetailModel extends MCDataModel {
	private String description;
	private String credit;
	private String name;
	private String target;
	private int	   count;
	private	String imgUrl;
	private String   startDate;
	private String   endDate;
	private String	mainTeacher;//主讲教师
	private List<MCUserModel> teacher;
	private String id;

	public MCCourseDetailModel() {
		super();
	}

	public String getDescription() {
		return this.description;
	}

	public List<MCUserModel> getTeacher() {
		return this.teacher;
	}

	public MCCourseDetailModel modelWithData(Object data) {
		if(data !=null && data.toString().length()>0) {
			MCCourseDetailModel model = new MCCourseDetailModel();
			try {
				JSONObject courseInfo = new JSONObject(data.toString());
				if(courseInfo.has("cName")){
					model.setName(courseInfo.getString("cName"));
				}
				if(courseInfo.has("cCredit")){
					String credit = courseInfo.getString("cCredit");
					if(credit.toLowerCase().contains("null")){
						credit = "0";
					}else {
						credit = credit.replace(".0", "");//去掉服务器返回的.0
					}
					model.setCredit(credit);
				}
				if(!courseInfo.isNull("cIntro")){
					model.setDescription(courseInfo.getString("cIntro"));
				}else{
					model.setDescription("暂无");
				}
				if(!courseInfo.isNull("cTargetIntro")){
					model.setTarget(courseInfo.getString("cTargetIntro"));
				}else{
					model.setTarget("暂无");
				}
				if(courseInfo.has("cStuCnt")){
					model.setCount(courseInfo.getInt("cStuCnt"));
				}
				if(courseInfo.has("cImage")){
					model.setImgUrl(courseInfo.getString("cImage"));
				}
				if(courseInfo.has("mainTeacher")){
					//界面不能显示null
					model.setMainTeacher(courseInfo.getString("mainTeacher").equals("null")?"":courseInfo.getString("mainTeacher"));
				}
				//开课时间
				if(courseInfo.isNull("cStartDate")){
					model.setStartDate("暂未确定");
				}else{
					String startDate = courseInfo.getString("cStartDate");
					model.setStartDate(startDate.replace("-", "."));
				}


				List<MCUserModel> teacherList = new ArrayList<MCUserModel>();
				if(courseInfo.has("teachers")){
//					MCFullUserModel fullUserModel = new MCFullUserModel();
					MCFullUserModel fullUserModel = null;
//					fullUserModel.setDesc(courseInfo.getString("cTeacherIntro"));

					JSONArray jsonArray = new JSONArray(courseInfo.getString("teachers"));

	                for(int i = 0; i < jsonArray.length(); ++i) {
	                    JSONObject userObj = jsonArray.getJSONObject(i);
	                     fullUserModel = new MCFullUserModel();
	                    fullUserModel.setNickname(userObj.getString("tName"));
	                    String tIntro = userObj.getString("tIntro");
	                	if(TextUtils.isEmpty(tIntro) || tIntro.toLowerCase().contains("null")){
	                		tIntro = "暂无简介";
	                	}
	                    fullUserModel.setDesc(tIntro);
	                    if(!userObj.isNull("tPhoto"))
	                    	fullUserModel.setImageUrl(userObj.getString("tPhoto"));
	                }
					teacherList.add(fullUserModel);


				}
				model.setTeacher(teacherList);
				return model;
			}
			catch(Exception v1) {
				v1.printStackTrace();
			}
		}
		return null;

	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTeacher(List<MCUserModel> arg1) {
		this.teacher = arg1;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	public String getMainTeacher() {
		return mainTeacher;
	}

	public void setMainTeacher(String mainTeacher) {
		this.mainTeacher = mainTeacher;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

