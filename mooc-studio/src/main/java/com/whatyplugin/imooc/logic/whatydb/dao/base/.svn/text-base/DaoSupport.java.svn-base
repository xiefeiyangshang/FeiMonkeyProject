package com.whatyplugin.imooc.logic.whatydb.dao.base;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.whatyplugin.imooc.logic.whatydb.MCDBManager;
import com.whatyplugin.imooc.logic.whatydb.annotation.Column;
import com.whatyplugin.imooc.logic.whatydb.annotation.ID;
import com.whatyplugin.imooc.logic.whatydb.annotation.TableName;

/**
 * 通用的数据库操作的实现 问题一：表名的获取 问题二：将实体中的数据设置给表中具体的列（对应关系） 问题三：获取到实体中主键的值
 * 问题四：将数据库表中列的数据设置给实体对应的属性 问题五：创建实体的对象
 * 
 * @author maizi
 * @param <T>
 */
public class DaoSupport<T> implements Dao<T> {

	private static final String TAG = "DaoSupport";
	protected SQLiteDatabase db;

	public DaoSupport() {
		db = MCDBManager.getDB();
	}

	@Override
	public boolean insert(T t) {
		ContentValues values = new ContentValues();
		fillContentValues(t, values);
		return db.insert(getTableName(), null, values) != -1;
	}

