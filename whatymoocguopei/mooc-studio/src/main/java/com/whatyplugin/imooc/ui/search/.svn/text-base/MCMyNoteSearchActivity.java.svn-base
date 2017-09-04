package com.whatyplugin.imooc.ui.search;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.service_.MCCourseDetailService;
import com.whatyplugin.imooc.logic.service_.MCCourseDetailServiceInterface;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.ui.note.MCMyNoteActivity;
import com.whatyplugin.imooc.ui.note.MCNoteCommon;
import com.whatyplugin.uikit.toast.MCToast;

public class MCMyNoteSearchActivity extends MCBaseSearchActivity {
	private MCCourseDetailServiceInterface service;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		hisKey = Contants.SEARCH_KEY_MY_NOTE_COURSE;
		this.service = new MCCourseDetailService();
		super.onCreate(savedInstanceState);
	}

	public void requestData() {
		String condition = this.et_search_content == null ? "" : this.et_search_content.getText().toString();
		this.service.getMyNoteList(mCurrentPage, Const.PAGESIZE, condition, this, this.mContext);
	}
	
	@Override
	public void doSomethingWithResult(List resultList) {
		MCNoteCommon.removeZeroCountNoteFromList(resultList);
		super.doSomethingWithResult(resultList);
	}
	
	@Override
	public void initAdapter() {
		this.adapter = MCNoteCommon.initCourseNoteAdapter(this);
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCCourseModel courseModel = (MCCourseModel) obj;
		if (courseModel.getnCount() > 0) {
			Intent intent = new Intent(this, MCMyNoteActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("course", courseModel);
			intent.putExtras(bundle);
			this.startActivity(intent);
		} else {
			MCToast.show(this, "暂无笔记");
		}
	}

	@Override
	public String getFunctionTitle() {
		return "";
	}

}
