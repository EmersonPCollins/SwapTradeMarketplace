package com.example.myfirstapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myfirstapp.R;
import com.example.myfirstapp.domain.RequestNotification;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity{

    private RecyclerView notificationsRecycler;
    //NotificationsAdapter notificationsAdapter; // it looks like NotificationsAdapter and MyViewHolder are the same?
    //FirebaseRecyclerOptions<RequestNotification> options;
    NotificationsAdapter adapter;
    static String storedEmail;

    DatabaseReference requestsRef;
    //DatabaseReference ref;


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
