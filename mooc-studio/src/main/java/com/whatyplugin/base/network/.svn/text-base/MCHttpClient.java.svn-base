package com.whatyplugin.base.network;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCCommonResult;
import com.whatyplugin.base.model.MCCommonResult.MCResultCode;
import com.whatyplugin.base.network.MCNetworkDefine.MCNetworkStatus;
import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAsyncTask;
import com.whatyplugin.imooc.logic.service_.MCAsyncTaskInterface;
import com.whatyplugin.imooc.logic.utils.StringUtils;

public class MCHttpClient {
    private static final int CORE_POOL_SIZE = 20;
    private static int DEFAULT_TIMEOUT = 0;
    private static final int KEEP_ALIVE = 1;
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static String TAG;
    public static Executor THREAD_POOL_EXECUTOR;
    private static HttpClient mHttpClient;
    private static BlockingQueue sPoolWorkQueue;
    private static ThreadFactory sThreadFactory;

    static {
        MCHttpClient.TAG = "MCHttpClient";
        MCHttpClient.DEFAULT_TIMEOUT = 30000;
        MCHttpClient.sPoolWorkQueue = new LinkedBlockingQueue(10);
        MCHttpClient.sThreadFactory = new ThreadFactory() {
            private  AtomicInteger mCount = new AtomicInteger();

            public Thread newThread(Runnable r) {
                return new Thread(r, "AsyncTask #" + this.mCount.getAndIncrement());
            }
        };
        MCHttpClient.THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(20, 128, 1, TimeUnit.SECONDS, MCHttpClient.sPoolWorkQueue, MCHttpClient.sThreadFactory);
    }

    public MCHttpClient() {
        super();
    }

    private static HttpClient getHttpClient(Context context, MCNetwokConfigInteface config, String userAgent) {
        HttpClient httpClient = null;
        Class v4 = MCHttpClient.class;
        try {
            if(MCHttpClient.mHttpClient == null) {
                BasicHttpParams basicHttpParams = new BasicHttpParams();
                int v3_1 = config.getHttpConnectTimeOut() == 0 ? MCHttpClient.DEFAULT_TIMEOUT : config.getHttpConnectTimeOut();
                ConnManagerParams.setTimeout(((HttpParams)basicHttpParams), ((long)v3_1));
                v3_1 = config.getHttpConnectTimeOut() == 0 ? MCHttpClient.DEFAULT_TIMEOUT : config.getHttpConnectTimeOut();
                HttpConnectionParams.setConnectionTimeout(((HttpParams)basicHttpParams), v3_1);
                v3_1 = config.getHttpConnectTimeOut() == 0 ? MCHttpClient.DEFAULT_TIMEOUT : config.getHttpConnectTimeOut();
                HttpConnectionParams.setSoTimeout(((HttpParams)basicHttpParams), v3_1);
                HttpProtocolParams.setUseExpectContinue(((HttpParams)basicHttpParams), true);
                HttpConnectionParams.setStaleCheckingEnabled(((HttpParams)basicHttpParams), false);
                HttpProtocolParams.setVersion(((HttpParams)basicHttpParams), HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(((HttpParams)basicHttpParams), "UTF-8");
                HttpClientParams.setRedirecting(((HttpParams)basicHttpParams), false);
                HttpConnectionParams.setTcpNoDelay(((HttpParams)basicHttpParams), true);
                HttpConnectionParams.setSocketBufferSize(((HttpParams)basicHttpParams), 8192);
                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
                MCHttpClient.mHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(((HttpParams)basicHttpParams), schemeRegistry), ((HttpParams)basicHttpParams));
            }

            httpClient = MCHttpClient.mHttpClient;
        }
        catch(Throwable v3) {
        }
        return httpClient;
    }

    private static String getResponseFromHttpClient(MCBaseRequest request, MCNetwokConfigInteface config, Context context) throws Exception {
        HttpClient client = MCHttpClient.getHttpClient(context, config, MCHttpClient.getUserAgent(context));
        client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 15000); // 设置读数据超时时间(单位毫秒)
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
        HttpPost httpPost = null;
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        if(request.requestUrl.startsWith("http://")){
        	httpPost = new HttpPost(request.requestUrl);
        	String loginType = config.getLoginType(context);
        	params.add(new BasicNameValuePair("loginType", loginType));
        	params.add(new BasicNameValuePair("params.loginType", loginType));
        	
        	String learnLoginType=MCSaveData.getUserInfo(Contants.LEARLOGINTYPE, context).toString();
            params.add(new BasicNameValuePair("unTyxlLoginToken", learnLoginType));
        }else{
        	//所有原来的都不再请求了，直接返回。
        	return "";
        }
        Iterator<String> v8 = request.requestParams.keySet().iterator();
        while(v8.hasNext()) {
        	String key = v8.next();
        	if(request.requestParams.get(key)==null){
        		continue;
        	}
            params.add(new BasicNameValuePair(key, request.requestParams.get(key).toString()));
        }

        //如果有图片链接形式的参数在这里封装
        List<NameValuePair> fileParams = request.fileParams;
        if(fileParams!=null&&fileParams.size()>0){
        	for(NameValuePair pair : fileParams){
        		params.add(new BasicNameValuePair(pair.getName(), pair.getValue()));
        	}
        }

