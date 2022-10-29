package com.lock.fragment;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.lock.Controllers.LocationSettingController;
import com.lock.SplashLaunchActivity;
import com.lock.activites.AppsFilterListActivity;
import com.lock.activites.AppsListActivity;
import com.lock.activites.HelpActivity;
import com.lock.activites.HomeActivity;

import com.lock.background.PrefManager;
import com.lock.background.Utils;
import com.lock.background.WallpapersCategoryActivity;
import com.lock.utils.Constants;
import com.lock.utils.MySettings;
import com.lock.utils.RatingDialog;
import com.dynamic.island.harsha.notification.R;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.flag.BubbleFlag;
import com.skydoves.colorpickerview.listeners.ColorPickerViewListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    View backgroung_rl;
    Context context;
    private ToggleButton enable_controls_gesture;
    LocationManager locationManager;
    RelativeLayout more_rl;
    PrefManager prefManager;
    RelativeLayout rateus_rl;
    RelativeLayout share_rl;
    TextView textViewBackgroundGallery;
    private ToggleButton toggle_hide_in_full_screen;
    private ToggleButton toggle_show_on_lock;
    TextView tv_more;
    TextView tv_rateus;
    private TextView tv_remove_ads;
    TextView tv_share;
    Typeface typefaceBold;
    public View viw;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_settings2, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        this.context = getActivity();
        this.prefManager = new PrefManager(this.context);
        this.textViewBackgroundGallery = (TextView) view.findViewById(R.id.textViewBackgroundGallery);
        this.viw = view;
        this.typefaceBold = Typeface.createFromAsset(this.context.getAssets(), "roboto_medium.ttf");
        view.findViewById(R.id.help_rl).setOnClickListener(this);
        view.findViewById(R.id.privacy_rl).setOnClickListener(this);
        view.findViewById(R.id.cv_tile_color).setOnClickListener(this);
        view.findViewById(R.id.rl_appsList).setOnClickListener(this);
        view.findViewById(R.id.rl_appsFilterList).setOnClickListener(this);
        ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.enable_island);
        this.enable_controls_gesture = toggleButton;
        toggleButton.setChecked(Constants.getControlEnabled(this.context));
        this.enable_controls_gesture.setOnCheckedChangeListener(new SettingsFragment$$ExternalSyntheticLambda15(this));
        ToggleButton toggleButton2 = (ToggleButton) view.findViewById(R.id.toggle_show_on_lock);
        this.toggle_show_on_lock = toggleButton2;
        toggleButton2.setChecked(Constants.getShowOnLock(this.context));
        this.toggle_show_on_lock.setOnCheckedChangeListener(new SettingsFragment$$ExternalSyntheticLambda16(this));
        ToggleButton toggleButton3 = (ToggleButton) view.findViewById(R.id.toggle_hide_in_full_screen);
        this.toggle_hide_in_full_screen = toggleButton3;
        toggleButton3.setChecked(Constants.getShowInFullScreen(this.context));
        this.toggle_hide_in_full_screen.setOnCheckedChangeListener(new SettingsFragment$$ExternalSyntheticLambda17(this));
        ToggleButton toggleButton4 = (ToggleButton) view.findViewById(R.id.toggle_auto_close_noti);
        toggleButton4.setChecked(Constants.getAutoCloseNoti(this.context));
        toggleButton4.setOnCheckedChangeListener(new SettingsFragment$$ExternalSyntheticLambda1(this));
        ToggleButton toggleButton5 = (ToggleButton) view.findViewById(R.id.toggle_hide_iphone_call);
        toggleButton5.setChecked(this.prefManager.getIphoneCall(this.context));
        toggleButton5.setOnCheckedChangeListener(new SettingsFragment$$ExternalSyntheticLambda2(this));
        ((ImageView) view.findViewById(R.id.iv_tile_color)).setColorFilter(this.prefManager.getDefaultColor());
        this.locationManager = (LocationManager) this.context.getSystemService("location");
        this.backgroung_rl = view.findViewById(R.id.card_view_BackgroundGallery);
        TextView textView = (TextView) view.findViewById(R.id.tv_remove_ads);
        this.tv_remove_ads = textView;
        textView.setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.tv_tile_color)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.textViewEnableCC)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.tv_show_on_lock)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.tv_hide_in_full_screen)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.help_more)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.camera_count_text)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.camera_pos_text)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.top_margin_tv)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.top_height_tv)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.y_pos_text)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.y_height_text)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.tv_auto_close_noti)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.tv_reverse_call_btn)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.tv_FilterList)).setTypeface(this.typefaceBold);
        ((TextView) view.findViewById(R.id.tv_iphone_call)).setTypeface(this.typefaceBold);
        this.tv_rateus = (TextView) view.findViewById(R.id.tv_rateus);
        this.tv_more = (TextView) view.findViewById(R.id.tv_more);
        this.tv_share = (TextView) view.findViewById(R.id.tv_share);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.rateus_rl);
        this.rateus_rl = relativeLayout;
        relativeLayout.setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda3(this));
        RelativeLayout relativeLayout2 = (RelativeLayout) view.findViewById(R.id.share_rl);
        this.share_rl = relativeLayout2;
        relativeLayout2.setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda4(this));
        RelativeLayout relativeLayout3 = (RelativeLayout) view.findViewById(R.id.more_rl);
        this.more_rl = relativeLayout3;
        relativeLayout3.setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda5(this));
        this.tv_share.setTypeface(this.typefaceBold);
        this.tv_rateus.setTypeface(this.typefaceBold);
        this.tv_more.setTypeface(this.typefaceBold);
        this.textViewBackgroundGallery.setTypeface(this.typefaceBold);
        this.backgroung_rl.setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda6(this));
        if (Constants.isFirstTimeLoad(this.context)) {
            Context context2 = this.context;
            Constants.setWallpaperColor(context2, context2.getResources().getColor(R.color.default_wallpaper_color));
        }
        int cameraCount = this.prefManager.getCameraCount();
        if (cameraCount == 1) {
            ((RadioButton) view.findViewById(R.id.one_camera)).setChecked(true);
        }
        if (cameraCount == 2) {
            ((RadioButton) view.findViewById(R.id.two_camera)).setChecked(true);
        }
        if (cameraCount == 3) {
            ((RadioButton) view.findViewById(R.id.three_camera)).setChecked(true);
        }
        ((RadioGroup) view.findViewById(R.id.cam_count_group)).setOnCheckedChangeListener(new SettingsFragment$$ExternalSyntheticLambda9(this));
        int cameraPos = this.prefManager.getCameraPos();
        if (cameraPos == 1) {
            ((RadioButton) view.findViewById(R.id.left_camera)).setChecked(true);
        }
        if (cameraPos == 2) {
            ((RadioButton) view.findViewById(R.id.center_camera)).setChecked(true);
        }
        if (cameraPos == 3) {
            ((RadioButton) view.findViewById(R.id.right_camera)).setChecked(true);
        }
        ((RadioGroup) view.findViewById(R.id.cam_pos_group)).setOnCheckedChangeListener(new SettingsFragment$$ExternalSyntheticLambda10(this));
        ((TextView) view.findViewById(R.id.y_pos_text)).setText(this.prefManager.getYPosOfIsland() + "");
        view.findViewById(R.id.y_plus_btn).setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda11(this, view));
        view.findViewById(R.id.y_minus_btn).setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda12(this, view));
        ((TextView) view.findViewById(R.id.y_height_text)).setText(this.prefManager.getHeightOfIsland() + "");
        view.findViewById(R.id.y_height_plus_btn).setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda13(this, view));
        view.findViewById(R.id.y_height_minus_btn).setOnClickListener(new SettingsFragment$$ExternalSyntheticLambda14(this, view));
    }

    public  void m93lambda$onViewCreated$0$comlockfragmentSettingsFragment(View view) {
        getCustomAppBackground();
    }

    public  void m94lambda$onViewCreated$1$comlockfragmentSettingsFragment(CompoundButton compoundButton, boolean z) {
        Constants.setControlEnabled(this.context, z);
    }
    
    public  void m101lambda$onViewCreated$2$comlockfragmentSettingsFragment(CompoundButton compoundButton, boolean z) {
        Constants.setShowOnLock(this.context, z);
    }

    public  void m102lambda$onViewCreated$3$comlockfragmentSettingsFragment(CompoundButton compoundButton, boolean z) {
        Constants.SetShowInFullScreen(this.context, z);
    }

    public  void m103lambda$onViewCreated$4$comlockfragmentSettingsFragment(CompoundButton compoundButton, boolean z) {
        Constants.setAutoCloseNoti(this.context, z);
    }

    public  void m104lambda$onViewCreated$5$comlockfragmentSettingsFragment(CompoundButton compoundButton, boolean z) {
        this.prefManager.setIphoneCall(this.context, z);
    }

    public  void m105lambda$onViewCreated$6$comlockfragmentSettingsFragment(View view) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + this.context.getPackageName())));
    }

    public  void m106lambda$onViewCreated$7$comlockfragmentSettingsFragment(View view) {
        shareLink("https://play.google.com/store/apps/details?id=" + this.context.getApplicationInfo().packageName);
    }

    public  void m107lambda$onViewCreated$8$comlockfragmentSettingsFragment(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + SplashLaunchActivity.MoreApps));
        startActivity(intent);
    }

    public  void m108lambda$onViewCreated$9$comlockfragmentSettingsFragment(View view) {
        startActivity(new Intent(this.context, WallpapersCategoryActivity.class));
    }

    public  void m95lambda$onViewCreated$10$comlockfragmentSettingsFragment(RadioGroup radioGroup, int i) {
        if (i == R.id.one_camera) {
            this.prefManager.setCameraCount(1);
        } else if (i == R.id.two_camera) {
            this.prefManager.setCameraCount(2);
        } else if (i == R.id.three_camera) {
            this.prefManager.setCameraCount(3);
        }
    }

    public  void m96lambda$onViewCreated$11$comlockfragmentSettingsFragment(RadioGroup radioGroup, int i) {
        if (i == R.id.left_camera) {
            this.prefManager.setCameraPos(1);
        } else if (i == R.id.center_camera) {
            this.prefManager.setCameraPos(2);
        } else if (i == R.id.right_camera) {
            this.prefManager.setCameraPos(3);
        }
    }

    public  void m97lambda$onViewCreated$12$comlockfragmentSettingsFragment(View view, View view2) {
        String obj = ((TextView) view.findViewById(R.id.y_pos_text)).getText().toString();
        if (Integer.parseInt(obj) < 20) {
            int parseInt = Integer.parseInt(obj) + 1;
            ((TextView) view.findViewById(R.id.y_pos_text)).setText(parseInt + "");
            this.prefManager.setYPosOfIsland(parseInt);
        }
    }

    public  void m98lambda$onViewCreated$13$comlockfragmentSettingsFragment(View view, View view2) {
        String obj = ((TextView) view.findViewById(R.id.y_pos_text)).getText().toString();
        if (Integer.parseInt(obj) >= 1) {
            int parseInt = Integer.parseInt(obj) - 1;
            ((TextView) view.findViewById(R.id.y_pos_text)).setText(parseInt + "");
            this.prefManager.setYPosOfIsland(parseInt);
        }
    }

    public  void m99lambda$onViewCreated$14$comlockfragmentSettingsFragment(View view, View view2) {
        String obj = ((TextView) view.findViewById(R.id.y_height_text)).getText().toString();
        if (Integer.parseInt(obj) < 40) {
            int parseInt = Integer.parseInt(obj) + 1;
            ((TextView) view.findViewById(R.id.y_height_text)).setText(parseInt + "");
            this.prefManager.setHeightOfIsland(parseInt);
        }
    }

    public  void m100lambda$onViewCreated$15$comlockfragmentSettingsFragment(View view, View view2) {
        String obj = ((TextView) view.findViewById(R.id.y_height_text)).getText().toString();
        if (Integer.parseInt(obj) >= 25) {
            int parseInt = Integer.parseInt(obj) - 1;
            ((TextView) view.findViewById(R.id.y_height_text)).setText(parseInt + "");
            this.prefManager.setHeightOfIsland(parseInt);
        }
    }

    public void shareLink(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", str);
        boolean z = false;
        try {
            this.context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            intent.setPackage("com.facebook.katana");
            z = true;
        } catch (Exception unused) {
        }
        if (!z) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/sharer/sharer.php?u=" + str));
        }
        try {
            startActivityForResult(intent, 6565);
        } catch (Exception unused2) {
            Toast.makeText(this.context, "Error: Unable to share", 1).show();
        }
    }

    public void getCustomAppBackground() {
        ImagePicker.with((Fragment) this).crop().compress(1024).maxResultSize(1080, 1920).start();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Uri data;
        String pathFromUri;
        Uri data2;
        if (!(intent == null || (data2 = intent.getData()) == null)) {
            String path = data2.getPath();
            if (new File(path).exists()) {
                Glide.with(this.context).asBitmap().load(path).into(new CustomTarget<Bitmap>() {
                    public void onLoadCleared(Drawable drawable) {
                    }

                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        if (bitmap.getWidth() > 300) {
                            Utils.saveToInternalSorage(SettingsFragment.this.getActivity(), bitmap);
                        }
                    }
                });
            }
        }
        if (i == 1133) {
            if (intent != null && (data = intent.getData()) != null && (pathFromUri = Constants.getPathFromUri(this.context, data)) != null && new File(pathFromUri).exists()) {
                Glide.with(this.context).asBitmap().load(pathFromUri).into(new CustomTarget<Bitmap>() {
                    public void onLoadCleared(Drawable drawable) {
                    }

                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        Utils.saveImage(bitmap);
                    }
                });
            }
        } else if (i == 9 && Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this.context)) {
                enableLockToggle();
            } else {
                disableLockToggle();
            }
        }
    }

    private void disableLockToggle() {
        MySettings.setLockscreen(false, this.context);
    }

    private void enableLockToggle() {
        MySettings.setLockscreen(true, this.context);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_tile_color:
                ColorPickerDialog.Builder negativeButton = new ColorPickerDialog.Builder(this.context).setTitle((CharSequence) "Pick tiles color").setPositiveButton((CharSequence) getString(R.string.confirm), (ColorPickerViewListener) new SettingsFragment$$ExternalSyntheticLambda7(this)).setNegativeButton((CharSequence) getString(R.string.cancel), (DialogInterface.OnClickListener) new SettingsFragment$$ExternalSyntheticLambda8());
                negativeButton.getColorPickerView().setFlagView(new BubbleFlag(this.context));
                negativeButton.show();
                return;
            case R.id.help_rl:
                showHelpScreen();
                return;
            case R.id.privacy_rl:
                Intent intentPrivacy = new Intent(Intent.ACTION_VIEW, Uri.parse(SplashLaunchActivity.PrivacyPolicy));
                intentPrivacy.setPackage("com.android.chrome");
                startActivity(intentPrivacy);
                return;
            case R.id.rl_appsFilterList:
                Intent intent1 = new Intent(getActivity(), AppsFilterListActivity.class);
                SplashLaunchActivity.InterstitialAdsCall(getActivity(), intent1);
                return;
            case R.id.rl_appsList:
                Intent intent = new Intent(getActivity(), AppsListActivity.class);
                SplashLaunchActivity.InterstitialAdsCall(getActivity(), intent);
                return;
            default:
                return;
        }
    }

    public void m92lambda$onClick$16$comlockfragmentSettingsFragment(ColorEnvelope colorEnvelope, boolean z) {
        setLayoutColor(colorEnvelope);
    }

    private void setLayoutColor(ColorEnvelope colorEnvelope) {
        ((ImageView) this.viw.findViewById(R.id.iv_tile_color)).setColorFilter(colorEnvelope.getColor());
        new PrefManager(getActivity()).setKeyDefaultColor(colorEnvelope.getColor());
        if (Constants.getRatingDailoge(this.context)) {
            new RatingDialog((HomeActivity) this.context).showDialog();
        }
    }

    private void showHelpScreen() {
        Intent intent = new Intent(getActivity(), HelpActivity.class);
        SplashLaunchActivity.InterstitialAdsCall(getActivity(), intent);
    }

    private void checkMethod() {
        Toast.makeText(getActivity(), "Name: " + new LocationSettingController(getActivity()).getName(), 0).show();
    }

    private void getPrivateFunc(boolean z) {
        WallpaperManager.getInstance(getActivity());
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService("wifi");
        try {
            wifiManager.getClass().getMethod("setWifiEnabled", new Class[]{Boolean.TYPE}).invoke(wifiManager, new Object[]{Boolean.valueOf(z)});
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void askForPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.MODIFY_PHONE_STATE") != 0 && Build.VERSION.SDK_INT >= 23) {
            getActivity().requestPermissions(new String[]{"android.permission.MODIFY_PHONE_STATE"}, 69);
        }
    }

}
