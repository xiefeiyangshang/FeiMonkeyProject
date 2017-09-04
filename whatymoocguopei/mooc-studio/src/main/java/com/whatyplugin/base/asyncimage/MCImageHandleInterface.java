package com.whatyplugin.base.asyncimage;

import android.graphics.Bitmap;

import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;

public interface MCImageHandleInterface {
    Bitmap handleBitmapShape(Bitmap arg1, ImageType arg2);

    Bitmap scaleBitmap(Bitmap arg1, int arg2, int arg3);
}

