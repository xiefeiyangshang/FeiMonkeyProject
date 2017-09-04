package com.whatyplugin.imooc.logic.service_;
import android.content.Context;
import android.text.TextUtils;

import com.whatyplugin.base.define.MCBaseDefine;
import com.whatyplugin.base.define.MCBaseDefine.MCDownloadNodeType;
import com.whatyplugin.base.download.MCDownloadQueue;
import com.whatyplugin.base.download.MCDownloadVideoNode;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.model.MCXmlSectionModel;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.base.network.MCNetworkBackListener;
import com.whatyplugin.base.utils.CommonUtils;
import com.whatyplugin.base.utils.ParseXMLUtil;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCChapterAndSectionModel;
import com.whatyplugin.imooc.logic.model.MCChapterModel;
import com.whatyplugin.imooc.logic.model.MCCourseDetailModel;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCProgrammeModel;
import com.whatyplugin.imooc.logic.model.MCResourceModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.logic.utils.RequestUrl;
import com.whatyplugin.imooc.ui.download.MCDownloadHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
public class MCCourseService extends MCBaseService implements MCCourseServiceInterface {
    public MCCourseService() {
        super();
    }

    /**
     * 全部课程
     */
    public void getAllCourse(int page, String keyWord, final MCAnalyzeBackBlock resultBack, Context context) {
        MCBaseRequest request = new MCBaseRequest();
        String uid = MCSaveData.getUserInfo(Contants.UID, context).toString();
        if (TextUtils.isEmpty(uid) || uid.equals(Contants.DEFAULT_UID)) {
            request.requestUrl = RequestUrl.getInstance().GET_FIRSTCOUSELIST_URL;
        } else {
            request.requestUrl = RequestUrl.getInstance().GET_COUSELIST_URL;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("page.searchItem.type", "0");
        map.put("page.searchItem.roleType", "0");
        map.put("page.pageSize", Const.PAGESIZE + "");
        map.put("page.searchItem.siteCode", Const.SITECODE);
        map.put("page.searchItem.queryType", "0");
        map.put("page.curPage", page + "");
        map.put("page.searchItem.queryDate", "1");
        map.put("page.searchItem.courseName", keyWord);
        request.requestParams = this.PreprocessParams(map, context);
        request.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                MCCourseService.this.analyzeDataWithResult(result, responeData, MCCourseModel.class, resultBack);
            }
        };
        MCNetwork.post(request, context);
    }

    /**
     * 我的课程
     */
    public void getMyCourse(String uid, int page, final MCAnalyzeBackBlock resultBack, Context context) {
        MCBaseRequest request = new MCBaseRequest();
        request.requestUrl = RequestUrl.getInstance().GET_COUSELIST_URL;
        Map<String, String> map = new HashMap<String, String>();
        map.put("page.searchItem.type", "1");
        map.put("page.searchItem.roleType", "0");
        map.put("page.pageSize", Const.PAGESIZE + "");
        map.put("page.searchItem.siteCode", Const.SITECODE);
        map.put("page.searchItem.queryType", "0");
        map.put("page.curPage", page + "");
        map.put("page.searchItem.queryDate", "1");
        System.out.println("[!] startIndex" + Const.PAGESIZE * (page - 1) + " count" + Const.PAGESIZE);
        request.requestParams = this.PreprocessParams(map, context);
        request.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                MCCourseService.this.analyzeDataWithResult(result, responeData, MCCourseModel.class, resultBack);
            }
        };
        MCNetwork.post(request, context);
    }

    /**
     * 根据sectionid查找资源
     */
    public void getChapterByCourseId2(String courseId, String uid, final MCAnalyzeBackBlock resultBack, Context context) {
        MCBaseRequest v1 = new MCBaseRequest();
        v1.requestUrl = RequestUrl.getInstance().GET_DIRECTORIES_URL;
        Map<String, String> map = new HashMap<String, String>();
        map.put("page.searchItem.siteCode", Const.SITECODE);
        map.put("page.searchItem.opencourseId", courseId);
        v1.requestParams = this.PreprocessParams(map, context);
        v1.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                if (responeData != null && !responeData.equals("")) {
                    responeData = responeData.replace("\"result\"", "\"data\"");
                    MCLog.d(TAG, "getChapterByCourseId responeData:" + responeData);
                    MCCourseService.this.analyzeChapterWithResult(result, responeData, MCChapterModel.class, resultBack);
                }
            }
        };
        MCNetwork.post(v1, context);
    }

    public void getAutoSearchCourse(String uid, String keyword, final MCAnalyzeBackBlock resultBack, Context context) {
        MCBaseRequest baseRequest = new MCBaseRequest();
        baseRequest.requestUrl = "getcoursekeyword";
        HashMap hashMap = new HashMap();
        ((Map) hashMap).put("uid", new StringBuilder(String.valueOf(uid)).toString());
        ((Map) hashMap).put("keyword", keyword);
        baseRequest.requestParams = this.PreprocessParams(((Map) hashMap), context);
        baseRequest.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                MCCourseService.this.analyzeDataWithResult(result, responeData, MCCourseModel.class, resultBack);
            }
        };
        MCNetwork.post(baseRequest, context);
    }

    public void getChapterByCourseId(String courseId, String uid, final MCAnalyzeBackBlock resultBack, Context context) {
        MCBaseRequest baseRequest = new MCBaseRequest();
        baseRequest.requestUrl = RequestUrl.getInstance().GET_DIRECTORIES_URL;
        Map<String, String> map = new HashMap<String, String>();
        map.put("page.searchItem.siteCode", Const.SITECODE);
        map.put("page.searchItem.opencourseId", courseId);
        baseRequest.requestParams = this.PreprocessParams(map, context);
        baseRequest.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                if (responeData != null && !responeData.equals("")) {
                    responeData = responeData.replace("\"result\"", "\"data\"");
                    MCLog.d(TAG, "getChapterByCourseId responeData:" + responeData);
                    MCCourseService.this.analyzeChapterWithResult(result, responeData, MCChapterModel.class, resultBack);
                }
            }
        };
        MCNetwork.post(baseRequest, context);
    }

    /**
     * 获取课程详情
     */
    public void getCourseDetailByCourseId(String uid, String courseId, final MCAnalyzeBackBlock resultBack, Context context) {
        MCBaseRequest request = new MCBaseRequest();
        if (TextUtils.isEmpty(uid)) {
            request.requestUrl = RequestUrl.getInstance().COURSE_FIRSTDETAIL_URL;//首页的课程详情
        } else {
            request.requestUrl = RequestUrl.getInstance().COURSE_DETAIL_URL;
        }
        /**
         * page.searchItem.courseId=4028ac1a5310e7fa01531243d9630001&
         * page.searchItem.siteCode=localhost
         */
        Map<String, String> map = new HashMap<String, String>();
        //        map.put("page.searchItem.courseId", courseId);
        map.put("params.courseId", courseId);
//        map.put("page.searchItem.siteCode",Const.SITECODE);
        request.requestParams = this.PreprocessParams(map, context);
        request.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                MCLog.d(TAG, "getCourseDetailByCourseId responeData:" + responeData);
                MCCourseService.this.analyzeDataWithResult(result, responeData, MCCourseDetailModel.class, resultBack);
            }
        };
        MCNetwork.post(request, context);
    }

    /**
     * 新添加的学生选课功能
     * String courseId  课程id
     * final MCAnalyzeBackBlock resultBack 回调函数
     * Context context  进程上下文
     */
    @Override
    public void choiceCourse(String courseId, final MCAnalyzeBackBlock resultBack, Context context) {
        MCBaseRequest request = new MCBaseRequest();
        request.requestUrl = RequestUrl.getInstance().CHOICE_COURSE_URL;//改成选课链接
        Map<String, String> map = new HashMap<String, String>();
        map.put("entity.courseId", courseId);
        request.requestParams = this.PreprocessParams(map, context);
        request.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                MCLog.d(TAG, "focusCourse responeData:" + responeData);
                MCCourseService.this.analyzeDataWithResult(result, responeData, MCProgrammeModel.class, resultBack);
            }
        };
        MCNetwork.post(request, context);
    }

    /**
     * 查找某课程下所有的下载资料
     *
     * @param resourceId
     * @param mcAnalyzeBackBlock
     * @param context
     */
    @Override
    public void getDownloadResouce(String resourceId, final MCAnalyzeBackBlock mcAnalyzeBackBlock, Context context) {
        MCBaseRequest request = new MCBaseRequest();
        request.requestUrl = RequestUrl.getInstance().GET_RESOURCE_URL;
        Map<String, String> map = new HashMap<String, String>();
        map.put("page.searchItem.siteCode", Const.SITECODE);
        map.put("page.searchItem.resourceId", resourceId);
        request.requestParams = this.PreprocessParams(map, context);
        request.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                MCCourseService.this.analyzeDownloadResResult(result, responeData, MCResourceModel.class, mcAnalyzeBackBlock);
            }
        };
        MCNetwork.post(request, context);
    }

    @Override
    public void getResouce(String resourceId, final MCAnalyzeBackBlock mcAnalyzeBackBlock, Context context) {
        MCBaseRequest request = new MCBaseRequest();
        request.requestUrl = RequestUrl.getInstance().GET_RESOURCE_URL;
        Map<String, String> map = new HashMap<String, String>();
        map.put("page.searchItem.siteCode", Const.SITECODE);//0是全部课程，1是我的课程
        map.put("page.searchItem.resourceId", resourceId);
        //        map.put("loginType", Const.PAGESIZE * (page-1) + "");
        request.requestParams = this.PreprocessParams(map, context);
        request.listener = new MCNetworkBackListener() {
            public void onNetworkBackListener(MCCommonResult result, String responeData) {
                MCCourseService.this.analyzeDataWithResult(result, responeData, MCResourceModel.class, mcAnalyzeBackBlock);
            }
        };
        MCNetwork.post(request, context);
    }

    @Override
    public void getCourseType(String uid, MCAnalyzeBackMapBlock mcAnalyzeBackMapBlock, Context context) {
    }

    /**
     * 获取三分屏的节点信息
     */
    public void getSFPSectionsByPath(MCSectionModel section, final MCAnalyzeBackBlock resultBack, Context context) {
        final String savePath = CommonUtils.basePath + section.getId() + "/nodeList.xml";
        final String link = section.getMediaUrl();
        // 验证本地文件是否存在
        File file = new File(savePath);
        final List<MCXmlSectionModel> modelList = new ArrayList<MCXmlSectionModel>();
        if (file.exists()) {
            // 解析本地处理好的文件
            System.out.println("already exit");
            try {
                ParseXMLUtil.getNodeListFromLocal(file, modelList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 如果从本地已经获取到了就直接返回了，不再从网络请求。
        if (modelList.size() != 0) {
            MCServiceResult statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, null);
            resultBack.OnAnalyzeBackBlock(statusResult, modelList);
            return;
        }
        // 从网络请求解析
        MCAsyncTask asyncTask = new MCAsyncTask(new MCAsyncTaskInterface() {
            @Override
            public String DoInBackground(MCAsyncTask task) {
                try {
                    ParseXMLUtil.parseDoubleMp4(link, modelList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    ParseXMLUtil.writeToXml(savePath, modelList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }

            @Override
            public void DoAfterExecute(String result) {
                MCServiceResult statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, null);
                resultBack.OnAnalyzeBackBlock(statusResult, modelList);
            }

            @Override
            public void onProgressUpdate(Integer values) {
            }
        });
        asyncTask.execute(1000);
    }

    /**
     * 获取三分屏的节点信息，  同步方式获取
     */
    private List<MCXmlSectionModel> getSFPSectionsByPathSynchronous(MCSectionModel section, Context context) {
        final String savePath = CommonUtils.basePath + section.getId() + "/nodeList.xml";
        final String link = section.getMediaUrl();
        // 验证本地文件是否存在
        File file = new File(savePath);
        List<MCXmlSectionModel> modelList = new ArrayList<MCXmlSectionModel>();
        if (file.exists()) {
            // 解析本地处理好的文件
            System.out.println("already exit");
            try {
                ParseXMLUtil.getNodeListFromLocal(file, modelList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            ParseXMLUtil.parseDoubleMp4(link, modelList);
            ParseXMLUtil.writeToXml(savePath, modelList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelList;
    }

    /**
     * 将选择的添加到下载队列
     *
     * @param resultBack
     * @param mContext
     */
    public void addCheckSFPToDownloadQueue(final MCCourseModel mCourse, final List<MCChapterAndSectionModel> checkedSections, final List<MCChapterAndSectionModel> checkedSFPSections, final MCAnalyzeBackBlock resultBack, final Context mContext) {
        // 从网络请求解析
        MCAsyncTask asyncTask = new MCAsyncTask(new MCAsyncTaskInterface() {
            @Override
            public String DoInBackground(MCAsyncTask task) {
                int count = 0;
                if (checkedSections != null && checkedSections.size() > 0) {
                    count = addCheckToDownloadQueue(mCourse, checkedSections, mContext);
                }
                final long startTime = System.currentTimeMillis();
                int i = 0;
                if (checkedSFPSections != null && checkedSFPSections.size() > 0) {
                    for (final MCChapterAndSectionModel model : checkedSFPSections) {
                        if (MCDownloadQueue.mDownloadTasks.containsKey(model.getSection().getId() + Contants.SFP_SUFFIX)) {
                            continue;
                        }
                        List<MCXmlSectionModel> modelList = getSFPSectionsByPathSynchronous(model.getSection(), mContext);
                        MCDownloadNodeType downloadType = model.getDownloadType();
                        //遍历一下， 一些可能需要从网盘获取下载路径
                        long totalSize = 0;// 三分屏资源的总大小
                        List nodeList = new ArrayList<MCDownloadVideoNode>();
                        int j = 0;
                        long totalCourseFileSize = 0;
                        for (MCXmlSectionModel section : modelList) {
                            if (section.isParent()) {
                                String screenUrl = section.getScreenUrl();
                                if (screenUrl != null && screenUrl.endsWith(".json")) {
                                    section.setScreenUrl(getVideoMp4(screenUrl, mContext));
                                }
                                String videoUrl = section.getVideoUrl();
                                if (videoUrl != null && videoUrl.endsWith(".json")) {
                                    section.setVideoUrl(getVideoMp4(videoUrl, mContext));
                                }
                            }
                            long totalFileSize = MCDownloadHelper.startDownLoad(mContext, section, model.getSection(), mCourse, downloadType, nodeList);
                            if (totalFileSize == 0) {
                                break;
                            } else {
                                totalCourseFileSize += totalFileSize;
                            }
                            j++;
                        }
                        if (j == modelList.size()) {
                            totalSize += totalCourseFileSize;
                        } else {
                            continue;
                        }
                        MCDownloadHelper.insertMainInfo(mContext, model.getSection(), mCourse, totalSize, downloadType, nodeList);
                        if (model.getSection() != null && (model.getSection().getType() == MCBaseDefine.MCMediaType.MC_SCORM_TYPE)) {
                            //MCScormStuScoHelper.insertScormStuSco(mContext, model.getSection(), mCourse);
                        }
                        i++;
                    }
                }
                MCLog.d(TAG, "添加三分屏下载成功，耗时：" + (System.currentTimeMillis() - startTime));
                // 更新下载队列
                MCDownloadQueue.getInstance().initQueue();
                String result = "";
                if (checkedSections.size() - count > 0) {
                    result += (checkedSections.size() - count) + "个视频";
                    if (i > 0) {
                        result += ",";
                    }
                }
                if (i > 0) {
                    result += i + "个三分屏";
                }
                result += "成功添加到下载队列！";
                if (count > 0 || (checkedSFPSections.size() - i > 0)) {
                    result += (count + (checkedSFPSections.size() - i)) + "个已添加过或者请求资源失败";
                }
                return result;
            }

            @Override
            public void DoAfterExecute(String result) {
                MCServiceResult statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, result);
                resultBack.OnAnalyzeBackBlock(statusResult, null);
            }

            @Override
            public void onProgressUpdate(Integer values) {
            }
        });
        asyncTask.execute(500);
    }

    @Override
    public void getSFPSectionsByPathLocal(String courseId, final String sectionId, final MCAnalyzeBackBlock resultBack, Context context) {
        final String savePath = CommonUtils.basePath + sectionId + "/nodeList.xml";
        // 验证本地文件是否存在
        File file = new File(savePath);
        final List<MCXmlSectionModel> modelList = new ArrayList<MCXmlSectionModel>();
        if (file.exists()) {
            // 解析本地处理好的文件
            try {
                ParseXMLUtil.getNodeListFromLocal(file, modelList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 如果从本地已经获取到了就直接返回了，不再从网络请求。
        if (modelList.size() != 0) {
            MCDownloadService mDownloadService = new MCDownloadService();
            mDownloadService.getAllDownloadSectionByCourseId(MCDownloadNodeType.MC_SFP_TYPE, courseId, new MCAnalyzeBackBlock() {
                @Override
                public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
                    List<MCDownloadVideoNode> list = resultList;
                    Map<String, MCDownloadVideoNode> map = new HashMap<String, MCDownloadVideoNode>();
                    for (MCDownloadVideoNode node : list) {
                        map.put(node.getSectionId(), node);
                    }
                    String filePath = Contants.VIDEO_PATH + "/" + sectionId + "/";
                    for (MCXmlSectionModel mcXmlSectionModel : modelList) {
                        mcXmlSectionModel.setLocal(true);
                        if (mcXmlSectionModel.isParent()) {
                            mcXmlSectionModel.setScreenUrl(filePath + map.get(mcXmlSectionModel.getId() + "ppt").filename);
                            mcXmlSectionModel.setVideoUrl(filePath + map.get(mcXmlSectionModel.getId() + "video").filename);
                        } else {
                            if (map.get(mcXmlSectionModel.getId()) != null) {
                                mcXmlSectionModel.setThumbImage(filePath + map.get(mcXmlSectionModel.getId()).filename);
                            }
                        }
                    }
                    MCServiceResult statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, null);
                    resultBack.OnAnalyzeBackBlock(statusResult, modelList);
                }
            }, context);
            return;
        }
    }

    @Override
    public void getSFPSectionsByMobileXml(final String url, final MCAnalyzeBackBlock resultBack) {
        final List<MCXmlSectionModel> modelList = new ArrayList<MCXmlSectionModel>();
        final String remotePath = url.replace("mobile.xml", "");
        // 从网络请求解析
        MCAsyncTask asyncTask = new MCAsyncTask(new MCAsyncTaskInterface() {
            @Override
            public String DoInBackground(MCAsyncTask task) {
                String path = CommonUtils.downloadFile(url, Contants.VIDEO_PATH + "/");
                // 验证本地文件是否存在
                File file = new File(path);
                if (file.exists()) {
                    // 解析本地处理好的文件
                    try {
                        ParseXMLUtil.getNodeListFromLocal(file, modelList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return modelList.size() + "";
            }

            @Override
            public void DoAfterExecute(String result) {
                MCServiceResult statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, result);
                for (MCXmlSectionModel model : modelList) {
                    if (!TextUtils.isEmpty(model.getVideoUrl())) {
                        model.setVideoUrl(remotePath + model.getVideoUrl());
                    }
                    if (!TextUtils.isEmpty(model.getScreenUrl())) {
                        model.setScreenUrl(remotePath + model.getScreenUrl());
                    }
                    if (!TextUtils.isEmpty(model.getThumbImage())) {
                        model.setScreenUrl(remotePath + model.getThumbImage());
                    }
                }
                resultBack.OnAnalyzeBackBlock(statusResult, modelList);
            }

            @Override
            public void onProgressUpdate(Integer values) {
            }
        });
        asyncTask.execute();
    }

    @Override
    public void initSFPDownloadInfo(final List<MCXmlSectionModel> modelList, final MCSectionModel section, final MCCourseModel course, final MCDownloadNodeType downloadType, final MCAnalyzeBackBlock resultBack, final Context mContext) {
        // 从网络请求解析
        MCAsyncTask asyncTask = new MCAsyncTask(new MCAsyncTaskInterface() {
            @Override
            public String DoInBackground(MCAsyncTask task) {
                long totalSize = 0;//三分屏资源的总大小
                List nodeList = new ArrayList<MCDownloadVideoNode>();
                for (MCXmlSectionModel model : modelList) {
                    totalSize += MCDownloadHelper.startDownLoad(mContext, model, section, course, downloadType, nodeList);
                }
                MCDownloadHelper.insertMainInfo(mContext, section, course, totalSize, downloadType, nodeList);
                MCDownloadQueue.getInstance().initQueue();
                return null;
            }

            @Override
            public void DoAfterExecute(String result) {
                MCServiceResult statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, null);
                resultBack.OnAnalyzeBackBlock(statusResult, null);
            }

            @Override
            public void onProgressUpdate(Integer values) {
            }
        });
        asyncTask.execute(1000);
    }

    /**
     * 将选择的添加到下载队列
     * @param mContext
     */
    private int addCheckToDownloadQueue(final MCCourseModel mCourse, List<MCChapterAndSectionModel> checkedSections, final Context mContext) {
        int count = 0;
        Iterator<MCChapterAndSectionModel> iterator = checkedSections.iterator();
        while (iterator.hasNext()) {
            MCChapterAndSectionModel innerModel = iterator.next();
            final MCSectionModel optSection = innerModel.getSection();
            String videoPath = optSection.getMediaUrl();
            if (videoPath != null && videoPath.endsWith(".mp4")) {
                MCLog.d(TAG, optSection.getName() + " 原始路径可用");
                MCDownloadHelper.addToDownLoadQueue(mCourse, optSection, videoPath, mContext);
            } else if (videoPath.endsWith(".json")) {
                String path = getVideoMp4(videoPath, mContext);
                if (!TextUtils.isEmpty(path)) {
                    MCDownloadHelper.addToDownLoadQueue(mCourse, optSection, path, mContext);
                } else {
                    count++;
                }
            } else {
                count++;
                MCLog.d(TAG, optSection.getName() + " 原始路径不可用又没在网盘转码……");
            }
        }
        return count;
    }

    /**
     * 将选择的添加到下载队列
     *
     * @param resultBack
     * @param mContext
     */
    public void addCheckToDownloadQueue(final MCCourseModel mCourse, final List<MCChapterAndSectionModel> checkedSections, final MCAnalyzeBackBlock resultBack, final Context mContext) {
        // 从网络请求解析
        MCAsyncTask asyncTask = new MCAsyncTask(new MCAsyncTaskInterface() {
            @Override
            public String DoInBackground(MCAsyncTask task) {
                int count = addCheckToDownloadQueue(mCourse, checkedSections, mContext);
                //更新下载队列
                MCDownloadQueue.getInstance().notifyChanged();
                String msg = null;
                if (count > 0) {
                    msg = count + "个视频的下载资源不存在，其他" + (checkedSections.size() - count) + "个视频成功添加到下载队列！";
                } else {
                    msg = checkedSections.size() + "个视频成功添加到下载队列！";
                }
                return msg;
            }

            @Override
            public void DoAfterExecute(String result) {
                MCServiceResult statusResult = MCServiceResult.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS, result);
                resultBack.OnAnalyzeBackBlock(statusResult, null);
            }

            @Override
            public void onProgressUpdate(Integer values) {
            }
        });
        asyncTask.execute(500);
    }

    /**
     * 通过视频转码后的地址获得原地址
     * 如：通过  /videoForSeg/yunPan/fjsd/tch002/%E5%BC%80%E6%94%BE%E8%AF%BE%E7%A8%8B%E6%96%87%E4%BB%B6/20150317/%E4%B8%AD%E5%9B%BD%E7%8E%B0%E5%BD%93%E4%BB%A3%E6%95%A3%E6%96%87%E7%A0%94%E7%A9%B6-%E5%AE%A3%E4%BC%A0%E7%89%87.mp4.VIDEOSEGMENTS/meta.json
     * 获得   http://stream.10.tel.webtrn.cn/videoForSeg/yunPan/fjsd/tch002/%E5%BC%80%E6%94%BE%E8%AF%BE%E7%A8%8B%E6%96%87%E4%BB%B6/20150317/%E4%B8%AD%E5%9B%BD%E7%8E%B0%E5%BD%93%E4%BB%A3%E6%95%A3%E6%96%87%E7%A0%94%E7%A9%B6-%E5%AE%A3%E4%BC%A0%E7%89%87.mp4.VIDEOSEGMENTS/11139a75022eb529bda27a18d18a0f9b.mp4
     *
     * @param jsonPath
     * @param context
     * @return
     */
    private String getVideoMp4(String jsonPath, Context context) {
        MCBaseRequest request = new MCBaseRequest();
        String streamServers = "http://3.searchvideo.webtrn.cn/search_servers?path=";
        if (jsonPath.startsWith("/")) {
            jsonPath = jsonPath.substring(1);
        }
        if (jsonPath != null && jsonPath.contains("%25")) {
            jsonPath = jsonPath.replaceAll("%25", "%");
        }
        final String videoPath = jsonPath;
        request.requestUrl = streamServers + videoPath;
        MCLog.d(TAG, "searchvideo request.requestUrl：" + request.requestUrl);
        try {
            // 阻塞，进行第一次请求
            String responeData = MCNetwork.getWithSynchronous(request, context);
            JSONObject jsonObject = new JSONObject(responeData);
            JSONArray jsonArray = jsonObject.getJSONArray("servers");
            String path = jsonObject.get("path") == null ? "" : jsonObject.get("path").toString();
            for (int j = 0; j < jsonArray.length(); j++) {
                String lastService = (String) jsonArray.get(jsonArray.length() - 1 - j);
                final String metaJsonUrl = "http://stream." + lastService + "/" + path;
                request.requestUrl = metaJsonUrl;
                MCLog.d(TAG, "metaJsonUrl request.requestUrl：" + request.requestUrl);
                // 阻塞，进行第二次请求
                responeData = MCNetwork.getWithSynchronous(request, context);
                if (TextUtils.isEmpty(responeData)) {
                    continue;
                }
                jsonObject = new JSONObject(responeData);
                JSONObject levels = new JSONObject(jsonObject.optString("levels"));
                String[] array = new String[]{"360p", "270p", "ori", "op"};
                for (String type : array) {
                    if (!levels.has(type)) {
                        continue;
                    }
                    JSONObject obj = new JSONObject(levels.optString(type));
                    if (obj != null) {
                        String mp4Name = obj.optString("mp4");
                        if (!TextUtils.isEmpty(mp4Name)) {
                            String mp4Path = metaJsonUrl.substring(0, metaJsonUrl.lastIndexOf("/") + 1) + mp4Name;
                            MCLog.d(TAG, "[" + j + "]" + "  获得的mp4下载地址为：" + mp4Path);
                            if (!TextUtils.isEmpty(mp4Name) && mp4Name.endsWith(".mp4")) {
                                return mp4Path;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        MCLog.d(TAG, "未获得到mp4下载地址：" + jsonPath);
        return "";
    }
}

