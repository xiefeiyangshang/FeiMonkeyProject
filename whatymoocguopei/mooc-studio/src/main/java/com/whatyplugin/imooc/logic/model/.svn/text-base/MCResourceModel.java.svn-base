package com.whatyplugin.imooc.logic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.whatyplugin.base.model.MCDataModel;

/**
 * 课程下资源Model
 * 
 * @author liubin
 * 
 */
public class MCResourceModel extends MCDataModel implements Serializable {
	private String content;
	private String description;
	private String id;

	private List<MCResourceDetailModel> detailModelList;

	public List<MCResourceDetailModel> getDetailModelList() {
		return detailModelList;
	}

	public void setDetailModelList(List<MCResourceDetailModel> detailModelList) {
		this.detailModelList = detailModelList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MCResourceModel() {
		super();
	}

	public MCResourceModel modelWithData(Object data) {
		MCResourceModel sectionModel = null;
		JSONObject jsonObject = null;
		String type = null;
		if (data != null) {
			try {
				jsonObject = new JSONObject(data.toString());
				sectionModel = new MCResourceModel();
				if (jsonObject.has("content")) {
					sectionModel.setContent(jsonObject.getString("content"));
				}
				if (jsonObject.has("description")) {
					sectionModel.setDescription(jsonObject
							.getString("description"));
				}
				if (jsonObject.has("type")) {
					type = jsonObject.getString("type");
				}

				// 下载资料类型的，需要再封装一层
				if ("resource".equals(type) && !jsonObject.isNull("content")) {
					JSONArray segmentArray = jsonObject.getJSONArray("content");
					List<MCResourceDetailModel> modelList = new ArrayList<MCResourceDetailModel>();
					// 讲
					for (int j = 0; j < segmentArray.length(); j++) {
						MCResourceDetailModel model = new MCResourceDetailModel();
						model = model.modelWithData(segmentArray
								.getJSONObject(j));
						modelList.add(model);
					}
					sectionModel.setDetailModelList(modelList);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sectionModel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
