package com.whatyplugin.base.network;


import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.util.ByteArrayBuffer;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public abstract class MCAsyncHttpResponseHandler implements MCResponseHandlerInterface {
    class ResponderHandler extends Handler {
        private final MCAsyncHttpResponseHandler mResponder;

        ResponderHandler(MCAsyncHttpResponseHandler mResponder, Looper looper) {
            super(looper);
            this.mResponder = mResponder;
        }

        public void handleMessage(Message msg) {
            this.mResponder.handleMessage(msg);
        }
    }

    protected static final int BUFFER_SIZE = 4096;
    protected static final int CANCEL_MESSAGE = 6;
    public static final String DEFAULT_CHARSET = "UTF-8";
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int FINISH_MESSAGE = 3;
    private static final String LOG_TAG = "AsyncHttpResponseHandler";
    protected static final int PROGRESS_MESSAGE = 4;
    protected static final int RETRY_MESSAGE = 5;
    protected static final int START_MESSAGE = 2;
    protected static final int SUCCESS_MESSAGE = 0;
    public static final String UTF8_BOM = "�?";
    private Handler handler;
    private Looper looper;
    private Header[] requestHeaders;
    private URI requestURI;
    private String responseCharset;
    private boolean useSynchronousMode;

    public MCAsyncHttpResponseHandler() {
        this(null);
    }

    public MCAsyncHttpResponseHandler(Looper looper) {
        super();
        this.responseCharset = "UTF-8";
        this.requestURI = null;
        this.requestHeaders = null;
        this.looper = null;
        if(looper == null) {
            looper = Looper.myLooper();
        }

        this.looper = looper;
        this.setUseSynchronousMode(false);
    }

    public String getCharset() {
        String charSet = this.responseCharset == null ? "UTF-8" : this.responseCharset;
        return charSet;
    }

    public Header[] getRequestHeaders() {
        return this.requestHeaders;
    }

    public URI getRequestURI() {
        return this.requestURI;
    }

    byte[] getResponseData(HttpEntity entity) throws IOException {
        long v10_2;
        int v7 = 0;
        int v4  = 0;
        byte[] v9 = null;
        int v10;
        ByteArrayBuffer buffer;
        long v12 = 0;
        int v1 = 4096;
        byte[] v8 = null;
        if(entity != null) {
            InputStream inputStream = entity.getContent();
            if(inputStream != null) {
                long length = entity.getContentLength();
                if(length > 2147483647) {
                    throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
                }
                else {
                    if(length > v12) {
                        v1 = ((int)length);
                    }

                    try {
                        buffer = new ByteArrayBuffer(v1);
                        v10 = 4096;
                    }
                    catch(OutOfMemoryError v5) {
                    	System.gc();
                        throw new IOException("File too large to fit into available memory");
                    }

                    try {
                        v9 = new byte[v10];
                        v4 = 0;
                        while(true) {
                        label_17:
                            v7 = inputStream.read(v9);
                            if(v7 != -1 && !Thread.currentThread().isInterrupted()) {
                                break;
                            }

                            MCAsyncHttpClient.silentCloseInputStream(inputStream);
                            MCAsyncHttpClient.endEntityViaReflection(entity);
                            return buffer.toByteArray();
                        }
                    }
                    catch(Throwable v10_1) {
                    	  MCAsyncHttpClient.silentCloseInputStream(inputStream);
                          MCAsyncHttpClient.endEntityViaReflection(entity);
                    }

                    v4 += v7;
                    try {
                        buffer.append(v9, 0, v7);
                        if(length <= v12) {
                            v10_2 = 1;
                        }
                        else {
                        	 v10_2 = length;
                             try {
                                 this.sendProgressMessage(v4, ((int)v10_2));
                             }
                             catch(Throwable v10_1) {
                             	  MCAsyncHttpClient.silentCloseInputStream(inputStream);
                                   MCAsyncHttpClient.endEntityViaReflection(entity);
                             }
                        }

                        this.sendProgressMessage(v4, ((int)v10_2));
                    }
                    catch(Throwable v10_1) {
                    	  MCAsyncHttpClient.silentCloseInputStream(inputStream);
                          MCAsyncHttpClient.endEntityViaReflection(entity);
                    }

                    v10_2 = length;
                    try {
                        this.sendProgressMessage(v4, ((int)v10_2));
                      

                        MCAsyncHttpClient.silentCloseInputStream(inputStream);
                        MCAsyncHttpClient.endEntityViaReflection(entity);
                        return buffer.toByteArray();
                    }
                    catch(Throwable v10_1) {
                    	  MCAsyncHttpClient.silentCloseInputStream(inputStream);
                          MCAsyncHttpClient.endEntityViaReflection(entity);
                    }

                    try {
                        MCAsyncHttpClient.silentCloseInputStream(inputStream);
                        MCAsyncHttpClient.endEntityViaReflection(entity);
                        return buffer.toByteArray();
                    }
                    catch(OutOfMemoryError v5) {
                    	System.gc();
                        throw new IOException("File too large to fit into available memory");
                    }

                }
            }
        }

        return v8;
    }

    public boolean getUseSynchronousMode() {
        return this.useSynchronousMode;
    }

    protected void handleMessage(Message message) {
        int v8 = 3;
        int v7 = 2;
        Object[] datas = (Object[]) message.obj;
        switch(message.what) {
            case 0: {
            	
                 if(datas != null && datas.length >= v8) {
                	 //(int arg1, Header[] arg2, byte[] arg3, Throwable arg4);
                     this.onSuccess(Integer.valueOf(datas[0].toString()), (Header[])datas[1], (byte[] )datas[v7]);
                     return;
                 }

                 Log.e("AsyncHttpResponseHandler", "SUCCESS_MESSAGE didn\'t got enough params");
                 return;
            }
            case 1: {
            	  datas = (Object[]) message.obj;
                  if(datas != null && datas.length >= 4) {
                      this.onFailure(Integer.valueOf(datas[0].toString()), (Header[])datas[1], (byte[] )datas[v7], (Throwable)datas[v8]);
                      return;
                  }

                  Log.e("AsyncHttpResponseHandler", "FAILURE_MESSAGE didn\'t got enough params");
                  return;
            }
            case 2: {
            	  this.onStart();
                  return;
            }
            case 3: {
            	 this.onFinish();
                 return;
            }
            case 4: {
                 if(datas != null && datas.length >= v7) {
                     try {
                         this.onProgress(Integer.valueOf(datas[0].toString()), Integer.valueOf(datas[1].toString()));
                     }
                     catch(Throwable v1) {
                         Log.e("AsyncHttpResponseHandler", "custom onProgress contains an error", v1);
                     }
                 }
                 else {
                     Log.e("AsyncHttpResponseHandler", "PROGRESS_MESSAGE didn\'t got enough params");
                     return;
                 }
            }
            case 5: {
                 if(datas != null && datas.length == 1) {
                     this.onRetry(Integer.valueOf(datas[0].toString()));
                     return;
                 }

                 Log.e("AsyncHttpResponseHandler", "RETRY_MESSAGE didn\'t get enough params");
                 return;
            }
            case 6: {
            	 this.onCancel();
            	 return;
            }
        }
        return;
    }

    protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
        return Message.obtain(this.handler, responseMessageId, responseMessageData);
    }

    public void onCancel() {
        Log.d("AsyncHttpResponseHandler", "Request got cancelled");
    }

    public abstract void onFailure(int arg1, Header[] arg2, byte[] arg3, Throwable arg4);

    public void onFinish() {
    }

    public void onPostProcessResponse(MCResponseHandlerInterface instance, HttpResponse response) {
    }

    public void onPreProcessResponse(MCResponseHandlerInterface instance, HttpResponse response) {
    }

    public void onProgress(int bytesWritten, int totalSize) {
        String asyncHttpResponseHandler = "AsyncHttpResponseHandler";
        String progress = "Progress %d from %d (%2.0f%%)";
        Object[] v4 = new Object[3];
        v4[0] = Integer.valueOf(bytesWritten);
        v4[1] = Integer.valueOf(totalSize);
        int v5 = 2;
        double percent = totalSize > 0 ? (((double)bytesWritten)) * 1 / (((double)totalSize)) * 100 : -1;
        v4[v5] = Double.valueOf(percent);
        Log.v(asyncHttpResponseHandler, String.format(progress, v4));
    }

    public void onRetry(int retryNo) {
        Log.d("AsyncHttpResponseHandler", String.format("Request retry no. %d", Integer.valueOf(retryNo)));
    }

    public void onStart() {
    }

    public abstract void onSuccess(int arg1, Header[] arg2, byte[] arg3);

    protected void postRunnable(Runnable runnable) {
        if(runnable != null) {
            if(!this.getUseSynchronousMode() && this.handler != null) {
                this.handler.post(runnable);
                return;
            }

            runnable.run();
        }
    }

    public final void sendCancelMessage() {
        this.sendMessage(this.obtainMessage(6, null));
    }

    public final void sendFailureMessage(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
        this.sendMessage(this.obtainMessage(1, new Object[]{Integer.valueOf(statusCode), headers, responseBody, throwable}));
    }

    public final void sendFinishMessage() {
        this.sendMessage(this.obtainMessage(3, null));
    }

    protected void sendMessage(Message msg) {
        if((this.getUseSynchronousMode()) || this.handler == null) {
            this.handleMessage(msg);
        }
        else if(!Thread.currentThread().isInterrupted()) {
            this.handler.sendMessage(msg);
        }
    }

    public final void sendProgressMessage(int bytesWritten, int bytesTotal) {
        this.sendMessage(this.obtainMessage(4, new Object[]{Integer.valueOf(bytesWritten), Integer.valueOf(bytesTotal)}));
    }

    public void sendResponseMessage(HttpResponse response) throws IOException {
        if(!Thread.currentThread().isInterrupted()) {
            StatusLine line = response.getStatusLine();
            byte[] datas = this.getResponseData(response.getEntity());
            if(!Thread.currentThread().isInterrupted()) {
                if(line.getStatusCode() >= 300) {
                    this.sendFailureMessage(line.getStatusCode(), response.getAllHeaders(), datas, new HttpResponseException(line.getStatusCode(), line.getReasonPhrase()));
                }
                else {
                    this.sendSuccessMessage(line.getStatusCode(), response.getAllHeaders(), datas);
                }
            }
        }
    }

    public final void sendRetryMessage(int retryNo) {
        this.sendMessage(this.obtainMessage(5, new Object[]{Integer.valueOf(retryNo)}));
    }

    public final void sendStartMessage() {
        this.sendMessage(this.obtainMessage(2, null));
    }

    public final void sendSuccessMessage(int statusCode, Header[] headers, byte[] responseBytes) {
        this.sendMessage(this.obtainMessage(0, new Object[]{Integer.valueOf(statusCode), headers, responseBytes}));
    }

    public void setCharset(String charset) {
        this.responseCharset = charset;
    }

    public void setRequestHeaders(Header[] requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public void setRequestURI(URI requestURI) {
        this.requestURI = requestURI;
    }

    public void setUseSynchronousMode(boolean sync) {
        if(!sync && this.looper == null) {
            Log.w("AsyncHttpResponseHandler", "Current thread has not called Looper.prepare(). Forcing synchronous mode.");
        }

        if(1 == 0 && this.handler == null) {
            this.handler = new ResponderHandler(this, this.looper);
        }
        else if(1 != 0 && this.handler != null) {
            this.handler = null;
        }

        this.useSynchronousMode = true;
    }
}

