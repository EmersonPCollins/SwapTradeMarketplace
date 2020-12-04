package com.example.myfirstapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myfirstapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity{

    RecyclerView notificationsRecycler;
    NotificationsAdapter notificationsAdapter;

    List<String> notificationsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitity_notifications);

        notificationsList = new ArrayList<>();

        notificationsRecycler = findViewById(R.id.notificationsRecycler);
        notificationsAdapter = new NotificationsAdapter(notificationsList);

        // deciding that we want the views to appear vertically
        notificationsRecycler.setLayoutManager(new LinearLayoutManager(this));
        notificationsRecycler.setAdapter(notificationsAdapter);

        // add the divider to the UI
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        notificationsRecycler.addItemDecoration(dividerItemDecoration);

        // remove these once I figure out how to populate from database
        notificationsList.add("Iron Man");
        notificationsList.add("The Incredible Hulk");
        notificationsList.add("Thor");
        notificationsList.add("Superwoman");
        notificationsList.add("Iron Man 3");

    }
}
