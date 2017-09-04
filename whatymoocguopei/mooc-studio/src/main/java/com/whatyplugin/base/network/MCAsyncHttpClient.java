package com.whatyplugin.base.network;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

public class MCAsyncHttpClient {
    class InflatingEntity extends HttpEntityWrapper {
        GZIPInputStream gzippedStream;
        PushbackInputStream pushbackStream;
        InputStream wrappedStream;

        public InflatingEntity(HttpEntity wrapped) {
            super(wrapped);
        }

        public void consumeContent() throws IOException {
            MCAsyncHttpClient.silentCloseInputStream(this.wrappedStream);
            MCAsyncHttpClient.silentCloseInputStream(this.pushbackStream);
            MCAsyncHttpClient.silentCloseInputStream(this.gzippedStream);
            super.consumeContent();
        }

        public InputStream getContent() throws IOException {
            PushbackInputStream pushbackInputStream = null;
            this.wrappedStream = this.wrappedEntity.getContent();
            this.pushbackStream = new PushbackInputStream(this.wrappedStream, 2);
            if(MCAsyncHttpClient.isInputStreamGZIPCompressed(this.pushbackStream)) {
                this.gzippedStream = new GZIPInputStream(this.pushbackStream);
                GZIPInputStream gzipInputStream = this.gzippedStream;
            }
            else {
                pushbackInputStream = this.pushbackStream;
            }

            return ((InputStream)pushbackInputStream);
        }

        public long getContentLength() {
            long contentLength = this.wrappedEntity == null ? 0 : this.wrappedEntity.getContentLength();
            return contentLength;
        }
    }

    public static final int DEFAULT_MAX_CONNECTIONS = 10;
    public static final int DEFAULT_MAX_RETRIES = 5;
    public static final int DEFAULT_RETRY_SLEEP_TIME_MILLIS = 1500;
    public static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    public static final int DEFAULT_SOCKET_TIMEOUT = 15000;
    public static final String ENCODING_GZIP = "gzip";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_CONTENT_RANGE = "Content-Range";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String LOG_TAG = "AsyncHttpClient";
    private final Map clientHeaderMap;
    private int connectTimeout;
    private final DefaultHttpClient httpClient;
    private final HttpContext httpContext;
    private boolean isUrlEncodingEnabled;
    private int maxConnections;
    private final Map requestMap;
    private int responseTimeout;
    private ExecutorService threadPool;

    public MCAsyncHttpClient() {
        this(false, 80, 443);
    }

    public MCAsyncHttpClient(boolean fixNoHttpResponseException, int httpPort, int httpsPort) {
        this(MCAsyncHttpClient.getDefaultSchemeRegistry(fixNoHttpResponseException, httpPort, httpsPort));
    }

    public MCAsyncHttpClient(int httpPort) {
        this(false, httpPort, 443);
    }

    public MCAsyncHttpClient(int httpPort, int httpsPort) {
        this(false, httpPort, httpsPort);
    }

