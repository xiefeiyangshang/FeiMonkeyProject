package com.whatyplugin.imooc.ui.mymooc;

import android.app.Application;
import android.content.Context;

import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.db.MoocContentProvider;

public class MoocApplication extends Application {
	private static CrashHandler crashHandler;
	private static Context context;
	private static String packageName;

	public static Context getInstance() {
		return context;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initMoocApplication(this);
	}

	public static String getMainPackageName() {
		if (packageName == null) {
			packageName = context.getPackageName();
		}
		return packageName;
	}
	
	/**
	 * 不继承该类的时候调用此方法进行初始化。
	 * @param icontext
	 */
	public static void initMoocApplication(Context icontext){
		if(context == null){
			
			context = icontext;
			
			crashHandler = CrashHandler.getInstance();
			
			// 注册crashHandler
			crashHandler.init(icontext);
			
			// 初始化内容提供者
			MoocContentProvider.initMatchUri();// 初始化内容提供者

			// 初始化文件保存路径
			Contants.updateVideoPath();
		}
	}
}
