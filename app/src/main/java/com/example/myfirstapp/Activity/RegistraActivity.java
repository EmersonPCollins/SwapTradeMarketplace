package com.example.myfirstapp.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.R;
import com.example.myfirstapp.domain.User;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class RegistraActivity extends AppCompatActivity {
    EditText fName_et;
    EditText lName_et;
    EditText email_et;
    EditText stPass_et;
    EditText ndPass_et;
    TextView error_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra);

        Button register = (Button) findViewById(R.id.registButton);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        fName_et = findViewById(R.id.firstName);
        lName_et = findViewById(R.id.lastname);
        email_et = findViewById(R.id.email);
        stPass_et = findViewById(R.id.first_password);
        ndPass_et = findViewById(R.id.second_password);
        error_tv = findViewById(R.id.errorMessageView);

        String fName = fName_et.getText().toString();
        String lName = lName_et.getText().toString();
        String email = email_et.getText().toString();
        String stPass = stPass_et.getText().toString();
        String ndPass = ndPass_et.getText().toString();

        if(!nameEmpty(fName,lName) && validEmail(email) && validPass(stPass) && stPass.equals(ndPass)){
            User user = new User(fName, lName, email, stPass);
            //Database connection here
            goToHome();
        }else{
            TextView error_et = (TextView) findViewById(R.id.errorText);
            error_et.setText("invalid input");
        }

    }

    private void goToHome() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    private boolean validEmail(String email) {
        if(Pattern.matches("^[a-z]+[0-9]*@([\\w-]+\\.)+[\\w-]{2,4}$", email)){
            return true;
        }
        error_tv.setText("Error: Email not valid");
        return false;
    }

    private boolean validPass(String password) {
        if(Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", password)){
            return true;
        }
        error_tv.setText("Error: password not valid");
        return false;
    }

    private boolean nameEmpty(String fName, String lName){
        if(fName.isEmpty() || lName.isEmpty()){
            error_tv.setText("Error: name fields can not be empty");
            return true;
        }
        return false;
    }
}


