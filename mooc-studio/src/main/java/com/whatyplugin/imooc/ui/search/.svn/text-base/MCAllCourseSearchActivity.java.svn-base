package com.whatyplugin.imooc.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.asyncimage.MCAsyncImageDefine.ImageType;
import com.whatyplugin.base.asyncimage.MCCacheManager;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel.MCCourseFocusStatus;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCCourseService;
import com.whatyplugin.imooc.logic.service_.MCCourseServiceInterface;
import com.whatyplugin.imooc.ui.mymooc.ChoiceCourseActivity;
import com.whatyplugin.imooc.ui.showmooc.ShowMoocActivity;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnFooterRefreshListener;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnHeaderRefreshListener;
import com.whatyplugin.uikit.resolution.MCResolution;

public class MCAllCourseSearchActivity extends MCBaseSearchActivity implements MCAnalyzeBackBlock, OnFooterRefreshListener, OnHeaderRefreshListener,
		AdapterView.OnItemClickListener {
	private MCCourseServiceInterface service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		hisKey = Contants.SEARCH_KEY_ALL_COURSE;
		this.service = new MCCourseService();
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(this, R.layout.allcourse_item_layout) {
			protected void convert(BaseAdapterHelper helper, MCCourseModel item) {
				helper.getView(R.id.downloading_img).setVisibility(View.GONE);
				helper.setText(R.id.name, item.getName());
				helper.setText(R.id.desc, item.getDescription());
				helper.setText(R.id.learnedcount_tv, new StringBuilder(String.valueOf(item.getLearnedCount())).toString());
				ViewGroup.LayoutParams v10 = helper.getView(R.id.image).getLayoutParams();
				v10.width = MCResolution.getInstance(MCAllCourseSearchActivity.this).scaleWidth(Contants.COURSE_IMAGE_WIDTH);
				v10.height = MCResolution.getInstance(MCAllCourseSearchActivity.this).scaleHeightByScaleWidth(v10.width);
				helper.getView(R.id.image).setLayoutParams(v10);
				ViewGroup.LayoutParams v9 = helper.getView(R.id.content_layout).getLayoutParams();
				v9.width = v10.width;
				v9.height = v10.height;
				helper.getView(R.id.content_layout).setLayoutParams(v9);
				helper.setImageUrl(R.id.image, item.getImageUrl(), MCCacheManager.getInstance().getImageLoader(), v10.width, v10.height, false,
						ImageType.CICLE_IMAGE, null);

				if (item.getIsFocused() == MCCourseFocusStatus.MC_COURSE_FOCUSED) // 已选课程
				{
					helper.getView(R.id.choiced_layout).setVisibility(View.VISIBLE);
				}
			}

			protected void convert(BaseAdapterHelper arg1, Object arg2) {
				this.convert(arg1, ((MCCourseModel) arg2));
			}
		};
	}

	@Override
	public void requestData() {
		this.service.getAllCourse(this.mCurrentPage, this.et_search_content.getText().toString(), this, this.mContext);
	}

	@Override
	public void doAfterItemClick(Object obj) {
		MCCourseModel courseModel = (MCCourseModel) obj;

		if (courseModel.getIsFocused() == MCCourseFocusStatus.MC_COURSE_FOCUSED) // 已选课程
		{
			Intent intent = new Intent(this, ShowMoocActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("course", courseModel);
			intent.putExtras(bundle);
			this.startActivityForResult(intent, 10);
		} else {// 未选课的课程跳转到选课页面
			Intent intent = new Intent(this, ChoiceCourseActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("course", courseModel);
			intent.putExtras(bundle);
			this.startActivityForResult(intent, 12);
		}
	}
}
