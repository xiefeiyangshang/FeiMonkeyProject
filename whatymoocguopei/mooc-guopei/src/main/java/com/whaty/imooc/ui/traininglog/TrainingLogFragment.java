package com.whaty.imooc.ui.traininglog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whaty.imooc.utile.myViewPager;
import com.whatyplugin.base.viewpager.ViewPagerChange;
import com.whatyplugin.imooc.ui.adapter.MyFragmentAdapter;
import com.whatyplugin.imooc.ui.base.MCBaseV4Fragment;

import java.util.ArrayList;
import java.util.List;

import cn.com.whatyguopei.mooc.R;

public class TrainingLogFragment extends MCBaseV4Fragment implements OnClickListener {
	private myViewPager vp_Blog;
	private View rootView;
	private List<Fragment> listFragment;
	private MyTrainingNewLogFragment hotFragment;
	private MyTrainingNewLogFragment newFragment;
	private myBlogFragment myFragment;
	private String HOTBLOG = "hotBlog"; // 热门日志
	private String NEWBLOG = "getNewBlog"; // 最新日志
	private TextView[] tvs = new TextView[3];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.traininglog_layout, null);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		vp_Blog = (myViewPager) rootView.findViewById(R.id.vp_blog);
		tvs[0] = (TextView) rootView.findViewById(R.id.myblog);
		tvs[1] = (TextView) rootView.findViewById(R.id.hotblog);
		tvs[2] = (TextView) rootView.findViewById(R.id.newblog);
	}

	private void initEvent() {
		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setOnClickListener(this);
		}
	}

	private void initData() {
		listFragment = new ArrayList<Fragment>();
		hotFragment = new MyTrainingNewLogFragment(HOTBLOG);
		newFragment = new MyTrainingNewLogFragment(NEWBLOG);
		myFragment = new myBlogFragment();
		listFragment.add(myFragment);
		listFragment.add(hotFragment);
		listFragment.add(newFragment);
		vp_Blog.setOffscreenPageLimit(listFragment.size());
		vp_Blog.setAdapter(new MyFragmentAdapter(this.getFragmentManager(), listFragment));
		vp_Blog.setCurrentItem(1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myblog:
			updateChange(0);
			break;
		case R.id.hotblog:
			updateChange(1);
			break;
		case R.id.newblog:
			updateChange(2);
			break;

		}

	}

	private void updateChange(int index) {
		ViewPagerChange.backUpdateTabBar(index, tvs);
		vp_Blog.setCurrentItem(index);
	}

}
