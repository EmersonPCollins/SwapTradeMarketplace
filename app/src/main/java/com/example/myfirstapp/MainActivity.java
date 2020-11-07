package com.example.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.Activity.*;
import com.example.myfirstapp.service.DatabaseService;
import com.google.firebase.database.FirebaseDatabase;

/**
 * MainActivity is the login page of the app
 */
public class MainActivity extends AppCompatActivity {

    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseService = new DatabaseService(FirebaseDatabase.getInstance());

        final EditText emailText = findViewById(R.id.emailField);
        final EditText passwordText = findViewById(R.id.passwordField);
        final Button loginButton = findViewById(R.id.loginButton);
        final Button registerButton = findViewById(R.id.newRegisterButton);
        final TextView errorText = (TextView) findViewById(R.id.errorText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    errorText.setText("Missing fields for log in");
                    return;
                }

                if (databaseService.userExists(email, password)) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
                else {
                    errorText.setText("The email address or password does not match any account.");
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistraActivity.class);
                startActivity(intent);
            }
        });
    }
}

