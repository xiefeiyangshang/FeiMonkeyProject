package com.whatyplugin.imooc.logic.db;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.whatyplugin.imooc.logic.db.DBCommon.AddressColumns;
import com.whatyplugin.imooc.logic.db.DBCommon.CacheColumns;
import com.whatyplugin.imooc.logic.db.DBCommon.ChapterColumns;
import com.whatyplugin.imooc.logic.db.DBCommon.CourseColumns;
import com.whatyplugin.imooc.logic.db.DBCommon.DownloadColumns;
import com.whatyplugin.imooc.logic.db.DBCommon.HomeworkColumns;
import com.whatyplugin.imooc.logic.db.DBCommon.MediaDurationColumns;
import com.whatyplugin.imooc.logic.db.DBCommon.MsgColumns;
import com.whatyplugin.imooc.logic.db.DBCommon.OfflineReportColums;
import com.whatyplugin.imooc.logic.db.DBCommon.SectionColumns;
import com.whatyplugin.imooc.logic.db.DBCommon.UserColumns;

public class DBOpenHelper extends SQLiteOpenHelper {
	
	//数据库的版本号是从8开始的， 每次更新数据库这里要发生改变。
    public DBOpenHelper(Context context) {
        super(context, "mooc.db", null, 10);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DownloadColumns.CREATE_TABLE_DOWNLOADINFO);
        db.execSQL(HomeworkColumns.CREATE_TABLE_SQL);
        db.execSQL(CourseColumns.CREATE_TABLE_COURSE);
        db.execSQL(ChapterColumns.CREATE_TABLE_CHAPTER);
        db.execSQL(SectionColumns.CREATE_TABLE_SECTION);
        db.execSQL(MsgColumns.CREATE_TABLE_MSG);
        db.execSQL(UserColumns.CREATE_TABLE_USER);
        db.execSQL(AddressColumns.CREATE_TABLE_ADDRESS);
        db.execSQL(CacheColumns.CREATE_TABLE_CACHE);
        db.execSQL(MediaDurationColumns.CREATE_TABLE_MEDIADURATION);
        db.execSQL(OfflineReportColums.CREATE_TABLE_OFFLINEREPORT);
    }

    /**
     * 升级的时候，这里针对不同版本的数据库进行更新。case后面不要加break， 方便版本相差太多的升级
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            switch(oldVersion) {
                case 8: {
         
                    //下载资料表添加了2个字段
                    try {
						db.execSQL("ALTER TABLE " + MoocContentProvider.DOWNLOADINFO_TABLE_NAME + " ADD COLUMN type VARCHAR ;");
						db.execSQL("ALTER TABLE " + MoocContentProvider.DOWNLOADINFO_TABLE_NAME + " ADD COLUMN resourceSection VARCHAR ;");
					} catch (SQLException e) {
						e.printStackTrace();
					}
                    
                    //类型全部设置为缓存视频的，因为这之前的数据肯定都是缓存视频的
                	try {
						db.execSQL("update " + MoocContentProvider.DOWNLOADINFO_TABLE_NAME + " set type='0' ;");
					} catch (SQLException e) {
						e.printStackTrace();
					}
 
                    //新建了一个作业表
                    try {
						db.execSQL(HomeworkColumns.CREATE_TABLE_SQL);
					} catch (SQLException e) {
						e.printStackTrace();
					}
                }
                case 9:
                	//为作业添加一个USERID字段
                	try{
                		db.execSQL("ALTER TABLE " + MoocContentProvider.HOMEWORK_TABLE_NAME + " ADD LOGINID type VARCHAR ;");
                	}catch(Exception e){
                		e.printStackTrace();
                	}
                	
                
             
        }
    }
    
    /**
     * 改变列名,  仅限varchar类型的, 不支持删除列，还是不能用啊
     */
    public void reNameColumn(SQLiteDatabase db, String tableName, String newColumn, String newType){
    	
    	//添加一个备份表
    	String addColumn = "ALTER TABLE " + tableName + " ADD COLUMN "+ newColumn + "_bak  " + newType +" ;";
    	
    	//开始备份原始列
    	String bakSql = "update " + tableName + " set " + newColumn + "_bak" + "=" + newColumn;
    	
    	String addNewColumn = "ALTER TABLE " + tableName + " ADD COLUMN "+ newColumn +" VARCHAR ;";
    	
    	String resumeSql = "update " + tableName + " set " + newColumn + "=" + newColumn + "_bak" ;
    
    	try {
			db.execSQL(addColumn);
			db.execSQL(bakSql);
			db.execSQL(addNewColumn);
			db.execSQL(resumeSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
}

