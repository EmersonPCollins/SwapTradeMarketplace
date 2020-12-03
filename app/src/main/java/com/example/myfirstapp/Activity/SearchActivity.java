package com.example.myfirstapp.Activity;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;
import com.example.myfirstapp.domain.Good;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
                        for (DataSnapshot adSnapshot : dataSnapshot.getChildren()) {
                            Good good = adSnapshot.getValue(Good.class);
                            if (good == null || good.getTitle() == null || good.getType() == null || good.getExchange_location() == null) {
                                continue;
                            }

                            if (good.getTitle().equals(query) && good.getType().equals(categorySpinnerValue) && good.getExchange_location().contains(locationSpinnerValue)) {
                                listOfGoods.add(good);

                                TextView goodName = (TextView) findViewById(R.id.goodName);
                                TextView goodCategory = (TextView) findViewById(R.id.goodCategory);
                                TextView goodLocation = (TextView) findViewById(R.id.goodLocation);

                                goodName.setText(good.getTitle());
                                goodCategory.setText(good.getType());
                                goodLocation.setText(good.getExchange_location());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }});

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


}