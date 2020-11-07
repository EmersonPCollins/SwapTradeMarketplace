package com.example.myfirstapp.domain;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a user of the app, used in FireBase DB
 *
 */
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User() {};

    /**
     * Creates a user
     *
     * @param firstName - first name of user
     * @param lastName - last name of user
     * @param email - email of user
     * @param password - password of user
     */
    public User(String firstName, String lastName, String email, String password) {

        this.id = email.replace(".", "");
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public User(String firstName, String lastName, String email, String password, String id) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    /**
     * Getters
     */
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

    public String getId() {
        return id;
    }

    /**
     * Setters
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(User user) {
        return this.firstName.equals(user.firstName) &&
                this.lastName.equals(user.lastName) &&
                this.email.equals(user.email) &&
                this.password.equals(user.password);
    }

    public void setId(String id) {
        this.id = id;
    }

}
