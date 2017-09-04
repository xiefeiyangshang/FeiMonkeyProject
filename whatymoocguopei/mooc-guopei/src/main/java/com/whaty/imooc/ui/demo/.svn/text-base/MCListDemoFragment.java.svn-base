package com.whaty.imooc.ui.demo;

import android.content.Intent;
import android.os.Bundle;

import com.whaty.imooc.logic.model.MCDemoModel;
import com.whaty.imooc.logic.service_.MCCommonService;
import com.whaty.imooc.logic.service_.MCCommonServiceInterface;
import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.imooc.ui.base.MCBaseV4ListFragment;
import com.whatyplugin.imooc.ui.showmooc.ShowMoocActivity;

import java.io.Serializable;

import cn.com.whatyplugin.mooc.R;

/**
 * listview类型的fragment demo
 * 返回数据会在基类中自动封装到adapter里。
 * 最少的情况只要重写3个方法就可以实现数据显示了。 onActivityCreated requestData initAdapter
 * @author 马彦君
 * 
 */
public class MCListDemoFragment extends MCBaseV4ListFragment {
	private MCCommonServiceInterface service;

	/**
	 * 没有内容显示什么图标
	 */
	@Override
	public int getNoDataImage() {
		return R.drawable.no_course_icon;
	}

	/**
	 * 没有内容显示什么提示
	 */
	@Override
	public String getNoDataTip() {
		return getResources().getString(R.string.no_focus_course_label);
	}

	/**
	 * 初始化自己的参数
	 */
	public void onActivityCreated(Bundle savedInstanceState) {
		this.service = new MCCommonService();

		super.onActivityCreated(savedInstanceState);

	}

	/**
	 * 请求数据
	 */
	@Override
	public void requestData() {
		this.service.getAllCourse(this.mCurrentPage, "", this, this.getActivity());
	}

	/**
	 * 点击条目做的事情，比如跳转页面等 obj为这个条目的bean
	 */
	@Override
	public void doAfterItemClick(Object obj) {
		Intent intent = new Intent(this.getActivity(), ShowMoocActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("course", (Serializable) obj);
		intent.putExtras(bundle);
		this.startActivityForResult(intent, 10);
	}

	/**
	 * 初始化adapter， 主要是从谢convert方法，给item设置属性
	 */
	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(this.getActivity(), R.layout.allcourse_item_layout) {
			protected void convert(BaseAdapterHelper helper, MCDemoModel item) {
				// 这里给item设置属性
			}

			protected void convert(BaseAdapterHelper helper, Object obj) {
				this.convert(helper, ((MCDemoModel) obj));
			}
		};
	}
}
