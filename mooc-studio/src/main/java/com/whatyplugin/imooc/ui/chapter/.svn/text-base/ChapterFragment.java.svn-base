package com.whatyplugin.imooc.ui.chapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.define.MCBaseDefine.MCMediaType;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCChapterAndSectionModel;
import com.whatyplugin.imooc.logic.model.MCChapterModel;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel.MCSectionStatus;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseService;
import com.whatyplugin.imooc.logic.service_.MCCourseServiceInterface;
import com.whatyplugin.imooc.logic.service_.MCLearningRecordService;
import com.whatyplugin.imooc.ui.base.MCBaseFragment;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;
import com.whatyplugin.imooc.ui.showmooc.MCCourseCCVideoMediaPlayActivity;
import com.whatyplugin.imooc.ui.showmooc.ShowMoocActivity;
import com.whatyplugin.uikit.pinnedlistview.PinnedSectionListView;
import com.whatyplugin.uikit.pinnedlistview.PinnedSectionListView.PinnedSectionListAdapter;
import com.whatyplugin.uikit.toast.MCToast;

public class ChapterFragment extends MCBaseFragment implements AdapterView.OnItemClickListener, MCAnalyzeBackBlock {
	public static final String TAG = "ChapterFragment";
	public  ArrayList<String>  vidList = new ArrayList<String>();
	//	public static String[] playVideoIds = new String[] {"74DB43E3F9D90ACF9C33DC5901307461","AAB0872E8DB3F5B09C33DC5901307461","49108DA63CC0E5549C33DC5901307461","E3C62869AED376DF9C33DC5901307461","58A20DB88E48E1769C33DC5901307461"};

	class MyBasePinnedAdapter extends BaseAdapter implements PinnedSectionListAdapter {
		class Holder {
			LinearLayout chapter_layout;
			LinearLayout lesson_layout;
			TextView chapter_name;
			TextView lesson_name;
			ImageView download_status;
			ImageView learned_status;
			TextView media_duration;
			RelativeLayout section_layout;
			View section_line;
			TextView section_name;
			TextView section_time;

			Holder(MyBasePinnedAdapter arg1) {
				super();
			}
		}

		private LayoutInflater inflater;
		private List list;
		private Context mContext;

		public MyBasePinnedAdapter(ChapterFragment arg2, Context context, List arg4) {
			super();
			this.inflater = null;
			this.mContext = context;
			this.list = arg4;
			this.inflater = LayoutInflater.from(this.mContext);
		}

		public int getCount() {
			return this.list.size();
		}

