package com.whatyplugin.imooc.ui.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.viewpager.ViewPagerChange;
import com.whatyplugin.imooc.ui.search.MCAllNoteSearchActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;

/**
 * 共享笔记列表，可以切换全部笔记和我的笔记
 * 
 * @author 马彦君
 * 
 */
public class MCNoteListActivity extends FragmentActivity implements OnClickListener {
	private TextView[] tvTabs = new TextView[2];
	private ViewPager vpNote;
	private MCNotePagerAdapter adapter;
	private int current = 0;
	private String courseId;
	private BaseTitleView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notelist);
		initView();
		initData();
		initEvent();
		// 重置显示颜色
		ViewPagerChange.backUpdateTabBar(0, tvTabs);

	}

	private void initEvent() {
		tvTabs[0].setOnClickListener(this);
		tvTabs[1].setOnClickListener(this);
		vpNote.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				current = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				ViewPagerChange.pagerViewTabBar(arg0, arg1, arg2, tvTabs);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		titleView.setRigImageListener(new RightClickListener() {

			@Override
			public void onRightViewClick() {
				gotoSearch();
			}
		});
	}

	private void initData() {
		courseId = getIntent().getStringExtra("courseId");
		adapter = new MCNotePagerAdapter(this.getSupportFragmentManager(), courseId);
		vpNote.setAdapter(adapter);
	}

	private void initView() {
		vpNote = (ViewPager) findViewById(R.id.vp_note);
		tvTabs[0] = (TextView) findViewById(R.id.tv_gxnote);
		
		tvTabs[1] = (TextView) findViewById(R.id.tv_mynote);
		
		titleView = (BaseTitleView) findViewById(R.id.rl_titile);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.tv_gxnote) {
			ViewPagerChange.updatePagerChange(0, current, vpNote, tvTabs);
		} else if (id == R.id.tv_mynote) {
			ViewPagerChange.updatePagerChange(1, current, vpNote, tvTabs);
		}
	}

	private void gotoSearch() {
		Intent intent = new Intent(this, MCAllNoteSearchActivity.class);
		intent.putExtra("courseId", courseId);
		startActivity(intent);
	}
}