    public MCAsyncHttpClient(SchemeRegistry schemeRegistry) {
        super();
        this.maxConnections = 10;
        this.connectTimeout = 15000;
        this.responseTimeout = 15000;
        this.isUrlEncodingEnabled = true;
        BasicHttpParams v1 = new BasicHttpParams();
        ConnManagerParams.setTimeout(((HttpParams)v1), ((long)this.connectTimeout));
        ConnManagerParams.setMaxConnectionsPerRoute(((HttpParams)v1), new ConnPerRouteBean(this.maxConnections));
        ConnManagerParams.setMaxTotalConnections(((HttpParams)v1), 10);
        HttpConnectionParams.setSoTimeout(((HttpParams)v1), this.responseTimeout);
        HttpConnectionParams.setConnectionTimeout(((HttpParams)v1), this.connectTimeout);
        HttpConnectionParams.setTcpNoDelay(((HttpParams)v1), true);
        HttpConnectionParams.setSocketBufferSize(((HttpParams)v1), 8192);
        HttpProtocolParams.setVersion(((HttpParams)v1), HttpVersion.HTTP_1_1);
        ThreadSafeClientConnManager threadManager = new ThreadSafeClientConnManager(((HttpParams)v1), schemeRegistry);
        this.threadPool = this.getDefaultThreadPool();
        this.requestMap = Collections.synchronizedMap(new WeakHashMap());
        this.clientHeaderMap = new HashMap();
        this.httpContext = new SyncBasicHttpContext(new BasicHttpContext());
        this.httpClient = new DefaultHttpClient(((ClientConnectionManager)threadManager), ((HttpParams)v1));
        this.httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) {
                if(!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }

                Iterator iterator = MCAsyncHttpClient.this.clientHeaderMap.keySet().iterator();
                while(iterator.hasNext()) {
                    String item = (String) iterator.next();
                    if(request.containsHeader((item))) {
                        Header v1 = request.getFirstHeader((item));
                        Log.d("AsyncHttpClient", String.format("Headers were overwritten! (%s | %s) overwrites (%s | %s)", item, MCAsyncHttpClient.this.clientHeaderMap.get(item), v1.getName(), v1.getValue()));
                        request.removeHeader(v1);
                    }

                    request.addHeader((item), (String) MCAsyncHttpClient.this.clientHeaderMap.get(item));
                }
            }
        });
        this.httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(HttpResponse response, HttpContext context) {
                HttpEntity entity = response.getEntity();
                if(entity != null) {
                    Header header = entity.getContentEncoding();
                    if(header != null) {
                        HeaderElement[] elements = header.getElements();
                        int length = elements.length;
                        int i = 0;
                        while(i < length) {
                            if(elements[i].getName().equalsIgnoreCase("gzip")) {
                                response.setEntity(new InflatingEntity(entity));
                            }
                            else {
                                ++i;
                                continue;
                            }

                            return;
                        }
                    }
                }
            }
        });
        this.httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
            	AuthState authState = (AuthState) context.getAttribute("http.auth.target-scope");
            	CredentialsProvider credentialsProvider = (CredentialsProvider) context.getAttribute("http.auth.credentials-provider");
            	HttpHost httpHost = (HttpHost) context.getAttribute("http.target_host");
                if((authState).getAuthScheme() == null) {
                    Credentials credentials = (credentialsProvider).getCredentials(new AuthScope((httpHost).getHostName(), (httpHost).getPort()));
                    if(credentials != null) {
                        (authState).setAuthScheme(new BasicScheme());
                        (authState).setCredentials(credentials);
                    }
                }
            }
        }, 0);
        this.httpClient.setHttpRequestRetryHandler(new MCRetryHandler(5, 1500));
    }

    static Map access$0(MCAsyncHttpClient asyncHttpClient) {
        return asyncHttpClient.clientHeaderMap;
    }

    static Map access$1(MCAsyncHttpClient asyncHttpClient) {
        return asyncHttpClient.requestMap;
    }

    private HttpEntityEnclosingRequestBase addEntityToRequestBase(HttpEntityEnclosingRequestBase requestBase, HttpEntity entity) {
        if(entity != null) {
            requestBase.setEntity(entity);
        }

        return requestBase;
    }

    public void addHeader(String header, String value) {
        this.clientHeaderMap.put(header, value);
    }

    public static void allowRetryExceptionClass(Class arg0) {
        if(arg0 != null) {
            MCRetryHandler.addClassToWhitelist(arg0);
        }
    }

    public static void blockRetryExceptionClass(Class arg0) {
        if(arg0 != null) {
            MCRetryHandler.addClassToBlacklist(arg0);
        }
    }

    public void cancelAllRequests(boolean mayInterruptIfRunning) {
        Object list;
        Iterator iterator = this.requestMap.values().iterator();
        do {
            if(!iterator.hasNext()) {
            	  this.requestMap.clear();
                  return;
            }

            list = iterator.next();
        }
        while(list == null);

        Iterator iterator2 = ((List)list).iterator();
      
        while(true) {
            if(!iterator2.hasNext()) {
            	  if(!iterator.hasNext()) {
                	  this.requestMap.clear();
                      return;
                }

                list = iterator.next();
            }

            ((MCRequestHandle) iterator2.next()).cancel(mayInterruptIfRunning);
        }
    }

    public void cancelRequests(final Context context, final boolean mayInterruptIfRunning) {
        if(context == null) {
            Log.e("AsyncHttpClient", "Passed null Context to cancelRequests");
        }
        else {
            Runnable runnable = new Runnable() {
                public void run() {
                    Object list = MCAsyncHttpClient.this.requestMap.get(context);
                    if(list != null) {
                        Iterator iterator = ((List)list).iterator();
                        while(iterator.hasNext()) {
                            ((MCRequestHandle) iterator.next()).cancel(mayInterruptIfRunning);
                        }

                        MCAsyncHttpClient.this.requestMap.remove(context);
                    }
                }
            };
            if(Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(((Runnable)runnable)).start();
            }
            else {
                ((Runnable)runnable).run();
            }
        }
    }

    @Deprecated public void clearBasicAuth() {
        this.clearCredentialsProvider();
    }

    public void clearCredentialsProvider() {
        this.httpClient.getCredentialsProvider().clear();
    }

    public MCRequestHandle delete(Context context, String url, MCResponseHandlerInterface responseHandler) {
        return this.sendRequest(this.httpClient, this.httpContext, new HttpDelete(URI.create(url).normalize()), null, responseHandler, context);
    }
    
    public MCRequestHandle delete(Context context, String url, Header[] headers, MCRequestParams params, MCResponseHandlerInterface responseHandler) {
        HttpDelete v3 = new HttpDelete(MCAsyncHttpClient.getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        if(headers != null) {
            v3.setHeaders(headers);
        }

        return this.sendRequest(this.httpClient, this.httpContext, ((HttpUriRequest)v3), null, responseHandler, context);
    }

    public MCRequestHandle delete(Context context, String url, Header[] headers, MCResponseHandlerInterface responseHandler) {
        HttpDelete v3 = new HttpDelete(URI.create(url).normalize());
        if(headers != null) {
            v3.setHeaders(headers);
        }

        return this.sendRequest(this.httpClient, this.httpContext, ((HttpUriRequest)v3), null, responseHandler, context);
    }

    public MCRequestHandle delete(String url, MCResponseHandlerInterface responseHandler) {
        return this.delete(null, url, responseHandler);
    }

    public static void endEntityViaReflection(HttpEntity entity) {
        Field field;
        int i;
        if(!(entity instanceof HttpEntityWrapper)) {
            return;
        }

        Field temp = null;
        try {
            Field[] fields = HttpEntityWrapper.class.getDeclaredFields();
            int length = fields.length;
            i = 0;
            while(true) {
                if(i < length) {
                    field = fields[i];
                    if(field.getName().equals("wrappedEntity")) {
                        break;
                    }
                    else {
                    	++i;
                    	continue;
                    }
                }

                if(temp != null) {
                    temp.setAccessible(true);
                    HttpEntity httpEntity = (HttpEntity) temp.get(entity);
                    if(httpEntity == null) {
                        return;
                    }
                    (httpEntity).consumeContent();
                }
            }
        }
        catch(Throwable v3) {
        	 Log.e("AsyncHttpClient", "wrappedEntity consume", v3);
             return;
        }
    }

    public MCRequestHandle get(Context context, String url, MCRequestParams params, MCResponseHandlerInterface responseHandler) {
        return this.sendRequest(this.httpClient, this.httpContext, new HttpGet(MCAsyncHttpClient.getUrlWithQueryString(this.isUrlEncodingEnabled, url, params)), null, responseHandler, context);
    }

    public MCRequestHandle get(Context context, String url, MCResponseHandlerInterface responseHandler) {
        return this.get(context, url, null, responseHandler);
    }

    public MCRequestHandle get(Context context, String url, Header[] headers, MCRequestParams params, MCResponseHandlerInterface responseHandler) {
        HttpGet v3 = new HttpGet(MCAsyncHttpClient.getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        if(headers != null) {
            ((HttpUriRequest)v3).setHeaders(headers);
        }

        return this.sendRequest(this.httpClient, this.httpContext, ((HttpUriRequest)v3), null, responseHandler, context);
    }

    public MCRequestHandle get(String url, MCRequestParams params, MCResponseHandlerInterface responseHandler) {
        return this.get(null, url, params, responseHandler);
    }

    public MCRequestHandle get(String url, MCResponseHandlerInterface responseHandler) {
        return this.get(null, url, null, responseHandler);
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    private static SchemeRegistry getDefaultSchemeRegistry(boolean fixNoHttpResponseException, int httpPort, int httpsPort) {
        if(fixNoHttpResponseException) {
            Log.d("AsyncHttpClient", "Beware! Using the fix is insecure, as it doesn\'t verify SSL certificates.");
        }

        if(httpPort < 1) {
            httpPort = 80;
            Log.d("AsyncHttpClient", "Invalid HTTP port number specified, defaulting to 80");
        }

        if(httpsPort < 1) {
            httpsPort = 443;
            Log.d("AsyncHttpClient", "Invalid HTTPS port number specified, defaulting to 443");
        }

        SSLSocketFactory v1 = fixNoHttpResponseException ? MCMySSLSocketFactory.getFixedSocketFactory() : SSLSocketFactory.getSocketFactory();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), httpPort));
        schemeRegistry.register(new Scheme("https", ((SocketFactory)v1), httpsPort));
        return schemeRegistry;
    }

    protected ExecutorService getDefaultThreadPool() {
        return Executors.newCachedThreadPool();
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public HttpContext getHttpContext() {
        return this.httpContext;
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    public int getResponseTimeout() {
        return this.responseTimeout;
    }

    public ExecutorService getThreadPool() {
        return this.threadPool;
    }

    public int getTimeout() {
        return this.connectTimeout;
    }

    public static String getUrlWithQueryString(boolean shouldEncodeUrl, String url, MCRequestParams params) {
        String v1;
        if(url == null) {
            v1 = null;
            return v1;
        }

        if(shouldEncodeUrl) {
            try {
                URL v8 = new URL(URLDecoder.decode(url, "UTF-8"));
                url = new URI(v8.getProtocol(), v8.getUserInfo(), v8.getHost(), v8.getPort(), v8.getPath(), v8.getQuery(), v8.getRef()).toASCIIString();
            }
            catch(Exception v10) {
                Log.e("AsyncHttpClient", "getUrlWithQueryString encoding URL", ((Throwable)v10));
            }
        }

        if(params != null) {
            String v11 = params.getParamString().trim();
            if(!v11.equals("") && !v11.equals("?")) {
                StringBuilder v2 = new StringBuilder(String.valueOf(url));
                v1 = url.contains("?") ? "&" : "?";
                url = String.valueOf(v2.append(v1).toString()) + v11;
            }
        }

        return url;
    }

    public MCRequestHandle head(Context context, String url, MCRequestParams params, MCResponseHandlerInterface responseHandler) {
        return this.sendRequest(this.httpClient, this.httpContext, new HttpHead(MCAsyncHttpClient.getUrlWithQueryString(this.isUrlEncodingEnabled, url, params)), null, responseHandler, context);
    }

    public MCRequestHandle head(Context context, String url, MCResponseHandlerInterface responseHandler) {
        return this.head(context, url, null, responseHandler);
    }

    public MCRequestHandle head(Context context, String url, Header[] headers, MCRequestParams params, MCResponseHandlerInterface responseHandler) {
        HttpHead v3 = new HttpHead(MCAsyncHttpClient.getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        if(headers != null) {
            ((HttpUriRequest)v3).setHeaders(headers);
        }

        return this.sendRequest(this.httpClient, this.httpContext, ((HttpUriRequest)v3), null, responseHandler, context);
    }

    public MCRequestHandle head(String url, MCRequestParams params, MCResponseHandlerInterface responseHandler) {
        return this.head(null, url, params, responseHandler);
    }

    public MCRequestHandle head(String url, MCResponseHandlerInterface responseHandler) {
        return this.head(null, url, null, responseHandler);
    }

    public static boolean isInputStreamGZIPCompressed(PushbackInputStream inputStream) throws IOException {
        int length = 2;
        boolean flag = false;
        if(inputStream != null) {
            byte[] arr = new byte[length];
            int inLength = inputStream.read(arr);
            inputStream.unread(arr);
            int result = arr[0] & 255 | arr[1] << 8 & 65280;
            if(inLength == length && 35615 == result) {
                flag = true;
            }
        }
        return flag;
    }

    public boolean isUrlEncodingEnabled() {
        return this.isUrlEncodingEnabled;
    }

    protected MCAsyncHttpRequest newAsyncHttpRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, String contentType, MCResponseHandlerInterface responseHandler, Context context) {
        return new MCAsyncHttpRequest(((AbstractHttpClient)client), httpContext, uriRequest, responseHandler);
    }

    private HttpEntity paramsToEntity(MCRequestParams params, MCResponseHandlerInterface responseHandler) {
        Header[] headers = null;
        HttpEntity httpEntity = null;
        if(params != null) {
            try {
                httpEntity = params.getEntity(responseHandler);
            }
            catch(IOException e) {
                if(responseHandler != null) {
                    responseHandler.sendFailureMessage(0, headers, headers.toString().getBytes(), ((Throwable)e));
                    return httpEntity;
                }

                e.printStackTrace();
            }
        }

        return httpEntity;
    }

    public MCRequestHandle post(Context context, String url, MCRequestParams params, MCResponseHandlerInterface responseHandler) {
        return this.post(context, url, this.paramsToEntity(params, responseHandler), null, responseHandler);
    }

    public MCRequestHandle post(Context context, String url, HttpEntity entity, String contentType, MCResponseHandlerInterface responseHandler) {
        return this.sendRequest(this.httpClient, this.httpContext, this.addEntityToRequestBase(new HttpPost(URI.create(url).normalize()), entity), contentType, responseHandler, context);
    }

    public MCRequestHandle post(Context context, String url, Header[] headers, MCRequestParams params, String contentType, MCResponseHandlerInterface responseHandler) {
        HttpPost v3 = new HttpPost(URI.create(url).normalize());
        if(params != null) {
            ((HttpEntityEnclosingRequestBase)v3).setEntity(this.paramsToEntity(params, responseHandler));
        }

        if(headers != null) {
            ((HttpEntityEnclosingRequestBase)v3).setHeaders(headers);
        }

        return this.sendRequest(this.httpClient, this.httpContext, ((HttpUriRequest)v3), contentType, responseHandler, context);
    }

    public MCRequestHandle post(Context context, String url, Header[] headers, HttpEntity entity, String contentType, MCResponseHandlerInterface responseHandler) {
        HttpEntityEnclosingRequestBase v3 = this.addEntityToRequestBase(new HttpPost(URI.create(url).normalize()), entity);
        if(headers != null) {
            v3.setHeaders(headers);
        }

        return this.sendRequest(this.httpClient, this.httpContext, ((HttpUriRequest)v3), contentType, responseHandler, context);
    }

    public MCRequestHandle post(String url, MCRequestParams params, MCResponseHandlerInterface responseHandler) {
        return this.post(null, url, params, responseHandler);
    }

    public MCRequestHandle post(String url, MCResponseHandlerInterface responseHandler) {
        return this.post(null, url, null, responseHandler);
    }

    public MCRequestHandle put(Context context, String url, MCRequestParams params, MCResponseHandlerInterface responseHandler) {
        return this.put(context, url, this.paramsToEntity(params, responseHandler), null, responseHandler);
    }

    public MCRequestHandle put(Context context, String url, HttpEntity entity, String contentType, MCResponseHandlerInterface responseHandler) {
        return this.sendRequest(this.httpClient, this.httpContext, this.addEntityToRequestBase(new HttpPut(URI.create(url).normalize()), entity), contentType, responseHandler, context);
    }

    public MCRequestHandle put(Context context, String url, Header[] headers, HttpEntity entity, String contentType, MCResponseHandlerInterface responseHandler) {
        HttpEntityEnclosingRequestBase v3 = this.addEntityToRequestBase(new HttpPut(URI.create(url).normalize()), entity);
        if(headers != null) {
            v3.setHeaders(headers);
        }

        return this.sendRequest(this.httpClient, this.httpContext, ((HttpUriRequest)v3), contentType, responseHandler, context);
    }

    public MCRequestHandle put(String url, MCRequestParams params, MCResponseHandlerInterface responseHandler) {
        return this.put(null, url, params, responseHandler);
    }

    public MCRequestHandle put(String url, MCResponseHandlerInterface responseHandler) {
        return this.put(null, url, null, responseHandler);
    }

    public void removeAllHeaders() {
        this.clientHeaderMap.clear();
    }

    public void removeHeader(String header) {
        this.clientHeaderMap.remove(header);
    }

    protected MCRequestHandle sendRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, String contentType, MCResponseHandlerInterface responseHandler, Context context) {
        List list;
        if(uriRequest == null) {
            throw new IllegalArgumentException("HttpUriRequest must not be null");
        }

        if(responseHandler == null) {
            throw new IllegalArgumentException("ResponseHandler must not be null");
        }

        if(responseHandler.getUseSynchronousMode()) {
            throw new IllegalArgumentException("Synchronous ResponseHandler used in AsyncHttpClient. You should create your response handler in a looper thread or use SyncHttpClient instead.");
        }

        if(contentType != null) {
        	uriRequest.setHeader("Content-Type", contentType);
            if(((uriRequest instanceof HttpEntityEnclosingRequestBase)) && ((HttpResponse) uriRequest).getEntity() != null) {
                Log.w("AsyncHttpClient", "Passed contentType will be ignored because HttpEntity sets content type");
            }

        }

    label_22:
        responseHandler.setRequestHeaders(uriRequest.getAllHeaders());
        responseHandler.setRequestURI(uriRequest.getURI());
        MCAsyncHttpRequest asyncHttpRequest = this.newAsyncHttpRequest(client, httpContext, uriRequest, contentType, responseHandler, context);
        this.threadPool.submit(((Runnable)asyncHttpRequest));
        MCRequestHandle requestHandle = new MCRequestHandle(asyncHttpRequest);
        if(context != null) {
            Object value = this.requestMap.get(context);
            Map requestMap = this.requestMap;
            //__monitor _enter(v5);
            if(value == null) {
                try {
                    list = Collections.synchronizedList(new LinkedList());
                    this.requestMap.put(context, list);
                    //__monitor _exit(v5);
                	 list.add(requestHandle);
                Iterator iterator = list.iterator();
                while(iterator.hasNext()) {
                    if(!((MCRequestHandle) iterator.next()).shouldBeGarbageCollected()) {
                        continue;
                    }

                    iterator.remove();
                }
                }
                catch(Throwable v4) {

                }
            }

           
        }

        return requestHandle;
    }

    public void setBasicAuth(String username, String password) {
        this.setBasicAuth(username, password, false);
    }

    public void setBasicAuth(String username, String password, boolean preemtive) {
        this.setBasicAuth(username, password, null, preemtive);
    }

    public void setBasicAuth(String username, String password, AuthScope scope) {
        this.setBasicAuth(username, password, scope, false);
    }

    public void setBasicAuth(String username, String password, AuthScope scope, boolean preemtive) {
        this.setCredentials(scope, new UsernamePasswordCredentials(username, password));
    }

    public void setConnectTimeout(int value) {
        if(value < 1000) {
            value = 15000;
        }

        this.connectTimeout = value;
        HttpParams httpParams = this.httpClient.getParams();
        ConnManagerParams.setTimeout(httpParams, ((long)this.connectTimeout));
        HttpConnectionParams.setConnectionTimeout(httpParams, this.connectTimeout);
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.httpContext.setAttribute("http.cookie-store", cookieStore);
    }

    public void setCredentials(AuthScope authScope, Credentials credentials) {
        if(credentials == null) {
            Log.d("AsyncHttpClient", "Provided credentials are null, not setting");
        }
        else {
            CredentialsProvider credentialsProvider = this.httpClient.getCredentialsProvider();
            if(authScope == null) {
                authScope = AuthScope.ANY;
            }

            credentialsProvider.setCredentials(authScope, credentials);
        }
    }

    public void setEnableRedirects(boolean enableRedirects) {
    }

    public void setEnableRedirects(boolean enableRedirects, boolean enableRelativeRedirects) {
    }

    public void setMaxConnections(int maxConnections) {
        if(maxConnections < 1) {
            maxConnections = 10;
        }

        this.maxConnections = maxConnections;
        ConnManagerParams.setMaxConnectionsPerRoute(this.httpClient.getParams(), new ConnPerRouteBean(this.maxConnections));
    }

    public void setMaxRetriesAndTimeout(int retries, int timeout) {
        this.httpClient.setHttpRequestRetryHandler(new MCRetryHandler(retries, timeout));
    }

    public void setProxy(String hostname, int port) {
        this.httpClient.getParams().setParameter("http.route.default-proxy", new HttpHost(hostname, port));
    }

    public void setProxy(String hostname, int port, String username, String password) {
        this.httpClient.getCredentialsProvider().setCredentials(new AuthScope(hostname, port), new UsernamePasswordCredentials(username, password));
        this.httpClient.getParams().setParameter("http.route.default-proxy", new HttpHost(hostname, port));
    }

    public void setRedirectHandler(RedirectHandler customRedirectHandler) {
        this.httpClient.setRedirectHandler(customRedirectHandler);
    }

    public void setResponseTimeout(int value) {
        if(value < 1000) {
            value = 15000;
        }

        this.responseTimeout = value;
        HttpConnectionParams.setSoTimeout(this.httpClient.getParams(), this.responseTimeout);
    }

    public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", ((SocketFactory)sslSocketFactory), 443));
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public void setTimeout(int value) {
        if(value < 1000) {
            value = 15000;
        }

        this.setConnectTimeout(value);
        this.setResponseTimeout(value);
    }

    public void setURLEncodingEnabled(boolean enabled) {
        this.isUrlEncodingEnabled = enabled;
    }

    public void setUserAgent(String userAgent) {
        HttpProtocolParams.setUserAgent(this.httpClient.getParams(), userAgent);
    }

    public static void silentCloseInputStream(InputStream is) {
        if(is != null) {
            try {
                is.close();
            }
            catch(IOException exception) {
                Log.w("AsyncHttpClient", "Cannot close input stream", ((Throwable)exception));
            }
        }
    }

    public static void silentCloseOutputStream(OutputStream os) {
        if(os != null) {
            try {
                os.close();
            }
            catch(IOException exception) {
                Log.w("AsyncHttpClient", "Cannot close output stream", ((Throwable)exception));
            }
        }
    }
}

