package com.whaty.imooc.ui.index;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import cn.com.whatyguopei.mooc.R;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MCBaseActivity extends SlidingFragmentActivity {
    protected Fragment mFrag;
    private int mTitleRes;

    public MCBaseActivity(int titleRes) {
        super();
        this.mTitleRes = titleRes;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(this.mTitleRes);
        this.setBehindContentView(R.layout.menu_frame);
        if(savedInstanceState == null) {
            FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
            this.mFrag = new Fragment();
            transaction.replace(R.id.menu_frame, this.mFrag);
            transaction.commit();
        }
        else {
            this.mFrag = this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
        }

        SlidingMenu menu = this.getSlidingMenu();
        menu.setShadowWidthRes(R.dimen.mooc_15_dp);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.mooc_60_dp);
        menu.setFadeDegree(0.35f);
        menu.setTouchModeAbove(1);
    }
}

