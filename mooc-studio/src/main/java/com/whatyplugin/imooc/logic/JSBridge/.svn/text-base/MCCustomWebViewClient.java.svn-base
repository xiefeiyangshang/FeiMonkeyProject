package com.whatyplugin.imooc.logic.JSBridge;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class MCCustomWebViewClient extends WebViewClient {

	private Context aa = null;

	MCCustomWebViewClient(Context aas) {
		aa = aas;
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		view.loadUrl("javascript:android_app_ready()");
		super.onPageFinished(view, url);
	}
}
