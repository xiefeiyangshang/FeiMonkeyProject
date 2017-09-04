package com.whatyplugin.base.asyncimage;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MCRequestManager {
    private static RequestQueue mRequestQueue;

    private MCRequestManager() {
        super();
    }

    public static RequestQueue getRequestQueue() {
        if(MCRequestManager.mRequestQueue != null) {
            return MCRequestManager.mRequestQueue;
        }
        return null;
        //throw new IllegalStateException("Not initialized");
    }

    public static void init(Context context, String path, String dir) {
        MCRequestManager.mRequestQueue = Volley.newRequestQueue(context);
    }
}

