package com.whatyplugin.imooc.logic.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.whatyplugin.base.model.MCAdditionalData;
/**
 * 自测提交答案后获取分数等信息
 * @author banzhenyu
 *
 */
public class MCTestAdditionalData extends MCAdditionalData implements Parcelable {
	private String currentScore;
	private String maxScore;
	private String title;

	public MCTestAdditionalData() {
	}
	public MCTestAdditionalData(Parcel parcel) {
		currentScore = parcel.readString();
		maxScore = parcel.readString();
		title = parcel.readString();
	}
	public String getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(String currentScore) {
		this.currentScore = currentScore;
	}

	public String getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		 dest.writeString(currentScore);
		 dest.writeString(maxScore);
		 dest.writeString(title);
	}

	public static final Parcelable.Creator<MCAdditionalData> CREATOR = new Creator<MCAdditionalData>() {
		@Override
		public MCAdditionalData[] newArray(int size) {
			return new MCAdditionalData[size];
		}

		@Override
		public MCAdditionalData createFromParcel(Parcel parcel) {
			return new MCTestAdditionalData(parcel);
		}
	};
}
