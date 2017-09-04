package com.whatyplugin.base.download;
import android.accounts.NetworkErrorException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import com.whatyplugin.base.define.MCBaseDefine.MCDownloadNodeType;
import com.whatyplugin.base.define.MCBaseDefine.MCResourcesType;
import com.whatyplugin.base.http.AndroidHttpClient;
import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.network.MCNetwork;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.db.DBCommon.DownloadColumns;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCDownloadService;
import com.whatyplugin.imooc.logic.utils.FileUtils;
import com.whatyplugin.imooc.logic.utils.StringUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.List;
public class MCDownloadVideoNode extends MCDownloadNode {
    private int BUFFER_SIZE = 81920;
    byte[] buffers = new byte[BUFFER_SIZE];
    private static String TAG;
    static {
        MCDownloadVideoNode.TAG = MCDownloadVideoNode.class.getSimpleName();
    }
    public String filePath;
    private int chapter_seq;
    private DefaultHttpClient client;
    private String courseId;
    private String courseImageUrl;
    private String courseName;
    private File file;
    private HttpGet httpGet;
    private Context mContext;
    private ProgressReportingRandomAccessFile mProgressRandomAccessFile;
    private HttpResponse response;
    private int section_seq;
    private String resourceSection;// 资源父节点
    private MCDownloadTask task;
    private MCResourcesType resourcesType;

    public MCDownloadVideoNode(Context context) {
        super();
        this.mContext = context;
    }

