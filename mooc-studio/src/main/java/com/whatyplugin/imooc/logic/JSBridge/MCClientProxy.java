package com.whatyplugin.imooc.logic.JSBridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.widget.AdapterView;

import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.uikit.dialog.MCCommonDialog;

import java.util.ArrayList;
import java.util.List;

class MCClientProxy {
    private Activity mContext;

    MCClientProxy(Activity context) {
        mContext = context;
    }

    /**
     * 打开新的地址
     *
     * @param link
     */
    public void openView(String link) {
        Intent intent = new Intent(mContext, MCWebViewNetActivity.class);
        intent.putExtra("link", link);
        mContext.startActivity(intent);
    }

    /**
     * 弹出选择站点的对话框
     *
     * @param title
     * @param content
     * @param result
     */
    public void openSelectDialog(String title, String content, final JsPromptResult result) {
        String[] val = content.split("-");
        List<String> list = new ArrayList<>();
        for (String str : val) {
            list.add(str);
        }

        final MCCommonDialog dialog = MCCreateDialog.getInstance().createSelectDialog(mContext, title, list, null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.setItemListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        result.confirm("{\"result\":\"" + position + "\"}");
                    }
                });

                dialog.setOnDismissListener(new MCCommonDialog.IOnDismissListener() {
                    @Override
                    public void onDismiss() {
                        result.confirm("{\"result\":\"\"}");
                    }
                });
            }
        }, 200);
    }

    /**
     * 打开确认对话框
     *
     * @param message
     * @param result
     */
    public void openConfirmDialog(String message, final JsResult result) {
        final MCCommonDialog dialog = MCCreateDialog.getInstance().createOkCancelDialog(mContext, message);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                dialog.setCommitListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        result.confirm();
                    }
                });

                dialog.setCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        result.cancel();
                    }
                });

                dialog.setOnDismissListener(new MCCommonDialog.IOnDismissListener() {
                    @Override
                    public void onDismiss() {
                        result.cancel();
                    }
                });
            }
        }, 200);
    }

}
