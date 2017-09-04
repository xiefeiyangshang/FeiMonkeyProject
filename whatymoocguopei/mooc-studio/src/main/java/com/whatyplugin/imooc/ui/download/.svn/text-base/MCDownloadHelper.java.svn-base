package com.whatyplugin.imooc.ui.download;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import com.whatyplugin.base.define.MCBaseDefine.MCDownloadNodeType;
import com.whatyplugin.base.download.MCDownloadQueue;
import com.whatyplugin.base.download.MCDownloadTask;
import com.whatyplugin.base.download.MCDownloadVideoNode;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCXmlSectionModel;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.db.DBCommon;
import com.whatyplugin.imooc.logic.model.MCCourseModel;
import com.whatyplugin.imooc.logic.model.MCSectionModel;

import java.util.ArrayList;
import java.util.List;
public class MCDownloadHelper {
    private static ContentValues getDownloadNodeContentAndValue(MCDownloadVideoNode model) {
        ContentValues content = new ContentValues();
        content.put("courseId", model.getCourseId());
        content.put("courseName", model.getCourseName());
        content.put("courseImageUrl", model.getCourseImageUrl());
        content.put("chapter_seq", Integer.valueOf(model.getChapter_seq()));
        content.put("section_seq", Integer.valueOf(model.getSection_seq()));
        content.put("downloadUrl", model.getDownloadUrl());
        content.put("sectionName", model.getSectionName());
        content.put("sectionId", model.getSectionId());
        content.put("resourceSection", model.getResourceSection());
        content.put("type", model.getNodeType().value());
        content.put("fileSize", Long.valueOf(model.getFileSize()));
        return content;
    }

    /**
     * 添加到下载队列
     */
    public static void addToDownLoadQueue(MCCourseModel mCourse, final MCSectionModel optSection, String downloadLink, Context mContext) {
        MCDownloadVideoNode videoNode = new MCDownloadVideoNode(mContext);
        videoNode.setCourseId(mCourse.getId());
        videoNode.setChapter_seq(optSection.getChapterSeq());
        videoNode.setCourseName(mCourse.getName());
        videoNode.setCourseImageUrl(mCourse.getImageUrl());
        videoNode.setSection_seq(optSection.getSeq());
        videoNode.setSectionName(optSection.getName());
        videoNode.setSectionId(optSection.getId());
        videoNode.setResourceSection(optSection.getId());
        videoNode.fileSize = optSection.getDownloadMediaSize();
        videoNode.downloadUrl = downloadLink;
        videoNode.setNodeType(MCDownloadNodeType.MC_VIDEO_TYPE);//设置为离线缓存视频类型
        MCDownloadTask task = new MCDownloadTask(videoNode);
        MCDownloadQueue.getInstance().addTask(task);
        //videoNode.saveInitVideoNodeToDB();
        saveVideoNodeToDB(videoNode, mContext);
    }

    public static void saveVideoNodeToDB(MCDownloadVideoNode node, Context context) {
        if (node == null) {
            return;
        }
        List<MCDownloadVideoNode> nodeList = new ArrayList<MCDownloadVideoNode>();
        nodeList.add(node);
        saveAllVideoNodeToDB(nodeList, context);
    }

