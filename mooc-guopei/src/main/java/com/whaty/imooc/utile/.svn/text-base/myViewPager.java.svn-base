package com.whaty.imooc.utile;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * 默认为不可切换
 * 
 * @author 李庆举
 *
 */
public class myViewPager  extends ViewPager{
	private boolean isCanScroll = true;
	public myViewPager(Context context) {
		super(context);
	}

	public myViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return isCanScroll?false:super.onTouchEvent(arg0);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
	              
		return isCanScroll?false:super.onInterceptTouchEvent(arg0);
	}

	public boolean isCanScroll() {
		return isCanScroll;
	}

	public void setCanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}
	
	
	
}
