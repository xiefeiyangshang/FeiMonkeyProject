package com.whatyplugin.base.viewpager;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.imooc.ui.mymooc.MoocApplication;

public class ViewPagerChange {
	private static int viewPagerChange;
	private static int viewPagerNum;
	private static String color;
	private static int themColor;
	private static int whiteColor;
	private static int alpha;
	private static int r;
	private static int g;
	private static int b;

	static {
		themColor = MoocApplication.getInstance().getResources().getColor(R.color.theme_color);
		whiteColor = MoocApplication.getInstance().getResources().getColor(android.R.color.white);
		color = Integer.toHexString(themColor);
		alpha = Integer.parseInt(color.substring(0, 2), 16);
		r = Integer.parseInt(color.substring(2, 4), 16);
		g = Integer.parseInt(color.substring(4, 6), 16);
		b = Integer.parseInt(color.substring(6, 8), 16);

	}

	/**
	 * 改变点击事件,返回当前点击位置
	 * 
	 * @param index
	 *            当前点击位置
	 * @param current
	 *            上次点击位置
	 * @param viewPager
	 * @param tvs
	 *            tabbar
	 */

	public static int updatePagerChange(int index, int current, ViewPager viewPager, TextView[] tvs) {

		if (current == index)
			return current;
		for (int i = 0; i < tvs.length; i++) {
			((GradientDrawable) tvs[i].getBackground()).setColor(whiteColor);
			tvs[i].setTextColor(themColor);
		}
		tvs[index].setTextColor(whiteColor);
		((GradientDrawable) tvs[index].getBackground()).setColor(themColor);

		if (viewPager.getCurrentItem() != index)
			viewPager.setCurrentItem(index);
		return index;

	}

	/**
	 * 防止代码复用，颜色不正常问题
	 * 
	 * @param index
	 * @param current
	 * @param viewPager
	 * @param tvs
	 */

	public static void backUpdateTabBar(int index, TextView[] tvs) {

		for (int i = 0; i < tvs.length; i++) {
			((GradientDrawable) tvs[i].getBackground()).setColor(whiteColor);
			tvs[i].setTextColor(themColor);
		}
		tvs[index].setTextColor(whiteColor);
		((GradientDrawable) tvs[index].getBackground()).setColor(themColor);
	}

	/**
	 * 
	 * 
	 * @param arg0
	 *            当前页面index
	 * @param arg1
	 *            过度量
	 * @param arg2
	 *            当前页面大小
	 * @param tvs
	 *            tabbar
	 */

	public static void pagerViewTabBar(int arg0, float arg1, int arg2, TextView[] tvs) {
		// 右滑
		if (arg2 > viewPagerChange) {

			GradientDrawable sdLeft = (GradientDrawable) tvs[arg0].getBackground();
			sdLeft.setColor(Color.argb(Math.round((1 - arg1) * alpha), r, g, b));
			tvs[arg0].setTextColor(themColor);

			GradientDrawable sdRight = (GradientDrawable) tvs[arg0 + 1].getBackground();
			sdRight.setColor(Color.argb(Math.round(arg1 * alpha), r, g, b));
			tvs[arg0 + 1].setTextColor(whiteColor);
		} else if (viewPagerNum == arg0 && arg2 > 0) {
			GradientDrawable sdRight = (GradientDrawable) tvs[arg0].getBackground();
			sdRight.setColor(Color.argb(Math.round((1 - arg1) * alpha), r, g, b));
			tvs[arg0].setTextColor(whiteColor);
			GradientDrawable sdLeft = (GradientDrawable) tvs[arg0 + 1].getBackground();
			sdLeft.setColor(Color.argb(Math.round(arg1 * alpha), r, g, b));
			tvs[arg0 + 1].setTextColor(themColor);

		}
		viewPagerNum = arg0;
		viewPagerChange = arg2;

	}

}
