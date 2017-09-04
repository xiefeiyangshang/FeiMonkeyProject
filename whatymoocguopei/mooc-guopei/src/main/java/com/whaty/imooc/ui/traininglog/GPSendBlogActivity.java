package com.whaty.imooc.ui.traininglog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.whaty.imooc.logic.model.BlogModel;
import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whaty.imooc.utile.GPContants;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCUploadModel;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.utils.StringUtils;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.ChoicePicFromLocalView;
import com.whatyplugin.uikit.dialog.MCCommonDialog;

import java.util.List;

import cn.com.whatyguopei.mooc.R;

public class GPSendBlogActivity extends MCBaseActivity implements BaseTitleView.RightClickListener {
	private EditText et_title;
	private EditText et_content;
	private BaseTitleView baseTitleView;
	private ChoicePicFromLocalView fromLocalView;
	private LinearLayout pic_linearlayout;
	private Handler mHandler = new Handler();
	private MCStudyService serviceUpdateFile;
	private GPPerformanceServiceInterface service;
	private MCCommonDialog loading_dialog;
	private MCCreateDialog createDialog = new MCCreateDialog();
	private EditText et_abstractContent;
	private BlogModel blogModel;
	private String BlogId = null;
	private String abstractContent;
	private InputMethodManager imm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sandblog_activity);
		initView();
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		serviceUpdateFile = new MCStudyService();
		service = new GPPerformanceService();
		blogModel = (BlogModel) getIntent().getSerializableExtra("blogModel");
		if (blogModel != null) {
			iniDate();
		}
	}

	private void initView() {
		baseTitleView = (BaseTitleView) findViewById(R.id.basetitle);
		baseTitleView.setRigTextListener(this);
		baseTitleView.findViewById(R.id.left_img).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				closeSolf();
				finish();
				
			}
		});
		
		et_title = (EditText) findViewById(R.id.title);
		et_content = (EditText) findViewById(R.id.content);
		pic_linearlayout = (LinearLayout) findViewById(R.id.pic_linearlayout);
		fromLocalView = (ChoicePicFromLocalView) findViewById(R.id.pic_layout);
		et_abstractContent = (EditText) findViewById(R.id.tags);
		this.fromLocalView.setContentWrap(this.pic_linearlayout);// 传入要显示图片的布局
	}

	private void iniDate() {
		BlogId = blogModel.getId();
		et_title.setText(blogModel.getTitle());
		et_abstractContent.setText(blogModel.getAbstractContent());
//		 et_content.setText(Html.fromHtml(blogModel.getContent()));
		et_content.setText(blogModel.getContent());
		et_title.setSelection(blogModel.getTitle().length()); // 光标定位到最后一行

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.fromLocalView.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onRightViewClick() {

		final String title = et_title.getText().toString().trim();
		final String content = et_content.getText().toString().trim();
		 abstractContent = et_abstractContent.getText().toString().trim();
		if (StringUtils.isWhiteSpace(title)) {
			Toast.makeText(this, "标题不能为空哦", Toast.LENGTH_LONG).show();
			return;
		}
		

		if (StringUtils.isWhiteSpace(content)) {
			Toast.makeText(this, "提交内容不能为空哦", Toast.LENGTH_LONG).show();
			return;
		}
		if (StringUtils.isWhiteSpace(abstractContent)) {
			Toast.makeText(this, "摘要不能为空哦", Toast.LENGTH_LONG).show();
			return;
		}
		closeSolf();
		loading_dialog = createDialog.createLoadingDialog(this, "亲，你的日志正在发表，\n请耐心等待一下下~",0);
		List<String> pathList = this.fromLocalView.getAllFilePaths();
		if (pathList != null && pathList.size() > 0) {
			// 上传文件
			this.serviceUpdateFile.uploadFiles(pathList, new MCAnalyzeBackBlock() {
				@Override
				public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
					if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
						sendReplyContent(resultList, content, title, abstractContent);
					} else {
						loading_dialog.dismiss();
						createDialog.createOkDialog(GPSendBlogActivity.this, "附件上传失败……");
					}
				}
			}, this);
		} else {
			sendReplyContent(null, content, title, abstractContent);
		}

	}

	// 最终的保存方法
	private void sendReplyContent(List<MCUploadModel> fileList, String content, final String title, final String abstractContent) {
		// 这里封装上传的图片的路径，用于web端显示效果。
		final StringBuffer sb = new StringBuffer();
		sb.append(content);// 内容
		if (fileList != null && fileList.size() > 0) {
			for (MCUploadModel model : fileList) {
				if (model.isSuccess()) {
					sb.append("<p><img src=\"" + model.getLink() + "\"  title=\"" + model.getTitle() + "\" alt=\"" + model.getAlt() + "\" /></p> ");
				}
			}
		}
		this.service.saveMyBlog(GPSendBlogActivity.this, BlogId, title, sb.toString(), abstractContent, new MCAnalyzeBackBlock() {
			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
				if (loading_dialog.isVisible()) {
					loading_dialog.dismiss();
				}
				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
					createDialog.createOkDialog(GPSendBlogActivity.this, "联网失败！");
				} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_FAILURE) {
					createDialog.createOkDialog(GPSendBlogActivity.this, "保存失败！");
				} else {
					final MCCommonDialog inner = createDialog.createOkDialog(GPSendBlogActivity.this, "提交成功！");
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							inner.setCommitListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									// 发送广播更新我的日志列表，最新日志列表
									Intent snadeIntent;
									if (blogModel == null) {
										snadeIntent = new Intent(GPContants.REFRESHBLOG);
									} else {
										snadeIntent = new Intent(GPContants.UPDATEBLOG);
										blogModel.setContent(sb.toString());
										blogModel.setTitle(title);
										blogModel.setAbstractContent(abstractContent);
										snadeIntent.putExtra("blogModel", blogModel);
									}
									closeSolf();
									GPSendBlogActivity.this.sendBroadcast(snadeIntent);
									finish();
								}
							});
							inner.setCancelable(false);
						}
					}, 200);
				}
			}
		});
	}

	public void closeSolf(){
		imm.hideSoftInputFromWindow(et_title.getApplicationWindowToken(), 0);
	}
	
}
