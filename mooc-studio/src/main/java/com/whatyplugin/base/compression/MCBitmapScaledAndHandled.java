package com.whatyplugin.base.compression;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class MCBitmapScaledAndHandled {
    public MCBitmapScaledAndHandled() {
        super();
    }

    static int findBestSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        float v11 = 2f;
        double v3 = Math.min((((double)actualWidth)) / (((double)desiredWidth)), (((double)actualHeight)) / (((double)desiredHeight)));
        float v2;
        for(v2 = 1f; (((double)(v2 * v11))) <= v3; v2 *= v11) {
        }

        return ((int)v2);
    }

    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary, int actualSecondary) {
        if(maxPrimary != 0 || maxSecondary != 0) {
            if(maxPrimary == 0) {
                actualPrimary = ((int)((((double)actualPrimary)) * ((((double)maxSecondary)) / (((double)actualSecondary)))));
            }
            else if(maxSecondary == 0) {
                actualPrimary = maxPrimary;
            }
            else {
                double proportion = (((double)actualSecondary)) / (((double)actualPrimary));
                int max = maxPrimary;
                if((((double)max)) * proportion > (((double)maxSecondary))) {
                    max = ((int)((((double)maxSecondary)) / proportion));
                }

                actualPrimary = max;
            }
        }

        return actualPrimary;
    }

    public static Bitmap scaleBitmap(Bitmap bm, int mMaxWidth, int mMaxHeight) {
    	return null;
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        float v10;
        float v12;
        float v13;
        float v11;
        float v19;
        float v15;
        float v5;
        float v22;
        float midWidth;
        Bitmap v16;
        if(bitmap == null) {
            v16 = null;
        }
        else {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if(width <= height) {
                midWidth = ((float)(width / 2));
                v22 = 0f;
                v5 = ((float)width);
                v15 = 0f;
                v19 = ((float)width);
                height = width;
                v11 = 0f;
                v13 = 0f;
                v12 = ((float)width);
                v10 = ((float)width);
            }
            else {
                midWidth = ((float)(height / 2));
                float v7 = ((float)((width - height) / 2));
                v15 = v7;
                v19 = (((float)width)) - v7;
                v22 = 0f;
                v5 = ((float)height);
                width = height;
                v11 = 0f;
                v13 = 0f;
                v12 = ((float)height);
                v10 = ((float)height);
            }

            v16 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas v6 = new Canvas(v16);
            Paint v17 = new Paint();
            Rect v21 = new Rect(((int)v15), ((int)v22), ((int)v19), ((int)v5));
            Rect v9 = new Rect(((int)v11), ((int)v13), ((int)v12), ((int)v10));
            RectF v18 = new RectF(v9);
            v17.setAntiAlias(true);
            v6.drawARGB(0, 255, 255, 255);
            v17.setColor(-1);
            v6.drawRoundRect(v18, midWidth, midWidth, v17);
            //v17.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            v6.drawBitmap(bitmap, v21, v9, v17);
        }

        return v16;
    }
}

