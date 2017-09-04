package com.whaty.imooc.ui.workshop;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.whaty.imooc.logic.model.WorkShopModel;
import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;

import cn.com.whatyguopei.mooc.R;
@SuppressLint("ValidFragment")
public class WorkShopMainFragment extends MCBaseV4ListFragment {
	private GPPerformanceServiceInterface service;
	private BroadcastReceiver receiver;
	private String type; // 0 代表工作坊活动 1 代表校本研修

	public WorkShopMainFragment(String type) {
		this.type = type;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		service = new GPPerformanceService();
		super.onActivityCreated(savedInstanceState);
		// 接口给的不合适 禁止刷新
		this.mListView.setAllowFooterPull(false);
		this.mListView.setAllowHeaderPull(true);
		IntentFilter filter = new IntentFilter();
		filter.addAction(Contants.USER_LOGIN_ACTION);
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
			if("0".equals(type)){
				onHeaderRefresh(mListView);
			}}
		};
		getActivity().registerReceiver(receiver, filter);

	}

	@Override
	public void requestData() {
		service.getWorkShopList(getActivity(), type, this);
	}

	@Override
	public void doAfterItemClick(Object obj) {

	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(getActivity(), R.layout.workshop_items) {

			@Override
			protected void convert(BaseAdapterHelper arg1, Object objModel) {
				final WorkShopModel model = (WorkShopModel) objModel;
				arg1.setVisible(R.id.workshop, model.getIsWorkShop());
				arg1.setVisible(R.id.activity, true);
				if (model.getIsWorkShop()) {
					arg1.setText(R.id.title, model.getTitle());
					arg1.setText(R.id.worknum, model.getWorkNum() + "");
				}

				if (model.getWorkTitle().equals("")) {
					arg1.setText(R.id.worknum, "0");
					arg1.setVisible(R.id.activity, false);
				} else {
					arg1.getView(R.id.activity).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(getActivity(), themeWorkShopListActivity.class);
							Bundle extras = new Bundle();
							extras.putSerializable("workShopModel", model);
							intent.putExtras(extras);
							startActivity(intent);
						}
					});

					arg1.setText(R.id.activitytype, model.getWorkType());
					arg1.setText(R.id.tv_worktitle, model.getWorkTitle());
					arg1.setText(R.id.time, model.getTime());
				}
			}
		};

	}

}
