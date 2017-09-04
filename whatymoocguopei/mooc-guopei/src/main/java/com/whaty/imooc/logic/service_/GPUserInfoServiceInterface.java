package com.whaty.imooc.logic.service_;

import java.util.Map;

import android.content.Context;

import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;

public interface GPUserInfoServiceInterface {
	String getUserInfo(String arg1, int arg2, Map<String, Object> arg3, long arg4, MCAnalyzeBackBlock arg5, Context arg6);

	String SettingUserInfo(Map<String, Object> parameter, MCAnalyzeBackBlock arg5, Context arg6);

	void getUserInfo(MCAnalyzeBackBlock arg5, Context arg6);

	public void settingAvatar(Map parameter, final MCAnalyzeBackBlock back, Context context);
}
