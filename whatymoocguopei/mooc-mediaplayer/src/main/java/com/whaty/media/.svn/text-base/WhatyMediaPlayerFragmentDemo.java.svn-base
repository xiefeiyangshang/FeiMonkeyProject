package com.whaty.media;

import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.whaty.mediaplayer.R;
import com.whaty.mediaplayer.WhatyMediaPlayer;

public class WhatyMediaPlayerFragmentDemo extends Fragment implements WhatyMediaPlayer.Listener, View.OnLayoutChangeListener {
	private static final int UPDATE_DOWNLOAD_SPEED = 1;
	private static final int UPDATE_PROGRESS = 2;
	private static final int FADE_OUT = 3;
	private static final int sDefaultTimeout = 3000;
	private static final int ViewScrollRatio = 200;
	WhatyMediaPlayer mPlayer;
	MessageHandler mHandler;
	FrameLayout mRootView;
	SurfaceView mSurfaceView;
	View mLoadingView;
	TextView mDownloadSpeed;
	TextView mDownloadHint;
	TextView mNetworkInfo;
	View mBackGroundView;
	View mMediaControllerView;
	ImageButton mPlayPauseButton;
	boolean mPlayWhenReady;
	boolean mDragging;
	ProgressBar mProgress;
	StringBuilder mFormatBuilder;
	Formatter mFormatter;
	TextView mEndTime;
	TextView mCurrentTime;
	Button mQualityLevel;
	NetworkStateReceiver mNetworkStateReceiver;
	boolean mOnlyUseWifi;
	private DialogInterface.OnClickListener qualityLevelOnClickListener;
	private View.OnClickListener qualityLevelChooserLister;
	ImageButton mFullScreen;
	TextView mSeekInfo;
	private SeekBar.OnSeekBarChangeListener mSeekListener;
	FullScreenHandler mFullScreenHandler;
	ControllerViewHideShowListener mControllerViewHideShowHandler;
	private int mOldVideoWidth;
	private int mOldVideoHeight;
	private int mOldVideoContainerWidth;
	private int mOldVideoContainerHeight;

