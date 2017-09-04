package com.whatyplugin.base.download;
import android.content.Context;
import android.text.TextUtils;

import com.whatyplugin.base.define.MCBaseDefine.MCDownloadNodeType;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.proxy.MCResourceProxy;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCDownloadService;
import com.whatyplugin.imooc.ui.mymooc.MoocApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class MCDownloadQueue {
    public final static int START_DOWNLOAD = 0;
    public final static int RESUME_DOWNLOAD = 1;
    public final static int PAUSE_DOWNLOAD = 2;
    public final static int WAIT_DOWNLOAD = 3;
    private static String TAG = "MCDownloadQueue";
    private List<MCDownloadTask> mDownloadingTask;
    private List<MCDownloadTask> mFailedTask;
    private List<MCDownloadTask> mPausingTask;
    private List<MCDownloadTask> mWaitingTask;
    private List<MCDownloadTask> mFinishTask;
    public static Map<String, MCDownloadTask> mDownloadTasks = new HashMap<String, MCDownloadTask>();//离线缓存
    public static Map<String, List<MCDownloadVideoNode>> mResMap = new HashMap<String, List<MCDownloadVideoNode>>();//下载资料的记录
    private static MCDownloadQueue queue;
    private Map<MCDownloadNodeType, Integer> map;

    private MCDownloadQueue() {
        super();
        this.mDownloadingTask = new ArrayList<MCDownloadTask>();
        this.mPausingTask = new ArrayList<MCDownloadTask>();
        this.mWaitingTask = new LinkedList<MCDownloadTask>();
        this.mFailedTask = new LinkedList<MCDownloadTask>();
        this.mFinishTask = new ArrayList<MCDownloadTask>();
        map = new HashMap<MCDownloadNodeType, Integer>();
        map.put(MCDownloadNodeType.MC_VIDEO_TYPE, 2);
        map.put(MCDownloadNodeType.MC_RESOURCE_TYPE, 2);
        map.put(MCDownloadNodeType.MC_SFP_TYPE, 1);
    }

    public static MCDownloadQueue getInstance() {
        if (queue == null) {
            queue = new MCDownloadQueue();
        }
        return queue;
    }

    /**
     * 关闭队列的时候清空资源
     */
    public void closeQueue() {
        //清空正在下载的任务
        for (MCDownloadTask task : this.mDownloadingTask) {
            task.cancel(true);
        }
        //释放所有任务中还没有释放的AndroidHttpClient
        Set<String> set = mDownloadTasks.keySet();
        for (String str : set) {
            MCDownloadTask task = mDownloadTasks.get(str);
            MCDownloadVideoNode node = (MCDownloadVideoNode) task.getNode();
            node.release();
        }
        //释放队列
        this.mDownloadingTask.clear();
        this.mPausingTask.clear();
        this.mWaitingTask.clear();
        this.mFailedTask.clear();
        this.mFinishTask.clear();
        this.map.clear();
        this.mDownloadingTask = null;
        this.mPausingTask = null;
        this.mWaitingTask = null;
        this.mFailedTask = null;
        this.mFinishTask = null;
        this.map = null;
    }

    public void initQueue() {
        MCLog.d(TAG, "initQueue");
        final Context context = MoocApplication.getInstance();
        final MCDownloadService service = new MCDownloadService();
        service.getAllDownloadCourse(null, new MCAnalyzeBackBlock() {
            public void OnAnalyzeBackBlock(MCServiceResult result, List arg8) {
                Iterator<MCDownloadVideoNode> iter = arg8.iterator();
                while (iter.hasNext()) {
                    service.getAllDownloadSectionByCourseId(null, iter.next().getCourseId(), new MCAnalyzeBackBlock() {
                        public void OnAnalyzeBackBlock(MCServiceResult result, List retList) {
                            MCLog.d(TAG, "下载任务总数量为： " + retList.size());
                            List<MCDownloadVideoNode> list = retList;
                            Map<String, List<MCDownloadNode>> map = new HashMap<String, List<MCDownloadNode>>();
                            List<MCDownloadVideoNode> sfpParentNodeList = new ArrayList<MCDownloadVideoNode>();
                            for (MCDownloadVideoNode node : list) {
                                if (node.getNodeType() == MCDownloadNodeType.MC_SFP_TYPE) {
                                    if (node.getSectionId().endsWith(Contants.SFP_SUFFIX)) {//是三分屏类型的根节点
                                        sfpParentNodeList.add(node);
                                    } else {
                                        List<MCDownloadNode> innerList = map.get(node.getResourceSection());
                                        if (innerList != null) {
                                            innerList.add(node);
                                        } else {
                                            innerList = new ArrayList<MCDownloadNode>();
                                            innerList.add(node);
                                            map.put(node.getResourceSection(), innerList);
                                        }
                                    }
                                } else {
                                    MCDownloadTask task = new MCDownloadTask(node);
                                    MCDownloadQueue.this.addTask(task);
                                }
                            }
                            //对上面查找到的三分屏的相关节点信息进行处理
                            if (sfpParentNodeList.size() > 0) {
                                for (MCDownloadVideoNode node : sfpParentNodeList) {
                                    node.isMultipleSection = true;
                                    node.childNodeList = map.get(node.getResourceSection());
                                    if (node.childNodeList != null && node.childNodeList.size() > 0) {
                                        for (MCDownloadNode inner : node.childNodeList) {
                                            inner.parentNode = node;
                                        }
                                        MCDownloadTask task = new MCDownloadTask(node);
                                        MCDownloadQueue.this.addTask(task);
                                        MCLog.d(TAG, "三分屏下载情况为：" + node.getSectionName() + "[ " + node.childNodeList.size() + "]个");
                                    }
                                }
                            }
                        }
                    }, context);
                }
            }
        }, context);
        this.notifyChanged();
    }

    /**
     * 添加一个新的任务、防止重复
     *
     * @param task
     */
    public void addTask(MCDownloadTask task) {
        MCDownloadVideoNode node = (MCDownloadVideoNode) task.getNode();
        if (!this.mDownloadTasks.keySet().contains(node.getSectionId())) {
            if (node.isDownloadOver()) {
                this.mFinishTask.add(task);
                MCResourceProxy.getInstance().lock(task.getNode().getFileFullPath());
            } else {
                this.mWaitingTask.add(task);
            }
            this.mDownloadTasks.put(node.getSectionId(), task);
        }
    }

    /**
     * 数据有变动，整体的调整一次
     */
    public void notifyChanged() {
        Set<MCDownloadNodeType> set = map.keySet();
        for (MCDownloadNodeType type : set) {
            int max = map.get(type);
            MCLog.d(TAG, "对类型为 [" + type.info() + " -》 " + max + "]的任务进行处理中……");
            // 数量小于说明可以往里面添加任务
            int size = getDownloadingSize(type);
            if (size < max) {
                operateQueueByCount(START_DOWNLOAD, type, max - size);
            }
        }
        pringInfo();
    }

    /**
     * 根据操作类型及数量处理集合
     *
     * @param operateType
     */
    private void operateQueueByCount(int operateType, MCDownloadNodeType type, int count) {
        MCLog.d(TAG, "操作队列类型  [" + operateType + " - " + type.info() + "]");
        switch (operateType) {
            case START_DOWNLOAD:// 从等待列表移动到下载列表
                for (int i = 0; i < count; i++) {
                    MCDownloadTask downloadTask = getFirstTypeFromList(type, this.mWaitingTask);
                    if (downloadTask != null) {
                        removeTaskFromDownloading(type);
                        downloadTask.execute();
                        this.mDownloadingTask.add(downloadTask);
                    } else {
                        MCLog.d(TAG, "等待列表里已不存在类型为 [" + type.info() + "] 的任务！");
                        break;
                    }
                }
                break;
            case RESUME_DOWNLOAD:// 从暂停列表移动到下载列表
                for (int i = 0; i < count; i++) {
                    MCDownloadTask downloadTask = getFirstTypeFromList(type, this.mPausingTask);
                    if (downloadTask != null) {
                        removeTaskFromDownloading(type);
                        downloadTask.execute();
                        this.mDownloadingTask.add(downloadTask);
                    } else {
                        MCLog.d(TAG, "暂停列表里已不存在类型为 [" + type.info() + "] 的任务！");
                        break;
                    }
                }
                break;
            case PAUSE_DOWNLOAD:// 从下载列表移动到暂停列表
                for (int i = 0; i < count; i++) {
                    MCDownloadTask downloadTask = getFirstTypeFromList(type, this.mDownloadingTask);
                    if (downloadTask != null) {
                        downloadTask.cancel(true);
                        this.mPausingTask.add(downloadTask);
                        downloadTask = null;
                    } else {
                        MCLog.d(TAG, "类型为 [" + type.info() + "] 的任务不在下载列表里");
                        break;
                    }
                }
                break;
            case WAIT_DOWNLOAD:// 添加到等待列表
                break;
            default:
                break;
        }
    }

    /**
     * 移除一个正在下载的已知类型的任务
     *
     * @param type
     */
    private void removeTaskFromDownloading(MCDownloadNodeType type) {
        //下载达到上限了
        if (getDownloadingSize(type) >= this.map.get(type)) {
            MCDownloadTask task = getFirstTypeFromList(type, this.mDownloadingTask);
            //等待一些任务
            task.cancel(true);
            this.mWaitingTask.add(0, task);
        }
    }

    /**
     * 根据操作类型及指定任务处理集合
     *
     * @param operateType
     */
    private void operateQueueByTask(int operateType, MCDownloadNodeType type, MCDownloadTask downloadTask) {
        MCLog.d(TAG, "操作队列类型  [" + operateType + " - " + type.info() + "]");
        switch (operateType) {
            case START_DOWNLOAD:// 从等待列表移动到下载列表
                this.mWaitingTask.remove(downloadTask);
                if (downloadTask != null) {
                    removeTaskFromDownloading(type);
                    downloadTask.execute();
                    this.mDownloadingTask.add(downloadTask);
                } else {
                    MCLog.d(TAG, "等待列表里已不存在类型为 [" + type.info() + "] 的任务！");
                    break;
                }
                break;
            case RESUME_DOWNLOAD:// 从暂停列表移动到下载列表
                this.mPausingTask.remove(downloadTask);
                if (downloadTask != null) {
                    removeTaskFromDownloading(type);
                    downloadTask.execute();
                    this.mDownloadingTask.add(downloadTask);
                } else {
                    MCLog.d(TAG, "暂停列表里已不存在类型为 [" + type.info() + "] 的任务！");
                    break;
                }
                break;
            case PAUSE_DOWNLOAD:// 从下载列表移动到暂停列表
                this.mDownloadingTask.remove(downloadTask);
                if (downloadTask != null) {
                    downloadTask.cancel(true);
                    this.mPausingTask.add(downloadTask);
                    downloadTask = null;
                } else {
                    MCLog.d(TAG, "类型为 [" + type.info() + "] 的任务不在下载列表里");
                    break;
                }
                break;
            case WAIT_DOWNLOAD:// 添加到等待列表
                break;
            default:
                break;
        }
    }

    /**
     * 从指定结合中获取第一个符合类型的下载任务
     *
     * @param type
     * @param list
     * @return
     */
    private MCDownloadTask getFirstTypeFromList(MCDownloadNodeType type, List<MCDownloadTask> list) {
        MCDownloadTask retTask = null;
        for (MCDownloadTask task : list) {
            if (task.getTaskType() == type) {
                list.remove(task);
                retTask = task;
                break;
            }
        }
        return retTask;
    }

    /**
     * 正在下载的任务数量
     *
     * @param type
     * @return
     */
    public int getDownloadingSize(MCDownloadNodeType type) {
        return getCurrentTypeSize(type, this.mDownloadingTask);
    }

    public int getPausingSize(MCDownloadNodeType type) {
        return getCurrentTypeSize(type, this.mPausingTask);
    }

    public int getWaitingSize(MCDownloadNodeType type) {
        return getCurrentTypeSize(type, this.mWaitingTask);
    }

    public int getFinishSize(MCDownloadNodeType type) {
        return getCurrentTypeSize(type, this.mFinishTask);
    }

    public int getFailSize(MCDownloadNodeType type) {
        return getCurrentTypeSize(type, this.mFailedTask);
    }

    /**
     * 某个集合里制定类型任务的数量
     *
     * @param type
     * @param taskList
     * @return
     */
    private int getCurrentTypeSize(MCDownloadNodeType type, List<MCDownloadTask> taskList) {
        int count = 0;
        for (MCDownloadTask task : taskList) {
            if (task.getTaskType() == type) {
                count++;
            }
        }
        return count;
    }

    /**
     * 某课程是否有任务正在进行下载
     *
     * @param courseId
     * @return
     */
    public boolean isCourseDownloading(String courseId) {
        if (TextUtils.isEmpty(courseId)) {
            return false;
        }
        for (MCDownloadTask task : this.mDownloadingTask) {
            MCDownloadVideoNode node = (MCDownloadVideoNode) task.getNode();
            if (courseId.equals(node.getCourseId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 输出任务执行情况
     */
    public void pringInfo() {
        int vidoeNum1 = getDownloadingSize(MCDownloadNodeType.MC_VIDEO_TYPE);
        int sfpNum1 = getDownloadingSize(MCDownloadNodeType.MC_SFP_TYPE);
        int vidoeNum2 = getPausingSize(MCDownloadNodeType.MC_VIDEO_TYPE);
        int sfpNum2 = getPausingSize(MCDownloadNodeType.MC_SFP_TYPE);
        int vidoeNum3 = getWaitingSize(MCDownloadNodeType.MC_VIDEO_TYPE);
        int sfpNum3 = getWaitingSize(MCDownloadNodeType.MC_SFP_TYPE);
        int vidoeNum4 = getFinishSize(MCDownloadNodeType.MC_VIDEO_TYPE);
        int sfpNum4 = getFinishSize(MCDownloadNodeType.MC_SFP_TYPE);
        int vidoeNum5 = getFailSize(MCDownloadNodeType.MC_VIDEO_TYPE);
        int sfpNum5 = getFailSize(MCDownloadNodeType.MC_SFP_TYPE);
        MCLog.d(TAG, "mDownloadingTask.size()" + this.mDownloadingTask.size() + "[" + vidoeNum1 + " - " + sfpNum1 + "]");
        MCLog.d(TAG, "mPausingTask.size()" + this.mPausingTask.size() + "[" + vidoeNum2 + " - " + sfpNum2 + "]");
        MCLog.d(TAG, "mWaitingTask.size()" + this.mWaitingTask.size() + "[" + vidoeNum3 + " - " + sfpNum3 + "]");
        MCLog.d(TAG, "mFinishTask.size()" + this.mFinishTask.size() + "[" + vidoeNum4 + " - " + sfpNum4 + "]");
        MCLog.d(TAG, "mFailedTask.size()" + this.mFailedTask.size() + "[" + vidoeNum5 + " - " + sfpNum5 + "]");
        for (MCDownloadTask task : this.mDownloadingTask) {
            MCLog.d(TAG, "正在下载的任务：" + ((MCDownloadVideoNode) task.getNode()).getSectionName());
        }
    }

    /**
     * 将任务添加到失败队列
     *
     * @param mcDownloadTask
     */
    public void addToFailedList(MCDownloadTask mcDownloadTask) {
        MCLog.d(TAG, "失败了的任务：" + ((MCDownloadVideoNode) mcDownloadTask.getNode()).getSectionName());
    }

    /**
     * 通过sectionId删除制定任务
     *
     * @param sectionId
     */
    public void deleteTaskBySectionId(String sectionId) {
        MCDownloadTask task = MCDownloadQueue.mDownloadTasks.remove(sectionId);
        if (task != null) {
            task.cancel(true);
            this.mDownloadingTask.remove(task);
            this.mWaitingTask.remove(task);
            this.mFailedTask.remove(task);
            this.mPausingTask.remove(task);
            try {
                task.getNode().deleteFileFromLocal();
            } catch (Exception e) {
                MCLog.d(TAG, task.getNode().getFilename() + " 删除本地文件失败！");
            }
        }
    }

    /**
     * 某任务是否正在下载
     *
     * @param downloadTask
     * @return
     */
    public boolean isTaskDownloading(MCDownloadTask downloadTask) {
        return this.mDownloadingTask.contains(downloadTask);
    }

    /**
     * 某任务是否已下载
     *
     * @param downloadTask
     * @return
     */
    public boolean isTaskDownloaded(MCDownloadTask downloadTask) {
        return this.mFinishTask.contains(downloadTask);
    }

    /**
     * 某任务是否正在等待下载
     *
     * @param downloadTask
     * @return
     */
    public boolean isTaskWaiting(MCDownloadTask downloadTask) {
        return this.mWaitingTask.contains(downloadTask);
    }

    /**
     * 某种类型的任务是否是排满
     *
     * @param mcDownloadNodeType
     * @return
     */
    public boolean isQueueFulled(MCDownloadNodeType mcDownloadNodeType) {
        return getDownloadingSize(mcDownloadNodeType) >= this.map.get(mcDownloadNodeType);
    }

    /**
     * 暂停任务
     *
     * @param mcDownloadTask
     */
    public void pauseTask(MCDownloadTask downloadTask) {
        MCLog.d(TAG, "任务 ：[" + downloadTask.getNode().getFilename() + "]被暂停！");
        if (!this.mPausingTask.contains(downloadTask)) {
            this.mPausingTask.add(downloadTask);
        }
        if (this.mDownloadingTask.contains(downloadTask)) {
            downloadTask.cancel(true);
            this.mDownloadingTask.remove(downloadTask);
        }
        this.notifyChanged();
    }

    /**
     * 完成任务
     *
     * @param downloadTask
     */
    public void completeTask(MCDownloadTask downloadTask) {
        MCLog.d(TAG, "任务 ：[" + downloadTask.getNode().getFilename() + "]完成！");
        if (!this.mFinishTask.contains(downloadTask)) {
            this.mFinishTask.add(downloadTask);
        }
        if (this.mDownloadingTask.contains(downloadTask)) {
            this.mDownloadingTask.remove(downloadTask);
        }
        MCResourceProxy.getInstance().lock(downloadTask.getNode().getFileFullPath());
        this.notifyChanged();
    }

    /**
     * 某个任务从等待切换到下载
     *
     * @param sectionId
     */
    public void insertTaskFromWaitingToDownloading(String sectionId) {
        MCLog.d(TAG, "某个任务从等待切换到下载");
        MCDownloadTask downloadTask = this.mDownloadTasks.get(sectionId);
        if (downloadTask == null) {
            MCLog.d(TAG, "某个任务从等待切换到下载时， 没有通过sectionId获取到MCDownloadTask！！！");
            return;
        }
        if (this.mWaitingTask.contains(downloadTask)) {
            this.operateQueueByTask(START_DOWNLOAD, downloadTask.getTaskType(), downloadTask);
            this.notifyChanged();
        } else {
            MCLog.d(TAG, "某个任务从等待切换到下载时， 没有在mDownloadingTask中找到该任务！！！");
        }
    }

    /**
     * 某个任务从暂停切换到下载
     *
     * @param sectionId
     */
    public void insertTaskFromPauseToDownloading(String sectionId) {
        MCLog.d(TAG, "某个任务从暂停切换到下载");
        MCDownloadTask downloadTask = this.mDownloadTasks.get(sectionId);
        if (downloadTask == null) {
            MCLog.d(TAG, "某个任务从等待切换到下载时， 没有通过sectionId获取到MCDownloadTask！！！");
            return;
        }
        if (this.mPausingTask.contains(downloadTask)) {
            this.operateQueueByTask(RESUME_DOWNLOAD, downloadTask.getTaskType(), downloadTask);
            this.notifyChanged();
        } else {
            MCLog.d(TAG, "某个任务从暂停切换到下载时， 没有在mPausingTask中找到该任务！！！");
        }
    }

    /**
     * 某个任务从下载切换到暂停
     *
     * @param sectionId
     */
    public void insertTaskFromDownloadingToPause(String sectionId) {
        MCLog.d(TAG, "某个任务从下载切换到暂停");
        MCDownloadTask downloadTask = this.mDownloadTasks.get(sectionId);
        if (downloadTask == null) {
            MCLog.d(TAG, "某个任务从等待切换到下载时， 没有通过sectionId获取到MCDownloadTask！！！");
            return;
        }
        if (this.mDownloadingTask.contains(downloadTask)) {
            this.operateQueueByTask(PAUSE_DOWNLOAD, downloadTask.getTaskType(), downloadTask);
            this.notifyChanged();
        } else {
            MCLog.d(TAG, "某个任务从下载切换到暂停时， 没有在mDownloadingTask中找到该任务！！！");
        }
    }

    /**
     * 正在下载任务数量
     *
     * @return
     */
    public int getDownloadingTaskCount() {
        return this.mDownloadingTask.size();
    }

    public static Map<String, List<MCDownloadVideoNode>> getDownloadMap() {
        return mResMap;
    }

    /**
     * 整个队列中是否有某个任务的下载
     *
     * @param item
     * @return
     */
    public boolean isDownloadNodeInQueue(MCDownloadVideoNode item) {
        if (item == null) {
            return false;
        }
        return mDownloadTasks.get(item.getSectionId()) != null;
    }

    /**
     * 暂停所有下载的任务，  切换缓存目录的时候会用到
     *
     * @param item
     * @return
     */
    public void pauseDownLoadingTask() {
        if (mDownloadingTask.size() > 0) {
            for (MCDownloadTask task : mDownloadingTask) {
                task.cancel(true);
            }
        }
    }

    /**
     * 切换缓存目录的时候会用到
     *
     * @param item
     * @return
     */
    public void resumeDownLoadingTask() {
        if (mDownloadingTask.size() > 0) {
            for (MCDownloadTask task : mDownloadingTask) {
                if (task.isCancelled()) {
                    task.execute();
                }
            }
        }
    }
}

