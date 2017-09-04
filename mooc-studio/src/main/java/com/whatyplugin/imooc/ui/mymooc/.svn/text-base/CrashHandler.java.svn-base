package com.whatyplugin.imooc.ui.mymooc;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.whatyplugin.imooc.logic.contants.Contants;
import com.whatyplugin.imooc.logic.model.MCCrashInfo;
import com.whatyplugin.imooc.logic.model.MCServiceResult;
import com.whatyplugin.imooc.logic.save.MCSaveData;
import com.whatyplugin.imooc.logic.service_.MCAnalyzeBackBlock;
import com.whatyplugin.imooc.logic.service_.MCSettingService;
import com.whatyplugin.imooc.logic.utils.Const;
import com.whatyplugin.imooc.logic.utils.SendMailUtil;


/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类
 * 来接管程序,并记录 发送错误报告.
 *
 */
public class CrashHandler implements UncaughtExceptionHandler {
	/** Debug Log tag*/
	public static final String TAG = "CrashHandler";
	/** 是否开启日志输出,在Debug状态下开启,
	 * 在Release状态下关闭以提示程序性能
	 * */
	public static boolean EXIT = false;
	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	/** CrashHandler实例 */
	private static CrashHandler INSTANCE;
	/** 程序的Context对象 */
	private Context mContext;
	
	private String VERSION_NAME;
	private String VERSION_CODE;
	private String NAME;
	private String courseName = "";
	
