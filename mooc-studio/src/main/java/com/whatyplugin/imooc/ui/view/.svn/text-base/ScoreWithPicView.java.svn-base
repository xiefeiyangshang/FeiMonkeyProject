package com.whatyplugin.imooc.ui.view;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.com.whatyplugin.mooc.R;


/**
 * 显示分数
 * 
 * @author Administrator
 * 
 */
public class ScoreWithPicView extends RelativeLayout {
	public static final int COMMON = 0;// 正常形式的
	public static final int INCREMENT = 1;// 增长形式的

	private RelativeLayout layout = null;
	private Context context;

	private ImageView first_num;
	private ImageView second_num;
	private Map<Integer, Integer> drawMap = new HashMap<Integer, Integer>();
	private int delayTime;
	private Handler handler;

	public ScoreWithPicView(Context context) {
		super(context);
		initView(context);
		this.context = context;
	}

	public ScoreWithPicView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);

		initView(context);
		this.context = context;
	}

	public ScoreWithPicView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
		this.context = context;
	}

	private void initView(Context context) {
		this.delayTime = 20;
		initDrawMap();
		initHandler();
		if (this.layout == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.layout = (RelativeLayout) inflater.inflate(
					R.layout.score_view_layout, this);
		}
		this.first_num = (ImageView) this.layout.findViewById(R.id.first_num);
		this.second_num = (ImageView) this.layout.findViewById(R.id.second_num);
	}

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

	public void setScore(final int score, int type) {
		if (score > 100 || score < 0) {
			Toast.makeText(this.context, "动态显示分数 - 数字应该在0-100之间",
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (type == ScoreWithPicView.INCREMENT) {
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
		} else {
			showScoreWithPic(score);
		}
	}

	private void showScoreWithPic(int score) {

		if (score == 100) {
			this.first_num.setImageResource(drawMap.get(100));
			this.second_num.setVisibility(View.INVISIBLE);
		} else if (score >= 0 && score < 10) {
			this.first_num.setImageResource(drawMap.get(score));
			this.second_num.setVisibility(View.INVISIBLE);
		} else {
			this.first_num.setImageResource(drawMap.get(score / 10));
			this.second_num.setImageResource(drawMap.get(score % 10));
			this.second_num.setVisibility(View.VISIBLE);
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
}
