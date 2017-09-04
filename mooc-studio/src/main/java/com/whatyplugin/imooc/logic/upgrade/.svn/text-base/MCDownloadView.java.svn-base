package com.whatyplugin.imooc.logic.upgrade;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.SeekBar;

import com.whatyplugin.base.define.MCBaseDefine.MCUpgradeType;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.upgrade.MCDownloadFile.ProgressChangeListener;
import com.whatyplugin.imooc.logic.utils.OpenFile;
import com.whatyplugin.imooc.ui.showmooc.ShowMoocCommon;
import com.whatyplugin.imooc.ui.view.UpgradeDailogView;
import com.whatyplugin.imooc.ui.view.UpgradeDailogView.INetworkListener;
import com.whatyplugin.uikit.dialog.MCDialog;
import com.whatyplugin.uikit.toast.MCToast;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.com.whatyplugin.mooc.R;

public class MCDownloadView implements ProgressChangeListener,INetworkListener {
    private static final String CANCEL_UPDATE_ACTION = "cancel_update";
    private static final int NOTFICATION_ID = 1;
    private Context context;
    private Dialog dialog;
    private UpgradeDailogView dialogView;
    private Handler handler;
    private boolean isRegister;
    private int lastCount;
    private Handler mHandler;
    private NotificationManager manager;
    private Notification notification;
    private BroadcastReceiver receiver;
    private SeekBar seekBar;
    private MCUpgradeType type;
    private int downType;
    private MCDownloadManager downloadManager;
    private MCSectionModel section;
    protected MCDownloadView(Context context,int downType,MCDownloadManager downloadManager) {
        super();
        this.downType = downType;
        this.lastCount = 0;
        this.isRegister = false;
        this.downloadManager = downloadManager;
        this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.type = MCUpgradeType.MC_UPGRADE_TYPE_NO_UPGRADE;
        this.receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(!TextUtils.isEmpty(((CharSequence)action)) && (action.equals("cancel_update"))) {
                    if(MCDownloadView.this.dialog != null && !MCDownloadView.this.dialog.isShowing()) {
//                        MCDownloadView.this.dialog.show();
                    }

                    MCDownloadView.this.cancelDownload();
                    MCDownloadView.this.cancelDialog();

//                  MCDownloadView.this.cancelNotification();
                }
            }
        };
        this.handler = new Handler() {
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case 2: {
                        MCDownloadView.this.cancelNotification();
                        MCDownloadView.this.cancelDialog();
                        MCToast.show(MCDownloadView.this.context, "取消更新");
                        break;
                    }
                    case 3: {
                        MCDownloadView.this.cancelNotification();
                        MCDownloadView.this.cancelDialog();
                        if(MCDownloadView.this.type == MCUpgradeType.MC_UPGRADE_TYPE_MUST_UPGRADE && MCDownloadView.this.mHandler != null) {
                            MCDownloadView.this.mHandler.sendEmptyMessage(0);
                        }

                        MCToast.show(MCDownloadView.this.context, MCDownloadView.this.context.getString(R.string.update_failed));
                        break;
                    }

                    case 4: {
                        MCDownloadView.this.cancelNotification();
                        MCDownloadView.this.cancelDialog();
                        MCToast.show(MCDownloadView.this.context, "下载资源不存在");
                        break;
                    }


                }
            }
        };
        this.context = context;
        this.dialogView = new UpgradeDailogView(context, null, null);
        this.dialogView.setNetworkListener(((INetworkListener)this));
        this.seekBar = (SeekBar)this.dialogView.findViewById(R.id.upgrade_progress);
        this.seekBar.setProgress(0);
        if(this.downType == 1){
            this.createNotification();
        }else{
            dialogView.setTitle("提示");
            dialogView.setContent("正在下载文件");
        }
    }

    public void backgrounder() {
        this.dismissDialog();
    }

    public void cancel() {
        cancelDownload();
        MCDownloadView.this.cancelDialog();
//        MCDownloadView.this.cancelNotification();
    }

    private void cancelDialog() {
        if(this.seekBar != null) {
            this.seekBar.setProgress(0);
        }
        this.dismissDialog();
    }

    private void cancelDownload() {
     /*   if(this.type == MCUpgradeType.MC_UPGRADE_TYPE_MUST_UPGRADE && this.mHandler != null) {
            this.mHandler.sendEmptyMessage(0);
        }*/

        downloadManager.cancelDownLoad();
        this.dismissDialog();
    }

    private void cancelNotification() {
        if(this.manager != null) {
            this.manager.cancel(1);
            this.lastCount = 0;
            this.unRegister();
            this.notification = null;
        }
    }
    private void createNotification() {
        this.notification = new Notification();
        this.notification.icon = R.drawable.icon;
        this.notification.tickerText = this.context.getResources().getString(R.string.app_name);
        RemoteViews  remoteView =new RemoteViews(this.context.getPackageName(), R.layout.app_download_notification_view);
        this.notification.contentView = remoteView;
        Intent intent = new Intent();
        intent.setAction("cancel_update");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, intent, 0);
        this.notification.contentIntent = pendingIntent;
        this.notification.contentView.setOnClickPendingIntent(R.id.notif_layout, null);
        this.notification.contentView.setOnClickPendingIntent(R.id.cancel_update, pendingIntent);

    }
    protected void dismissDialog() {
        if(this.dialog != null && (this.dialog.isShowing())) {
            this.dialog.dismiss();
        }
    }

    public Handler getHandler() {
        return this.handler;
    }

    public void initView(int countSize) {
        if(this.seekBar != null) {
            this.seekBar.setMax(countSize);
        }
    }

    public void onProgressChange(int countSize, int currentCount,String filePath) {
        if(countSize != currentCount) {
            this.updateNotificationProgressBar(countSize, currentCount);
            return;
        }
        this.cancelDialog();
        if(downType == 1)
            this.cancelNotification();
        try {
            if(downType == 1){
                //downloadManager.installApk(String.valueOf(Contants.APK_PATH) + Contants.APK_NAME);
                // MCToast.show(this.context, this.context.getString(R.string.update_download_over));
            }else{
                //OpenFile.openFile(new File(filePath), this.context);
            }
        }
        catch(Exception v1) {
        }
    }

    private String percent(double d1, double d2) {
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMinimumFractionDigits(2);
        return format.format(d1 / d2);
    }

    protected void showDialog(MCUpgradeType type, Handler handler) {
        int styleNum = R.style.NetworkDialogStyle;  // R.style.NetworkDialogStyle
        this.mHandler = handler;
        this.type = type;
        if(this.dialog == null) {
            if(type == MCUpgradeType.MC_UPGRADE_TYPE_NEED_UPGRADE) {
                this.dialog = MCDialog.show(this.dialogView, styleNum, this.context);
            }
            else if(type == MCUpgradeType.MC_UPGRADE_TYPE_MUST_UPGRADE) {
                this.dialog = MCDialog.show(this.dialogView, styleNum, this.context, false);
            }
        }
        else if(!this.dialog.isShowing()) {
            this.dialog.show();
        }
    }

    protected void showNotification() {
        if(this.notification == null) {
            this.createNotification();
        }

        if(this.manager != null) {
            this.manager.notify(1, this.notification);
        }

        if(!this.isRegister) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("cancel_update");
            this.context.registerReceiver(this.receiver, intentFilter);
            this.isRegister = true;
        }
    }

    private void unRegister() {
        if((this.isRegister) && this.context != null) {
            this.context.unregisterReceiver(this.receiver);
            this.isRegister = false;
        }
    }

    private void updateNotificationProgressBar(int countSize, int currentCount) {
        int viewNum = R.id.content_view_text1;  // R.id.content_view_text1
        if((currentCount - this.lastCount) * 100 / countSize >= 1) {
            if(this.seekBar != null) {
                this.seekBar.setProgress(currentCount);
            }
            if(downType == 1){
                String str = this.percent(((double)currentCount), ((double)countSize));
                if(str.contains("%")) {
                    this.notification.contentView.setTextViewText(viewNum, ((CharSequence)str));
                }
                else {
                    this.notification.contentView.setTextViewText(viewNum, "0%");
                }

                this.notification.contentView.setProgressBar(R.id.content_view_progress, countSize, currentCount, false);
                this.notification.contentView.setTextViewText(R.id.notif_time, new SimpleDateFormat("HH:mm", Locale.CHINA).format(new Date(System.currentTimeMillis())));
                this.manager.notify(1, this.notification);
                this.lastCount = currentCount;
            }
        }
    }

    private Handler downHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    MCToast.show(context, context.getString(R.string.update_download_over));
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    public void onProgressEnd(String filePath) {
        this.cancelDialog();
        if(downType == 1)
            this.cancelNotification();
        try {
            downHandler.sendEmptyMessage(0);
//        	MCToast.show(this.context, this.context.getString(R.string.update_download_over));
            if(downType == 1){
                downloadManager.installApk(filePath);
            }else{
                OpenFile.openFile(new File(filePath),context.getApplicationContext());
                saveLearnRecord(filePath);
            }
        }
        catch(Exception e) {
            if(downType == 1){
                downloadManager.installApk(filePath);
            }else{
                OpenFile.openFile(new File(filePath),context.getApplicationContext());
                saveLearnRecord(filePath);
            }
        }
    }

    /**
     * 下载完成保存学习记录
     * @param filePath
     */
    private void saveLearnRecord(String filePath) {
        if (section != null) {
            String downloadUrl = this.section.getSharedUrl();
//
//			String fileNameRemot = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1, downloadUrl.length());
//			String fileNameLocal = downloadUrl.substring(filePath.lastIndexOf("/") + 1, filePath.length());
//
//			if(fileNameRemot.equals(fileNameLocal)){
            ShowMoocCommon.saveClickRecord(this.section, context);
            //}
        }
    }

    public void setSection(MCSectionModel section) {
        this.section = section;
    }

}

