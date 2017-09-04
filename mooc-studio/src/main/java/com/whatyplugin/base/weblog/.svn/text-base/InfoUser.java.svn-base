package com.whatyplugin.base.weblog;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

import org.apache.http.conn.util.InetAddressUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InfoUser {
	/**
	 * ��ȡ�����IP��ַ
	 * @return
	 * @throws SocketException 
	 */
	public  static String getIpAddress(Context context){
		
		String ip = "";
		try {
			NetworkInfo localNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
			if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable())) {
		        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {  
		            NetworkInterface intf = en.nextElement();  
		            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {  
		                InetAddress inetAddress = enumIpAddr.nextElement();  
		                if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {  
		                	ip =  inetAddress.getHostAddress().toString();  
		                }  
		            }  
		        }  
			}else
				ip = "unknown";
		} catch (Exception e) {
		}
		return ip;
	}
	
	/**
	 * ��ȡ��������
	 * @param context
	 */
	public static void getLanguage(Context context){
		Locale locale = context.getResources().getConfiguration().locale;
		@SuppressWarnings("unused")
		String language = locale.getLanguage();
	}
}
