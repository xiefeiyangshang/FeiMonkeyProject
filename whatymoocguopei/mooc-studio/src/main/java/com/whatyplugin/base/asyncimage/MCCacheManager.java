package com.whatyplugin.base.asyncimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.whatyplugin.imooc.logic.manager.MCManager;

public class MCCacheManager {
	public enum CacheType {
		DISK("DISK", 0), MEMORY("MEMORY", 1);

		private CacheType(String arg1, int arg2) {
		}

	}

	private ImageCache mImageCache;
	private ImageLoader mImageLoader;
	private static MCCacheManager mInstance;
	private static int[] cacheType;
	private RequestQueue mQueue;
	
	static int[] initCacheType() {
		int[] cacheType = MCCacheManager.cacheType;
		if (cacheType == null) {
			cacheType = new int[CacheType.values().length];
			try {
				cacheType[CacheType.DISK.ordinal()] = 1;
			} catch (NoSuchFieldError v1) {
			}

			try {
				cacheType[CacheType.MEMORY.ordinal()] = 2;
			} catch (NoSuchFieldError v1) {
			}

			MCCacheManager.cacheType = cacheType;
		}

		return cacheType;
	}

	public MCCacheManager() {
		super();
		initCacheType();
	}

	private String createKey(String url) {
		return String.valueOf(url.hashCode());
	}

	public Bitmap getBitmap(String url) {
		try {
			return this.mImageCache.getBitmap(this.createKey(url));
		} catch (NullPointerException exception) {
			throw new IllegalStateException("Disk Cache Not initialized");
		}
	}

	public void getImage(String url, ImageListener listener) {
		this.mImageLoader.get(url, listener);
	}

	public ImageLoader getImageLoader() {
		if(this.mImageLoader == null){
			MCManager.createImageCache();//重新初始化一次图片缓存
		}
		
		return this.mImageLoader;
	}

	public static MCCacheManager getInstance() {
		if (MCCacheManager.mInstance == null) {
			MCCacheManager.mInstance = new MCCacheManager();
		}

		return MCCacheManager.mInstance;
	}

	
	//原来是内存缓存，现在改成磁盘缓存看看效果。
	public void init(Context context, String uniqueName, int cacheSize, CompressFormat compressFormat, int quality, CacheType type) {
		switch (MCCacheManager.cacheType[type.ordinal()]) {
		case 1: {
			this.mImageCache = new MCDiskLruCache(context, uniqueName, cacheSize, compressFormat, quality);
			break;
		}
		case 2: {
			this.mImageCache = new MCBitmapLruCache(cacheSize);
			break;
		}
		}

		if (this.mImageCache == null) {// 如果磁盘缓存没成功就建立内存缓存
			this.mImageCache = new MCBitmapLruCache(cacheSize);
		}
		
		RequestQueue queue = MCRequestManager.getRequestQueue();
		if (queue == null) {
			MCRequestManager.init(context, "", "");
		}
		this.mImageLoader = new ImageLoader(MCRequestManager.getRequestQueue(), this.mImageCache);

	}

	public void putBitmap(String url, Bitmap bitmap) {
		try {
			this.mImageCache.putBitmap(this.createKey(url), bitmap);
			return;
		} catch (NullPointerException exception) {
			throw new IllegalStateException("Disk Cache Not initialized");
		}
	}
}
