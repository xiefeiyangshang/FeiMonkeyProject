package com.whaty.imooc.ui.index;
import com.tencent.bugly.crashreport.CrashReport;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;

/**
 * 主Application继承MoocApplication
 * @author 马彦君
 *
 */
public class MCMainApplication extends MoocApplication{
	
	@Override
	public void onCreate() {
//		super.isDebug = false;
		super.onCreate();
		CrashReport.initCrashReport(getApplicationContext(), "9141676fad" ,false);//使用腾讯bugly捕获异常
		GPInitInformation.initPluginParams(this);//初始化参数
	}
}
