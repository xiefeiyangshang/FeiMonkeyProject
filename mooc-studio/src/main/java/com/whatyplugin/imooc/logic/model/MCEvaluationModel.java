package com.whatyplugin.imooc.logic.model;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.whatyplugin.base.define.MCBaseDefine.MCAnswerRule;
import com.whatyplugin.base.define.MCBaseDefine.MCAnswerStatus;
import com.whatyplugin.base.define.MCBaseDefine.MCQuestionType;
import com.whatyplugin.base.model.MCDataModel;
import com.whatyplugin.base.type.MCTime;

public class MCEvaluationModel extends MCDataModel {
    private MCAnswerRule answerRule;
    private MCAnswerStatus answerStatus;
    private String content;
    private int mid;
    private List options;
    private int score;
    private MCTime time;
    private MCQuestionType type;
    private String id;
    
    public MCEvaluationModel() {
        super();
    }

    public MCAnswerRule getAnswerRule() {
        return this.answerRule;
    }

    public MCAnswerStatus getAnswerStatus() {
        return this.answerStatus;
    }

    public String getContent() {
        return this.content;
    }

    public int getMid() {
        return this.mid;
    }

    public List getOptions() {
        return this.options;
    }

    public int getScore() {
        return this.score;
    }

    public MCTime getTime() {
        return this.time;
    }

    public MCQuestionType getType() {
        return this.type;
    }

    public MCEvaluationModel modelWithData(Object data) {
        MCEvaluationModel v1 = null;
        String v3 = data.toString();
        if(v3 != null && v3.length() > 0) {
            v1 = new MCEvaluationModel();
            try {
                JSONObject v4 = new JSONObject(v3);
                v1.setId(v4.getString("id"));
                v1.setMid(v4.getInt("mid"));
                v1.setContent(v4.getString("name"));
                v1.setScore(v4.getInt("score"));
                JSONArray v8 = new JSONArray(v4.getString("options"));
                ArrayList v9 = new ArrayList();
                int v2;
                for(v2 = 0; v2 < v8.length(); ++v2) {
                    ((List)v9).add(new MCOptionModel().modelWithData(v8.getJSONObject(v2)));
                }

                v1.setOptions(((List)v9));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        return v1;
    }

    public void setAnswerRule(MCAnswerRule answerRule) {
        this.answerRule = answerRule;
    }

    public void setAnswerStatus(MCAnswerStatus answerStatus) {
        this.answerStatus = answerStatus;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public void setOptions(List arg1) {
        this.options = arg1;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTime(MCTime time) {
        this.time = time;
    }

    public void setType(MCQuestionType type) {
        this.type = type;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
    
}

