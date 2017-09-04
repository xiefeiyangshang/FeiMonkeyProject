package com.whatyplugin.imooc.ui.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;

import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCQuestionModel;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.ui.question.MCQuestionCommon;
import com.whatyplugin.imooc.ui.question.MCQuestionDetailActivity;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnFooterRefreshListener;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnHeaderRefreshListener;

public class MCMyQuestionSingleSearchActivity extends MCBaseSearchActivity implements MCAnalyzeBackBlock, OnFooterRefreshListener,
		OnHeaderRefreshListener, AdapterView.OnItemClickListener {
	private MCStudyServiceInterface service;
	private String courseId;
	private String questionId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (getIntent() != null)
			courseId = getIntent().getStringExtra("courseId");
		hisKey = Contants.SEARCH_KEY_MY_QUESTION;
		this.service = new MCStudyService();
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initAdapter() {
		this.adapter = MCQuestionCommon.initMyQuestionAdapter(this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == 2) {
			reloadData();
		}
	}

	@Override
	public void requestData() {
		this.service.getQuestion(courseId, mCurrentPage, "myquestion", this.et_search_content.getText().toString(), this, this);
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCQuestionModel model = (MCQuestionModel) obj;
		questionId = model.getId();
		Intent intent = null;
		intent = new Intent(this, MCQuestionDetailActivity.class);
		intent.putExtra("questionId", questionId);
		startActivity(intent);
	}
}
