package com.whatyplugin.imooc.ui.question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.model.MCQuestionModel;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.ui.base.MCBaseListActivity;
import com.whatyplugin.imooc.ui.search.MCMyQuestionSingleSearchActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;

/**
 * 我的所有问题列表
 * 
 * @author
 */
public class MCMyQuestionListActivity extends MCBaseListActivity {

	private String courseId;
	private MCStudyService service;
	private String courseName;
	private BaseTitleView titleView;
	@Override
	public int getRootViewId() {
		return R.layout.fragment_myallquesiton;
	}

	/**
	 * ui填充，adapter初始化
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initData();
		super.onCreate(savedInstanceState);
		titleView = (BaseTitleView) findViewById(R.id.rl_titile);
		((TextView) findViewById(R.id.course_name)).setText(courseName);
		titleView.setRigImageListener(new RightClickListener() {
			@Override
			public void onRightViewClick() {
				gotoSearch();
			}
		});
	}

	private void initData() {
		courseName = getIntent().getStringExtra("courseName");
		courseId = getIntent().getStringExtra("courseId");
		this.service = new MCStudyService();
	}
	@Override
	public void requestData() {
		this.service.getQuestion(this.courseId, this.mCurrentPage, "myquestion", "", this, this);
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCQuestionModel model = (MCQuestionModel) obj;
		Intent intent = new Intent(this, MCQuestionDetailActivity.class);
		intent.putExtra("questionId", model.getId());
		startActivity(intent);
	}

	@Override
	public String getFunctionTitle() {
		return "我的问题";
	}
	@Override
	public void initAdapter() {
		this.adapter = MCQuestionCommon.initMyQuestionAdapter(this);
	}
	private void gotoSearch() {
		Intent intent = new Intent(this, MCMyQuestionSingleSearchActivity.class);
		intent.putExtra("courseId", courseId);
		startActivity(intent);
	}
}
