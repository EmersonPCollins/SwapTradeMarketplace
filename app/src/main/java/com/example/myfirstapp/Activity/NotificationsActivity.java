package com.example.myfirstapp.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myfirstapp.R;
import com.example.myfirstapp.domain.Good;
import com.example.myfirstapp.domain.RequestNotification;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity{

    private RecyclerView notificationsRecycler;
    NotificationsAdapter adapter;
    static String storedEmail;

    DatabaseReference requestsRef;


    private static final String TAG = "NotificationsActive";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitity_notifications);

        requestsRef = FirebaseDatabase.getInstance().getReference().child("requests");
        notificationsRecycler = findViewById(R.id.notificationsRecycler);
        notificationsRecycler.setLayoutManager(new LinearLayoutManager(this));

        // add the divider to the UI
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        notificationsRecycler.addItemDecoration(dividerItemDecoration);
        FirebaseRecyclerOptions<RequestNotification> options
                = new FirebaseRecyclerOptions.Builder<RequestNotification>()
                .setQuery(requestsRef, RequestNotification.class)
                .build();

        adapter = new NotificationsAdapter(options);
        notificationsRecycler.setAdapter(adapter);



        SharedPreferences preference = getSharedPreferences("login", MODE_PRIVATE);
        storedEmail = preference.getString("email", "");
    }

    public static String storedEmail() {
        return storedEmail;
    }


    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }


}
