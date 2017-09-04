package com.whatyplugin.imooc.logic.utils;

public class RequestUrl {

    private static RequestUrl requestUrl;

    private final String BASE_PATH_COURSE = "";
    //用于补全图片的URL
    public String startUrl = "";

    public String MODEL_BASE = getSiteLocalUrl() + getBasePath() + "/";

    /**
     * 域名 如："http://inside.kfkc.webtrn.cn"
     *
     * @return
     */
    private String getSiteLocalUrl() {
        return Const.SITE_LOCAL_URL;
    }

    /**
     * 项目名 如："learnspace"
     *
     * @return
     */
    private String getBasePath() {
        return Const.BASE_PATH;
    }

    // 意见反馈
    public String IDEA_FEED_URL = "http://question.webtrn.cn/mobile/mobileSuggestion.action";

    /**
     * ***************************** 用户注册、登陆相关 **************************
     */
    // 注册
    public String REGIST_URL = getSiteLocalUrl() + "/center/mobile/mobileRegister_register.action";

    // 登陆url
    public String LOGIN_URL = getSiteLocalUrl() + "/center/mobile/mobileLogin_mobileLogin.action";

    //多站点登录 LOGIN_URL
    public String LOGIN_URL_MORESITES = "http://login.kfkc.webtrn.cn/center/center/login_productSingleLogin.action";
    // 退出url
    public final String GET_LOGINQUIT_URL = getSiteLocalUrl() + "/sso/login_close.action";

    // 退出url
    public final String GET_LOGINCLOSE_URL = getSiteLocalUrl() + "/center/center/login_quit.action";

    /**
     * ***************************** 管理端相关功能 **************************
     * (上面是新的)
     */
    // 获取课程列表url   /u/kfkc/openCourse/queryCourses.json
    //旧的  /login/kfkc/firstOpenCourseAction/openCourse/queryCourses.json
    public String GET_COUSELIST_URL = getSiteLocalUrl() + BASE_PATH_COURSE + "/u/kfkc/openCourse/queryCourses.json";

    // 不登陆获取课程列表url  /open/kfkc/openCourse/queryCourses.json
    ///first/kfkc/firstOpenCourseAction/openCourse/queryCourses.json
    public String GET_FIRSTCOUSELIST_URL = getSiteLocalUrl() + BASE_PATH_COURSE + "/open/kfkc/openCourse/queryCourses.json";

    // 选课  /u/kfkc/openCourse/saveStuElective.json
    ///login/kfkc/firstOpenCourseAction/openCourse/saveStuElective.json
    public String CHOICE_COURSE_URL = getSiteLocalUrl() + BASE_PATH_COURSE + "/u/kfkc/openCourse/saveStuElective.json";

    // 课程详情  /u/kfkc/openCourse/queryCourseDetail.json
    ///login/kfkc/firstOpenCourseAction/openCourse/queryCourseDetail.json
    public String COURSE_DETAIL_URL = getSiteLocalUrl() + BASE_PATH_COURSE + "/u/kfkc/openCourse/queryCourseDetail.json";

    // 不登陆的课程详情(课程简介)
//    public String COURSE_FIRSTDETAIL_URL = getSiteLocalUrl() + BASE_PATH_COURSE + "/open/kfkc/openCourse/queryCourseDetail.json";

    public String COURSE_FIRSTDETAIL_URL =  getSiteLocalUrl() + getBasePath() + "/v1/course/info.json";
    // 获取用户信息
    public String GET_USRINFO_URL = getSiteLocalUrl() + BASE_PATH_COURSE + "/login/kfkc/firstOpenCourseAction/userInfo/queryUserInfo.json";

    // 修改用户信息
    public String SET_USRINFO_URL = getSiteLocalUrl() + BASE_PATH_COURSE + "/login/kfkc/firstOpenCourseAction/userInfo_saveUserBasicInfo.action";

    /**
     * ***************************** Learning相关功能 **************************
     */

    // 保存笔记
    public String SAVE_NOTE_URL = getSiteLocalUrl() + getBasePath() + "/u/note/saveNote.json";

    // 获取笔记列表
    public String GET_NOTE_URL = getSiteLocalUrl() + getBasePath() + "/u/note/getNoteList.json";

    // 获取我的笔记列表
    public String GET_MYNOTE_URL = getSiteLocalUrl() + getBasePath() + "/u/note/getMyNoteList.json";

    // 获取最新问题接口
    public String GET_QUESTION_URL = getSiteLocalUrl() + getBasePath() + "/u/answer/getQuestions.json";

    // 获得问题详情接口
    public String GET_QUESTION_DETAIL_URL = getSiteLocalUrl() + getBasePath() + "/u/answer/getAnswerOfQuestion.json";

    // 获取章节目录
//    public String GET_DIRECTORIES_URL = getSiteLocalUrl() + getBasePath() + "/u/resource/getDirectories.json";
    public String GET_DIRECTORIES_URL = getSiteLocalUrl() + getBasePath() + "/u/resource/getDirectories.json?page.searchItem.queryTypeFlag=0";

    // 获取目录下资源
    public String GET_RESOURCE_URL = getSiteLocalUrl() + getBasePath() + "/u/resource/getResource.json";

    // 获取某课程下的作业列表
    public String GET_HOMEWORK_URL = getSiteLocalUrl() + getBasePath() + "/u/homework/getHomeworks.json";

    // 发送问题
    public String SEND_QUESTION_URL = getSiteLocalUrl() + getBasePath() + "/u/answer/saveQuestion.json";

