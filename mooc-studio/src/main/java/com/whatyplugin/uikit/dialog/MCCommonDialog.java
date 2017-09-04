package com.whatyplugin.uikit.dialog;

import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.define.MCBaseDefine.MCUpgradeType;

public class MCCommonDialog extends DialogFragment {

    public interface INetworkListener {
        void cancel();

        void backgrounder();

        void update(MCUpgradeType arg1);
    }

    public interface IOnDismissListener {
        void onDismiss();
    }

    private Context context;
    private Handler handler;
    private String title;
    private String content;
    private String leftButtonName;
    private int resId;
    private View view;
    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_cancel;
    private TextView tv_commit;
    private View.OnClickListener commitListener;
    private View.OnClickListener cancelListener;
    private IOnDismissListener onDismissListener;
    private ListView listView;
    private SimpleAdapter contentListViewAdpater;
    private BroadcastReceiver receiver;

    private ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    private MCUpgradeType type;

    public MCUpgradeType getType() {
        return type;
    }

    public void setType(MCUpgradeType type) {
        this.type = type;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private INetworkListener listener;

    public INetworkListener getListener() {
        return listener;
    }

    public void setListener(INetworkListener listener) {
        this.listener = listener;
    }

    public MCCommonDialog(int resId) {
        this.resId = resId;
    }

    public MCCommonDialog() {
        super();
    }

    /*
     *普通对话框
     *清除缓存对话框、提示开启wifi网络、退出对话框
     */
    public MCCommonDialog(String title, String content, int resId) {
        super();
        this.resId = resId;
        this.title = title;
        this.content = content;
    }

    /*
     * 更新提示对话框
     */
    public MCCommonDialog(String title, String leftButtonName, String content, int resId, MCUpgradeType type, Handler handler) {
        this.title = title;
        this.handler = handler;
        this.resId = resId;
        this.content = content;
        this.leftButtonName = leftButtonName;
    }

    /*
     * 下载进度条对话框
     */
    public MCCommonDialog(String title, String content, int resId, int x) {

    }

    /*
     * 带有listview的对话框
     */
    public MCCommonDialog(String title, int resId, SimpleAdapter adapter) {
        this.title = title;
        this.resId = resId;
        this.contentListViewAdpater = adapter;
    }

    /*
     * 初始化view
     */
    private void initView(View view) {
        tv_title = (TextView) view.findViewById(R.id.title);
        tv_cancel = (TextView) view.findViewById(R.id.cancel);
        tv_commit = (TextView) view.findViewById(R.id.commit);
        tv_content = (TextView) view.findViewById(R.id.content);
        listView = (ListView) view.findViewById(R.id.listviewdialog);
        imageView = (ImageView) view.findViewById(R.id.loading_pic);
        if (leftButtonName != null) {
            tv_commit.setText(leftButtonName);
        }

        if (title == null && tv_title != null) {
            if (tv_title.getText() == null)
                tv_title.setVisibility(View.GONE);
        } else {
            if (tv_title != null)
                tv_title.setText(title);
        }
        if (tv_content != null && content != null)
            tv_content.setText(content);
        if (tv_cancel != null)
            tv_cancel.setOnClickListener(cancelListener);
        if (tv_commit != null)
            tv_commit.setOnClickListener(commitListener);
        if (contentListViewAdpater != null && listView != null) {
            listView.setAdapter(contentListViewAdpater);
        }
        if (imageView != null) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.dialog_loading_anim);
            imageView.startAnimation(animation);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.none);
        //去掉dialogfragment自带的title
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.view = inflater.inflate(resId, container, false);
        this.context = this.getActivity();
        cancelListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MCCommonDialog.this.dismiss();
            }
        };
        commitListener = cancelListener;
        initView(view);
        return view;
    }

    public void setCommitListener(android.view.View.OnClickListener listener) {
        if (tv_commit != null)
            tv_commit.setOnClickListener(listener);
    }

    public void setCancelListener(android.view.View.OnClickListener listener) {
        if (tv_cancel != null)
            tv_cancel.setOnClickListener(listener);
    }

    public void setCommitText(String text) {
        tv_commit.setText(text);
    }

    public void setCancelText(String text) {
        if (tv_cancel != null)
            tv_cancel.setText(text);
    }

    public void setContentText(String text) {
        if (tv_content != null)
            tv_content.setText(text);
    }

    public void setTitle(String title) {
        if (tv_title != null) {
            if (tv_title.getVisibility() == View.GONE)
                tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }
    }

    public TextView getContent() {
        return tv_content;
    }

    public void setContent(String content) {
        if (tv_content != null)
            tv_content.setText(content);
    }

    public void setNetworkListener(INetworkListener l) {
        this.listener = l;
    }

    public void setItemListener(OnItemClickListener listener) {
        if (listView != null) {
            listView.setOnItemClickListener(listener);
        }
    }

    public void setNoTitle() {
        if (tv_title != null)
            tv_title.setVisibility(View.GONE);
    }

    public void setOnDismissListener(IOnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
        super.onDismiss(dialog);
    }
}
