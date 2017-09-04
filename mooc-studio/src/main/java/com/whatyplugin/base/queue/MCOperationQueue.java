package com.whatyplugin.base.queue;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MCOperationQueue {
    private static int MAX_QUEUE;
    private static MCOperationQueue manager;
    private ThreadPoolExecutor pool;

    static {
        MCOperationQueue.MAX_QUEUE = 100;
    }

    private MCOperationQueue() {
        super();
        int v1 = Runtime.getRuntime().availableProcessors();
        if(v1 <= 0) {
            v1 = 1;
        }

        try {
            this.pool = new ThreadPoolExecutor(v1, v1 + 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue(MCOperationQueue.MAX_QUEUE), new ThreadPoolExecutor.DiscardOldestPolicy());
        }
        catch(Exception v8) {
            v8.printStackTrace();
        }
    }

    public void addTask(MCOperationNode operation) {
        this.pool.execute(((Runnable)operation));
    }

    public void clearPollTask() {
        ThreadPoolExecutor v1 = null;
        if(this.pool != null) {
            this.pool.shutdown();
            this.pool.shutdownNow();
            this.pool = v1;
           // MCOperationQueue.manager = ((MCOperationQueue)v1);
        }
    }

    public static MCOperationQueue getInstance() {
        if(MCOperationQueue.manager == null) {
            MCOperationQueue.manager = new MCOperationQueue();
        }

        return MCOperationQueue.manager;
    }
}

