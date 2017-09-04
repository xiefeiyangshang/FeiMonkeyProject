package com.whatyplugin.imooc.logic.service_;


import android.content.Context;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.base.runstat.MCRunStart.MCAPPType;
import com.whatyplugin.base.runstat.MCRunStart.MCPlatType;
import com.whatyplugin.base.runstat.MCRunStart.MCServiceType;
import com.whatyplugin.base.storage.MCUserDefaults;
import com.whatyplugin.imooc.logic.model.MCFullUserModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.Md5Util;
import com.whatyplugin.imooc.logic.utils.RequestUrl;

import java.util.HashMap;
import java.util.Map;

public class MCUserService extends MCBaseService implements MCUserServiceInterface {
    public MCUserService() {
        super();
    }

    /**
     * 退出清理session 该函数没有回调。
     * @param context
     */
    public void loginOut(Context context) {
        MCBaseRequest request = new MCBaseRequest();
        request.requestUrl = RequestUrl.getInstance().GET_LOGINCLOSE_URL;
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginErrMessage", "clear");//clear代表清理
        request.requestParams = this.PreprocessParams(map, context);
        MCNetwork.post(request, context);

        map.clear();
        request.requestUrl = RequestUrl.getInstance().GET_LOGINQUIT_URL;
        request.requestParams = this.PreprocessParams(map, context);
        MCNetwork.post(request, context);
    }


    public void loginByWhaty(String email, String password, MCServiceType service, String channelId, MCPlatType plat, MCAPPType app, String screensize, final MCAnalyzeBackBlock resultBack, Context context) {
        MCBaseRequest baseRequest = new MCBaseRequest();
        baseRequest.requestUrl = RequestUrl.getInstance().LOGIN_URL_MORESITES;
        Map<String, String> values = new HashMap<String, String>();
        values.put("username", email);
        values.put("passwd", password);
        String tmp = getAlias(email);
        values.put("alias", tmp);
        values.put("appType", "1");
        values.put("sign", "0");

        baseRequest.requestParams = values;
        baseRequest.requestParams = this.PreprocessParams(values, context);
        baseRequest.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                MCUserService.this.analyzeDataWithResult(result, responeData, MCFullUserModel.class, resultBack);
            }
        };
        MCNetwork.post(baseRequest, context);
    }

    private String getAlias(String usr) {
        String tmp = usr.replace("@", "_");
    tmp = tmp.replace(".", "_");
    tmp = tmp.replace("-", "_");
    return tmp;
}

    public void logout(int uid, MCAnalyzeBackBlock resultBack, Context context) {
        MCSaveData.clearUser(context);
        String v2 = MCUserDefaults.getUserDefaults(context, "user_info").getString("platform_name");
        MCLog.i(TAG, "platform_name:" + v2);
    }

    public void userRegister(String email, String password, String nickname, MCServiceType service, String channelId, MCPlatType plat, MCAPPType app, String screensize, final MCAnalyzeBackBlock resultBack, Context context) {
        MCBaseRequest v5 = new MCBaseRequest();
        v5.requestUrl = RequestUrl.getInstance().REGIST_URL;
        Map<String, String> v4 = new HashMap<String, String>();
        v4.put("email", email);
        v4.put("password", Md5Util.MD5(password));
        v4.put("nickName", nickname);
        v5.requestParams = v4;
        v5.requestParams = this.PreprocessParams(v4, context);
        v5.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                MCUserService.this.analyzeDataWithResult(result, responeData, null, resultBack);
            }
        };
        MCNetwork.post(v5, context);
    }

	@Override
	public void getFullUserInfoByUid(String uid,
			MCAnalyzeBackBlock mcAnalyzeBackBlock, Context mContext) {
		// TODO Auto-generated method stub

	}

}

