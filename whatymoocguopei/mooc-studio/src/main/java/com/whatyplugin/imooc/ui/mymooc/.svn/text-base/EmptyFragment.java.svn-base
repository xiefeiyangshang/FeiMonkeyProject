package com.whatyplugin.imooc.ui.mymooc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.com.whatyplugin.mooc.R;

public class EmptyFragment extends Fragment{
	private static final String TAG = "EmptyFragment";
	private View view;
	private String title;
	
	@Override
	public void onResume() {
		super.onResume();
//		MobclickAgent.onPageStart(TAG);
//		WhatyLog.LoadAnalyze(getActivity(),TAG);
	}
	
	@Override
	public void onPause() {
		super.onPause();
//		MobclickAgent.onPageEnd(TAG);
//		WhatyLog.EndAnalyze(getActivity(),TAG);
	}
	
	public static EmptyFragment newInstance(String title) {
		EmptyFragment df = new EmptyFragment();
		Bundle args = new Bundle();
		args.putString("title", title);
		df.setArguments(args);
		return df;
	}
	
	@Override
	public void onCreate(Bundle myBundle) {
		super.onCreate(myBundle);
//		title = getArguments().getString("title");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.page_empty, container, false);
//			TextView tv = (TextView)view.findViewById(R.id.title);
//			tv.setText(title);
		} else{
			ViewGroup p = (ViewGroup) view.getParent();
			if (p != null) {
				p.removeAllViewsInLayout();
			}
		}
		return view;
	}
}
