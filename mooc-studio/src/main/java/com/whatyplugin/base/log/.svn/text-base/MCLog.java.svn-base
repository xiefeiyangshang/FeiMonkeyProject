package com.whatyplugin.base.log;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MCLog {
    private static int LEVEL;
    private static boolean print;
    private static List<String> list = new ArrayList<String>();
    static {
        MCLog.LEVEL = 6;
        MCLog.print = false;
        list.add("MCHttpClient");
    }

    public MCLog() {
        super();
    }

    private static String createNewLogMessage(String msg) {
        return msg;
    }

    public static void d(String TAG, String msg) {
        if((MCLog.print) && MCLog.LEVEL >= 3) {
            Log.d(TAG, MCLog.createNewLogMessage(msg));
        }
    }

    public static void e(String TAG, String msg) {
        if((MCLog.print) && MCLog.LEVEL >= 6) {
            Log.e(TAG, MCLog.createNewLogMessage(msg));
        }
    }

    private static String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
 
		if (sts == null) {
			return null;
		}
 
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
 
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
 
//			if (st.getClassName().equals(this.getClass().getName())) {
//				continue;
//			}
// 
			return "[" + Thread.currentThread().getName() + "(" + Thread.currentThread().getId()
					+ "): " + st.getFileName() + ":" + st.getLineNumber() + "]";
		}
 
		return null;
	}

    public static void i(String TAG, String msg) {
        if((MCLog.print) && MCLog.LEVEL >= 4) {
            Log.i(TAG, MCLog.createNewLogMessage(msg));
        }
        
      
    }

    public static void setMCLogLevel(int level) {
        MCLog.LEVEL = level;
    }

    public static void v(String TAG, String msg) {
        if((MCLog.print) && MCLog.LEVEL >= 2) {
            Log.v(TAG, MCLog.createNewLogMessage(msg));
        }
    }

    public static void w(String TAG, String msg) {
        if((MCLog.print) && MCLog.LEVEL >= 5) {
            Log.w(TAG, MCLog.createNewLogMessage(msg));
        }
    }
}

