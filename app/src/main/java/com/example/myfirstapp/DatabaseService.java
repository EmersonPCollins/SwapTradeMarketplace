package com.example.myfirstapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseService {
    private DatabaseReference db;

    DatabaseService(){
        db = FirebaseDatabase.getInstance().getReference();
    }

    public void writeGood(Good good){

    }
}
