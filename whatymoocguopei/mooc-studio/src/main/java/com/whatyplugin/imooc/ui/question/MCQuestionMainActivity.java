package com.whatyplugin.imooc.ui.question;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.viewpager.ViewPagerChange;
import com.whatyplugin.imooc.ui.adapter.MyFragmentAdapter;
import com.whatyplugin.imooc.ui.search.MCAllQueationSearchActivity;

/**
 * 问题主界面，可以切换全部问题和我的问题。
 * 
 * @author 马彦君
 * 
 */
public class MCQuestionMainActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {
	private TextView[] tvs = new TextView[2];
	private ViewPager mViewPager;
	private List<Fragment> fragmentList;
	private MCMyQuestionFragment fragmentMyQuestion;
	private MCAllQuestionFragment fragmentNewQuestion;

	private ViewPager vp_question;
	private ImageView iv_search;
	private int current = 0;
	private String courseId;
	private TextView title_label;

	public MCQuestionMainActivity() {
		super();
		this.fragmentList = new ArrayList<Fragment>();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actvity_getquestion);
		initView();
		if (getIntent() != null)
			this.courseId = getIntent().getStringExtra("courseId");
		initData();
		// 重置显示颜色
		ViewPagerChange.backUpdateTabBar(0, tvs);

	}

	private void initData() {
		this.fragmentMyQuestion = new MCMyQuestionFragment();
		this.fragmentNewQuestion = new MCAllQuestionFragment();
		this.fragmentList.add(this.fragmentNewQuestion);
		this.fragmentList.add(this.fragmentMyQuestion);
		this.mViewPager.setOffscreenPageLimit(2);
		this.mViewPager.setOnPageChangeListener(this);

		this.mViewPager.setAdapter(new MyFragmentAdapter(this.getSupportFragmentManager(), this.fragmentList));
	}

	private void initView() {
		this.title_label = (TextView) this.findViewById(R.id.title_label);
		this.findViewById(R.id.back).setOnClickListener(this);
		this.title_label.setText("大家疑问");
		this.iv_search = (ImageView) findViewById(R.id.right_img);
		iv_search.setVisibility(View.VISIBLE);

		iv_search.setOnClickListener(this);
		TextView iv_question = (TextView) findViewById(R.id.edit);
		iv_question.setOnClickListener(this);
		iv_question.setVisibility(View.VISIBLE);
		iv_question.setBackgroundDrawable(getResources().getDrawable(R.drawable.qa));

		tvs[0] = (TextView) findViewById(R.id.question_new);

		tvs[1] = (TextView) findViewById(R.id.question_mine);

		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setOnClickListener(this);
		}
		vp_question = (ViewPager) findViewById(R.id.vp_question);

		this.mViewPager = (ViewPager) this.findViewById(R.id.vp_question);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.back) {
			this.finish();
		} else if (id == R.id.edit) {
			Intent intent = new Intent(this, MCQuestionCommitActivity.class);
			intent.putExtra("courseId", courseId);
			startActivityForResult(intent, 0);
		} else if (id == R.id.question_new) {
			current = ViewPagerChange.updatePagerChange(0, current, vp_question, tvs);
		} else if (id == R.id.question_mine) {
			current = ViewPagerChange.updatePagerChange(1, current, vp_question, tvs);
		} else if (id == R.id.right_img) {
			gotoSearch();
		}
	}

	private void gotoSearch() {
		Intent intent = new Intent(this, MCAllQueationSearchActivity.class);
		intent.putExtra("courseId", courseId);
		startActivity(intent);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		ViewPagerChange.pagerViewTabBar(arg0, arg1, arg2, tvs);
	}

	@Override
	public void onPageSelected(int position) {
		current = position;

	}

	public String getCourseId() {
		return courseId;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == 1) {
			// 重新刷新数据
			fragmentMyQuestion.mListView.headerRefreshing();
			fragmentNewQuestion.mListView.headerRefreshing();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
