package com.whatyplugin.imooc.logic.service_;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.whatyplugin.base.define.MCBaseDefine.MCDownloadNodeType;

public interface MCDownloadServiceInterface {

	public void getAllDownloadCourse(MCDownloadNodeType downloadNodeType, MCAnalyzeBackBlock callBack, Context context);

	public void getAllDownloadSectionByCourseId(MCDownloadNodeType downloadNodeType, String courseId, MCAnalyzeBackBlock callBack, Context context);

	public void getSectionCountByCourseId(MCDownloadNodeType downloadNodeType, String courseId, MCAnalyzeBackBlock callBack, Context context);

	public void getTotalSizeByCourseId(MCDownloadNodeType downloadNodeType, String courseId, MCAnalyzeBackBlock callBack, Context context);

	public void getResourceBySectionId(String id, MCAnalyzeBackBlock callBack, Context context);

	public void getSFPFilenameBySectionId(String sectionId, MCAnalyzeBackBlock callBack, Context context);

	public void removeVideoToNewDisk(List<String> filelist, String orgPath, String targetPath, boolean deleteOrg, TextView tipView,
			MCAnalyzeBackBlock mcAnalyzeBackBlock, Context mContext);
}
