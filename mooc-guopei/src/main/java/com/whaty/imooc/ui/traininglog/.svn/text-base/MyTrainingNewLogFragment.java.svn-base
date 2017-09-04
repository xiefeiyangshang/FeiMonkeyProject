package com.whaty.imooc.ui.traininglog;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.whaty.imooc.logic.model.BlogModel;
import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.SharedClassInfo;
import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.asyncimage.MCAsyncImageDefine;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;

import cn.com.whatyguopei.mooc.R;
@SuppressLint("ValidFragment")
public class MyTrainingNewLogFragment extends MCBaseV4ListFragment {
	private GPPerformanceServiceInterface service;
	private String queryId;
	private BroadcastReceiver receiver;
	private BlogModel blogModel;

	public MyTrainingNewLogFragment(String queryId) {
		this.queryId = queryId;
	}
    private MyTrainingNewLogFragment(){}

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
	public void requestData() {
		String loginId = MCSaveData.getUserInfo(Contants.USERID, getActivity()).toString();
		service.getHotAndNewBlog(getActivity(), this.mCurrentPage, SharedClassInfo.getKeyValue(GPContants.USER_BANJIID), queryId, loginId,
				BlogModel.class, this);
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 2) {
			// 删除自身的，发送广播删除我的日志里面的
			adapter.getAdapterList().remove(blogModel);
			adapter.getAdapterList().add(0, blogModel);
			Intent intent = new Intent(GPContants.RMOTHERITEMS);
			intent.putExtra("blogId", blogModel.getId());
			getActivity().sendBroadcast(intent);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		service = new GPPerformanceService();
		super.onActivityCreated(savedInstanceState);
		// 支队最新日志做更新，新发日志不可能是热门日志
		addFilter(this.queryId);

	}

	private void addFilter(String queryId) {
		IntentFilter filter = new IntentFilter();
		filter.addAction(GPContants.RMOTHERITEMS);
		filter.addAction(GPContants.UPDATEBLOG);
		filter.addAction(Contants.USER_LOGIN_ACTION);
		if ("getNewBlog".equals(queryId)) {
			filter.addAction(GPContants.REFRESHBLOG);
		}

		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (GPContants.REFRESHBLOG.equals(action) || GPContants.REFRESHFRAGEMENT.equals(action) || Contants.USER_LOGIN_ACTION.equals(action)) {
					onHeaderRefresh(mListView);
				}
				// 删除，更新
				BlogFilter.onReceive(adapter, intent);
			}
		};
		getActivity().registerReceiver(receiver, filter);

	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(getActivity(), R.layout.hot_or_newblog_items) {
			@Override
			protected void convert(BaseAdapterHelper arg1, Object objModel) {
				BlogModel blogModel = (BlogModel) objModel;
				String uir = blogModel.getUserImg();
				MCLog.e("MyTrainingNewLogFragment","MyTrainingNewLogFragment" +uir);
				arg1.setDefImage(R.id.pic, R.drawable.user_default_img);
				arg1.setImageUrl(R.id.pic, blogModel.getUserImg(), MCCacheManager.getInstance().getImageLoader(), 53, 53, false, MCAsyncImageDefine.ImageType.CICLE_IMAGE, null);
				arg1.setText(R.id.user_name, blogModel.getUserName());
				arg1.setText(R.id.blog_name, blogModel.getTitle());
				arg1.setText(R.id.blog_time, blogModel.getUpdateDate());
				arg1.setText(R.id.like_blog_num, blogModel.getLikeNum());
				arg1.setText(R.id.watch_num, blogModel.getViewNum());

			}
		};

	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();
	}

}
