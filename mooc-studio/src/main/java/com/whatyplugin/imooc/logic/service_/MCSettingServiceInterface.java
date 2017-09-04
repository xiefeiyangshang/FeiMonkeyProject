package com.whatyplugin.imooc.logic.service_;



import android.content.Context;

import com.whatyplugin.imooc.logic.model.MCCrashInfo;

public interface MCSettingServiceInterface {
    
	void checkedUpgrade(String arg1, int arg2, int arg3, int arg4, MCAnalyzeBackBlock arg5, Context arg6);

    void sendSuggest(String arg1, String arg2, MCAnalyzeBackBlock arg3, Context arg4);
    
    void sendCrashInfo(MCCrashInfo crashInfo, final MCAnalyzeBackBlock resultBack, Context context);
    	
}

