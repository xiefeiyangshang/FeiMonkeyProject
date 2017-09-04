package com.whatyplugin.imooc.ui.question;

import android.content.Intent;
import android.os.Bundle;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.model.MCQuestionModel;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;
/**
 * 所有问题Fragment
 * @author 马彦君
 *
 */
public class MCAllQuestionFragment extends MCBaseV4ListFragment {
	private MCStudyServiceInterface service;
	private String courseId;

	@Override
	public int getNoDataImage() {
		return R.drawable.no_message_icon;
	}
	
	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return "问题列表为空";
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		this.courseId = ((MCQuestionMainActivity) this.getActivity()).getCourseId();
		this.service = new MCStudyService();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void requestData() {
		this.service.getQuestion(this.courseId, this.mCurrentPage, "general", "", this, this.getActivity());
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCQuestionModel model = (MCQuestionModel) obj;
		Intent intent = new Intent(getActivity(), MCQuestionDetailActivity.class);
		intent.putExtra("submituserId", model.getSubmituserName());// 获得发布问题的ID
		intent.putExtra("questionId", model.getId());
		startActivity(intent);
	}

	@Override
	public void initAdapter() {
		this.adapter = MCQuestionCommon.initAllQuestionAdapter(this.getActivity());
	}

}
