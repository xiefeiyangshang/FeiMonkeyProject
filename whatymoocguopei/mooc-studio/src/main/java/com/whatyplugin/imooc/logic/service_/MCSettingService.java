package com.whatyplugin.imooc.logic.service_;



import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCrashInfo;
import com.whatyplugin.imooc.logic.model.MCNoteModel;
import com.whatyplugin.imooc.logic.model.MCUpgradeModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.logic.utils.RequestUrl;

public class MCSettingService extends MCBaseService implements MCSettingServiceInterface {
    private static String TAG;

    static {
        MCSettingService.TAG = MCSettingService.class.getSimpleName();
    }

    public void checkedUpgrade(String version, int versionCode, int appid, int platid, final MCAnalyzeBackBlock resultBack, Context context) {
        MCBaseRequest v1 = new MCBaseRequest();
        v1.requestUrl = Const.DOWNURL;
        Map<String, String> map = new HashMap<String, String>();
        v1.requestParams = this.PreprocessParams(map, context);
        v1.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                MCLog.e(MCSettingService.TAG, "checkedUpgrade responeData:" + responeData);
                MCSettingService.this.analyzeDataWithResult(result, responeData, MCUpgradeModel.class, resultBack);
            }
        };
        MCNetwork.post(v1, context);
    }

    public void sendSuggest(String content, String contact, final MCAnalyzeBackBlock resultBack, Context context) {
        int v2 = 0;
        MCBaseRequest v1 = new MCBaseRequest();
        v1.requestUrl = RequestUrl.getInstance().IDEA_FEED_URL;
        Map<String, String> map = new HashMap<String, String>();
        try {
            v2 = Integer.parseInt(MCSaveData.getUserInfo(Contants.UID, context).toString());
        }
        catch(Exception v3) {
        }
        
        map.put("userID", new StringBuilder(String.valueOf(v2)).toString());
        map.put("note", content+",联系方式:"+contact);
        map.put("appInfo", "android");
        map.put("JGID", "fszx");
        v1.requestParams = this.PreprocessParams(map, context);
        v1.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                MCLog.e(MCSettingService.TAG, "sendSuggest responeData:" + responeData);
                MCSettingService.this.analyzeDataWithResult(result, responeData, MCUpgradeModel.class, resultBack);
            }
        };
        MCNetwork.post(v1, context);
    }

	@Override
	public void sendCrashInfo(MCCrashInfo crashInfo, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SEND_CRASH_INFO_URL;// 这里改成自己的笔记路径
		Map<String, String> map = new HashMap<String, String>();
		map.put("deviceType", "codeand");// 设备类别
		map.put("bean.crashInfo", crashInfo.getCrashInfo());//崩溃信息
		map.put("bean.remarks", crashInfo.getRemarks());//备注
		map.put("bean.packageName", crashInfo.getPackageName());//包名
		map.put("bean.version", crashInfo.getVersion());//版本号
		map.put("bean.exceptionType", crashInfo.getExceptionType());//异常类型
		map.put("bean.phoneType", crashInfo.getPhoneType());//手机型号

		request.requestParams = map;
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result,
					String responeData) {
				MCSettingService.this.analyzeDataWithResult(result,
						responeData, MCNoteModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}
    
}

