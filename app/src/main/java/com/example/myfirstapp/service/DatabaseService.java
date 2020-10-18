package com.example.myfirstapp.service;

import androidx.annotation.NonNull;

import com.example.myfirstapp.domain.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseService {

    private FirebaseDatabase database;
    private String userLocation = "users";

    /**
     * Constructors
     */
    public DatabaseService(FirebaseDatabase database) {
        this.database = database;
    }

    public DatabaseService() {
        this.database = FirebaseDatabase.getInstance();
    }

    /**
     * Used to write a user to the firebase database
     *
     * @param user - User to be submitted
     */
    public void writeUser(User user) {
        DatabaseReference ref = database.getReference(userLocation);

        ref.setValue(user);
    }


    public User readUser(String email) {

        final User[] userData = new User[1];
        DatabaseReference ref = database.getReference(userLocation);

        ref.child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user != null) {
                    userData[0] = user;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return userData[0];

    }

}
