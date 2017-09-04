package com.whatyplugin.imooc.logic.service_;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.whatyplugin.base.error.MCBaseErrorCode;
import com.whatyplugin.base.error.MCError;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.imooc.logic.config.MCNetworkConfig;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCChapterModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCTestAdditionalData;
import com.whatyplugin.imooc.logic.model.MCTestQuesModel;
import com.whatyplugin.imooc.logic.model.MCTestResultModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.NumUtil;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;
import com.whatyplugin.uikit.toast.MCToast;

public class MCBaseService {
	public final String TAG = "MCBaseService";
	private  Map<String,String> MapString;

	public MCBaseService() {
		super();
	}

	public Map<String,String> getMapString(){
		if(MapString ==null){
			MapString = new HashMap<String,String>();
		}else{
			MapString.clear();
		}

		return MapString;
	}


	public Map<String, String> PreprocessParams(Map arg5, Context context) {
		int uid = 0;
		try {
			uid = Integer.parseInt(MCSaveData.getUserInfo(Contants.UID, context).toString());
		} catch (Exception v1) {
		}

		if (!arg5.keySet().contains("uid")) {
			arg5.put("uid", new StringBuilder(String.valueOf(uid)).toString());
		}

		if (MCNetwork.getMCNetworkConfig() == null) {
			MCNetwork.setMCNetworkConfig(new MCNetworkConfig());
		}

		return arg5;
	}

	public void analyzeDataWithResult(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
		JSONArray jsonArray = null;
		String jsonData = null;
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;
		if (result == null) {
			MCLog.e(TAG, "解析数据参数MCCommonResult为空，不正常");
			return;
		}
		// 如果不是成功，直接返回了
		if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS) {
			statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
			try {
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
			} catch (Exception v4) {
				v4.printStackTrace();
				// TODO
			}
			return;
		}

		try {
			// 解析数据
			statusResult = MCServiceResult.resultWithData(responseData);
			if (statusResult.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || TextUtils.isEmpty(responseData)) {
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}
		} catch (Exception v4) {
			// 解析异常
			v4.printStackTrace();
			MCToast.show(MoocApplication.getInstance(), "操作失败！");
			return;
		}

