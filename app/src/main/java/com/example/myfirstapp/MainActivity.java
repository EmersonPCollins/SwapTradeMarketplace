package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.Activity.HomeActivity;
import com.example.myfirstapp.domain.User;
import com.google.firebase.database.DataSnapshot;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), HomeActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });
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

