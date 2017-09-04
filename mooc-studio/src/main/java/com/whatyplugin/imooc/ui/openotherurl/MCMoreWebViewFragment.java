package com.whatyplugin.imooc.ui.openotherurl;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import cn.com.whatyplugin.mooc.R;

public class MCMoreWebViewFragment extends DialogFragment {

	private View view;
	private Button bt_Cancel;
	private LinearLayout ll_Open_System_WebView;
	private LinearLayout ll_CopyUrl;
	private LinearLayout ll_Reload;
	private OnClickListener copyOnListener;
	private OnClickListener openOtherUrlListener;
	private OnClickListener ReloadListener;

	private MCMoreWebViewFragment() {
	}

	public MCMoreWebViewFragment(OnClickListener openOtherUrlListener, OnClickListener copyOnListener, OnClickListener ReloadListener) {
		this.copyOnListener = copyOnListener;
		this.openOtherUrlListener = openOtherUrlListener;
		this.ReloadListener = ReloadListener;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (getDialog() == null) {
			return;
		}
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
		getDialog().getWindow().setBackgroundDrawableResource(R.color.white);
		getDialog().getWindow().setWindowAnimations(R.style.DialogOpenOtherUrl);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().getWindow().setBackgroundDrawableResource(R.color.none);
		getDialog().getWindow().setGravity(Gravity.BOTTOM);
		view = inflater.inflate(R.layout.dialog_openotherurlfragment, container);
		initDate();
		initEvten();
		return view;
	}

	private void initEvten() {
		bt_Cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		if (ReloadListener != null && copyOnListener != null && openOtherUrlListener != null) {
			ll_Open_System_WebView.setOnClickListener(openOtherUrlListener);
			ll_CopyUrl.setOnClickListener(copyOnListener);
			ll_Reload.setOnClickListener(ReloadListener);
		}

	}

	private void initDate() {
		bt_Cancel = (Button) getView(R.id.other_url_cancel);
		ll_Open_System_WebView = (LinearLayout) getView(R.id.other_url_webview);
		ll_CopyUrl = (LinearLayout) getView(R.id.other_url_copy);
		ll_Reload = (LinearLayout) getView(R.id.other_url_reload);

	}

	private View getView(int viewId) {
		return view.findViewById(viewId);
	}

}
