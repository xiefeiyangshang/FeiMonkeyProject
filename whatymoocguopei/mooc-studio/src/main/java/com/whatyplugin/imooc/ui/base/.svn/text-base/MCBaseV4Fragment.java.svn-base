package com.whatyplugin.imooc.ui.base;


import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.weblog.WhatyLog;
import com.whatyplugin.base.weblog.WhatyLogParams;

public class MCBaseV4Fragment extends Fragment{



	@Override
	public void onResume() {
		super.onResume();
		String className = getClassName();
		MobclickAgent.onPageStart(className);
		MCLog.e(getClassName(), "启动");
		if(WhatyLogParams.LOG_LIST.contains(className)){
			WhatyLog.LoadAnalyze(this.getActivity(), className);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		String className = getClassName();
		MobclickAgent.onPageEnd(className);
		if(WhatyLogParams.LOG_LIST.contains(className)){
			WhatyLog.EndAnalyze(this.getActivity(), className);
		}
	}

	public String getClassName(){
		return this.getClass().getSimpleName();
	}

}
