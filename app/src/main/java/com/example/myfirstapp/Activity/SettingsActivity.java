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
    GPSLocation gps = new GPSLocation(SettingsActivity.this);;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String manifestPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    Button locationPermission, findLocation;
    TextView locationmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);

        //check for permission
        locationmsg = (TextView) findViewById(R.id.location);
        locationPermission = (Button) findViewById(R.id.allowLocationPermission);
        if (ActivityCompat.checkSelfPermission(SettingsActivity.this, manifestPermission)
                == PackageManager.PERMISSION_GRANTED) {//if it has permission gray out button
            locationPermission.setEnabled(false);
            locationmsg.setText("Location Permission Granted. Can be changed in permission settings of device.");
            gps.getLocation();
        }else{
            locationPermission.setEnabled(true);
        }

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), HomeActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        locationPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ActivityCompat.checkSelfPermission(SettingsActivity.this, manifestPermission)
                            != PackageManager.PERMISSION_GRANTED) {//if it does not have permission
                        ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{manifestPermission},
                                REQUEST_CODE_PERMISSION);//ask for it
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (ActivityCompat.checkSelfPermission(SettingsActivity.this, manifestPermission)
                        == PackageManager.PERMISSION_GRANTED) {//if permission was granted
                    locationmsg.setText("Location Permission Granted. Can be changed in permission settings of device.");
                    locationPermission.setEnabled(false);
                    gps.getLocation();
                } else {
                    locationmsg.setText("Location Permission Denied.");
                }
            }
        });

        findLocation = (Button) findViewById(R.id.getLocationButton);
        findLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //ask for location permission if it isn't already given
                try {
                    if (ActivityCompat.checkSelfPermission(SettingsActivity.this, manifestPermission)
                            != PackageManager.PERMISSION_GRANTED) {//if it does not have permission

                        ActivityCompat.requestPermissions(SettingsActivity.this, new String[]{manifestPermission},
                                REQUEST_CODE_PERMISSION);
                    }
                } catch (Exception e) {
                            e.printStackTrace();
                }
                if (ActivityCompat.checkSelfPermission(SettingsActivity.this, manifestPermission)
                        == PackageManager.PERMISSION_GRANTED) {//if it was granted already or in the previous statement
                    locationmsg.setText("Location Permission Granted. Can be changed in permission settings of device.");
                    locationPermission.setEnabled(false);
                    gps.getLocation();
                    // check if GPS enabled
                    if (gps.canGetLocation()) {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        locationmsg.setText("Latitude: " + latitude + "\nLongitude: " + longitude);

                    } else {
                        locationmsg.setText("Could not connect to GPS or Network.");
                    }
                } else {
                    locationmsg.setText("Enable location by clicking the Allow Location button to use this feature.");
                    locationPermission.setEnabled(true);//just in case
                }
            }
        });
    }
}

