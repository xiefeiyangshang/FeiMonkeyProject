package com.whatyplugin.imooc.logic.manager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.util.SparseIntArray;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.base.asyncimage.MCCacheManager.CacheType;
import com.whatyplugin.base.asyncimage.MCRequestManager;
import com.whatyplugin.base.download.MCDownloadQueue;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.imooc.logic.config.MCNetworkConfig;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.FileUtils;
import com.whatyplugin.imooc.ui.download.DownloadNetworkSevice;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;
import com.whatyplugin.uikit.resolution.MCResolution;

public class MCManager {
    private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT;
    private static int DISK_IMAGECACHE_QUALITY;
    private static int DISK_IMAGECACHE_SIZE;
    private static String TAG;
    public static Map expressionMap;
    public static boolean isNeedUpdateUnReadNum;
    private static Context mContext;
    
    public static Map<String, Integer> unreadMsgNumMap;
    public static SparseIntArray mUnReadMsgNum;
    private static MCManager manager;
    private String uid;

    static {
        MCManager.TAG = MCManager.class.getSimpleName();
        MCManager.DISK_IMAGECACHE_SIZE = 10485760;
        MCManager.DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
        MCManager.DISK_IMAGECACHE_QUALITY = 100;
        unreadMsgNumMap = new HashMap<String, Integer>();
        MCManager.mUnReadMsgNum = new SparseIntArray();
        MCManager.isNeedUpdateUnReadNum = true;
        MCManager.expressionMap = new HashMap();
    }

    private MCManager(Context context) {
        super();
        this.uid = Contants.DEFAULT_UID;
        this.mContext = context;
       // ShareSDK.initSDK(this.mContext, "9021e88b1df", true);
        MCManager.DISK_IMAGECACHE_SIZE = 1048576 * ((ActivityManager)context.getSystemService("activity")).getMemoryClass() / 8;
        
        if(MCManager.mUnReadMsgNum == null) {
            MCManager.mUnReadMsgNum = new SparseIntArray();
        }
        
        if(unreadMsgNumMap == null)
        	unreadMsgNumMap = new HashMap<String, Integer>();

        if(MCManager.expressionMap == null) {
            MCManager.expressionMap = new HashMap();
        }

        this.init();
    }

    public static void createImageCache() {
        MCCacheManager.getInstance().init(MoocApplication.getInstance(), 
        		"imageCache", 
        		MCManager.DISK_IMAGECACHE_SIZE, 
        		MCManager.DISK_IMAGECACHE_COMPRESS_FORMAT, 
        		MCManager.DISK_IMAGECACHE_QUALITY, 
        		CacheType.DISK);
    }

    public static void destroy() {
        if(MCManager.manager != null) {
            MCManager.manager.destroyRunning();
        }
    }

    private void destroyRunning() {
        MCDownloadQueue.getInstance().closeQueue();
        MCManager.mUnReadMsgNum.clear();
        MCManager.unreadMsgNumMap.clear();
        MCManager.expressionMap.clear();
        //this.mContext.stopService(new Intent(this.mContext, NetworkListenerSevice.class));
        this.mContext.stopService(new Intent(this.mContext, DownloadNetworkSevice.class));
        //this.mContext.stopService(new Intent(this.mContext, SdcardStatusListenerSevice.class));

        MCManager.manager = null;
        MCManager.mUnReadMsgNum = null;
        MCManager.unreadMsgNumMap = null;
        MCManager.expressionMap = null;
    }

    public static MCManager getInstance() {
        return MCManager.manager;
    }

    private void init() {
    	this.initVideoPath();
        MCNetwork.setMCNetworkConfig(new MCNetworkConfig());
        MCRequestManager.init(this.mContext, Contants.BASE_PATH, "image");
        //初始化图片缓存
        createImageCache();
        
        MCResolution.getInstance(this.mContext).setDesignResolutionSize(1080, 1920);
      
      //  this.mContext.startService(new Intent(this.mContext, SdcardStatusListenerSevice.class));
        
        MCDownloadQueue.getInstance().initQueue();
        
        this.mContext.startService(new Intent(this.mContext, DownloadNetworkSevice.class));

        //初始化路径
        this.mkdir();
    }

  

    private void initVideoPath() {
        if(FileUtils.isTwoSdcard(this.mContext)) {
            Contants.BASE_VIDEO_PATH = String.valueOf(MCSaveData.getDownloadSDcardPath(this.mContext)) + Contants.BASE_FOLDER_PATH;
            Contants.VIDEO_PATH = String.valueOf(Contants.BASE_VIDEO_PATH) + "/video";
        }
    }

    public static void initialize(Context context) {
        MCLog.e(MCManager.TAG, "manager:" + MCManager.manager);
        if(MCManager.manager == null) {
            MCManager.manager = new MCManager(context);
        }
    }

    private void loadExpression() {
        String expression;
        String[] arr = this.mContext.getResources().getStringArray(R.array.expression_list);
        int length = arr.length;
        for(int i = 0; i < length; ++i) {
            String[] splitArr = arr[i].split(",");
            int number = Integer.valueOf(splitArr[1].substring(0, splitArr[1].lastIndexOf("."))).intValue();
            if(number < 10) {
                expression = "f00" + number;
            }
            else if(number < 100) {
                expression = "f0" + number;
            }
            else {
                expression = "f" + number;
            }

            MCManager.expressionMap.put(splitArr[0], expression);
        }
    }

    private void mkdir() {
        File file = new File(Contants.IMAGE_PATH);
        if(!file.exists()) {
            file.mkdirs();
        }

        File fileVideo = new File(Contants.VIDEO_PATH);
        MCLog.e(TAG, "video path:" + Contants.VIDEO_PATH + " save:" + MCSaveData.getDownloadSDcardPath(this.mContext) + "  exists:" + fileVideo.exists());
        if(!fileVideo.exists()) {
            fileVideo.mkdirs();
        }

        File fileApk = new File(Contants.APK_PATH);
        if(!fileApk.exists()) {
            fileApk.mkdirs();
        }
    }

}

