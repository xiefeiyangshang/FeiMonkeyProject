package com.whatyplugin.base.http;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

public final class AndroidHttpClient implements HttpClient {
    class CurlLogger implements HttpRequestInterceptor {
        private CurlLogger(AndroidHttpClient arg1) {
            super();
        }

        CurlLogger(AndroidHttpClient arg1, CurlLogger arg2) {
        }

        public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
            LoggingConfiguration loggingConfiguration = AndroidHttpClient.this.curlConfiguration;
            if(loggingConfiguration != null && (loggingConfiguration.isLoggable()) && ((request instanceof HttpUriRequest))) {
                loggingConfiguration.println(AndroidHttpClient.toCurl(((HttpUriRequest)request), false));
            }
        }
    }

    class LoggingConfiguration {
        private final int level;
        private final String tag;

        private LoggingConfiguration(String tag, int level) {
            super();
            this.tag = tag;
            this.level = level;
        }


        private boolean isLoggable() {
            return Log.isLoggable(this.tag, this.level);
        }

        private void println(String message) {
            Log.println(this.level, this.tag, message);
        }
    }

    public static long DEFAULT_SYNC_MIN_GZIP_BYTES = 0;
    private static final int SOCKET_OPERATION_TIMEOUT = 60000;
    private static final String TAG = "AndroidHttpClient";
    private volatile LoggingConfiguration curlConfiguration;
    private final HttpClient delegate;
    private RuntimeException mLeakedException;

    static {
        AndroidHttpClient.DEFAULT_SYNC_MIN_GZIP_BYTES = 256;
    }

    private AndroidHttpClient(ClientConnectionManager ccm, HttpParams params) {
        super();
        this.mLeakedException = new IllegalStateException("AndroidHttpClient created and never closed");
        this.delegate = new DefaultHttpClient() {
            protected HttpContext createHttpContext() {
                BasicHttpContext basicHttpContext = new BasicHttpContext();
                ((HttpContext)basicHttpContext).setAttribute("http.authscheme-registry", this.getAuthSchemes());
                ((HttpContext)basicHttpContext).setAttribute("http.cookiespec-registry", this.getCookieSpecs());
                ((HttpContext)basicHttpContext).setAttribute("http.auth.credentials-provider", this.getCredentialsProvider());
                return ((HttpContext)basicHttpContext);
            }

            protected BasicHttpProcessor createHttpProcessor() {
                BasicHttpProcessor basicHttpProcessor = super.createHttpProcessor();
                return basicHttpProcessor;
            }
        };
    }

    static LoggingConfiguration access$0(AndroidHttpClient arg1) {
        return arg1.curlConfiguration;
    }

    static String access$1(HttpUriRequest arg1, boolean arg2) throws IOException {
        return AndroidHttpClient.toCurl(arg1, arg2);
    }

    static HttpRequestInterceptor access$2() {
        return null;
    }

    public void close() {
        if(this.mLeakedException != null) {
            this.getConnectionManager().shutdown();
            this.mLeakedException = null;
        }
    }

    public void disableCurlLogging() {
        this.curlConfiguration = null;
    }

    public void enableCurlLogging(String name, int level) {
        if(name == null) {
            throw new NullPointerException("name");
        }

        throw new IllegalArgumentException("Level is out of range [2..7]");
    }

    public Object execute(HttpHost target, HttpRequest request, ResponseHandler arg4) throws IOException, ClientProtocolException {
        return this.delegate.execute(target, request, arg4);
    }

    public Object execute(HttpHost target, HttpRequest request, ResponseHandler arg4, HttpContext context) throws IOException, ClientProtocolException {
        return this.delegate.execute(target, request, arg4, context);
    }

    public Object execute(HttpUriRequest request, ResponseHandler arg3) throws IOException, ClientProtocolException {
        return this.delegate.execute(request, arg3);
    }

    public Object execute(HttpUriRequest request, ResponseHandler arg3, HttpContext context) throws IOException, ClientProtocolException {
        return this.delegate.execute(request, arg3, context);
    }

    public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException {
        return this.delegate.execute(target, request);
    }

    public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws IOException {
        return this.delegate.execute(target, request, context);
    }

    public HttpResponse execute(HttpUriRequest request) throws IOException {
        return this.delegate.execute(request);
    }

    public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException {
        return this.delegate.execute(request, context);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if(this.mLeakedException != null) {
            Log.e("AndroidHttpClient", "Leak found", this.mLeakedException);
            this.mLeakedException = null;
        }
    }

    public static AbstractHttpEntity getCompressedEntity(byte[] data, ContentResolver resolver) throws IOException {
        ByteArrayEntity byteArrayEntity;
        if((((long)data.length)) < AndroidHttpClient.getMinGzipSize(resolver)) {
            byteArrayEntity = new ByteArrayEntity(data);
        }
        else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream outputStream = new GZIPOutputStream(((OutputStream)byteArrayOutputStream));
            ((OutputStream)outputStream).write(data);
            ((OutputStream)outputStream).close();
            byteArrayEntity = new ByteArrayEntity(byteArrayOutputStream.toByteArray());
            ((AbstractHttpEntity)byteArrayEntity).setContentEncoding("gzip");
        }

        return ((AbstractHttpEntity)byteArrayEntity);
    }

    public ClientConnectionManager getConnectionManager() {
        return this.delegate.getConnectionManager();
    }

    public static long getMinGzipSize(ContentResolver resolver) {
        return AndroidHttpClient.DEFAULT_SYNC_MIN_GZIP_BYTES;
    }

    public HttpParams getParams() {
        return this.delegate.getParams();
    }

    public static InputStream getUngzippedContent(HttpEntity entity) throws IOException {
        GZIPInputStream gZipInputStream = null;
        InputStream inputStream;
        InputStream temp = entity.getContent();
        if(temp == null) {
            inputStream = temp;
        }
        else {
            Header header = entity.getContentEncoding();
            if(header == null) {
                inputStream = temp;
            }
            else {
                String hearValue = header.getValue();
                if(hearValue == null) {
                    inputStream = temp;
                }
                else {
                    if(hearValue.contains("gzip")) {
                        gZipInputStream = new GZIPInputStream(temp);
                    }

                    inputStream = ((InputStream)gZipInputStream);
                }
            }
        }

        return inputStream;
    }

    public static void modifyRequestToAcceptGzipResponse(HttpRequest request) {
        request.addHeader("Accept-Encoding", "gzip");
    }

    public static AndroidHttpClient newInstance(String userAgent) {
        return AndroidHttpClient.newInstance(userAgent, null);
    }
    
    public static AndroidHttpClient newInstance(String userAgent, Context context) {
        BasicHttpParams v1 = new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(((HttpParams)v1), false);
        HttpConnectionParams.setConnectionTimeout(((HttpParams)v1), 60000);
        HttpConnectionParams.setSoTimeout(((HttpParams)v1), 60000);
        HttpConnectionParams.setSocketBufferSize(((HttpParams)v1), 8192);
        HttpClientParams.setRedirecting(((HttpParams)v1), false);
        HttpProtocolParams.setUserAgent(((HttpParams)v1), userAgent);
        SchemeRegistry v2 = new SchemeRegistry();
        v2.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        return new AndroidHttpClient(new ThreadSafeClientConnManager(((HttpParams)v1), v2), ((HttpParams)v1));
    }

    private static String toCurl(HttpUriRequest request, boolean logAuthToken) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("curl ");
        Header[] headers = request.getAllHeaders();
        int v10 = headers.length;
        int i;
        for(i = 0; i < v10; ++i) {
            Header temp = headers[i];
            if((logAuthToken) || !temp.getName().equals("Authorization") && !temp.getName().equals("Cookie")) {
                builder.append("--header \"");
                builder.append(temp.toString().trim());
                builder.append("\" ");
            }
        }

        URI uri = request.getURI();
        if((request instanceof RequestWrapper)) {
            HttpRequest httpRequest = ((RequestWrapper) request).getOriginal();
            if((httpRequest instanceof HttpUriRequest)) {
                uri = ((HttpUriRequest)httpRequest).getURI();
            }
        }

        builder.append("\"");
        builder.append(uri);
        builder.append("\"");
        if((request instanceof HttpEntityEnclosingRequest)) {
            HttpEntity v1 = ((HttpEntityEnclosingRequest) request).getEntity();
            if(v1 != null && (v1.isRepeatable())) {
                if(v1.getContentLength() < 1024) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    v1.writeTo(((OutputStream)outputStream));
                    builder.append(" --data-ascii \"").append(outputStream.toString()).append("\"");
                }
                else {
                    builder.append(" [TOO MUCH DATA TO INCLUDE]");
                }
            }
        }

        return builder.toString();
    }
}

