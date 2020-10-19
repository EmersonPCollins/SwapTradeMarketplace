package com.example.myfirstapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GoodsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        Spinner spinner = findViewById(R.id.categoriesSpinner);
        Button submitButton = findViewById(R.id.submitGoodButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView helloTextView = (TextView) findViewById(R.id.errorMessageTextView);
            }
        });
    }

    // Currently this saves the text of the selected category
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     *
     * To be examined
     *
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void validate() throws ParseException {
        EditText title = (EditText) findViewById(R.id.titleText);
        EditText description = (EditText) findViewById(R.id.descriptionText);
        EditText date = (EditText) findViewById(R.id.dateText);
        EditText location = (EditText) findViewById(R.id.locationText);
        TextView errorMessage = (TextView) findViewById(R.id.errorMessageTextView);
       // if(!title.getText().toString().isEmpty() && !description.getText().toString().isEmpty() && !location.getText().toString().isEmpty() && goodDate(date.getText().toString())){
            //ADD TO DATABASE
            //replace "email@email.com" with the logged in user email

    }


    //METHODS I USE TO CHECK THE DATE ARE VALID REQUIRE THIS API PART
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean goodDate(String sdate) throws ParseException {
        if(sdate.equals("")){
            return false;
        }
        LocalDate d1 = java.time.LocalDate.now();
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
        LocalDate date = LocalDate.parse(sdate,f);
        if(!sdate.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")){
            return false;
        }
        if(date.isBefore(d1)){
            return false;
        }
        return true;
    }
    **/
}