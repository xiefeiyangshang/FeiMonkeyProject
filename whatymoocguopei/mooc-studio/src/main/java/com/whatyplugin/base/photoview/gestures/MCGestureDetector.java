package com.whatyplugin.base.photoview.gestures;



import android.view.MotionEvent;

public interface MCGestureDetector {
    boolean isScaling();

    boolean onTouchEvent(MotionEvent arg1);

    void setOnGestureListener(MCOnGestureListener arg1);
}

