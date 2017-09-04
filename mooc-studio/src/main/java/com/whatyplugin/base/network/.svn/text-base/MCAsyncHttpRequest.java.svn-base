package com.whatyplugin.base.network;


import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

public class MCAsyncHttpRequest implements Runnable {
    private boolean cancelIsNotified;
    private final AbstractHttpClient client;
    private final HttpContext context;
    private int executionCount;
    private boolean isCancelled;
    private boolean isFinished;
    private boolean isRequestPreProcessed;
    private final HttpUriRequest request;
    private final MCResponseHandlerInterface responseHandler;

    public MCAsyncHttpRequest(AbstractHttpClient client, HttpContext context, HttpUriRequest request, MCResponseHandlerInterface responseHandler) {
        super();
        this.client = client;
        this.context = context;
        this.request = request;
        this.responseHandler = responseHandler;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        this.isCancelled = true;
        this.request.abort();
        return this.isCancelled();
    }

    public boolean isCancelled() {
        if(this.isCancelled) {
            this.sendCancelNotification();
        }

        return this.isCancelled;
    }

    public boolean isDone() {
        boolean done = (this.isCancelled()) || (this.isFinished) ? true : false;
        return done;
    }

    private void makeRequest() throws IOException {
        if(!this.isCancelled()) {
            if(this.request.getURI().getScheme() == null) {
                throw new MalformedURLException("No valid URI scheme was provided");
            }
            else {
                HttpResponse httpResponse = this.client.execute(this.request, this.context);
                if(!this.isCancelled() && this.responseHandler != null) {
                    this.responseHandler.onPreProcessResponse(this.responseHandler, httpResponse);
                    if(!this.isCancelled()) {
                        this.responseHandler.sendResponseMessage(httpResponse);
                        if(!this.isCancelled()) {
                            this.responseHandler.onPostProcessResponse(this.responseHandler, httpResponse);
                        }
                    }
                }
            }
        }
    }

    private void makeRequestWithRetries() throws IOException {
        int count;
        IOException exception = new IOException();
        boolean flag = true;
        HttpRequestRetryHandler httpRequestRetryHandler = this.client.getHttpRequestRetryHandler();
        IOException v1 = null;
        while(true) {
            if(!flag) {
            	 throw exception;
            }

            try {
                this.makeRequest();
                return;
            }catch(IOException v2_1) {
                try {
                    if(this.isCancelled()) {
                        return;
                    }
                }
                catch(Exception v2) {
                    break;
                }

                exception = v2_1;
                try {
                    count = this.executionCount + 1;
                    this.executionCount = count;
                    flag = httpRequestRetryHandler.retryRequest(exception, count, this.context);
                label_26:
                    if((flag) && this.responseHandler != null) {
                        this.responseHandler.sendRetryMessage(this.executionCount);
                    }
                }
                catch(Exception v2) {
                label_72:
                    break;
                }

                v1 = exception;
                continue;
            }
            catch(NullPointerException v2_2) {
                try {
                    exception = new IOException("NPE in HttpClient: " + v2_2.getMessage());
                }
                catch(Exception v2) {
                    break;
                }

                try {
                    count = this.executionCount + 1;
                    this.executionCount = count;
                    flag = httpRequestRetryHandler.retryRequest(exception, count, this.context);
                }
                catch(Exception v2) {
                	v2.printStackTrace();
                }
            }
        }
        return;
    }

    public void onPostProcessRequest(MCAsyncHttpRequest request) {
    }

    public void onPreProcessRequest(MCAsyncHttpRequest request) {
    }

    public void run() {
        Header[] v4 = null;
        if(!this.isCancelled()) {
            if(!this.isRequestPreProcessed) {
                this.isRequestPreProcessed = true;
                this.onPreProcessRequest(this);
            }

            if(this.isCancelled()) {
                return;
            }

            if(this.responseHandler != null) {
                this.responseHandler.sendStartMessage();
            }

            if(this.isCancelled()) {
                return;
            }

            try {
                this.makeRequestWithRetries();
            }
            catch(IOException exception) {
                if(!this.isCancelled() && this.responseHandler != null) {
                    this.responseHandler.sendFailureMessage(0, v4, v4.toString().getBytes(), ((Throwable)exception));
                    if(this.isCancelled()) {
                        return;
                    }

                    if(this.responseHandler != null) {
                        this.responseHandler.sendFinishMessage();
                    }

                    if(this.isCancelled()) {
                        return;
                    }
                }

                Log.e("AsyncHttpRequest", "makeRequestWithRetries returned error, but handler is null", ((Throwable)exception));
            }
            if(this.isCancelled()) {
                return;
            }

            if(this.responseHandler != null) {
                this.responseHandler.sendFinishMessage();
            }

            if(this.isCancelled()) {
                return;
            }

            this.onPostProcessRequest(this);
            this.isFinished = true;
        }
    }

    private void sendCancelNotification() {
        try {
            if(!this.isFinished && (this.isCancelled) && !this.cancelIsNotified) {
                this.cancelIsNotified = true;
                if(this.responseHandler != null) {
                    this.responseHandler.sendCancelMessage();
                }
            }
        }
        catch(Throwable e) {
        }

    }
}

