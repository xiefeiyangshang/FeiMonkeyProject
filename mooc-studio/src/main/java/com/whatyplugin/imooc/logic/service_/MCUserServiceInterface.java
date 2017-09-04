package com.whatyplugin.imooc.logic.service_;

import android.content.Context;

import com.whatyplugin.base.runstat.MCRunStart.MCAPPType;
import com.whatyplugin.base.runstat.MCRunStart.MCPlatType;
import com.whatyplugin.base.runstat.MCRunStart.MCServiceType;



public interface MCUserServiceInterface {
	
    public void loginOut(Context context) ;
   
    public void loginByWhaty(String email, String password, MCServiceType service, String channelId, MCPlatType plat, MCAPPType app, String screensize, final MCAnalyzeBackBlock resultBack, Context context) ;
  
    public void logout(int uid, MCAnalyzeBackBlock resultBack, Context context) ;
    
    public void userRegister(String email, String password, String nickname, MCServiceType service, String channelId, MCPlatType plat, MCAPPType app, String screensize, final MCAnalyzeBackBlock resultBack, Context context) ;

	public void getFullUserInfoByUid(String uid, MCAnalyzeBackBlock mcAnalyzeBackBlock, Context mContext);

}

