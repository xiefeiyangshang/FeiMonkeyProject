package com.whatyplugin.base.photoview.scrollerproxy;



import android.annotation.TargetApi;
import android.content.Context;
import android.widget.OverScroller;

@TargetApi(value=9) public class MCGingerScroller extends MCScrollerProxy {
    private boolean mFirstScroll;
    protected final OverScroller mScroller;

    public MCGingerScroller(Context context) {
        super();
        this.mFirstScroll = false;
        this.mScroller = new OverScroller(context);
    }

    public boolean computeScrollOffset() {
        if(this.mFirstScroll) {
            this.mScroller.computeScrollOffset();
            this.mFirstScroll = false;
        }

        return this.mScroller.computeScrollOffset();
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, overX, overY);
    }

    public void forceFinished(boolean finished) {
        this.mScroller.forceFinished(finished);
    }

    public int getCurrX() {
        return this.mScroller.getCurrX();
    }

    public int getCurrY() {
        return this.mScroller.getCurrY();
    }

    public boolean isFinished() {
        return this.mScroller.isFinished();
    }
}