		public Object getItem(int position) {
			return this.list.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		// 通过这个方法来进行分类的
		public int getItemViewType(int position) {
			return ((MCChapterAndSectionModel) this.getItem(position)).getWhichType();
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout layout = null;
			Holder holder = null;
			if (convertView == null) {
				holder = new Holder(this);
				layout = new LinearLayout(this.mContext);
				this.inflater.inflate(R.layout.chapter_item_layout, layout);
				holder.section_name = (TextView) layout.findViewById(R.id.section_name);
				holder.chapter_name = (TextView) layout.findViewById(R.id.chapter_name);
				holder.download_status = (ImageView) layout.findViewById(R.id.download_checked);
				holder.learned_status = (ImageView) layout.findViewById(R.id.learned_status);
				holder.chapter_layout = (LinearLayout) layout.findViewById(R.id.chapter_layout);
				holder.section_layout = (RelativeLayout) layout.findViewById(R.id.section_layout);
				holder.section_line = layout.findViewById(R.id.section_line);
				holder.media_duration = (TextView) layout.findViewById(R.id.media_duration);
				holder.lesson_name = (TextView) layout.findViewById(R.id.lesson_name);
				holder.lesson_layout = (LinearLayout) layout.findViewById(R.id.lesson_layout);
				layout.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			MCChapterAndSectionModel model = (MCChapterAndSectionModel) this.getItem(position);
			model.getChapter().getSections();

			if (model.getType() == 1) {
				holder.chapter_layout.setVisibility(View.VISIBLE);
				holder.chapter_name.setText(model.getChapter().getName());
				holder.section_layout.setVisibility(View.GONE);
				holder.lesson_layout.setVisibility(View.GONE);
			} else if (model.getType() == 2) {
				holder.lesson_layout.setVisibility(View.VISIBLE);
				holder.lesson_name.setText(model.getChapter().getName());
				holder.section_layout.setVisibility(View.GONE);
				holder.chapter_layout.setVisibility(View.GONE);
			} else {
				MCSectionModel sectionModel = model.getSection();
				holder.section_layout.setVisibility(0);
				holder.section_name.setText(sectionModel.getName());
				// 其余2种隐藏
				holder.chapter_layout.setVisibility(View.GONE);
				holder.lesson_layout.setVisibility(View.GONE);

				// 该枚举类型的编号，决定了用哪个图标来显示 num*3 跟文件section_learned_status_bg.xml中的
				// android:maxLevel 对应
				int num = sectionModel.getType().toNumber();
				if (sectionModel.getStatus() == MCSectionStatus.MC_SECTION_UNPLAY) {
					holder.learned_status.getDrawable().setLevel(0 + num * 4);
					holder.section_name.setTextAppearance(this.mContext, R.style.SectionNameTextStyle);
				} else if (sectionModel.getStatus() == MCSectionStatus.MC_SECTION_PLAYING) {
					holder.learned_status.getDrawable().setLevel(1 + num * 4);
					holder.section_name.setTextAppearance(this.mContext, R.style.SectionSelectNameTextStyle);
				} else if (sectionModel.getStatus() == MCSectionStatus.MC_SECTION_PLAYED) {
					holder.learned_status.getDrawable().setLevel(2 + num * 4);
					holder.section_name.setTextAppearance(this.mContext, R.style.SectionNameTextStyle);
				} else if (sectionModel.getStatus() == MCSectionStatus.MC_SECTION_FINISH) {
					holder.learned_status.getDrawable().setLevel(3 + num * 4);
					holder.section_name.setTextAppearance(this.mContext, R.style.SectionNameTextStyle);
				} else {// 走到这里状态就是空了
					holder.learned_status.getDrawable().setLevel(0 + num * 4);
					holder.section_name.setTextAppearance(this.mContext, R.style.SectionNameTextStyle);
				}

				if (sectionModel.getType() == MCMediaType.MC_VIDEO_TYPE) {
					holder.media_duration.setVisibility(0);
					if (sectionModel.getDuration() != null)
						holder.media_duration.setText(sectionModel.getDuration().ENAUTOHHMMSS());
				} else {
					holder.media_duration.setVisibility(4);
				}

				if (position == this.getCount() - 1) {
					holder.section_line.setVisibility(0);
					return layout != null ? layout : convertView;
				}

				holder.section_line.setVisibility(4);
			}

			return layout != null ? layout : convertView;
		}

		public int getViewTypeCount() {
			return 2;
		}

		public boolean isItemViewTypePinned(int viewType) {
			boolean flag = true;
			if (viewType != 1) {
				flag = false;
			}

			return flag;
		}
	}

	private MyBasePinnedAdapter adapter;
	private boolean isVisible;
	private List<MCChapterAndSectionModel> mChaptes;
	private MCSectionModel mClickCurrentSection;
	private MCCourseModel mCourse;
	private MCCourseServiceInterface mCourseService;
	private PinnedSectionListView mPinnedListView;
	private String uid;
	private View mView;
	private ProgressBar mPbLoading;
	private LinearLayout ll_reLoading; // 重新加载
	private ImageView iv_reLoading;// 重新加载图标
	private ImageView iv_cloading; // 转圈的重新加载
	private Animation animation;
	private TextView tv_reload;
	private Boolean reLoad = false; // 防止多次请求 ，当一次请求不会来，不能进行第二次请求
	private List<MCSectionModel> statusList = new ArrayList<MCSectionModel>();
	private List<MCChapterModel> chapterList = new ArrayList<MCChapterModel>();
	private List<MCChapterModel> chapterList1 = new ArrayList<MCChapterModel>();

	private boolean isStatusReturn = false;
	private boolean isSectionsReturn = false;

	public ChapterFragment() {
		super();

		this.mChaptes = new ArrayList();
	}

	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
		if (mPbLoading != null) {
			mPbLoading.setVisibility(View.GONE);
		}
		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS || result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
			ll_reLoading.setVisibility(View.GONE);
			iv_cloading.clearAnimation();
			iv_cloading.setVisibility(View.GONE);
		}
		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
			chapterList = resultList;
			chapterList1 = resultList;