    /**
     * 批量保存
     *
     * @param nodeList
     * @param context
     */
    public static void saveAllVideoNodeToDB(List<MCDownloadVideoNode> nodeList, Context context) {
        if (nodeList == null || nodeList.size() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        ContentValues[] cvs = new ContentValues[nodeList.size()];
        for (int i = 0, j = nodeList.size(); i < j; i++) {
            sb.append("'").append(nodeList.get(i).getSectionId()).append("',");
            cvs[i] = getDownloadNodeContentAndValue(nodeList.get(i));
        }
        sb.deleteCharAt(sb.length() - 1);
        ContentResolver resolver = context.getContentResolver();
        String condition="sectionId in (" + sb.toString() + ") and resourceSection= '" + nodeList.get(0).getResourceSection() + "' ";
        resolver.delete(DBCommon.DownloadColumns.CONTENT_URI_DOWNLOADINFO,condition , null);
        resolver.bulkInsert(DBCommon.DownloadColumns.CONTENT_URI_DOWNLOADINFO, cvs);
    }

    /**
     * 将三分屏信息添加到数据库， 同时获取下载内容的总大小
     *
     * @param context
     * @param model
     * @param section
     * @param course
     * @return
     */
    public static long startDownLoad(Context context, MCXmlSectionModel model, MCSectionModel section, MCCourseModel course, MCDownloadNodeType downloadType, List nodeList) {
        long totalFileSize = 0;
        if (model.isParent()) {
            MCDownloadVideoNode videoNode = modelDownloadNodeWithDate(context, model, section, course, downloadType);
            videoNode.downloadUrl = model.getScreenUrl();
            videoNode.setSectionId(model.getId() + "ppt");
            videoNode.setSectionName("[ppt视频] " + model.getTitle());
            long fileSize = videoNode.initFileSize(videoNode.downloadUrl, 5);
            if (fileSize == 0) {
                return fileSize;
            }
            totalFileSize += fileSize;
            MCLog.d("downloadScorm", "video url：" + videoNode.downloadUrl);
            nodeList.add(videoNode);
            //videoNode.saveInitVideoNodeToDB(); // 保存到数据库
            videoNode = modelDownloadNodeWithDate(context, model, section, course, downloadType);
            videoNode.downloadUrl = model.getVideoUrl();
            videoNode.setSectionId(model.getId() + "video");
            videoNode.setSectionName("[讲师视频] " + model.getTitle());
            fileSize = videoNode.initFileSize(videoNode.downloadUrl, 5);
            if (fileSize == 0) {
                return fileSize;
            }
            totalFileSize += fileSize;
            MCLog.d("downloadScorm", "video url：" + videoNode.downloadUrl);
            //videoNode.saveInitVideoNodeToDB(); // 保存到数据库
            nodeList.add(videoNode);
        } else if (!TextUtils.isEmpty(model.getThumbImage())) {
            MCDownloadVideoNode videoNode = modelDownloadNodeWithDate(context, model, section, course, downloadType);
            videoNode.downloadUrl = model.getThumbImage();
            videoNode.setSectionName("[缩略图] " + model.getTitle());
            videoNode.setSectionId(model.getId());
            long fileSize = videoNode.initFileSize(videoNode.downloadUrl, 5);
            if (fileSize == 0) {
                return fileSize;
            }
            totalFileSize += fileSize;
            MCLog.d("downloadScorm", "pic url：" + videoNode.downloadUrl);
            //videoNode.saveInitVideoNodeToDB(); // 保存到数据库
            nodeList.add(videoNode);
        }
        return totalFileSize;
    }

    /**
     * 下载列表中显示的一个条目，实际上整个三分屏的分散文件的整体信息。
     *
     * @param context
     * @param section
     * @param course
     */
    public static void insertMainInfo(Context context, MCSectionModel section, MCCourseModel course, long fileSize, MCDownloadNodeType downloadType, List nodeList) {
        MCDownloadVideoNode videoNode = new MCDownloadVideoNode(context);
        videoNode.setSectionId(section.getId() + Contants.SFP_SUFFIX);
        videoNode.setCourseId(course.getId());
        videoNode.setCourseName(course.getName());
        videoNode.setCourseImageUrl(course.getImageUrl());
        videoNode.setSectionName(section.getName());
        videoNode.fileSize = fileSize;
        videoNode.downloadSize = 0;
        videoNode.setNodeType(downloadType);// 设置为资源类型下载的标记
        videoNode.setResourceSection(section.getId());// 记录是哪个节点下的
        nodeList.add(videoNode);
        MCDownloadHelper.saveAllVideoNodeToDB(nodeList, context);
        //videoNode.saveInitVideoNodeToDB(); // 保存到数据库
    }

    private static MCDownloadVideoNode modelDownloadNodeWithDate(Context context, MCXmlSectionModel model, MCSectionModel section, MCCourseModel course, MCDownloadNodeType downloadType) {
        MCDownloadVideoNode videoNode = new MCDownloadVideoNode(context);
        videoNode.setCourseId(course.getId());
        //videoNode.setCourseName(course.getName());
        //videoNode.setCourseImageUrl(course.getImageUrl());
        videoNode.setSectionName(model.getTitle());
        videoNode.fileSize = 1;
        videoNode.downloadSize = 0;
        videoNode.downloadUrl = model.getScreenUrl();
        videoNode.setNodeType(downloadType);// 设置为资源类型下载的标记
        videoNode.setResourceSection(section.getId());// 记录是哪个节点下的
        return videoNode;
    }
}
