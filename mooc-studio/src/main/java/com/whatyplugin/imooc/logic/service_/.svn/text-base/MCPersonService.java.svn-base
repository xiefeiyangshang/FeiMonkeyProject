package com.whatyplugin.imooc.logic.service_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.imooc.logic.model.MCUploadModel;
import com.whatyplugin.imooc.logic.model.MCUserModel;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.logic.utils.RequestUrl;

public class MCPersonService extends MCBaseService implements MCPersonServiceInterface {
	public MCPersonService() {
		super();
	}

	public void getPersonDetail(String uid, int provid, int cityid, int areaid, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_USRINFO_URL;
		Map<String, String> map = new HashMap<String, String>();
		map.put("params.siteCode", Const.SITECODE);
		map.put("country_id", "1");
		map.put("prov_id", new StringBuilder(String.valueOf(provid)).toString());
		map.put("city_id", new StringBuilder(String.valueOf(cityid)).toString());
		map.put("area_id", new StringBuilder(String.valueOf(areaid)).toString());
		MCLog.e(TAG, " prov_id:" + provid + " city_id:" + cityid + " area_id:" + areaid);
		request.requestParams = map;
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCLog.d(TAG, "getPersonAddress responeData:" + responeData);
				MCPersonService.this.analyzeDataWithResult(result, responeData, MCUserModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void saveHandImage(String path, Context context, final MCAnalyzeBackBlock resultBack) {

		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SAVE_HAND_IMAGE;

		List<NameValuePair> fileParams = null;
		Map<String, String> map = new HashMap<String, String>();

		fileParams = new ArrayList<NameValuePair>();
		fileParams.add(new BasicNameValuePair("entity.file.file", path));

		request.fileParams = fileParams;
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				System.out.println("responeData===  "+responeData);
				MCPersonService.this.analyzeDataWithResult(result, responeData, MCUploadModel.class, resultBack);
			}
		};
		MCNetwork.upload(request, context);

	}

}
