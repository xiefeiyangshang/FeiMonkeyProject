package com.whatyplugin.imooc.ui.SFPscreen;

import android.os.Bundle;
import cn.com.whatyplugin.mooc.R;

/**
 * 没有缩略图的三分屏播放
 * @author whaty
 *
 */
public class MCSFPScreenNoThumbActivity extends MCSFPScreenBaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setMyContentView(R.layout.sfp_no_thumb_activity_layout);
		
		//设置视频尺寸
		videoSizeMap.put(R.id.fm_video_screen, 0.0);
		videoSizeMap.put(R.id.fm_video_ppt, 0.0);
		super.onCreate(savedInstanceState);
	}

}
