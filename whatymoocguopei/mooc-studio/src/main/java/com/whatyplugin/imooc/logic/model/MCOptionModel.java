package com.whatyplugin.imooc.logic.model;


import org.json.JSONObject;

import com.whatyplugin.base.log.MCLog;
import com.whatyplugin.base.model.MCDataModel;

public class MCOptionModel extends MCDataModel {
    private String content;
    private boolean isAnswer;
    private String tip;
    private static final String TAG = MCOptionModel.class.getSimpleName();
	private String id;
	
    public MCOptionModel() {
        super();
    }

    public String getContent() {
        return this.content;
    }

    public String getTip() {
        return this.tip;
    }

    public boolean isAnswer() {
        return this.isAnswer;
    }

    public MCOptionModel modelWithData(Object data) {
        MCOptionModel v3 = null;
        String v2 = data.toString();
        if(v2 != null && v2.length() >0 ) {
            v3 = new MCOptionModel();
            try {
                MCLog.i(TAG, "optionmodel:" + v2);
                JSONObject v4 = new JSONObject(v2);
                v3.setId(v4.getString("id"));
                v3.setContent(v4.getString("name"));
                v3.setTip(v4.getString("tip"));
                if(v4.getInt("is_answer") == 1) {
                    v3.setAnswer(true);
                }
                else {
                    v3.setAnswer(false);
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Object v3_1 = null;
        }

        return v3;
    }

    public void setAnswer(boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}

