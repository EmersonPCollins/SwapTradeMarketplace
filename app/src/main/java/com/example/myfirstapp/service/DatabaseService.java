package com.example.myfirstapp.service;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.myfirstapp.Activity.HomeActivity;
import com.example.myfirstapp.MainActivity;
import com.example.myfirstapp.domain.Good;
import com.example.myfirstapp.domain.RequestNotification;
import com.example.myfirstapp.domain.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseService {

    private FirebaseDatabase database;
    private static String userLocation = "users";
    private String goodLocation = "goods";
    private String requestNotificationLocation = "requests";

    public DatabaseService(FirebaseDatabase database) {
        this.database = database;
    }

    public DatabaseService() {
        this.database = FirebaseDatabase.getInstance();
    }

    public void writeUser(User user) {
        DatabaseReference ref = database.getReference(userLocation);

        ref.child(user.getId()).setValue(user);
    }

    public boolean readUser(final String email, final String password) {

        final boolean[] userExists = {false};
        DatabaseReference ref = database.getReference(userLocation);
        final String id = email.replace(".", "");

        ref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String passwordGiven = dataSnapshot.child("password").getValue(String.class);

                if (password.equals(passwordGiven)) {
                    userExists[0] = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }});

        return userExists[0];
    }

    public void writeGood(Good good) {
        DatabaseReference ref = database.getReference(goodLocation);

        ref.child(good.getId()).setValue(good);
    }

    public void removeRequestNotification(String id){
        DatabaseReference ref = database.getReference(requestNotificationLocation + "/" + id);

        ref.removeValue();
    }

    public void writeRequestNotification(RequestNotification requestNotification) {
        DatabaseReference ref = database.getReference(requestNotificationLocation);

        ref.child(requestNotification.getId()).setValue(requestNotification);
    }

    public ArrayList<Good> readGoods(final String title, final String location, final String type) {
        final ArrayList<Good> goods = new ArrayList<>();
        DatabaseReference ref = database.getReference(goodLocation);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot adSnapshot : snapshot.getChildren()) {
                    Good good = adSnapshot.getValue(Good.class);

                    if (good == null) {
                        continue;
                    }

                    if (!title.equals("") && !title.equals(good.getTitle())) {
                        continue;
                    }

                    if (!location.equals("") && !location.equals(good.getExchange_location())) {
                        continue;
                    }

                    if (!type.equals("") && !type.equals(good.getType())) {
                        continue;
                    }

                    goods.add(good);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return goods;
    }

}
