package com.whatyplugin.imooc.logic.config;


import android.content.Context;

import com.whatyplugin.base.network.MCNetwokConfigInteface;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.save.MCSaveData;

public class MCNetworkConfig implements MCNetwokConfigInteface {
    public static final String BASE_URL = "";

    public MCNetworkConfig() {
        super();
    }

    public int getHttpConnectTimeOut() {
        return 20000;
    }

    public String getHttpServiceBaseUrl() {
        return "";
    }

    public String getToken(Context context, String method) {
        return  MCSaveData.getUserInfo(Contants.UID, context).toString();
    }
    
    public String getLoginType(Context context) {
        return  MCSaveData.getUserInfo(Contants.LOGINTYPE, context).toString();
    }
}

