package com.whatyplugin.uikit.toast;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.whatyplugin.imooc.ui.mymooc.MoocApplication;

public class MCToast {
    private static Toast mToast;
    private static volatile MCCustomToast mcCustomToast;
    private static volatile Handler mHandler;

    public static void show(Context mContext, String msg) {
        if (MCToast.mToast == null) {
            MCToast.mToast = new Toast(mContext);
            MCToast.mToast = Toast.makeText(mContext, ((CharSequence) msg), 0);
        }

        MCToast.mToast.setDuration(Toast.LENGTH_SHORT);
        MCToast.mToast.setText(((CharSequence) msg));
        MCToast.mToast.show();
    }

    public static void show(Context mContext, String msg, int duration) {
        if (MCToast.mToast == null) {
            MCToast.mToast = new Toast(mContext);
            MCToast.mToast = Toast.makeText(mContext, ((CharSequence) msg), duration);
        }

        MCToast.mToast.setDuration(duration);
        MCToast.mToast.setText(((CharSequence) msg));
        MCToast.mToast.show();
    }


    private static MCCustomToast getCustomToastInstance() {
        if (mcCustomToast == null) {
            synchronized (MCCustomToast.class) {
                mcCustomToast = new MCCustomToast(MoocApplication.getInstance());
                return mcCustomToast;
            }
        }
        return mcCustomToast;
    }

    public static void toastFail(String msg) {
        showToastInCenter(msg, 1);
    }

    public static void toastSuccess(String msg) {
        showToastInCenter(msg, 2);
    }

    public static void showToastInCenter(final String msg, final int type) {
        getHandlerInstance().post(new Runnable() {
            @Override
            public void run() {
                getCustomToastInstance().setMsg(msg, type);
                mcCustomToast.setDuration(Toast.LENGTH_SHORT);
                mcCustomToast.show();
            }
        });
    }


    private static Handler getHandlerInstance() {
        if (mHandler == null) {
            synchronized (MCCustomToast.class) {
                mHandler = new Handler(Looper.getMainLooper());
                return mHandler;
            }
        }
        return mHandler;
    }
}

