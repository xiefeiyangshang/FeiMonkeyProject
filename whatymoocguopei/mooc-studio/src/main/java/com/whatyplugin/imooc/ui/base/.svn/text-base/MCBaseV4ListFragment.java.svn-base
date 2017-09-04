package com.whatyplugin.imooc.ui.base;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.ui.view.MCTipManager;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnFooterRefreshListener;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnHeaderRefreshListener;

public abstract class MCBaseV4ListFragment extends MCBaseV4Fragment implements MCAnalyzeBackBlock, OnFooterRefreshListener, OnHeaderRefreshListener,
		OnItemClickListener {
	public QuickAdapter adapter;
	public MCPullToRefreshView mListView;
	public int mCurrentPage = 1;
	public View mView;
	private BroadcastReceiver mBroadcastRecevier;
	private List<String> actionList;
	public abstract void requestData();// 请求数据

	public abstract void doAfterItemClick(Object obj);// 条目点击的事件处理

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
	 */
	public int getRootViewId() {
		return R.layout.common_list_fragment;
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
	
	public void onActivityCreated(Bundle savedInstanceState) {
		initAdapter();
		this.mListView.setDataAdapter(this.adapter);
		initLoadingNoTitle();//这个要在上面，因为如果有的操作不是异步请求的就会导致进度条不能消失。 
		requestData();
		
		// 注册广播，包括网络状态的、登陆、退出的。
		if (actionList != null && actionList.size() > 0) {
			this.mBroadcastRecevier = new BroadcastReceiver() {
				public void onReceive(Context context, Intent intent) {
					if (actionList.contains(Contants.USER_LOGIN_ACTION) && intent.getAction().equals(Contants.USER_LOGIN_ACTION)) {
						mListView.headerRefreshing();
					} else if (actionList.contains(Contants.USER_LOGOUT_ACTION) && intent.getAction().equals(Contants.USER_LOGOUT_ACTION)) {
						mListView.headerRefreshing();
					} else if (actionList.contains(Contants.NETWORK_CHANGED_ACTION) && intent.getAction().equals(Contants.NETWORK_CHANGED_ACTION)) {
						if (adapter.getAdapterList().size() == 0) {
							mListView.headerRefreshing();
						}
					}
				}
			};
			IntentFilter intentFilter = new IntentFilter();
			for (String action : actionList) {
				intentFilter.addAction(action);
			}
			this.getActivity().registerReceiver(this.mBroadcastRecevier, intentFilter);
		}
		super.onActivityCreated(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(getRootViewId(), null);
		this.mListView = (MCPullToRefreshView) view.findViewById(R.id.mListView);
		this.mListView.setOnItemClickListener(this);
		this.mListView.setOnHeaderRefreshListener(this);
		this.mListView.setOnFooterRefreshListener(this);
		this.mView = view;
		return view;
	}

	public void onFooterRefresh(MCPullToRefreshView view) {
		++this.mCurrentPage;
		requestData();
	}

	public void onHeaderRefresh(MCPullToRefreshView view) {
		this.mCurrentPage = 1;
		requestData();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Object obj = parent.getAdapter().getItem(position);
		doAfterItemClick(obj);
	}

	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
		removeLoading();
		doSomethingWithResult(resultList);
		
		// 不是链接不到网络，并且list为空 resultList.get(0)==null 这个判断 请求成功，登陆状态不行
		if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE && (resultList == null || resultList.size() == 0||resultList.get(0)==null)) {
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
			MCLog.e("MCBaseV4ListFragment", "错误的list返回状态");
		}
	}

	/**
	 * 有标题的用这个产生正在加载
	 */
	public void initLoadingWithTitle() {
		MCTipManager.initLoading(this.getActivity(), this.toString() ,mView, 1);
	}

	/**
	 * 没有标题的用这个产生正在加载
	 */
	public void initLoadingNoTitle() {
		MCTipManager.initLoading(this.getActivity(), this.toString() ,mView, 0);
	}

	/**
	 * 移除正在加载，常用在网络请求回来的时候第一时间调用
	 */
	public void removeLoading() {
		MCTipManager.removeLoading(this.toString());
	}
	@Override
	public void onDestroy() {
		if (this.mBroadcastRecevier != null) {
			this.getActivity().unregisterReceiver(this.mBroadcastRecevier);
		}
		super.onDestroy();
	}
	
	public void setActionList(List<String> actionList) {
		this.actionList = actionList;
	}
	public List<String> getActionList(){
		return actionList;
	} 
}
