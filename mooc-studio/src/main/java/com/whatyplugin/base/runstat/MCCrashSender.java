package com.whatyplugin.base.runstat;



import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.base.network.MCNetworkDefine.MCNetworkStatus;
import com.whatyplugin.base.queue.MCOperationQueue;
import com.whatyplugin.base.queue.MCRequestOperation;

public class MCCrashSender {
    private static String TAG;

    static {
        MCCrashSender.TAG = MCCrashSender.class.getSimpleName();
    }

    public MCCrashSender() {
        super();
    }

    public static void senderCrashLog(String log, int uid, Context context) {
        PackageInfo v4 = null;
        PackageManager v5 = context.getPackageManager();
        try {
            v4 = v5.getPackageInfo(context.getPackageName(), 0);
        }
        catch(PackageManager.NameNotFoundException exception) {
            exception.printStackTrace();
        }

        MCNetworkStatus v3 = MCNetwork.currentNetwork(context);
        MCBaseRequest v6 = new MCBaseRequest();
        v6.requestUrl = "collecterror";
        HashMap v2 = new HashMap();
        ((Map)v2).put("client_version", new StringBuilder(String.valueOf(v4.versionName)).toString());
        ((Map)v2).put("os", "Android");
        ((Map)v2).put("os_version", Build.VERSION.RELEASE);
        ((Map)v2).put("brand", String.valueOf(Build.BRAND) + " " + Build.MODEL);
        ((Map)v2).put("net_type", new StringBuilder(String.valueOf(v3.value())).toString());
        ((Map)v2).put("crash_content", log);
        ((Map)v2).put("d_code", MCRunStart.getDeviceId(context));
        ((Map)v2).put("uid", new StringBuilder(String.valueOf(uid)).toString());
        v6.requestParams = ((Map)v2);
        v6.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                MCLog.e(MCCrashSender.TAG, "collecterror response:" + responeData);
            }
        };
        MCOperationQueue.getInstance().addTask(MCRequestOperation.operationWithData(v6, context));
        MCLog.e(MCCrashSender.TAG, "senderCrashLog");
    }
}

