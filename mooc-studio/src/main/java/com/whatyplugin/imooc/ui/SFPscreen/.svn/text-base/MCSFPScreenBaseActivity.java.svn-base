package com.whatyplugin.imooc.ui.SFPscreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whaty.media.WhatyMediaPlayerCommonFragment;
import com.whaty.media.WhatyMediaPlayerCommonFragment.BufferingUpdateHandler;
import com.whaty.media.WhatyMediaPlayerCommonFragment.FullScreenHandler;
import com.whaty.media.WhatyMediaPlayerCommonFragment.PlayNextHandler;
import com.whaty.media.WhatyMediaPlayerCommonFragment.SeekToHandler;
import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.define.MCBaseDefine.MCMediaType;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.model.MCXmlSectionModel;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCLearnOfflineRecord;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseService;
import com.whatyplugin.imooc.logic.service_.MCCourseServiceInterface;
import com.whatyplugin.imooc.logic.whatydb.dao.base.OfflineDao;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.showmooc.ShowMoocCommon;
import com.whatyplugin.imooc.ui.view.MySlidingDrawer;
import com.whatyplugin.uikit.resolution.MCResolution;
import com.whatyplugin.uikit.toast.MCToast;

public class MCSFPScreenBaseActivity extends MCBaseActivity implements View.OnClickListener,
	FullScreenHandler,SeekToHandler, BufferingUpdateHandler, MCAnalyzeBackBlock, OnItemClickListener, PlayNextHandler {

	private static final String TAG = "MCSFPScreenBaseActivity";
	protected MCSectionModel section;
	protected WhatyMediaPlayerCommonFragment fm_video_screen;
	protected WhatyMediaPlayerCommonFragment fm_video_ppt;
	protected QuickAdapter adapter;
	protected QuickAdapter listAdapter;
	
	protected TextView tv_search;
	protected EditText ev_search;

	protected List<MCXmlSectionModel> childSections = new ArrayList<MCXmlSectionModel>();// 所有的子节点
	protected MySlidingDrawer sd_node_drawer;
	protected ListView section_list;

	protected MCCourseServiceInterface service;
	protected MCXmlSectionModel currentModel;
	protected Map<Integer, Double> videoSizeMap = new HashMap<Integer, Double>();//2个视频及其对应的宽高比
	
	private int contentViewId;
	
	private boolean isLocal;
	protected String sectionId;
	protected String courseId;
	private String studentId;
	private Long totalTime = (long) 0;
	private Handler mHandler;
	private View rl_guide;
	
	public MCSFPScreenBaseActivity(){
		mHandler = new Handler(){
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					//根据传递的参数判断是离线缓存播放还是在线播放
					if(isLocal){
						service.getSFPSectionsByPathLocal(courseId, sectionId, MCSFPScreenBaseActivity.this, MCSFPScreenBaseActivity.this);
					}else{
						service.getSFPSectionsByPath(section, MCSFPScreenBaseActivity.this, MCSFPScreenBaseActivity.this);
					}
				}else if(msg.what==1){
					service.getSFPSectionsByMobileXml(msg.obj.toString(), MCSFPScreenBaseActivity.this);
				}

				super.handleMessage(msg);
			}
		};
	}
	
	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.tv_search) {
			String caseWorld = this.ev_search.getText().toString();
			showSearchResult(caseWorld);
			//隐藏软键盘
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		} else if (id == R.id.iv_test_know) {
			rl_guide.setVisibility(View.GONE);
			View rv_bottom = this.findViewById(R.id.rv_bottom);
			if(rv_bottom!=null){
				rv_bottom.setVisibility(View.VISIBLE);
			}
		} else {
		}
	}

	public void setMyContentView(int viewId){
		this.contentViewId = viewId;
		
	}
	
	/**
	 * 把符合结果的搜索条件显示处理
	 * 
	 * @param caseWorld
	 */
	private void showSearchResult(String caseWorld) {
		if (TextUtils.isEmpty(caseWorld)) {//没有输入就是默认所有的
			this.adapter.clear();
			this.adapter.addAll(childSections);
		} else {
			List<MCXmlSectionModel> list = new ArrayList<MCXmlSectionModel>();
			for (MCXmlSectionModel model : childSections) {
				if (model.getTitle().contains(caseWorld)) {
					list.add(model);
				}
			}
			this.adapter.clear();
			this.adapter.addAll(list);
			if(list.size()==0){
				MCToast.show(this, "没有符合条件的结果");
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); 
		//获取用户ID 用于学习记录
		studentId = MCSaveData.getUserInfo(Contants.USERID,this).toString();
		// 一进来不让旋转，后面根据情况旋转
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		super.onCreate(savedInstanceState);
		setContentView(this.contentViewId);
		
		initView();
		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			this.section = (MCSectionModel) extras.getSerializable("section");
		}

		this.sd_node_drawer = ((MySlidingDrawer) findViewById(R.id.sd_node_drawer));

		this.section_list = (ListView) findViewById(R.id.section_list);
	
		this.adapter = new QuickAdapter(this, R.layout.sfp_item_layout) {
			protected void convert(final BaseAdapterHelper helper, MCXmlSectionModel item) {
				
				TextView  tv_node_name = (TextView) helper.getView(R.id.tv_node_name);
				String currentId = currentModel==null?"":currentModel.getId();
				
				tv_node_name.setText(item.getTitle());
				if(currentId.equals(item.getId())){
					//当前正在播放的，进行样式的处理
					helper.setImageResource(R.id.iv_node_image, R.drawable.sfp_node_current);
					tv_node_name.setTextAppearance(MCSFPScreenBaseActivity.this, R.style.sfp_item_current_name);
				}else{
					helper.setImageResource(R.id.iv_node_image, R.drawable.sfp_node_normal);
					tv_node_name.setTextAppearance(MCSFPScreenBaseActivity.this, R.style.sfp_item_name);
				}
			}

			protected void convert(BaseAdapterHelper arg1, Object arg2) {
				this.convert(arg1, ((MCXmlSectionModel) arg2));
			}
		};
		
		this.section_list.setAdapter(this.adapter);
		
		this.section_list.setOnItemClickListener(this);
		
		this.service = new MCCourseService();

		this.isLocal = getIntent().getBooleanExtra("isLocal", false);
		String mobileLink = getIntent().getStringExtra("mobileLink");
		//根据传递的参数判断是离线缓存播放还是在线播放
		if (mobileLink != null) {
			Message msg = Message.obtain();
			msg.what = 1;
			msg.obj = mobileLink;
			this.mHandler.sendMessageDelayed(msg, 300);
		} else if (isLocal) {
			this.courseId = getIntent().getStringExtra("courseId");
			this.sectionId = getIntent().getStringExtra("sectionId");
			this.mHandler.sendEmptyMessageDelayed(0, 300);
		} else {
			this.sectionId = this.section.getId();
			this.courseId = this.section.getCourseId();
			this.mHandler.sendEmptyMessageDelayed(0, 300);
		}

		//填充视频布局
		adjustVideoView(this.fm_video_screen, videoSizeMap.get(R.id.fm_video_screen));
		adjustVideoView(this.fm_video_ppt, videoSizeMap.get(R.id.fm_video_ppt));

	}

	private void initView() {
		childInitView();
		this.tv_search = (TextView) this.findViewById(R.id.tv_search);
		this.ev_search = (EditText) this.findViewById(R.id.ev_search);
		this.tv_search.setOnClickListener(this);
		
		this.rl_guide = this.findViewById(R.id.rl_guide);
		
		//导航界面的处理
		((ImageView)this.findViewById(R.id.iv_test_finger)).setImageResource(R.drawable.guide_sfp_hint_graphic);
		((ImageView)this.findViewById(R.id.iv_test_know)).setOnClickListener(this);
		
		this.findViewById(R.id.rl_guide).bringToFront();

		this.fm_video_screen = (WhatyMediaPlayerCommonFragment) (getFragmentManager().findFragmentById(R.id.fm_video_screen));
		this.fm_video_ppt = (WhatyMediaPlayerCommonFragment) (getFragmentManager().findFragmentById(R.id.fm_video_ppt));
		
		this.fm_video_screen.setParentViewId(R.id.fm_video_screen);
		this.fm_video_ppt.setParentViewId(R.id.fm_video_ppt);

		this.fm_video_screen.getVideoView().setZOrderMediaOverlay(true);

		this.fm_video_screen.setBindVideo(this.fm_video_ppt);
		this.fm_video_ppt.setBindVideo(this.fm_video_screen);

		this.fm_video_screen.setSeekToHandler(this);
		this.fm_video_ppt.setSeekToHandler(this);
		this.fm_video_screen.setFullScreenHandler(this);
		this.fm_video_ppt.setFullScreenHandler(this);
		
		this.fm_video_screen.setOnBufferingUpdate(this);

		this.fm_video_ppt.setPlayNextHandler(this);
		
	}

	/**
	 * 子类重写，初始化自己的视图
	 */
	public void childInitView() {
	}

	/**
	 * 对返回键的处理，如果是全屏就退出全屏
	 */
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == 4) {
			if (this.fm_video_screen != null && this.fm_video_screen.isFullScreen()) {

				this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				fm_video_ppt.toggleFullScreen();
				fm_video_screen.toggleFullScreen();
				return true;
			}
		}
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * 按视频的宽高比进行缩放
	 * 
	 * @param view
	 *            //存放播放器的容器
	 * @param heightPoint
	 *            //3 高度所占的比例
	 * @param widthPoint
	 *            //4 宽度所占的比例
	 */
	public void adjustVideoView(WhatyMediaPlayerCommonFragment videoFragment, Double ratio) {

		int devHeight = MCResolution.getInstance(this).getDevHeight(this);
		int devWidth = MCResolution.getInstance(this).getDevWidth(this);
		MCLog.d(getTag(), "三分屏切换视图 ： " + videoFragment.getParentViewId()+ "->" + videoFragment.isFullScreen());
		View view = videoFragment.getView();
		RelativeLayout.LayoutParams videoParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

		RelativeLayout.LayoutParams drawerParams = (android.widget.RelativeLayout.LayoutParams) this.sd_node_drawer.getLayoutParams();

		int controllerHeight = (int) this.getResources().getDimension(R.dimen.player_60_dp);
		 
		if (videoFragment.isFullScreen()) {
			if (videoFragment.getParentViewId() == R.id.fm_video_screen) {
				videoParams.height = (int) getResources().getDimension(R.dimen.mooc_135_dp);
				videoParams.width = (int) getResources().getDimension(R.dimen.mooc_180_dp);

				videoFragment.hideMediaController();
				videoFragment.setControlShow(false);
				videoFragment.setCanDrag(true);
				
				//这句话很重要， 不然后面的拖动事件可能就不起作用了
				videoFragment.getView().bringToFront();
				//videoFragment.getVideoView().getCurrentView().bringToFront();
			} else {
				videoParams.height = devHeight;
				videoParams.width = devWidth;
				videoFragment.setControlShow(true);
				videoParams.addRule(RelativeLayout.BELOW, 0);
				
				// 全屏时抽屉容器的尺寸动态调整一下
				drawerParams.width = (int) getResources().getDimension(R.dimen.mooc_450_dp);

			}

			//全屏的时候设置 sd_node_drawer 的不响应范围。
			this.sd_node_drawer.setBottomTouchOffset(videoFragment.getMediaControllerHeight() + 20);
		} else {
			videoParams.width = devWidth;
			if(ratio<0.1){
				int barHeight = MCResolution.getInstance(this).getStatusBarHeight(this);
				videoParams.height = (devHeight-barHeight)/2;
				MCLog.d(TAG, "宽高为：" + videoParams.width + " - " + videoParams.height);
			}else{
				videoParams.height = (int) (videoParams.width * ratio);
			}
			if (videoFragment.getParentViewId() == R.id.fm_video_ppt) {
				videoFragment.hideMediaController();
				videoFragment.setControlShow(false);
				videoParams.addRule(RelativeLayout.BELOW, R.id.fm_video_screen);
				
			} else {
				videoParams.leftMargin = 0;
				videoParams.topMargin = 0;
				videoParams.bottomMargin = 0;
				videoParams.rightMargin = 0;
				videoFragment.setControlShow(true);
				videoFragment.setCanDrag(false);
				drawerParams.width = devWidth;
			}
			
			//非全屏的时候设置 sd_node_drawer 的不响应范围为0。
			this.sd_node_drawer.setBottomTouchOffset(0);
		}
		
		operateAfterToggle(devWidth, devHeight, videoParams);

		view.setLayoutParams(videoParams);
		if(videoFragment.getVideoView().getCurrentView()!=null){
			videoFragment.getVideoView().getCurrentView().invalidate();//videoview重绘一下，  不然可能没有及时更新
		}
		view.invalidate();
		this.sd_node_drawer.setLayoutParams(drawerParams);
		this.sd_node_drawer.bringToFront();
		
	}

	/**
	 * 由子类重写，主要是根据是否有缩略图做不同的处理
	 * @param devWidth
	 * @param devHeight
	 * @param videoParams
	 */
	public void operateAfterToggle(int devWidth, int devHeight, LayoutParams videoParams) {
		
	}

	@Override
	public void afterToggleFullScreen(WhatyMediaPlayerCommonFragment videoFragment) {

		if(this.rl_guide!=null && this.rl_guide.getVisibility()!=View.GONE){
			this.rl_guide.setVisibility(View.GONE);
		}
		adjustVideoView(videoFragment, videoSizeMap.get(videoFragment.getParentViewId()));
	}

	//播放下一个的回调
	@Override
	public void playNext(View v) {
		int index = adapter.getAdapterList().indexOf(MCSFPScreenBaseActivity.this.currentModel);
		if (index < 0) {
			MCToast.show(MCSFPScreenBaseActivity.this, "当前节点不存在？");
		} else if (index == MCSFPScreenBaseActivity.this.adapter.getAdapterList().size() - 1) {
			MCToast.show(MCSFPScreenBaseActivity.this, "已经是最后一个了呢~");
		} else {
			MCXmlSectionModel model = (MCXmlSectionModel) adapter.getAdapterList().get(index + 1);
			playSectionModel(model);
			MCToast.show(MCSFPScreenBaseActivity.this, "开始播放下一节点 ： " + model.getTitle());
		}
	}
	
	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {

		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
			if (resultList != null && resultList.size() > 0) {
				List<MCXmlSectionModel> modelList = resultList;
				String json1 = "/videoForSeg/yunPan/fjsd/tch003/%E5%BC%80%E6%94%BE%E8%AF%BE%E7%A8%8B%E6%96%87%E4%BB%B6/20150316/%E5%AF%BC%E8%AF%BB.mp4.VIDEOSEGMENTS/meta.json";
				String json2 = "/videoForSeg/yunPan/fjsd/tch003/%E5%BC%80%E6%94%BE%E8%AF%BE%E7%A8%8B%E6%96%87%E4%BB%B6/20150316/%E5%AF%BC%E8%AF%BB.mp4.VIDEOSEGMENTS/meta.json";
			
				json1 = "/tycj/tycj/cdmc/bx004/009/presentation.mp4.VIDEOSEGMENTS/meta.json";
				json2 = "/tycj/tycj/cdmc/bx004/009/presentation.mp4.VIDEOSEGMENTS/meta.json";
			String	mp41 = "http://stream.2.bgp.webtrn.cn/videoForSeg/yunPan/fjsd/tch002/%E5%BC%80%E6%94%BE%E8%AF%BE%E7%A8%8B%E6%96%87%E4%BB%B6/20150317/%E4%B8%AD%E5%9B%BD%E7%8E%B0%E5%BD%93%E4%BB%A3%E6%95%A3%E6%96%87%E7%A0%94%E7%A9%B6-%E5%AE%A3%E4%BC%A0%E7%89%87.mp4.VIDEOSEGMENTS/11139a75022eb529bda27a18d18a0f9b.mp4";
			String	mp42 = "http://stream.2.bgp.webtrn.cn/videoForSeg/yunPan/fjsd/tch002/%E5%BC%80%E6%94%BE%E8%AF%BE%E7%A8%8B%E6%96%87%E4%BB%B6/20150317/%E4%B8%AD%E5%9B%BD%E7%8E%B0%E5%BD%93%E4%BB%A3%E6%95%A3%E6%96%87%E7%A0%94%E7%A9%B6-%E5%AE%A3%E4%BC%A0%E7%89%87.mp4.VIDEOSEGMENTS/11139a75022eb529bda27a18d18a0f9b.mp4";
				
			List<MCXmlSectionModel>  list = new ArrayList<MCXmlSectionModel>();
				for (MCXmlSectionModel model : modelList) {
					if (!model.isParent()) {
						childSections.add(model);
						if(!TextUtils.isEmpty(model.getThumbImage())){
							list.add(model);
						}
					}else{
						//SFP总时长
						totalTime += model.getTime();
						//model.setScreenUrl(json1);
						//model.setVideoUrl(json2);
						
						//model.setScreenUrl(mp41);
						//model.setVideoUrl(mp42);
					}
					
				}
				this.adapter.addAll(childSections);//章节列表
				if(this.listAdapter!=null){
					this.listAdapter.addAll(list);//缩略图
				}
				if(childSections.size() > 0){
					// 播放初始视频
					startNewVideo(childSections.get(0));
					startRecord();
					
					
					
				}else{
					MCToast.show(this, "节点数量为空");
				}
			} else {
				MCToast.show(this, "没有节 点信息");
			}

		} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
			MCToast.show(this, "节点信息不存在！");
		} else {
			MCToast.show(this, "未知错误！");
		}
	}

	/**
	 * 新播放一个视频
	 */
	private void startNewVideo(MCXmlSectionModel model) {
		fm_video_screen.play(model.getParentModel().getVideoUrl());
		//fm_video_screen.seekTo(model.getTime());
	//	fm_video_screen.start();
		
		fm_video_ppt.play(model.getParentModel().getScreenUrl());
		//fm_video_ppt.seekTo(model.getTime());
	//	fm_video_ppt.start();

		this.currentModel = model;
		MCLog.d(getTag(), "播放视频地址: " + model.getParentModel().getVideoUrl());
		MCLog.d(getTag(), "播放ppt地址: " + model.getParentModel().getScreenUrl());
	}

	/**
	 * 子条目的点击
	 */
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		MCXmlSectionModel model = (MCXmlSectionModel) adapter.getAdapter().getItem(position);
		
		playSectionModel(model);
		if(this.sd_node_drawer.isOpened()){
			sd_node_drawer.animateClose();
		}
	}

	private void playSectionModel(MCXmlSectionModel model) {
		//切换样式问题
		String currentParentId = this.currentModel ==null?"":this.currentModel.getParentModel().getId();
		if (currentParentId.equals(model.getParentModel().getId())) {// 点击的跟正在播放的是一个视频
			this.fm_video_screen.seekTo(model.getTime());
			this.fm_video_ppt.seekTo(model.getTime());
			this.currentModel = model;
			
			MCLog.d(getTag(), "跳转到--" + model.getTime());
		} else {
			startNewVideo(model);// 点击的跟正在播放的不是同一个视频
		}
		
		this.adapter.notifyDataSetChanged();
		
		if(this.listAdapter!=null){
			this.listAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 进度条的变更引起的节点变化
	 */
	@Override
	public void afterSeekTo(int position) {

		//如果是不是人为的点击节点改变的进度就做如下处理
		MCXmlSectionModel resultModel = null;
		try {
			for(MCXmlSectionModel model : childSections){
				//首先父类是一个类， 因为视频的路径一样的都是有一个相同的parent
				if(model.getParentModel().getId().equals(this.currentModel.getParentModel().getId())){
					if(model.getTime()>position){
						break;
					}
					resultModel = model;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(resultModel == null){
			return;
		}
		if(this.currentModel != resultModel){
			this.currentModel = resultModel;
			this.adapter.notifyDataSetChanged();
			
			if(this.listAdapter != null){
				this.listAdapter.notifyDataSetChanged();
			}
		}
	}
	
	@Override
	protected void onPause() {
		if(this.fm_video_screen.getVideoView().isPlaying()){
			this.fm_video_screen.pause();
		}
		ShowMoocCommon.stopRecordTimer(this.sectionId);
		if(isLocal)
		{
			OfflineDao dao = new OfflineDao();
			List<MCLearnOfflineRecord> list = new ArrayList<MCLearnOfflineRecord>();
			MCLearnOfflineRecord item = new MCLearnOfflineRecord();
			item.setId(sectionId);
			item.setCourseId(courseId);
			item.setStudyTime(ShowMoocCommon.studyTime);
			item.setTotalTime(totalTime+"");
			item.setType(5);
			item.setUserId(Contants.USERID);
			item.setRecordTime(fm_video_screen.getVideoView().getDuration()+"");
			list.add(item);
			dao.insertOffline(list, sectionId, ShowMoocCommon.flag);
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		this.fm_video_screen.release();
		this.fm_video_ppt.release();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		startRecord();
		super.onResume();
	}

	private void startRecord() {
		if(totalTime>0){//不是播放离线缓存的
			ShowMoocCommon.startRecordTimer(this.sectionId,studentId ,MCMediaType.MC_COURSEWARE_TYPE,this);
			ShowMoocCommon.updateTimeInfo(sectionId, courseId, totalTime);
		}
	}
	
	@Override
	public void afterBufferingUpdate(int position) {
		afterSeekTo(position);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		//在这个方法里可以获取到   onCreate里获取不到
		this.sd_node_drawer.setNoTouchScope(fm_video_screen.getView().getMeasuredHeight());
	}
}
