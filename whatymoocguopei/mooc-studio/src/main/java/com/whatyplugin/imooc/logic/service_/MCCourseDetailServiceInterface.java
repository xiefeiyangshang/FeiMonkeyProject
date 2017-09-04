package com.whatyplugin.imooc.logic.service_;


import android.content.Context;

public interface MCCourseDetailServiceInterface {
     /**
     * 保存笔记
     * @param courseId
     * @param arg3
     * @param arg4
     * @param arg5
     * @param arg6
     * @param arg7
     */
    void getSendNote(String courseId, String arg3, String arg4, int arg5, MCAnalyzeBackBlock arg6, Context arg7);
    /**
     * 更新笔记
     * @param courseId
     * @param content
     * @param noteId
     * @param updateType
     * @param arg6
     * @param arg7
     */
    void updateNote(String courseId, String content,String noteId, String updateType, MCAnalyzeBackBlock arg6, Context arg7);
    
    void delNote(String noteId, MCAnalyzeBackBlock arg6, Context arg7);
    /**
     * 获取笔记列表及搜索笔记
     * @param courseId
     * @param curPage
     * @param pageSize
     * @param type 筛选条件：0所有笔记、1教师推荐笔记、2我的笔记
     * @param keyword	搜索关键词：笔记名称、标签(可选)
     * @param arg6
     * @param arg7
     */
    void getNoteList(String courseId, int curPage, int pageSize, String type, String keyword, MCAnalyzeBackBlock arg6, Context arg7);
    
    void getMyNoteList(int curPage, int pageSize,String keyword,MCAnalyzeBackBlock resultBack, Context context);
}

