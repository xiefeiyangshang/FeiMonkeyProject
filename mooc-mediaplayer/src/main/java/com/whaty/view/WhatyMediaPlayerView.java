package com.whaty.view;

import android.content.Context;
import android.view.View;

public abstract class WhatyMediaPlayerView {

	protected Context mContext;

	protected WhatyMediaPlayerViewListener mPlayerViewListener;

	WhatyMediaPlayerView() {

	}

	WhatyMediaPlayerView(Context mcContext) {
		this.mContext = mcContext;
	}

	/**
	 * 释放资源
	 */
	public abstract void release();

	public abstract void seekTo(int msec);

	public abstract void pause();

	public abstract void start();

	public abstract void stop();

	public abstract View getCurrentView();

	public abstract void suspend();

	public abstract void resume();

	public abstract int getDuration();

	public abstract int getCurrentPosition();

	public abstract boolean isPlaying();

	public abstract void setZOrderMediaOverlay(boolean flag);

	public abstract void setVideoPath (String url);
	/**
	 * 视频事件的几个回调
	 * 
	 * @author 马彦君
	 * 
	 */
	public static abstract interface WhatyMediaPlayerViewListener {
		public abstract void onError();

		public abstract void onCompletion();

		public abstract void onPrepared();

		public abstract void onSeekComplete();
		
		public abstract void onBufferComplete();

		public abstract void onBuffering();
	}

	public void setPlayerViewListener(WhatyMediaPlayerViewListener listener) {
		this.mPlayerViewListener = listener;
	}
}
