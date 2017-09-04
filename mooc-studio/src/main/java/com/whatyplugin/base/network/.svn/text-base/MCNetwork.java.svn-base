package com.whatyplugin.base.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.network.MCNetworkDefine.MCNetworkStatus;

public class MCNetwork {
	private static String TAG;
	private static MCNetwokConfigInteface config;

	static {
		MCNetwork.TAG = "MCNetwork";
	}

	public MCNetwork() {
		super();
	}

	public static boolean checkedNetwork(Context context) {
		boolean isNet = false;
		try {
			ConnectivityManager netManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = netManager.getActiveNetworkInfo();
			// 没有 数据流量 也没有WIFI的情况下 获取到的 netInfo是空的
			if (netInfo != null)
				isNet = netInfo.isAvailable();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isNet;
	}

	/**
	 * 判断网络是否存在
	 * @param context
	 * @return
	 */
	public static MCNetworkStatus currentNetwork(Context context) {
		MCNetworkStatus status = MCNetworkStatus.MC_NETWORK_STATUS_NONE;
		try {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			
			NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			
			NetworkInfo wwInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			
			if(wifiInfo!=null && wifiInfo.isConnectedOrConnecting()){
				status = MCNetworkStatus.MC_NETWORK_STATUS_WIFI;
			}else if(wwInfo!=null && wwInfo.isConnectedOrConnecting()){
				status = MCNetworkStatus.MC_NETWORK_STATUS_WWAN;
			}else if (manager.getActiveNetworkInfo().isAvailable()) {
				status = MCNetworkStatus.MC_NETWORK_STATUS_WWAN;
			}else{
				status = MCNetworkStatus.MC_NETWORK_STATUS_NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public static MCNetwokConfigInteface getMCNetworkConfig() {
		return MCNetwork.config;
	}

	/**
	 * post请求
	 * 
	 * @param request
	 * @param context
	 */
	public static void post(MCBaseRequest request, Context context) {
		if (MCNetwork.checkedNetwork(context)) {
			MCHttpClient.post(request, MCNetwork.config, context);
		} else {
			if(request.listener!=null){
				request.listener.onNetworkBackListener(MCCommonResult
						.resultWithData(
								MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE,
								"MC_RESULT_CODE_NETWORK_FAILURE"),
						"MC_RESULT_CODE_NETWORK_FAILURE");
			}
		}
	}

	/**
	 * get请求
	 * 
	 * @param request
	 * @param context
	 */
	public static void get(MCBaseRequest request, Context context) {
		if (MCNetwork.checkedNetwork(context)) {
			MCHttpClient.get(request, MCNetwork.config, context);
		} else {
			if(request.listener!=null){
				request.listener.onNetworkBackListener(MCCommonResult
						.resultWithData(
								MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE,
								"MC_RESULT_CODE_NETWORK_FAILURE"),
						"MC_RESULT_CODE_NETWORK_FAILURE");
			}
		}
	}

	/**
	 * 同步的get请求
	 * 
	 * @param request
	 * @param context
	 */
	public static String getWithSynchronous(MCBaseRequest request, Context context) {
		if (MCNetwork.checkedNetwork(context)) {
			try {
				return MCHttpClient.getResponseOfGetMethod(request, config, context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * 多文件上传
	 * 
	 * @param request
	 * @param context
	 */
	public static void upload(MCBaseRequest request, Context context) {
		if (MCNetwork.checkedNetwork(context)) {
			MCHttpClient.upload(request, MCNetwork.config, context);
		} else {
			request.listener.onNetworkBackListener(MCCommonResult
					.resultWithData(
							MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE,
							"MC_RESULT_CODE_NETWORK_FAILURE"),
					"MC_RESULT_CODE_NETWORK_FAILURE");
		}
	}

	public static void setMCNetworkConfig(MCNetwokConfigInteface newConfig) {
		MCNetwork.config = newConfig;
	}

	/**
	 * 单张图片的上传
	 * 
	 * @param bitmap
	 * @param context
	 * @param request
	 */
	public static void upload(Bitmap bitmap, Context context,
			MCBaseRequest request) {
		String v2 = null;
		if (MCNetwork.checkedNetwork(context)) {
			// MCHttpClient.upload(bitmap, MCNetwork.config, request);
		} else {
			request.listener.onNetworkBackListener(MCCommonResult
					.resultWithData(
							MCResultCode.MC_RESULT_CODE_NETWORK_FAILURE, v2),
					v2);
		}
	}
}
