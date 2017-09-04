package com.whatyplugin.imooc.logic.manager;


import android.content.Context;

import com.whatyplugin.base.runstat.MCRunStart;
import com.whatyplugin.base.runstat.MCRunStart.MCAPPType;
import com.whatyplugin.base.runstat.MCRunStart.MCPlatType;
import com.whatyplugin.base.runstat.MCRunStart.MCRunType;
import com.whatyplugin.base.runstat.MCRunStart.MCServiceType;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.uikit.resolution.MCResolution;

public class MCRunStartManager {
	
	private static final String TAG = MCRunStartManager.class.getSimpleName();
	
    public MCRunStartManager() {
        super();
    }

    public static String getChannel(Context context) {
       
        return "00-0";
    }

    public static void installapp(Context context) {
    	int number = 0;
        try {
            number = Integer.parseInt(MCSaveData.getUserInfo(Contants.UID, context).toString());
        }
        catch(Exception e) {
        }

        MCRunStart.installrun(MCServiceType.MC_SERVICE_TYPE_APP, MCRunStartManager.getChannel(context), MCPlatType.MC_PLAT_TYPE_ANDROID, MCAPPType.MC_APP_TYPE_IMOOC, number, MCRunType.MC_Run_TYPE_RUN, MCResolution.getInstance(context).getDevScreenSize(), context);
    }

    public static void runapp(Context context) {
        int number = 0;
        try {
            number = Integer.parseInt(MCSaveData.getUserInfo(Contants.UID, context).toString());
        }
        catch(Exception e) {
        }

        MCRunStart.apprun(MCServiceType.MC_SERVICE_TYPE_APP, MCRunStartManager.getChannel(context), MCPlatType.MC_PLAT_TYPE_ANDROID, MCAPPType.MC_APP_TYPE_IMOOC, number, MCRunType.MC_Run_TYPE_RUN, MCResolution.getInstance(context).getDevScreenSize(), context);
    }
}

