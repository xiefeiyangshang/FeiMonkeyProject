package com.whatyplugin.imooc.ui.note;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.model.MCMyNoteModel;
import com.whatyplugin.imooc.logic.service_.MCCourseDetailService;
import com.whatyplugin.imooc.logic.service_.MCCourseDetailServiceInterface;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;

/**
 * 笔记的fragment
 * 
 * @author 马彦君
 * 
 */
public class MCNoteListFragment extends MCBaseV4ListFragment {

	private int type;
	private String courseId;
	private MCCourseDetailServiceInterface service;
	@Override
	public int getNoDataImage() {
		return R.drawable.no_note_icon;
	}

	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return "笔记列表为空";
	}

	public static MCNoteListFragment newInstance(int type, String courseId) {
		MCNoteListFragment frag = new MCNoteListFragment();
		Bundle args = new Bundle();
		args.putInt("type", type);
		args.putString("courseId", courseId);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		type = getArguments().getInt("type");
		courseId = getArguments().getString("courseId");
		this.service = new MCCourseDetailService();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == 2) {
			mListView.headerRefreshing();
		}
	}

	@Override
	public void requestData() {
		this.service.getNoteList(courseId, mCurrentPage, Const.PAGESIZE, String.valueOf(type), null, this, this.getActivity());
	}

	@Override
	public void doAfterItemClick(Object obj) {
		Intent intent = new Intent(this.getActivity(), MCNoteDetailActivity.class);
		Bundle bundle = new Bundle();
		Serializable serializable = (Serializable) obj;
		MCMyNoteModel note = (MCMyNoteModel)serializable;
		intent.putExtra("username", note.getUserId());
		bundle.putSerializable("note", (Serializable) obj);
		intent.putExtras(bundle);
		startActivityForResult(intent, 2);
	}

	@Override
	public void initAdapter() {
		if (type == 0) {
			this.adapter = MCNoteCommon.initAllNoteAdapter(this.getActivity());
		} else if (type == 2) {
			this.adapter = MCNoteCommon.initMyNoteAdapter(this.getActivity());
		}
	}
}
