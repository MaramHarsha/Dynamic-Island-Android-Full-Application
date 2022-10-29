package com.lock.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.github.mmin18.widget.RealtimeBlurView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.lock.SplashLaunchActivity;
import com.lock.adaptar.ViewPagerAdapter;
import com.lock.utils.Constants;
import com.dynamic.island.harsha.notification.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    static final int RC_REQUEST = 10001;
    static final String SKU_GAS = "no_ads";
    static final String TAG = "InAppPurchase";
    public String[] STORAGE_PERMISSION = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    TextView bg_btn;
    DrawerLayout drawer;
    Boolean isAdRemoved;
    NavigationBarView.OnItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                /*case R.id.navigation_apps:
                    HomeActivity.this.viewPager.setCurrentItem(3);
                    return true;*/
                case R.id.navigation_hone:
                    HomeActivity.this.viewPager.setCurrentItem(0);
                    return true;
                /*case R.id.navigation_lock:
                    HomeActivity.this.viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_theme:
                    HomeActivity.this.viewPager.setCurrentItem(1);
                    return true;*/
                default:
                    return false;
            }
        }
    };
    TextView more_btn;
    BottomNavigationView navigation;
    private ProgressDialog pd_progressDialog;
    TextView permission_btn;
    MenuItem prevMenuItem;
    TextView rate_us_btn;
    RealtimeBlurView real_time_blur;
    TextView remove_ads_btn;
    public ViewPager2 viewPager;

    private void setFullScreen() {
        getWindow().getDecorView().setSystemUiVisibility(8192);
    }

    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_drawer);


        //fb ads call
        SplashLaunchActivity.FBInterstitialAdCall(this);


        //Mix Banner Ads Call
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.btm10);
        RelativeLayout adContainer2 = (RelativeLayout) findViewById(R.id.ads2);
        ImageView OwnBannerAds = (ImageView) findViewById(R.id.bannerads);
        SplashLaunchActivity.MixBannerAdsCall(this, adContainer, adContainer2, OwnBannerAds);


        setFullScreen();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.setting_tv_color));
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.setting_tv_color, (Resources.Theme) null));
        this.drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.pd_progressDialog = progressDialog;
        progressDialog.setProgressStyle(0);
        this.pd_progressDialog.setMessage(" Please wait...");
        this.pd_progressDialog.setCancelable(false);
        this.pd_progressDialog.setTitle("Loading");
        setWaitScreen(true);
        this.navigation = (BottomNavigationView) findViewById(R.id.navigation);
        this.viewPager = (ViewPager2) findViewById(R.id.viewpager);
        this.remove_ads_btn = (TextView) findViewById(R.id.remove_ads_btn);
        this.rate_us_btn = (TextView) findViewById(R.id.rate_us_btn);
        this.more_btn = (TextView) findViewById(R.id.more_btn);
        this.permission_btn = (TextView) findViewById(R.id.permission_btn);
        this.bg_btn = (TextView) findViewById(R.id.bg_btn);
        this.real_time_blur = (RealtimeBlurView) findViewById(R.id.real_time_blur);
        this.navigation.setOnItemSelectedListener(this.mOnNavigationItemSelectedListener);

        this.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageScrolled(int i, float f, int i2) {
                if (HomeActivity.this.prevMenuItem != null) {
                    HomeActivity.this.prevMenuItem.setChecked(false);
                } else {
                    HomeActivity.this.navigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + i);
                HomeActivity.this.navigation.getMenu().getItem(i).setChecked(true);
                HomeActivity homeActivity = HomeActivity.this;
                homeActivity.prevMenuItem = homeActivity.navigation.getMenu().getItem(i);
                super.onPageScrolled(i, f, i2);
            }

            public void onPageSelected(int i) {
                super.onPageSelected(i);
            }

            public void onPageScrollStateChanged(int i) {
                super.onPageScrollStateChanged(i);
            }
        });
        this.viewPager.setOffscreenPageLimit(1);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                HomeActivity.this.setWaitScreen(false);
                HomeActivity homeActivity = HomeActivity.this;
                homeActivity.setupViewPager(homeActivity.viewPager);
            }
        }, 2000);
        this.real_time_blur.setOnClickListener(this);
        this.remove_ads_btn.setOnClickListener(this);
        this.bg_btn.setOnClickListener(this);
        this.rate_us_btn.setOnClickListener(this);
        this.more_btn.setOnClickListener(this);
        this.permission_btn.setOnClickListener(this);
        if (Constants.getCameraPkg(this).equals("")) {
            loadApps();
        }
    }

    private void loadApps() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    PackageManager packageManager = HomeActivity.this.getPackageManager();
                    Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
                    intent.addCategory("android.intent.category.LAUNCHER");
                    for (ResolveInfo next : packageManager.queryIntentActivities(intent, 0)) {
                        String obj = next.loadLabel(packageManager).toString();
                        String str = next.activityInfo.packageName;
                        if (obj.toLowerCase().equals("camera")) {
                            Constants.setCameraPkg(HomeActivity.this, next.activityInfo.packageName);
                        }
                        if (next.loadLabel(packageManager).toString().toLowerCase().equals("phone") || next.activityInfo.name.toLowerCase().equals("dialler")) {
                            Constants.setPhonePkg(HomeActivity.this, next.activityInfo.packageName);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bg_btn:
                if (this.drawer.isDrawerOpen((int) GravityCompat.START)) {
                    this.drawer.closeDrawer((int) GravityCompat.START);
                }
                //startActivity(new Intent(this, WallpapersCategoryActivity.class));
                return;
            case R.id.more_btn:
                Intent intentM = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + SplashLaunchActivity.MoreApps));
                startActivity(intentM);
                return;
            case R.id.permission_btn:
                if (this.drawer.isDrawerOpen((int) GravityCompat.START)) {
                    this.drawer.closeDrawer((int) GravityCompat.START);
                }
                startActivity(new Intent(this, PermissionsActivity.class));
                return;
            case R.id.rate_us_btn:
                if (this.drawer.isDrawerOpen((int) GravityCompat.START)) {
                    this.drawer.closeDrawer((int) GravityCompat.START);
                }
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    return;
                } catch (Exception unused) {
                }
            case R.id.remove_ads_btn:
                if (this.drawer.isDrawerOpen((int) GravityCompat.START)) {
                    this.drawer.closeDrawer((int) GravityCompat.START);
                }

                return;
            default:
                return;
        }
    }

    public void onBackPressed() {
        if (this.drawer.isDrawerOpen((int) GravityCompat.START)) {
            this.drawer.closeDrawer((int) GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setupViewPager(ViewPager2 viewPager2) {
        viewPager2.setAdapter(new ViewPagerAdapter(this));
        viewPager2.setSaveEnabled(false);
    }

    public void setWaitScreen(boolean z) {
        try {
            ProgressDialog progressDialog = this.pd_progressDialog;
            if (progressDialog == null) {
                return;
            }
            if (z) {
                progressDialog.show();
            } else if (!isFinishing()) {
                this.pd_progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
