package com.whatyplugin.imooc.logic.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.imooc.logic.contants.Contants;

public class MCChapterModel extends MCDataModel {
	private String launch;
	private String zhangId;
	private String vId;

	private int courseId;
	private String name;
	private List<MCSectionModel> sections;
	private int seq;
	private boolean first;
	private String id;

	public String getvId() {
		return vId;
	}

	public void setvId(String vId) {
		this.vId = vId;
	}

	public String getLaunch() {
		return launch;
	}

	public void setLaunch(String launch) {
		this.launch = launch;
	}

	public String getZhangId() {
		return zhangId;
	}

	public void setZhangId(String zhangId) {
		this.zhangId = zhangId;
	}
	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public MCChapterModel() {
		super();
	}

	public int getCourseId() {
		return this.courseId;
	}

	public String getName() {
		return this.name;
	}

	public List getSections() {
		return this.sections;
	}

	public int getSeq() {
		return this.seq;
	}

	public MCChapterModel modelWithData(Object data) {
		MCChapterModel chapter = null;
		JSONObject jsonObject = null;
		if (data != null) {
			chapter = new MCChapterModel();
			try {
				jsonObject = new JSONObject(data.toString());
				List<MCSectionModel> modelList = new ArrayList<MCSectionModel>();
				if(TextUtils.isEmpty(jsonObject.optString("sectionTitle"))){
					chapter.setName(Contants.SPACENOP + jsonObject.optString("coursename"));
				}else {
					chapter.setName(Contants.SPACENOP + jsonObject.optString("sectionTitle"));
				}

				chapter.setvId(jsonObject.optString("vid"));
				if(jsonObject.has("resources")){
					JSONArray segmentArray = jsonObject.optJSONArray("resources");
					//讲
					for (int j = 0; j < segmentArray.length(); j++) {
						MCSectionModel model = new MCSectionModel();
						model = model.modelWithData(segmentArray.getJSONObject(j));
						model.setSeq(j+1);
						String typeInfo = model.getType()==null?"":" ("+model.getType().typeInfo()+")";
						model.setName(typeInfo + model.getName());
						modelList.add(model);
					}
				}

				chapter.setSections(modelList);

			} catch (Exception v3) {
				v3.printStackTrace();
			}
		}
		return chapter;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSections(List<MCSectionModel> arg1) {
		this.sections = arg1;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
