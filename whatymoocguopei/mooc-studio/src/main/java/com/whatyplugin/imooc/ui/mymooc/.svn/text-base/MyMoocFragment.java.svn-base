package com.whatyplugin.imooc.ui.mymooc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCCourseService;
import com.whatyplugin.imooc.logic.service_.MCCourseServiceInterface;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;
import com.whatyplugin.imooc.ui.showmooc.ShowMoocActivity;
import com.whatyplugin.uikit.resolution.MCResolution;

public class MyMoocFragment extends MCBaseV4ListFragment {
	private MCCourseServiceInterface service;
	private final static String TAG = "MyMoocFragment";

	@Override
	public String getNoDataTip() {
		return getResources().getString(R.string.no_focus_course_label);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		this.service = new MCCourseService();
		//声明要注册的service类型
		List<String> actions = new ArrayList<String>();
		actions.add(Contants.NETWORK_CHANGED_ACTION);
		actions.add(Contants.USER_LOGIN_ACTION);
		this.setActionList(actions);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void requestData() {
		String uid = MCSaveData.getUserInfo(Contants.UID, this.getActivity()).toString();
		this.service.getMyCourse(uid, this.mCurrentPage, this, this.getActivity());
	}

	@Override
	public void doAfterItemClick(Object obj) {
		Intent intent = new Intent(this.getActivity(), ShowMoocActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("course", (Serializable) obj);
		intent.putExtras(bundle);
		this.startActivityForResult(intent, 10);
	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(this.getActivity(), R.layout.allcourse_item_layout) {
			protected void convert(BaseAdapterHelper helper, MCCourseModel item) {
				int downloading_img_id = R.id.downloading_img; // R.id.downloading_img
				try {
					helper.getView(downloading_img_id).setVisibility(8);
					helper.setText(R.id.name, item.getName());
					helper.setText(R.id.desc, item.getDescription());// 课程简介
					helper.setText(R.id.learnedcount_tv, String.valueOf(item.getLearnedCount()));

					ViewGroup.LayoutParams lp = helper.getView(R.id.image).getLayoutParams();
					lp.width = MCResolution.getInstance(MyMoocFragment.this.getActivity()).scaleWidth(Contants.COURSE_IMAGE_WIDTH);
					lp.height = MCResolution.getInstance(MyMoocFragment.this.getActivity()).scaleHeightByScaleWidth(lp.width);
					helper.getView(R.id.image).setLayoutParams(lp);
					ViewGroup.LayoutParams v9 = helper.getView(R.id.content_layout).getLayoutParams();
					v9.width = lp.width;
					v9.height = lp.height;
					helper.getView(R.id.content_layout).setLayoutParams(v9);
					helper.setDefImage(R.id.image, R.drawable.course_default_bg);
					helper.setImageUrl(R.id.image, item.getImageUrl(), MCCacheManager.getInstance().getImageLoader(), lp.width, lp.height, false,
							ImageType.CICLE_IMAGE, null);
				} catch (Exception e) {
					MCLog.e(TAG, "课程列表中加入课程抛异常" + e.getMessage());
				}
			}

			protected void convert(BaseAdapterHelper arg1, Object arg2) {
				this.convert(arg1, ((MCCourseModel) arg2));
			}
		};
	}
}
