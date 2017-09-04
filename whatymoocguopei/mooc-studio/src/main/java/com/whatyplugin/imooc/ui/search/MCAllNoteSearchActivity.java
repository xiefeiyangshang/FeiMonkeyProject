package com.whatyplugin.imooc.ui.search;

import java.io.Serializable;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.asyncimage.MCImageView;
import com.whatyplugin.base.utils.DateUtil;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCMyNoteModel;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseDetailService;
import com.whatyplugin.imooc.logic.service_.MCCourseDetailServiceInterface;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.ui.note.MCNoteDetailActivity;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnFooterRefreshListener;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnHeaderRefreshListener;

public class MCAllNoteSearchActivity extends MCBaseSearchActivity implements MCAnalyzeBackBlock, OnFooterRefreshListener, OnHeaderRefreshListener,
		AdapterView.OnItemClickListener {
	private MCCourseDetailServiceInterface service;
	private String courseId;

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
		return R.drawable.no_note_icon;
	}

	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return "笔记列表为空";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (getIntent() != null)
			courseId = getIntent().getStringExtra("courseId");
		hisKey = Contants.SEARCH_KEY_ALL_NOTE;
		this.service = new MCCourseDetailService();
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(MCAllNoteSearchActivity.this, R.layout.item_note) {

			@Override
			protected void convert(BaseAdapterHelper arg1, Object arg2) {
				this.convert(arg1, ((MCMyNoteModel) arg2));
			}

			protected void convert(BaseAdapterHelper helper, MCMyNoteModel item) {
				helper.setText(R.id.note_user_name, item.getSsoUserTrueName());
				helper.setText(R.id.note_name, item.getTitle());
				Date date = DateUtil.parseAll(item.getCreateDate());

				helper.setText(R.id.note_time, DateUtil.format(date, DateUtil.FORMAT_SHORT));
				helper.setText(R.id.note_content, item.getContent());

				if (item.isTRecommend()) {
					helper.getView(R.id.iv_recmd).setVisibility(View.VISIBLE);
				} else {
					helper.getView(R.id.iv_recmd).setVisibility(View.GONE);
				}

				MCImageView note_headimage = (MCImageView) helper.getView(R.id.note_headimage);
				note_headimage.setImageUrl(item.getPhoto());
				note_headimage.setDefaultImageResId(R.drawable.user_default_img);
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

	public void requestData() {
		this.service.getNoteList(courseId, mCurrentPage, Const.PAGESIZE, "0", this.et_search_content.getText().toString(), this, this);
	}

	@Override
	public void doAfterItemClick(Object obj) {
		Intent intent = new Intent(this, MCNoteDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("note", (Serializable) obj);
		intent.putExtras(bundle);
		startActivityForResult(intent, 2);
	}

}
