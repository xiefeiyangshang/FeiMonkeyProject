package com.whatyplugin.imooc.logic.service_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.db.DBCommon.HomeworkColumns;
import com.whatyplugin.imooc.logic.model.MCHomeworkModel;
import com.whatyplugin.imooc.logic.model.MCLiveOnLineModel;
import com.whatyplugin.imooc.logic.model.MCNotice;
import com.whatyplugin.imooc.logic.model.MCQuestionModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCTestModel;
import com.whatyplugin.imooc.logic.model.MCTestQuesModel;
import com.whatyplugin.imooc.logic.model.MCTestResultModel;
import com.whatyplugin.imooc.logic.model.MCUploadModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.logic.utils.RequestUrl;


public class MCStudyService extends MCBaseService implements MCStudyServiceInterface {

	/**
	 * 获取某课程的自测接口
	 */
	public void getAllTest(String opencourseId, int page, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_TEST_LIST_URL;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.loginId", MCSaveData.getUserInfo(Contants.USERID, context).toString());//查询答疑
		map.put("page.searchItem.opencourseId", opencourseId);
		map.put("page.pageSize", "10");//查询答疑
		map.put("page.curPage", String.valueOf(page));

		//下面还未整理比如分页加载
		map.put("params.startIndex", Const.PAGESIZE * (page-1) + "");
		map.put("params.pageSize", Const.PAGESIZE + "");
		map.put("params.siteCode", Const.SITECODE);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeTestListResult(result, responeData, MCTestModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}


	@Override
	public void getTestQuestions(String testId,String opencourseId,int pageSize, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_TEST_QUESTIONS_URL;
		Map<String, String> map = new HashMap<String, String>();

		//map.put("page.curPage", String.valueOf(page));
		map.put("page.pageSize", String.valueOf(pageSize));//查询答疑
		map.put("page.searchItem.loginId", MCSaveData.getUserInfo(Contants.USERID, context).toString());//查询答疑
		map.put("page.searchItem.testId", testId);//查询答疑 //402896024c9da717014c9dadc9e60003
		map.put("page.searchItem.opencourseId", opencourseId);  //402814a349c0aaae0149c1bfcd7f0002

		//下面还未整理比如分页加载
		//map.put("params.startIndex", RequestUrl.getInstance().PAGESIZE * (page-1) + "");
		//map.put("params.pageSize", String.valueOf(pageSize));
		map.put("params.siteCode", Const.SITECODE);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeDataWithResult(result, responeData, MCTestQuesModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void getTestFromNode(String testItemId, int page, final MCAnalyzeBackBlock resultBack, Context context){
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_TEST_QUESTIONS_NODE_URL;
		Map<String, String> map = new HashMap<String, String>();

		map.put("page.curPage", String.valueOf(page));
		map.put("page.pageSize", Const.PAGESIZE + "");
		//map.put("page.searchItem.type", "myquestion");//查询答疑
		map.put("page.searchItem.testItemId", testItemId);
		map.put("loginType", "000");
		request.requestParams = map;
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeDataWithResult(result, responeData, MCTestModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void saveTestAndGetResult(String id, String answer, int flag,  final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SAVE_TEXT_URL;
		Map<String, String> map = new HashMap<String, String>();
		map.put("entity.testId", id);
		map.put("entity.loginId", MCSaveData.getUserInfo(Contants.USERID, context).toString());
		map.put("entity.answer", answer);
		map.put("entity.flag", String.valueOf(flag));
		//map.put("loginType", "student001");
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeTestResult(result, responeData, MCTestResultModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);


	}

	@Override
	public void getQuestion(String courseId, int page, String type, String key, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_QUESTION_URL;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.curPage", String.valueOf(page));
		map.put("page.pageSize", Const.PAGESIZE + "");
		map.put("page.searchItem.type", type);// 查询答疑
		map.put("page.searchItem.content", key);// 查询答疑
		if("myquestion".equals(type)){
			String loginId = MCSaveData.getUserInfo(Contants.USERID, context).toString();
			map.put("page.searchItem.loginId", loginId);
		}
		map.put("page.searchItem.opencourseId", courseId);
		request.requestParams = map;
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeDataWithResult(result, responeData, MCQuestionModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	/**
	 * 获取问题详情接口
	 */
	@Override
	public void getQuestionDetail(String questionId,String loginId,String opencourseId,int page, final MCAnalyzeBackBlock resultBack,
								  Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_QUESTION_DETAIL_URL;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.curPage", String.valueOf(page));
		map.put("page.pageSize", Const.PAGESIZE + "");
		map.put("page.searchItem.loginId", loginId);
		map.put("page.searchItem.opencourseId", opencourseId);
		map.put("page.searchItem.questionId", questionId);
		map.put("page.searchItem.siteCode", Const.SITECODE);
		request.requestParams = map;
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeDataWithResult(result, responeData, MCQuestionModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void sendQuestion(String courseId, String content,
							 List<MCUploadModel> fileList, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		String loginId = MCSaveData.getUserInfo(Contants.USERID, context).toString();
		String userName = MCSaveData.getUserInfo(Contants.NICKNAME, context).toString();

		request.requestUrl = RequestUrl.getInstance().SEND_QUESTION_URL;
		Map<String, String> map = new HashMap<String, String>();
		map.put("entity.submituserId", loginId);
		String title = null;
		if (content.length() > 10) {//如果长度大于10截取
			title = content.substring(0, 10);
		} else {
			title = content;
		}
		map.put("entity.title", title);
		map.put("entity.submituserName",userName);
		map.put("entity.opencourseId",courseId);
		map.put("entity.content",content);
		map.put("entity.siteCode",Const.SITECODE);
		map.put("entity.submituserType","student");
		map.put("opt","2");

		if(fileList != null){
			request.fileParams = new ArrayList<NameValuePair>();
			for (MCUploadModel model : fileList) {
				request.fileParams.add(new BasicNameValuePair("entity.imgUrl", model.getShortPath()));
			}
		}
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeDataWithResult(result, responeData, MCHomeworkModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void sendAnswer(String isReplyAnswer,String reAnswerId,String content, List<MCUploadModel> fileList, String questionId, final MCAnalyzeBackBlock resultBack, Context context) {

		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SEND_QUESTION_ANSWER_URL;
		String loginId = MCSaveData.getUserInfo(Contants.USERID, context).toString();
		String userName = MCSaveData.getUserInfo(Contants.NICKNAME, context).toString();

		Map<String, String> map = new HashMap<String, String>();
		map.put("entity.submituserId", loginId);
		map.put("entity.submituserName",userName);
		map.put("entity.submituserType","student");
		map.put("entity.siteCode", Const.SITECODE);
		map.put("entity.questionId",questionId);
		map.put("entity.content",content);
		map.put("entity.isReplyAnswer",isReplyAnswer);
		map.put("entity.reAnswerId",reAnswerId);
		map.put("opt","2");

		if(fileList != null){
			request.fileParams = new ArrayList<NameValuePair>();
			for (MCUploadModel model : fileList) {
				request.fileParams.add(new BasicNameValuePair("entity.imgUrl", model.getShortPath()));
			}
		}

		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {

			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeDataWithResult(result, responeData, MCHomeworkModel.class, resultBack);

			}
		};
		MCNetwork.post(request, context);
	}

	//获得所有的课程问题个数，按照课程分组
	@Override
	public void getMyAllQuestionList(int curPage, int pageSize, String keyword,
									 final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_ALL_QUESTIN_URL;// 这里改成自己的笔记路径
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.curPage", String.valueOf(curPage));
		map.put("page.pageSize", String.valueOf(pageSize));
		if (!TextUtils.isEmpty(keyword))
			map.put("page.searchItem.keyTagWord", keyword);
		else
			map.put("page.searchItem.keyTagWord", "");
		request.requestParams = map;
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result,
											  String responeData) {
				MCStudyService.this.analyzeNoteWithResult(result,
						responeData, MCQuestionModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void getMyAllHomeWork(int curPage, int pageSize, String keyword, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_ALL_HOMEWORK;// 这里改成自己的笔记路径
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.curPage", String.valueOf(curPage));
		map.put("page.pageSize", String.valueOf(pageSize));
		if (!TextUtils.isEmpty(keyword))
			map.put("page.searchItem.keyTagWord", keyword);
		else
			map.put("page.searchItem.keyTagWord", "");
		request.requestParams = map;
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeNoteWithResult(result, responeData, MCQuestionModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}


	@Override
	public void getCourseHomeworkList(String courseId, int mCurrentPage, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_HOMEWORK_URL;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.courseId", courseId);
		map.put("page.curPage", mCurrentPage + "");
		map.put("page.pageSize", Const.PAGESIZE + "");
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeDataWithResult(result, responeData, MCHomeworkModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	/**
	 * 获取作业详情， 只有type为0的时候才是通过节点id去查询的。
	 * @param homeworkStuId
	 * @param type
	 * @param resultBack
	 * @param context
	 */
	@Override
	public void getHomeworkDetail(String homeworkStuId, int type,
								  final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_HOMEWORK_DETAIL_URL;
		Map<String, String> map = new HashMap<String, String>();
		if(type==0){
			map.put("page.searchItem.homeworkItemId", homeworkStuId);
		}else{
			map.put("page.searchItem.homeworkStuId", homeworkStuId);
		}
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeDataWithResult(result, responeData, MCHomeworkModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	/**
	 * 保存作业， type=1是提交  type=0是存草稿
	 */
	@Override
	public void commitHomeWork(MCHomeworkModel homeworkModel, int type,
							   final MCAnalyzeBackBlock resultBack, Context context) {
		//entity.homeworkId	作业id
		//[entity.homeworkStuId]	学生作业id
		//entity.note	学生作业内容
		//entity.status	作业状态：0保存草稿、1提交

		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SAVE_HOMEWORK_URL;
		Map<String, String> map = new HashMap<String, String>();
		map.put("entity.homeworkId", homeworkModel.getId());

		//国培使用的假数据
		if(!TextUtils.isEmpty(homeworkModel.getHomeworkStuId())&&!homeworkModel.getHomeworkStuId().equals("11")){
			map.put("entity.homeworkStuId", homeworkModel.getHomeworkStuId());
		}
		map.put("entity.note", homeworkModel.getNote());
		map.put("entity.status", "" + type);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeDataWithResult(result, responeData, MCHomeworkModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	/**
	 *  文件上传
	 */
	public void uploadFiles(List<String> imgPaths, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_UPLOADFILE_URL;

		List<NameValuePair> fileParams = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("opt", "1");//1是图片  2是其他
		if(imgPaths != null && imgPaths.size() > 0){
			fileParams = new ArrayList<NameValuePair>();
			for (int i = 0; i < imgPaths.size(); i++) {
				String path = imgPaths.get(i);
				fileParams.add(new BasicNameValuePair("entity.file.file", path));
				map.put("entity.file.fileType", ".jpg");
				map.put("entity.file.rules", "jpg,jpeg");
				String fileName = path.substring(path.lastIndexOf("/")+1, path.length());
				map.put("entity.file.filename", fileName);
				map.put("entity.file.security", "1");
			}
			request.fileParams = fileParams;
		}

		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeDataWithResult(result, responeData, MCUploadModel.class, resultBack);
			}
		};
		MCNetwork.upload(request, context);
	}

	//作业草稿的本地保存
	@Override
	public boolean saveHomeworkToLocal(MCHomeworkModel homeworkModel,String loginId, Context context) {
		ContentResolver resolver = context.getContentResolver();
		ContentValues content = new ContentValues();
		content.put(HomeworkColumns.LOGINID, loginId); //保存的时候保存人
		content.put(HomeworkColumns.COURSEID, homeworkModel.getCourseId());
		content.put(HomeworkColumns.HOMEWORKID, homeworkModel.getId());
		content.put(HomeworkColumns.NOTE, homeworkModel.getNote());
		content.put(HomeworkColumns.LASTDATE, homeworkModel.getLastDate());
		String picPaths = "";
		int length = homeworkModel.getPicPathList()==null ? 0 : homeworkModel.getPicPathList().size();
		for(int i = 0; i < length; i++){
			picPaths = picPaths + homeworkModel.getPicPathList().get(i);
			if(i!=length-1){
				picPaths = picPaths + "___";//3个_
			}
		}
		content.put(HomeworkColumns.PICPATHS, picPaths);
		try {
			//删除根据作业ID和人的ID 进行删除
			resolver.delete(HomeworkColumns.CONTENT_URI, "homeworkId='"+ homeworkModel.getId() +"' and LOGINID = '"+loginId+"'", null);
			resolver.insert(HomeworkColumns.CONTENT_URI, content);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// 获取作业草稿的本地保存
	@Override
	public List<MCHomeworkModel> getHomeworkFromLocal(String courseId, String loginId, Context context) {
		Cursor cursor = null;
		List<MCHomeworkModel> list = new ArrayList<MCHomeworkModel>();
		try {
			cursor = context.getContentResolver().query(HomeworkColumns.CONTENT_URI, HomeworkColumns.projects,
					"courseId='" + courseId + "' and loginId='" + loginId + "'", null, null);
			while (cursor.moveToNext()) {
				MCHomeworkModel model = new MCHomeworkModel();
				model.setCourseId(cursor.getString(cursor.getColumnIndex(HomeworkColumns.COURSEID)));
				model.setId(cursor.getString(cursor.getColumnIndex(HomeworkColumns.HOMEWORKID)));
				model.setNote(cursor.getString(cursor.getColumnIndex(HomeworkColumns.NOTE)));
				model.setLastDate(cursor.getString(cursor.getColumnIndex(HomeworkColumns.LASTDATE)));
				String paths = cursor.getString(cursor.getColumnIndex(HomeworkColumns.PICPATHS));
				if (!TextUtils.isEmpty(paths)) {
					String[] array = paths.split("___");
					List<String> pathList = new ArrayList<String>();
					for (String str : array) {
						pathList.add(str);
					}
					model.setPicPathList(pathList);
				}

				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return list;
	}

	@Override
	public void saveTest(String id, String answer, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().SAVE_TEXT_DETEERMINE;
		Map<String, String> map = new HashMap<String, String>();
		map.put("entity.testId", id);
		map.put("entity.loginId", MCSaveData.getUserInfo(Contants.USERID, context).toString());
		map.put("entity.answer", answer);
		map.put("entity.flag","1"); //1为提交确定
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				//MCStudyService.this.analyzeTestResult(result, responeData, MCTestResultModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void getTestResultDetails(String id, final MCAnalyzeBackBlock resultBack,Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_TEST_RESULT;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem. testId", id);
		map.put("page.searchItem.loginId", MCSaveData.getUserInfo(Contants.USERID, context).toString());

		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeTestDetailsResult(result, responeData, MCTestQuesModel.class, resultBack);

			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void getNoticeList(String courseId, int page,final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().GET_NOTICE_LIST_URL;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.loginId", MCSaveData.getUserInfo(Contants.USERID, context).toString());// 查询答疑
		map.put("page.searchItem.courseId", courseId);
		map.put("page.pageSize", "10");// 查询答疑
		map.put("page.curPage", String.valueOf(page));
		map.put("params.siteCode", Const.SITECODE);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCStudyService.this.analyzeNoticeListResult(result, responeData, MCNotice.class, resultBack);
			}
		};
		MCNetwork.post(request, context);
	}

	@Override
	public void makeNoticeState(String noticeId, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().MAKE_NOTICE_STATE;
		Map<String, String> map = new HashMap<String, String>();
		map.put("entity.noticeId", noticeId);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCServiceResult statusResult = new MCServiceResult();
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);
				resultBack.OnAnalyzeBackBlock(statusResult, null);
			}
		};
		MCNetwork.post(request, context);
	}


	@Override
	public void getLiveOnLine(String noticeId, final MCAnalyzeBackBlock resultBack, Context context) {
		MCBaseRequest request = new MCBaseRequest();
		request.requestUrl = RequestUrl.getInstance().LIVE_ON_LINE;
		Map<String, String> map = new HashMap<String, String>();
		map.put("page.searchItem.itemId", noticeId);
		request.requestParams = this.PreprocessParams(map, context);
		request.listener = new MCNetworkBackListener() {
			public void onNetworkBackListener(MCCommonResult result, String responeData) {
				MCServiceResult statusResult = new MCServiceResult();
				statusResult.setResultCode(MCResultCode.MC_RESULT_CODE_SUCCESS);
				MCStudyService.this.analyzeLiveOnLine(statusResult, responeData, MCLiveOnLineModel.class, resultBack);
			}
		};
		MCNetwork.post(request, context);

	}

}

