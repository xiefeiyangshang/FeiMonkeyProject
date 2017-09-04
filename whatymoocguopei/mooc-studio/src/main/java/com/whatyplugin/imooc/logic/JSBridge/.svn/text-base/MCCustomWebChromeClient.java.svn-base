package com.whatyplugin.imooc.logic.JSBridge;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.whatyplugin.base.log.MCLog;

class MCCustomWebChromeClient extends WebChromeClient {

    private Activity mContext = null;
    private MCClientProxy mClientProxy = null;

    private static final String TAG = "MCCustomWebChromeClient";

    MCCustomWebChromeClient(Activity context) {
        mContext = context;
        mClientProxy = new MCClientProxy(mContext);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        result.confirm();
        if (mContext != null) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        mClientProxy.openConfirmDialog(message, result);
        return true;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        MCLog.e(TAG, "onJsPrompt: " + message);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject == null) {
            return true;
        }

        String cmd = jsonObject.optString("command");
        if ("openview".equals(cmd)) {
            mClientProxy.openView(jsonObject.optString("link"));
            result.confirm("{\"result\":\"1\"}");
        } else if ("select".equals(cmd)) {
            mClientProxy.openSelectDialog("111", jsonObject.optString("options"), result);
        }
        return true;
    }

}
