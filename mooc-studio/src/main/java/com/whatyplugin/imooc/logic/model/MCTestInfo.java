package com.whatyplugin.imooc.logic.model;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
/**
 * 题目构成   和关联知识点
 * @author banzhenyu
 */
public class MCTestInfo implements Parcelable {

	public HashMap<String, MCTestStatisticInfo> statisticInfo;
	public String  desc;
	
	public MCTestInfo(Parcel parcel) { 
		statisticInfo = parcel.readHashMap(MCTestStatisticInfo.class.getClassLoader());
		desc = parcel.readString();
	}

	public MCTestInfo() {
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeMap(statisticInfo);
		dest.writeString(desc);
	}

	public static final Parcelable.Creator<MCTestInfo> CREATOR = new Creator<MCTestInfo>() {
		@Override
		public MCTestInfo[] newArray(int size) {
			return new MCTestInfo[size];
		}

		@Override
		public MCTestInfo createFromParcel(Parcel parcel) {
			return new MCTestInfo(parcel);
		}
	};

	@Override
	public String toString() { 
		return "StringList:" + desc.toString() + "statisticInfo:"
				+ statisticInfo.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}
}
