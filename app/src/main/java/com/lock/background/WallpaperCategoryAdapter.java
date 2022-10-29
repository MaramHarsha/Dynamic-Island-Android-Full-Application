package com.lock.background;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dynamic.island.harsha.notification.R;
import java.util.ArrayList;
import java.util.List;

public class WallpaperCategoryAdapter extends BaseAdapter {
    private Context _activity;
    private int imageWidth;
    private LayoutInflater inflater;
    private List<WallpaperCategoryList> wallpapersList;

    public long getItemId(int i) {
        return (long) i;
    }

    public WallpaperCategoryAdapter(Context context, List<WallpaperCategoryList> list, int i) {
        new ArrayList();
        this._activity = context;
        this.wallpapersList = list;
        this.inflater = LayoutInflater.from(context);
        this.imageWidth = i;
    }

    public int getCount() {
        return this.wallpapersList.size();
    }

    public Object getItem(int i) {
        return this.wallpapersList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = this.inflater.inflate(R.layout.grid_item_photo, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            viewHolder.image = (ImageView) view.findViewById(R.id.thumbnail);
            viewHolder.albumName_tv = (TextView) view.findViewById(R.id.albumName_tv);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.albumName_tv.setVisibility(0);
        viewHolder.albumName_tv.setText(this.wallpapersList.get(i).getAlbum());
        viewHolder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageView imageView = viewHolder.image;
        int i2 = this.imageWidth;
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(i2, i2));
        String trim = this.wallpapersList.get(i).getIcon().trim();
        Context context = this._activity;
        if (context != null) {
            Glide.with(context).load(AppConst.IMAGE_URL + trim).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    viewHolder.progressBar.setVisibility(8);
                    return false;
                }

                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    viewHolder.progressBar.setVisibility(8);
                    return false;
                }
            }).into(viewHolder.image);
        }
        return view;
    }

    static class ViewHolder {
        TextView albumName_tv;
        ImageView image;
        ProgressBar progressBar;

        ViewHolder() {
        }
    }
}
