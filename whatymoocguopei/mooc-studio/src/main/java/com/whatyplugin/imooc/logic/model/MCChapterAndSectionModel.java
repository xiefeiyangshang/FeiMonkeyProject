package com.whatyplugin.imooc.logic.model;
import com.whatyplugin.base.define.MCBaseDefine;
import com.whatyplugin.base.define.MCBaseDefine.MCDownloadNodeType;
import com.whatyplugin.base.model.MCDataModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
public class MCChapterAndSectionModel extends MCDataModel implements Serializable {
    public static final int ITEM = 0;
    public static final int SECTION = 1;
    private MCChapterModel chapter;
    private MCSectionModel section;
    private MCDownloadNodeType downloadType;
    private int type;
    private String id;

    public MCChapterAndSectionModel() {
        super();
    }

    public boolean equals(Object obj) {
        boolean flag = false;
        if ((obj instanceof MCChapterAndSectionModel)) {
            MCChapterAndSectionModel model = (MCChapterAndSectionModel) obj;
            if (this.getSection() != null && this.getSection().getId() == (model).getSection().getId()) {
                flag = true;
            }
        }
        return flag;
    }

    public MCChapterModel getChapter() {
        return this.chapter;
    }

    public MCSectionModel getSection() {
        return this.section;
    }

    public int getType() {
        return type;
    }

    public MCChapterAndSectionModel modelWithData(Object data) {
        MCChapterAndSectionModel v1 = null;
        String v5 = data.toString();
        if (v5 != null && v5.length() > 0) {
            v1 = new MCChapterAndSectionModel();
            try {
                JSONObject v6 = new JSONObject(v5);
                JSONObject v3 = new JSONObject(v6.getString("chapter"));
                MCChapterModel model = new MCChapterModel();
                model.setId(v3.getString("id"));
                model.setName(v3.getString("name"));
                model.setSeq(v3.getInt("seq"));
                model.setCourseId(v3.getInt("cid"));
                new JSONArray(v6.getString("section"));
                new MCSectionModel();
            } catch (Exception v4) {
                v4.printStackTrace();
            }
        } else {
            Object v1_1 = null;
        }
        return v1;
    }

    public void setChapter(MCChapterModel chapter) {
        this.chapter = chapter;
    }

    public void setSection(MCSectionModel section) {
        this.section = section;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWhichType() {
        return this.section != null ? 0 : 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MCBaseDefine.MCDownloadNodeType getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(MCBaseDefine.MCDownloadNodeType downloadType) {
        this.downloadType = downloadType;
    }
}

