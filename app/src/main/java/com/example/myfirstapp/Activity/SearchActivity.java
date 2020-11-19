package com.example.myfirstapp.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;
import com.example.myfirstapp.domain.Good;
import com.example.myfirstapp.service.DatabaseService;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private DatabaseService databaseService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        databaseService = new DatabaseService(FirebaseDatabase.getInstance());
        ArrayList<Good> goods = databaseService.readAllGoods();
        displayGoods(goods);
    }

    private void displayGoods(ArrayList<Good> goods) {

    }
}