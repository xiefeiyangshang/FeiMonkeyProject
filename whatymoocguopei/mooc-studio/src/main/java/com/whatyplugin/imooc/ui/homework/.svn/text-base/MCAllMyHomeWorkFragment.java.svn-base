package com.whatyplugin.imooc.ui.homework;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCQuestionModel;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;
import com.whatyplugin.imooc.ui.question.MCQuestionCommon;
import com.whatyplugin.uikit.toast.MCToast;

public class MCAllMyHomeWorkFragment extends MCBaseV4ListFragment {
	private MCStudyServiceInterface service;
	@Override
	public int getNoDataImage() {
		return R.drawable.no_message_icon;
	}
	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return "作业列表为空";
	}
	
	
	public void onActivityCreated(Bundle savedInstanceState) {
		this.service = new MCStudyService();
		//声明要注册的service类型
		List<String> actions = new ArrayList<String>();
		actions.add(Contants.NETWORK_CHANGED_ACTION);
		actions.add(Contants.USER_LOGIN_ACTION);
		this.setActionList(actions);
		super.onActivityCreated(savedInstanceState);
	}
	
	
	@Override
	public void requestData() {
		this.service.getMyAllHomeWork(mCurrentPage, Const.PAGESIZE, null, MCAllMyHomeWorkFragment.this, MCAllMyHomeWorkFragment.this.getActivity());
	}

	@Override
	public void doAfterItemClick(Object obj) {

		MCQuestionModel courseModel = (MCQuestionModel) obj;
		Intent intent = null;
		String courseId = courseModel.getCourseId();
		if (courseModel.getaCount() > 0) {
			intent = new Intent(getActivity(), MCHomeworkListActivity.class);
			intent.putExtra("courseId", courseId);
			startActivity(intent);
		} else {
			MCToast.show(getActivity(), "暂无作业哦 =_=");
		}
	
		
	}

	@Override
	public void initAdapter() {
		this.adapter = MCQuestionCommon.initCourseQuestionOrHomeWorkAdapter(this.getActivity(),MCHomeworkCommon.HOMEWORK);
		
	}

}
