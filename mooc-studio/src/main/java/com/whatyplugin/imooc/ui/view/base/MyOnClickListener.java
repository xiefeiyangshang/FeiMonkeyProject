package com.whatyplugin.imooc.ui.view.base;

import android.view.View;
import android.view.View.OnClickListener;

public class MyOnClickListener implements OnClickListener {

	int position;
	
	public MyOnClickListener(int position) {
		super();
		this.position = position;
	}

	@Override
	public void onClick(View v) {
	}

	public int getPosition() {
		return position;
	}

}
