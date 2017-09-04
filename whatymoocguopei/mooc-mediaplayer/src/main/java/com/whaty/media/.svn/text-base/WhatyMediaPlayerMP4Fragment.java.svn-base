package com.whaty.media;

import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.whaty.mediaplayer.R;


public class WhatyMediaPlayerMP4Fragment extends Fragment {
	private static final int UPDATE_DOWNLOAD_SPEED = 1;
	private static final int UPDATE_PROGRESS = 2;
	private static final int FADE_OUT = 3;
	private static final int sDefaultTimeout = 3000;
	private static final int ViewScrollRatio = 200;
	private static final String CLEAR_URL = "CLEAR_URL"; //清除播放器URL
	private MessageHandler mHandler;
	private FrameLayout mRootView;
	private VideoView mSurfaceView;
	private View mLoadingView;
	private TextView mDownloadSpeed;
	private TextView mNetworkInfo;
	private View mBackGroundView=null;
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
	private ControllerViewHideShowListener mControllerViewHideShowHandler;
	private int mOldVideoWidth;
	private int mOldVideoHeight;
	private int mOldVideoContainerWidth;
	private int mOldVideoContainerHeight;
	private boolean isFullScreen;
	private boolean isControlShow = true;//控制栏是否要显示， 默认显示
	private boolean isShowNextButton = false;//是否显示播放一下
	private boolean isCanDrag = false;//视频是否能拖动，能拖动的话就不以拖动来控制进度条了
	private int parentViewId;
	private WhatyMediaPlayerMP4Fragment bindVideo;
	private boolean isStart = false;  //是否点击了播放键，引起的请求

