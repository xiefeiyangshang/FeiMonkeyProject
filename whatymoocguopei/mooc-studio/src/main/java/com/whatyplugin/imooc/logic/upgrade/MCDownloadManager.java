package com.whatyplugin.imooc.logic.upgrade;



import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import com.whatyplugin.base.define.MCBaseDefine.MCUpgradeType;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.upgrade.MCDownloadFile.ProgressChangeListener;

public class MCDownloadManager {
    private static String UNINSTALL_PACKAGE_PREFIX;
    private Context context;
    private MCDownloadFile downloadFile;
    private MCDownloadView downloadView;
    private Handler handler;
    private static MCDownloadManager instance;
    private MCUpgradeType type;
    private int downType;	//0 普通文件，1 apk下载
    static {
        MCDownloadManager.UNINSTALL_PACKAGE_PREFIX = "package:";
    }

    //用原来单例的存在局限性，不能多任务同时下载呢。
    public MCDownloadManager(Context context,int downType) {
        super();
        this.type = MCUpgradeType.MC_UPGRADE_TYPE_NO_UPGRADE;
        this.context = context;
        this.downType = downType;
        this.initData();
    }

    public void cancelDownLoad() {
        if(this.downloadFile != null) {
            this.downloadFile.cancel();
        }
    }

    public MCDownloadView getDownloadView() {
        return this.downloadView;
    }
    private void initData() {
        this.downloadView = new MCDownloadView(this.context, downType, this);
    }

    public void installApk(String apkFilePath) {
        if(!apkFilePath.endsWith(".apk")) {
            throw new IllegalArgumentException("Invalidate File ...");
        }

        try {
            File file = new File(apkFilePath);
            if(!file.exists()) {
                throw new Exception("The file not found");
            }

            Intent intent = new Intent();
            intent.addFlags(268435456);
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            this.context.startActivity(intent);
            return;
        }
        catch(Exception e) {
            return;
        }
    }

    public boolean isDownloading() {
    	return this.downloadFile != null ? this.downloadFile.isStart() : false;
    }

    public void setDownloadUrl(String downloadUrl) {
    	if(downType == 1){
	        this.downloadFile = new MCDownloadFile(downloadUrl, Contants.APK_NAME, this.downloadView.getHandler());
	        this.downloadFile.setProgressChangeListener((ProgressChangeListener) this.downloadView);
    	}else{
    		String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1, downloadUrl.length());
    		this.downloadFile = new MCDownloadFile(downloadUrl, fileName, this.downloadView.getHandler());
	        this.downloadFile.setProgressChangeListener((ProgressChangeListener) this.downloadView);
    	}
    }

    /**
     * 传入节点信息，保存学习记录可能会用到
     * @param downloadUrl
     * @param section
     */
    public void setDownloadUrl(String downloadUrl, MCSectionModel section) {
    	this.downloadView.setSection(section);
    	setDownloadUrl(downloadUrl);
    }
    
    public void showDialog() {
        if(this.downloadView != null) {
            this.downloadView.showDialog(this.type, this.handler);
        }
    }

    public void startDownLoad(MCUpgradeType type, Handler handler) {
        if(this.downloadFile != null) {
            if(this.downloadView != null) {
                this.downloadView.showDialog(type, handler);
                if(downType == 1)
                	this.downloadView.showNotification();
                else{
                	
                }
            }

            this.downloadFile.start();
        }

        this.type = type;
        this.handler = handler;
    }

    public void unInstallApk(String packageName) {
        this.context.startActivity(new Intent("android.intent.action.DELETE", Uri.parse(String.valueOf(MCDownloadManager.UNINSTALL_PACKAGE_PREFIX) + packageName)));
    }
}

