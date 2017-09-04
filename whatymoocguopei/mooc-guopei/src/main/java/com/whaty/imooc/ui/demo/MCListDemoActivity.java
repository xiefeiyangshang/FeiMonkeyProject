package com.whaty.imooc.ui.demo;

import java.util.List;

import android.os.Bundle;
import cn.com.whatyplugin.mooc.R;

import com.whaty.imooc.logic.model.MCDemoModel;
import com.whaty.imooc.logic.service_.MCCommonService;
import com.whaty.imooc.logic.service_.MCCommonServiceInterface;
import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.imooc.ui.base.MCBaseListActivity;


/**
 * listview类型的activity demo 
 * 返回数据会在基类中自动封装到adapter里
 * 最少的情况只要重写3个方法就可以实现数据显示了 onCreate requestData initAdapter
 * 
 * @author 马彦君
 * 
 */
public class MCListDemoActivity extends MCBaseListActivity {

	private String courseId;
	private MCCommonServiceInterface service;

	/**
	 * 子类有自己特殊提示的时候重写此方法
	 */
	public String getNoDataTip() {
		return "作业列表为空";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.courseId = getIntent().getStringExtra("courseId");
		this.service = new MCCommonService();
		super.onCreate(savedInstanceState);
	}

	/**
	 * 请求数据的主体
	 */
	public void requestData() {
		this.service.getAllCourse(mCurrentPage, this.courseId, this, this);
	}

	/**
	 * 条目点击事件
	 */
	@Override
	public void doAfterItemClick(Object obj) {
	}

	/**
	 * 设置标题
	 */
	@Override
	public String getFunctionTitle() {
		return "我的作业";
	}

	/**
	 * 对返回的集合做的一些处理，可以不重写此方法
	 * 
	 * @param resultList
	 */
	@Override
	public void doSomethingWithResult(List resultList) {
	}

	/**
	 * 初始化adapter， 主要是从谢convert方法，给item设置属性
	 */
	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(this, R.layout.allcourse_item_layout) {
			protected void convert(BaseAdapterHelper helper, MCDemoModel item) {
				// 这里给item设置属性
			}

			protected void convert(BaseAdapterHelper helper, Object obj) {
				this.convert(helper, ((MCDemoModel) obj));
			}
		};
	}

}
