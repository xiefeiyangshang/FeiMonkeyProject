package com.whaty.imooc.ui.index;

import com.whaty.imooc.utile.GPContants;
import com.whaty.imooc.utile.SharedClassInfo;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.RequestUrl;
/**
 * 国培项目自己的请求地址
 *
 * @author 李庆举
 */

public class GPRequestUrl {
    private static GPRequestUrl gPRequestUrl;
    public String CODEURL = getUserInfo(GPContants.CODEURL).equals("") ? "http://gp.ahtvu.ah.cn" : getUserInfo(GPContants.CODEURL);
    // public static String CODEURL = "http://nlts.ahtvu.ah.cn";
    // public String LoginId = ;
    public String SITDECODE = getUserInfo(GPContants.CODESITE).equals("") ?  getSiteCode() : getUserInfo(GPContants.CODESITE);
    //    public static String SITDECODE = "2015gkgp";
    public static String OTHERCODEURL = "http://2015ahdd.learn.webtrn.cn";

    private GPRequestUrl() {
    }

    public static GPRequestUrl getInstance() {
        if (gPRequestUrl == null) {
            gPRequestUrl = new GPRequestUrl();
        }
        return gPRequestUrl;
    }

    // 自定义SQL 接口
    private String CUSTOM_SQL = CODEURL + "/learning/o/userDefinedSql/getBySqlCode.json?data=info";

    // 获取考核标准
    public String GET_ASSESSMENT_CRITERIA = CUSTOM_SQL;
    // 成绩列表
    public String GET_PERFORMANCE = CODEURL + "/learning/o/student/app/getGrade.action?siteCode=" + SITDECODE +  "&classId=";
    // 获取项目ID
    public String GET_PROJECT_ID = CUSTOM_SQL + "&page.searchItem.queryId=appGetClassProjectHomework";
    // 通知 未读改成已读
    public String SAVE_NOTIC_READ = CODEURL + "/learning/app/interface/studyBulletin_saveBulletinReadState.action";
    // 通知 已读状态+1
    public String SAVE_NOTIC_READ_AND_ONE = CODEURL + "/learning/app/interface/studyBulletin_saveBulletinViewCache.action";

    // 点赞，取消点赞 接口
    public String UPDATE_BLOG_LIKE = CODEURL + "/learning/app/interface/blogArticle_saveLikes.action";
    // 上传图片
    public String UPLOAD_IMAGE = CODEURL + "/learning/app/interface/blogArticle_saveUploadFile.action";
    // 写日志
    public String SAVE_MYBLOG = CODEURL + "/learning/app/interface/blogArticle_saveArticle.action";
    // 更新日志
    public String UPDATE_MYBLOG = CODEURL + "/learning/app/interface/blogArticle_updateArticle.action";
    // 删除日志
    public String DELETE_MYBLOG = CODEURL + "/learning/app/interface/blogArticle_delete.action";
    // 更新日志浏览次数
    public String UPDATE_VIEWCOUNT_BLOG = CODEURL + "/learning/app/interface/blogArticle_updateViewCount.action";
    // 回复日志
    public String REPLY_BLOG = CODEURL + "/learning/app/interface/forum_savePostReply.action";
    // 获取日志评论列表 ,主题评论列表
    public String REPLY_BLOG_LIST = CODEURL + "/learning/o/mainTopic/queryMainTopicList.json?data=info&page.searchItem.siteCode=" + getSiteCode();
    // 回复跟帖
    public String REPLY_REPLY_BLOG = CODEURL + "/learning/app/interface/forum_saveRePostReply.action";

    // 回复主题列表跟帖
    public String REPLY_THEME = CODEURL + "/learning/app/interface/forum_savePostReply.action";
    // 获取主贴列表跟帖
    public String REPLY_REPOST_THEME = CODEURL + "/learning/app/interface/forum_saveRePostReply.action";
    // 主题列表点赞
    public String LIKE_UNLIKE_THEME = CODEURL + "/learning/app/interface/forum_saveFavorPostReply.action";
    public String COURSE_LIST = CUSTOM_SQL;
    public String HOMEWORKLIST = OTHERCODEURL + "/learnspace/course/open/queryWithSQL_getBySql.action?queryId=getOneHomework2";



    public String getLogId() {
        return getMCValue(Contants.USERID);

    }

