package com.whaty.imooc.ui.traininglog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.whaty.imooc.logic.model.BlogModel;
import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whaty.imooc.utile.GPContants;
import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;

import cn.com.whatyguopei.mooc.R;

public class myBlogFragment extends MCBaseV4ListFragment {
	private GPPerformanceServiceInterface service;
	private String CODE = "getBlogArticle";
	private BlogModel blogModel;
	private BroadcastReceiver receiver;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		service = new GPPerformanceService();
		super.onActivityCreated(savedInstanceState);
		addFilter();
	}

	private void addFilter() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(GPContants.REFRESHBLOG);
		intentFilter.addAction(GPContants.RMOTHERITEMS);
		intentFilter.addAction(GPContants.UPDATEBLOG);
		intentFilter.addAction(Contants.USER_LOGIN_ACTION);
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (GPContants.REFRESHBLOG.equals(action)||GPContants.REFRESHFRAGEMENT.equals(action)||Contants.USER_LOGIN_ACTION.equals(action)) {
					onHeaderRefresh(mListView);
				} 
				// 删除，更新
				BlogFilter.onReceive(adapter, intent);
			}
		};
		getActivity().registerReceiver(receiver, intentFilter);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 重新返回到这个页面刷新 返回值 ==2 就代表 被干掉了 广播通知其他地方
		if (resultCode == 2) {
			// 先删除 再放到第一位 减少遍历次数提高代码效率
			adapter.getAdapterList().remove(blogModel);
			adapter.getAdapterList().add(0, blogModel);
			Intent intent = new Intent(GPContants.RMOTHERITEMS);
			intent.putExtra("blogId", blogModel.getId());
			getActivity().sendBroadcast(intent);
		}

	}

	@Override
	public void requestData() {
		service.getHotAndNewBlog(getActivity(), this.mCurrentPage, null, CODE, getLoginId(), BlogModel.class, this);

	}

	@Override
	public void doAfterItemClick(Object obj) {
		Intent intent = new Intent(getActivity(), BlogInfoActivity.class);
		// 传过去当前的条目
		blogModel = (BlogModel) obj;
		blogModel.setViewNum(blogModel.getAndOneViewCount());
		intent.putExtra("blogModel", blogModel);
		startActivityForResult(intent, 0);
	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(getActivity(), R.layout.my_blog_items) {

			@Override
			protected void convert(BaseAdapterHelper arg1, Object objModel) {
				BlogModel model = (BlogModel) objModel;
				arg1.setText(R.id.title, model.getTitle());
				arg1.setText(R.id.time, model.getUpdateDate());
				arg1.setText(R.id.like_blog_num, model.getLikeNum());
				arg1.setText(R.id.watch_num, model.getViewNum());

			}
		};

	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();
	}

	private String getLoginId() {
		return MCSaveData.getUserInfo(Contants.USERID, getActivity()).toString();
	}
}
