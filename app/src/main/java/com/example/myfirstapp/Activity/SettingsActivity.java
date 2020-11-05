package com.example.myfirstapp.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myfirstapp.Activity.HomeActivity;
import com.example.myfirstapp.R;
import com.example.myfirstapp.service.GPSLocation;

import org.w3c.dom.Text;

public class SettingsActivity extends AppCompatActivity {

    //To keep gps location
    GPSLocation gps;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String manifestPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), HomeActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        Switch locationPermission = (Switch) findViewById(R.id.locationAllowSwitch);
        final TextView locationmsg = (TextView) findViewById(R.id.location);
        locationPermission.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //the toggle is enabled - enable location permission prompt? figure out how to just allow(?_
                    try{
                        if (ActivityCompat.checkSelfPermission(SettingsActivity.this, manifestPermission)
                                != PackageManager.PERMISSION_GRANTED) {//if it does not have permission

                            ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{manifestPermission},
                                    REQUEST_CODE_PERMISSION);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    if (ActivityCompat.checkSelfPermission(SettingsActivity.this, manifestPermission)
                            == PackageManager.PERMISSION_GRANTED) {//if permission was granted
                        locationmsg.setText("Location Permission Granted");
                    }else{
                        locationmsg.setText("Location Permission Denied");
                    }
                }else{
                    gps.setCanGetLocation(false);
                    locationmsg.setText("Location Permission \nDenied");
                }
            }
        });

        Button findLocation = (Button) findViewById(R.id.getLocationButton);
        findLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                //gps = new GPSLocation(SettingsActivity.this);
                //ask for location permission if it isn't already given
                try{
                    if (ActivityCompat.checkSelfPermission(SettingsActivity.this, manifestPermission)
                            != PackageManager.PERMISSION_GRANTED) {//if it does not have permission

                        ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{manifestPermission},
                                REQUEST_CODE_PERMISSION);
                    }
                    if(ActivityCompat.checkSelfPermission(SettingsActivity.this, manifestPermission)
                            == PackageManager.PERMISSION_GRANTED){//if it was granted already or in the previous statement
                        gps.setUpGPS();//will check to make sure network is good
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                // check if GPS enabled
                if(gps.canGetLocation()){
                    gps.getLocation();
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    locationmsg.setText("Latitude: "+latitude+"\nLongitude: "+longitude);
                }else{

                }

            }
        });
    }
}

