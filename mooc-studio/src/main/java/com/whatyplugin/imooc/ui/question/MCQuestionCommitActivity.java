package com.whatyplugin.imooc.ui.question;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCUploadModel;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.logic.utils.StringUtils;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;
import com.whatyplugin.imooc.ui.view.ChoicePicFromLocalView;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

/**
 * 回答问题
 * 
 * @author 发送问题
 * 
 */
public class MCQuestionCommitActivity extends FragmentActivity implements MCAnalyzeBackBlock {

	private String courseId;
	private EditText edit;
	private BaseTitleView titleView;
	private MCStudyServiceInterface service;
	private TextView tv_count;
	private int i = 0;
	private ChoicePicFromLocalView pic_layout;
	private LinearLayout pic_content_layout;
	private MCCreateDialog createDialog = new MCCreateDialog();
	private MCCommonDialog loading_dialog;
	private Handler mHandler = new Handler();

	public MCQuestionCommitActivity() {
		super();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		setContentView(R.layout.activity_send_question);
		
		initView();
		initData();
		initEvent();

	}

	private void initEvent() {
		edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				int length = edit.getText().toString().length();
				tv_count.setText(length + "/400");
				if (length < 400) {
					tv_count.setTextColor(0xffafafaf);
				} else {
					tv_count.setTextColor(0xffd80001);
				}
			}
		});
		titleView.setRigTextListener(new RightClickListener() {
			
			@Override
			public void onRightViewClick() {
				checkPicAndSendQuestion();
			}
		});
	}

	private void initData() {
		if (getIntent() != null)
			this.courseId = getIntent().getStringExtra("courseId");
		this.service = new MCStudyService();
	}

	private void initView() {
		titleView = (BaseTitleView) this.findViewById(R.id.rl_titile);
		tv_count = (TextView) findViewById(R.id.tv_count);
		edit = (EditText) findViewById(R.id.et_desc);
		this.pic_content_layout = (LinearLayout) this.findViewById(R.id.pic_linearlayout);
		this.pic_layout = (ChoicePicFromLocalView) this.findViewById(R.id.pic_layout);
		this.pic_layout.setContentWrap(this.pic_content_layout);// 传入要显示图片的布局
	}
	// 最终的保存方法
	private void sendQuestionContent(List<MCUploadModel> fileList, String content) {
		this.service.sendQuestion(this.courseId, content, fileList, new MCAnalyzeBackBlock() {

			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
				if (loading_dialog.isVisible()) {
					loading_dialog.dismiss();
				}
				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
					createDialog.createOkDialog(MCQuestionCommitActivity.this, "联网失败！");
				} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_FAILURE) {
					createDialog.createOkDialog(MCQuestionCommitActivity.this, "保存失败！");
				} else {
					final MCCommonDialog inner = createDialog.createOkDialog(MCQuestionCommitActivity.this, "提交成功！");
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							inner.setCommitListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									inner.dismiss();
									MCQuestionCommitActivity.this.setResult(1, new Intent());
									MCQuestionCommitActivity.this.finish();
								}
							});
							inner.setCancelable(false);
						}
					}, 200);
				}

			}
		}, this);

	}

	public void checkPicAndSendQuestion() {
		final String content = edit.getText().toString();
		if (StringUtils.isWhiteSpace(content)) {
			MCToast.show(this, "提交内容不能为空哦");
		} else if (content.length() < 4) {
			MCToast.show(this, "最少输入四个字哦亲");
		} 
		else if(content.length()>400){
			MCToast.show(this, "不能超过400个字哦");
		}
		else {
			loading_dialog = createDialog.createLoadingDialog(this, "亲，你的问题正在提交中，\n请耐心等待一下下~",0);
			List<String> pathList = this.pic_layout.getAllFilePaths();
			if (pathList != null && pathList.size() > 0) {
				// 上传文件
				this.service.uploadFiles(pathList, new MCAnalyzeBackBlock() {
					@Override
					public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
						if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
							sendQuestionContent(resultList, content);
						} else {
							createDialog.createOkDialog(MCQuestionCommitActivity.this, "附件上传失败……");
						}
					}
				}, this);
			} else {
				sendQuestionContent(null, content);
			}

		}
	}

	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {

		if ("成功".equals(result.getResultDesc())) {
			Toast.makeText(this, "提交成功", 1).show();
			// TODO 回调
			this.finish();
		}
	}
	@Override
	// 涉及到activity的回调，因此要调用一下控件的这个方法
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.pic_layout.onActivityResult(requestCode, resultCode, data);
	}

}
