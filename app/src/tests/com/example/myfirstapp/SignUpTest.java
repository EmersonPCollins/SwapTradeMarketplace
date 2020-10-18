package com.example.myfirstapp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class SignUpTest {
    // Test Data
    public static final String firstName = "Anne";
    public static final String lastName = "Droid";
    public static final String email = "anned@studio.com";
    public static final String password = "apple";
    User user;

    // Assumes that creating a user will create an account
    @Before
    public void setup() throws Exception {
        user = new User(firstName, lastName, email, password);
    }

    @Test
    public void testNewUserCanSignUp() {
        assertEquals(user.getFirstName(),"Anne" );
        assertEquals(user.getLastName(), "Droid");
        assertEquals(user.getEmail(), "anned@studio.com");
        assertEquals(user.getPassword(), "apple");
    }


    // Checks that a user cannot sign up with an existing email
    @Test
    public void testExistingUserCannotSignUp() throws Exception {
        boolean userCannotSignUp = false;
        User existingUser = new User(firstName, lastName, email, password);
        if(existingUser.getEmail().equals(user.getEmail())) {
            userCannotSignUp = true;
            throw new Exception("User Exists already is" + userCannotSignUp);
        }
    }


    @After
    public void tearDown() throws Exception {
        user.deleteAccount();
        assertNull(user.getFirstName());
    }

}
