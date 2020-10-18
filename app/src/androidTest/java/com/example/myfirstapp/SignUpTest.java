package com.example.myfirstapp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class SignUpTest {
    // Test Data
    String firstName = "Anne";
    String lastName = "Droid";
    String email = "anned@studio.com";
    String password = "apple";
    User user;

    // Assumes that creating a user will create an account
    @Before
    public void setup() throws Exception {
        user = new User(firstName, lastName, email, password);
    }

    @Test
    public void testNewUserCanSignUp() {
        assertEquals(user.getFirstName(), this.firstName);
        assertEquals(user.getLastName(), this.lastName);
        assertEquals(user.getEmail(), this.email);
        assertEquals(user.getPassword(), this.password);
    }


    // Checks that a user cannot sign up with an existing email
    @Test
    public void testExistingUserCannotSignUp() {
        boolean userCannotSignUp = false;
        User existingUser = new User(firstName, lastName, email, password);
        try {
            existingUser.getEmail();
        }
        catch(NullPointerException e) {
            userCannotSignUp = true;
        }

        assertTrue(userCannotSignUp);
    }


    @After
    public void tearDown() throws Exception {
        user.deleteAccount();
        user = null;
    }

}
