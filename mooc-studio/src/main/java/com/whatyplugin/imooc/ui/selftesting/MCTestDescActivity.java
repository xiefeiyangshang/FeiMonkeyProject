package com.whatyplugin.imooc.ui.selftesting;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.enums.TimeStyle;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.imooc.logic.model.MCTestInfo;
import com.whatyplugin.imooc.logic.model.MCTestModel;
import com.whatyplugin.imooc.logic.model.MCTestStatisticInfo;
import com.whatyplugin.imooc.ui.base.MCBaseActivity;
import com.whatyplugin.uikit.toast.MCToast;

/**
 * 课程自测详情
 * 
 * @author banzhenyu
 * 
 */
public class MCTestDescActivity extends MCBaseActivity implements OnClickListener {

	private static final String TAG = "MCTestDescActivity";
	private MCTestModel mcTestModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selftest_desc);

		// GET PARCELABLE
		Intent intent = getIntent();
		if (intent != null) {
			mcTestModel = intent.getParcelableExtra("MCTestModel");
		}
		// 标题
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(mcTestModel.getTitle());

		// 开始结束时间
		TextView tv_time = (TextView) findViewById(R.id.tv_time);
		tv_time.setText(mcTestModel.getDescTime(TimeStyle.Y));

		// 测试用时
		if (mcTestModel.getTxtLimitTime() > 0) {
			TextView tv_limit_time = (TextView) findViewById(R.id.tv_limit_time);
			tv_limit_time.setText(String.valueOf(mcTestModel.getTxtLimitTime() / 60 / 1000) + "分钟");
			findViewById(R.id.rl_limit_time).setVisibility(View.VISIBLE);
		}

		// 题目构成
		TextView tv_constitute = (TextView) findViewById(R.id.tv_constitute);
		StringBuffer sb = new StringBuffer();
		//
		MCTestInfo testInfo = mcTestModel.getTestInfo();
		if (testInfo != null && testInfo.statisticInfo != null && !testInfo.statisticInfo.isEmpty()) {
			Set<Entry<String, MCTestStatisticInfo>> entrySet = testInfo.statisticInfo.entrySet();
			Iterator<Entry<String, MCTestStatisticInfo>> iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Entry<String, MCTestStatisticInfo> next = iterator.next();
				MCTestStatisticInfo value = next.getValue();
				if (value.count > 0) { // 是零的话就不显示了
					String temType = "";
					if (value.type.equals("DANXUAN")) {
						temType = "单选题";
					} else if (value.type.equals("DUOXUAN")) {
						temType = "多选题";
					} else if (value.type.equals("PANDUAN")) {
						temType = "判断题";
					}
					sb.append(temType + value.count + "题,每题" + value.score + "分;\n");
					temType = null;
				}
			}
		}
		if (sb.toString() != null && !sb.toString().equals(""))
			tv_constitute.setText(sb.toString().replaceFirst(";\n$", "") + "。\n");
		
		
		// 关联知识点 ， 数据是json数组
		TextView tv_relation = (TextView) findViewById(R.id.tv_relation);
		if (testInfo.desc != null) {
			StringBuilder sbder;
			try {
				JSONArray jarr = new JSONArray(testInfo.desc);
				if (jarr != null) {
					sbder = new StringBuilder();
					for (int i = 0; i < jarr.length(); i++) {
						sbder.append(jarr.get(i) + ",\n");
					}
					if (sbder.toString() != null && !sbder.toString().equals(""))
						tv_relation.setText(sbder.toString().replaceFirst(",$\n", "") + "。\n");
				}
			} catch (JSONException e) {
				e.printStackTrace();
				tv_relation.setText("暂无");
			}
		}

		findViewById(R.id.bt_start).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (MCNetwork.checkedNetwork(this)) {
			Intent intent = new Intent(MCTestDescActivity.this, MCTestDoActivity.class);
			intent.putExtra("MCTestModel", mcTestModel);
			startActivityForResult(intent, 0);
		} else {
			MCToast.show(this, "请您检查网络连接!");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 202 || resultCode == 200) {
			setResult(resultCode, data);
			this.finish();
		} else {
			this.finish();
		}
	}
}
