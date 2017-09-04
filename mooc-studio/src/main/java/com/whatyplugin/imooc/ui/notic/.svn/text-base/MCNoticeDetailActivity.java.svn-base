package com.whatyplugin.imooc.ui.notic;

import java.net.HttpURLConnection;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.logic.model.MCNotice;
import com.whatyplugin.imooc.logic.utils.WebViewUtil;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;

public class MCNoticeDetailActivity extends MCBaseActivity {

	private MCNotice notice;
	private ImageView iv_icon;
	private  WebView wv_notice;
	private TextView tv_time;
	private TextView tv_name;
	private TextView tv_title;
	private BaseTitleView titleView;

	/**
	 * 改成 onKeyUp 要不然 返回 fragment的时候，会有冲突
	 * 
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			myFinish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void myFinish(){
		Intent data = new Intent();
		data.putExtra("noticeId", notice.getId());
		setResult(HttpURLConnection.HTTP_OK, data);
		this.finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice_detail);
		
		initView();
	
		initData();		
	}

	private void initData() {
		Intent ext = getIntent();
		if(ext != null){
			notice = ext.getParcelableExtra("MCNotice");
		}
		tv_title.setText(notice.getTitle());
		tv_name.setText(notice.getUserName());
		tv_time.setText(notice.getUpdateDate());
		iv_icon.setVisibility("1".equals(notice.getIsTop())?View.VISIBLE:View.GONE);
		wv_notice.setVerticalScrollBarEnabled(false);
		wv_notice.setBackgroundColor(Color.TRANSPARENT);
		WebViewUtil.loadContentWithPic(notice.getNote(), wv_notice, this);
		wv_notice.setVisibility(View.VISIBLE);
	}

	private void initView() {
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_time = (TextView) findViewById(R.id.tv_time);
		wv_notice = (WebView) findViewById(R.id.wv_notice);
		this.titleView = (BaseTitleView) findViewById(R.id.rl_titile);
		this.titleView.findViewById(R.id.left_img).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myFinish();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		WebViewUtil.DestoryWebView(wv_notice);
	}
	
}
