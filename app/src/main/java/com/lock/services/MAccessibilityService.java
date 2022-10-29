package com.lock.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Path;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.ColorUtils;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.lock.adaptar.CustomNotificationAdapter;
import com.lock.adaptar.CustomNotificationIconAdapter;
import com.lock.background.PrefManager;
import com.lock.entity.AppDetail;
import com.lock.entity.AppPackageList;
import com.lock.entity.Notification;
import com.lock.providers.InAppPurchaseProvider;
import com.lock.utils.Constants;
import com.lock.utils.ItemOffsetDecoration2;
import com.lock.utils.Utils;
import com.lock.views.CustomRecyclerView;
import com.lock.views.StatusBarParentView;

import com.dynamic.island.harsha.notification.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class MAccessibilityService extends AccessibilityService {
    public static final Uri ENABLED_ACCESSIBILITY_SERVICES = Settings.Secure.getUriFor("enabled_accessibility_services");
    private static MContentObserver mContentObserver;
    
    public CustomNotificationAdapter adapter_island_big;
    
    public CustomNotificationIconAdapter adapter_island_small;
    public AppPackageList callPKG = new AppPackageList();
    int currentIndex = 0;
    public AppPackageList filterPKG = new AppPackageList();
    int flagKeyBoard = 8913696;
    int flagNormal = 8913704;
    private final Handler handler;
    boolean isControlEnabled = true;
    
    public boolean isInFullScreen;
    
    public boolean isPhoneLocked;
    
    public LinearLayout island_parent_layout;
    
    public LinearLayout island_top_layout;
    private final ArrayList<Notification> list_big_island = new ArrayList<>();
    
    public final ArrayList<Notification> list_small_island = new ArrayList<>();
    SharedPreferences.OnSharedPreferenceChangeListener listener;
    WindowManager.LayoutParams localLayoutParams;
    Context mContext;
    Handler mHandle;
    private BroadcastReceiver mInfoReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice bluetoothDevice;
            if (intent.getAction().matches("android.intent.action.BATTERY_CHANGED")) {
                int intExtra = intent.getIntExtra(NotificationCompat.CATEGORY_STATUS, -1);
                if (intExtra == 2 || intExtra == 5) {
                    Notification notification = null;
                    int i = 0;
                    while (true) {
                        if (i >= MAccessibilityService.this.list_small_island.size()) {
                            i = 0;
                            break;
                        } else if (((Notification) MAccessibilityService.this.list_small_island.get(i)).type.equals(Constants.TYPE_CHARGING)) {
                            notification = (Notification) MAccessibilityService.this.list_small_island.get(i);
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (notification == null) {
                        notification = new Notification(Constants.TYPE_CHARGING, 0, 0);
                        MAccessibilityService.this.list_small_island.add(notification);
                    }
                    notification.tv_text = MAccessibilityService.this.utils.getBatteryLevel() + "%";
                    notification.color = MAccessibilityService.this.mContext.getColor(R.color.green_500);
                    if (MAccessibilityService.this.utils.getBatteryLevel() < 100) {
                        notification.tv_title = MAccessibilityService.this.mContext.getString(R.string.charging_txt);
                    } else {
                        notification.tv_title = MAccessibilityService.this.mContext.getString(R.string.full_txt);
                    }
                    MAccessibilityService.this.scrollToPos(i);
                } else {
                    Iterator it = MAccessibilityService.this.list_small_island.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Notification notification2 = (Notification) it.next();
                        if (notification2.type.equals(Constants.TYPE_CHARGING)) {
                            MAccessibilityService.this.list_small_island.remove(notification2);
                            break;
                        }
                    }
                    MAccessibilityService.this.scrollToLastItem();
                }
            }
            if (intent.getAction().matches("android.media.RINGER_MODE_CHANGED")) {
                int dndState = MAccessibilityService.this.utils.getDndState();
                if (dndState != 0 && dndState != 1) {
                    Iterator it2 = MAccessibilityService.this.list_small_island.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        Notification notification3 = (Notification) it2.next();
                        if (notification3.type.equals(Constants.TYPE_SILENT)) {
                            MAccessibilityService.this.list_small_island.remove(notification3);
                            break;
                        }
                    }
                } else {
                    Iterator it3 = MAccessibilityService.this.list_small_island.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        Notification notification4 = (Notification) it3.next();
                        if (notification4.type.equals(Constants.TYPE_SILENT)) {
                            MAccessibilityService.this.list_small_island.remove(notification4);
                            break;
                        }
                    }
                    if (dndState == 0) {
                        Notification notification5 = new Notification(Constants.TYPE_SILENT, R.drawable.silent, 0);
                        notification5.tv_title = MAccessibilityService.this.mContext.getString(R.string.silent_txt);
                        notification5.color = MAccessibilityService.this.mContext.getColor(R.color.red_500);
                        MAccessibilityService.this.list_small_island.add(notification5);
                    }
                    if (dndState == 1) {
                        Notification notification6 = new Notification(Constants.TYPE_SILENT, R.drawable.vibration_icon, 0);
                        notification6.tv_title = MAccessibilityService.this.mContext.getString(R.string.vibrate);
                        notification6.color = MAccessibilityService.this.mContext.getColor(R.color.purple_400);
                        MAccessibilityService.this.list_small_island.add(notification6);
                    }
                }
                MAccessibilityService.this.scrollToLastItem();
            }
            if (intent.getAction() != null && (("android.bluetooth.device.action.ACL_CONNECTED".equals(intent.getAction()) || "android.bluetooth.device.action.ACL_DISCONNECTED".equals(intent.getAction())) && (bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")) != null)) {
                if (Build.VERSION.SDK_INT < 31 || ActivityCompat.checkSelfPermission(MAccessibilityService.this.mContext, "android.permission.BLUETOOTH_CONNECT") == 0) {
                    if ("android.bluetooth.device.action.ACL_CONNECTED".equals(intent.getAction())) {
                        if (bluetoothDevice.getType() == 1) {
                            Iterator it4 = MAccessibilityService.this.list_small_island.iterator();
                            while (true) {
                                if (!it4.hasNext()) {
                                    break;
                                }
                                Notification notification7 = (Notification) it4.next();
                                if (notification7.type.equals(Constants.TYPE_AIRBUDS)) {
                                    MAccessibilityService.this.list_small_island.remove(notification7);
                                    break;
                                }
                            }
                            Notification notification8 = new Notification(Constants.TYPE_AIRBUDS, 0, 0);
                            notification8.color = MAccessibilityService.this.mContext.getColor(R.color.green_500);
                            MAccessibilityService.this.list_small_island.add(notification8);
                        }
                        MAccessibilityService.this.scrollToLastItem();
                    }
                    if ("android.bluetooth.device.action.ACL_DISCONNECTED".equals(intent.getAction())) {
                        if (bluetoothDevice.getType() == 1) {
                            Iterator it5 = MAccessibilityService.this.list_small_island.iterator();
                            while (true) {
                                if (!it5.hasNext()) {
                                    break;
                                }
                                Notification notification9 = (Notification) it5.next();
                                if (notification9.type.equals(Constants.TYPE_AIRBUDS)) {
                                    MAccessibilityService.this.list_small_island.remove(notification9);
                                    break;
                                }
                            }
                        }
                        MAccessibilityService.this.scrollToLastItem();
                    }
                } else {
                    Toast.makeText(MAccessibilityService.this.mContext, R.string.bluetooth_permission, 0).show();
                    return;
                }
            }
            KeyguardManager keyguardManager = (KeyguardManager) MAccessibilityService.this.mContext.getSystemService("keyguard");
            if (!intent.getAction().equals("android.intent.action.USER_PRESENT") && !intent.getAction().equals("android.intent.action.SCREEN_OFF") && !intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                return;
            }
            if (keyguardManager.inKeyguardRestrictedInputMode()) {
                boolean unused = MAccessibilityService.this.isPhoneLocked = true;
                if (!Constants.getShowOnLock(MAccessibilityService.this.mContext)) {
                    MAccessibilityService.this.closeSmallIslandNotification();
                    MAccessibilityService.this.closeFullNotificationIsland();
                    return;
                }
                return;
            }
            boolean unused2 = MAccessibilityService.this.isPhoneLocked = false;
        }
    };
    Runnable mRunnable = new MAccessibilityService$$ExternalSyntheticLambda1(this);
    WindowManager manager;
    int margin = 25;
    private final int maxIslandHeight = 170;
    private final int maxIslandHeight2 = 110;
    private final int maxIslandHeightCall = 90;
    private final int maxIslandWidth = 350;
    Handler mediaHandler = new Handler();
    Runnable mediaUpdateRunnable = new Runnable() {
        public void run() {
            MAccessibilityService.this.notification.duration = MAccessibilityService.this.getMediaDuration();
            MAccessibilityService.this.notification.position = MAccessibilityService.this.getMediaPosition();
            if (MAccessibilityService.this.notification.duration > 0) {
                MAccessibilityService.this.notification.progressMax = 100;
                MAccessibilityService.this.notification.progress = (int) ((((float) MAccessibilityService.this.notification.position) / ((float) MAccessibilityService.this.notification.duration)) * 100.0f);
                MAccessibilityService.this.notification.progressIndeterminate = false;
                MAccessibilityService.this.adapter_island_big.updateMediaProgress();
            }
            MAccessibilityService.this.mediaHandler.postDelayed(MAccessibilityService.this.mediaUpdateRunnable, 1000);
        }
    };
    public int minCameIslandWidth = 150;
    
    public int minIslandHeight = 30;
    public int minOneCameIslandWidth = 150;
    public int minThreeCameIslandWidth = 200;
    public int minTwoCameIslandWidth = 180;
    BroadcastReceiver notiReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().matches(Utils.FROM_NOTIFICATION_SERVICE + context.getPackageName()) && intent.getAction().equals(Utils.FROM_NOTIFICATION_SERVICE + context.getPackageName())) {
                MAccessibilityService.this.updateNotificationList(intent);
            }
        }
    };
    Notification notification;
    int pos = 1;
    PrefManager prefManager;
    SharedPreferences preferences;
    
    public CustomRecyclerView rv_island_big;
    RecyclerView rv_island_small;
    View statusBarParentView;
    public StatusBarParentView statusBarView;
    int tempMargin;
    private boolean useIphoneCallDesign = false;
    public Utils utils;
    public int zeroHeight = 0;

    public void onInterrupt() {
    }

    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        try {
            if (accessibilityEvent.getPackageName() != null) {
                accessibilityEvent.getEventType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MAccessibilityService() {
        Handler handler2 = new Handler();
        this.handler = handler2;
        mContentObserver = new MContentObserver(handler2);
    }

    public class MContentObserver extends ContentObserver {
        public MContentObserver(Handler handler) {
            super(handler);
        }

        public void onChange(boolean z, Uri uri) {
            if (MAccessibilityService.ENABLED_ACCESSIBILITY_SERVICES.equals(uri) && !Constants.checkAccessibilityEnabled(MAccessibilityService.this)) {
                MAccessibilityService.this.cleanUp();
            }
        }
    }

    public final void cleanUp() {
        this.handler.removeCallbacksAndMessages((Object) null);
        try {
            getContentResolver().unregisterContentObserver(mContentObserver);
        } catch (Throwable unused) {
        }
    }

    
    public void onServiceConnected() {
        this.mContext = this;
        setTheme(R.style.AppTheme);
        this.prefManager = new PrefManager(this);
        this.mHandle = new Handler();
        this.utils = new Utils(this);
        this.manager = (WindowManager) getSystemService("window");
        int i = Build.VERSION.SDK_INT;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.localLayoutParams = layoutParams;
        layoutParams.y = (int) (((float) this.prefManager.getYPosOfIsland()) * getResources().getDisplayMetrics().scaledDensity);
        this.localLayoutParams.type = 2032;
        this.localLayoutParams.gravity = 49;
        this.localLayoutParams.flags = this.flagNormal;
        this.localLayoutParams.width = (int) (((float) this.minCameIslandWidth) * getResources().getDisplayMetrics().scaledDensity);
        this.localLayoutParams.height = (int) (((float) this.zeroHeight) * getResources().getDisplayMetrics().scaledDensity);
        this.localLayoutParams.format = -3;
        StatusBarParentView statusBarParentView2 = (StatusBarParentView) LayoutInflater.from(this).inflate(R.layout.status_bar, (ViewGroup) null);
        this.statusBarView = statusBarParentView2;
        statusBarParentView2.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            public void onSystemUiVisibilityChange(int i) {
                if ((i & 4) == 0) {
                    boolean unused = MAccessibilityService.this.isInFullScreen = false;
                    MAccessibilityService.this.showSmallIslandNotification();
                    return;
                }
                boolean unused2 = MAccessibilityService.this.isInFullScreen = true;
                if (!Constants.getShowInFullScreen(MAccessibilityService.this.mContext)) {
                    MAccessibilityService.this.closeSmallIslandNotification();
                }
            }
        });
        this.statusBarParentView = this.statusBarView.findViewById(R.id.statusbar_parent);
        LinearLayout linearLayout = (LinearLayout) this.statusBarView.findViewById(R.id.island_parent_layout);
        this.island_parent_layout = linearLayout;
        linearLayout.setClipToOutline(true);
        this.island_top_layout = (LinearLayout) this.statusBarView.findViewById(R.id.island_top_layout);
        this.statusBarParentView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MAccessibilityService.this.closeFullNotificationIsland();
            }
        });
        this.localLayoutParams.softInputMode = 16;
        if (i >= 28) {
            this.localLayoutParams.layoutInDisplayCutoutMode = 1;
        }
        try {
            this.manager.addView(this.statusBarView, this.localLayoutParams);
        } catch (Exception unused) {
            Toast.makeText(this, "Unfortunately something didn't work. Please try again or contact the developer.", 1).show();
        }
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.listener = new MAccessibilityService$$ExternalSyntheticLambda2(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.media.RINGER_MODE_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        this.mContext.registerReceiver(this.mInfoReceiver, intentFilter);
        this.preferences.registerOnSharedPreferenceChangeListener(this.listener);
        this.island_parent_layout.getLayoutTransition().enableTransitionType(4);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(Utils.FROM_NOTIFICATION_SERVICE + this.mContext.getPackageName());
        intentFilter2.addAction("android.intent.action.SCREEN_ON");
        intentFilter2.addAction("android.intent.action.SCREEN_OFF");
        intentFilter2.addAction("android.intent.action.USER_PRESENT");
        try {
            LocalBroadcastManager.getInstance(this.mContext).registerReceiver(this.notiReceiver, intentFilter2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initNotifications();
        setIslandPosition();
        super.onServiceConnected();
    }

    public  void m111xf51aef93(SharedPreferences sharedPreferences, String str) {
        if (str.equals(PrefManager.KEY_DEFAULT_COLOR)) {
            this.utils.setTileColor(this.prefManager.getDefaultColor());
            this.adapter_island_small.notifyDataSetChanged();
        }
        if (str.equals(Constants.SHOW_IN_FULL_SCREEN)) {
            showTempBar();
        }
        if (str.equals(Constants.CONTROL_GESTURE)) {
            enableControls(Constants.getControlEnabled(this.mContext));
        }
        if (str.equals(Constants.SHOW_IN_LOCK)) {
            showTempBar();
        }
        if (str.equals(PrefManager.CAM_COUNT) || str.equals(PrefManager.CAM_POS)) {
            setIslandPosition();
            showTempBar();
        }
        if (str.equals(PrefManager.Y_POS)) {
            setYPosIsland();
            showTempBar();
        }
        if (str.equals(PrefManager.Y_HEIGHT)) {
            int heightOfIsland = this.prefManager.getHeightOfIsland();
            this.minIslandHeight = heightOfIsland;
            this.localLayoutParams.height = (int) (((float) heightOfIsland) * getResources().getDisplayMetrics().scaledDensity);
            this.manager.updateViewLayout(this.statusBarParentView, this.localLayoutParams);
            this.island_parent_layout.requestLayout();
            showTempBar();
        }
        if (str.equals(PrefManager.SELECTED_PKG)) {
            this.callPKG = PrefManager.getCallPkg(this.mContext);
        }
        if (str.equals(PrefManager.FILTER_PKG)) {
            this.filterPKG = PrefManager.getSelectedFilterPkg(this.mContext);
        }
        if (str.equals(PrefManager.IPHONE_CALL)) {
            this.useIphoneCallDesign = this.prefManager.getIphoneCall(this.mContext);
        }
    }

    private void setIslandPosition() {
        this.pos = this.prefManager.getCameraPos();
        int cameraCount = this.prefManager.getCameraCount();
        this.margin = (int) (getResources().getDisplayMetrics().scaledDensity * 25.0f);
        if (cameraCount == 1) {
            this.localLayoutParams.width = (int) (((float) this.minOneCameIslandWidth) * getResources().getDisplayMetrics().scaledDensity);
        }
        if (cameraCount == 2) {
            this.margin = (int) (getResources().getDisplayMetrics().scaledDensity * 50.0f);
            this.localLayoutParams.width = (int) (((float) this.minTwoCameIslandWidth) * getResources().getDisplayMetrics().scaledDensity);
        }
        if (cameraCount == 3) {
            this.localLayoutParams.width = (int) (((float) this.minThreeCameIslandWidth) * getResources().getDisplayMetrics().scaledDensity);
            this.margin = (int) (getResources().getDisplayMetrics().scaledDensity * 75.0f);
        }
        this.minCameIslandWidth = this.localLayoutParams.width;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.island_top_layout.getLayoutParams();
        int i = this.pos;
        if (i == 1) {
            setLeftCamera();
            layoutParams.leftMargin = this.margin;
            layoutParams.rightMargin = 0;
        } else if (i == 2) {
            setCenterCamera();
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
        } else if (i == 3) {
            setRightCamera();
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = this.margin;
        }
        this.island_top_layout.setLayoutParams(layoutParams);
        this.island_parent_layout.requestLayout();
    }

    private void setFullIslandMargin(boolean z) {
        if (z) {
            this.tempMargin = this.margin;
            this.margin = (int) (getResources().getDisplayMetrics().scaledDensity * 25.0f);
        } else {
            this.margin = this.tempMargin;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.island_top_layout.getLayoutParams();
        if (this.pos == 1) {
            layoutParams.leftMargin = this.margin;
            layoutParams.rightMargin = 0;
        }
        if (this.pos == 2) {
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
        }
        if (this.pos == 3) {
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = this.margin;
        }
        this.island_top_layout.setLayoutParams(layoutParams);
    }

    private void setYPosIsland() {
        this.localLayoutParams.y = (int) (((float) this.prefManager.getYPosOfIsland()) * getResources().getDisplayMetrics().scaledDensity);
        this.manager.updateViewLayout(this.statusBarParentView, this.localLayoutParams);
    }

    private void setLeftCamera() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    MAccessibilityService.this.localLayoutParams.gravity = 8388659;
                    MAccessibilityService.this.manager.updateViewLayout(MAccessibilityService.this.statusBarParentView, MAccessibilityService.this.localLayoutParams);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100);
    }

    private void setCenterCamera() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    MAccessibilityService.this.localLayoutParams.gravity = 49;
                    MAccessibilityService.this.manager.updateViewLayout(MAccessibilityService.this.statusBarParentView, MAccessibilityService.this.localLayoutParams);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100);
    }

    private void setRightCamera() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    MAccessibilityService.this.localLayoutParams.gravity = 8388661;
                    MAccessibilityService.this.manager.updateViewLayout(MAccessibilityService.this.statusBarParentView, MAccessibilityService.this.localLayoutParams);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100);
    }

    private int getIconColor(int i) {
        return ColorUtils.calculateLuminance(i) < 0.15d ? Color.rgb(240, 240, 240) : i;
    }

    public boolean isCallPkgFound(String str) {
        Iterator<AppDetail> it = this.callPKG.appDetailList.iterator();
        while (it.hasNext()) {
            if (it.next().pkg.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFilterPkgFound(String str) {
        Iterator<AppDetail> it = this.filterPKG.appDetailList.iterator();
        while (it.hasNext()) {
            if (it.next().pkg.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public void updateNotificationList(Intent intent) {
        String str;
        CharSequence charSequence;
        CharSequence charSequence2;
        CharSequence charSequence3;
        ArrayList arrayList;
        String str2;
        Intent intent2 = intent;
        Calendar instance = Calendar.getInstance();
        String stringExtra = intent2.getStringExtra("package");
        CharSequence charSequenceExtra = intent2.getCharSequenceExtra("substName");
        CharSequence charSequenceExtra2 = intent2.getCharSequenceExtra("subText");
        CharSequence charSequenceExtra3 = intent2.getCharSequenceExtra("titleBig");
        CharSequence charSequenceExtra4 = intent2.getCharSequenceExtra("summaryText");
        CharSequence charSequenceExtra5 = intent2.getCharSequenceExtra("info_text");
        int intExtra = intent2.getIntExtra("progressMax", 0);
        int intExtra2 = intent2.getIntExtra("progress", 0);
        boolean booleanExtra = intent2.getBooleanExtra("progressIndeterminate", false);
        boolean booleanExtra2 = intent2.getBooleanExtra("showChronometer", false);
        boolean booleanExtra3 = intent2.getBooleanExtra("isGroupConversation", false);
        boolean booleanExtra4 = intent2.getBooleanExtra("isAppGroup", false);
        boolean booleanExtra5 = intent2.getBooleanExtra("isGroup", false);
        boolean booleanExtra6 = intent2.getBooleanExtra("isOngoing", false);
        String stringExtra2 = intent2.getStringExtra("tag");
        String str3 = stringExtra2 == null ? "" : stringExtra2;
        int intExtra3 = intent2.getIntExtra("uId", 0);
        String stringExtra3 = intent2.getStringExtra("template");
        if (stringExtra3 == null) {
            str = "";
        } else {
            str = stringExtra3;
        }
        String stringExtra4 = intent2.getStringExtra(InAppPurchaseProvider.ID);
        String stringExtra5 = intent2.getStringExtra("group_key");
        String str4 = stringExtra5 == null ? stringExtra4 : stringExtra5;
        String stringExtra6 = intent2.getStringExtra("key");
        String str5 = stringExtra6 == null ? stringExtra4 : stringExtra6;
        String stringExtra7 = intent2.getStringExtra("category");
        String stringExtra8 = intent2.getStringExtra("appName");
        CharSequence charSequenceExtra6 = intent2.getCharSequenceExtra("title");
        if (charSequenceExtra6 == null) {
            charSequence = "";
        } else {
            charSequence = charSequenceExtra6;
        }
        CharSequence charSequenceExtra7 = intent2.getCharSequenceExtra("text");
        if (charSequenceExtra7 == null) {
            charSequence2 = "";
        } else {
            charSequence2 = charSequenceExtra7;
        }
        CharSequence charSequenceExtra8 = intent2.getCharSequenceExtra("bigText");
        if (charSequenceExtra8 == null) {
            charSequence3 = "";
        } else {
            charSequence3 = charSequenceExtra8;
        }
        int iconColor = getIconColor(intent2.getIntExtra("color", -1));
        boolean booleanExtra7 = intent2.getBooleanExtra("isClearable", true);
        boolean booleanExtra8 = intent2.getBooleanExtra("isAdded", true);
        CharSequence charSequence4 = charSequence;
        long longExtra = intent2.getLongExtra("postTime", instance.getTime().getTime());
        Bitmap bitmapFromByteArray = this.utils.getBitmapFromByteArray(intent2.getByteArrayExtra("icon"));
        Bitmap bitmapFromByteArray2 = this.utils.getBitmapFromByteArray(intent2.getByteArrayExtra("largeIcon"));
        Bitmap bitmapFromByteArray3 = this.utils.getBitmapFromByteArray(intent2.getByteArrayExtra("picture"));
        PendingIntent pendingIntent = (PendingIntent) intent2.getParcelableExtra(BaseGmsClient.KEY_PENDING_INTENT);
        Notification notification2 = null;
        try {
            arrayList = intent2.getParcelableArrayListExtra("actions");
        } catch (Exception unused) {
            arrayList = null;
        }
        if (booleanExtra8) {
            boolean z = booleanExtra7;
            String str6 = stringExtra4;
            Bitmap bitmap = bitmapFromByteArray;
            //Notification notification3 = r4;
            String str7 = str5;
            String str8 = str4;
            int i = intExtra2;
            boolean z2 = booleanExtra;
            int i2 = intExtra;
            Notification notification3 = new Notification(str6, bitmap, bitmapFromByteArray2, charSequence4, charSequence2, 0, stringExtra, longExtra, pendingIntent, arrayList, charSequence3, stringExtra8, z, iconColor, bitmapFromByteArray3, str8, str7, booleanExtra3, booleanExtra4, booleanExtra5, booleanExtra6, str3, intExtra3, str, charSequenceExtra, charSequenceExtra2, charSequenceExtra3, charSequenceExtra5, i2, i, z2, charSequenceExtra4, booleanExtra2, stringExtra7);
            notification3.useIphoneCallDesign = this.useIphoneCallDesign;
            int i3 = 0;
            int i4 = -1;
            while (i3 < this.list_small_island.size()) {
                if (!this.list_small_island.get(i3).isLocal) {
                    str2 = str8;
                    if (this.list_small_island.get(i3).groupKey.equals(str2)) {
                        i4 = i3;
                    }
                } else {
                    str2 = str8;
                }
                i3++;
                str8 = str2;
            }
            String str9 = str7;
            boolean equals = str8.equals(str9);
            if (i4 != -1) {
                this.list_small_island.get(i4).isClearable = z;
                this.list_small_island.get(i4).progress = i;
                this.list_small_island.get(i4).progressMax = i2;
                this.list_small_island.get(i4).progressIndeterminate = z2;
                if (equals || notification3.template.equals("InboxStyle") || notification3.tag.toLowerCase().contains("summary")) {
                    updateNotificationItem(notification3, i4);
                } else if (!isSameItem(notification3)) {
                    this.list_small_island.get(i4).keyMap.put(str9, notification3);
                }
            } else {
                this.list_small_island.add(notification3);
            }
            notification2 = notification3;
        } else {
            String str10 = str5;
            for (int size = this.list_small_island.size() - 1; size >= 0; size--) {
                if (!this.list_small_island.get(size).isLocal && this.list_small_island.get(size).key.equals(str10)) {
                    this.list_small_island.remove(size);
                }
            }
        }
        if (!isFilterPkgFound(stringExtra)) {
            if (notification2 == null || !notification2.category.equalsIgnoreCase(NotificationCompat.CATEGORY_CALL)) {
                showSmallIslandNotification();
            } else if (Constants.getAutoCloseNoti(this.mContext) && notification2.isOngoing) {
                closeHeadsUpNotification(notification2);
            } else if (!notification2.isOngoing || notification2.actions == null || notification2.actions.size() != 2) {
                this.currentIndex = this.list_small_island.size() - 1;
                closeFullNotificationIsland();
            } else {
                showFullIslandNotification(notification2);
            }
        }
        this.adapter_island_small.notifyDataSetChanged();
        this.adapter_island_big.notifyDataSetChanged();
    }

    public void setKeyboardFlag(final boolean z) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (z) {
                    MAccessibilityService.this.localLayoutParams.flags = MAccessibilityService.this.flagKeyBoard;
                } else {
                    MAccessibilityService.this.localLayoutParams.flags = MAccessibilityService.this.flagNormal;
                }
                try {
                    MAccessibilityService.this.manager.updateViewLayout(MAccessibilityService.this.statusBarParentView, MAccessibilityService.this.localLayoutParams);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 300);
    }

    public void closeSmallIslandNotification() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.island_parent_layout.getLayoutParams();
        layoutParams.width = (int) (this.utils.convertDpToPixel(1.0f, this.mContext) * getResources().getDisplayMetrics().scaledDensity);
        layoutParams.height = (int) (this.utils.convertDpToPixel(1.0f, this.mContext) * getResources().getDisplayMetrics().scaledDensity);
        this.island_parent_layout.setLayoutParams(layoutParams);
        setKeyboardFlag(false);
        new Handler().postDelayed(new MAccessibilityService$$ExternalSyntheticLambda0(this), 500);
    }


    public  void m109x91a39370() {
        this.localLayoutParams.height = (int) (this.utils.convertDpToPixel(1.0f, this.mContext) * getResources().getDisplayMetrics().scaledDensity);
        this.localLayoutParams.width = (int) (this.utils.convertDpToPixel(1.0f, this.mContext) * getResources().getDisplayMetrics().scaledDensity);
        this.manager.updateViewLayout(this.statusBarParentView, this.localLayoutParams);
        this.island_top_layout.setVisibility(8);
    }


    public  void m110lambda$new$2$comlockservicesMAccessibilityService() {
        if (this.list_small_island.size() == 0) {
            closeSmallIslandNotification();
        }
    }

    public void showTempBar() {
        this.island_top_layout.setVisibility(0);
        this.localLayoutParams.height = (int) (((float) this.prefManager.getHeightOfIsland()) * getResources().getDisplayMetrics().scaledDensity);
        this.localLayoutParams.width = this.minCameIslandWidth;
        this.manager.updateViewLayout(this.statusBarParentView, this.localLayoutParams);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.island_parent_layout.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        this.island_parent_layout.setLayoutParams(layoutParams);
        this.mHandle.removeCallbacks(this.mRunnable);
        this.mHandle.postDelayed(this.mRunnable, 3000);
    }

    public long getMediaDuration() {
        try {
            return ((MediaSessionManager) getSystemService("media_session")).getActiveSessions(new ComponentName(getApplicationContext(), NotificationService.class)).get(0).getMetadata().getLong("android.media.metadata.DURATION");
        } catch (Exception unused) {
            return 1;
        }
    }

    public long getMediaPosition() {
        try {
            return ((MediaSessionManager) getSystemService("media_session")).getActiveSessions(new ComponentName(getApplicationContext(), NotificationService.class)).get(0).getPlaybackState().getPosition();
        } catch (Exception unused) {
            return 0;
        }
    }

    
    public boolean isShowingSmall() {
        return this.localLayoutParams.width == this.minCameIslandWidth && this.island_top_layout.getVisibility() == 0;
    }

    public void showSmallIslandNotification() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (MAccessibilityService.this.list_small_island.size() == 0) {
                    if (MAccessibilityService.this.isShowingFullIsland()) {
                        MAccessibilityService.this.closeFullNotificationIsland();
                    }
                    if (MAccessibilityService.this.isShowingSmall()) {
                        MAccessibilityService.this.closeSmallIslandNotification();
                    }
                }
                if (MAccessibilityService.this.adapter_island_small.getItemCount() >= 1) {
                    MAccessibilityService.this.rv_island_small.scrollToPosition(MAccessibilityService.this.adapter_island_small.getItemCount() - 1);
                }
                if (MAccessibilityService.this.isShowingSmall()) {
                    MAccessibilityService.this.island_top_layout.setVisibility(0);
                    MAccessibilityService.this.rv_island_small.setVisibility(0);
                } else if (!MAccessibilityService.this.isControlEnabled) {
                } else {
                    if ((MAccessibilityService.this.isPhoneLocked && !Constants.getShowOnLock(MAccessibilityService.this.mContext)) || MAccessibilityService.this.isShowingFullIsland()) {
                        return;
                    }
                    if (!MAccessibilityService.this.isInFullScreen || Constants.getShowInFullScreen(MAccessibilityService.this.mContext)) {
                        MAccessibilityService.this.island_top_layout.setVisibility(0);
                        MAccessibilityService.this.rv_island_small.setVisibility(0);
                        MAccessibilityService.this.rv_island_big.setVisibility(8);
                        if (MAccessibilityService.this.list_small_island.size() == 0) {
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) MAccessibilityService.this.island_parent_layout.getLayoutParams();
                            layoutParams.width = (int) (MAccessibilityService.this.getResources().getDisplayMetrics().scaledDensity * 0.0f);
                            MAccessibilityService.this.island_parent_layout.setLayoutParams(layoutParams);
                            return;
                        }
                        MAccessibilityService.this.localLayoutParams.height = (int) (((float) MAccessibilityService.this.prefManager.getHeightOfIsland()) * MAccessibilityService.this.getResources().getDisplayMetrics().scaledDensity);
                        MAccessibilityService.this.localLayoutParams.width = MAccessibilityService.this.minCameIslandWidth;
                        MAccessibilityService.this.manager.updateViewLayout(MAccessibilityService.this.statusBarParentView, MAccessibilityService.this.localLayoutParams);
                        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) MAccessibilityService.this.island_parent_layout.getLayoutParams();
                        layoutParams2.width = -1;
                        layoutParams2.height = -1;
                        MAccessibilityService.this.island_parent_layout.setLayoutParams(layoutParams2);
                        if (MAccessibilityService.this.adapter_island_small.getItemCount() >= 1) {
                            MAccessibilityService.this.rv_island_small.scrollToPosition(MAccessibilityService.this.adapter_island_small.getItemCount() - 1);
                        }
                    }
                }
            }
        }, 1000);
    }

    public void closeHeadsUpNotification(final Notification notification2) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                DisplayMetrics displayMetrics = MAccessibilityService.this.getResources().getDisplayMetrics();
                GestureDescription.Builder builder = new GestureDescription.Builder();
                Path path = new Path();
                double d = (double) displayMetrics.heightPixels;
                float f = (float) ((displayMetrics.widthPixels / 2) + (displayMetrics.widthPixels / 4));
                path.moveTo(f, (float) (d * 0.1d));
                path.lineTo(f, (float) (0.01d * d));
                builder.addStroke(new GestureDescription.StrokeDescription(path, 100, 50));
                MAccessibilityService.this.dispatchGesture(builder.build(), new GestureResultCallback() {
                    public void onCompleted(GestureDescription gestureDescription) {
                        super.onCompleted(gestureDescription);
                        MAccessibilityService.this.showFullIslandNotification(notification2);
                    }
                }, (Handler) null);
            }
        }, 700);
    }

    private void setMediaUpdateHandler() {
        this.mediaHandler.postDelayed(this.mediaUpdateRunnable, 1000);
    }

    
    public boolean isShowingFullIsland() {
        return this.localLayoutParams.height == -1 && this.island_top_layout.getVisibility() == 0;
    }

    
    public void showFullIslandNotification(Notification notification2) {
        this.notification = notification2;
        if (!notification2.isLocal) {
            this.island_top_layout.setVisibility(0);
            this.rv_island_small.setVisibility(8);
            if (isShowingFullIsland()) {
                this.list_big_island.clear();
                this.adapter_island_big.notifyItemChanged(0);
                this.list_big_island.add(notification2);
                this.adapter_island_big.notifyItemChanged(0);
                return;
            }
            if (notification2.template.equals("MediaStyle")) {
                setMediaUpdateHandler();
            }
            setFullIslandMargin(true);
            setKeyboardFlag(true);
            this.localLayoutParams.height = -1;
            this.localLayoutParams.width = (int) (getResources().getDisplayMetrics().scaledDensity * 350.0f);
            this.manager.updateViewLayout(this.statusBarParentView, this.localLayoutParams);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.island_parent_layout.getLayoutParams();
            layoutParams.width = -1;
            if (notification2.category.equalsIgnoreCase(NotificationCompat.CATEGORY_CALL) && notification2.isOngoing && notification2.useIphoneCallDesign) {
                layoutParams.height = (int) (getResources().getDisplayMetrics().scaledDensity * 90.0f);
            } else if (notification2.actions == null || notification2.actions.size() <= 0) {
                layoutParams.height = (int) (getResources().getDisplayMetrics().scaledDensity * 110.0f);
            } else {
                layoutParams.height = (int) (getResources().getDisplayMetrics().scaledDensity * 170.0f);
            }
            this.island_parent_layout.setLayoutParams(layoutParams);
            this.rv_island_big.setVisibility(0);
            this.list_big_island.clear();
            this.adapter_island_big.notifyItemChanged(0);
            this.list_big_island.add(notification2);
            this.adapter_island_big.notifyItemChanged(0);
        }
    }

    public void closeFullNotificationIsland() {
        this.mediaHandler.removeCallbacks(this.mediaUpdateRunnable);
        setFullIslandMargin(false);
        setKeyboardFlag(false);
        this.localLayoutParams.height = (int) (getResources().getDisplayMetrics().scaledDensity * 170.0f);
        this.manager.updateViewLayout(this.statusBarParentView, this.localLayoutParams);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.island_parent_layout.getLayoutParams();
        layoutParams.width = (int) (getResources().getDisplayMetrics().scaledDensity * 0.0f);
        layoutParams.height = (int) (((float) this.minIslandHeight) * getResources().getDisplayMetrics().scaledDensity);
        this.island_parent_layout.setLayoutParams(layoutParams);
        this.rv_island_small.setVisibility(0);
        this.rv_island_big.setVisibility(8);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (MAccessibilityService.this.list_small_island.size() == 0) {
                    MAccessibilityService.this.closeSmallIslandNotification();
                    return;
                }
                MAccessibilityService.this.localLayoutParams.height = (int) (((float) MAccessibilityService.this.minIslandHeight) * MAccessibilityService.this.getResources().getDisplayMetrics().scaledDensity);
                MAccessibilityService.this.localLayoutParams.width = MAccessibilityService.this.minCameIslandWidth;
                MAccessibilityService.this.manager.updateViewLayout(MAccessibilityService.this.statusBarParentView, MAccessibilityService.this.localLayoutParams);
                new Handler().postDelayed(new MAcceExternalSyntheticLambda( this), 100);

            }

            public void comlockservicesMAccessibilityService$10() {
                /*LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) MAccessibilityService.this.island_parent_layout.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = -1;
                MAccessibilityService.this.island_parent_layout.setLayoutParams(layoutParams);
                if (MAccessibilityService.this.adapter_island_small.getItemCount() > MAccessibilityService.this.currentIndex) {
                    MAccessibilityService.this.rv_island_small.scrollToPosition(MAccessibilityService.this.currentIndex);
                }*/
            }
        }, 500);
    }

    private boolean isSameItem(Notification notification2) {
        if (notification2.pack.equals("com.whatsapp") && notification2.template.equals("")) {
            return true;
        }
        if (!notification2.pack.equals("com.google.android.gm") || !notification2.id.equals("0")) {
            return false;
        }
        return true;
    }

    private void updateNotificationItem(Notification notification2, int i) {
        this.list_small_island.get(i).senderIcon = notification2.senderIcon;
        this.list_small_island.get(i).icon = notification2.icon;
        this.list_small_island.get(i).actions = notification2.actions;
        this.list_small_island.get(i).pendingIntent = notification2.pendingIntent;
        this.list_small_island.get(i).tv_title = notification2.tv_title;
        this.list_small_island.get(i).tv_text = notification2.tv_text;
        this.list_small_island.get(i).pack = notification2.pack;
        this.list_small_island.get(i).postTime = notification2.postTime;
        this.list_small_island.get(i).count = notification2.count;
        this.list_small_island.get(i).bigText = notification2.bigText;
        this.list_small_island.get(i).app_name = notification2.app_name;
        this.list_small_island.get(i).isClearable = notification2.isClearable;
        this.list_small_island.get(i).color = notification2.color;
        this.list_small_island.get(i).picture = notification2.picture;
        this.list_small_island.get(i).id = notification2.id;
        this.list_small_island.get(i).template = notification2.template;
        this.list_small_island.get(i).key = notification2.key;
        this.list_small_island.get(i).groupKey = notification2.groupKey;
        this.list_small_island.get(i).isAppGroup = notification2.isAppGroup;
        this.list_small_island.get(i).isGroup = notification2.isGroup;
        this.list_small_island.get(i).isOngoing = notification2.isOngoing;
        this.list_small_island.get(i).isGroupConversation = notification2.isGroupConversation;
        this.list_small_island.get(i).showChronometer = notification2.showChronometer;
        this.list_small_island.get(i).progress = notification2.progress;
        this.list_small_island.get(i).progressMax = notification2.progressMax;
        this.list_small_island.get(i).progressIndeterminate = notification2.progressIndeterminate;
        this.list_small_island.get(i).category = notification2.category;
    }

    private void initNotifications() {
        this.rv_island_big = (CustomRecyclerView) this.statusBarView.findViewById(R.id.rv_island_big);
        this.rv_island_small = (RecyclerView) this.statusBarView.findViewById(R.id.rv_island_small);
        CustomNotificationIconAdapter customNotificationIconAdapter = new CustomNotificationIconAdapter(this, this.list_small_island, new NotificationListener() {
            public void onItemClicked(Notification notification) {
            }

            public void onItemClicked(Notification notification, int i) {
                MAccessibilityService.this.showFullIslandNotification(notification);
                MAccessibilityService.this.currentIndex = i;
            }
        });
        this.adapter_island_small = customNotificationIconAdapter;
        this.rv_island_small.setAdapter(customNotificationIconAdapter);
        this.rv_island_small.setHasFixedSize(true);
        this.rv_island_small.addItemDecoration(new ItemOffsetDecoration2(this, R.dimen.small_margin));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        this.rv_island_small.setLayoutManager(linearLayoutManager);
        this.adapter_island_big = new CustomNotificationAdapter(this, this.list_big_island, new NotificationListener() {
            public void onItemClicked(Notification notification) {
            }

            public void onItemClicked(Notification notification, int i) {
            }
        });
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(1);
        this.rv_island_big.setAdapter(this.adapter_island_big);
        this.rv_island_big.setHasFixedSize(true);
        this.rv_island_big.addItemDecoration(new ItemOffsetDecoration2(this, R.dimen.small_margin));
        this.rv_island_big.setLayoutManager(linearLayoutManager2);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent.getIntExtra("com.control.center.intent.MESSAGE", -1) == 0) {
            try {
                if (Build.VERSION.SDK_INT >= 24) {
                    disableSelf();
                }
            } catch (Exception unused) {
            }
        }
        return super.onStartCommand(intent, i, i2);
    }

    private void enableControls(boolean z) {
        this.isControlEnabled = z;
        if (!z) {
            closeSmallIslandNotification();
        } else {
            showTempBar();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateFontSize(configuration);
    }

    public final void updateFontSize(Configuration configuration) {
        if (configuration.fontScale > 1.3f) {
            configuration.fontScale = 1.3f;
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            ((WindowManager) getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            displayMetrics.scaledDensity = configuration.fontScale * displayMetrics.density;
            getResources().updateConfiguration(configuration, displayMetrics);
        }
    }

    public void onDestroy() {
        Context context;
        Context context2;
        SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;
        View view;
        try {
            WindowManager windowManager = this.manager;
            if (!(windowManager == null || (view = this.statusBarParentView) == null)) {
                windowManager.removeView(view);
            }
            SharedPreferences sharedPreferences = this.preferences;
            if (!(sharedPreferences == null || (onSharedPreferenceChangeListener = this.listener) == null)) {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            }
            if (!(this.notiReceiver == null || (context2 = this.mContext) == null)) {
                LocalBroadcastManager.getInstance(context2).unregisterReceiver(this.notiReceiver);
            }
            BroadcastReceiver broadcastReceiver = this.mInfoReceiver;
            if (!(broadcastReceiver == null || (context = this.mContext) == null)) {
                context.unregisterReceiver(broadcastReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    
    public void scrollToPos(int i) {
        this.adapter_island_small.notifyDataSetChanged();
        if (!isShowingSmall()) {
            showSmallIslandNotification();
        }
        if (this.adapter_island_small.getItemCount() > 0) {
            this.rv_island_small.scrollToPosition(i);
        }
    }

    
    public void scrollToLastItem() {
        this.adapter_island_small.notifyDataSetChanged();
        if (this.adapter_island_small.getItemCount() > 0) {
            this.rv_island_small.scrollToPosition(this.adapter_island_small.getItemCount() - 1);
        }
        showSmallIslandNotification();
    }

    private class MAcceExternalSyntheticLambda implements Runnable {

        MAcceExternalSyntheticLambda aaa;

        public MAcceExternalSyntheticLambda(Runnable runnable) {
        }

        @Override
        public void run() {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) MAccessibilityService.this.island_parent_layout.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            MAccessibilityService.this.island_parent_layout.setLayoutParams(layoutParams);
            if (MAccessibilityService.this.adapter_island_small.getItemCount() > MAccessibilityService.this.currentIndex) {
                MAccessibilityService.this.rv_island_small.scrollToPosition(MAccessibilityService.this.currentIndex);
            }
        }
    }
}
