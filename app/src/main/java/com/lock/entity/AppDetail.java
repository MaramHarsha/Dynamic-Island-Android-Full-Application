package com.lock.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class AppDetail implements Parcelable {
    public static final Creator<AppDetail> CREATOR = new Creator<AppDetail>() {
        public AppDetail createFromParcel(Parcel parcel) {
            return new AppDetail(parcel);
        }

        public AppDetail[] newArray(int i) {
            return new AppDetail[i];
        }
    };
    public String activityInfoName;
    public int image;
    public boolean isCurrentUser;
    public boolean isSelected;
    public boolean isSorted;
    public String label;
    public String pkg;

    public int describeContents() {
        return 0;
    }

    public AppDetail() {
        this.isSelected = false;
        this.isCurrentUser = true;
    }

    public AppDetail(String str, String str2, String str3, String str4, boolean z, boolean z2) {
        this.isSelected = false;
        this.label = str2;
        this.pkg = str3;
        this.activityInfoName = str4;
        this.isCurrentUser = z2;
    }

    public AppDetail(String str, String str2, String str3, String str4, boolean z, boolean z2, int i) {
        this.isSelected = false;
        this.pkg = str;
        this.label = str2;
        this.activityInfoName = str3;
        this.isCurrentUser = z;
        this.image = i;
    }

    public AppDetail(long j, String str, String str2, String str3, String str4, boolean z, boolean z2, boolean z3) {
        this.isSelected = false;
        this.label = str2;
        this.pkg = str3;
        this.activityInfoName = str4;
        this.isCurrentUser = z3;
    }

    private AppDetail(Parcel parcel) {
        boolean z = false;
        this.isSelected = false;
        this.isCurrentUser = true;
        this.label = parcel.readString();
        this.pkg = parcel.readString();
        this.activityInfoName = parcel.readString();
        this.image = parcel.readInt();
        this.isSorted = parcel.readByte() != 0;
        this.isSelected = parcel.readByte() != 0;
        this.isCurrentUser = parcel.readByte() != 0 ? true : z;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.label);
        parcel.writeString(this.pkg);
        parcel.writeString(this.activityInfoName);
        parcel.writeInt(this.image);
        parcel.writeByte(this.isSorted ? (byte) 1 : 0);
        parcel.writeByte(this.isSelected ? (byte) 1 : 0);
        parcel.writeByte(this.isCurrentUser ? (byte) 1 : 0);
    }
}
