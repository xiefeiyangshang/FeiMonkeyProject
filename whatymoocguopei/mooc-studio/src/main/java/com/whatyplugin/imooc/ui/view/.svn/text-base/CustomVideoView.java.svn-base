package com.whatyplugin.imooc.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

public class CustomVideoView extends VideoView {
	private PlayPauseListener mListener;
	private int mVideoHeight = 0;
	private int mVideoWidth = 0;
	private int type = 0;
	
	public void setType(int v){
		type = v;
	}

	public CustomVideoView(Context paramContext) {
		super(paramContext);
	}

	public CustomVideoView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public CustomVideoView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public void changeVideoSize(int paramInt1, int paramInt2) {
		this.mVideoWidth = paramInt1;
		this.mVideoHeight = paramInt2;
		getHolder().setFixedSize(paramInt1, paramInt2);
		forceLayout();
		invalidate();
		Log.e("tag", "update video size to " + paramInt1 + ", " + paramInt2);
	}

	@Override
	public void onDraw(Canvas paramCanvas) {
		super.onDraw(paramCanvas);
	}

	@Override
	public void onMeasure(int paramInt1, int paramInt2) {
		if(type == 0)
			super.onMeasure(paramInt1, paramInt2);
		else{
			if (this.mVideoWidth == 0)
				setMeasuredDimension(paramInt1, paramInt2);
			else
				setMeasuredDimension(this.mVideoWidth, this.mVideoHeight);
		}
	}

	public void pause() {
		super.pause();
		if (this.mListener != null)
			this.mListener.onPause();
	}

	public void setPlayPauseListener(PlayPauseListener paramPlayPauseListener) {
		this.mListener = paramPlayPauseListener;
	}

	public void start() {
		super.start();
		if (this.mListener != null)
			this.mListener.onPlay();
	}

	public static abstract interface PlayPauseListener {
		public abstract void onPause();

		public abstract void onPlay();
	}
}