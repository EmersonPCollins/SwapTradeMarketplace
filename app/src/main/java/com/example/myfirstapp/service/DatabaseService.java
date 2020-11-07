package com.example.myfirstapp.service;

import androidx.annotation.NonNull;
import com.example.myfirstapp.domain.Good;
import com.example.myfirstapp.domain.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseService {

    private FirebaseDatabase database;
    private String userLocation = "users";
    private String goodLocation = "goods";

    public DatabaseService(FirebaseDatabase database) {
        this.database = database;
    }

    public DatabaseService() {
        this.database = FirebaseDatabase.getInstance();
    }

    public void writeUser(User user) {
        DatabaseReference ref = database.getReference(userLocation);

        ref.setValue(user);
    }

    public User readUser(final String email) {

        final User[] userData = new User[1];
        DatabaseReference ref = database.getReference(userLocation);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                    User user = adSnapshot.getValue(User.class);

                    if (user != null && email.equals(user.getEmail())) {
                        userData[0] = user;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return userData[0];
    }

    public void writeGood(Good good) {
        DatabaseReference ref = database.getReference(goodLocation);

        ref.setValue(good);
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

    /**
     * Check that a user exists in db given login details
     */
    public boolean userExists(String email, String password) {
        User user = readUser(email);

        if (user == null) {
            return false;
        }

        if (!user.getEmail().equals(email)) {
            return false;
        }

        if (!user.getPassword().equals(password)) {
            return false;
        }

        return true;
    }
}
