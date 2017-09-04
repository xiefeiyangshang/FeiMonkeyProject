package com.whaty.imooc.ui.index;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whaty.imooc.ui.assessment.GPAssessmentCriteriaActivity;
import com.whaty.imooc.ui.assessment.GPPerformanceFragment;
import com.whaty.imooc.ui.course.GPCourseFragment;
import com.whaty.imooc.ui.homework.GPHomeWorkMianFragment;
import com.whaty.imooc.ui.login.ClassDialog;
import com.whaty.imooc.ui.notic.GPNoticListFreament;
import com.whaty.imooc.ui.setting.MCPersonInformationActivity;
import com.whaty.imooc.ui.setting.MCSettingFragment;
import com.whaty.imooc.ui.traininglog.GPSendBlogActivity;
import com.whaty.imooc.ui.traininglog.TrainingLogFragment;
import com.whaty.imooc.ui.workshop.SchoolBasedFragment;
import com.whaty.imooc.ui.workshop.WorkShopMainFragment;
import com.whaty.imooc.utile.GPContants;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.login.MCLoginBackListener;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.manager.MCManager;
import com.whatyplugin.imooc.logic.model.MCCourseTypeModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.proxy.MCLoginProxy;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.ui.base.MCBaseV4Fragment;
import com.whatyplugin.imooc.ui.download.DownloadFragment;
import com.whatyplugin.imooc.ui.homework.MCAllMyHomeWorkFragment;
import com.whatyplugin.imooc.ui.homework.MCMyHomeWorkSearchActivity;
import com.whatyplugin.imooc.ui.mymooc.EmptyFragment;
import com.whatyplugin.imooc.ui.note.MCAllNoteMenuFragment;
import com.whatyplugin.imooc.ui.question.MCAllQuestionMenuFragment;
import com.whatyplugin.imooc.ui.search.MCAllCourseSearchActivity;
import com.whatyplugin.imooc.ui.search.MCMyNoteSearchActivity;
import com.whatyplugin.imooc.ui.search.MCMyQuestionSearchActivity;
import com.whatyplugin.imooc.ui.view.AllCourseTypeView;
import com.whatyplugin.imooc.ui.view.AllCourseTypeView.IOnCourseTypeItemClick;
import com.whatyplugin.imooc.ui.view.CircleImageView;
import com.whatyplugin.imooc.ui.view.SlidingTitileView;
import com.whatyplugin.imooc.ui.view.SlidingTitileView.IMidListener;
import com.whatyplugin.imooc.ui.view.SlidingTitileView.IRightListener;
import com.whatyplugin.imooc.ui.view.SlidingTitileView.IRightTextListener;
import com.whatyplugin.uikit.resolution.MCResolution;
import com.whatyplugin.uikit.toast.MCToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.whatyguopei.mooc.R;

@SuppressLint("ValidFragment")
public class MCMenuItemFragment extends MCBaseV4Fragment implements View.OnClickListener, MCLoginBackListener, MCAnalyzeBackBlock, IOnCourseTypeItemClick {
	private static final String TAG = "MCMenuItemFragment";
	private boolean arrowDown;
	private ImageView arrowImageView;
	private String currentCourseTypeName;
	private ImageView download_img;
	public SparseArray fragments;
	private boolean isClick;
	private boolean isCourseTypeViewShow;
	private ImageView login_ic;
	private FrameLayout mCourseTypeLayout;
	private AllCourseTypeView mCourseTypeView;
	private Handler mHandler;
	private ImageView myquestion_icon;
	private CircleImageView mHeadImageView;
	private SlidingTitileView mSlidingTitle;
	private IMidListener midListener;
	private TextView name;
	private Fragment newContent;
	private ImageView myquestion_img;
	private BroadcastReceiver receiver;
	private IRightListener rightListener;
	private String tag;
	private Map types;
	private String uid;

	// 菜单上的6个选中label
	private ImageView studyfrist_img;
	private ImageView workshopactivities_img;
	private ImageView mycourse_img;
	private ImageView mymessage_img;
	private ImageView mynote_img;
	private ImageView newmessage_img;
	private ImageView setting_img;
	private ImageView fieldpractice_img;
	private ImageView mymark_img;
	private ImageView myHomeWork_img;
	private ImageView notice_img;

	// 菜单中的字
	private TextView label_studyfrist;
	private TextView label_mycourse;
	private TextView label_workshopactivities;
	private TextView label_download;
	private TextView label_mynote;
	private TextView label_mymessage;
	private TextView label_setting;
	private TextView label_myquestion;
	private TextView label_myhomework;
	// private TextView label_fieldpractice;
	private TextView label_mymark;
	private TextView label_notice;