    // 获得所有的答疑信息
    public String GET_ALL_QUESTIN_URL = getSiteLocalUrl() + getBasePath() + "/u/answer/getMyQuestionList.json";

    // 发送答案
    public String SEND_QUESTION_ANSWER_URL = getSiteLocalUrl() + getBasePath() + "/u/answer/saveAnswer.json";

    // 获取自己的某个作业作答情况
    public String GET_HOMEWORK_DETAIL_URL = getSiteLocalUrl() + getBasePath() + "/u/homework/getHomeworkStudent.json";

    // 保存、提交作业
    public String SAVE_HOMEWORK_URL = getSiteLocalUrl() + getBasePath() + "/u/homework/saveHomeworkStudent.json";

    // 文件上传路径
    public String GET_UPLOADFILE_URL = getSiteLocalUrl() + getBasePath() + "/u/common/saveUploadFile.json";

    // 获取课程自测列表
    public String GET_TEST_LIST_URL = getSiteLocalUrl() + getBasePath() + "/u/test/getTests.json";

    // 获取课程自测问题
    public String GET_TEST_QUESTIONS_URL = getSiteLocalUrl() + getBasePath() + "/u/test/getTestQuestions.json";

    // 获取课程自测从节点
    public String GET_TEST_QUESTIONS_NODE_URL = getSiteLocalUrl() + getBasePath() + "/u/test/getTest.json";

    // 保存自测并且获取答案
    public String SAVE_TEXT_URL = getSiteLocalUrl() + getBasePath() + "/u/test/saveTestAndGetTestResultDeatils.json";

    // 保存自测提交确定
    public String SAVE_TEXT_DETEERMINE = getSiteLocalUrl() + getBasePath() + "/u/test/saveTestCommitFlag.json";// "/u/test/saveTest.json";

    // 获取自测结果
    public String GET_TEST_RESULT = getSiteLocalUrl() + getBasePath() + "/u/test/getTestResult.json";

    // 保存学习记录
    public String SAVE_LEARNING_RECORD = getSiteLocalUrl() + getBasePath() + "/course/study/learningTime_saveCourseItemLearnRecord.action";

    // 三分屏学习记录
    public String SAVE_SFP_LEARNING_RECODE = getSiteLocalUrl() + getBasePath() + "/u/scorm/saveScormStuCourse.json";

    // 保存学习记录
    public String SAVE_LEARNING_FOR_TIMER = getSiteLocalUrl() + getBasePath() + "/u/scorm/saveLearnRecordForTimer.json";

    public String GPSAVE_LEARNING_FOR_TIMER = getSiteLocalUrl() + getBasePath() + "/u/scorm/saveLearnTimer.json";


    // 获取某课程下的学习记录
    public String GET_LEARNING_RECORD = getSiteLocalUrl() + getBasePath() + "/learn/learnCourseware/queryItemStudyRecordByCourseId.json";

    // 获取课程通知列表
    public String GET_NOTICE_LIST_URL = getSiteLocalUrl() + getBasePath() + "/u/notice/getNoticeList.json";

    // 保存通知阅读状态
    public String MAKE_NOTICE_STATE = getSiteLocalUrl() + getBasePath() + "/u/notice/saveNoticeReaded.json";

    // 获取主题讨论列表
    public String GET_LIST_FORUM = getSiteLocalUrl() + getBasePath() + "/u/mainTopic/getMainTopicList.json";

    // 获取主题列表的内容
    public String GET_FORUM_INFO = getSiteLocalUrl() + getBasePath() + "/u/mainTopic/getMainTopicReplies.json";

    // 更新点赞操作
    public String SAVE_OR_DELETE_ZAN = getSiteLocalUrl() + getBasePath() + "/u/mainTopic/saveFavorPostReply.json";

    // 回复评论
    public String SAVE_REPLY = getSiteLocalUrl() + getBasePath() + "/u/mainTopic/saveRePostReply.json";

    // 回复跟帖
    public String SAVE_POST_REPLY = getSiteLocalUrl() + getBasePath() + "/u/mainTopic/savePostReply.json";
    // 作业列表
    public String GET_ALL_HOMEWORK = getSiteLocalUrl() + getBasePath() + "/u/homework/getMyHomeworkList.json";
    // 上传头像接口 。。 只针对上传头像 上传到用户中心
    public String SAVE_HAND_IMAGE = getSiteLocalUrl() + "/center/uInfo/userInfo_uploadImageForSdk.action";
    // 保存头像接口 上传全路径头像
    public String SAVE_HAND = getSiteLocalUrl() + "/center/uInfo/userInfo_saveUserPhotoForSdk.action";

    // 问题管理系统的崩溃日志收集
    public String SEND_CRASH_INFO_URL = "http://question.webtrn.cn/mobile/collectionCrash_collectionCrash.action";

    // 问题管理系统统计装机量
    public String SEND_LOAD_INFO_URL = "http://question.webtrn.cn/mobile/collectionCrash_collectionCrash.action";

    // 直播地址
    public String LIVE_ON_LINE = getSiteLocalUrl() + getBasePath() + "/u/resource/getLiveInfo.json";

    public static RequestUrl getInstance() {
        if (requestUrl == null) {
            requestUrl = new RequestUrl();
        }
        return requestUrl;
    }

    /**
     * 重新创建的对象地址就是从const里重新获取的了。
     */
    public static void initUrl() {
        requestUrl = new RequestUrl();
    }

}
