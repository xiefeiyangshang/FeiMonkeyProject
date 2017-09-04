package com.whaty.media;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.whaty.mediaplayer.R;
import com.whaty.mediaplayer.WhatyMediaPlayer;
import com.whaty.mediaplayer.WhatyMediaPlayerFragment;
import com.whaty.mediaplayer.WhatyMediaPlayerJSONFragment;

/**
 * 用此view封装2个不同播放器， 根据不同文件格式播放不同视频
 * @author 马彦君
 *
 */
public class WhatyVideoView extends RelativeLayout  implements WhatyMediaPlayerFragment.FullScreenHandler, WhatyMediaPlayerMP4Fragment.FullScreenHandler{

	private static final String TAG = "VideoPlayFragment";
	private WhatyMediaPlayer player = null;
	private RelativeLayout mRootView;
	private WhatyMediaPlayerJSONFragment json_fragment;
	private WhatyMediaPlayerMP4Fragment mp4_fragment;
	private Activity parentActivity;
	private String currentUrl;
	private static final int MP4_TYPE = 1;
	private static final int JSON_TYPE = 2;
	private static final int OTHER_TYPE = 3;
	private Context mContext;
	private View surfaceView;
	private FullScreenCallBack fullScreenCallBack;

	public WhatyVideoView(Context context) {
		super(context);
		initView(context);

	}

	public WhatyVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public WhatyVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public void initView(Context context){
		this.mContext = context;
		if (this.mRootView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.mRootView = (RelativeLayout) inflater.inflate(R.layout.video_control_layout, this);
		}
	}

	/**
	 * 通过传入的activity来进行fragment初始化
	 * @param activity
	 */
	public void initWithActivity(Activity activity) {
		this.parentActivity = activity;
		this.json_fragment = (WhatyMediaPlayerJSONFragment) parentActivity.getFragmentManager().findFragmentById(R.id.json_fragment);
		this.mp4_fragment = (WhatyMediaPlayerMP4Fragment) parentActivity.getFragmentManager().findFragmentById(R.id.mp4_fragment);
		this.player = this.json_fragment.getMediaPlayer();
		this.surfaceView = this.json_fragment.getView().findViewById(R.id.surfaceView);

		this.json_fragment.setFullScreenHandler(this);
		this.mp4_fragment.setFullScreenHandler(this);

	}

	/**
	 * 设置背景
	 *
	 */
	public void setBackGroup(Bitmap bm) {
		json_fragment.setBackGroupImageView(bm);
		mp4_fragment.setBackGroup(bm);
	}

	/**
	 * 根据地址格式选择合适播放器进行播放
	 * @param url
	 */
	public void setMediaUrl(String url) {
		if (TextUtils.isEmpty(url)) {
			return;
		}

		// 把原来的停掉
//		this.mp4_fragment.stop();
//		this.player.stop();
		this.currentUrl = url;

		int mediaType = checkUrlType();
		if (mediaType == MP4_TYPE) {
			this.json_fragment.getView().setVisibility(View.GONE);
			this.surfaceView.setVisibility(View.GONE);
			this.mp4_fragment.getView().setVisibility(View.VISIBLE);
			this.mp4_fragment.setVideoPath(url);
			this.mp4_fragment.start();
		} else if (mediaType == JSON_TYPE) {
			this.mp4_fragment.getView().setVisibility(View.GONE);
			this.json_fragment.getView().setVisibility(View.VISIBLE);
			this.surfaceView.setVisibility(View.VISIBLE);
			if (this.player != null) {
				this.player.setDataSource(url);
				this.player.prepareAsync();
			}
		} else {
			Log.e(TAG, "播放路径不符合条件：" + url);
		}
	}

	/**
	 * 根据链接判断类型
	 *
	 * @param url
	 * @return
	 */
	private int checkUrlType() {
		if(TextUtils.isEmpty(currentUrl)){
			return OTHER_TYPE;
		}
		if (currentUrl.endsWith(".mp4")) {
			return MP4_TYPE;
		} else if (currentUrl.endsWith(".json")) {
			return JSON_TYPE;
		} else {
			return OTHER_TYPE;
		}
	}

	/**
	 * 释放资源
	 */
	public void release() {
		if (this.mp4_fragment != null) {
			this.mp4_fragment.release();
		}

		if (this.player != null) {
			this.player.release();
		}
	}

