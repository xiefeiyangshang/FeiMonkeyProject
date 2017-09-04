package com.whatyplugin.imooc.logic.service_;
import android.content.Context;

import com.whatyplugin.base.define.MCBaseDefine;
import com.whatyplugin.base.model.MCXmlSectionModel;
import com.whatyplugin.imooc.logic.model.MCChapterAndSectionModel;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel;

import java.util.List;
public interface MCCourseServiceInterface {
    /**
     * 全部课程
     */
    public void getAllCourse(int page, String keyWord, final MCAnalyzeBackBlock resultBack, Context context);

    /**
     * 我的课程
     */
    public void getMyCourse(String uid, int page, final MCAnalyzeBackBlock resultBack, Context context);

    /**
     * 根据sectionid查找资源
     */
    public void getChapterByCourseId2(String courseId, String uid, final MCAnalyzeBackBlock resultBack, Context context);

    public void getAutoSearchCourse(String uid, String keyword, final MCAnalyzeBackBlock resultBack, Context context);

    public void getChapterByCourseId(String courseId, String uid, final MCAnalyzeBackBlock resultBack, Context context);

    /**
     * 获取课程详情
     */
    public void getCourseDetailByCourseId(String uid, String courseId, final MCAnalyzeBackBlock resultBack, Context context);

    /**
     * 新添加的学生选课功能
     * String courseId  课程id
     * final MCAnalyzeBackBlock resultBack 回调函数
     * Context context  进程上下文
     */
    public void choiceCourse(String courseId, final MCAnalyzeBackBlock resultBack, Context context);

    public void getResouce(String resourceId, final MCAnalyzeBackBlock mcAnalyzeBackBlock, Context context);

    /**
     * 查找所有类型的课程
     *
     * @param uid
     * @param mcAnalyzeBackMapBlock
     * @param context
     */
    public void getCourseType(String uid, MCAnalyzeBackMapBlock mcAnalyzeBackMapBlock, Context context);

    public void getSFPSectionsByPath(MCSectionModel section, final MCAnalyzeBackBlock resultBack, Context context);

    public void getSFPSectionsByMobileXml(String url, final MCAnalyzeBackBlock resultBack);

    /**
     * 查找某课程下所有的下载资料
     *
     * @param resourceId
     * @param mcAnalyzeBackBlock
     * @param context
     */
    public void getDownloadResouce(String resourceId, final MCAnalyzeBackBlock mcAnalyzeBackBlock, Context context);

    /**
     * 离线播放三分屏
     *
     * @param mcAnalyzeBackBlock
     * @param context
     */
    public void getSFPSectionsByPathLocal(String courseId, String sectionId, MCAnalyzeBackBlock mcAnalyzeBackBlock, Context context);

    public void initSFPDownloadInfo(List<MCXmlSectionModel> modelList, MCSectionModel section, MCCourseModel course, MCBaseDefine.MCDownloadNodeType downloadType, MCAnalyzeBackBlock mcAnalyzeBackBlock, Context mContext);

    /**
     * 将选择的添加到下载队列
     *
     * @param course
     * @param checkedSectiones
     * @param resultBack
     * @param mContext
     */
    public void addCheckToDownloadQueue(final MCCourseModel course, final List<MCChapterAndSectionModel> checkedSectiones, final MCAnalyzeBackBlock resultBack, final Context mContext);

    /**
     * 将选择的添加到下载队列
     *
     * @param course
     * @param checkedSectiones
     * @param resultBack
     * @param mContext
     */
    public void addCheckSFPToDownloadQueue(final MCCourseModel course, final List<MCChapterAndSectionModel> checkedSectiones, final List<MCChapterAndSectionModel> checkedSFPSectiones, final MCAnalyzeBackBlock resultBack, final Context mContext);
}

