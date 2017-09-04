package com.whatyplugin.imooc.logic.whatydb.util;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

import android.speech.tts.TextToSpeech.Engine;

import com.whatyplugin.imooc.logic.whatydb.MCDBManager;
import com.whatyplugin.imooc.logic.whatydb.annotation.AccessDB;
import com.whatyplugin.imooc.logic.whatydb.annotation.Transaction;
import com.whatyplugin.imooc.logic.whatydb.dao.base.Dao;



/**
 * bean工厂
 * 
 * @author maizi
 */
public class BeanFactory {
	protected static final String TAG = "BeanFactory";
	private static Properties properties;

	private BeanFactory() {
	}

	static {
		try {
			properties = new Properties();
			properties.load(BeanFactory.class.getResourceAsStream("/bean.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载需要的Engine -- 并根据注解管理事务,管理数据库连接的释放
	 * 
	 * @param clazz
	 * @return null 请检查配置文件是否配置
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T extends Engine> T getEngine(Class<T> clazz) {
		try {
			final T t = (T) Class.forName(getFullClassName(clazz)).newInstance();
			// --创建代理对象代理Engine,改造每个方法,根据是否有@Transaction注解确定是否对该方法管理事务
			return (T) Proxy.newProxyInstance(clazz.getClassLoader(), t.getClass().getInterfaces(), new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					// 如果此Engine方法上有@Transaction,则管理事务
					if (method.isAnnotationPresent(Transaction.class)) {
						Object result = null;
						MCDBManager.beginTransaction();
						try {
							result = method.invoke(t, args);
							MCDBManager.setTransactionSuccessful();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
							throw e.getCause();
						} finally {
							MCDBManager.endTransaction();
							MCDBManager.release();
						}
						return result;
					} else if (method.isAnnotationPresent(AccessDB.class)) {
						// 如果此Engine方法上没有@Transaction,但有@AccessDB,则不管理事务,但要自动帮用户关闭数据库释放资源
						try {
							return method.invoke(t, args);
						} catch (InvocationTargetException e) {
							e.printStackTrace();
							throw e.getCause();
						} finally {
							MCDBManager.release();
						}
					} else { // 啥特殊注解都没有,那就还调原方法执行了呗
						try {
							return method.invoke(t, args);
						} catch (InvocationTargetException e) {
							e.printStackTrace();
							throw e.getCause();
						}
					}
				}
			});
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加载所需要的DAO
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Dao> T getDao(Class<T> clazz) {
		try {
			return (T) Class.forName(getFullClassName(clazz)).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取要加载类的完整类名
	 * 
	 * @param clazz
	 * @return 完整类名
	 */
	private static <T> String getFullClassName(Class<T> clazz) {
		String simpleName = clazz.getSimpleName();
		String fullClassName = properties.getProperty(simpleName);
		return fullClassName;
	}

}
