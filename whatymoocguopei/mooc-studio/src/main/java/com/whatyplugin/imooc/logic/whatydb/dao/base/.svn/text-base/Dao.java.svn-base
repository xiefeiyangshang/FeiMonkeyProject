package com.whatyplugin.imooc.logic.whatydb.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * 通用的数据库操作接口
 * 
 * @author maizi
 * @param <T>
 */
public interface Dao<T> {

	/**
	 * 添加
	 * 
	 * @param t
	 *            eturn
	 */
	public boolean insert(T t);

	/**
	 * 按主键删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(Serializable id);// int long String

	/**
	 * 按主键更新
	 * 
	 * @param t
	 * @return
	 */
	public boolean update(T t);

	/**
	 * 查询所有
	 * 
	 * @return null表示查询失败
	 */
	public List<T> queryAll();

	public void execSql(String sql);
  
	/**
	 * 
	 * 根据SQL语句查询
	 */
	public List<T> findListByColumns(String columns,Map<String,String> values);

	/**
	 * 根据传入参数获取相应的数据
	 * 
	 */

	public List<T> findListByMap(Map<String,String> values);
	/**
	 * 批量插入
	 * @param listT
	 * @return
	 */
	public int insert(List<T> listT);
	/**
	 * 唯一性条件，查出的数据只有一个
	 * @param values
	 * @return
	 */
	public T findOneTByMap(Map<String,String> values);
	/**
	 * 唯一性条件，查出的数据只有一个
	 * @param columns  是你想显示的列 比如  id,name,userid,age
	 * @param values
	 * @return
	 */
	public T findOneTByColumns(String columns, Map<String, String> values);
     /**
      * 根据ID批量删除
      * 
      * @param listT
      * @return
      */
	public int deleteList(List<T> listT);
    /**
     * 根据ben删除所有的这个Ben的表数据
     * 
     * @return
     */
	public int deleteAllTable();
      /**
       * 根据T。ID删除某个数据
       * 
       * @param t
       * @return
       */
	public boolean delete(T t);
      /**
       * 根据ID 批量跟新
       * 
       * @param listT
       * @return
       */
	public int updateList(List<T> listT);
    /**
     * 根据ben批量更新table中所有的数据
     * 
     * @param Values
     * @return
     */
	public int updateAllTable(ContentValues Values);
	 /**
	  * 根据SQL语句更新
	  * 
	  * @param SQL
	  * @param values
	  * @return
	  */
	public int updateBySql(Map<String,String> whereMap,ContentValues values);
	/**
	 * 根据SQL语句删除
	 * @param SQL
	 * @param values
	 * @return
	 */
	public int deleteBySql(Map<String,String> values);
	
	
}
