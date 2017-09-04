package com.whaty.imooc.ui.workshop;

import com.whatyplugin.imooc.ui.themeforum.ThemeForumInfoActivity;

public class WorkShopInfoActivity extends ThemeForumInfoActivity {

	/**
	 * 重写要写进去的界面
	 * 
	 */
	public Class getIntenClass() {
		return GPSendReplyActivity.class;
	}

	public void initFragment(String type, String noteId, String courseId, Boolean forumState, String hint) {
		/**
		 * 参数为null的时候显示 显示全部 其中包括 精华
		 * 如果参数为 “0”的时候显示全部 其中不包括精华
		 */
		forumReply = new WorkShopFragment(null, noteId, courseId, forumState, hint); // 全部
		forumTopic = new WorkShopFragment("1", noteId, courseId, forumState, hint); // 精华
	}
}
