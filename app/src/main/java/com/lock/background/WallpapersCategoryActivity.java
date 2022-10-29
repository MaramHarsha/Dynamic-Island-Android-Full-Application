package com.lock.background;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.lock.MyOneApplication;
import com.dynamic.island.harsha.notification.R;
import java.util.ArrayList;
import org.json.JSONObject;

public class WallpapersCategoryActivity extends AppCompatActivity {

    private WallpaperCategoryAdapter adapter;
    private int columnWidth;
    private GridView gridView;
    Context mContext;
    public ProgressBar pbLoader;
    public ArrayList<WallpaperCategoryList> photosList = new ArrayList<>();
    private PrefManager pref;
    private Utils utils;
    
    public String wallpaperPosition;

    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.wallpapers_category_layout);
        this.mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.setting_tv_color));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getWindow().getDecorView().setSystemUiVisibility(8192);
        Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.setTint(getResources().getColor(R.color.setting_tv_color));
        }
        this.utils = new Utils(this.mContext);
        this.pref = new PrefManager(this.mContext);
        this.photosList = new ArrayList<>();
        GridView gridView2 = (GridView) findViewById(R.id.grid_view);
        this.gridView = gridView2;
        gridView2.setVisibility(8);
        InitilizeGridLayout();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbLoader);
        this.pbLoader = progressBar;
        progressBar.setVisibility(0);
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(0, AppConst.CATEGORY_URL, (JSONObject) null, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject jSONObject) {
                    WallpapersCategoryActivity.this.getAllImages(jSONObject);
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                    WallpapersCategoryActivity.this.pbLoader.setVisibility(8);
                    Toast.makeText(WallpapersCategoryActivity.this.mContext, "Try Again in Few minutes", 1).show();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 2, 1.0f));
            jsonObjectRequest.setShouldCache(false);
            MyOneApplication.getInstance().addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                WallpapersCategoryActivity wallpapersCategoryActivity = WallpapersCategoryActivity.this;
                String unused = wallpapersCategoryActivity.wallpaperPosition = ((WallpaperCategoryList) wallpapersCategoryActivity.photosList.get(i)).getId().trim();

                Intent intent = new Intent(WallpapersCategoryActivity.this.mContext, WallpapersActivity.class);
                intent.putExtra("album_id", ((WallpaperCategoryList) WallpapersCategoryActivity.this.photosList.get(i)).getId().trim());
                WallpapersCategoryActivity.this.startActivity(intent);
            }
        });
    }

    public void getAllImages(JSONObject jSONObject) {
        try {
            this.photosList.clear();
            this.pbLoader.setVisibility(8);
            this.gridView.setVisibility(0);
            this.photosList.addAll(((WallpaperCategory) new Gson().fromJson(jSONObject.toString(), WallpaperCategory.class)).getCategory());
            WallpaperCategoryAdapter wallpaperCategoryAdapter = new WallpaperCategoryAdapter(this.mContext, this.photosList, this.columnWidth);
            this.adapter = wallpaperCategoryAdapter;
            this.gridView.setAdapter(wallpaperCategoryAdapter);
            this.adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void InitilizeGridLayout() {
        float applyDimension = TypedValue.applyDimension(1, 4.0f, getResources().getDisplayMetrics());
        this.columnWidth = (int) ((((float) this.utils.getScreenWidth()) - (((float) (this.pref.getNoOfGridColumnsCategory() + 1)) * applyDimension)) / ((float) this.pref.getNoOfGridColumnsCategory()));
        this.gridView.setNumColumns(this.pref.getNoOfGridColumnsCategory());
        this.gridView.setColumnWidth(this.columnWidth);
        this.gridView.setStretchMode(0);
        int i = (int) applyDimension;
        this.gridView.setPadding(i, i, i, i);
        this.gridView.setHorizontalSpacing(i);
        this.gridView.setVerticalSpacing(i);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
