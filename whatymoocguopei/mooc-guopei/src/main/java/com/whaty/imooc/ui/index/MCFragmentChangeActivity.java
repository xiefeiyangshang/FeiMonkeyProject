package com.whaty.imooc.ui.index;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.whaty.imooc.ui.course.GPCourseFragment;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.ui.mymooc.EmptyFragment;
import com.whatyplugin.imooc.ui.view.SlidingTitileView;
import com.whatyplugin.imooc.ui.view.SlidingTitileView.ILeftListener;

import java.util.ArrayList;
import java.util.List;

import cn.com.whatyguopei.mooc.R;
public class MCFragmentChangeActivity extends SlidingFragmentActivity {
    private static String FRAGMENTS_TAG;
    private SparseArray<Fragment> fragments;
    private Fragment mContent;
    private FrameLayout mCourseTypeViewLayout;
    private Handler mHandler;
    private MCMenuItemFragment mMenuFragment;
    private SlidingTitileView mSlidingTitle;
    private SlidingMenu sm;
    private List<Integer> tags;
    static {
        MCFragmentChangeActivity.FRAGMENTS_TAG = "android:support:fragments";
    }
    public MCFragmentChangeActivity(int mSlidingTitleRes, CanvasTransformer transformer) {
        super();
        this.tags = new ArrayList<Integer>();
        this.fragments = new SparseArray();
        this.mHandler = new Handler() {
            public void handleMessage(Message msg) {
                Fragment fragment;
                try {
                    // 只处理msg.what=0 和等于1的
                    // 等于0的是没有添加过的。
                    if (msg.what != 0) {
                        if (msg.what == 1) {
                            MCFragmentChangeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, MCFragmentChangeActivity.this.mContent).commitAllowingStateLoss();
                            MCFragmentChangeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, MCFragmentChangeActivity.this.mMenuFragment).commitAllowingStateLoss();
                        }
                        super.handleMessage(msg);
                        return;
                    }
                    fragment = (Fragment) msg.obj;
                    int tag = msg.arg1;
                    if (fragment.isAdded()) {
                        if (fragment.isHidden()) {
                            MCFragmentChangeActivity.this.getSupportFragmentManager().beginTransaction().show(((Fragment) fragment)).commitAllowingStateLoss();
                        }
                        MCFragmentChangeActivity.this.getSlidingMenu().showContent();
                        super.handleMessage(msg);
                        return;
                    }
                    MCFragmentChangeActivity.this.tags.add(Integer.valueOf(tag));
                } catch (Exception e) {
                    e.printStackTrace();
                    super.handleMessage(msg);
                    return;
                }
                try {
                    MCFragmentChangeActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.content_frame, ((Fragment) fragment)).commitAllowingStateLoss();
                    MCFragmentChangeActivity.this.getSlidingMenu().showContent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.handleMessage(msg);
            }
        };
    }

    protected FrameLayout getCourseTypeViewLayout() {
        return this.mCourseTypeViewLayout;
    }

    protected MCMenuItemFragment getMenuItemFragment() {
        return this.mMenuFragment;
    }

    public SlidingTitileView getmSlidingmSlidingTitle() {
        return this.mSlidingTitle;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            MCLog.v("MCFragmentChangeActivity", "clear the fragment from bundle");
            savedInstanceState.remove(MCFragmentChangeActivity.FRAGMENTS_TAG);
        }
        super.onCreate(savedInstanceState);
        // 主视图是content_frame， 上面是标题控件SlidingTitileView，下面是FrameLayout
        this.setContentView(R.layout.content_frame);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        // 点击左侧菜单的listener
        this.mSlidingTitle.setOnLeftListener(new ILeftListener() {
            public void onLeftListener() {
                if (MCFragmentChangeActivity.this.mCourseTypeViewLayout.getVisibility() == 0) {
                    // 如果全部课程里面的下拉是展开的就进行处理。
                    MCFragmentChangeActivity.this.mCourseTypeViewLayout.setVisibility(View.GONE);
                    MCFragmentChangeActivity.this.mMenuFragment.courseTypeViewAnim(false, MCFragmentChangeActivity.this.mCourseTypeViewLayout, null);
                    MCFragmentChangeActivity.this.mMenuFragment.arrowAnim(MCFragmentChangeActivity.this.mSlidingTitle.getArrowImageView());
                } else {
                    // 切换效果
                    MCFragmentChangeActivity.this.toggle();
                }
            }
        });
    }

    private void addFragment() {
        if (this.mContent == null) {
            this.mContent = new GPCourseFragment();
        }
        // 所有Fragment ，
        // 先把AllMoocFragment加入。MCMainActivity》MCFragmentChangeActivity》myMoocFragment
        this.fragments.put(R.id.mycourse_layout, this.mContent);
        // 向tags集合中加入 allcourse_layout
        this.tags.add(R.id.mycourse_layout);
    }

    private void initData() {
        // 初始化数据库
        MCSaveData.saveIsFirstIn(Boolean.valueOf(false), ((Context) this));
        mSlidingTitle.setRightViewVisibility(false);
        addFragment();
        // 设置文本内容
        this.mSlidingTitle.setMidViewText(this.getString(R.string.mycourse));
        // 设置显示下拉
        this.mSlidingTitle.setArrowImageVisibility(false);
        // 把菜单布局设置为 BehindContentView，这样跟this.sm配合就会出现遮罩效果
        this.setBehindContentView(R.layout.menu_frame);
        // 左侧菜单内容初始化
        this.mMenuFragment = new MCMenuItemFragment(this.fragments);
        // 构建一个1类型的消息，10毫秒后发出 this.mHandler会收到并处理，这里很重要
        this.mHandler.sendEmptyMessageDelayed(1, 10);
        // 初始化左侧的滑动菜单效果 SlidingMenu
        this.sm = this.getSlidingMenu();
        // 设置滑动菜单效果
        this.setSlidingActionBarEnabled(true);
        this.sm.setBehindScrollScale(0f);
        this.sm.setShadowWidth(0);
        this.sm.setBehindOffsetRes(R.dimen.mooc_60_dp);
        this.sm.setFadeDegree(0.35f);
        this.sm.setTouchModeAbove(1);
        // 显示进度条
        this.setLoadingLayoutVisibility(true);
    }

    private void initView() {
        // 上方的红框元素
        this.mSlidingTitle = (SlidingTitileView) this.findViewById(R.id.title_layout);
        // 下拉选择课程类型
        this.mCourseTypeViewLayout = (FrameLayout) this.findViewById(R.id.course_type_view_content);
    }

	/*public boolean onMenuOpened(int featureId, Menu menu) {
        this.toggle();
		return true;
	}*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            this.toggle();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void refreshAfterLogin() {
        this.mMenuFragment.refreshAfterLogin();
    }

    public void refreshAfterLogout() {
        this.mMenuFragment.refreshAfterLogout();
    }

    /**
     * 正在加载的进度条
     *
     * @param visibility
     */
    public void setLoadingLayoutVisibility(boolean visibility) {
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }

    /**
     * 点击菜单时候切换右侧内容的主体
     *
     * @param fragment
     * @param tag
     */
    public void switchContent(Fragment fragment, int tag) {
        this.getSupportFragmentManager().beginTransaction().hide(this.mContent).commitAllowingStateLoss();
        this.mContent = fragment;
        // 显示主要内容，隐藏左侧菜单
        this.getSlidingMenu().showContent();
        this.mHandler.removeMessages(0);
        if (this.mContent.isAdded()) {
            this.getSupportFragmentManager().beginTransaction().show(this.mContent).commitAllowingStateLoss();
        } else {
            this.setLoadingLayoutVisibility(true);
            if (this.mContent instanceof EmptyFragment)
                this.setLoadingLayoutVisibility(false);
            Message message = new Message();
            message.what = 0;
            message.obj = fragment;
            message.arg1 = tag;
            this.mHandler.sendMessageDelayed(message, 500);
        }
    }
}
