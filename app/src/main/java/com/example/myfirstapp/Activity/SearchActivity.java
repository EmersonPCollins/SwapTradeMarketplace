package com.example.myfirstapp.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Spinner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;
import com.example.myfirstapp.domain.Good;
import com.example.myfirstapp.domain.RequestNotification;
import com.example.myfirstapp.service.DatabaseService;
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

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        final LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER);
        sv.addView(ll);

        final ArrayList<Good> listOfGoods = new ArrayList<Good>();

        final Spinner categorySpinner = (Spinner) findViewById(R.id.categoriesSearchSpinner);
        final Spinner locationSpinner = (Spinner) findViewById(R.id.locationSearchSpinner);
        SearchView searchView = (SearchView) findViewById(R.id.searchField);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("goods");
                final String categorySpinnerValue = categorySpinner.getSelectedItem().toString();
                final String locationSpinnerValue = locationSpinner.getSelectedItem().toString();

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listOfGoods.clear();

                        for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                            Good good = adSnapshot.getValue(Good.class);
                            if (good == null || good.getTitle() == null || good.getType() == null || good.getExchange_location() == null) {
                                continue;
                            }

                            if (good.getTitle().contains(query) && good.getType().equals(categorySpinnerValue) && good.getExchange_location().contains(locationSpinnerValue)) {
                                listOfGoods.add(good);

                            }
                        }

                        displayGoods(listOfGoods, ll);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void readAllGoods() {
        final ArrayList<Good> goods = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("goods");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    String title = (String) messageSnapshot.child("title").getValue();
                    String date = (String) messageSnapshot.child("availability_end_date").getValue();
                    String description = (String) messageSnapshot.child("description").getValue();
                    String location = (String) messageSnapshot.child("exchange_location").getValue();
                    String user = (String) messageSnapshot.child("user_email").getValue();
                    String url = (String) messageSnapshot.child("image_url").getValue();
                    String type = (String) messageSnapshot.child("type").getValue();
                    Good good = new Good(title, null, date, description, location, user, url, type);
                    goods.add(good);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void displayGoods(ArrayList<Good> goods, LinearLayout ll) {

        ll.removeAllViews();

        for (final Good good : goods) {
            TextView goodName = new TextView(this);
            goodName.setText(good.getTitle());
            goodName.setTextColor(Color.BLUE);
            goodName.setTextSize(18);
            goodName.setGravity(Gravity.CENTER);
            ll.addView(goodName);

            ImageView goodImage = getImage(good.getImage_url());
            ll.addView(goodImage);

            Button requestButton = new Button(this);
            requestButton.setText("Request");
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(500, 100);
            ll.addView(requestButton, layoutParams2);

            requestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Make alert and functionality
                    //if request button is pressed create confirmation popup
                    AlertDialog.Builder confirmRequest = new AlertDialog.Builder(SearchActivity.this);
                    confirmRequest.setTitle("Confirmation Message").setMessage("Are you sure?");
                    confirmRequest.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPreferences preference = getSharedPreferences("login", MODE_PRIVATE);
                            String loggedinUser = preference.getString("email", "");

                            RequestNotification request = new RequestNotification(loggedinUser, good.getUser_email(), good.getId(), good.getTitle(), good.getExchange_location());
                            DatabaseService db = new DatabaseService();
                            db.writeRequestNotification(request);
                        }
                    });
                    confirmRequest.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    confirmRequest.show();
                }
            });

            TextView goodLocation = new TextView(this);
            goodLocation.setText(good.getExchange_location());
            goodLocation.setGravity(Gravity.CENTER);
            ll.addView(goodLocation);

            TextView goodType = new TextView(this);
            goodType.setText(good.getType());
            goodType.setGravity(Gravity.CENTER);
            ll.addView(goodType);

            TextView goodDescription = new TextView(this);
            goodDescription.setText(good.getDescription());
            goodDescription.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500, 100);
            layoutParams.setMargins(0, 0, 0, 40);
            ll.addView(goodDescription, layoutParams);

        }
    }

    private ImageView getImage(String url) {
        final ImageView goodImage = new ImageView(this);
        if (url == null) return goodImage;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference goodImageReference = storageReference.child("images/" + url);

        final long ONE_MEGABYTE = 1024 * 1024;
        goodImageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                goodImage.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });

        return goodImage;
    }
}



