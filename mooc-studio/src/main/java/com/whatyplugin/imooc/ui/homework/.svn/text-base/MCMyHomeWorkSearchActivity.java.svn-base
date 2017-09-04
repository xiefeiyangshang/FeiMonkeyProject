package com.whatyplugin.imooc.ui.homework;

import android.content.Intent;
import android.os.Bundle;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCQuestionModel;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.ui.question.MCQuestionCommon;
import com.whatyplugin.imooc.ui.search.MCBaseSearchActivity;

public class MCMyHomeWorkSearchActivity extends MCBaseSearchActivity {
	private MCStudyServiceInterface service;

	public int getRootViewId() {
		return R.layout.common_search_layout;
	}

	public int getNoDataImage() {
		return R.drawable.no_course_icon;
	}

	public String getNoDataTip() {
		return MCHomeworkCommon.HOMEWORK_LIST_IS_EMPTY;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.service = new MCStudyService();
		hisKey = Contants.SEARCH_KEY_MY_QUESTION_COURSE;
		super.onCreate(savedInstanceState);
	}

	@Override
	public String getFunctionTitle() {
		return MCHomeworkCommon.MY_HOMEWORK;
	}

	@Override
	public void initAdapter() {
		this.adapter = MCQuestionCommon.initCourseQuestionOrHomeWorkAdapter(this, MCHomeworkCommon.HOMEWORK);
	}

	@Override
	public void requestData() {
		this.service.getMyAllHomeWork(mCurrentPage, Const.PAGESIZE, this.et_search_content.getText().toString(), this, this);

	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCQuestionModel courseModel = (MCQuestionModel) obj;
		String courseId = courseModel.getCourseId();
		Intent intent = new Intent(this, MCHomeworkListActivity.class);
		intent.putExtra("courseId", courseId);
		startActivity(intent);

	}

}
