package com.whaty.imooc.logic.service_;

import android.content.Context;
import android.text.TextUtils;

import com.whaty.imooc.logic.model.MCMyUserModel;
import com.whaty.imooc.ui.index.GPRequestUrl;
import com.whatyplugin.base.define.MCBaseDefine;
import com.whatyplugin.base.download.MCDownloadQueue;
import com.whatyplugin.base.download.MCDownloadVideoNode;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.model.MCXmlSectionModel;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.base.runstat.MCRunStart.MCAPPType;
import com.whatyplugin.base.runstat.MCRunStart.MCPlatType;
import com.whatyplugin.base.runstat.MCRunStart.MCServiceType;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseDetailModel;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCAsyncTask;
import com.whatyplugin.imooc.logic.service_.MCAsyncTaskInterface;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.logic.utils.RequestUrl;
import com.whatyplugin.imooc.ui.download.MCDownloadHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 网络请求及耗时操作的service，用回调的方式返回结果。
 * 
 * @author 马彦君
 * 
 */
public class MCCommonService extends MCCommonBaseService implements MCCommonServiceInterface {

	/**
	 * 请求网络的方式 - DEMO 全部课程
	 */
	public void getAllCourse(int page, String keyWord, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		String uid = MCSaveData.getUserInfo(Contants.UID, context).toString();
		if (TextUtils.isEmpty(uid) || uid.equals(Contants.DEFAULT_UID)) {
			request.requestUrl = RequestUrl.getInstance().GET_FIRSTCOUSELIST_URL;
		} else {
			request.requestUrl = RequestUrl.getInstance().GET_COUSELIST_URL;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("params.type", "0");// 0是全部课程，1是我的课程
		map.put("params.host", Const.SITECODE);
		map.put("params.startIndex", Const.PAGESIZE * (page - 1) + "");
		map.put("params.pageSize", Const.PAGESIZE + "");
		map.put("params.siteCode", Const.SITECODE);
		map.put("params.courseName", keyWord);

		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCCommonService.this.analyzeDataWithResult(result, responeData, MCCourseModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	/**
	 * 耗时操作进行的 AsyncTask的方式 - DEMO 解析节点信息
	 */
	@Override
	public void initSFPDownloadInfo(final List<MCXmlSectionModel> modelList, final MCSectionModel section, final MCCourseModel course, final MCBaseDefine.MCDownloadNodeType downloadType, final MCAnalyzeBackBlock resultBack, final Context mContext) {
		// 从网络请求解析
		MCAsyncTask asyncTask = new MCAsyncTask(new MCAsyncTaskInterface() {
			@Override
			public String DoInBackground(MCAsyncTask task) {
				long totalSize = 0;//三分屏资源的总大小
				List nodeList = new ArrayList<MCDownloadVideoNode>();
				for (MCXmlSectionModel model : modelList) {
					totalSize += MCDownloadHelper.startDownLoad(mContext, model, section, course, downloadType, nodeList);
				}
				MCDownloadHelper.insertMainInfo(mContext, section, course, totalSize, downloadType, nodeList);
				MCDownloadQueue.getInstance().initQueue();
				return null;
			}

			@Override
			public void DoAfterExecute(String result) {
				MCServiceResult statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, null);
				resultBack.OnAnalyzeBackBlock(statusResult, null);
			}

			@Override
			public void onProgressUpdate(Integer values) {
			}
		});
		asyncTask.execute(1000);
	}

	//webtrn 登陆===
	
	@Override
	public void loginByWhaty(String email, String password, MCServiceType service, String channelId, MCPlatType plat, MCAPPType app,
			String screensize, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest baseRequest = new MCBaseRequest();
		baseRequest.requestUrl = RequestUrl.getInstance().LOGIN_URL;
		Map<String, String> values = new HashMap<String, String>();
		values.put("username", email);
		values.put("passwd", password);
		String tmp = getAlias(email);
		values.put("alias", tmp);
		values.put("appType", "1");
		values.put("sign", "0");
//		values.put("siteCode", "xngp");
		values.put("code", GPRequestUrl.getInstance().SITDECODE);
		baseRequest.requestParams = values;
		baseRequest.requestParams = this.PreprocessParams(values, context);
		baseRequest.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCCommonService.this.analyzeDataWithResult(result, responeData, MCMyUserModel.class, resultBack);
			}
		};
		MCNetwork.post(baseRequest, context);
	}

	/**
	 * 获取课程详情
	 */
	@Override
	public void getCourseDetailByCourseId(String uid, String courseId, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		if (TextUtils.isEmpty(uid)) {
			request.requestUrl = RequestUrl.getInstance().COURSE_FIRSTDETAIL_URL;// 首页的课程详情
		} else {
			request.requestUrl = RequestUrl.getInstance().COURSE_DETAIL_URL;
		}

		Map<String, String> map = new HashMap<String, String>();

		map.put("page.searchItem.openCourse", courseId);
		map.put("params.courseId", courseId);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCLog.d(TAG, "getCourseDetailByCourseId responeData:" + responeData);
				MCCommonService.this.analyzeDataWithResult(result, responeData, MCCourseDetailModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	private String getAlias(String usr) {
		String tmp = usr.replace("@", "_");
		tmp = tmp.replace(".", "_");
		tmp = tmp.replace("-", "_");
		return tmp;
	}

	 public void loginByWhaty(String email, String password,String siteCode, MCServiceType service, String channelId, MCPlatType plat, MCAPPType app, String screensize, final MCAnalyzeBackBlock resultBack, Context context) {
	        MCBaseRequest baseRequest = new MCBaseRequest();
	        baseRequest.requestUrl = RequestUrl.getInstance().LOGIN_URL;
	        Map<String, String> values = new HashMap<String, String>();
	        values.put("username", email);
	        values.put("passwd", password);
	        values.put("siteCode", siteCode);
	        String tmp = getAlias(email);
	        values.put("alias", tmp);
	        values.put("appType", "1");
	        values.put("sign", "0");
	        
	        baseRequest.requestParams = values;
	        baseRequest.requestParams = this.PreprocessParams(values, context);
	        baseRequest.listener = new MCNetworkBackListener() {
	            public void onNetworkBackListener(MCCommonResult result, String responeData) {
	                MCLog.d(TAG, "responeData:" + responeData);
	                MCCommonService.this.analyzeDataWithResult(result, responeData, MCMyUserModel.class, resultBack);
	            }
	        };
	        MCNetwork.post(baseRequest, context);
	    }
	    
	 
	 
}
