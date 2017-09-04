package com.whatyplugin.imooc.ui.base;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnFooterRefreshListener;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnHeaderRefreshListener;

/**
 * 一个activity中包含listview展示页面的基类
 * 
 * @author 马彦君
 * 
 */
public abstract class MCBaseListActivity extends MCBaseActivity implements AdapterView.OnItemClickListener, MCAnalyzeBackBlock, OnFooterRefreshListener,
		OnHeaderRefreshListener {

	public MCPullToRefreshView mListView;
	public int mCurrentPage = 1;
	public QuickAdapter adapter;
	private boolean requestWhenCreate = true;// 进来就请求，如果不想请求就设置为false
	private RightClickListener rightListener;

	public void onFooterRefresh(MCPullToRefreshView view) {
		++this.mCurrentPage;
		requestData();
	}

	public void onHeaderRefresh(MCPullToRefreshView view) {
		this.mCurrentPage = 1;
		this.mListView.setNoContentVisibility();
		requestData();
	}

	public abstract void requestData();// 请求数据

	public abstract void doAfterItemClick(Object obj);// 条目点击的事件处理

	public abstract String getFunctionTitle();// 设置标题

	public abstract void initAdapter();// 初始化adapter

	/**
	 * 子类选择性重写此方法
	 *
	 * @param resultList
	 */
	public void doSomethingWithResult(List resultList) {

	}

	/**
	 * 子类有自己布局的时候重写此方法
	 *
	 * @param
	 */
	public int getRootViewId() {
		return R.layout.common_list_activity;
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
		return "列表为空";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(getRootViewId());// 一个公用的上面标题，下面listview的布局
		this.mListView = (MCPullToRefreshView) this.findViewById(R.id.mListView);

		BaseTitleView titleView = (BaseTitleView) this.findViewById(R.id.rl_titile);
		if (titleView != null) {
			titleView.setTitle(getFunctionTitle());
			if (rightListener != null) {
				titleView.setRigTextListener(rightListener);
			}
		}
		initAdapter();

		this.mListView.setOnHeaderRefreshListener(this);
		this.mListView.setOnFooterRefreshListener(this);
		this.mListView.setOnItemClickListener(this);
		this.mListView.setDataAdapter(this.adapter);

		if (requestWhenCreate) {
			initLoadingWithTitle();
			requestData();
		}
	}

	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
		removeLoading();
		doSomethingWithResult(resultList);

		// 不是链接不到网络，并且list为空
		if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE && (resultList == null || resultList.size() == 0)) {
			result.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
		}

		this.mListView.onHeaderRefreshComplete();
		this.mListView.onFooterRefreshComplete();
		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
			this.mListView.setAdapterViewWhenHasData();
			if (this.mCurrentPage == 1) {
				this.adapter.clear();
			}

			this.adapter.addAll(resultList);
			requestDataBack(resultList);
		} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
			if (this.mCurrentPage == 1) {
				this.adapter.clear();
				this.mListView.setGuidanceViewWhenNoData(getNoDataImage(), getNoDataTip());
			} else {
				this.mListView.setNoContentVisibility();
			}
		} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
			this.mListView.setGuidanceViewWhenNoNet();
		} else {
			MCLog.e("MyBaseListActivity", "错误的list返回状态");
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Object obj = parent.getAdapter().getItem(position);
		doAfterItemClick(obj);
	}

	public void setRequestWhenCreate(boolean requestWhenCreate) {
		this.requestWhenCreate = requestWhenCreate;
	}

	public void setRightListener(RightClickListener rightListener) {
		this.rightListener = rightListener;
	}

	/**
	 * 结果回来之后的回调 支持多接口调用
	 *
	 * @param resultList
	 */
	public void requestDataBack(List resultList) {

	}


}
