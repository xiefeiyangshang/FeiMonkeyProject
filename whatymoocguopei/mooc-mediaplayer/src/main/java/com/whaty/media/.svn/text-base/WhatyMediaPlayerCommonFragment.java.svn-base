package com.whaty.media;

import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.whaty.mediaplayer.R;
import com.whaty.view.WhatyJSONPlayerView;
import com.whaty.view.WhatyMediaPlayerView;
import com.whaty.view.WhatyMediaPlayerView.WhatyMediaPlayerViewListener;
import com.whaty.view.WhatyMp4PlayerView;

public class WhatyMediaPlayerCommonFragment extends Fragment {
	private static final String TAG = "WhatyMediaPlayerCommonFragment";
	private MessageHandler mHandler;
	private FrameLayout mRootView;
	private WhatyMediaPlayerView mPlayerView;
	private View mLoadingView;
	private TextView mDownloadSpeed;
	private View mBackGroundView;
	private View mMediaControllerView;
	private ImageButton mPlayPauseButton;
	private boolean mPlayWhenReady;
	private boolean mDragging;
	private ProgressBar mProgress;
	private StringBuilder mFormatBuilder;
	private Formatter mFormatter;
	private TextView mEndTime;
	private TextView mCurrentTime;
	private boolean mOnlyUseWifi;
	private ImageButton mFullScreen;
	private ImageButton mPlayNext;
	private TextView mSeekInfo;
	private SeekBar.OnSeekBarChangeListener mSeekListener;
	private FullScreenHandler mFullScreenHandler;
	private BufferingUpdateHandler mBufferingUpdateHandler;
	private SeekToHandler mSeekToHandler;
	private PlayNextHandler mPlayNextListener;
	
	private int mOldVideoWidth;
	private int mOldVideoHeight;
	private int mOldVideoContainerWidth;
	private int mOldVideoContainerHeight;
	private boolean isFullScreen;
	private boolean isControlShow = true;// 控制栏是否要显示， 默认显示
	private boolean isCanDrag = false;// 视频是否能拖动，能拖动的话就不以拖动来控制进度条了
	private int parentViewId;
	private WhatyMediaPlayerCommonFragment bindVideo;
	private boolean isStart = false; // 是否点击了播放键，引起的请求
	private OnTouchListener mOnTouchListener;

	private SurfaceView json_surface_view;
	private VideoView mp4_surface_view;
	private WhatyMediaPlayerViewListener playerViewListener;
	private String playingUrl;

	public WhatyMediaPlayerCommonFragment() {
		this.mPlayWhenReady = true;
		this.mOnlyUseWifi = true;
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
		this.mHandler = new MessageHandler(this);
		this.mFormatBuilder = new StringBuilder();
		this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
	}

	public boolean isPlaying() {
		return mPlayerView.isPlaying();
	}

	public void setFullScreenHandler(FullScreenHandler fullScreenHandler) {
		this.mFullScreenHandler = fullScreenHandler;
	}

	public void setSeekToHandler(SeekToHandler seekToHandler) {
		this.mSeekToHandler = seekToHandler;
	}
	
	public void setPlayNextHandler(PlayNextHandler playNextListener) {
		this.mPlayNextListener = playNextListener;
	}

	@SuppressLint({ "WrongViewCast" })
	public void initViews() {
		if (this.mRootView == null)
			return;

		json_surface_view = (SurfaceView) this.mRootView.findViewById(R.id.json_surface_view);
		mp4_surface_view = (VideoView) this.mRootView.findViewById(R.id.mp4_surface_view);

		this.mLoadingView = this.mRootView.findViewById(R.id.loadingView);
		this.mDownloadSpeed = ((TextView) this.mRootView.findViewById(R.id.download_speed));
		this.mBackGroundView = this.mRootView.findViewById(R.id.backgroundView);
		this.mMediaControllerView = this.mRootView.findViewById(R.id.mediacontroller);
		this.mEndTime = ((TextView) this.mRootView.findViewById(R.id.mediacontroller_time_total));
		this.mCurrentTime = ((TextView) this.mRootView.findViewById(R.id.mediacontroller_time_current));
		this.mPlayPauseButton = ((ImageButton) this.mRootView.findViewById(R.id.mediacontroller_play_pause));
		this.mProgress = ((ProgressBar) this.mRootView.findViewById(R.id.mediacontroller_seekbar));
		this.mFullScreen = ((ImageButton) this.mRootView.findViewById(R.id.mediacontroller_fullscreen));
		this.mSeekInfo = ((TextView) this.mRootView.findViewById(R.id.seekInfo));
		this.mPlayNext = (ImageButton) this.mRootView.findViewById(R.id.mediacontroller_play_next);

		this.mPlayerView = new WhatyMp4PlayerView(this.getActivity(), this.mp4_surface_view);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.mRootView = ((FrameLayout) inflater.inflate(R.layout.whaty_video_player_common_fragment, container, false));
		initViews();
		initEvents();
		return this.mRootView;
	}

