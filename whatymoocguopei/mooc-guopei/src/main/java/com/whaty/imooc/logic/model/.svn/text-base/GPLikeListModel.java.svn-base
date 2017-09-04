package com.whaty.imooc.logic.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.whaty.imooc.ui.index.GPRequestUrl;
import com.whatyplugin.base.model.MCDataModel;

public class GPLikeListModel  extends MCDataModel {
    private  Boolean  isLike ; //我是否点赞
    private  Map<String,String>  likeUsers;
	
	
	
	@Override
	public MCDataModel modelWithData(Object arg1) {
		GPLikeListModel model = new GPLikeListModel();
		try {
			JSONArray array =  (JSONArray)arg1;
			Map<String,String>  likeList =  new HashMap<String, String>();
			for(int i=0;i<array.length();i++){
				JSONObject jsonObject =	array.getJSONObject(i);
				likeList.put(jsonObject.optString("loginId"), jsonObject.optString("trueName"));
			}
			model.setLikeUsers(likeList);
			model.setIsLike(likeList.containsKey(GPRequestUrl.getInstance().getLogId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return model;
	}

	@Override
	public String getId() {
		
		return null;
	}

	public Boolean getIsLike() {
		return isLike;
	}

	public void setIsLike(Boolean isLike) {
		this.isLike = isLike;
	}

	public Map<String, String> getLikeUsers() {
		return likeUsers;
	}

	public void setLikeUsers(Map<String, String> likeUsers) {
		this.likeUsers = likeUsers;
	}
	
	
	

}
