package com.example.myfirstapp;

// Stub added to pass SignUpTest
public class User {
    String firstName, lastName, email, password;

    User() {}

    User(String fn, String ln, String em, String pass){
        firstName = fn;
        lastName = ln;
        email = em;
        password = pass;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void deleteAccount(){
    }
}
