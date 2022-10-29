package com.lock.services;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class ActionParsable implements Parcelable {
    public static final Creator<ActionParsable> CREATOR = new Creator<ActionParsable>() {
        public ActionParsable createFromParcel(Parcel parcel) {
            return new ActionParsable(parcel);
        }

        public ActionParsable[] newArray(int i) {
            return new ActionParsable[i];
        }
    };
    public int actionIcon;
    public boolean allowGeneratedReplies;
    public Bundle bundle;
    public CharSequence charSequence;
    public boolean isContextual;
    public PendingIntent pendingIntent;
    public RemoteInput[] remoteInputs;
    public int semanticAction;

    public int describeContents() {
        return 0;
    }

    protected ActionParsable(Parcel parcel) {
        this.charSequence = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        boolean z = true;
        if (parcel.readInt() == 1) {
            this.pendingIntent = (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel);
        }
        this.bundle = parcel.readBundle(Bundle.class.getClassLoader());
        this.remoteInputs = (RemoteInput[]) parcel.createTypedArray(RemoteInput.CREATOR);
        this.allowGeneratedReplies = parcel.readInt() == 1;
        this.semanticAction = parcel.readInt();
        this.isContextual = parcel.readInt() != 1 ? false : z;
        this.actionIcon = parcel.readInt();
    }

    public ActionParsable clone() {
        return new ActionParsable(this.charSequence, this.pendingIntent, this.bundle == null ? new Bundle() : new Bundle(this.bundle), this.remoteInputs, this.allowGeneratedReplies, this.semanticAction, this.isContextual, this.actionIcon);
    }

    public void writeToParcel(Parcel parcel, int i) {
        TextUtils.writeToParcel(this.charSequence, parcel, i);
        if (this.pendingIntent != null) {
            parcel.writeInt(1);
            this.pendingIntent.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBundle(this.bundle);
        parcel.writeTypedArray(this.remoteInputs, i);
        parcel.writeInt(this.allowGeneratedReplies ? 1 : 0);
        parcel.writeInt(this.semanticAction);
        parcel.writeInt(this.isContextual ? 1 : 0);
        parcel.writeInt(this.actionIcon);
    }

    public ActionParsable(CharSequence charSequence2, PendingIntent pendingIntent2, Bundle bundle2, RemoteInput[] remoteInputArr, boolean z, int i, boolean z2, int i2) {
        this.charSequence = charSequence2;
        this.pendingIntent = pendingIntent2;
        this.bundle = bundle2 == null ? new Bundle() : bundle2;
        this.remoteInputs = remoteInputArr;
        this.allowGeneratedReplies = z;
        this.semanticAction = i;
        this.isContextual = z2;
        this.actionIcon = i2;
    }
}
