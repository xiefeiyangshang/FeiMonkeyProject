package com.whatyplugin.imooc.ui.view;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.com.whatyplugin.mooc.R;

import com.whatyplugin.base.log.MCLog;

public class MCTipManager {

	private static final int LOADING = 0;
	private static final int TIPINFO = 1;
	private static Map<String, View> viewMap = new HashMap<String, View>();

	/**
	 * 正在加载的提示
	 * 
	 * @param mContext
	 * @param parentView
	 * @param index
	 */
	public static void initLoading(Context mContext, String tag, View parentView, int index) {
		initInfo(mContext, tag, parentView, index, MCTipManager.LOADING);
	}

	/**
	 * 移除正在加载的提示
	 * 
	 * @param mContext
	 */
	public static void removeLoading(String tag) {
		View view = viewMap.get(tag);
		if (view != null) {
			ViewGroup vp = ((ViewGroup) view.getParent());
			vp.removeView(view);
			viewMap.remove(tag);
		}
	}

	public static void initTipInfo(Context mContext, String tag, View parentView, int index) {

		initInfo(mContext, tag, parentView, index, MCTipManager.TIPINFO);
	}

	private static void initInfo(Context mContext, String tag, View parentView, int index, int type) {
		MCLog.d("MCTipManager", "tag = " + tag);
		if (parentView instanceof ViewGroup) {
			View view = LayoutInflater.from(mContext).inflate(R.layout.guidance_layout_view, null);

			if (type == MCTipManager.TIPINFO) {
				view.findViewById(R.id.loading_layout).setVisibility(View.GONE);
				view.findViewById(R.id.guidance_layout).setVisibility(View.VISIBLE);
			}
			ViewGroup parent = ((ViewGroup) parentView);
			if (mContext instanceof FragmentActivity) {

			} else {
				parent = (ViewGroup) parent.getChildAt(0);// 如果是activity就再找一层
			}

			if (parent instanceof RelativeLayout) {
				parent.addView(view, index);
				View first = parent.getChildAt(0);
				RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
				lp.width = -1;
				lp.height = -1;
				lp.addRule(RelativeLayout.BELOW, first.getId());
				view.setLayoutParams(lp);
			} else if (parent instanceof LinearLayout) {
				parent.addView(view, index);
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
				lp.width = -1;
				lp.height = -1;
				view.setLayoutParams(lp);
			}
			view.bringToFront();
			viewMap.put(tag, view);
		}
	}
}
