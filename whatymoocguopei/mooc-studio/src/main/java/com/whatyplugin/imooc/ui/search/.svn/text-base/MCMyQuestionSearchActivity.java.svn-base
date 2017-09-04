package com.whatyplugin.imooc.ui.search;

import android.content.Intent;
import android.os.Bundle;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCQuestionModel;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.ui.question.MCMyQuestionListActivity;
import com.whatyplugin.imooc.ui.question.MCQuestionCommon;
import com.whatyplugin.uikit.toast.MCToast;

/**
 * 左侧菜单弹出的按课程分类的答疑----搜索界面
 * 
 * @author 马彦君
 * 
 */
public class MCMyQuestionSearchActivity extends MCBaseSearchActivity {

	private MCStudyServiceInterface service;

	/**
	 * 子类有自己布局的时候重写此方法
	 * 
	 * @param resultList
	 */
	public int getRootViewId() {
		return R.layout.common_search_layout;
	}

	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public int getNoDataImage() {
		return R.drawable.no_course_icon;
	}

	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return "课程列表为空";
	}

	protected void onCreate(Bundle savedInstanceState) {
		this.service = new MCStudyService();
		hisKey = Contants.SEARCH_KEY_MY_QUESTION_COURSE;
		super.onCreate(savedInstanceState);
	}

	@Override
	public void requestData() {
		this.service.getMyAllQuestionList(mCurrentPage, Const.PAGESIZE, this.et_search_content.getText().toString(), this, this);
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCQuestionModel courseModel = (MCQuestionModel) obj;
		Intent intent = null;
		String courseId = courseModel.getCourseId();
		String courseName = courseModel.getcName();
		if (courseModel.getaCount() > 0) {
			intent = new Intent(this, MCMyQuestionListActivity.class);
			intent.putExtra("courseId", courseId);
			intent.putExtra("courseName", courseName);
			startActivity(intent);
		} else {
			MCToast.show(this, "暂无答疑哦 =_=");
		}
	}

	@Override
	public void initAdapter() {
		this.adapter = MCQuestionCommon.initCourseQuestionOrHomeWorkAdapter(this,MCQuestionCommon.QUESTION);
	}

}
