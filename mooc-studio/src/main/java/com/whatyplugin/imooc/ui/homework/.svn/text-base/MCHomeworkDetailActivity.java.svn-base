package com.whatyplugin.imooc.ui.homework;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCHomeworkModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.service_.MCStudyServiceInterface;
import com.whatyplugin.imooc.logic.utils.WebViewUtil;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.imooc.ui.view.BaseTitleView;
import com.whatyplugin.imooc.ui.view.BaseTitleView.RightClickListener;
import com.whatyplugin.imooc.ui.view.ScoreWithPicView;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

/**
 * 带下拉刷新的activitydemo
 * 
 * @author 马彦君
 * 
 */
public class MCHomeworkDetailActivity extends MCBaseActivity implements View.OnClickListener, MCAnalyzeBackBlock {

	private MCStudyServiceInterface service;
	public MCHomeworkModel homeworkModel;
	private TextView content_label;
	private TextView teacher_comments_lable;
	private WebView detail_label;
	private TextView time_label;
	private TextView selfanswer_label;
	private View self_content;
	private View teacher_content;
	private BaseTitleView titleView;
	private ScoreWithPicView score_content;
	public WebView answer_label;
	private Button redo;
	private Handler mHandler = new Handler();
	private MCCreateDialog createDialog = new MCCreateDialog();

	/**
	 * ui填充，adapter初始化
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homework_detail_layout);
		homeworkModel = (MCHomeworkModel) getIntent().getSerializableExtra("homework");
		this.initView();
		initEvent();

		// 查询数据
		requestData();
	}

	/**
	 * 这里初始化父视图
	 */
	private void initView() {
		titleView = (BaseTitleView) findViewById(R.id.rl_titile);
		this.content_label = (TextView) findViewById(R.id.content_label);
		this.selfanswer_label = (TextView) findViewById(R.id.selfanswer_label);
		this.answer_label = (WebView) findViewById(R.id.answer_label);
		this.teacher_comments_lable = (TextView) findViewById(R.id.teacher_comments_lable);
		this.detail_label = (WebView) findViewById(R.id.detail_label);
		this.time_label = (TextView) findViewById(R.id.time_label);
		this.score_content = (ScoreWithPicView) this.findViewById(R.id.score_content);
		this.teacher_content = findViewById(R.id.teacher_content);
		this.self_content = findViewById(R.id.self_content);
		this.redo = (Button) findViewById(R.id.redo);

	}

	private void initEvent() {
		this.redo.setOnClickListener(this);
		this.service = new MCStudyService();
		titleView.setRigTextListener(new RightClickListener() {

			@Override
			public void onRightViewClick() {
				uploadHomework();
			}
		});
	}

	/**
	 * 请求数据的主体
	 */
	private void requestData() {
		String homeworkStuId = this.homeworkModel.getHomeworkStuId();

		this.content_label.setText(this.homeworkModel.getTitle());
		WebViewUtil.loadContentWithPic(this.homeworkModel.getDetail(), detail_label, this);
		this.time_label.setText(this.getString(R.string.homework_detail_time,
				new Object[] { this.homeworkModel.getStartDateWithYear(), this.homeworkModel.getEndDateWithYear() }));
		titleView.setRightTextVisible(false);// 隐藏按钮
		if (this.homeworkModel.getLocalStatus() == 5) {
			TextView over_time_tip = (TextView) this.findViewById(R.id.over_time_tip);
			if (this.homeworkModel.isOverTime()) {
				over_time_tip.setText("作业已过期！");
			} else {
				over_time_tip.setText("还未到开始时间！");
			}
			over_time_tip.setVisibility(View.VISIBLE);
			this.selfanswer_label.setVisibility(View.GONE);
			this.answer_label.setVisibility(View.GONE);
		} else if (TextUtils.isEmpty(homeworkStuId)) {
			// 没做过作业的就不用去网络请求了。
			this.self_content.setVisibility(View.GONE);
		} else {
			reQuestDataHomeWorkDetail();
		}
	}

	public void reQuestDataHomeWorkDetail() {
		this.service.getHomeworkDetail(this.homeworkModel.getHomeworkStuId(), 1, this, this);
	}

