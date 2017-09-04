package com.whatyplugin.imooc.logic.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MCTestStatisticInfo  implements Parcelable{

	public String type;
	public int count;
	public int score;
	
	public MCTestStatisticInfo(Parcel parcel) {
		// TODO Auto-generated constructor stub
		type = parcel.readString();
		count = parcel.readInt();
		score = parcel.readInt();
	}

	public MCTestStatisticInfo() {
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public String toString() {
//		return "StatisticInfo [type=" + type + ", count=" + count + ", score="
//				+ score + "]";
//	}
	public static final Parcelable.Creator<MCTestStatisticInfo> CREATOR = new Creator<MCTestStatisticInfo>() {
		@Override
		public MCTestStatisticInfo[] newArray(int size) {
			return new MCTestStatisticInfo[size];
		}

		@Override
		public MCTestStatisticInfo createFromParcel(Parcel parcel) {
			return new MCTestStatisticInfo(parcel);
		}
	};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(type);
		dest.writeInt(count);
		dest.writeInt(score);
	}
}
