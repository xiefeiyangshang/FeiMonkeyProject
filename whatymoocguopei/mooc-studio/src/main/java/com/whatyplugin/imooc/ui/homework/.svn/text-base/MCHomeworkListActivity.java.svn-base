package com.whatyplugin.imooc.ui.homework;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCHomeworkModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.ui.base.MCBaseListActivity;

/**
 * 作业列表
 * 
 * @author 马彦君
 *  
 */
public class MCHomeworkListActivity extends MCBaseListActivity {

	private String courseId;
	private MCStudyService service;
	private String loginId;
	private MCCreateDialog createDialog;
	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return "作业列表为空";
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		loginId = MCSaveData.getUserInfo(Contants.USERID, this).toString();
		courseId = getIntent().getStringExtra("courseId");
		createDialog = new MCCreateDialog();
		this.service = new MCStudyService();
		super.onCreate(savedInstanceState);
	}

	/**
	 * 请求数据的主体
	 */
	public void requestData() {
		this.service.getCourseHomeworkList(this.courseId, mCurrentPage, this, this);
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCHomeworkModel model = (MCHomeworkModel) obj;
		MCHomeworkCommon.gotoHomework(model, this);
	}

	@Override
	public String getFunctionTitle() {
		return "我的作业";
	}
	
	/**
	 * 重写此方法用于草稿的替换
	 * @param resultList
	 */
	@Override
	public void doSomethingWithResult(List resultList) {
		// 用本地草稿进行替换
		MCHomeworkCommon.replaceHomeworkWithLocal(service, courseId, loginId, (List<MCHomeworkModel>) resultList, this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
	public void initAdapter() {
		this.adapter = new QuickAdapter(this, R.layout.homework_item_layout) {

			protected void convert(BaseAdapterHelper helper, final MCHomeworkModel item) {

				helper.setText(R.id.tjtime_label,
						MCHomeworkListActivity.this.getString(R.string.homework_commit_time, new Object[] { item.getStartDate(), item.getEndDate() }));

				helper.setText(
						R.id.hptime_label,
						MCHomeworkListActivity.this.getString(R.string.homework_comment_time,
								new Object[] { item.getCommentStartDate(), item.getCommentEndDate() }));
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
				// 0 去做作业
				// 1 查看作业 - 有草稿（可以查看，选择重做）
				// 2 没有标记 ,已提交，未批改
				// 3 成绩已出
				// 4 互评
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
					view.setImageResource(R.drawable.homework_enter);
					break;
				case 3:
					helper.getView(R.id.status_img).getBackground().setLevel(0);
					operate.setText(item.getTotalScore());
					operate.setTextSize(36);
					view.setVisibility(View.INVISIBLE);// 不显示图片
					break;
				case 4:
					helper.getView(R.id.status_img).getBackground().setLevel(1);
					helper.setVisible(R.id.hp_img, true);
					helper.setVisible(R.id.hptime_label, true);
					operate.setText("去互评");
					operate.setTextColor(getResources().getColor(R.color.yellow_hp_color));
					view.setImageResource(R.drawable.homework_hp_enter);
					//过期互评作业，显示最终的成绩
					if(!item.getIsClick()){
						operate.setText(item.getTotalScore());
						operate.setTextSize(36);
						operate.setTextColor(getResources().getColor(R.color.theme_color));
						view.setVisibility(View.INVISIBLE);
						operate.setFocusable(false);
					}
					
					//过期互评作业，显示最终的成绩
					if(!item.getIsClick()){
						operate.setText(item.getTotalScore());
						operate.setTextSize(36);
						operate.setTextColor(getResources().getColor(R.color.theme_color));
						view.setVisibility(View.INVISIBLE);
						operate.setFocusable(false);
					}
					
					break;

				case 5:
					helper.getView(R.id.status_img).getBackground().setLevel(2);
					if (item.isOverTime()) {
						operate.setText("已过期");
					} else {
						operate.setText("未到时间");
					}
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

				operate.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (item.getLocalStatus() == 4) {
							createDialog.createOkDialog(MCHomeworkListActivity.this, "此功能暂时未开放\n请到PC端进行互评");
						} else {
							MCHomeworkCommon.gotoHomework(item, MCHomeworkListActivity.this);
						}
					}

				});
			}
			protected void convert(BaseAdapterHelper helper, Object model) {
				this.convert(helper, ((MCHomeworkModel) model));
			}
		};
	}

}
