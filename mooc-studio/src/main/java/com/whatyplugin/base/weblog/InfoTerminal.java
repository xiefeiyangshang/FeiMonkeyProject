package com.whatyplugin.base.weblog;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

public class InfoTerminal {

	private static final String TAG = "InfoTerminal";

	public static String getDeviceId(Context context) {
		String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		return androidId;
	}

	public static String getMacAddress(Context context) {
		try {
			String str = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
			return str;
		} catch (Exception e) {

		}
		return "Please make sure you have enabled WiFi!";
	}

	public static String getModel() {
		return android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
	}

	public static String getOS() {
		String version = android.os.Build.VERSION.RELEASE;
		return version;
	}
	
	public static String getTelecommunicasOperator(Context context){
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String operator = tm.getSimOperator();
		String tel = "其他";
		if(operator!=null){
			if(operator.equals("46000") || operator.equals("46002")|| operator.equals("46007")){
//				Log.i(TAG, "中国移动");
				tel = "中国移动";
			}else if(operator.equals("46001")){
//				Log.i(TAG, "中国联通");
				tel = "中国联通";
			}else if(operator.equals("46003")){
//				Log.i(TAG, "中国电信");
				tel = "中国电信";
			}else{
//				Log.i(TAG, "其他");
				tel = "其他";
			}
		}
		return tel;
	}
	
	private static LocationManager lm;
	private static MyListener listener;
	
	public static void getLocatin(Context context) {
		listener = new InfoTerminal().new MyListener();
		lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		if (lm != null && lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				WhatyLog.latitude = location.getLatitude();
				WhatyLog.longitude = location.getLongitude();
				Log.d(TAG,"历史位置信息:"+WhatyLog.latitude +","+ WhatyLog.longitude);
			}else {
				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
			}
		}else{
			Log.e(TAG, "NO,GPS");
		}
	}
	
	private void removeLocation() {
		if(listener != null && lm != null){
			lm.removeUpdates(listener);
			listener = null;
		}
	}
	
	private class MyListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			WhatyLog.latitude = location.getLatitude();
			WhatyLog.longitude = location.getLongitude();
			Log.d(TAG,"请求位置信息:" + WhatyLog.latitude + "," + WhatyLog.longitude);
			removeLocation();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

	}
	
	public static BeanTerminal getTerminal(Context context) {
		BeanTerminal terminal = new BeanTerminal();
		terminal.setDeviceID(getDeviceId(context));
		terminal.setDeviceMac(getMacAddress(context));
		terminal.setDeviceName(getModel());
		terminal.setOs(getOS());
		terminal.setTelecommunicasOperator(getTelecommunicasOperator(context));
		return terminal;
	}

}
