package com.whatyplugin.imooc.logic.utils;

import android.content.Context;
import android.text.format.Formatter;

import com.bokecc.sdk.mobile.download.DownloadListener;
import com.bokecc.sdk.mobile.download.Downloader;
import com.bokecc.sdk.mobile.exception.DreamwinException;
import com.whatyplugin.imooc.logic.model.DownloadInfo;

import java.io.File;

/**
 * 下载downloader包装类
 */

public class DownloaderWrapper {
    Downloader downloader;
    DownloadInfo downloadInfo;

    long lastStart;

    public DownloaderWrapper(final DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;

        lastStart = downloadInfo.getStart();

        File file = MediaUtil.createFile(downloadInfo.getTitle());
        downloader = new Downloader(file, downloadInfo.getVideoId(), ConfigUtil.USERID, ConfigUtil.API_KEY);
        downloader.setDownloadDefinition(downloadInfo.getDefinition());
        downloader.setHttps(false);
        downloader.setDownloadListener(new DownloadListener() {
            @Override
            public void handleProcess(long start, long end, String videoId) {
                downloadInfo.setStart(start).setEnd(end);
            }

            @Override
            public void handleException(DreamwinException exception, int status) {
                downloadInfo.setStatus(status);
            }

            @Override
            public void handleStatus(String videoId, int status) {
                if (status == downloadInfo.getStatus()) {
                	return;
                } else {
                	downloadInfo.setStatus(status);
                	DataSet.updateDownloadInfo(downloadInfo);
                }
            }

            @Override
            public void handleCancel(String videoId) {}
        });

        if (downloadInfo.getStatus() == Downloader.DOWNLOAD) {
            downloader.start();
        }
    }

    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public int getStatus() {
        return downloadInfo.getStatus();
    }

    public String getDownloadProgressText(Context context) {
        String start = Formatter.formatFileSize(context, downloadInfo.getStart());
        String end = Formatter.formatFileSize(context, downloadInfo.getEnd());
        String downloadText = String.format("%s/%s", start, end);
        return downloadText;
    }

    public long getDownloadProgressBarValue() {
        if (downloadInfo.getEnd() == 0) {
            return 0;
        } else {
            return downloadInfo.getStart() * 100 / downloadInfo.getEnd();
        }
    }

    //TODO 待优化
    public String getSpeed(Context context) {
        String speed = Formatter.formatFileSize(context, downloadInfo.getStart() - lastStart) + "/s";
        lastStart = downloadInfo.getStart();
        return speed;
    }

    public void start() {
    	downloadInfo.setStatus(Downloader.DOWNLOAD);
        downloader.start();
    }

    public void resume() {
    	downloadInfo.setStatus(Downloader.DOWNLOAD);
        downloader.resume();
    }

    public void setToWait() {
    	downloadInfo.setStatus(Downloader.WAIT);
        downloader.setToWaitStatus();
    }

    public void pause() {
    	downloadInfo.setStatus(Downloader.PAUSE);
        downloader.pause();
    }

    public void cancel() {
    	downloadInfo.setStatus(Downloader.PAUSE);
        downloader.cancel();
    }
}
