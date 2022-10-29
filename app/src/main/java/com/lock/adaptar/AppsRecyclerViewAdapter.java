package com.lock.adaptar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.lock.entity.AppDetail;
import com.lock.handler.ChangeAppIconRequestHandler;
import com.dynamic.island.harsha.notification.R;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.List;

public class AppsRecyclerViewAdapter extends RecyclerView.Adapter<AppsRecyclerViewAdapter.ViewHolder> {
    
    public final List<AppDetail> apps;
    private Picasso.Builder builder;
    
    public ItemClickListener mClickListener;
    private final LayoutInflater mInflater;
    private Picasso mPicasso;

    public interface ItemClickListener {
        void onItemClick(View view, int i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public AppsRecyclerViewAdapter(Activity activity, List<AppDetail> list, HashMap<String, AppDetail> hashMap) {
        this.mInflater = LayoutInflater.from(activity);
        this.apps = list;
        if (this.builder == null) {
            Picasso.Builder builder2 = new Picasso.Builder(activity);
            this.builder = builder2;
            builder2.addRequestHandler(new ChangeAppIconRequestHandler(activity, hashMap));
        }
        if (this.mPicasso == null) {
            this.mPicasso = this.builder.build();
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this.mInflater.inflate(R.layout.apps_list_item_entry, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.cb_apps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ((AppDetail) AppsRecyclerViewAdapter.this.apps.get(viewHolder.getAbsoluteAdapterPosition())).isSelected = z;
            }
        });
        viewHolder.cb_apps.setChecked(this.apps.get(i).isSelected);
        this.mPicasso.load(ChangeAppIconRequestHandler.getUri(this.apps.get(i).label + this.apps.get(i).activityInfoName + this.apps.get(i).pkg)).into(viewHolder.iv_icon);
        viewHolder.tv_app_name.setText(this.apps.get(i).label);
    }

    public int getItemCount() {
        return this.apps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox cb_apps;
        ImageView iv_icon;
        TextView tv_app_name;

        ViewHolder(View view) {
            super(view);
            this.tv_app_name = (TextView) view.findViewById(R.id.tv_app_name);
            this.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            this.cb_apps = (CheckBox) view.findViewById(R.id.cb_apps);
            this.tv_app_name.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (AppsRecyclerViewAdapter.this.mClickListener != null) {
                AppsRecyclerViewAdapter.this.mClickListener.onItemClick(view, getAbsoluteAdapterPosition());
            }
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
