package com.example.myfirstapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.domain.User;
import com.google.firebase.database.DataSnapshot;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public boolean credentialsCheck(String firstname, String lastname, String email, String password, @NonNull DataSnapshot dataSnapshot){
        Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
        while (it.hasNext()) {
            User kv = it.next().getValue(User.class);
            if(firstname.equals(kv.getFirstName())&&lastname.equals(kv.getLastName())&&email.equals(kv.getEmail())&&password.equals(kv.getPassword())){
                return true;
            }
        }
        return false;
    }

}

