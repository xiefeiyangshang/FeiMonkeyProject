package com.whaty.imooc.ui.workshop;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.whaty.imooc.logic.service_.GPPerformanceService;
import com.whaty.imooc.logic.service_.GPPerformanceServiceInterface;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.ui.themeforum.FragmentThemeForumReply;
@SuppressLint("ValidFragment")
public class WorkShopFragment extends FragmentThemeForumReply {
	private GPPerformanceServiceInterface service;

	@Override
	public void initData() {
		super.initData();
		service = new GPPerformanceService();
	}

	/**
	 * 创建全部帖子 精华帖子
	 * 
	 * @param topicType
	 * @param noteId
	 * @param CourseId
	 * @param forumState
	 * @param hint
	 */
	public WorkShopFragment(String topicType, String noteId, String CourseId, boolean forumState, String hint) {
		super(topicType, noteId, CourseId, forumState, hint);
	}

	/**
	 * 点赞取消点赞
	 * 
	 */
	public void saveOrDaleteZan(String postId, String mainId, String opt) {
		service.likeUnlikeTheme(getActivity(), postId, mainId, opt, ((MCAnalyzeBackBlock) this));

	}

	/**
	 * 回复跟帖 直接回复空不管返回值
	 */
	@Override
	public void sendComment(String postId, String mainId, String detail) {
		service.rePeplyFollowUp(getActivity(), postId, mainId, detail, null);
	}

	/**
	 * 不显示加1 的动画
	 */
	public void andOneAnimation(TextView view) {

	}

}
