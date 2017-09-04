package com.whaty.imooc.logic.model;

import android.text.TextUtils;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.utils.RequestUrl;

import org.json.JSONObject;

/**
 * "LAST_DATE": "2015-10-10 14:21:28.0", "courseware_percent": "1.60",
 * "cwareName": "", "cwareLink": "", "courseId":
 * "ff8080814a03ea21014a04fcc5250144", "cwareTime": "", "courseCode":
 * "ahddnlts0192", "TOTAL_TIME": "96", "openCourseId":
 * "ff8080815011b01a015012217870008c", "attempt_num": "1", "stuId":
 * "ff8080815011b01a0150129d4fda015f", "Attribute": "必修", "courseTime": "60",
 * "elescore": "", "cwareId": "", "LoginID": "chen02", "cwareimgUrl": "",
 * "typeName": "类别二：综合类", "category": "能力提升", "isLearnSpace": "1",
 * "LESSON_STATUS": "complete", "stuName": "陈02你是什么样的人", "watPath": "",
 * "typeId": "ff80808149f68d27014a03e23e860112", "cwareCode": "", "courseName":
 * "T10:学生微电影在思想品德课教学中的应用"
 * 
 * @author whaty
 * 
 */
public class GPCourseMode extends MCDataModel {

	
	@Override
	public MCDataModel modelWithData(Object arg1) {
		MCCourseModel courseMode = new MCCourseModel();
		JSONObject jsonObject = (JSONObject) arg1;
		courseMode.setName(jsonObject.optString("courseName"));
		courseMode.setId(jsonObject.optString("openCourseId"));

		courseMode.setPageTime(jsonObject.optString("pageTime"));
		courseMode.setCourseTime(jsonObject.optString("courseTime"));
		courseMode.setEffectTime(jsonObject.optString("effectTime"));

		if (!jsonObject.isNull("cPhoto")) {
			String cPicture = jsonObject.optString("cPhoto");
			if (!cPicture.startsWith("http:") && !TextUtils.isEmpty(cPicture)) {
				cPicture = RequestUrl.getInstance().MODEL_BASE + cPicture;
			}
			courseMode.setImageUrl(cPicture);
		}
//		courseMode.setImageUrl(jsonObject.optString("cPhoto"));
		return courseMode;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}



}
