package com.whaty.imooc.ui.assessment;

import java.util.List;

import android.os.Bundle;
import android.widget.Toast;
import cn.com.whatyguopei.mooc.R;

import com.whaty.imooc.logic.model.GPAssessmentModel;
import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whatyplugin.base.adaper.BaseAdapterHelper;
import com.whatyplugin.base.adaper.QuickAdapter;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.ui.base.MCBaseListActivity;


public class GPAssessmentCriteriaActivity extends MCBaseListActivity {
	private GPPerformanceServiceInterface service;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		service = new GPPerformanceService();
		super.onCreate(savedInstanceState);
		this.mListView.setAllowFooterPull(false);
		this.mListView.setAllowHeaderPull(false);
		
	}

	@Override
	public void requestData() {
		service.getAssessmentAndCriteria("", ((MCAnalyzeBackBlock) this), this);

	}

	@Override
	public void doAfterItemClick(Object obj) {

	}
	
	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
		removeLoading();
		doSomethingWithResult(resultList);
		if(result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE){
			Toast.makeText(this, "网络错误", Toast.LENGTH_LONG).show();
		}
		
		// 不是链接不到网络，并且list为空
		if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE && (resultList == null || resultList.size() == 0)) {
			result.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
			return;
		}
			this.mListView.setAdapterViewWhenHasData();
			this.adapter.clear();
			this.adapter.addAll(resultList);

	}
	

	@Override
	public String getFunctionTitle() {
		return "考核要求";
	}
	@Override
	public String getNoDataTip() {
		return "网络错误";
	}
	@Override
	public void initAdapter() {
		this.adapter = new QuickAdapter(this, R.layout.assessmentcriteria_items) {
			@Override
			protected void convert(BaseAdapterHelper ListView, Object objModel) {
				GPAssessmentModel model = (GPAssessmentModel) objModel;
				ListView.setText(R.id.assessment_title, model.getName());
				ListView.setText(R.id.assessment_content, model.getCommand());
			}
		};
	}

}
