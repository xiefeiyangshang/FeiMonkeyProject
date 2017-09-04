package com.whatyplugin.base.weblog;import android.content.Context;import android.util.DisplayMetrics;
public class InfoScreen {

	public static BeanScreen getScreen(Context context) {
		
		DisplayMetrics dm = new DisplayMetrics();  
		dm = context.getResources().getDisplayMetrics();  
		
		
//		WindowManager winMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//		Display display = winMgr.getDefaultDisplay();
//		DisplayMetrics dm = new DisplayMetrics();
//		display.getMetrics(dm);
		BeanScreen screen = new BeanScreen();
		screen.setColorDepth(dm.densityDpi);
		screen.setHeight(dm.heightPixels);
		screen.setWidth(dm.widthPixels);
		return screen;
	}
}
