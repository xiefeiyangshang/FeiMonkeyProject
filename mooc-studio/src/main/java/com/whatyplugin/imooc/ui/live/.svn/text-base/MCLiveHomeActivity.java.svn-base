package com.whatyplugin.imooc.ui.live;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;

import cn.com.whatyplugin.mooc.R;

public class MCLiveHomeActivity extends MCBaseActivity {
	private WebView wb_LiveHome;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mclivehomeactivity);
		url = getIntent().getStringExtra("url");
		MCLog.e("URL==  ", url);
		wb_LiveHome = (WebView) findViewById(R.id.live_home);
		WebSettings settings = wb_LiveHome.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(false);

//		  webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

	        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

//	        progressBar = ProgressDialog.show(Main.this, "WebView Example", "Loading...");

	        wb_LiveHome.setWebViewClient(new WebViewClient() {
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//	                Log.i(TAG, "Processing webview url click...");
	                view.loadUrl(url);
	                return true;
	            }

	            public void onPageFinished(WebView view, String url) {
//	                Log.i(TAG, "Finished loading URL: " +url);
//	                if (progressBar.isShowing()) {
//	                    progressBar.dismiss();
//	                }
	            }

	            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//	                Log.e(TAG, "Error: " + description);
//	                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
	                alertDialog.setTitle("Error");
	                alertDialog.setMessage(description);
	                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        return;
	                    }
	                });
	                alertDialog.show();
	            }
	        });
	        wb_LiveHome.loadUrl(url);
	}

}
