package com.whatyplugin.imooc.logic.utils;

import android.os.CountDownTimer;

public class MyCountDownTimer extends CountDownTimer {

	private onDownTimerEventListener listener;
	
	public MyCountDownTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	public void setListener(onDownTimerEventListener listener) {
		this.listener = listener;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		if(listener != null ){
			listener.onTick();			
		}
	}

	@Override
	public void onFinish() {
		if(listener != null ){
			listener.onFinish();			
		}
	}

	public interface onDownTimerEventListener{
		void onTick();
		void onFinish();
	}
}
