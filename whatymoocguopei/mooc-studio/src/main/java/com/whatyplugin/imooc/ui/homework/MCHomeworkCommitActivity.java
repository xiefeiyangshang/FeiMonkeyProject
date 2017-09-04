package com.whatyplugin.imooc.ui.homework;

import java.util.List;

import ukplugin.co.senab.photoview.KeyboardRelativeLayout;
import ukplugin.co.senab.photoview.KeyboardRelativeLayout.onSizeChangedListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCHomeworkModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCUploadModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.logic.utils.WebViewUtil;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;
import com.whatyplugin.imooc.ui.view.ChoicePicFromLocalView;
import com.whatyplugin.imooc.ui.view.ContainsEmojiEditText;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

/**
 * 做作业
 * 
 * @author 马彦君
 * 
 */
public class MCHomeworkCommitActivity extends MCBaseActivity {

	private MCStudyServiceInterface service;
	private MCHomeworkModel homeworkModel;
	private TextView content_label;
	private ContainsEmojiEditText answer_label;
	private WebView detail_label;
	private TextView time_label;
	private BaseTitleView titleView;
	private ChoicePicFromLocalView choic_carme_pic;
	private LinearLayout pic_content_layout;
	private MCCommonDialog loading_dialog;
	private String loginId; // 获取到用户名用于处理作业的离线保存
	private Handler mHandler = new Handler();
	private ScrollView sv_MyScrollView;
	private int scrollViewBottom = 0;
	private MCCreateDialog createDialog = new MCCreateDialog();
	private  KeyboardRelativeLayout   rl_Key;
	
	/**
	 * ui填充，adapter初始化
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 或者清单文件中配置 android:windowSoftInputMode="adjustResize|stateHidden"
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.homework_commit_layout);
		initData();
		initView();
		initEvent();
		// 查询数据
		requestData();
	}

	private void initEvent() {
		titleView.setRigTextListener(new RightClickListener() {
			@Override
			public void onRightViewClick() {
				commitHomework();
			}
		});
		titleView.findViewById(R.id.left_img).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finishWithResult();
			}
		});

		rl_Key.setOnSizeChangedListener(new onSizeChangedListener() {

			@Override
			public void onChanged(boolean showKeyboard) {
				if (showKeyboard) {
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							sv_MyScrollView.fullScroll(View.FOCUS_DOWN);
						}
					}, 200);

				}
			}
		});

	}

	private void initData() {
		this.service = new MCStudyService();
		loginId = MCSaveData.getUserInfo(Contants.USERID, this).toString();

		homeworkModel = (MCHomeworkModel) getIntent().getSerializableExtra("homework");
	}

	/**
	 * 这里初始化父视图
	 */
	private void initView() {
		this.titleView = (BaseTitleView) findViewById(R.id.rl_titile);
		this.content_label = (TextView) findViewById(R.id.content_label);
		this.detail_label = (WebView) findViewById(R.id.detail_label);
		this.time_label = (TextView) findViewById(R.id.time_label);
		this.answer_label = (ContainsEmojiEditText) findViewById(R.id.answer_label);
		this.sv_MyScrollView = (ScrollView) findViewById(R.id.scorll_view);
		this.pic_content_layout = (LinearLayout) findViewById(R.id.pic_linearlayout);

		this.choic_carme_pic = (ChoicePicFromLocalView) findViewById(R.id.choic_carme_pic);
		this.choic_carme_pic.setContentWrap(this.pic_content_layout);// 传入要显示图片的布局
		this.choic_carme_pic.setOrgPic(this.homeworkModel.getPicPathList());// 初始化原来的图片
		rl_Key = (KeyboardRelativeLayout) findViewById(R.id.root);
	}

	/**
	 * 请求数据的主体
	 */
	private void requestData() {
		this.content_label.setText(this.homeworkModel.getTitle());

		if (TextUtils.isEmpty(this.homeworkModel.getDetail())) {
			this.detail_label.setVisibility(View.GONE);
		} else {
			WebViewUtil.loadContentWithPic(this.homeworkModel.getDetail(), detail_label,this);
		}

		this.answer_label.setText(this.homeworkModel.getNote());// 原来的草稿

		this.time_label.setText(this.getString(R.string.homework_detail_time,
				new Object[] { this.homeworkModel.getStartDateWithYear(), this.homeworkModel.getEndDateWithYear() }));
	}

