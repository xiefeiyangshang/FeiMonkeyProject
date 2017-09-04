package com.whatyplugin.base.utils;

import java.util.HashMap;
import java.util.Map;

import com.whatyplugin.imooc.ui.homework.MCHomeworkListActivity;
import com.whatyplugin.imooc.ui.note.MCNoteListActivity;
import com.whatyplugin.imooc.ui.notic.MCNoticeListActivity;
import com.whatyplugin.imooc.ui.question.MCQuestionMainActivity;
import com.whatyplugin.imooc.ui.selftesting.MCTestListActivity;
import com.whatyplugin.imooc.ui.themeforum.ThemeForumListActivity;

public class MCCourseConst {
	private static MCCourseConst mCCourseConst;
	/**
	 * 默认配置 弹出选项个数 null代表 默认的六个 大于2
	 */
	public Integer count = null;
	// 显示下到上 0-6；key =index List 选项名称 ， Class 要跳转的Class
	public Map<Integer, Object[]> MapClass = MapClass();

	/**
	 * 从新赋值的时候 必须使用 MCCourseConst.getInstance().MapClass.put(0,object[]{});
	 * 从新赋值的时候 必须使用 MCCourseConst.getInstance().MapClass.put(1,object[]{});
	 * 从新赋值的时候 必须使用 MCCourseConst.getInstance().MapClass.put(2,object[]{});
	 * 否者会报空指针
	 * 
	 * @return
	 */
	private Map<Integer, Object[]> MapClass() {
		MapClass = new HashMap<Integer, Object[]>();
		MapClass.put(5, new Object[] { "   通知     ", MCNoticeListActivity.class });
		MapClass.put(4, new Object[] { "我的讨论", ThemeForumListActivity.class });
		MapClass.put(3, new Object[] { "我的自测", MCTestListActivity.class });
		MapClass.put(2, new Object[] { "我的作业", MCHomeworkListActivity.class });
		MapClass.put(1, new Object[] { "大家疑问", MCQuestionMainActivity.class });
		MapClass.put(0, new Object[] { "共享笔记", MCNoteListActivity.class });
		return MapClass;
	}

	public static MCCourseConst getInstance() {
		if (mCCourseConst == null) {
			mCCourseConst = new MCCourseConst();
		}
		return mCCourseConst;
	}

	public static void initMCourseConst() {
		mCCourseConst = new MCCourseConst();
	}

}
