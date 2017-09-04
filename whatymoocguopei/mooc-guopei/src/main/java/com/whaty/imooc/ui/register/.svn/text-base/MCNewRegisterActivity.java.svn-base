package com.whaty.imooc.ui.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.runstat.MCRunStart.MCAPPType;
import com.whatyplugin.base.runstat.MCRunStart.MCPlatType;
import com.whatyplugin.base.runstat.MCRunStart.MCServiceType;
import com.whatyplugin.imooc.logic.manager.MCRunStartManager;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCUserService;
import com.whatyplugin.imooc.logic.utils.Utils;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.uikit.resolution.MCResolution;
import com.whatyplugin.uikit.toast.MCToast;

import java.util.List;
import java.util.regex.Pattern;

import cn.com.whatyguopei.mooc.R;


public class MCNewRegisterActivity extends MCBaseActivity implements View.OnClickListener {
    ProgressDialog dialog;
    private Context mContext;
    private EditText mail;
    private EditText name;
    private EditText psw;

    public boolean isEmail(String strEmail) {
    	return Pattern.compile("^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z].", 2).matcher(((CharSequence)strEmail)).matches() ? true : false;
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.over: {
            	if(Utils.isFastDoubleClick()){
            		return;
            	}
                 final String email = this.mail.getText().toString();
                 final String password = this.psw.getText().toString();
                 
                 if(TextUtils.isEmpty(email)) {
                	 MCToast.show(this.mContext, "邮箱不能为空！");
                     break;
                 }
                 
                 if(TextUtils.isEmpty(password)) {
                	 MCToast.show(this.mContext, "密码不能为空！");
                     break;
                 }
                 
                 if(!this.isEmail(email)) {
                     MCToast.show(this.mContext, this.mContext.getResources().getString(R.string.email_error));
                 } else if(password.length()<6||password.length()>15) {
                     MCToast.show(this.mContext, this.mContext.getResources().getString(R.string.pwd_error_tip));
                 } else {
                    new MCUserService().userRegister(email, password, "", MCServiceType.MC_SERVICE_TYPE_APP, MCRunStartManager.getChannel(this.mContext), MCPlatType.MC_PLAT_TYPE_ANDROID, MCAPPType.MC_APP_TYPE_IMOOC, MCResolution.getInstance(this.mContext).getDevScreenSize(), new MCAnalyzeBackBlock() {
                        public void OnAnalyzeBackBlock(MCServiceResult result, List objs) {
                            if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
                                MCToast.show(MCNewRegisterActivity.this, "注册成功！开始登陆……");
                                Intent data = new Intent();
                                data.putExtra("email", email);
                                data.putExtra("password", password);
                                MCNewRegisterActivity.this.setResult(Activity.RESULT_OK, data);
                                MCNewRegisterActivity.this.finish();
                                return;
                            }

                            if(result.getResultCode() != MCResultCode.MC_RESULT_CODE_EMPTY) {
                                if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
                                    MCToast.show(MCNewRegisterActivity.this.mContext, MCNewRegisterActivity.this.mContext.getString(R.string.no_network_label));
                                    return;
                                }

                                if(result.isReNickName()) {
                                    MCToast.show(mContext, result.getResultDesc());
                                }
                                else {
                                    MCToast.show(mContext, result.getResultDesc());
                                }
                            }
                        }
                    }, this.mContext);
                }

            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mail_newregister_layout);
        this.mContext = this;
        initView();
        initEvent();
    }

	private void initEvent() {
		this.findViewById(R.id.over).setOnClickListener(this);
	}

	private void initView() {
		this.name = (EditText)this.findViewById(R.id.nickname_edit);
        this.mail = (EditText)this.findViewById(R.id.email_edit);
        this.psw = (EditText)this.findViewById(R.id.password_edit);
	}
}

