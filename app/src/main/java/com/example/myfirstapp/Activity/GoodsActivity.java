package com.example.myfirstapp.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myfirstapp.domain.*;
import com.example.myfirstapp.R;
import com.example.myfirstapp.domain.Good;
import com.example.myfirstapp.service.DatabaseService;

import java.time.LocalDate;

public class GoodsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText titleText;
    private EditText dateText;
    private EditText locationText;
    private EditText descriptionText;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        Spinner spinner = findViewById(R.id.categoriesSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        titleText = findViewById(R.id.titleText);
        dateText = findViewById(R.id.dateText);
        locationText = findViewById(R.id.locationText);
        descriptionText = findViewById(R.id.descriptionText);
        errorMessage = findViewById(R.id.errorMessageTextView);

    }

    // Currently this saves the text of the selected category
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean validateTitle(String titleInput){
        if(titleInput.isEmpty()) {
            errorMessage.setText("Error: Enter a valid title.");
            return false;
        }
        return true;
    }

    private boolean validateLocation(String locationInput){
        if(locationInput.isEmpty()) {
            errorMessage.setText("Error: Enter a location.");
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validateDate(String dateInput){
        boolean goodDate = goodDate(dateInput);
        if(!goodDate) {
            errorMessage.setText("Error: Enter a valid date.");
            return false;
        }
        return true;
    }

    private boolean validateDescription(String descriptionInput){
        if(descriptionInput.isEmpty()) {
            errorMessage.setText("Error: Enter a description.");
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSubmit(View view) {
        errorMessage.setText(null);
        String titleInput = titleText.getText().toString().trim();
        String endDate = dateText.getText().toString().trim();
        String descriptionInput = descriptionText.getText().toString().trim();
        String locationInput = locationText.getText().toString().trim();
        LocalDate startDate = LocalDate.now();

        if(validateTitle(titleInput) && validateLocation(locationInput) && validateDate(endDate) && validateDescription(descriptionInput)) {
            insertGood(titleInput, startDate.toString(), endDate, descriptionInput, locationInput, "email@example.com");
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean goodDate(String sdate) {
        if(sdate.isEmpty()){
            return false;
        }
        if(!sdate.matches("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$")){
            return false;
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertGood(String title, String startDate, String endDate, String description, String location, String email){
        Good good = new Good(title, startDate, endDate, description, location, email);
        //get the db connection
        DatabaseService db = new DatabaseService();
        db.writeGood(good);
    }

}
