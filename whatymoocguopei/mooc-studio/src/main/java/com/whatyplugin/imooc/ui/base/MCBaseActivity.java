
package com.whatyplugin.imooc.ui.base;

import android.app.Activity;

import com.umeng.analytics.MobclickAgent;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.weblog.WhatyLog;
import com.whatyplugin.base.weblog.WhatyLogParams;
import com.whatyplugin.imooc.ui.view.MCTipManager;

public class MCBaseActivity extends Activity {
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getTag());
		MobclickAgent.onResume(this);
		MCLog.e(getTag(), "启动");
		if (WhatyLogParams.LOG_LIST.contains(getTag())) {
			try {
				WhatyLog.LoadAnalyze(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getTag());
		MobclickAgent.onPause(this);

		if (WhatyLogParams.LOG_LIST.contains(getTag())) {
			try {
				WhatyLog.EndAnalyze(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getTag() {
		return getClass().getSimpleName();
	}

	/**
	 * 有标题的用这个产生正在加载
	 */
	public void initLoadingWithTitle() {
		MCTipManager.initLoading(this, this.toString(), findViewById(android.R.id.content), 1);
	}

	/**
	 * 没有标题的用这个产生正在加载
	 */
	public void initLoadingNoTitle() {


		MCTipManager.initLoading(this, this.toString(), findViewById(android.R.id.content), 0);
	}

	/**
	 * 移除正在加载，常用在网络请求回来的时候第一时间调用
	 */
	public void removeLoading() {
		MCTipManager.removeLoading(this.toString());
	}
}
