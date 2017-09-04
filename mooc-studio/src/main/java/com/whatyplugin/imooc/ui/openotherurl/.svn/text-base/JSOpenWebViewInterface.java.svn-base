package com.whatyplugin.imooc.ui.openotherurl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.whatyplugin.imooc.logic.utils.OpenOtherUrlActivity;
import com.whatyplugin.imooc.logic.utils.WebViewUtil;

public class JSOpenWebViewInterface {
	private Activity activity;

	public JSOpenWebViewInterface() {
		super();
	}

	public JSOpenWebViewInterface(Activity activity) {
		this.activity = activity;
	}

	@JavascriptInterface
	public void openWebView(String urlStr) {
		if (TextUtils.isEmpty(urlStr)) {
			return;
		}
		if (isDownLoad(urlStr)) {
			openSystemWebView(urlStr);
		} else {
			openMoocWebView(urlStr);
		}
	}

	private  void openMoocWebView(String urlStr) {
		Intent intent = new Intent(activity, OpenOtherUrlActivity.class);
		intent.putExtra("url", urlStr);
		activity.startActivity(intent);
	}

	public  void openSystemWebView(String urlStr) {
		Uri uri = Uri.parse(urlStr);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		activity.startActivity(intent);
	}

	public static boolean isDownLoad(String urlStr) {
		StringBuffer sb = new StringBuffer();
		sb.append("\\.((");
		sb.append(WebViewUtil.regexString.replace("|", ")|("));
		sb.append("))$");

		Pattern pattern = Pattern.compile(sb.toString());
		Matcher matcher = pattern.matcher(urlStr);
		Boolean find = matcher.find();
		return find;
	}
}
