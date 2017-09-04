package com.whatyplugin.imooc.ui.themeforum;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.utils.DateUtil;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCThemeForumreply;
import com.whatyplugin.imooc.logic.model.MCforumreplyMode;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCFourmService;
import com.whatyplugin.imooc.logic.service_.MCFourmServiceInterface;
import com.whatyplugin.imooc.logic.utils.StringUtils;
import com.whatyplugin.imooc.logic.utils.WebViewUtil;
import com.whatyplugin.imooc.ui.base.MCBaseV4Fragment;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;
import com.whatyplugin.imooc.ui.view.CircleImageView;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnFooterRefreshListener;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnHeaderRefreshListener;

public class FragmentThemeForumReply extends MCBaseV4Fragment implements MCAnalyzeBackBlock, OnFooterRefreshListener, OnHeaderRefreshListener {
	private LinearLayout ll_zan;
	private LinearLayout ll_zan_discuss;
	private MCPullToRefreshView listView;
	private int mPage = 1;
	private MCFourmServiceInterface service;
	private QuickAdapter adapter;
	private String userName;
	private TextView tv_zan;
	private int pageSize = 10;
	private String noteId;
	private String topicType; // 获取帖子的类型 0一般1精华2灌水
	private String CourseId;
	private TextView tv_andOne;
	private boolean forumState;
	private LinearLayout ll_comment;
	// 赞或者取消赞
	private String save_zan = "0";
	private String delecte_zan = "1";
	private InputMethodManager imm;
	private String hint;
	private List<WebView> listwebView = new ArrayList<WebView>();
	private Boolean isTextView; // 如果是textview就不用webweiw
	private boolean Showforum_talk; // 是否显示
	private Animation animation;
	public ProgressBar pb_Loading;

	private FragmentThemeForumReply() {

	}

