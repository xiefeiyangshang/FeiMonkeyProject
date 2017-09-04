package com.whatyplugin.imooc.logic.db;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.whatyplugin.imooc.logic.db.DBCommon.OfflineReportColums;
import com.whatyplugin.imooc.logic.model.MCBaseOfflineModel;

public class OfflineReportDBHandle extends DBHandle {
    public OfflineReportDBHandle(Context context) {
        super(context);
    }

    public void deleteOfflineReport(MCBaseOfflineModel model) {
        this.delete(OfflineReportColums.CONTENT_URI_OFFLINEREPORT, "_id = " + model.getId());
    }

    public List<MCBaseOfflineModel> getOfflineReport() {
        Cursor cursor = null;
        List<MCBaseOfflineModel> list = new ArrayList<MCBaseOfflineModel>();
        try {
            cursor = this.mContext.getContentResolver().query(OfflineReportColums.CONTENT_URI_OFFLINEREPORT, OfflineReportColums.projects, null, null, null);
            while(cursor.moveToNext()) {
                MCBaseOfflineModel model = new MCBaseOfflineModel();
                model.setId(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                model.setUid(cursor.getInt(cursor.getColumnIndexOrThrow("uid")));
                model.setCourseId(cursor.getString(cursor.getColumnIndexOrThrow("courseid")));
                model.setChapterId(cursor.getString(cursor.getColumnIndexOrThrow("chapterid")));
                model.setSectionId(cursor.getString(cursor.getColumnIndexOrThrow("sectionid")));
                model.setValue1(cursor.getString(cursor.getColumnIndexOrThrow("value1")));
                model.setValue2(cursor.getString(cursor.getColumnIndexOrThrow("value2")));
                model.setValue3(cursor.getString(cursor.getColumnIndexOrThrow("value3")));
                model.setInsertTime(cursor.getLong(cursor.getColumnIndexOrThrow("inserttime")));
                model.setReportType(cursor.getInt(cursor.getColumnIndexOrThrow("reporttype")));
                list.add(model);
            }
        } catch(Exception e) {
        	e.printStackTrace();
        } finally{
        	if(cursor!=null){
        		cursor.close();
        	}
        }
        return list;
    }

    public void saveOfflineReport(MCBaseOfflineModel model) {
        if(model != null) {
            ContentValues content = new ContentValues();
            content.put("uid", Integer.valueOf(model.getUid()));
            content.put("courseid", Integer.valueOf(model.getCourseId()));
            content.put("chapterid", Integer.valueOf(model.getChapterId()));
            content.put("sectionid", Integer.valueOf(model.getSectionId()));
            content.put("value1", model.getValue1());
            content.put("value2", model.getValue2());
            content.put("value3", model.getValue3());
            content.put("inserttime", Long.valueOf(model.getInsertTime()));
            content.put("reporttype", Integer.valueOf(model.getReportType()));
            this.insert(OfflineReportColums.CONTENT_URI_OFFLINEREPORT, content);
        }
    }
}

