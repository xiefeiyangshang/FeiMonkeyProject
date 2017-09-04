package com.whatyplugin.base.photoview.gestures;



import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;

import com.whatyplugin.base.photoview.MCCompat;

@TargetApi(value=5) public class MCEclairGestureDetector extends MCCupcakeGestureDetector {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId;
    private int mActivePointerIndex;

    public MCEclairGestureDetector(Context context) {
        super(context);
        this.mActivePointerId = -1;
        this.mActivePointerIndex = 0;
    }

    float getActiveX(MotionEvent ev) {
        float x;
        try {
            x = ev.getX(this.mActivePointerIndex);
        }
        catch(Exception e) {
            x = ev.getX();
        }

        return x;
    }

    float getActiveY(MotionEvent ev) {
        float y;
        try {
            y = ev.getY(this.mActivePointerIndex);
        }
        catch(Exception e) {
            y = ev.getY();
        }

        return y;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int v6 = -1;
        int v4 = 0;
        switch(ev.getAction() & 255) {
            case 0: {
                this.mActivePointerId = ev.getPointerId(0);
                break;
            }
            case 1: 
            case 3: {
                this.mActivePointerId = v6;
                break;
            }
            case 6: {
                int v3 = MCCompat.getPointerIndex(ev.getAction());
                if(ev.getPointerId(v3) != this.mActivePointerId) {
                	 if(this.mActivePointerId != v6) {
                         v4 = this.mActivePointerId;
                     }

                     this.mActivePointerIndex = ev.findPointerIndex(v4);
                     return super.onTouchEvent(ev);
                }

                int v1 = v3 == 0 ? 1 : 0;
                this.mActivePointerId = ev.getPointerId(v1);
                this.mLastTouchX = ev.getX(v1);
                this.mLastTouchY = ev.getY(v1);
                break;
            }
        }

        if(this.mActivePointerId != v6) {
            v4 = this.mActivePointerId;
        }

        this.mActivePointerIndex = ev.findPointerIndex(v4);
        return super.onTouchEvent(ev);
    }
}