    /**
     * 下载方法
     *
     * @param input
     * @param out
     * @return
     * @throws NetworkErrorException
     */
    public long copy(InputStream input, RandomAccessFile out) throws NetworkErrorException {
        this.isDownloading = true;
        if (input != null && out != null) {
            try {
                out.seek(out.length());
            } catch (IOException e) {
                e.printStackTrace();
                MCLog.e(TAG, "下载  [" + this.getSectionName() + "] 出现IO异常！");
            }
            long result = 0;
            int retryTime = 5;
            int count = 0;
            BufferedInputStream inputStream = new BufferedInputStream(input, BUFFER_SIZE);
            result = progressFile(inputStream, out, retryTime, count);
            MCLog.d("4downloadNode", "downloadSize:" + downloadSize);
            this.updateDownloadSizeInDB();
            if (parentNode != null) {//如果有父节点更新父节点大小
                MCLog.d("4downloadNode", "parentNode.downloadSize:" + parentNode.downloadSize);
                parentNode.updateDownloadSizeInDB();
            }
            try {
                out.close();
                this.client.getConnectionManager().shutdown();
                this.client = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
        return -1;
    }

    private long progressFile(BufferedInputStream inputStream, RandomAccessFile out, int retryTime, int count) throws NetworkErrorException {
        MCLog.e(TAG, "下载  [" + this.getSectionName() + "] 中。。。");
        int length = 0;
        int step = 0;
        try {
            while (this.downloadSize < this.fileSize) {
                length = inputStream.read(buffers);
                if (length == -1) {
                    break;
                }
                step = 1;
                if (!MCNetwork.checkedNetwork(this.mContext)) {
                    throw new NetworkErrorException("no network.");
                } else {
                    //this.downloadSize += length;
                    int overSize = (int) (this.downloadSize + length - this.fileSize);
                    if (overSize >= 0) {//防止下载的超过文件本身大小
                        this.downloadSize = this.fileSize;
                        int factSize = length - overSize;//实际大小
                        out.write(buffers, 0, factSize);
                        this.updateDownloadSizeInDB();
                        if (parentNode != null) {
                            parentNode.downloadSize = parentNode.downloadSize + factSize;
                            parentNode.updateDownloadSizeInDB();
                        }
                        break;
                    } else {
                        this.downloadSize += length;
                        step = 2;
                    }
                    if (parentNode != null) {//如果有父节点更新父节点大小
                        parentNode.downloadSize += length;
                    }
                    out.write(buffers, 0, length);
                }
                // 用户终止了下载
                if (this.task.isCancelled()) {
                    break;
                }
                if (count > 5) {
                    this.updateDownloadSizeInDB();
                    if (parentNode != null) {//如果有父节点更新父节点大小
                        parentNode.updateDownloadSizeInDB();
                    }
                    count = 0;
                } else {
                    count++;
                }
                step = 0;
                retryTime=5;//每下载成功一部分，重试次数还原
            }
        } catch (Exception e) {
            MCLog.e(TAG, "下载  [" + this.getSectionName() + "] 出现异常！");
            e.printStackTrace();
            if (retryTime-- > 0 && step != 1) {
                if (step == 2) {
                    this.downloadSize -= length;
                }
                try {
                    this.response = this.client.execute(this.httpGet);
                    inputStream = new BufferedInputStream(this.response.getEntity().getContent(), BUFFER_SIZE);
                    progressFile(inputStream, out, retryTime, count);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return this.downloadSize;
    }

    /**
     * 清除本地已下载的文件
     */
    public void deleteFileFromLocal() {
        if (nodeType == MCDownloadNodeType.MC_SFP_TYPE || nodeType == MCDownloadNodeType.MC_SCORM_TYPE) {
            MCDownloadService service = new MCDownloadService();
            final String sectionId = this.getResourceSection();
            service.getSFPFilenameBySectionId(sectionId, new MCAnalyzeBackBlock() {
                @Override
                public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
                   /* for (String str : (List<String>) resultList) {
                        deleteFileByPathAndName(Contants.VIDEO_PATH + "/" + sectionId, str);
                    }*/
                    File dir = new File(Contants.VIDEO_PATH + "/" + sectionId);
                    FileUtils.deleteAll(dir);//递归删除
                    dir.delete();
                    mContext.getContentResolver().delete(DownloadColumns.CONTENT_URI_DOWNLOADINFO, "resourceSection=" + "'" + sectionId + "'", null);
                }
            }, this.mContext);
        } else {
            this.mContext.getContentResolver().delete(DownloadColumns.CONTENT_URI_DOWNLOADINFO, "sectionId=" + "'" + this.getSectionId() + "'", null);
            FileUtils.deleteFileByPathAndName(Contants.VIDEO_PATH, this.getFilename());
        }
    }



    private String getLocalFileName() {
        String url = this.downloadUrl.substring(this.downloadUrl.lastIndexOf(46));
        if (url.indexOf("?") > 0) {
            url = url.substring(0, url.indexOf("?"));
        }
        return this.getSectionId() + url;
    }

    @Override
    public void updateDownloadSizeInDB() {
        ContentResolver resolver = this.mContext.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("downloadSize", this.getDownloadSize());
        resolver.update(DownloadColumns.CONTENT_URI_DOWNLOADINFO, values, "sectionId=" + "'" + this.getSectionId() + "' and resourceSection= '" + this.getResourceSection() + "' ", null);
    }

    public void updateVideoNodeInDB() {
        ContentResolver content = this.mContext.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("filename", this.getFilename());
        values.put("fileSize", Long.valueOf(this.getFileSize()));
        values.put("downloadSize", Long.valueOf(this.getDownloadSize()));
        content.update(DownloadColumns.CONTENT_URI_DOWNLOADINFO, values, "sectionId=" + "'" + this.getSectionId() + "' and resourceSection= '" + this.getResourceSection() + "' ", null);
    }

    /**
     * 核心的视频下载方法
     */
    @Override
    public long download(MCDownloadTask task) throws Exception {
        this.task = task;
        if (TextUtils.isEmpty(this.downloadUrl)) {
            return 0;
        }
        if (TextUtils.isEmpty(this.filename)) {
            this.filename = this.getLocalFileName();
        }
        String fileFolder = null;
        //不同类型视频保存位置有区别
        if (nodeType == MCDownloadNodeType.MC_SFP_TYPE || nodeType == MCDownloadNodeType.MC_SCORM_TYPE) {
            fileFolder = Contants.VIDEO_PATH + "/" + this.getResourceSection();
        } else {
            fileFolder = Contants.VIDEO_PATH;
        }
        File newFile = new File(fileFolder);
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        this.file = new File(fileFolder, this.filename);
        filePath = this.file.getAbsolutePath();
        MCLog.d(TAG, "" + fileFolder + "downloadUrl：" + this.downloadUrl);
        if (downloadUrl.startsWith("http://stream.") && downloadUrl.contains(".mp4")) {//如果能判断一个url已经encode过最好
            this.fileSize = this.initFileSize(downloadUrl, 5);//云盘文件如果包括中文会encode过，再次encode会找不到
            this.httpGet = new HttpGet(downloadUrl);
        } else {
            this.fileSize = this.initFileSize(StringUtils.encodeUrl(downloadUrl), 5);//初始化文件大小
            this.httpGet = new HttpGet(StringUtils.encodeUrl(downloadUrl));
        }
        MCLog.d(TAG, "首次初始化后文件大小为===：" + this.fileSize);
        // 文件是否存在的处理
        if (this.file.exists()) {
            MCLog.d(MCDownloadVideoNode.TAG, "文件已存在 - 总大小 :" + this.fileSize + " 已下载大小:" + this.file.length());
            this.downloadSize = this.file.length();
            this.httpGet.addHeader("Range", "bytes=" + this.downloadSize + "-");
        } else {
            this.downloadSize = 0L;
            this.file.createNewFile();
        }
        this.updateVideoNodeInDB();//最新信息更新到数据库里
        if (this.client != null) {
            this.client.getConnectionManager().shutdown();
        }
        BasicHttpParams httpParams = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(httpParams, 50); // 设置最大连接数
        ConnManagerParams.setTimeout(httpParams, 60 * 1000);// 设置获取连接的最大等待时间
        ConnPerRouteBean connPerRoute = new ConnPerRouteBean(50);// 设置每个路由最大连接数
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, connPerRoute);
        HttpConnectionParams.setConnectionTimeout(httpParams, 60 * 1000);// 设置连接超时时间
        HttpConnectionParams.setSoTimeout(httpParams, 60 * 1000);// 设置读取超时时间
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager(httpParams, registry);
        this.client = new DefaultHttpClient(connectionManager, httpParams);
        DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(5, true);
        client.setHttpRequestRetryHandler(retryHandler);
        this.response = this.client.execute(this.httpGet);
        this.mProgressRandomAccessFile = new ProgressReportingRandomAccessFile(this, this.file, "rw");
        long count = 0;
        if (this.downloadSize < this.fileSize) {
            count = this.copy(this.response.getEntity().getContent(), this.mProgressRandomAccessFile);
        }
        return count;
    }

    /**
     * 初始化下载文件大小总大小
     */
    public long initFileSize(String downloadUrl, int retryTime) {
        long size = 0;
        AndroidHttpClient client = null;
        try {
            if (downloadUrl == null || !downloadUrl.startsWith("http")) {
                return 0;
            }
            while (size <= 0 && retryTime-- > 0) {
                HttpGet httpGet = new HttpGet(downloadUrl);
                client = AndroidHttpClient.newInstance(MCDownloadVideoNode.TAG);
                HttpResponse response = client.execute(httpGet);
                size = response.getEntity().getContentLength();
            }
        } catch (IOException e) {
            e.printStackTrace();
            initFileSize(downloadUrl, retryTime);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return size;
    }

    /**
     * 判断是否是父节点
     *
     * @return
     */
    public boolean isParent() {
        if ((this.nodeType == MCDownloadNodeType.MC_SFP_TYPE || this.nodeType == MCDownloadNodeType.MC_SCORM_TYPE) && this.getSectionId() != null && this.getSectionId().endsWith(Contants.SFP_SUFFIX)) {
            return true;
        }
        return false;
    }

    public String getFileFullPath() {
        String fileFolder;

        //不同类型视频保存位置有区别
        if (nodeType == MCDownloadNodeType.MC_SFP_TYPE) {
            fileFolder = Contants.VIDEO_PATH + "/" + this.getResourceSection();
        } else {
            fileFolder = Contants.VIDEO_PATH;
        }
        File newFile = new File(fileFolder);
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        return fileFolder + "/" + this.filename;
    }

    @Override
    public void cancel() {
        if (this.client != null) {
            this.client.getConnectionManager().shutdown();
        }
    }

    public void release() {
        if (this.task != null) {
            this.task.cancel(true);
            this.isDownloading = false;
        }
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public DefaultHttpClient getClient() {
        return client;
    }

    public void setClient(DefaultHttpClient client) {
        this.client = client;
    }

    public int getChapter_seq() {
        return chapter_seq;
    }

    public void setChapter_seq(int chapter_seq) {
        this.chapter_seq = chapter_seq;
    }

    public String getCourseImageUrl() {
        return courseImageUrl;
    }

    public void setCourseImageUrl(String courseImageUrl) {
        this.courseImageUrl = courseImageUrl;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getResourceSection() {
        return resourceSection;
    }

    public void setResourceSection(String resourceSection) {
        this.resourceSection = resourceSection;
    }

    public MCResourcesType getResourcesType() {
        return resourcesType;
    }

    public void setResourcesType(MCResourcesType resourcesType) {
        this.resourcesType = resourcesType;
    }

    public int getSection_seq() {
        return section_seq;
    }

    public void setSection_seq(int section_seq) {
        this.section_seq = section_seq;
    }

    public class ProgressReportingRandomAccessFile extends RandomAccessFile {
        private int progress;

        public ProgressReportingRandomAccessFile(MCDownloadVideoNode arg2, File file, String mode) throws FileNotFoundException {
            super(file, mode);
            this.progress = 0;
        }

        public void write(byte[] buffer, int offset, int count) throws IOException {
            super.write(buffer, offset, count);
            this.progress += count;
            MCDownloadVideoNode.this.task.onTaskProgressUpdate(Integer.valueOf(this.progress));
        }
    }
}
