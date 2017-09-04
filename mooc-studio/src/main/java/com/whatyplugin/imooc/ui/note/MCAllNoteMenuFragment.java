package com.whatyplugin.imooc.ui.note;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.service_.MCCourseDetailService;
import com.whatyplugin.imooc.logic.service_.MCCourseDetailServiceInterface;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;
import com.whatyplugin.uikit.toast.MCToast;

/**
 * 左侧菜单点击出来的按课程分类的笔记
 * 
 * @author 马彦君
 * 
 */
public class MCAllNoteMenuFragment extends MCBaseV4ListFragment {
	private MCCourseDetailServiceInterface service;
	
	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public int getNoDataImage() {
		return R.drawable.no_note_icon;
	}

	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return getResources().getString(R.string.no_note_label);
	}
	
	@Override
	public void doSomethingWithResult(List resultList) {
		MCNoteCommon.removeZeroCountNoteFromList(resultList);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		this.service = new MCCourseDetailService();
		//声明要注册的service类型
		List<String> actions = new ArrayList<String>();
		actions.add(Contants.NETWORK_CHANGED_ACTION);
		actions.add(Contants.USER_LOGIN_ACTION);
		this.setActionList(actions);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void requestData() {
		this.service.getMyNoteList(mCurrentPage, Const.PAGESIZE, null, MCAllNoteMenuFragment.this, MCAllNoteMenuFragment.this.getActivity());
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCCourseModel courseModel = (MCCourseModel) obj;
		if (courseModel.getnCount() > 0) {
			Intent intent = new Intent(this.getActivity(), MCMyNoteActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("course", courseModel);
			intent.putExtras(bundle);
			this.startActivity(intent);
		} else {
			MCToast.show(getActivity(), "暂无笔记");
		}
	}

	@Override
	public void initAdapter() {
		this.adapter = MCNoteCommon.initCourseNoteAdapter(this.getActivity());
	}
}
