package com.whatyplugin.imooc.logic.proxy;

import java.util.List;

import android.content.Context;

import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;

public class MCLoginProxy {
	private static MCLoginProxy instance;
	private Context mContext;
	private MCLoginInterface mcLoginInterface;
	private MCAnalyzeBackBlock mLoginBlockLister;

	static {
		MCLoginProxy.instance = new MCLoginProxy();
	}

	public void login(Context context, MCAnalyzeBackBlock listener) {
		this.mContext = context;
		mLoginBlockLister = listener;
		if (MCSaveData.getUserInfo(Contants.UID, context).toString() == "") {
			if (instance.mcLoginInterface != null) {
				instance.mcLoginInterface.loginInListener(this.mContext);
			}
		} else {
			if (instance.mcLoginInterface != null) {
				instance.mcLoginInterface.jumpToMainListener(this.mContext);
			}
		}
	}

	public static MCLoginProxy loginInstance() {
		return MCLoginProxy.instance;
	}

	public void onAnalyzeBackBlock(MCServiceResult result, List resultList) {
		if (this.mLoginBlockLister != null) {
			try {
				this.mLoginBlockLister.OnAnalyzeBackBlock(result, resultList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setLoginInterface(MCLoginInterface handler) {
		this.mcLoginInterface = handler;
	}

	public interface MCLoginInterface {
		public void loginInListener(Context context);

		public void jumpToMainListener(Context context);
	}
}
