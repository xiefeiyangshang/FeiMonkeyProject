package com.whatyplugin.base.photoview;



import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

public class MCCompat {
    private static final int SIXTY_FPS_INTERVAL = 16;

    public MCCompat() {
        super();
    }

    public static int getPointerIndex(int action) {
        int pointerIndex = Build.VERSION.SDK_INT >= 11 ? MCCompat.getPointerIndexHoneyComb(action) : MCCompat.getPointerIndexEclair(action);
        return pointerIndex;
    }

    @TargetApi(value=5) private static int getPointerIndexEclair(int action) {
        return (65280 & action) >> 8;
    }

    @TargetApi(value=11) private static int getPointerIndexHoneyComb(int action) {
        return (65280 & action) >> 8;
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        if(Build.VERSION.SDK_INT >= 16) {
            MCCompat.postOnAnimationJellyBean(view, runnable);
        }
        else {
            view.postDelayed(runnable, 16);
        }
    }

    @TargetApi(value=16) private static void postOnAnimationJellyBean(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }
}