	// 涉及到activity的回调，因此要调用一下控件的这个方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		choic_carme_pic.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 拦截返回事件
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == 4) {
			if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
				return super.onKeyUp(keyCode, event);
			} else {
				finishWithResult();
				return true;
			}
		} else {
			return super.onKeyUp(keyCode, event);
		}
	}

	private void setResultAndFinish(int code) {
		Intent retIntent = new Intent();
		Bundle retBundle = new Bundle();
		retBundle.putSerializable("homeworkModel", MCHomeworkCommitActivity.this.homeworkModel);
		retIntent.putExtras(retBundle);
		setResult(code, retIntent);
		finish();
	}

	/**
	 * 有变动的提示保存草稿
	 */
	private void finishWithResult() {
		String beforeText = homeworkModel.getNote() == null ? "" : homeworkModel.getNote();
		if (!beforeText.equals(this.answer_label.getText().toString()) || !this.choic_carme_pic.isValueChanged()) {
			final MCCommonDialog customDialog = new MCCommonDialog(null,"您的答案有变动，要保存草稿吗？",R.layout.okcancel_dialog);
			customDialog.show(createDialog.getFragmentTransaction(this),"homecommit");
		
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					customDialog.setCommitListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							boolean result = saveHomeworkToLocal();// 存草稿
							String tipInfo = result ? "草稿保存成功！" : "草稿保存失败！";
							final MCCommonDialog inner = createDialog.createOkDialog(MCHomeworkCommitActivity.this,tipInfo);
							mHandler.postDelayed(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									inner.setCommitListener(new android.view.View.OnClickListener() {
										@Override
										public void onClick(View v) {
											inner.dismiss();
											setResultAndFinish(-1);
										}
									});
								}
							}, 200);
							customDialog.dismiss();
						}
					});
					
					customDialog.setCancelListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							setResultAndFinish(-1);
							customDialog.dismiss();
						}
					});
				}
			}, 200);
		} else {
			this.finish();
		}
	}

	private boolean saveHomeworkToLocal() {
		List<String> picPaths = this.choic_carme_pic.getAllFilePaths();
		String content = this.answer_label.getText().toString();
		this.homeworkModel.setNote(content);
		this.homeworkModel.setPicPathList(picPaths);
		return service.saveHomeworkToLocal(this.homeworkModel, loginId, this);
	}

	private void uploadHomework(List<MCUploadModel> fileList, String content) {
		// 这里封装上传的图片的路径，用于web端显示效果。
		StringBuffer sb = new StringBuffer();

		sb.append("<p><p>").append(content).append("</p>");
		if (fileList != null && fileList.size() > 0) {
			for (MCUploadModel model : fileList) {
				if (model.isSuccess()) {
					sb.append("<p><img src=\"" + model.getLink() + "\"  title=\"" + model.getTitle() + "\" alt=\"" + model.getAlt() + "\" /></p> ");
				}
			}
		}
		sb.append("</p>");

		this.homeworkModel.setNote(sb.toString());

		service.commitHomeWork(this.homeworkModel, 1, new MCAnalyzeBackBlock() {
			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
				if (loading_dialog.isVisible()) {
					loading_dialog.dismiss();
				}
				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
					createDialog.createOkDialog(MCHomeworkCommitActivity.this,"联网失败！");
				} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_FAILURE) {
					createDialog.createOkDialog(MCHomeworkCommitActivity.this,"保存失败！");
				} else {
					final MCCommonDialog inner = createDialog.createOkDialog(MCHomeworkCommitActivity.this,"提交成功！");
					mHandler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							inner.setCommitListener(new android.view.View.OnClickListener() {
								@Override
								public void onClick(View v) {
									inner.dismiss();
									setResultAndFinish(-2);
								}
							});
							inner.setCancelable(false);
						}
					}, 200);
				}
			}
		}, this);

	}

	private void commitHomework() {
		final String content = this.answer_label.getText().toString();
		List<String> list = this.choic_carme_pic.getAllFilePaths();
		if (TextUtils.isEmpty(content.trim()) && (list == null || list.size() == 0)) {
			MCToast.show(this, "内容不能为空");
			return;
		}
		loading_dialog  = new MCCommonDialog(null,"亲，你的作业正在提交中，\n请耐心等待一下下~",R.layout.loading_dialog);
		loading_dialog.show(createDialog.getFragmentTransaction(this),"homework");

		if (list != null && list.size() > 0) {
			// 上传文件
			this.service.uploadFiles(list, new MCAnalyzeBackBlock() {
				@Override
				public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
					if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
						uploadHomework(resultList, content);
					} else {
						createDialog.createOkDialog(MCHomeworkCommitActivity.this,"附件上传失败~");
						loading_dialog.dismiss();
					}
				}
			}, this);
		} else {
			uploadHomework(null, content);
		}

	}

}
