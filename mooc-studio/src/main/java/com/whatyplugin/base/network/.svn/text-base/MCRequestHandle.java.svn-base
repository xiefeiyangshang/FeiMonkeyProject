package com.whatyplugin.base.network;


import java.lang.ref.WeakReference;

import android.os.Looper;

public class MCRequestHandle {
    private final WeakReference request;

    public MCRequestHandle(MCAsyncHttpRequest request) {
        super();
        this.request = new WeakReference(request);
    }

    public boolean cancel(final boolean mayInterruptIfRunning) {
    	MCAsyncHttpRequest mcAsyncHttpRequest = (MCAsyncHttpRequest) this.request.get();
        if(mcAsyncHttpRequest != null) {
            if(Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    public void run() {
                        cancel(mayInterruptIfRunning);
                    }
                }).start();
            }
            else {
                (mcAsyncHttpRequest).cancel(mayInterruptIfRunning);
            }
        }

        return false;
    }

    public boolean isCancelled() {
    	MCAsyncHttpRequest mcAsyncHttpRequest = (MCAsyncHttpRequest) this.request.get();
        boolean cancelled = mcAsyncHttpRequest == null || ((mcAsyncHttpRequest).isCancelled()) ? true : false;
        return cancelled;
    }

    public boolean isFinished() {
    	MCAsyncHttpRequest mcAsyncHttpRequest = (MCAsyncHttpRequest) this.request.get();
        boolean done = mcAsyncHttpRequest == null || ((mcAsyncHttpRequest).isDone()) ? true : false;
        return done;
    }

    public boolean shouldBeGarbageCollected() {
        boolean finished = (this.isCancelled()) || (this.isFinished()) ? true : false;
        if(finished) {
            this.request.clear();
        }

        return finished;
    }
}

