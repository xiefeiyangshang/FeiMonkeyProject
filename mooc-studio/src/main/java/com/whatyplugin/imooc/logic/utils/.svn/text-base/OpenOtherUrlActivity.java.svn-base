package com.whatyplugin.imooc.logic.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.openotherurl.MCMoreWebViewFragment;
import com.whatyplugin.uikit.toast.MCToast;

public class OpenOtherUrlActivity extends MCBaseActivity {

	private WebView wb_otherUrl;
	private String url;
	private ImageView iv_Back;
	private TextView tv_Close;
	private SeekBar seekBar;
	private ImageView iv_MoreMenu;
	private OnClickListener openOtherUrlListener;
	private OnClickListener copyOnListener;
	private OnClickListener ReloadListener;
	private MCMoreWebViewFragment diaLog;
	private List<String> listUrl;
	private MCWebViewClient client;
	private InputMethodManager imm;
	private TextView tv_WebviewTitle;
	

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_webview_pictxt);
		url = getIntent().getStringExtra("url");
		initView();
		initData();
		initEntve();
	}

	class MCWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return false;
		}
		

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		
			imm.hideSoftInputFromWindow(wb_otherUrl.getApplicationWindowToken(), 0);
			 return !webViewBack()?super.onKeyDown(keyCode, event):true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initEntve() {
		iv_Back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				NotWebViewBack();

			}
		});
		tv_Close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		openOtherUrlListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				WebViewUtil.openSystemWebView(getUrl());
				dismiss();
			}
		};
		ReloadListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				wb_otherUrl.reload();
				dismiss();
			}
		};

		copyOnListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				clip.setText(getUrl()); // 复制
				MCToast.show(getApplicationContext(), "已复制到粘贴板");
				dismiss();
			}
		};

		iv_MoreMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				diaLog = new MCMoreWebViewFragment(openOtherUrlListener, copyOnListener, ReloadListener);
				diaLog.show(MCCreateDialog.getFragmentTransaction(OpenOtherUrlActivity.this), "showMore");
			}
		});
	
	}

	private void initData() {
		imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		wb_otherUrl.loadUrl(url);
		listUrl = new ArrayList<String>();
		listUrl.add(url);
		WebSettings wSet = wb_otherUrl.getSettings();
		wSet.setJavaScriptEnabled(true);
		 client = new MCWebViewClient();
		wb_otherUrl.setWebViewClient(client);
		
		
		
		wb_otherUrl.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				boolean isDone = newProgress == 100;
				seekBar.setVisibility(isDone ? View.INVISIBLE : View.VISIBLE);
				seekBar.setProgress(isDone ? 0 : newProgress);
			}
			@Override
			public void onReceivedTitle(WebView view, String title) {
				tv_WebviewTitle.setText(title);
			}

		});

	}

	private void NotWebViewBack() {
		if (!webViewBack()) {
			finish();
		}
	}

	private boolean webViewBack() {
		boolean CanGoBack =wb_otherUrl.canGoBack();
		if (CanGoBack) {
			wb_otherUrl.goBack();
		}

		return CanGoBack;

	}

	private void initView() {
		wb_otherUrl = (WebView) findViewById(R.id.wb_pictxt);
		iv_Back = (ImageView) findViewById(R.id.go_back);
		tv_Close = (TextView) findViewById(R.id.close);
		seekBar = (SeekBar) findViewById(R.id.upgrade_progress);
		iv_MoreMenu = (ImageView) findViewById(R.id.more_menu);
		tv_WebviewTitle = (TextView) findViewById(R.id.webview_title);
	}

	private String getUrl() {
		return wb_otherUrl.getUrl();

	}

	private void dismiss() {
		diaLog.dismiss();
	}

	@Override
	protected void onDestroy() {
		wb_otherUrl.destroy();
		super.onDestroy();
	}

}
