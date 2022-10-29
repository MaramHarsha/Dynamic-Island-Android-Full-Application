package com.lock.adaptar;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.Notification;
import com.lock.services.MAccessibilityService;
import com.lock.services.NotificationListener;
import com.lock.utils.Constants;
import com.dynamic.island.harsha.notification.R;
import java.util.ArrayList;

public class CustomNotificationIconAdapter extends RecyclerView.Adapter<CustomNotificationIconAdapter.ViewHolder> {
    private final Context mContext;
    
    public final NotificationListener notificationListener;
    
    public final ArrayList<Notification> notifications;
    ViewGroup viewGroup;

    public CustomNotificationIconAdapter(Context context, ArrayList<Notification> arrayList, NotificationListener notificationListener2) {
        this.mContext = context;
        this.notifications = arrayList;
        this.notificationListener = notificationListener2;
    }

    public int getItemCount() {
        ArrayList<Notification> arrayList = this.notifications;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup2, int i) {
        LayoutInflater from = LayoutInflater.from(this.mContext);
        this.viewGroup = viewGroup2;
        return new ViewHolder(from.inflate(R.layout.notification_icon_item, viewGroup2, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        boolean z;
        Notification notification = this.notifications.get(viewHolder.getAbsoluteAdapterPosition());
        viewHolder.mChronometer.setVisibility(8);
        if (!notification.isChronometerRunning) {
            viewHolder.mChronometer.setBase(SystemClock.elapsedRealtime());
            viewHolder.mChronometer.stop();
        }
        viewHolder.itemView.setOnLongClickListener((View.OnLongClickListener) null);
        viewHolder.itemView.setLongClickable(false);
        viewHolder.mLottieAnimationView.setVisibility(8);
        viewHolder.mLottieAnimationView.pauseAnimation();
        viewHolder.island_small_image_left.clearColorFilter();
        viewHolder.island_small_image_right.clearColorFilter();
        viewHolder.island_small_image_left.setImageResource(0);
        viewHolder.island_small_image_right.setImageResource(0);
        viewHolder.island_small_image_left.setVisibility(0);
        viewHolder.island_small_image_right.setVisibility(0);
        viewHolder.island_small_text_left.setVisibility(0);
        viewHolder.island_small_text_right.setVisibility(0);
        viewHolder.island_small_text_right.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        viewHolder.island_small_text_left.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        viewHolder.island_small_text_left.setTextColor(((MAccessibilityService) this.mContext).utils.getTileColor());
        viewHolder.island_small_text_right.setTextColor(((MAccessibilityService) this.mContext).utils.getTileColor());
        if (notification.type.equalsIgnoreCase(Constants.TYPE_AIRBUDS)) {
            viewHolder.island_small_image_left.setImageResource(R.drawable.earbuds);
            viewHolder.island_small_text_right.setTextColor(notification.color);
            int airpodsBattery = ((MAccessibilityService) this.mContext).utils.getAirpodsBattery();
            if (airpodsBattery != -1) {
                viewHolder.island_small_text_right.setCompoundDrawablesWithIntrinsicBounds(0, 0, airpodsBattery, 0);
                viewHolder.island_small_text_right.setCompoundDrawablePadding((int) Constants.convertDpToPixel(5.0f, this.mContext));
                viewHolder.island_small_text_right.setText(((MAccessibilityService) this.mContext).utils.getAirPodLevel() + this.mContext.getString(R.string.percent));
            } else {
                viewHolder.island_small_text_right.setText(R.string.airpods);
            }
            viewHolder.island_small_text_left.setVisibility(8);
            viewHolder.island_small_image_right.setVisibility(8);
            z = false;
        } else {
            z = true;
        }
        if (notification.type.equalsIgnoreCase(Constants.TYPE_CHARGING)) {
            viewHolder.island_small_image_left.setVisibility(8);
            viewHolder.island_small_text_right.setTextColor(notification.color);
            viewHolder.island_small_text_right.setText(notification.tv_text);
            viewHolder.island_small_text_right.setCompoundDrawablesWithIntrinsicBounds(0, 0, ((MAccessibilityService) this.mContext).utils.getBatteryImage(), 0);
            viewHolder.island_small_text_right.setCompoundDrawablePadding((int) Constants.convertDpToPixel(5.0f, this.mContext));
            viewHolder.island_small_text_left.setTextColor(notification.color);
            viewHolder.island_small_text_left.setText(notification.tv_title);
            viewHolder.island_small_image_right.setVisibility(8);
            z = false;
        }
        if (notification.type.equalsIgnoreCase(Constants.TYPE_SILENT)) {
            viewHolder.island_small_image_left.clearColorFilter();
            viewHolder.island_small_image_left.setImageResource(notification.local_left_icon);
            viewHolder.island_small_text_left.setText("");
            viewHolder.island_small_text_right.setText(notification.tv_title);
            viewHolder.island_small_text_right.setTextColor(notification.color);
            viewHolder.island_small_image_right.setImageResource(0);
            viewHolder.island_small_image_right.setVisibility(8);
            z = false;
        }
        if (z) {
            if (notification.icon != null) {
                viewHolder.island_small_image_left.setImageBitmap(notification.icon);
                viewHolder.island_small_image_left.setColorFilter(-1);
                if (notification.showChronometer) {
                    viewHolder.mChronometer.setVisibility(0);
                    viewHolder.mChronometer.start();
                    notification.isChronometerRunning = true;
                    viewHolder.island_small_text_left.setText("");
                    viewHolder.island_small_image_left.setColorFilter(this.mContext.getColor(R.color.green_500));
                } else {
                    viewHolder.island_small_text_left.setText(((MAccessibilityService) this.mContext).utils.getFormatedDate(notification.postTime));
                }
            } else {
                viewHolder.island_small_image_left.setImageBitmap((Bitmap) null);
            }
            if (notification.senderIcon == null) {
                viewHolder.island_small_image_right.setImageResource(0);
                viewHolder.island_small_image_right.setVisibility(8);
                if (notification.tv_title == null || notification.tv_title.length() <= 0) {
                    viewHolder.island_small_text_right.setVisibility(8);
                } else {
                    viewHolder.island_small_text_right.setVisibility(0);
                    String[] split = notification.tv_title.toString().split(" ");
                    if (split.length > 0) {
                        viewHolder.island_small_text_right.setText(split[0]);
                    } else {
                        viewHolder.island_small_text_right.setText("");
                    }
                }
            } else if (notification.showChronometer && notification.category.equalsIgnoreCase(NotificationCompat.CATEGORY_CALL) && notification.isOngoing) {
                viewHolder.island_small_text_right.setVisibility(8);
                viewHolder.island_small_image_right.setVisibility(8);
                viewHolder.island_small_text_right.setText("");
                viewHolder.mLottieAnimationView.setAnimation((int) R.raw.wave_call01);
                viewHolder.mLottieAnimationView.setVisibility(0);
                if (!viewHolder.mLottieAnimationView.isAnimating()) {
                    viewHolder.mLottieAnimationView.playAnimation();
                }
            } else if (!notification.template.equals("MediaStyle") || notification.isClearable) {
                viewHolder.island_small_text_right.setVisibility(8);
                viewHolder.island_small_image_right.setImageBitmap(notification.senderIcon);
            } else {
                viewHolder.mLottieAnimationView.setAnimation((int) R.raw.music_wave);
                viewHolder.mLottieAnimationView.setVisibility(0);
                if (!viewHolder.mLottieAnimationView.isAnimating()) {
                    viewHolder.mLottieAnimationView.playAnimation();
                }
                viewHolder.island_small_text_right.setVisibility(8);
                viewHolder.island_small_image_right.setVisibility(8);
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int absoluteAdapterPosition = viewHolder.getAbsoluteAdapterPosition();
                    if (absoluteAdapterPosition >= 0 && absoluteAdapterPosition < CustomNotificationIconAdapter.this.notifications.size()) {
                        CustomNotificationIconAdapter.this.notificationListener.onItemClicked((Notification) CustomNotificationIconAdapter.this.notifications.get(absoluteAdapterPosition));
                        CustomNotificationIconAdapter.this.notificationListener.onItemClicked((Notification) CustomNotificationIconAdapter.this.notifications.get(absoluteAdapterPosition), absoluteAdapterPosition);
                    }
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        
        public final ImageView island_small_image_left;
        
        public final ImageView island_small_image_right;
        
        public final TextView island_small_text_left;
        
        public final TextView island_small_text_right;
        
        public final Chronometer mChronometer;
        
        public final LottieAnimationView mLottieAnimationView;
        private final View mRootLayout;

        public ViewHolder(View view) {
            super(view);
            this.mRootLayout = view;
            this.island_small_image_left = (ImageView) view.findViewById(R.id.island_small_left_iv);
            this.island_small_text_left = (TextView) view.findViewById(R.id.island_small_text_left);
            this.mLottieAnimationView = (LottieAnimationView) view.findViewById(R.id.right_lottie);
            this.island_small_image_right = (ImageView) view.findViewById(R.id.island_small_image_right);
            this.island_small_text_right = (TextView) view.findViewById(R.id.island_small_text_right);
            this.mChronometer = (Chronometer) view.findViewById(R.id.chronometer);
        }
    }

    public void onViewDetachedFromWindow(ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
    }
}
