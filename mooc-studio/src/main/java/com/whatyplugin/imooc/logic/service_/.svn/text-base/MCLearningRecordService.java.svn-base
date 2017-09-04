package com.whatyplugin.imooc.logic.service_;

import android.content.Context;

import com.whatyplugin.base.define.MCBaseDefine.MCMediaType;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.base.weblog.WhatyLog;
import com.whatyplugin.base.weblog.WhatyLogParams;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCLearnOfflineRecord;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.logic.utils.RequestUrl;
import com.whatyplugin.imooc.logic.utils.TimeFormatUtils;
import com.whatyplugin.imooc.logic.whatydb.dao.base.OfflineDao;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCLearningRecordService extends MCBaseService implements MCLearningRecordServiceInterface {

	/**
	 * 保存点击类型的学习记录 该类型节点包括doc、link、resource、text
	 */
	public void saveClickRecord(String courseId, String itemId, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SAVE_LEARNING_RECORD;
		Map<String, String> map = new HashMap<String, String>();
		map.put("recordType", "0");// 0是点击类型的，1是时间类型的
		map.put("courseId", courseId);
		map.put("itemId", itemId);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCLearningRecordService.this.analyzeDataWithResult(result, responeData, MCCourseModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	/**
	 * 保存记录时长类型的学习记录 基础视频 和三分屏
	 */
	public void saveTimeRecord(String url,String courseId, String itemId, String studyTime, String resourceTotalTime, MCMediaType type,
			final MCAnalyzeBackBlock resultBack, Context context) {

		// 过滤下不正确的参数
		long time = 0;

		try {
			time = Long.valueOf(studyTime);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (time == 0 || time > 600) {// 时间范围不对，返回
			return;
		}

		// 请求主体
		MCBaseRequest request = new MCBaseRequest();
		Map<String, String> map = new HashMap<String, String>();
		if (type == MCMediaType.MC_COURSEWARE_TYPE) {
			// 三分屏学习记录
			request.requestUrl = RequestUrl.getInstance().SAVE_SFP_LEARNING_RECODE;
			map.put("entity.courseId", courseId);
			map.put("entity.scormTime", studyTime);
			map.put("entity.totalTime", resourceTotalTime);
			map.put("entity.itemId", itemId);
			map.put("entity.percent", "0.8 ");
		} else {
			// 普通视频学习记录
			request.requestUrl = url;
			map.put("entity.itemId", itemId);
			map.put("entity.courseId", courseId);
			map.put("entity.studyTime", studyTime);
			resourceTotalTime = TimeFormatUtils.formatTime(Long.valueOf(resourceTotalTime) * 1000);
			map.put("entity.resourceTotalTime", resourceTotalTime);
		}
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCLearningRecordService.this.analyzeDataWithResult(result, responeData, MCCourseModel.class, resultBack);
				System.out.println("SFP==   " + responeData);
			}
		};
		MCNetwork.post(request, context);
	}


	/**
	 * 获取某门课程下的节点学习记录
	 */
	public void getCourseLearnRecord(String courseId, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_LEARNING_RECORD;
		String uid = MCSaveData.getUserInfo(Contants.USERID, context).toString();
		Map<String, String> map = new HashMap<String, String>();
		map.put("params.courseId", courseId);
		map.put("params.loginId", uid);
		map.put("params.siteCode", Const.SITECODE);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCLearningRecordService.this.analyzeDataWithResult(result, responeData, MCSectionModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	/**
	 * 记录日志
	 */
	public void recordWhatyLog(final Context context) {
		MCBaseRequest request = new MCBaseRequest();
		Map<String, String> map = new HashMap<String, String>();
		request.requestUrl = WhatyLogParams.SEND_LOG_URL;
		final String fileName = WhatyLog.fileName;
		long start = System.currentTimeMillis();
		try {
			String content = WhatyLog.is2Str(context.openFileInput(fileName));

			String[] split = content.split("@@");
			request.fileParams = new ArrayList<NameValuePair>();
			for (int i = 0; i < split.length; i++) {
				try {
					if (split.length < 1) {
						break;
					} else if (i == 0) {
						continue;
					}
					request.fileParams.add(new BasicNameValuePair("info", split[i]));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 没有解析到数据就不上传了
		if (request.fileParams == null || request.fileParams.size() == 0) {
			return;
		}
		MCLog.d("MCLearningRecordService", "解析日志耗时：" + (System.currentTimeMillis() - start));
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
					File dir = new File(context.getFilesDir().getAbsolutePath() + "/" + fileName);
					if (dir.exists()) {
						dir.delete();
					}
					MCLog.d("MCLearningRecordService", "日志上传成功并已删除！");
				} else {
					MCLog.d("MCLearningRecordService", "日志上传失败！");
				}
			}
		};
		MCNetwork.post(request, context);
	}

	/*
	 * 调用此方法 请求后台网络
	 */
	public void updateStudyTime(Context context) {
		if (MCNetwork.checkedNetwork(context)) {// 启动的时候有网络就上传日志
			recordWhatyLog(context);
			final OfflineDao dao = new OfflineDao();
			List<MCLearnOfflineRecord> list = dao.queryAll();
			for (int i = 0; i < list.size(); i++) {
				MCLearnOfflineRecord item = list.get(i);
				if (!item.getStudyTime().equals("0")) {
					String courseId = item.getCourseId();
					final String itemId = item.getId();
					final String studyTime = item.getStudyTime();
					String resourceTotalTime = item.getTotalTime();
					MCMediaType type = item.getType() == 0 ? MCMediaType.MC_VIDEO_TYPE : MCMediaType.MC_COURSEWARE_TYPE;
					saveTimeRecord(null,courseId, itemId, studyTime, resourceTotalTime, type, new MCAnalyzeBackBlock() {
						@Override
						public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
							if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
								dao.updateStudyTime(studyTime, itemId);
								MCLog.d("第一次启动测试", "更新成功");
							} else {
								MCLog.d("第一次启动测试", "更新失败");
							}
						}
					}, context);
				}
			}
		}
	}

}
