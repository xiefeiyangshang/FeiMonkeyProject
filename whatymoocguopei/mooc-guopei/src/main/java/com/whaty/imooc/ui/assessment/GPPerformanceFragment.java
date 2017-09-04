package com.whaty.imooc.ui.assessment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.whaty.imooc.ui.index.GPRequestUrl;
import com.whaty.imooc.utile.GPContants;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.ui.base.MCBaseV4Fragment;

import cn.com.whatyguopei.mooc.R;

public class GPPerformanceFragment extends MCBaseV4Fragment {
	private WebView webView;
	private SwipeRefreshLayout swipeRefresh;
	private IntentFilter filter;
	private BroadcastReceiver broadcastReceiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.performance_layout, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		filter = new IntentFilter();
		filter.addAction(GPContants.REFRESHFRAGEMENT);
		filter.addAction(Contants.USER_LOGIN_ACTION);
		broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// 重新登录的时候 从新登录
				webViewLoad();
			}
		};
		getActivity().registerReceiver(broadcastReceiver, filter);
		webView = (WebView) this.getActivity().findViewById(R.id.wb);
		webView.loadUrl(getUrl());
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(false);
		swipeRefresh = (SwipeRefreshLayout) this.getActivity().findViewById(R.id.swipe_container);
		swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				webViewLoad();
			}
		});

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				swipeRefresh.setRefreshing(!(newProgress == 100));
			}
			

		});
		
	
	}

	private void webViewLoad() {
		webView.loadUrl(getUrl());
	}

	private String getUrl() {
		String loginId = MCSaveData.getUserInfo(Contants.USERID, getActivity()).toString();

		StringBuilder Sb = new StringBuilder();
		Sb.append(GPRequestUrl.getInstance().GET_PERFORMANCE);
		Sb.append(GPRequestUrl.getInstance().getClassId());
		Sb.append("&homeCourseId=");
		Sb.append(GPRequestUrl.getInstance().getHomeWorkCourseId());
		Sb.append("&loginId=");
		Sb.append(loginId);
		return Sb.toString();
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}

}
