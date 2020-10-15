package com.example.myfirstapp.service;

import com.example.myfirstapp.domain.Good;
import com.example.myfirstapp.domain.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseService {

    private FirebaseDatabase database;
    private String userLocation = "users";
    private String goodLocation = "goods";

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

    /**
     * Used to write a good to the firebase database
     *
     * @param good - The good a user is adding
     */
    public void writeGood(Good good) {
        DatabaseReference ref = database.getReference(goodLocation);

        ref.setValue(good);
    }

}
