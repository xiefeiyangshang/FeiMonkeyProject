package com.whatyplugin.imooc.ui.showmooc;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.whatyplugin.mooc.R;

import com.whaty.media.WhatyVideoView;
import com.whatyplugin.base.asyncimage.HttpBitMap;
import com.whatyplugin.base.define.MCBaseDefine.MCMediaType;
import com.whatyplugin.base.define.MCBaseDefine.MCUpgradeType;
import com.whatyplugin.base.download.MCDownloadQueue;
import com.whatyplugin.base.download.MCDownloadTask;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkDefine.MCNetworkStatus;
import com.whatyplugin.base.storage.MCUserDefaults;
import com.whatyplugin.base.utils.MCCourseConst;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCChapterAndSectionModel;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.proxy.MCLoginProxy;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseService;
import com.whatyplugin.imooc.logic.service_.MCCourseServiceInterface;
import com.whatyplugin.imooc.logic.upgrade.MCDownloadManager;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.logic.utils.FileUtils;
import com.whatyplugin.imooc.logic.utils.OpenFile;
import com.whatyplugin.imooc.ui.SFPscreen.MCSFPScreenActivity;
import com.whatyplugin.imooc.ui.SFPscreen.MCSFPScreenNoThumbActivity;
import com.whatyplugin.imooc.ui.adapter.MyFragmentAdapterNew;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.chapter.ChapterFragment;
import com.whatyplugin.imooc.ui.download.DownloadResourcesActivity;
import com.whatyplugin.imooc.ui.live.MCLiveOnLineActivity;
import com.whatyplugin.imooc.ui.mymooc.PicTxtWebviewActivity;
import com.whatyplugin.imooc.ui.pathmenu.MyAnimations;
import com.whatyplugin.imooc.ui.view.ChapterDownloadView;
import com.whatyplugin.imooc.ui.view.FullplaySectionsView;
import com.whatyplugin.imooc.ui.view.FullplaySectionsView.OnSectionListClickListener;
import com.whatyplugin.imooc.ui.view.MCSendTalkDialogFragment;
import com.whatyplugin.imooc.ui.view.MyComposerView;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.resolution.MCResolution;
import com.whatyplugin.uikit.toast.MCToast;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
public class ShowMoocActivity extends MCBaseActivity implements OnPageChangeListener, View.OnClickListener, WhatyVideoView.FullScreenCallBack {
    private static String FRAGMENTS_TAG;
    private ImageView back_img;
    private TextView chapter_tv;
    public static int clickCount;
    private TextView detail_tv;
    private ImageView download_img;
    private ImageView focus_img;
    private List fragmentList;
    public static boolean isFullByClick;
    private RelativeLayout layout;
    private MCAnalyzeBackBlock loginBackBlock;
    private ChapterFragment mChapterFragment;
    private Context mContext;
    private MCCourseModel mCourse;
    private CourseDetailFragment mCourseDetailFragment;
    private MCCourseServiceInterface mCourseService;
    private Handler mHandler;
    private BroadcastReceiver mNetworkListener;
    private SensorManager mSensorManager;
    private MCSectionModel mShouldPlaySection;
    private WhatyVideoView mVideoFragment;
    private ViewPager mViewPager;
    private ImageView note_img;
    private PopupWindow popupWindow;
    private RelativeLayout reload_layout;
    private int CurrentPosition;

    private ImageView share_img;
    private ImageView iv_focus_point;
    // 文字下方的红线
    private ImageView tab_chapter_img;
    private ImageView tab_detail_img;

    private String uid;
    private int viewId;
    private static final String TAG = ShowMoocActivity.class.getSimpleName();
    private RelativeLayout composerButtonsWrapper;
    private RelativeLayout composerButtonsShowHideButton;
    private ImageView composerButtonsShowHideButtonIcon;

    private Timer timer;

    private TextView titleText;
    private FrameLayout playerLayout;
    private boolean should_start_when_resume = false;
    private boolean should_resume_loading_when_resume = false;
    private int orgInfo;// 一进来的屏幕情况
    private String studentId;
    private MCSendTalkDialogFragment dialogFragment;
    // 设置 菜单字
    private MyComposerView cv_Notice;
    private MyComposerView cv_ThemeForum;
    private MyComposerView cv_Test;
    private MyComposerView cv_Homework;
    private MyComposerView cv_Question;
    private MyComposerView cv_Note;
    private MCCourseConst mCCourseConst;
    private long OtherAppBackTime = 0;

    static {
        ShowMoocActivity.isFullByClick = false;
        ShowMoocActivity.clickCount = 0;
        ShowMoocActivity.FRAGMENTS_TAG = "android:support:fragments";
    }

