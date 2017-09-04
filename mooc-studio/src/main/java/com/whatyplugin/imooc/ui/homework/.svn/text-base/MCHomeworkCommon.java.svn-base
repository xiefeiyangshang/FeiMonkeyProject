package com.whatyplugin.imooc.ui.homework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.model.MCHomeworkModel;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;

/**
 * 作业部分的公用代码
 * 
 * @author 马彦君
 * 
 */
public class MCHomeworkCommon {

	private static final String TAG = "MCHomeworkCommon";

	public static final int DO_HOMEWORK_CODE = 12;		//做作业的requestCode
	public static final int DETAIL_HOMEWORK_CODE = 11;	//作业详情的requestCode
	
	public static final int COMMIT_HOMEWORK_RESULT = -2;		//提交作业的返回code
	public static final int SAVE_LOCAL_HOMEWORK_RESULT = -1;	//保存草稿的返回code
	public static  final String HOMEWORK =" 个作业"; //用于作业列表的显示
	public static final String MY_HOMEWORK ="我的作业"; //title 显示
	public static final String   HOMEWORK_LIST_IS_EMPTY ="作业列表为空"; //没有作业列表的时候
	
	
	/**
	 * 通过课程id获取本地草稿
	 * @param service
	 * @param courseId
	 * @param context
	 * @return
	 */
	public static Map<String, MCHomeworkModel> getLocalHomeWork(
			MCStudyServiceInterface service, 
			String courseId,
			String loginId,
			Context context) {
		// 获取本地草稿
		List<MCHomeworkModel> localHomeWork = service.getHomeworkFromLocal(
				courseId,loginId, context);
		Map<String, MCHomeworkModel> map = new HashMap<String, MCHomeworkModel>();
		for (MCHomeworkModel model : localHomeWork) {
			model.setCourseId(courseId);
			map.put(model.getId(), model);
		}
		return map;
	}
	
	/**
	 * 用草稿替换原来的作业答案
	 * @param service
	 * @param courseId
	 * @param list
	 * @param context
	 */
	public static void replaceHomeworkWithLocal(
			MCStudyServiceInterface service, 
			String courseId,
			String loginId,
			List<MCHomeworkModel> list, 
			Context context) {

		try {
			Map<String, MCHomeworkModel> map = getLocalHomeWork(service, courseId,loginId, context);
			for (MCHomeworkModel model : list) {
				model.setCourseId(courseId);
				if (model.getLocalStatus() == 0 || model.getLocalStatus() == 1) {
					MCHomeworkModel localModel = map.get(model.getId());
					if (localModel != null) {// 本地有草稿
						model.setNote(localModel.getNote());
						model.setPicPathList(localModel.getPicPathList());
						model.setLocalStatus(0);//本地有草稿的都设置为0
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			MCLog.e(TAG, "用草稿替换作业出错！" + e.getMessage());
		}
	}
	

	/**
	 * 根据情况判断是做作业还是查看作业  公用的跳转到作业方法
	 * @param model
	 * @param activity
	 */
	public static void gotoHomework(MCHomeworkModel model, Activity activity){
		if (model.getLocalStatus() == 0) {
			Intent intent = new Intent(activity, MCHomeworkCommitActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("homework", model);
			intent.putExtras(bundle);
			activity.startActivityForResult(intent, DO_HOMEWORK_CODE);
		} else {
			Intent intent = new Intent(activity, MCHomeworkDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("homework", model);
			intent.putExtras(bundle);
			activity.startActivityForResult(intent, DETAIL_HOMEWORK_CODE);
		}
	}
}
