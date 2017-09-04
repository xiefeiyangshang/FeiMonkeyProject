package com.whatyplugin.imooc.logic.model;


import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;

public class MCCourseTypeModel extends MCDataModel {
    private int courseNum;
    private String imgUrl;
    private String name;
    private String id;
    
    public MCCourseTypeModel() {
        super();
    }

    public int getCourseNum() {
        return this.courseNum;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public String getName() {
        return this.name;
    }

    public MCCourseTypeModel modelWithData(Object data) {
        String v1 = data.toString();
        if(v1 != null && v1.length()>0) {
            MCCourseTypeModel v3 = new MCCourseTypeModel();
            try {
                JSONObject v2 = new JSONObject(v1);
                v3.setId(v2.getString("id"));
                v3.setName(v2.getString("name"));
                v3.setImgUrl(v2.getString("pic"));
                v3.setCourseNum(v2.getInt("numbers"));
                return v3;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

