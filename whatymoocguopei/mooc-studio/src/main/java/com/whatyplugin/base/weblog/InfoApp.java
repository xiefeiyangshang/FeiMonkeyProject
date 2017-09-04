package com.whatyplugin.base.weblog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;


public class InfoApp {

	public static BeanApp getInfo(Context context) {
		BeanApp info = new BeanApp();
		try {
			String packageName = context.getPackageName();
			PackageManager pm = context.getPackageManager();
			PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
			ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);
			
			String appName = (String) pm.getApplicationLabel(applicationInfo);
			String description = "guopei-mooc";
			String code = "guopei_Android";
			int version = packageInfo.versionCode;
			
			info.setCode(code);
			info.setDescription(description);
			info.setName(appName);
			info.setVersion(String.valueOf(version));
			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info;
	}
}
