package com.whatyplugin.imooc.logic.whatydb.dao.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.imooc.logic.model.MCNotice;
import com.whatyplugin.imooc.logic.utils.ListUtils;
import com.whatyplugin.imooc.logic.whatydb.MCDBOpenHelper;

/**
 * getTableName 为表名字在类的注解上
 * @author add bzy
 */
public class NoticDao extends DaoSupport<MCNotice> {
	/**
	 * 更新数据库中的公告列表
	 * @param resultList 从服务器返回的列表
	 * @param courseId   课程id
	 */
	public void updateNotices(List<MCNotice> resultList, String courseId) {
		
		// 查询本课程的所有通知
		List<MCNotice> queryAll = queryByCondition(true, null, MCDBOpenHelper.TABLE_NOTIC_COURSEID + "=?", new String[] { courseId }, null, null, null, null);

		// 如果数据库中无数据或者数据库中的数据和线上最新的不一致, 
		// 把数据库中本课程中的所有公告清空,然后插入新的
		if (queryAll == null || queryAll.size() < 1 
				|| !ListUtils.compare(queryAll, resultList)) {
			
			Map<String, String> values = new HashMap<String, String>();
			values.put(MCDBOpenHelper.TABLE_NOTIC_COURSEID, courseId);
			deleteBySql(values);
			
			// 插入新的公告到数据库
			insert(resultList);
		}else{
			MCLog.d("NoticDao", "数据相同");
		}
	}
}
