package com.whatyplugin.imooc.ui.search;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.base.asyncimage.MCImageView;
import com.whatyplugin.base.utils.DateUtil;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCQuestionModel;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.ui.question.MCQuestionCommon;
import com.whatyplugin.imooc.ui.question.MCQuestionDetailActivity;

public class MCAllQueationSearchActivity extends MCBaseSearchActivity {
	private MCStudyServiceInterface service;
	private String courseId;
	private String questionId;

	/**
	 * 子类有自己布局的时候重写此方法
	 * 
	 * @param resultList
	 */
	public int getRootViewId() {
		return R.layout.common_search_layout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (getIntent() != null)
			courseId = getIntent().getStringExtra("courseId");
		hisKey =  Contants.SEARCH_KEY_ALL_QUESTION;
		this.service = new MCStudyService();
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(MCAllQueationSearchActivity.this, R.layout.item_question) {

			@Override
			protected void convert(BaseAdapterHelper arg1, Object arg2) {
				this.convert(arg1, ((MCQuestionModel) arg2));
			}

			protected void convert(BaseAdapterHelper helper, MCQuestionModel question) {
				helper.setText(R.id.tv_desc, question.getBody());
				helper.setText(R.id.tv_nick, question.getSubmituserName());
				Date data;
				data = DateUtil.parseAll(question.getPublishDate());
				helper.setText(R.id.tv_time, DateUtil.format(data, DateUtil.FORMAT_NEW_SHORT));
				if (question.isTeacherReplay()) {
					helper.setVisible(R.id.iv_recmd, true);
				} else {
					helper.setVisible(R.id.iv_recmd, false);
				}

				MCImageView question_headimage = (MCImageView) helper.getView(R.id.question_headimage);
				question_headimage.setDefaultImageResId(R.drawable.user_default_img);
				question_headimage.setImageUrl(question.getUserPic(), MCCacheManager.getInstance().getImageLoader());
				MCQuestionCommon.showPicInItem(helper, question.getImgUrlList(), MCAllQueationSearchActivity.this);
			}
		};
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 2) {
				if (mListView != null)
					mListView.headerRefreshing();
				if (data != null) {
				}
			}
		}
	}

	@Override
	public void requestData() {
		this.service.getQuestion(courseId, mCurrentPage, "general", this.et_search_content.getText().toString(), this, this);
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
