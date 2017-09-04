package com.whatyplugin.base.network;


import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;

import javax.net.ssl.SSLException;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

import android.os.SystemClock;

class MCRetryHandler implements HttpRequestRetryHandler {
    private static HashSet exceptionBlacklist;
    private static HashSet exceptionWhitelist;
    private final int maxRetries;
    private final int retrySleepTimeMS;

    static {
        MCRetryHandler.exceptionWhitelist = new HashSet();
        MCRetryHandler.exceptionBlacklist = new HashSet();
        MCRetryHandler.exceptionWhitelist.add(NoHttpResponseException.class);
        MCRetryHandler.exceptionWhitelist.add(UnknownHostException.class);
        MCRetryHandler.exceptionWhitelist.add(SocketException.class);
        MCRetryHandler.exceptionBlacklist.add(InterruptedIOException.class);
        MCRetryHandler.exceptionBlacklist.add(SSLException.class);
    }

    public MCRetryHandler(int maxRetries, int retrySleepTimeMS) {
        super();
        this.maxRetries = maxRetries;
        this.retrySleepTimeMS = retrySleepTimeMS;
    }

    static void addClassToBlacklist(Class arg1) {
        MCRetryHandler.exceptionBlacklist.add(arg1);
    }

    static void addClassToWhitelist(Class arg1) {
        MCRetryHandler.exceptionWhitelist.add(arg1);
    }

    protected boolean isInList(HashSet arg4, Throwable error) {
        Iterator iterator = arg4.iterator();
        do {
            if(iterator.hasNext()) {
                if(!((Class) iterator.next()).isInstance(error)) {
                    continue;
                }

                break;
            }
            else {
                return false;
            }
        }
        while(true);

        return true;
    }

    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        boolean ret = false;
        boolean temp = true;
        Object obj = context.getAttribute("http.request_sent");
        int v3 = obj == null || !((Boolean)obj).booleanValue() ? 0 : 1;
        if(executionCount > this.maxRetries) {
            temp = false;
        }
        else if(this.isInList(MCRetryHandler.exceptionWhitelist, ((Throwable)exception))) {
            temp = true;
        }
        else if(this.isInList(MCRetryHandler.exceptionBlacklist, ((Throwable)exception))) {
            temp = false;
        }
        else if(v3 == 0) {
            temp = true;
        }

        if(!temp || context.getAttribute("http.request") != null) {
            if(temp) {
                SystemClock.sleep(((long)this.retrySleepTimeMS));
            }
            else {
                exception.printStackTrace();
            }

            ret = temp;
        }

        return ret;
    }
}

