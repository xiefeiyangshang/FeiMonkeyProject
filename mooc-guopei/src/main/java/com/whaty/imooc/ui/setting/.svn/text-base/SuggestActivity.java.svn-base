package com.whaty.imooc.ui.setting;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCSettingService;
import com.whatyplugin.imooc.logic.service_.MCSettingServiceInterface;
import com.whatyplugin.imooc.logic.utils.RegexValidateUtil;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;
import com.whatyplugin.uikit.dialog.MCLoadDialog;
import com.whatyplugin.uikit.toast.MCToast;

import java.util.List;

import cn.com.whatyguopei.mooc.R;


public class SuggestActivity extends MCBaseActivity implements MCAnalyzeBackBlock {
    private EditText contact_content;
    private String contact_contentString;
    private EditText content;
    private String contentString;
    private Dialog dialog_loading;
    private Animation hyperspaceJumpAnimation;
    private Context mContext;
    private MCSettingServiceInterface mSettingService;
	private BaseTitleView titleView;

    public SuggestActivity() {
        super();
    }

    public void OnAnalyzeBackBlock(MCServiceResult result, List arg6) {
        if(this.dialog_loading != null && (this.dialog_loading.isShowing())) {
            this.dialog_loading.dismiss();
        }
        if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
            Toast.makeText(this.mContext, this.mContext.getResources().getString(R.string.suggest_success), 1).show();
            this.finish();
        }
        else if(result.getResultCode() != MCResultCode.MC_RESULT_CODE_EMPTY) {
            if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_FAILURE) {
                Toast.makeText(this.mContext, this.mContext.getResources().getString(R.string.suggest_fail), 1).show();
            }
            else if(result.isExposedToUser()) {
                Toast.makeText(this.mContext, this.mContext.getResources().getString(R.string.suggest_fail), 1).show();
            }
            else {
                result.getResultCode();
            }
        }
    }
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.suggest_layout);
        
        initView();
        
        
        initData();
        
        titleView.setRigTextListener(new RightClickListener() {
			
			@Override
			public void onRightViewClick() {
				contact_contentString = contact_content.getText().toString();
				contentString = content.getText().toString();
	        	
	        	//要填写内容
	        	if(TextUtils.isEmpty(SuggestActivity.this.contentString.trim())) {
	        		Toast.makeText(mContext, R.string.suggest_tip_null, 1).show();
	        		return;
	        	}
	        	
	        	//填写内容长度控制 大于400给提示
	        	if(SuggestActivity.this.contentString.length()>=401) {
	        		Toast.makeText(mContext, R.string.suggest_tip_long, 1).show();
	        		return;
	        	}
	        	
	        	//要填写联系方式
		    	if(TextUtils.isEmpty(contact_contentString)) {
		            Toast.makeText(mContext, R.string.contact_tip_null, 1).show();
		            return;
		        }
	        	  
		    	//联系方式格式限制
	        	if(!RegexValidateUtil.checkEmail(contact_contentString) 
	        			&&!RegexValidateUtil.checkMobileNumber(contact_contentString) 
	        			&&!RegexValidateUtil.checkQQ(contact_contentString)){
	        		MCToast.show(SuggestActivity.this,getResources().getString(R.string.contact_error));
	        		return;
	        	}
	            dialog_loading = MCLoadDialog.createLoadingDialog(mContext, mContext.getString(R.string.dialog_sendding), R.drawable.dialog_loading, SuggestActivity.this.hyperspaceJumpAnimation);
	            dialog_loading.show();
	            mSettingService.sendSuggest(content.getText().toString(), contact_content.getText().toString(), ((MCAnalyzeBackBlock)SuggestActivity.this), SuggestActivity.this);
			}
		});
    }

	private void initData() {
		this.mContext = this;
        this.mSettingService = new MCSettingService();
        this.hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.dialog_loading_anim);
        titleView.setRight(R.string.submit);
	}

	private void initView() {
		this.content = (EditText)this.findViewById(R.id.content);
        this.contact_content = (EditText)this.findViewById(R.id.contact_content);
        titleView = (BaseTitleView) this.findViewById(R.id.rl_titile);
	}
}

