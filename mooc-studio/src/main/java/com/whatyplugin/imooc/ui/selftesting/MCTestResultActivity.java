package com.whatyplugin.imooc.ui.selftesting;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCTestAdditionalData;
import com.whatyplugin.imooc.logic.model.MCTestModel;
import com.whatyplugin.imooc.logic.model.MCTestQuesModel;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.utils.DisplayUtil;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

import java.util.ArrayList;
import java.util.List;

import cn.com.whatyplugin.mooc.R;

public class MCTestResultActivity extends MCBaseActivity implements OnClickListener {

	private static final String TAG = "MCTestResultActivity";
	private MCTestAdditionalData data;
	private MCTestModel mcTestModel;
	private MCStudyService service;
	private BaseTitleView titleView;
	private Handler mHandler = new Handler();
	private MCCreateDialog createDialog = new MCCreateDialog();
	/**
	 * 用来表示当前自测已经完成了(用户再自测列表,剩余做题此处为零,只能查看答案)
	 */
	private boolean isComplete;
	private Button bt_loook;
	private Button bt_agin;
	private TextView tv_desc_icontv;
	private TextView tv_cj1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selftest_reuslt);

		Intent intent = getIntent();
		if (intent != null) {
			mcTestModel = intent.getParcelableExtra("MCTestModel");
			isComplete = intent.getBooleanExtra("isComplete", false);
			data = intent.getParcelableExtra("MCAdditionalData"); // 一致
		}
		initView();
		initEvent();
		if (isComplete || mcTestModel.getRedo_num() == 1 ) {
			// 查看答案按钮对齐
			LinearLayout.LayoutParams llps = (LinearLayout.LayoutParams) bt_loook
					.getLayoutParams();
			llps.leftMargin = DisplayUtil.dip2px(this, 18);
			llps.rightMargin = DisplayUtil.dip2px(this, 18);
		}
		initData();

	}

	private void initData() {
		if(isComplete){
			tv_cj1.setText(String.valueOf(mcTestModel.getFinalScore()));
		}else{
			tv_cj1.setText(String.valueOf(data.getCurrentScore()));
		}
		titleView.setTitle(mcTestModel.getTitle());
		String tempScore = isComplete ? mcTestModel.getFinalScore() : data
				.getCurrentScore();
		int score = 0;
		try {
			score = Integer.valueOf(tempScore);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			score = 0;
		}

		if (score < 60) {
			Drawable drawable_top = getResources().getDrawable(
					R.drawable.test_sum3);
			tv_desc_icontv.setCompoundDrawablesWithIntrinsicBounds(
					drawable_top, null, null, null);
			tv_desc_icontv.setText("还有很大的提升空间，不要放弃!");
			// 小旗帜
		} else if (score >= 60
				&& score < 80) {
			// 笑脸
			Drawable drawable_top = getResources().getDrawable(
					R.drawable.test_sum2);
			tv_desc_icontv.setCompoundDrawablesWithIntrinsicBounds(
					drawable_top, null, null, null);
			tv_desc_icontv.setText("只要在努力一点点，就有很大的进步!");
		} else if (score >= 80
				&& score <= 100) {
			// 大拇指
			Drawable drawable_top = getResources().getDrawable(
					R.drawable.test_sum1);
			tv_desc_icontv.setCompoundDrawablesWithIntrinsicBounds(
					drawable_top, null, null, null);
			tv_desc_icontv.setText("成绩已经很好了，继续努力!");
		}
		tempScore = null;
	}

	private void initEvent() {
		bt_loook.setOnClickListener(this);
		if (isComplete || mcTestModel.getRedo_num() == 1 ) {
			bt_agin.setVisibility(View.GONE);
		} else {
			bt_agin.setOnClickListener(this);
		}
		titleView.findViewById(R.id.left_img).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isComplete) {
					Intent intent = new Intent();
					intent.putExtra("scoreFromResult", data.getCurrentScore());
					if (mcTestModel.getRedo_num() > 0) {
						// 去--,因为返回用户按返回键盘的时候要更新自测列表的适配器 decrement
						intent.putExtra("decrement", true);
					}
					setResult(200, intent);
				}
				MCTestResultActivity.this.finish();
			}
		});
	}

	private void initView() {
		bt_loook = (Button) findViewById(R.id.bt_look);
		bt_agin = (Button) findViewById(R.id.bt_agin);
		tv_desc_icontv = (TextView) findViewById(R.id.tv_desc_icontv);
		tv_cj1 = (TextView) findViewById(R.id.tv_cj1);
		titleView = (BaseTitleView) findViewById(R.id.rl_titile);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.bt_agin) {
			setResult(205);
			this.finish();
		} else if (id == R.id.bt_look) {
			if (isComplete) {
				showGetAnsWarningDialog();
				requestData();

			} else if(this.mcTestModel.getRedo_num()==1) {
				lookForAnswer();//不能重做类型的做完后直接查看答案而不用提示
			}else{
				showLookAnsDialog();
			}
		}
	}

	private MCCommonDialog warningDialog;

	private void showGetAnsWarningDialog() {
		warningDialog = createDialog.createLoadingDialog(this, "正在获取答案,请稍等!",0);
		warningDialog.setCancelable(false);
	}

	private void disGetAnsWarningDialog() {
		if (warningDialog != null && warningDialog.isVisible()) {
			warningDialog.dismiss();
		}
	}

	/**
	 * 提示用户查看答案后不能重做了
	 */
	private void showLookAnsDialog() {

		final MCCommonDialog dialog = createDialog.createOkCancelDialog(this, "查看答案后，不能重做。\n是否继续查看？");
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				dialog.setCancelable(false);
				dialog.setCommitListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						lookForAnswer();
					}
				});
			}
		}, 200);
	}

	/**
	 * 查看答案核心逻辑
	 */
	private void lookForAnswer() {
		MCStudyService service = new MCStudyService();
		service.saveTest(mcTestModel.getId(), mcTestModel.getAnswer(),
				new MCAnalyzeBackBlock() {
					@Override
					public void OnAnalyzeBackBlock(
							MCServiceResult result, List resultList) {
						Log.d(TAG, result.toString());
					}
				}, MCTestResultActivity.this);
		Intent intent = new Intent();
		intent.putExtra("scoreFromResult", data.getCurrentScore());
		setResult(202, intent);
		MCTestResultActivity.this.finish();
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
	 * 联网获取自测答案解析
	 */
	private void requestData() {
		service = new MCStudyService();
		service.getTestResultDetails(mcTestModel.getId(),
				new MCAnalyzeBackBlock<MCTestQuesModel>() {
					@Override
					public void OnAnalyzeBackBlock(MCServiceResult result,
							List<MCTestQuesModel> resultList) {
						// 进查看答案界面
						if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
							disGetAnsWarningDialog();
							Intent intent = new Intent();
							intent.putExtra("isComplete", true);
							intent.putExtra("MCTestModel", mcTestModel);
							intent.setClass(MCTestResultActivity.this,
									MCTestDoActivity.class);
							Bundle bundle = new Bundle();
							bundle.putParcelableArrayList(
									"MCTestQuesModelList",
									(ArrayList<? extends Parcelable>) resultList);
							intent.putExtras(bundle);
							startActivity(intent);
							MCTestResultActivity.this.finish();
						} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
							MCToast.show(MCTestResultActivity.this,
									"网络连接失败了,请您稍后重试!", 200);
							disGetAnsWarningDialog();
							return;
						} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
							MCToast.show(MCTestResultActivity.this,
									"获取数据失败了,请您稍后重试!", 200);
							disGetAnsWarningDialog();
							return;
						} else {
							MCToast.show(MCTestResultActivity.this,
									"网络连接失败了,请您稍后重试!", 200);
							disGetAnsWarningDialog();
							return;
						}
					}
				}, this);
	}

}
