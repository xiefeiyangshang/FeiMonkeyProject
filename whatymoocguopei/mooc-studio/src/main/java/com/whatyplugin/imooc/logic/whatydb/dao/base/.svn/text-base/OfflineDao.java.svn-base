package com.whatyplugin.imooc.logic.whatydb.dao.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;

import com.whatyplugin.imooc.logic.model.MCLearnOfflineRecord;
import com.whatyplugin.imooc.logic.whatydb.MCDBOpenHelper;

public class OfflineDao extends DaoSupport<MCLearnOfflineRecord> {
    public void insertOffline(List<MCLearnOfflineRecord> resultList, String sectionId, boolean flag) {
        try {
            List<MCLearnOfflineRecord> listT = queryByCondition(true, null, MCDBOpenHelper.TABLE_OFFLINE_ID + "=?", new String[]{sectionId}, null, null, null, null);
            int time = 0;
            if (listT.size() > 0 && !flag) {
                time = Integer.parseInt(listT.get(0).getStudyTime()) + Integer.parseInt(resultList.get(0).getStudyTime());
            }
            resultList.get(0).setStudyTime(time + "");
            Map<String, String> values = new HashMap<String, String>();
            values.put(MCDBOpenHelper.TABLE_OFFLINE_ID, sectionId);
            deleteBySql(values);
            insert(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getStudyTime(String sectionId) {
        int studyTime = 0;
        try {
            List<MCLearnOfflineRecord> listT = queryByCondition(true, null, MCDBOpenHelper.TABLE_OFFLINE_ID + "=?", new String[]{sectionId}, null, null, null, null);
            if (listT.size() > 0) {
                studyTime = Integer.parseInt(listT.get(0).getStudyTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studyTime;
    }

    public int updateStudyTime(String studyTime, String sectionId) {
        try {
            MCLearnOfflineRecord t = new MCLearnOfflineRecord();
            t.setId(sectionId);
            t.setStudyTime(studyTime);
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", sectionId);
            ContentValues cv = new ContentValues();
            cv.put("studyTime", studyTime);
            return updateBySql(map, cv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
