package com.lock.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.view.ContextThemeWrapper;
import com.dynamic.island.harsha.notification.R;

public class RatingDialog {
    
    public Activity context;

    public RatingDialog(Activity activity) {
        this.context = activity;
    }

    public void showDialog() {
        View inflate = this.context.getLayoutInflater().inflate(R.layout.rating_dialog, (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper((Context) this.context, (int) R.style.AlertDialogCustom));
        final RatingBar ratingBar = (RatingBar) inflate.findViewById(R.id.dialog_rating_rating_bar);
        builder.setCancelable(false);
        builder.setView(inflate);
        builder.setPositiveButton("Rate", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                Constants.setRatingDailoge(RatingDialog.this.context, false);
            }
        });
        final AlertDialog create = builder.create();
        create.show();
        create.getButton(-1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ratingBar.getRating() == 5.0f) {
                    try {
                        RatingDialog.this.context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + RatingDialog.this.context.getPackageName())));
                    } catch (ActivityNotFoundException unused) {
                        RatingDialog.this.context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + RatingDialog.this.context.getPackageName())));
                    }
                }
                if (ratingBar.getRating() > 0.0f) {
                    create.dismiss();
                    Toast.makeText(RatingDialog.this.context, "Thank you for feedback.!", 1).show();
                } else {
                    Toast.makeText(RatingDialog.this.context, "Please rate 5 star.!", 1).show();
                }
                Constants.setRatingDailoge(RatingDialog.this.context, false);
            }
        });
    }
}