	@Override
	public void execSql(String sql) {
		db.execSQL(sql);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean delete(Serializable id) {
		return db.delete(getTableName(), getSpecifyKey(ID.class) + "=? ", new String[] { id.toString() }) > 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean update(T t) {
		ContentValues values = new ContentValues();
		fillContentValues(t, values);
		return db.update(getTableName(), values, getSpecifyKey(ID.class) + "=? ", new String[] { getSpecifyKey(ID.class, t) }) != 0;
	}

	@Override
	public List<T> queryAll() {
		return queryByCondition(null, null, null, null);
	}

	/**
	 * 按条件查询
	 * 
	 * @param distinct
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @param limit
	 * @return null表示操作失败了
	 */
	public List<T> queryByCondition(boolean distinct, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		Cursor cursor = db.query(distinct, getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		return cursorToList(cursor);
	}
	
	/**
	 * 按条件查询
	 * 
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @param limit
	 * @return null表示操作失败了
	 */
	protected List<T> queryByCondition(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
		return queryByCondition(false, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}

	/**
	 * 按条件查询
	 * 
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param orderBy
	 * @return null表示操作失败了
	 */
	protected List<T> queryByCondition(String[] columns, String selection, String[] selectionArgs, String orderBy) {
		return queryByCondition(columns, selection, selectionArgs, null, null, orderBy, null);
	}

	/**
	 * 问题一：表名的获取
	 * 
	 * @return
	 */
	protected String getTableName() {
		// 方式一：获取需要操作的实体，将实体的简单名称看成表名
		// 方式二：获取需要操作的实体，利用注解
		T t = getInstance();
		if (t != null) {
			TableName tableName = t.getClass().getAnnotation(TableName.class);
			if (tableName != null) {
				return tableName.value();
			}
		}
		return "";
	}

	/**
	 * 问题二：将实体中的数据设置给表中具体的列（对应关系）
	 * 
	 * @param t
	 * @param values
	 * @param isFillPrimaryKey
	 *            是否填充主键
	 */
	protected void fillContentValues(T t, ContentValues values, boolean... isFillPrimaryKey) {
		Field[] fields = t.getClass().getDeclaredFields();
		for (Field item : fields) {
			item.setAccessible(true);// 暴力反射
			Column column = item.getAnnotation(Column.class);
			if (column != null) {
				ID id = item.getAnnotation(ID.class);
				if (id != null) {
					// 主键+自增
					if (id.autoIncrement()) {
						// 默认不需要设置数据到ContentValues
						if (isFillPrimaryKey.length > 0 && isFillPrimaryKey[0] == true) {// 需要设置
							String key = column.value();
							try {
								String value = item.get(t).toString();
								values.put(key, value);
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
						}
					} else {
						String key = column.value();
						try {
							String value = item.get(t).toString();
							values.put(key, value);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				} else {
					String key = column.value();
					try {
						Object object = item.get(t);
						if (object != null) {// 如果属性有值就设置上去
							String value = item.get(t).toString();
							values.put(key, value);
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}

	/**
	 * 问题四：将数据库表中列的数据设置给实体对应的属性
	 * 
	 * @param cursor
	 * @param m
	 */
	@SuppressWarnings("rawtypes")
	protected void fillFields(Cursor cursor, T t) {
		Class clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field item : fields) {
			item.setAccessible(true);
			Column column = item.getAnnotation(Column.class);
			if (column != null) {
				String columnName = column.value();
				int columnIndex = cursor.getColumnIndex(columnName);
				if (columnIndex != -1) {// 如果等于-1,说明当前结果集中没有该字段
					String value = cursor.getString(columnIndex);
					if (value != null) {// value不为空的话
						if (item.getType() == String.class) {
							try {
								item.set(t, value);
								continue;
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						try {// 基本数据类型的包装类都有这个静态方法
							Method method = item.getType().getMethod("valueOf", String.class);
							Object ret = method.invoke(item.getType(), value);
							item.set(t, ret);
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}

	/**
	 * 问题五：创建实体的对象
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected T getInstance() {
		Type genericSuperclass = getGenericSuperclass();
		// ③获取到泛型中携带参数
		// 但凡泛型都会实现接口：接口（泛型操作公共部分），参数化的类型
		if (genericSuperclass instanceof ParameterizedType) {
			Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
			Class target = (Class) actualTypeArguments[0];
			try {
				return (T) target.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取bean中指定字段对应的数据库列名 或者 该bean中该属性所所对应的值
	 * 
	 * @param t
	 *            获取主键列名不用传参,获取某个bean该属性的值则传入对应的bean
	 * @return 主键的列名 or 该bean种主键所对应的值
	 */
	@SuppressWarnings("rawtypes")
	protected String getSpecifyKey(Class<? extends Annotation> clazz, T... t) {
		Type genericSuperclass = getGenericSuperclass();
		if (genericSuperclass instanceof ParameterizedType) {
			Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
			Class target = (Class) actualTypeArguments[0];
			Field[] declaredFields = target.getDeclaredFields();
			for (Field item : declaredFields) {
				// 暴力反射
				Annotation annotation = item.getAnnotation(clazz);
				item.setAccessible(true);
				if (annotation != null) {
					if (t.length == 0) {
						Column column = item.getAnnotation(Column.class);
						if (column != null) {
							return column.value();
						}
					} else
						try {
							return item.get(t[0]).toString();

						} catch (Exception e) {
							e.printStackTrace();
						}
				}
			}
		}
		return null;
	}

	/**
	 * 获取带泛型的父类
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Type getGenericSuperclass() {
		// ①获取到具体子类:正在运行的子类
		Class clazz = getClass();// 正在运行
		// ②获取到该子类的支持泛型的父类
		// Class superclass = clazz.getSuperclass();// 获取不到泛型内容
		Type genericSuperclass = clazz.getGenericSuperclass();// 获取支持泛型的父类
		return genericSuperclass;
	}

	@Override
	public List<T> findListByMap(Map<String, String> values) {

		if (values == null || values.size() == 0)
			return queryAll();

		Map<String, String[]> map = MapToString(values);
		return queryByCondition(null, map.get("whereClause")[0], map.get("whereArgs"), null);
	}

	@Override
	public List<T> findListByColumns(String columns, Map<String, String> values) {

		Map<String, String[]> map = MapToString(values);
		String SQL = "select  " + columns + "  from " + getTableName() + "  where " + map.get("whereClause")[0];
		return cursorToList(db.rawQuery(SQL, map.get("whereArgs")));
	}

	public List<T> cursorToList(Cursor cursor) {
		ArrayList<T> result = null;
		if (cursor != null) {
			result = new ArrayList<T>();
			while (cursor.moveToNext()) {
				T t = getInstance();
				fillFields(cursor, t);
				result.add(t);
			}
			cursor.close();

		}
		return result;
	}

	@Override
	public int insert(List<T> listT) {
		int sum = 0;
		// 开启事务
		db.beginTransaction();
		for (T t : listT) {
			sum += (insert(t) ? 1 : 0);
		}
		db.setTransactionSuccessful();// 设置事务处理成功
		db.endTransaction();// 事务结束
		return sum;
	}

	@Override
	public T findOneTByMap(Map<String, String> values) {
		return isOneT(findListByMap(values));
	}

	@Override
	public T findOneTByColumns(String columns, Map<String, String> values) {
		return isOneT(findListByColumns(columns, values));
	}

	public T isOneT(List<T> listT) {
		if (listT == null)
			return null; // 报错 就返回 null
		int size = listT.size();
		if (size == 0)
			return getInstance(); // 查询不到，就返回空的实体类
		if (size > 1) {
			Log.e("DAO", "返回数据为多个请使用findListBy*()方法，这里默认返回第一个");
			return listT.get(0);
		}
		if (size == 1)
			return listT.get(0);

		return null;
	}

	@Override
	public int deleteList(List<T> listT) {
		int sum = 0;
		db.beginTransaction();
		for (T t : listT) {
			sum += (delete(t) ? 1 : 0);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		return sum;
	}

	@Override
	public int deleteAllTable() {
		return db.delete(getTableName(), null, null);
	}

	@Override
	public int updateList(List<T> listT) {
		db.beginTransaction();
		int sum = 0;
		for (T t : listT) {
			sum += (update(t) ? 1 : 0);
		}

		db.setTransactionSuccessful();
		db.endTransaction();

		return sum;
	}

	@Override
	public int updateAllTable(ContentValues values) {
		return db.update(getTableName(), values, null, null);
	}

	@Override
	public boolean delete(T t) {
		return db.delete(getTableName(), getSpecifyKey(ID.class) + "=? ", new String[] { getSpecifyKey(ID.class, t) }) != -1;
	}

	@Override
	public int updateBySql(Map<String, String> whereMap, ContentValues values) {
		Map<String, String[]> map = MapToString(whereMap);
		return db.update(getTableName(), values, map.get("whereClause")[0], map.get("whereArgs"));
	}

	@Override
	public int deleteBySql(Map<String, String> values) {
		Map<String, String[]> map = MapToString(values);
		return db.delete(getTableName(), map.get("whereClause")[0], map.get("whereArgs"));
	}

	public Map<String, String[]> MapToString(Map<String, String> map) {
		Map<String, String[]> mapValues = new HashMap<String, String[]>();
		Set<String> SetWhere = map.keySet();
		String selection = "";
		String[] selectionArgs = new String[map.size()];
		int index = 0;
		for (String whereStr : SetWhere) {
			selection = selection + whereStr + " =? AND ";
			selectionArgs[index] = map.get(whereStr);
			index++;
		}
		selection = selection + "1=1";

		mapValues.put("whereClause", new String[] { selection });
		mapValues.put("whereArgs", selectionArgs);
		return mapValues;

	}

}