	public WhatyMediaPlayerMP4Fragment() {
		this.mPlayWhenReady = true;

		this.mOnlyUseWifi = true;
		this.mSeekListener = new SeekBar.OnSeekBarChangeListener() {
			public void onStartTrackingTouch(SeekBar bar) {
				WhatyMediaPlayerMP4Fragment.this.showMediaController();
				WhatyMediaPlayerMP4Fragment.this.mDragging = true;
				WhatyMediaPlayerMP4Fragment.this.mHandler.removeMessages(2);
				if (WhatyMediaPlayerMP4Fragment.this.mSeekInfo != null)
					WhatyMediaPlayerMP4Fragment.this.mSeekInfo.setVisibility(0);
			}

			public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
				if (fromuser)
					WhatyMediaPlayerMP4Fragment.this.showMediaController();
				long duration = mSurfaceView.getDuration();
				long newposition = duration * bar.getProgress() / 1000L;
				if (WhatyMediaPlayerMP4Fragment.this.mCurrentTime != null)
					WhatyMediaPlayerMP4Fragment.this.mCurrentTime.setText(WhatyMediaPlayerMP4Fragment.this.stringForTime((int) newposition));
				if (WhatyMediaPlayerMP4Fragment.this.mSeekInfo != null)
					WhatyMediaPlayerMP4Fragment.this.mSeekInfo.setText(WhatyMediaPlayerMP4Fragment.this.stringForTime((int) newposition));
			}

			public void onStopTrackingTouch(SeekBar bar) {
				WhatyMediaPlayerMP4Fragment.this.mDragging = false;
				long duration = mSurfaceView.getDuration();
				long newposition = duration * bar.getProgress() / 1000L;
				WhatyMediaPlayerMP4Fragment.this.mLoadingView.setVisibility(View.VISIBLE);
				seekTo((int) newposition);
				if(mSeekToHandler!=null){
					mSeekToHandler.afterSeekTo((int) newposition);
				}
				if(bindVideo!=null){
					bindVideo.seekTo((int) newposition);
				}
				if (WhatyMediaPlayerMP4Fragment.this.mSeekInfo != null)
					WhatyMediaPlayerMP4Fragment.this.mSeekInfo.setVisibility(8);
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
		this.mHandler = new MessageHandler(this);
		this.mFormatBuilder = new StringBuilder();
		this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
		IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
	}

	public void onDestroy() {
		super.onDestroy();
	}

	//获取当前播放的位置
	public int getCurrentPosition(){
		return mSurfaceView.getCurrentPosition();
	}

	public boolean isPlaying(){
		return  mSurfaceView.isPlaying();
	}


	public void setFullScreenHandler(FullScreenHandler fullScreenHandler) {
		this.mFullScreenHandler = fullScreenHandler;
	}

	public void setSeekToHandler(SeekToHandler seekToHandler) {
		this.mSeekToHandler = seekToHandler;
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
		this.mSurfaceView = (VideoView) this.mRootView.findViewById(R.id.surfaceView);

		this.mLoadingView = this.mRootView.findViewById(R.id.loadingView);
		this.mDownloadSpeed = ((TextView) this.mRootView.findViewById(R.id.download_speed));
		this.mNetworkInfo = ((TextView) this.mRootView.findViewById(R.id.network_info));
		this.mBackGroundView = this.mRootView.findViewById(R.id.backgroundView);
		this.mMediaControllerView = this.mRootView.findViewById(R.id.mediacontroller);
		this.mEndTime = ((TextView) this.mRootView.findViewById(R.id.mediacontroller_time_total));
		this.mCurrentTime = ((TextView) this.mRootView.findViewById(R.id.mediacontroller_time_current));
		this.mPlayPauseButton = ((ImageButton) this.mRootView.findViewById(R.id.mediacontroller_play_pause));
		this.mProgress = ((ProgressBar) this.mRootView.findViewById(R.id.mediacontroller_seekbar));
		this.mFullScreen = ((ImageButton) this.mRootView.findViewById(R.id.mediacontroller_fullscreen));
		this.mSeekInfo = ((TextView) this.mRootView.findViewById(R.id.seekInfo));
		this.mPlayNext = (ImageButton) this.mRootView.findViewById(R.id.mediacontroller_play_next);

		//整个fragment的滑动事件处理
		this.mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
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
				if(isCanDrag){//拖动视频位置的处理
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
							if (Math.abs(_x - X) > 20 || Math.abs(_y - Y) > 20){
								RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRootView
										.getLayoutParams();
								layoutParams.leftMargin = X - _xDelta;
								layoutParams.topMargin = Y - _yDelta;
								layoutParams.rightMargin = 0-layoutParams.width;
								layoutParams.bottomMargin = 0-layoutParams.height;// -(landH);
								mRootView.setLayoutParams(layoutParams);
							}
							break;
					}
					mRootView.invalidate();
					return true;
				}else{//滑动调整视频进度的处理
					if(!isControlShow){//不显示控制栏，不能拖动的情况下就对touch事件进行处理 了。
						return true;
					}
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							if ((WhatyMediaPlayerMP4Fragment.this.mMediaControllerView != null) && (mSurfaceView != null)) {
								if (WhatyMediaPlayerMP4Fragment.this.mMediaControllerView.getVisibility() == 0)
									WhatyMediaPlayerMP4Fragment.this.hideMediaController();
								else {
									WhatyMediaPlayerMP4Fragment.this.showMediaController();
								}
							}

							this.startX = (event.getX() * 200.0F);
							this.startY = (event.getY() * 200.0F);
							this.start_pos = mSurfaceView.getCurrentPosition();
							this.x_scroll_start = false;
							break;
						case MotionEvent.ACTION_MOVE:
							this.stopX = (event.getX() * 200.0F);
							this.stopY = (event.getY() * 200.0F);
							if ((!this.x_scroll_start) && (Math.abs(this.stopX - this.startX) >= 3000.0F)
									&& (Math.abs(this.stopX - this.startX) > Math.abs(this.stopY - this.startY))
									&& (mSurfaceView != null)) {
								this.x_scroll_start = true;
							}
							if (this.x_scroll_start) {
								this.new_pos = (long) (Math.min((float) mSurfaceView.getDuration(),
										Math.max(0.0F, (float) this.start_pos + this.stopX - this.startX)));
								WhatyMediaPlayerMP4Fragment.this.mSeekInfo.setVisibility(0);
								WhatyMediaPlayerMP4Fragment.this.mSeekInfo.setText(WhatyMediaPlayerMP4Fragment.this.stringForTime((int) this.new_pos));
								WhatyMediaPlayerMP4Fragment.this.showMediaController();
							}

							break;
						case MotionEvent.ACTION_UP:
							this.stopX = (event.getX() * 200.0F);
							this.stopY = (event.getY() * 200.0F);
							if ((this.x_scroll_start) && (Math.abs(this.stopX - this.startX) >= 1000.0F)) {
								this.new_pos = (long) (Math.min((float) mSurfaceView.getDuration(),
										Math.max(0.0F, (float) this.start_pos + this.stopX - this.startX)));
								WhatyMediaPlayerMP4Fragment.this.mLoadingView.setVisibility(View.VISIBLE);

								seekTo((int) this.new_pos);

								if(bindVideo!=null){
									bindVideo.seekTo((int) this.new_pos);
								}

								if(mSeekToHandler!=null){
									mSeekToHandler.afterSeekTo((int) this.new_pos);
								}
							}
							if (WhatyMediaPlayerMP4Fragment.this.mSeekInfo != null)
								WhatyMediaPlayerMP4Fragment.this.mSeekInfo.setVisibility(8);
							break;
					}
				}
				return true;
			}
		});
		if (this.mMediaControllerView != null) {
			this.mMediaControllerView.setOnTouchListener(new View.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					WhatyMediaPlayerMP4Fragment.this.showMediaController();
					return true;
				}
			});
		}

		//暂停事件的处理
		if (this.mPlayPauseButton != null) {
			this.mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (mSurfaceView.isPlaying())
						mSurfaceView.pause();
					else {
						mSurfaceView.start();
						mBackGroundView.setVisibility(View.GONE);
					}

					if(bindVideo!=null){
						if (bindVideo.getVideoView().isPlaying())
							bindVideo.getVideoView().pause();
						else {
							bindVideo.getVideoView().start();
							bindVideo.mBackGroundView.setVisibility(View.GONE);
						}
					}

					WhatyMediaPlayerMP4Fragment.this.showMediaController();
				}
			});
		}

		//进度条事件处理
		if (this.mProgress != null) {
			if ((this.mProgress instanceof SeekBar)) {
				SeekBar seeker = (SeekBar) this.mProgress;
				seeker.setOnSeekBarChangeListener(this.mSeekListener);
			}
			this.mProgress.setMax(1000);
		}

		//全屏事件处理
		if (this.mFullScreen != null)
			this.mFullScreen.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					//切换横竖屏幕
					if (isFullScreen) {
						getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					} else {
						getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					}
					if(WhatyMediaPlayerMP4Fragment.this.bindVideo != null){
						WhatyMediaPlayerMP4Fragment.this.bindVideo.toggleFullScreen();
					}
					toggleFullScreen();
				}
			});

		this.mSurfaceView.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
			@Override
			public void onPrepared(MediaPlayer mp) {
				//加载完成去掉
				WhatyMediaPlayerMP4Fragment.this.mLoadingView.setVisibility(View.GONE);
				WhatyMediaPlayerMP4Fragment.this.mBackGroundView.setVisibility(View.INVISIBLE);
				WhatyMediaPlayerMP4Fragment.this.mPlayPauseButton.getBackground().setLevel(1);
				mp.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

					@Override
					public void onBufferingUpdate(MediaPlayer mp, int percent) {
//						if(mBufferingUpdateHandler!=null){
//							mBufferingUpdateHandler.afterBufferingUpdate(mSurfaceView.getCurrentPosition());
//						}
					}
				});

				mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {

					@Override
					public void onSeekComplete(MediaPlayer mp) {
						//加载完成去掉
						WhatyMediaPlayerMP4Fragment.this.mLoadingView.setVisibility(View.GONE);
					}
				});

			}
		});






		this.mSurfaceView.setOnErrorListener(new OnErrorListener(){
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				if (isStart) {
					Toast.makeText(WhatyMediaPlayerMP4Fragment.this.getActivity(), "该视频不能播放！", 0).show();
				}else{
					mProgress.setEnabled(false);
					mPlayPauseButton.setEnabled(false);
				}
				WhatyMediaPlayerMP4Fragment.this.mLoadingView.setVisibility(View.GONE);
				WhatyMediaPlayerMP4Fragment.this.mBackGroundView.setVisibility(View.VISIBLE);
				// return true之后就不会弹出来默认的此视频不能播放的框框了.
				return true;
			}
		});

		this.mSurfaceView.setOnCompletionListener(new OnCompletionListener(){

			@Override
			public void onCompletion(MediaPlayer mp) {
				//播放完成的时候显示默认背景图片
				WhatyMediaPlayerMP4Fragment.this.mBackGroundView.setVisibility(View.VISIBLE);
			}

		});
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.mRootView = ((FrameLayout) inflater.inflate(R.layout.whaty_video_player_mp4_fragment, container, false));
		initViews();
		return this.mRootView;
	}


	/**
	 * 全屏和非全屏控件大小不一致， 用这个方法来更新这些控件
	 */
	public void updateControlView(boolean isFull) {

		int level = this.mPlayPauseButton.getBackground().getLevel();

		ViewGroup.LayoutParams params = this.mMediaControllerView.getLayoutParams();
		if(isFull){
			this.mPlayPauseButton.setBackgroundResource(R.drawable.whaty_mediaplayer_play_big_level);
			this.mFullScreen.setImageResource(R.drawable.whaty_mediaplayer_fullscreen_big);
			params.height = (int) this.getActivity().getResources().getDimension(R.dimen.player_60_dp) ;
			if(isShowNextButton){//设置了播放一下个，这里要给以显示
				this.mPlayNext.setVisibility(View.VISIBLE);
			}
		}else{
			this.mPlayPauseButton.setBackgroundResource(R.drawable.whaty_mediaplayer_play_small_level);
			this.mFullScreen.setImageResource(R.drawable.whaty_mediaplayer_fullscreen);
			params.height = (int) this.getActivity().getResources().getDimension(R.dimen.player_30_dp) ;
			this.mPlayNext.setVisibility(View.GONE);
		}
		this.mMediaControllerView.setLayoutParams(params);
		this.mPlayPauseButton.getBackground().setLevel(level);
	}

	public void updatePausePlay() {
		if (mSurfaceView.isPlaying())
			this.mPlayPauseButton.getBackground().setLevel(1);
		else
			this.mPlayPauseButton.getBackground().setLevel(0);
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
		if ((mSurfaceView == null) || (this.mDragging)) {
			return 0L;
		}

		long position = mSurfaceView.getCurrentPosition();
		long duration = mSurfaceView.getDuration();
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
		if(!this.isControlShow){
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

	public void adjustVideoSize() {
		if ((this.mRootView == null) || (mSurfaceView == null) || (this.mSurfaceView == null))
			return;
		if ((this.mOldVideoHeight == mSurfaceView.getHeight()) && (this.mOldVideoWidth == mSurfaceView.getWidth())
				&& (this.mOldVideoContainerHeight == this.mRootView.getHeight()) && (this.mOldVideoContainerWidth == this.mRootView.getWidth()))
			return;

		this.mOldVideoContainerWidth = this.mRootView.getWidth();
		this.mOldVideoContainerHeight = this.mRootView.getHeight();
		this.mOldVideoWidth = mSurfaceView.getWidth();
		this.mOldVideoHeight = mSurfaceView.getHeight();

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

	void toggleBackgroundView() {
		if (this.mBackGroundView.getVisibility() == View.VISIBLE) {
			this.mBackGroundView.setVisibility(View.INVISIBLE);
		} else {
			this.mBackGroundView.setVisibility(View.VISIBLE);
		}
	}

	private class MessageHandler extends Handler {
		private final WeakReference<WhatyMediaPlayerMP4Fragment> video_fragment_ref;

		MessageHandler(WhatyMediaPlayerMP4Fragment video_fragment) {
			this.video_fragment_ref = new WeakReference(video_fragment);
		}

		public void handleMessage(Message msg) {
			WhatyMediaPlayerMP4Fragment video_fragment = (WhatyMediaPlayerMP4Fragment) this.video_fragment_ref.get();
			if (this.video_fragment_ref.get() == null) {
				return;
			}

			switch (msg.what) {
				case 3:
					video_fragment.hideMediaController();
					break;
				case 2:
					long pos = video_fragment.setProgress();

					if(!video_fragment.mDragging && mBufferingUpdateHandler!=null){
						mBufferingUpdateHandler.afterBufferingUpdate((int)  pos);
						sendMessageDelayed(obtainMessage(2), 1000L);
					}else if(!video_fragment.mDragging	&& (video_fragment.mMediaControllerView.getVisibility() == 0)){
						sendMessageDelayed(obtainMessage(2), 1000L - pos % 1000L);
					}

					break;
				case 1:
					if (video_fragment.mDownloadSpeed != null) {
						video_fragment.mDownloadSpeed.setText("正在加载...");
					}
					if ((video_fragment.mDownloadSpeed != null)
							&& (video_fragment.mLoadingView != null)
							&& (video_fragment.mLoadingView.getVisibility() == 0)
							&& (video_fragment.mSurfaceView != null))
						sendEmptyMessageDelayed(1, 1000L);
					else
						removeMessages(1);
					break;
			}
		}
	}

	public boolean isFullScreen(){
		return isFullScreen;
	}

	public void setFullScreen(boolean isFullScreen){
		this.isFullScreen = isFullScreen;
	}

	public void setVideoPath(String url){
		mPlayPauseButton.setEnabled(true);
		this.mSurfaceView.setVideoPath(url);
	}

	public void seekTo(int msec){
		WhatyMediaPlayerMP4Fragment.this.mLoadingView.setVisibility(View.VISIBLE);
		this.mSurfaceView.seekTo(msec);
	}

	public void pause(){
		this.mSurfaceView.pause();
	}

	public void start(){
		//去掉背景图片， 显示进度条
		mProgress.setEnabled(true);
		isStart =true;
		this.mLoadingView.setVisibility(View.VISIBLE);
		this.mSurfaceView.start();
	}

	public void stop(){
		//显示背景图片，去掉进度条
		isStart =false;  //停止之后，结束网络请求返回事件
		this.mSurfaceView.stopPlayback();
		this.mSurfaceView.setVideoPath(CLEAR_URL);
		this.mBackGroundView.setVisibility(View.VISIBLE);
		this.mLoadingView.setVisibility(View.GONE);

	}
	public void setBackGroup(final Bitmap bm){
		mBackGroundView.post(new Runnable() {

			@Override
			public void run() {
				((ImageView)mBackGroundView).setImageBitmap(bm);

			}
		});

	}

	public void release(){
		this.mHandler.removeMessages(2);
		this.mHandler.removeMessages(3);
		this.mSurfaceView.stopPlayback();
	}

	public VideoView getVideoView(){
		return this.mSurfaceView;
	}

	/**
	 * 手动点击引起的屏幕全屏变化
	 */
	public void toggleFullScreen(){

		this.isFullScreen = !this.isFullScreen;

		if (this.mFullScreenHandler != null) {
			this.mFullScreenHandler.afterToggleFullScreen(this);//切换之后要做的事情
		}
		updateControlView(this.isFullScreen);
	}

	public boolean isControlShow() {
		return isControlShow;
	}

	public void setControlShow(boolean isControlShow) {
		this.isControlShow = isControlShow;
	}

	public WhatyMediaPlayerMP4Fragment getBindVideo() {
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
	 * @param otherVideo
	 */
	public void setBindVideo(WhatyMediaPlayerMP4Fragment bindVideo) {
		this.bindVideo = bindVideo;
	}

	public ImageButton getPlayNext() {
		return mPlayNext;
	}

	public void setOnBufferingUpdate(BufferingUpdateHandler bufferingUpdateHandler) {
		this.mBufferingUpdateHandler = bufferingUpdateHandler;
	}

	public void setShowNextButton(boolean isShowNextButton) {
		this.isShowNextButton = isShowNextButton;
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
	 * @author 马彦君
	 *
	 */
	public static abstract interface FullScreenHandler {

		public abstract void afterToggleFullScreen(WhatyMediaPlayerMP4Fragment videoFragment);
	}

	/**
	 * 切换到某个进度条的回调
	 * @author 马彦君
	 *
	 */
	public static abstract interface SeekToHandler {

		public abstract void afterSeekTo(int position);
	}

	/**
	 * 进度条更新后的回调
	 * @author 马彦君
	 *
	 */
	public static abstract interface BufferingUpdateHandler {

		public abstract void afterBufferingUpdate(int position);
	}

}