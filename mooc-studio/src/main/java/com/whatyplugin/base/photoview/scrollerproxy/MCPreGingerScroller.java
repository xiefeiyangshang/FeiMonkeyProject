package com.whatyplugin.base.photoview.scrollerproxy;



import android.content.Context;
import android.widget.Scroller;

public class MCPreGingerScroller extends MCScrollerProxy {
    private final Scroller mScroller;

    public MCPreGingerScroller(Context context) {
        super();
        this.mScroller = new Scroller(context);
    }

    public boolean computeScrollOffset() {
        return this.mScroller.computeScrollOffset();
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
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

