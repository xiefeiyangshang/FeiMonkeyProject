package com.whatyplugin.imooc.ui.selftesting;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.utils.DensityUtil;
import com.whatyplugin.imooc.logic.model.AnsOption;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCTestAdditionalData;
import com.whatyplugin.imooc.logic.model.MCTestInfo;
import com.whatyplugin.imooc.logic.model.MCTestModel;
import com.whatyplugin.imooc.logic.model.MCTestQuesModel;
import com.whatyplugin.imooc.logic.model.MCTestResultModel;
import com.whatyplugin.imooc.logic.model.MCTestStatisticInfo;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.utils.DisplayUtil;
import com.whatyplugin.imooc.logic.utils.MyCountDownTimer;
import com.whatyplugin.imooc.logic.utils.TimeFormatUtils;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.base.MyOnClickListener;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

public class MCTestDoActivity extends MCBaseActivity implements OnClickListener, MCAnalyzeBackBlock {
	private static final String TAG = "MCTestDoActivity";
	private RelativeLayout rl_guide;
	private ViewPagerAdapter adapter;
	private TextView tv_progress;
	private View bottom_layout;
	private TextView tv_time;
	private List resultList;
	private ProgressBar pb;
	private ViewPager vp;
	private MCCreateDialog createDialog = new MCCreateDialog();
	private ArrayList<View> pages = new ArrayList<View>();
	private long countdownTime = -1;
	private boolean isLookAns;
	private int index = 0; // 记录未做题的页
	private String scoreFromResult; //从MCTestResultActivity获取分数需要插入到MCTestListActivity
	private BaseTitleView titleView;
	private MCStudyService service;
	// 定时器
	private MyCountDownTimer countTimer;
	private Handler mHandler = new Handler();
	private MCTestModel mcTestModel;
	/**
	 * 用户从查看分数界面返回的list
	 */
	private ArrayList<MCTestQuesModel> quesList;
	private ImageView iv_test_know;
	private int themeColor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		themeColor = getResources().getColor(R.color.theme_color);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selftest_do);

		// GET PARCELABLE
		if (getIntent() != null) {
			isComplete = getIntent().getBooleanExtra("isComplete", false);
			if(isComplete){ //未做题直接从答案界面跳转过啊来
				quesList = getIntent().getParcelableArrayListExtra("MCTestQuesModelList");
				//初始化TextInfo
			}
				mcTestModel = getIntent().getParcelableExtra("MCTestModel");
				countdownTime = mcTestModel.getTxtLimitTime();
		}
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		// VIEW
		iv_test_know = (ImageView) findViewById(R.id.iv_test_know);
		titleView = (BaseTitleView) findViewById(R.id.rl_titile);
		rl_guide = (RelativeLayout) findViewById(R.id.rl_guide); // 配置指南页面
		tv_progress = (TextView) findViewById(R.id.tv_progress);
		bottom_layout = findViewById(R.id.bottom_layout);
		tv_time = (TextView) findViewById(R.id.tv_time);
		pb = (ProgressBar) findViewById(R.id.pb);
		vp = (ViewPager) findViewById(R.id.test_vp);
	}

	private void initEvent() {
		iv_test_know.setOnClickListener(this);
		vp.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent even) {
				return rl_guide.getVisibility() == View.VISIBLE ? true : false;
			}
		});
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int seleIndex) {
				if (seleIndex + 1 >= 10) {
					tv_progress.setText(seleIndex + 1 + "/" + vp.getAdapter().getCount());
				} else {
					tv_progress.setText(String.format("% d", seleIndex + 1) + "/" + vp.getAdapter().getCount());
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
		
		titleView.findViewById(R.id.left_img).setOnClickListener(this);
	}

	private void initData() {
		rl_guide.setVisibility(View.VISIBLE);
		titleView.setTitle(mcTestModel.getTitle() + "");
		if(!isComplete){
			requestData();
		}else{
			rl_guide.setVisibility(View.GONE);
			tv_progress.setText(" " + 1 + "/" + quesList.size());
			setAdapter(quesList);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(countTimer != null){
			countTimer.cancel();
		}
	}
	
	/**
	 * 倒计时
	 */
	private void startCountDown() {
		if (countdownTime > 0) {
			tv_time.setVisibility(View.VISIBLE);
			countTimer = new MyCountDownTimer(countdownTime, 1000);
			countTimer.setListener(new MyCountDownTimer.onDownTimerEventListener() {
				@Override
				public void onTick() {
					countdownTime -= 1000;
					tv_time.setText("倒计时: " + TimeFormatUtils.formatTime(countdownTime));
				}
				@Override
				public void onFinish() {
					tv_time.setText("倒计时: 00:00:00");
					TimeOutSubmit();
				}
			});
			countTimer.start();
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.iv_test_know) {
			rl_guide.setVisibility(View.GONE);
		} else if (id == R.id.left_img) {
			//防止网络不稳定 崩溃
			if(!isComplete){
				if(adapter != null && adapter.getList() != null && adapter.getList().size()>0){
					if (isLookAns) {
						//<------
						Intent exitDoIntent = new Intent();
						exitDoIntent.putExtra("id", mcTestModel.getId());
						exitDoIntent.putExtra("scoreFromResult", scoreFromResult);
						setResult(202,exitDoIntent);
						this.finish();
					} else {
						if (checkBlank()) {
							showDialog(true);
						} else {
							showDialog(false);
						}
					}
				}else{
					this.finish(); //联网失败了或者真的没数据,让用户直接退出悲哀啊
				}
			}else{
				this.finish(); 
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			titleView.findViewById(R.id.left_img).performClick();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 联网获取自测
	 */
	private void requestData() {
		pb.setVisibility(View.VISIBLE);
		service = new MCStudyService();
		service.getTestQuestions(String.valueOf(mcTestModel.getId()), mcTestModel.getOpenCourseID(), mcTestModel.getQuestionCount(), this, this);
	}

	/**
	 * 获取到数据
	 */
	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
		pb.setVisibility(View.GONE);
		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
			startCountDown();
			tv_progress.setText(" " + 1 + "/" + resultList.size());
			setAdapter(resultList);
		}else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
			MCToast.show(MCTestDoActivity.this, "网络连接失败了,请您稍后重试!",200);
			return;
		}else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
			MCToast.show(MCTestDoActivity.this, "获取数据失败了,请您稍后重试!",200);
			return;
		} 
	}

	/**
	 * 设置适配器
	 * 
	 * @param resultList
	 */
	private void setAdapter(List resultList) {
		for (int i = 0; i < resultList.size(); i++) {
			pages.add(inflaterView(i, resultList));// .size(),resultList.get(i))
		}
		if (adapter == null) {
			adapter = new ViewPagerAdapter(resultList);
			vp.setAdapter(adapter);
		} else
			adapter.notifyDataSetChanged();
	}

	/**
	 * 填充ViewPager的每一页,这个是一个大的线性布局,如果看不懂的话可以用试图工具来分析
	 * sdk\tools\hierarchyviewer.bat 代码中写布局主要是为了效率，viewpager嵌套listview导致冲突
	 * 1.做题的时候用的这个View
	 * 2.做题完成后查看答案也是用的这个View
	 * 3.直接查看答案也是用的这个View
	 * @param position
	 *            页数
	 * @param resultList
	 *            获取的数据
	 * @return 对应页显示的View
	 */
	private View inflaterView(final int position, List resultList) {
		final MCTestQuesModel mcQuesModel = (MCTestQuesModel) resultList.get(position);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		// 根布局

		final LinearLayout level_root_View = new LinearLayout(this);
		level_root_View.setOrientation(LinearLayout.VERTICAL);
		// level_root_View.setLayoutParams(params);
		// 1 标题一 <------挂到根布局
		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(this, 40));
		TextView tv_title = new TextView(this);
		tv_title.setLayoutParams(params);
		tv_title.setTextSize(15);
		tv_title.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.homework_left_green), null, null, null);
		tv_title.setCompoundDrawablePadding(DensityUtil.dip2px(this, 9));
		
		MCTestInfo testInfo = mcTestModel.getTestInfo();
		if(testInfo != null && testInfo.statisticInfo!= null){
			if (mcQuesModel.type.equalsIgnoreCase("danxuan")) {
				if(testInfo.statisticInfo.containsKey("DANXUAN")){
					MCTestStatisticInfo info = testInfo.statisticInfo.get("DANXUAN");
					if(info.count>0){
						tv_title.setText("单选题 ( 共"+info.count+"题,每题"+info.score+"分 ) ");
					}
				}
			} else if (mcQuesModel.type.equalsIgnoreCase("panduan")) {
				if( testInfo.statisticInfo.containsKey("PANDUAN")){
					MCTestStatisticInfo info =  testInfo.statisticInfo.get("PANDUAN");
					if(info.count>0){
						tv_title.setText("判断题 ( 共"+info.count+"题,每题"+info.score+"分 ) ");
					}
				}
			}else if(mcQuesModel.type.equalsIgnoreCase("duoxuan")){
				if( testInfo.statisticInfo.containsKey("DUOXUAN")){
					MCTestStatisticInfo info =  testInfo.statisticInfo.get("DUOXUAN");
					if(info.count>0){
						tv_title.setText("多选题 ( 共"+info.count+"题,每题"+info.score+"分 ) ");
					}
				}
			}
		}
		
		tv_title.setTextColor(Color.parseColor("#616161"));
		tv_title.setBackgroundColor(Color.parseColor("#E5E5E5"));
		tv_title.setPadding(0, 8, 0, 8);
		tv_title.setGravity(Gravity.CENTER | Gravity.LEFT);
		level_root_View.addView(tv_title);

		// 3 问题Contents和答题提交按钮 <------挂到根布局
		ScrollView level_1_sview = new ScrollView(this);
		
		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.weight = 110; // 优先权留给其他控件
		level_1_sview.setLayoutParams(params);
		
		// 2 问题标题一 <------挂到 scrollView 上 使其全部都能滚动 以防止 题目过长看不见答案
		LinearLayout level_1_View = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(25, 35, 30, 12);
		TextView tv_q = new TextView(this);
		tv_q.setTextSize(18);
		tv_q.setTextColor(Color.parseColor("#000000"));
		tv_q.setText(position + 1 + ". " + mcQuesModel.title);
		tv_q.setLayoutParams(params);
		//添加到 lina中
		level_1_View.addView(tv_q);
	
		
		level_1_sview.addView(level_1_View);
		level_root_View.addView(level_1_sview);// <--挂载到root
		level_1_View.setOrientation(LinearLayout.VERTICAL);
		FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//flp.weight = 110; // 优先权留给其他控件
		level_1_View.setLayoutParams( flp);
		
		

		Log.d("positionP:", "count:" + resultList.size() + ",position:" + position);
		if (resultList.size() - 1 == position && !isComplete) {// position是从零开始的 isComplete模式除外
			Button bt_save = new Button(MCTestDoActivity.this);
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(MCTestDoActivity.this, 45));
			params.leftMargin = 20;
			params.rightMargin = 20;
			params.bottomMargin = 50;
			bt_save.setText("交卷");
			bt_save.setTextSize(18);
			bt_save.setId(R.id.bt_save);
			bt_save.setTextColor(Color.parseColor("#FFFFFF"));
			bt_save.setBackgroundColor(themeColor);

			bt_save.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (checkBlank()) {
						// showDialog();
						showSaveWarningDialog();
					} else {
						showSaveProgressDialog();
						checkMyPapers();
					}
				}
			});
			bt_save.setLayoutParams(params);
			level_root_View.addView(bt_save);// <--挂载到root
		}

		boolean isAdd = true;
		if (isAdd) {//这个在查看答案的时候用到   position是从零开始的

			LinearLayout ll_parser_laout = new LinearLayout(MCTestDoActivity.this);
			ll_parser_laout.setBackgroundColor(Color.WHITE);
		    ll_parser_laout.setVisibility(View.GONE);
			ll_parser_laout.setMinimumHeight(280);
			ll_parser_laout.setOrientation(LinearLayout.VERTICAL);
			ll_parser_laout.setLayoutParams(params);
			ll_parser_laout.setBackgroundColor(Color.parseColor("#F6F6F6"));
			ll_parser_laout.setId(level_root_View.hashCode() + position);
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.bottomMargin = 40;
			ll_parser_laout.setLayoutParams(params);

			//
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(30, 25, 30, 15);
			TextView tv_ans = new TextView(this);
			tv_ans.setTextSize(15);
			Drawable drawable_top = getResources().getDrawable(R.drawable.test_key_r);
			drawable_top.setBounds(10, 10, 10, 10);
			tv_ans.setCompoundDrawables(drawable_top, null, null, null);
			tv_ans.setCompoundDrawablePadding(DensityUtil.dip2px(this, 9));
			tv_ans.setLayoutParams(params);
			tv_ans.setId(level_root_View.hashCode() + position + "tv_ans".hashCode());
			ll_parser_laout.addView(tv_ans);

			// 下面条目分割线
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
			View level_3_line = new View(this);
			params.setMargins(85, 15, 30, 15);
			level_3_line.setBackgroundColor(Color.parseColor("#ffd0d6d9"));
			level_3_line.setLayoutParams(params);
			ll_parser_laout.addView(level_3_line);

			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(30, 15, 30, 5);
			TextView tv_par = new TextView(this);
			tv_par.setTextColor(themeColor);
			Drawable drawable_down = getResources().getDrawable(R.drawable.test_key_l);
			drawable_down.setBounds(0, 0, 32, 32);
			tv_par.setCompoundDrawables(drawable_down, null, null, null);
			tv_par.setCompoundDrawablePadding(DensityUtil.dip2px(this, 9));
			// tv_par.setCompoundDrawablesWithIntrinsicBounds(drawable_down,
			// null, null, null);
			tv_par.setText("答案解析:");
			tv_par.setTextSize(15);
			tv_par.setLayoutParams(params);
			ll_parser_laout.addView(tv_par);
			// 32+20+
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(80, 5, 10, 5);
			TextView tv_par_content = new TextView(this);
			tv_par_content.setTextSize(15);
			tv_par_content.setTextColor(Color.parseColor("#666666"));
			tv_par_content.setLayoutParams(params);
			tv_par_content.setId(level_root_View.hashCode() + position + "tv_par".hashCode());
			ll_parser_laout.addView(tv_par_content);
			level_root_View.addView(ll_parser_laout);// <--挂载到root
			
			if(isComplete){ //是查看答案  从未做题进入
					ll_parser_laout.setVisibility(View.VISIBLE);
					// 0不正确、1正确
					if (mcQuesModel.correctness == 1) {
						tv_ans.setTextColor(themeColor);
						tv_ans.setText("恭喜你,答对了!");
						tv_ans.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.test_key_g), null, null, null);
					} else {
						tv_ans.setText("很遗憾,答错了!标准答案 : " + mcQuesModel.correctOption);
						tv_ans.setTextColor(Color.parseColor("#D94D4D"));
						tv_ans.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.test_key_r), null, null, null);
					} if (null == mcQuesModel.solution || mcQuesModel.solution.equals("")) {
						tv_par_content.setText("暂无答案解析");
					} else {
						tv_par_content.setText(mcQuesModel.solution);
					}
			}
			
		}

		for (int iopt = 0; iopt < mcQuesModel.options.size(); iopt++) {
			final AnsOption ansOption = mcQuesModel.options.get(iopt);

			// 题目Index,题目内容,分割线
			LinearLayout level_2_View = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			level_2_View.setOrientation(LinearLayout.VERTICAL);
			level_1_View.addView(level_2_View);

			// 题目Index 和题目内容
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			LinearLayout level_3_View = new LinearLayout(this);
			level_3_View.setLayoutParams(params);
			level_3_View.setMinimumHeight(115);
			level_3_View.setOrientation(LinearLayout.HORIZONTAL);
			level_3_View.setGravity(Gravity.CENTER_VERTICAL);
			level_2_View.addView(level_3_View);

			// 显示第几题
			final TextView tv_num = new TextView(this);
			tv_num.setText(ansOption.id);
			tv_num.setBackgroundResource(R.drawable.test_choice_w);
			//tv_num.setBackgroundResource(getResources().getDrawable(R.drawable.test_choice_w));
			tv_num.setTextColor(themeColor);
			tv_num.setGravity(Gravity.CENTER);
			tv_num.setTag(mcQuesModel.options.get(iopt).hashCode());
			//  要判断单选和多选然后使用不同的背景色
			if (mcQuesModel.type.equalsIgnoreCase("danxuan") || mcQuesModel.type.equalsIgnoreCase("panduan")) {
				tv_num.setBackgroundResource(R.drawable.test_radio_w);
			} else if (mcQuesModel.type.equalsIgnoreCase("duoxuan")) {
				tv_num.setBackgroundResource(R.drawable.test_choice_w);
			}
			params = new LinearLayout.LayoutParams(DisplayUtil.dip2px(MCTestDoActivity.this, 20), DisplayUtil.dip2px(MCTestDoActivity.this, 20));
			params.setMargins(25, 12, 12, 12);
			tv_num.setLayoutParams(params);
			level_3_View.addView(tv_num);

			// 显示题目内容
			TextView tv_content = new TextView(this);
			tv_content.setPadding(0, 30, 0, 30);
			tv_content.setText(mcQuesModel.options.get(iopt).content);
			tv_content.setTextSize(15);
			tv_content.setTextColor(Color.parseColor("#717171"));
			level_3_View.addView(tv_content);
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 7;
			params.rightMargin = 39 + tv_num.getMeasuredWidth();
			tv_content.setLayoutParams(params);
			// 下面条目分割线
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
			params.setMargins(39 + tv_num.getMeasuredWidth(), 0, 39 + tv_num.getMeasuredWidth(), 0);
			View level_3_line = new View(this);
			level_3_line.setBackgroundColor(Color.parseColor("#ffd0d6d9"));
			level_2_View.addView(level_3_line);
			level_3_line.setLayoutParams(params);
			//如果是直接查看答案的话就不去set点击事件了.而是改为选中某个item
			// 用户选中事件监听 1.做题的时候需要,2.不能再做查看答案后 不需要 ,3.做题提交后不需要响应
			if(!isComplete){
				level_2_View.setOnClickListener(new MyOnClickListener(iopt) {
					@Override
					public void onClick(View v) {
						if (isLookAns) { // 用户查看答案
							return;
						}
						if (mcQuesModel.type.equalsIgnoreCase("danxuan") || mcQuesModel.type.equalsIgnoreCase("panduan")) {
							for (int i = 0; i < mcQuesModel.options.size(); i++) {
								int position2 = this.getPosition();
								if (this.getPosition() == i) {
									if (ansOption.isCheck) { // 选中改变背景色为选中状态
										ansOption.isCheck = false;
										tv_num.setTextColor(themeColor);
										tv_num.setBackgroundResource(R.drawable.test_radio_w);
									} else { // 取消选中状态
										ansOption.isCheck = true;
										tv_num.setTextColor(Color.parseColor("#ffffff"));
										tv_num.setBackgroundResource(R.drawable.test_radio_g);
									}
								} else {
									TextView findViewWithTag = (TextView) level_root_View.findViewWithTag(mcQuesModel.options.get(i).hashCode());
									findViewWithTag.setTextColor(themeColor);
									findViewWithTag.setBackgroundResource(R.drawable.test_radio_w);
									mcQuesModel.options.get(i).isCheck = false;
								}
							}
						} else if (mcQuesModel.type.equalsIgnoreCase("duoxuan")) {
							if (ansOption.isCheck) { // 选中改变背景色为选中状态
								ansOption.isCheck = false;
								tv_num.setTextColor(themeColor);
								tv_num.setBackgroundResource(R.drawable.test_choice_w);
							} else { // 取消选中状态
								ansOption.isCheck = true;
								tv_num.setTextColor(Color.parseColor("#ffffff"));
								tv_num.setBackgroundResource(R.drawable.test_choice_g);
							}
						}
					}
				});
			}else{ 
				//这里修改成显示用户自己的作答
				String userAnswer = mcQuesModel.getUserAnswer();
				if (!TextUtils.isEmpty(userAnswer)) {
					if (userAnswer.contains(ansOption.id)) {
						// 这里用isCheck表示正确答案
						tv_num.setTextColor(Color.parseColor("#FFFFFF"));
						if (mcQuesModel.type.equalsIgnoreCase("danxuan") || mcQuesModel.type.equalsIgnoreCase("panduan")) {
							tv_num.setBackgroundResource(R.drawable.test_radio_g);
						} else if (mcQuesModel.type.equalsIgnoreCase("duoxuan")) {
							tv_num.setBackgroundResource(R.drawable.test_choice_g);
						}
					}
				}
			}
			
		}
		return level_root_View;
	}

	private class ViewPagerAdapter extends PagerAdapter {
		List list;

		public ViewPagerAdapter(List list) {
			super();
			this.list = list;
		}

		public List getList() {
			return list;
		}

		@Override
		public int getCount() {
			return pages.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup view, int position, Object object) {
			view.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			view.addView(pages
					.get(position));
			return pages.get(position);
		}
	}

	private MCCommonDialog exitDialog;
	private void showDialog(boolean isExit) {
		String str = null;
		if (isExit) {
			str = "你还有题目未做,是否退出?";
		} else {
			str = "当前自测还未提交，是否退出?";
		}
		exitDialog  = createDialog.createOkCancelDialog(this, str);
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				exitDialog.setCancelable(false);
				exitDialog.setCommitListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						MCTestDoActivity.this.finish();
					}
				});
			}
		}, 200);
	}

	private MCCommonDialog warningDialog; 
	private void showSaveWarningDialog() {
		warningDialog = createDialog.createOkCancelDialog(this,  "还有题未做,是否提交?");
		warningDialog.setCancelable(false);
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				warningDialog.setCommitListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						showSaveProgressDialog();
						checkMyPapers();
						warningDialog.dismiss();
					}
				});
			}
		}, 200);		
	}
  //超时自动提交
	private void TimeOutSubmit() {
		showSaveProgressDialog();
		checkMyPapers(); 
	}	
	private MCCommonDialog loadingDialog;
	private boolean isComplete;
	private void showSaveProgressDialog() {
		loadingDialog = createDialog.createLoadingDialog(this, "亲,你的试卷正在提交中,\n请耐心等待一下下~",0);
	}

	private void dismissLoadingDialog() {
		if (loadingDialog != null && loadingDialog.isVisible()) {
			loadingDialog.dismiss();
		}
	}

	/**
	 * 检查是否有未做题目
	 * 
	 * @return
	 */
	private boolean checkBlank() {
		boolean select = false;
		ArrayList<MCTestQuesModel> list = (ArrayList<MCTestQuesModel>) adapter.getList();
		for (int i = 0; i < list.size(); i++) {
			if (!select) {
				MCTestQuesModel mcTestQuesModel = list.get(i);
				ArrayList<AnsOption> opts = mcTestQuesModel.options;
				boolean b = false;
				for (int j = 0; j < opts.size(); j++) {
					if (opts.get(j).isCheck == true)
						b = true;
					if (j == opts.size() - 1 && b == false) { // 最后一个并且有未选中状态存在标记跳出
						index = i;
						select = true;
					}
				}
			} else
				break;
		}
		return select;
	}

	/**
	 * 拼答案
	 */
	private void checkMyPapers() {
		ArrayList<MCTestQuesModel> list = (ArrayList<MCTestQuesModel>) adapter.getList();
		StringBuilder builder = new StringBuilder();
		 for(MCTestQuesModel mcTestQuesModel:list){
			ArrayList<AnsOption> opts = mcTestQuesModel.options;
			//这里要考虑用户有未做题
			String select = "";
			for (int j = 0; j < opts.size(); j++) {
				if (opts.get(j).isCheck){
					select += opts.get(j).id;
				}
			}
			
			// 1:ff8080814ac91645014acc98345f0046:A
			//不管做不做题 我都会拼接答案
				builder.append(mcTestQuesModel.index + ":");
				builder.append(mcTestQuesModel.questionId + ":");
				builder.append(select+"|");
		
			
		}
	
	     	if(builder.length()>0)
			builder.deleteCharAt(builder.length() - 1); // 按照模板格式去掉最后一个
			saveTestAndGetResult(builder.toString());

		

	}

	/**
	 * 提交自测 获取自测答案
	 * 
	 * @param answer
	 *            用户做的题 拼接
	 */
	private void saveTestAndGetResult(final String answer) {
		MCLog.i(TAG, answer);
		service.saveTestAndGetResult(mcTestModel.getId(), answer, 0, new MCAnalyzeBackBlock() {
			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
				dismissLoadingDialog();

				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
					MCToast.show(MCTestDoActivity.this, "网络连接失败！");
				} else if (resultList != null && resultList.size() == 0) {
					MCToast.show(MCTestDoActivity.this, "获取数据失败！");
				} else {
					if (mcTestModel.getTxtLimitTime() > 0) {
						countTimer.cancel(); // 停止计时器
					}
					mcTestModel.setAnswer(answer); // 将答案保存到bean
					MCTestDoActivity.this.resultList = resultList;
					Intent intent = new Intent(MCTestDoActivity.this, MCTestResultActivity.class);
					intent.putExtra("MCTestModel", mcTestModel);
					intent.putExtra("MCAdditionalData", (MCTestAdditionalData) result.getAddtionalData());
					startActivityForResult(intent, 0);
				}
			}
		}, this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 200) { //back
			if(data!= null){
				data.putExtra("id", mcTestModel.getId());
			}
			setResult(200,data);
			this.finish();
		} else if (resultCode == 202) { //back look ans
			isLookAns = true;
			lookAnswer();
			if(data!= null){
				scoreFromResult = data.getExtras().getString("scoreFromResult","0");
			}
			//setResult(202);
			// this.finish();//del
		} else if (resultCode == 205) {
			// 倒计时重置
			countdownTime = mcTestModel.getTxtLimitTime();
			if (countdownTime > 0) {
				tv_time.setText("倒计时: " + TimeFormatUtils.formatTime(countdownTime));
				countTimer.start();
			}
			setReset();
		}
		// super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 用户要求重做
	 */
	private void setReset() {
		ArrayList<MCTestQuesModel> list = (ArrayList<MCTestQuesModel>) adapter.getList();
		for (int i = 0; i < list.size(); i++) { // <--data

			for (int j = 0; j < list.get(i).options.size(); j++) { // <--data--opts
				list.get(i).options.get(j).isCheck = false;
				View view = pages.get(i); // 数据结构是有序排列
											// <--view和data是对应关系,不需要考虑安全问题
				TextView tv_num = (TextView) view.findViewWithTag(list.get(i).options.get(j).hashCode());

				if (list.get(i).type.equalsIgnoreCase("danxuan") || list.get(i).type.equalsIgnoreCase("panduan")) {
					tv_num.setBackgroundResource(R.drawable.test_radio_w);
				} else if (list.get(i).type.equalsIgnoreCase("duoxuan")) {
					tv_num.setBackgroundResource(R.drawable.test_choice_w);
				}
				tv_num.setTextColor(themeColor);
			}
		}
		vp.setCurrentItem(0, false);
	}

	/**
	 * 查看答案
	 */
	private void lookAnswer() {
		bottom_layout.setVisibility(View.GONE);
		ArrayList<MCTestQuesModel> list = (ArrayList<MCTestQuesModel>) adapter.getList();
		// 到这里的时候list应该和resultList的顺序不一样
		
		for (int i = 0; i < list.size(); i++) { // <--data
			View view = pages.get(i);
			if (i + 1 == list.size()) {
				view.findViewById(R.id.bt_save).setVisibility(View.GONE);
			}
			
			TextView tv_ans = (TextView) view.findViewById(view.hashCode() + i + "tv_ans".hashCode());
			Log.d("get-id", "code:" + view.hashCode() + i + "tv_ans".hashCode());
			view.findViewById(view.hashCode() + i).setVisibility(View.VISIBLE);
			TextView tv_par_content = (TextView) view.findViewById(view.hashCode() + i + "tv_par".hashCode());
			//服务器返回的index是从1开始的 
			MCTestResultModel resultModel = getResultModel(i+1); 
			if(resultModel != null){
				// 0不正确、1正确
				if (resultModel.getCorrectness() == 1) {
					tv_ans.setTextColor(themeColor);
					tv_ans.setText("恭喜你,答对了!");
					tv_ans.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.test_key_g), null, null, null);
				} else {
					tv_ans.setText("很遗憾,答错了!标准答案 : " + resultModel.getCorrectOption());
					tv_ans.setTextColor(Color.parseColor("#D94D4D"));
					tv_ans.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.test_key_r), null, null, null);
				}
				if (null == resultModel.getSolution() || resultModel.getSolution().equals("")) {
					tv_par_content.setText("暂无答案解析");
				} else {
					tv_par_content.setText(resultModel.getSolution());
				}
			}
		}
		vp.setCurrentItem(0, false);
	}
	
	/**
	 * 从结果中取正确答案等    后面再优化
	 * @param questionIndex
	 * @return
	 */
	private MCTestResultModel getResultModel(int questionIndex){
		if(resultList != null){
			MCTestResultModel resultModel;
			for(int i = 0;i<resultList.size();i++){
				resultModel = (MCTestResultModel) resultList.get(i);
				if(resultModel.getQuestionIndex() == questionIndex){
					return resultModel;
				}
			}
		}
		return null;
	}
}