	/** 错误报告文件的扩展名 */
	private static final String CRASH_REPORTER_EXTENSION = ".txt";
	
	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {}
	/** 获取CrashHandler实例 ,单例模式*/
	public static CrashHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CrashHandler();
		}
		return INSTANCE;
	}

	/**
	 * 初始化,注册Context对象,
	 * 获取系统默认的UncaughtException处理器,
	 * 设置该CrashHandler为程序的默认处理器
	 * 
	 * @param ctx
	 */
	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public void setCouseName(String name){
		if(name != null)
			courseName = name;
	}
	
	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	public void uncaughtException(Thread thread, Throwable ex) {
		EXIT = true;
		Log.e("tag", "异常信息" + ex.toString());
		Log.e("tag", "set exit true");
		if (!handleException(ex) && mDefaultHandler != null) {
			//如果用户没有处理则让系统默认的异常处理器来处理
		//	mDefaultHandler.uncaughtException(thread, ex);
			Log.e("tag", "default crash");
		} else {
			//Sleep一会后结束程序
			Log.e("tag", "start crash sleep");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Log.e("tag", "Error : " + e.toString());
			}
			Log.e("tag", "finish crash sleep");
		}
		Log.e("tag", "set exit, kill myself");
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
		ActivityManager activityMgr = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		activityMgr.killBackgroundProcesses("cn.com.whatyplugin.mooc");
	}

	/**
	 * 自定义错误处理,收集错误信息
	 * 发送错误报告等操作均在此完成.
	 * 开发者可以根据自己的情况来自定义异常处理逻辑
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false
	 */
	private boolean handleException(final Throwable ex) {
		try{
			if (ex == null) {
				return true;
			}
			
			MCCrashInfo crashInfo = new MCCrashInfo();
			
			//收集设备信息
			collectCrashDeviceInfo(mContext);
			//保存错误报告文件
			String err = saveCrashInfoToFile(ex);
			//发送错误报告到服务器
			final StringBuffer sb = new StringBuffer();
			sb.append(NAME+"\n"+android.os.Build.VERSION.SDK_INT+"\n"+VERSION_NAME+"\n"+VERSION_CODE+"\n"+MCSaveData.getUserInfo(Contants.UID, mContext).toString()+"\n"+courseName+"\n");
//				sb.append(Whatyurls.info.password + "\n");
			try{
				TelephonyManager phoneMgr=(TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
				String str = phoneMgr.getLine1Number();
				sb.append("phone: " + str + "\n");
			}catch(Exception e){
				Log.e("tag", "get phone number error " + e.toString());
			}
			Field[] fields = Build.class.getDeclaredFields();
			String mobileModel  = "";
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					String key = field.getName().toLowerCase(Locale.US);
					if("model".equals(key)){
						mobileModel = field.get(null).toString();
					}
					
					//手机型号
					if("device".equals(key)){
						crashInfo.setPhoneType(field.get(null).toString());
					}
					sb.append(key +":\t"+field.get(null).toString());
					sb.append("\n");
				} catch (Exception e) {
					Log.e("tag", e.toString());
				}
			}
			
			crashInfo.setRemarks(sb.toString());
			sb.append(err);
			crashInfo.setCrashInfo(err);
			crashInfo.setPackageName(NAME);
			crashInfo.setVersion(VERSION_NAME);
			crashInfo.setExceptionType(ex.toString());
			final String title = "【重要】 - MOOC崩溃记录[" + android.os.Build.VERSION.SDK_INT + " | " + VERSION_NAME + " | " + mobileModel + "] "
					+ (ex == null ? "" : ex.getMessage());
			String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/fzxxlog";
			File f = new File(path);
			f.mkdirs();
			if(f.exists() && f.isDirectory()){
				File file = new File(path+"/"+"crash"+System.currentTimeMillis()+".txt");
				try{
					FileWriter fw = new FileWriter(file);
					fw.write(sb.toString());
					fw.flush();
					fw.close();
				}catch(Exception e){}
			}
			
			//保存崩溃日志到问题反馈
			if(Const.COLLECTION_CRASH_COMPANY){
				MCSettingService service = new MCSettingService();
				service.sendCrashInfo(crashInfo, new MCAnalyzeBackBlock() {
					@Override
					public void OnAnalyzeBackBlock(MCServiceResult result, List resultList) {
						
					}
				}, mContext);
			}
			
			//发送邮件
			new Thread() {
				@Override
				public void run() {
					try {
						SendMailUtil.sendEmailInner(sb.toString(), title);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}.start();
			
		
		}catch(Exception e){
			Log.e("tag", e.toString());
		}
		return true;
	}

	/**
	 * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
	 */
	public void sendPreviousReportsToServer() {
		sendCrashReportsToServer(mContext);
	}

	/**
	 * 把错误报告发送给服务器,包含新产生的和以前没发送的.
	 * 
	 * @param ctx
	 */
	private void sendCrashReportsToServer(Context ctx) {
		String[] crFiles = getCrashReportFiles(ctx);
		if (crFiles != null && crFiles.length > 0) {
			TreeSet<String> sortedFiles = new TreeSet<String>();
			sortedFiles.addAll(Arrays.asList(crFiles));

			for (String fileName : sortedFiles) {
				File cr = new File(ctx.getFilesDir(), fileName);
				postReport(cr);
//				cr.delete();// 删除已发送的报告
			}
		}
	}

	private void postReport(File file) {
		// TODO 使用HTTP Post 发送错误报告到服务器
		// 这里不再详述,开发者可以根据OPhoneSDN上的其他网络操作
		// 教程来提交错误报告
	}

	/**
	 * 获取错误报告文件名
	 * @param ctx
	 * @return
	 */
	private String[] getCrashReportFiles(Context ctx) {
		File filesDir = ctx.getFilesDir();
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(CRASH_REPORTER_EXTENSION);
			}
		};
		return filesDir.list(filter);
	}
	/**
	 * 保存错误信息到文件中
	 * @param ex
	 * @return
	 */
	private String saveCrashInfoToFile(Throwable ex) {
		Writer info = new StringWriter();
		PrintWriter printWriter = new PrintWriter(info);
		ex.printStackTrace(printWriter);
		ex.printStackTrace();

		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}

		String result = info.toString();
		printWriter.close();
		return result;
/*
		try {
			long timestamp = System.currentTimeMillis();
			String fileName = "/mnt/sdcard/" + "crash" + timestamp + CRASH_REPORTER_EXTENSION;
			File file = new File(fileName);
			OutputStream os = new FileOutputStream(file);  
			os.write(("VERSION_NAME is "+VERSION_NAME+"\n").getBytes());
			os.write(("VERSION_CODE is "+VERSION_CODE+"\n").getBytes());
			os.write(result.getBytes());
			os.flush();
			os.close();
			return result;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing report file...", e);
		}
		return "";
		*/
	}


	/**
	 * 收集程序崩溃的设备信息
	 * 
	 * @param ctx
	 * @throws Exception 
	 */
	private void collectCrashDeviceInfo(Context ctx) throws Exception {
		PackageManager pm = ctx.getPackageManager();
		PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
				PackageManager.GET_ACTIVITIES);
		if (pi != null) {
			NAME = pi.packageName == null ? "not set" : pi.packageName;
			VERSION_NAME = pi.versionName == null ? "not set" : pi.versionName;
			VERSION_CODE = pi.versionCode+"";
		}
	}
}