	// 菜单中的图标
	private ImageView studyfrist_icon;
	private ImageView fieldpractice_icon;
	private ImageView mymark_icon;
	private ImageView setting_icon;
	private ImageView mycourse_icon;
	private ImageView workshopactivities_icon;
	private ImageView download_icon;
	private ImageView mymessage_icon;
	private ImageView mynote_icon;
	private ImageView myhomework_icon;
	private ImageView notice_icon;

	// private View fieldpractice_layout;
	private View myQuestionLayout;
	private View myCourseLayout;
	// 切换班级的点击事件
	private RelativeLayout rl_ChangBanji;
	private TextView tv_ClassName;

	// 校本研修
	private final String JIAOBENYANXIU = "1";
	private final String GONGZUOFANGHUODONG = "0";


	public MCMenuItemFragment() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public MCMenuItemFragment(final SparseArray fragments) {
		this.types = new HashMap();
		this.tag = "";
		this.newContent = (Fragment) fragments.get(R.id.mycourse_layout);
		this.uid = Contants.DEFAULT_UID;
		this.arrowDown = true;
		this.isClick = false;
		this.rightListener = new IRightListener() {
			public void onRightListener() {
				if (newContent instanceof MCAllNoteMenuFragment) {
					MCMenuItemFragment.this.getActivity().startActivity(new Intent(MCMenuItemFragment.this.getActivity(), MCMyNoteSearchActivity.class));
				} else if (newContent instanceof GPCourseFragment) {
					MCMenuItemFragment.this.getActivity().startActivity(new Intent(MCMenuItemFragment.this.getActivity(), MCAllCourseSearchActivity.class));
				} else if (newContent instanceof MCAllQuestionMenuFragment) {
					MCMenuItemFragment.this.getActivity().startActivity(new Intent(MCMenuItemFragment.this.getActivity(), MCMyQuestionSearchActivity.class));
				} else if (newContent instanceof MCAllMyHomeWorkFragment) {
					MCMenuItemFragment.this.getActivity().startActivity(new Intent(MCMenuItemFragment.this.getActivity(), MCMyHomeWorkSearchActivity.class));
				}
			}
		};

		// 处理消息
		this.mHandler = new Handler() {
			public void handleMessage(Message msg) {
				MCServiceResult result = (MCServiceResult) msg.obj;
				if (((MCServiceResult) result).getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS) {
					if (((MCServiceResult) result).getResultCode() != MCResultCode.MC_RESULT_CODE_EMPTY && ((result).isExposedToUser())) {
						MCToast.show(MCMenuItemFragment.this.getActivity(), (result).getResultDesc());
					}

					super.handleMessage(msg);
				}
			}
		};
		// 定义一个广播接收者 onReceive 做不同处理
		this.receiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(GPContants.REFESHHANDPHONE)) {
					MCMenuItemFragment.this.refreshAfterLogin();
					setClassName();
					if (myCourseLayout != null)
						onClick(myCourseLayout);
				} else if (intent.getAction().equals(Contants.USER_LOGIN_ACTION)) {
					MCMenuItemFragment.this.refreshAfterLogin();
					setClassName();
					if (myCourseLayout != null)
						onClick(myCourseLayout);
				} else if (intent.getAction().equals(Contants.USER_LOGOUT_ACTION)) {
					MCMenuItemFragment.this.refreshAfterLogout();
					MCMenuItemFragment.this.updateNewMsgImage();
				} else if (intent.getAction().equals(Contants.USER_UPDATE_HANDIMG_ACTION)) {
					MCMenuItemFragment.this.mHeadImageView.setImageBitmap(BitmapFactory.decodeFile(intent.getStringExtra("path")));
				} else {
					if (!intent.getAction().equals(Contants.UPDATE_UNREAD_NUM_ACTION) && !intent.getAction().equals(Contants.QUERY_MESSAGE_FINISH_ACTION)) {
						return;
					}

					MCMenuItemFragment.this.updateNewMsgImage();
				}
			}
		};
		// MCFragmentChangeActiviy中的 fragments 传递过来。
		this.fragments = fragments;

	}

	private void setClassName() {
		tv_ClassName.setText(GPChangeClassUtile.getClassName());
		tv_ClassName.postInvalidate();

	}

	/**
	 * 相应解析后的数据
	 */
	public void OnAnalyzeBackBlock(MCServiceResult result, List arg6) {
		Message msg = new Message();
		msg.what = 0;
		msg.obj = result;
		this.mHandler.sendMessageDelayed(msg, 500);
	}

	protected void arrowAnim(ImageView imageView) {
		RotateAnimation animation;
		float toDegrees = -180f;
		float pivotXValue = 0.5f;
		if (this.arrowDown) {
			animation = new RotateAnimation(0f, toDegrees, 1, pivotXValue, 1, pivotXValue);
			this.arrowDown = false;
		} else {
			animation = new RotateAnimation(toDegrees, 0f, 1, pivotXValue, 1, pivotXValue);
			this.arrowDown = true;
		}

		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(200);
		animation.setFillAfter(true);
		imageView.clearAnimation();
		imageView.startAnimation(animation);
	}

	protected void courseTypeViewAnim(boolean isShowView, FrameLayout frame, final Fragment fragment) {
		int anim; // R.anim.course_type_view_anim_in
		if (frame == null) {
			throw new IllegalArgumentException("frame param can not be null");
		}

		if (isShowView) {
			anim = R.anim.course_type_view_anim_in; // R.anim.course_type_view_anim_in
			this.isCourseTypeViewShow = true;
			if (this.isClick) {
				this.isClick = false;
			}
		} else {
			anim = R.anim.course_type_view_anim_out; // R.anim.course_type_view_anim_out
			this.isCourseTypeViewShow = false;
		}

		Animation animation = AnimationUtils.loadAnimation(this.getActivity(), anim);
		if (fragment != null && (this.isClick)) {
		}

		frame.startAnimation(animation);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View infoLayout = this.getActivity().findViewById(R.id.info_layout);
		if (infoLayout == null) {
			return;
		}
		initView();

		initEvent(infoLayout);

		initData();
	}

	private void initData() {
		setClassName();
		// 一开始我的课程是高亮的
		this.mycourse_img.setVisibility(View.VISIBLE);
		this.mycourse_icon.setImageResource(R.drawable.silding_mycoursework_red);
		this.label_mycourse.setTextColor(this.getActivity().getResources().getColor(R.color.theme_color));

		ViewGroup.LayoutParams params = this.mHeadImageView.getLayoutParams();
		params.width = MCResolution.getInstance(this.getActivity()).scaleWidth(Contants.USER_PIC_WIDTH_SLIDING);
		params.height = params.width;
		this.mHeadImageView.setLayoutParams(params);
		this.login_ic = (ImageView) this.getActivity().findViewById(R.id.login_pic);
		this.login_ic.setOnClickListener(this);
		this.uid = MCSaveData.getUserInfo(Contants.UID, this.getActivity()).toString();
		if (this.uid.equals(Contants.DEFAULT_UID)) {
			this.login_ic.setVisibility(View.VISIBLE);
			this.mHeadImageView.setVisibility(View.INVISIBLE);
			this.name.setVisibility(View.INVISIBLE);
		} else {
			this.refreshAfterLogin();
			this.updateNewMsgImage();
		}

		IntentFilter filter = new IntentFilter();
		filter.addAction(Contants.USER_LOGIN_ACTION);
		filter.addAction(Contants.USER_LOGOUT_ACTION);
		filter.addAction(Contants.UPDATE_UNREAD_NUM_ACTION);
		filter.addAction(Contants.QUERY_MESSAGE_FINISH_ACTION);
		filter.addAction(Contants.USER_UPDATE_HANDIMG_ACTION);
		filter.addAction(GPContants.REFESHHANDPHONE);
		this.getActivity().registerReceiver(this.receiver, filter);
		// changeClassMenu();
	}

	private void initView() {
		this.label_mycourse = (TextView) this.getActivity().findViewById(R.id.label_mycourse);
		this.label_workshopactivities = (TextView) this.getActivity().findViewById(R.id.label_workshopactivities);
		this.label_download = (TextView) this.getActivity().findViewById(R.id.label_download);
		this.label_mynote = (TextView) this.getActivity().findViewById(R.id.label_mynote);
		this.label_mymessage = (TextView) this.getActivity().findViewById(R.id.label_mymessage);
		this.label_setting = (TextView) this.getActivity().findViewById(R.id.label_setting);
		this.label_myquestion = (TextView) this.getActivity().findViewById(R.id.label_myquestion);
		this.label_myhomework = (TextView) this.getActivity().findViewById(R.id.label_myhomework);
		this.label_studyfrist = (TextView) this.getActivity().findViewById(R.id.label_studyfrist);
		// this.label_fieldpractice = (TextView)
		// this.getActivity().findViewById(R.id.label_fieldpractice);
		this.label_mymark = (TextView) this.getActivity().findViewById(R.id.label_mymark);
		this.label_notice = (TextView) this.getActivity().findViewById(R.id.label_notice);

		// this.fieldpractice_icon = (ImageView)
		// this.getActivity().findViewById(R.id.fieldpractice_icon);
		this.mymark_icon = (ImageView) this.getActivity().findViewById(R.id.mymark_icon);
		this.studyfrist_icon = (ImageView) this.getActivity().findViewById(R.id.studyfirst_img_icon);
		this.setting_icon = (ImageView) this.getActivity().findViewById(R.id.setting_icon);
		this.mycourse_icon = (ImageView) this.getActivity().findViewById(R.id.mycourse_icon);
		this.workshopactivities_icon = (ImageView) this.getActivity().findViewById(R.id.workshopactivities_icon);
		this.download_icon = (ImageView) this.getActivity().findViewById(R.id.download_icon);
		this.mymessage_icon = (ImageView) this.getActivity().findViewById(R.id.mymessage_icon);
		this.mynote_icon = (ImageView) this.getActivity().findViewById(R.id.mynote_icon);
		this.myquestion_icon = (ImageView) this.getActivity().findViewById(R.id.myquestion_icon);
		this.notice_icon = (ImageView) this.getActivity().findViewById(R.id.notice_icon);

		// this.fieldpractice_img = (ImageView)
		// this.getActivity().findViewById(R.id.fieldpractice_img);
		this.mymark_img = (ImageView) this.getActivity().findViewById(R.id.mymark_img);
		this.studyfrist_img = (ImageView) this.getActivity().findViewById(R.id.studyfirst_img);
		this.workshopactivities_img = (ImageView) this.getActivity().findViewById(R.id.workshopactivities_img);
		this.mycourse_img = (ImageView) this.getActivity().findViewById(R.id.mycourse_img);
		this.download_img = (ImageView) this.getActivity().findViewById(R.id.download_img);
		this.mymessage_img = (ImageView) this.getActivity().findViewById(R.id.mymessage_img);
		this.newmessage_img = (ImageView) this.getActivity().findViewById(R.id.new_image);
		this.mynote_img = (ImageView) this.getActivity().findViewById(R.id.mynote_img);
		this.setting_img = (ImageView) this.getActivity().findViewById(R.id.setting_img);
		this.myHomeWork_img = (ImageView) this.getActivity().findViewById(R.id.myhomework_img);
		this.notice_img = (ImageView) this.getActivity().findViewById(R.id.notice_img);

		this.name = (TextView) this.getActivity().findViewById(R.id.nickname);
		this.mHeadImageView = (CircleImageView) this.getActivity().findViewById(R.id.pic);
		this.myquestion_img = (ImageView) this.getActivity().findViewById(R.id.myquestion_img);
		this.myhomework_icon = (ImageView) this.getActivity().findViewById(R.id.myhomework_icon);
		// 切换班级
		this.rl_ChangBanji = (RelativeLayout) getActivity().findViewById(R.id.changbanji_layout);
		this.tv_ClassName = (TextView) getActivity().findViewById(R.id.my_banji);

		MCFragmentChangeActivity aaa = (MCFragmentChangeActivity) this.getActivity();
		this.mSlidingTitle = aaa.getmSlidingmSlidingTitle();
		this.arrowImageView = this.mSlidingTitle.getArrowImageView();
		this.mCourseTypeLayout = aaa.getCourseTypeViewLayout();
		this.mCourseTypeView = new AllCourseTypeView(this.getActivity());
		this.mCourseTypeLayout.addView(this.mCourseTypeView);

	}

	private void initEvent(View infoLayout) {
		// fieldpractice_layout =
		// this.getActivity().findViewById(R.id.fieldpractice_layout);
		View mymark_layout = this.getActivity().findViewById(R.id.mymark_layout);
		View studyFirstLayout = this.getActivity().findViewById(R.id.studyfirst_layout);
		View workshopactivitiesLayout = this.getActivity().findViewById(R.id.workshopactivities_layout);
		myCourseLayout = this.getActivity().findViewById(R.id.mycourse_layout);
		View downloadLayout = this.getActivity().findViewById(R.id.download_layout);
		View myMessageLayout = this.getActivity().findViewById(R.id.mymessage_layout);
		View myNoteLayout = this.getActivity().findViewById(R.id.mynote_layout);
		View settingLayout = this.getActivity().findViewById(R.id.setting_layout);
		myQuestionLayout = this.getActivity().findViewById(R.id.myquestion_layout);
		View myHomeWorkLayout = this.getActivity().findViewById(R.id.myhomework_layout);
		View noticeLayout = this.getActivity().findViewById(R.id.notice_layout);

		// fieldpractice_layout.setOnClickListener(this);
		mymark_layout.setOnClickListener(this);
		studyFirstLayout.setOnClickListener(this);
		infoLayout.setOnClickListener(this);
		workshopactivitiesLayout.setOnClickListener(this);
		myCourseLayout.setOnClickListener(this);
		downloadLayout.setOnClickListener(this);
		myMessageLayout.setOnClickListener(this);
		myNoteLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
		myQuestionLayout.setOnClickListener(this);
		myHomeWorkLayout.setOnClickListener(this);
		noticeLayout.setOnClickListener(this);
		this.mHeadImageView.setOnClickListener(this);
		this.mCourseTypeView.setOnCourseTypeItemClick(this);
		this.mSlidingTitle.setOnRightListener(this.rightListener);
		rl_ChangBanji.setOnClickListener(this);
	}

	// 主菜单的点击事件处理
	public void onClick(View v) {
		// 如果没有登陆的话
		if (!this.isLogined()) {
			MCLoginProxy.loginInstance().login(this.getActivity(), this);
			return;
		}
		// 如果没有选择班级，先选择班级
		if (GPChangeClassUtile.isChandClass() && v.getId() != R.id.setting_layout) {
			// TODO 弹出选择班级对话框
//			GPChangeClassUtile.createDialog(getActivity());
			GPChangeClassUtile.check(getActivity());
			return;
		}
		setClassName();
		Fragment fragment = null;
		this.newContent = fragment;
		if ((this.isCourseTypeViewShow) && this.mCourseTypeLayout != null) {
			this.mCourseTypeLayout.setVisibility(View.GONE);
			this.courseTypeViewAnim(false, this.mCourseTypeLayout, fragment);
		}

		if (this.mSlidingTitle.getArrowImageView() != null) {
			this.mSlidingTitle.getArrowImageView().clearAnimation();
		}

		switch (v.getId()) {
		case R.id.changbanji_layout: {
//			if (GPChangeClassUtile.StuHaveOneClass()) {
//				MCToast.show(getActivity(), "您就一个班级不需要切换滴");
//				return;
//			}
			// TODO 弹出选择班级对话框
			GPChangeClassUtile.check(getActivity());
//			GPChangeClassUtile.createDialog(getActivity());
			break;
		}
		case R.id.studyfirst_layout: {// 学习首页
			// 设置字体色
			this.changeTextColor(this.label_studyfrist);

			// 设置选中效果
			this.changeImageLabel(this.studyfrist_img);
			this.changeImageResource(this.studyfrist_icon, R.drawable.sliding_studyfirst_icon_on);

			this.mSlidingTitle.setRightViewVisibility(false);
			this.mSlidingTitle.setMidViewText(this.getString(R.string.studyfrist));

			this.newContent = (Fragment) this.fragments.get(v.getId());
			this.mSlidingTitle.setArrowImageVisibility(false);
			this.mSlidingTitle.setOnMidListener(((IMidListener) fragment));
			if (this.newContent == null) {
				this.newContent = new EmptyFragment();
				this.fragments.put(v.getId(), this.newContent);
			}
			break;
		}

		case R.id.mymark_layout: {// 学习成绩
			// 设置字体色
			this.changeTextColor(this.label_mymark);

			// 设置选中效果
			this.changeImageLabel(this.mymark_img);
			this.changeImageResource(this.mymark_icon, R.drawable.sliding_mymark_icon_on);

			this.mSlidingTitle.setRightViewVisibility(false);
			this.mSlidingTitle.setMidViewText(this.getString(R.string.mymark));

			this.newContent = (Fragment) this.fragments.get(v.getId());
			this.mSlidingTitle.setArrowImageVisibility(false);
			this.mSlidingTitle.setOnMidListener(((IMidListener) fragment));
			if (this.newContent == null) {
				this.newContent = new GPPerformanceFragment();
				this.fragments.put(v.getId(), this.newContent);
			}
			this.mSlidingTitle.setRightText("考核要求");
			this.mSlidingTitle.setOnRightTextListener(new IRightTextListener() {
				@Override
				public void onRightTextListener() {
					MCMenuItemFragment.this.getActivity().startActivity(new Intent(MCMenuItemFragment.this.getActivity(), GPAssessmentCriteriaActivity.class));
				}
			});

			break;
		}
		case R.id.download_layout: {// 离线下载
			// 设置字体色
			this.changeTextColor(this.label_download);

			// 设置选中效果
			this.changeImageLabel(this.download_img);
			this.changeImageResource(this.download_icon, R.drawable.sliding_download_icon_on);

			this.mSlidingTitle.setRightViewVisibility(false);
			this.mSlidingTitle.setMidViewText(this.getString(R.string.download));

			this.newContent = (Fragment) this.fragments.get(v.getId());
			this.mSlidingTitle.setArrowImageVisibility(false);
			this.mSlidingTitle.setOnMidListener(((IMidListener) fragment));
			if (this.newContent == null) {
				this.newContent = new DownloadFragment();
				this.fragments.put(v.getId(), this.newContent);
			}
			break;
		}
		case R.id.login_pic: {// 登录
			MCLoginProxy.loginInstance().login(this.getActivity(), new MCAnalyzeBackBlock() {
				public void OnAnalyzeBackBlock(MCServiceResult result, List arg3) {
					MCMenuItemFragment.this.refreshAfterLogin();
				}
			});
			break;
		}
		case R.id.workshopactivities_layout: {// 工作坊活动
			// 设置字体色
			this.changeTextColor(this.label_workshopactivities);
			// 设置选中效果
			this.changeImageLabel(this.workshopactivities_img);
			this.changeImageResource(this.workshopactivities_icon, R.drawable.silding_workshopactivities_icon_on);

			this.tag = "工作坊活动";
			this.newContent = (Fragment) this.fragments.get(v.getId());
			if (this.newContent == null) {
				this.newContent = new WorkShopMainFragment(GONGZUOFANGHUODONG);
				this.fragments.put(v.getId(), this.newContent);
			}

			if (!TextUtils.isEmpty(this.currentCourseTypeName)) {
				this.mSlidingTitle.setMidViewText(this.currentCourseTypeName);
			} else {
				this.mSlidingTitle.setMidViewText(this.tag);
			}
			this.mSlidingTitle.setRightViewVisibility(false);
			break;
		}
		case R.id.mycourse_layout: {// 课程学习
			studyHomePage();
			break;
		}

		case R.id.mynote_layout: {// 研修日志
			// 设置字体色
			this.changeTextColor(this.label_mynote);
			// 设置选中效果
			this.changeImageLabel(this.mynote_img);
			this.changeImageResource(this.mynote_icon, R.drawable.sliding_mynote_icon_on);

			this.newContent = (Fragment) this.fragments.get(v.getId());
			if (this.newContent == null) {
				this.newContent = new TrainingLogFragment();
				this.fragments.put(v.getId(), this.newContent);
			}
			this.mSlidingTitle.setOnRightListener(this.rightListener);
			this.mSlidingTitle.setRightText("写日志");
			this.mSlidingTitle.setOnRightTextListener(new IRightTextListener() {

				@Override
				public void onRightTextListener() {
					getActivity().startActivity(new Intent(getActivity(), GPSendBlogActivity.class));

				}
			});
			this.mSlidingTitle.setMidViewText(this.getString(R.string.mynote));
			break;
		}
		case R.id.myquestion_layout: {// 校本研修

			changeClassMenu();
			break;
		}

		case R.id.pic: {
			this.getActivity().startActivity(new Intent(this.getActivity(), MCPersonInformationActivity.class));
			break;
		}
		case R.id.setting_layout: {
			// 设置字体色
			this.changeTextColor(this.label_setting);
			// 设置选中效果
			this.changeImageLabel(this.setting_img);
			this.changeImageResource(this.setting_icon, R.drawable.sliding_setting_icon_on);

			this.mSlidingTitle.setRightViewImage(0);
			this.mSlidingTitle.setRightViewVisibility(false);
			this.mSlidingTitle.setMidViewText(this.getString(R.string.setting));
			this.mSlidingTitle.setArrowImageVisibility(false);
			this.mSlidingTitle.setOnMidListener(((IMidListener) fragment));
			this.newContent = (Fragment) this.fragments.get(v.getId());
			if (this.newContent == null) {
				this.newContent = new MCSettingFragment();
				this.fragments.put(v.getId(), this.newContent);
			}
			break;
		}
		case R.id.myhomework_layout: {//我的作业
			// 设置字体色
			this.changeTextColor(this.label_myhomework);
			// 设置选中效果
			this.changeImageLabel(this.myHomeWork_img);
			this.changeImageResource(this.myhomework_icon, R.drawable.silding_myhomework_red);

			this.newContent = (Fragment) this.fragments.get(v.getId());
			if (this.newContent == null) {
				this.newContent = new GPHomeWorkMianFragment();
				this.fragments.put(v.getId(), this.newContent);
			}

			this.mSlidingTitle.setRightViewImage(R.drawable.search);
			this.mSlidingTitle.setRightViewVisibility(false);
			this.mSlidingTitle.setOnRightListener(this.rightListener);
			this.mSlidingTitle.setMidViewText("我的作业");
			this.mSlidingTitle.setArrowImageVisibility(false);
			this.mSlidingTitle.setOnMidListener(((IMidListener) fragment));
			break;
		}
		case R.id.notice_layout: {
			// 设置字体色
			this.changeTextColor(this.label_notice);
			// 设置选中效果
			this.changeImageLabel(this.notice_img);
			this.changeImageResource(this.notice_icon, R.drawable.sliding_notice_icon_red);

			this.newContent = (Fragment) this.fragments.get(v.getId());
			if (this.newContent == null) {
				this.newContent = new GPNoticListFreament();
				this.fragments.put(v.getId(), this.newContent);
			}
			this.mSlidingTitle.setMidViewText("通知");
			this.mSlidingTitle.setRightViewVisibility(false);
			break;
		}
		}

		if (this.newContent != null) {
			this.switchFragment(this.newContent, v.getId());
		}
	}

	/**
	 * 左侧菜单的字体颜色切换效果，选中的变成主题色，其余还是白色
	 *
	 * @param
	 */
	private void changeTextColor(TextView view) {

		// 主题色
		int themeColor = this.getActivity().getResources().getColor(R.color.theme_color);
		// 普通色
		int normalColor = this.getActivity().getResources().getColor(R.color.sliding_item_color);

		// 其余设置为普通色
		// this.label_fieldpractice.setTextColor(normalColor);
		this.label_mymark.setTextColor(normalColor);
		this.label_studyfrist.setTextColor(normalColor);
		this.label_mycourse.setTextColor(normalColor);
		this.label_workshopactivities.setTextColor(normalColor);
		this.label_download.setTextColor(normalColor);
		this.label_mynote.setTextColor(normalColor);
		this.label_myquestion.setTextColor(normalColor);
		this.label_mymessage.setTextColor(normalColor);
		this.label_setting.setTextColor(normalColor);
		this.label_myhomework.setTextColor(normalColor);
		this.label_notice.setTextColor(normalColor);
		// 选中的设置为主题色
		view.setTextColor(themeColor);
	}

	/**
	 * 左侧菜单的字体颜色切换效果，选中的变成主题色，其余还是白色
	 *
	 * @param
	 */
	private void changeImageLabel(ImageView view) {

		// 其余设置为不可见
		// this.fieldpractice_img.setVisibility(View.INVISIBLE);
		this.mymark_img.setVisibility(View.INVISIBLE);
		this.studyfrist_img.setVisibility(View.INVISIBLE);
		this.setting_img.setVisibility(View.INVISIBLE);
		this.workshopactivities_img.setVisibility(View.INVISIBLE);
		this.mycourse_img.setVisibility(View.INVISIBLE);
		this.mymessage_img.setVisibility(View.INVISIBLE);
		this.download_img.setVisibility(View.INVISIBLE);
		this.mynote_img.setVisibility(View.INVISIBLE);
		this.myHomeWork_img.setVisibility(View.INVISIBLE);
		this.myquestion_img.setVisibility(View.INVISIBLE);
		this.notice_img.setVisibility(View.INVISIBLE);

		// 选中的设置为可见
		view.setVisibility(View.VISIBLE);
	}

	/**
	 * 左侧菜单的字体颜色切换效果，选中的变成主题色，其余还是白色
	 *
	 * @param
	 */
	private void changeImageResource(ImageView view, int sourceId) {

		// this.fieldpractice_icon.setImageResource(R.drawable.silding_fieldpractice_icon);
		this.mymark_icon.setImageResource(R.drawable.sliding_mymark_icon);
		this.studyfrist_icon.setImageResource(R.drawable.sliding_studyfirst_icon);
		this.mycourse_icon.setImageResource(R.drawable.silding_mycourse_whitle);
		this.workshopactivities_icon.setImageResource(R.drawable.silding_workshopactivities_icon);
		this.download_icon.setImageResource(R.drawable.sliding_download_icon);
		this.mynote_icon.setImageResource(R.drawable.sliding_mynote_icon);
		this.myquestion_icon.setImageResource(R.drawable.sliding_question_icon);
		this.mymessage_icon.setImageResource(R.drawable.sliding_message_icon);
		this.setting_icon.setImageResource(R.drawable.sliding_setting_icon);
		this.myhomework_icon.setImageResource(R.drawable.silding_myhomework_whitle);
		this.notice_icon.setImageResource(R.drawable.sliding_notice_white_icon);

		// 选中的设置为主题色
		view.setImageResource(sourceId);
	}

	public void onCourseTypeItemClick(String key, MCCourseTypeModel type) {
		this.types.put(key, type);
		Object mycourse_layout = this.fragments.get(R.id.mycourse_layout);
		this.mSlidingTitle.setMidViewText(type.getName());
		this.currentCourseTypeName = type.getName();
		this.mCourseTypeLayout.setVisibility(8);
		this.isClick = true;
		this.courseTypeViewAnim(false, this.mCourseTypeLayout, ((Fragment) mycourse_layout));
		this.arrowAnim(this.arrowImageView);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.sliding_behind_layout, null);
	}

	public void onDestroy() {
		this.getActivity().unregisterReceiver(this.receiver);
		if (this.mCourseTypeView != null) {
			this.mCourseTypeView.unRegisterReceiver();
		}

		super.onDestroy();
	}

	private void changeClassMenu() {
		// 设置字体色
		this.changeTextColor(this.label_myquestion);
		// 设置选中效果
		this.changeImageLabel(this.myquestion_img);
		if (GPChangeClassUtile.isStatuName()) {
			// 现场实践
			this.newContent = (Fragment) this.fragments.get(1);
			if (this.newContent == null) {
				this.newContent = new SchoolBasedFragment();
				this.fragments.put(1, this.newContent);
			}
		} else {

			this.newContent = (Fragment) this.fragments.get(0);
			if (this.newContent == null) {
				this.newContent = new WorkShopMainFragment(JIAOBENYANXIU);
				this.fragments.put(0, this.newContent);
			}

		}
		this.mSlidingTitle.setRightViewVisibility(false);
		this.mSlidingTitle.setRightViewVisibility(false);
		this.changeImageResource(this.myquestion_icon, R.drawable.sliding_question_icon_on);
		this.mSlidingTitle.setMidViewText("校本研修");
	}

	public void refreshAfterLogin() {
		this.uid = MCSaveData.getUserInfo(Contants.UID, this.getActivity()).toString();
		this.name.setVisibility(View.VISIBLE);
		this.login_ic.setVisibility(4);
		this.mHeadImageView.setVisibility(View.VISIBLE);
		this.name.setText(MCSaveData.getUserInfo(Contants.NICKNAME, this.getActivity()).toString());
		// 设置服务器存储的图片
		String picUrl = MCSaveData.getUserInfo(Contants.PIC, this.getActivity()).toString();
		MCLog.d(TAG, "获取网络头像 ========" + picUrl);
		this.mHeadImageView.setDefaultImageResId(R.drawable.user_default_img);
		this.mHeadImageView.setImageUrl(picUrl);
	}

	public void refreshAfterLogout() {
		try {
			this.uid = MCSaveData.getUserInfo(Contants.UID, this.getActivity()).toString();
		} catch (Exception e) {
		}

		this.name.setVisibility(View.INVISIBLE);
		this.name.setText("");
		this.login_ic.setVisibility(View.VISIBLE);
		this.mHeadImageView.setVisibility(View.INVISIBLE);
	}

	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
	}

	public void studyHomePage() {
		this.tag = this.getString(R.string.mycourse);
		// 设置字体色
		this.changeTextColor(this.label_mycourse);
		// 设置选中效果
		this.changeImageLabel(this.mycourse_img);
		this.changeImageResource(this.mycourse_icon, R.drawable.silding_coursestudy_icon_on);
		this.newContent = (Fragment) this.fragments.get(R.id.mycourse_layout);
		if (this.newContent == null) {
			this.newContent = new GPCourseFragment();
			this.fragments.put(R.id.mycourse_layout, this.newContent);
		}
		this.mSlidingTitle.setRightViewVisibility(false);
		this.mSlidingTitle.setMidViewText(this.getString(R.string.mycourse));

	}

	private void switchFragment(Fragment fragment, int tag) {
		if (this.getActivity() != null && ((this.getActivity() instanceof MCFragmentChangeActivity))) {
			MCFragmentChangeActivity aa = (MCFragmentChangeActivity) this.getActivity();
			aa.switchContent(fragment, tag);
		}
	}

	private boolean isLogined() {
		String userInfo = "0";
		try {
			userInfo = MCSaveData.getUserInfo(Contants.UID, this.getActivity()).toString();
		} catch (Exception v1) {
		}

		boolean flag = userInfo.equals(Contants.DEFAULT_UID) ? false : true;
		return flag;
	}

	/**
	 * 没有选择班级返回 true
	 *
	 * @return
	 */

	private void updateNewMsgImage() {
		if (this.newmessage_img != null) {
			if (this.uid != Contants.DEFAULT_UID) {
				int num = 0;
				if (MCManager.mUnReadMsgNum != null) {
					int i;
					for (i = 0; i < MCManager.mUnReadMsgNum.size(); ++i) {
						num = MCManager.mUnReadMsgNum.get(MCManager.mUnReadMsgNum.keyAt(i));
						if (num != 0) {
							break;
						}
					}
				}

				if (num == 0) {
					this.newmessage_img.setVisibility(View.GONE);
					return;
				}

				this.newmessage_img.setVisibility(View.VISIBLE);
			} else {
				this.newmessage_img.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void OnLoginBack(MCCommonResult arg1) {

	}


}
