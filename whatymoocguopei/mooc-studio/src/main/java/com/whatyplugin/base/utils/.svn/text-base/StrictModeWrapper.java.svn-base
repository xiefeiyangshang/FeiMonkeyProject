package com.whatyplugin.base.utils;


import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

public class StrictModeWrapper {
	
	private static final String TAG = StrictModeWrapper.class.getSimpleName();
	
    public StrictModeWrapper() {
        super();
    }

    public static void init(Context context) {
        int flag = context.getApplicationInfo().flags;
        int vesion = Build.VERSION.SDK_INT;
        Log.i(TAG, "currentapiVersion" + vesion);
        if(vesion >= 10 && vesion < 19 && (flag & 2) != 0) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
        }
    }
}

