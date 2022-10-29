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

public class WallpapersActivity extends AppCompatActivity {

    private GridViewAdapter adapter;
    private int columnWidth;
    private GridView gridView;
    Context mContext;
    public ProgressBar pbLoader;
    public ArrayList<ImageList> photosList = new ArrayList<>();
    private PrefManager pref;
    private Utils utils;
    public String wallpaperPosition;

    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.wallpapers_fragment_layout);
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
        getIntent();
        String str = "http://45.55.46.214/wallpaper_ahmed/api/albumimageurl.php?package_name=" + this.mContext.getApplicationContext().getPackageName() + "&album_id=" + getIntent().getStringExtra("album_id");
        GridView gridView2 = (GridView) findViewById(R.id.grid_view);
        this.gridView = gridView2;
        gridView2.setVisibility(8);
        InitilizeGridLayout();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbLoader);
        this.pbLoader = progressBar;
        progressBar.setVisibility(0);
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(0, str, (JSONObject) null, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject jSONObject) {
                    WallpapersActivity.this.getAllImages(jSONObject);
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                    WallpapersActivity.this.pbLoader.setVisibility(8);
                    Toast.makeText(WallpapersActivity.this.mContext, "Try Again in Few minutes", 1).show();
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
                WallpapersActivity wallpapersActivity = WallpapersActivity.this;
                wallpapersActivity.wallpaperPosition = ((ImageList) wallpapersActivity.photosList.get(i)).getImage().trim();

                Intent intent = new Intent(WallpapersActivity.this.mContext, FullScreenViewActivity.class);
                intent.putExtra("image_name", ((ImageList) WallpapersActivity.this.photosList.get(i)).getImage().trim());
                WallpapersActivity.this.startActivity(intent);
            }
        });
    }

    public void getAllImages(JSONObject jSONObject) {
        try {
            Wallpaper wallpaper = (Wallpaper) new Gson().fromJson(jSONObject.toString(), Wallpaper.class);
            if (wallpaper.getStatus().equals("true")) {
                this.photosList.clear();
                this.pbLoader.setVisibility(8);
                this.gridView.setVisibility(0);
                this.photosList.addAll(wallpaper.getAlbum_images());
                GridViewAdapter gridViewAdapter = new GridViewAdapter(this.mContext, this.photosList, this.columnWidth);
                this.adapter = gridViewAdapter;
                this.gridView.setAdapter(gridViewAdapter);
                this.adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void InitilizeGridLayout() {
        float applyDimension = TypedValue.applyDimension(1, 4.0f, getResources().getDisplayMetrics());
        this.columnWidth = (int) ((((float) this.utils.getScreenWidth()) - (((float) (this.pref.getNoOfGridColumns() + 1)) * applyDimension)) / ((float) this.pref.getNoOfGridColumns()));
        this.gridView.setNumColumns(this.pref.getNoOfGridColumns());
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
