package com.whatyplugin.base.photoview.gestures;



import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;

@TargetApi(value=8) public class MCFroyoGestureDetector extends MCEclairGestureDetector {
    protected final ScaleGestureDetector mDetector;

    public MCFroyoGestureDetector(Context context) {
        super(context);
        this.mDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            public boolean onScale(ScaleGestureDetector detector) {
                boolean flag;
                float scaleFactor = detector.getScaleFactor();
                if((Float.isNaN(scaleFactor)) || (Float.isInfinite(scaleFactor))) {
                    flag = false;
                }
                else {
                    MCFroyoGestureDetector.this.mListener.onScale(scaleFactor, detector.getFocusX(), detector.getFocusY());
                    flag = true;
                }

                return flag;
            }

            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            public void onScaleEnd(ScaleGestureDetector detector) {
            }
        });
    }

    public boolean isScaling() {
        return this.mDetector.isInProgress();
    }

    public boolean onTouchEvent(MotionEvent ev) {
        this.mDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }
}

