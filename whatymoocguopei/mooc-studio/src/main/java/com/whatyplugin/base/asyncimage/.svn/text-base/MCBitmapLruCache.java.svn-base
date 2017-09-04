package com.whatyplugin.base.asyncimage;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class MCBitmapLruCache extends LruCache implements ImageCache {
    private final String TAG;

    public MCBitmapLruCache(int maxSize) {
        super(maxSize);
        this.TAG = this.getClass().getSimpleName();
    }

    protected void entryRemoved(boolean arg1, Object arg2, Object arg3, Object arg4) {
        this.entryRemoved(arg1, ((String)arg2), ((Bitmap)arg3), ((Bitmap)arg4));
    }

    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
    }

    public Bitmap getBitmap(String url) {
        return (Bitmap) this.get(url);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        this.put(url, bitmap);
    }

    protected int sizeOf(Object arg2, Object arg3) {
        return this.sizeOf(((String)arg2), ((Bitmap)arg3));
    }

    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }
}

