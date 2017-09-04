package com.whatyplugin.imooc.logic.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AnsOption implements Parcelable {
	
	public String id;
	public String content;
	public boolean isCheck;

	
	public AnsOption() {}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(content);
		boolean[] val = new boolean[1];
		val[0] = isCheck;
		dest.writeBooleanArray(val);
	}
	
	public AnsOption(Parcel source) {
		id = source.readString();
		content = source.readString();
		boolean[] val = new boolean[1];
		source.readBooleanArray(val);
		isCheck = val[0];
	}
	
	public static final Parcelable.Creator<AnsOption> CREATOR = new Creator<AnsOption>() {
		
		@Override
		public AnsOption[] newArray(int size) {
			return new AnsOption[size];
		}
		
		@Override
		public AnsOption createFromParcel(Parcel source) {
			return new AnsOption(source);
		}
	};
	
}