		try {
			JSONObject jsonObj = new JSONObject(responseData);
			if (jsonObj != null && jsonObj.has("data")) {
				jsonData = jsonObj.getString("data");
			} else if (jsonObj != null && jsonObj.has("page")) {
				jsonData = jsonObj.getString("page");
				jsonData = new JSONObject(jsonData).getString("items");
			} else {
				throw new RuntimeException();
			}
			if (modelClass != null) {
				model = (MCDataModel) modelClass.newInstance();
				if (jsonData != null && jsonData.length() > 0 && !jsonData.isEmpty()) {
					jsonArray = new JSONArray(jsonData);
					for (int i = 0; i < jsonArray.length(); ++i) {
						MCDataModel resultModel = model.modelWithData(jsonArray.get(i));
						if (resultModel != null) {
							retList.add(resultModel);
						}
					}
				}
			}

			if (retList.size() == 0) {
				statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_EMPTY, null);
			}
		} catch (Exception e) {
			// 是单个实体的用这种形式转换
			try {
				if (TextUtils.isEmpty(jsonData))
					jsonData = responseData;
				if (modelClass != null) {
					model = (MCDataModel) modelClass.newInstance();
					MCDataModel resultModel = model.modelWithData(jsonData);
					if (resultModel != null) {
						retList.add(resultModel);
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null);
			}
		}

		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		return;

	}

	/**
	 * 个人资料的解析
	 *
	 */

	public void analyzeUserInfoData(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
		JSONArray v8 = null;
		String jsonData = null;
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;
		if (result == null || result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || TextUtils.isEmpty(responseData)) {
			statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
			try {
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
			} catch (Exception v4) {
				v4.printStackTrace();
			}
			return;
		}

		try {

			if (responseData != null) {
				JSONObject jsonObj = new JSONObject(responseData);
				jsonData = jsonObj.getString("data");
			}

			if (TextUtils.isEmpty(jsonData) && modelClass != null) {
				model = (MCDataModel) modelClass.newInstance();
				MCDataModel resultModel = model.modelWithData(jsonData);
				if (resultModel != null) {
					retList.add(resultModel);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE, null);
		}

		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		return;

	}

	/**
	 * 下载资源的解析
	 *
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */
	public void analyzeDownloadResResult(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;
		if (result == null) {
			MCLog.e(TAG, "解析数据参数MCCommonResult为空，不正常");
			return;
		}
		// 如果不是成功，直接返回了
		if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS) {
			statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
			try {
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
			} catch (Exception v4) {
				v4.printStackTrace();
			}
			return;
		}

		try {
			// 解析数据
			statusResult = MCServiceResult.resultWithData(responseData);
			if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || responseData == null || responseData.isEmpty()) {
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}
		} catch (Exception v4) {
			// 解析异常
			v4.printStackTrace();
			statusResult = (MCServiceResult) MCServiceResult.resultWithError(MCError.errorWithCode(MCBaseErrorCode.ERROR_ANALYZE_DATA.value()));
			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
			return;
		}

		try {
			JSONObject obj = new JSONObject(responseData);
			model = (MCDataModel) modelClass.newInstance();
			if (obj != null && !obj.isNull("data")) {
				MCDataModel resultModel = model.modelWithData(obj.getString("data"));
				if (resultModel != null) {
					retList.add(resultModel);
				}
			}
		} catch (Exception e) {

		}
		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		return;

	}

	/**
	 * 查询课程类型就是这种解析方式
	 *
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */
	public void analyzeDataWithResult(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackMapBlock analyzeResultBack) {
		String v16 = null;
		JSONArray resultArray;
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		String categories = null;
		JSONObject v6;
		MCDataModel model = null;
		MCServiceResult serviceResult;
		new MCServiceResult();
		Map<String, Object> retMap = new HashMap<String, Object>();
		if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
			try {
				serviceResult = MCServiceResult.resultWithData(responseData);
				if (serviceResult.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || responseData == null || responseData.isEmpty()) {
					analyzeResultBack.OnAnalyzeBackBlock(serviceResult, retMap);
					return;
				}

				String v4 = new JSONObject(responseData).getString("data");
				if (v4 == null || v4.isEmpty()) {
					analyzeResultBack.OnAnalyzeBackBlock(serviceResult, retMap);
					return;
				}

				model = (MCDataModel) modelClass.newInstance();
				v6 = new JSONObject(v4);
				if (v6.has("categories")) {
					categories = v6.getString("categories");
					resultArray = new JSONArray(categories);
					for (int i = 0; i < resultArray.length(); ++i) {
						MCDataModel tempModel = model.modelWithData(resultArray.get(i));
						retList.add(tempModel);
					}
					retMap.put("categories", retList);
				}
			} catch (Exception v7) {
				try {
					v7.printStackTrace();
					retList.add(model.modelWithData(v16));
					retMap.put("orders", retList);
				} catch (Exception v9) {
					v7.printStackTrace();
					serviceResult = (MCServiceResult) MCServiceResult.resultWithError(MCError.errorWithCode(MCBaseErrorCode.ERROR_ANALYZE_DATA.value()));
				}

			}

			serviceResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());

			try {
				analyzeResultBack.OnAnalyzeBackBlock(serviceResult, retMap);
			} catch (Exception v7) {
				v7.printStackTrace();
			}
		}
	}

	public void analyzeExamWithResult(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
		String v6;
		MCDataModel model = null;
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		JSONArray v8 = null;
		MCServiceResult statusResult = new MCServiceResult();
		List v11 = Collections.EMPTY_LIST;
		String jsonData = "";
		// 如果不是成功，直接返回了
		if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS) {
			statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
			try {
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
			} catch (Exception v4) {
				v4.printStackTrace();
			}
			return;
		}

		try {
			// 解析数据
			statusResult = MCServiceResult.resultWithData(responseData);
			if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || responseData == null || responseData.isEmpty()) {
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}
		} catch (Exception v4) {
			// 解析异常
			v4.printStackTrace();
			statusResult = (MCServiceResult) MCServiceResult.resultWithError(MCError.errorWithCode(MCBaseErrorCode.ERROR_ANALYZE_DATA.value()));
			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
			return;
		}
		try {
			jsonData = new JSONObject(responseData).getString("data");

			model = (MCDataModel) modelClass.newInstance();
			if (jsonData != null && jsonData.length() > 0 && !jsonData.isEmpty()) {
				if (jsonData.contains("exam")) {
					v6 = new JSONObject(jsonData).getString("exam");
					v8 = new JSONArray(v6);
					for (int i = 0; i < v8.length(); ++i) {

						MCDataModel resultModel = model.modelWithData(v8.get(i));
						if (resultModel != null) {
							retList.add(resultModel);
						}

					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// 是单个实体的用这种形式转换
			retList.add(model.modelWithData(jsonData));
		}
		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		return;
	}

	public void analyzePicWithResult(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
	}

	/**
	 * 章节类型的解析
	 *
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */
	public void analyzeChapterWithResult(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
		JSONArray jsonArray = null;
		String jsonData = null;
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;
		if (result == null) {
			MCLog.e(TAG, "解析数据参数MCCommonResult为空，不正常");
			return;
		}
		// 如果不是成功，直接返回了
		if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS) {
			statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
			try {
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
			} catch (Exception v4) {
				v4.printStackTrace();
			}
			return;
		}

		try {
			// 解析数据
			statusResult = MCServiceResult.resultWithData(responseData);
			if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || responseData == null || responseData.isEmpty()) {
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}
		} catch (Exception v4) {
			// 解析异常
			v4.printStackTrace();
			statusResult = (MCServiceResult) MCServiceResult.resultWithError(MCError.errorWithCode(MCBaseErrorCode.ERROR_ANALYZE_DATA.value()));
			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
			return;
		}

		try {
			model = (MCDataModel) modelClass.newInstance();
			jsonData = new JSONObject(responseData).getString("data");

			if (jsonData != null && jsonData.length() > 0 && !jsonData.isEmpty()) {

				jsonArray = new JSONArray(jsonData);

				for (int i = 0; i < jsonArray.length(); ++i) {
					JSONArray arraySections = null;
					JSONObject innerJsonData = new JSONObject(jsonArray.get(i).toString());

					String chapterTitle = NumUtil.createChapterName(i + 1) + Contants.SPACENOP + innerJsonData.getString("chapterTitle");
					MCChapterModel resultModelParent = new MCChapterModel();
					resultModelParent.setName(chapterTitle);
					resultModelParent.setLaunch(innerJsonData.optString("launch"));
					resultModelParent.setZhangId(innerJsonData.optString("id"));

					resultModelParent.setFirst(true);// true表示是章
					List<MCSectionModel> list = new ArrayList<MCSectionModel>();
					resultModelParent.setSections(list);
					retList.add(resultModelParent);

					String str = innerJsonData.optString("extendInfo");
					str = str.replaceAll("\\\\","");
					if(!TextUtils.isEmpty(str)){
						arraySections = new JSONArray(str);
					}else {
						arraySections = innerJsonData.getJSONArray("sections");

					}

					for (int j = 0; j < arraySections.length(); j++) {

						MCChapterModel resultModel = (MCChapterModel) model.modelWithData(arraySections.get(j));
						if (resultModel != null) {
							resultModel.setSeq(j + 1);
							resultModel.setName(NumUtil.createSectionName(j + 1) + resultModel.getName());
							retList.add(resultModel);
						}
					}

				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
			// 是单个实体的用这种形式转换
			retList.add(model.modelWithData(jsonData));
		}
		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		return;
	}

	/**
	 * 笔记类型的解析
	 *
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */
	public void analyzeNoteWithResult(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
		JSONArray jsonArray = null;
		String jsonData = null;
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;
		if (result == null) {
			MCLog.e(TAG, "解析数据参数MCCommonResult为空，不正常");
			return;
		}
		// 如果不是成功，直接返回了
		if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS) {
			statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
			try {
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
			} catch (Exception v4) {
				v4.printStackTrace();
			}
			return;
		}

		try {
			// 解析数据
			statusResult = MCServiceResult.resultWithData(responseData);
			if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS || responseData == null || responseData.isEmpty()) {
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}
		} catch (Exception v4) {
			// 解析异常
			v4.printStackTrace();
			statusResult = (MCServiceResult) MCServiceResult.resultWithError(MCError.errorWithCode(MCBaseErrorCode.ERROR_ANALYZE_DATA.value()));
			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
			return;
		}

		try {
			model = (MCDataModel) modelClass.newInstance();
			JSONObject obj = new JSONObject(responseData);
			jsonData = obj.getJSONObject("page").getString("items");
			// jsonData = new JSONObject(responseData).getString("data");
			if (jsonData != null && jsonData.length() > 0 && !jsonData.isEmpty()) {
				jsonArray = new JSONArray(jsonData);

				for (int i = 0; i < jsonArray.length(); ++i) {
					JSONObject innerJsonData = jsonArray.getJSONObject(i);
					model = (MCDataModel) modelClass.newInstance();
					MCDataModel resultModel = model.modelWithData(innerJsonData);
					if (resultModel != null) {
						retList.add(resultModel);
					}
				}
			}

		} catch (Exception e) {
			// e.printStackTrace();
			// 是单个实体的用这种形式转换
			retList.add(model.modelWithData(jsonData));
		}
		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		return;
	}

	/**
	 * 自测完成后解析
	 *
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */
	public void analyzeTestQuestionResult(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {

		MCServiceResult statusResult = new MCServiceResult();
		List<MCDataModel> retList = new ArrayList<MCDataModel>();

		// 不成功，直接返回了
		if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS) {
			statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		} else {
			MCTestResultModel model;
			MCTestAdditionalData data = new MCTestAdditionalData();
			try {
				JSONObject obj = new JSONObject(responseData);
				data.setCurrentScore(obj.optString("currentScore"));
				data.setMaxScore(obj.optString("maxScore"));
				data.setTitle(obj.optString("title"));
				statusResult.setAddtionalData(data);

				JSONArray detailsArr = obj.getJSONArray("details");
				if (detailsArr != null) {
					for (int i = 0; i < detailsArr.length(); i++) {
						JSONObject detail = (JSONObject) detailsArr.get(i);
						JSONArray questions = detail.getJSONArray("questions");
						for (int j = 0; j < questions.length(); j++) {
							JSONObject ques = (JSONObject) questions.get(j);
							// MCTestResultModel.
							model = (MCTestResultModel) modelClass.newInstance();
							retList.add(model.modelWithData(ques));
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		}
	}

	/**
	 * 获取自测列表
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */
	public void analyzeTestListResult(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;
		try {
			JSONObject jObj = new JSONObject(responseData);
			JSONObject jsonObject = jObj.getJSONObject("page");
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			model = (MCDataModel) modelClass.newInstance();
			for (int i = 0; i < jsonArray.length(); ++i) {
				MCDataModel resultModel = model.modelWithData(jsonArray.get(i));
				if (resultModel != null) {
					retList.add(resultModel);
				}
			}
			statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
		}
		analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
	}

	/**
	 * 自测完成后解析
	 *
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */
	public void analyzeTestResult(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
		MCServiceResult statusResult = new MCServiceResult();
		List<MCDataModel> retList = new ArrayList<MCDataModel>();

		// 不成功，直接返回了
		if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS) {
			statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		} else {
			MCTestResultModel model;
			MCTestAdditionalData data = new MCTestAdditionalData();
			try {
				JSONObject obj = new JSONObject(responseData);
				data.setCurrentScore(obj.optString("currentScore"));
				data.setMaxScore(obj.optString("maxScore"));
				data.setTitle(obj.optString("title"));
				statusResult.setAddtionalData(data);

				JSONArray detailsArr = obj.getJSONArray("details");
				if (detailsArr != null) {
					for (int i = 0; i < detailsArr.length(); i++) {
						JSONObject detail = (JSONObject) detailsArr.get(i);
						JSONArray questions = detail.getJSONArray("questions");
						for (int j = 0; j < questions.length(); j++) {
							JSONObject ques = (JSONObject) questions.get(j);
							// MCTestResultModel.
							model = (MCTestResultModel) modelClass.newInstance();
							retList.add(model.modelWithData(ques));
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		}
	}

	/**
	 * 当前用户不可做,获取自测详情查看答案
	 *
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */
	public void analyzeTestDetailsResult(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {

		MCServiceResult statusResult = new MCServiceResult();
		List<MCDataModel> retList = new ArrayList<MCDataModel>();

		// 不成功，直接返回了
		if (result.getResultCode() != MCResultCode.MC_RESULT_CODE_SUCCESS) {
			statusResult = MCServiceResult.resultWithData(result.getResultCode(), result.getResultDesc());
			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		} else {
			MCTestQuesModel model;
			try {
				JSONObject obj = new JSONObject(responseData);
				JSONObject jsonObject = obj.getJSONObject("data");
				if (jsonObject == null) {
				} else {
					JSONArray questionsArr = jsonObject.getJSONArray("questions");
					if (questionsArr != null) {
						for (int i = 0; i < questionsArr.length(); i++) {
							JSONObject question = (JSONObject) questionsArr.get(i);
							model = (MCTestQuesModel) modelClass.newInstance();
							retList.add(model.modelWithData(question, true));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);//
			}
			statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);
			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		}
	}

	/**
	 * 获取自测列表
	 * @param result
	 * @param responseData
	 * @param modelClass
	 * @param analyzeResultBack
	 */
	public void analyzeNoticeListResult(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;

		try {
			if (responseData == null) {
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
				return;
			}
			JSONObject jObj = new JSONObject(responseData);
			JSONObject jsonObject = jObj.getJSONObject("page");
			if(jsonObject.optString("items").equals("null")){
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}

			JSONArray items = jsonObject.getJSONArray("items");
			if(items.length()>0){
				model = (MCDataModel) modelClass.newInstance();
				for (int i = 0; i < items.length(); ++i) {
					MCDataModel resultModel = model.modelWithData(items.get(i));
					if (resultModel != null) {
						retList.add(resultModel);
					}
				}
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);
			}else{
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
			}

			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		} catch (Exception e1) {
			e1.printStackTrace();
			statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
		}
	}

	public void analyzeLiveOnLine(MCCommonResult result, String responseData, Class modelClass, MCAnalyzeBackBlock analyzeResultBack) {
		List<MCDataModel> retList = new ArrayList<MCDataModel>();
		MCServiceResult statusResult = new MCServiceResult();
		MCDataModel model = null;

		try {
			if (responseData == null) {
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
				analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
				return;
			}
			model = (MCDataModel) modelClass.newInstance();
			MCDataModel resultModel = model.modelWithData(responseData);
			retList.add(resultModel);
			statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);

			analyzeResultBack.OnAnalyzeBackBlock(statusResult, retList);
		} catch (Exception e1) {
			e1.printStackTrace();
			statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_EMPTY);
		}
	}

}
