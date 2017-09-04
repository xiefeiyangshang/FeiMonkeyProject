package com.whaty.imooc.ui.homework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whaty.imooc.ui.index.GPRequestUrl;
import com.whaty.imooc.utile.GPContants;
import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCHomeworkModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;
import com.whatyplugin.imooc.ui.homework.MCHomeworkCommon;
import com.whatyplugin.imooc.ui.view.ScoreWithPicView;

import java.util.List;

import cn.com.whatyplugin.mooc.R;

public class GPHomeWorkMianFragment extends MCBaseV4ListFragment {

	private String courseId;
	private GPPerformanceServiceInterface service;
	private String loginId;
	private MCStudyServiceInterface studyService;
	private BroadcastReceiver receiver;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		loginId = MCSaveData.getUserInfo(Contants.USERID, getActivity()).toString();
		courseId = GPRequestUrl.getInstance().getHomeWorkCourseId();
		this.service = new GPPerformanceService();
		this.studyService = new MCStudyService();
		super.onActivityCreated(savedInstanceState);
		// 禁止下拉刷新
		mListView.setAllowFooterPull(false);
		IntentFilter filter = new IntentFilter();
		filter.addAction(Contants.USER_LOGIN_ACTION);
		filter.addAction(GPContants.REFESHHOMEWORK);
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				onHeaderRefresh(mListView);
			}
		};
		getActivity().registerReceiver(receiver, filter);

	}

	@Override
	public void requestData() {
		service.getHomeWorkList(getActivity(), this);
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCHomeworkModel homeWork =((MCHomeworkModel)obj);
		int status = homeWork.getLocalStatus();
		if (status == 0) {
			alert("请在PC端 做作业吧！");
		} else if (status == 4) {
			alert("作业被驳回 请到PC端重新做吧！");
		}
		
		
//		MCHomeworkModel model = (MCHomeworkModel) obj;
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("homework", model);
//		boolean doHomeWork = model.getLocalStatus() == 0;
//		Class startClass =doHomeWork ? MCHomeworkCommitActivity.class : GPHomeWorkDatileActivity.class;
//		Intent intent = new Intent(getActivity(), startClass);
//		intent.putExtras(bundle);
//		startActivityForResult(intent, doHomeWork ? MCHomeworkCommon.DO_HOMEWORK_CODE : MCHomeworkCommon.DETAIL_HOMEWORK_CODE);
	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(getActivity(), R.layout.homework_item_layout) {

			protected void convert(BaseAdapterHelper helper, final MCHomeworkModel item) {

				helper.setText(R.id.tjtime_label, getString(R.string.homework_commit_time, new Object[] { item.getStartDate(), item.getEndDate() }));
				helper.getView(R.id.title).setVisibility(View.VISIBLE);
				// 子条目内容的填充在这里进行。
				TextView title = (TextView) helper.getView(R.id.title_label);
				TextView operate = (TextView) helper.getView(R.id.operate_lable);
				ImageView view = (ImageView) helper.getView(R.id.enter_img);

				title.setText(item.getTitle());

				// 默认是深颜色的
				title.setTextColor(getResources().getColor(R.color.font_black_color));
				view.setVisibility(View.VISIBLE);

				helper.setVisible(R.id.hp_img, false);
				helper.setVisible(R.id.hptime_label, false);
				operate.setTextColor(getResources().getColor(R.color.theme_color));
				operate.setTextSize(12);
				helper.getView(R.id.chengjichulai).setVisibility(View.GONE);
				// 0 去做作业
				// 1 查看作业 - 有草稿（可以查看，选择重做）
				// 2 没有标记 ,已提交，未批改
				// 3 成绩已出
				// 4 作业被驳回
				// 5作业已过时的
				// 6不应该出现的状态

				switch (item.getLocalStatus()) {
				case 0:
					helper.getView(R.id.status_img).getBackground().setLevel(0);
					operate.setText("去做作业");
					view.setImageResource(R.drawable.homework_enter);
					break;
				case 1:
					helper.getView(R.id.status_img).getBackground().setLevel(0);
					operate.setText("提交作业");
					view.setImageResource(R.drawable.homework_enter);
					break;
				case 2:
					helper.getView(R.id.status_img).getBackground().setLevel(0);
					operate.setText("待批改");
					view.setVisibility(View.INVISIBLE);  //待批改的改成 去掉后面的对勾
					break;
				case 3:
					helper.getView(R.id.title).setVisibility(View.GONE);
					helper.getView(R.id.chengjichulai).setVisibility(View.VISIBLE);
					helper.getView(R.id.status_exam_img).getBackground().setLevel(0);
					((ScoreWithPicView)helper.getView(R.id.score_content)).setScore(Integer.valueOf(item.getTotalScore()), 0);
					helper.setText(R.id.tjtime, getString(R.string.homework_commit_time, new Object[] { item.getStartDate(), item.getEndDate() }));
					helper.setText(R.id.title_exam, item.getTitle());
					break;
				case 4:
					helper.getView(R.id.status_img).getBackground().setLevel(0);
					operate.setText("作业被驳回");
					view.setImageResource(R.drawable.homework_enter);
					break;
				case 5:
					helper.getView(R.id.status_img).getBackground().setLevel(2);
					operate.setText(item.isOverTime() ? "已过期" : "未到时间");
					operate.setTextColor(getResources().getColor(R.color.time_text_color));
					view.setVisibility(View.INVISIBLE);// 不显示图片
					title.setTextColor(getResources().getColor(R.color.font_gray_color));
					break;
				case 6:
					operate.setText("");
					break;

				default:
					break;
				}

			}

			protected void convert(BaseAdapterHelper helper, Object model) {
				this.convert(helper, ((MCHomeworkModel) model));
			}
		};
	}
//	
	private void alert(String digMsg) {
		new MCCreateDialog().createOkDialog(getActivity(), digMsg);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 课程详情里面重做作业了之后保存 草稿 之后更新数据
		if (requestCode == MCHomeworkCommon.DETAIL_HOMEWORK_CODE && resultCode == MCHomeworkCommon.SAVE_LOCAL_HOMEWORK_RESULT) {
			MCHomeworkModel homeworkModel = (MCHomeworkModel) data.getSerializableExtra("homeworkModel");
			this.adapter.replaceModel(homeworkModel);
		}
		// 草稿变动后更新数据
		if (requestCode == MCHomeworkCommon.DO_HOMEWORK_CODE && resultCode == MCHomeworkCommon.SAVE_LOCAL_HOMEWORK_RESULT) {
			MCHomeworkModel homeworkModel = (MCHomeworkModel) data.getSerializableExtra("homeworkModel");
			this.adapter.replaceModel(homeworkModel);

		}
		// 提交作业后的数据更新
		if (requestCode == MCHomeworkCommon.DO_HOMEWORK_CODE && resultCode == MCHomeworkCommon.COMMIT_HOMEWORK_RESULT) {
			this.mCurrentPage = 1;
			requestData();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void doSomethingWithResult(List resultList) {
		MCHomeworkCommon.replaceHomeworkWithLocal(studyService, courseId, loginId, (List<MCHomeworkModel>) resultList, getActivity());
	}



}
