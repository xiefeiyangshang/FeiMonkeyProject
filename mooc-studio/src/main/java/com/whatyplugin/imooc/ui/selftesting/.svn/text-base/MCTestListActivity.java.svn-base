package com.whatyplugin.imooc.ui.selftesting;

import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.enums.TimeStyle;
import com.whatyplugin.base.utils.DensityUtil;
import com.whatyplugin.imooc.logic.model.MCTestModel;
import com.whatyplugin.imooc.logic.model.MCTestModel.TextConstant;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.ui.base.MCBaseListActivity;

/**
 * 自测列表
 * 
 * @author bzy
 */
public class MCTestListActivity extends MCBaseListActivity {

	private String courseId;
	private MCStudyService service;

	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return "自测列表为空";
	}

	/**
	 * ui填充，adapter初始化
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (getIntent() != null) {
			this.courseId = getIntent().getStringExtra("courseId");
		}

		this.service = new MCStudyService();

		super.onCreate(savedInstanceState);
	}

	/**
	 * 请求数据的主体
	 */
	public void requestData() {
		this.service.getAllTest(this.courseId, this.mCurrentPage, this, this);
	}

	/**
	 * 查看答案后返回的结果
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 202 || resultCode == 200) {
			if (data != null) {
				String id = data.getStringExtra("id");
				List adapterList = adapter.getAdapterList();
				Iterator iterator = adapterList.iterator();
				while (iterator.hasNext()) {
					MCTestModel next = (MCTestModel) iterator.next();
					String id2 = next.getId();
					if (id.equals(id2)) {
						if (resultCode == 202) { // 用户查看了答案
													// ,用户不查看答案并且是第一次进入,直接退出的话也需要刷洗界面
							next.setRedo_num(0);
						} else { // 用户按的返回键
							if (data.getBooleanExtra("decrement", false)) {
								// Log.d("add bzy",
								// "测试前的可做次数:"+next.getRedo_num());
								next.setRedo_num(next.getRedo_num() - 1);
								// Log.d("add bzy",
								// "测试后的可做次数:"+next.getRedo_num());
							}
						}

						int score = 0;
						try {
							score = Integer.valueOf(next.getFinalScore());
						} catch (NumberFormatException e) {
							e.printStackTrace();
							score = 0;
						}

						if (score <= 0) { // 这道题目未做过
							next.setFinalScore(data.getStringExtra("scoreFromResult"));
						}

						adapter.notifyDataSetChanged();
						break; // 跳出 刷新适配
					}
				}
			}
		}
	}

	/**
	 * 跳到分数界面
	 */
	private void jumpResultActivity(MCTestModel model) {
		Intent intent = new Intent(this, MCTestResultActivity.class);
		intent.putExtra("MCTestModel", model);
		intent.putExtra("isComplete", true);
		startActivityForResult(intent, 0);
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCTestModel model = (MCTestModel) obj;
		if (model != null) {
			// 判断做题时间
			try {
				String FinalScore = model.getFinalScore();
				if (TextConstant.TEST_END.equals(FinalScore)) {
					Toast.makeText(MCTestListActivity.this, TextConstant.TEXT_TIME_NOTIN_SETTIME, Toast.LENGTH_SHORT).show();
					return;
				}
				if(model.isUnStart()){
					Toast.makeText(MCTestListActivity.this, "题目尚未开始!", Toast.LENGTH_SHORT).show();
					return;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			// 判断做题次数
			if (model.getRedo_num() == 0) {
				jumpResultActivity(model);
				return;
			}

			Intent intent = new Intent(MCTestListActivity.this, MCTestDescActivity.class);
			model.setOpenCourseID(courseId);
			intent.putExtra("MCTestModel", model);
			startActivityForResult(intent, 111);

		}
	}

	@Override
	public String getFunctionTitle() {
		return "我的自测";
	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(this, R.layout.item_ans_list) {

			protected void convert(BaseAdapterHelper helper, final MCTestModel item) {

				String score = item.getFinalScore();
				TextView grade_view = (TextView) helper.getView(R.id.grade);
				grade_view.setText(score);
				if (TextConstant.TEST_END.equals(score)) {
					grade_view.setTextSize(12);
					grade_view.setTextColor(Color.parseColor("#8E979C"));
					helper.setTextColor(R.id.tv_title, Color.parseColor("#8E979C"));
					helper.setImageResource(R.id.iv_icon, R.drawable.test_titico2);
					grade_view.setCompoundDrawables(null, null, null, null);

				} else if (TextConstant.TEST_START.equals(score)) {
					grade_view.setTextSize(12);
					Drawable drawable = getResources().getDrawable(R.drawable.status_arr_green);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					grade_view.setCompoundDrawables(null, null, drawable, null);
					grade_view.setCompoundDrawablePadding(DensityUtil.dip2px(MCTestListActivity.this, 9));
					grade_view.setTextColor(getResources().getColor(R.color.theme_color));
					helper.setTextColor(R.id.tv_title, Color.parseColor("#000000"));
					helper.setImageResource(R.id.iv_icon, R.drawable.test_titico);
				} else if (TextConstant.TEST_NOT_START.equals(score)) {
					grade_view.setTextSize(12);
					grade_view.setTextColor(Color.parseColor("#ff8400"));
					helper.setTextColor(R.id.tv_title, Color.parseColor("#000000"));
					helper.setImageResource(R.id.iv_icon, R.drawable.test_titico);

				} else {
					grade_view.setTextSize(34);
					grade_view.setTextColor(getResources().getColor(R.color.theme_color));
					grade_view.setCompoundDrawables(null, null, null, null);
					grade_view.setText(score); // 分数
					helper.setTextColor(R.id.tv_title, Color.parseColor("#000000"));
					helper.setImageResource(R.id.iv_icon, R.drawable.test_titico);
				}

				helper.setText(R.id.tv_title, item.getTitle()); // 标题
				helper.setText(R.id.tv_time, "起止时间 : " + item.getDescTime(TimeStyle.M));
			}

			protected void convert(BaseAdapterHelper helper, Object obj) {
				this.convert(helper, ((MCTestModel) obj));
			}
		};
	}

}
