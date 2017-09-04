package com.whaty.imooc.logic.service_;

import android.content.Context;

import com.whatyplugin.base.define.MCBaseDefine;
import com.whatyplugin.base.model.MCXmlSectionModel;
import com.whatyplugin.base.runstat.MCRunStart.MCAPPType;
import com.whatyplugin.base.runstat.MCRunStart.MCPlatType;
import com.whatyplugin.base.runstat.MCRunStart.MCServiceType;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;

import java.util.List;

/**
 * 自己app写的自己的service请求类，开发人员可以参考该demo进行相应的处理
 * 
 * @author 马彦君
 * 
 */
public interface MCCommonServiceInterface {

	/**
	 * 请求网络的方式 - DEMO 全部课程
	 */
	public void getAllCourse(int page, String keyWord, final MCAnalyzeBackBlock resultBack, Context context);

	/**
	 * 耗时操作进行的 AsyncTask的方式 - DEMO 解析节点信息
	 */
	public void initSFPDownloadInfo(List<MCXmlSectionModel> modelList, MCSectionModel section, MCCourseModel course, MCBaseDefine.MCDownloadNodeType downloadType, MCAnalyzeBackBlock mcAnalyzeBackBlock, Context mContext);

	public void loginByWhaty(String email, String password, MCServiceType service, String channelId, MCPlatType plat, MCAPPType app,
							 String screensize, final MCAnalyzeBackBlock resultBack, Context context);

	public void loginByWhaty(String email, String password, String siteCode, MCServiceType service, String channelId, MCPlatType plat, MCAPPType app,
							 String screensize, final MCAnalyzeBackBlock resultBack, Context context);

	public void getCourseDetailByCourseId(String uid, String courseId, final MCAnalyzeBackBlock resultBack, Context context);


}
