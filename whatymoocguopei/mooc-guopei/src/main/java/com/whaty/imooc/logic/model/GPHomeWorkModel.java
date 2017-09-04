package com.whaty.imooc.logic.model;

import com.whaty.imooc.utile.GPStringUtile;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.imooc.logic.model.MCHomeworkModel;

import org.json.JSONObject;
/**
 * hstatus1  已提交未批改  hstatus2 已批改  hstatus3 已驳回
 * 
 * // 0 去做作业
				// 1 查看作业 - 有草稿（可以查看，选择重做）
				// 2 没有标记 ,已提交，未批改
				// 3 成绩已出
				// 5作业已过时的
				// 6不应该出现的状态
 * 
 * 
 * @author whaty
 *
 */


public class GPHomeWorkModel extends MCDataModel {
private JSONObject jsonObject;


	@Override
	public MCDataModel modelWithData(Object arg1) {
		MCHomeworkModel homeworkModel = new MCHomeworkModel();
		 jsonObject = (JSONObject) arg1;
		homeworkModel.setComment(false);
		homeworkModel.setDetail(getValues("str8"));
		homeworkModel.setTitle(getValues("str1"));
		homeworkModel.setBetweenCommitTime(homeworkModel.checkBetweenTime(getValues("str3"), getValues("str4")));
//		homeworkModel.setLocalStatus(homeworkModel.isBetweenCommitTime()||getLocalStatus(getValues("str6"))==3?getLocalStatus(getValues("str6")):5);
		homeworkModel.setLocalStatus(homeworkModel.isBetweenCommitTime()||getLocalStatus(getValues("str6"))==3?getLocalStatus(getValues("str6")):5);

		homeworkModel.setTotalScore(getValues("str7"));
		homeworkModel.setStartDate(GPStringUtile.MMdd(getValues("str3")));
		homeworkModel.setEndDate(GPStringUtile.MMdd(getValues("str4")));
		homeworkModel.setId(getValues("id"));
		homeworkModel.setStartDateWithYear(GPStringUtile.YYYYMMdd(getValues("str3")));
		homeworkModel.setEndDateWithYear(GPStringUtile.YYYYMMdd(getValues("str4")));
		homeworkModel.setNote(getValues("str9"));
		homeworkModel.setHomeworkStuId("11");
		homeworkModel.checkIsOverTime(homeworkModel,getValues("str3"),getValues("str4"));
		return homeworkModel;
	}

	private int getLocalStatus(String hstatus){

		return "hstatus1".equals(hstatus)?2:"hstatus2".equals(hstatus)?3:"hstatus3".equals(hstatus)?4:"hstatus0".equals(hstatus)?1:0;

	}


	@Override
	public String getId() {

		return null;
	}

	private String getValues(String key) {
		return jsonObject.optString(key);
	}

}
