package com.whatyplugin.imooc.logic.service_;

import android.content.Context;

import com.whatyplugin.base.define.MCBaseDefine.MCMediaType;

public interface MCLearningRecordServiceInterface {

	/**
	 * 保存点击类型的学习记录 该类型节点包括doc、link、resource、text
	 */
	public void saveClickRecord(String courseId, String itemId, 
			final MCAnalyzeBackBlock resultBack, Context context);

	/**
	 * 保存记录时长类型的学习记录
	 */
	public void saveTimeRecord(String url,String courseId, String itemId, String studyTime, String resourceTotalTime,  MCMediaType type
			,final MCAnalyzeBackBlock resultBack, Context context);
	
	 /**
     * 获取某门课程下的节点学习记录
     */
    public void getCourseLearnRecord(String courseId, final MCAnalyzeBackBlock resultBack, Context context) ;
    
	/**
	 * 记录日志
	 */
	public void recordWhatyLog(Context context) ;
}