	/**
	 * 全屏和非全屏控件大小不一致， 用这个方法来更新这些控件
	 */
	public void updateControlView(boolean isFull) {

		int level = this.mPlayPauseButton.getBackground().getLevel();

		ViewGroup.LayoutParams params = this.mMediaControllerView.getLayoutParams();
		if (isFull) {
			this.mPlayPauseButton.setBackgroundResource(R.drawable.whaty_mediaplayer_play_big_level);
			this.mFullScreen.setImageResource(R.drawable.whaty_mediaplayer_fullscreen_big);
			params.height = (int) this.getActivity().getResources().getDimension(R.dimen.player_60_dp);
			
			// 设置了播放一下个，这里要给以显示
			if (mPlayNextListener!=null) {
				this.mPlayNext.setVisibility(View.VISIBLE);
				this.mPlayNext.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mPlayNextListener.playNext(v);
					}
				});
			}
		} else {
			this.mPlayPauseButton.setBackgroundResource(R.drawable.whaty_mediaplayer_play_small_level);
			this.mFullScreen.setImageResource(R.drawable.whaty_mediaplayer_fullscreen);
			params.height = (int) this.getActivity().getResources().getDimension(R.dimen.player_30_dp);
			this.mPlayNext.setVisibility(View.GONE);
		}
		this.mMediaControllerView.setLayoutParams(params);
		this.mPlayPauseButton.getBackground().setLevel(level);
	}

	public void updatePausePlay() {
		if (mPlayerView.isPlaying())
			this.mPlayPauseButton.getBackground().setLevel(1);
		else
			this.mPlayPauseButton.getBackground().setLevel(0);
	}
	
	public int getMediaControllerHeight() {
		return this.mMediaControllerView.getMeasuredHeight();
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
		if ((mPlayerView == null) || (this.mDragging)) {
			return 0L;
		}

		long position = mPlayerView.getCurrentPosition();
		long duration = mPlayerView.getDuration();
		if (this.mProgress != null) {
			if (duration > 0L) {
				long pos = 1000L * position / duration;
				this.mProgress.setProgress((int) pos);
			}
		}

		if (this.mEndTime != null)
			this.mEndTime.setText(stringForTime(duration));
		if (this.mCurrentTime != null)
			this.mCurrentTime.setText(stringForTime(position));

		return position;
	}

	public void hideMediaController() {
		if (this.mMediaControllerView != null) {
			this.mMediaControllerView.setVisibility(View.INVISIBLE);
		}
	}

	public void showMediaController() {
		showMediaController(3000);
	}

	public void showMediaController(int timeout) {
		if (!this.isControlShow) {
			return;
		}
		if (this.mMediaControllerView != null) {
			this.mMediaControllerView.setVisibility(View.VISIBLE);
		}
		setProgress();
		updatePausePlay();
		this.mHandler.removeMessages(3);
		this.mHandler.sendEmptyMessage(2);
		if (timeout != 0)
			this.mHandler.sendEmptyMessageDelayed(3, timeout);
	}

	private class MessageHandler extends Handler {
		private final WeakReference<WhatyMediaPlayerCommonFragment> video_fragment_ref;

		MessageHandler(WhatyMediaPlayerCommonFragment video_fragment) {
			this.video_fragment_ref = new WeakReference(video_fragment);
		}

		public void handleMessage(Message msg) {
			WhatyMediaPlayerCommonFragment video_fragment = (WhatyMediaPlayerCommonFragment) this.video_fragment_ref.get();
			if (this.video_fragment_ref.get() == null) {
				return;
			}

			switch (msg.what) {
			case 4:
				video_fragment.start();//播放json的时候要延时start
				break;
			case 3:
				video_fragment.hideMediaController();
				break;
			case 2:
				long pos = video_fragment.setProgress();

				if (!video_fragment.mDragging && mBufferingUpdateHandler != null) {
					mBufferingUpdateHandler.afterBufferingUpdate((int) pos);
					sendMessageDelayed(obtainMessage(2), 1000L);
				} else if (!video_fragment.mDragging && (video_fragment.mMediaControllerView.getVisibility() == 0)) {
					sendMessageDelayed(obtainMessage(2), 1000L - pos % 1000L);
				}

				break;
			case 1:
				if (video_fragment.mDownloadSpeed != null) {
					video_fragment.mDownloadSpeed.setText("正在加载...");
				}
				if ((video_fragment.mDownloadSpeed != null) && (video_fragment.mLoadingView != null)
						&& (video_fragment.mLoadingView.getVisibility() == 0) && (video_fragment.mPlayerView != null))
					sendEmptyMessageDelayed(1, 1000L);
				else
					removeMessages(1);
				break;
			}
		}
	}

	public boolean isFullScreen() {
		return isFullScreen;
	}

	/**
	 * 根据类型初始化不同的实例
	 * 
	 * @param currentUrl
	 */
	public void initBeforePlay(String currentUrl) {
		if(currentUrl.contains("?")) {
			currentUrl = currentUrl.substring(0, currentUrl.indexOf("?"));
		}
		if (currentUrl.endsWith(".mp4")) {
			if (this.mPlayerView == null || !(this.mPlayerView instanceof WhatyMp4PlayerView)) {
				this.mPlayerView = new WhatyMp4PlayerView(this.getActivity(), this.mp4_surface_view);
				this.mPlayerView.setPlayerViewListener(playerViewListener);
			}
			this.mp4_surface_view.setVisibility(View.VISIBLE);
			this.json_surface_view.setVisibility(View.GONE);
			this.mRootView.setOnTouchListener(mOnTouchListener);

		} else if (currentUrl.endsWith(".json")) {
			if (this.mPlayerView == null || !(this.mPlayerView instanceof WhatyJSONPlayerView)) {
				this.mPlayerView = new WhatyJSONPlayerView(this.getActivity(), this.json_surface_view);
				this.mPlayerView.setPlayerViewListener(playerViewListener);
			}
			this.json_surface_view.setVisibility(View.VISIBLE);
			this.mp4_surface_view.setVisibility(View.GONE);
			this.mRootView.setOnTouchListener(mOnTouchListener);
		} else {
		}
	}

	public void play(String url) {

		if (this.playingUrl != null && this.playingUrl.equals(url)) {
			this.mPlayerView.seekTo(0);
		} else {
			setVideoPath(url);
			if (this.mPlayerView instanceof WhatyJSONPlayerView) {
				Message msg = new Message();
				msg.what = 4;
				this.mHandler.sendMessageDelayed(msg, 800);
			} else {
				start();
			}
		}
	}
	
	public void setVideoPath(String url) {
		initBeforePlay(url);
		this.mPlayerView.setVideoPath(url);
	}

	public void seekTo(int msec) {
		WhatyMediaPlayerCommonFragment.this.mLoadingView.setVisibility(View.VISIBLE);
		this.mPlayerView.seekTo(msec);
	}

	public void pause() {
		this.mPlayerView.pause();
	}
	
	public void start() {
		// 去掉背景图片， 显示进度条
		mProgress.setEnabled(true);
		isStart = true;
		mPlayPauseButton.setEnabled(true);
	//	mBackGroundView.setVisibility(View.GONE);
		this.mLoadingView.setVisibility(View.VISIBLE);
		this.mPlayerView.start();
	}

	public void stop() {
		// 显示背景图片，去掉进度条
		isStart = false; // 停止之后，结束网络请求返回事件
		this.mPlayerView.stop();
		this.mBackGroundView.setVisibility(View.VISIBLE);
		this.mLoadingView.setVisibility(View.GONE);

	}

	public void release() {
		this.mHandler.removeMessages(2);
		this.mHandler.removeMessages(3);
		this.mPlayerView.release();
	}

	public WhatyMediaPlayerView getVideoView() {
		return this.mPlayerView;
	}

	/**
	 * 手动点击引起的屏幕全屏变化
	 */
	public void toggleFullScreen() {

		this.isFullScreen = !this.isFullScreen;

		if (this.mFullScreenHandler != null) {
			this.mFullScreenHandler.afterToggleFullScreen(this);// 切换之后要做的事情
		}
		updateControlView(this.isFullScreen);
	}

	public boolean isControlShow() {
		return isControlShow;
	}

	public void setControlShow(boolean isControlShow) {
		this.isControlShow = isControlShow;
	}

	public WhatyMediaPlayerCommonFragment getBindVideo() {
		return bindVideo;
	}

	public boolean isCanDrag() {
		return isCanDrag;
	}

	public void setCanDrag(boolean isCanDrag) {
		this.isCanDrag = isCanDrag;
	}

	/**
	 * 绑定一个其他的视频，实现2个视频之间的联动效果。
	 * 
	 * @param otherVideo
	 */
	public void setBindVideo(WhatyMediaPlayerCommonFragment bindVideo) {
		this.bindVideo = bindVideo;
	}

	public void setOnBufferingUpdate(BufferingUpdateHandler bufferingUpdateHandler) {
		this.mBufferingUpdateHandler = bufferingUpdateHandler;
	}

	public int getParentViewId() {
		return parentViewId;
	}

	public void setParentViewId(int parentViewId) {
		this.parentViewId = parentViewId;
	}

	public static abstract interface ControllerViewHideShowListener {
		public abstract void onHideMediaController();

		public abstract void onShowMediaController();
	}

	/**
	 * 全屏之后的回调
	 * 
	 * @author 马彦君
	 * 
	 */
	public static abstract interface FullScreenHandler {

		public abstract void afterToggleFullScreen(WhatyMediaPlayerCommonFragment videoFragment);
	}

	/**
	 * 切换到某个进度条的回调
	 * 
	 * @author 马彦君
	 * 
	 */
	public static abstract interface SeekToHandler {

		public abstract void afterSeekTo(int position);
	}

	/**
	 * 进度条更新后的回调
	 * 
	 * @author 马彦君
	 * 
	 */
	public static abstract interface BufferingUpdateHandler {

		public abstract void afterBufferingUpdate(int position);
	}
	
	/**
	 * 点击播放下一个的回调
	 * 
	 * @author 马彦君
	 * 
	 */
	public static abstract interface PlayNextHandler {

		public abstract void playNext(View v);
	}

	private void initPlayerListener() {
		playerViewListener = new WhatyMediaPlayerViewListener() {

			@Override
			public void onError() {
				if (isStart) {
					Toast.makeText(WhatyMediaPlayerCommonFragment.this.getActivity(), "该视频不能播放！", 0).show();
				} else {
					mProgress.setEnabled(false);
					mPlayPauseButton.setEnabled(false);
				}
				WhatyMediaPlayerCommonFragment.this.mLoadingView.setVisibility(View.GONE);
				WhatyMediaPlayerCommonFragment.this.mBackGroundView.setVisibility(View.VISIBLE);
			}

			@Override
			public void onCompletion() {
				// 播放完成的时候显示默认背景图片
				WhatyMediaPlayerCommonFragment.this.mBackGroundView.setVisibility(View.VISIBLE);
				
			}

			@Override
			public void onSeekComplete() {
				// 加载完成去掉
				WhatyMediaPlayerCommonFragment.this.mLoadingView.setVisibility(View.GONE);
				WhatyMediaPlayerCommonFragment.this.mBackGroundView.setVisibility(View.GONE);
			}

			@Override
			public void onPrepared() {
				// 加载完成去掉
				WhatyMediaPlayerCommonFragment.this.mLoadingView.setVisibility(View.GONE);
				WhatyMediaPlayerCommonFragment.this.mBackGroundView.setVisibility(View.INVISIBLE);
				WhatyMediaPlayerCommonFragment.this.mPlayPauseButton.getBackground().setLevel(1);

			}

			@Override
			public void onBuffering() {
			}

			@Override
			public void onBufferComplete() {
				// 加载完成去掉
				WhatyMediaPlayerCommonFragment.this.mLoadingView.setVisibility(View.GONE);
				WhatyMediaPlayerCommonFragment.this.mBackGroundView.setVisibility(View.GONE);
			}
		};
		
		
		// 整个fragment的滑动事件处理
		mOnTouchListener = new View.OnTouchListener() {
			float startX;
			float startY;
			float stopX;
			float stopY;
			long new_pos;
			long start_pos;
			boolean x_scroll_start;
			private int _x;
			private int _xDelta;
			private int _y;
			private int _yDelta;

			public boolean onTouch(View arg0, MotionEvent event) {
				if (isCanDrag) {// 拖动视频位置的处理
					final int X = (int) event.getRawX();
					final int Y = (int) event.getRawY();
					switch (event.getAction() & MotionEvent.ACTION_MASK) {
					case MotionEvent.ACTION_DOWN:
						RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) mRootView.getLayoutParams();
						_x = X;
						_y = Y;
						_xDelta = X - lParams.leftMargin;
						_yDelta = Y - lParams.topMargin;
						break;

					case MotionEvent.ACTION_MOVE:
						if (Math.abs(_x - X) > 20 || Math.abs(_y - Y) > 20) {
							RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRootView.getLayoutParams();
							layoutParams.leftMargin = X - _xDelta;
							layoutParams.topMargin = Y - _yDelta;
							layoutParams.rightMargin = 0 - layoutParams.width;
							layoutParams.bottomMargin = 0 - layoutParams.height;// -(landH);
							mRootView.setLayoutParams(layoutParams);
						}
						break;
					}
					mRootView.invalidate();
					return true;
				} else {// 滑动调整视频进度的处理
					if (!isControlShow) {// 不显示控制栏，不能拖动的情况下就对touch事件进行处理 了。
						return true;
					}
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if ((WhatyMediaPlayerCommonFragment.this.mMediaControllerView != null) && (mPlayerView != null)) {
							if (WhatyMediaPlayerCommonFragment.this.mMediaControllerView.getVisibility() == 0)
								WhatyMediaPlayerCommonFragment.this.hideMediaController();
							else {
								WhatyMediaPlayerCommonFragment.this.showMediaController();
							}
						}

						this.startX = (event.getX() * 200.0F);
						this.startY = (event.getY() * 200.0F);
						this.start_pos = mPlayerView.getCurrentPosition();
						this.x_scroll_start = false;
						break;
					case MotionEvent.ACTION_MOVE:
						this.stopX = (event.getX() * 200.0F);
						this.stopY = (event.getY() * 200.0F);
						if ((!this.x_scroll_start) && (Math.abs(this.stopX - this.startX) >= 3000.0F)
								&& (Math.abs(this.stopX - this.startX) > Math.abs(this.stopY - this.startY)) && (mPlayerView != null)) {
							this.x_scroll_start = true;
						}
						if (this.x_scroll_start) {
							this.new_pos = (long) (Math.min((float) mPlayerView.getDuration(),
									Math.max(0.0F, (float) this.start_pos + this.stopX - this.startX)));
							WhatyMediaPlayerCommonFragment.this.mSeekInfo.setVisibility(0);
							WhatyMediaPlayerCommonFragment.this.mSeekInfo.setText(WhatyMediaPlayerCommonFragment.this
									.stringForTime((int) this.new_pos));
							WhatyMediaPlayerCommonFragment.this.showMediaController();
						}

						break;
					case MotionEvent.ACTION_UP:
						this.stopX = (event.getX() * 200.0F);
						this.stopY = (event.getY() * 200.0F);
						if ((this.x_scroll_start) && (Math.abs(this.stopX - this.startX) >= 1000.0F)) {
							this.new_pos = (long) (Math.min((float) mPlayerView.getDuration(),
									Math.max(0.0F, (float) this.start_pos + this.stopX - this.startX)));
							WhatyMediaPlayerCommonFragment.this.mLoadingView.setVisibility(View.VISIBLE);

							seekTo((int) this.new_pos);

							if (bindVideo != null) {
								bindVideo.seekTo((int) this.new_pos);
							}

							if (mSeekToHandler != null) {
								mSeekToHandler.afterSeekTo((int) this.new_pos);
							}
						}
						if (WhatyMediaPlayerCommonFragment.this.mSeekInfo != null)
							WhatyMediaPlayerCommonFragment.this.mSeekInfo.setVisibility(8);
						break;
					}
				}
				return true;
			}
		};
	}
	
	private void initEvents() {

		initPlayerListener();

		this.mSeekListener = new SeekBar.OnSeekBarChangeListener() {
			public void onStartTrackingTouch(SeekBar bar) {
				WhatyMediaPlayerCommonFragment.this.showMediaController();
				WhatyMediaPlayerCommonFragment.this.mDragging = true;
				WhatyMediaPlayerCommonFragment.this.mHandler.removeMessages(2);
				if (WhatyMediaPlayerCommonFragment.this.mSeekInfo != null)
					WhatyMediaPlayerCommonFragment.this.mSeekInfo.setVisibility(0);
			}

			public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
				if (fromuser)
					WhatyMediaPlayerCommonFragment.this.showMediaController();
				long duration = mPlayerView.getDuration();
				long newposition = duration * bar.getProgress() / 1000L;
				if (WhatyMediaPlayerCommonFragment.this.mCurrentTime != null)
					WhatyMediaPlayerCommonFragment.this.mCurrentTime.setText(WhatyMediaPlayerCommonFragment.this.stringForTime((int) newposition));
				if (WhatyMediaPlayerCommonFragment.this.mSeekInfo != null)
					WhatyMediaPlayerCommonFragment.this.mSeekInfo.setText(WhatyMediaPlayerCommonFragment.this.stringForTime((int) newposition));
			}

			public void onStopTrackingTouch(SeekBar bar) {
				WhatyMediaPlayerCommonFragment.this.mDragging = false;
				long duration = mPlayerView.getDuration();
				long newposition = duration * bar.getProgress() / 1000L;
				WhatyMediaPlayerCommonFragment.this.mLoadingView.setVisibility(View.VISIBLE);
				seekTo((int) newposition);
				if (mSeekToHandler != null) {
					mSeekToHandler.afterSeekTo((int) newposition);
				}
				if (bindVideo != null) {
					bindVideo.seekTo((int) newposition);
				}
				if (WhatyMediaPlayerCommonFragment.this.mSeekInfo != null)
					WhatyMediaPlayerCommonFragment.this.mSeekInfo.setVisibility(8);
			}
		};
		
		if (this.mMediaControllerView != null) {
			this.mMediaControllerView.setOnTouchListener(new View.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					WhatyMediaPlayerCommonFragment.this.showMediaController();
					return true;
				}
			});
		}

		// 暂停事件的处理
		if (this.mPlayPauseButton != null) {
			this.mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (mPlayerView.isPlaying())
						mPlayerView.pause();
					else {
						mPlayerView.start();
						mBackGroundView.setVisibility(View.GONE);
					}

					if (bindVideo != null) {
						if (bindVideo.getVideoView().isPlaying())
							bindVideo.getVideoView().pause();
						else {
							bindVideo.getVideoView().start();
							bindVideo.mBackGroundView.setVisibility(View.GONE);
						}
					}

					WhatyMediaPlayerCommonFragment.this.showMediaController();
				}
			});

		}

		// 进度条事件处理
		if (this.mProgress != null) {
			if ((this.mProgress instanceof SeekBar)) {
				SeekBar seeker = (SeekBar) this.mProgress;
				seeker.setOnSeekBarChangeListener(this.mSeekListener);
			}
			this.mProgress.setMax(1000);
		}

		// 全屏事件处理
		if (this.mFullScreen != null)
			this.mFullScreen.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// 切换横竖屏幕
					if (isFullScreen) {
						getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					} else {
						getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					}
					if (WhatyMediaPlayerCommonFragment.this.bindVideo != null) {
						WhatyMediaPlayerCommonFragment.this.bindVideo.toggleFullScreen();
					}
					toggleFullScreen();
				}
			});

		this.mPlayerView.setPlayerViewListener(playerViewListener);
	}
}