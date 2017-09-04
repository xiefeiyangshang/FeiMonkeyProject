package com.whaty.imooc.ui.workshop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.whaty.imooc.logic.model.ThemeListModel;
import com.whaty.imooc.logic.model.WorkShopModel;
import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whaty.imooc.utile.GPStringUtile;
import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.imooc.logic.model.MCForumModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.ui.base.MCBaseListActivity;
import com.whatyplugin.imooc.ui.themeforum.MCForumCommon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.whatyguopei.mooc.R;

public class themeWorkShopListActivity extends MCBaseListActivity {
	private WorkShopModel workShopModel;
	private GPPerformanceServiceInterface service;
	private BroadcastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		workShopModel = (WorkShopModel) getIntent().getSerializableExtra("workShopModel");
		service = new GPPerformanceService();
		super.onCreate(savedInstanceState);
		getReceiverFiler();
	}

	/**
	 * 添加数据的
	 * 
	 */
	private void getReceiverFiler() {
		IntentFilter filter = new IntentFilter();

		receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String itemsId = intent.getStringExtra("id");
				for (int i = 0; i < adapter.getCount(); i++) {
					ThemeListModel model = (ThemeListModel) adapter.getAdapterList().get(i);
					if (model.getId().equals(itemsId)) {
						model.setTieziNum(model.gettieziNumAndOne());
						break;
					}

				}
				adapter.notifyDataSetChanged();

			}
		};
		filter.addAction(MCForumCommon.UPDATETHEMENUM);
		this.registerReceiver(receiver, filter);
	}

	@Override
	public void requestData() {
		GPStringUtile.ThemeListNum = mCurrentPage == 1 ? 1 : GPStringUtile.ThemeListNum;
		service.getThemeList(this, this.mCurrentPage, workShopModel.getActivityId(), this);
	}

	@Override
	public void doAfterItemClick(Object obj) {

		ThemeListModel themeListModel = (ThemeListModel) obj;
		// 只有主题列表类型才能点击进去
		if (themeListModel.getIsType()) {
			MCForumModel forumModel = new MCForumModel();
			forumModel.setNodeId(themeListModel.getId()); // 用于获取 评论列表
			forumModel.setState(themeListModel.getState()); // 临时设置成都可用
			forumModel.setBody(themeListModel.getActivityDetail());
			forumModel.setForumName(themeListModel.getName());
			forumModel.setStartAndEndTime(themeListModel.getStartAndEndTime());
			forumModel.setReplyNum(themeListModel.getTieziNum());
			forumModel.setTopicNum(themeListModel.getJinghuaNum());
			forumModel.setHint(themeListModel.getHint());
			Intent intent = new Intent(this, WorkShopInfoActivity.class);
			intent.putExtra("ForumModel", forumModel);
			intent.putExtra("courseId", "");
			startActivity(intent);
		} else {
			new MCCreateDialog().createOkDialog(themeWorkShopListActivity.this, "请到PC 端上传~");
		}
	}

	@Override
	public String getFunctionTitle() {
		return workShopModel.getWorkTitle();
	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(this, R.layout.workshopactivity_items) {

			@Override
			protected void convert(BaseAdapterHelper arg1, Object objModel) {
				ThemeListModel mdoel = (ThemeListModel) objModel;
				arg1.setText(R.id.title, mdoel.getName());

				arg1.setText(R.id.time, mdoel.getStartAndEndTime());
				// 全部gone掉 防止绘制时代码复用 产生奇怪的效果
				arg1.setVisible(R.id.rl_recourse, false);
				arg1.setVisible(R.id.rl_replynum, false);

				if (mdoel.getIsType()) {
					arg1.setVisible(R.id.rl_replynum, true);
					arg1.setText(R.id.jianghuanum, mdoel.getJinghuaNum());
					arg1.setText(R.id.replynum, mdoel.getTieziNum());
					arg1.setText(R.id.discuss, mdoel.isOverdue() ? "查看" : "去讨论");
				} else {
					arg1.setVisible(R.id.rl_recourse, true);
					arg1.setText(R.id.resourcenum, mdoel.getResourceNum());
				}
				arg1.setText(R.id.them_num, mdoel.getThemeNum());

			}
		};

	}

	public void requestDataBack(final List list) {
		if (list == null || list.size() < 1) {
			return;
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			ThemeListModel model = ((ThemeListModel) list.get(i));
			// 变态三元表达式 如果是资源 就不加 如果不是最后一个就加上 ‘，’ 拼接传给后方
			sb.append(model.getIsType() ? model.getId() + ((i + 1) != list.size() ? "','" : "") : "");

		}
		service.getThemeListReplyNum(this, sb.toString(), new MCAnalyzeBackBlock() {
			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultListBack) {
				if (resultListBack != null && resultListBack.size() > 0) {
					// listToMap 这就几个MODEL 不会超过10个 我这就在这里循环吧
					Map<String, ThemeListModel> ThemeMap = listToMap(resultListBack);
					for (int i = 0; i < list.size(); i++) {
						ThemeListModel model = ((ThemeListModel) list.get(i));
						if (model.getIsType() && ThemeMap.containsKey(model.getId())) {
							model.setJinghuaNum(ThemeMap.get(model.getId()).getJinghuaNum());
							model.setTieziNum(ThemeMap.get(model.getId()).getTieziNum());
						}
					}
					// 刷新当前数据
					adapter.notifyDataSetChanged();
				}
			}
		});

	}

	private Map<String, ThemeListModel> listToMap(List list) {
		Map<String, ThemeListModel> map = new HashMap<String, ThemeListModel>();
		for (int i = 0; i < list.size(); i++) {
			ThemeListModel model = (ThemeListModel) list.get(i);
			map.put(model.getThemeId(), model);
		}

		return map;

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

}
