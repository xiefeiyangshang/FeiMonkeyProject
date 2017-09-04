package com.whatyplugin.imooc.logic.service_;

import java.util.List;

import android.content.Context;

import com.whatyplugin.imooc.logic.model.MCHomeworkModel;
import com.whatyplugin.imooc.logic.model.MCUploadModel;

public interface MCStudyServiceInterface {
	// 获取作业列表
	public void getMyAllHomeWork(int curPage, int pageSize, String keyword, final MCAnalyzeBackBlock resultBack, Context context);
	// 获得问题列表
	public void getQuestion(String courseId, int page, String type, String key, MCAnalyzeBackBlock resultBack, Context context);

	// 获得问题详情
	public void getQuestionDetail(String questinId, String courseId, String opencourseId, int page, MCAnalyzeBackBlock resultBack, Context context);

	// 提问
	public void sendQuestion(String courseId, String content, List<MCUploadModel> pathList, MCAnalyzeBackBlock resultBack, Context context);

	// 回答提交问题
	public void sendAnswer(String isReplyAnswer, String reAnswerId, String content, List<MCUploadModel> fileList, String questionId,
						   final MCAnalyzeBackBlock resultBack, Context context);

	// 所有课程的问题答疑列表
	public void getMyAllQuestionList(int curPage, int pageSize, String keyword, MCAnalyzeBackBlock resultBack, Context context);

	public void getCourseHomeworkList(String courseId, int mCurrentPage, MCAnalyzeBackBlock resultBack, Context context);

	public void getHomeworkDetail(String homeworkStuId, int type, MCAnalyzeBackBlock resultBack, Context context);

	public void commitHomeWork(MCHomeworkModel homeworkModel, int type, MCAnalyzeBackBlock resultBack, Context context);

	public boolean saveHomeworkToLocal(MCHomeworkModel homeworkModel, String loginId, Context context);

	public List<MCHomeworkModel> getHomeworkFromLocal(String courseId, String loginId, Context context);

	public void uploadFiles(List<String> imgPaths, final MCAnalyzeBackBlock resultBack, Context context);

	/**
	 * 自测-获取列表
	 *
	 * @param courseId
	 * @param page
	 * @param resultBack
	 * @param context
	 */
	public void getAllTest(String courseId, int page, MCAnalyzeBackBlock resultBack, Context context);

	/**
	 * 自测-从节点进入获取详情
	 *
	 * @param testItemId
	 * @param page
	 * @param resultBack
	 * @param context
	 */
	public void getTestFromNode(String testItemId, int page, final MCAnalyzeBackBlock resultBack, Context context);

	/**
	 * 自测-获取问题
	 *
	 * @param testId
	 * @param opencourseId
	 * @param page
	 * @param resultBack
	 * @param context
	 */
	public void getTestQuestions(String testId, String opencourseId, int page, final MCAnalyzeBackBlock resultBack, Context context);

	/**
	 * 自测-保存并获取结果
	 *
	 * @param resultBack
	 * @param context
	 */
	public void saveTestAndGetResult(String id, String answer, int flag, final MCAnalyzeBackBlock resultBack, Context context);

	/**
	 * 自测-提交确定
	 *
	 * @param id
	 * @param answer
	 * @param resultBack
	 * @param context
	 */
	public void saveTest(String id, String answer, final MCAnalyzeBackBlock resultBack, Context context);

	/**
	 * 获取自测结果详情（getTestResultDetails）
	 *
	 * @param id
	 * @param resultBack
	 * @param context
	 */
	public void getTestResultDetails(String id, final MCAnalyzeBackBlock resultBack, Context context);

	/**
	 * 自测-获取列表
	 *
	 * @param courseId
	 * @param page
	 * @param resultBack
	 * @param context
	 */
	public void getNoticeList(String courseId, int page, MCAnalyzeBackBlock resultBack, Context context);

	/**
	 * 标记通知公告阅读次数
	 *
	 * @param noticeId
	 * @param resultBack
	 * @param context
	 */
	public void makeNoticeState(String noticeId, MCAnalyzeBackBlock resultBack, Context context);
	/**
	 *
	 * 获取在线直播信息
	 */
	public void getLiveOnLine(String noticeId, MCAnalyzeBackBlock resultBack, Context context);


}
