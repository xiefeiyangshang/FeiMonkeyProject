package com.whaty.imooc.ui.course;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whaty.imooc.ui.index.GPChangeClassUtile;
import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.SharedClassInfo;
import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.ui.mymooc.MyMoocFragment;
import com.whatyplugin.uikit.resolution.MCResolution;

import cn.com.whatyplugin.mooc.R;

public class GPCourseFragment extends MyMoocFragment {
	private GPPerformanceServiceInterface service;

	@Override
	public void requestData() {
		if (GPChangeClassUtile.isChandClass()&&!GPChangeClassUtile.noClass()) {
			removeLoading();
			mListView.setGuidanceViewWhenNoDataWith("请在左侧菜单栏选择班级");
			GPChangeClassUtile.createDialog(getActivity());
		} else /*if(!GPChangeClassUtile.noClass())*/{
			service.getCourseList(getActivity(), String.valueOf(this.mCurrentPage), this);

		}
	}

	@Override
	public String getNoDataTip() {
		
		return "您还没有选课或所选课程暂不支持手机播放先到PC端查看吧~";
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		service = new GPPerformanceService();
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * 这里面处理布局和数据的对应关系。
	 */
	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(this.getActivity(), R.layout.allcourse_item_layout) {
			protected void convert(BaseAdapterHelper helper, MCCourseModel item) {//tv_starstudenty
				String banStateFlag = SharedClassInfo.getKeyValue(GPContants.USER_STATE_FLAG).trim();

				int downloading_img_id = R.id.downloading_img; // R.id.downloading_img
				try {

					if("有效学习".equals(banStateFlag)){
						if(item!= null ){
							int effectTime = 0;
							int courseTime = 0;
							if(TextUtils.isEmpty(item.getEffectTime())){
								effectTime = 0;
							}else{

								effectTime = Integer.parseInt(item.getEffectTime());
							}
							if(TextUtils.isEmpty(item.getCourseTime())){
								courseTime = 0;
							}else{

								courseTime = Integer.parseInt(item.getCourseTime());
							}
							if(effectTime>courseTime){
								helper.setText(R.id.tv_starstudenty, "温故知新");

							}else if(effectTime<courseTime && effectTime>0){
								helper.setText(R.id.tv_starstudenty, "继续学习");

							}else if(effectTime == 0 ){
								helper.setText(R.id.tv_starstudenty, "开始学习");

							}

						}

					}else {
						if(item!= null ){
							int pageTime = 0;
							int courseTime = 0;
							if(TextUtils.isEmpty(item.getPageTime())){
								pageTime = 0;
							}else{

								pageTime = Integer.parseInt(item.getPageTime());
							}
							if(TextUtils.isEmpty(item.getCourseTime())){
								courseTime = 0;
							}else{

								courseTime = Integer.parseInt(item.getCourseTime());
							}
							if(pageTime>courseTime){
								helper.setText(R.id.tv_starstudenty, "温故知新");

							}else if(pageTime<courseTime && pageTime>0){
								helper.setText(R.id.tv_starstudenty, "继续学习");

							}else if(pageTime == 0 ){
								helper.setText(R.id.tv_starstudenty, "开始学习");

							}

						}
					}
					helper.getView(downloading_img_id).setVisibility(View.GONE);
					helper.setText(R.id.name, item.getName());
//					helper.setText(R.id.desc, item.getDescription());// 课程简介
					helper.setText(R.id.learnedcount_tv, String.valueOf(item.getLearnedCount()));
					helper.setVisible(R.id.learned_count, false);

					ViewGroup.LayoutParams lp = helper.getView(R.id.image).getLayoutParams();
					lp.width = MCResolution.getInstance(GPCourseFragment.this.getActivity()).scaleWidth(Contants.COURSE_IMAGE_WIDTH);
					lp.height = MCResolution.getInstance(GPCourseFragment.this.getActivity()).scaleHeightByScaleWidth(lp.width);
					helper.getView(R.id.image).setLayoutParams(lp);
					ViewGroup.LayoutParams v9 = helper.getView(R.id.content_layout).getLayoutParams();
					v9.width = lp.width;
					v9.height = lp.height;
					helper.getView(R.id.content_layout).setLayoutParams(v9);
					helper.setDefImage(R.id.image, R.drawable.course_default_bg);
					helper.setImageUrl(R.id.image, item.getImageUrl(), MCCacheManager.getInstance().getImageLoader(), lp.width, lp.height, false,
							ImageType.CICLE_IMAGE, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			protected void convert(BaseAdapterHelper arg1, Object arg2) {
				this.convert(arg1, ((MCCourseModel) arg2));
			}
		};
	}

}
