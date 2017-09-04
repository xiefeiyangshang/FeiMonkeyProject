package com.whatyplugin.imooc.ui.crash;


import java.io.PrintWriter;
import java.io.StringWriter;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.view.animation.AnimationUtils;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.runstat.MCCrashSender;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.uikit.dialog.MCLoadDialog;
import com.whatyplugin.uikit.dialog.MCLoadDialog.TimeCount;

public class CrashSenderHandler implements Thread.UncaughtExceptionHandler {
    public static String TAG;
    private Dialog dialog;
    private Dialog dialog_load_exit;
    private static CrashSenderHandler instance;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    static {
        CrashSenderHandler.TAG = CrashSenderHandler.class.getSimpleName();
        CrashSenderHandler.instance = null;
    }

    public CrashSenderHandler() {
        super();
    }
    
    static Thread.UncaughtExceptionHandler access$5(CrashSenderHandler arg1) {
        return arg1.mDefaultHandler;
    }

    private void dismissExceptionDialog(final Thread arg0, final Throwable ex) {
        new Handler().postDelayed(new Runnable() {

            public void run() {
                CrashSenderHandler.this.dialog.dismiss();
                new TimeCount(2000, 1000, CrashSenderHandler.this.dialog_load_exit).start();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        MCLog.e(CrashSenderHandler.TAG, "killProcess");
                        CrashSenderHandler.this.mDefaultHandler.uncaughtException(arg0, ex);
                    }
                }, 1000);
            }
        }, 3000);
    }

    public static CrashSenderHandler getInstance() {
        if(CrashSenderHandler.instance == null) {
            CrashSenderHandler.instance = new CrashSenderHandler();
        }

        return CrashSenderHandler.instance;
    }

    public void init(Context ctx) {
        this.mContext = ctx;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(((Thread.UncaughtExceptionHandler)this));
    }

    private void showExceptionDialog() {
        this.dialog = MCLoadDialog.createLoadingDialog(this.mContext, this.mContext.getString(R.string.crash_msg_label), R.drawable.dialog_loading, AnimationUtils.loadAnimation(this.mContext, R.anim.dialog_loading_anim));
        this.dialog_load_exit = MCLoadDialog.createLoadingDialog(this.mContext, this.mContext.getString(R.string.crash_msg_success_label), R.drawable.dialog_load_success, null);
        this.dialog.getWindow().setType(2003);
        this.dialog_load_exit.getWindow().setType(2003);
    }

    public void uncaughtException(final Thread thead, final Throwable throwable) {
        if(this.mDefaultHandler == null || throwable == null) {
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
        else {
            StringWriter sWriter = new StringWriter();
            PrintWriter pWriter = new PrintWriter(sWriter);
            throwable.printStackTrace(pWriter);
            Throwable t;
            for(t = throwable.getCause(); t != null; t = t.getCause()) {
                t.printStackTrace(pWriter);
            }

            pWriter.close();
            new Thread() {
                public void run() {
                    int uid = 0;
                    Looper.prepare();
                    CrashSenderHandler.this.showExceptionDialog();
                    try {
                        uid = Integer.parseInt(MCSaveData.getUserInfo(Contants.UID, CrashSenderHandler.this.mContext).toString());
                    }
                    catch(Exception v1) {
                    }

                    MCCrashSender.senderCrashLog(throwable.getStackTrace().toString(), uid, CrashSenderHandler.this.mContext);
                    CrashSenderHandler.this.dismissExceptionDialog(thead, throwable);
                    Looper.loop();
                }
            }.start();
        }
    }

   
}

