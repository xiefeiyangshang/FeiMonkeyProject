package com.whatyplugin.imooc.logic.upgrade;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Environment;
import android.os.Handler;

import com.whatyplugin.imooc.logic.contants.Contants;

public class MCDownloadFile extends Thread {
    public interface ProgressChangeListener {
        void initView(int arg1);

        void onProgressChange(int arg1, int arg2,String filePath);
        
        void onProgressEnd(String filePath);
    }

    private static final int CONNECT_TIMEOUT = 30000;
    protected static final int DOWNLOAD_BEGIN = 0;
    protected static final int DOWNLOAD_CANCEL = 2;
    protected static final int DOWNLOAD_ERROR = 3;
    protected static final int DOWNLOAD_OVER = 1;
    private String apkName;
    private String downloadUrl;
    private Handler handler;
    private boolean isCancel;
    private boolean isStart;
    private ProgressChangeListener listener;

    public MCDownloadFile(String downloadUrl, String apkName, Handler handler) {
        super();
        this.isCancel = false;
        this.isStart = false;
        this.downloadUrl = downloadUrl;
        this.apkName = apkName;
        this.handler = handler;
    }

    public void cancel() {
        this.isCancel = true;
        this.isStart = false;
    }

    protected boolean isStart() {
        return this.isStart;
    }

    public void run() {
    	FileOutputStream fos = null;
        URL url;
        if(this.isStart) {
            return;
        }

        this.isStart = true;
        FileOutputStream outPutStream = null;
        InputStream ins = null;
        try {
            if(Environment.getExternalStorageState().equals("mounted")) {
                File file = new File(Contants.APK_PATH);
                if(file.exists()) {
                    file.delete();
                }

                file.mkdir();
                String filepath = file.getAbsolutePath() + File.separator + this.apkName;
                String filepathTmp = file.getAbsolutePath() + File.separator + this.apkName + ".tmp";
                url = new URL(this.downloadUrl);
                fos = new FileOutputStream(new File(file, this.apkName + ".tmp"));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Accept-Encoding", "identity");
                con.setConnectTimeout(30000);
                con.connect();
                
                final int BUFFER_SIZE = 8192;
                
                if(con.getResponseCode() == 200) {
                	int contentLength = ((HttpURLConnection)con).getContentLength();
                	if(contentLength <= 0) {
                		contentLength = 8477790;
                	}
                    ins = con.getInputStream();

                    int totalLength = 0;
                    byte[] arr = new byte[BUFFER_SIZE];
                    if(this.listener != null) {
                        this.listener.initView(contentLength);
                    }
                    int length = 0;
					while ((length = ins.read(arr, 0, BUFFER_SIZE)) != -1) {
						fos.write(arr, 0, length);
						totalLength += length;
						if (this.listener != null) {
							this.listener.onProgressChange(contentLength, totalLength, filepath);
						}
						if (this.isCancel) {
							break;
						}
					}
                    //如果取消的话删除残余文件
                    File newFile = new File(filepath);
                    if(newFile.exists()){
                    	newFile.delete();
                    }
                  
					if (this.isCancel) {
						this.isCancel = false;
						if (this.handler != null)
							this.handler.sendEmptyMessage(2);
					} else {
						File oldFile = new File(filepathTmp);
						if (oldFile.exists()) {
							oldFile.renameTo(newFile);
						}
						outPutStream = fos;
						if (this.listener != null) {
							this.listener.onProgressEnd(filepath);
						}
					}
                } else{
                	//返回 非200 都是认为是错误的 在  MCDownloadView  中使用
                	   this.handler.sendEmptyMessage(4);
                }
            }
            }catch(Exception e) {
            	e.printStackTrace();
                outPutStream = fos;
                this.handler.sendEmptyMessage(3);
            }finally{
            	 if(ins != null) {
                 	try {
						ins.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
                 }
                 if(outPutStream != null) {
                 	 try {
						outPutStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
                 }
            }
        this.isStart = false;
    	
    }

    public void setProgressChangeListener(ProgressChangeListener listener) {
        this.listener = listener;
    }
}

