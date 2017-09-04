package com.whatyplugin.imooc.ui.showmooc;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.whatyplugin.base.define.MCBaseDefine.MCMediaType;
import com.whatyplugin.base.dialog.MCCreateDialog;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCForumModel;
import com.whatyplugin.imooc.logic.model.MCHomeworkModel;
import com.whatyplugin.imooc.logic.model.MCNotice;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.model.MCTestModel;
import com.whatyplugin.imooc.logic.model.MCTimerModel;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCFourmService;
import com.whatyplugin.imooc.logic.service_.MCFourmServiceInterface;
import com.whatyplugin.imooc.logic.service_.MCLearningRecordService;
import com.whatyplugin.imooc.logic.service_.MCStudyService;
import com.whatyplugin.imooc.logic.utils.ListUtils;
import com.whatyplugin.imooc.logic.utils.RequestUrl;
import com.whatyplugin.imooc.logic.utils.TimeFormatUtils;
import com.whatyplugin.imooc.logic.whatydb.MCDBOpenHelper;
import com.whatyplugin.imooc.logic.whatydb.dao.base.NoticDao;
import com.whatyplugin.imooc.logic.whatydb.dao.base.OfflineDao;
import com.whatyplugin.imooc.ui.homework.MCHomeworkCommon;
import com.whatyplugin.imooc.ui.selftesting.MCTestDescActivity;
import com.whatyplugin.imooc.ui.selftesting.MCTestResultActivity;
import com.whatyplugin.imooc.ui.themeforum.ThemeForumInfoActivity;
import com.whatyplugin.imooc.ui.view.MyComposerView;
import com.whatyplugin.uikit.dialog.MCCommonDialog;
import com.whatyplugin.uikit.toast.MCToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.whatyplugin.mooc.R;
/**
 * 学习记录 1.视频 视频时长的80%算完成 记录时长 2.图文 点开过就记录完成 记录点击次数 OK 3.文档 下载过就算完成 OK 4.下载资料
 * 点击下载过就算完成 OK 5.电子课件 视频时长的80%算完成 6.外部连接 点过就算完成 记录点击次数 OK
 *
 * @author 马彦君
 */
public class ShowMoocCommon {
    protected static final String TAG = "ShowMoocCommon";
    private static MCLearningRecordService recordService;
    private static MCStudyService studyService;
    private static Map<String, MCTimerModel> timerMap;
    public static String studyTime = "";
    public static boolean flag = false;
    static {
        recordService = new MCLearningRecordService();
        studyService = new MCStudyService();
        timerMap = new HashMap<String, MCTimerModel>();
    }
    private static MCCreateDialog createDialog = new MCCreateDialog();
    private static MCCommonDialog testDialog;

    /**
     * 显示等待框
     *
     * @param context
     * @param info
     */
    public static void showDialog(Context context, String info) {
        testDialog = createDialog.createLoadingDialog((Activity) context, info, 0);
        testDialog.setCancelable(false);
    }

    /**
     * 关闭等待框
     */
    public static void disDialog() {
        if (testDialog != null) {
            testDialog.setCancelable(true);
            testDialog.dismiss();
        }
    }