    public String getClassId() {
        return getUserInfo(GPContants.USER_BANJIID);
    }

    public String getProjectId() {

        return getUserInfo(GPContants.USER_PROJECTID);
    }

    public void updateUrl(String host) {
        // 自定义SQL 接口
        CUSTOM_SQL = host + "/learning/o/userDefinedSql/getBySqlCode.json?data=info";
        CODEURL = getMCValue(GPContants.CODEURL).equals("") ? "http://gp.ahtvu.ah.cn" : getMCValue(GPContants.CODEURL);
        SITDECODE = getMCValue(GPContants.CODESITE).equals("") ? getSiteCode(): getMCValue(GPContants.CODESITE);
        // 获取考核标准
        GET_ASSESSMENT_CRITERIA = CUSTOM_SQL;
        // 成绩列表
        GET_PERFORMANCE = host + "/learning/o/student/app/getGrade.action?siteCode=" + SITDECODE + "&classId=";
        // 获取项目ID
        GET_PROJECT_ID = CUSTOM_SQL + "&page.searchItem.queryId=appGetClassProjectHomework";
        // 通知 未读改成已读
        SAVE_NOTIC_READ = host + "/learning/app/interface/studyBulletin_saveBulletinReadState.action";
        // 通知 已读状态+1
        SAVE_NOTIC_READ_AND_ONE = host + "/learning/app/interface/studyBulletin_saveBulletinViewCache.action";

        // 点赞，取消点赞 接口
        UPDATE_BLOG_LIKE = host + "/learning/app/interface/blogArticle_saveLikes.action";
        // 上传图片
        UPLOAD_IMAGE = host + "/learning/app/interface/blogArticle_saveUploadFile.action";
        // 写日志
        SAVE_MYBLOG = host + "/learning/app/interface/blogArticle_saveArticle.action";
        // 更新日志
        UPDATE_MYBLOG = host + "/learning/app/interface/blogArticle_updateArticle.action";
        // 删除日志
        DELETE_MYBLOG = host + "/learning/app/interface/blogArticle_delete.action";
        // 更新日志浏览次数
        UPDATE_VIEWCOUNT_BLOG = host + "/learning/app/interface/blogArticle_updateViewCount.action";
        // 回复日志
        REPLY_BLOG = host + "/learning/app/interface/forum_savePostReply.action";
        // 获取日志评论列表 ,主题评论列表
        REPLY_BLOG_LIST = host + "/learning/o/mainTopic/queryMainTopicList.json?data=info&page.searchItem.siteCode=" + SITDECODE;
        // 回复跟帖
        REPLY_REPLY_BLOG = host + "/learning/app/interface/forum_saveRePostReply.action";

        // 回复主题列表跟帖
        REPLY_THEME = host + "/learning/app/interface/forum_savePostReply.action";
        // 获取主贴列表跟帖
        REPLY_REPOST_THEME = host + "/learning/app/interface/forum_saveRePostReply.action";
        MCLog.e("====   =", REPLY_REPOST_THEME);
        // 主题列表点赞
        LIKE_UNLIKE_THEME = host + "/learning/app/interface/forum_saveFavorPostReply.action";
        COURSE_LIST = CUSTOM_SQL;
        HOMEWORKLIST = OTHERCODEURL + "/learnspace/course/open/queryWithSQL_getBySql.action?queryId=getOneHomework2";


        RequestUrl.getInstance().GET_DIRECTORIES_URL = host + "/learnspace/u/resource/getDirectories.json?page.searchItem.queryTypeFlag=0";
        // 国培用时间统计
        RequestUrl.getInstance().GPSAVE_LEARNING_FOR_TIMER = host + "/learnspace/u/scorm/saveLearnTimer.json";

        RequestUrl.getInstance().SAVE_LEARNING_FOR_TIMER = host+ "/learnspace/u/scorm/saveLearnRecordForTimer.json";
    }

    public String getHomeWorkCourseId() {
        return getUserInfo(GPContants.USER_HOMECOURSEID);
    }

    public String getUserInfo(String key) {
        return SharedClassInfo.getKeyValue(key);
    }

    public String getMCValue(String key) {
        return MCSaveData.getUserInfo(key, MCMainApplication.getInstance()).toString();
    }

    public String getSiteCode() {
        return SharedClassInfo.getKeyValue(GPContants.CODESITE);
    }
}
