package com.whatyplugin.imooc.logic.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.whatyplugin.base.model.MCDataModel;

/**
 * 自测回答问题的ITEM
 * 
 * @author bzy
 */
public class MCTestQuesModel extends MCDataModel implements Parcelable {

	public int index;
	public String title;
	public String type;
	public String questionId;
	private String id;
	/**
	 * 正确选项
	 */
	public String correctOption;
	/**
	 * 答案解析
	 */
	public String solution;
	/**
	 * 是否正确
	 */
	public int correctness; // 0 error 1 OK

	private String userAnswer;
	
	public ArrayList<AnsOption> options = new ArrayList<AnsOption>(); // 做题的选项

	public MCTestQuesModel() {
	}

	@Override
	public int describeContents() {
		return options == null ? 0 : options.size();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(index);
		dest.writeString(title);
		dest.writeString(type);
		dest.writeString(questionId);
		dest.writeString(correctOption);
		dest.writeString(solution);
		dest.writeInt(correctness);
		dest.writeString(userAnswer);
		dest.writeTypedList(options);
	}

	public MCTestQuesModel(Parcel parcel) {
		index = parcel.readInt();
		title = parcel.readString();
		type = parcel.readString();
		questionId = parcel.readString();
		correctOption = parcel.readString();
		solution = parcel.readString();
		correctness = parcel.readInt();
		userAnswer = parcel.readString();
		parcel.readTypedList(options, AnsOption.CREATOR);
	}

	public static final Parcelable.Creator<MCTestQuesModel> CREATOR = new Creator<MCTestQuesModel>() {

		@Override
		public MCTestQuesModel[] newArray(int size) {
			return new MCTestQuesModel[size];
		}

		@Override
		public MCTestQuesModel createFromParcel(Parcel parcel) {
			return new MCTestQuesModel(parcel);
		}
	};

	/**
	 * 解析问题
	 */
	@Override
	public MCDataModel modelWithData(Object obj) {
		return modelWithData(obj, false);
	}

	/**
	 * 解析问题
	 * @param obj
	 * @param isExt== true是解析答案详情
	 * @return
	 */
	public MCDataModel modelWithData(Object obj, boolean isExt) {
		MCTestQuesModel quesModel = null;
		if (isExt) {
			JSONObject jsonObject = null;
			if (!TextUtils.isEmpty(obj.toString())) {
				quesModel = new MCTestQuesModel();
				try {
					jsonObject = new JSONObject(obj.toString());
					quesModel.title = jsonObject.optString("body");
					quesModel.type = jsonObject.optString("type");
					quesModel.solution = jsonObject.optString("note");
					
					String uanswer = jsonObject.optString("uanswer");
					StringBuilder ans = new StringBuilder();
//					uanswer=A
					//correctness
					JSONArray options = jsonObject.getJSONArray("itemList");
					for (int j = 0; j < options.length(); j++) {
						JSONObject itemopt = (JSONObject) options.get(j);
						AnsOption opt = new AnsOption(); // SELECT
						opt.content = itemopt.getString("content");
						opt.id = itemopt.getString("index");
						opt.isCheck = itemopt.getBoolean("isAnswer");
						if(opt.isCheck){ //是正确答案
							ans.append(opt.id);
						}
						quesModel.options.add(opt);
					}
					//是否正确
					quesModel.correctness = uanswer.equals(ans.toString()) ? 1:0;
					//正确答案
					quesModel.correctOption = ans.toString();
					quesModel.setUserAnswer(uanswer);
					//uanswer = null;
					//ans = null;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		
		} else {
			JSONObject jsonObject = null;

			if (!TextUtils.isEmpty(obj.toString())) {
				quesModel = new MCTestQuesModel();
				try {
					jsonObject = new JSONObject(obj.toString());
					quesModel.index = jsonObject.optInt("index");
					quesModel.questionId = jsonObject.optString("questionId");
					quesModel.title = jsonObject.optString("title");
					quesModel.type = jsonObject.optString("type");

					JSONArray options = jsonObject.getJSONArray("options");
					for (int j = 0; j < options.length(); j++) {
						JSONObject itemopt = (JSONObject) options.get(j);
						AnsOption opt = new AnsOption(); // SELECT
						opt.content = itemopt.getString("content");
						opt.id = itemopt.getString("id");
						quesModel.options.add(opt);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return quesModel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

}
