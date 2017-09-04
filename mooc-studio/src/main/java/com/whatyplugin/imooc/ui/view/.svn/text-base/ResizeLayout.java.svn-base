package com.whatyplugin.imooc.ui.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ResizeLayout extends LinearLayout {
    public interface OnResizeListener {
        void OnResize(int arg1, int arg2, int arg3, int arg4);
    }

    private OnResizeListener mListener;

    public ResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(this.mListener != null) {
            this.mListener.OnResize(w, h, oldw, oldh);
        }
    }

    public void setOnResizeListener(OnResizeListener l) {
        this.mListener = l;
    }
}

