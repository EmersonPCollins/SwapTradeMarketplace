package com.example.myfirstapp.service;

import android.annotation.SuppressLint;
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
import android.provider.Settings;

import androidx.annotation.Nullable;

import com.example.myfirstapp.Activity.SettingsActivity;

public class GPSLocation extends Service implements LocationListener {

    private final Context context;

    //GPS enabled flag
    boolean isGPSEnabled = false;

    //network status flag
    boolean isNetworkEnabled = false;

    //GPS status flag
    boolean canGetLocation = false;

    Location location;
    protected LocationManager locationManager;
    double latitude;
    double longitude;

    //min distance to change update in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // update every 10 meters

    //min time between updates in milliseconds
    private static final long MIN_TIME_BETWEEN_UPDATES = 1000*60; // 1 min


    public GPSLocation(Context context){
        this.context = context;
        setUpGPS();
        getLocation();
    }

    public void setUpGPS(){
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            //see if GPS is enabled
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            //get network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled && isNetworkEnabled) {
                this.canGetLocation = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    public Location getLocation(){
        if(canGetLocation){
            //get location from network provider if the network is enabled
            if(isNetworkEnabled){
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BETWEEN_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if(locationManager != null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }

            //get GPS lat/long with GPS Services if GPS is enabled
            if(isGPSEnabled){//can add error messages at any step
                if(location == null){
                    locationManager.requestLocationUpdates(
                            locationManager.GPS_PROVIDER,
                            MIN_TIME_BETWEEN_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if(locationManager!=null){
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if(location!= null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        }
        return location;
    }




    //stop using gps listener - call to stop using gps -- keep getting errors when used
    public void stopGPS(){
        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(myIntent);
    }

    public double getLatitude(){
        if(location!=null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location!=null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public void setCanGetLocation(boolean canGetLocation){
        if(canGetLocation){
            if (isGPSEnabled && isNetworkEnabled) {
                this.canGetLocation = true;//because canGetLocation is true and it has the GPS enabled and Network enabled
                getLocation();
            }else{
                this.canGetLocation=false;//because the GPS and Network is not enabled
            }
        }else{
            this.canGetLocation=false;//false because canGetLocation is false
        }
    }

    //make an alert function in case we want to initiate the location prompt on different pages
    public void showAlertMessages(){
        AlertDialog.Builder alertDialong = new AlertDialog.Builder(context);

        alertDialong.setTitle("GPS Settings");
        alertDialong.setMessage("GPS is not enabled. Do you want to go to the settings menu?");

        alertDialong.setPositiveButton("Settings", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
            }
        });

        alertDialong.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alertDialong.show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
