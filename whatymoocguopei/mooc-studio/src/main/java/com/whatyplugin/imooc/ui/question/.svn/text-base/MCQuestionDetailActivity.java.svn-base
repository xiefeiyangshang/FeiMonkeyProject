package com.whatyplugin.imooc.ui.question;

import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.utils.DateUtil;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCAnswerQuestionModel;
import com.whatyplugin.imooc.logic.model.MCQuestionModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.CircleImageView;
import com.whatyplugin.imooc.ui.view.MCGuidanceView;

/**
 * 问题详情
 * 
 * @author 马彦君
 * 
 */
public class MCQuestionDetailActivity extends MCBaseActivity implements View.OnClickListener, MCAnalyzeBackBlock {
	private MCStudyServiceInterface service;
	private final static String TAG = "FragmentMyQuestion";
	private String courseId;
	private String userId;
	private MCGuidanceView mGuidanceView;
	private String questionId;
	private String questionBody;
	private TextView tv_answer_count, tv_title, tv_time, tv_desc, tv_nick;// 头标题,答复数，题目，时间，内容，昵称
	private LinearLayout picContentLayout;

	public MCQuestionDetailActivity() {
		super();
	}

	public void OnAnalyzeBackBlock(MCServiceResult result, List myQeustionList) {

		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {

			MCQuestionModel questionModel = (MCQuestionModel) myQeustionList.get(0);
			this.tv_title.setText(questionModel.getTitle());
			this.questionBody = questionModel.getBody();
			if ("0".equals(questionModel.getReplyCount())) {
				this.tv_answer_count.setText("暂无答复");
			} else {
				this.tv_answer_count.setText(questionModel.getReplyCount() + "个答复");
			}

			MCQuestionCommon.showPicInItemWithActivity(this, questionModel.getImgUrlList(), MCQuestionDetailActivity.this);
			Date data = DateUtil.parse(questionModel.getPublishDate(), DateUtil.FORMAT_LONG);
			this.tv_time.setText(DateUtil.format(data, DateUtil.FORMAT_MINUTE));
			this.tv_desc.setText(questionModel.getBody());
			this.tv_nick.setText("提问人：" + questionModel.getSubmituserName());

			LayoutInflater inflater = getLayoutInflater();

			for (MCAnswerQuestionModel question : questionModel.getQuestionModelList()) {
				View itemLayout = inflater.inflate(R.layout.item_question, null);
				TextView tv_desc = (TextView) itemLayout.findViewById(R.id.tv_desc);
				TextView tv_nick = (TextView) itemLayout.findViewById(R.id.tv_nick);
				TextView tv_time = (TextView) itemLayout.findViewById(R.id.tv_time);
				CircleImageView question_headimage = (CircleImageView) itemLayout.findViewById(R.id.question_headimage);
				TextView tv_answer_count = (TextView) itemLayout.findViewById(R.id.tv_answer_count);
				ImageView iv_recmd = (ImageView) itemLayout.findViewById(R.id.iv_recmd);
				tv_desc.setText(question.getBody());
				tv_nick.setText(question.getReuserName());
				data = DateUtil.parse(question.getPublishDate(), DateUtil.FORMAT_LONG);
				tv_time.setText(DateUtil.format(data, DateUtil.FORMAT_NEW_SHORT));
				tv_answer_count.setText("");
				question_headimage.setImageUrl(question.getAvatarUrl());
				
				if ("teacher".equals(question.getReuserType())) {
					iv_recmd.setImageResource(R.drawable.question_teacher_replay);
					iv_recmd.setVisibility(View.VISIBLE);
				} else if ("1".equals(question.getIsRecommend())) {
					iv_recmd.setImageResource(R.drawable.question_teacher_recommend);
					iv_recmd.setVisibility(View.VISIBLE);
				} else {
					iv_recmd.setVisibility(View.GONE);
				}

				this.picContentLayout.addView(itemLayout);
				MCQuestionCommon.showPicInItemWithView(itemLayout, question.getImgUrlList(), MCQuestionDetailActivity.this);
			}

		} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_EMPTY) {

		} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
			this.mGuidanceView.setGuidanceBitmap(R.drawable.no_network_icon);
			this.mGuidanceView.setGuidanceText(R.string.no_network_label);
			this.mGuidanceView.setLayoutMarginTop(this.getResources().getDimensionPixelSize(R.dimen.mooc_110_dp));
		} else {

		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_detail);
		initView();
		initData();
		requestData();
	}

	private void initData() {
		this.userId = MCSaveData.getUserInfo(Contants.USERID, this).toString();
		this.service = new MCStudyService();
		if (getIntent() != null) {
			this.questionId = getIntent().getStringExtra("questionId");
			this.courseId = getIntent().getStringExtra("courseId");
		}
	}

	private void initView() {
		this.mGuidanceView = new MCGuidanceView(this);
		mGuidanceView.setGuidanceBackgroundColor(0xffECECEC);
		this.tv_answer_count = (TextView) findViewById(R.id.tv_answer_count);
		this.tv_title = (TextView) findViewById(R.id.tv_title);
		this.tv_desc = (TextView) findViewById(R.id.tv_desc);
		this.tv_time = (TextView) findViewById(R.id.tv_time);
		this.tv_nick = (TextView) findViewById(R.id.tv_nick);
		this.picContentLayout = (LinearLayout) findViewById(R.id.mListView);
		this.findViewById(R.id.anser_question).setOnClickListener(this);
	}

	private void requestData() {
		this.service.getQuestionDetail(this.questionId, this.userId, this.courseId, 1, this, this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.anser_question) {
			Intent intent = new Intent(this, MCQuestionAnswerActivity.class);
			intent.putExtra("courseId", courseId);
			intent.putExtra("questionBody", questionBody);
			intent.putExtra("questionId", questionId);
			startActivityForResult(intent, 0);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == 1) {
			this.picContentLayout.removeAllViews();
			requestData();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
