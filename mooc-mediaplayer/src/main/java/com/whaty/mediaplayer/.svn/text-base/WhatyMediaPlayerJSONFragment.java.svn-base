package com.whaty.mediaplayer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WhatyMediaPlayerJSONFragment extends WhatyMediaPlayerFragment {

	TextView mDownloadHint;
	private Button mBt_changRate;
	private ImageView im_backGroup;

	@Override
	@SuppressLint("WrongViewCast")
	public void initViews() {
		// TODO Auto-generated method stub
		super.initViews();
		mDownloadHint = (TextView) this.mRootView.findViewById(R.id.download_hint);
		mBt_changRate = (Button) this.mRootView.findViewById(R.id.chang_playback_rate);
		im_backGroup = (ImageView) this.mRootView.findViewById(R.id.backgroundView);
		ChangeRate();
	}
	/**
	 * 切换播放方式
	 *
	 */
	private void ChangeRate(){
		mBt_changRate.setText("1.0X");
		mBt_changRate.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
				builder.setTitle(null);
				final double[] rates={0.25,0.5,1.0,1.5,2.0,3.0,4.0};
				final String[] items=new String[rates.length];
				for(int i=0;i<rates.length;i++){
					items[i]=rates[i]+"X";
				}
				builder.setItems(items, new  DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mPlayer.setPlayBackRate(rates[which]);
						mBt_changRate.setText(items[which]);
					}
				});
				builder.show();
			}
		});
	}


	/**
	 *重写暂停、播放的视图切换
	 */
	public void updatePausePlay() {
		if ((this.mPlayPauseButton == null) || (this.mPlayer == null)) {
			return;
		}
		if (this.mPlayer.isPlaying()) {
			if (this.mFullScreenHandler.isFullScreen()) {
				this.mPlayPauseButton.setImageResource(R.drawable.whaty_mediaplayer_pause_big);
			} else {
				this.mPlayPauseButton.setImageResource(R.drawable.whaty_mediaplayer_pause);
			}
		} else {
			if (this.mFullScreenHandler.isFullScreen()) {
				this.mPlayPauseButton.setImageResource(R.drawable.whaty_mediaplayer_play_big);
			} else {
				this.mPlayPauseButton.setImageResource(R.drawable.whaty_mediaplayer_play);
			}
		}
	}

	/**
	 * 全屏后更新ui
	 */
	public void updateControlBeforeToggle() {
		ViewGroup.LayoutParams params = this.mMediaControllerView.getLayoutParams();
		if (this.mFullScreenHandler.isFullScreen()) {
			params.height = (int) this.getResources().getDimension(R.dimen.player_30_dp);

			if (this.mPlayer.isPlaying()) {
				this.mPlayPauseButton.setImageResource(R.drawable.whaty_mediaplayer_pause);
			} else {
				this.mPlayPauseButton.setImageResource(R.drawable.whaty_mediaplayer_play);
			}
		} else {
			params.height = (int) this.getResources().getDimension(R.dimen.player_60_dp);
			if (this.mPlayer.isPlaying()) {
				this.mPlayPauseButton.setImageResource(R.drawable.whaty_mediaplayer_pause_big);
			} else {
				this.mPlayPauseButton.setImageResource(R.drawable.whaty_mediaplayer_play_big);
			}
		}
		this.mMediaControllerView.setLayoutParams(params);
	}

	/**
	 *退出全屏幕
	 */
	public void updateControlQuitFullScreen() {
		ViewGroup.LayoutParams params = this.mMediaControllerView.getLayoutParams();
		params.height = (int) this.getResources().getDimension(R.dimen.player_30_dp);
		this.mMediaControllerView.setLayoutParams(params);
	}
	/**
	 * 设置背景
	 * @param bm
	 */
	public void setBackGroupImageView(final Bitmap bm){
		im_backGroup.post(new Runnable() {

			@Override
			public void run() {
				im_backGroup.setImageBitmap(bm);

			}
		});

	}


	@Override
	public void updateBufferingUI() {
		if (this.mDownloadSpeed != null) {
			if ((this.mPlayer != null) && (this.mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.Buffering))
				if (Build.VERSION.SDK_INT >= 16){
					this.mDownloadHint.setText("正在缓冲,请稍候");
					this.mDownloadSpeed.setText(getSpeedStr(this.mPlayer.getDownloadSpeed()));
				}
				else{
					this.mDownloadHint.setText("正在缓冲,请稍候");
				}
			if (this.mPlayer.getPlaybackState() == WhatyMediaPlayer.PlayerState.Preparing)
			{
				this.mDownloadHint.setText("正在加载,请稍候");
			}
		}
	}
}