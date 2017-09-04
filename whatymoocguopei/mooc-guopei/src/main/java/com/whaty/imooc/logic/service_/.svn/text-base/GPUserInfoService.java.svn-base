package com.whaty.imooc.logic.service_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

import com.whaty.imooc.logic.model.WebtrnUserInfoModel;
import com.whaty.imooc.logic.model.deleteUpdateModel;
import com.whaty.imooc.ui.index.GPInitInformation;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCBaseService;
import com.whatyplugin.imooc.logic.utils.RequestUrl;
import com.whatyplugin.uikit.toast.MCToast;

public class GPUserInfoService extends MCBaseService implements GPUserInfoServiceInterface {

	@Override
	public String getUserInfo(String arg1, int arg2, Map<String, Object> arg3, long arg4, final MCAnalyzeBackBlock arg5, Context context) {
		String loginType = MCSaveData.getUserInfo(Contants.LOGINTYPE, context).toString();
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_USRINFO_URL;
		if (loginType != null && !"".equals(loginType)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("loginType", loginType);
			request.requestParams = this.PreprocessParams(map, context);
			List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
			params = new LinkedList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("loginType", loginType));
			params.add(new BasicNameValuePair("gender", map.get("gender")));
			try {
				HttpPost postMethod = new HttpPost(request.requestUrl);
				postMethod.setEntity(new UrlEncodedFormEntity(params, "utf-8")); // 将参数填入POST
																					// Entity中
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse response = httpClient.execute(postMethod); // 执行POST方法
				Log.i(TAG, "resCode = " + response.getStatusLine().getStatusCode()); // 获取响应码
				return EntityUtils.toString(response.getEntity(), "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// MCToast.show(context, "请先登录！");
			return null;
		}
		return null;
	}

	@Override
	public String SettingUserInfo(Map<String, Object> parameter, final MCAnalyzeBackBlock resultBack, Context context) {
		String loginType = MCSaveData.getUserInfo(Contants.LOGINTYPE, context).toString();
		// TODO Auto-generated method stub
		MCBaseRequest request = new MCBaseRequest();
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		params = new LinkedList<BasicNameValuePair>();
		request.requestUrl = RequestUrl.getInstance().SET_USRINFO_URL;
		if (loginType != null && !"".equals(loginType)) {
			Map<String, String> map = new HashMap<String, String>();

			// map.put("loginType", loginType);
			if (parameter != null) {
				Object gender = parameter.get("gender");
				Object sign = parameter.get("sign");
				if (gender != null) {
					params.add(new BasicNameValuePair("gender", gender.toString()));
					map.put("gender", gender.toString());
				}
				if (sign != null) {
					params.add(new BasicNameValuePair("sign", sign.toString()));
					map.put("sign", sign.toString());
				}
			}
			request.requestParams = this.PreprocessParams(map, context);
			request.listener = new MCNetworkBackListener() {
				public void onNetworkBackListener(MCCommonResult result, String responeData) {
					MCServiceResult statusResult = MCServiceResult.resultWithData(result.getResultCode(), null);
					resultBack.OnAnalyzeBackBlock(statusResult, null);
				}
			};
			MCNetwork.post(request, context);
		} else {
			MCToast.show(context, "请先登录！");
			return null;
		}
		return null;
	}

	public void getUserInfo(final MCAnalyzeBackBlock arg5, Context context) {
		String loginType = MCSaveData.getUserInfo(Contants.LOGINTYPE, context).toString();
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_USRINFO_URL;
		if (loginType != null && !"".equals(loginType)) {
			Map<String, String> map = new HashMap<String, String>();
			request.requestParams = this.PreprocessParams(map, context);
			request.listener = new MCNetworkBackListener() {
				public void onNetworkBackListener(MCCommonResult result, String responeData) {
					MCLog.d(TAG, "responeData:" + responeData);
					GPUserInfoService.this.analyzeDataWithResult(result, responeData, WebtrnUserInfoModel.class, arg5);
				}
			};
			MCNetwork.post(request, context);
		} else {
			// MCToast.show(context, "请先登录！");
		}
	}

	/**
	 * 往webtrn后台上传图片
	 */
	@Override
	public void settingAvatar(final Map parameter, final MCAnalyzeBackBlock resultBack, final Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = GPInitInformation.SET_WEBTRN_USER_Avatar_New;

		List<NameValuePair> fileParams = null;
		Map<String, String> map = new HashMap<String, String>();

		fileParams = new ArrayList<NameValuePair>();
		fileParams.add(new BasicNameValuePair("entity.file.file", parameter.get("phoneUrl").toString()));

		request.fileParams = fileParams;
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				GPUserInfoService.this.analyzeDataWithResult(result, responeData, deleteUpdateModel.class, resultBack);
			}
		};
		MCNetwork.upload(request, context);
	}

}
