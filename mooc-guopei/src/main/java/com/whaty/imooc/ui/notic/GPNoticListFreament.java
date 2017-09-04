package com.whaty.imooc.ui.notic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.widget.TextView;

import com.whaty.imooc.logic.model.GPNoticModel;
import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCNotice;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;
import com.whatyplugin.imooc.ui.notic.MCNoticeDetailActivity;

import java.net.HttpURLConnection;

import cn.com.whatyguopei.mooc.R;

public class GPNoticListFreament extends MCBaseV4ListFragment {
	private GPPerformanceServiceInterface service;
	private BroadcastReceiver receiver;

	@Override
	public void requestData() {
		service.getNoticList(getActivity(), this.mCurrentPage, this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		service = new GPPerformanceService();
		super.onCreate(savedInstanceState);
		IntentFilter intent = new IntentFilter();
		intent.addAction(Contants.USER_LOGIN_ACTION);
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				onHeaderRefresh(mListView);

			}
		};
		getActivity().registerReceiver(receiver, intent);

	}

	/**
	 * 三件事情 第一 ：发送 +1 请求 浏览次数加1 第二： 跳转到详情界面 在详情界面进行请求 发送服务器更新浏览次数 第三 把
	 * 未读状态改成已读状态
	 * 
	 */
	@Override
	public void doAfterItemClick(Object obj) {
		GPNoticModel gpNotic = (GPNoticModel) obj;
		Intent intent = new Intent(getActivity(), MCNoticeDetailActivity.class);
		// 如果是未读改为已读。。已读的话就节约资源不再去请求
		if (gpNotic.isRead()) {
			service.setRead(getActivity(), gpNotic.getId(), gpNotic.getType(), this);
		}
		// 进行网络加1
		service.setNoticAndOne(getActivity(), gpNotic.getId(), gpNotic.getType(), String.valueOf(gpNotic.getViewCount()), this);
		// 这里赋值 为了使用以前的类
		MCNotice mcNotice = new MCNotice();
		mcNotice.setTitle(gpNotic.getName());
		mcNotice.setId(gpNotic.getId());
		mcNotice.setUpdateDate(gpNotic.getTime());
		mcNotice.setUserName(gpNotic.getAuthor());
		mcNotice.setNote(gpNotic.getContent());
		mcNotice.setIsTop(gpNotic.isTop() ? "1" : "0");
		intent.putExtra("MCNotice", mcNotice);
//		MCNoticeDetailActivity.middTitle =gpNotic.getTitle();
		// 点击进去的时候 就进行更新 浏览次数 。返回的时候进行刷新当前listview
		gpNotic.setRead(false); // 未读改为已读
		startActivityForResult(intent, 0);
	}

	/**
	 * 返回的时候 重新刷新当前界面 做到+1 的显示效果
	 * 
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 没有必要循环查找进行更新
		if (HttpURLConnection.HTTP_OK == resultCode) {
			// 刷新试图
			adapter.notifyDataSetChanged();
		}

	}

	@Override
	public void initAdapter() {

		final ImageGetter imgGetter = new ImageGetter() {
			@Override
			public Drawable getDrawable(String source) {
				Drawable drawable = GPNoticListFreament.this.getResources().getDrawable(R.drawable.img_ico);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth() + 10, drawable.getIntrinsicHeight() + 10);
				return drawable;
			}
		};
		this.adapter = new QuickAdapter(getActivity(), R.layout.gp_notic_items) {
			@Override
			protected void convert(BaseAdapterHelper arg1, Object objModel) {
				GPNoticModel value = (GPNoticModel) objModel;
				arg1.setText(R.id.tv_noread, value.getNoRead());
				arg1.setText(R.id.tv_title, value.getName());
				arg1.setText(R.id.tv_time, value.getTime());
				arg1.setText(R.id.tv_look_count, value.getStrViewCount());
				arg1.setVisible(R.id.iv_top, value.isTop());
				arg1.setVisible(R.id.iv_isread, false); // 后来修改的
				// 添加小图片
				CharSequence test = Html.fromHtml(value.getContent(), imgGetter, null);
				String str = new String(test+"");
//				MCLog.e("GPNoticListFreament CharSequence","GPNoticListFreament CharSequence test = "+str.replaceAll("\n", ""));

				((TextView) arg1.getView(R.id.tv_desc)).setText(str.replaceAll("\n",""));
			}
		};

	}
	
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();
	}

}
