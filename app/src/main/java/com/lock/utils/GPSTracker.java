package com.lock.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.view.ContextThemeWrapper;
import com.dynamic.island.harsha.notification.R;

public class GPSTracker extends Service implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 60000;
    boolean canGetLocation = false;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    double latitude;
    Location location;
    protected LocationManager locationManager;
    double longitude;
    
    public final Activity mContext;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onLocationChanged(Location location2) {
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    public GPSTracker(Activity activity) {
        this.mContext = activity;
        getLocation();
    }

    public void getLocation() {
        try {
            LocationManager locationManager2 = (LocationManager) this.mContext.getSystemService("location");
            this.locationManager = locationManager2;
            this.isGPSEnabled = locationManager2.isProviderEnabled("gps");
            boolean isProviderEnabled = this.locationManager.isProviderEnabled("network");
            this.isNetworkEnabled = isProviderEnabled;
            if (this.isGPSEnabled || isProviderEnabled) {
                this.canGetLocation = true;
                if (isProviderEnabled) {
                    this.locationManager.requestLocationUpdates("network", MIN_TIME_BW_UPDATES, 10.0f, this);
                    Log.d("Network", "Network");
                    LocationManager locationManager3 = this.locationManager;
                    if (locationManager3 != null) {
                        Location lastKnownLocation = locationManager3.getLastKnownLocation("network");
                        this.location = lastKnownLocation;
                        if (lastKnownLocation != null) {
                            this.latitude = lastKnownLocation.getLatitude();
                            this.longitude = this.location.getLongitude();
                        }
                    }
                }
                if (this.isGPSEnabled && this.location == null) {
                    this.locationManager.requestLocationUpdates("gps", MIN_TIME_BW_UPDATES, 10.0f, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    LocationManager locationManager4 = this.locationManager;
                    if (locationManager4 != null) {
                        Location lastKnownLocation2 = locationManager4.getLastKnownLocation("gps");
                        this.location = lastKnownLocation2;
                        if (lastKnownLocation2 != null) {
                            this.latitude = lastKnownLocation2.getLatitude();
                            this.longitude = this.location.getLongitude();
                        }
                    }
                }
            }
            stopUsingGPS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLocationEnabled() {
        this.isGPSEnabled = this.locationManager.isProviderEnabled("gps");
        boolean isProviderEnabled = this.locationManager.isProviderEnabled("network");
        this.isNetworkEnabled = isProviderEnabled;
        if (this.isGPSEnabled || isProviderEnabled) {
            this.canGetLocation = true;
        } else {
            this.canGetLocation = false;
        }
        return this.canGetLocation;
    }

    public void stopUsingGPS() {
        LocationManager locationManager2 = this.locationManager;
        if (locationManager2 != null) {
            locationManager2.removeUpdates(this);
        }
    }

    public double getLatitude() {
        Location location2 = this.location;
        if (location2 != null) {
            this.latitude = location2.getLatitude();
        }
        return this.latitude;
    }

    public double getLongitude() {
        Location location2 = this.location;
        if (location2 != null) {
            this.longitude = location2.getLongitude();
        }
        return this.longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper((Context) this.mContext, (int) R.style.AlertDialogCustom));
        builder.setTitle("Location Disabled");
        builder.setMessage(R.string.enable_location).setIcon(17301543).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    GPSTracker.this.mContext.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                } catch (Exception unused) {
                    Toast.makeText(GPSTracker.this.mContext, "GPS not available", 1).show();
                }
                dialogInterface.cancel();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!GPSTracker.this.mContext.isFinishing()) {
                    dialogInterface.cancel();
                }
            }
        });
        try {
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