	public void seekTo() {
		mp4_fragment.seekTo(0);
	}

	public void cancleWhenNoWifi() {

	}

	public int getCurrentDuratoin() {
		return 0;
	}

	public boolean isPlayedEndTime() {
		return false;
	}

	public void setLoadingBeforePlay() {

	}

	public void setUserVisibleHint(boolean is){

	}

	public boolean isPlaying(){
		int mediaType = checkUrlType();
		if (mediaType == MP4_TYPE) {
			return mp4_fragment.isPlaying();
		} else if (mediaType == JSON_TYPE) {
			return this.player.isPlaying();
		}else{
			return false;
		}
	}


	public int getCurrentPosition() {
		return  mp4_fragment.getCurrentPosition();
	}

	public void pause() {
		int mediaType = checkUrlType();
		if (mediaType == MP4_TYPE) {
			this.mp4_fragment.pause();
		} else if (mediaType == JSON_TYPE) {
			this.player.pause();
		}
	}

	public void pauseLoading() {
		int mediaType = checkUrlType();
		if (mediaType == MP4_TYPE) {
		} else if (mediaType == JSON_TYPE) {
			this.player.pauseLoading();
		}
	}

	public boolean isLoadingPaused() {
		return this.player.isLoadingPaused();
	}

	public void resumeLoading() {
		int mediaType = checkUrlType();
		if (mediaType == MP4_TYPE) {
		} else if (mediaType == JSON_TYPE) {
			this.player.resumeLoading();
		}
	}

	public void start(int CurrentPosition) {
		int mediaType = checkUrlType();
		if (mediaType == MP4_TYPE) {
			this.mp4_fragment.start();
			this.mp4_fragment.seekTo(CurrentPosition);
		} else if (mediaType == JSON_TYPE) {
			this.player.start();
		}
	}

	public void stop() {
		int mediaType = checkUrlType();
		if (mediaType == MP4_TYPE) {
			this.mp4_fragment.stop();
		} else if (mediaType == JSON_TYPE) {
			this.player.stop();
		}
	}

	public Object getPlayer(){
		int mediaType = checkUrlType();
		if (mediaType == MP4_TYPE) {
			return this.mp4_fragment.getVideoView();
		} else if (mediaType == JSON_TYPE) {
			return this.player;
		}else{
			return null;
		}
	}

	/**
	 * 是否全屏的判断
	 */
	public boolean isFullScreen() {
		return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	/**
	 * 金明理播放器的全屏控制
	 */
	public void toggleFullScreen() {

		//更新全屏后的按钮及视图
		this.json_fragment.updateControlBeforeToggle();

		if (isFullScreen()) {
			parentActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			parentActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} else {
			parentActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			parentActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}

		if (fullScreenCallBack != null) {
			fullScreenCallBack.adjustVideoView();
		}
	}

	/**
	 * mp4理播放器的全屏控制
	 */
	@Override
	public void afterToggleFullScreen(WhatyMediaPlayerMP4Fragment videoFragment) {
		if (fullScreenCallBack != null) {
			fullScreenCallBack.adjustVideoView();
		}
	}

	/**
	 * mp4理播放器的全屏控制赋值
	 */
	public void setFullScreenCallBack(FullScreenCallBack callBack){
		this.fullScreenCallBack = callBack;
	}

	/**
	 * 横竖屏的处理, 2中播放器不同的处理方式
	 */
	public void onConfigurationChanged() {

		if (this.json_fragment.getView().getVisibility() == View.VISIBLE) {
			if (isFullScreen()) {
				parentActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			} else {
				this.json_fragment.updateControlQuitFullScreen();
				parentActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			}
			if (fullScreenCallBack != null) {
				fullScreenCallBack.adjustVideoView();
			}

		}

		if (this.mp4_fragment.getView().getVisibility() == View.VISIBLE) {
			if (fullScreenCallBack != null) {
				fullScreenCallBack.adjustVideoView();
			}
			this.mp4_fragment.updateControlView(isFullScreen());
		}
	}


	/**
	 * 全屏之后的回调
	 * @author 马彦君
	 *
	 */
	public static abstract interface FullScreenCallBack {

		public abstract void adjustVideoView();
	}
}