    public ShowMoocActivity() {
        super();
        this.fragmentList = new ArrayList();
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    View view = (View) msg.obj;
                    int id = view.getId();
                    // 获取跳转的索引值
                    Integer mapIndex = null;

                    if (id == R.id.composer_button_notic) {// 通知
                        // 把小红点隐藏掉
                        ((MyComposerView) composerButtonsWrapper.findViewById(R.id.composer_button_notic)).setPointVisibility(View.GONE);
                        iv_focus_point.setVisibility(View.GONE);
                        mapIndex = 5;
                    } else if (id == R.id.composer_button_people) {// 讨论
                        mapIndex = 4;
                    } else if (id == R.id.composer_button_place) {// 自测
                        mapIndex = 3;
                    } else if (id == R.id.composer_button_homework) {// 作业
                        mapIndex = 2;
                    } else if (id == R.id.composer_button_thought) {// 答疑
                        mapIndex = 1;
                    } else if (id == R.id.composer_button_sleep) { // 笔记
                        mapIndex = 0;
                    }
                    if (mapIndex == null) {
                        MCToast.show(ShowMoocActivity.this, "该功能后续开放，敬请期待!");
                    } else {
                        Intent intent = new Intent(ShowMoocActivity.this, (Class) mCCourseConst.MapClass.get(mapIndex)[1]);
                        intent.putExtra("courseId", mCourse.getId());
                        startActivity(intent);
                    }
                }
                super.handleMessage(msg);
            }
        };
        this.loginBackBlock = new MCAnalyzeBackBlock() {
            public void OnAnalyzeBackBlock(MCServiceResult result, List objs) {
                ViewGroup v8 = null;
                int v7 = R.layout.share_layout; // R.layout.share_layout
                int v6 = 80;
                if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS && result.getResultCode() != MCResultCode.MC_RESULT_CODE_EMPTY) {
                    result.isExposedToUser();
                }
                ShowMoocActivity.this.uid = MCSaveData.getUserInfo(Contants.UID, ShowMoocActivity.this).toString();
                if (ShowMoocActivity.this.isFinishing()) {
                    return;
                }
                int viewId = ShowMoocActivity.this.viewId;
                if (viewId == R.id.note_img) {
                    dialogFragment = new MCSendTalkDialogFragment(getString(R.string.course_note_label), mCourse.getId());
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialogFragment.show(ft, "note");
                    return;
                } else if (viewId == R.id.download_img) {
                    PopupWindow popwindow = ShowMoocActivity.this.chapterDownloadPop();
                    popwindow.showAtLocation(LayoutInflater.from(ShowMoocActivity.this.mContext).inflate(v7, v8), v6, 0, 0);
                    popwindow.setFocusable(true);
                    return;
                }
                return;

            }
        };
        this.mNetworkListener = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                MCNetworkStatus v1 = MCNetwork.currentNetwork(context);
                if (v1 != MCNetworkStatus.MC_NETWORK_STATUS_NONE && v1 == MCNetworkStatus.MC_NETWORK_STATUS_WWAN) {
                    if (!MCUserDefaults.getUserDefaults(ShowMoocActivity.this.mContext, Contants.NETWORK).getBoolean(Contants.NETWORK_SETTING)
                            && ShowMoocActivity.this.mVideoFragment != null) {
                        ShowMoocActivity.this.mVideoFragment.cancleWhenNoWifi();
                    }
                }
            }
        };
    }

    private PopupWindow chapterDownloadPop() {
        ChapterDownloadView downloadView = new ChapterDownloadView(this.mContext, this.mChapterFragment.getmChaptes(), this.mCourse);
        int height = this.mContext.getResources().getDimensionPixelSize(R.dimen.mooc_500_dp);

        // 这种动态计算的有的机型兼容性有问题。
        // int height2 =
        // MCResolution.getInstance(this.mContext).getDevHeight(this.mContext) *
        // 3 / 5;
        PopupWindow popWindow = new PopupWindow(((View) downloadView), -1, height);
        downloadView.setPop(popWindow);
        popWindow.setOutsideTouchable(true);
        popWindow.setFocusable(true);
        popWindow.setBackgroundDrawable(this.mContext.getResources().getDrawable(R.color.white));
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
            }
        });
        return popWindow;
    }

    public int getCurrentDuration() {
        return this.mVideoFragment.getCurrentDuratoin();
    }

    public MCSectionModel getCurrentPlayingSection() {
        return this.mShouldPlaySection;
    }

    public MCCourseModel getmCourse() {
        return this.mCourse;
    }

    private void initData() {
        this.mChapterFragment = new ChapterFragment();
        this.mCourseDetailFragment = new CourseDetailFragment();
        this.fragmentList.add(this.mCourseDetailFragment);
        this.fragmentList.add(this.mChapterFragment);
        this.mViewPager.setOffscreenPageLimit(2);// 有笔记的时候是3
        this.mViewPager.setOnPageChangeListener(this);
        // 设置表明
        cv_Notice.setTitle((String) mCCourseConst.MapClass.get(5)[0]);
        cv_ThemeForum.setTitle((String) mCCourseConst.MapClass.get(4)[0]);
        cv_Test.setTitle((String) mCCourseConst.MapClass.get(3)[0]);
        cv_Homework.setTitle((String) mCCourseConst.MapClass.get(2)[0]);
        cv_Question.setTitle((String) mCCourseConst.MapClass.get(1)[0]);
        cv_Note.setTitle((String) mCCourseConst.MapClass.get(0)[0]);

        ShowMoocCommon.setNoticPointVisible(mCourse.getId(), iv_focus_point, composerButtonsWrapper, ShowMoocActivity.this);
        this.layout.setVisibility(View.VISIBLE);
        this.reload_layout.setVisibility(View.INVISIBLE);
        this.mViewPager.setAdapter(new MyFragmentAdapterNew(this.getFragmentManager(), this.fragmentList));
        this.mViewPager.setCurrentItem(2);// 有笔记的时候是3
        if (Const.WHATYPALYBACKGROUP) {
            setBackGroup();
        }
    }

    private void initView() {
        this.mViewPager = (ViewPager) this.findViewById(R.id.viewpager);
        this.layout = (RelativeLayout) this.findViewById(R.id.layout);

        this.reload_layout = (RelativeLayout) this.findViewById(R.id.reload_layout);
        this.reload_layout.setOnClickListener(((View.OnClickListener) this));
        this.detail_tv = (TextView) this.findViewById(R.id.detail_tv);
        this.detail_tv.setOnClickListener(((View.OnClickListener) this));
        this.chapter_tv = (TextView) this.findViewById(R.id.chapter_tv);
        this.chapter_tv.setOnClickListener(((View.OnClickListener) this));
        this.back_img = (ImageView) this.findViewById(R.id.back_img);
        this.note_img = (ImageView) this.findViewById(R.id.note_img);
        this.share_img = (ImageView) this.findViewById(R.id.share_img);
        this.download_img = (ImageView) this.findViewById(R.id.download_img);
        this.focus_img = (ImageView) this.findViewById(R.id.focus_img);
        this.back_img.setOnClickListener(((View.OnClickListener) this));
        this.note_img.setOnClickListener(((View.OnClickListener) this));
        this.share_img.setOnClickListener(((View.OnClickListener) this));
        this.download_img.setOnClickListener(((View.OnClickListener) this));
        this.focus_img.setOnClickListener(((View.OnClickListener) this));
        this.tab_detail_img = (ImageView) this.findViewById(R.id.tab_detail_img);
        this.tab_chapter_img = (ImageView) this.findViewById(R.id.tab_chapter_img);
        // 获取图标

        cv_Notice = (MyComposerView) findViewById(R.id.composer_button_notic);
        cv_ThemeForum = (MyComposerView) findViewById(R.id.composer_button_people);
        cv_Test = (MyComposerView) findViewById(R.id.composer_button_place);
        cv_Homework = (MyComposerView) findViewById(R.id.composer_button_homework);
        cv_Question = (MyComposerView) findViewById(R.id.composer_button_thought);
        cv_Note = (MyComposerView) findViewById(R.id.composer_button_sleep);

        composerButtonsWrapper = (RelativeLayout) findViewById(R.id.composer_buttons_wrapper);
        composerButtonsShowHideButton = (RelativeLayout) findViewById(R.id.composer_buttons_show_hide_button);
        composerButtonsShowHideButtonIcon = (ImageView) findViewById(R.id.composer_buttons_show_hide_button_icon);
        removeView(mCCourseConst.count);
        for (int i = 0; i < composerButtonsWrapper.getChildCount(); i++) {
            final View smallIcon = composerButtonsWrapper.getChildAt(i);
            final int position = i;
            smallIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 这里写各个item的点击事件
                    // 1.加号按钮缩小后消失 缩小的animation
                    // 2.其他按钮缩小后消失 缩小的animation
                    // 3.被点击按钮放大后消失 透明度渐变 放大渐变的animation
                    // composerButtonsShowHideButton.startAnimation(MyAnimations.getMiniAnimation(300));
                    ShowMoocActivity.this.focus_img.getDrawable().setLevel(0);
                    Animation rotateAnimation = MyAnimations.getRotateAnimation(-225, 0, 200);
                    composerButtonsShowHideButtonIcon.startAnimation(rotateAnimation);
                    rotateAnimation.setAnimationListener(new AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            composerButtonsShowHideButton.setVisibility(View.GONE);
                        }
                    });
                    smallIcon.startAnimation(MyAnimations.getMaxAnimation(200));
                    smallIcon.setClickable(false);
                    for (int j = 0; j < composerButtonsWrapper.getChildCount(); j++) {
                        if (j != position) {
                            final View smallIcon = composerButtonsWrapper.getChildAt(j);
                            smallIcon.startAnimation(MyAnimations.getMiniAnimation(200));
                            smallIcon.setClickable(false);
                        }
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = view;
                    mHandler.sendMessageDelayed(msg, 250);
                }
            });
        }
        this.mVideoFragment = (WhatyVideoView) this.findViewById(R.id.video_fragment);
        this.mVideoFragment.initWithActivity(this);
        this.mVideoFragment.setFullScreenCallBack(this);

        this.playerLayout = (FrameLayout) this.findViewById(R.id.player_layout);

        // 设置播放器区域大小
        adjustVideoView();

        titleText = (TextView) findViewById(R.id.titleText);
        iv_focus_point = (ImageView) findViewById(R.id.focus_img_point);
    }

    public boolean isSectionPlayedOver() {
        return this.mVideoFragment.isPlayedEndTime();

    }

    public void setBackGroup() {
        if (mCourse.getImageUrl() != null) {
            mVideoFragment.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            Bitmap mBitMap = HttpBitMap.getHttpBitmap(mCourse.getImageUrl());
                            if (mBitMap != null) {
                                mBitMap = Bitmap.createScaledBitmap(mBitMap, mVideoFragment.getWidth(), mVideoFragment.getHeight(), true);
                                mVideoFragment.setBackGroup(mBitMap);

                            }
                        }
                    }).start();
                    mVideoFragment.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });


        }
    }

    public void onBackPressed() {
        if (!TextUtils.isEmpty(this.getCurrentPlayingSection().getId())) {
            Intent intent = this.getIntent();
            Bundle bundle = new Bundle();
            if (this.getCurrentPlayingSection() != null) {
                this.mCourse.setStudiedChapterSeq(this.getCurrentPlayingSection().getChapterSeq());
                this.mCourse.setStudiedMediaSeq(this.getCurrentPlayingSection().getSeq());
            }

            bundle.putSerializable("course", this.mCourse);
            intent.putExtras(bundle);
            this.setResult(-1, intent);
        }

        super.onBackPressed();
    }

    // 下方图标的点击事件
    public void onClick(View v) {
        ViewGroup mContentParent = null;
        int layoutResID = R.layout.share_layout; // R.layout.share_layout
        int v2 = 80;
        this.viewId = v.getId();
        int id = v.getId();
        if (id == R.id.back_img) {
            Intent intent = this.getIntent();
            Bundle bundle = new Bundle();
            if (this.getCurrentPlayingSection() != null) {
                if (this.getCurrentPlayingSection().getChapterSeq() > this.mCourse.getStudiedChapterSeq()) {
                    this.mCourse.setStudiedChapterSeq(this.getCurrentPlayingSection().getChapterSeq());
                    this.mCourse.setStudiedMediaSeq(this.getCurrentPlayingSection().getSeq());
                } else if (this.getCurrentPlayingSection().getChapterSeq() >= this.mCourse.getStudiedChapterSeq()) {
                    MCCourseModel model = this.mCourse;
                    int seq = this.getCurrentPlayingSection().getSeq() > this.mCourse.getStudiedMediaSeq() ? this.getCurrentPlayingSection().getSeq()
                            : this.mCourse.getStudiedMediaSeq();
                    model.setStudiedMediaSeq(seq);
                }
            }
            bundle.putSerializable("course", this.mCourse);
            intent.putExtras(bundle);
            this.setResult(-1, intent);
            this.finish();
        } else if (id == R.id.note_img) {
            this.mVideoFragment.setUserVisibleHint(false);
            if (this.uid == Contants.DEFAULT_UID) {
                MCLoginProxy.loginInstance().login(this.mContext, this.loginBackBlock);
                return;
            }
            // 弹出笔记框
            dialogFragment = new MCSendTalkDialogFragment(this.getString(R.string.course_note_label), this.mCourse.getId());
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            dialogFragment.show(ft, "note");
            this.note_img.setEnabled(true);
        } else if (id == R.id.share_img) {
            this.mVideoFragment.setUserVisibleHint(false);
            if (this.uid == Contants.DEFAULT_UID) {
                MCLoginProxy.loginInstance().login(this.mContext, this.loginBackBlock);
                return;
            }
            // 弹出答疑
            dialogFragment = new MCSendTalkDialogFragment(this.getString(R.string.course_question_label), this.mCourse.getId());
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            dialogFragment.show(ft, "question");
            this.note_img.setEnabled(true);
        } else if (id == R.id.detail_tv) {
            this.mViewPager.setCurrentItem(0, true);
        } else if (id == R.id.chapter_tv) {
            this.mViewPager.setCurrentItem(1, true);
        } else if (id == R.id.download_img) {
            if (this.uid == Contants.DEFAULT_UID) {
                MCLoginProxy.loginInstance().login(this.mContext, this.loginBackBlock);
                return;
            }
            this.popupWindow = this.chapterDownloadPop();
            this.popupWindow.setFocusable(true);
            this.popupWindow.showAtLocation(LayoutInflater.from(this.mContext).inflate(layoutResID, mContentParent), v2, 0, 0);
        } else if (id == R.id.focus_img) {
            if (this.uid == Contants.DEFAULT_UID) {
                MCLoginProxy.loginInstance().login(this.mContext, this.loginBackBlock);
                return;
            }
            if (this.focus_img.getDrawable().getLevel() == 0) {
                composerButtonsShowHideButton.setVisibility(View.VISIBLE);

                MyAnimations.startAnimationsIn(composerButtonsWrapper, 300);
                composerButtonsShowHideButtonIcon.startAnimation(MyAnimations.getRotateAnimation(0, -270, 300));
                ShowMoocActivity.this.focus_img.getDrawable().setLevel(1);
                return;
            }
            MyAnimations.startAnimationsOut(composerButtonsWrapper, 300);
            ShowMoocActivity.this.focus_img.getDrawable().setLevel(0);
            Animation rotateAnimation = MyAnimations.getRotateAnimation(-225, 0, 300);
            composerButtonsShowHideButtonIcon.startAnimation(rotateAnimation);
            rotateAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    composerButtonsShowHideButton.setVisibility(View.GONE);
                }
            });
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        orgInfo = getRequestedOrientation();

        // 一进来不让旋转，后面根据情况旋转
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (savedInstanceState != null) {
            savedInstanceState.remove(ShowMoocActivity.FRAGMENTS_TAG);
        }

        super.onCreate(savedInstanceState);
        this.mContext = ((Context) this);
        this.setContentView(R.layout.showmooc_main_layout);
        this.mCourseService = new MCCourseService();
        mCCourseConst = MCCourseConst.getInstance(); // 初始化显示课程显示view参数
        timer = new Timer();
        try {
            this.uid = MCSaveData.getUserInfo(Contants.UID, ((Context) this)).toString();
        } catch (Exception v1) {
        }
        this.mCourse = (MCCourseModel) this.getIntent().getExtras().getSerializable("course");
        // 初始化视图
        this.initView();
        // 初始化数据
        this.initData();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Contants.NETWORK_CHANGED_ACTION);
        this.registerReceiver(this.mNetworkListener, filter);
        MCLog.e(TAG, "oncreate");
        MyAnimations.initOffset(ShowMoocActivity.this);

    }

    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        this.unregisterReceiver(this.mNetworkListener);
        timer.cancel();
        this.mVideoFragment.release();
        super.onDestroy();
    }

    private boolean isOtherAppBack(int IntervalTime) {
        return (System.currentTimeMillis() - OtherAppBackTime < IntervalTime);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean flag;

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isOtherAppBack(150)) {
                return true;
            }

            if (this.mVideoFragment != null && this.mVideoFragment.isFullScreen()) {
                ++ShowMoocActivity.clickCount;
                ShowMoocActivity.isFullByClick = false;
                this.setRequestedOrientation(1);
                return true;
            }

            Intent intent = this.getIntent();
            Bundle bundle = new Bundle();
            if (this.getCurrentPlayingSection() != null) {
                if (this.getCurrentPlayingSection().getChapterSeq() > this.mCourse.getStudiedChapterSeq()) {
                    this.mCourse.setStudiedChapterSeq(this.getCurrentPlayingSection().getChapterSeq());
                    this.mCourse.setStudiedMediaSeq(this.getCurrentPlayingSection().getSeq());
                } else if (this.getCurrentPlayingSection().getChapterSeq() >= this.mCourse.getStudiedChapterSeq()) {
                    MCCourseModel v4 = this.mCourse;
                    int v2_1 = this.getCurrentPlayingSection().getSeq() > this.mCourse.getStudiedMediaSeq() ? this.getCurrentPlayingSection().getSeq()
                            : this.mCourse.getStudiedMediaSeq();
                    v4.setStudiedMediaSeq(v2_1);
                }
            }

            bundle.putSerializable("course", this.mCourse);
            intent.putExtras(bundle);
            this.setResult(-1, intent);
            this.finish();
            flag = true;
        } else {
            flag = super.onKeyUp(keyCode, event);
        }

        return flag;
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Serializable v2 = null;
        if (intent != null) {
            try {
                v2 = intent.getExtras().getSerializable("course");
            } catch (Exception v1) {
                v1.printStackTrace();
            }
        }

        if (v2 == null || this.mCourse.getId() == ((MCCourseModel) v2).getId()) {
            this.mVideoFragment.setUserVisibleHint(true);
        } else {
            this.mCourse = ((MCCourseModel) v2);
            if (this.mVideoFragment != null) {
                this.mVideoFragment.setLoadingBeforePlay();
            }
        }
    }

    public void onPageScrollStateChanged(int arg0) {
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    /**
     * 批量去除掉选择菜单
     *
     * @param count 保留的个数 最大为6
     */
    public void removeView(Integer count) {
        if (count == null)
            return;
        if (count > 6 || count < 2) {
            Log.e("e", "最大数字为6，最小为2");
            return;
        }
        composerButtonsWrapper.removeViews(0, 6 - count);
    }

    public void removeViewByList(Set<Class> list) {
        composerButtonsWrapper.getChildCount();
        composerButtonsWrapper.removeViewAt(1);

    }

    public void onPageSelected(int position) {
        int v5 = R.style.SectionSelectNameTextStyle; // R.style.SectionSelectNameTextStyle
        int v3 = R.style.CourseNameTextStyle; // R.style.CourseNameTextStyle
        switch (position) {
            case 0: {
                this.chapter_tv.setTextAppearance(((Context) this), v3);
                this.detail_tv.setTextAppearance(((Context) this), v5);
                this.tab_detail_img.setVisibility(0);
                this.tab_chapter_img.setVisibility(View.INVISIBLE);
                if (this.mChapterFragment.getVisible()) {
                    this.mChapterFragment.setVisible(false);
                }

                this.mCourseDetailFragment.setVisible(true);
                break;
            }
            case 1: {
                this.detail_tv.setTextAppearance(((Context) this), v3);
                this.chapter_tv.setTextAppearance(((Context) this), v5);
                this.tab_detail_img.setVisibility(View.INVISIBLE);
                this.tab_chapter_img.setVisibility(0);
                if (this.mCourseDetailFragment.getVisible()) {
                    this.mCourseDetailFragment.setVisible(false);
                }

                this.mChapterFragment.setVisible(true);
                break;
            }
        }
    }

    protected void onRestart() {
        super.onRestart();
    }


    /**
     * onResume里查询的数据
     */
    protected void onResume() {
        ShowMoocActivity.this.mVideoFragment.setUserVisibleHint(true);
        this.mSensorManager = (SensorManager) this.getSystemService("sensor");

        if (should_start_when_resume) {
            // 开始播放视频
            should_start_when_resume = false;
            if (mVideoFragment != null) {
//				mVideoFragment.start(CurrentPosition);
                ShowMoocCommon.startRecordTimer(this.mShouldPlaySection.getId(), studentId, mShouldPlaySection.getType(), this);
                ShowMoocCommon.updateTimeInfo(this.mShouldPlaySection.getId(), this.mCourse.getId(), this.mVideoFragment.getPlayer());
            }
        }
        if (should_resume_loading_when_resume) {
            should_resume_loading_when_resume = false;
            if (mVideoFragment != null) {
                mVideoFragment.resumeLoading();
            }
        }

        super.onResume();
        OtherAppBackTime = System.currentTimeMillis();
    }

    // 切换到后台
    protected void onPause() {
        if (this.mShouldPlaySection != null) {
            ShowMoocCommon.stopRecordTimer(this.mShouldPlaySection.getId());// 停止记录
        }

        if (mVideoFragment != null && mVideoFragment.isPlaying()) {
            CurrentPosition = (int) mVideoFragment.getCurrentPosition();
            mVideoFragment.pause();
            should_start_when_resume = true;
        }
        if (mVideoFragment != null && !mVideoFragment.isLoadingPaused()) {
            mVideoFragment.pauseLoading();
            should_resume_loading_when_resume = true;
        }
        super.onPause();
    }

    protected void onStart() {
        this.mHandler.sendEmptyMessageDelayed(0, 500);
        super.onStart();
    }

    protected void onStop() {
        manager = null;
        try {
            this.mSensorManager.unregisterListener(((SensorEventListener) this));
            this.mVideoFragment.setUserVisibleHint(false);
        } catch (Exception e) {
        }
        super.onStop();
    }

    private Dialog dialog;
    private MCDownloadManager manager;
    private MCCommonDialog downDialog;

    /**
     * 显示下载文档的对话框
     */
    private void showDownDocDialog(final MCSectionModel section) {
        createCommonDialog("提示", "要查看该资料需要先下载，您继续吗？", R.layout.network_dialog_layout);
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                downDialog.setCommitListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        downDialog.dismiss();
                        if (manager == null) { // 没有就new
                            // 有的话把类型更新成普通，不然如果manager里残留的是apk类型的就会很麻烦了。
                            manager = new MCDownloadManager(ShowMoocActivity.this, 0);
                        } else {
                            // manager.setDownType(0);
                        }

                        manager.setDownloadUrl(section.getSharedUrl(), section);

                        manager.startDownLoad(MCUpgradeType.MC_UPGRADE_TYPE_NEED_UPGRADE, new Handler());
                    }
                });
            }
        }, 200);
    }

    /**
     * 章节点击后的核心处理函数，不同类型节点做不同的处理
     * <p/>
     * <p/>
     * section 节点信息
     */
    public void play(final MCSectionModel section) {
        this.mChapterFragment.refreshByCourse(this.mCourse);
        this.mCourseDetailFragment.refreshByCourse(this.mCourse);
        if (section != null) {

            // 切换节点就停止播放
            if (this.mShouldPlaySection != null) {
                ShowMoocCommon.stopRecordTimer(this.mShouldPlaySection.getId());
            }
            this.mVideoFragment.stop();

            section.setCourseId(this.mCourse.getId());
            this.mShouldPlaySection = section;

            // 视频类型的设置为默认
            if (this.mShouldPlaySection.getType() == MCMediaType.MC_VIDEO_TYPE) {
                setRequestedOrientation(orgInfo);// 回复activity的默认值
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            this.note_img.setVisibility(View.VISIBLE);

            MCNetworkStatus netStatus = MCNetwork.currentNetwork(((Context) this));

            // 没有网络
            if (netStatus == MCNetworkStatus.MC_NETWORK_STATUS_NONE) {
                this.mVideoFragment.cancleWhenNoWifi();
                this.mChapterFragment.currentPlayingSection();
                MCToast.show(((Context) this), this.getResources().getString(R.string.download_nonetwork_label));
                return;
            }
            String sharedUrl = section.getSharedUrl();
            this.mChapterFragment.currentPlayingSection();
            // 处理外部链接的形式
            if (section.getType() == MCMediaType.MC_LINK_TYPE) {

                // 保存学习记录
                ShowMoocCommon.saveClickRecord(section, this);

                String url = sharedUrl;// 外部链接地址
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return;
            } else if (section.getType() == MCMediaType.MC_PROGRAMME_TYPE) {

                // 保存学习记录
                ShowMoocCommon.saveClickRecord(section, this);

                Intent intent = new Intent(this, PicTxtWebviewActivity.class);
                intent.putExtra("section", section);
                startActivity(intent);
                return;
            } else if (section.getType() == MCMediaType.MC_DOC_TYPE) {
                sharedUrl = section.getSharedUrl();
                if (sharedUrl != null && !"".equals(sharedUrl) && !"null".equals(sharedUrl)) {
                    String filePath = isFileExist(sharedUrl);
                    MCLog.e("filepath", filePath);
                    if (!TextUtils.isEmpty(filePath)) {
                        // 如果已经下载过了并且原来状态是未完成的就保存学习记录。这样等保证记录次数
                        ShowMoocCommon.saveClickRecord(section, this);
                        OpenFile.openFile(new File(filePath), ShowMoocActivity.this);
                    } else {
                        showDownDocDialog(section);
                    }
                } else {
                    MCToast.show(this, "暂无下载地址");
                }
                return;
            } else if (section.getType() == MCMediaType.MC_VIDEO_TYPE) {
                if (netStatus == MCNetworkStatus.MC_NETWORK_STATUS_WWAN) {
                    if (!MCUserDefaults.getUserDefaults(this.mContext, Contants.NETWORK).getBoolean(Contants.NETWORK_SETTING)) {
                        MCToast.show(this, "现在不是WIFI网络呢，请在设置里设置非WIFI环境也在线看视频或者在WIFI环境下观看视频……");
                        return;
                    }
                }
                this.titleText.setText(section.getName());
                // 这里开始播放视频
                // 视频播放的逻辑
                this.mVideoFragment.setMediaUrl(getLoadOrJSONMediaPalyUrl(section));


                ShowMoocCommon.startRecordTimer(section.getId(), studentId, mShouldPlaySection.getType(), this);
                ShowMoocCommon.updateTimeInfo(section.getId(), section.getCourseId(), this.mVideoFragment.getPlayer());

                return;
            } else if (section.getType() == MCMediaType.MC_RESOURCE_TYPE) {// 下载资料
                // 保存学习记录
                ShowMoocCommon.saveClickRecord(section, this);

                Intent intent = new Intent(this, DownloadResourcesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("section", section);
                intent.putExtras(bundle);
                this.startActivityForResult(intent, 10);
                return;
            } else if (section.getType() == MCMediaType.MC_COURSEWARE_TYPE) {// 电子课件
                Intent intent = null;
                if (section.getName().contains("FLASH")) {
                    intent = new Intent(this, MCSFPScreenNoThumbActivity.class);
                } else {
                    intent = new Intent(this, MCSFPScreenActivity.class);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("section", section);
                bundle.putSerializable("course", this.mCourse);
                intent.putExtras(bundle);

                this.startActivity(intent);
                return;
            } else if (section.getType() == MCMediaType.MC_HOMEWORK_TYPE) {// 作业类型
                ShowMoocCommon.goToHomeworkFromSection(this.mCourse, section, this);
            } else if (section.getType() == MCMediaType.MC_EVALUATION_TYPE) {
                ShowMoocCommon.goToSelfTestFromSection(this.mCourse, section, this);
            } else if (section.getType() == MCMediaType.MC_TOPIC_TYPE) {
                ShowMoocCommon.goToThemeForumFromSection(this.mCourse, section, this);
            } else if (section.getType() == MCMediaType.MC_LIVE_TYPE) { // 直播类型
                Intent intent = new Intent(this, MCLiveOnLineActivity.class);
                intent.putExtra("itemsId", section.getId());
                startActivity(intent);
            } else {
                MCToast.show(this, "暂未开放的类型。");
                return;
            }

        }
    }

    /**
     * 播放本地 还是播放线上的视频
     */
    public String getLoadOrJSONMediaPalyUrl(MCSectionModel section) {

        MCDownloadTask itemTask = MCDownloadQueue.mDownloadTasks.get(section.getId());
        String url;
        if (itemTask != null && itemTask.getNode().isDownloadOver()) {
            String fileName = String.valueOf(itemTask.getNode().getSectionId()) + itemTask.getNode().getDownloadUrl().substring(itemTask.getNode().getDownloadUrl().lastIndexOf(46));
            url = FileUtils.getVideoFullPath(fileName);
        } else {
            url = section.getMediaUrl();
        }
        return url;
    }


    /**
     * 判断文件是否存在，若存在返回文件全路径
     *
     * @param sharedUrl 文件下载地址
     * @return 文件全路径
     */
    private String isFileExist(String sharedUrl) {
        String filePath = "";
        String fileName = sharedUrl.substring(sharedUrl.lastIndexOf("/") + 1, sharedUrl.length());
        File file = new File(Contants.APK_PATH + File.separator + fileName);
        if (file.exists() && file.isFile())
            filePath = file.getAbsolutePath();
        return filePath;
    }

    public void setmCourse(MCCourseModel mCourse) {
        this.mCourse = mCourse;
    }

    public PopupWindow showSectionLists(View dropView) {
        Display display = this.getWindow().getWindowManager().getDefaultDisplay();
        FullplaySectionsView v4 = new FullplaySectionsView(((Context) this), this.mChapterFragment.getmChaptes(), this.getCurrentPlayingSection());
        v4.setOnSectionListClickListener(new OnSectionListClickListener() {
            public void onSectionListClick(MCSectionModel section) {
                ShowMoocActivity.this.play(section);
            }
        });
        PopupWindow popWindow = new PopupWindow(((View) v4), display.getWidth() / 3, -1);
        popWindow.setContentView(((View) v4));
        popWindow.setOutsideTouchable(true);
        popWindow.setAnimationStyle(R.style.SectionPopwindowStyle);
        try {
            popWindow.setBackgroundDrawable(this.getResources().getDrawable(R.color.fullplay_bg));
        } catch (Exception v1) {
            v1.printStackTrace();
        }

        dropView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        popWindow.showAsDropDown(dropView, MCResolution.getInstance(((Context) this)).getDevWidth(((Context) this)), 0);
        popWindow.setFocusable(true);
        return popWindow;
    }

    public boolean theSectionIsLastest(MCSectionModel section) {
        List<MCChapterAndSectionModel> v2 = this.mChapterFragment.getmChaptes();
        try {
            if (section.getId() != ((MCChapterAndSectionModel) v2.get(v2.size() - 1)).getSection().getId()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        boolean v3 = true;
        return v3;
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public void setPopupWindow(PopupWindow popupWindow) {
        this.popupWindow = popupWindow;
    }

    /**
     * 横竖屏事件的拦截
     */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mVideoFragment.onConfigurationChanged();
    }

    @Override
    public void adjustVideoView() {
        if (this.mVideoFragment.isFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            LayoutParams params = this.playerLayout.getLayoutParams();
            params.height = MCResolution.getInstance(this).getDevHeight(this);
            params.width = MCResolution.getInstance(this).getDevWidth(this);
            this.playerLayout.setLayoutParams(params);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            LayoutParams params = this.playerLayout.getLayoutParams();
            params.width = MCResolution.getInstance(this).getDevWidth(this);
            params.height = params.width * 9 / 16;
            this.playerLayout.setLayoutParams(params);
        }
    }

    // 答疑加载图片返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (dialogFragment != null && dialogFragment.getPic_layout() != null)
            dialogFragment.getPic_layout().onActivityResult(requestCode, resultCode, data);
    }

    public void createCommonDialog(String title, String content, int resId) {
        downDialog = new MCCommonDialog(title, content, resId);
        FragmentTransaction ft = this.getFragmentManager().beginTransaction();
        downDialog.show(ft, "下载");
    }
}
