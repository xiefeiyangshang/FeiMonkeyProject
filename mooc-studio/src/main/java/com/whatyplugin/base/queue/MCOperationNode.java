package com.whatyplugin.base.queue;



public abstract class MCOperationNode implements Runnable {
    public MCOperationNode() {
        super();
    }

    public void run() {
        this.start();
    }

    public abstract void start();
}

