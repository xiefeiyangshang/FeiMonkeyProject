package com.whatyplugin.imooc.ui.base;

import android.app.Fragment;

import com.umeng.analytics.MobclickAgent;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.weblog.WhatyLog;
import com.whatyplugin.base.weblog.WhatyLogParams;

public class MCBaseFragment extends Fragment{



	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(getClassName());
		MCLog.e(getClassName(), "启动");
		if(WhatyLogParams.LOG_LIST.contains(getClassName())){
			WhatyLog.LoadAnalyze(this.getActivity());
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(getClassName());
		if(WhatyLogParams.LOG_LIST.contains(getClassName())){
			WhatyLog.EndAnalyze(this.getActivity());
		}
	}

	public String getClassName(){
		return this.getClass().getSimpleName();
	}

}
