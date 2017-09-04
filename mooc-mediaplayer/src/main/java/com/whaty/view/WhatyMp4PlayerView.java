package com.whaty.view;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.view.View;
import android.widget.VideoView;

public class WhatyMp4PlayerView extends WhatyMediaPlayerView {

	private VideoView mp4_surface_view;

	public WhatyMp4PlayerView() {
	}

	public WhatyMp4PlayerView(Activity activity, VideoView mp4_surface_view) {
		super(activity);

		this.mp4_surface_view = mp4_surface_view;
		initVideoViewListener();
	}

	private void initVideoViewListener() {
		this.mp4_surface_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				if (mPlayerViewListener != null) {
					mPlayerViewListener.onPrepared();
				}
				mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {

					@Override
					public void onSeekComplete(MediaPlayer mp) {
						if (mPlayerViewListener != null) {
							mPlayerViewListener.onSeekComplete();
						}
					}
				});

			}
		});

		this.mp4_surface_view.setOnErrorListener(new OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				if (mPlayerViewListener != null) {
					mPlayerViewListener.onError();
				}
				return true;
			}
		});

		this.mp4_surface_view.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				if (mPlayerViewListener != null) {
					mPlayerViewListener.onCompletion();
				}
			}

		});
	}

	/**
	 * 释放资源
	 */
	public void release() {
		this.mp4_surface_view.stopPlayback();
	}

	public void setVideoPath(String url) {
		this.mp4_surface_view.setVideoPath(url);
	}

	public void seekTo(int msec) {

		this.mp4_surface_view.seekTo(msec);

	}

	public void pause() {
		this.mp4_surface_view.pause();
	}

	public void start() {
		this.mp4_surface_view.start();
	}

	public void stop() {
		this.mp4_surface_view.setVideoPath("CLEAR_URL");
		this.mp4_surface_view.stopPlayback();
	}

	public View getCurrentView() {
		return this.mp4_surface_view;
	}

	public void suspend() {
		this.mp4_surface_view.suspend();
	}

	public void resume() {
		this.mp4_surface_view.suspend();
	}

	public int getDuration() {
		return this.mp4_surface_view.getDuration();
	}

	public int getCurrentPosition() {
		return this.mp4_surface_view.getCurrentPosition();
	}

	public boolean isPlaying() {
		return this.mp4_surface_view.isPlaying();
	}

	public void setZOrderMediaOverlay(boolean flag) {
		this.mp4_surface_view.setZOrderMediaOverlay(flag);
	}

}
