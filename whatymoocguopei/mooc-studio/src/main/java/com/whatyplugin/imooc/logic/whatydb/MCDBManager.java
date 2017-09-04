package com.whatyplugin.imooc.logic.whatydb;

import com.whatyplugin.imooc.ui.mymooc.MoocApplication;

import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings.Global;
import android.util.Log;

/**
 * 数据库对象的管理类
 * 
 * @author maizi
 */
public class MCDBManager {

	private static final String TAG = "MCDBManager";
	private static ThreadLocal<SQLiteDatabase> db_local = new ThreadLocal<SQLiteDatabase>();
	private static ThreadLocal<Boolean> trans_local = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return false;
		}
	};

	private MCDBManager() {
	}

	/** 获取一个可读写的数据库对象 */
	public static SQLiteDatabase getDB() {
		Log.i(TAG, "Thread:" + Thread.currentThread().getName());
		try {
			// 对于同一个线程返回同一个连接
			if (db_local.get() == null) {
				SQLiteDatabase db = new MCDBOpenHelper(MoocApplication.getInstance()).getWritableDatabase();
				if (trans_local.get())// 该线程对应的业务操作需要开启事务,返回同一个开启过事务的连接
					db.beginTransaction();
				db_local.set(db);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return db_local.get();
	}

	public static void beginTransaction() {
		trans_local.set(true);
	}

	public static void setTransactionSuccessful() {
		if (db_local.get() != null)
			db_local.get().setTransactionSuccessful();
	}

	public static void endTransaction() {
		if (db_local.get() != null)
			db_local.get().endTransaction();
	}

	/**
	 * 当操作完数据库后释放资源 (为了提高效率,在一个业务方法中操作进行一次数据库操作后
	 * 不应该马上关闭数据库连接,而应该让该业务方法中所有数据库的操作都使用同一个连接,等到 业务方法执行完之后才关闭数据库连接
	 */
	public static void release() {
		if (db_local.get() != null) {
			db_local.get().close();
		}
		db_local.remove();
		trans_local.remove();
	}
}
