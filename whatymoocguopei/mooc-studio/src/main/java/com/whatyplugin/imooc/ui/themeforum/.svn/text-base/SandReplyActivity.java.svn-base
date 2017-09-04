package com.whatyplugin.imooc.ui.themeforum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCUploadModel;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCFourmService;
import com.whatyplugin.imooc.logic.service_.MCFourmServiceInterface;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.utils.StringUtils;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;
import com.whatyplugin.imooc.ui.view.ChoicePicFromLocalView;
import com.whatyplugin.uikit.dialog.MCCommonDialog;

import java.util.List;

import cn.com.whatyplugin.mooc.R;

public class SandReplyActivity extends MCBaseActivity {

	private String courseId;
	private EditText edit;
	private MCFourmServiceInterface service;
	private MCStudyService serviceUpdateFile;
	private TextView tv_count;
	private String titleText;
	private ChoicePicFromLocalView pic_layout;
	private LinearLayout pic_content_layout;
	private MCCommonDialog loading_dialog;
	public String itemId;
	private BaseTitleView titleView;
	private ScrollView sv_myScrollView;
	private Handler changeScrollViewHandler = new Handler();
	private int scrollViewBottom;
	private MCCreateDialog createDialog = new MCCreateDialog();
	private Handler mHandler = new Handler();
	public MCAnalyzeBackBlock backBlockForUplodload;
	public MCAnalyzeBackBlock sandSavePostReply;
	private String content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sandreply);
		this.service = new MCFourmService();
		serviceUpdateFile = new MCStudyService();
		this.courseId = getIntent().getStringExtra("courseId");
		this.titleText = getIntent().getStringExtra("title");
		this.itemId = getIntent().getStringExtra("itemId");
		initView();
		initEvent();
		initValues();

	}

	private void initValues() {
		pic_layout.setVisibility(showPic() ? View.VISIBLE : View.GONE);
		backBlockForUplodload = new MCAnalyzeBackBlock() {
			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
					sendReplyContent(resultList, content);
				} else {
					if (loading_dialog != null) {
						loading_dialog.dismiss();
					}
					createDialog.createOkDialog(SandReplyActivity.this, "附件上传失败……");
				}
			}
		};

		sandSavePostReply = new MCAnalyzeBackBlock() {
			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
				if (loading_dialog != null && loading_dialog.isVisible()) {
					loading_dialog.dismiss();
				}
				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
					createDialog.createOkDialog(SandReplyActivity.this, "联网失败！");
				} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_FAILURE) {
					createDialog.createOkDialog(SandReplyActivity.this, "保存失败！");
				} else  {
					final MCCommonDialog inner = createDialog.createOkDialog(SandReplyActivity.this, "提交成功！");
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							inner.setCommitListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									Intent intent = new Intent(MCForumCommon.UPDATETHEMENUM);
									intent.putExtra("id", itemId);
									SandReplyActivity.this.sendBroadcast(intent);
									setResult(MCForumCommon.SEND_FORUM_RESULT, intent);
									finish();
								}
							});
							inner.setCancelable(false);
						}
					}, 200);
				} 
			}
		};

	}

	private void initEvent() {
		titleView.setRigTextListener(new RightClickListener() {
			@Override
			public void onRightViewClick() {
				checkPicAndSendReply();
			}
		});
		titleView.findViewById(R.id.left_img).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edit.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				SandReplyActivity.this.finish();
			}
		});
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
				tv_count.setText(length + "/1000");
				if (length < 1000) {
					tv_count.setTextColor(0xffafafaf);
				} else {
					tv_count.setTextColor(0xffd80001);
				}

			}
		});

		// 获取点击事件进行scrollview滚动
		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int onclickBottom = sv_myScrollView.getBottom();
				if (onclickBottom > scrollViewBottom) {
					changeScrollViewHandler.postDelayed(new Runnable() {

						@Override
						public void run() {

							sv_myScrollView.fullScroll(View.FOCUS_DOWN);
							scrollViewBottom = sv_myScrollView.getBottom();
						}
					}, 200);
				}

			}
		});

	}

	private void initView() {
		this.titleView = (BaseTitleView) findViewById(R.id.rl_titile);
		titleView.setTitle(titleText);
		tv_count = (TextView) findViewById(R.id.tv_count);
		edit = (EditText) findViewById(R.id.et_desc);
		sv_myScrollView = (ScrollView) findViewById(R.id.scorll_view);
		titleView.setRightTextVisible(true);
		this.pic_content_layout = (LinearLayout) this.findViewById(R.id.pic_linearlayout);
		this.pic_layout = (ChoicePicFromLocalView) this.findViewById(R.id.pic_layout);
		this.pic_layout.setContentWrap(this.pic_content_layout);// 传入要显示图片的布局
	}

	// 最终的保存方法
	private void sendReplyContent(List<MCUploadModel> fileList, String content) {
		// 这里封装上传的图片的路径，用于web端显示效果。
		StringBuffer sb = new StringBuffer();
		sb.append(content);// 内容
		if (fileList != null && fileList.size() > 0) {
			for (MCUploadModel model : fileList) {
				if (model.isSuccess()) {
					sb.append("<p><img src=\"" + model.getLink() + "\"  title=\"" + model.getTitle() + "\" alt=\"" + model.getAlt() + "\" /></p> ");

				}
			}
		}
		// 发送
		sendReply(itemId, sb.toString(), sandSavePostReply);

	}

	public void checkPicAndSendReply() {
		content = edit.getText().toString();
		if (StringUtils.isWhiteSpace(content) && content.trim().equals("")) {
			Toast.makeText(this, "提交内容不能为空哦", Toast.LENGTH_LONG).show();
		} else {
			loading_dialog = createDialog.createLoadingDialog(this, "亲，你的回复正在提交中，\n请耐心等待一下下~",0);
			List<String> pathList = this.pic_layout.getAllFilePaths();
			if (pathList != null && pathList.size() > 0) {
				// 上传文件
				sendPic(pathList, backBlockForUplodload);

			} else {
				sendReplyContent(null, content);
			}
		}
	}

	@Override
	// 涉及到activity的回调，因此要调用一下控件的这个方法
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.pic_layout.onActivityResult(requestCode, resultCode, data);
	}

	// 上传图片用到的service。子类可继承
	public void sendPic(List<String> pathList, MCAnalyzeBackBlock backBlockForUplodload) {
		this.serviceUpdateFile.uploadFiles(pathList, backBlockForUplodload, this);
	}

	// 发送可继承
	public void sendReply(String itemId, String conment, MCAnalyzeBackBlock sandSavePostReply) {
		this.service.sendRepyly(courseId, itemId, conment, sandSavePostReply, this);
	}

	/**
	 * 是否可上传图片 pic_layout
	 */
	public Boolean showPic() {
		return true;
	}

}
