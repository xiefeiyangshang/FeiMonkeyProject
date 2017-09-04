package com.whatyplugin.uikit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

public class MCLoadDialog {
    public static class TimeCount extends CountDownTimer {
        Dialog dialog;

        public TimeCount(long millisInFuture, long countDownInterval, Dialog dialog) {
            super(millisInFuture, countDownInterval);
            this.dialog = dialog;
        }

        public void onFinish() {
            try {
                this.dialog.dismiss();
                this.dialog = null;
            }
            catch(Exception e) {
            }
        }

        public void onTick(long arg0) {
        }
    }

    private static final int DIALOG_HEIGHT = 480;
    private static final int DIALOG_WIDTH = 366;

    public MCLoadDialog() {
        super();
    }

    public static Dialog createLoadingDialog(Context context, String msg, int imageres, Animation anim) {
        View view = LayoutInflater.from(context).inflate(R.layout.load_dialog, null);
        ImageView dialog_image = (ImageView) view.findViewById(R.id.dialog_image);
        TextView dialog_content = (TextView) view.findViewById(R.id.dialog_content);
        dialog_image.setImageResource(imageres);
        if(anim != null) {
            dialog_image.startAnimation(anim);
        }

        dialog_content.setText(msg);
        Dialog dialog = new Dialog(context, R.style.LoadDialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        return dialog;
    }

    public static Dialog createLoginLoadingDialog(Context context) {
        int param = -1;
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.dialog_loading_anim);
        View view = LayoutInflater.from(context).inflate(R.layout.login_load_dialog, null);
        View dialog_view = view.findViewById(R.id.dialog_view);
        View dialog_image = view.findViewById(R.id.dialog_image);
        if(animation != null) {
            ((ImageView)dialog_image).startAnimation(animation);
        }

        Dialog dialog = new Dialog(context, R.style.LoginLoadDialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(dialog_view, new LinearLayout.LayoutParams(param, param));
        return dialog;
    }

    public static Dialog createUpdateDialog(Context context, String msg, int imageres, Animation anim) {
        View view = LayoutInflater.from(context).inflate(R.layout.load_dialog, null);
        View dialog_view = view.findViewById(R.id.dialog_view);
        View dialog_image = view.findViewById(R.id.dialog_image);
        TextView dialog_content = (TextView) view.findViewById(R.id.dialog_content);
        ((ImageView)dialog_image).setImageResource(imageres);
        if(anim != null) {
            ((ImageView)dialog_image).startAnimation(anim);
        }

        dialog_content.setText(((CharSequence)msg));
        Dialog dialog = new Dialog(context, R.style.LoadDialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(dialog_view, new LinearLayout.LayoutParams(480, 366));
        return dialog;
    }
}

