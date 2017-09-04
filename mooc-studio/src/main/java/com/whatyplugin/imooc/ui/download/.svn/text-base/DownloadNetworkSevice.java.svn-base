package com.whatyplugin.imooc.ui.download;


import java.util.LinkedList;
import java.util.Queue;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.download.MCDownloadTask;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkDefine.MCNetworkStatus;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.uikit.toast.MCToast;

public class DownloadNetworkSevice extends Service {
    private static String TAG;
    private MCDownloadTask mCurrentDownloadingTask;
    private Queue mCurrentWaitingTasks;
    private BroadcastReceiver receiver;

    static {
        DownloadNetworkSevice.TAG = DownloadNetworkSevice.class.getSimpleName();
    }

    public DownloadNetworkSevice() {
        super();
        this.mCurrentWaitingTasks = new LinkedList();
        this.receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent arg1) {
                arg1.getAction();
                MCNetworkStatus v1 = MCNetwork.currentNetwork(context);
                if(v1 == MCNetworkStatus.MC_NETWORK_STATUS_WIFI) {
//                    while(true) {
//                    	MCDownloadTask v3 = (MCDownloadTask) MCDownloadQueue.getInstance().getFailedTaskQueue().poll();
//                        if(v3 == null) {
//                            break;
//                        }
//
//                        MCDownloadQueue.getInstance().addTask(v3);
//                        MCManager.mDownloadTasks.put(((MCDownloadVideoNode)v3.getNode()).getSectionId(), v3);
//                    }
//
//                    MCDownloadQueue.getInstance().clearFailedQueue();
                    context.sendBroadcast(new Intent(Contants.RELOAD_CHANGED_ACTION));
                    MCToast.show(context, context.getResources().getString(R.string.download_wifi_label));
                }
                else if(v1 == MCNetworkStatus.MC_NETWORK_STATUS_WWAN) {
                    MCToast.show(context, context.getResources().getString(R.string.download_walan_label));
                }
                else {
                    MCToast.show(context, context.getResources().getString(R.string.download_nonetwork_label));
                }
            }
        };
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Contants.NETWORK_CHANGED_ACTION);
        this.registerReceiver(this.receiver, filter);
        super.onCreate();
    }

    public void onDestroy() {
        this.unregisterReceiver(this.receiver);
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}

