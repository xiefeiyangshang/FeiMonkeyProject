package com.whatyplugin.imooc.ui.note;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MCNotePagerAdapter extends FragmentPagerAdapter {
	private Fragment[] frag = new Fragment[2];
	private String courseId;
	public MCNotePagerAdapter(FragmentManager fm,String courseId) {
		super(fm);
		this.courseId = courseId;
	}

	@Override
	public Fragment getItem(int arg0) {
		if(frag[0] == null)
			frag[0] = MCNoteListFragment.newInstance(0,courseId);
		if(frag[1] == null)
			frag[1] = MCNoteListFragment.newInstance(2, courseId);
		
		return frag[arg0];
	}

	@Override
	public int getCount() {
		return frag.length;
	}

}