	// 根据传过来的请求地址 来判断是精华 还是回帖
	public FragmentThemeForumReply(String topicType, String noteId, String CourseId, boolean forumState, String hint) {
		this.topicType = topicType;
		this.noteId = noteId;
		this.CourseId = CourseId;
		this.forumState = forumState;
		this.hint = hint;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.themeforum_reply, null);
		listView = (MCPullToRefreshView) view.findViewById(R.id.listview);
		ll_comment = (LinearLayout) view.findViewById(R.id.forum_reply);
		pb_Loading = (ProgressBar) view.findViewById(R.id.pb_loading);
		initView();
		return view;
	}
	private void zanRen(TextView textView,MCThemeForumreply forumreply){
		String []str = forumreply.getOtherZambiaNames().split(",");
		if(str.length>3){
			textView.setText(str[0] + "," + str[1] + "," + str[2] + "等" + str.length + "人觉得很赞");

		}else{
			textView.setText(forumreply.getOtherZambiaNames());
		}
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initData();
		adapter = new QuickAdapter(this.getActivity(), R.layout.items_vp_fragment) {

			@Override
			protected void convert(final BaseAdapterHelper viewHelper, Object arg2) {
				final MCThemeForumreply forumreply = (MCThemeForumreply) arg2;
				useTextViewOrWebView(viewHelper, forumreply.getBody());

				viewHelper.setText(R.id.user_name, forumreply.getNickName());
				viewHelper.setText(R.id.repyly_time, forumreply.getTime());
				viewHelper.setImageUrl(R.id.hand_image, forumreply.getHandIamgeUrl(), MCCacheManager.getInstance().getImageLoader(), 0, 0, false, null, null);

				Object obj = viewHelper.getView(R.id.talk_menu).getTag();
				ll_zan_discuss = (LinearLayout) viewHelper.getView(R.id.froum_discuss); // 去除里面加的所有的View
				ll_zan_discuss.removeAllViews();
				if (obj != null) {
					if (obj instanceof String && !obj.equals(forumreply.getId())) {
						// 不一致的话,说明复用了对象,重新初始化View状态
						viewHelper.getView(R.id.forum_comments_zan).setVisibility(View.GONE);
						// 重新设置标记
						viewHelper.getView(R.id.talk_menu).setTag(forumreply.getId());
					} else {
						// 不一致的话,说明复用了对象,重新初始化View状态
						viewHelper.getView(R.id.forum_comments_zan).setVisibility(View.GONE);
						// 重新设置标记
						viewHelper.getView(R.id.talk_menu).setTag(forumreply.getId());
					}

				} else {
					viewHelper.getView(R.id.forum_comments_zan).setVisibility(View.GONE);
					viewHelper.getView(R.id.talk_menu).setTag(forumreply.getId());
				}
				// 不是加精的就gone掉 1 是精华 2 是普通
				viewHelper.setVisible(R.id.forum_cream, forumreply.isDigest());
				LayoutInflater infater = getLayoutInflater(getArguments());
				View itemLayout = infater.inflate(R.layout.forum_items_reply, null);
				final LinearLayout ll_forum_like = (LinearLayout) itemLayout.findViewById(R.id.forum_like);
				if (forumreply.isOtherUserZambia()) {
					ll_zan_discuss.setVisibility(View.VISIBLE);
					itemLayout.findViewById(R.id.forum_talk).setVisibility(Showforum_talk ? View.VISIBLE : View.GONE);
					ll_forum_like.setVisibility(View.VISIBLE);
					TextView tv_likeNames = ((TextView) itemLayout.findViewById(R.id.forum_usernames));
					zanRen(tv_likeNames,forumreply);

					ll_zan_discuss.addView(itemLayout);
				} else {
					ll_zan_discuss.setVisibility(View.GONE);
					ll_forum_like.setVisibility(View.GONE);
				}

				// 获取评论
				if (forumreply.isComment()) {
					List<MCforumreplyMode> list = forumreply.getList();
					int index = 0;
					for (final MCforumreplyMode listReply : list) {
						itemLayout = infater.inflate(R.layout.forum_items_reply, null);
						// 修改配置文件
						itemLayout.findViewById(R.id.forum_talk).setVisibility(Showforum_talk ? View.VISIBLE : View.GONE);

						View firstImage = itemLayout.findViewById(R.id.forum_comments);
						firstImage.setVisibility(index == 0 ? View.VISIBLE : View.INVISIBLE);

						if (index != 0 || forumreply.isOtherUserZambia()) { // 箭头是否显示
							itemLayout.findViewById(R.id.arrow).setVisibility(View.GONE);
						}
						index = 2; // 这里随便给值...
						// 填充回复评论数据
						RepleyOtherUser(itemLayout, forumreply, listReply);
						ll_zan_discuss.setVisibility(View.VISIBLE);
						ll_zan_discuss.addView(itemLayout);
					}
				}

				// 过期的帖子，不设置点击事件
				hasReply(viewHelper, forumreply.getZambia());

				// 点赞处理
				viewHelper.getView().findViewById(R.id.forum_like).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String isZan = tv_zan.getText().toString();
						String zanNames = forumreply.getOtherZambiaNames();
						if ("赞".equals(isZan)) {
							forumreply.setZambia(true);
							forumreply.setOtherUserZambia(true);
							// 动画效果
							tv_andOne = (TextView) viewHelper.getView().findViewById(R.id.froum_zan_and_one);
							andOneAnimation(tv_andOne);
							// 赞_后台处理
							saveOrDaleteZan(forumreply.getId(), forumreply.getParentId(), save_zan);

						} else {
							// 取消赞_后台处理
							saveOrDaleteZan(forumreply.getId(), forumreply.getParentId(), delecte_zan);
							forumreply.setZambia(false);
							tv_zan.setText("赞");

						}
						// 点过赞之后就消失
						if (tv_andOne != null) {
							new Handler().postDelayed(new Runnable() {
								public void run() {
									ll_zan.setVisibility(View.GONE);
									tv_andOne.setVisibility(View.GONE);
									tv_andOne = null;
								}
							}, 500);
						} else {
							ll_zan.setVisibility(View.GONE);
						}

						// 点赞事件处理
						if (forumreply.getZambia()) {
							zanNames = forumreply.getOtherZambiaNames();
							forumreply.setOtherZambiaNames((zanNames != null && !zanNames.equals("")) ? zanNames + "," + userName : userName);
						} else { // 取消点赞
							// 以这个开始 中间 ，结束
							forumreply.setOtherZambiaNames(zanNames.startsWith(userName + ",") ? zanNames.replace(userName + ",", "") : zanNames
									.equals(userName) ? null : zanNames.replaceAll("," + userName, ""));
							if (zanNames.equals(userName)) {
								viewHelper.getView(R.id.froum_discuss).setVisibility(View.GONE);
								forumreply.setOtherUserZambia(false);
								ll_forum_like.setVisibility(View.GONE);
								ll_zan_discuss.removeAllViews();
							}
						}

						localNotifyDataSetChanged();
					}

				});

				// 点击评论事件
				viewHelper.getView().findViewById(R.id.forum_comments).setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						ll_zan.setVisibility(View.GONE);
						// 评论帖子
						replyView(" 回复：" + forumreply.getNickName(), v, forumreply, null);

					}
				});

			}

		};

		listView.setDataAdapter(adapter);

		super.onActivityCreated(savedInstanceState);
		requestData();
	}

	private void replyView(String str_reply, View v, final MCThemeForumreply forumreply, final MCforumreplyMode forum) {
		final EditText pustCommebt = (EditText) ll_comment.findViewById(R.id.forum_reply_context);
		if (ll_comment.getVisibility() == View.VISIBLE) {
			// 保存临时草稿
			String str_pustCommebt = pustCommebt.getText().toString();
			if (forum != null) {
				forum.setTemp_draft(str_pustCommebt);
			} else {
				forumreply.setTempDraft(str_pustCommebt);
			}
			pustCommebt.setText("");// 初始化控件
			ll_comment.setVisibility(View.GONE);
			if (ll_zan != null) {
				ll_zan.setVisibility(View.GONE);
			}

			// 隐藏输入法
			if (this.imm != null && this.imm.isActive()) {
				imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
			}
			return;
		}
		ll_comment.setVisibility(View.VISIBLE);

		// 文字提示
		pustCommebt.setHint(str_reply);
		// 设置临时草稿
		if (forum != null && forum.getTemp_draft() != null) {
			pustCommebt.setText(forum.getTemp_draft());
		} else if (forumreply.getTempDraft() != null) {
			pustCommebt.setText(forumreply.getTempDraft());
		}

		pustCommebt.requestFocus();

		imm.showSoftInput(pustCommebt, InputMethodManager.SHOW_FORCED);
		Button bt_comment = (Button) ll_comment.findViewById(R.id.forum_reply_pinglun);
		Message msg = new Message();
		msg.what = 0;
		msg.obj = ll_comment;
		getHandler().sendMessageDelayed(msg, 250);
		bt_comment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String str_comment = pustCommebt.getText().toString();
				if (StringUtils.isWhiteSpace(str_comment)) {
					Toast.makeText(getActivity(), "输入内容不能为空！", Toast.LENGTH_LONG).show();
					return;
				}
				pustCommebt.setText("");// 初始化控件
				// 初始化临时草稿
				if (forum != null)
					forum.setTemp_draft(null);
				forumreply.setTempDraft(null);

				forumreply.setComment(true);
				// 在这里发送出去
				MCforumreplyMode sandReply = new MCforumreplyMode();
				sandReply.setDetail(str_comment); // 添加内容
				sandReply.setParentId(forumreply.getId());// 主贴ID
				sandReply.setNickName(userName); // 回复用户名
				sandReply.setPostTime(DateUtil.getNow(DateUtil.FORMAT_YY_MM_DD).replaceAll("\\.0", ".")); // 回复时间
				sandReply.setPicFileUrl(MCSaveData.getUserInfo("pic", MoocApplication.getInstance()).toString());// 头像
				sandReply.setOnlyReply(false);
				sandReply.setReplyNickname(forum == null ? forumreply.getNickName() : forum.getNickName()); // 回复对方的名称
				sendComment(forumreply.getId(), forum == null ? null : forum.getId(), str_comment); // 发送跟帖的评论
				forumreply.getList().add(sandReply);

				adapter.notifyDataSetChanged();
				// 隐藏评论
				ll_comment.setVisibility(View.GONE);
				// 隐藏点赞
				imm.hideSoftInputFromWindow(pustCommebt.getWindowToken(), 0);
				if (ll_zan != null) // 点击回复的时候 这里是空的
					ll_zan.setVisibility(View.GONE);
			}
		});

	}

	public void initView() {
		this.listView.setOnFooterRefreshListener(this);
		this.listView.setOnHeaderRefreshListener(this);

	}

	/**
	 * 重写次方法可以添加新的需要初始化的数据
	 * 
	 */

	public void initData() {
		this.service = new MCFourmService();
		imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		userName = MCSaveData.getUserInfo("nickname", this.getActivity()).toString();
		isTextView = getActivity().getResources().getBoolean(R.bool.istextview);
		Showforum_talk = getActivity().getResources().getBoolean(R.bool.showtalk);

	}

	// 获取列表
	public void requestData() {
		service.getRepylyListByNoteId(CourseId, noteId, mPage, pageSize, topicType, ((MCAnalyzeBackBlock) this), getActivity());
	}

	// 保存或者删除点赞 参数 0为保存 1位删除

	public void saveOrDaleteZan(String postId, String mainId, String opt) {
		service.saveOrDeleteZan(postId, mainId, opt, ((MCAnalyzeBackBlock) this), getActivity());
	}

	// 发送评论，或者发送跟帖的评论
	public void sendComment(String postId, String mainId, String detail) {
		service.sendComment(postId, mainId, detail, ((MCAnalyzeBackBlock) this), getActivity());
	}

	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
		this.listView.onFooterRefreshComplete();
		this.listView.onHeaderRefreshComplete();
		pb_Loading.setVisibility(View.GONE);
		// 没有网络
		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
			this.listView.setGuidanceViewWhenNoNet();
			return;
		}
		// 没有数据
		if ((resultList == null || resultList.size() == 0) && this.mPage == 1) {
			this.listView.setGuidanceViewWhenNoData(R.drawable.no_note_icon, "1".equals(this.topicType) ? "暂无精华帖！" : hint);
			return;
		}
		//没有数据就不去加载 禁止下拉刷新
		if((resultList == null || resultList.size() == 0) && this.mPage != 1){
			this.listView.setAllowFooterPull(false);
			return;
		}
		
		this.listView.setAdapterViewWhenHasData();
		if (mPage == 1) {
			adapter.clear();
		}
		adapter.addAll(resultList);
	}

	@Override
	public void onFooterRefresh(MCPullToRefreshView arg1) {
		++mPage;
		requestData();
	}

	@Override
	public void onHeaderRefresh(MCPullToRefreshView arg1) {
		mPage = 1;
		requestData();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 回收所有的webView,手动执行清除
		for (WebView webView : listwebView) {
			WebViewUtil.DestoryWebView(webView);
		}
		listwebView.clear();
	}

	/**
	 * 隐藏输入法及评论框
	 * 
	 * @return
	 */
	public boolean hideBoard() {
		boolean flag = false;

		// 隐藏输入法
		if (this.imm != null && this.imm.isActive()) {
			View view = this.getActivity().getCurrentFocus();
			if (view != null) {
				this.imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}

		if (ll_comment.isShown()) {
			this.ll_comment.setVisibility(View.GONE);
			flag = true;
		}
		return flag;

	}

	public Handler getHandler() {
		ThemeForumInfoActivity activity = (ThemeForumInfoActivity) this.getActivity();
		return activity.getHandler();
	}

	/**
	 * 显示点赞评论 第二次点击 如果显示则 隐藏如果隐藏则显示
	 */
	private void openOrCloseComment(final View forum_comments_zan) {
		forum_comments_zan.clearAnimation();
		if (forum_comments_zan.isShown()) {
			animation = AnimationUtils.loadAnimation(getActivity(), R.anim.close_like_comment);
			getHandler().postDelayed(new Runnable() {
				@Override
				public void run() {
					forum_comments_zan.setVisibility(View.GONE);
				}
			}, 200);

		} else {
			forum_comments_zan.setVisibility(View.VISIBLE);
			animation = AnimationUtils.loadAnimation(getActivity(), R.anim.open_like_comment);
		}
		forum_comments_zan.setAnimation(animation);

	}

	/**
	 * 使用 textview 还是使用 webview
	 * 
	 */
	private int useTextViewOrWebView(BaseAdapterHelper viewHelper, String conment) {
		return isTextView ? useTextView(viewHelper, conment) : useWebView(viewHelper, conment);
	}

	/**
	 * 使用textview
	 * 
	 */
	private int useTextView(BaseAdapterHelper viewHelper, String conment) {
		viewHelper.setVisible(R.id.repyly, true);
		viewHelper.setText(R.id.repyly, conment);
		Log.e("ISTEXTVIEW", "使用的是textview");
		return 1;
	}

	/**
	 * 使用webView 如果 没有图 默认使用textview
	 */

	private int useWebView(BaseAdapterHelper viewHelper, String conment) {

		WebView wv_content = (WebView) viewHelper.getView(R.id.web_repyly);
		wv_content.setVisibility(View.VISIBLE);
		WebViewUtil.loadContentWithPic(conment, wv_content,getActivity());
		listwebView.add(wv_content);
		wv_content = null;
		Log.e("ISTEXTVIEW", "使用的是webview");
		return 2;
	}

	/**
	 * 帖子是否具有回复功能 过期就不具备回复功能 只能看
	 */
	private void hasReply(final BaseAdapterHelper viewHelper, final Boolean isLike) {

		if (forumState) {
			// 处理点击发送按钮的点击事件
			viewHelper.setImageOnClickListener(R.id.talk_menu, new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					ll_zan = (LinearLayout) viewHelper.getView().findViewById(R.id.forum_comments_zan);
					openOrCloseComment(ll_zan);
					tv_zan = (TextView) viewHelper.getView().findViewById(R.id.froum_zan);
					tv_zan.setText(isLike ? "取消" : "赞");
				}

			});
		} else {
			// 去掉多余的view 这样看起来好看点
			viewHelper.setVisible(R.id.forum_pelypy, false);
		}
	}

	/**
	 * 评论回复,填充数据列表
	 * 
	 */

	private void RepleyOtherUser(View itemLayout, final MCThemeForumreply forumreply, final MCforumreplyMode listReply) {
		LinearLayout ll_context = (LinearLayout) itemLayout.findViewById(R.id.forum_context);
		// 显示 评论
		ll_context.setVisibility(View.VISIBLE);

		if (forumState) {
			ll_context.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String str_reply_name = ((TextView) v.findViewById(R.id.user_name)).getText().toString();
					// 回复某人的评论
					replyView(" 回复：" + str_reply_name, v, forumreply, listReply);

				}
			});
		}
		// 用户名
		TextView tv_user_name = (TextView) ll_context.findViewById(R.id.user_name);
		tv_user_name.setText(listReply.getNickName());
		// 回复两个字
		ll_context.findViewById(R.id.forum_pely).setVisibility(View.VISIBLE);

		// 被回复人名称
		TextView tv_user_name_reply = (TextView) ll_context.findViewById(R.id.forum_pely_user_name);
		tv_user_name_reply.setText(listReply.getReplyNickname());

		tv_user_name_reply.setVisibility(View.VISIBLE);

		// 头像
		CircleImageView Civ_handImage = (CircleImageView) ll_context.findViewById(R.id.hand_image);
		Civ_handImage.setImageUrl(listReply.getPicFileUrl());

		// 回复时间
		TextView tv_time = (TextView) ll_context.findViewById(R.id.forum_pely_context_time);
		tv_time.setText(listReply.getPostTime());

		// 回复内容
		TextView tv_froum_comment = (TextView) ll_context.findViewById(R.id.forum_reply_context);
		tv_froum_comment.setText(listReply.getDetail());

	}

	/**
	 * 刷新主界面
	 * 
	 */

	private void localNotifyDataSetChanged() {
		// 延迟进行刷新 不然的话 当只有一个条目的时候 会出问题
		getHandler().postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.notifyDataSetChanged();
			}
		}, 100);

	}

	/**
	 * 加+ 的动画配置
	 * 
	 */
	public void andOneAnimation(TextView tv_andOne) {
		// 动画效果
		Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_andone);
		tv_andOne.setVisibility(View.VISIBLE);
		tv_andOne.setAnimation(animation);
	}

}
