package com.whaty.imooc.ui.workshop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.whaty.imooc.logic.model.GPScoreModel;
import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.ui.base.MCBaseV4Fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.whatyguopei.mooc.R;

public class SchoolBasedFragment extends MCBaseV4Fragment implements MCAnalyzeBackBlock {
	// private ScoreWithPicView swPic;
	private View viewContext;
	private SwipeRefreshLayout mSRefresh;
	private ImageView iv_pic;
	private TextView tv_textInfo;
	private TextView tv_scoreInfo;
	private GPPerformanceServiceInterface serviceDate;

	private ImageView mScoreLeft, mScoreRight, mLine;

	private Handler handler;
	private Map<Integer, Integer> drawMap = new HashMap<Integer, Integer>();
	private int delayTime;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		viewContext = inflater.inflate(R.layout.schoolbased_layout, null);
		// initView();
		// initGone();
		return viewContext;
	}

	private void initView() {
		// by zk
		initDrawMap();
		initHandler();
		mScoreLeft = (ImageView) viewContext.findViewById(R.id.scoreLeft);
		mScoreRight = (ImageView) viewContext.findViewById(R.id.scoreRight);
		mLine = (ImageView) viewContext.findViewById(R.id.bottomLine);
		//
		mSRefresh = (SwipeRefreshLayout) getActivity().findViewById(R.id.refreshlayout);
		iv_pic = (ImageView) viewContext.findViewById(R.id.wait_score);
		tv_textInfo = (TextView) viewContext.findViewById(R.id.textinfo);
		tv_scoreInfo = (TextView) viewContext.findViewById(R.id.scoreinfo);
		// swPic = (ScoreWithPicView)
		// getActivity().findViewById(R.id.score_content);

		mSRefresh.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				mSRefresh.setRefreshing(true);
				requestDate();
			}
		});

	}

	private void initGone() {
		iv_pic.setVisibility(View.GONE);
		tv_textInfo.setVisibility(View.GONE);
		// swPic.setVisibility(View.GONE);
		tv_scoreInfo.setVisibility(View.GONE);
	}

	// private void setScore(Integer score) {
	// if (score > 100 || score <0) {
	// Toast.makeText(getActivity(), "动态分数应该在0-100之间",
	// Toast.LENGTH_LONG).show();
	// return;
	// }else{
	// String str_Score = String.valueOf(score);
	// if(str_Score.length() == 1){
	//
	// }
	// }
	//
	// initGone();
	// tv_scoreInfo.setVisibility(View.VISIBLE);
	// // swPic.setVisibility(View.VISIBLE);
	// // swPic.setScore(score, ScoreWithPicView.COMMON);
	// }

	/**
	 * 
	 */
	private void initHandler() {
		this.handler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				try {
					switch (msg.what) {
					case 1:

						showScoreWithPic(Integer.valueOf(msg.obj.toString()));
						break;
					}
				} catch (Exception e) {
				}
			}
		};
	}

	public void setScore(final int score) {
		if (score > 100 || score < 0) {
			Toast.makeText(getActivity(), "动态显示分数 - 数字应该在0-100之间", Toast.LENGTH_SHORT).show();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				int first = score / 3;
				int second = score * 2 / 3;
				for (int i = 0; i <= first; i++) {
					sendScoreToHandler(i, 1);
				}
				for (int i = first; i <= second; i++) {
					sendScoreToHandler(i, 2 * delayTime);
				}
				for (int i = second; i <= score; i++) {
					sendScoreToHandler(i, 6 * delayTime);
				}
			}

			private void sendScoreToHandler(int i, int times) {
				Message msg = new Message();
				msg.what = 1;
				msg.obj = i;
				handler.sendMessage(msg);
				try {
					Thread.sleep(times);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		tv_scoreInfo.setVisibility(View.VISIBLE);
		showScoreWithPic(score);
		mSRefresh.postInvalidate();
	}

	private void showScoreWithPic(int score) {

		if (score == 100) {
			mScoreLeft.setImageResource(drawMap.get(100));
			mScoreRight.setVisibility(View.INVISIBLE);
		} else if (score >= 0 && score < 10) {
			mScoreLeft.setImageResource(drawMap.get(score));
			mScoreRight.setVisibility(View.INVISIBLE);
		} else {
			mScoreLeft.setImageResource(drawMap.get(score / 10));
			mScoreRight.setImageResource(drawMap.get(score % 10));
			mScoreRight.setVisibility(View.VISIBLE);
		}
	}

	private void initDrawMap() {
		drawMap.put(0, R.drawable.num0);
		drawMap.put(1, R.drawable.num1);
		drawMap.put(2, R.drawable.num2);
		drawMap.put(3, R.drawable.num3);
		drawMap.put(4, R.drawable.num4);
		drawMap.put(5, R.drawable.num5);
		drawMap.put(6, R.drawable.num6);
		drawMap.put(7, R.drawable.num7);
		drawMap.put(8, R.drawable.num8);
		drawMap.put(9, R.drawable.num9);
		drawMap.put(100, R.drawable.num100);
	}

	// 设置图片变动时候的延时
	public void setDelayTime(int time) {
		this.delayTime = time;
	}

	private void setNoScore() {
		initGone();
		iv_pic.setVisibility(View.VISIBLE);
		tv_textInfo.setVisibility(View.VISIBLE);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		serviceDate = new GPPerformanceService();
		requestDate();
		initView();
		initGone();

	}

	private void requestDate() {
		serviceDate.studentScore(getActivity(), this);
	}

	@Override
	public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
		
		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE) {
			Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_LONG).show();
			return;
		}

		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS && resultList.size() > 0) {
			GPScoreModel gpScoreModel = (GPScoreModel) resultList.get(0);
			mSRefresh.setRefreshing(false);
			Integer score = gpScoreModel.getScore();
			Log.d("zk", "score == " + score);
			if (score == -1) {
				setNoScore();
			} else {
				mLine.setVisibility(View.VISIBLE);
				setScore(score);
			}
			// swPic.postInvalidate();
			mSRefresh.postInvalidate();
		}

	}

}
