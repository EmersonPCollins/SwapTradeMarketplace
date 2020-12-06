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

public class NotificationsActivity extends AppCompatActivity{

    private RecyclerView notificationsRecycler;
    //NotificationsAdapter notificationsAdapter; // it looks like NotificationsAdapter and MyViewHolder are the same?
    //FirebaseRecyclerOptions<RequestNotification> options;
    NotificationsAdapter adapter;
    boolean notification;
    static String storedEmail;

    DatabaseReference requestsRef;
    DatabaseReference goodsRef;
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


      requestsRef.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              for (DataSnapshot requestSnapshot: snapshot.getChildren()) {
                    Log.i(TAG, requestSnapshot.getKey() + " : " + requestSnapshot.child("notifiedEmail").getValue());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //       goodsRef = FirebaseDatabase.getInstance().getReference().child("goods");


/*
            requestsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot requestSnapshot: snapshot.getChildren()) {
                        //if(storedEmail.equals(requestSnapshot.child("notifiedEmail").getValue())|| storedEmail.equals(requestSnapshot.child("requestingEmail").getValue())) {

                        Log.i(TAG, "Is the stored email ("+ storedEmail + ") equal to notifiedEmail (" + requestSnapshot.child("notifiedEmail").getValue() + ")?: ");
                        if(storedEmail.equals(requestSnapshot.child("notifiedEmail").getValue())
                                || storedEmail.equals(requestSnapshot.child("requestingEmail").getValue()))
                        {
                            Log.i(TAG, "TRUE");
                        }
                        else {
                            Log.i(TAG, "FALSE");

                        }

                        Log.i(TAG, requestSnapshot.getKey() + " : " + requestSnapshot.child("notifiedEmail").getValue());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            /*final Query notifiedEmailQuery = requestsRef.orderByChild("notifiedEmail").equalTo(storedEmail);
            //Query requestingEmail = requestsRef.orderByChild("requestEmail").equalTo(storedEmail).limitToFirst(1);

            String notifiedEmail, requestingEmail;
            notifiedEmailQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.i(TAG, "" + dataSnapshot.hasChildren());
                    for (DataSnapshot zoneSnapshot: dataSnapshot.getChildren()) {
                        String str = zoneSnapshot.child("requestingEmail").getValue(String.class);
                        Log.i(TAG, str);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
                }
            });

*/


            /*options = new FirebaseRecyclerOptions.Builder<RequestNotification>().setQuery(requestsRef, RequestNotification.class).build();

            adapter = new FirebaseRecyclerAdapter<RequestNotification, NotificationsAdapter>(options) {
               @Override
                protected void onBindViewHolder(@NonNull final NotificationsAdapter notificationsAdapter, int i, @NonNull RequestNotification requestNotification) {



                    //notificationsAdapter.goodName.setText(requestNotification.getGoodID());
                    //notificationsAdapter.goodDescription.setText("Person who requested: " + requestNotification.getRequestingEmail());
                    //if(storedEmail.equals(requestsRef.))
                   goodsRef.child(requestNotification.getGoodID()).child("title").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            notificationsAdapter.goodName.setText("" + snapshot.getValue());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });

                    goodsRef.child(requestNotification.getGoodID()).child("description").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            notificationsAdapter.goodDescription.setText("" + snapshot.getValue());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });


                }

                @NonNull
                @Override
                public NotificationsAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accept_decline_row_item, parent, false);
                    return new NotificationsAdapter(view);
               }
            };
            */

    }

    public static String storedEmail() {
        return storedEmail;
    }

    public static String notifiedEmail(String notifiedEmail) {
        return notifiedEmail;
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
