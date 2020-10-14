package com.example.myfirstapp;

import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void validate() throws ParseException {
        EditText title = (EditText) findViewById(R.id.titleText);
        EditText description = (EditText) findViewById(R.id.descriptionText);
        EditText date = (EditText) findViewById(R.id.dateText);
        EditText location = (EditText) findViewById(R.id.locationText);
        TextView errorMessage = (TextView) findViewById(R.id.errorMessageTextView);
        if(!title.getText().toString().isEmpty() && !description.getText().toString().isEmpty() && location.getText().toString().isEmpty() && goodDate(date.getText().toString())){
            //ADD TO DATABASE
        }else{
            if(title.getText().toString().isEmpty()){
                errorMessage.setText("Error: Enter a valid title.");
            }else{
                if(description.getText().toString().isEmpty()){
                    errorMessage.setText("Error: Enter a description.");
                }else{
                    if(!goodDate(date.getText().toString())){
                        errorMessage.setText("Error: Enter a valid date.");
                    }else{
                        if(location.getText().toString().equals("")){
                            errorMessage.setText("Error: Enter a location.");
                        }
                    }
                }
            }
        }
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

}