	public WhatyMediaPlayerFragmentDemo() {
		this.mPlayWhenReady = true;

		this.mOnlyUseWifi = true;
		this.qualityLevelOnClickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (WhatyMediaPlayerFragmentDemo.this.mPlayer == null)
					return;
				WhatyMediaPlayerFragmentDemo.this.mPlayer.setCurrentQuality(which, true);
				if (WhatyMediaPlayerFragmentDemo.this.mQualityLevel != null)
					WhatyMediaPlayerFragmentDemo.this.mQualityLevel.setText(WhatyMediaPlayerFragmentDemo.this.mPlayer.getQualityLevels()[which]);
				WhatyMediaPlayerFragmentDemo.this.showMediaController();
			}
		};
		this.qualityLevelChooserLister = new View.OnClickListener() {
			public void onClick(View v) {
				if ((WhatyMediaPlayerFragmentDemo.this.mPlayer == null) || (WhatyMediaPlayerFragmentDemo.this.mPlayer.getQualityLevels() == null)
						|| (WhatyMediaPlayerFragmentDemo.this.mPlayer.getQualityLevels().length < 2))
					return;
				AlertDialog.Builder builder = new AlertDialog.Builder(WhatyMediaPlayerFragmentDemo.this.getActivity(), 5);
				builder.setTitle(null);
				builder.setItems(WhatyMediaPlayerFragmentDemo.this.mPlayer.getQualityLevels(), WhatyMediaPlayerFragmentDemo.this.qualityLevelOnClickListener);
				builder.show();
			}
		};
		this.mSeekListener = new SeekBar.OnSeekBarChangeListener() {
			public void onStartTrackingTouch(SeekBar bar) {
				WhatyMediaPlayerFragmentDemo.this.showMediaController();
				WhatyMediaPlayerFragmentDemo.this.mDragging = true;
				WhatyMediaPlayerFragmentDemo.this.mHandler.removeMessages(2);
				if (WhatyMediaPlayerFragmentDemo.this.mSeekInfo != null)
					WhatyMediaPlayerFragmentDemo.this.mSeekInfo.setVisibility(0);
			}

			public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
				if (fromuser)
					WhatyMediaPlayerFragmentDemo.this.showMediaController();
				long duration = WhatyMediaPlayerFragmentDemo.this.mPlayer.getDuration();
				long newposition = duration * bar.getProgress() / 1000L;
				if (WhatyMediaPlayerFragmentDemo.this.mCurrentTime != null)
					WhatyMediaPlayerFragmentDemo.this.mCurrentTime.setText(WhatyMediaPlayerFragmentDemo.this.stringForTime((int) newposition));
				if (WhatyMediaPlayerFragmentDemo.this.mSeekInfo != null)
					WhatyMediaPlayerFragmentDemo.this.mSeekInfo.setText(WhatyMediaPlayerFragmentDemo.this.stringForTime((int) newposition));
			}

			public void onStopTrackingTouch(SeekBar bar) {
				WhatyMediaPlayerFragmentDemo.this.mDragging = false;
				long duration = WhatyMediaPlayerFragmentDemo.this.mPlayer.getDuration();
				long newposition = duration * bar.getProgress() / 1000L;
				if (WhatyMediaPlayerFragmentDemo.this.mPlayer != null)
					WhatyMediaPlayerFragmentDemo.this.mPlayer.seekTo((int) newposition);
				if (WhatyMediaPlayerFragmentDemo.this.mSeekInfo != null)
					WhatyMediaPlayerFragmentDemo.this.mSeekInfo.setVisibility(8);
			}
		};
		this.mOldVideoWidth = -1;
		this.mOldVideoHeight = -1;
		this.mOldVideoContainerWidth = -1;
		this.mOldVideoContainerHeight = -1;
	}

	static String getSpeedStr(long speed) {
		if (speed > 1000000L)
			return speed * 100L / 1000L / 1000L / 100.0D + "MB/s";
		if (speed > 1000L)
			return speed * 100L / 1000L / 100.0D + "KB/s";
		return speed + "B/s";
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("onCreate(Bundle savedInstanceState)");
		this.mPlayer = WhatyMediaPlayer.Factory.newInstance(getActivity());
		this.mPlayer.addListener(this);
		this.mHandler = new MessageHandler(this);
		this.mFormatBuilder = new StringBuilder();
		this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
		updateNetWorkInfo();
		IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
		this.mNetworkStateReceiver = new NetworkStateReceiver(this);
		getActivity().registerReceiver(this.mNetworkStateReceiver, filter);
	}

	public void onDestroy() {
		if (this.mPlayer != null) {
			this.mPlayer.release();
		}
		if (this.mNetworkStateReceiver != null) {
			getActivity().unregisterReceiver(this.mNetworkStateReceiver);
		}
		super.onDestroy();
	}

	public WhatyMediaPlayer getMediaPlayer() {
		return this.mPlayer;
	}

	public void setFullScreenHandler(FullScreenHandler fullScreenHandler) {
		this.mFullScreenHandler = fullScreenHandler;
		updateFullScreen();
	}

	public void setOnlyUseWifi(boolean onlyUseWifi) {
		this.mOnlyUseWifi = onlyUseWifi;
		updateNetWorkInfo();
	}

	public boolean isOnlyUseWifi() {
		return this.mOnlyUseWifi;
	}

	public boolean isAutoStart() {
		return this.mPlayWhenReady;
	}

	public void setAutoStart(boolean autoStart) {
		this.mPlayWhenReady = autoStart;
	}

	public void setControllerViewHideShowListener(ControllerViewHideShowListener listener) {
		this.mControllerViewHideShowHandler = listener;
	}

	@SuppressLint({ "WrongViewCast" })
	public void initViews() {
		if (this.mRootView == null)
			return;
		this.mRootView.addOnLayoutChangeListener(this);
		this.mSurfaceView = ((SurfaceView) this.mRootView.findViewById(R.id.surfaceView));
		if (this.mPlayer != null)
			this.mPlayer.setDisplay(this.mSurfaceView.getHolder());
		this.mLoadingView = this.mRootView.findViewById(R.id.loadingView);
		this.mDownloadSpeed = ((TextView) this.mRootView.findViewById(R.id.download_speed));
		this.mDownloadHint = (TextView) this.mRootView.findViewById(R.id.download_hint);
		this.mNetworkInfo = ((TextView) this.mRootView.findViewById(R.id.network_info));
		this.mBackGroundView = this.mRootView.findViewById(R.id.backgroundView);
		this.mMediaControllerView = this.mRootView.findViewById(R.id.mediacontroller);
		this.mEndTime = ((TextView) this.mRootView.findViewById(R.id.mediacontroller_time_total));
		this.mCurrentTime = ((TextView) this.mRootView.findViewById(R.id.mediacontroller_time_current));
		this.mPlayPauseButton = ((ImageButton) this.mRootView.findViewById(R.id.mediacontroller_play_pause));
		this.mProgress = ((ProgressBar) this.mRootView.findViewById(R.id.mediacontroller_seekbar));
		this.mQualityLevel = ((Button) this.mRootView.findViewById(R.id.mediacontroller_quality_level));
		this.mFullScreen = ((ImageButton) this.mRootView.findViewById(R.id.mediacontroller_fullscreen));
		this.mSeekInfo = ((TextView) this.mRootView.findViewById(R.id.seekInfo));
		this.mRootView.setOnTouchListener(new View.OnTouchListener() {
			float startX;
			float startY;
			float stopX;
			float stopY;
			long new_pos;
			long start_pos;
			boolean x_scroll_start;

			public boolean onTouch(View arg0, MotionEvent event) {
				switch (event.getAction()) {
				case 0:
					if ((WhatyMediaPlayerFragmentDemo.this.mMediaControllerView != null) && (WhatyMediaPlayerFragmentDemo.this.mPlayer != null)) {
						if ((WhatyMediaPlayerFragmentDemo.this.mMediaControllerView.getVisibility() == 0)
								&& (WhatyMediaPlayerFragmentDemo.this.mPlayer.getPlaybackState() != WhatyMediaPlayer.PlayerState.Idle)
								&& (WhatyMediaPlayerFragmentDemo.this.mPlayer.getPlaybackState() != WhatyMediaPlayer.PlayerState.Preparing))
							WhatyMediaPlayerFragmentDemo.this.hideMediaController();
						else {
							WhatyMediaPlayerFragmentDemo.this.showMediaController();
						}
					}
					this.startX = (event.getX() * 200.0F);
					this.startY = (event.getY() * 200.0F);
					this.start_pos = WhatyMediaPlayerFragmentDemo.this.mPlayer.getCurrentPosition();
					this.x_scroll_start = false;
					break;
				case 2:
					this.stopX = (event.getX() * 200.0F);
					this.stopY = (event.getY() * 200.0F);
					if ((!this.x_scroll_start) && (Math.abs(this.stopX - this.startX) >= 3000.0F)
							&& (Math.abs(this.stopX - this.startX) > Math.abs(this.stopY - this.startY))
							&& (WhatyMediaPlayerFragmentDemo.this.mPlayer != null)) {
						if ((WhatyMediaPlayerFragmentDemo.this.mPlayer.getPlaybackState() != WhatyMediaPlayer.PlayerState.Idle)
								&& (WhatyMediaPlayerFragmentDemo.this.mPlayer.getPlaybackState() != WhatyMediaPlayer.PlayerState.Preparing)
								&& (WhatyMediaPlayerFragmentDemo.this.mPlayer.getPlaybackState() != WhatyMediaPlayer.PlayerState.PreparingPaused))
							this.x_scroll_start = true;
					}
					if (this.x_scroll_start) {
						this.new_pos = (long) (Math.min((float) WhatyMediaPlayerFragmentDemo.this.mPlayer.getDuration(),
								Math.max(0.0F, (float) this.start_pos + this.stopX - this.startX)));
						if (WhatyMediaPlayerFragmentDemo.this.mSeekInfo != null) {
							WhatyMediaPlayerFragmentDemo.this.mSeekInfo.setVisibility(0);
							WhatyMediaPlayerFragmentDemo.this.mSeekInfo.setText(WhatyMediaPlayerFragmentDemo.this.stringForTime((int) this.new_pos));
						}
					}
					break;
				case 1:
					this.stopX = (event.getX() * 200.0F);
					this.stopY = (event.getY() * 200.0F);
					if ((this.x_scroll_start) && (Math.abs(this.stopX - this.startX) >= 1000.0F)) {
						this.new_pos = (long) (Math.min((float) WhatyMediaPlayerFragmentDemo.this.mPlayer.getDuration(),
								Math.max(0.0F, (float) this.start_pos + this.stopX - this.startX)));
						WhatyMediaPlayerFragmentDemo.this.mPlayer.seekTo(this.new_pos);
					}
					if (WhatyMediaPlayerFragmentDemo.this.mSeekInfo != null)
						WhatyMediaPlayerFragmentDemo.this.mSeekInfo.setVisibility(8);
					break;
				}
				return true;
			}
		});
		if (this.mMediaControllerView != null) {
			this.mMediaControllerView.setOnTouchListener(new View.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					WhatyMediaPlayerFragmentDemo.this.showMediaController();
					return true;
				}
			});
		}
		if (this.mPlayPauseButton != null) {
			this.mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (WhatyMediaPlayerFragmentDemo.this.mPlayer == null) {
						return;
					}

					if (WhatyMediaPlayerFragmentDemo.this.mPlayer.isPlaying())
						WhatyMediaPlayerFragmentDemo.this.mPlayer.pause();
					else {
						WhatyMediaPlayerFragmentDemo.this.mPlayer.start();
					}
					WhatyMediaPlayerFragmentDemo.this.showMediaController();
				}
			});
		}

		if (this.mProgress != null) {
			if ((this.mProgress instanceof SeekBar)) {
				SeekBar seeker = (SeekBar) this.mProgress;
				seeker.setOnSeekBarChangeListener(this.mSeekListener);
			}
			this.mProgress.setMax(1000);
		}
		if (this.mFullScreen != null)
			this.mFullScreen.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (WhatyMediaPlayerFragmentDemo.this.mFullScreenHandler != null) {
						WhatyMediaPlayerFragmentDemo.this.mFullScreenHandler.toggleFullScreen();
						WhatyMediaPlayerFragmentDemo.this.updateFullScreen();
					}
				}
			});
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.mRootView = ((FrameLayout) inflater.inflate(R.layout.whaty_video_player_fragment, container, false));
		initViews();
		return this.mRootView;
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		updateFullScreen();
	}

	public void updateFullScreen() {
		if (this.mFullScreen == null)
			return;
		if (this.mFullScreenHandler != null) {
			this.mFullScreen.setVisibility(0);
			if (this.mFullScreenHandler.isFullScreen())
				this.mFullScreen.setImageResource(R.drawable.whaty_mediaplayer_contract);
			else
				this.mFullScreen.setImageResource(R.drawable.whaty_mediaplayer_fullscreen);
		} else {
			this.mFullScreen.setVisibility(8);
		}
	}

	public void updatePausePlay() {
		if ((this.mPlayPauseButton == null) || (this.mPlayer == null)) {
			return;
		}

		if (this.mPlayer.isPlaying())
			this.mPlayPauseButton.setImageResource(R.drawable.whaty_mediaplayer_pause);
		else
			this.mPlayPauseButton.setImageResource(R.drawable.whaty_mediaplayer_play);
	}

	private String stringForTime(long timeMs) {
		int totalSeconds = (int) timeMs / 1000;

		int seconds = totalSeconds % 60;
		int minutes = totalSeconds / 60 % 60;
		int hours = totalSeconds / 3600;

		this.mFormatBuilder.setLength(0);
		if (hours > 0) {
			return this.mFormatter
					.format("%d:%02d:%02d", new Object[] { Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds) }).toString();
		}
		return this.mFormatter.format("%02d:%02d", new Object[] { Integer.valueOf(minutes), Integer.valueOf(seconds) }).toString();
	}

	private long setProgress() {
		if ((this.mPlayer == null) || (this.mDragging)) {
			return 0L;
		}

		long position = this.mPlayer.getCurrentPosition();
		long duration = this.mPlayer.getDuration();
		if (this.mProgress != null) {
			if (duration > 0L) {
				long pos = 1000L * position / duration;
				this.mProgress.setProgress((int) pos);
			}
			int percent = this.mPlayer.getBufferPercentage();
			this.mProgress.setSecondaryProgress(percent * 10);
		}

		if (this.mEndTime != null)
			this.mEndTime.setText(stringForTime(duration));
		if (this.mCurrentTime != null)
			this.mCurrentTime.setText(stringForTime(position));
		return position;
	}

	public void hideMediaController() {
		if ((this.mMediaControllerView != null) && (this.mMediaControllerView.getVisibility() != 8)) {
			this.mMediaControllerView.setVisibility(8);
			if (this.mControllerViewHideShowHandler != null)
				this.mControllerViewHideShowHandler.onHideMediaController();
		}
	}

	public void showMediaController() {
		showMediaController(3000);
	}

	public void showMediaController(int timeout) {
		if ((this.mMediaControllerView != null) && (this.mMediaControllerView.getVisibility() != 0)) {
			this.mMediaControllerView.setVisibility(0);
			if (this.mControllerViewHideShowHandler != null)
				this.mControllerViewHideShowHandler.onShowMediaController();
		}
		setProgress();
		updatePausePlay();
		updateFullScreen();
		this.mHandler.removeMessages(3);
		this.mHandler.sendEmptyMessage(2);
		if (timeout != 0)
			this.mHandler.sendEmptyMessageDelayed(3, timeout);
	}

	public void adjustVideoSize() {
		if ((this.mRootView == null) || (this.mPlayer == null) || (this.mSurfaceView == null))
			return;
		if ((this.mOldVideoHeight == this.mPlayer.getVideoHeight()) && (this.mOldVideoWidth == this.mPlayer.getVideoWidth())
				&& (this.mOldVideoContainerHeight == this.mRootView.getHeight()) && (this.mOldVideoContainerWidth == this.mRootView.getWidth()))
			return;

		this.mOldVideoContainerWidth = this.mRootView.getWidth();
		this.mOldVideoContainerHeight = this.mRootView.getHeight();
		this.mOldVideoWidth = this.mPlayer.getVideoWidth();
		this.mOldVideoHeight = this.mPlayer.getVideoHeight();

		if ((this.mOldVideoWidth <= 0) || (this.mOldVideoHeight <= 0)) {
			return;
		}
		ViewGroup.LayoutParams layoutParams = this.mSurfaceView.getLayoutParams();
		if (this.mOldVideoContainerWidth * this.mOldVideoHeight > this.mOldVideoContainerHeight * this.mOldVideoWidth) {
			layoutParams.width = (this.mOldVideoContainerHeight * this.mOldVideoWidth / this.mOldVideoHeight);
			layoutParams.height = this.mOldVideoContainerHeight;
		} else {
			layoutParams.width = this.mOldVideoContainerWidth;
			layoutParams.height = (this.mOldVideoHeight * this.mOldVideoContainerWidth / this.mOldVideoWidth);
		}
		this.mSurfaceView.setLayoutParams(layoutParams);
	}

	public void onPlaybackStateChange(WhatyMediaPlayer player) {
		if (player.getPlaybackState() == WhatyMediaPlayer.PlayerState.Prepared) {
			if (this.mQualityLevel != null) {
				if ((player.getQualityLevels() != null) && (player.getQualityLevels().length >= 2)) {
					this.mQualityLevel.setText(player.getQualityLevels()[player.getCurrentQuality()]);
					if (Build.VERSION.SDK_INT >= 16)
						this.mQualityLevel.setVisibility(0);
					this.mQualityLevel.setOnClickListener(this.qualityLevelChooserLister);
				} else {
					this.mQualityLevel.setVisibility(8);
				}
			}
			if (this.mPlayWhenReady)
				player.start();
		}
		if (this.mLoadingView != null) {
			if ((player.getPlaybackState() == WhatyMediaPlayer.PlayerState.Buffering)
					|| (player.getPlaybackState() == WhatyMediaPlayer.PlayerState.Preparing)) {
				this.mLoadingView.setVisibility(0);
				this.mHandler.sendEmptyMessage(1);
			} else {
				this.mLoadingView.setVisibility(8);
				this.mHandler.removeMessages(1);
			}
		}
		updateBackGroundView();
		if (this.mProgress != null)
			if ((player.getPlaybackState() == WhatyMediaPlayer.PlayerState.Idle)
					|| (player.getPlaybackState() == WhatyMediaPlayer.PlayerState.Preparing)
					|| (player.getPlaybackState() == WhatyMediaPlayer.PlayerState.PreparingPaused))
				this.mProgress.setEnabled(false);
			else
				this.mProgress.setEnabled(true);
	}

	void updateNetWorkInfo() {
		ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService("connectivity");

		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			if ((networkInfo.getType() == 1) || (!this.mOnlyUseWifi)) {
				if (getMediaPlayer().isLoadingPaused()) {
					getMediaPlayer().resumeLoading();
				}
				if ((this.mNetworkInfo != null) && (this.mNetworkInfo.getVisibility() == 0))
					this.mNetworkInfo.setVisibility(8);
			} else {
				getMediaPlayer().pauseLoading();
				if (this.mNetworkInfo != null) {
					this.mNetworkInfo.setText("检测到现在使用的是移动网络,下载已经停止!");
					if (this.mNetworkInfo.getVisibility() != 0)
						this.mNetworkInfo.setVisibility(0);
				}
			}
		} else {
			getMediaPlayer().pauseLoading();
			if (this.mNetworkInfo != null) {
				this.mNetworkInfo.setText("无法连接网络，请检查网络!");
				if (this.mNetworkInfo.getVisibility() != 0)
					this.mNetworkInfo.setVisibility(0);
			}
		}
	}

	void updateBackGroundView() {
		if (this.mBackGroundView != null)
			if (((this.mPlayer != null) && (this.mPlayer.getVideoWidth() <= 0)) || (this.mPlayer.getVideoHeight() <= 0)
					|| (this.mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.Idle)
					|| (this.mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.Preparing))
				this.mBackGroundView.setVisibility(0);
			else
				this.mBackGroundView.setVisibility(8);
	}

	public void onVideoSizeChanged(WhatyMediaPlayer player) {
		updateBackGroundView();
		adjustVideoSize();
	}

	public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
		adjustVideoSize();
	}

	private static class NetworkStateReceiver extends BroadcastReceiver {
		WhatyMediaPlayerFragmentDemo video_fragment;

		NetworkStateReceiver(WhatyMediaPlayerFragmentDemo video_fragment) {
			this.video_fragment = video_fragment;
		}

		public void onReceive(Context context, Intent intent) {
			this.video_fragment.updateNetWorkInfo();
		}
	}

	private static class MessageHandler extends Handler {
		private final WeakReference<WhatyMediaPlayerFragmentDemo> video_fragment_ref;

		MessageHandler(WhatyMediaPlayerFragmentDemo video_fragment) {
			this.video_fragment_ref = new WeakReference(video_fragment);
		}

		public void handleMessage(Message msg) {
			WhatyMediaPlayerFragmentDemo video_fragment = (WhatyMediaPlayerFragmentDemo) this.video_fragment_ref.get();
			if (this.video_fragment_ref.get() == null) {
				return;
			}

			switch (msg.what) {
			case 3:
				video_fragment.hideMediaController();
				break;
			case 2:
				long pos = video_fragment.setProgress();
				if ((!video_fragment.mDragging)
						&& (video_fragment.mMediaControllerView != null)
						&& (video_fragment.mMediaControllerView.getVisibility() == 0)
						&& (video_fragment.mPlayer != null)
						&& ((video_fragment.mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.PLAYING)
								|| (video_fragment.mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.Paused) || (video_fragment.mPlayer
								.getPlaybackState() == WhatyMediaPlayer.PlayerState.Buffering)))
					sendMessageDelayed(obtainMessage(2), 1000L - pos % 1000L);
				break;
			case 1:
				if (video_fragment.mDownloadSpeed != null) {
					if ((video_fragment.mPlayer != null) && (video_fragment.mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.Buffering))
						if (Build.VERSION.SDK_INT >= 16){
							video_fragment.mDownloadSpeed.setText(WhatyMediaPlayerFragmentDemo.getSpeedStr(video_fragment.mPlayer.getDownloadSpeed()));
							video_fragment.mDownloadHint.setText("正在缓冲...");
						}
						else{
//							video_fragment.mDownloadSpeed.setText("正在缓冲...\n");
							video_fragment.mDownloadHint.setText("正在缓冲...");
						}
					if (video_fragment.mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.Preparing)
					{	
//						video_fragment.mDownloadSpeed.setText("正在加载...");
						video_fragment.mDownloadHint.setText("正在111加载...");
					}
				}
				if ((video_fragment.mDownloadSpeed != null)
						&& (video_fragment.mLoadingView != null)
						&& (video_fragment.mLoadingView.getVisibility() == 0)
						&& (video_fragment.mPlayer != null)
						&& ((video_fragment.mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.Buffering) || (video_fragment.mPlayer
								.getPlaybackState() == WhatyMediaPlayer.PlayerState.Preparing)))
					sendEmptyMessageDelayed(1, 1000L);
				else
					removeMessages(1);
				break;
			}
		}
	}

	public static abstract interface ControllerViewHideShowListener {
		public abstract void onHideMediaController();

		public abstract void onShowMediaController();
	}

	public static abstract interface FullScreenHandler {
		public abstract boolean isFullScreen();

		public abstract void toggleFullScreen();
	}
}