package com.whatyplugin.imooc.ui.mymooc;

import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCResourceModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseService;
import com.whatyplugin.imooc.logic.service_.MCCourseServiceInterface;
import com.whatyplugin.imooc.logic.utils.WebViewUtil;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.uikit.dialog.MCLoadDialog;
import com.whatyplugin.uikit.toast.MCToast;

public class PicTxtWebviewActivity extends MCBaseActivity implements MCAnalyzeBackBlock {
	private MCCourseServiceInterface mCourseService;
	private Dialog dialog_loading;
	private Animation hyperspaceJumpAnimation;
	private MCSectionModel section;
	private WebView wb_pictxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview_txt);
		wb_pictxt = (WebView) findViewById(R.id.wb_pictxt);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			section = (MCSectionModel) extras.getSerializable("section");
		}
		if (TextUtils.isEmpty(section.getNote())) {
			mCourseService = new MCCourseService();
			this.hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.dialog_loading_anim);
			this.dialog_loading = MCLoadDialog.createLoadingDialog(this, this.getString(R.string.choice_dialog_loading), R.drawable.dialog_loading,
					this.hyperspaceJumpAnimation);
			if (!TextUtils.isEmpty(section.getId())) {
				mCourseService.getResouce(section.getId(), (MCAnalyzeBackBlock) this, this);
				dialog_loading.show();
			}
		} else {
			WebViewUtil.loadContentWithPic(section.getNote(), wb_pictxt, this);
		}
		BaseTitleView titleView = (BaseTitleView) this.findViewById(R.id.rl_titile);
		titleView.setTitle(section.getOrgName());
	}

	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
		dialog_loading.dismiss();
		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
			if (resultList != null && resultList.size() > 0) {
				MCResourceModel resourceModel = (MCResourceModel) resultList.get(0);
				if (resourceModel == null) {
					return;
				}
				WebViewUtil.loadContentWithPic(resourceModel.getContent(), wb_pictxt, this);
			}
		} else if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_EMPTY && (result.isExposedToUser())) {
			MCToast.show(this, "暂无内容");
		} else {
			MCToast.show(this, "暂无内容");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		WebViewUtil.DestoryWebView(wb_pictxt);

	}
}
