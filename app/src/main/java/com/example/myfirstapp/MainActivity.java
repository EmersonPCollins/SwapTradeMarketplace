package com.example.myfirstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.Activity.*;
import com.example.myfirstapp.domain.Good;
import com.example.myfirstapp.domain.RequestNotification;
import com.example.myfirstapp.domain.User;
import com.example.myfirstapp.service.DatabaseService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;

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

        Button test = findViewById(R.id.test_email);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"bennma14@gmail.com"}); //recipient email
            email.putExtra(Intent.EXTRA_SUBJECT, "accepted request"); //subject
            email.putExtra(Intent.EXTRA_TEXT, "your request for [insert here owner of product email]'s product has been accepted"); //body

            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailText.getText().toString();
                final String password = passwordText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    errorText.setText("Missing fields for log in");
                    return;
                }
                else {
                    SharedPreferences preference = getSharedPreferences("login", MODE_PRIVATE);
                    preference.edit().putString("email", email).apply();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                    final String id = email.replace(".", "");

                    ref.child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String passwordGiven = dataSnapshot.child("password").getValue(String.class);

                            if (password.equals(passwordGiven)) {
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }});

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

