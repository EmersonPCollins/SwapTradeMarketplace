package com.example.myfirstapp.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

    Button requestButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        readAllGoods();

        //if request button is pressed confirm
        AlertDialog.Builder confirmRequest;
        requestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
    }

    public void readAllGoods() {
        final ArrayList<Good> goods = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("goods");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot adSnapshot : snapshot.getChildren()) {
                    Good good = snapshot.getValue(Good.class);
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

            requestButton = new Button(this);
            requestButton.setText("Request");
            ll.addView(requestButton);

            TextView goodLocation = new TextView(this);
            goodLocation.setText(good.getExchange_location());
            ll.addView(goodLocation);

            TextView goodType = new TextView(this);
            goodType.setText(good.getType());
            ll.addView(goodType);

            TextView goodDescription = new TextView(this);
            goodDescription.setText(good.getDescription());
            ll.addView(goodDescription);
        }
    }

    private ImageView getImage(String url) {
        final ImageView goodImage = new ImageView(this);
        if (url == null) return goodImage;
        StorageReference goodImageReference = FirebaseStorage.getInstance().getReference(url);
        final long ONE_MEGABYTE = 1024 * 1024;
        goodImageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);

                goodImage.setMinimumHeight(dm.heightPixels);
                goodImage.setMinimumWidth(dm.widthPixels);
                goodImage.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

        return goodImage;
    }
}