package com.whatyplugin.imooc.ui.question;

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
import com.whatyplugin.uikit.toast.MCToast;

/**
 * 左侧菜单弹出的按课程分类的答疑
 * 
 * @author 马彦君
 * 
 */
public class MCAllQuestionMenuFragment extends MCBaseV4ListFragment {
	private MCStudyServiceInterface service;

	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public int getNoDataImage() {
		return R.drawable.no_message_icon;
	}

	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return "答疑列表为空";
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
		this.service.getMyAllQuestionList(mCurrentPage, Const.PAGESIZE, null, MCAllQuestionMenuFragment.this,
				MCAllQuestionMenuFragment.this.getActivity());
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCQuestionModel courseModel = (MCQuestionModel) obj;
		Intent intent = null;
		String courseId = courseModel.getCourseId();
		String courseName = courseModel.getcName();
		if (courseModel.getaCount() > 0) {
			intent = new Intent(getActivity(), MCMyQuestionListActivity.class);
			intent.putExtra("courseId", courseId);
			intent.putExtra("courseName", courseName);
			startActivity(intent);
		} else {
			MCToast.show(getActivity(), "暂无答疑哦 =_=");
		}
	}

	@Override
	public void initAdapter() {
		this.adapter = MCQuestionCommon.initCourseQuestionOrHomeWorkAdapter(this.getActivity(),MCQuestionCommon.QUESTION);
	}
}
