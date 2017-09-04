package com.whatyplugin.imooc.ui.adapter;



import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.LinearLayout;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private FragmentManager mFm;

    public MyFragmentAdapter(FragmentManager fm, List arg2) {
        super(fm);
        this.mFm = fm;
        this.list = arg2;
    }

    public void destroyItem(View container, int position, Object object) {
        ((LinearLayout)container).removeViewAt(position);
        super.destroyItem(container, position, object);
    }

    public int getCount() {
        return this.list.size();
    }

    public Fragment getItem(int arg0) {
        return this.list.get(arg0);
    }

    public Object instantiateItem(View container, int position) {
        this.mFm.saveFragmentInstanceState(this.list.get(position));
        return super.instantiateItem(container, position);
    }
}

