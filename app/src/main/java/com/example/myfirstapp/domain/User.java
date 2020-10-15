package com.example.myfirstapp.domain;

import java.util.Objects;

/**
 * Represents a user of the app, used in FireBase DB
 *
 */
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    /**
     * Creates a user
     *
     * @param firstName - first name of user
     * @param lastName - last name of user
     * @param email - email of user
     * @param password - password of user
     */
    public User(String firstName, String lastName, String email, String password) {

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

}
