package com.whatyplugin.imooc.ui.adapter;


import java.util.List;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCFullUserModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCUserModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;

public class ViewPagerAdapter extends PagerAdapter implements GestureDetector.OnGestureListener, MCAnalyzeBackBlock {
    private static final int FLING_MIN_DISTANCE = 700;
    private static final int FLING_MIN_VELOCITY = 1500;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    private Activity activity;
    GestureDetector detector;
    private String sign;
    private MCUserModel user;
    private List<View> views;

    public ViewPagerAdapter(List<View> views, Activity activity) {
        super();
        this.sign = null;
        this.detector = new GestureDetector(this);
        this.views = views;
        this.activity = activity;
    }

    public void OnAnalyzeBackBlock(MCServiceResult result, List objs) {
        if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
            this.user = (MCUserModel) objs.get(0);
            MCSaveData.saveUserInfo((MCFullUserModel) this.user, this.activity);
        }
        else if(result.getResultCode() != MCResultCode.MC_RESULT_CODE_EMPTY) {
            result.isExposedToUser();
        }
    }

    static void access$0(ViewPagerAdapter arg0) {
        arg0.goHome();
    }

    public void destroyItem(LinearLayout arg0, int arg1, Object arg2) {
        arg0.removeView((View) this.views.get(arg1));
    }

    public void finishUpdate(View arg0) {
    }

    public int getCount() {
        int count = this.views != null ? this.views.size() : 0;
        return count;
    }

    private void goHome() {

        this.activity.finish();
    }

    public Object instantiateItem(LinearLayout view, int position) {
        view.addView((View) this.views.get(position), 0);
        if(position == this.views.size() - 1) {
            view.findViewById(R.id.iv_start).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ViewPagerAdapter.this.goHome();
                }
            });
            this.detector.setIsLongpressEnabled(true);
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View arg0, MotionEvent arg1) {
                    return ViewPagerAdapter.this.detector.onTouchEvent(arg1);
                }
            });
        }

        return this.views.get(position);
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        boolean flag = arg0 == (((View)arg1)) ? true : false;
        return flag;
    }

    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1 != null && e2 != null && e1.getX() - e2.getX() > 700f && Math.abs(velocityX) > 1500f) {
            this.setGuided();
            this.goHome();
        }

        return false;
    }

    public void onLongPress(MotionEvent arg0) {
    }

    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
        return false;
    }

    public void onShowPress(MotionEvent arg0) {
    }

    public boolean onSingleTapUp(MotionEvent arg0) {
        return false;
    }

    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    public Parcelable saveState() {
        return null;
    }

    private void setGuided() {
        MCSaveData.saveIsFirstIn(Boolean.valueOf(false), this.activity);
    }

    public void startUpdate(View arg0) {
    }
}

