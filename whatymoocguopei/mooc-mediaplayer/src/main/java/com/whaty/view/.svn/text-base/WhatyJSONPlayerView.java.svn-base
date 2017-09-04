package com.whaty.view;

import android.app.Activity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.whaty.mediaplayer.WhatyMediaPlayer;

public class WhatyJSONPlayerView extends WhatyMediaPlayerView implements WhatyMediaPlayer.Listener {

	private SurfaceView json_surface_view;
	private WhatyMediaPlayer mPlayer;
	private static final String TAG = "WhatyJSONPlayerView";

	public WhatyJSONPlayerView() {
	}

	public WhatyJSONPlayerView(Activity activity, SurfaceView json_surface_view) {

		this.json_surface_view = json_surface_view;

		this.mPlayer = WhatyMediaPlayer.Factory.newInstance(activity);
		this.mPlayer.addListener(this);
		if (this.mPlayer != null)
			this.mPlayer.setDisplay(this.json_surface_view.getHolder());
	}

	/**
	 * 释放资源
	 */
	public void release() {
		this.mPlayer.release();
	}

	public void setVideoPath(String url) {

		if (this.mPlayer != null) {
			this.mPlayer.setDataSource(url);
			this.mPlayer.prepareAsync();
		}
	}

	public void seekTo(int msec) {

		this.mPlayer.seekTo(msec);

	}

	public void pause() {
		this.mPlayer.pause();
	}

	public void start() {
		this.mPlayer.start();
	}

	public void stop() {
		this.mPlayer.stop();
	}

	public View getCurrentView() {
		return this.json_surface_view;
	}

	public void suspend() {
		// this.mPlayer.suspend();
	}

	public void resume() {
		// this.mPlayer.resume();
	}

	public int getDuration() {
		return (int) this.mPlayer.getDuration();
	}

	public int getCurrentPosition() {
		return (int) this.mPlayer.getCurrentPosition();
	}

	public boolean isPlaying() {
		return this.mPlayer.isPlaying();
	}

	public void setZOrderMediaOverlay(boolean flag) {
	}

	/*
	 * Idle; Preparing; PreparingPaused; Prepared; Buffering; BufferingPaused;
	 * PLAYING Paused PlaybackCompleted;
	 */
	@Override
	public void onPlaybackStateChange(WhatyMediaPlayer arg0) {
		Log.d(TAG, mPlayer.getPlaybackState().toString());
		if (mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.Prepared) {
			if (this.mPlayerViewListener != null) {
				this.mPlayerViewListener.onPrepared();
			}
		}
		if ((mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.Buffering)
				|| (mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.Preparing)) {
			if (this.mPlayerViewListener != null) {
				this.mPlayerViewListener.onBuffering();
			}
		}
		if (mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.PlaybackCompleted) {
			if (this.mPlayerViewListener != null) {
				this.mPlayerViewListener.onCompletion();
			}
		}
		
		if (mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.PLAYING) {
			if (this.mPlayerViewListener != null) {
				this.mPlayerViewListener.onBufferComplete();
			}
		}
	}

	@Override
	public void onVideoSizeChanged(WhatyMediaPlayer arg0) {

	}
}
