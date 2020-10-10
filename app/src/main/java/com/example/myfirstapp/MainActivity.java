package com.example.myfirstapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
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
