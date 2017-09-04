package com.whatyplugin.base.login;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;

public class MCLogin implements Handler.Callback {
    public enum MCLoginType {
        

            MC_LOGIN_TYPE_QQ("MC_LOGIN_TYPE_QQ", 0, "QZone"),
            MC_LOGIN_TYPE_SINA("MC_LOGIN_TYPE_SINA", 1, "SinaWeibo");
        private final String logintype;
        private MCLoginType(String arg1, int arg2, String logintype) {
            this.logintype = logintype;
        }

        public String getLoginType() {
            return this.logintype;
        }

    }

    private static final String GRAPH_SIMPLE_USER_INFO = "user/get_simple_userinfo";
    private String data;
    private Handler handler;
    private static MCLogin instance;
    private MCLoginBackListener listener;
    private MCLoginConfigInterface mConfig;
    private Context mContext;
    private static final String TAG = MCLogin.class.getSimpleName();
    static {
        MCLogin.instance = new MCLogin();
    }

    public MCLogin() {
        super();
        this.mConfig = null;
    }

    static MCLoginConfigInterface access$0(MCLogin arg1) {
        return arg1.mConfig;
    }

    public boolean handleMessage(Message msg) {
        switch(msg.what) {
            case 1: {
                this.data = String.valueOf(msg.obj);
                MCLog.i(TAG, "data:" + this.data);
                MCLog.i(TAG, "listener:" + this.listener);
                this.listener.OnLoginBack(MCCommonResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, this.mContext.getResources().getText(R.string.login_completed).toString(), this.data));
                break;
            }
            default: {
                switch(msg.arg1) {
                    case 2: {
                    	 this.listener.OnLoginBack(MCCommonResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, this.mContext.getResources().getText(R.string.login_failed).toString()));
                    }
                    case 3: {
                    	this.listener.OnLoginBack(MCCommonResult.resultWithData(MCResultCode.MC_RESULT_CODE_EMPTY, this.mContext.getResources().getText(R.string.login_canceled).toString()));
                    }
                }
            }
        }

        return false;
    }

    public void login(Context context, String typename, MCLoginBackListener OnBackListener) {
        this.mContext = context;
        this.listener = OnBackListener;
        this.handler = new Handler(((Handler.Callback)this));
    }

    public static MCLogin loginInstance() {
        return MCLogin.instance;
    }

   

    public void setConfig(MCLoginConfigInterface config) {
        this.mConfig = config;
    }
}
