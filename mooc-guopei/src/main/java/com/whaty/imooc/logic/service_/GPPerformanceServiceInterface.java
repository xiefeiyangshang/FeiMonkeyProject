package com.whaty.imooc.logic.service_;

import java.util.List;

import android.content.Context;

import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;

public interface GPPerformanceServiceInterface {
	public void getAssessmentAndCriteria(String projectID, MCAnalyzeBackBlock mCAnalyzeBackBlock, Context context);

	// 获取ID
	public void getProjectId(Context context, String LoginId, MCAnalyzeBackBlock mCAnalyzeBackBlock);

	// 获取热门BLOG和最新BLOG
	public void getHotAndNewBlog(Context context, int curPage, String ClassId, String queryId, String loginId, Class Clazz,
								 MCAnalyzeBackBlock mCAnalyzeBackBlock);

	// 获取工作坊活动
	public void getWorkShopList(Context context, String type, MCAnalyzeBackBlock mCAnalyzeBackBlock);

	// 保存我的日志
	public void saveMyBlog(Context context, String BlogId, String title, String content, String abstractContent, MCAnalyzeBackBlock mCAnalyzeBackBlock);

	// 获取主题列表

	public void getThemeList(Context context, int curPage, String activityId, MCAnalyzeBackBlock mCAnalyzeBackBlock);

	// 获取通知公告列表
	public void getNoticList(Context context, int curPage, MCAnalyzeBackBlock mCAnalyzeBackBlock);

	// 更新已读未读，
	public void setRead(Context context, String noticId, String type, MCAnalyzeBackBlock mCAnalyzeBackBlock);

	// 阅读次数加1
	public void setNoticAndOne(Context context, String noticId, String type, String viewCount, MCAnalyzeBackBlock mCAnalyzeBackBlock);

	// 上传图片 webtrn平台的上传头像
	public void uploadFiles(List<String> imgPaths, final MCAnalyzeBackBlock resultBack, Context context);

	// 日志浏览次数+1
	public void updateViewCount(Context context, String blogId, String viewCount);

	// 写日志
	public void updateMyBlog(Context context, String title, String content, MCAnalyzeBackBlock resultBack);

	// 删除日志
	public void deleteMyBlog(Context context, String blogId, MCAnalyzeBackBlock resultBack);

	// 点赞 取消点赞
	public void likeBlog(Context context, String blogId);

	// 获取点赞列表
	public void getLikeList(Context context, String blogId, MCAnalyzeBackBlock resultBack);

	// 回复日志
	public void replyBlog(Context context, String blogId, String detail, MCAnalyzeBackBlock resultBack);

	// 获取评论列表
	public void getReplyBlogList(Context context, String blogId, int curPage, MCAnalyzeBackBlock resultBack);

	// 回复跟帖
	public void rePeplyFollowUp(Context context, String postId, String rePostId, String detail, MCAnalyzeBackBlock resultBack);

	// 工作坊活动点赞事件
	public void likeUnlikeTheme(Context context, String postId, String mainId, String opt, MCAnalyzeBackBlock resultBack);

	// 获取活动帖子数跟精华数
	public void getThemeListReplyNum(Context context, String ids, MCAnalyzeBackBlock resultBack);

	// 获取作业列表
	public void getHomeWorkList(Context context, MCAnalyzeBackBlock resultBack);

	// 获取课程列表
	public void getCourseList(Context context, String pageNum, MCAnalyzeBackBlock resultBack);

	// 获取学生成绩
	public void studentScore(Context context, MCAnalyzeBackBlock resultBack);

}
