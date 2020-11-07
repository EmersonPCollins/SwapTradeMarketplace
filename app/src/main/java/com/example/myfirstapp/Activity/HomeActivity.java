package com.example.myfirstapp.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signed_in);

        TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        SharedPreferences preference = getSharedPreferences("login", MODE_PRIVATE);
        String storedEmail = preference.getString("email", "");
        welcomeMessage.setText("Welcome " + storedEmail + "!");


        Button provideGoods = findViewById(R.id.provideButton);
        provideGoods.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), GoodsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button settings = findViewById(R.id.SettingsButton);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SettingsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        Button browse = findViewById(R.id.browseButton);
        browse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SearchActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }


}
