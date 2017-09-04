package com.whatyplugin.base.photoview.scrollerproxy;

import android.content.Context;

public abstract class MCScrollerProxy {
	public MCScrollerProxy() {
		super();
	}

	public abstract boolean computeScrollOffset();

	public abstract void fling(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8, int arg9, int arg10);

	public abstract void forceFinished(boolean arg1);

	public abstract int getCurrX();

	public abstract int getCurrY();

	public static MCScrollerProxy getScroller(Context context) {
		MCPreGingerScroller mcPreGingerScroller = new MCPreGingerScroller(context);
		return mcPreGingerScroller;
	}

	public abstract boolean isFinished();
}
