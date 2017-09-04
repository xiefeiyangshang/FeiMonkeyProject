package com.whatyplugin.base.dialog;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.whatyplugin.imooc.ui.mymooc.MoocApplication;
import com.whatyplugin.uikit.dialog.MCCommonDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.whatyplugin.mooc.R;

public class MCCreateDialog {
    private Handler mHandler = new Handler();

    private static MCCreateDialog instance;

    /**
     * 提供一个单例方法来使用
     * @return
     */
    public static MCCreateDialog getInstance() {
        if (instance == null) {
            instance = new MCCreateDialog();
        }
        return instance;
    }

    /**
     * 只有一个OK 按钮的对话框
     *
     * @param ac
     * @param content
     * @return
     */
    public MCCommonDialog createOkDialog(Activity ac, String content) {
        MCCommonDialog okDialog = new MCCommonDialog(null, content, R.layout.ok_dialog);
        okDialog.show(getFragmentTransaction(ac), "ok");
        return okDialog;
    }

    /**
     * 带确定 取消的按钮
     *
     * @param ac
     * @param content
     * @return
     */

    public MCCommonDialog createOkCancelDialog(Activity ac, String content) {
        MCCommonDialog okcancelDialog = new MCCommonDialog(null, content, R.layout.okcancel_dialog);
        okcancelDialog.show(getFragmentTransaction(ac), "okcancel");
        return okcancelDialog;
    }

    /**
     * 带动画的 正在加载
     *
     * @param ac
     * @param content
     * @return
     */
    public MCCommonDialog createLoadingDialog(Activity ac, String content, int type) {
        final MCCommonDialog loadingDialog;
        if (type == 1) {
            loadingDialog = new MCCommonDialog(null, content, R.layout.loading_horizontal_dialog);
        } else {
            loadingDialog = new MCCommonDialog(null, content, R.layout.loading_dialog);
        }
        loadingDialog.show(getFragmentTransaction(ac), "loading");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView iv = loadingDialog.getImageView();
                if (iv != null) {
                    Animation animation = AnimationUtils.loadAnimation(MoocApplication.getInstance(), R.anim.dialog_loading_anim);
                    iv.startAnimation(animation);
                }
            }
        }, 200);
        return loadingDialog;
    }

    /**
     * 获取事务
     *
     * @param ac
     * @return
     */
    public static FragmentTransaction getFragmentTransaction(Activity ac) {
        FragmentTransaction ft = ac.getFragmentManager().beginTransaction();
        return ft;
    }

    /**
     * 列表类型选择对话框
     * @param mContext
     * @param title
     * @param items
     * @return
     */
    public MCCommonDialog createSelectDialog(Activity mContext, String title, List<String> items, final AdapterView.OnItemClickListener listener) {

        final List<Map<String, String>> listMapData = new ArrayList<Map<String, String>>();
        String key = "SITE-KEY";
        for (String str : items) {
            Map<String, String> item = new HashMap<String, String>();
            item.put(key, str);
            listMapData.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(mContext, listMapData, R.layout.listview_dialog_textview_items, new String[]{key},
                new int[]{R.id.onlytextview});
        final MCCommonDialog listDialog = new MCCommonDialog(title, R.layout.listview_dialog, adapter);
        listDialog.show(this.getFragmentTransaction(mContext), key);
        if (listener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listDialog.setItemListener(listener);
                }
            }, 200);
        }
        return listDialog;
    }

}