			this.isSectionsReturn = true;
			if (isStatusReturn) {
				showNodeResult();
			}
//			fistPlay(chapterList);

		} else if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_EMPTY && (result.isExposedToUser())) {
			MCToast.show(this.getActivity(), result.getResultDesc());
		} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
			failReLoad("请检查您的网络");
		} else {
			failReLoad(null);
			MCToast.show(getActivity(), "加载失败");
		}

	}

	// 网络请求失败 给的提示
	private void failReLoad(String reLoadText) {
		iv_cloading.clearAnimation();
		iv_cloading.setVisibility(View.GONE);
		ll_reLoading.setVisibility(View.VISIBLE);
		if (reLoadText != null) {
			tv_reload.setText(reLoadText);
		}
		reLoad = true;
	}

	private void showNodeResult() {
		this.mChaptes.clear();
		List<MCChapterAndSectionModel> modelList = new ArrayList<MCChapterAndSectionModel>();
		Map<String, MCSectionStatus> map = new HashMap<String, MCSectionStatus>();
		for (MCSectionModel model : statusList) {
			map.put(model.getId(), model.getStatus());
		}
		int count = 0;
		for (Object obj : chapterList) {
			MCChapterModel tmp = (MCChapterModel) obj;
			if (tmp == null) {
				continue;
			}
			// 添加章
			MCChapterAndSectionModel chapter = new MCChapterAndSectionModel();
			List<MCSectionModel> list = tmp.getSections();
			if (tmp.isFirst()) {// 章
				chapter.setType(1);
			} else {// 讲
				chapter.setType(2);
				if(!TextUtils.isEmpty(tmp.getvId())){
					vidList.add(tmp.getvId());
				}
			}
			chapter.setChapter(tmp);
			modelList.add(chapter);

			for (MCSectionModel section : list) {
				if (mClickCurrentSection == null) {
					mClickCurrentSection = section;
				}
				// 添加节
				MCChapterAndSectionModel model = new MCChapterAndSectionModel();
				model.setType(0);// 节
				model.setChapter(tmp);
				section.setStatus(map.get(section.getId()));
				if (section.getStatus() == null) {
					section.setStatus(MCSectionStatus.MC_SECTION_UNPLAY);
				}
				section.setOrgStatus(section.getStatus());// 保存原始的状态
				section.setChapterSeq(tmp.getSeq());
				model.setSection(section);
				modelList.add(model);
				count++;
			}
		}
		this.mChaptes.addAll(modelList);
		this.adapter.notifyDataSetChanged();
		//播放第一个视频节点
		fistPlay(chapterList);

	}

	public void currentPlayingSection() {
		if (this.getActivity() != null) {
			MCSectionModel sectionModel = ((ShowMoocActivity) this.getActivity()).getCurrentPlayingSection();
			if (sectionModel == null) {
				sectionModel = this.mClickCurrentSection;
			}

			if (sectionModel == null) {
				return;
			}

			for (int i = 0; i < this.mChaptes.size(); i++) {
				MCChapterAndSectionModel model = this.mChaptes.get(i);
				if (model.getSection() != null && model.getSection().getId() == sectionModel.getId()) {
					model.getSection().setStatus(MCSectionStatus.MC_SECTION_PLAYING);
					this.adapter.notifyDataSetChanged();
					if (i >= this.mPinnedListView.getFirstVisiblePosition() && i <= this.mPinnedListView.getLastVisiblePosition()) {
						return;
					}

					this.mPinnedListView.setSelection(i - 2);
					return;
				}
			}
		}
	}

	public boolean getVisible() {
		return this.isVisible;
	}

	public List<MCChapterAndSectionModel> getmChaptes() {
		return this.mChaptes;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		try {
			this.uid = MCSaveData.getUserInfo(Contants.UID, this.getActivity()).toString();
		} catch (Exception e) {
		}

		reLoading(); // 初始化重新加载

		ShowMoocActivity thisActivity = (ShowMoocActivity) this.getActivity();
		this.mCourseService = new MCCourseService();
		this.mPinnedListView = (PinnedSectionListView) this.getActivity().findViewById(R.id.pinnedListview);
		this.adapter = new MyBasePinnedAdapter(this, this.getActivity(), this.mChaptes);
		this.mPinnedListView.setAdapter(this.adapter);
		this.mPinnedListView.setShadowVisible(false);
		this.mPinnedListView.setOnItemClickListener(((AdapterView.OnItemClickListener) this));
		this.mCourse = thisActivity.getmCourse();
		// 请求节点信息
		getChapterByCourseId();

		MCLearningRecordService recordService = new MCLearningRecordService();
		// 学习记录 请求
		recordService.getCourseLearnRecord(this.mCourse.getId(), new MCAnalyzeBackBlock() {

			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
				statusList = resultList;
				isStatusReturn = true;
				if (isSectionsReturn) {
					showNodeResult();
				}
			}
		}, this.getActivity());
		super.onActivityCreated(savedInstanceState);

	}

	/***
	 *  播放视频第一个节点
	 * @param chapterList
	 */
	private void fistPlay(List<MCChapterModel> chapterList){
		for (Object obj : chapterList) {
			MCChapterModel tmp = (MCChapterModel) obj;
			if (tmp == null) {
				continue;
			}
			// 添加章
			List<MCSectionModel> list = tmp.getSections();
			for(MCSectionModel sectionModel :list){
				if(sectionModel != null){
					if(sectionModel.getType() == MCMediaType.MC_VIDEO_TYPE){
						if (this.mClickCurrentSection != null && this.mClickCurrentSection.getOrgStatus() == MCSectionStatus.MC_SECTION_UNPLAY) {
							this.mClickCurrentSection.setStatus(MCSectionStatus.MC_SECTION_PLAYED);
						} else {
							this.mClickCurrentSection.setStatus(this.mClickCurrentSection.getOrgStatus());
						}
						this.mClickCurrentSection = sectionModel;
						((ShowMoocActivity) this.getActivity()).play(sectionModel);
						this.adapter.notifyDataSetChanged();
						return;
					}
				}
			}
		}
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.chapter_listview_layout, null);
		return mView;
	}

	public void onItemClick(AdapterView arg5, View view, int position, long id) {
		MCChapterAndSectionModel chapter = (MCChapterAndSectionModel) arg5.getAdapter().getItem(position);
		if (!MCNetwork.checkedNetwork(this.getActivity())) {
			MCToast.show(this.getActivity(), this.getResources().getString(R.string.download_nonetwork_label));
		} else if (chapter.getChapter() != null && !TextUtils.isEmpty(chapter.getChapter().getvId())) {
			//MCCourseCCVideoMediaPlayActivity
			Intent intent = new Intent (getActivity(),MCCourseCCVideoMediaPlayActivity.class);
			intent.putExtra("id", chapter.getChapter().getZhangId());
			intent.putExtra("name", chapter.getChapter().getName());
			intent.putExtra("courseId", this.mCourse.getId());
			intent.putStringArrayListExtra("vidList",  vidList);
			intent.putExtra("videoId", chapter.getChapter().getvId());
			this.startActivity(intent);
		} else if (chapter.getSection() != null) {
			MCSectionModel section = chapter.getSection();
			if (this.mClickCurrentSection != null && this.mClickCurrentSection.getOrgStatus() == MCSectionStatus.MC_SECTION_UNPLAY) {
				this.mClickCurrentSection.setStatus(MCSectionStatus.MC_SECTION_PLAYED);
			} else {
				this.mClickCurrentSection.setStatus(this.mClickCurrentSection.getOrgStatus());
			}
			this.mClickCurrentSection = section;
			((ShowMoocActivity) this.getActivity()).play(chapter.getSection());

		}
	}

	public void refreshByCourse(MCCourseModel course) {
		if (this.mCourse != null && this.mCourse.getId() != course.getId()) {
			ShowMoocActivity thisActivity = (ShowMoocActivity) this.getActivity();
			this.mCourse = thisActivity.getmCourse();
			this.mCourseService.getChapterByCourseId(this.mCourse.getId(), this.uid, ((MCAnalyzeBackBlock) this), this.getActivity());
		}
	}

	public void setVisible(boolean isVisible) {
		int v2 = R.string.section_page_name; // R.string.section_page_name
		this.isVisible = isVisible;
		if (this.getActivity() != null && (this.isAdded())) {
			if (isVisible) {
				// MCBaiduStatService.onPageStart(this.getActivity(),
				// this.getResources().getString(v2));
			} else {
				// MCBaiduStatService.onPageEnd(this.getActivity(),
				// this.getResources().getString(v2));
			}
		}
	}

	/**
	 * 重新加载核心类
	 *
	 */
	private void reLoading() {

		animation = AnimationUtils.loadAnimation(MoocApplication.getInstance(), R.anim.dialog_loading_anim);

		mPbLoading = (ProgressBar) mView.findViewById(R.id.pb_loading);
		tv_reload = (TextView) mView.findViewById(R.id.tv_loading);
		ll_reLoading = (LinearLayout) mView.findViewById(R.id.ll_reloading);
		iv_reLoading = (ImageView) mView.findViewById(R.id.iv_reloading);
		mPbLoading.setVisibility(View.VISIBLE);
		iv_cloading = (ImageView) mView.findViewById(R.id.iv_cloading);
		ll_reLoading.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (reLoad) {
					reLoad = false;
					ll_reLoading.setVisibility(View.GONE);
					iv_cloading.setVisibility(View.VISIBLE);
					iv_cloading.startAnimation(animation);
					getChapterByCourseId();
				}
			}
		});
	}

	/**
	 * 请求节点数据
	 *
	 */
	private void getChapterByCourseId() {
		this.mCourseService.getChapterByCourseId(this.mCourse.getId(), this.uid, ((MCAnalyzeBackBlock) this), this.getActivity());
	}

	/**
	 * 没有标题的用这个产生正在加载
	 */
	/*
	 * public void initLoadingNoTitle() {
	 * MCTipManager.initLoading(this.getActivity(), this.toString() ,
	 * getActivity().getCurrentFocus(), 0); }
	 *//**
	 * 移除正在加载，常用在网络请求回来的时候第一时间调用
	 */
	/*
	 * public void removeLoading() {
	 * MCTipManager.removeLoading(this.toString()); }
	 */
}