    /**
     * 记录点击类型的学习记录
     *
     * @param section
     * @param context
     */
    public static void saveClickRecord(final MCSectionModel section, final Context context) {
        recordService.saveClickRecord(section.getCourseId(), section.getId(), new MCAnalyzeBackBlock() {
            @Override
            public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
                if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
                } else {
                }
            }
        }, context);
    }

    /**
     * 记录时长类型的学习记录
     *
     * @param courseId
     * @param sectionId
     * @param totalTime
     * @param context
     */
    public static void saveTimeRecord(String courseId, final String sectionId, String studytime, String totalTime, MCMediaType type, final Context context) {
        final OfflineDao dao = new OfflineDao();
        final String id = sectionId;
        int time = dao.getStudyTime(sectionId) + Integer.parseInt(studytime);
        MCLog.d("this.studyTime", studyTime);
        studytime = time + "";
        recordService.saveTimeRecord(RequestUrl.getInstance().SAVE_LEARNING_FOR_TIMER,courseId, sectionId, studytime, totalTime, type, new MCAnalyzeBackBlock() {
            @Override
            public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
                if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
                    flag = true;
                } else {
                    flag = false;
                    dao.updateStudyTime(studyTime, sectionId);
                }
            }
        }, context);

        recordService.saveTimeRecord(RequestUrl.getInstance().GPSAVE_LEARNING_FOR_TIMER,courseId, sectionId, studytime, totalTime, type, new MCAnalyzeBackBlock() {
            @Override
            public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
                if (result.getResultCode() == MCResultCode.MC_RESULT_CODE_SUCCESS) {
                    flag = true;
                } else {
                    flag = false;
                    dao.updateStudyTime(studyTime, sectionId);
                }
            }
        }, context);
    }

    // 开始定时器
    public static void startRecordTimer(final String sectionId, final String studentId, final MCMediaType Type, final Context context) {
        MCTimerModel model = timerMap.get(sectionId);
        flag = false;
        if (model == null) {
            MCLog.d(TAG, "开始学习定时器！" + sectionId);
            model = new MCTimerModel();
            Timer timer = new Timer();
            final MCTimerModel inner = model;
            TimerTask task = timeTask(inner, studentId, sectionId, Type, context);
            model.setTimer(timer);
            model.setTask(task);
            model.setStartTime(System.currentTimeMillis());
            timer.schedule(task, model.getDelay(), model.getDelay());
            timerMap.put(sectionId, model);
        } else {
            try {
                model.setTimer(new Timer());
                model.setTask(timeTask(model, studentId, sectionId, Type, context));
                model.getTimer().schedule(model.getTask(), model.getDelay(), model.getDelay());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 取消定时器
    public static void stopRecordTimer(String sectionId) {
        MCTimerModel model = timerMap.remove(sectionId);
        if (model != null && model.getTimer() != null) {
            model.getTask().run();// 取消之前执行一次
            model.getTimer().cancel();
        }
    }

    /**
     * 更新属性
     *
     * @param sectionId
     * @param courseId
     */
    public static void updateTimeInfo(String sectionId, String courseId, Object obj) {
        MCTimerModel model = timerMap.get(sectionId);
        if (model != null && model.getTimer() != null) {
            model.setCourseId(courseId);
            if (obj instanceof Long) {
                model.setTotalTime((Long) obj);
            } else {
                model.setPlayer(obj);
            }
        }
    }

    /**
     * 是否显示通知小红点
     *
     * @param moreView
     */
    public static void setNoticPointVisible(final String courseId, final View moreView, final View composerButtonsWrapper, Context context) {
        studyService.getNoticeList(courseId, 1, new MCAnalyzeBackBlock<MCNotice>() {
            @Override
            public void OnAnalyzeBackBlock(MCServiceResult result, List<MCNotice> resultList) {
                if (MCResultCode.MC_RESULT_CODE_SUCCESS.equals(result.getResultCode()) && resultList.size() > 0) {
                    NoticDao dao = new NoticDao();
                    List<MCNotice> queryAll = dao.queryByCondition(true, null, MCDBOpenHelper.TABLE_NOTIC_COURSEID + "=?", new String[]{courseId}, null, null, null, null);
                    if (queryAll == null || queryAll.size() < 1 || !ListUtils.compare(queryAll, resultList)) {
                        moreView.setVisibility(View.VISIBLE);
                        MyComposerView composer_button_notic = (MyComposerView) composerButtonsWrapper.findViewById(R.id.composer_button_notic);
                        composer_button_notic.setPointVisibility(View.VISIBLE);
                    } else {
                        MCLog.d("NoticDao", "数据相同");
                    }
                }
            }
        }, context);
    }

    /**
     * 从节点进入主题讨论
     *
     * @param mCourse
     * @param section
     * @param activity
     */
    public static void goToThemeForumFromSection(final MCCourseModel mCourse, MCSectionModel section, final Activity activity) {
        MCFourmServiceInterface fourmService = new MCFourmService();
        showDialog(activity, "正在获取主题讨论...");
        final String sectionId = section.getId();
        fourmService.getForumListBycourseId(mCourse.getId(), 1, 100, new MCAnalyzeBackBlock<MCForumModel>() {
            @Override
            public void OnAnalyzeBackBlock(MCServiceResult result, List<MCForumModel> resultList) {
                disDialog();
                if (resultList != null && resultList.size() > 0) {
                    MCForumModel currentModle = null;
                    for (MCForumModel model : resultList) {
                        if (sectionId.equals(model.getNodeId())) {
                            currentModle = model;
                            break;
                        }
                    }
                    if (currentModle != null) {
                        Intent intent = new Intent(activity, ThemeForumInfoActivity.class);
                        intent.putExtra("courseId", mCourse.getId());
                        intent.putExtra("ForumModel", currentModle);
                        activity.startActivity(intent);
                    } else {
                        MCToast.show(activity, "没有找到指定的讨论");
                    }
                } else {
                    MCToast.show(activity, "没有获得到该课程相关的主题讨论");
                }
            }
        }, activity);
    }

    /**
     * 从节点进入自测
     *
     * @param mCourse
     * @param section
     * @param activity
     */
    public static void goToSelfTestFromSection(final MCCourseModel mCourse, final MCSectionModel section, final Activity activity) {
        showDialog(activity, "正在获取自测...");
        studyService.getTestFromNode(section.getId(), 0, new MCAnalyzeBackBlock() {
            @Override
            public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
                disDialog();
                if (resultList != null && resultList.size() == 1) {
                    MCTestModel mcTestModel = (MCTestModel) resultList.get(0);
                    if (mcTestModel != null) {
                        // 判断做题时间
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date parse;
                        try {
                            parse = format.parse(mcTestModel.getEndDate());
                            long timelong = parse.getTime();
                            if (timelong < System.currentTimeMillis()) {
                                MCToast.show(activity, "题目已经过期!");
                                return;
                            }
                            if (mcTestModel.isUnStart()) {
                                MCToast.show(activity, "题目尚未开始!");
                                return;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // 判断做题次数
                        if (mcTestModel.getRedo_num() == 0) {
                            /**
                             * 跳到分数界面
                             */
                            Intent intent = new Intent(activity, MCTestResultActivity.class);
                            intent.putExtra("MCTestModel", mcTestModel);
                            intent.putExtra("isComplete", true);
                            activity.startActivityForResult(intent, 0);
                            return;
                        }
                        Intent intent = new Intent(activity, MCTestDescActivity.class);
                        mcTestModel.setOpenCourseID(mCourse.getId());
                        intent.putExtra("MCTestModel", mcTestModel);
                        activity.startActivity(intent);
                    }
                } else {
                    MCToast.show(activity, "获取数据失败,请稍后重试!");
                }
            }
        }, activity);
    }

    /**
     * 从节点进入作业
     *
     * @param mCourse
     * @param section
     * @param activity
     */
    public static void goToHomeworkFromSection(final MCCourseModel mCourse, final MCSectionModel section, final Activity activity) {
        showDialog(activity, "正在获取作业...");
        // 初始化用户名
        final String loginId = MCSaveData.getUserInfo(Contants.USERID, activity).toString();
        studyService.getHomeworkDetail(section.getId(), 0, new MCAnalyzeBackBlock() {
            @Override
            public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
                disDialog();
                if (resultList != null && resultList.size() > 0) {
                    // 用本地草稿进行替换
                    MCHomeworkCommon.replaceHomeworkWithLocal(studyService, mCourse.getId(), loginId, (List<MCHomeworkModel>) resultList, activity);
                    MCHomeworkModel model = (MCHomeworkModel) resultList.get(0);
                    MCHomeworkCommon.gotoHomework(model, activity);// 根据不同情况进入作业
                } else {
                    MCToast.show(activity, "该作业不存在");
                }
            }
        }, activity);
    }

    /**
     * 创建定时器，每次销毁或者锁死之后，一定要new一个。不然的话会报错，定时器无法启动， 无法记录学时。
     *
     * @param inner
     * @param studentId
     * @param sectionId
     * @param Type
     * @param context
     * @return
     */
    public static TimerTask timeTask(final MCTimerModel inner, final String studentId, final String sectionId, final MCMediaType Type, final Context context) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                long videoTime;
                if( studentId !=null || !TextUtils.isEmpty(studentId)){
                     videoTime  =  Long.parseLong(studentId);
                }else{
                     videoTime = inner.getTotalTime();
                }
                if (TextUtils.isEmpty(inner.getCourseId()) || videoTime <= 0) {
                    MCLog.d(TAG, "保存学习记录的时候发现参数不正确！" + sectionId + " -> " + inner.getCourseId() + " - " + videoTime);
                } else {
                    studyTime = TimeFormatUtils.longToStringTime(inner.getStartTime(), inner.getStopTime());
                    // String totalTime = TimeFormatUtils.formatTime(videoTime);
                    String totalTime = String.valueOf(videoTime / 1000);
                    saveTimeRecord(inner.getCourseId(), sectionId, studyTime, totalTime, Type, context);
                    inner.setStartTime(System.currentTimeMillis());
                    MCLog.d(TAG, "更新学习记录：" + studyTime + " of " + totalTime);
                }
            }
        };
        return task;
    }
}
