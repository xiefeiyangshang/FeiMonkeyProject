package com.whaty.imooc.logic.service_;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.whaty.imooc.logic.model.WorkShopModel;
import com.whaty.imooc.utile.GPStringUtile;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCBaseService;

/**
 * 返回数据的解析处理
 * 
 * @author 马彦君
 * 
 */
public class MCCommonBaseService extends MCBaseService {

	/**
	 * 网络返回数据解析特殊形式的自定义处理方法，具体实现参考MCBaseService 的 analyzeDataWithResultDemo 方法。
	 * 
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */
	public void analyzeDataWithResultDemo(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {

	}

	/**
	 * 国培自定义SQL通用解析
	 * 
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */

	public void GPanalyzeData(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
		GPStringUtile.KAO_HE_SHULIANG = 1;
		JSONObject jsonData = null;
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;
		try {
			if (result == null || result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || TextUtils.isEmpty(responseData)) {
				statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}
			JSONArray jsonArray = null;
			if (responseData != null) {
				JSONObject jsonObj = new JSONObject(responseData);

				jsonData = jsonObj.getJSONObject("page");

				if (jsonData.getJSONArray("items").length() < 1) {
					statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
					analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
					return;
				}

				jsonArray = jsonData.getJSONArray("items").getJSONObject(0).getJSONArray("info");
			}
			if (responseData == null) {
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}

			if (jsonData != null && modelClass != null) {
				model = (MCDataModel) modelClass.newInstance();
				for (int i = 0; i < jsonArray.length(); i++) {
					MCDataModel resultModel = model.modelWithData(jsonArray.getJSONObject(i));
					if (resultModel != null) {
						retList.add(resultModel);
					}
				}
			}

		} catch (Exception e) {
			statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null);
			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
			e.printStackTrace();
		}
		statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);
		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		return;

	}

	/**
	 * 新课程空间自定义SQL通用解析
	 * 
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */

	public void GPanalyzeMoocData(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {

		JSONObject jsonData = null;
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;
		try {
			if (result == null || result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || TextUtils.isEmpty(responseData)) {
				statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}
			JSONArray jsonArray = null;
			if (responseData != null) {
				jsonData = new JSONObject(responseData);
				jsonArray = jsonData.getJSONArray("data");
			}
			if (responseData == null) {
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}

			if (jsonData != null && modelClass != null) {
				model = (MCDataModel) modelClass.newInstance();
				for (int i = 0; i < jsonArray.length(); i++) {
					MCDataModel resultModel = model.modelWithData(jsonArray.getJSONObject(i));
					if (resultModel != null) {
						retList.add(resultModel);
					}
				}
			}
		} catch (Exception e) {
			statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null);
			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
			e.printStackTrace();
		}
		statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);
		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		return;

	}

	/**
	 * 国培返回整个info中的jsonarray
	 * 
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */

	public void GPanalyzeInfoData(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {

		JSONObject jsonData = null;
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;
		try {
			if (result == null || result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || TextUtils.isEmpty(responseData)) {
				statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}
			JSONArray jsonArray = null;
			if (responseData != null) {
				JSONObject jsonObj = new JSONObject(responseData);

				jsonData = jsonObj.getJSONObject("page");
				jsonObj = jsonData.getJSONArray("items").getJSONObject(0);

				jsonArray = jsonObj.getJSONArray("info");
			}
			if (responseData == null) {
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
				return;
			}

			if (jsonData != null && modelClass != null) {
				model = (MCDataModel) modelClass.newInstance();
				MCDataModel resultModel = model.modelWithData(jsonArray);
				if (resultModel != null) {
					retList.add(resultModel);
				}

			}
		} catch (Exception e) {
			statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null);
			e.printStackTrace();
		}
		statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);
		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		return;

	}

	/**
	 * 解析工作坊活动列表 解析器
	 * 
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */

	public void GPanalyzeWorkShopData(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {

		JSONObject jsonData = null;
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;
		try {
			if (result == null || result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || TextUtils.isEmpty(responseData)) {
				statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}
			JSONArray jsonArray = null;
			if (responseData != null) {
				JSONObject jsonObj = new JSONObject(responseData);

				jsonData = jsonObj.getJSONObject("page");
				jsonArray = jsonData.getJSONArray("items").getJSONObject(0).getJSONArray("info");
			}
			if (responseData == null) {
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
				return;
			}

			if (jsonData != null && modelClass != null) {
				model = (MCDataModel) modelClass.newInstance();
				for (int i = 0; i < jsonArray.length(); i++) {
					WorkShopModel resultModel = (WorkShopModel) model.modelWithData(jsonArray.getJSONObject(i));
					if (resultModel == null)
						continue;
					// 设置上一个工作坊的活动数量
					if (resultModel.getIsWorkShop() != null && resultModel.getIsWorkShop()) {
						WorkShopModel model2 = ((WorkShopModel) retList.get(retList.size() - resultModel.getWorkNum()));
						model2.setWorkNum(resultModel.getWorkNum());
						model2.setIsWorkShop(true);
					}

					if (resultModel != null) {
						retList.add(resultModel);
					}
				}
			}
			if (retList.size() < 1) {
				statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, null);
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}

			// 设置最后一个工作坊的数量
			WorkShopModel model2 = ((WorkShopModel) retList.get(retList.size() - GPStringUtile.WORKSHOPNUM));
			model2.setWorkNum(GPStringUtile.WORKSHOPNUM);
			model2.setIsWorkShop(true);

			// 使用完之后 各种数据初始化到0或者NULL 以方便刷新的时候跟刚刚进去的时候一样

			GPStringUtile.WORKSHOP = null;
			GPStringUtile.WORKSHOPNUM = 0;
		} catch (Exception e) {
			statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null);
			e.printStackTrace();
		}
		statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);
		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		return;

	}

	/**
	 * 解析评论列表
	 * 
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */

	public void GPanalyzeBlogListData(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
		JSONObject jsonData = null;
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;
		try {
			if (result == null || result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || TextUtils.isEmpty(responseData)) {
				statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}
			JSONArray jsonArray = null;
			if (responseData != null) {
				JSONObject jsonObj = new JSONObject(responseData);

				jsonData = jsonObj.getJSONObject("page");
				jsonArray = jsonData.optJSONArray("items");
			}
			if (jsonArray == null || jsonArray.equals("")) {
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}

			if (jsonData != null && modelClass != null) {
				model = (MCDataModel) modelClass.newInstance();
				for (int i = 0; i < jsonArray.length(); i++) {
					MCDataModel resultModel = model.modelWithData(jsonArray.getJSONObject(i));
					if (resultModel != null) {
						retList.add(resultModel);
					}
				}
			}
		} catch (Exception e) {
			statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null);
			e.printStackTrace();
		}
		statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);
		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		return;

	}

	/**
	 * 返回 成功失败的解析器
	 * 
	 */

	public void GPUpdateOrDelete(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {

		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;
		JSONObject jsonObj = null;
		try {
			if (result == null || result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || TextUtils.isEmpty(responseData)) {
				statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}

			if (responseData != null) {
				jsonObj = new JSONObject(responseData);
			}

			model = (MCDataModel) modelClass.newInstance();
			MCDataModel resultModel = model.modelWithData(jsonObj);
			if (resultModel != null) {
				retList.add(resultModel);

			}
		} catch (Exception e) {
			statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null);
			e.printStackTrace();
		}

		statusResult.setResultCode(jsonObj.optBoolean("success") ? MCResultCode.MC_RESULT_CODE_SUCCESS : MCResultCode.MC_RESULT_CODE_FAILURE);
		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		return;

	}

}
