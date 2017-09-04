package com.whatyplugin.imooc.ui.allmooc;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel.MCCourseFocusStatus;
import com.whatyplugin.imooc.logic.service_.MCCourseService;
import com.whatyplugin.imooc.logic.service_.MCCourseServiceInterface;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;
import com.whatyplugin.imooc.ui.mymooc.ChoiceCourseActivity;
import com.whatyplugin.imooc.ui.showmooc.ShowMoocActivity;
import com.whatyplugin.uikit.resolution.MCResolution;

public class AllMoocFragment extends MCBaseV4ListFragment {
	private MCCourseServiceInterface service;

	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return "课程列表为空";
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		this.service = new MCCourseService();
		//声明要注册的service类型
		List<String> actions = new ArrayList<String>();
		actions.add(Contants.NETWORK_CHANGED_ACTION);
		actions.add(Contants.USER_LOGIN_ACTION);
		actions.add(Contants.USER_LOGOUT_ACTION);
		this.setActionList(actions);
		super.onActivityCreated(savedInstanceState);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 12 && resultCode == -1) {
			String courseId = data.getStringExtra("courseId");
			for (Object obj : this.adapter.getAdapterList()) {
				MCCourseModel innerCourse = (MCCourseModel) obj;
				if (courseId.equals(innerCourse.getId())) {
					// 课程状态设置为已选
					innerCourse.setIsFocused(MCCourseFocusStatus.MC_COURSE_FOCUSED);
					break;
				}

			}
			this.adapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void requestData() {
		this.service.getAllCourse(this.mCurrentPage, null, this, this.getActivity());
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCCourseModel courseModel = (MCCourseModel) obj;

		if (courseModel.getIsFocused() == MCCourseFocusStatus.MC_COURSE_FOCUSED) // 已选课程
		{
			Intent intent = new Intent(this.getActivity(), ShowMoocActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("course", courseModel);
			intent.putExtras(bundle);
			this.startActivityForResult(intent, 10);

		} else {// 未选课的课程跳转到选课页面
			Intent intent = new Intent(this.getActivity(), ChoiceCourseActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("course", courseModel);
			intent.putExtras(bundle);
			this.startActivityForResult(intent, 12);
		}
	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(this.getActivity(), R.layout.allcourse_item_layout) {
			protected void convert(BaseAdapterHelper helper, MCCourseModel item) {
				helper.getView(R.id.downloading_img).setVisibility(View.GONE);
				helper.setText(R.id.name, item.getName());
				helper.setText(R.id.desc, item.getDescription());
				helper.setText(R.id.learnedcount_tv, new StringBuilder(String.valueOf(item.getLearnedCount())).toString());
				ViewGroup.LayoutParams v10 = helper.getView(R.id.image).getLayoutParams();
				v10.width = MCResolution.getInstance(AllMoocFragment.this.getActivity()).scaleWidth(Contants.COURSE_IMAGE_WIDTH);
				v10.height = MCResolution.getInstance(AllMoocFragment.this.getActivity()).scaleHeightByScaleWidth(v10.width);
				helper.getView(R.id.image).setLayoutParams(v10);
				ViewGroup.LayoutParams v9 = helper.getView(R.id.content_layout).getLayoutParams();
				v9.width = v10.width;
				v9.height = v10.height;
				helper.getView(R.id.content_layout).setLayoutParams(v9);
				helper.setDefImage(R.id.image, R.drawable.course_default_bg);
				helper.setImageUrl(R.id.image, item.getImageUrl(), MCCacheManager.getInstance().getImageLoader(), v10.width, v10.height, false,
						ImageType.CICLE_IMAGE, null);

				if (item.getIsFocused() == MCCourseFocusStatus.MC_COURSE_FOCUSED) {// 已选课程
					helper.getView(R.id.choiced_layout).setVisibility(View.VISIBLE);
				} else {
					helper.getView(R.id.choiced_layout).setVisibility(View.INVISIBLE);
				}

			}

			protected void convert(BaseAdapterHelper arg1, Object arg2) {
				this.convert(arg1, ((MCCourseModel) arg2));
			}
		};
	}

}
