package com.example.myfirstapp.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;
import com.example.myfirstapp.domain.Good;
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

        readAllGoods();
    }

    public void readAllGoods() {
        final ArrayList<Good> goods = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("goods");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot messageSnapshot: snapshot.getChildren()) {
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
                displayGoods(goods);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void displayGoods(ArrayList<Good> goods) {
        ScrollView sv = (ScrollView) findViewById(R.id.scrollView);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        for (Good good: goods) {
            TextView goodName = new TextView(this);
            goodName.setText(good.getTitle());
            ll.addView(goodName);

            ImageView goodImage = getImage(good.getImage_url());
            ll.addView(goodImage);

            Button requestButton = new Button(this);
            requestButton.setText("Request");
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(500, 100);
            ll.addView(requestButton,layoutParams2);

            TextView goodLocation = new TextView(this);
            goodLocation.setText(good.getExchange_location());
            ll.addView(goodLocation);

            TextView goodType = new TextView(this);
            goodType.setText(good.getType());
            ll.addView(goodType);

            TextView goodDescription = new TextView(this);
            goodDescription.setText(good.getDescription());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500, 100);
            layoutParams.setMargins(0, 0, 0, 40);
            ll.addView(goodDescription,layoutParams);

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