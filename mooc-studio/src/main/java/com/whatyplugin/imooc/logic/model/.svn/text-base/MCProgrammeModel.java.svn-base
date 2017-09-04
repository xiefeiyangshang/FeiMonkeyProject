package com.whatyplugin.imooc.logic.model;


import java.io.Serializable;

import org.json.JSONObject;

public class MCProgrammeModel extends MCSectionModel implements Serializable {
    private String desc;

    public MCProgrammeModel() {
        super();
    }

    public String getDesc() {
        return this.desc;
    }
    public MCProgrammeModel modelWithData(Object data) {

    	MCProgrammeModel model = null;
        String jsonData = data.toString();
        if(jsonData != null && jsonData.length()>0) {
            MCProgrammeModel v3 = new MCProgrammeModel();
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                v3.setId(jsonObject.optString("id"));
                v3.setDesc(jsonObject.optString("desc"));
                v3.setName(jsonObject.optString("name"));
                v3.setSeq(jsonObject.optInt("media_seq"));
                v3.setType(this.convertMediaTypeFromString(jsonObject.optString("type")));
                v3.setChapterSeq(jsonObject.optInt("chapter_seq"));
                v3.setChapterId(jsonObject.optInt("chapter_id"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            model = null;
        }

        return model;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

