package com.whatyplugin.imooc.logic.model;


import java.io.Serializable;

import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;

public class MCLoadingModel extends MCDataModel implements Serializable {
    private String end_time;
    private String pic;
    private String url;
    private String id;
    
    public MCLoadingModel() {
        super();
    }

    public String getEnd_time() {
        return this.end_time;
    }

    public String getPic() {
        return this.pic;
    }

    public String getUrl() {
        return this.url;
    }

    public MCLoadingModel modelWithData(Object data) {
    	MCLoadingModel v2_1 = null;
        String v1 = data.toString();
        if(v1 != null && v1.length()>0) {
            MCLoadingModel v2 = new MCLoadingModel();
            try {
                JSONObject v3 = new JSONObject(v1);
                if(v3.has("id")) {
                    v2.setId(v3.getString("id"));
                }

                if(v3.has("pic")) {
                    v2.setPic(v3.getString("pic"));
                }

                if(v3.has("url")) {
                    v2.setUrl(v3.getString("url"));
                }

                if(!v3.has("end_time")) {
                    return v2_1;
                }

                v2.setEnd_time(v3.getString("end_time"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            v2_1 = null;
        }

        return v2_1;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}

