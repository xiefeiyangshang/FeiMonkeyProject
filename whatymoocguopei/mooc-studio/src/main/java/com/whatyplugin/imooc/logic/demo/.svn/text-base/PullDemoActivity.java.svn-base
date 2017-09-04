package com.whatyplugin.imooc.logic.demo;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCHomeworkModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.MCGuidanceView;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnFooterRefreshListener;
import com.whatyplugin.uikit.refreshview.MCPullToRefreshView.OnHeaderRefreshListener;

/**
 * 带下拉刷新的activitydemo
 * @author 马彦君
 *
 */
public class PullDemoActivity extends MCBaseActivity implements
		View.OnClickListener, AdapterView.OnItemClickListener,
		MCAnalyzeBackBlock, OnFooterRefreshListener, OnHeaderRefreshListener {

	private String courseId;
	private QuickAdapter adapter;
	private MCPullToRefreshView mListView;
	private MCStudyServiceInterface service;
    private int mCurrentPage; //分页中会用到的指定当前页
    private MCGuidanceView mGuidanceView;
    
	/**
	 * ui填充，adapter初始化
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_list_activity);

		courseId = getIntent().getStringExtra("courseId");
		this.mCurrentPage = 1;
        this.mListView = (MCPullToRefreshView)this.findViewById(R.id.mListView);
        
		this.initView();
		
		this.adapter = new QuickAdapter(this, R.layout.homework_item_layout) {

			protected void convert(BaseAdapterHelper helper, MCHomeworkModel item) {
				//子条目内容的填充在这里进行。
			}

			protected void convert(BaseAdapterHelper helper, Object obj) {
				this.convert(helper, ((MCHomeworkModel) obj));
			}
		};
        this.mListView.setDataAdapter(this.adapter);
        this.service = new MCStudyService();
       
        //查询数据
        requestData();
	}
	
	/**
	 * 这里初始化父视图
	 */
	private void initView() {
		this.findViewById(R.id.back).setOnClickListener(this);
		this.mListView.setOnItemClickListener(this);
        this.mListView.setOnHeaderRefreshListener(this);
        this.mListView.setOnFooterRefreshListener(this);
        this.mGuidanceView = new MCGuidanceView(this);
	}

	/**
	 * 请求数据的主体
	 */
	private void requestData() {
		 this.service.getCourseHomeworkList(this.courseId, mCurrentPage, this, this);
	}

	/**
	 * 这里对回传list进行处理，放到adapter里
	 */
	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {

		this.mListView.onHeaderRefreshComplete();
		this.mListView.onFooterRefreshComplete();
		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
			this.mListView.setAdapterViewWhenHasData();
			if (this.mCurrentPage == 1) {
				this.adapter.clear();
			}
			this.adapter.addAll(resultList);
		} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {
			if (this.mCurrentPage == 1) {
				this.mListView.setGuidanceViewWhenNoData(R.drawable.no_note_icon, R.string.no_homework_label);
			}
		} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
			this.mListView.setGuidanceViewWhenNoNet();
		}
	}

	/**
	 * 子条目的点击
	 */
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		MCHomeworkModel model = (MCHomeworkModel) adapter.getAdapter().getItem(position);
	}

	/**
	 * 父视图上按钮点击事件，如返回
	 */
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.back) {
			this.finish();
		} else {
		}
	}

	@Override
	public void onHeaderRefresh(MCPullToRefreshView refreshView) {
		 this.mCurrentPage = 1;
		 requestData();
	}

	@Override
	public void onFooterRefresh(MCPullToRefreshView refreshView) {
		 ++this.mCurrentPage;
		 requestData();
	}

}
