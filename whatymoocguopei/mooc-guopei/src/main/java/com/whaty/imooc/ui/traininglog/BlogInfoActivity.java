package com.whaty.imooc.ui.traininglog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.whaty.imooc.logic.model.BlogModel;
import com.whaty.imooc.logic.model.GPLikeListModel;
import com.whaty.imooc.logic.model.GPReplyBlogListMode;
import com.whaty.imooc.logic.model.deleteUpdateModel;
import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whaty.imooc.ui.index.GPRequestUrl;
import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.PullToRefreshBase;
import com.whaty.imooc.utile.PullToRefreshScrollView;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.utils.WebViewUtil;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;
import com.whatyplugin.imooc.ui.view.CircleImageView;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

import java.util.List;

import cn.com.whatyguopei.mooc.R;

public class BlogInfoActivity extends MCBaseActivity implements OnClickListener {
	private TextView tv_title; // 标题
	private TextView tv_name_time; // 时间姓名
	private WebView wv_content; // 日志内容
	private LinearLayout lv_ListView; // 评论列表
	private RelativeLayout ll_comments_like; // 点赞事件
	private LinearLayout ll_comments; // 评论事件
	private ImageView iv_talk_menu; // 评论，点赞菜单
	private LinearLayout ll_like_list; // 点赞列表
	private LinearLayout forum_comments_zan; // 赞 评论框
	private TextView tv_andOne; // 点赞+1 动画
	private TextView froum_zan; // 赞 字
	private LinearLayout ll_reply; // 回复框
	private ImageView iv_delete;
	private BlogModel blogModel;
	private BaseTitleView BaseTitleView;
	private GPPerformanceServiceInterface service;
	private Handler mHandler = new Handler();
	private Context context;
	private MCCreateDialog createDialog = new MCCreateDialog();
	private EditText et_comment;
	private InputMethodManager imm;
	private Handler mHnaler;
	private BroadcastReceiver receiver;
	private TextView tv_likeUser;
	private ImageView im_arrow;
	private GPLikeListModel gpLikeListModel;
	private Animation animation;
	private boolean fristCome = true;
	private Button bt_forum_reply;
	private int pageCount = 1;
	private LayoutInflater inflater;
	private boolean isReplyComment = false; // 用于判断是回帖 还是跟帖 fasle表示回复 true
	// 表示回复的回复
	private MCAnalyzeBackBlock backBlock; // 值用于回帖 和回复的
	private String replyItemId;
	private String repostId;
	private LinearLayout ll_zan_name;
	private PullToRefreshScrollView scrollView;
	private ProgressBar pb_Loading;
	private TextView tv_tags;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blog_info_activity);
		blogModel = (BlogModel) getIntent().getSerializableExtra("blogModel");
		service = new GPPerformanceService();
		context = BlogInfoActivity.this;
		updateViewCount();
		initView();
		iniEvent();
		initData();
		addFilter();
		getLikeList();
		getRequestBlogList();

	}

	/**
	 * 接收 修改之后的显示
	 *
	 */
	private void addFilter() {
		IntentFilter filter = new IntentFilter(GPContants.UPDATEBLOG);
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (GPContants.UPDATEBLOG.equals(intent.getAction())) {
					BlogModel getBlogModel = (BlogModel) intent.getSerializableExtra("blogModel");
					tv_title.setText(getBlogModel.getTitle());
					blogModel.setContent(getBlogModel.getContent());
					blogModel.setTitle(getBlogModel.getTitle());
					blogModel.setAbstractContent(getBlogModel.getAbstractContent());
					WebViewUtil.loadContentWithPic(getBlogModel.getContent(), wv_content,BlogInfoActivity.this);
				}
			}
		};
		this.registerReceiver(receiver, filter);

	}

	/**
	 * 如果按返回键
	 *
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK && ll_reply.isShown()) {
			ll_reply.setVisibility(View.GONE);
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

	private void iniEvent() {
		ll_comments_like.setOnClickListener(this);
		ll_comments.setOnClickListener(this);
		iv_delete.setOnClickListener(this);
		bt_forum_reply.setOnClickListener(this);
		BaseTitleView.setRigTextListener(new RightClickListener() {
			@Override
			public void onRightViewClick() {
				compileMyBlog();
			}
		});
	}

	private void initData() {
		mHnaler = new Handler();
		inflater = getLayoutInflater();
		imm = (InputMethodManager) et_comment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		BaseTitleView.setTitle("详情");
		tv_title.setText(blogModel.getTitle()); // 设置标题
		tv_name_time.setText(blogModel.getNameAndTime()); // 设置时间 用户名
		WebViewUtil.loadContentWithPic(blogModel.getContent(), wv_content, this);
		BaseTitleView.setRightTextVisible(blogModel.isMyBlog()); // 显示是否显示编辑按钮
		iv_delete.setVisibility(blogModel.isMyBlog() ? View.VISIBLE : View.GONE); // 是否显示删除按钮
		tv_tags.setText(blogModel.getAbstractContent()); // 显示我的tag摘要
		forum_comments_zan.setVisibility(View.GONE); // 隐藏点赞 评论
		im_arrow.setVisibility(View.GONE);
		lv_ListView.removeAllViews();
		scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
		scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				pageCount++;
				getRequestBlogList();

			}
		});
		backBlock = new MCAnalyzeBackBlock() {

			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
				// 返回结果成功，添加成功
				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS && ((deleteUpdateModel) resultList.get(0)).isSuccess()) {
					reloadReplyList();
				} else {
					MCToast.show(context, "发送失败,请稍后再试");
				}

			}

		};

	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.title);
		tv_name_time = (TextView) findViewById(R.id.tv_name_time);
		wv_content = (WebView) findViewById(R.id.content);
		iv_talk_menu = (ImageView) findViewById(R.id.talk_menu);
		lv_ListView = (LinearLayout) findViewById(R.id.mListView);
		ll_comments_like = (RelativeLayout) findViewById(R.id.forum_like);
		ll_comments = (LinearLayout) findViewById(R.id.forum_comments);
		ll_like_list = (LinearLayout) findViewById(R.id.ll_like_list);
		forum_comments_zan = (LinearLayout) findViewById(R.id.forum_comments_zan);
		tv_andOne = (TextView) findViewById(R.id.froum_zan_and_one);
		froum_zan = (TextView) findViewById(R.id.froum_zan);
		ll_reply = (LinearLayout) findViewById(R.id.reply);
		iv_delete = (ImageView) findViewById(R.id.delete_myblog);
		BaseTitleView = (BaseTitleView) findViewById(R.id.basetitle);
		et_comment = (EditText) findViewById(R.id.forum_reply_context);
		tv_likeUser = (TextView) findViewById(R.id.forum_usernames);
		im_arrow = (ImageView) findViewById(R.id.arrow);
		// ll_Comment = (LinearLayout) findViewById(R.id.ll_comment);
		bt_forum_reply = (Button) findViewById(R.id.forum_reply_pinglun);
		ll_zan_name = (LinearLayout) findViewById(R.id.zan_name);
		scrollView = (PullToRefreshScrollView) findViewById(R.id.scrollview);
		pb_Loading = (ProgressBar) findViewById(R.id.pb_loading);
		tv_tags = (TextView) findViewById(R.id.context_tag);
	}

	/**
	 * 点击事件过多
	 *
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.talk_menu: // 点击显示赞评论菜单
				openOrCloseComment();
				break;
			case R.id.forum_like: // 点赞 三件事： 1，后台 完成 阅读+ -1 ，2，本地数据完成 +1 -1
				// 3，点赞，取消点赞（已完成）
				if (blogModel.isMyBlog()) {
					MCToast.show(context, "自己是不能给自己点赞的哦");
					forum_comments_zan.setVisibility(View.GONE);
					return;
				}
				// 反选取消 点赞 +1 -1 动画
				boolean islike = froum_zan.getText().toString().equals("赞");
				froum_zan.setText(islike ? "取消" : "赞");
				openOrCloseComment();
				// 后台处理点赞
				service.likeBlog(this, blogModel.getId());
				// 处理界面显示
				if (islike) {
					gpLikeListModel.getLikeUsers().put(GPRequestUrl.getInstance().getLogId(), MCSaveData.getUserInfo("nickname", context).toString());
					addLikeNames();
				} else {

					gpLikeListModel.getLikeUsers().remove(GPRequestUrl.getInstance().getLogId());
					if (gpLikeListModel.getLikeUsers().size() == 0) {

						if (lv_ListView.getChildCount() == 0) {
							im_arrow.setVisibility(View.GONE);
							// 是否有评论的存在
							ll_like_list.setVisibility(View.GONE);
						}
					}
					setLikeNames();
				}
				// 广播通知 更新点赞人数
				blogModel.setLikeNum(String.valueOf(gpLikeListModel.getLikeUsers().size()));
				Broadcast();
				break;

			case R.id.forum_comments: // 点击出来评论框 正在弹出则关闭
				isReplyComment = false;
				// 未知情况解决第一次点击不弹出键盘
				if (fristCome) {
					fristCome = false;
					et_comment.requestFocus();
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);// 如果键盘显示就隐藏，如果隐藏就显示
					ll_reply.setVisibility(View.VISIBLE);
					forum_comments_zan.setVisibility(View.GONE);

					break;
				}
				if (ll_reply.isShown()) {
					CloseComment();
				} else {
					openComment(blogModel.getUserName());
				}
				forum_comments_zan.setVisibility(View.GONE);
				break;

			case R.id.delete_myblog: // 删除日志 弹出是否确定删除对话对话框
				deleteBlog();
				break;
			case R.id.forum_reply_pinglun:
				String comment = et_comment.getText().toString();
				if (comment == null || TextUtils.isEmpty(comment.trim())) {
					MCToast.show(context, "写几个字吧！");
					return;
				}
				ll_reply.setVisibility(View.GONE);
				if (isReplyComment) {
					service.rePeplyFollowUp(context, repostId, replyItemId, comment, backBlock);
				} else {
					service.replyBlog(context, blogModel.getId(), comment, backBlock);
				}
				imm.hideSoftInputFromWindow(et_comment.getApplicationWindowToken(), 0);
				et_comment.setText("");
				break;

			default:
				break;
		}

	}

	private void reloadReplyList() {
		lv_ListView.removeAllViews();
		pageCount = 1;
		getRequestBlogList();
	}

	// 打开评论框
	private void openComment(String replyName) {
		ll_reply.setVisibility(View.VISIBLE);
		et_comment.setHint("回复： " + replyName);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);// 如果键盘显示就隐藏，如果隐藏就显示
		et_comment.setFocusable(true);
		et_comment.requestFocus();
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (!ll_reply.isShown()) {
					CloseComment();// 关闭键盘
				}
			}
		}, 200);

	}

	// 关闭评论框
	private void CloseComment() {
		imm.hideSoftInputFromWindow(et_comment.getApplicationWindowToken(), 0);
		ll_reply.setVisibility(View.GONE);
	}

	/**
	 * 这里在oncreat里面写吧。热门日志我的日志都能兼顾 发送广播更新其他位置进行更改
	 */
	public void updateViewCount() {
		service.updateViewCount(this, blogModel.getId(), blogModel.getViewNum());
		Intent intent = new Intent(GPContants.UPDATEBLOG);
		intent.putExtra("blogModel", blogModel);
		this.sendBroadcast(intent);
	}

	private void deleteBlog() {
		final MCCommonDialog customDialog = new MCCommonDialog(null, "您的日志删除后将不能恢复，是否确定删除。", R.layout.okcancel_dialog);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				customDialog.setCommitListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						service.deleteMyBlog(context, blogModel.getId(), new MCAnalyzeBackBlock() {
							@Override
							public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
								if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
									MCToast.show(context, "删除成功");
									Intent intent = new Intent();
									setResult(2, intent);// 放入回传的值,并添加一个Code,方便区分返回的数据
									finish();
									return;
								}
								MCToast.show(context, "删除失败，请稍候重试");
							}
						});

						customDialog.dismiss();
					}
				});
			}
		}, 200);
		customDialog.show(createDialog.getFragmentTransaction(this), "删除");
	}

	/**
	 * 编辑我的日志
	 *
	 */
	private void compileMyBlog() {
		final MCCommonDialog customDialog = new MCCommonDialog(null, "建议到PC端修改，这里修改会改变样式和内容丢失！", R.layout.okcancel_dialog);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				customDialog.setCommitListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context, GPSendBlogActivity.class);
						intent.putExtra("blogModel", blogModel);
						startActivity(intent);
						customDialog.dismiss();
					}
				});
			}
		}, 200);
		customDialog.show(createDialog.getFragmentTransaction(this), "编辑");

	}

	private void getLikeList() {
		service.getLikeList(context, blogModel.getId(), new MCAnalyzeBackBlock() {

			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
				iv_talk_menu.setOnClickListener(BlogInfoActivity.this); // 数据返回之后再添加事件。。
				if (resultList == null || resultList.size() == 0) {
					froum_zan.setText("赞");
					return;
				}
				gpLikeListModel = (GPLikeListModel) resultList.get(0);
				froum_zan.setText(gpLikeListModel.getIsLike() ? "取消" : "赞");
				if (gpLikeListModel.getLikeUsers() != null && gpLikeListModel.getLikeUsers().size() > 0) {
					addLikeNames();
				}
			}
		});
	}

	/**
	 * 点击回答 滚动到你面前 ，类似微信 但是现在用的 view 非 diglog 点击屏幕不会消失。 个人感觉这个好用 故没有改成diglog
	 *
	 */
	public void scrollToSandOn(final View onClickView) {
		mHnaler.postDelayed(new Runnable() {
			@Override
			public void run() {
				int scroll2Top =0;
				if (onClickView.getY() + ll_reply.getHeight() - ll_reply.getY() > 0) {
					// 获取当前点击位置的 点击位置view高度 + 自身高度高度 -输入框的高度
					scroll2Top  =(int) (onClickView.getY() + ll_reply.getHeight() + ll_reply.getHeight() / 2);
				} else if (onClickView.getY() + ll_reply.getHeight() + onClickView.getHeight() + im_arrow.getY() + ll_zan_name.getHeight() - ll_reply.getY() > 0) {
					scroll2Top = (int) (onClickView.getY() + im_arrow.getY() + onClickView.getHeight() + ll_zan_name.getHeight() - ll_reply.getY() + ll_reply.getHeight());
				}
				scrollView.getRefreshableView().scrollTo(0,scroll2Top);
			}
		}, 200);
	}

	/**
	 * 点赞的时候不管 直接显示
	 */
	private void addLikeNames() {
		im_arrow.setVisibility(View.VISIBLE);
		ll_like_list.setVisibility(View.VISIBLE);
		setLikeNames();

	}

	/**
	 * 显示点赞评论 第二次点击 如果显示则 隐藏如果隐藏则显示
	 */
	private void openOrCloseComment() {
		forum_comments_zan.clearAnimation();
		if (forum_comments_zan.isShown()) {
			animation = AnimationUtils.loadAnimation(this, R.anim.close_like_comment);
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					forum_comments_zan.setVisibility(View.GONE);
				}
			}, 200);

		} else {
			forum_comments_zan.setVisibility(View.VISIBLE);
			animation = AnimationUtils.loadAnimation(this, R.anim.open_like_comment);
		}
		forum_comments_zan.setAnimation(animation);

	}

	/**
	 * 更新点赞列表
	 *
	 */
	private void setLikeNames() {
		ll_zan_name.setVisibility(gpLikeListModel.getLikeUsers().size() > 0 ? View.VISIBLE : View.GONE);
		zanRen(tv_likeUser,gpLikeListModel.getLikeUsers().values().toString().replace("[", "").replace("]", ""));
		ll_zan_name.postInvalidate();
	}

	private void zanRen(TextView textView,String forumreply){
		String []str = forumreply.split(",");
		if(str.length>3){
			textView.setText(str[0] + "," + str[1] + "," + str[2] + "等" + str.length + "人觉得很赞");

		}else{
			textView.setText(forumreply);
		}
	}
	// 评论列表
	public void BlogReplyListView(List<GPReplyBlogListMode> list) {
		// 设置箭头显示
		int isShow = list.size() > 0 || ll_zan_name.isShown() ? View.VISIBLE : View.GONE;
		im_arrow.setVisibility(isShow);
		ll_like_list.setVisibility(isShow);
		for (GPReplyBlogListMode blogListMode : list) {
			initDateReply(blogListMode, false);
			// 回复的内容条目

			if (blogListMode.getListReply() != null) {
				for (GPReplyBlogListMode replyMode : blogListMode.getListReply()) {
					initDateReply(replyMode, true);
				}
			}

		}

	}

	/**
	 * 填充条目的数据
	 *
	 * @param blogListMode
	 * @param isreply
	 */

	private boolean Showforum_talk = true; // 是否显示
	private void initDateReply(final GPReplyBlogListMode blogListMode, boolean isreply) {
		 final LinearLayout forum_comments_zanRP;
		 ImageView talk_menu;

		View view_vp_fragment = inflater.inflate(R.layout.blog_items_vp_fragment, null);

		View view = inflater.inflate(R.layout.blog_comment_items, null);
		forum_comments_zanRP = (LinearLayout)view_vp_fragment.findViewById(R.id.forum_comments_zan1);

		 talk_menu = (ImageView)view_vp_fragment.findViewById(R.id.talk_menu1);
		Object obj = talk_menu.getTag();

		//回复留言条目的点击事件
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isReplyComment = true;
				if (blogListMode.isReply()) {
					repostId = blogListMode.getParentId();
					replyItemId = blogListMode.getId();
				} else {
					repostId = blogListMode.getId();
					replyItemId = null;
				}
				// 取到 回复条目的ID 以便于 回复是传参数
				scrollToSandOn(v);
				openComment(blogListMode.getNickName());
			}
		});
		forum_comments_zanRP.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isReplyComment = true;
				if (blogListMode.isReply()) {
					repostId = blogListMode.getParentId();
					replyItemId = blogListMode.getId();
				} else {
					repostId = blogListMode.getId();
					replyItemId = null;
				}
				// 取到 回复条目的ID 以便于 回复是传参数
				scrollToSandOn(v);
				openComment(blogListMode.getNickName());
				forum_comments_zanRP.setVisibility(View.GONE);
			}
		});
		if(isreply){//有回复的时候
			// 姓名，时间 回复内容
			if(Showforum_talk){
				((ImageView)view.findViewById(R.id.forum_comments_image)).setVisibility(View.VISIBLE);
				((ImageView)view.findViewById(R.id.arrow)).setVisibility(View.VISIBLE);
				Showforum_talk = false;
			}else{
				((ImageView)view.findViewById(R.id.forum_comments_image)).setVisibility(View.INVISIBLE);
				((ImageView)view.findViewById(R.id.arrow)).setVisibility(View.GONE);


			}
			((TextView) view.findViewById(R.id.user_name)).setText(isreply ? blogListMode.getNikeReplyName() : blogListMode.getNickName());
			((TextView) view.findViewById(R.id.forum_pely_context_time)).setText(blogListMode.getPostTime());
			((TextView) view.findViewById(R.id.forum_reply_context)).setText(blogListMode.getDetail());
			((CircleImageView) view.findViewById(R.id.hand_image)).setImageUrl(blogListMode.getPicFileName());
			lv_ListView.addView(view);


		}else {
			((TextView) view_vp_fragment.findViewById(R.id.user_name)).setText(isreply ? blogListMode.getNikeReplyName() : blogListMode.getNickName());
			((TextView) view_vp_fragment.findViewById(R.id.repyly_time)).setText(blogListMode.getPostTime());
			((TextView) view_vp_fragment.findViewById(R.id.repyly)).setVisibility(View.VISIBLE);
			((TextView) view_vp_fragment.findViewById(R.id.repyly)).setText(blogListMode.getDetail());
			((CircleImageView) view_vp_fragment.findViewById(R.id.hand_image)).setImageUrl(blogListMode.getPicFileName());

			if (obj != null) {
				if (obj instanceof String && !obj.equals(blogListMode.getId())) {
					// 不一致的话,说明复用了对象,重新初始化View状态
					forum_comments_zanRP.setVisibility(View.GONE);				// 重新设置标记
					talk_menu.setTag(blogListMode.getId());
				} else {
					// 不一致的话,说明复用了对象,重新初始化View状态
					forum_comments_zanRP.setVisibility(View.GONE);				// 重新设置标记
					talk_menu.setTag(blogListMode.getId());
				}

			} else {
				forum_comments_zanRP.setVisibility(View.GONE);
				talk_menu.setTag(blogListMode.getId());
			}

			Showforum_talk = true;


			talk_menu.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					openOrCloseCommentReplyPerson(forum_comments_zanRP);

				}
			});

			lv_ListView.addView(view_vp_fragment);

		}

	}

	/**
	 * 显示点赞评论 第二次点击 如果显示则 隐藏如果隐藏则显示
	 */
	private void openOrCloseCommentReplyPerson(final View forum_comments_zanRP) {
		forum_comments_zanRP.clearAnimation();
		if (forum_comments_zanRP.isShown()) {
			animation = AnimationUtils.loadAnimation(this, R.anim.close_like_comment);
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					forum_comments_zanRP.setVisibility(View.GONE);
				}
			}, 200);

		} else {
			forum_comments_zanRP.setVisibility(View.VISIBLE);
			animation = AnimationUtils.loadAnimation(this, R.anim.open_like_comment);
		}
		forum_comments_zanRP.setAnimation(animation);
		forum_comments_zanRP.postInvalidate();
	}

	private void getRequestBlogList() {
		service.getReplyBlogList(context, blogModel.getId(), pageCount, new MCAnalyzeBackBlock() {
			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
				scrollView.onRefreshComplete();
				pb_Loading.setVisibility(View.GONE);
				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {

					if (resultList == null || resultList.size() == 0) {
						return;
					}
					BlogReplyListView(resultList);

				} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
					// MCToast.show(context, "没有更多数据");
				} else {
					MCToast.show(context, "刷新失败");
				}

			}
		});
	}

	/**
	 * 发送广播 更改其他的地方显示的点赞数量
	 */
	private void Broadcast() {
		Intent intent = new Intent(GPContants.UPDATEBLOG);
		intent.putExtra("blogModel", blogModel);
		this.sendBroadcast(intent);

	}

	@Override
	protected void onDestroy() {
		this.unregisterReceiver(receiver);
		super.onDestroy();
	}

}
