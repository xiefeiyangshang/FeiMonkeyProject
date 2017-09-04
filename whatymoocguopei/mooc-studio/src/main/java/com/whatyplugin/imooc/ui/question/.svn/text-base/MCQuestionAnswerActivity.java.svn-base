package com.whatyplugin.imooc.ui.question;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;
import com.whatyplugin.imooc.ui.view.ChoicePicFromLocalView;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

/**
 * 回答问题
 * 
 * @author 马彦君
 * 
 */
public class MCQuestionAnswerActivity extends MCBaseActivity {

	private EditText edit;
	private MCStudyServiceInterface service;
	private TextView tv_count;
	private TextView title;
	private String questionId;
	private ChoicePicFromLocalView pic_layout;
	private LinearLayout pic_content_layout;
	private MCCreateDialog createDialog = new MCCreateDialog();
	private MCCommonDialog loading_dialog;
	private String questionBody;
	private BaseTitleView titleView;
	private Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_answer);
		this.service = new MCStudyService();
		initView();
		initData();
		initEvent();
	}

	private void initData() {
		if(getIntent()!=null)
		{	
			this.questionId = getIntent().getStringExtra("questionId");
			this.questionBody = getIntent().getStringExtra("questionBody");
			title.setText(questionBody);
		}
		titleView.setRightTextVisible(true);
	}

	private void initEvent() {
		edit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				int length = edit.getText().toString().length();
				tv_count.setText(length + "/400");
				tv_count.setTextColor(length < 400?0xffafafaf:0xffd80001);
				
			}
		});
		titleView.setRigTextListener(new RightClickListener() {
			
			@Override
			public void onRightViewClick() {
				checkPicAndSendQuestion();
			}
		});
	}

	private void initView() {
		tv_count = (TextView) findViewById(R.id.tv_count);
		edit = (EditText) findViewById(R.id.et_desc);
		this.edit.setHint("请输入您的答案(最少四个字哦)");
		title = (TextView) findViewById(R.id.title);
		titleView = (BaseTitleView) findViewById(R.id.rl_titile);
		this.pic_content_layout = (LinearLayout) this
				.findViewById(R.id.pic_linearlayout);
		this.pic_layout = (ChoicePicFromLocalView) this
				.findViewById(R.id.pic_layout);
		this.pic_layout.setContentWrap(this.pic_content_layout);// 传入要显示图片的布局
		
	}

	// 最终的保存方法
	private void sendAnswerContent(List<MCUploadModel> fileList, String content) {
		this.service.sendAnswer("0", "", content, fileList, this.questionId,
				new MCAnalyzeBackBlock() {
					@Override
					public void OnAnalyzeBackBlock(MCServiceResult result,
							List resultList) {
						if (loading_dialog!=null&&loading_dialog.isVisible()) {
							loading_dialog.dismiss();
						}
						if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
							createDialog.createOkDialog(MCQuestionAnswerActivity.this, "联网失败！");
						} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_FAILURE) {
							createDialog.createOkDialog(MCQuestionAnswerActivity.this, "保存失败！");
						} else {
							final MCCommonDialog inner = createDialog.createOkDialog(MCQuestionAnswerActivity.this, "提交成功！");
							mHandler.postDelayed(new Runnable() {
								@Override
								public void run() {
									inner.setCommitListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											inner.dismiss();
											MCQuestionAnswerActivity.this.setResult(1, new Intent());
											MCQuestionAnswerActivity.this.finish();
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
			Toast.makeText(this, "提交内容不能为空哦", 0).show();
		} else if (content.length() < 4) {
			Toast.makeText(this, "最少输入四个字哦亲", 0).show();
		}  
		else if(content.length()>400){
			MCToast.show(this, "不能超过400个字哦");
		}
		else {
			loading_dialog = createDialog.createLoadingDialog(this, "亲，你的回复正在提交中，\n请耐心等待一下下~",0);
			List<String> pathList = this.pic_layout.getAllFilePaths();
			if (pathList != null && pathList.size() > 0) {
				// 上传文件
				this.service.uploadFiles(pathList, new MCAnalyzeBackBlock() {
					@Override
					public void OnAnalyzeBackBlock(MCServiceResult result,
							List resultList) {
						if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
							sendAnswerContent(resultList, content);
						} else {
							createDialog.createOkDialog(MCQuestionAnswerActivity.this, "附件上传失败……");
						}
					}
				}, this);
			} else {
				sendAnswerContent(null, content);
			}
		}
	}

	@Override
	// 涉及到activity的回调，因此要调用一下控件的这个方法
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.pic_layout.onActivityResult(requestCode, resultCode, data);
	}
}
