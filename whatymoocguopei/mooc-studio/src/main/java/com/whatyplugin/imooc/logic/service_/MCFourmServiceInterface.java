package com.whatyplugin.imooc.logic.service_;

import android.content.Context;

public interface MCFourmServiceInterface {
	/**
	 * 
	 * 获取主题讨论列表
	 * @param courseId
	 * @param curPage
	 * @param pageSize
	 * @param callBack
	 * @param context
	 * @return
	 */
	public void getForumListBycourseId(String courseId, int curPage,int pageSize,MCAnalyzeBackBlock callBack, Context context);

	/**
	 * 获取某回复下的评论
	 * 
	 * @param forumId
	 * @return
	 */
	public void getRepylyListByNoteId(String courseId,String noteId,int curPage,int pageSize,String topicType, MCAnalyzeBackBlock callBack, Context context);

	/**
	 * 发送回帖
	 * 
	 * @param coruseId
	 * @param itemsId
	 * @param callBack
	 * @param context
	 */
	public void sendRepyly(String coruseId, String itemsId,String detail, MCAnalyzeBackBlock callBack, Context context);

	/**
	 * 发送某人对帖子或某人的的回复
	 * 
	 * @param postId
	 * @param mainId
	 * @param detail
	 * @param callBack
	 * @param context
	 */
	
	public void  sendComment(String postId, String mainId, String detail, MCAnalyzeBackBlock callBack, Context context);

	/**
	 * 发送某人对回帖的回复
	 * 
	 * @param forumId
	 * @param fromUserId
	 * @param toforumId
	 * @return
	 */
	public void sendConmentToRepyly(String forumId, String fromUserId, String toforumId);

	/**
	 * 发送赞
	 * 
	 * @param forumId
	 * @param userId
	 * @return
	 */
	public void saveOrDeleteZan(String postId,String mainId, String opt, MCAnalyzeBackBlock callBack, Context context);

	
}
