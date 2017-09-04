package com.whatyplugin.imooc.ui.themeforum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.model.MCForumModel;
import com.whatyplugin.imooc.logic.utils.WebViewUtil;
import com.whatyplugin.imooc.ui.adapter.MyFragmentAdapter;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.CustomScrollView;

import java.util.ArrayList;
import java.util.List;

import cn.com.whatyplugin.mooc.R;

public class ThemeForumInfoActivity extends FragmentActivity implements OnClickListener {
	private TextView tv_forumtitle; // 名称
	private TextView tv_forumtime; // 时间
	private WebView tv_forumBody; // 内容
	private TextView tv_forumtopic; // 精华数
	private TextView tv_forumrepyly; // 回帖数
	private Button bt_sendDiscuss;
	public FragmentThemeForumReply forumReply; // 回复
	public FragmentThemeForumReply forumTopic; // 精华
	private List<Fragment> listFragment;
	private ViewPager mViewPager;
	private MCForumModel forumModel;
	private String courseId;
	private LinearLayout ll_topic_bar; // 精华
	private LinearLayout ll_reply_bar;// 回帖数
	private boolean forumState = false; // 帖子的状态
	private CustomScrollView mScrollView;
	private LinearLayout view_pager_container;
	private int newNum = 0;
	private BaseTitleView titleView;
	private Handler mHandler;
	private ImageView iv_foruminfo_img;

	public ThemeForumInfoActivity() {
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					View view = (View) msg.obj;
					mScrollView.fullScroll(ScrollView.FOCUS_DOWN);// 滚动到底部，其实用上面注掉的也可以解决。
					view.requestFocus();
				}
				super.handleMessage(msg);
			}
		};
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.themeforuminfo);
		Intent intent = getIntent();
		forumModel = (MCForumModel) intent.getSerializableExtra("ForumModel");

		if (forumModel.getState() == 2 || forumModel.getState() == 3)
			forumState = true;
		courseId = intent.getStringExtra("courseId");
		initView();
		addValueOrListenter();
	}

	private void initView() {
		tv_forumtitle = (TextView) findViewById(R.id.foruminfo_title);
		tv_forumBody = (WebView) findViewById(R.id.foruminfo_body);
		tv_forumrepyly = (TextView) findViewById(R.id.foruminfo_repylynum);
		tv_forumtopic = (TextView) findViewById(R.id.foruminfo_topic_num);
		tv_forumtime = (TextView) findViewById(R.id.foruminfo_time);
		bt_sendDiscuss = (Button) findViewById(R.id.foruminfo_senddiscuss);
		ll_topic_bar = (LinearLayout) findViewById(R.id.foruminfo_topic_bar);
		ll_reply_bar = (LinearLayout) findViewById(R.id.foruminfo_reply_bar);
		this.mScrollView = (CustomScrollView) this.findViewById(R.id.mScrollView);
		iv_foruminfo_img = (ImageView) findViewById(R.id.foruminfo_img);
		this.titleView = (BaseTitleView) findViewById(R.id.rl_titile);
		this.view_pager_container = (LinearLayout) this.findViewById(R.id.view_pager_container);
		this.mScrollView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				MCLog.d("onPreDraw", "执行到了 onPreDraw");
				view_pager_container.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, mScrollView.getMeasuredHeight()));
				mScrollView.getViewTreeObserver().removeOnPreDrawListener(this);
				return true;
			}
		});
	}

	private void addValueOrListenter() {
		tv_forumtitle.setText(forumModel.getForumName());
		iv_foruminfo_img.setVisibility(getResources().getBoolean(R.bool.foruminfo_img) ? View.VISIBLE : View.GONE);
		WebViewUtil.loadContentWithPicSize(forumModel.getBody(), tv_forumBody,this);
		tv_forumrepyly.setText(forumModel.getReplyNum());
		tv_forumtopic.setText(forumModel.getTopicNum());
		tv_forumtime.setText(forumModel.getStartAndEndTime());
		initData();
		if (forumState) {
			bt_sendDiscuss.setOnClickListener(this);
		} else {
			bt_sendDiscuss.setBackgroundColor(0xececec);
		}

		ll_topic_bar.setOnClickListener(this);
		ll_reply_bar.setOnClickListener(this);

		titleView.findViewById(R.id.left_img).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				ThemeForumInfoActivity.this.finishWithResult();
			}
		});
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int postion) {
				onViewPagerChange(postion);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	private void initData() {
		listFragment = new ArrayList<Fragment>();
		mViewPager = null;
		mViewPager = (ViewPager) findViewById(R.id.foruminfo_vp_repyly);// 讨论下面的回复
		initFragment(null, forumModel.getNodeId(), courseId, forumState, forumModel.getHint());
		listFragment.add(forumTopic);
		listFragment.add(forumReply);
		mViewPager.setOffscreenPageLimit(2);
		mViewPager.setAdapter(new MyFragmentAdapter(this.getSupportFragmentManager(), this.listFragment));
		mViewPager.setCurrentItem(1);
	}

	public void initFragment(String type, String noteId, String courseId, Boolean forumState, String hint) {
		forumReply = new FragmentThemeForumReply(null, noteId, courseId, forumState, hint); // 全部
		forumTopic = new FragmentThemeForumReply("1", noteId, courseId, forumState, hint); // 精华
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.foruminfo_senddiscuss) {
			Intent intent = new Intent(this, getIntenClass());
			intent.putExtra("courseId", courseId);
			intent.putExtra("title", forumModel.getForumName());
			intent.putExtra("itemId", forumModel.getNodeId());
			startActivityForResult(intent, MCForumCommon.SEND_FORUM_CODE);
		} else if (id == R.id.foruminfo_reply_bar) {
			onViewPagerChange(1);
		} else if (id == R.id.foruminfo_topic_bar) {
			onViewPagerChange(0);
		} else {

		}

	}

	public Class getIntenClass() {
		return SandReplyActivity.class;
	}

	private void onViewPagerChange(int postion) {
		mViewPager.setCurrentItem(postion);
		if (postion == 1) {
			ll_reply_bar.setBackgroundColor(0xFFe3dfdf);
			ll_topic_bar.setBackgroundColor(0xFFF7F7F7);
		} else {
			ll_topic_bar.setBackgroundColor(0xFFe3dfdf);
			ll_reply_bar.setBackgroundColor(0xFFF7F7F7);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == MCForumCommon.SEND_FORUM_CODE && resultCode == MCForumCommon.SEND_FORUM_RESULT) {
			newNum++;
			forumReply.requestData();
			tv_forumrepyly.setText((Integer.valueOf(tv_forumrepyly.getText().toString()) + 1) + "");
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		}
	}

	/**
	 * 如果有新帖子就设置返回result
	 */
	private void finishWithResult() {
		if (newNum > 0) {
			Intent retIntent = new Intent();
			this.forumModel.setReplyNum((Integer.valueOf(this.forumModel.getReplyNum()) + newNum) + "");
			retIntent.putExtra("MCForumModel", this.forumModel);
			setResult(MCForumCommon.DETAIL_FORUM_RESULT, retIntent);
		}
		this.finish();
	}

	/**
	 * 拦截返回事件
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == 4) {
			if (forumReply.hideBoard() || forumTopic.hideBoard()) {

			} else {
				finishWithResult();
			}
			return true;
		} else {
			return super.onKeyUp(keyCode, event);
		}
	}

	public Handler getHandler() {
		return mHandler;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		WebViewUtil.DestoryWebView(tv_forumBody);

	}
}
