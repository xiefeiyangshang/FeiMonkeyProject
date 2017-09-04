package com.whatyplugin.base.download;

import com.whatyplugin.base.define.MCBaseDefine.MCDownloadNodeType;
import com.whatyplugin.base.log.MCLog;



public class MCDownloadTask implements Runnable {
    private static String TAG;
    private boolean isCanceled;
    private MCDownloadListener listener;
    private MCDownloadNode node;
    private MCDownloadNodeType taskType;
    static {
        MCDownloadTask.TAG = MCDownloadTask.class.getSimpleName();
    }

    public MCDownloadTask(MCDownloadNode node) {
        this(node, null);
        this.taskType = node.nodeType;
    }

    public MCDownloadTask(MCDownloadNode node, MCDownloadListener listener) {
        super();
        this.isCanceled = false;
        this.node = node;
        this.listener = listener;
    }

    public void cancel(boolean mayInterruptIfRunning) {
        this.isCanceled = true;
    }

    public boolean equals(Object o) {
        boolean v1;
        if(!(o instanceof MCDownloadTask)) {
            v1 = false;
        }
        else if(((MCDownloadTask)o).node.equals(this.node)) {
            v1 = true;
        }
        else {
            v1 = super.equals(o);
        }

        return v1;
    }

    public void execute() {
    	this.isCanceled = false;//是否取消设置为false
    	Thread thread=new Thread(this);
    	thread.start();
    }

    public MCDownloadListener getListener() {
        return this.listener;
    }

    public MCDownloadNode getNode() {
        return this.node;
    }

    public boolean isCancelled() {
        return this.isCanceled;
    }

    public void onTaskProgressUpdate(Integer value) {
        if(this.listener != null) {
            this.listener.updateProcess(this.node);
        }
    }

    public void run() {
        if(this.listener != null) {
            this.listener.preDownload(this.node);
        }

        String error = "";
        try {
        	//三分屏类型的下载单独处理
        	if(this.node.nodeType == MCDownloadNodeType.MC_SFP_TYPE){
        		if(this.node.childNodeList!=null&&this.node.childNodeList.size()>0){
        			for(MCDownloadNode inner : this.node.childNodeList){
        				if(!inner.isDownloadOver()){
        					inner.download(this);//多个文件的同时下载
        					MCLog.d(TAG, inner.getSectionName() + "["+inner.getFileSize()+"]" +"下载完成！");
        				}
        				if(this.isCancelled()){
        					MCLog.d(TAG, inner.getSectionName() + "下载被取消！");
        					break;
        				}
        			}
        		}
        	}else{
        		this.node.download(this);
        	}
        } catch(Exception e) {
            e.printStackTrace();
            error = e.getMessage();
            if(this.listener!=null){
            	this.listener.errorDownload(this.node, error);
            }
        }
        
        if(this.listener != null && this.node.isDownloadOver()) {
            this.listener.finishDownload(this.node);
        }
        
        if(this.node.isDownloadOver()){
        	MCDownloadQueue.getInstance().completeTask(this);
        }
    }
    
    public void setListener(MCDownloadListener listener) {
        this.listener = listener;
    }

    public void setNode(MCDownloadNode node) {
        this.node = node;
    }

	public MCDownloadNodeType getTaskType() {
		return taskType;
	}

	public void setTaskType(MCDownloadNodeType taskType) {
		this.taskType = taskType;
	}
    
    
}

