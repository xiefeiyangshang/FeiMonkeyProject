package com.whatyplugin.base.photoview.gestures;



import android.content.Context;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class MCCupcakeGestureDetector implements MCGestureDetector {
    private static final String LOG_TAG = "CupcakeGestureDetector";
    private boolean mIsDragging;
    float mLastTouchX;
    float mLastTouchY;
    protected MCOnGestureListener mListener;
    final float mMinimumVelocity;
    final float mTouchSlop;
    private VelocityTracker mVelocityTracker;

    public MCCupcakeGestureDetector(Context context) {
        super();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mMinimumVelocity = ((float)viewConfiguration.getScaledMinimumFlingVelocity());
        this.mTouchSlop = ((float)viewConfiguration.getScaledTouchSlop());
    }

    float getActiveX(MotionEvent ev) {
        return ev.getX();
    }

    float getActiveY(MotionEvent ev) {
        return ev.getY();
    }

    public boolean isScaling() {
        return false;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        VelocityTracker v12 = null;
        boolean v6 = false;
        switch(ev.getAction()) {
            case 0: {
                this.mVelocityTracker = VelocityTracker.obtain();
                if(this.mVelocityTracker != null) {
                    this.mVelocityTracker.addMovement(ev);
                }
                else {
                    Log.i("CupcakeGestureDetector", "Velocity tracker is null");
                }

                this.mLastTouchX = this.getActiveX(ev);
                this.mLastTouchY = this.getActiveY(ev);
                this.mIsDragging = false;
                break;
            }
            case 1: {
                if((this.mIsDragging) && this.mVelocityTracker != null) {
                    this.mLastTouchX = this.getActiveX(ev);
                    this.mLastTouchY = this.getActiveY(ev);
                    this.mVelocityTracker.addMovement(ev);
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    float v2 = this.mVelocityTracker.getXVelocity();
                    float v3 = this.mVelocityTracker.getYVelocity();
                    if(Math.max(Math.abs(v2), Math.abs(v3)) >= this.mMinimumVelocity) {
                        this.mListener.onFling(this.mLastTouchX, this.mLastTouchY, -v2, -v3);
                    }
                }

                if(this.mVelocityTracker == null) {
                    return true;
                }

                this.mVelocityTracker.recycle();
                this.mVelocityTracker = v12;
                break;
            }
            case 2: {
                float v4 = this.getActiveX(ev);
                float v5 = this.getActiveY(ev);
                float v0 = v4 - this.mLastTouchX;
                float v1 = v5 - this.mLastTouchY;
                if(!this.mIsDragging) {
                    if(FloatMath.sqrt(v0 * v0 + v1 * v1) >= this.mTouchSlop) {
                        v6 = true;
                    }

                    this.mIsDragging = v6;
                }

                if(!this.mIsDragging) {
                    return true;
                }

                this.mListener.onDrag(v0, v1);
                this.mLastTouchX = v4;
                this.mLastTouchY = v5;
                if(this.mVelocityTracker == null) {
                    return true;
                }

                this.mVelocityTracker.addMovement(ev);
                break;
            }
            case 3: {
                if(this.mVelocityTracker == null) {
                    return true;
                }

                this.mVelocityTracker.recycle();
                this.mVelocityTracker = v12;
                break;
            }
        }

        return true;
    }

    public void setOnGestureListener(MCOnGestureListener listener) {
        this.mListener = listener;
    }
}

