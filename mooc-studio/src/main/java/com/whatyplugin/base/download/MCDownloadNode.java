package com.whatyplugin.base.download;
import com.whatyplugin.base.define.MCBaseDefine.MCDownloadNodeType;

import java.io.Serializable;
import java.util.List;
public abstract class MCDownloadNode implements Serializable {
    public int downloadProgress;
    public long downloadSize;
    public String downloadUrl;
    public long fileSize;
    public String filename;
    private String sectionId;
    private String sectionName;
    public boolean isDownloading;
    public boolean isChecked;
    public MCDownloadNodeType nodeType;
    public MCDownloadNode parentNode;
    public List<MCDownloadNode> childNodeList;
    public boolean isMultipleSection = false;

    public MCDownloadNode() {
        super();
    }

    public abstract void cancel();

    public abstract String getFileFullPath();

    public abstract void deleteFileFromLocal();

    public abstract long download(MCDownloadTask task) throws Exception;

    public abstract void updateDownloadSizeInDB();

    public int getDownloadProgress() {
        if (fileSize == 0) {
            return 0;
        } else {
            return (int) (this.downloadSize * 100 / this.fileSize);
        }
    }

    /**
     * 根据大小判断是否下载完成
     *
     * @return
     */
    public boolean isDownloadOver() {
        if (!isMultipleSection) {
            return this.getDownloadSize() != 0 && this.getFileSize() == this.getDownloadSize();
        } else {
            if (childNodeList != null) {
                //子节点中有一个没完成就是没完成
                for (MCDownloadNode node : childNodeList) {
                    if (!node.isDownloadOver()) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public MCDownloadNodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(MCDownloadNodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public long getDownloadSize() {
        return this.downloadSize;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public String getFilename() {
        return this.filename;
    }
}

