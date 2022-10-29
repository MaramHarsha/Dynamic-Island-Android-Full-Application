package com.lock.entity;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import com.lock.services.ActionParsable;
import java.util.ArrayList;
import java.util.HashMap;

public class Notification {
    public ArrayList<ActionParsable> actions;
    public String app_name;
    public CharSequence bigText;
    public String category;
    public int color;
    public int count;
    public long duration;
    public String groupKey;
    public Bitmap icon;
    public String id;
    public CharSequence info_text;
    public boolean isAppGroup;
    public boolean isChronometerRunning;
    public boolean isClearable;
    public boolean isGroup;
    public boolean isGroupConversation;
    public boolean isLocal;
    public boolean isOngoing;
    public String key;
    public HashMap<String, Notification> keyMap;
    public int local_left_icon;
    public int local_right_icon;
    public String pack;
    public PendingIntent pendingIntent;
    public Bitmap picture;
    public long position;
    public long postTime;
    public int progress;
    public boolean progressIndeterminate;
    public int progressMax;
    public Bitmap senderIcon;
    public boolean showChronometer;
    public CharSequence subText;
    public CharSequence substName;
    public CharSequence summaryText;
    public String tag;
    public String template;
    public CharSequence titleBig;
    public CharSequence tv_text;
    public CharSequence tv_title;
    public String type;
    public int uId;
    public boolean useIphoneCallDesign;

    public Notification(String str, Bitmap bitmap, Bitmap bitmap2, CharSequence charSequence, CharSequence charSequence2, int i, String str2, long j, PendingIntent pendingIntent2, ArrayList<ActionParsable> arrayList, CharSequence charSequence3, String str3, boolean z, int i2, Bitmap bitmap3, String str4, String str5, boolean z2, boolean z3, boolean z4, boolean z5, String str6, int i3, String str7, CharSequence charSequence4, CharSequence charSequence5, CharSequence charSequence6, CharSequence charSequence7, int i4, int i5, boolean z6, CharSequence charSequence8, boolean z7, String str8) {
        this.keyMap = new HashMap<>();
        this.type = "";
        this.local_left_icon = 0;
        this.local_right_icon = 0;
        this.isLocal = false;
        this.useIphoneCallDesign = false;
        this.isChronometerRunning = false;
        this.id = str;
        this.icon = bitmap;
        this.senderIcon = bitmap2;
        this.tv_title = charSequence;
        this.tv_text = charSequence2;
        this.count = i;
        this.pack = str2;
        this.postTime = j;
        this.pendingIntent = pendingIntent2;
        this.actions = arrayList;
        this.bigText = charSequence3;
        this.app_name = str3;
        this.isClearable = z;
        this.color = i2;
        this.picture = bitmap3;
        this.groupKey = str4;
        this.key = str5;
        this.isGroupConversation = z2;
        this.isAppGroup = z3;
        this.isGroup = z4;
        this.isOngoing = z5;
        this.tag = str6;
        this.uId = i3;
        this.template = str7;
        this.substName = charSequence4;
        this.subText = charSequence5;
        this.titleBig = charSequence6;
        this.info_text = charSequence7;
        this.progressMax = i4;
        this.progress = i5;
        this.progressIndeterminate = z6;
        this.summaryText = charSequence8;
        this.showChronometer = z7;
        this.category = str8;
    }

    public Notification(String str, int i, int i2) {
        this.keyMap = new HashMap<>();
        this.useIphoneCallDesign = false;
        this.isChronometerRunning = false;
        this.type = str;
        this.isLocal = true;
        this.local_left_icon = i;
        this.local_right_icon = i2;
    }
}
