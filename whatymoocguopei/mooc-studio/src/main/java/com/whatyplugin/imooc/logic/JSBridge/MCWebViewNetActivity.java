package com.whatyplugin.imooc.logic.JSBridge;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.whatyplugin.imooc.logic.utils.WebViewUtil;

import cn.com.whatyplugin.mooc.R;

public class MCWebViewNetActivity extends Activity {

	private WebView wv_content;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.webview_activity);
		this.wv_content = (WebView) this.findViewById(R.id.wv_content);
		WebViewUtil.configWebview(wv_content);
		wv_content.setWebChromeClient(new MCCustomWebChromeClient(this));
		wv_content.setWebViewClient(new MCCustomWebViewClient(this));
		String url = this.getIntent().getExtras() == null ? "" : this.getIntent().getExtras().getString("url");
//		if (TextUtils.isEmpty(url)) {
//			wv_content.loadUrl("http://192.168.44.130/appschool/index.html");
//		} else {
//			wv_content.loadUrl(url);
//		}

		wv_content.loadUrl("http://192.168.44.130/h5/index.html");
	}

}
