package com.example.myfirstapp;

import com.google.firebase.database.FirebaseDatabase;

public class DatabaseService {
    private FirebaseDatabase databaseReference;

    DatabaseService(FirebaseDatabase firebase){
            databaseReference = firebase;
    }

    public void writeGood(Good good){

    }
}
