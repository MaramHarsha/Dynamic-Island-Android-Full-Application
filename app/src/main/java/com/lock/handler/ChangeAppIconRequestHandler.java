package com.lock.handler;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import com.lock.entity.AppDetail;
import com.lock.utils.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;
import java.util.HashMap;

public class ChangeAppIconRequestHandler extends RequestHandler {
    private static final String SCHEME_APP_ICON = "app-icon";
    private final HashMap<String, AppDetail> appDetailHashMap;
    private final Activity context;
    private final PackageManager mPackageManager;

    public ChangeAppIconRequestHandler(Activity activity, HashMap<String, AppDetail> hashMap) {
        this.context = activity;
        this.mPackageManager = activity.getPackageManager();
        this.appDetailHashMap = hashMap;
    }

    public static Uri getUri(String str) {
        return Uri.fromParts(SCHEME_APP_ICON, str, (String) null);
    }

    public boolean canHandleRequest(Request request) {
        return SCHEME_APP_ICON.equals(request.uri.getScheme());
    }

    public Result load(Request request, int i) {
        ActivityInfo activityInfo = null;
        AppDetail appDetail = this.appDetailHashMap.get(request.uri.getSchemeSpecificPart());
        Bitmap drawableToBmp = (appDetail == null || activityInfo == null) ? null : Constants.drawableToBmp(this.context, activityInfo.loadIcon(this.mPackageManager), 35);
        if (drawableToBmp != null) {
            return new Result(drawableToBmp, Picasso.LoadedFrom.DISK);
        }
        return null;
    }
}
