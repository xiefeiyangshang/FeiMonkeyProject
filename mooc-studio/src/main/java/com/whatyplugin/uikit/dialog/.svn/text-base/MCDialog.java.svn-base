package com.whatyplugin.uikit.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class MCDialog {
    public MCDialog() {
        super();
    }

    public static Dialog show(View contentView, int theme, Context context) {
        Dialog dialog = new Dialog(context, theme);
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static Dialog show(View contentView, int theme, Context context, boolean cancelable) {
        Dialog dialog = new Dialog(context, theme);
        dialog.setContentView(contentView);
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
    }

    public static void show(Context context, String title, String msg, View.OnClickListener[] listeners) {
        new AlertDialog.Builder(context).setTitle(((CharSequence)title)).setMessage(((CharSequence)msg)).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setNeutralButton("忽略", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setCancelable(false).show();
    }
}