	/**
	 * 这里对回传list进行处理，放到adapter里
	 */
	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {

		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
			MCHomeworkModel model = (MCHomeworkModel) resultList.get(0);

			// 返回的值里面无法获取courseId， 这里要给封装上。
			model.setCourseId(this.homeworkModel.getCourseId());
			this.homeworkModel = model;
			this.content_label.setText(model.getTitle());
			WebViewUtil.loadContentWithPic(model.getDetail(), detail_label, this);

			WebViewUtil.loadContentWithPic(model.getNote(), this.answer_label, this);

			this.time_label.setText(this.getString(R.string.homework_detail_time, new Object[] { model.getStartDateWithYear(), model.getEndDateWithYear() }));

			if (model.getLocalStatus() == 1) {// 服务器有草稿，可以重做的情况
				this.redo.setVisibility(View.VISIBLE);
				this.selfanswer_label.setText("PC端保存的草稿：");
				titleView.setRightTextVisible(true);
			} else if (model.getLocalStatus() == 3) {// 成绩已出
				showScore(model);
			} else {
				this.redo.setVisibility(View.INVISIBLE);
				this.selfanswer_label.setText("你的答案：");
				titleView.setRightTextVisible(false);
			}

		} else {
			MCToast.show(this, "获取作业详情失败，请检查网络是否通畅！");
		}
	}

	/**
	 * 
	 * 有成绩的显示方式
	 * 
	 */

	public void showScore(MCHomeworkModel model) {

		this.teacher_content.setVisibility(View.VISIBLE);
		this.teacher_comments_lable.setText(model.getComments() == null ? "暂无" : model.getComments());
		// 有成绩的才显示成绩
		if (!TextUtils.isEmpty(model.getTotalScore())) {
			this.score_content.setVisibility(View.VISIBLE);
			int score = 0;
			try {
				score = Integer.valueOf(model.getTotalScore());
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.score_content.setScore(score, ScoreWithPicView.COMMON);

			try {
				// 如果有分数就让标题在分数的坐标。
				RelativeLayout.LayoutParams lp = (LayoutParams) findViewById(R.id.top_content).getLayoutParams();
				lp.addRule(RelativeLayout.LEFT_OF, R.id.score_content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 父视图上按钮点击事件，如返回
	 */
	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.redo) {
			redoHomework();
		}
	}

	/**
	 * 重做作业，给出提示
	 */
	private void redoHomework() {
		final MCCommonDialog dialog = new MCCommonDialog(null, "确定重做作业吗？\n一旦重做手机上将不再显示PC端的草稿！", R.layout.okcancel_dialog);
		dialog.show(createDialog.getFragmentTransaction(this), "homedetail");
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				dialog.setCommitListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MCHomeworkDetailActivity.this, MCHomeworkCommitActivity.class);
						Bundle bundle = new Bundle();
						MCHomeworkDetailActivity.this.homeworkModel.setNote("");
						bundle.putSerializable("homework", MCHomeworkDetailActivity.this.homeworkModel);
						intent.putExtras(bundle);
						MCHomeworkDetailActivity.this.startActivityForResult(intent, 12);
					}
				});
			}
		}, 200);

	}

	/**
	 * 提交作业
	 */
	private void uploadHomework() {

		service.commitHomeWork(this.homeworkModel, 1, new MCAnalyzeBackBlock() {
			@Override
			public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
					createDialog.createOkDialog(MCHomeworkDetailActivity.this, "联网失败！");
				} else if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_FAILURE) {
					createDialog.createOkDialog(MCHomeworkDetailActivity.this, "保存失败！");
				} else {
					final MCCommonDialog inner = createDialog.createOkDialog(MCHomeworkDetailActivity.this, "提交成功！");
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							inner.setCommitListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									setResultAndFinish(-2);
								}
							});
						}
					}, 200);

				}
			}
		}, this);
	}

	private void setResultAndFinish(int code) {
		Intent retIntent = new Intent();
		Bundle retBundle = new Bundle();
		retBundle.putSerializable("homeworkModel", MCHomeworkDetailActivity.this.homeworkModel);
		retIntent.putExtras(retBundle);
		setResult(code, retIntent);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// 提交作业后的数据更新
		if (requestCode == MCHomeworkCommon.DO_HOMEWORK_CODE && resultCode == MCHomeworkCommon.COMMIT_HOMEWORK_RESULT) {
			requestData();// 如果是提交作业就再次请求
		}

		// 保存了本地草稿, 当前页面退出
		if (requestCode == MCHomeworkCommon.DO_HOMEWORK_CODE && resultCode == MCHomeworkCommon.SAVE_LOCAL_HOMEWORK_RESULT) {
			MCHomeworkModel homeworkModel = (MCHomeworkModel) data.getSerializableExtra("homeworkModel");
			homeworkModel.setLocalStatus(0);// 设置为显示本地草稿、做作业的形式
			Intent retIntent = new Intent();
			Bundle retBundle = new Bundle();
			retBundle.putSerializable("homeworkModel", homeworkModel);
			retIntent.putExtras(retBundle);
			this.setResult(resultCode, retIntent);
			this.finish();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		WebViewUtil.DestoryWebView(answer_label);
	}
}
