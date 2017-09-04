package com.whatyplugin.imooc.ui.download;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import cn.com.whatyplugin.mooc.R;

import com.whaty.media.WhatyMediaPlayerMP4Fragment;
import com.whaty.media.WhatyMediaPlayerMP4Fragment.FullScreenHandler;
import com.whatyplugin.base.define.MCBaseDefine.MCMediaType;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.model.MCLearnOfflineRecord;
import com.whatyplugin.imooc.logic.proxy.MCResourceProxy;
import com.whatyplugin.imooc.logic.utils.FileUtils;
import com.whatyplugin.imooc.logic.whatydb.MCDBOpenHelper;
import com.whatyplugin.imooc.logic.whatydb.dao.base.OfflineDao;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.showmooc.ShowMoocCommon;
import com.whatyplugin.uikit.resolution.MCResolution;

public class OfflinePlayerActivity extends MCBaseActivity implements FullScreenHandler {
    private String url;

    private WhatyMediaPlayerMP4Fragment fm_video_screen;
    private int currentPosition;//当前位置
    private String courseId;
    private String userId;
    private String sectionId;
    private OfflineDao dao = new OfflineDao();
    List<MCLearnOfflineRecord> listT = new ArrayList<MCLearnOfflineRecord>();

    public OfflinePlayerActivity() {
        super();
        this.url = "";
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.offline_player_layout);

        this.fm_video_screen = (WhatyMediaPlayerMP4Fragment) getFragmentManager().findFragmentById(R.id.fm_video_screen);

        String fileName = this.getIntent().getStringExtra("filename");
        this.url = FileUtils.getVideoFullPath(fileName);
        MCResourceProxy.getInstance().unlock(this.url);
        Intent in = this.getIntent();
        this.userId = in.getStringExtra("userid");
        this.courseId = in.getStringExtra("courseid");
        this.sectionId = in.getStringExtra("sectionid");
        this.fm_video_screen.setFullScreenHandler(this);
        //进来直接设置为全屏
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);//原来这样写有的手机行，有的手机不行
        currentPosition = 0;
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.fm_video_screen.toggleFullScreen();
        this.fm_video_screen.setVideoPath(this.url);
        this.fm_video_screen.start();
        try {
            listT = dao.queryByCondition(true, null, MCDBOpenHelper.TABLE_OFFLINE_ID + "=?", new String[]{sectionId},
                    null, null, null, null);
            if (listT.size() > 0) {
                MCLearnOfflineRecord record = listT.get(0);
                MCLog.d("studyTime", record.getStudyTime());
                currentPosition = Integer.parseInt(record.getRecordTime());
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void afterToggleFullScreen(WhatyMediaPlayerMP4Fragment videoFragment) {
        int devHeight = MCResolution.getInstance(this).getDevHeight(this);
        int devWidth = MCResolution.getInstance(this).getDevWidth(this);

        View view = videoFragment.getView();
        RelativeLayout.LayoutParams videoParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

        if (videoFragment.isFullScreen()) {
            videoParams.height = devHeight;
            videoParams.width = devWidth;
            view.setLayoutParams(videoParams);
            view.invalidate();// videoview重绘一下， 不然可能没有及时更新
        } else {
            MCResourceProxy.getInstance().lock(this.url);
            this.finish();//从全屏退出就直接退出了
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MCResourceProxy.getInstance().lock(this.url);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        fm_video_screen.start();
        fm_video_screen.seekTo(currentPosition);
        ShowMoocCommon.startRecordTimer(sectionId, "", MCMediaType.MC_VIDEO_TYPE, this);
        ShowMoocCommon.updateTimeInfo(sectionId, courseId, this.fm_video_screen.getVideoView());
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (fm_video_screen != null && fm_video_screen.isPlaying()) {
            fm_video_screen.pause();
        }
        ShowMoocCommon.stopRecordTimer(sectionId);// 停止记录
        Handler handler = new Handler();
        currentPosition = fm_video_screen.getCurrentPosition();
        final int totalTime = fm_video_screen.getVideoView().getDuration() / 1000;
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                MCLearnOfflineRecord record = new MCLearnOfflineRecord();
                record.setRecordTime(currentPosition + "");
                record.setCourseId(courseId);
                record.setId(sectionId);
                record.setUserId(userId);
                record.setTotalTime(String.valueOf(totalTime));
                record.setType(0);
                if (!ShowMoocCommon.flag) {
                    record.setStudyTime(ShowMoocCommon.studyTime);
                } else {
                    record.setStudyTime("0");
                }
                listT.clear();
                listT.add(record);
                dao.insertOffline(listT, sectionId, ShowMoocCommon.flag);
            }
        }, 1000);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        this.fm_video_screen.release();
        super.onDestroy();
    }
}
