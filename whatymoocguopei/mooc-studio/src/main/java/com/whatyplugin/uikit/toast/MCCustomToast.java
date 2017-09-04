package com.whatyplugin.uikit.toast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.whatyplugin.mooc.R;

/**
 * Created by whaty on 2016/1/26.
 */
public class MCCustomToast extends Toast {
    public TextView mTextView;
    public ImageView mImageView;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public MCCustomToast(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_style_center, null, false);
        mTextView = (TextView) view.findViewById(R.id.jd_toast_txt);
        mImageView = (ImageView) view.findViewById(R.id.jd_toast_image);
        this.setView(view);
        setGravity(17, 0, 0);
    }

    public void  setMsg(String msg,int type){
        if (this.mImageView != null) {
            switch (type) {
                case 1: {
                    this.mImageView.setBackgroundResource(R.drawable.toast_exclamation);
                    break;
                }
                case 2: {
                    this.mImageView.setBackgroundResource(R.drawable.toast_tick);
                    break;
                }
            }
            if (this.mTextView != null) {
                this.mTextView.setText(msg);
            }

        }
    }

}
