package com.whatyplugin.imooc.logic.service_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;

import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCUserModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.logic.utils.RequestUrl;

public class MCUpdateUserInfoService extends MCBaseService implements MCUpdateUserInfoServiceInterface {

	String handImagePath = "";

	/**
	 * 获取个人资料
	 * 
	 */

	@Override
	public void getUserInfo(final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_USRINFO_URL;
		Map<String, String> map = new HashMap<String, String>();
		map.put("params.siteCode", Const.SITECODE);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {

			@Override
			public void onNetworkBackListener(MCCommonResult arg1, String arg2) {
				MCUpdateUserInfoService.this.analyzeDataWithResult(arg1, arg2, MCUserModel.class, resultBack);
			}
		};

		MCNetwork.post(request, context);
	}

	/**
	 * 修改个人资料
	 */
	@Override
	public void updateUserInfo(MCUserModel mcUserModel, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SET_USRINFO_URL;
		Map<String, String> mapParameter = new HashMap<String, String>();
		mapParameter.put("params.nickName", mcUserModel.getNickname());
		mapParameter.put("params.email", mcUserModel.getEmile());
		mapParameter.put("params.trueName", mcUserModel.getTrueName());
		mapParameter.put("params.gender", mcUserModel.getSex());
		mapParameter.put("params.mobile", mcUserModel.getMobile());
		mapParameter.put("params.qq", mcUserModel.getQq());
		request.requestParams = this.PreprocessParams(mapParameter, context);

		request.listener = new MCNetworkBackListener() {

			@Override
			public void onNetworkBackListener(MCCommonResult arg1, String arg2) {
				// 如果保存成功 直接返回
				if (arg1.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
					List list = new ArrayList();
					list.add("1");
					list.add("2");
					resultBack.OnAnalyzeBackBlock(null, list);
				} else {
					resultBack.OnAnalyzeBackBlock(null, null);
				}

			}
		};

		MCNetwork.post(request, context);

	}

	@Override
	public void updatePhoto(String filePath, final MCAnalyzeBackBlock resultBack, Context context) {

		if (TextUtils.isEmpty(filePath)) {
			return;
		}
		MCBaseRequest request = new MCBaseRequest();
		// request.requestUrl = RequestUrl.getInstance().UPDATE_USER_HANAD;
		request.requestUrl = request.requestUrl = RequestUrl.getInstance().SAVE_HAND;
		Map<String, String> map = new HashMap<String, String>();
		map.put("params.photo", filePath);
		map.put("params.loginid", MCSaveData.getUserInfo(Contants.USERID, context).toString());
		
		request.listener = new MCNetworkBackListener() {
			@Override
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCUpdateUserInfoService.this.analyzeDataWithResult(result, responeData, MCUserModel.class, resultBack);
			}
		};

		request.requestParams = this.PreprocessParams(map, context);
		MCNetwork.post(request, context);

	}

}