       StringUtils.printRequestInfo(request.requestUrl, params);
        
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        HttpResponse v2 = client.execute(((HttpUriRequest)httpPost));
        MCHttpClient.logHeaders(v2);
        String v5 = "";
        if(v2.getStatusLine().getStatusCode() == 200) {
        	v5 = EntityUtils.toString(v2.getEntity(),"UTF-8");
        }

        httpPost.abort();
        return v5;
    }

    public static String getResponseOfGetMethod(MCBaseRequest request, MCNetwokConfigInteface config, Context context) throws Exception {
        HttpClient client = MCHttpClient.getHttpClient(context, config, MCHttpClient.getUserAgent(context));
        client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 3000); // 设置读数据超时时间(单位毫秒)
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        HttpGet httpGet = new HttpGet(request.requestUrl);
       
        HttpResponse response = client.execute(((HttpUriRequest)httpGet));
        MCHttpClient.logHeaders(response);
        String result = "";
        
        int code = response.getStatusLine().getStatusCode();
        if(code == 200) {
        	result = EntityUtils.toString(response.getEntity(),"UTF-8");
        }else if (code == 302) {
        	 System.out.println("请求返回状态不是200：" + code);
        }
        httpGet.abort();
        return result;
    }
    
    private static String getUserAgent(Context context) {
        String v7;
        MCNetworkStatus status;
        PackageInfo packageInfo = null;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        }
        catch(PackageManager.NameNotFoundException v1) {
            v1.printStackTrace();
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        boolean connect = ((ConnectivityManager)connectivityManager).getNetworkInfo(1).isConnectedOrConnecting();
        NetworkInfo networkInfo = ((ConnectivityManager)connectivityManager).getNetworkInfo(0);
        boolean connectStatus = false;
        if(networkInfo != null) {
            connectStatus = networkInfo.isConnectedOrConnecting();
        }

        if(connect) {
            status = MCNetworkStatus.MC_NETWORK_STATUS_WIFI;
        }
        else if(connectStatus) {
            status = MCNetworkStatus.MC_NETWORK_STATUS_WWAN;
        }
        else {
            status = MCNetworkStatus.MC_NETWORK_STATUS_NONE;
        }

        if(status == MCNetworkStatus.MC_NETWORK_STATUS_WWAN) {
            v7 = "2G/3G";
        }
        else if(status == MCNetworkStatus.MC_NETWORK_STATUS_WIFI) {
            v7 = "WIFI";
        }
        else {
            v7 = "Unkonw";
        }

        return String.format("%s/%s (Android %s; %s Build/%s),Network %s", "mukewang", packageInfo.versionName, Build.VERSION.RELEASE, String.valueOf(Build.BRAND) + " " + Build.MODEL, Build.ID, v7);
    }

    private static void logHeaders(HttpResponse httpResponse) {
        Header[] headers = httpResponse.getAllHeaders();
        int length = headers.length;
        int i;
        for(i = 0; i < length; ++i) {
            MCLog.i(MCHttpClient.TAG, String.valueOf(headers[i].getName()) + ":" + headers[i].getValue());
        }
    }

    /**
     * 文件上传核心方法
     * @param request
     * @param config
     * @param context
     * @return
     * @throws Exception
     */
	public static String getResponseFromHttpOfUpload(MCBaseRequest request, MCNetwokConfigInteface config, Context context) throws Exception {

		String str = "";
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 8000); // 设置读数据超时时间(单位毫秒)
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);
		HttpResponse httpResponse;
		MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		List<NameValuePair> fileParams = request.fileParams;
		if (fileParams != null) {
			for (NameValuePair v : fileParams) {
				String filePath = v.getValue();
				FileBody file1 = new FileBody(new File(filePath));
				reqEntity.addPart(v.getName(), file1);
			}
		}
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		if (request.requestUrl.startsWith("http://")) {
			String loginType = config.getLoginType(context);
			params.add(new BasicNameValuePair("loginType", loginType));
			params.add(new BasicNameValuePair("params.loginType", loginType));
			params.add(new BasicNameValuePair("token", loginType));
			String learnLoginType=MCSaveData.getUserInfo(Contants.LEARLOGINTYPE, context).toString();
            params.add(new BasicNameValuePair("unTyxlLoginToken", learnLoginType));
		} else {
			// 所有原来的都不再请求了，直接返回。
			return "";
		}
		
		HttpPost httpRequest = new HttpPost(request.requestUrl);
		
		params.add(new BasicNameValuePair("token", config.getToken(context,
				request.requestUrl)));

		Iterator<String> v8 = request.requestParams.keySet().iterator();
		while (v8.hasNext()) {
			String key = v8.next();
			if (request.requestParams.get(key) == null) {
				continue;
			}
			params.add(new BasicNameValuePair(key, request.requestParams.get(
					key).toString()));
		}

		for (NameValuePair v : params) {
			StringBody value = new StringBody(v.getValue());
			reqEntity.addPart(v.getName(), value);
		}
		httpRequest.setEntity(reqEntity);
		httpResponse = client.execute(httpRequest);
		int tmp = httpResponse.getStatusLine().getStatusCode();
		if (tmp == HttpStatus.SC_OK) {
			HttpEntity entity = httpResponse.getEntity();
			str = EntityUtils.toString(entity, "UTF-8");
		} else
			throw new RuntimeException("服务器错误" + tmp);
		return str;
	}
    
	public static void post(final MCBaseRequest request, final MCNetwokConfigInteface config, final Context context) {
		MCLog.d(TAG, "请求地址： " + request.requestUrl);
		// 从网络请求解析
		MCAsyncTask asyncTask = new MCAsyncTask(new MCAsyncTaskInterface() {
			public String exception;
			@Override
			public String DoInBackground(MCAsyncTask task) {
				String result = null;
				try {
					result = MCHttpClient.getResponseFromHttpClient(request,
							config, context);
				} catch (Exception e) {
					try {
						// 这里抛异常了
						result = MCHttpClient.getResponseFromHttpClient(request,
								config, context);
					} catch (Exception e1) {
						e1.printStackTrace();
						exception = e1.getMessage();
					}
				}

				return html2Str(result);
			}

			@Override
			public void DoAfterExecute(String result) {
				//ResultWriteMySDKCard.writeFileSdcardFile(request.requestUrl, result);
				//MCLog.e(TAG, "上线请注释掉我~~~~~");
				MCLog.d(TAG, "返回数据： " + result);
				if (request.listener != null) {
					if (result == null || result.isEmpty()) {
						request.listener.onNetworkBackListener(
								MCCommonResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE,
										this.exception), this.exception);
					} else {
						request.listener.onNetworkBackListener(MCCommonResult
								.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS,
										result), result);
					}
				}
			}

			@Override
			public void onProgressUpdate(Integer values) {
				
			}
		});
		asyncTask.executeOnExecutor(MCHttpClient.THREAD_POOL_EXECUTOR, 0);
    }

	/**
	 * get请求
	 * @param request
	 * @param config
	 * @param context
	 */
	public static void get(final MCBaseRequest request, final MCNetwokConfigInteface config, final Context context) {
		MCLog.d(TAG, "请求地址： " + request.requestUrl);
		// 从网络请求解析
		MCAsyncTask asyncTask = new MCAsyncTask(new MCAsyncTaskInterface() {
			public String exception;
			@Override
			public String DoInBackground(MCAsyncTask task) {
				String result = null;
				try {
					result = MCHttpClient.getResponseOfGetMethod(request,
							config, context);
				} catch (Exception e) {
					try {
						// 这里抛异常了
						result = MCHttpClient.getResponseOfGetMethod(request,
								config, context);
					} catch (Exception e1) {
						e1.printStackTrace();
						exception = e1.getMessage();
					}
				}

				return html2Str(result);
			}

			@Override
			public void DoAfterExecute(String result) {
				MCLog.d(TAG, "返回数据： " + result);
				if (request.listener != null) {
					if (result == null || result.isEmpty()) {
						request.listener.onNetworkBackListener(
								MCCommonResult.resultWithData(MCResultCode.MC_RESULT_CODE_FAILURE,
										this.exception), this.exception);
					} else {
						request.listener.onNetworkBackListener(MCCommonResult
								.resultWithData(MCResultCode.MC_RESULT_CODE_SUCCESS,
										result), result);
					}
				}
			}

			@Override
			public void onProgressUpdate(Integer values) {
				
			}
		});
		asyncTask.executeOnExecutor(MCHttpClient.THREAD_POOL_EXECUTOR, 0);
    }
	
	public static void upload(final MCBaseRequest request, final MCNetwokConfigInteface config, final Context context) {
		MCLog.d(TAG, "上传文件请求地址： " + request.requestUrl);
		// 从网络请求解析
		MCAsyncTask asyncTask = new MCAsyncTask(new MCAsyncTaskInterface() {
			public String exception;
			
			@Override
			public String DoInBackground(MCAsyncTask task) {
				String result = null;
				try {

					result = MCHttpClient.getResponseFromHttpOfUpload(request,
							config, context);
				} catch (Exception e) {
					exception = e.getMessage();
				}

				return html2Str(result);
			}

			@Override
			public void DoAfterExecute(String result) {
				MCLog.d(TAG, "上传文件返回数据： " + result);
				if (request.listener != null) {
					if (result == null || result.isEmpty()) {
						request.listener.onNetworkBackListener(MCCommonResult
								.resultWithData(
										MCResultCode.MC_RESULT_CODE_FAILURE,
										this.exception), this.exception);
					} else {
						request.listener.onNetworkBackListener(MCCommonResult
								.resultWithData(
										MCResultCode.MC_RESULT_CODE_SUCCESS,
										result), result);
					}
				}
			}

			@Override
			public void onProgressUpdate(Integer values) {
				
			}
		});
		asyncTask.executeOnExecutor(MCHttpClient.THREAD_POOL_EXECUTOR, 0);
    }
	
	
	
	private static String html2Str(String result){
		return StringUtils.htmlStrToTextStr(result);
		
	}
}

